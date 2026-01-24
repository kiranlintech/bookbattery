<%-- Author Of The Document
Copyright Notice : NGIT Private Lmtd
Document   : Brand Loyalty
Created on : March 23, 2013, 7:22:20 PM
Author     : Kishore
--%>
<!-- importing all the java predefined classes-->
<%@page language="java" import="java.util.ArrayList,java.util.Hashtable,java.util.Vector,com.ngit.javabean.loglevel.*,java.util.Enumeration,javax.servlet.ServletContext"%>
<!-- start of scriplet-->
<%
String strUserid=(String)session.getAttribute("sesBatteryAdminName");
if(strUserid==null)
{
	strUserid="";
	session.setAttribute("sesErrorMsg","Session Timed Out. Please login again");
	response.sendRedirect("../../../../admin/index.html");
	return;
}
// getting attributes from session in to vector
String strLogMsg=(String)session.getAttribute("sesErrorMsg");
//checking if session is having any error msg or not
if(strLogMsg==null)
{
	strLogMsg="";
}
else
{
	session.removeAttribute("sesErrorMsg");
}
Vector alist=(Vector)session.getAttribute("sesLoyaltyPointsVector1");
Vector sesname=(Vector)session.getAttribute("sesloyalid");
String pagenumber = (request.getParameter("pagenumber")!=null)?(request.getParameter("pagenumber")):"0";
LogLevel.DEBUG(5,new Throwable(),"pagenumber :"+pagenumber );
String struserid = (request.getParameter("userid")!=null)?(request.getParameter("userid")):"0";
LogLevel.DEBUG(5,new Throwable(),"struserid :"+struserid );
String strloyaltyid = (request.getParameter("loyaltyid")!=null)?(request.getParameter("loyaltyid")):"0";
LogLevel.DEBUG(5,new Throwable(),"strloyaltyid :"+strloyaltyid );
String stremailid = (request.getParameter("emailid")!=null)?(request.getParameter("emailid")):"0";
LogLevel.DEBUG(5,new Throwable(),"stremailid :"+stremailid );
String strloyaltyname = (request.getParameter("loyaltyname")!=null)?(request.getParameter("loyaltyname")):"0";
LogLevel.DEBUG(5,new Throwable(),"strloyaltyname :"+strloyaltyname );
String strpoints = (request.getParameter("points")!=null)?(request.getParameter("points")):"0";
LogLevel.DEBUG(5,new Throwable(),"strpoints :"+strpoints );
String strvalue = (request.getParameter("value")!=null)?(request.getParameter("value")):"0";
LogLevel.DEBUG(5,new Throwable(),"strvalue :"+strvalue );
Vector alist1=(Vector)session.getAttribute("sesLoyaltyPointsCount1");
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
<!-- body content starts here-->
<body onload="onloadFunpr();" onkeydown="if($('#scrollpopup').is(':visible')){ if(event.keyCode==9){ return false;}};">
<center>
<form name="fetchuser" method="post" >
<!--added div for getting the pop up-->
	<div id='scrollpopup' class='scrollpopup' style="display:none;"></div>
<!--div ends here-->
<table width="960" border="0" align="center" cellpadding="0" cellspacing="0">
	<tr>
		<td align="left" valign="top">
			<table width="960" border="0" cellspacing="0" cellpadding="0">
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
																		<td class="subheading"><font size="2">Loyalty Management Options>>Loyalty User Management:: </td>
																		<td bgcolor="#FFFFFF" align="right" ><a HREF="../../../../jsp/admin/batterystore/brandloyaltyprogram/brandloyaltyselection.jsp"class="bluelinks"><b>Back</b></a></td>
																	</tr>
																	<tr>
																		<td>
																			<table width="100%" border="0" cellspacing="1" bgcolor="#FFFFFF" cellpadding="0">
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
																								<a class="blue1" href="javascript:funOnClickFirst();">First</a>&nbsp;<a class="blue1" href="javascript:funOnClickPrevious();" > Previous</a> &nbsp;&nbsp;
																									<%
																									}
																									if(pagenumber.equals(Lastpage))
																									{
																									%>
																								&nbsp;<font class="blue2">Next</font>&nbsp;<font class="blue2">Last</font>&nbsp;
																									<%
																									}
																									else
																									{
																									%>
																								&nbsp;<a class="blue1" href="javascript:funOnClickNext();">Next</a>&nbsp; <a class="blue1" href="javascript:funOnClickLast('<%=Lastpage%>');">Last</a>&nbsp;
																									<%
																									}
																									%>
																								</td>
																								<td  align="left"><font class="blue1">Showing page&nbsp; <select name="page" onChange="javascript:getpage()" class="pages">
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
																								<select>  of&nbsp;&nbsp; 
																									<%if(Lastpage.equals("0"))
																									{
																									Lastpage="1";
																									}
																									else
																									{
																									Lastpage=Lastpage;
																									}
																									%>
																								<%=Lastpage%></font>
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
																		<td colspan="3" align="center"> There are no users. Please click 'Add' to add a users.</td>
																	</tr>	
																	<tr bgcolor="#FFFFFF">
																		<td colspan="3" align="center" bgcolor="#FFFFFF"> 
																			<table width="15%" cellspacing="5"  cellpadding="5">
																				<tr>
																					<td align="center"><a href="javascript:addUser()" class="smallbutton">Add</a></td>
																				</tr>
																			</table>	
																		</td>
																	</tr>
																		<%
																		}
																		else
																		{
																		for(int k=0;k<sesname.size();k++)
																		{
																		Hashtable ht1=(Hashtable)sesname.get(k);
																		strloyaltyid=String.valueOf(ht1.get("loyalty_id"));
																		}
																		%>
																	<tr bgcolor="#FFFFFF">
																		<td colspan="3" align="center" bgcolor="#FFFFFF"> 
																			<table width="30%" cellspacing="5" valign="center" cellpadding="5">
																				<tr >
																					<td align="center"><a href="javascript:addUser()" class="smallbutton">Add</a></td>
																					<td align="center"><a href="javascript:modifyPoints()" class="smallbutton">Modify</a></td>
																					<td align="center"><a href="javascript:deleteUser('<%=strloyaltyid%>')" class="smallbutton">Delete</a></td>
																				</tr>
																			</table>	
																		</td>
																	</tr>
																	<tr>
																		<td>
																			<table  width="550" cellspacing="0"  valign="bottom" border="1" cellpadding="2" align="center" bgcolor="#CCCCCC">
																				<tr  bgcolor="#FFFFFF">
																					<td align="center" class="prodheading"><input type="checkbox" name="check" value="" style="display:none">&nbsp;S.No</td>
																					<td align="center" class="prodheading">User ID</td>
																					<td align="center" class="prodheading">Loyalty Program / Points</td>
																				</tr>
																					<%
																					try
																					{
																					int w=1;
																					for ( int i=0; i<alist.size(); i++)
																					{
																					Hashtable ht=(Hashtable)alist.get(i);
																					LogLevel.DEBUG(5,new Throwable(),"ht :"+ht );
																					stremailid=String.valueOf(ht.get("email_id"));
																					LogLevel.DEBUG(5,new Throwable(),"stremailid :"+stremailid );
																					String email = stremailid.replaceAll("[^a-zA-Z0-9]+","");
																					LogLevel.DEBUG(5,new Throwable(),"email :"+email );
																					String tdclass="table1";
																					int pagenum=Integer.parseInt(pagenumber);
																					LogLevel.DEBUG(5,new Throwable(),"pagenum :"+pagenum );
																					if (w==(w/2)*2)
																					{
																					LogLevel.DEBUG(5,new Throwable(),"w :"+w );
																					%>
																				<tr bgcolor="#BEADCB">
																					<td align="center" class="insidecontent"  align="center"><input type="checkbox" name="check" value="<%=stremailid%>">&nbsp;<%=(10*(pagenum-1)+w)%></td>
																					<td  class="<%=tdclass%>"><%=stremailid%></td>
																					<td align="center" class="<%=tdclass%>" ><a href="javascript:getAjaxPrograms('<%=stremailid%>')"> Loyalty Programs </a><div id=<%="divprograms"+email%> ></div></td>
																				</tr>
																					<%
																					}
																					else
																					{
																					%>
																				<tr bgcolor="#BEADCB">
																					<td align="center" class="insidecontent"  align="center"><input type="checkbox" name="check" value="<%=stremailid%>">&nbsp;<%=(10*(pagenum-1)+w)%></td>
																					<td  class="<%=tdclass%>"><%=stremailid%></td>
																					<td align="center" class="<%=tdclass%>" ><a href="javascript:getAjaxPrograms('<%=stremailid%>')"> Loyalty Programs </a><div id=<%="divprograms"+email%> ></div></td>
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
		</td>
	</tr>
</table>
<input type="hidden" name="hidWhatToDo" value="deleteaccount">
<input type="hidden" name="pagenumber" value="<%=pagenumber%>">
<input type="hidden" name="loyaltyid" value="<%=stremailid%>">
<input type="hidden" name="loyaltyname" value="<%=strloyaltyname%>">
<input type="hidden" name="points" value="<%=strpoints%>">
<input type="hidden" name="value" value="<%=strvalue%>">
</form>
</center>
</body>
<!-- body content ends here-->
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
<!--javascript starts here-->
<script language="javascript">

//This function is used to delete an user.
function deleteUser(loyaltyid)
{
	var productid=0;
	for (i = 0; i<document.fetchuser.check.length; i++)
	{
		if(document.fetchuser.check[i].checked)
		{
			productid++;
		}
	}
	if (productid == 0)
	{
		errMsg ="<table border='0' width='400px' height='10px' cellpadding='0' cellspacing='0' bgcolor='#88689f'><tr class='top1'><td>&nbsp;<font color='#FFFFFF'>BookBattery</td><td align='right'><a href='javascript:closeScrollPopup(greyout(false));'><img src=\"../../../../images/Delete1.png \" border='0'></a></td></tr></table><br><table border='0' width='400px' height='10px'><tr class='pages'><td align='center'>Please select atleast one checkbox to delete.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='closeScrollPopup();' class='button4'><br></td></tr><tr height='15'></tr></table>";
		document.getElementById("scrollpopup").style.display='block';
		document.getElementById("scrollpopup").innerHTML=errMsg
		document.getElementById("emailok").focus();
		greyout(true);
		return;
	}
	if(productid>=2)
	{
		errMsg ="<table border='0' width='400px' height='10px' cellpadding='0' cellspacing='0' bgcolor='#88689f'><tr class='top1'><td>&nbsp;<font color='#FFFFFF'>BookBattery</td><td align='right'><a href='javascript:closeScrollPopup(greyout(false));'><img src=\"../../../../images/Delete1.png \" border='0'></a></td></tr></table><br><table border='0' width='400px' height='10px'><tr class='pages'><td align='center'>Please select only one checkbox to delete.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='closeScrollPopup();' class='button4'><br></td></tr><tr height='15'></tr></table>";
		document.getElementById("scrollpopup").style.display='block';
		document.getElementById("scrollpopup").innerHTML=errMsg
		document.getElementById("emailok").focus();
		greyout(true);
		return;
	}
	var selectedArray = new Array();
	count = 0;
	for(x=0; x<document.fetchuser.check.length; x++)
	{
		if(document.fetchuser.check[x].checked==true)
		{
			selectedArray[count] = document.fetchuser.check[x].value;
			count++;	
		}
	}
	var agree=confirm("Are you sure you wish to Delete?");
	if (agree)
	{
		document.fetchuser.method = "post";
		document.fetchuser.action = "../../../../servlet/BrandLoyalty?hidWhatToDo=deleteuser&loyaltyid="+loyaltyid;
		document.fetchuser.submit();
	}
}

// This function is used to modify points of an user.
function modifyPoints()
{
	var productid=0;
	for (i = 0;i<document.fetchuser.check.length;i++)
	{
		if(document.fetchuser.check[i].checked)
		{
			productid++;
		}
	}
	if (productid == 0)
	{
		errMsg ="<table border='0' width='400px' height='10px' cellpadding='0' cellspacing='0' bgcolor='#88689f'><tr class='top1'><td>&nbsp;<font color='#FFFFFF'>BookBattery</td><td align='right'><a href='javascript:closeScrollPopup(greyout(false));'><img src=\"../../../../images/Delete1.png \" border='0'></a></td></tr></table><br><table border='0' width='400px' height='10px'><tr class='pages'><td align='center'>Please select atleast one checkbox to modify.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='closeScrollPopup();' class='button4'><br></td></tr><tr height='15'></tr></table>";
		document.getElementById("scrollpopup").style.display='block';
		document.getElementById("scrollpopup").innerHTML=errMsg
		document.getElementById("emailok").focus();
		greyout(true);
		return;
	}
	if(productid>=2)
	{
		errMsg ="<table border='0' width='400px' height='10px' cellpadding='0' cellspacing='0' bgcolor='#88689f'><tr class='top1'><td>&nbsp;<font color='#FFFFFF'>BookBattery</td><td align='right'><a href='javascript:closeScrollPopup(greyout(false));'><img src=\"../../../../images/Delete1.png \" border='0'></a></td></tr></table><br><table border='0' width='400px' height='10px'><tr class='pages'><td align='center'>Please select only one checkbox to modify.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='closeScrollPopup();' class='button4'><br></td></tr><tr height='15'></tr></table>";
		document.getElementById("scrollpopup").style.display='block';
		document.getElementById("scrollpopup").innerHTML=errMsg
		document.getElementById("emailok").focus();
		greyout(true);
		return;
	}
	document.fetchuser.method="post";
	document.fetchuser.action="../../../../servlet/BrandLoyalty?hidWhatToDo=modifypoints";	
	document.fetchuser.submit();
}

//This function is used to get Programs to the userid.

function getAjaxPrograms(userid)
{
	var strvendorName="Amaron";
	if(strvendorName==null)
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
	re = /\$|@|~|`|\%|\*|\^|\&|\(|\)|\+|\=|\[|\-|\_|\]|\[|\}|\{|\:|\'|\"|\<|\>|\||\\|\!|\$|\./g;
	var userid1=userid.replace(re, "");
	var paramName = "divprograms"+userid1;
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
				document.getElementById("divprograms"+userid1).innerHTML = resp;
				document.getElementById("divprograms"+userid1).innerHTML = xmlhttp.responseText;
			}
		}			
	}	
	xmlhttp.open("POST","../../../../servlet/BrandLoyalty?hidWhatToDo=getAjaxPrograms&emailid="+userid,true);		
	xmlhttp.send();	
}

//This function is used to  pagination.

function funOnClickFirst()
{
	var pagenumber=document.fetchuser.pagenumber.value;
	document.fetchuser.method="post";
	document.fetchuser.action="../../../../servlet/BrandLoyalty?hidWhatToDo=loyaltyManagement1&pagenumber="+pagenumber+"&pagetype=first";
	document.fetchuser.submit();
}

function funOnClickPrevious()
{
	var pagenumber=document.fetchuser.pagenumber.value;
	document.fetchuser.method="post";
	document.fetchuser.action="../../../../servlet/BrandLoyalty?hidWhatToDo=loyaltyManagement1&pagenumber="+pagenumber+"&pagetype=previous";			
	document.fetchuser.submit();
}

function funOnClickNext()
{
	var pagenumber=document.fetchuser.pagenumber.value;
	document.fetchuser.method="post";
	document.fetchuser.action="../../../../servlet/BrandLoyalty?hidWhatToDo=loyaltyManagement1&pagenumber="+pagenumber+"&pagetype=next";
	document.fetchuser.submit();
}

function funOnClickLast(lastpage)
{
	var pagenumber=document.fetchuser.pagenumber.value;
	document.fetchuser.method="post";
	document.fetchuser.action="../../../../servlet/BrandLoyalty?hidWhatToDo=loyaltyManagement1&pagenumber="+pagenumber+"&lastpage="+lastpage+"&pagetype=last";
	document.fetchuser.submit();
}

function getpage()
{
	var pagenumber=document.fetchuser.page.value;
	document.fetchuser.method="post";
	document.fetchuser.action="../../../../servlet/BrandLoyalty?hidWhatToDo=loyaltyManagement1&pagenumber="+pagenumber;			
	document.fetchuser.submit();
}	

// This functions is to hide popups of to modify  and to place cusrsors on the fields.
function closeScrollPopup()
{
	$('#scrollpopup').hide();
	greyout(false);
}
function close_popup_ses()
{
	$('#scrollpopup').hide();
	greyout(false);
	document.fetchuser.action="../../../../jsp/admin/batterystore/batteryadminlogin.jsp";
	document.fetchuser.submit();
}
function onloadFunpr()
{
	$(document).ready(function() 
	{
		var revVals=document.getElementsByTagName("*");
		for(i=0;i<revVals.length;i++)
		{
			var name=revVals[i].id;
			if(name.indexOf("divprograms")>=0)
			{
				$("#"+name).hide();
			}
		}
	});
}
// This function is used to add an User.
function addUser()
{
	document.fetchuser.method="post";
	document.fetchuser.action="../../../../servlet/BrandLoyalty?hidWhatToDo=getLoyaltyNames";			
	document.fetchuser.submit();
}
</script>
<!--javascript ends here-->
</html>
