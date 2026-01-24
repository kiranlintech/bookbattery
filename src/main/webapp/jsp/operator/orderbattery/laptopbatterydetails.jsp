<%--
    Document   : operator laptopbattery details
    Created on : Feb 25, 2014 11:22:12 AM
    Author     : Sai Krishna Daddala.
--%>
<%@ page language="java" import="java.sql.*"%>
<%@page language="java" import="java.io.File,java.text.*,java.util.Calendar,java.util.ArrayList,java.util.Hashtable,java.util.Vector,com.ngit.javabean.loglevel.*,java.util.Date,java.util.Properties,java.util.*,com.ngit.javabean.loglevel.LogLevel,java.util.Properties,java.io.FileInputStream"%>
<%
String strUserid=(String)session.getAttribute("sesBatteryOperatorName");
if(strUserid==null)
{
	strUserid="";
	session.setAttribute("sesErrorMsg","Session Timed Out. Please login again");
	response.sendRedirect("../../../operator/index.html");
	return;
}

Vector alist=(Vector)session.getAttribute("LaptopBatteryDetailsVector");
Vector alist1=(Vector)session.getAttribute("LaptopBatteryDetailsVectorCount");

String batterytype = (request.getParameter("batterytype")!=null)?(request.getParameter("batterytype")):"0";
LogLevel.DEBUG(5,new Throwable(),"batterytype :"+batterytype ); 

String laptopmake = (request.getParameter("laptopmake")!=null)?(request.getParameter("laptopmake")):"0";
LogLevel.DEBUG(5,new Throwable(),"laptopmake :"+laptopmake );

String laptopmodel = (request.getParameter("laptopmodel")!=null)?(request.getParameter("laptopmodel")):"0";
LogLevel.DEBUG(5,new Throwable(),"laptopmodel :"+laptopmodel );

String laptopproduct = (request.getParameter("laptopproduct")!=null)?(request.getParameter("laptopproduct")):"0";
LogLevel.DEBUG(5,new Throwable(),"laptopproduct :"+laptopproduct );

String state = (request.getParameter("state")!=null)?(request.getParameter("state")):"0";
LogLevel.DEBUG(5,new Throwable(),"state :"+state );

String city = (request.getParameter("city")!=null)?(request.getParameter("city")):"0";
LogLevel.DEBUG(5,new Throwable(),"city :"+city );

String area = (request.getParameter("area")!=null)?(request.getParameter("area")):"0";
LogLevel.DEBUG(5,new Throwable(),"area :"+area );

String sortpricess = (request.getParameter("sortpricess")!=null)?(request.getParameter("sortpricess")):"0"; 
LogLevel.DEBUG(5,new Throwable(),"sortpricess :"+sortpricess );

String backlink = (request.getParameter("backlink")!=null)?(request.getParameter("backlink")):"0"; 
LogLevel.DEBUG(5,new Throwable(),"backlink :"+backlink );

String pagenumber = (request.getParameter("pagenumber")!=null)?(request.getParameter("pagenumber")):"0";
String Dummypagenumber = (request.getParameter("Dummypagenumber")!=null)?(request.getParameter("Dummypagenumber")):"0";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<link rel="shortcut icon" href="../../../images/favicon.png" type="image/x-icon">
<title>BookBattery.com-India's No.1 Automobile Battery Store</title>
<script language="JavaScript" src="../../../js/jquery-1.8.2.js" ></script>
<script src="../../../js/jquery-1.3.2.min.js"></script>
<script language="JavaScript" src="../../../js/pophide.js" ></script>
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
.divmobile{left:34.5%;top:1%;font: 16px/22px 'Trebuchet MS', Verdana, Arial; text-align:left; border:6px solid #424242; border-radius: 6px 6px 6px 6px;position:absolute;padding: 1px;display: none;z-index:60;}
-->
</style>
<link href="/../../../css/bookbattery.css" rel="stylesheet" type="text/css" />
</head>
<body onload="" >
<!-- Start Alexa Certify Javascript -->
<script type="text/javascript">
_atrk_opts = { atrk_acct:"VrG0j1a4ZP00yt", domain:"BookBattery.com",dynamic: true};
(function() { var as = document.createElement('script'); as.type = 'text/javascript'; as.async = true; as.src = "https://d31qbv1cthcecs.cloudfront.net/atrk.js"; var s = document.getElementsByTagName('script')[0];s.parentNode.insertBefore(as, s); })();
</script>
<noscript><img src="https://d5nxst8fruw4z.cloudfront.net/atrk.gif?account=VrG0j1a4ZP00yt" style="display:none" height="1" width="1" alt="" /></noscript>
<!-- End Alexa Certify Javascript -->
<form name="batterydet" method="post" ENCTYPE="multipart/form-data">
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
								<td class="subheadingbat3" align="left" size="4"><%=batterytype%> >> <%=laptopmake%>  >> <%=laptopmodel%>>> <%=laptopproduct%></td>
							</tr>
							<td  align="right"><a href="../../../jsp/operator/orderbattery/orderbattery.jsp" class="onclick1">Back&nbsp;&nbsp;</a></td>
							<tr><td height="10"></td></tr>
						</table>
						<table width="100%" border="0">
						<tr>
							<td width="40%" align="right">
							<%
								try
								{   
									int j=0;
									Hashtable ht2=(Hashtable)alist1.get(j);
									String strCount=String.valueOf(ht2.get("count"));
									int TotalCount=Integer.parseInt(strCount);
									int BookBattery_per_page=5;
									int tp=((int)(Math.ceil((double)TotalCount/BookBattery_per_page)));
									String Lastpage=Integer.toString(tp);
									if(pagenumber.equals("1"))
									{
										%>
										<font class="blue2">First</font>	
										&nbsp;&nbsp;
										<font class="blue2">Previous</font>&nbsp;&nbsp;
										<%
									}
									else
									{
										%>	
										<font color="#929ba1"  ><a class="blue1" href="javascript:funOnClickFirst();">First</a></font>&nbsp;&nbsp;
										<font color="#929ba1" > <a class="blue1" href="javascript:funOnClickPrevious();" > Previous</a> </font>&nbsp;&nbsp;
										<%
									}
									%>									
									<%
									if(pagenumber.equals(Lastpage))
									{
										%>
										&nbsp;&nbsp;<font class="blue2">Next</font>		
										&nbsp;&nbsp;
										<font class="blue2">Last</font>&nbsp;&nbsp;
										<%
									}
									else
									{
										%>
										&nbsp;&nbsp;<font color="#929ba1" ><a class="blue1" href="javascript:funOnClickNext();" > Next</a> </font>
										&nbsp;&nbsp; 
										<font color="#929ba1"><a class="blue1" href="javascript:funOnClickLast('<%=Lastpage%>');" >Last</a>&nbsp;&nbsp;</font>
										<%
									}%>
								</td>
								<td width="30%" align="right"><font class="blue1">Showing page&nbsp; <select name="page" onChange="javascript:getpage()" class="pages">
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
								<select>  of&nbsp;&nbsp; <%if(Lastpage.equals("0"))
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
																	
							<%
							}
							catch(Exception e)
							{
								e.printStackTrace();
							}
							%>
							<!--<td align="right" width="30%">
									<div class="styled-select">Sort by : <select  name='sortprice' id='sortprice' onChange="sortprices();" class="insidecontent" style='width:145px; height:30px;  bgcolor:#FFF;' >
									<option style="background:#FFF" value='asc' <%=(sortpricess.equals("asc") ?"selected":"")%>>Price : Low to High</option>
									<option style="background:#FFF" value="desc" <%=(sortpricess.equals("desc") ?"selected":"")%>>Price : High to Low</option>
								</select></div>
						 	</td>-->
						</tr>
						</table>
						<table width="100%" border="0" >
							<tr>
									<td colspan="6" align="right">
									<table  width="100%" cellspacing="1" align="center" cellpadding="2"  border="0" bgcolor="#FF8C00">
										<tr  bgcolor="#FFFFFF">
											<td align="center" width="40%"  bgcolor="#FFFFFF" class="prodheadingbat">Battery</td>
											<td align="center" width="40%" bgcolor="#FFFFFF" class="prodheadingbat">Specs</td>
										</tr>
											<%
											try{
												for ( int i=0; i<alist.size() ; i++)
												{

													Hashtable ht=(Hashtable)alist.get(i);
													String strbatterybrand=String.valueOf(ht.get("battery_brand"));
													String strBatmodel=(String)ht.get("battery_model");
													String strbatcellcount=String.valueOf(ht.get("battery_cellcount"));
													String strbatwarr=(String)ht.get("battery_warranty");
													String batterypartno=(String)ht.get("battery_part_no");
													String voltage=(String)ht.get("voltage");
													String strwatthr=(String)ht.get("watt_hr");
													String striconurl=(String)ht.get("icon_url");
													LogLevel.DEBUG(5, new Throwable(), "striconurl :" + striconurl);

													String amazonlink=(String)ht.get("amazonlink");
													String strdesc=(String)ht.get("description");
													String stractprice=String.valueOf(ht.get("battery_actual_price"));
													String tdclass="tablebat1";
													String bat_warrenty="";
													String mon="";
													String dat="";
													if(striconurl == null)
													{
														striconurl = "../../../images/noimage.jpg";
													}
													else
													{
														striconurl =striconurl;
													}
													if(strbatwarr.indexOf("+")!=-1)
													{
														String[] batwarr = strbatwarr.split("\\+"); 
														mon = batwarr[0];
														dat = batwarr[1];
														bat_warrenty = mon + "<br>" +dat;
													}
													else
													{
														bat_warrenty = strbatwarr;
													}	
											%>
											<tr>
											<td class="<%=tdclass%>" bgcolor="#FFFFFF" valign="top" align="center">
												<table width="85%" border="0" class="<%=tdclass%>" align="center" bgcolor="#FFFFFF">
													<tr>
														<td  class="<%=tdclass%>" bgcolor="#FFFFFF" align="center"><br><img align="center" src="<%=striconurl%>" ALT="battery" width="250" height="250"/></td>
														<br><br><b><%=strbatterybrand%></b><br><b><%=strBatmodel%></b>
													</tr>
												</table>
										
											</td>
											
											<td  class="<%=tdclass%>" bgcolor="#FFFFFF" valign="top" align="center">
											
												<table width="100%" border="0" class="<%=tdclass%>" bgcolor="#FFFFFF" align="center">
												<tr><td height="45"></td></tr>
													<tr>
														<td class="<%=tdclass%>" bgcolor="#FFFFFF" width="50%" align="right"><b><br>Price</b></td><td width="10%" align="center"><b><br>:</b></td>
														<td class="<%=tdclass%>" bgcolor="#FFFFFF" width="40%" align="left"><b><br>Rs. <%=stractprice%></b></td>
													</tr>
													<tr>
													<%
													if(strbatwarr.equals(""))
													{
													%>
														

													<%
													}
													
													else
													{
													%>
													<td class="<%=tdclass%>" bgcolor="#FFFFFF" width="50%" align="right"><b><br>Warranty</b></td><td width="10%" align="center"><b><br>:</b></td>
														<td class="<%=tdclass%>" bgcolor="#FFFFFF" width="40%" align="left"><b><br><%=strbatwarr%></b></td>
													<%
													}
													%>
													</tr>
													<tr>
														<td class="<%=tdclass%>" bgcolor="#FFFFFF" width="50%" align="right"><b><br>Cell Count</b></td><td width="10%" align="center"><b><br>:</b></td>
														<td class="<%=tdclass%>" bgcolor="#FFFFFF" width="40%" align="left"><b><br><%=strbatcellcount%></b></td>
													</tr>
													<tr>
														<td class="<%=tdclass%>" bgcolor="#FFFFFF" width="50%" align="right"><b><br>Voltage</b></td><td width="10%" align="center"><b><br>:</b></td>
														<td class="<%=tdclass%>" bgcolor="#FFFFFF" width="40%" align="left"><b><br><%=voltage%></b></td>
													</tr>
													<tr>
														<td class="<%=tdclass%>" bgcolor="#FFFFFF" width="50%" align="right"><b><br>Watt Hr</b></td><td width="10%" align="center"><b><br>:</b></td>
														<td class="<%=tdclass%>" bgcolor="#FFFFFF" width="40%" align="left"><b><br><%=strwatthr%></b></td>
													</tr>
													<tr>
													<%
													if(batterypartno.equals(""))
													{
													%>
														

													<%
													}
													
													else
													{
													%>
														<td class="<%=tdclass%>" bgcolor="#FFFFFF" width="50%" align="right"><b><br>Battery Part No</b></td><td width="10%" align="center"><b><br>:</b></td>
														<td class="<%=tdclass%>" bgcolor="#FFFFFF" width="40%" align="left"><b><br><%=batterypartno%></b></td>
														<%
													}
													%>
													</tr>
												</table>
											</td>
											</tr>
												<%
												}
												}catch(Exception e)
												{
														e.printStackTrace();
												}
												%>
											</table>

										</td>
									</tr>
								</td>
							</tr>
				</table>
				<%
				}
				else
				{
				%>
				<tr>
				<td  align="center" class="insidecontent">No Battery details found based on selection.!</td>
				</tr>
				<%
				}
				%>
				<table width="100%" border="0">
					<tr>
						<td width="40%" align="right">
							<%
								try
								{   
									int j=0;
									Hashtable ht2=(Hashtable)alist1.get(j);
									String strCount=String.valueOf(ht2.get("count"));
									int TotalCount=Integer.parseInt(strCount);
									int BookBattery_per_page=5;
									int tp=((int)(Math.ceil((double)TotalCount/BookBattery_per_page)));
									String Lastpage=Integer.toString(tp);
									if(pagenumber.equals("1"))
									{
										%>
										<font class="blue2">First</font>	
										&nbsp;&nbsp;
										<font class="blue2">Previous</font>&nbsp;&nbsp;
										<%
									}
									else
									{
										%>	
										<font color="#929ba1"  ><a class="blue1" href="javascript:funOnClickFirst();">First</a></font>&nbsp;&nbsp;
										<font color="#929ba1" > <a class="blue1" href="javascript:funOnClickPrevious();" > Previous</a> </font>&nbsp;&nbsp;
										<%
									}
									%>									
									<%
									if(pagenumber.equals(Lastpage))
									{
										%>
										&nbsp;&nbsp;<font class="blue2">Next</font>		
										&nbsp;&nbsp;
										<font class="blue2">Last</font>&nbsp;&nbsp;
										<%
									}
									else
									{
										%>
										&nbsp;&nbsp;<font color="#929ba1" ><a class="blue1" href="javascript:funOnClickNext();" > Next</a> </font>
										&nbsp;&nbsp; 
										<font color="#929ba1"><a class="blue1" href="javascript:funOnClickLast('<%=Lastpage%>');" >Last</a>&nbsp;&nbsp;</font>
										<%
									}%>
								</td>
								<td width="30%" align="right"><font class="blue1">Showing page&nbsp; <select name="page" onChange="javascript:getpage()" class="pages">
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
								<select>  of&nbsp;&nbsp; <%if(Lastpage.equals("0"))
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
																	
						<%
						}
						catch(Exception e)
						{
							e.printStackTrace();
						}
						%>
						
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
<input type="hidden" name="batterytype" value="<%=batterytype%>">
<input type="hidden" name="laptopmake" value="<%=laptopmake%>">
<input type="hidden" name="laptopmodel" value="<%=laptopmodel%>">
<input type="hidden" name="laptopproduct" value="<%=laptopproduct%>">
<input type="hidden" name="state" value="<%=state%>">
<input type="hidden" name="city" value="<%=city%>">
<input type="hidden" name="area" value="<%=area%>">
<input type="hidden" name="sortpricess" value="<%=sortpricess%>">
<input type="hidden" name="backlink" value="<%=backlink%>">
<input type="hidden" name="pagenumber" value="<%=pagenumber%>">
<input type="hidden" name="Dummypagenumber" value="<%=Dummypagenumber%>">
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