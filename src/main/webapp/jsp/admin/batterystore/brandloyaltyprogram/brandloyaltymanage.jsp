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

Vector alist=(Vector)session.getAttribute("sesLoyaltyVector");
String pagenumber = (request.getParameter("pagenumber")!=null)?(request.getParameter("pagenumber")):"0";
LogLevel.DEBUG(5,new Throwable(),"pagenumber :"+pagenumber );

String strloyaltyid = (request.getParameter("loyaltyid")!=null)?(request.getParameter("loyaltyid")):"0";
LogLevel.DEBUG(5,new Throwable(),"strloyaltyid :"+strloyaltyid );

String strloyaltyname = (request.getParameter("loyaltyname")!=null)?(request.getParameter("loyaltyname")):"0";
LogLevel.DEBUG(5,new Throwable(),"strloyaltyname :"+strloyaltyname );

String strpoints = (request.getParameter("points")!=null)?(request.getParameter("points")):"0";
LogLevel.DEBUG(5,new Throwable(),"strpoints :"+strpoints );

String strvalue = (request.getParameter("value")!=null)?(request.getParameter("value")):"0";
LogLevel.DEBUG(5,new Throwable(),"strvalue :"+strvalue );

Vector alist1=(Vector)session.getAttribute("sesLoyaltyCount");
LogLevel.DEBUG(5,new Throwable(),"alist1 :"+alist1 );
session.removeAttribute("sesVector2");

%>
<!-- end of scriplet -->
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<link href="../../../../css/bookbattery.css" rel="stylesheet" type="text/css" />
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<link rel="shortcut icon" href="../../../../images/favicon.png" type="image/x-icon">
<title>BookBattery.com - India's No.1 Automobile Battery Store</title>
</head>
<!-- body content starts here -->
<body onload="displayleftmenu();" onkeydown="if($('#scrollpopup').is(':visible')){ if(event.keyCode==9){ return false;}};">
<center>
<form name="fetchprogram" method="post" >
<div id='scrollpopup' class='scrollpopup' style="display:none;"></div>
<table cellpadding="0" cellspacing="0"  width="960">
	<tr>
		<td>
			<!-- top page starts here  -->
			<jsp:include page = "../header.jsp" />
			<!-- top page ends here  -->
		</td>
	</tr>
	<tr>
		<td align="left" valign="top" bgcolor="#FFFFFF">
			<table width="960" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="180" height="438" align="left" valign="top">
						<table width="180" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td width="180" height="438" align="left" valign="top">
									<table width="100%" border="0" cellspacing="0" cellpadding="0">
										<tr><jsp:include page="../batteryadminleftmenu.jsp"/></tr>
									</table>
								</td>
								<td width="680" align="left" valign="top" >
									<table width="513" border="0" cellspacing="0" cellpadding="0">
										<!-- your page content starts here  -->
										<table width="770" border="0" cellpadding="0" cellspacing="0" bgcolor="#FFFFFF">
											<tr>
												<td>
													<table width="713" border="0" cellpadding="0" cellspacing="0" bgcolor="#FFFFFF">
														<tr>
															<td class="subheading"><font size="2">Loyalty Management Options>>Loyalty Management:: </td>
															<td bgcolor="#FFFFFF" align="right" ><a HREF="../../../../jsp/admin/batterystore/brandloyaltyprogram/brandloyaltyselection.jsp"class="bluelinks"><b>Back</b></a></td>
														</tr>
														<tr>
															<td>
																<table width="100%" cellspacing="1" bgcolor="#FFFFFF" cellpadding="0">
																	<tr>
																		<td colspan="8" align="right">
																			<table width="100%" >
																				<tr>
																					<td width="50%" align="right">
																						<%
																						try
																						{   
																						int k=0;
																						Hashtable ht2=(Hashtable)alist1.get(k);
																						String strCount=String.valueOf(ht2.get("count"));
																						int TotalCount=Integer.parseInt(strCount);
																						int Programs_per_page=10;
																						int tp=((int)(Math.ceil((double)TotalCount/Programs_per_page)));
																						String Lastpage=Integer.toString(tp);
																						if(pagenumber.equals("1"))
																						{
																						%>
																					<font class="blue2">First</font>&nbsp;<font class="blue2">Previous</font>&nbsp;
																						<%
																						}
																						else
																						{
																						%>	
																					<a class="blue1"	href="javascript:funOnClickFirst();">First</a>&nbsp;<a class="blue1" href="javascript:funOnClickPrevious();" >Previous</a> &nbsp;&nbsp;
																						<%
																						}
																						%>
																						<%
																						if(pagenumber.equals(Lastpage))
																						{
																						%>
																					&nbsp;<font class="blue2">Next</font>&nbsp;<font class="blue2">Last</font>&nbsp;
																						<%
																						}
																						else
																						{
																						%>
																					&nbsp;<a class="blue1" href="javascript:funOnClickNext();">Next</a>&nbsp;<a class="blue1" href="javascript:funOnClickLast('<%=Lastpage%>');">Last</a>&nbsp;
																						<%
																						}
																						%>
																					</td>
																					<td align="left"><font class="blue1">Showing page&nbsp; <select name="page" onChange="javascript:getpage()" class="pages">
																						<%
																						String strsel="";
																						for(int i=1;i<=tp;i++)
																						{
																						String si=Integer.toString(i);
																						if(pagenumber.equals(si))
																						{
																						strsel="selected";
																						}
																						else
																						{
																						strsel="";
																						}
																						%>
																					<option value="<%=i%>"<%=strsel%>><%=i%></option> 
																						<%
																						}
																						%>
																					<select>of&nbsp;&nbsp; <%if(Lastpage.equals("0"))
																						{
																						Lastpage="1";
																						}
																						else
																						{
																						Lastpage=Lastpage;
																						}
																						%>
																					<%=Lastpage%>
																					</font>
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
															<!--Pagination ends here-->
															<%
															if(alist==null || alist.size() == 0)
															{
															%>
															<tr>
															<td colspan="3" align="center"> There are no loyalty programs. Please click 'Add' to add a program.</td>
															</tr>	
															<tr bgcolor="#FFFFFF">
															<td colspan="3" align="center" bgcolor="#FFFFFF"> 
															<table width="15%" cellspacing="5"  cellpadding="5">
															<tr>
															<td align="center"><a href="../../../../jsp/admin/batterystore/brandloyaltyprogram/brandaddloyalty.jsp" class="smallbutton">Add</a></td>
															</tr>
															</table>	
															</td>
															</tr>
															<%
															}
															else
															{
															%>
														<tr bgcolor="#FFFFFF">
															<td colspan="3" align="center" bgcolor="#FFFFFF"> 
																<table width="30%" cellspacing="5" valign="center" cellpadding="5">
																	<tr>
																		<td align="center"><a href="../../../../jsp/admin/batterystore/brandloyaltyprogram/brandaddloyalty.jsp" class="smallbutton">Add</a></td>
																		<td align="center"><a href="javascript:modifyprogram()" class="smallbutton">Modify</a></td>
																		<td align="center"><a href="javascript:deleteprogram()" class="smallbutton">Delete</a></td>
																	</tr>
																</table>	
															</td>
														</tr>
														<tr>
															<td>
																<table  width="550" cellspacing="0"  valign="bottom" border="1" cellpadding="2" align="center" bgcolor="#CCCCCC">
																	<tr  bgcolor="#FFFFFF">
																		<td align="center" class="prodheading"><input type="checkbox" value="" name="check" style="display:none" >&nbsp;S.No</td>
																		<td align="center" class="prodheading">Loyalty Program</td>
																		<td align="center" class="prodheading">Loyalty points</td>
																		<td align="center" class="prodheading">Value(Rs)</td>
																		<td align="center" class="prodheading">Loyalty Partners to Redeem</td>
																	</tr>
																		<%
																		try
																		{
																		int w=1;
																		for ( int i=0; i<alist.size(); i++)
																		{
																		Hashtable ht=(Hashtable)alist.get(i);
																		LogLevel.DEBUG(5,new Throwable(),"ht :"+ht );
																		strloyaltyid=String.valueOf(ht.get("loyalty_id"));
																		LogLevel.DEBUG(5,new Throwable(),"strloyaltyid :"+strloyaltyid );
																		strloyaltyname=(String)ht.get("loyalty_name");
																		strloyaltyname = strloyaltyname.replaceAll("<","&lt;");
																		strloyaltyname = strloyaltyname.replaceAll(">","&gt;");
																		strpoints=String.valueOf(ht.get("loyalty_points"));
																		strvalue=String.valueOf(ht.get("value"));
																		String tdclass="table1";
																		int pagenum=Integer.parseInt(pagenumber);
																		LogLevel.DEBUG(5,new Throwable(),"pagenum :"+pagenum );
																		if (w==(w/2)*2)
																		{
																		LogLevel.DEBUG(5,new Throwable(),"w :"+w );
																		%>
																	<tr bgcolor="#BEADCB">
																		<td align="center" class="<%=tdclass%>"  align="center"><input type="checkbox" name="check" value="<%=strloyaltyid%>">&nbsp;<%=(10*(pagenum-1)+w)%></td>
																		<td  class="<%=tdclass%>"><%=strloyaltyname%></td>
																		<td align="center" class="<%=tdclass%>"><%=strpoints%></td>
																		<td align="center" class="<%=tdclass%>"><%=strvalue%></td>
																		<td align="center" class="<%=tdclass%>" ><a href="javascript:getAjaxPartner('<%=strloyaltyid%>')"> Click Here to view partners.. </a><div id=<%="divpartnername"+strloyaltyid%> style="display:none;"></div></td>
																	</tr>
																		<%
																		}
																		else
																		{
																		%>
																	<tr bgcolor="#DAD0E1">	
																		<td align="center" class="<%=tdclass%>"  align="center"><input type="checkbox" name="check" value="<%=strloyaltyid%>">&nbsp;<%=(10*(pagenum-1)+w)%></td>
																		<td  class="<%=tdclass%>"><%=strloyaltyname%></td>
																		<td align="center" class="<%=tdclass%>"><%=strpoints%></td><td align="center" class="<%=tdclass%>"><%=strvalue%></td>
																		<td align="center" class="<%=tdclass%>"><a href="javascript:getAjaxPartner('<%=strloyaltyid%>')"> Click Here to view partners..</a><div id=<%="divpartnername"+strloyaltyid%> style="display:none;"></div></td>
																	</tr>
																		<% 
																		}
																		w++;
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
															<%
															}
															%>
														<tr height="10">
															<td colspan="3">&nbsp;</td>
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
			<tr>
		<td>
			<!-- footer page starts here  -->
			<jsp:include page = "../footer.jsp" />
			<!-- top page ends here  -->
		</td>
	</tr>
		</td>
	</tr>
</table>
<input type="hidden" name="hidWhatToDo" value="deleteaccount">
<input type="hidden" name="sesusername" value="<%=strUserid%>">
<input type="hidden" name="pagenumber" value="<%=pagenumber%>">
<input type="hidden" name="loyaltyid" value="<%=strloyaltyid%>">
<input type="hidden" name="loyaltyname" value="<%=strloyaltyname%>">
<input type="hidden" name="points" value="<%=strpoints%>">
<input type="hidden" name="value" value="<%=strvalue%>">
</form>
</center>
</body>
<!-- body content ends here -->
<script type="text/javascript" src="../../../../js/pophide.js"></script>
<script type="text/javascript">
(function(){var po=document.createElement('script');po.type = 'text/javascript';po.async=true;
po.src = "../../../../js/pophide.js" ;
var s=document.getElementsByTagName('script')[0];s.parentNode.insertBefore(po,s);})();
</script>
<!-- javascript starts here -->
<script language="javascript">

// This function is used to delete a program.

function deleteprogram()
{
	var productid=0;
	for (i = 0; i<document.fetchprogram.check.length; i++)
	{
		if(document.fetchprogram.check[i].checked)
		{
			productid++;
		}
	}
	if (productid == 0)
	{
		errMsg ="<table border='0' width='400px' height='10px' cellpadding='0' cellspacing='0' bgcolor='#88689f'><tr class='top1'><td>&nbsp;<font color='#FFFFFF'>BookBattery</td><td align='right'><a href='javascript:closeScrollPopup(greyout(false));'><img src=\"../../../../images/Delete1.png \" border='0'></a></td></tr></table><br><table border='0' width='400px' height='10px'><tr class='pages'><td align='center'>Please select atleast one checkbox to delete.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='close_popup_modify();' class='button4'><br></td></tr><tr height='15'></tr></table>";
		document.getElementById("scrollpopup").style.display='block';
		document.getElementById("scrollpopup").innerHTML=errMsg
		document.getElementById("emailok").focus();
		greyout(true);
		return;
	}
	if(productid>=2)
	{
		errMsg ="<table border='0' width='400px' height='10px' cellpadding='0' cellspacing='0' bgcolor='#88689f'><tr class='top1'><td>&nbsp;<font color='#FFFFFF'>BookBattery</td><td align='right'><a href='javascript:closeScrollPopup(greyout(false));'><img src=\"../../../../images/Delete1.png \" border='0'></a></td></tr></table><br><table border='0' width='400px' height='10px'><tr class='pages'><td align='center'>Please select only one checkbox to delete.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='close_popup_modify();' class='button4'><br></td></tr><tr height='15'></tr></table>";
		document.getElementById("scrollpopup").style.display='block';
		document.getElementById("scrollpopup").innerHTML=errMsg
		document.getElementById("emailok").focus();
		greyout(true);
		return;
	}
	var selectedArray = new Array();
	count = 0;
	for(x=0; x<document.fetchprogram.check.length; x++)
	{
		if(document.fetchprogram.check[x].checked==true)
		{
			selectedArray[count] = document.fetchprogram.check[x].value;
			count++;
		}
	}
	var agree=confirm("Are you sure you wish to Delete?");
	if (agree)
	{
		document.fetchprogram.method="post";
		document.fetchprogram.action="../../../../servlet/BrandLoyalty?hidWhatToDo=deleteprogram";	
		document.fetchprogram.submit();
	}
}

// This function is used to modify a program.
function modifyprogram()
{
	var productid=0;
	for (i = 0; i<document.fetchprogram.check.length; i++)
		{
		if(document.fetchprogram.check[i].checked)
		{
		productid++;
		}
	}
	if(productid==0)
	{
		errMsg ="<table border='0' width='400px' height='10px' cellpadding='0' cellspacing='0' bgcolor='#88689f'><tr class='top1'><td>&nbsp;<font color='#FFFFFF'>BookBattery</td><td align='right'><a href='javascript:closeScrollPopup(greyout(false));'><img src=\"../../../../images/Delete1.png \" border='0'></a></td></tr></table><br><table border='0' width='400px' height='10px'><tr class='pages'><td align='center'>Please select atleast one checkbox to modify.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='close_popup_modify();' class='button4'><br></td></tr><tr height='15'></tr></table>";
		document.getElementById("scrollpopup").style.display='block';
		document.getElementById("scrollpopup").innerHTML=errMsg
		document.getElementById("emailok").focus();
		greyout(true);
		return;
	}
	if(productid>=2)
	{
		errMsg ="<table border='0' width='400px' height='10px' cellpadding='0' cellspacing='0' bgcolor='#88689f'><tr class='top1'><td>&nbsp;<font color='#FFFFFF'>BookBattery</td><td align='right'><a href='javascript:closeScrollPopup(greyout(false));'><img src=\"../../../../images/Delete1.png \" border='0'></a></td></tr></table><br><table border='0' width='400px' height='10px'><tr class='pages'><td align='center'>Please select only one checkbox to modify.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='close_popup_modify();' class='button4'><br></td></tr><tr height='15'></tr></table>";
		document.getElementById("scrollpopup").style.display='block';
		document.getElementById("scrollpopup").innerHTML=errMsg
		document.getElementById("emailok").focus();
		greyout(true);
		return;
	}
	document.fetchprogram.method="post";
	document.fetchprogram.action="../../../../servlet/BrandLoyalty?hidWhatToDo=modifyprogram";	
	document.fetchprogram.submit();
}

// This function is used to fetch  partners when user selects loyaltyname and loyaltyid.

function getAjaxPartner(varloyalid)
{
	var strUserid=document.fetchprogram.sesusername.value;
	if(strUserid==null)
	{
		errMsg ="<table border='0' width='400px' height='10px' cellpadding='0' cellspacing='0' bgcolor='#88689f'><tr class='top1'><td>&nbsp;<font color='#FFFFFF'>BookBattery</td><td align='right'><a href='javascript:closeScrollPopup(greyout(false));'><img src=\"../../../../images/Delete1.png \" border='0'></a></td></tr></table><br><table border='0' width='400px' height='10px'><tr class='pages'><td align='center'>Your Session has been expired, Please Login Again.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='close_popup_ses();' class='button4'><br></td></tr><tr height='15'></tr></table>";
		document.getElementById("scrollpopup").style.display='block';
		document.getElementById("scrollpopup").innerHTML=errMsg
		document.getElementById("emailok").focus();
		greyout(true);
		return;
	}
	else
	{
	}
	var paramName = "divpartnername"+varloyalid;
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
				document.getElementById("divpartnername"+varloyalid).innerHTML = resp;
				document.getElementById("divpartnername"+varloyalid).innerHTML = xmlhttp.responseText;
			}
		}			
	}
	xmlhttp.open("POST","../../../../servlet/BrandLoyalty?hidWhatToDo=getPartnerName&varloyalid="+varloyalid,true);		
	xmlhttp.send();	
}

// This functions is to hide popups 
function close_popup_modify()
{
	$('#scrollpopup').hide();
	greyout(false);
}

function closeScrollPopup()
{
	$('#scrollpopup').hide();
	greyout(false);
}

function close_popup_ses()
{
	$('#scrollpopup').hide();
	greyout(false);
	document.fetchprogram.action="../../../../jsp/admin/batterystore/batteryadminlogin.jsp";
	document.fetchprogram.submit();
}
// This function is used to show the loyaltyprogram names in paginations.
function funOnClickFirst()
{
	var pagenumber=document.fetchprogram.pagenumber.value;
	document.fetchprogram.method="post";
	document.fetchprogram.action="../../../../servlet/BrandLoyalty?hidWhatToDo=loyaltyManagement&pagenumber="+pagenumber+"&pagetype=first";
	document.fetchprogram.submit();
}

function funOnClickPrevious()
{
	var pagenumber=document.fetchprogram.pagenumber.value;
	document.fetchprogram.method="post";
	document.fetchprogram.action="../../../../servlet/BrandLoyalty?hidWhatToDo=loyaltyManagement&pagenumber="+pagenumber+"&pagetype=previous";			
	document.fetchprogram.submit();
}

function funOnClickNext()
{
	var pagenumber=document.fetchprogram.pagenumber.value;
	document.fetchprogram.method="post";
	document.fetchprogram.action="../../../../servlet/BrandLoyalty?hidWhatToDo=loyaltyManagement&pagenumber="+pagenumber+"&pagetype=next";
	document.fetchprogram.submit();
}

function funOnClickLast(lastpage)
{
	var pagenumber=document.fetchprogram.pagenumber.value;
	document.fetchprogram.method="post";
	document.fetchprogram.action="../../../../servlet/BrandLoyalty?hidWhatToDo=loyaltyManagement&pagenumber="+pagenumber+"&lastpage="+lastpage+"&pagetype=last";
	document.fetchprogram.submit();
}
function getpage()
{
	var pagenumber=document.fetchprogram.page.value;
	document.fetchprogram.method="post";
	document.fetchprogram.action="../../../../servlet/BrandLoyalty?hidWhatToDo=loyaltyManagement&pagenumber="+pagenumber;			
	document.fetchprogram.submit();
}
</script>
<!-- javascript ends here -->

</html>
