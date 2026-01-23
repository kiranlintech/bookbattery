<%-- Author Of The Document
Copyright Notice : NGIT Private Lmtd
Document   : Brand Loyalty
Created on : March 23, 2013, 7:22:20 PM
Author     : Kishore
--%>
<%@page language="java" import="java.util.ArrayList,java.util.Hashtable,java.util.Vector,com.ngit.javabean.loglevel.*,java.util.Enumeration,javax.servlet.ServletContext"%>
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
Vector alist=(Vector)session.getAttribute("sesLoyalty1");
String pagenumber = (request.getParameter("pagenumber")!=null)?(request.getParameter("pagenumber")):"0";
LogLevel.DEBUG(5,new Throwable(),"pagenumber :"+pagenumber );
String allowedImgFormats =  "gif,jpg,jpeg,png,bmp,jpe,wbmp";

Vector partnerName=(Vector)session.getAttribute("sesAllPartnerVector1");
LogLevel.DEBUG(5,new Throwable(),"partnerName :"+partnerName );
session.removeAttribute("sesAllPartnerVector1");

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<link href="../../../../css/bookbattery.css" rel="stylesheet" type="text/css" />
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<link rel="shortcut icon" href="../../../../images/favicon.png" type="image/x-icon">
<title>BookBattery.com - India's No.1 Automobile Battery Store</title>
</head>
<!-- body content starts here -->

<body onload="onClickLoyaltyPartner(); limitText(document.modifyprogram.countdown.value,150);" onkeydown="if($('#scrollpopup').is(':visible')){ if(event.keyCode==9){ return false;}};">
<center>
 <form name="modifyprogram" method="post" ENCTYPE="multipart/form-data">
	<div id='scrollpopup' class='scrollpopup' style="display:none;"></div>
<table width="960" border="0" align="center" cellpadding="0" cellspacing="0">
 <tr>
    <td align="right" valign="top">
		<table width="960" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td>
					<!-- top page starts here  -->
						<jsp:include page = "../header.jsp" />
					<!-- top page ends here  -->
				</td>
			</tr>
			 <!-- Starts Menu category and subcategory.-->
			<tr>
				<td align="center" valign="top" bgcolor="#FFFFFF">
					<table width="960" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="180" height="438" align="center" valign="top">
								<table width="180" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="180" height="438" align="center" valign="top">
								<table width="100%" border="0" cellspacing="0" cellpadding="0">
									<tr>
										<jsp:include page="../batteryadminleftmenu.jsp"/>
									</tr>
		 <!-- Ends Menu Category and subcategory -->
								</table>
							</td>
							<td width="680" align="left" valign="top" >
								<table width="513" border="0" cellspacing="0" cellpadding="0">
									<!-- your page content starts here  -->
										<table width="770" border="0" cellpadding="5" cellspacing="0" bgcolor="#FFFFFF">
											<tr>
												<td>
													<table width="713" border="0" cellpadding="5" cellspacing="0" bgcolor="#FFFFFF">
														<tr>
															<td class="subheading">Loyalty Management Options>>Loyalty Management>>Modify Loyalty:: </td>
														</tr>
														<tr>
															<td>
																<table width="100%" cellspacing="1" bgcolor="#BEADCB" cellpadding="0">
																	<tr>
																		<td>
																			<table width="100%"  border="0" cellspacing="0" bgcolor="#FFFFFF" cellpadding="5">
																				<tr height="10">
																					<td colspan="5" align="center"><%=strLogMsg%></td>
																				</tr>
																				<%
																				try
																				{
																					int i=0;
																					Hashtable ht=(Hashtable)alist.get(i);
																					String strvendorid=String.valueOf(ht.get("vendor_id"));
																					String strloyaltyid=String.valueOf(ht.get("loyalty_id"));
																					String strloyaltyname=(String)ht.get("loyalty_name");
																					LogLevel.DEBUG(5,new Throwable(),"strloyaltyname :"+strloyaltyname );	
																					strloyaltyname = strloyaltyname.replaceAll("<","&lt;");
																					strloyaltyname = strloyaltyname.replaceAll(">","&gt;");
																					strloyaltyname = strloyaltyname.replaceAll("\"","&quot");
																					String strloyaltypoints=String.valueOf(ht.get("loyalty_points"));
																					String strdescription=(String)ht.get("description");
																					String striconname=String.valueOf(ht.get("icon_name"));
																					String stricon=String.valueOf(ht.get("icon"));
																					String strvalue=String.valueOf(ht.get("value"));
																					LogLevel.DEBUG(5,new Throwable(),"striconname :"+striconname );
																					String Url="";
																					if(striconname==null || striconname=="null")
																					{
																						Url="../../../../images/noimage.jpg";
																						
																					}
																					else
																					{
																						Url="../../../../userdata/loyalty/"+strloyaltyname+"/Thumb_"+striconname+"" ;
																					}
																					if(strloyaltypoints==null)
																					{
																						strloyaltypoints="";
																					}
																					else
																					{
																						strloyaltypoints=strloyaltypoints;
																					}
																					if(strvalue==null)
																					{
																						strvalue="";
																					}
																					else
																					{
																						strvalue=strvalue;
																					}
																					if(strdescription==null)
																					{
																						strdescription="";
																					}
																					else
																					{
																						strdescription=strdescription;
																					}
																					if(stricon==null)
																					{
																						stricon="";
																					}
																					else
																					{
																						stricon=stricon;
																					}
																					%>
																				<tr  bgcolor="FFFFFF">
																					<td class="subheading" ></td>
																					<td colspan="3">
																					<input type="text" name="loyalty_id" value="<%=strloyaltyid%>" size="31"  class="noborder" readonly="readonly" style="display:none">
																					</td>
																				</tr>
																				<tr class="#FFFFFF">
																					<td class="subheading" width="160" >Loyalty Program Name</td>
																					<td class="details" width="500">:&nbsp;
																						<input type="text" name="loyaltyname"  value="<%=strloyaltyname%>" size="50" readonly="readonly" disabled='true'><font size="1">&nbsp;&nbsp;Readonly</font>
																					</td>
																				</tr>
																				<tr class="#FFFFFF">	
																					<td class="subheading" >No. of points<font color="FF0000">*</font></td>
																					<td >:&nbsp;
																						<input type="text" name="loyaltypoints" value="<%=strloyaltypoints%>" size="31" maxlength="4" >
																					</td>
																				</tr>
																				<tr class="#FFFFFF">
																					<td class="subheading">Value(Rs)<font color="FF0000">*</font></td>
																					<td class="details">:&nbsp;
																						<input type="text" name="rupeevalue" value="<%=strvalue%>" size="31" maxlength="4">
																					</td>
																				</tr>
																				<tr class="#FFFFFF" height="90" width="150">
																					<td class="subheading" >Image</td>
																					<td colspan="3" >&nbsp;&nbsp;&nbsp;<img src="<%=Url%>" height="90" width="150"  border="1"/></td>
																				</tr>
																				<tr class="#FFFFFF">
																					<td class="subheading">Image</td>
																					<td >:&nbsp;
																						<input  type="file" name="image" value="<%=stricon%>" size="31">
																					</td>
																				</tr>
																				<tr>
																				<td></td>
																				<td><font size="1">[Please check your image file name should not consists of spaces and special characters]</font>
																				</td>
																				</tr>
																				<tr class="#FFFFFF" bgcolor="FFFFFF">
																					<td class="subheading" width="147" >Program Description<font color="FF0000">*</font></td>
																					<td valign="center" width="525">:&nbsp;<textarea name = "desc" rows = "8" cols = "26" id="desc"  maxlength="150" style="overflow:hidden" onkeyup="limitText(document.modifyprogram.countdown.value,150);" 
																					onchange="limitText(document.modifyprogram.countdown.value,150);"><%=strdescription%></textarea>&nbsp;&nbsp;<font size="2"><input readonly="" type="text" name="countdown" size="2" value="150" disabled='true'>Characters Left </font></td>
																				</tr>		
																				<tr width="600" >
																					<td colspan="2" >
																						<table border="0" align="left">
																								<tr bgcolor="FFFFFF" height="310" >
																									<td  class="subheading" valign="center" width="150">Loyalty Program Partners:
																									</td>
																									<td valign="top" ><div id="divpartner"></div></td>
																								</tr>
																						</table>
																					</td>
																				 </tr>	
																				<tr bgcolor="FFFFFF">
																					<td class="subheading" colspan="4">
																				</td>
																				 </tr>
																				<%
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
															</td>
														</tr>
													</table>
												</td>
											</tr>
										</table>
									<!-- your page content ends here  -->
								</table>
							</td>
						</tr>
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
<input type="hidden" name="hidWhatToDo" value="modify">
<input type="hidden" name="pagenumber" value="<%=pagenumber%>">
</form>
</center>
</body>
<!-- body content ends here -->
<script language="JavaScript" src="../../../../js/jquery-1.3.2.min.js" ></script>
<script type="text/javascript" src="../../../../js/pophide.js"></script>
<script type="text/javascript">
(function(){var po=document.createElement('script');po.type = 'text/javascript';po.async=true;
po.src = "../../../../js/jquery-1.3.2.min.js" ;
var s=document.getElementsByTagName('script')[0];s.parentNode.insertBefore(po,s);})();
</script>

<script type="text/javascript">
(function(){var po=document.createElement('script');po.type = 'text/javascript';po.async=true;
po.src = "../../../../js/pophide.js" ;
var s=document.getElementsByTagName('script')[0];s.parentNode.insertBefore(po,s);})();
</script>
<!-- javascript starts here -->

<script language="javascript">

//This function is used to  trim the loyalpoints.
function Trim(loyalpoints)
{
    while (loyalpoints.substring(0,1) == ' ') // check for white spaces from beginning
    {
        loyalpoints = loyalpoints.substring(1, loyalpoints.length);
    }
    while (loyalpoints.substring(loyalpoints.length-1, loyalpoints.length) == ' ') // check white space from end
    {
        loyalpoints = loyalpoints.substring(0,loyalpoints.length-1);
    }
     return loyalpoints;
}	
//This function is used to  trim the loyalvalue.
function Trim(loyalvalue)
{
    while (loyalvalue.substring(0,1) == ' ') // check for white spaces from beginning
    {
        loyalvalue = loyalvalue.substring(1, loyalvalue.length);
    }
    while (loyalvalue.substring(loyalvalue.length-1, loyalvalue.length) == ' ') // check white space from end
    {
        loyalvalue = loyalvalue.substring(0,loyalvalue.length-1);
    }
       return loyalvalue;
}

function limitText(limitCount,limitNum) 
{
	if (document.modifyprogram.desc.value.length > limitNum)
	{
		document.modifyprogram.desc.value= document.modifyprogram.desc.value.substring(0,limitNum);
		document.modifyprogram.countdown.value=0;
	}
	else
	{
		document.modifyprogram.countdown.value = limitNum - document.modifyprogram.desc.value.length;
	}
}

// This function is used to update program when user selects loyaltyid.

function updateprogram(loyaltyid,selbrand)
{
	var loyalname=document.modifyprogram.loyaltyname.value;
	var loyalpoints=document.modifyprogram.loyaltypoints.value;
	var loyalvalue=document.modifyprogram.rupeevalue.value;
	var loyalpartner=document.modifyprogram.programpartner.value;
	var loyalid=document.modifyprogram.loyalty_id.value;
	var image=document.modifyprogram.image.value;

	var progdesc1=escape(document.modifyprogram.desc.value);
	var progdesc=progdesc1.replace(/\+/g, '%2B');
	
	var selectedArray = new Array();
	count = 0;
	for(x=0; x<document.modifyprogram.programpartner.length; x++)
	{
		if(document.modifyprogram.programpartner[x].checked==true)
		{
			selectedArray[count]= document.modifyprogram.programpartner[x].value;
			count++;
		}
	}					

	var points=Trim(loyalpoints);
	if(points == "")
	{
		errMsg ="<table border='0' width='400px' height='10px' cellpadding='0' cellspacing='0' bgcolor='#88689f'><tr class='top1'><td>&nbsp;<font color='#FFFFFF'>BookBattery</td><td align='right'><a href='javascript:closeScrollPopup(greyout(false));'><img src=\"../../../../images/Delete1.png \" border='0'></a></td></tr></table><br><table border='0' width='400px' height='10px'><tr class='pages'><td align='center'>Please enter loyalty points.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='close_popup_points();' class='button4'><br></td></tr><tr height='15'></tr></table>";
		document.getElementById("scrollpopup").style.display='block';
		document.getElementById("scrollpopup").innerHTML=errMsg
		document.getElementById("emailok").focus();
		greyout(true);
		return;
	}
	if(points == 0)
	{
		errMsg ="<table border='0' width='400px' height='10px' cellpadding='0' cellspacing='0' bgcolor='#88689f'><tr class='top1'><td>&nbsp;<font color='#FFFFFF'>BookBattery</td><td align='right'><a href='javascript:closeScrollPopup(greyout(false));'><img src=\"../../../../images/Delete1.png \" border='0'></a></td></tr></table><br><table border='0' width='400px' height='10px'><tr class='pages'><td align='center'>Please enter loyalty points greater than zero.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='close_popup_points();' class='button4'><br></td></tr><tr height='15'></tr></table>";
		document.getElementById("scrollpopup").style.display='block';
		document.getElementById("scrollpopup").innerHTML=errMsg
		document.getElementById("emailok").focus();
		greyout(true);
		return;
	}
	if (points != "")
	{
		var checkOK = "0123456789";
		var checkStr = points;
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
			errMsg ="<table border='0' width='400px' height='10px' cellpadding='0' cellspacing='0' bgcolor='#88689f'><tr class='top1'><td>&nbsp;<font color='#FFFFFF'>BookBattery</td><td align='right'><a href='javascript:closeScrollPopup(greyout(false));'><img src=\"../../../../images/Delete1.png \" border='0'></a></td></tr></table><br><table border='0' width='400px' height='10px'><tr class='pages'><td align='center'>Please enter only digits in the Loyalty Points Field.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='close_popup_points();' class='button4'><br></td></tr><tr height='15'></tr></table>";
			document.getElementById("scrollpopup").style.display='block';
			document.getElementById("scrollpopup").innerHTML=errMsg
			document.getElementById("emailok").focus();
			greyout(true);
			return;
			document.modifyprogram.loyalpoints.value="";
			document.modifyprogram.loyalpoints.focus();
			return;
		}
	}
	var value=Trim(loyalvalue);
	if(value == "")
	{
		errMsg ="<table border='0' width='400px' height='10px' cellpadding='0' cellspacing='0' bgcolor='#88689f'><tr class='top1'><td>&nbsp;<font color='#FFFFFF'>BookBattery</td><td align='right'><a href='javascript:closeScrollPopup(greyout(false));'><img src=\"../../../../images/Delete1.png \" border='0'></a></td></tr></table><br><table border='0' width='400px' height='10px'><tr class='pages'><td align='center'>Please enter value.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='close_popup_value();' class='button4'><br></td></tr><tr height='15'></tr></table>";
		document.getElementById("scrollpopup").style.display='block';
		document.getElementById("scrollpopup").innerHTML=errMsg
		document.getElementById("emailok").focus();
		greyout(true);
		return;
	}
	if(value == 0)
	{
		errMsg ="<table border='0' width='400px' height='10px' cellpadding='0' cellspacing='0' bgcolor='#88689f'><tr class='top1'><td>&nbsp;<font color='#FFFFFF'>BookBattery</td><td align='right'><a href='javascript:closeScrollPopup(greyout(false));'><img src=\"../../../../images/Delete1.png \" border='0'></a></td></tr></table><br><table border='0' width='400px' height='10px'><tr class='pages'><td align='center'>Please enter value greater than zero.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='close_popup_value();' class='button4'><br></td></tr><tr height='15'></tr></table>";
		document.getElementById("scrollpopup").style.display='block';
		document.getElementById("scrollpopup").innerHTML=errMsg
		document.getElementById("emailok").focus();
		greyout(true);
		return;
	}
	{
		var checkOK = "0123456789";
		var checkStr = value;
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
			errMsg ="<table border='0' width='400px' height='10px' cellpadding='0' cellspacing='0' bgcolor='#88689f'><tr class='top1'><td>&nbsp;<font color='#FFFFFF'>BookBattery</td><td align='right'><a href='javascript:closeScrollPopup(greyout(false));'><img src=\"../../../../images/Delete1.png \" border='0'></a></td></tr></table><br><table border='0' width='400px' height='10px'><tr class='pages'><td align='center'>Please enter only digits in the Value Field.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='close_popup_value();' class='button4'><br></td></tr><tr height='15'></tr></table>";
			document.getElementById("scrollpopup").style.display='block';
			document.getElementById("scrollpopup").innerHTML=errMsg
			document.getElementById("emailok").focus();
			greyout(true);
			return;
		}
	}
	varAllowedFormats = '<%=allowedImgFormats%>';
	var fileName = document.modifyprogram.image.value;
	if(fileName.length >0)
	{
		var varIndex = fileName.lastIndexOf(".");
		var varBolMatch = false;
		if(varIndex > 0)
		{
			var varFileExt  = fileName.substring(++varIndex);
			var varStringArr = varAllowedFormats.split(",");
			for(var i=0;i<varStringArr.length;i++)
			{
				if(varStringArr[i].toLowerCase() == varFileExt.toLowerCase())
				{
					varBolMatch = true;
				}
			}
			if(!varBolMatch)
			{ 
				errMsg ="<table border='0' width='400px' height='10px' cellpadding='0' cellspacing='0' bgcolor='#88689f'><tr class='top1'><td>&nbsp;<font color='#FFFFFF'>BookBattery</td><td align='right'><a href='javascript:closeScrollPopup(greyout(false));'><img src=\"../../../../images/Delete1.png \" border='0'></a></td></tr></table><br><table border='0' width='400px' height='10px'><tr class='pages'><td align='center'>You can attach "+varAllowedFormats+" only.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='closePopup_image();' class='button4'><br></td></tr><tr height='15'></tr></table>";
				document.getElementById("scrollpopup").style.display='block';
				document.getElementById("scrollpopup").innerHTML=errMsg
				document.getElementById("emailok").focus();
				greyout(true);
				return;
			}
		}
		var fileName = document.modifyprogram.image.value;
		var varimageindex = fileName.lastIndexOf("\\");
		if(varIndex > 0)
		{
			var varimagename  = fileName.substring(++varimageindex);
			var checkOK = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789.";
			var checkStr = varimagename;
			var allValid = true;
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
				errMsg ="<table border='0' width='400px' height='10px' cellpadding='0' cellspacing='0' bgcolor='#88689f'><tr class='top1'><td>&nbsp;<font color='#FFFFFF'>BookBattery</td><td align='right'><a href='javascript:closeScrollPopup(greyout(false));'><img src=\"../../../../images/Delete1.png \" border='0'></a></td></tr></table><br><table border='0' width='400px' height='10px'><tr class='pages'><td align='center'>Your image name should not have spaces or special characters</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='closePopup_image();' class='button4'><br></td></tr><tr height='15'></tr></table>";
				document.getElementById("scrollpopup").style.display='block';
				document.getElementById("scrollpopup").innerHTML=errMsg
				document.getElementById("emailok").focus();
				greyout(true);
				return;
			}
		}
		if(varimagename.length >50)
		{
			errMsg ="<table border='0' width='400px' height='10px' cellpadding='0' cellspacing='0' bgcolor='#88689f'><tr class='top1'><td>&nbsp;<font color='#FFFFFF'>BookBattery</td><td align='right'><a href='javascript:closeScrollPopup(greyout(false));'><img src=\"../../../../images/Delete1.png \" border='0'></a></td></tr></table><br><table border='0' width='400px' height='10px'><tr class='pages'><td align='center'>Your image name should not exceed 50 characters including file extension</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='closePopup_image();' class='button4'><br></td></tr><tr height='15'></tr></table>";
			document.getElementById("scrollpopup").style.display='block';
			document.getElementById("scrollpopup").innerHTML=errMsg
			document.getElementById("emailok").focus();
			greyout(true);
			return;
		}
		
	}
	var progdesc=Trim(progdesc);
	if(progdesc== "")
	{
		errMsg ="<table border='0' width='400px' height='10px' cellpadding='0' cellspacing='0' bgcolor='#88689f'><tr class='top1'><td>&nbsp;<font color='#FFFFFF'>BookBattery</td><td align='right'><a href='javascript:closeScrollPopup(greyout(false));'><img src=\"../../../../images/Delete1.png \" border='0'></a></td></tr></table><br><table border='0' width='400px' height='10px'><tr class='pages'><td align='center'>Please Enter Description.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='close_popup_desc();' class='button4'><br></td></tr><tr height='15'></tr></table>";
		document.getElementById("scrollpopup").style.display='block';
		document.getElementById("scrollpopup").innerHTML=errMsg
		document.getElementById("emailok").focus();
		greyout(true);
		return;
	}
	if (/[a-z][A-Z]{2}/i.test(document.modifyprogram.desc.value) != true)
	{
		errMsg ="<table border='0' width='400px' height='10px' cellpadding='0' cellspacing='0' bgcolor='#88689f'><tr class='top1'><td>&nbsp;<font color='#FFFFFF'>BookBattery</td><td align='right'><a href='javascript:closeScrollPopup(greyout(false));'><img src=\"../../../../images/Delete1.png \" border='0'></a></td></tr></table><br><table border='0' width='400px' height='10px'><tr class='pages'><td align='center'>Please enter atleast three Characters together In the \"Description\" Field.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='close_popup_desc();' class='button4'><br></td></tr><tr height='15'></tr></table>";
		document.getElementById("scrollpopup").style.display='block';
		document.getElementById("scrollpopup").innerHTML=errMsg
		document.getElementById("emailok").focus();
		greyout(true);
		return;
	}
	var url ="";
	if(selbrand!="" && selectedArray!="")
	{
		url="../../../../servlet/BrandLoyalty?hidWhatToDo=modify&loyaltyname="+loyalname+"&loyalpartner="+selbrand+","+selectedArray+"&loyaltypoints="+loyalpoints+"&loyaltyvalue="+loyalvalue+"&progdesc="+progdesc+"&image="+image+"&loyalty_id="+loyalid;	
	}
	else if(selbrand=="" && selectedArray!="")
	{
		url="../../../../servlet/BrandLoyalty?hidWhatToDo=modify&loyaltyname="+loyalname+"&loyalpartner="+selectedArray+"&loyaltypoints="+loyalpoints+"&loyaltyvalue="+loyalvalue+"&progdesc="+progdesc+"&image="+image+"&loyalty_id="+loyalid;
	}
	else if (selbrand!="" && selectedArray=="")
	{
		url="../../../../servlet/BrandLoyalty?hidWhatToDo=modify&loyaltyname="+loyalname+"&loyalpartner="+selbrand+"&loyaltypoints="+loyalpoints+"&loyaltyvalue="+loyalvalue+"&progdesc="+progdesc+"&image="+image+"&loyalty_id="+loyalid;	
	}
	else if(selbrand=="" && selectedArray=="")
	{
		url="../../../../servlet/BrandLoyalty?hidWhatToDo=modify&loyaltyname="+loyalname+"&loyalpartner="+selbrand+"&loyaltypoints="+loyalpoints+"&loyaltyvalue="+loyalvalue+"&progdesc="+progdesc+"&image="+image+"&loyalty_id="+loyalid;	
	}
	document.modifyprogram.method="post";
	document.modifyprogram.action=url;	
	document.modifyprogram.submit();
}
// This functions is to hide popup of loyaltyname and to place cusrsors on the loyaltyname field.
function close_popup_loyaltyname()
{
	$('#scrollpopup').hide();
	greyout(false);
	document.modifyprogram.loyaltyname.value="";
	document.modifyprogram.loyaltyname.focus();
}
// This functions is to hide popup of loyaltyname and to place cusrsors on the image field.
function closePopup_image()
{
	$('#scrollpopup').hide();
	greyout(false);
	document.modifyprogram.image.value="";
	document.modifyprogram.image.focus();
}
// This functions is to hide popup of loyaltyname and to place cusrsors on the loyaltyname field.
function close_popup_points()
{
	$('#scrollpopup').hide();
	greyout(false);
	document.modifyprogram.loyaltypoints.value="";
	document.modifyprogram.loyaltypoints.focus();
}
// This functions is to hide popups and to place cusrsors on the fields.
function close_popup_desc()
{
	$('#scrollpopup').hide();
	greyout(false);
	document.modifyprogram.desc.value="";
	document.modifyprogram.desc.focus();
	limitText(document.modifyprogram.countdown.value,150);
}
//This functions is to hide popup of loyaltyname and to place cusrsors on the loyaltyname field.
function close_popup_value()
{
	$('#scrollpopup').hide();
	greyout(false);
	document.modifyprogram.rupeevalue.value="";
	document.modifyprogram.rupeevalue.focus();
}
//This functions is to hide popups.
function closeScrollPopup()
{
	$('#scrollpopup').hide();
	greyout(false);
}

function funOnClickFirstbrands(pagenumber,selbrand,loyaltyid)
{	
	var selectedArray = new Array();
	count = 0;
	for(x=0; x<document.modifyprogram.programpartner.length; x++)
	{
		if(document.modifyprogram.programpartner[x].checked==true)
		{
				selectedArray[count]= document.modifyprogram.programpartner[x].value;
				count++;
		}
	}
	var url="";
	if(selbrand!="" && selectedArray!="")
	{
		url="../../../../servlet/BrandLoyalty?hidWhatToDo=getUpdatePartners&loyalty_id="+loyaltyid+"&pagetype=first&pagenumber="+pagenumber+"&selbrand="+selbrand+","+selectedArray+"&requestno="+(Math.random()*100);
	}
	else if(selbrand=="" && selectedArray!="")
	{
		url="../../../../servlet/BrandLoyalty?hidWhatToDo=getUpdatePartners&loyalty_id="+loyaltyid+"&pagetype=first&pagenumber="+pagenumber+"&selbrand="+selectedArray+"&requestno="+(Math.random()*100);
	}
	else if (selbrand!="" && selectedArray=="")
	{
		url="../../../../servlet/BrandLoyalty?hidWhatToDo=getUpdatePartners&loyalty_id="+loyaltyid+"&pagetype=first&pagenumber="+pagenumber+"&selbrand="+selbrand+"&requestno="+(Math.random()*100);
	}
	else if(selbrand=="" && selectedArray=="")
	{
		url="../../../../servlet/BrandLoyalty?hidWhatToDo=getUpdatePartners&loyalty_id="+loyaltyid+"&pagetype=first&pagenumber="+pagenumber+"&selbrand="+selbrand+"&requestno="+(Math.random()*100);
	}
	if (window.XMLHttpRequest)
	{// code for IE7+, Firefox, Chrome, Opera, Safari
		xmlhttp=new XMLHttpRequest();
	}
	else
	{// code for IE6, IE5
		xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
	}
	xmlhttp.onreadystatechange=function()
	{
		if(xmlhttp.readyState!=4)
		{
			document.getElementById("divpartner").innerHTML="<br><br><br><br><center><img src='../../../../images/loader.gif' align='center'><br><br><span class='style1'>Please wait! Searching Retailers...</span></center>";
		}
		if (xmlhttp.readyState==4 && xmlhttp.status==200)
		{
			document.getElementById("divpartner").innerHTML="";
			document.getElementById("divpartner").innerHTML=xmlhttp.responseText;
		}
	}
	xmlhttp.open("GET",url,true);
	xmlhttp.send();
}


function funOnClickPreviousbrands(pagenumber,selbrand,loyaltyid)
{
	var selectedArray = new Array();
	count = 0;
	for(x=0; x<document.modifyprogram.programpartner.length; x++)
	{
		if(document.modifyprogram.programpartner[x].checked==true)
		{
				selectedArray[count]= document.modifyprogram.programpartner[x].value;
				count++;
		}
	}
	var url="";
	if(selbrand!="" && selectedArray!="")
	{
		url="../../../../servlet/BrandLoyalty?hidWhatToDo=getUpdatePartners&loyalty_id="+loyaltyid+"&pagetype=previous&pagenumber="+pagenumber+"&selbrand="+selbrand+","+selectedArray+"&requestno="+(Math.random()*100);
	}
	else if(selbrand=="" && selectedArray!="")
	{
		url="../../../../servlet/BrandLoyalty?hidWhatToDo=getUpdatePartners&loyalty_id="+loyaltyid+"&pagetype=previous&pagenumber="+pagenumber+"&selbrand="+selectedArray+"&requestno="+(Math.random()*100);
	}
	else if (selbrand!="" && selectedArray=="")
	{
		url="../../../../servlet/BrandLoyalty?hidWhatToDo=getUpdatePartners&loyalty_id="+loyaltyid+"&pagetype=previous&pagenumber="+pagenumber+"&selbrand="+selbrand+"&requestno="+(Math.random()*100);
	}
	else if(selbrand=="" && selectedArray=="")
	{
		url="../../../../servlet/BrandLoyalty?hidWhatToDo=getUpdatePartners&loyalty_id="+loyaltyid+"&pagetype=previous&pagenumber="+pagenumber+"&selbrand="+selbrand+"&requestno="+(Math.random()*100);
	}
	if (window.XMLHttpRequest)
	{// code for IE7+, Firefox, Chrome, Opera, Safari
		xmlhttp=new XMLHttpRequest();
	}
	else
	{// code for IE6, IE5
		xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
	}
	xmlhttp.onreadystatechange=function()
	{
		if(xmlhttp.readyState!=4)
		{
			document.getElementById("divpartner").innerHTML="<br><br><br><br><center><img src='../../../../images/loader.gif' align='center'><br><br><span class='style1'>Please wait! Searching Retailers..</span></center>";
		}
		if (xmlhttp.readyState==4 && xmlhttp.status==200)
		{
			document.getElementById("divpartner").innerHTML="";
			document.getElementById("divpartner").innerHTML=xmlhttp.responseText;
		}
	}
	xmlhttp.open("GET",url,true);
	xmlhttp.send();
}

function funOnClickNextbrands(pagenumber,selbrand,loyaltyid)
{
	var selectedArray = new Array();
	count = 0;
	for(x=0; x<document.modifyprogram.programpartner.length; x++)
	{
		if(document.modifyprogram.programpartner[x].checked==true)
		{
			selectedArray[count]= document.modifyprogram.programpartner[x].value;
			count++;
		}
	}
	var url="";
	if(selbrand!="" && selectedArray!="")
	{
		url="../../../../servlet/BrandLoyalty?hidWhatToDo=getUpdatePartners&loyalty_id="+loyaltyid+"&pagetype=next&pagenumber="+pagenumber+"&selbrand="+selbrand+","+selectedArray+"&requestno="+(Math.random()*100);
	}
	else if(selbrand=="" && selectedArray!="")
	{
		url="../../../../servlet/BrandLoyalty?hidWhatToDo=getUpdatePartners&loyalty_id="+loyaltyid+"&pagetype=next&pagenumber="+pagenumber+"&selbrand="+selectedArray+"&requestno="+(Math.random()*100);
	}
	else if (selbrand!="" && selectedArray=="")
	{
		url="../../../../servlet/BrandLoyalty?hidWhatToDo=getUpdatePartners&loyalty_id="+loyaltyid+"&pagetype=next&pagenumber="+pagenumber+"&selbrand="+selbrand+"&requestno="+(Math.random()*100);
	}
	else if(selbrand=="" && selectedArray=="")
	{
		url="../../../../servlet/BrandLoyalty?hidWhatToDo=getUpdatePartners&loyalty_id="+loyaltyid+"&pagetype=next&pagenumber="+pagenumber+"&selbrand="+selbrand+"&requestno="+(Math.random()*100);
	}
	if (window.XMLHttpRequest)
	{// code for IE7+, Firefox, Chrome, Opera, Safari
		xmlhttp=new XMLHttpRequest();
	}
	else
	{// code for IE6, IE5
		xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
	}
	xmlhttp.onreadystatechange=function()
	{
		if(xmlhttp.readyState!=4)
		{
			document.getElementById("divpartner").innerHTML="<br><br><br><br><center><img src='../../../../images/loader.gif' align='center'><br><br><span class='style1'>Please wait! Fetching loyalty partners</span></center>";
		}
		if (xmlhttp.readyState==4 && xmlhttp.status==200)
		{
			document.getElementById("divpartner").innerHTML="";
			document.getElementById("divpartner").innerHTML=xmlhttp.responseText;
		}
	}
	xmlhttp.open("GET",url,true);
	xmlhttp.send();
}


function getpagebrands(selbrand,loyaltyid)
{
	var page = document.getElementById("page").value;
	
	var selectedArray = new Array();
	count = 0;
	for(x=0; x<document.modifyprogram.programpartner.length; x++)
	{
		if(document.modifyprogram.programpartner[x].checked==true)
		{
			selectedArray[count]= document.modifyprogram.programpartner[x].value;
			count++;
		}
	}
	var url="";
	if(selbrand!="" && selectedArray!="")
	{
		url="../../../../servlet/BrandLoyalty?hidWhatToDo=getUpdatePartners&loyalty_id="+loyaltyid+"&pagenumber="+page+"&selbrand="+selbrand+","+selectedArray+"&requestno="+(Math.random()*100);
	}
	else if(selbrand=="" && selectedArray!="")
	{
		url="../../../../servlet/BrandLoyalty?hidWhatToDo=getUpdatePartners&loyalty_id="+loyaltyid+"&pagenumber="+page+"&selbrand="+selectedArray+"&requestno="+(Math.random()*100);
	}
	else if (selbrand!="" && selectedArray=="")
	{
		url="../../../../servlet/BrandLoyalty?hidWhatToDo=getUpdatePartners&loyalty_id="+loyaltyid+"&pagenumber="+page+"&selbrand="+selbrand+"&requestno="+(Math.random()*100);
	}
	else if(selbrand=="" && selectedArray=="")
	{
		url="../../../../servlet/BrandLoyalty?hidWhatToDo=getUpdatePartners&loyalty_id="+loyaltyid+"&pagenumber="+page+"&selbrand="+selbrand+"&requestno="+(Math.random()*100);
	}
	if (window.XMLHttpRequest)
	{// code for IE7+, Firefox, Chrome, Opera, Safari
		xmlhttp=new XMLHttpRequest();
	}
	else
	{// code for IE6, IE5
		xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
	}
	xmlhttp.onreadystatechange=function()
	{
		if(xmlhttp.readyState!=4)
		{
			document.getElementById("divpartner").innerHTML="<br><br><br><br><center><img src='../../../../images/loader.gif' align='center'><br><br><span class='style1'>Please wait! Fetching loyalty partners...</span></center>";
		}
		if (xmlhttp.readyState==4 && xmlhttp.status==200)
		{
			document.getElementById("divpartner").innerHTML="";
			document.getElementById("divpartner").innerHTML=xmlhttp.responseText;
		}
	}
	xmlhttp.open("GET",url,true);
	xmlhttp.send();
}


function funOnClickLastbrands(Lastpage,pagenumber,selbrand,loyaltyid)
{
	var selectedArray = new Array();
	count = 0;
	for(x=0; x<document.modifyprogram.programpartner.length; x++)
	{
		if(document.modifyprogram.programpartner[x].checked==true)
		{
				selectedArray[count]= document.modifyprogram.programpartner[x].value;
				count++;
		}
	}
	var url="";
	if(selbrand!="" && selectedArray!="")
	{
		url="../../../../servlet/BrandLoyalty?hidWhatToDo=getUpdatePartners&loyalty_id="+loyaltyid+"&pagetype=last&pagenumber="+pagenumber+"&lastpage="+Lastpage+"&selbrand="+selbrand+","+selectedArray+"&requestno="+(Math.random()*100);
	}
	else if(selbrand=="" && selectedArray!="")
	{
	 	url="../../../../servlet/BrandLoyalty?hidWhatToDo=getUpdatePartners&loyalty_id="+loyaltyid+"&pagetype=last&pagenumber="+pagenumber+"&lastpage="+Lastpage+"&selbrand="+selectedArray+"&requestno="+(Math.random()*100);
	}
	else if (selbrand!="" && selectedArray=="")
	{
		url="../../../../servlet/BrandLoyalty?hidWhatToDo=getUpdatePartners&loyalty_id="+loyaltyid+"&pagetype=last&pagenumber="+pagenumber+"&lastpage="+Lastpage+"&selbrand="+selbrand+"&requestno="+(Math.random()*100);
	}
	else if(selbrand=="" && selectedArray=="")
	{
		url="../../../../servlet/BrandLoyalty?hidWhatToDo=getUpdatePartners&loyalty_id="+loyaltyid+"&pagetype=last&pagenumber="+pagenumber+"&lastpage="+Lastpage+"&selbrand="+selbrand+"&requestno="+(Math.random()*100);
	}
	if (window.XMLHttpRequest)
	{// code for IE7+, Firefox, Chrome, Opera, Safari
		xmlhttp=new XMLHttpRequest();
	}
	else
	{// code for IE6, IE5
		xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
	}
	xmlhttp.onreadystatechange=function()
	{
		if(xmlhttp.readyState!=4)
		{
			document.getElementById("divpartner").innerHTML="<br><br><br><br><center><img src='../../../../images/loader.gif' align='center'><br><br><span class='style1'>Please wait! Fetching loyalty partners....</span></center>";
		}
		if (xmlhttp.readyState==4 && xmlhttp.status==200)
		{
			document.getElementById("divpartner").innerHTML="";
			document.getElementById("divpartner").innerHTML=xmlhttp.responseText;
		}
	}
	xmlhttp.open("GET",url,true);
	xmlhttp.send();
}

// This function is used to show the UpdatePartners&loyalty_id when click on LoyaltyPartner.

function onClickLoyaltyPartner(loyaltyid)
{
	var loyaltyid=document.modifyprogram.loyalty_id.value;
	var paramName = "divpartner";
	if($("#"+paramName).is(':visible'))
	{
		$("#"+paramName).hide();
	}
	else
	{
		$("#"+paramName).show();
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
				document.getElementById("divpartner").innerHTML = resp;
				document.getElementById("divpartner").innerHTML = xmlhttp.responseText;
			}
		}			
	}
	xmlhttp.open("POST","../../../../servlet/BrandLoyalty?hidWhatToDo=getUpdatePartners&loyalty_id="+loyaltyid,true);		
	xmlhttp.send();
}
</script>
</html>
