<%-- 
Document   : index.jsp
Created on : Aug 23, 2013, 10:10:12 AM
Author     : Sai Krishna Daddala
--%>
<%@ page language="java" import="java.sql.*"%>
<%@page language="java" import="java.io.File,java.text.*,java.util.Calendar,java.util.ArrayList,java.util.Hashtable,java.util.Vector,com.ngit.javabean.loglevel.*,java.util.Date,java.util.Properties,java.util.*,com.ngit.javabean.loglevel.LogLevel,javax.servlet.ServletContext,java.util.Properties,java.io.FileInputStream"%>
<%
String strUserid=(String)session.getAttribute("sesBatteryUserLogin");
if(strUserid==null)
{
	strUserid="";
	session.setAttribute("sesErrorMsg","Session Timed Out. Please login again");
	response.sendRedirect("/bookbattery/index.jsp");
	return;
}
%>
<%response.setHeader("Pragma","no-cache"); response.setHeader("Cache-Control","no-store"); response.setHeader("Expires","0"); response.setDateHeader("Expires",-1); %>
<%
	String nametype =(session.getAttribute("nametype") != null)?(String)session.getAttribute("nametype"):""; 
	Properties propsMOPConfig = new Properties();
	ServletContext context = getServletContext();
	FileInputStream fin1      = new FileInputStream(new File(context.getRealPath("properties/bookbatteryconfig.properties"))); 
	propsMOPConfig.load(fin1); 
	fin1.close(); 
	String publicUrl = (propsMOPConfig.getProperty("publicUrl")!=null)?propsMOPConfig.getProperty("publicUrl"):"";
	String baseURL = (propsMOPConfig.getProperty("baseURL")!=null)?propsMOPConfig.getProperty("baseURL"):"";

	String keyword = (request.getParameter("keyword")!=null)?(request.getParameter("keyword")):"0"; 
	LogLevel.DEBUG(5,new Throwable(),"keyword :"+keyword );

	String pointsearned = (String)session.getAttribute("pointsearned");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<link href="../../css/bookbattery.css" rel="stylesheet" type="text/css" />
<link rel="shortcut icon" href="../../images/favicon.png" type="image/x-icon"/>
<script src="../../js/jquery-1.3.2.min.js" type="text/javascript"></script>
<script language="JavaScript" src="../../js/pophide.js" type="text/javascript"></script>
<script language="JavaScript" src="../../js/jquery-1.8.2.js" type="text/javascript"></script>
 <!-- Google Fonts -->
    <link href='http://fonts.googleapis.com/css?family=Titillium+Web:400,200,300,700,600' rel='stylesheet' type='text/css'>
    <link href='http://fonts.googleapis.com/css?family=Roboto+Condensed:400,700,300' rel='stylesheet' type='text/css'>
    <link href='http://fonts.googleapis.com/css?family=Raleway:400,100' rel='stylesheet' type='text/css'>
	
	
    <link rel="stylesheet" href="../../css/reset.css">
	<link rel="stylesheet" href="../../css/set2.css">
	<link rel='stylesheet prefetch' href='http://fonts.googleapis.com/css?family=Playball'>
   
    <!-- Bootstrap -->
    <link rel="stylesheet" href="../../css/bootstrap.min.css">
    <!-- Font Awesome -->
    <link rel="stylesheet" href="../../css/font-awesome.min.css">
	<!-- Custom CSS -->
    <link rel="stylesheet" href="../../css/owl.carousel.css">
    <link rel="stylesheet" href="../../css/style.css">
	<link rel="stylesheet" href="../../css/style1.css">
	<link rel="stylesheet" href="../../css/responsive.css">


<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>BookBattery - India' s No.1 Online Car/Inverter Battery Store</title>
<meta name='description' content="Order and Get Genuine Amaron Car Battery or Inverter Battery or Bike Battery.  BookBattery is India's No.1 Online Battery Store. Best Discount Prices with Free Installation and Original Warranty is Offered" />
<meta name='keywords' content='Battery, Batteries, Car Battery, Inverter Battery, Bike Battery, Branded Battery, Buy Battery online, Car Battery Online' />
<script type="text/javascript">
//<![CDATA[

(function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
  (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
  m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
  })(window,document,'script','//www.google-analytics.com/analytics.js','ga');

  ga('create', 'UA-45977372-1', 'BookBattery.com');
  ga('send', 'pageview');
//]]>
</script>
<style type='text/css'>
/*<![CDATA[*/


.button_example{
border:0px; -webkit-border-radius: 3px; -moz-border-radius: 3px;border-radius: 3px;font-size:12px;font-family:arial, helvetica, sans-serif; padding: 10px 10px 10px 10px; text-decoration:none; display:inline-block;text-shadow: -1px -1px 0 rgba(0,0,0,0.3);font-weight:bold; color: #FFFFFF;
 background-color: #FF8c00; background-image: -webkit-gradient(linear, left top, left bottom, from(#FF8c00), to(#FF8c00));
 background-image: -webkit-linear-gradient(top, #FF8c00, #FF8c00);
 background-image: -moz-linear-gradient(top, #FF8c00, #FF8c00);
 background-image: -ms-linear-gradient(top, #FF8c00, #FF8c00);
 background-image: -o-linear-gradient(top, #FF8c00, #FF8c00);
 background-image: linear-gradient(to bottom, #FF8c00, #FF8c00);filter:progid:DXImageTransform.Microsoft.gradient(GradientType=0,startColorstr=#FF8c00, endColorstr=#FF8c00);
}
.divmobile{left:22.5%;top:15%;font: 16px/22px 'Trebuchet MS', Verdana, Arial; text-align:left; border:6px solid #424242; border-radius: 6px 6px 6px 6px;position:absolute;padding: 1px;display: none;z-index:60;}

.divmobilelaptop{left:37.5%;top:15%;font: 16px/22px 'Trebuchet MS', Verdana, Arial; text-align:left; border:6px solid #424242; border-radius: 6px 6px 6px 6px;position:absolute;padding: 1px;display: none;z-index:60;}

.knowmore{font-family:Verdana;font-size:11px;color: #FF8C00;text-decoration:none;}
.knowmore:hover{font-family:Verdana;font-size:11px;color:#FF8C00;text-decoration:underline;}

.title{font-family:Verdana;font-size:10px;font-weight:bold;color: #FF8C00;text-decoration:none;}
.title:hover{font-family:Verdana;font-size:10px;font-weight:bold;color:#FF8C00;text-decoration:underline;}
/*]]>*/
</style>
</head>
<body >
<!-- Start Alexa Certify Javascript -->
<script type="text/javascript">
_atrk_opts = { atrk_acct:"VrG0j1a4ZP00yt", domain:"BookBattery.com",dynamic: true};
(function() { var as = document.createElement('script'); as.type = 'text/javascript'; as.async = true; as.src = "https://d31qbv1cthcecs.cloudfront.net/atrk.js"; var s = document.getElementsByTagName('script')[0];s.parentNode.insertBefore(as, s); })();
</script>
<noscript><img src="https://d5nxst8fruw4z.cloudfront.net/atrk.gif?account=VrG0j1a4ZP00yt" style="display:none" height="1" width="1" alt="" /></noscript>
<!-- End Alexa Certify Javascript -->
<form name="userloyalty" action="">
	<div id='divmobile' class='divmobile' style="display:none;"></div>
	<div id='scrollpopup' class='scrollpopup' style="display:none;"></div>
	<div id='scrollpopup1' class='scrollpopup1' style="display:none;"></div>
	<div id='divmobile' class='divmobile' style="display:none;"></div>
		<jsp:include page = "../../header.jsp" />

			<table width="950" border="0" class="insidecontent c24" align="center" cellspacing="0" cellpadding="0" style='margin-left: 168px;'>
			
			<tr>
				<td>
					<table border="0" align="left"  width="100%" bgcolor="#FFFFFF" cellpadding="0" cellspacing="0" >
						<tr>
							<td>								
								<table border="0" width="100%" style='border: 1px solid #ff8c00;border-radius: 6px 6px 6px 6px;'>
								<tr>
									<td>
									<table class='insidecontent' valign="top" align='center' cellspacing='0'  cellpadding='0' width='100%' border='0' >
									<tr>
												<td>
													<table width="100%" border="0" align="center" style='margin-left:107px;'>
														<tr>
															<td width="25%"></td>
															<td align="left" class = "subheading" style='font-size: 20px;'>User Redeem Points</td>
														</tr>
													</table>
													<table border="0" width="100%" align="center" border="0">
														<tr>
															<!-- to display the consumer loyalty points details  -->
															<td>
																<table width='500'  border='0' align='center' valign='top' border='0' style='margin-left: 350px;'>
																	<tr></tr>
																	<tr>
																		<td height='1' bgcolor='#FFFFFF'></td>
																	</tr>
																	<%
																		if(pointsearned=="0" || pointsearned=="null")
																		{
																	%>
																			<tr>
																				<td>
																					<table align='center' width='360' border='0' cellspacing='0' cellpadding='0'>
																						<tr>
																							<td class='click' align='left' style='font-size: 15px;'>Earned User Loyalty Points-0</td>
																						</tr>
																					</table>
																				</td>
																			</tr>
																			<tr height="5"></tr>
																		<%
																		}
																		else
																		{
																		%>
																			<tr>
																				<td>
																					<table align='center' width='360' border='0' cellspacing='0' cellpadding='0' style='margin-left: 350px;'>
																						<tr>
																							<td class='top1' align='left' style='font-size: 15px;'>
																							
																							<a class="click" href="javascript:merchantdetails()" ><b>Earned User Loyalty Points-<%=pointsearned%></b></a></td>
																						</tr>
																					</table>
																				</td>
																			</tr>
																		<%}
																		%>
																	<tr><td height='1' bgcolor='#FFFFFF'></td></tr>
																</table>
															</td>
														</tr>
			
			<table align="center" cellspacing="0" cellpadding="0" ><tr><td bgcolor='#FFFFFF'><div align='center' id="merchantdetails"></div></td></tr></table>
														<tr>
															<td>
																<div id="loyaltydetails"  width='73%' align='center' height='75%' bgcolor='CCCCCC'></div>
															</td>
														</tr>
														<tr>
														</tr>
														<tr>
															<td height="1" bgcolor="#FFFFFF"></td>
														</tr>
														</table>
														<div id="mobilerecharge" style="display:none;">
														<table align="center" width="360" border="0" cellspacing="0" cellpadding="0">
														<tr>
															<td>
																<table align="center" width="360" border="0" cellspacing="0" cellpadding="0">
																	<tr>
																		<td   class="top1" align="left"><b>Redeem Points</b></td>
																	</tr>
																</table>
															</td>
														</tr>
														<tr height="5"></tr>
												<tr>
													<td>
														<table align="center" width="360" border="0" cellspacing="0" cellpadding="0">
															<tr>
																<td width="25" align="center" valign="middle"><img alt="arrow" src="/bookbattery/images/arrow-right.png" width="16" height="16" /></td>
																<td   class="top1" align="left">Cash Back will be done if u have more than 500 points under particular loyalty program</td>
															</tr>
														</table>
													</td>
												</tr>
												<tr height="5"></tr>
												<tr>
													<td>
														<table align="center" width="360" border="0" cellspacing="0" cellpadding="0">
															<tr>
																<td   width="25" align="center" valign="middle"><img alt="arrow" src="/bookbattery/images/arrow-right.png" width="16" height="16" /></td>
																<td class="top1" align="left">Recharge will be done within 48 hours</td>
															</tr>
														</table>
													</td>
												</tr> 
												<tr height="5"></tr>
												<tr>
													<td>
														<table align="center" width="360" border="0" cellspacing="0" cellpadding="0">
															<tr>
																<td   width="25" align="center" valign="middle"><img alt="arrow" src="/bookbattery/images/arrow-right.png" width="16" height="16" /></td>
																<td class="top1" align="left">Enter Minimum of 10 points to Recharge your Mobile</td>
															</tr>
														</table>
													</td>
												</tr>
												<tr height="5"></tr>
												<tr>
													<td>
														<table align="center" width="360" border="0" cellspacing="0" cellpadding="0">
															<tr>
																<td width="25" align="center" valign="middle"><img alt="arrow" src="/bookbattery/images/arrow-right.png" width="16" height="16" /></td>
																<td  align="left"  class="top1"><a class="click" href="javascript:onClickMobilediv()" >Mobile Recharge</a></td>
															</tr>
															<tr height="5"></tr>
															<tr>
																<td width="25" align="center" valign="middle"><img alt="arrow" src="/bookbattery/images/arrow-right.png" width="16" height="16" /></td>
																<td  align="left"  class="top1"><a class="click" href="javascript:onClickCashBackDiv()" >Cash Back</a></td>
															</tr>
														</table>
													</td>
												</tr> 
												</table>
												</div>
												<!--Mobile recharge div starts here-->			  
												<tr>
													<td bgcolor="#FFFFFF">
														<div id="otherRet" style="display:none;">
															<table width = "200" cellspacing = "0" cellpadding = "1"  bgcolor="#BEADCB" align="center">	
																<tr>
																	<td>
																		<table width = "100%" cellspacing = "0" cellpadding = "0"  bgcolor="#FFFFFF" align="center">	
																			<tr>
																				<td bgcolor="#FFFFFF">
																					<table width="360" align="center">
																						<tr>
																							<td align="center" valign="middle" class="content">Mobile No:</td><td><input class = "top1" type = "text" name = "mobileno" value="" maxlength = "10" size = "42"></td>
																						</tr>
																						<tr>
																							<td align="center" valign="middle" class="content">Confirm Mobile No:</td><td><input class = "top1" type = "text" name = "confirmmobileno" value="" maxlength = "10" size = "42"></td>
																						</tr>
																						<tr>
																							<td align="center" valign="middle" class="content">
																							Points to Recharge:</td>
																							<td><input class = "top1" type = "text" name = "reedempoints" value="" maxlength = "6" size = "42"></td>
																						</tr>
																						<tr>
																							<td  align="left" valign="center" class="content">Serviceprovider:</td>
																							<td><jsp:include page="serviceprovider.jsp"/></td>
																						</tr>
																						<tr>
																							<td  align="left" valign="center" class="content">State:</td>
																							<td><jsp:include page="state.jsp"/></td>
																						</tr>
																						<table align="right" width="300" border="0">
																							<tr>
																								<td height = "5" bgcolor = "#FFFFFF" ></td>
																							</tr>
																							<tr id="hidebutton">
																								<td width="80" align="right"><a href="javascript:addmerchantreedem();" id="btnSearch" name="add" value="addbutton" class="button4" >Add </a></td>
																								<td width="50" align="center"><a href="javascript:document.userloyalty.reset();" class="button4" name="reset">Reset</a> </td>
																								<td width="50" align="left"><a href="javascript:onClickMobilediv()" class="button4">Cancel</a></td>
																							</tr>
																							<tr>
																								<td height = "5" bgcolor = "#FFFFFF" ></td>
																							</tr>
																						</table>
																					</table>
																				</td>
																			</tr>
																			</table>
																	</td>
																</tr>
															</div>
														</td>
													</tr>
													<!--Mobile recharge div ends here-->
													<br>
													<!--Cash Back div starts here-->			  
													<tr>
														<td bgcolor="#FFFFFF">
															<div id="cashbackdiv"  style="display:none;">
																<table width = "200" border="0" cellspacing = "0" cellpadding = "1"  bgcolor="#BEADCB" align="center">	
																	<tr>
																		<td>
																			<table width = "100%" cellspacing = "0" cellpadding = "0"  bgcolor="#FFFFFF" align="center">	
																				<tr>
																					<td> 
																						<table width="360" align="center" border="0">
																						<tr>
																							<td align="left" valign="middle" class="top1">Mobile No:</td><td><input class = "top1" type = "text" name = "cashmobileno" value="" maxlength = "10" size = "43"></td>
																						</tr>
																						<tr>
																							<td align="left" valign="middle" class="top1">
																							Points to Cash Back :</td>
																							<td><input class = "top1" type = "text" name = "cashreedempoints" value="" maxlength = "6" size = "43"></td>
																						</tr>
																							<tr>
																								<td align="left" valign="middle" class="top1">Address:</td><td><textarea rows="5" cols="50" id="useraddress" name = "useraddress" value=""></textarea></td>
																							</tr>
																							<table align="center" width="300" border="0">
																								<tr>
																									<td height = "5" bgcolor = "#FFFFFF" ></td>
																								</tr>
																								<tr id="hidebutton">
																									<td width="80" align="right"><a href="javascript:submitaddress('merchant');" id="btnSearch" name="add" value="addbutton" class="button4" >Submit </a></td>
																									<td width="50" align="center"><a href="javascript:document.userloyalty.reset();" class="button4" name="reset">Reset</a> </td>
																									<td width="50" align="left"><a href="javascript:onClickCashBackDiv()" class="button4">Cancel</a></td>
																								</tr>
																								<tr>
																									<td height = "5" bgcolor = "#FFFFFF" ></td>
																								</tr>
																							</table>
																							<tr>
																						<td class="top1" bgcolor = "#FFFFFF"><font color="#FF8C00">Note:Please mention correct address cheque will be sent for the mentioned address</font></td></tr>
																					</td>
																						</table>
																						
																				</tr>
																			</table>
																		</td>
																	</tr>
																</div>
															</td>
														</tr>
									
								</table>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			<tr><td height="50"></td></tr>
			<!-- Inner content ends here -->
			<!-- Footer Starts Here -->
			<tr>
				<td>
					
				</td>
			</tr>                        
			<!-- footer Ends Here -->
		</table>
				<jsp:include page = "../../footer.jsp" />

   <input type="hidden" name="publicUrl" id="publicUrl" value="<%=publicUrl%>"/>
<input type="hidden" name="keyword" id='keyword' value="<%=keyword%>">
<input type="hidden" name="strUserid"  id="strUserid" value="<%=strUserid%>">
</form>
<%
String strbatLogMsg=(String)session.getAttribute("sesErrorloginMsg");
if(strbatLogMsg ==null)
{
	strbatLogMsg="";
}
else
{
%>
<script type="text/javascript">
//<![CDATA[
var sesmessg; 
sesmessg= "<%=strbatLogMsg%>";
document.getElementById("displyloginsesmsg").innerHTML=sesmessg;
//]]>
</script>
<%
	session.removeAttribute("sesErrorloginMsg");
}
%>
<script type="text/javascript">
//<![CDATA[

$(window).load(function()
{
	 $('#scrollpopups').hide();	 
});


(function() {
    var po = document.createElement('script'); po.type = 'text/javascript'; po.async = true;
      po.src = '../../js/fetchdropdownvalues.js?<%=context.getInitParameter("fetchdropdownvalues")%>';
    var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(po, s);
  })();
if (/MSIE (\d+\.\d+);/.test(navigator.userAgent))
{
	var ieversion=new Number(RegExp.$1)
	if (ieversion > 9)
	{
		(function() {
		var po = document.createElement('script'); po.type = 'text/javascript'; po.async = true;
		po.src = '../../js/jqFancyTransitions.1.8.min.js';
		var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(po, s);
		})();
	} 
}
browsername=navigator.appName;
if ((browsername.indexOf("Netscape")!=-1) || (browsername.indexOf("Opera")!=-1))
{

		(function() {
		var po = document.createElement('script'); po.type = 'text/javascript'; po.async = true;
		po.src = '../../js/jqFancyTransitions.1.8.min.js';
		var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(po, s);
		})();
}
//]]>
function merchantdetails()
{
	var Email=document.getElementById("strUserid").value;
	var  url="../../servlet/ConsumerLoyalty?hidWhatToDo=dispmerchantLoyalty&emailid="+Email+"&requestno="+(Math.random()*100);
	var xmlhttp= "";
	var resp= "";
	if (window.XMLHttpRequest)
	{
		xmlhttp=new XMLHttpRequest();
	}
	else
	{
		xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
	}
	xmlhttp.onreadystatechange=function()
	{
		$("#mobilerecharge").show();
		if (xmlhttp.readyState==4)
		{
			if(xmlhttp.status!=200)
			{
				alert("Error Occured. Please try again.");
				return;
			}
			resp = xmlhttp.responseText;
			if(resp.indexOf("Session Timed Out")>=0)
			{
					errMsg ="<table border='0' align='center' width='350px' height='10px' cellpadding='0' cellspacing='0' bgcolor='#88689f'><tr class='top1'><td>&nbsp;<font color='#FFFFFF'><b>BookBattery</b></td><td align='right'><a href='javascript:SessionExpired(greyout(false));' style='font-weight:bold;text-transform:uppercase;color:#FFFFFF;letter-spacing:1pt;word-spacing:2pt;font-size:1em;text-align:left;font-family:georgia, serif;line-height:1;text-decoration:none;'>X</a></td></tr></table><br><table border='0' width='350px' height='10px' align='center'><tr class='pages'><td align='center'>Session Expired Please Login again.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='SessionExpired();' class='button4'><br></td></tr><tr height='15'></tr></table>";
					document.getElementById("loyaltydetails").style.display='block';
					document.getElementById("loyaltydetails").innerHTML=errMsg
					document.getElementById("emailok").focus();
					greyout(true);
					return;
			}
			//document.getElementById("loyaltydetails").innerHTML=resp;
			$("#merchantdetails").html(resp);
			//document.getElementById("loyaltydetails").innerHTML=resp
		}	
	}
	xmlhttp.open("GET",url,true);
	xmlhttp.send();	
}
function onClickMobilediv()
{
	$('#hidebutton').show();
	$('#otherRet1').hide();
	$('#cashbackdiv').hide();
	var paramName = "otherRet";
	document.userloyalty.mobileno.value="";
	document.userloyalty.confirmmobileno.value="";
	document.userloyalty.reedempoints.value="";
	document.userloyalty.serviceprovider.value="0";
	document.userloyalty.state.value="0";
	if($("#"+paramName).is(':visible'))
	{
		$("#"+paramName).hide();
	}
	else
	{
		$("#"+paramName).show();
	}
}
function addmerchantreedem()
{
	var mobileno=document.userloyalty.mobileno.value;
	var confirmmobileno=document.userloyalty.confirmmobileno.value;
	var userreedempoints=document.userloyalty.reedempoints.value;
	var serviceprovider=document.userloyalty.serviceprovider.value;
	var state=document.userloyalty.state.value;
	var x=userreedempoints;
	var x1=mobileno;
	var loyaltyid,merchantpoints,a;
	var Selectchkbox=0;
	for (i = 0; i < document.userloyalty.programname.length; i++)
	{
		if(document.userloyalty.programname[i].checked)
		{
			loyaltyid=document.userloyalty.programname[i].value;
			merchantpoints=document.userloyalty.programvalue[i].value;
			Selectchkbox++;
		}
	}
	if(Selectchkbox==0)
	{
		errMsg ="<table border='0' width='400px' height='10px' cellpadding='0' cellspacing='0' bgcolor='#88689f'><tr class='top1'><td>&nbsp;<font color='#FFFFFF'><b>BookBattery</b></td><td align='right'><a href='javascript:closeScrollPopup(greyout(false));'style='font-weight:bold;text-transform:uppercase;color:#FFFFFF;letter-spacing:1pt;word-spacing:2pt;font-size:1em;text-align:left;font-family:georgia, serif;line-height:1;text-decoration:none;'>X</a></td></tr></table><br><table border='0' width='400px' height='10px'><tr class='pages'><td align='center'>Please Select atleast one loyalty program for mobile recharge</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='closeScrollPopup(greyout(false));' class='button4'><br></td></tr><tr height='15'></tr></table>";
		document.getElementById("scrollpopup").style.display='block';
		document.getElementById("scrollpopup").innerHTML=errMsg
		document.getElementById("emailok").focus();
		greyout(true);
		return;
	}
	if(Selectchkbox>= 2)
	{
		errMsg ="<table border='0' width='400px' height='10px' cellpadding='0' cellspacing='0' bgcolor='#88689f'><tr class='top1'><td>&nbsp;<font color='#FFFFFF'><b>BookBattery</b></td><td align='right'><a href='javascript:closeScrollPopup(greyout(false));'style='font-weight:bold;text-transform:uppercase;color:#FFFFFF;letter-spacing:1pt;word-spacing:2pt;font-size:1em;text-align:left;font-family:georgia, serif;line-height:1;text-decoration:none;'>X</a></td></tr></table><br><table border='0' width='400px' height='10px'><tr class='pages'><td align='center'>Please Select atleast one loyalty program for mobile recharge</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='closeScrollPopup(greyout(false));' class='button4'><br></td></tr><tr height='15'></tr></table>";
		document.getElementById("scrollpopup").style.display='block';
		document.getElementById("scrollpopup").innerHTML=errMsg
		document.getElementById("emailok").focus();
		greyout(true);
		return;
	}
	if(mobileno == "")
	{
		errMsg ="<table border='0' width='400px' height='10px' cellpadding='0' cellspacing='0' bgcolor='#88689f'><tr class='top1'><td>&nbsp;<font color='#FFFFFF'><b>BookBattery</b></td><td align='right'><a href='javascript:closeScrollPopup(greyout(false));'style='font-weight:bold;text-transform:uppercase;color:#FFFFFF;letter-spacing:1pt;word-spacing:2pt;font-size:1em;text-align:left;font-family:georgia, serif;line-height:1;text-decoration:none;'>X</a></td></tr></table><br><table border='0' width='400px' height='10px'><tr class='pages'><td align='center'>Please enter Mobile Number like '9876543210'.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='close_popup_mobile3();' class='button4'><br></td></tr><tr height='15'></tr></table>";
		document.getElementById("scrollpopup").style.display='block';
		document.getElementById("scrollpopup").innerHTML=errMsg
		document.getElementById("emailok").focus();
		greyout(true);
		return;

	}
	if (mobileno != "")
	{
		var checkOK = "0123456789";
		var checkStr = mobileno;
		var allValid = true;
		var decPoints = 0;
		var allNum = "";
		for (i = 0; i < checkStr.length; i++)
		{
			ch = checkStr.charAt(i);

				for (j = 0; j < checkOK.length; j++)
				if (ch == checkOK.charAt(j))
					break;
				if (j == checkOK.length)
				{
					allValid = false;
					break;
				}
		}
		if (!allValid)
		{
			errMsg ="<table border='0' width='400px' height='10px' cellpadding='0' cellspacing='0' bgcolor='#88689f'><tr class='top1'><td>&nbsp;<font color='#FFFFFF'>BookBattery</td><td align='right'><a href='javascript:closeScrollPopup(greyout(false));'style='font-weight:bold;text-transform:uppercase;color:#FFFFFF;letter-spacing:1pt;word-spacing:2pt;font-size:1em;text-align:left;font-family:georgia, serif;line-height:1;text-decoration:none;'>X</a></td></tr></table><br><table border='0' width='400px' height='10px'><tr class='pages'><td align='center'>Please enter numerics in the \" Mobile Number\" field.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='close_popup_mobile();' class='button4'><br></td></tr><tr height='15'></tr></table>";
			document.getElementById("scrollpopup").style.display='block';
			document.getElementById("scrollpopup").innerHTML=errMsg
			document.getElementById("emailok").focus();
			greyout(true);
			return;
		}
		if (document.userloyalty.mobileno.value.length < 10)
		{
			errMsg ="<table border='0' width='400px' height='10px' cellpadding='0' cellspacing='0' bgcolor='#88689f'><tr class='top1'><td>&nbsp;<font color='#FFFFFF'><b>BookBattery</b></td><td align='right'><a href='javascript:closeScrollPopup(greyout(false));'style='font-weight:bold;text-transform:uppercase;color:#FFFFFF;letter-spacing:1pt;word-spacing:2pt;font-size:1em;text-align:left;font-family:georgia, serif;line-height:1;text-decoration:none;'>X</a></td></tr></table><br><table border='0' width='400px' height='10px'><tr class='pages'><td align='center'>Please enter mobile number like '9876543210'.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='close_popup_mobile();' class='button4'><br></td></tr><tr height='15'></tr></table>";
			document.getElementById("scrollpopup").style.display='block';
			document.getElementById("scrollpopup").innerHTML=errMsg
			document.getElementById("emailok").focus();
			greyout(true);
			return;
		}
		if (document.userloyalty.mobileno.value.length==10)
		{
			if (mobileno < 7000000000 )
			{
				errMsg ="<table border='0' width='400px' height='10px' cellpadding='0' cellspacing='0' bgcolor='#88689f'><tr class='top1'><td>&nbsp;<font color='#FFFFFF'><b>BookBattery</b></td><td align='right'><a href='javascript:closeScrollPopup(greyout(false));'style='font-weight:bold;text-transform:uppercase;color:#FFFFFF;letter-spacing:1pt;word-spacing:2pt;font-size:1em;text-align:left;font-family:georgia, serif;line-height:1;text-decoration:none;'>X</a></td></tr></table><br><table border='0' width='400px' height='10px'><tr class='pages'><td align='center'>Mobile Number Should start with 7 or 8 or 9...!</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='close_popup_mobile();' class='button4'><br></td></tr><tr height='15'></tr></table>";
				document.getElementById("scrollpopup").style.display='block';
				document.getElementById("scrollpopup").innerHTML=errMsg
				document.getElementById("emailok").focus();
				greyout(true);
				return;
			}
		}
	}
	if(confirmmobileno == "")
	{
		errMsg ="<table border='0' width='400px' height='10px' cellpadding='0' cellspacing='0' bgcolor='#88689f'><tr class='top1'><td>&nbsp;<font color='#FFFFFF'><b>BookBattery</b></td><td align='right'><a href='javascript:closeScrollPopup(greyout(false));'style='font-weight:bold;text-transform:uppercase;color:#FFFFFF;letter-spacing:1pt;word-spacing:2pt;font-size:1em;text-align:left;font-family:georgia, serif;line-height:1;text-decoration:none;'>X</a></td></tr></table><br><table border='0' width='400px' height='10px'><tr class='pages'><td align='center'>Please enter mobile number in Confirm Mobile Number field.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='closePopupc();' class='button4'><br></td></tr><tr height='15'></tr></table>";
		document.getElementById("scrollpopup").style.display='block';
		document.getElementById("scrollpopup").innerHTML=errMsg
		document.getElementById("emailok").focus();
		greyout(true);
		return;
	}
	else
	{
		if (confirmmobileno != "")
		{
			var checkOK = "0123456789";
			var checkStr = confirmmobileno;
			var allValid = true;
			var decPoints = 0;
			var allNum = "";
			for (i = 0; i < checkStr.length; i++)
			{
				ch = checkStr.charAt(i);

				for (j = 0; j < checkOK.length; j++)
					if (ch == checkOK.charAt(j))
						break;

				if (j == checkOK.length)
					{
					allValid = false;
					break;
					}
			}
			if (!allValid)
			{
				errMsg ="<table border='0' width='400px' height='10px' cellpadding='0' cellspacing='0' bgcolor='#88689f'><tr class='top1'><td>&nbsp;<font color='#FFFFFF'><b>BookBattery</b></td><td align='right'><a href='javascript:closeScrollPopup(greyout(false));'style='font-weight:bold;text-transform:uppercase;color:#FFFFFF;letter-spacing:1pt;word-spacing:2pt;font-size:1em;text-align:left;font-family:georgia, serif;line-height:1;text-decoration:none;'>X</td></tr></table><br><table border='0' width='400px' height='10px'><tr class='pages'><td align='center'>Please enter numerics in the \"Confirm Mobile Number\" field.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='closePopupc();' class='button4'><br></td></tr><tr height='15'></tr></table>";
				document.getElementById("scrollpopup").style.display='block';
				document.getElementById("scrollpopup").innerHTML=errMsg
				document.getElementById("emailok").focus();
				greyout(true);
				return;

			}
			if (document.userloyalty.confirmmobileno.value.length < 10)
			{
				errMsg ="<table border='0' width='400px' height='10px' cellpadding='0' cellspacing='0' bgcolor='#88689f'><tr class='top1'><td>&nbsp;<font color='#FFFFFF'><b>BookBattery</b></td><td align='right'><a href='javascript:closeScrollPopup(greyout(false));'style='font-weight:bold;text-transform:uppercase;color:#FFFFFF;letter-spacing:1pt;word-spacing:2pt;font-size:1em;text-align:left;font-family:georgia, serif;line-height:1;text-decoration:none;'>X</a></td></tr></table><br><table border='0' width='400px' height='10px'><tr class='pages'><td align='center'>Please enter valid mobile number in Cofirm Mobile number field</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='closePopupc();' class='button4'><br></td></tr><tr height='15'></tr></table>";
				document.getElementById("scrollpopup").style.display='block';
				document.getElementById("scrollpopup").innerHTML=errMsg
				document.getElementById("emailok").focus();
				greyout(true);
				return;
			}
			if (document.userloyalty.confirmmobileno.value.length==10)
			{
				if (confirmmobileno < 7000000000 )
				{
					errMsg ="<table border='0' width='400px' height='10px' cellpadding='0' cellspacing='0' bgcolor='#88689f'><tr class='top1'><td>&nbsp;<font color='#FFFFFF'><b>BookBattery</b></td><td align='right'><a href='javascript:closeScrollPopup(greyout(false));'style='font-weight:bold;text-transform:uppercase;color:#FFFFFF;letter-spacing:1pt;word-spacing:2pt;font-size:1em;text-align:left;font-family:georgia, serif;line-height:1;text-decoration:none;'>X</a></td></tr></table><br><table border='0' width='400px' height='10px'><tr class='pages'><td align='center'>Confirm Mobile Number Should start with 7 or 8 or 9...!</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='closePopupc();' class='button4'><br></td></tr><tr height='15'></tr></table>";
					document.getElementById("scrollpopup").style.display='block';
					document.getElementById("scrollpopup").innerHTML=errMsg
					document.getElementById("emailok").focus();
					greyout(true);
					return;
				}
			}
		}
		if(mobileno!=confirmmobileno)
		{
			errMsg ="<table border='0' width='400px' height='10px' cellpadding='0' cellspacing='0' bgcolor='#88689f'><tr class='top1'><td>&nbsp;<font color='#FFFFFF'><b>BookBattery</b></td><td align='right'><a href='javascript:closeScrollPopup(greyout(false));'style='font-weight:bold;text-transform:uppercase;color:#FFFFFF;letter-spacing:1pt;word-spacing:2pt;font-size:1em;text-align:left;font-family:georgia, serif;line-height:1;text-decoration:none;'>X</a></td></tr></table><br><table border='0' width='400px' height='10px'><tr class='pages'><td align='center'>Mobile Number And Confirm Mobile number Are Not The Same.\n</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='closePopupc();' class='button4'><br></td></tr><tr height='15'></tr></table>";
			document.getElementById("scrollpopup").style.display='block';
			document.getElementById("scrollpopup").innerHTML=errMsg
			document.getElementById("emailok").focus();
			greyout(true);
			return;
		}
	}
	if(userreedempoints == "")
	{
		errMsg ="<table border='0' width='400px' height='10px' cellpadding='0' cellspacing='0' bgcolor='#88689f'><tr class='top1'><td>&nbsp;<font color='#FFFFFF'><b>BookBattery</b></td><td align='right'><a href='javascript:closeScrollPopup(greyout(false));'style='font-weight:bold;text-transform:uppercase;color:#FFFFFF;letter-spacing:1pt;word-spacing:2pt;font-size:1em;text-align:left;font-family:georgia, serif;line-height:1;text-decoration:none;'>X</a></td></tr></table><br><table border='0' width='400px' height='10px'><tr class='pages'><td align='center'>Please enter points to recharge</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='close_popup_mobile1();' class='button4'><br></td></tr><tr height='15'></tr></table>";
		document.getElementById("scrollpopup").style.display='block';
		document.getElementById("scrollpopup").innerHTML=errMsg
		document.getElementById("emailok").focus();
		greyout(true);
		return;
	}
	if (userreedempoints != "")
	{
		var checkOK = "0123456789";
		var checkStr = userreedempoints;
		var allValid = true;
		var decPoints = 0;
		var allNum = "";
		for (i = 0; i < checkStr.length; i++)
		{
			ch = checkStr.charAt(i);
			for (j = 0; j < checkOK.length; j++)
				if (ch == checkOK.charAt(j))
					break;
			if (j == checkOK.length)
			{
				allValid = false;
				break;
			}
		}
		if (!allValid)
		{
			errMsg ="<table border='0' width='400px' height='10px' cellpadding='0' cellspacing='0' bgcolor='#88689f'><tr class='top1'><td>&nbsp;<font color='#FFFFFF'><b>BookBattery</b></td><td align='right'><a href='javascript:closeScrollPopup(greyout(false));'style='font-weight:bold;text-transform:uppercase;color:#FFFFFF;letter-spacing:1pt;word-spacing:2pt;font-size:1em;text-align:left;font-family:georgia, serif;line-height:1;text-decoration:none;'>X</a></td></tr></table><br><table border='0' width='400px' height='10px'><tr class='pages'><td align='center'>Please enter only numerics in the \"Points to Recharge \" Field.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='close_popup_mobile1();' class='button4'><br></td></tr><tr height='15'></tr></table>";
			document.getElementById("scrollpopup").style.display='block';
			document.getElementById("scrollpopup").innerHTML=errMsg
			document.getElementById("emailok").focus();
			greyout(true);
			return;
		}
		if((x.charAt(0)=='0' && x.charAt(1)=='0') || x.charAt(0)=='0' )
		{
			errMsg ="<table border='0' width='400px' height='10px' cellpadding='0' cellspacing='0' bgcolor='#88689f'><tr class='top1'><td>&nbsp;<font color='#FFFFFF'><b>BookBattery</b></td><td align='right'><a href='javascript:closeScrollPopup(greyout(false));'style='font-weight:bold;text-transform:uppercase;color:#FFFFFF;letter-spacing:1pt;word-spacing:2pt;font-size:1em;text-align:left;font-family:georgia, serif;line-height:1;text-decoration:none;'>X</a></td></tr></table><br><table border='0' width='400px' height='10px'><tr class='pages'><td align='center'>Starting with Zero's are not allowed in Points to Recharge field</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='close_popup_mobile1();' class='button4'><br></td></tr><tr height='15'></tr></table>";
			document.getElementById("scrollpopup").style.display='block';
			document.getElementById("scrollpopup").innerHTML=errMsg
			document.getElementById("emailok").focus();
			greyout(true);
			return;
		}
		if (document.userloyalty.reedempoints.value.length > 6)
		{
			errMsg ="<table border='0' width='400px' height='10px' cellpadding='0' cellspacing='0' bgcolor='#88689f'><tr class='top1'><td>&nbsp;<font color='#FFFFFF'><b>BookBattery</b></td><td align='right'><a href='javascript:closeScrollPopup(greyout(false));'style='font-weight:bold;text-transform:uppercase;color:#FFFFFF;letter-spacing:1pt;word-spacing:2pt;font-size:1em;text-align:left;font-family:georgia, serif;line-height:1;text-decoration:none;'>X</a></td></tr></table><br><table border='0' width='400px' height='10px'><tr class='pages'><td align='center'>Only six digits allowed in  \"Points to Recharge \" Field.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='close_popup_mobile1();' class='button4'><br></td></tr><tr height='15'></tr></table>";
			document.getElementById("scrollpopup").style.display='block';
			document.getElementById("scrollpopup").innerHTML=errMsg
			document.getElementById("emailok").focus();
			greyout(true);
			return;
		}
		if (document.userloyalty.reedempoints.value < 10)
		{
			errMsg ="<table border='0' width='400px' height='10px' cellpadding='0' cellspacing='0' bgcolor='#88689f'><tr class='top1'><td>&nbsp;<font color='#FFFFFF'><b>BookBattery</b></td><td align='right'><a href='javascript:closeScrollPopup(greyout(false));'style='font-weight:bold;text-transform:uppercase;color:#FFFFFF;letter-spacing:1pt;word-spacing:2pt;font-size:1em;text-align:left;font-family:georgia, serif;line-height:1;text-decoration:none;'>X</a></td></tr></table><br><table border='0' width='400px' height='10px'><tr class='pages'><td align='center'>Enter atleast \"10 Points\" in Points to Recharge Field.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='close_popup_mobile1();' class='button4'><br></td></tr><tr height='15'></tr></table>";
			document.getElementById("scrollpopup").style.display='block';
			document.getElementById("scrollpopup").innerHTML=errMsg
			document.getElementById("emailok").focus();
			greyout(true);
			return;
		}
	}
	/*if(x%25 == 0)
	{	
		var userreedempoints=x;
	}
	else
	{
		errMsg ="<table border='0' width='400px' height='10px' cellpadding='0' cellspacing='0' bgcolor='#88689f'><tr class='top1'><td>&nbsp;<font color='#FFFFFF'><b>BookBattery</b></td><td align='right'><a href='javascript:closeScrollPopup(greyout(false));'style='font-weight:bold;text-transform:uppercase;color:#FFFFFF;letter-spacing:1pt;word-spacing:2pt;font-size:1em;text-align:left;font-family:georgia, serif;line-height:1;text-decoration:none;'>X</a></td></tr></table><br><table border='0' width='400px' height='10px'><tr class='pages'><td align='center'>Please enter points to recharge  in 'multiples of 25' Only </br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='close_popup_mobile1();' class='button4'><br></td></tr><tr height='15'></tr></table>";
		document.getElementById("scrollpopup").style.display='block';
		document.getElementById("scrollpopup").innerHTML=errMsg
		document.getElementById("emailok").focus();
		greyout(true);
		return;
	}*/
	a=parseInt(merchantpoints);
	var b=parseInt(userreedempoints);
	if (b <= a )
	{
	}
	else
	{
		errMsg ="<table border='0' width='400px' height='10px' cellpadding='0' cellspacing='0' bgcolor='#88689f'><tr class='top1'><td>&nbsp;<font color='#FFFFFF'><b>BookBattery</b></td><td align='right'><a href='javascript:closeScrollPopup(greyout(false));'style='font-weight:bold;text-transform:uppercase;color:#FFFFFF;letter-spacing:1pt;word-spacing:2pt;font-size:1em;text-align:left;font-family:georgia, serif;line-height:1;text-decoration:none;'>X</a></td></tr></table><br><table border='0' width='400px' height='10px'><tr class='pages'><td align='center'>Only "+merchantpoints+" points are available for the selected loyalty program to Cash Back.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='close_popup_points();' class='button4'><br></td></tr><tr height='15'></tr></table>";
		document.getElementById("scrollpopup").style.display='block';
		document.getElementById("scrollpopup").innerHTML=errMsg
		document.getElementById("emailok").focus();
		greyout(true);
		return;
	}
	if (document.userloyalty.serviceprovider.selectedIndex == 0)
	{
		errMsg ="<table border='0' width='400px' height='10px' cellpadding='0' cellspacing='0' bgcolor='#88689f'><tr class='top1'><td>&nbsp;<font color='#FFFFFF'><b>BookBattery</b></td><td align='right'><a href='javascript:closeScrollPopup(greyout(false));'style='font-weight:bold;text-transform:uppercase;color:#FFFFFF;letter-spacing:1pt;word-spacing:2pt;font-size:1em;text-align:left;font-family:georgia, serif;line-height:1;text-decoration:none;'>X</a></td></tr></table><br><table border='0' width='400px' height='10px'><tr class='pages'><td align='center'>Please select Service Provider.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='closePop1();' class='button4'><br></td></tr><tr height='15'></tr></table>";
		document.getElementById("scrollpopup").style.display='block';
		document.getElementById("scrollpopup").innerHTML=errMsg
		document.getElementById("emailok").focus();
		greyout(true);
		return;
	}
	else if (document.userloyalty.state.selectedIndex == 0)
	{
		errMsg ="<table border='0' width='400px' height='10px' cellpadding='0' cellspacing='0' bgcolor='#88689f'><tr class='top1'><td>&nbsp;<font color='#FFFFFF'><b>BookBattery</b></td><td align='right'><a href='javascript:closeScrollPopup(greyout(false));'style='font-weight:bold;text-transform:uppercase;color:#FFFFFF;letter-spacing:1pt;word-spacing:2pt;font-size:1em;text-align:left;font-family:georgia, serif;line-height:1;text-decoration:none;'>X</a></td></tr></table><br><table border='0' width='400px' height='10px'><tr class='pages'><td align='center'>Please select state</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='closePop2();' class='button4'><br></td></tr><tr height='15'></tr></table>";
		document.getElementById("scrollpopup").style.display='block';
		document.getElementById("scrollpopup").innerHTML=errMsg
		document.getElementById("emailok").focus();
		greyout(true);
		return;
	}
	var url="../../servlet/ConsumerLoyalty?hidWhatToDo=updateBrandloyaltypoints&mobileno="+mobileno+"&reedempoints="+userreedempoints+"&loyalpoints="+merchantpoints+"&loyaltyid="+loyaltyid+"&serviceprovider="+serviceprovider+"&state="+state+"&requestno="+(Math.random()*100);
	if (window.XMLHttpRequest)
	{
		xmlhttp=new XMLHttpRequest();
	}
	else
	{
		xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
	}
	xmlhttp.onreadystatechange=function()
	{
		if(xmlhttp.readyState!=4)
		{
			document.getElementById("scrollpopup1").style.display = 'block';
			greyout(true);
			document.getElementById("scrollpopup1").innerHTML="<span class='pages'><center><br><br><b><img src='/bookbattery/images/loader.gif' align='center'><br>Please wait! reedeming your loyalty points.</b></center><br><br></span>";
		}
		if (xmlhttp.readyState==4 && xmlhttp.status==200)
		{ 
			$('#hidebutton').hide();
			document.getElementById("scrollpopup1").style.display='block';
			greyout(true);
			document.getElementById("scrollpopup1").innerHTML=xmlhttp.responseText;
			document.getElementById("emailok").focus();
		}
	}
	xmlhttp.open("GET",url,true);
	xmlhttp.send();
	//onClickMobilediv()
}
/*These functions are used to hide the popups*/
function close_popup_mobile()
{
	$('#scrollpopup').hide();
	document.userloyalty.mobileno.focus();
	greyout(false);
}
function close_popup_mobile3()
{
	$('#scrollpopup').hide();
	$('#scrollpopup1').hide();
	document.userloyalty.mobileno.focus();
	greyout(false);
}
function close_popup_mobile1()
{
	$('#scrollpopup').hide();
	document.userloyalty.reedempoints.focus();
	greyout(false);
}
function close_popup_mobile2()
{
	$('#scrollpopup').hide();
	consumerpointspage()
	greyout(false);
}
function closeScrollPopup()
{
	$('#scrollpopup').hide();
	$('#scrollpopup1').hide();
	greyout(false);
}
function closePop1()
{
	$('#scrollpopup').hide();
	document.userloyalty.serviceprovider.focus();
	greyout(false);
}
function closePop2()
{
	$('#scrollpopup').hide();
	document.userloyalty.state.focus();
	greyout(false);
}
function closePopupc()
{
	$('#scrollpopup').hide();
	document.userloyalty.confirmmobileno.focus();
	greyout(false);
}
function close_merchntreedemclose()
{
	$('#scrollpopup').hide();
	$('#scrollpopup1').hide();
	greyout(false);
	var Email=document.getElementById("strUserid").value;
	//funMerchantLoyaltyPoints(Email)
	
	document.userloyalty.method="post";
	document.userloyalty.action="../../servlet/UserLoginDetails?hidWhatToDo=merchantLoyalty&emailid="+Email;
	//alert(document.userloyalty.action+"document.userloyalty.action");
	document.userloyalty.submit();
}
function submitaddress(flag)
{	
	var flag=flag;
	var varEmail = document.getElementById("strUserid").value;
	var useraddress=document.userloyalty.useraddress.value;
	var mobileno=document.userloyalty.cashmobileno.value;
	var userreedempoints=document.userloyalty.cashreedempoints.value;
	var x=userreedempoints;
	var x1=mobileno;
	var loyaltyid,merchantpoints,a,b;
	if (flag=="undefined")
	{
		var strloyalitypointscount =document.userloyalty.strloyalitypointscount.value;
		if((strloyalitypointscount=="0")||(strloyalitypointscount=="null"))
		{
				
			errMsg ="<table border='0' width='400px' height='10px' cellpadding='0' cellspacing='0' bgcolor='#88689f'><tr class='top1'><td>&nbsp;<font color='#FFFFFF'><b>BookBattery</b></td><td align='right'><a href='javascript:closeScrollPopup(greyout(false));' style='font-weight:bold;text-transform:uppercase;color:#FFFFFF;letter-spacing:1pt;word-spacing:2pt;font-size:1em;text-align:left;font-family:georgia, serif;line-height:1;text-decoration:none;'>X</a></td></tr></table><br><table border='0' width='400px' height='10px'><tr class='pages'><td align='center'>Earn loyalty points to Recharge  your mobile number.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='close_reedemclose();' class='button4'><br></td></tr><tr height='15'></tr></table>";
			document.getElementById("scrollpopup").style.display='block';
			document.getElementById("scrollpopup").innerHTML=errMsg
			document.getElementById("emailok").focus();
			greyout(true);
			return;
		}
	}
	if (flag=="merchant")
	{
		var Selectchkbox=0;
		for (i = 0; i < document.userloyalty.programname.length; i++)
		{
			if(document.userloyalty.programname[i].checked)
			{
				loyaltyid=document.userloyalty.programname[i].value;
				merchantpoints=document.userloyalty.programvalue[i].value;
				Selectchkbox++;
			}
		}
		if(Selectchkbox==0)
		{
			errMsg ="<table border='0' width='400px' height='10px' cellpadding='0' cellspacing='0' bgcolor='#88689f'><tr class='top1'><td>&nbsp;<font color='#FFFFFF'><b>BookBattery</b></td><td align='right'><a href='javascript:closeScrollPopup(greyout(false));'style='font-weight:bold;text-transform:uppercase;color:#FFFFFF;letter-spacing:1pt;word-spacing:2pt;font-size:1em;text-align:left;font-family:georgia, serif;line-height:1;text-decoration:none;'>X</a></td></tr></table><br><table border='0' width='400px' height='10px'><tr class='pages'><td align='center'>Please select atleast one loyalty program to cash back loyalty points</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='closeScrollPopup(greyout(false));' class='button4'><br></td></tr><tr height='15'></tr></table>";
			document.getElementById("scrollpopup").style.display='block';
			document.getElementById("scrollpopup").innerHTML=errMsg
			document.getElementById("emailok").focus();
			greyout(true);
			return;
		}
		if(Selectchkbox>= 2)
		{
			errMsg ="<table border='0' width='400px' height='10px' cellpadding='0' cellspacing='0' bgcolor='#88689f'><tr class='top1'><td>&nbsp;<font color='#FFFFFF'><b>BookBattery</b></td><td align='right'><a href='javascript:closeScrollPopup(greyout(false));'style='font-weight:bold;text-transform:uppercase;color:#FFFFFF;letter-spacing:1pt;word-spacing:2pt;font-size:1em;text-align:left;font-family:georgia, serif;line-height:1;text-decoration:none;'>X</a></td></tr></table><br><table border='0' width='400px' height='10px'><tr class='pages'><td align='center'>Please select only one program to cash back loyalty points.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='closeScrollPopup(greyout(false));' class='button4'><br></td></tr><tr height='15'></tr></table>";
			document.getElementById("scrollpopup").style.display='block';
			document.getElementById("scrollpopup").innerHTML=errMsg
			document.getElementById("emailok").focus();
			greyout(true);
			return;
		}
		a=parseInt(userreedempoints);
		b=parseInt(merchantpoints);
		if (b < 500)
		{	
			errMsg ="<table border='0' width='400px' height='10px' cellpadding='0' cellspacing='0' bgcolor='#88689f'><tr class='top1'><td>&nbsp;<font color='#FFFFFF'><b>BookBattery</b></td><td align='right'><a href='javascript:closeScrollPopup(greyout(false));'style='font-weight:bold;text-transform:uppercase;color:#FFFFFF;letter-spacing:1pt;word-spacing:2pt;font-size:1em;text-align:left;font-family:georgia, serif;line-height:1;text-decoration:none;'>X</a></td></tr></table><br><table border='0' width='400px' height='10px'><tr class='pages'><td align='center'>You don't have sufficient points for the selected loyalty program to Cash Back.Still if you want to continue click on OK button for Mobile Recharge</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='showMobilediv();' class='button4'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type='button' name='cancel' id='cancel' value='Cancel' onclick='closeScrollPopup(greyout(false));' class='button4'><br></td></tr><tr height='15'></tr></table>";
			document.getElementById("scrollpopup").style.display='block';
			document.getElementById("scrollpopup").innerHTML=errMsg
			document.getElementById("emailok").focus();
			greyout(true);
			return;
		}
		if (a < 500)
		{
			errMsg ="<table border='0' width='400px' height='10px' cellpadding='0' cellspacing='0' bgcolor='#88689f'><tr class='top1'><td>&nbsp;<font color='#FFFFFF'><b>BookBattery</b></td><td align='right'><a href='javascript:closeScrollPopup(greyout(false));'style='font-weight:bold;text-transform:uppercase;color:#FFFFFF;letter-spacing:1pt;word-spacing:2pt;font-size:1em;text-align:left;font-family:georgia, serif;line-height:1;text-decoration:none;'>X</a></td></tr></table><br><table border='0' width='400px' height='10px'><tr class='pages'><td align='center'>Enter Minimum of 500 points to Cash Back loyalty points</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='close_popup_points();' class='button4'><br></td></tr><tr height='15'></tr></table>";
			document.getElementById("scrollpopup").style.display='block';
			document.getElementById("scrollpopup").innerHTML=errMsg
			document.getElementById("emailok").focus();
			greyout(true);
			return;

		}
	}
	if(mobileno == "")
	{
		errMsg ="<table border='0' width='400px' height='10px' cellpadding='0' cellspacing='0' bgcolor='#88689f'><tr class='top1'><td>&nbsp;<font color='#FFFFFF'><b>BookBattery</b></td><td align='right'><a href='javascript:closeScrollPopup(greyout(false));'style='font-weight:bold;text-transform:uppercase;color:#FFFFFF;letter-spacing:1pt;word-spacing:2pt;font-size:1em;text-align:left;font-family:georgia, serif;line-height:1;text-decoration:none;'>X</a></td></tr></table><br><table border='0' width='400px' height='10px'><tr class='pages'><td align='center'>Please enter Mobile Number like '9876543210'.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='close_popup_cashmobile();' class='button4'><br></td></tr><tr height='15'></tr></table>";
		document.getElementById("scrollpopup").style.display='block';
		document.getElementById("scrollpopup").innerHTML=errMsg
		document.getElementById("emailok").focus();
		greyout(true);
		return;
	}
	if (mobileno != "")
	{
		var checkOK = "0123456789";
		var checkStr = mobileno;
		var allValid = true;
		var decPoints = 0;
		var allNum = "";
		for (i = 0; i < checkStr.length; i++)
		{
			ch = checkStr.charAt(i);

				for (j = 0; j < checkOK.length; j++)
				if (ch == checkOK.charAt(j))
					break;
				if (j == checkOK.length)
				{
					allValid = false;
					break;
				}
		}
		if (!allValid)
		{
			errMsg ="<table border='0' width='400px' height='10px' cellpadding='0' cellspacing='0' bgcolor='#88689f'><tr class='top1'><td>&nbsp;<font color='#FFFFFF'>BookBattery</td><td align='right'><a href='javascript:closeScrollPopup(greyout(false));'style='font-weight:bold;text-transform:uppercase;color:#FFFFFF;letter-spacing:1pt;word-spacing:2pt;font-size:1em;text-align:left;font-family:georgia, serif;line-height:1;text-decoration:none;'>X</a></td></tr></table><br><table border='0' width='400px' height='10px'><tr class='pages'><td align='center'>Please enter numerics in the \" Mobile Number\" field.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='close_popup_cashmobile();' class='button4'><br></td></tr><tr height='15'></tr></table>";
			document.getElementById("scrollpopup").style.display='block';
			document.getElementById("scrollpopup").innerHTML=errMsg
			document.getElementById("emailok").focus();
			greyout(true);
			return;
		}
		if (document.userloyalty.cashmobileno.value.length < 10)
		{
			errMsg ="<table border='0' width='400px' height='10px' cellpadding='0' cellspacing='0' bgcolor='#88689f'><tr class='top1'><td>&nbsp;<font color='#FFFFFF'><b>BookBattery</b></td><td align='right'><a href='javascript:closeScrollPopup(greyout(false));'style='font-weight:bold;text-transform:uppercase;color:#FFFFFF;letter-spacing:1pt;word-spacing:2pt;font-size:1em;text-align:left;font-family:georgia, serif;line-height:1;text-decoration:none;'>X</a></td></tr></table><br><table border='0' width='400px' height='10px'><tr class='pages'><td align='center'>Please enter mobile number like '9876543210'.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='close_popup_cashmobile();' class='button4'><br></td></tr><tr height='15'></tr></table>";
			document.getElementById("scrollpopup").style.display='block';
			document.getElementById("scrollpopup").innerHTML=errMsg
			document.getElementById("emailok").focus();
			greyout(true);
			return;
		}
		if (document.userloyalty.cashmobileno.value.length==10)
		{
			if (mobileno < 7000000000 )
			{
				errMsg ="<table border='0' width='400px' height='10px' cellpadding='0' cellspacing='0' bgcolor='#88689f'><tr class='top1'><td>&nbsp;<font color='#FFFFFF'><b>BookBattery</b></td><td align='right'><a href='javascript:closeScrollPopup(greyout(false));'style='font-weight:bold;text-transform:uppercase;color:#FFFFFF;letter-spacing:1pt;word-spacing:2pt;font-size:1em;text-align:left;font-family:georgia, serif;line-height:1;text-decoration:none;'>X</a></td></tr></table><br><table border='0' width='400px' height='10px'><tr class='pages'><td align='center'>Mobile Number Should start with 7 or 8 or 9...!</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='close_popup_cashmobile();' class='button4'><br></td></tr><tr height='15'></tr></table>";
				document.getElementById("scrollpopup").style.display='block';
				document.getElementById("scrollpopup").innerHTML=errMsg
				document.getElementById("emailok").focus();
				greyout(true);
				return;
			}
		}
	}
	if(userreedempoints == "")
	{
		errMsg ="<table border='0' width='400px' height='10px' cellpadding='0' cellspacing='0' bgcolor='#88689f'><tr class='top1'><td>&nbsp;<font color='#FFFFFF'><b>BookBattery</b></td><td align='right'><a href='javascript:closeScrollPopup(greyout(false));'style='font-weight:bold;text-transform:uppercase;color:#FFFFFF;letter-spacing:1pt;word-spacing:2pt;font-size:1em;text-align:left;font-family:georgia, serif;line-height:1;text-decoration:none;'>X</a></td></tr></table><br><table border='0' width='400px' height='10px'><tr class='pages'><td align='center'>Please enter points to Cash Back</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='close_popup_points();' class='button4'><br></td></tr><tr height='15'></tr></table>";
		document.getElementById("scrollpopup").style.display='block';
		document.getElementById("scrollpopup").innerHTML=errMsg
		document.getElementById("emailok").focus();
		greyout(true);
		return;
	}
	if (userreedempoints != "")
	{
		var checkOK = "0123456789";
		var checkStr = userreedempoints;
		var allValid = true;
		var decPoints = 0;
		var allNum = "";
		for (i = 0; i < checkStr.length; i++)
		{
			ch = checkStr.charAt(i);
			for (j = 0; j < checkOK.length; j++)
				if (ch == checkOK.charAt(j))
					break;
			if (j == checkOK.length)
			{
				allValid = false;
				break;
			}
		}
		if (!allValid)
		{
			errMsg ="<table border='0' width='400px' height='10px' cellpadding='0' cellspacing='0' bgcolor='#88689f'><tr class='top1'><td>&nbsp;<font color='#FFFFFF'><b>BookBattery</b></td><td align='right'><a href='javascript:closeScrollPopup(greyout(false));'style='font-weight:bold;text-transform:uppercase;color:#FFFFFF;letter-spacing:1pt;word-spacing:2pt;font-size:1em;text-align:left;font-family:georgia, serif;line-height:1;text-decoration:none;'>X</a></td></tr></table><br><table border='0' width='400px' height='10px'><tr class='pages'><td align='center'>Please enter only numerics in the \"Points to Cash Back \" Field.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='close_popup_points();' class='button4'><br></td></tr><tr height='15'></tr></table>";
			document.getElementById("scrollpopup").style.display='block';
			document.getElementById("scrollpopup").innerHTML=errMsg
			document.getElementById("emailok").focus();
			greyout(true);
			return;
		}
		if((x.charAt(0)=='0' && x.charAt(1)=='0') || x.charAt(0)=='0' )
		{
			errMsg ="<table border='0' width='400px' height='10px' cellpadding='0' cellspacing='0' bgcolor='#88689f'><tr class='top1'><td>&nbsp;<font color='#FFFFFF'><b>BookBattery</b></td><td align='right'><a href='javascript:closeScrollPopup(greyout(false));'style='font-weight:bold;text-transform:uppercase;color:#FFFFFF;letter-spacing:1pt;word-spacing:2pt;font-size:1em;text-align:left;font-family:georgia, serif;line-height:1;text-decoration:none;'>X</a></td></tr></table><br><table border='0' width='400px' height='10px'><tr class='pages'><td align='center'>Starting with Zero's are not allowed in Points to Cash Back field</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='close_popup_points();' class='button4'><br></td></tr><tr height='15'></tr></table>";
			document.getElementById("scrollpopup").style.display='block';
			document.getElementById("scrollpopup").innerHTML=errMsg
			document.getElementById("emailok").focus();
			greyout(true);
			return;
		}
		if (document.userloyalty.cashreedempoints.value.length > 6)
		{
			errMsg ="<table border='0' width='400px' height='10px' cellpadding='0' cellspacing='0' bgcolor='#88689f'><tr class='top1'><td>&nbsp;<font color='#FFFFFF'><b>BookBattery</b></td><td align='right'><a href='javascript:closeScrollPopup(greyout(false));'style='font-weight:bold;text-transform:uppercase;color:#FFFFFF;letter-spacing:1pt;word-spacing:2pt;font-size:1em;text-align:left;font-family:georgia, serif;line-height:1;text-decoration:none;'>X</a></td></tr></table><br><table border='0' width='400px' height='10px'><tr class='pages'><td align='center'>Only six digits allowed in  \"Points to Cash Back \" Field.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='close_popup_points();' class='button4'><br></td></tr><tr height='15'></tr></table>";
			document.getElementById("scrollpopup").style.display='block';
			document.getElementById("scrollpopup").innerHTML=errMsg
			document.getElementById("emailok").focus();
			greyout(true);
			return;
		}
		if (document.userloyalty.cashreedempoints.value < 25)
		{
			errMsg ="<table border='0' width='400px' height='10px' cellpadding='0' cellspacing='0' bgcolor='#88689f'><tr class='top1'><td>&nbsp;<font color='#FFFFFF'><b>BookBattery</b></td><td align='right'><a href='javascript:closeScrollPopup(greyout(false));'style='font-weight:bold;text-transform:uppercase;color:#FFFFFF;letter-spacing:1pt;word-spacing:2pt;font-size:1em;text-align:left;font-family:georgia, serif;line-height:1;text-decoration:none;'>X</a></td></tr></table><br><table border='0' width='400px' height='10px'><tr class='pages'><td align='center'>Enter atleast \"25 Points\" in Points to Cash Back Field.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='close_popup_points();' class='button4'><br></td></tr><tr height='15'></tr></table>";
			document.getElementById("scrollpopup").style.display='block';
			document.getElementById("scrollpopup").innerHTML=errMsg
			document.getElementById("emailok").focus();
			greyout(true);
			return;
		}
	}
	if (flag=="merchant")
	{
		a=parseInt(merchantpoints);
		b=parseInt(userreedempoints);
		if (b <= a )
		{
		}
		else
		{
			errMsg ="<table border='0' width='400px' height='10px' cellpadding='0' cellspacing='0' bgcolor='#88689f'><tr class='top1'><td>&nbsp;<font color='#FFFFFF'><b>BookBattery</b></td><td align='right'><a href='javascript:closeScrollPopup(greyout(false));'style='font-weight:bold;text-transform:uppercase;color:#FFFFFF;letter-spacing:1pt;word-spacing:2pt;font-size:1em;text-align:left;font-family:georgia, serif;line-height:1;text-decoration:none;'>X</a></td></tr></table><br><table border='0' width='400px' height='10px'><tr class='pages'><td align='center'>Only "+merchantpoints+" points are available for the selected loyalty program to Cash Back.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='close_popup_points();' class='button4'><br></td></tr><tr height='15'></tr></table>";
			document.getElementById("scrollpopup").style.display='block';
			document.getElementById("scrollpopup").innerHTML=errMsg
			document.getElementById("emailok").focus();
			greyout(true);
			return;
		}
	}
	/*if(x%25 == 0)
	{	
		var userreedempoints=x;
	}
	else
	{
		errMsg ="<table border='0' width='400px' height='10px' cellpadding='0' cellspacing='0' bgcolor='#88689f'><tr class='top1'><td>&nbsp;<font color='#FFFFFF'><b>BookBattery</b></td><td align='right'><a href='javascript:closeScrollPopup(greyout(false));'style='font-weight:bold;text-transform:uppercase;color:#FFFFFF;letter-spacing:1pt;word-spacing:2pt;font-size:1em;text-align:left;font-family:georgia, serif;line-height:1;text-decoration:none;'>X</a></td></tr></table><br><table border='0' width='400px' height='10px'><tr class='pages'><td align='center'>Please enter Points to Cash Back in 'multiples of 25' Only </br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='close_popup_points();' class='button4'><br></td></tr><tr height='15'></tr></table>";
		document.getElementById("scrollpopup").style.display='block';
		document.getElementById("scrollpopup").innerHTML=errMsg
		document.getElementById("emailok").focus();
		greyout(true);
		return;
	}*/
	
	if(useraddress == "")
	{
		errMsg ="<table border='0' width='400px' height='10px' cellpadding='0' cellspacing='0' bgcolor='#88689f'><tr class='top1'><td>&nbsp;<font color='#FFFFFF'><b>BookBattery</b></td><td align='right'><a href='javascript:closeScrollPopup(greyout(false));'style='font-weight:bold;text-transform:uppercase;color:#FFFFFF;letter-spacing:1pt;word-spacing:2pt;font-size:1em;text-align:left;font-family:georgia, serif;line-height:1;text-decoration:none;'>X</a></td></tr></table><br><table border='0' width='400px' height='10px'><tr class='pages'><td align='center'>Please enter Your Address.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='close_popup_address();' class='button4'><br></td></tr><tr height='15'></tr></table>";
		document.getElementById("scrollpopup").style.display='block';
		document.getElementById("scrollpopup").innerHTML=errMsg
		document.getElementById("emailok").focus();
		greyout(true);
		return;

	}
	if (document.getElementById("useraddress").value.indexOf(' ') == 0 ) 
	{
		errMsg ="<table border='0' width='400px' height='10px' cellpadding='0' cellspacing='0' bgcolor='#88689f'><tr class='top1'><td>&nbsp;<font color='#FFFFFF'><b>BookBattery</b></td><td align='right'><a href='javascript:closeScrollPopup(greyout(false));'style='font-weight:bold;text-transform:uppercase;color:#FFFFFF;letter-spacing:1pt;word-spacing:2pt;font-size:1em;text-align:left;font-family:georgia, serif;line-height:1;text-decoration:none;'>X</a></td></tr></table><br><table border='0' width='400px' height='10px'><tr class='pages'><td align='center'>Only spaces and space before text is not allowed in Address Field</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='close_popup_address();' class='button4'><br></td></tr><tr height='15'></tr></table>";
		document.getElementById("scrollpopup").style.display='block';
		document.getElementById("scrollpopup").innerHTML=errMsg
		document.getElementById("emailok").focus();
		greyout(true);
		return;
	}
	if (/[a-z][A-Z]{2}/i.test(document.getElementById("useraddress").value) != true) 
	{

		errMsg ="<table border='0' width='400px' height='10px' cellpadding='0' cellspacing='0' bgcolor='#88689f'><tr class='top1'><td>&nbsp;<font color='#FFFFFF'><b>BookBattery</b></td><td align='right'><a href='javascript:closeScrollPopup(greyout(false));'style='font-weight:bold;text-transform:uppercase;color:#FFFFFF;letter-spacing:1pt;word-spacing:2pt;font-size:1em;text-align:left;font-family:georgia, serif;line-height:1;text-decoration:none;'>X</a></td></tr></table><br><table border='0' width='400px' height='10px'><tr class='pages'><td align='center'>Please enter valid Address</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='close_popup_address();' class='button4'><br></td></tr><tr height='15'></tr></table>";
		document.getElementById("scrollpopup").style.display='block';
		document.getElementById("scrollpopup").innerHTML=errMsg
		document.getElementById("emailok").focus();
		greyout(true);
		return;
	}
	if (flag=="merchant")
	{
		var url="../../servlet/ConsumerLoyalty?hidWhatToDo=updateBrandloyaltypoints&useraddress="+useraddress+"&mobileno="+mobileno+"&reedempoints="+userreedempoints+"&loyalpoints="+merchantpoints+"&loyaltyid="+loyaltyid+"&flag=profile&requestno="+(Math.random()*100);
	}
	else
	{
		var url="../../servlet/ConsumerLoyalityPoints?hidWhatToDo=redeemPoints&useraddress="+useraddress+"&mobileno="+mobileno+"&reedempoints="+userreedempoints+"&flag=profile&requestno="+(Math.random()*100);
	}
	if (window.XMLHttpRequest)
	{
		xmlhttp=new XMLHttpRequest();
	}
	else
	{
		xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
	}
	xmlhttp.onreadystatechange=function()
	{
		if(xmlhttp.readyState!=4)
		{
			document.getElementById("scrollpopup1").style.display = 'block';
			greyout(true);
			document.getElementById("scrollpopup1").innerHTML="<span class='pages'><center><br><br><b><img src='/bookbattery/images/loader.gif' align='center'><br>Please wait! Updating Your Address.</b></center><br><br></span>";
		}
		if (xmlhttp.readyState==4 && xmlhttp.status==200)
		{ 
			$('#hidebutton').hide();
			document.getElementById("scrollpopup1").style.display='block';
			greyout(true);
			document.getElementById("scrollpopup1").innerHTML=xmlhttp.responseText;
			document.getElementById("emailok").focus();
		}
	}
	xmlhttp.open("GET",url,true);
	xmlhttp.send();
	if (flag="merchant"){onClickCashBackDiv()}
	else{onClickMobilediv()}
}
function onClickCashBackDiv()
{
	$('#hidebutton').show();
	$('#otherRet1').hide();
	$('#otherRet').hide();
	var paramName = "cashbackdiv";
	document.userloyalty.cashmobileno.value="";
	document.userloyalty.cashreedempoints.value="";
	document.userloyalty.useraddress.value="";
	if($("#"+paramName).is(':visible'))
	{
		$("#"+paramName).hide();
	}
	else
	{
		$("#"+paramName).show();
	}
}
function close_popup_cashmobile()
{
	$('#scrollpopup').hide();
	$('#scrollpopup1').hide();
	document.userloyalty.cashmobileno.focus();
	greyout(false);
}
function close_popup_address()
{
	$('#scrollpopup').hide();
	$('#scrollpopup1').hide();
	document.userloyalty.useraddress.focus();
	greyout(false);
}
function close_popup_points()
{
	$('#scrollpopup').hide();
	$('#scrollpopup1').hide();
	document.userloyalty.cashreedempoints.focus();
	greyout(false);
}
function showMobilediv()
{
	$('#hidebutton').show();
	$('#otherRet1').hide();
	$('#cashbackdiv').hide();
	$('#scrollpopup1').hide();
	$('#scrollpopup').hide();
	greyout(false);
	var paramName = "otherRet";
	document.userloyalty.mobileno.value="";
	document.userloyalty.confirmmobileno.value="";
	document.userloyalty.reedempoints.value="";
	document.userloyalty.serviceprovider.value="0";
	document.userloyalty.state.value="0";
	if($("#"+paramName).is(':visible'))
	{
		$("#"+paramName).hide();
	}
	else
	{
		$("#"+paramName).show();
	}
	document.userloyalty.mobileno.focus();
}
</script>
</body>
</html>
