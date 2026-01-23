<%--
    Document   : orderServicedetails.jsp
    Created on : Feb 9th, 2016
    Author     : Lavanya Chowdary.
--%>
<%@ page language="java" import="java.sql.*"%>
<%@page language="java" import="java.io.File,java.text.*,java.util.Calendar,java.util.ArrayList,java.util.Hashtable,java.util.Vector,com.ngit.javabean.loglevel.*,java.util.Date,java.util.Properties,java.util.Arrays,com.ngit.javabean.loglevel.LogLevel,java.util.Properties,java.io.FileInputStream"%>
<%
String strUserid=(String)session.getAttribute("sesBatteryOperatorName");
if(strUserid==null)
{
	strUserid="";
	session.setAttribute("sesErrorMsg","Session Timed Out. Please login again");
	response.sendRedirect("../../../operator/index.html");
	return;
}

Vector alist=(Vector)session.getAttribute("ServicesdetailsVector");

String servicestype = (request.getParameter("services_type")!=null)?(request.getParameter("services_type")):"0";
LogLevel.DEBUG(5,new Throwable(),"servicestype :"+servicestype ); 

String vehiclemake = (request.getParameter("vehiclemake")!=null)?(request.getParameter("vehiclemake")):"0";
LogLevel.DEBUG(5,new Throwable(),"vehiclemake :"+vehiclemake );

String vehiclemodel = (request.getParameter("vehiclemodel")!=null)?(request.getParameter("vehiclemodel")):"0";
LogLevel.DEBUG(5,new Throwable(),"vehiclemodel :"+vehiclemodel );

String servicesplace = (request.getParameter("servicesplace")!=null)?(request.getParameter("servicesplace")):"0";
LogLevel.DEBUG(5,new Throwable(),"servicesplace :"+servicesplace );

String city = (request.getParameter("city")!=null)?(request.getParameter("city")):"0";
LogLevel.DEBUG(5,new Throwable(),"city :"+city );

String pincity = (request.getParameter("pincity")!=null)?(request.getParameter("pincity")):"0";
LogLevel.DEBUG(5,new Throwable(),"pincity :"+pincity );

String strarea = (request.getParameter("strarea")!=null)?(request.getParameter("strarea")):"0"; 
LogLevel.DEBUG(5,new Throwable(),"strarea :"+strarea );

String strstate = (request.getParameter("strstate")!=null)?(request.getParameter("strstate")):"0"; 
LogLevel.DEBUG(5,new Throwable(),"strstate :"+strstate );

String bat_type = (request.getParameter("bat_type")!=null)?(request.getParameter("bat_type")):"0";
LogLevel.DEBUG(5,new Throwable(),"bat_type :"+bat_type );

String inv_capacity = (request.getParameter("inv_capacity")!=null)?(request.getParameter("inv_capacity")):"0"; 
LogLevel.DEBUG(5,new Throwable(),"inv_capacity :"+inv_capacity);

String strpincode = (request.getParameter("strpincode")!=null)?(request.getParameter("strpincode")):"0"; 
LogLevel.DEBUG(5,new Throwable(),"strpincode :"+strpincode );

String visid = (request.getParameter("visid")!=null)?(request.getParameter("visid")):"0"; 
LogLevel.DEBUG(5,new Throwable(),"visid :"+visid );

Properties propsMOPConfig = new Properties();
ServletContext context = getServletContext();
FileInputStream fin1      = new FileInputStream(new File(context.getRealPath("properties/bookbatteryconfig.properties"))); 
propsMOPConfig.load(fin1); 
fin1.close(); 
String publicUrl = (propsMOPConfig.getProperty("publicUrl")!=null)?propsMOPConfig.getProperty("publicUrl"):"";
String baseURL = (propsMOPConfig.getProperty("baseURL")!=null)?propsMOPConfig.getProperty("baseURL"):"";
String baseurldirectory = (propsMOPConfig.getProperty("baseurldirectory")!=null)?propsMOPConfig.getProperty("baseurldirectory"):"";
String serverURL = (propsMOPConfig.getProperty("serverURL")!=null)?propsMOPConfig.getProperty("serverURL"):"";


%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<link rel="shortcut icon" href="../../../images/favicon.png" type="image/x-icon">
<title>BookBattery.com-India's No.1 Automobile Services Store</title>
<script language="JavaScript" src="../../../js/jquery-1.8.2.js" ></script>
<script src="../../../js/jquery-1.3.2.min.js"></script>
<script language="JavaScript" src="../../../js/pophide.js" ></script>
<style type="text/css">
</style>
<link href="../../../css/sgservices.css" rel="stylesheet" type="text/css" />
</head>
<body onload="" >
<!-- Start Alexa Certify Javascript -->
<script type="text/javascript">
_atrk_opts = { atrk_acct:"VrG0j1a4ZP00yt", domain:"BookBattery.com",dynamic: true};
(function() { var as = document.createElement('script'); as.type = 'text/javascript'; as.async = true; as.src = "https://d31qbv1cthcecs.cloudfront.net/atrk.js"; var s = document.getElementsByTagName('script')[0];s.parentNode.insertBefore(as, s); })();
</script>
<noscript><img src="https://d5nxst8fruw4z.cloudfront.net/atrk.gif?account=VrG0j1a4ZP00yt" style="display:none" height="1" width="1" alt="" /></noscript>
<!-- End Alexa Certify Javascript -->
<form name="servicedet" method="post" ENCTYPE="multipart/form-data">
<!-- Services Header Starts -->
<tr>
	<jsp:include page = "../header.jsp" />
</tr>
<!-- Services Header Ends -->
<tr>
	<td>
		<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" bgcolor="#ffffff">
		<tr>
			<td width="25%" align="left" valign="top" bgcolor="#ffffff">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<!-- Starts services admin left Menu -->
				<tr>
					<jsp:include page="../operatorleftmenu.jsp"/>
				</tr>
				<!-- Ends services admin left Menu -->
				</table>
			</td>
			<td width="75%" align="left" valign="top">
				<table width="100%" border="1" cellspacing="0" cellpadding="0">
				<tr>
					<td align="left" valign="top">
					<!--Popup messages div starts -->
						<div id='divmobile' class='divmobile' style="display:none;"></div>
						<div id='popup' class='popup' style="display:none;">
						<div id='popuptitle' class='popuptitle'><table border='0' width='410px' height='10px' cellpadding='0' cellspacing='0'>
						<tr class='top1'><td>&nbsp;<font color='#FFFFFF'>BookBattery</td><td align='right'>
						<a href='javascript:closePopup(greyout(false));' class="bluelinks333">x</a></td></tr></table>
						</div>
						<div id='popupmessage' class='popupmessage'></div> 
						</div>
						<!--Popup messages div Ends -->
					<!-- your page content starts here  -->
					<table width="100%" cellspacing="1" bgcolor="#FFFFFF" cellpadding="0" border="0">
							<tr><td height="20"></td></tr>
						<%
						if(alist!=null && alist.size() > 0)
						{
						%>
						<table width="100%" border="0" >
							<tr>
								
								<td class="subheadingbat3" align="left" size="4"><%=servicestype%> >> <%=vehiclemake%>  >> <%=vehiclemodel%> >> <%=servicesplace.replace("_"," ")%></td>
								
							</tr>
							<td  align="right"><a href="../../../jsp/operator/orderService/orderService.jsp" class="onclick1">Back&nbsp;&nbsp;</a></td>
							<tr><td height="10"></td></tr>
						</table>
						<table width="100%" border="0" >
							<tr>
									<td colspan="6" align="right">
									<table  width="100%" cellspacing="1" align="center" cellpadding="2"  border="1" bgcolor="#FFFFFF">
										<tr  bgcolor="#FFFFFF">
											<td align="center" width="20%" class="prodheadingservices">Service</td>
											<td align="center" width="25%" class="prodheadingservices">Price in INR</td>
											<td align="center" width="25%" class="prodheadingservices">Description</td>
											<!--<td align="center" width="10%" class="prodheadingservices">Size</td>-->
										</tr>
											<%
											try
											{
												for ( int i=0; i<alist.size() ; i++)
												{

													Hashtable ht=(Hashtable)alist.get(i);
													String appmapid=String.valueOf(ht.get("map_id"));
													String strservicestype=String.valueOf(ht.get("service_type"));
													String strbat_type=String.valueOf(ht.get("battery_type"));
													String strservicesplace=servicesplace;
													String stractprice="";
													String strdiscountprice="";
													if(servicesplace.equals("store"))
													{
														stractprice="0";
														strdiscountprice=String.valueOf(ht.get("store"));
													}
                                                    else if(servicesplace.equals("within_5km"))
													{
														stractprice="0";
														strdiscountprice=String.valueOf(ht.get("within_5km"));
													} 
                                                    else if(servicesplace.equals("within_10km"))
													{
														stractprice="0";
														strdiscountprice=String.valueOf(ht.get("within_10km"));
													}
													else
													{
														stractprice="0";
														strdiscountprice=String.valueOf(ht.get("store"));
													}

													String tdclass="tableservices1";
													String mon="";
													String dat="";
													int strdisprice = Integer.parseInt(strdiscountprice);

											%>
											<tr >
											<td  class="<%=tdclass%>" bgcolor="#FFFFFF	" align="center"><br><img align="center" src="<%=publicUrl%>/bookbattery/dev/includes/images-design/icons/Car_Battery_Health_Up.png" ALT="services" width="100" height="100"/><br><br><b><%=servicesplace.replace("_"," ")%></b><br><b><%=strservicestype%></b></td>
											
											<td  class="<%=tdclass%>" valign="top" align="center">
											<table width="85%" border="0" class="<%=tdclass%>" align="center">
											<tr><td height="6"></td><td height="5"></td></tr>
											<tr>
												<td class="<%=tdclass%>" width="60%" align="left"><b>Battery&nbsp;Type</b></td><td><b>:</b></td>
												<td class="<%=tdclass%>" width="40%" align="left"><b><%=strbat_type%></b></td></tr>
                                                <tr>
												<td class="<%=tdclass%>" width="60%" align="left"><b>Service&nbsp;Price</b></td><td><b>:</b></td>
												<td class="<%=tdclass%>" width="40%" align="left"><b><%=strdiscountprice%></b></td></tr>
											<tr><td height="6"></td><td height="5"></td></tr>
											
												</table>
											<table width="100%" border="0" class="<%=tdclass%>">
											
												<td width="100%" align="center" style='font-family:Verdana;font-size:9px;color:#cccccc;	text-decoration:none;padding:1px 1px;'>(Inclusive of all taxes)</td><tr>
											<tr><td height="5"></td></tr>
											<tr>
												<td width="100%" align="center" style='font-family:Verdana;font-size:9px;color:#cccccc;	text-decoration:none;padding:1px 1px;'><input type="button" value="Order&nbsp;it&nbsp;Now" class="buttonindex" onclick="javascript:askcosumerdetails('<%=strservicesplace%>','<%=strservicestype%>','<%=stractprice%>','<%=strdiscountprice%>');"><br><br></td></tr>
												
											</table>
											</td>
											<td  class="<%=tdclass%>" valign="center" align="center"><b>Professional and Experienced Technician will come to your place , Check the Voltage and AMPS at different stages, Check the water level/acid levels of the battery(top up if required), Clean the Battery Terminals and Provide a Report Card. If Battery Recharge is required, additional Rs 300 will be charged for getting the Battery Recharged. The battery will be taken for recharge at our service center and will be provided back within 24 - 48 hrs. If the battery needs to be replaced, it will be replaced at additional cost.</b></td>
											</tr>
												<%
												}
												}
												catch(Exception e)
												{
														e.printStackTrace();
												}
												%>
											</table>
										
										</td>
									</tr>
								
				</table>
				<%
				}
				else
				{
				%>
				<tr>
				<td  align="center" class="insidecontent">No Services details found based on selection.!</td>
				</tr>
				<%
				}
				%>
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
<input type="hidden" name="servicestype" value="<%=servicestype%>">
<input type="hidden" name="vehiclemake" value="<%=vehiclemake%>">
<input type="hidden" name="vehiclemodel" value="<%=vehiclemodel%>">
<input type="hidden" name="servicesplace" value="<%=servicesplace%>">
<input type="hidden" name="bat_type" value="<%=bat_type%>">
<input type="hidden" name="inv_capacity" value="<%=inv_capacity%>">
<input type="hidden" name="city" value="<%=city%>">
<input type="hidden" name="pincity" id='pincity' value="<%=pincity%>">
<input type="hidden" name="strarea" value="<%=strarea%>">
<input type="hidden" name="strstate" value="<%=strstate%>">
<input type="hidden" name="strpincode" value="<%=strpincode%>">
<input type="hidden" name="visid" value="<%=visid%>">
<input type="hidden" name="strEmail" value="">
<input type="hidden" name="serverURL" id='serverURL' value="<%=serverURL%>">

</form>
</center>
<%
String strservicesLogMsg=(String)session.getAttribute("sesservicesdetailsErrorMsg");
if(strservicesLogMsg ==null)
{
	strservicesLogMsg="";
}
else
{
%>
<script type="text/javascript">
var sesmessg; 
sesmessg= "<%=strservicesLogMsg%>";
document.getElementById("displysesmsg").innerHTML=sesmessg;
</script>
<%
	session.removeAttribute("sesservicesdetailsErrorMsg");
}
%>
</body>
<script type="text/javascript">

var referral_amount;

function visitorcomments(visitid)
{
		errMsg ="<table cellspacing='10' cellpadding='0' bgcolor='#FFFFFF'><table width='350' bgcolor='#FFFFFF' valign='top'><tr height='30'><td align='right' valign='top' style='cursor:pointer;' bgcolor='#E6E6E6'><font color='red' size='3'><b><a href=\"javascript:closemobdiv(greyout(false));\" style='color:#848484;text-decoration:none;'>X&nbsp;&nbsp;</a></b>				</td></tr></table><tr><td><table width='350'  cellspacing='0' cellpadding='0' bgcolor='#FFFFFF'><tr><td width='100%' colspan='3'><table width='100%' cellspacing='0' cellpadding='0'><tr><td class='insidecontent' align='center'><font size='2' color='#FF8C00'>Update&nbsp;Visitor&nbsp;Comment!</font></td></tr></table></td></tr><tr><td height='5'></td></tr><tr><td width='100%' align='center' style='font-family:Verdana;font-size:10px;color:#999999;	text-decoration:none;padding:1px 1px;'><font color='#ff3333'>&nbsp;*&nbsp;</font>Enter&nbsp;Visitor&nbsp;MobileNumber.</td></tr><tr><td height='2'></td></tr><tr><td width='100%' align='center'><input class='insidecontent' type='text'  name='vismobilenumber' id='vismobilenumber' placeholder='9603467559' style='width:195px;height:20px;border: 2px solid #CCC;font-size: 13px;font-family: Verdana;border-radius: 4px 4px 4px 4px;' maxlength='10'></td></tr><tr><td height='5'></td></tr><tr><td width='100%' align='center' style='font-family:Verdana;font-size:10px;color:#999999;	text-decoration:none;padding:1px 1px;'><font color='#ff3333'>&nbsp;*&nbsp;</font>Enter&nbsp;Visitor&nbsp;Comment.</td></tr><tr><td height='2'></td></tr><tr><td width='100%' align='center'><textarea style='width:195px;height:50px;border: 2px solid #CCC;font-size: 13px;font-family: Verdana;border-radius: 4px 4px 4px 4px;overflow:auto;' class='insidecontent' align='left' type='text' id='viscoment' name='viscoment' rows='4' cols='34' onkeydown=\"if ((event.which && event.which == 13) || (event.keyCode && event.keyCode == 13)){javascript:updatevisitorscomments('"+visitid+"');return false;} else return true;\"/></textarea></td></tr><tr><td height='5'></td></tr><tr><td  width='50%' align='center' ><input type='button' class='BookBatterysubmit' name='Submitrret' value='Submit' disable='false' onclick=\"javascript:updatevisitorscomments('"+visitid+"');\"></td></tr><tr height='26'><td colspan='3' align='center' class='subheading' id='displayvisitorermsg'></td></tr><tr><td height='15'></td></tr></table>";
	
	document.getElementById("divmobile").innerHTML="";
	document.getElementById("divmobile").style.display='block';
	document.getElementById("divmobile").innerHTML=errMsg
	greyout(true);	
	document.servicedet.vismobilenumber.focus();
}
function updatevisitorscomments(visitid)
{
	var vismobilenumber = document.servicedet.vismobilenumber.value;
	var viscomments = document.servicedet.viscoment.value;

	if(vismobilenumber == 0 || vismobilenumber == "")
	{
		errMsg ="<font color='#9B5BDD'>Please enter Mobile Number...!</font>";
		document.getElementById("displayvisitorermsg").innerHTML=errMsg;
		document.servicedet.vismobilenumber.focus();
		return ;
	}
	else 
	{
		var checkOK = "0123456789";
		var checkStr = vismobilenumber;
		var allValid = true;
		var decPoints = 0;
		var allNum = "";
		for (i = 0;  i < checkStr.length;  i++)
		{
			ch = checkStr.charAt(i);
			for (j = 0;  j < checkOK.length;  j++)
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
		 errMsg ="float strdisprice1 = strdisprice+(strdisprice*strcustax1)";
		ElementById("displayvisitorermsg").innerHTML=errMsg;
		document.servicedet.vismobilenumber.value="";
		document.servicedet.vismobilenumber.focus();
		return ;
		
		}
	}
	if (document.getElementById("vismobilenumber").value.length<10)
	{
		errMsg ="<font color='#9B5BDD'>Please enter valid Number...!</font>";
		document.getElementById("displayvisitorermsg").innerHTML=errMsg;
		document.servicedet.vismobilenumber.focus();
		return;
	}
	if (document.getElementById("vismobilenumber").value.length==10)
	{
		if (vismobilenumber < 7000000000 )
		 {
			errMsg ="<font color='#9B5BDD'>Number Should start with 7 or 8 or 9...!</font>";
			document.getElementById("displayvisitorermsg").innerHTML=errMsg;
			document.servicedet.vismobilenumber.focus();
			return ;
		 }
	}  
	if (document.getElementById("vismobilenumber").value.length>10)
	{
		if (vismobilenumber < 917000000000 || vismobilenumber >= 920000000000 )
		{
			errMsg ="<font color='#9B5BDD'>Number Should start with 91-7 or 8 or 9...!</font>";
			document.getElementById("displayvisitorermsg").innerHTML=errMsg;
			document.servicedet.vismobilenumber.focus();
			return ;
		}
	}  

	if(viscomments == "")
     {
		errMsg ="<font color='#9B5BDD'>Please enter Visitor Comment...!</font>";
		document.getElementById("displayvisitorermsg").innerHTML=errMsg;
		document.servicedet.viscoment.focus();
		return ;
     }
	 var xmlhttp= "";
	var resp= "";
	var url="../../../servlet/OperatorServicesDetails?hidWhatToDo=updatevisitorcomment&visid="+visitid+"&vismobilenumber="+vismobilenumber+"&viscomment="+viscomments;
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
		 if (xmlhttp.readyState==4 && xmlhttp.status==200)
		  {
			  resp =xmlhttp.responseText;
			  if(resp.indexOf("Failed")>=0)
				{
				  document.getElementById("divupdatevisitorcomment").innerHTML=xmlhttp.responseText;
				  document.getElementById("divupdatevisitorcomment").innerHTML=resp;
				  $('#divmobile').hide();
					greyout(false);
				}
				else
				{
					document.getElementById("divupdatevisitorcomment").innerHTML=xmlhttp.responseText;
					document.getElementById("divupdatevisitorcomment").innerHTML=resp;
					$('#divmobile').hide();
					greyout(false);
				}
		  }
	}
	xmlhttp.open("GET",url,true);
	xmlhttp.send();	
}
function negoprices(servicesplace,appmapid,strservicestype,strdisprice,strdiscountprice,stractprice)
 {
	errMsg ="<table cellspacing='10' cellpadding='0' bgcolor='#FFFFFF'><table width='350' bgcolor='#FFFFFF' valign='top'><tr height='30'><td align='right' valign='top' style='cursor:pointer;' bgcolor='#E6E6E6'><font color='red' size='3'><b><a href=\"javascript:closemobdiv(greyout(false));\" style='color:#848484;text-decoration:none;'>X&nbsp;&nbsp;</a></b>				</td></tr></table><tr><td><table width='350'  cellspacing='0' cellpadding='0' bgcolor='#FFFFFF'><tr><td width='100%' colspan='3'><table width='100%' cellspacing='0' cellpadding='0'><tr><td class='insidecontent' align='center'><font size='2' color='#FF8C00'>Negotiate&nbsp;Price!</font></td></tr></table></td><tr><td height='5'></td></tr></tr><tr><td width='100%' align='center' style='font-family:Verdana;font-size:10px;color:#999999;	text-decoration:none;padding:1px 1px;'><font color='#ff3333'>&nbsp;*&nbsp;</font>Enter&nbsp;Percentage&nbsp;to&nbsp;discount&nbsp;price.</td></tr><tr><td height='2'></td></tr></tr><tr><td width='100%' align='center'><input class='insidecontent' type='text'  name='discountprice' id='discountprice' placeholder='1% as 0.01' style='width:195px;height:20px;border: 2px solid #CCC;font-size: 13px;font-family: Verdana;border-radius: 4px 4px 4px 4px;' maxlength='6'></td></tr><tr><td height='5'></td></tr><tr><td width='100%' align='center' style='font-family:Verdana;font-size:10px;color:#999999;	text-decoration:none;padding:1px 1px;'><font color='#ff0000'>*</font>&nbsp;Note&nbsp;please&nbsp;enter&nbsp;1%&nbsp;as&nbsp;0.01.</td></tr><tr><td height='5'></td></tr></tr><tr><td width='100%' align='center' style='font-family:Verdana;font-size:10px;color:#999999;	text-decoration:none;padding:1px 1px;'><font color='#ff3333'>&nbsp;*&nbsp;</font>Enter&nbsp;Services&nbsp;Return&nbsp;price[OBRP].</td></tr><tr><td height='2'></td></tr></tr><tr><td width='100%' align='center'><input class='insidecontent' type='text'  name='servicesretprice' id='servicesretprice' value='"+stractprice+"' style='width:195px;height:20px;border: 2px solid #CCC;font-size: 13px;font-family: Verdana;border-radius: 4px 4px 4px 4px;' maxlength='6' onkeydown=\"if ((event.which && event.which == 13) || (event.keyCode && event.keyCode == 13)){javascript:getdiscountprices('"+appmapid+"','"+strservicestype+"','"+strdisprice+"','"+strdiscountprice+"','"+servicesplace+"');return false;} else return true;\"/></td></tr><tr><td height='5'></td></tr><tr><td  width='50%' align='center' ><input type='button' class='BookBatterysubmit' name='Submitrret' value='Submit' disable='false' onclick=\"javascript:getdiscountprices('"+appmapid+"','"+strservicestype+"','"+strdisprice+"','"+strdiscountprice+"','"+servicesplace+"');\"></td></tr><tr height='26'><td colspan='3' align='center' class='subheading' id='displayrefinederrormsg1'></td></tr><tr><td height='15'></td></tr></table>";
	
	document.getElementById("divmobile").innerHTML="";
	document.getElementById("divmobile").style.display='block';
	document.getElementById("divmobile").innerHTML=errMsg
	greyout(true);	
	document.servicedet.discountprice.focus();
}
function getdiscountprices(appmapid,strservicestype,strdisprice,strdiscountprice,servicesplace)
{
	
	var discountprice=document.servicedet.discountprice.value;
	var stractprice=document.servicedet.servicesretprice.value;
	var regularexp1=/[^0-9\.]/g;

	 if(discountprice == "")
	 {
		errMsg ="<font color='#9B5BDD'>Please enter \'Percentage to Discount Price\'.</font>";
		document.getElementById("displayrefinederrormsg1").innerHTML=errMsg;
		document.servicedet.discountprice.focus();
		return ;
	 }
	 if(discountprice.match(regularexp1))
	 {
		errMsg ="<font color='#9B5BDD'>Please enter \'Discount Percentage as format of 0.01\'.</font>";
		document.getElementById("displayrefinederrormsg1").innerHTML=errMsg;
		document.servicedet.discountprice.focus();
		return ;
	 }
	 if(stractprice == "")
	 {
		errMsg ="<font color='#9B5BDD'>Please enter \'Please Enter Services Return Price\'.</font>";
		document.getElementById("displayrefinederrormsg1").innerHTML=errMsg;
		document.servicedet.servicesretprice.focus();
		return ;
	 }
	   else if(document.servicedet.servicesretprice.value < 0.1)
		{
			errMsg ="<font color='#9B5BDD'>Please enter valid price in \'Services Return Price\'.</font>";
			document.getElementById("displayrefinederrormsg1").innerHTML=errMsg;
			document.servicedet.servicesretprice.focus();
			return ;
		}
		 else if (document.servicedet.servicesretprice.value.length < 2)
         {
			errMsg ="<font color='#9B5BDD'>Please enter at least 2 numbers in the \'Services Return Price\' field.</font>";
			document.getElementById("displayrefinederrormsg1").innerHTML=errMsg;
			document.servicedet.servicesretprice.focus();
			return ;
         }
		 else 
		{
		var checkOK = "0123456789";
		var checkStr = document.servicedet.servicesretprice.value;
		var allValid = true;
		var decPoints = 0;
		var allNum = "";
		for (i = 0;  i < checkStr.length;  i++)
		{
			ch = checkStr.charAt(i);
			for (j = 0;  j < checkOK.length;  j++)
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
			errMsg ="<font color='#9B5BDD'>Please enter only digits in the \'Services Return Price\' field.</font>";
			document.getElementById("displayrefinederrormsg1").innerHTML=errMsg;
			document.servicedet.servicesretprice.focus();
			return ;
		}
		}

	var xmlhttp= "";
	var resp= "";
	var url="../../../servlet/OperatorServicesDetails?hidWhatToDo=calculatepercentageprice&appmapid="+appmapid+"&strservicestype="+strservicestype+"&strdisprice="+strdisprice+"&strdiscountprice="+strdiscountprice+"&stractprice="+stractprice+"&pricediscount="+discountprice+"&servicesplace="+servicesplace;
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
		 if (xmlhttp.readyState==4 && xmlhttp.status==200)
		  {
			  resp =xmlhttp.responseText;
			  if(resp.indexOf("Failed")>=0)
				{
				  document.getElementById("divdisprice").innerHTML=xmlhttp.responseText;
				  document.getElementById("divdisprice").innerHTML=resp;
				}
				else
				{
					document.getElementById("divdisprice"+appmapid+"").innerHTML=xmlhttp.responseText;
					document.getElementById("divdisprice"+appmapid+"").innerHTML=resp;
					$('#divmobile').hide();
					greyout(false);
				}
		  }
	}
	xmlhttp.open("GET",url,true);
	xmlhttp.send();	
}

function Change_order_type()
{
	//alert("inside");
	//var order_type=escape(document.servicedet.order_type.value);
	var order_type=$('#order_type').val();
	
	//alert(order_type);
	
	if(document.servicedet.order_type.value.indexOf('Free') >=0)
	{
		//alert(22);
		//document.getElementById("ref_code_label").show();
		$('#ref_code_label').show();
		//document.getElementById("ref_code_value").show();
		$('#ref_code_value').show();
	}
	else
	{
		//alert(23);
		//document.getElementById("ref_code_label").hide();
		$('#ref_code_label').hide();
		//document.getElementById("ref_code_value").hide();
		$('#ref_code_value').hide();
	}
}
function validate_ref_code()
{
	var ref_code=$('#ref_code').val();
	var returnvalue="";
	//alert(ref_code);
		$.ajax
		({					 
			url: "/bookbattery/servlet/OperatorServicesDetails?hidWhatToDo=Validate_Ref_Code",
			type: 'GET',
			data:{ref_code:ref_code},
			success: function(data)
			{	
				//alert(data);
				returnvalue=data;
				var responsedata=data.split(" ");
				//alert(responsedata[3]);
				referral_amount=responsedata[3];
				//alert(referral_amount);
				//returnvalue=referral_amount;
				$('#error_msg').show();
				document.getElementById("refcodeerrormsg").innerHTML=data;
			}	
		});
	//return returnvalue;
}
function lookup(arg){
	
$('#error_msg').hide();

}
function askcosumerdetails(servicesplace,servicestype,servicepricemrp,servicepricediscount)
{
	var city=document.servicedet.city.value;
	var area=document.servicedet.strarea.value; 
	var pincode=document.servicedet.strpincode.value;
	var bat_type=document.servicedet.bat_type.value;
	
	//alert(bat_type);
	var styless;
	if(city == "" || city == 0)
	{
		 styless = "style='display:none;'";
	}
	else
	{
		 styles ="style='display:none;'";
	}
	
	errMsg ="<table cellspacing='10' cellpadding='0' bgcolor='#FFFFFF'><table width='350' bgcolor='#FFFFFF' valign='top'><tr height='30'><td align='right' valign='top' style='cursor:pointer;' bgcolor='#E6E6E6'><font color='red' size='3'><b><a href=\"javascript:closemobdiv(greyout(false));\" style='color:#848484;text-decoration:none;'>X&nbsp;&nbsp;</a></b>				</td></tr></table><tr><td><table width='350'  cellspacing='0' cellpadding='0' bgcolor='#FFFFFF'><tr><td width='100%' colspan='3'><table width='100%' cellspacing='0' cellpadding='0'><tr><td class='insidecontent' align='center'><font size='2' color='#FF8C00'>Please Enter Your Installation Location Details!</font></td></tr></table></td></tr><tr><td ><table width='100%' cellspacing='0' cellpadding='0'><tr><td width='20%'></td><td width='80%' height='10'></td></tr>	<tr><td width='20%'></td><td width='80%' align='left' style='font-family:Verdana;font-size:10px;color:#999999;	text-decoration:none;padding:1px 1px;'><font color='#ff3333'>&nbsp;*&nbsp;</font>Enter&nbsp;your&nbsp;Mobile&nbsp;Number</td></tr><tr><td width='20%'></td><td width='80%' align='left'>&nbsp;<input class='insidecontent' type='text' autocomplete='off' name='usermobilenumber' id='usermobilenumber' placeholder='9603467559' style='width:195px;height:20px;border: 2px solid #CCC;font-size: 13px;font-family: Verdana;border-radius: 4px 4px 4px 4px;' maxlength='10' ></td></tr><tr><td width='20%'></td><td width='80%' height='5'></td></tr><tr><td width='20%'></td><td width='80%' align='left' style='font-family:Verdana;font-size:10px;color:#999999;	text-decoration:none;padding:1px 1px;'><font color='#ff3333'>&nbsp;*&nbsp;</font>Enter&nbsp;your&nbsp;Name</td></tr><tr><td width='20%'></td><td width='80%' align='left'>&nbsp;<input class='insidecontent' type='text' autocomplete='off' name='username' id='username' placeholder='Johan' style='width:195px;height:20px;border: 2px solid #CCC;font-size: 13px;font-family: Verdana;border-radius: 4px 4px 4px 4px;' maxlength='40' ></td></tr><tr><td width='20%'></td><td width='80%' height='5'></td></tr><tr><td width='20%'></td><td width='80%' align='left' style='font-family:Verdana;font-size:10px;color:#999999;	text-decoration:none;padding:1px 1px;'><font color='#ff3333'>&nbsp;*&nbsp;</font>Enter&nbsp;your&nbsp;Email</td></tr><tr><td width='20%'></td><td width='80%' align='left'>&nbsp;<input class='insidecontent' type='text' autocomplete='off' name='emailid' id='emailid' placeholder='johan@gmail.com' style='width:195px;height:20px;border: 2px solid #CCC;font-size: 13px;font-family: Verdana;border-radius: 4px 4px 4px 4px;' maxlength='50' ></td></tr><tr><td width='20%'></td><td width='80%' height='5'></td></tr><tr><td width='20%'></td><td width='100%' align='left' style='font-family:Verdana;font-size:10px;color:#999999;	text-decoration:none;padding:1px 1px;'><font color='#ff3333'>&nbsp;*&nbsp;</font>Enter&nbsp;your&nbsp;Address&nbsp;1</td></tr><tr><td width='20%'></td><td width='80%' align='left'>&nbsp;<input class='insidecontent' type='text' autocomplete='off' name='addrs1' id='addrs1' placeholder='2-124,Road No:4,HSR Layout,Bangalore' style='width:195px;height:20px;border: 2px solid #CCC;font-size: 13px;font-family: Verdana;border-radius: 4px 4px 4px 4px;' maxlength='225' ></td></tr><tr><td width='20%'></td><td width='80%' height='5'></td></tr><tr><td width='20%'></td><td width='100%' align='left' style='font-family:Verdana;font-size:10px;color:#999999;	text-decoration:none;padding:1px 1px;'>&nbsp;Enter&nbsp;your&nbsp;Address&nbsp;2</td></tr><tr><td width='20%'></td><td width='80%' align='left'>&nbsp;<input class='insidecontent' type='text' autocomplete='off' name='addrs2' id='addrs2' placeholder='Near Forum Mall' style='width:195px;height:20px;border: 2px solid #CCC;font-size: 13px;font-family: Verdana;border-radius: 4px 4px 4px 4px;' maxlength='225' ></td></tr><tr "+styless+"><td width='20%'></td><td width='80%' height='5'></td></tr><tr><td width='20%'></td><td width='100%' align='left' style='font-family:Verdana;font-size:10px;color:#999999;	text-decoration:none;padding:1px 1px;'>&nbsp;Payment Mode Type</td></tr> <tr><td width='20%'></td><td width='80%' align='left'>&nbsp;<select name='paymenttype'  id='paymenttype' class='insidecontent' STYLE='width: 200px;' align='center' ><option value='0' >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;-- Select Payment Mode--&gt;</option> <option value='Cash' >Cash On Delivery</option>  <option value='Card On Delivery' >Card On Delivery</option><option value='Online Payment After Fitment' >Online Payment After Fitment</option><option value='Cheque'>Cheque</option></select></td></tr><tr><td width='20%'></td><td width='100%' align='left' style='font-family:Verdana;font-size:10px;color:#999999;	text-decoration:none;padding:1px 1px;'>&nbsp;Select&nbsp;Order&nbsp;Type&nbsp;</td></tr><tr><td width='20%'></td><td width='80%' align='left'>&nbsp;<select name='order_type' id='order_type' class='insidecontent' style='width: 200px;' align='center' onchange='Change_order_type()'><option value='0'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;-- Select Order Type--&gt;</option><option value='Free Order'>Free Order</option> <option value='Paid Order'>Paid Order</option></select><td width='20%'></td><td width='80%' height='5'></td></tr><tr  id='ref_code_label'><td width='20%'></td><td width='100%' align='left' style='font-family:Verdana;font-size:10px;color:#999999;text-decoration:none;padding:1px 1px;'>&nbsp;Enter&nbsp;Reference&nbsp;Code&nbsp;</td></tr><tr id='ref_code_value'><td width='20%'></td><td width='80%' align='left'>&nbsp;<input class='insidecontent' type='text' autocomplete='off' name='ref_code' id='ref_code' onkeyup='lookup(event.key);' placeholder='Reference Code' style='width:195px;height:20px;border: 2px solid #CCC;font-size: 13px;font-family: Verdana;border-radius: 4px 4px 4px 4px;' maxlength='225'><input type='button' class='BookBatteryvalidate' name='Apply' value='Apply' disable='false' onclick='javascript:validate_ref_code()' style='margin-left: 10px;'><td width='20%'></td><td width='80%' height='5'></td></tr><tr "+styles+" id='error_msg' height='26'><td colspan='3' align='center' class='subheading' id='refcodeerrormsg'></td></tr>";
	
		errMsg=errMsg+"<tr "+styless+"><td width='20%'></td><td width='100%' align='left' style='font-family:Verdana;font-size:10px;color:#999999;	text-decoration:none;padding:1px 1px;'>&nbsp;Enter&nbsp;your&nbsp;Quantity</td></tr><tr "+styless+"><td width='20%'></td><td width='80%' align='left'>&nbsp;<input class='insidecontent' type='text' autocomplete='off' name='quantity' id='quantity' value='1' placeholder='1 or 2 or 3 ...' style='width:195px;height:20px;border: 2px solid #CCC;font-size: 13px;font-family: Verdana;border-radius: 4px 4px 4px 4px;' maxlength='50' ></td></tr>";
	
	
	errMsg=errMsg+"<tr "+styless+"><td width='20%'></td><td width='100%' align='left' style='font-family:Verdana;font-size:10px;color:#999999;	text-decoration:none;padding:1px 1px;'>&nbsp;Enter&nbsp;your&nbsp;Area</td></tr><tr "+styless+"><td width='20%'></td><td width='80%' align='left'>&nbsp;<input class='insidecontent' type='text' autocomplete='off' name='userarea' id='userarea' value='"+area+"' placeholder='Benson Town' style='width:195px;height:20px;border: 2px solid #CCC;font-size: 13px;font-family: Verdana;border-radius: 4px 4px 4px 4px;' maxlength='50' readonly ></td></tr><tr><td width='20%'></td><td width='80%' height='5'></td></tr><tr "+styless+"><td width='20%'></td><td width='100%' align='left' style='font-family:Verdana;font-size:10px;color:#999999;	text-decoration:none;padding:1px 1px;'>&nbsp;Enter&nbsp;your&nbsp;City</td></tr><tr "+styless+"><td width='20%'></td><td width='80%' align='left'>&nbsp;<input class='insidecontent' type='text' autocomplete='off' name='usercity' id='usercity' value='"+city+"' placeholder='Bangalore' style='width:195px;height:20px;border: 2px solid #CCC;font-size: 13px;font-family: Verdana;border-radius: 4px 4px 4px 4px;' maxlength='50' readonly onkeydown=\"if ((event.which && event.which == 13) || (event.keyCode && event.keyCode == 13)){javascript:getconsumerdetails('"+servicesplace+"','"+servicestype+"','"+servicepricemrp+"','"+servicepricediscount+"',Submitrret);return false;} else return true;\" /></td></tr><tr><td width='20%'></td><td width='80%' height='5'></td></tr><tr "+styles+"><td width='20%'></td><td width='80%' align='left' style='font-family:Verdana;font-size:10px;color:#999999;	text-decoration:none;padding:1px 1px;'>&nbsp;Enter&nbsp;your&nbsp;ZipCode</td></tr><tr "+styles+"><td width='20%'></td><td width='80%' align='left'>&nbsp;<input class='insidecontent' type='text' autocomplete='off' name='userzipcode' id='userzipcode' value='"+pincode+"' placeholder='560037' style='width:195px;height:20px;border: 2px solid #CCC;font-size: 13px;font-family: Verdana;border-radius: 4px 4px 4px 4px;' maxlength='7' readonly onkeydown=\"if ((event.which && event.which == 13) || (event.keyCode && event.keyCode == 13)){javascript:getconsumerdetails('"+servicesplace+"','"+servicestype+"','"+servicepricemrp+"','"+servicepricediscount+"',Submitrret);return false;} else return true;\"/></td></tr><tr><td width='20%'></td><td width='80%' align='left'><input type='button' class='BookBatterysubmit' name='Submitrret' value='Submit' disable='false' onclick=\"javascript:getconsumerdetails('"+servicesplace+"','"+servicestype+"','"+servicepricemrp+"','"+servicepricediscount+"',Submitrret);\"></td></tr>  </table></td></tr><tr height='26'><td colspan='3' align='center' class='subheading' id='displayrefinederrormsg'></td></tr><tr><td height='15'></td></tr></table>";

	document.getElementById("divmobile").innerHTML="";
	document.getElementById("divmobile").style.display='block';
	document.getElementById("divmobile").innerHTML=errMsg
	greyout(true);
	document.servicedet.usermobilenumber.focus();
}
function getconsumerdetails(servicesplace,servicestype,servicepricemrp,servicepricediscount,varButton)
{
	var strUsermobileno=document.servicedet.usermobilenumber.value;
	var strusername=document.servicedet.username.value;
	var emailid=document.servicedet.emailid.value;
	var addrs1=document.servicedet.addrs1.value;
	var addrs2=document.servicedet.addrs2.value;  
	var userarea=document.servicedet.userarea.value; 
	var usercity=document.servicedet.usercity.value; 
	var userzipcode=document.servicedet.userzipcode.value;
	var city=document.servicedet.city.value;
	var pincity=document.servicedet.pincity.value;
	var strarea=document.servicedet.strarea.value; 
	var strstate=document.servicedet.strstate.value;
	var strpincode=document.servicedet.strpincode.value;
	var vehiclemake=document.servicedet.vehiclemake.value;
	var vehiclemodel=escape(document.servicedet.vehiclemodel.value);
	var bat_type=escape(document.servicedet.bat_type.value);
	var inv_capacity=escape(document.servicedet.inv_capacity.value);
	var order_type=escape(document.servicedet.order_type.value);
	var paymenttype=escape(document.servicedet.paymenttype.value);
	var quantity=escape(document.servicedet.quantity.value);
	
	
	//alert(validate_ref_code);
	//alert(order_type);
	//alert(quantity);
	//alert(bat_type);
	//alert(document.servicedet.bat_type.value);
	
	//referral_amount= validate_ref_code();
	
	//alert("ins"+referral_amount);
	
	//alert("outside"+referral_amount);
	
	if(order_type=="Free Order")
	{
		//alert(referral_amount);
		
		if(referral_amount<300)
		{
			//alert("inside if"+referral_amount);
			errMsg ="<font color='#9B5BDD'>He has "+referral_amount+" in his refferral amount and he cannot avail free battery health check.</font>";
			document.getElementById("displayrefinederrormsg").innerHTML=errMsg;
			document.servicedet.ref_code.focus();
			return ;
		}
		else
		{
			//alert("inside else"+referral_amount);
			if(referral_amount<600)
			{
				if(bat_type=="Inverter Battery" || bat_type=="Inverter Batteries")
				{
					errMsg ="<font color='#9B5BDD'>He has "+referral_amount+" in his refferral amount and he cannot avail free battery health check.</font>";
					document.getElementById("displayrefinederrormsg").innerHTML=errMsg;
					document.servicedet.ref_code.focus();
					return ;
				}
				else
				{

				}
			}
			else
			{

			}
		}
	}

   


     var reg="/@(([^\.-]*\.[^\.]*)?){1,3}$/";
     var re =
/^(([^()[\]\\.;:\s@\"]+(\.[^()[\]\\.;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
     var regexp=/^[a-zA-Z0-9_\+-]+(\.[a-zA-Z0-9_\+-]+)*@[a-zA-Z0-9-]+(\.[a-zA-Z0-9-]+)*\.([a-zA-Z]{2,3})$/;
     var iChars1 = "`~,!#$%^&*()+=[]\\\';/{}|\":<>?";
     var iChars3 = "`~!@#$%^&*()+=-_[]\\\';,/{}|\":<>?";
     var iChars = "!@~`©±µ¿£¢'\"#";
     var alpchar="/[a-zA-z]/";
     var expr="/^[.-_]/";
     var reg=/@(([^\.]*\.[^\.]*)?){1,3}$/;
     var iChars2 = "`~!@#$%^&*()+=[]\\\';,/{}|\"<>?";
	 var nonums ="1234567890";
	 var dot="."
	var iChars12 = "\\\'";
	var nonums1 =/^([a-z]|[A-Z]| )*$/;

	if(strUsermobileno == 0 || strUsermobileno == "")
	{
		errMsg ="<font color='#9B5BDD'>Please enter Mobile Number...!</font>";
		document.getElementById("displayrefinederrormsg").innerHTML=errMsg;
		document.servicedet.usermobilenumber.focus();
		return ;
	}
	else 
	{
		var checkOK = "0123456789";
		var checkStr = strUsermobileno;
		var allValid = true;
		var decPoints = 0;
		var allNum = "";
		for (i = 0;  i < checkStr.length;  i++)
		{
			ch = checkStr.charAt(i);
			for (j = 0;  j < checkOK.length;  j++)
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
		 errMsg ="<font color='#9B5BDD'>Please enter only digits...!</font>";
		document.getElementById("displayrefinederrormsg").innerHTML=errMsg;
		document.servicedet.usermobilenumber.value="";
		document.servicedet.usermobilenumber.focus();
		return ;
		
		}
	}
	if (document.getElementById("usermobilenumber").value.length<10)
	{
		errMsg ="<font color='#9B5BDD'>Please enter valid Number...!</font>";
		document.getElementById("displayrefinederrormsg").innerHTML=errMsg;
		document.servicedet.usermobilenumber.focus();
		return;
	}
	if (document.getElementById("usermobilenumber").value.length==10)
	{
		if (strUsermobileno < 7000000000 )
		 {
			errMsg ="<font color='#9B5BDD'>Number Should start with 7 or 8 or 9...!</font>";
			document.getElementById("displayrefinederrormsg").innerHTML=errMsg;
			document.servicedet.usermobilenumber.focus();
			return ;
		 }
	}  
	if (document.getElementById("usermobilenumber").value.length>10)
	{
		if (strUsermobileno < 917000000000 || strUsermobileno >= 920000000000 )
		{
			errMsg ="<font color='#9B5BDD'>Number Should start with 91-7 or 8 or 9...!</font>";
			document.getElementById("displayrefinederrormsg").innerHTML=errMsg;
			document.servicedet.usermobilenumber.focus();
			return ;
		}
	}  

	if(strusername == "")
     {
		errMsg ="<font color='#9B5BDD'>Please enter User Name...!</font>";
		document.getElementById("displayrefinederrormsg").innerHTML=errMsg;
		document.servicedet.username.focus();
		return ;
     }
     if(strusername.length <3)
     {
		errMsg ="<font color='#9B5BDD'>UserName should have minimum 3 characters</font>";
		document.getElementById("displayrefinederrormsg").innerHTML=errMsg;
		document.servicedet.username.focus();
		return ;
     }
     if(strusername.length == 3)
     {
         if (strusername.indexOf(' ') >= 0 )
         {
			errMsg ="<font color='#9B5BDD'>User Name should have minimum 3 characters</font>";
			document.getElementById("displayrefinederrormsg").innerHTML=errMsg;
			document.servicedet.username.focus();
			return ;
         }
     }
     if(strusername.length == 3)
     {
         if (strusername.indexOf(dot) >= 0 )
         {
			errMsg ="<font color='#9B5BDD'>UserName is invalid</font>";
			document.getElementById("displayrefinederrormsg").innerHTML=errMsg;
			document.servicedet.username.focus();
			return ;
         }
     }
     if (document.servicedet.username.value.indexOf(' ')==0 )
     {
			errMsg ="<font color='#9B5BDD'>UserName should not start with space</font>";
			document.getElementById("displayrefinederrormsg").innerHTML=errMsg;
			document.servicedet.username.focus();
			return ;
     }
     if (strusername.indexOf('  ') >= 0 )
     {
			errMsg ="<font color='#9B5BDD'> Invalid User Name</font>";
			document.getElementById("displayrefinederrormsg").innerHTML=errMsg;
			document.servicedet.username.focus();
			return ;
     }
     if (strusername.indexOf('..') >= 0 )
     {
			errMsg ="<font color='#9B5BDD'> Invalid User Name</font>";
			document.getElementById("displayrefinederrormsg").innerHTML=errMsg;
			document.servicedet.username.focus();
			return ;
     }
     if (document.servicedet.username.value.indexOf(dot)==0 )
     {
			errMsg ="<font color='#9B5BDD'> User Name should not start with dot</font>";
			document.getElementById("displayrefinederrormsg").innerHTML=errMsg;
			document.servicedet.username.focus();
			return ;
     }
     for (var i = 0; i < document.servicedet.username.value.length; i++)
     {
         if (iChars3.indexOf(document.servicedet.username.value.charAt(i))!= -1)
         {
			errMsg ="<font color='#9B5BDD'>Special characters are not allowed in User Name field.</font>";
			document.getElementById("displayrefinederrormsg").innerHTML=errMsg;
			document.servicedet.username.focus();
			return ;
         }
     }
     for (var i = 0; i < document.servicedet.username.value.length; i++)
     {
         if (nonums.indexOf(document.servicedet.username.value.charAt(i))!= -1)
         {
			errMsg ="<font color='#9B5BDD'>Numbers are not allowed in User Name field.</font>";
			document.getElementById("displayrefinederrormsg").innerHTML=errMsg;
			document.servicedet.username.focus();
			return ;
         }
     }
	  if (/[a-z][A-Z]{2}/i.test(document.servicedet.username.value) != true) 
		{
		  errMsg ="<font color='#9B5BDD'>Please enter atleast 3 Charaters together in the User Name Field.</font>";
			document.getElementById("displayrefinederrormsg").innerHTML=errMsg;
			document.servicedet.username.focus();
			return ;
         }

		var at="@"
		var lat=emailid.indexOf(at)
		var lstr=emailid.length
		var ldot=emailid.indexOf(dot)
		var sst=emailid.substring(at,lstr)
		var sstdot=sst.indexOf(dot)
		var hi = "-"
		var us = "_"
		var lhi = emailid.indexOf(hi)
		var lus = emailid.indexOf(us)
	

     if(emailid == "")
     {
         errMsg ="<font color='#9B5BDD'>Please Enter Email id</font>";
		 document.getElementById("displayrefinederrormsg").innerHTML=errMsg;
         document.servicedet.emailid.focus();
         return ;
     }
     if (document.servicedet.emailid.value.indexOf(' ') >= 0 )
     {
         errMsg ="<font color='#9B5BDD'>Spaces are not allowed for Email id</font>";
		document.getElementById("displayrefinederrormsg").innerHTML=errMsg;
         document.servicedet.emailid.focus();
         return ;
     }
     if (emailid.indexOf(at)==-1)
     {
         errMsg ="<font color='#9B5BDD'>Email-id should be in the form of abcxyz@gmail.com</font>";
		document.getElementById("displayrefinederrormsg").innerHTML=errMsg;
         document.servicedet.emailid.focus();
         return ;
     }
     if (emailid.indexOf(at) < 2)
     {
         errMsg ="<font color='#9B5BDD'>Please Enter Valid Email id</font>";
		 document.getElementById("displayrefinederrormsg").innerHTML=errMsg;
         document.servicedet.emailid.focus();
         return ;
     }
     if (emailid.indexOf(at)==-1 || emailid.indexOf(at)==0 || emailid.indexOf(at)==lstr)
     {
         errMsg ="<font color='#9B5BDD'>Email id should be in the form of abcxyz@gmail.com</font>";
		 document.getElementById("displayrefinederrormsg").innerHTML=errMsg;
         document.servicedet.emailid.focus();
         return ;
     }
     if (emailid.indexOf(dot)==-1 || emailid.indexOf(dot)==0 || emailid.indexOf(dot)==lstr)
     {
         errMsg ="<font color='#9B5BDD'>Email id should be in the form of abcxyz@gmail.com</font>";
		 document.getElementById("displayrefinederrormsg").innerHTML=errMsg;
         document.servicedet.emailid.focus();
         return ;
     }
     if (emailid.indexOf(at) >50)
     {
         errMsg ="<font color='#9B5BDD'>Email id should not exceed more than 50 characters</font>";
		 document.getElementById("displayrefinederrormsg").innerHTML=errMsg;
         document.servicedet.emailid.focus();
         return ;
     }
     if (emailid.indexOf(dot) == -1 || emailid.indexOf(dot) == 0 || emailid.indexOf(dot) == lstr)
     {
         errMsg ="<font color='#9B5BDD'>Email id should be in the form of abcxyz@gmail.com</font>";
		 document.getElementById("displayrefinederrormsg").innerHTML=errMsg;
         document.servicedet.emailid.focus();
         return ;
     }
     if (! document.servicedet.emailid.value.match(reg))
     {
         errMsg ="<font color='#9B5BDD'>Email id should be in the form of abcxyz@gmail.com</font>";
         document.getElementById("displayrefinederrormsg").innerHTML=errMsg;
         document.servicedet.emailid.focus();
         return ;
     }
     if (emailid.indexOf(at, (lat + 1)) != -1)
     {
         errMsg ="<font color='#9B5BDD'>Email id should be in the form of abcxyz@gmail.com</font>";
		 document.getElementById("displayrefinederrormsg").innerHTML=errMsg;
         document.servicedet.emailid.focus();
         return ;
     }
     if (emailid.substring(lat - 1, lat) == dot || emailid.substring(lat + 1, lat + 2) == dot || emailid.substring(ldot+1,ldot+2)==dot)
     {
         errMsg ="<font color='#9B5BDD'>Email id should be in the form of abcxyz@gmail.com</font>";
		 document.getElementById("displayrefinederrormsg").innerHTML=errMsg;
         document.servicedet.emailid.focus();
         return ;
     }
     if (emailid.substring(lat - 1, lat) == us || emailid.substring(lat + 1, lat + 2) == us || emailid.substring(lat+1,lat+2)==us)
     {
         errMsg ="<font color='#9B5BDD'>Email id should be in the form of abcxyz@gmail.com</font>";
		 document.getElementById("displayrefinederrormsg").innerHTML=errMsg;
         document.servicedet.emailid.focus();
         return ;
     }
     if (emailid.substring(lat - 1, lat) == hi || emailid.substring(lat + 1, lat + 2) == hi || emailid.substring(lhi+1,lhi+2)==hi)
     {
         errMsg ="<font color='#9B5BDD'>Email id should be in the form of abcxyz@gmail.com</font>";
		 document.getElementById("displayrefinederrormsg").innerHTML=errMsg;
         document.servicedet.emailid.focus();
         return ;
     }
     if (emailid.indexOf(dot, (lat + 2)) == -1)
     {
         errMsg ="<font color='#9B5BDD'>Email id should be in the form of abcxyz@gmail.com</font>";
		 document.getElementById("displayrefinederrormsg").innerHTML=errMsg;
         document.servicedet.emailid.focus();
         return ;
     }
     if (emailid.indexOf(" ") != -1)
     {
         errMsg ="<font color='#9B5BDD'>Email id should be in the form of abcxyz@gmail.com</font>";
		 document.getElementById("displayrefinederrormsg").innerHTML=errMsg;
         document.servicedet.emailid.focus();
         return ;
     }
     if (emailid.substring(lhi-1,lhi)==hi || emailid.substring(lhi+1,lhi+2)==hi)
     {
         errMsg ="<font color='#9B5BDD'>Email id should be in the form of abcxyz@gmail.com</font>";
		 document.getElementById("displayrefinederrormsg").innerHTML=errMsg;
         document.servicedet.emailid.focus();
         return ;
     }
     if ((emailid.substring(lhi-1,lhi)==hi || emailid.substring(lus+1,lus+2)==us) || (emailid.substring(lus-1,lus)==us ||   emailid.substring(lhi+1,lhi+2)==hi))
     {
         errMsg ="<font color='#9B5BDD'>Email id should be in the form of abcxyz@gmail.com</font>";
		 document.getElementById("displayrefinederrormsg").innerHTML=errMsg;
         document.servicedet.emailid.focus();
         return ;
     }
     if (emailid.substring(lhi - 1, lhi) == dot || emailid.substring(lhi + 1, lhi + 2) == dot || emailid.substring(lus+1,lus+2)==dot)
     {
         errMsg ="<font color='#9B5BDD'>Email id should be in the form of abcxyz@gmail.com</font>";
		 document.getElementById("displayrefinederrormsg").innerHTML=errMsg;
         document.servicedet.emailid.focus();
         return ;
     }
     if (emailid.substring(lus - 1, lus) == dot || emailid.substring(lus + 1, lus + 2) == dot || emailid.substring(ldot+1,ldot+2)==dot)
     {
         errMsg ="<font color='#9B5BDD'>Email id should be in the form of abcxyz@gmail.com</font>";
		 document.getElementById("displayrefinederrormsg").innerHTML=errMsg;
         document.servicedet.emailid.focus();
         return ;
     }
     if (document.servicedet.emailid.value.indexOf('com.in') >=0)
     {
         errMsg ="<font color='#9B5BDD'>Email id should be in the form of abcxyz@gmail.com</font>";
		 document.getElementById("displayrefinederrormsg").innerHTML=errMsg;
         document.servicedet.emailid.focus();
         return ;
     }
     if (emailid.substring(lhi - 1, lhi) == us || emailid.substring(lhi + 1, lhi + 2) == us || emailid.substring(lus+1,lus+2)==us)
     {
         errMsg ="<font color='#9B5BDD'>Email id should be in the form of abcxyz@gmail.com</font>";
	     document.getElementById("displayrefinederrormsg").innerHTML=errMsg;
         document.servicedet.emailid.focus();
         return ;
     }
     if (emailid.indexOf(hi, (lhi + 1)) != -1)
     {
         errMsg ="<font color='#9B5BDD'>Email id should be in the form of abcxyz@gmail.com</font>";
		 document.getElementById("displayrefinederrormsg").innerHTML=errMsg;
         document.servicedet.emailid.focus();
         return ;
     }
     if (! document.servicedet.emailid.value.match(re))
     {
         errMsg ="<font color='#9B5BDD'>Email id should be in the form of abcxyz@gmail.com</font>";
		 document.getElementById("displayrefinederrormsg").innerHTML=errMsg;
         document.servicedet.emailid.focus();
         return ;
     }
     for (var i = 0; i < document.servicedet.emailid.value.length; i++)
     {
         if (iChars1.indexOf(document.servicedet.emailid.value.charAt(i))!= -1)
         {
             errMsg ="<font color='#9B5BDD'>Invalid characters are not allowed in Email id field</font>";
		     document.getElementById("displayrefinederrormsg").innerHTML=errMsg;
             document.servicedet.emailid.focus();
             return ;
         }
     }
     if (! document.servicedet.emailid.value.match(regexp))
     {
         errMsg ="<font color='#9B5BDD'>Email id should be in the form of abcxyz@gmail.com</font>";
		 document.getElementById("displayrefinederrormsg").innerHTML=errMsg;
         document.servicedet.emailid.focus();
         return ;
     }
     if (document.servicedet.emailid.value.indexOf('gmail') >=0)
     {
         if (document.servicedet.emailid.value.indexOf('com')<0)
         {
             errMsg ="<font color='#9B5BDD'>Email id should be in the form of abcxyz@gmail.com</font>";
			 document.getElementById("displayrefinederrormsg").innerHTML=errMsg;
             document.servicedet.emailid.focus();
             return ;
         }
     }
     if (document.servicedet.emailid.value.indexOf('rediffmail') >=0)
     {
         if (document.servicedet.emailid.value.indexOf('com')<0)
         {
             errMsg ="<font color='#9B5BDD'>Email id should be in the form of abcxyz@gmail.com</font>";
			 document.getElementById("displayrefinederrormsg").innerHTML=errMsg;
             document.servicedet.emailid.focus();
             return ;
         }
     }
     if (document.servicedet.emailid.value.indexOf('hotmail') >=0)
     {
         if (document.servicedet.emailid.value.indexOf('com')<0)
         {
             errMsg ="<font color='#9B5BDD'>Email id should be in the form of abcxyz@gmail.com</font>";
			 document.getElementById("displayrefinederrormsg").innerHTML=errMsg;
             document.servicedet.emailid.focus();
             return ;
         }
     }
   
     if (document.servicedet.emailid.value.indexOf('ymail') >=0)
     {
         if (document.servicedet.emailid.value.indexOf('com')<0)
         {
             errMsg ="<font color='#9B5BDD'>Email id should be in the form of abcxyz@gmail.com</font>";
			 document.getElementById("displayrefinederrormsg").innerHTML=errMsg;
             document.servicedet.emailid.focus();
             return ;
         }
     }
     if (emailid.indexOf("__") != -1)
     {
         errMsg ="<font color='#9B5BDD'>Email id should be in the form of abcxyz@gmail.com</font>";
		 document.getElementById("displayrefinederrormsg").innerHTML=errMsg;
         document.servicedet.emailid.focus();
         return ;
     }
     if (emailid.indexOf(at,(lat+1))!=-1)
     {
         errMsg ="<font color='#9B5BDD'>Email id should be in the form of abcxyz@gmail.com</font>";
		 document.getElementById("displayrefinederrormsg").innerHTML=errMsg;
         document.servicedet.emailid.focus();
         return ;
     }
     if (emailid.substring(lat-1,lat)==dot || emailid.substring(lat+1,lat+2)==dot)
        {
         errMsg ="<font color='#9B5BDD'>Email id should be in the form of abcxyz@gmail.com</font>";
		 document.getElementById("displayrefinederrormsg").innerHTML=errMsg;
         document.servicedet.emailid.focus();
         return ;
     }
     if (emailid.indexOf(dot,(lat+2))==-1)
     {
         errMsg ="<font color='#9B5BDD'>Email id should be in the form of abcxyz@gmail.com</font>";
         openPopUp(errMsg);
         return;
         return false
     }
     if (emailid.indexOf(" ")!=-1)
     {
         errMsg ="<font color='#9B5BDD'>Email id should be in the form of abcxyz@gmail.com</font>";
		 document.getElementById("displayrefinederrormsg").innerHTML=errMsg;
         document.servicedet.emailid.focus();
         return ;
     }
     if (! document.servicedet.emailid.value.match(re))
     {
         errMsg ="<font color='#9B5BDD'>Email id should be in the form of abcxyz@gmail.com</font>";
		 document.getElementById("displayrefinederrormsg").innerHTML=errMsg;
         document.servicedet.emailid.focus();
         return ;
     }
     if (document.servicedet.emailid.value.indexOf("-")==0 )
     {
         errMsg ="<font color='#9B5BDD'>Email id should be in the form of abcxyz@gmail.com</font>";
		 document.getElementById("displayrefinederrormsg").innerHTML=errMsg;
         document.servicedet.emailid.focus();
         return ;
     }
     if (document.servicedet.emailid.value.indexOf("_")==0 )
     {
         errMsg ="<font color='#9B5BDD'>Email id should be in the form of abcxyz@gmail.com</font>";
		 document.getElementById("displayrefinederrormsg").innerHTML=errMsg;
         document.servicedet.emailid.focus();
         return ;
     }
     if (document.servicedet.emailid.value.indexOf("0123456789")==3 )
     {
         errMsg ="<font color='#9B5BDD'>Email id should be in the form of abcxyz@gmail.com</font>";
		document.getElementById("displayrefinederrormsg").innerHTML=errMsg;
         document.servicedet.emailid.focus();
         return ;
     }
	 if(addrs1 == "")
     {
		errMsg ="<font color='#9B5BDD'>Please enter Address 1...!</font>";
		document.getElementById("displayrefinederrormsg").innerHTML=errMsg;
		document.servicedet.addrs1.focus();
		return ;
     }
	 if (document.servicedet.addrs1.value.indexOf(' ') == 0 ) 
	 {
		 errMsg ="<font color='#9B5BDD'>Address 1 should not start with space</font>";
		document.getElementById("displayrefinederrormsg").innerHTML=errMsg;
		document.servicedet.addrs1.focus();
		return ;
	  }
	  var checkOK = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ#&()+=-[]',./\|_\":?0123456789 ";
		var checkStr = addrs1;
		var allValid = true;
		var decPoints = 0;
		var allNum = "";
		for (i = 0;  i < checkStr.length;  i++)
		{
			ch = checkStr.charAt(i);
			for (j = 0;  j < checkOK.length;  j++)
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
		errMsg ="<font color='#9B5BDD'>Address 1 have some special character. Please remove them and try again...!</font>";
		document.getElementById("displayrefinederrormsg").innerHTML=errMsg;
		document.servicedet.addrs1.focus();
		return ;
	  }
	   if(isDigits(addrs1)==true)
		{
			errMsg ="<font color='#9B5BDD'>Only Numerics are not allowed in the \'Address 1\' field.</font>";
			document.getElementById("displayrefinederrormsg").innerHTML=errMsg;
			document.servicedet.addrs1.focus();
			return ;
         }
	 if(addrs2 == "")
     {
		
     }
	 else
	{
	 if (document.servicedet.addrs2.value.indexOf(' ') == 0 ) 
	 {
		 errMsg ="<font color='#9B5BDD'>Address 2 should not start with space</font>";
		document.getElementById("displayrefinederrormsg").innerHTML=errMsg;
		document.servicedet.addrs2.focus();
		return ;
	  }
	  var checkOK = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ#&()+=-[]',./\|_\":?0123456789 ";
		var checkStr = addrs2;
		var allValid = true;
		var decPoints = 0;
		var allNum = "";
		for (i = 0;  i < checkStr.length;  i++)
		{
			ch = checkStr.charAt(i);
			for (j = 0;  j < checkOK.length;  j++)
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
		errMsg ="<font color='#9B5BDD'>Address 2 have some special character. Please remove them and try again...!</font>";
		document.getElementById("displayrefinederrormsg").innerHTML=errMsg;
		document.servicedet.addrs2.focus();
		return ;
	  }
	}
			if(isDigits(addrs2)==true)
			{
			errMsg ="<font color='#9B5BDD'>Only Numerics are not allowed in the \'Address 2\' field.</font>";
			document.getElementById("displayrefinederrormsg").innerHTML=errMsg;
			document.servicedet.addrs2.focus();
			return ;
			}
			if(order_type == 0 || order_type == "")
			{
			errMsg ="<font color='#9B5BDD'>Please select Order Type...!</font>";
			document.getElementById("displayrefinederrormsg").innerHTML=errMsg;
			document.servicedet.order_type.focus();
			return ;
			}
			var ref_code=0;
		if(document.servicedet.order_type.value.indexOf('Free') >= 0)
		{
			var ref_code=escape(document.servicedet.ref_code.value);
			//alert(ref_code);
			if(ref_code=="")
			 {
				errMsg ="<font color='#9B5BDD'>Please Enter Reference Code</font>";
				document.getElementById("displayrefinederrormsg").innerHTML=errMsg;
				document.servicedet.ref_code.focus();
				return ;
			 }
		}

		if(paymenttype == 0 || paymenttype == "")
		{
		errMsg ="<font color='#9B5BDD'>Please select Payment Type...!</font>";
		document.getElementById("displayrefinederrormsg").innerHTML=errMsg;
		document.servicedet.paymenttype.focus();
		return ;
		}	

		 if(quantity=="")
		 {
			errMsg ="<font color='#9B5BDD'>Please enter Quantity</font>";
			document.getElementById("displayrefinederrormsg").innerHTML=errMsg;
			document.servicedet.addrs1.focus();
			return ;
		 }
		 
		//alert(quantity);
		addrs1=escape(addrs1);
		addrs2=escape(addrs2);
		var xmlhttp= "";
		var resp= "";
		var url="../../../servlet/OperatorServicesDetails?hidWhatToDo=insertconsumerdetails&username="+strusername+"&emailid="+emailid+"&addrs1="+addrs1+"&addrs2="+addrs2+"&userarea="+userarea+"&usercity="+usercity+"&pincity="+pincity+"&userzipcode="+userzipcode+"&mobilenumber="+strUsermobileno+"&servicepricemrp="+servicepricemrp+"&servicepricediscount="+servicepricediscount+"&servicesplace="+servicesplace+"&city="+city+"&strarea="+strarea+"&strstate="+strstate+"&strpincode="+strpincode+"&inv_capacity="+inv_capacity+"&quantity="+quantity+"&order_type="+order_type+"&paymenttype="+paymenttype+"&ref_code="+ref_code+"&bat_type="+bat_type+"&servicestype="+servicestype+"&vehiclemake="+vehiclemake+"&vehiclemodel="+vehiclemodel;
     
		document.getElementById("displayrefinederrormsg").innerHTML=""; 
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
			if (xmlhttp.readyState==4)
			{
				if(xmlhttp.status!=200)
				{
					return;
				}
				else
				{
					resp = xmlhttp.responseText;
	
					document.getElementById("divmobile").innerHTML="";
					document.getElementById("divmobile").innerHTML=resp;
					resp= "";
					
				}
			}	
		}
		xmlhttp.open("GET",url,true);
		xmlhttp.send();
		varButton.disabled=true; 
		varButton.value='Please Wait...';  
}
function isDigits(argvalue) {
    argvalue = argvalue.toString();
	if(argvalue=="")
	{
		return false;
	}
    var validChars = "0123456789";
    var startFrom = 0;
    if (argvalue.substring(0, 2) == "0x") {
       validChars = "0123456789abcdefABCDEF";
       startFrom = 2;
    } else if (argvalue.charAt(0) == "0") {
       validChars = "01234567";
       startFrom = 1;
    }
    for (var n = 0; n < argvalue.length; n++) {
        if (validChars.indexOf(argvalue.substring(n, n+1)) == -1) return false;
    }
  return true;
}
function closemobdiv()
{
	$('#divmobile').hide();
	greyout(false);
}
function closemobdivindex()
{
	$('#divmobile').hide();
	greyout(false);
}
/* added function by jhansi to navigate page to BookBattery posts page after ordering the services */
function navigatetoBookBattery(email,mobilenumber,pwd,username,city,state)
{
	var serverURL = document.servicedet.serverURL.value;
	$('#divmobile').hide();
	greyout(false);
	window.open('http://'+serverURL+'/bookbattery/servlet/UserLoginServlet?hidWhatToDo=loginwithanotherserver&email='+email+'&mobilenumber='+mobilenumber+'&password='+pwd+'&username='+username+'&city='+city+'&state='+state+'&deviceflag=web','_blank');
}
</script>
