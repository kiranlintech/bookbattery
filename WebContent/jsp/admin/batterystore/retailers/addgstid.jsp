<%-- 
Author(s)			: Bharath Kumar.
Created on			: November 01, 2017.
Copyright Notice	: BookBattery Pvt.Ltd. Confidential
Description			: Add GSTID Retailers.
--%>
<%@page language="java" import="javax.servlet.ServletContext"%>
<%
ServletContext context = getServletContext();
String strUserid=(String)session.getAttribute("sesBatteryAdminName");
if(strUserid==null)
{
	strUserid="";
	session.setAttribute("sesErrorMsg","Session Timed Out. Please login again");
	response.sendRedirect("/bookbattery/admin/index.html");
	return;
}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<link type="text/css" href="/bookbattery/css/bookbattery.css" rel="stylesheet"/>
<head>

<meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
<script language="JavaScript" src="/bookbattery/js/jquery-1.8.2.js" ></script>
<script type="text/javascript" src="/bookbattery/js/retailerreg.js"></script>
<link rel="shortcut icon" href="/bookbattery/images/favicon.ico" type="image/x-icon">
<h1><title>BookBattery.com-Online Battery Store</title></h1>
<style>
.lighterShadeappend{font-family:Tahoma;font-size:11px;color:#000000;text-decoration:none}
</style>
</head>
<body>
	<center>
		<form name="addretailer" id="thisform" method="post"  ENCTYPE="multipart/form-data">
			<div id='popup' class='popup' style="display:none;">
			<div id='popuptitle' class='popuptitle'>
			<table border='0' width='410px' height='10px' cellpadding='0' cellspacing='0'>
				<tr class='top1'>
				<td>&nbsp;<font color='#FFFFFF'>BookBattery</font></td><td align='right'><a href='javascript:closePopup(greyout(false));' 	                    		class="bluelinks333">x</a></td>
				</tr>
			</table>
			</div>
			<div id='popupmessage' class='popupmessage'></div> 
			</div>
			<table cellpadding="0" cellspacing="0" border="0" width="960">
				<tr>
					<td>
						<jsp:include page = "../header.jsp" />
					</td>
				</tr>
				<tr>
					<td align="left" valign="top" bgcolor="#FFFFFF">
						<table width="960" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="180" height="400" align="left" valign="top">
								<table width="180" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td width="180" height="438" align="left" valign="top">
										<table width="100%" border="0" cellspacing="0" cellpadding="0">
										<tr>
											<jsp:include page="../batteryadminleftmenu.jsp"/>
										</tr>
										</table>
									</td>
									<td width="680" align="left" valign="top">
										<table width="600" cellspacing="1" bgcolor="#BEADCB" cellpadding="0">
										<tr>
											<td>									
												<table width="100%" border="1" cellspacing="0" cellpadding="0">
												<tr>
													<td align="left" valign="top">								
														<table width="100%" border="0" cellspacing="0" bgcolor="#FFFFFF" cellpadding="5px">
														<tr>
															<td height="10" bgcolor="#FFFFFF"></td>
														</tr>
														<tr>
															<td class="subheading" align="left"> <font size="2">ADD GST ID ::
															</td>
															<td  align="right"><a href="/bookbattery/servlet/AdminBatteryLogin?hidWhatToDo=batteryadminhome" class="onclick1">Back&nbsp;&nbsp;</a></td>
														</tr>
														<tr><td id="displaygsterrormsg"></td></tr>
														<tr>
															<td>
															<table width="100%" cellspacing="1" border="0" bgcolor="#FFFFFF" cellpadding="0">
															<tr>
																<td>
																	<table width="100%" border="0" cellspacing="0" bgcolor="#FFFFFF" cellpadding="5" >
																	<tr height="10">
																		<td colspan="3" align="center"><div id="divsessionmsg"></div></td>
																	</tr>
																	<!--<tr class="#FFFFFF" bgcolor="FFFFFF">
																	<td class="subheading" align="left">eRetailer<font color="FF0000"></font></td><td>:&nbsp;<input type="checkbox" name="eretailer" id="eretailer" onclick="javascript:eretailercheck()" size="31"></td>
																	</tr>-->
																	<tr bgcolor = "#FFFFFF" id="retstate">
																		<td class = "subheading" align="left">State<font color = "#FF0000">*</font></td>
																		<td>:&nbsp;<select  STYLE="width: 185px" name = "State" id = "State" onchange = "retailersetcities();" onchange="javascript:searchKeyPress11(event);"><jsp:include page = "state.jsp" /></select></td>
																		<td class = "subheading" align="left">City<font color = "#FF0000">*</font>
																		:&nbsp;<select STYLE="width: 185px"  name = "city" id = "city" ><option value="0">&lt;-----select city-----&gt</option></select></td>
																	</tr>
																	<tr bgcolor = "#FFFFFF" id="retstate">
																		<td class = "subheading" align="left">Retailer<font color = "#FF0000">*</font></td>
																		<td>:&nbsp;<select  STYLE="width: 185px" name = "retailername" id = "retailername"><option value="0">&lt;-----select Retailer-----&gt</option></select></td>
																	</tr>
																	
																	</table>
																	<table>
																	<tr>
																	<td class="subheading" width="20%">Retailer GSTIN</td>
																	<td width="50%">:&nbsp;<input type="text" name="gstid" maxlength="16" size="31" id="gstid"></td><td class="insidecontent"></td>
																	</tr>
																	</table>
																	<table align="center" border="0" cellpadding="0" cellspacing="0">
																		<tr height="20" id="retsubmitbutton">
																		<td>&nbsp;&nbsp;&nbsp;<a href="javascript:funToaddgstid()" class="smallbutton">Submit</a>&nbsp;&nbsp;&nbsp;</td>
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
			<input type="hidden" name="hidWhatToDo" value="add">
			<!-- Footer Starts Here -->
			<tr>
				<td>
					<jsp:include page = "../footer.jsp" />
				</td>
			</tr>                           
			<!-- footer Ends Here -->
		</form>
	</center>
</body>
<script type="text/javascript">
	(function() {
	var po = document.createElement('script'); po.type = 'text/javascript'; po.async = true;
	po.src = '/bookbattery/js/pophide.js';
	var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(po, s);
	})();
	(function() {
	var po = document.createElement('script'); po.type = 'text/javascript'; po.async = true;
	po.src = '/bookbattery/js/jquery-1.3.1.min.js';
	var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(po, s);
	})();

$(document).ready(function(){
		$categorycity = $('#city');
        $categorycity.change(
            function() {
			var splitvalm =$categorycity.val();
			var state = document.addretailer.State.value;
			if(splitvalm == "default" || splitvalm == 0)
			 {
				/*for(i=document.addretailer.model.options.length-1;i>=1;i--)
				{
				document.addretailer.model.remove(i);
				}
				document.addretailer.batname.focus();*/
			 }
			 else
			 {
                $.ajax({
                    type: "GET",
                     url: "/bookbattery/servlet/RetailerLocationMap?hidWhatToDo=getretailers",
                    data: {state:state, city: $categorycity.val() },
                    success: function(data){
                        $("#retailername").html(data)
						document.addretailer.retailername.focus();
                    }
                });
			 }
            }
        );
	});

function funToaddgstid()
{
	//alert("inside");
	var state = document.addretailer.State.value; 
	var city = document.addretailer.city.value;
	var retailername = document.addretailer.retailername.value;
	var gstid = document.addretailer.gstid.value;
	
	//alert(retailername);
	//alert(22);
	if(state==0)
	{
		errMsg ="<font color='#ff3333'>Please select \'Retailer State\'.</font>";
		document.getElementById("displaygsterrormsg").innerHTML=errMsg;
		document.addretailer.State.focus();
		return ;
	}
	if(city==0||city=="<-----select city----->")
	{
		errMsg ="<font color='#ff3333'>Please select \'Retailer City\'.</font>";
		document.getElementById("displaygsterrormsg").innerHTML=errMsg;
		document.addretailer.city.focus();
		return ;
	}
	if(retailername==0 ||retailername=="default"||retailername=="defaultss")
	{
		errMsg ="<font color='#ff3333'>Please select \'Retailer Name\'.</font>";
		document.getElementById("displaygsterrormsg").innerHTML=errMsg;
		document.addretailer.retailername.focus();
		return ;
	}
	
	if(gstid=="")
	{
		errMsg ="<font color='#ff3333'>Please Enter \'Retailer GSTID\'.</font>";
		document.getElementById("displaygsterrormsg").innerHTML=errMsg;
		document.addretailer.gstid.focus();
		return ;
	}	
	if(gstid.length<12)
	{
		errMsg ="<font color='#ff3333'>Please Enter \'Valid Retailer GSTID\'.</font>";
		document.getElementById("displaygsterrormsg").innerHTML=errMsg;
		document.addretailer.gstid.focus();
		return ;
	}
	var gstidregex = "~!@#$^*()+=[]\\\';,./{}|\"<>?";
	for (var i = 0; i < document.addretailer.gstid.value.length; i++) 
	{
		if (gstidregex.indexOf(document.addretailer.gstid.value.charAt(i)) != -1)
		{
			errMsg ="<font color='#ff3333'>Please Enter \'Valid Retailer GSTID\'.</font>";
			document.getElementById("displaygsterrormsg").innerHTML=errMsg;
			document.addretailer.gstid.focus();
			return ;
		}
    }
	
	var xmlhttp= "";
	var resp= "";
	var url="/bookbattery/servlet/RetailerRegistration?hidWhatToDo=insertgstiddetails&state="+state+"&city="+city+"&retailername="+retailername+"&gstid="+gstid;
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
				if(resp=="ERROR")
				{
					alert("Error occurred.Please try again.");
					return;
				}
				if(resp.indexOf("Failed")>=0)
				{
					document.getElementById("divsessionmsg").innerHTML = resp;
					document.getElementById("divsessionmsg").innerHTML = xmlhttp.responseText;
				}
				else
				{
					document.getElementById("divsessionmsg").innerHTML = resp;
					document.getElementById("divsessionmsg").innerHTML = xmlhttp.responseText;
					clearfileds();
				}
			}
		}			
	}
	xmlhttp.open("POST",url,true);		
	xmlhttp.send();	
}

function clearfileds()
{
	//alert(22);
	document.getElementById("gstid").value = "";
	
}

</script>
</html>
