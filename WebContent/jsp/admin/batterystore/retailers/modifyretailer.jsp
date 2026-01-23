<%-- 
Author(s)			: Sai Krishna Daddala.
Created on			: March 18, 2015.
Copyright Notice	: BookBattery Pvt.Ltd. Confidential
Description			: Modify Retailers.
--%>
<%@page language="java" import="javax.servlet.ServletContext"%>
<%
ServletContext context = getServletContext();
String strUserid=(String)session.getAttribute("sesBatteryAdminName");
if(strUserid==null)
{
	strUserid="";
	session.setAttribute("sesErrorMsg","Session Timed Out. Please login again");
	response.sendRedirect("../../../../admin/index.html");
	return;
}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<link type="text/css" href="../../../../css/bookbattery.css" rel="stylesheet"/>
<head>
<meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
<script type="text/javascript" src="../../../../js/jquery-1.3.2.min.js"></script>
<script type="text/javascript" src="../../../../js/retailerreg.js"></script>
<link rel="shortcut icon" href="../../../../images/favicon.ico" type="image/x-icon">
<h1><title>BookBattery.com - India's No.1 Automobile Battery Store</title></h1>
<style>
.lighterShadeappend{font-family:Tahoma;font-size:11px;color:#000000;text-decoration:none}
</style>
</head>
<body onload="onloaderetailerhide(); hideMoreFields(); noBack();" onkeydown="if($('#popup').is(':visible')){ if(event.keyCode==9){ return false;}};">
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
															<td class="subheading" align="left"> <font size="2">Modify Retailer ::</td>
															<td  align="right"><a href="../../../../servlet/AdminBatteryLogin?hidWhatToDo=batteryadminhome" class="onclick1">Back&nbsp;&nbsp;</a></td>
														</tr>
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
																		:&nbsp;<select STYLE="width: 185px"  name = "city" id = "city" onchange="javascript:checkcityretname();"><option value="0">&lt;-----select city-----&gt</option></select></td>
																	</tr>
																	<tr bgcolor = "#FFFFFF" id="retstate">
																		<td class = "subheading" align="left">Retailer<font color = "#FF0000">*</font></td>
																		<td>:&nbsp;<select  STYLE="width: 185px" name = "retailer" id = "retailer" onchange = "getretailerdetails();" ><option value="0">&lt;-----select Retailer-----&gt</option></select></td>
																	</tr>
																	</table>
																	<table width="100%" border="0" cellspacing="0" bgcolor="#FFFFFF" cellpadding="5" >
																		<tr><td><div id="divgetretailer"></div></td></tr>
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
	po.src = '../../../../js/pophide.js';
	var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(po, s);
	})();
	(function() {
	var po = document.createElement('script'); po.type = 'text/javascript'; po.async = true;
	po.src = '../../../../js/jquery-1.3.1.min.js';
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
                     url: "../../../../servlet/RetailerLocationMap?hidWhatToDo=getretailers",
                    data: {state:state, city: $categorycity.val() },
                    success: function(data){
                        $("#retailer").html(data)
						document.addretailer.retailer.focus();
                    }
                });
			 }
            }
        );
	});
function getretailerdetails()
{
	var state = document.addretailer.State.value; 
	var city = document.addretailer.city.value;
	var retailer = document.addretailer.retailer.value; 

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
				//alert(resp);
				if(resp=="ERROR")
				{
					alert("Error occurred.Please try again.");
					return;
				}
				document.getElementById("divgetretailer").innerHTML = resp;
				document.getElementById("divgetretailer").innerHTML = xmlhttp.responseText;
			}
		}			
	}
	xmlhttp.open("POST","../../../../servlet/ModifyRetailer?hidWhatToDo=getretailerdetailstomodify&state="+state+"&city="+city+"&retailer="+retailer,true);		
	xmlhttp.send();	
}
function funToUpdateretailers(retid)
{
	var state = document.addretailer.State.value; 
	var city = document.addretailer.city.value;
	var retailerloginname = document.addretailer.retailerloginname.value; 
	var passwd=escape(document.addretailer.password.value);
	var password=passwd.replace(/\+/g, '%2B');
	var emailid = document.addretailer.emailid.value; 
	var emailidother = document.addretailer.emailidother.value;
	var phoneno=document.addretailer.landline.value;
	var landline=phoneno.replace(/\+/g, '%2B');
	var otherphonenumber=document.addretailer.landlineother.value;
	var landlineother=otherphonenumber.replace(/\+/g, '%2B');
	var mobilenumber11 = document.addretailer.mobilenumber.value; 
	var mobilenumber=mobilenumber11.replace(/\+/g, '%2B'); 
	var mobilenumberother11 = document.addretailer.mobilenumberother.value;
	var mobilenumberother=mobilenumberother11.replace(/\+/g, '%2B'); 
	var tollfreenumber = document.addretailer.tollfreenumber.value; 
	var address1 = document.addretailer.address1.value;
	var webaddress = document.addretailer.webaddress.value; 
	var area = document.addretailer.area.value;
	var zipcode = document.addretailer.zipcode.value; 
	var invoiceflag = document.addretailer.invoiceflag.value;

	var xmlhttp= "";
	var resp= "";
	var url="../../../../servlet/ModifyRetailer?hidWhatToDo=modifyretailers&state="+state+"&city="+city+"&retailerloginname="+retailerloginname+"&password="+password+"&emailid="+emailid+"&emailidother="+emailidother+"&landline="+landline+"&landlineother="+landlineother+"&mobilenumber="+mobilenumber+"&mobilenumberother="+mobilenumberother+"&tollfreenumber="+tollfreenumber+"&address1="+address1+"&webaddress="+webaddress+"&area="+area+"&zipcode="+zipcode+"&invoiceflag="+invoiceflag+"&retid="+retid;

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
					getretailerdetails()
				}
			}
		}			
	}
	xmlhttp.open("POST",url,true);		
	xmlhttp.send();	
}
function funToDeleteretailers(retid)
{
	var state = document.addretailer.State.value;
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
					getretailerdetails()
				}
			}
		}			
	}
	var agree=confirm("Are You sure want to delete Retailers details! ");
	if (agree)
	{
	xmlhttp.open("POST","../../../../servlet/ModifyRetailer?hidWhatToDo=deleteretailers&state="+state+"&retid="+retid,true);		
	xmlhttp.send();	
	}
}
</script>
</html>
