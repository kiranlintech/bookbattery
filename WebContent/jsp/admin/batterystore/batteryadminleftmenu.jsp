<script src="/bookbattery/js/jquery-1.3.2.min.js" type="text/javascript"></script>
<script language="JavaScript" src="/bookbattery/js/pophide.js" type="text/javascript"></script>
<script language="javascript">
function expand(s)
{
  var td = s;
  var d = td.getElementsByTagName("div").item(0);

  td.className = "menuHover";
  d.className = "menuHover";
}
function collapse(s)
{
  var td = s;
  var d = td.getElementsByTagName("div").item(0);

  td.className = "menuNormal";
  d.className = "menuNormal";
}
function validatepassword(val)
{
		errMsg ="<table cellspacing='10' cellpadding='0' bgcolor='#FFFFFF'><table width='350' bgcolor='#FFFFFF' valign='top'><tr height='30'><td align='right' valign='top' style='cursor:pointer;' bgcolor='#E6E6E6'><font color='red' size='3'><b><a href=\"javascript:closemobilediv(greyout(false));\" style='color:#848484;text-decoration:none;'>X&nbsp;&nbsp;</a></b>				</td></tr></table><tr><td><table width='350'  cellspacing='0' cellpadding='0' bgcolor='#FFFFFF'><tr><td width='100%' colspan='3'><table width='100%' cellspacing='0' cellpadding='0'><tr><td class='insidecontent' align='center'><font size='2' color='#FF8C00'>Please Provide Password to Access this Option!</font></td></tr></table></td><tr><td height='10'></td></tr></tr><tr><td width='100%' align='center'><input class='insidecontent' type='password' autocomplete='off' name='password' id='password'  style='width:195px;height:20px;border: 2px solid #CCC;font-size: 13px;font-family: Verdana;border-radius: 4px 4px 4px 4px;' maxlength='25' onkeydown=\"if ((event.which && event.which == 13) || (event.keyCode && event.keyCode == 13)){javascript:validateoption('"+val+"');return false;} else return true;\"/></td></tr><tr><td  width='50%' align='center' ><input type='button' class='BookBatterysubmit' name='Submitrret' value='Submit' disable='false' onclick=\"javascript:validateoption('"+val+"');\"></td></tr><tr height='26'><td colspan='3' align='center' class='subheading' id='displayrefinederrormsg1'></td></tr><tr><td height='15'></td></tr></table>";
	document.getElementById("divmobile").innerHTML="";
	document.getElementById("divmobile").style.display='block';
	document.getElementById("divmobile").innerHTML=errMsg
	greyout(true);	
	document.forms[0].password.focus();
}
function validateoption(val)
{
	var password = document.forms[0].password.value;

	if(password == "")
	{
		errMsg ="<font color='#9B5BDD'>Please Provide Password...!</font>";
		document.getElementById("displayrefinederrormsg1").innerHTML=errMsg;
		document.forms[0].password.focus();
		return;
	}
	var xmlhttp= "";
	var resp= "";
	var url="/bookbattery/servlet/AdminBatteryLogin?hidWhatToDo=checkpassword&password="+password;
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
			  if(resp.indexOf("Successfully")>=0)
				{
				  if(val == 1)
					{
					location.href="/bookbattery/servlet/BatteryPriceDetails?hidWhatToDo=getstatetoedit";
					}
				 else if(val == 2)
					{
					 location.href="/bookbattery/servlet/BatteryPriceDetails?hidWhatToDo=getstatetoedit&keyword=editobrp";
					}
				else if(val == 3)
					{
					 location.href="/bookbattery/servlet/AddBatteryDetails?hidWhatToDo=getbatterybrands&keyword=editbattery";
					}
				else if(val == 4)
					{
					 location.href="/bookbattery/servlet/AddBatteryDetails?hidWhatToDo=getbatterybrands&keyword=deletebattery";
					}
				else if(val == 5)
					{
					 location.href="/bookbattery/servlet/AddVehicleDetails?hidWhatToDo=getbatterytype";
					}
				else if(val == 6)
					{
					 location.href="/bookbattery/jsp/admin/batterystore/applicationchat/editapplicationchatmapping.jsp";
					}
				else if(val == 7)
					{
					 location.href="/bookbattery/jsp/admin/batterystore/retailerlocmap/retlocareaoptions.jsp";
					}
				else if(val == 8)
					{
					 location.href="/bookbattery/servlet/GenerateRetailerInvoice?hidWhatToDo=getcities&keyword=editpercent";
					}
				else if(val == 9)
					{
					 location.href="/bookbattery/servlet/GenerateRetailerInvoice?hidWhatToDo=getcities&keyword=editcuspercent";
					}
				else if(val == 10) 
					{
					 location.href="/bookbattery/jsp/admin/batterystore/locationarea/deletelocandarea.jsp";
					}
				else if(val == 11)
					{
					 location.href="/bookbattery/jsp/admin/batterystore/locationarea/deletepincode.jsp";
					}
				else if(val == 12)
					{
					 location.href="/bookbattery/servlet/InverterDetails?hidWhatToDo=editordeleteinveterdetails";
					}
				else if(val == 13)
					{
					 location.href="/bookbattery/servlet/InverterDetails?hidWhatToDo=getstatetoedit";
					}
				else if(val == 14)
					{									
												
						 location.href="/bookbattery/servlet/AddLaptopBatteryDetails?hidWhatToDo=getlaptopbatterybrands&keyword=deletebattery";
					}
					else if(val == 15)
					{
						 location.href="/bookbattery/servlet/AddLaptopDetails?hidWhatToDo=getlaptopbatterytype";
					}
					else if(val == 16)
					{
						 location.href="/bookbattery/jsp/admin/batterystore/laptopBookBattery/deletelaptopapplicationchart.jsp";
					}
				}
				else
				{
					document.getElementById("displayrefinederrormsg1").innerHTML=xmlhttp.responseText;
					document.getElementById("displayrefinederrormsg1").innerHTML=resp;
				}
		  }
	}
	xmlhttp.open("GET",url,true);
	xmlhttp.send();	
}
function closemobilediv()
{
	$('#divmobile').hide();
	greyout(false);
}
</script>
<style type='text/css'>
/*<![CDATA[*/
.divmobile{left:27.5%;top:55%;font: 16px/22px 'Trebuchet MS', Verdana, Arial; text-align:left; border:6px solid #424242; border-radius: 6px 6px 6px 6px;position:absolute;padding: 1px;display: none;z-index:60;}
/*]]>*/
</style>
<tr>
		<td align="center" valign="top">
			<table width="180" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td>
						<table width="180" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td width="20" align="left" valign="middle"><img src="/bookbattery/images/arrow-right.png" width="16" height="16" /></td>
								<td height="25" align="left" valign="middle" onmouseover="expand(this);" onmouseout="collapse(this);"><a href="/bookbattery/jsp/admin/batterystore/battery/addbattery.jsp" class="category">Battery&nbsp;Details</a>
								<div class="menuNormal" width="120">
										<table class="menu" width="80">
											<tr>
												<td class="menuNormal"><a href="/bookbattery/jsp/admin/batterystore/battery/addbattery.jsp" class="menuitem">Add&nbsp;Battery</a></td>
											</tr>
											<tr>
												<td class="menuNormal"><a href="javascript:validatepassword(3);" class="menuitem">Edit&nbsp;Battery</a></td>
											</tr>
											<tr>
												<td class="menuNormal"><a href="javascript:validatepassword(4);" class="menuitem">Delete&nbsp;Battery</a></td>
											</tr>
										</table>
									</div></td>
							</tr>
						</table>
					</td>
				</tr> 
				<tr><td height="1" bgcolor="#CCCCCC"></td></tr>
				<tr><td height="1" bgcolor="#FFFFFF"></td></tr>
					<tr>
					<td>
						<table width="180" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td width="20" align="left" valign="middle"><img src="/bookbattery/images/arrow-right.png" width="16" height="16" /></td>
								<td height="25" align="left" valign="middle" onmouseover="expand(this);" onmouseout="collapse(this);"><a href="/bookbattery/jsp/admin/batterystore/inverter/addinverter.jsp" class="category">Inverter&nbsp;Details</a>
								<div class="menuNormal" width="120">
										<table class="menu" width="80">
											<tr>
												<td class="menuNormal"><a href="/bookbattery/jsp/admin/batterystore/inverter/addinverter.jsp" class="menuitem">Add&nbsp;Inverter</a></td>
											</tr>
											<tr>
												<td class="menuNormal"><a href="javascript:validatepassword(12);" class="menuitem">Edit/Delete&nbsp;Inverter</a></td>
											</tr>
										</table>
									</div></td>
							</tr>
						</table>
					</td>
				</tr>
				<tr><td height="1" bgcolor="#CCCCCC"></td></tr>
				<tr><td height="1" bgcolor="#FFFFFF"></td></tr>
				<tr>
					<td>
						<table width="180" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td width="20" align="left" valign="middle"><img src="/bookbattery/images/arrow-right.png" width="16" height="16" /></td>
								<td height="25" align="left" valign="middle" onmouseover="expand(this);" onmouseout="collapse(this);"><a href="/bookbattery/servlet/AddBatteryDetails?hidWhatToDo=getbatterybrands" class="category">Battery&nbsp;Price</a><div class="menuNormal" width="120">
										<table class="menu" width="80">
											<tr>
												<td class="menuNormal"><a href="/bookbattery/servlet/AddBatteryDetails?hidWhatToDo=getbatterybrands" class="menuitem">Add&nbsp;Battery&nbsp;Price</a></td>
											</tr>
											<tr>
												<td class="menuNormal"><a href="javascript:validatepassword(1);" class="menuitem">Edit/Delete&nbsp;Battery&nbsp;Price</a></td>
											</tr>
											<!--<tr>
												<td class="menuNormal"><a href="javascript:validatepassword(2);" class="menuitem">Edit&nbsp;OBRP&nbsp;Battery&nbsp;Price</a></td>
											</tr>-->
										</table>
									</div></td>
							</tr>
						</table>
					</td>
				</tr>				
				<tr><td height="1" bgcolor="#CCCCCC"></td></tr>
				<tr><td height="1" bgcolor="#FFFFFF"></td></tr>
				<tr>
					<td>
						<table width="180" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td width="20" align="left" valign="middle"><img src="/bookbattery/images/arrow-right.png" width="16" height="16" /></td>
								<td height="25" align="left" valign="middle" onmouseover="expand(this);" onmouseout="collapse(this);"><a href="/bookbattery/servlet/AddBatteryDetails?hidWhatToDo=getbatterybrands" class="category">B2B&nbsp;Customers</a><div class="menuNormal" width="120">
										<table class="menu" width="80">
											<tr>
												<td class="menuNormal"><a href="/bookbattery/jsp/admin/batterystore/bbcustomers/addaccount.jsp" class="menuitem">Add&nbsp;Account</a></td>
											</tr>
											<tr>
												<td class="menuNormal"><a href="/bookbattery/jsp/admin/batterystore/bbcustomers/editdeleteaccount.jsp" class="menuitem">Edit/Delete&nbsp;Account</a></td>
											</tr>
											<!--<tr>
												<td class="menuNormal"><a href="javascript:validatepassword(2);" class="menuitem">Edit&nbsp;OBRP&nbsp;Battery&nbsp;Price</a></td>
											</tr>-->
										</table>
									</div></td>
							</tr>
						</table>
					</td>
				</tr>
				
				<tr><td height="1" bgcolor="#CCCCCC"></td></tr>
				<tr><td height="1" bgcolor="#FFFFFF"></td></tr>
				<tr>
					<td>
						<table width="180" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td width="20" align="left" valign="middle"><img src="/bookbattery/images/arrow-right.png" width="16" height="16" /></td>
								<td height="25" align="left" valign="middle" onmouseover="expand(this);" onmouseout="collapse(this);"><a href="/bookbattery/servlet/InverterDetails?hidWhatToDo=getinverterbrands" class="category">Inverter&nbsp;Price</a>
								<div class="menuNormal" width="120">
										<table class="menu" width="80">
											<tr>
												<td class="menuNormal"><a href="/bookbattery/servlet/InverterDetails?hidWhatToDo=getinverterbrands" class="menuitem">Add&nbsp;Price</a></td>
											</tr>
											<tr>
												<td class="menuNormal"><a href="javascript:validatepassword(13);" class="menuitem">Edit/Delete&nbsp;Price</a></td>
											</tr>
										</table>
									</div></td>
							</tr>
						</table>
					</td>
				</tr>
				<tr><td height="1" bgcolor="#CCCCCC"></td></tr>
				<tr><td height="1" bgcolor="#FFFFFF"></td></tr>
				
								<tr>
					<td>					
						<table width="180" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td width="20" align="left" valign="middle"><img src="/bookbattery/images/arrow-right.png" width="16" height="16" /></td>
								<td height="25" align="left" valign="middle" onmouseover="expand(this);" onmouseout="collapse(this);"><a href="/bookbattery/jsp/admin/batterystore/stock/batterystock.jsp" class="category">Update&nbsp;Stock</a><div class="menuNormal" width="120">
										<table class="menu" width="80">
											<tr>
												<td class="menuNormal"><a href="/bookbattery/jsp/admin/batterystore/stock/batterystock.jsp" class="menuitem">Battery&nbsp;Stock</a></td>
											</tr>
											<tr>
												<td class="menuNormal"><a href="/bookbattery/jsp/admin/batterystore/stock/inverterstock.jsp" class="menuitem">Inverter&nbsp;Stock</a></td>
											</tr>
										</table>
									</div></td>
							</tr>
						</table>
					</td>
				</tr>
				<tr><td height="1" bgcolor="#CCCCCC"></td></tr>
				<tr><td height="1" bgcolor="#FFFFFF"></td></tr>

				<tr>
					<td>
						<table width="180" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td width="20" align="left" valign="middle"><img src="/bookbattery/images/arrow-right.png" width="16" height="16" /></td>
								<td height="25" align="left" valign="middle" onmouseover="expand(this);" onmouseout="collapse(this);"><a href="/bookbattery/jsp/admin/batterystore/automobiles/addvehicles.jsp" class="category">Add&nbsp;Automobiles</a><div class="menuNormal" width="120">
										<table class="menu" width="80">
											<tr>
												<td class="menuNormal"><a href="/bookbattery/jsp/admin/batterystore/automobiles/addvehicles.jsp" class="menuitem">Add&nbsp;Automobiles</a></td>
											</tr>
											<tr>
												<td class="menuNormal"><a href="javascript:validatepassword(5);" class="menuitem">Delete&nbsp;Automobiles</a></td>
											</tr>
										</table>
									</div></td>
							</tr>
						</table>
					</td>
				</tr>
				<tr><td height="1" bgcolor="#CCCCCC"></td></tr>
				<tr><td height="1" bgcolor="#FFFFFF"></td></tr>
				<tr>
					<td>
						<table width="180" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td width="20" align="left" valign="middle"><img src="/bookbattery/images/arrow-right.png" width="16" height="16" /></td>
								<td height="25" align="left" valign="middle"  onmouseover="expand(this);" onmouseout="collapse(this);"><a href="/bookbattery/jsp/admin/batterystore/applicationchat/applicationchatmapping.jsp" class="category">Application&nbsp;Chart&nbsp;Mapping</a><div class="menuNormal" width="120">
										<table class="menu" width="80">
											<tr>
												<td class="menuNormal"><a href="/bookbattery/jsp/admin/batterystore/applicationchat/applicationchatmapping.jsp" class="menuitem">Add&nbsp;Application&nbsp;Chart</a></td>
											</tr>
											<tr>
												<td class="menuNormal"><a href="javascript:validatepassword(6);" class="menuitem">Delete&nbsp;Application&nbsp;Chart</a></td>
											</tr>
										</table>
									</div></td>
							</tr>
						</table>
					</td>
				</tr>
				<tr><td height="1" bgcolor="#CCCCCC"></td></tr>
				<tr><td height="1" bgcolor="#FFFFFF"></td></tr>
				<tr>
					<td>
						<table width="180" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td width="20" align="left" valign="middle"><img src="/bookbattery/images/arrow-right.png" width="16" height="16" /></td>
								<td height="25" align="left" valign="middle" onmouseover="expand(this);" onmouseout="collapse(this);"><a href="/bookbattery/servlet/RetailerLocationMap?hidWhatToDo=getstates" class="category">Retailer&nbsp;Location&nbsp;Mapping</a>
								<div class="menuNormal" width="120">
										<table class="menu" width="80">
											<tr>
												<td class="menuNormal"><a href="/bookbattery/servlet/RetailerLocationMap?hidWhatToDo=getstates" class="menuitem">Map&nbsp;with&nbsp;State&nbsp;and&nbsp;City</a></td>
											</tr>
											<tr>
												<td class="menuNormal"><a href="/bookbattery/servlet/RetailerLocationMap?hidWhatToDo=getstates&keyword=pin" class="menuitem">Map&nbsp;with&nbsp;Pincode</a></td>
											</tr>
											<tr>
												<td class="menuNormal"><a href="javascript:validatepassword(7);" class="menuitem">Delete&nbsp;Mapping&nbsp;Details</a></td>
											</tr>
										</table>
									</div>
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr><td height="1" bgcolor="#CCCCCC"></td></tr>
				<tr><td height="1" bgcolor="#FFFFFF"></td></tr>
				<tr>
					<td>
						<table width="180" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td width="20" align="left" valign="middle"><img src="/bookbattery/images/arrow-right.png" width="16" height="16" /></td>
								<td height="25" align="left" valign="middle" onmouseover="expand(this);" onmouseout="collapse(this);"><a href="/bookbattery/jsp/admin/batterystore/locationarea/locandarea.jsp" class="category">Add&nbsp;Locations&nbsp;and&nbsp;pincode</a><div class="menuNormal" width="120">
										<table class="menu" width="80">
											<tr>
												<td class="menuNormal"><a href="/bookbattery/jsp/admin/batterystore/locationarea/locandarea.jsp" class="menuitem">Add&nbsp;Locations&nbsp;and&nbsp;Area</a></td>
											</tr>
											<tr>
												<td class="menuNormal"><a href="/bookbattery/servlet/RetailerLocationMap?hidWhatToDo=getstates&keyword=area" class="menuitem">Add&nbsp;Pincode</a></td>
											</tr>
											<tr>
												<td class="menuNormal"><a href="javascript:validatepassword(10);" class="menuitem">Delete&nbsp;Locations&nbsp;and&nbsp;Area</a></td>
											</tr>
											<tr>
												<td class="menuNormal"><a href="javascript:validatepassword(11);" class="menuitem">Delete&nbsp;Pincode</a></td>
											</tr>
										</table>
									</div>
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr><td height="1" bgcolor="#CCCCCC"></td></tr>
				<tr><td height="1" bgcolor="#FFFFFF"></td></tr>
				<tr>
					<td>
						<table width="180" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td width="20" align="left" valign="middle"><img src="/bookbattery/images/arrow-right.png" width="16" height="16" /></td>
								<td height="25" align="left" valign="middle" onmouseover="expand(this);" onmouseout="collapse(this);"><a href="/bookbattery/jsp/admin/batterystore/retailers/addretailer.jsp" class="category">Retailers</a><div class="menuNormal" width="120">
										<table class="menu" width="80">
											<tr>
												<td class="menuNormal"><a href="/bookbattery/jsp/admin/batterystore/retailers/addretailer.jsp" class="menuitem">Add&nbsp;Retailer</a></td>
											</tr>
											<tr>
												<td class="menuNormal"><a href="/bookbattery/jsp/admin/batterystore/retailers/modifyretailer.jsp" class="menuitem">Edit/Delete&nbsp;Retailer</a></td>
											</tr>
										</table>
									</div>
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr><td height="1" bgcolor="#CCCCCC"></td></tr>
				<tr><td height="1" bgcolor="#FFFFFF"></td></tr>
				<tr>
					<td>
						<table width="180" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td width="20" align="left" valign="middle"><img src="/bookbattery/images/arrow-right.png" width="16" height="16" /></td>
								<td height="25" align="left" valign="middle" onmouseover="expand(this);" onmouseout="collapse(this);"><a href="/bookbattery/jsp/admin/servicesstore/serviceengineers/addretailer.jsp" class="category">Service Engineers</a><div class="menuNormal" width="120">
										<table class="menu" width="80">
											<tr>
												<td class="menuNormal"><a href="/bookbattery/jsp/admin/servicesstore/serviceengineers/addserviceengineer.jsp" class="menuitem">Add&nbsp;Service Engineer</a></td>
											</tr>
											<tr>
												<td class="menuNormal"><a href="/bookbattery/jsp/admin/servicesstore/serviceengineers/pincodemapping.jsp" class="menuitem">Add&nbsp;Pincode&nbsp;Mapping</a></td>
											</tr>
										</table>
									</div>
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr><td height="1" bgcolor="#CCCCCC"></td></tr>
				<tr><td height="1" bgcolor="#FFFFFF"></td></tr>
				<tr>
					<td>
						<table width="180" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td width="20" align="left" valign="middle"><img src="/bookbattery/images/arrow-right.png" width="16" height="16" /></td>
								<td height="25" align="left" valign="middle" onmouseover="expand(this);" onmouseout="collapse(this);"><a href="/bookbattery/jsp/admin/batterystore/retailers/addretailer.jsp" class="category">GST Details</a><div class="menuNormal" width="120">
										<table class="menu" width="80"> 
											<tr>
												<td class="menuNormal"><a href="/bookbattery/jsp/admin/batterystore/retailers/addgstid.jsp" class="menuitem">Add&nbsp;GSTID</a></td>
											</tr>
											<tr>
												<td class="menuNormal"><a href="/bookbattery/jsp/admin/batterystore/retailers/editgstid.jsp" class="menuitem">Edit&nbsp;GSTID</a></td>
											</tr>
										</table>
									</div>
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr><td height="1" bgcolor="#CCCCCC"></td></tr>
				<tr><td height="1" bgcolor="#FFFFFF"></td></tr>
				<tr>
					<td>
						<table width="180" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td width="20" align="left" valign="middle"><img src="/bookbattery/images/arrow-right.png" width="16" height="16" /></td>
								<td height="25" align="left" valign="middle" onmouseover="expand(this);" onmouseout="collapse(this);"><a href="/bookbattery/jsp/admin/batterystore/mis/misoptions.jsp" class="category">Retailer Invoice</a>
								<div class="menuNormal" width="120">
										<table class="menu" width="80">
											<!--tr>
												<td class="menuNormal"><a href="/bookbattery/jsp/admin/batterystore/mis/misoptions.jsp" class="menuitem">Order&nbsp;Details</a></td>
											</tr>
											<tr>
												<td class="menuNormal"><a href="/bookbattery/jsp/admin/batterystore/mis/healthcheckmisoption.jsp" class="menuitem">Battery&nbsp;Health&nbsp;Check</a></td>
											</tr>
											<tr>
												<td class="menuNormal"><a href="/bookbattery/jsp/admin/batterystore/mis/registerbatterymisoption.jsp" class="menuitem">Battery&nbsp;Registration&nbsp;Details</a></td>
											</tr>
											<tr>
												<td class="menuNormal"><a href="/bookbattery/jsp/admin/batterystore/laptopBookBattery/laptopbatterymisoption.jsp" class="menuitem">Laptop&nbsp;Battery&nbsp;Order&nbsp;Details</a></td>
											</tr>
											<tr>
												<td class="menuNormal"><a href="/bookbattery/jsp/admin/batterystore/mis/invertermisoptions.jsp" class="menuitem">Inverter&nbsp;Order&nbsp;Details</a></td>
											</tr>
											<tr>
												<td class="menuNormal"><a href="/bookbattery/jsp/admin/batterystore/mis/selectpostponedate.jsp" class="menuitem">Postponed&nbsp;Battery&nbsp;Order&nbsp;Details</a></td>
											</tr>
											<tr>
												<td class="menuNormal"><a href="/bookbattery/jsp/admin/batterystore/mis/inverterselectpostponedate.jsp" class="menuitem">Postponed&nbsp;Inverter&nbsp;Order&nbsp;Details</a></td>
											</tr-->
											<tr>
												<td class="menuNormal"><a href="/bookbattery/servlet/GenerateRetailerInvoice?hidWhatToDo=getretailers" class="menuitem">Generate&nbsp;Retailer&nbsp;Invoice</a></td>
											</tr>
											<!--tr>
												<td class="menuNormal"><a href="/bookbattery/servlet/GenerateRetailerInvoice?hidWhatToDo=getinvretailers" class="menuitem">Generate&nbsp;Retailer&nbsp;Invoice[Inverter]</a></td>
											</tr>
											<tr>
												<td class="menuNormal"><a href="/bookbattery/servlet/GenerateServiceRetailerInvoice?hidWhatToDo=getretailers" class="menuitem">Generate&nbsp;Retailer&nbsp;Invoice[Services]</a></td>
											</tr-->
											<tr>
												<td class="menuNormal"><a href="/bookbattery/jsp/admin/batterystore/mis/viewretailerinvoice.jsp" class="menuitem">View&nbsp;Retailer&nbsp;Invoice</a></td>
											</tr>
										</table>
									</div>
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr><td height="1" bgcolor="#CCCCCC"></td></tr>
				<tr><td height="1" bgcolor="#FFFFFF"></td></tr>
				<tr>
					<td>
						<table width="180" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td width="20" align="left" valign="middle"><img src="/bookbattery/images/arrow-right.png" width="16" height="16" /></td>
								<td height="25" align="left" valign="middle" onmouseover="expand(this);" onmouseout="collapse(this);"><a href="/bookbattery/servlet/GenerateRetailerInvoice?hidWhatToDo=getcities" class="category">Percentage</a>
								<div class="menuNormal" width="120">
										<table class="menu" width="80">
											<tr>
												<td class="menuNormal"><a href="/bookbattery/servlet/GenerateRetailerInvoice?hidWhatToDo=getcities" class="menuitem">Add&nbsp;Retailer&nbsp;Invoice&nbsp;Percentage</a></td>
											</tr>
											<tr>
												<td class="menuNormal"><a href="javascript:validatepassword(8);" class="menuitem">Edit&nbsp;Retailer&nbsp;Invoice&nbsp;Percentage</a></td>
											</tr>
											<tr>
												<td class="menuNormal"><a href="/bookbattery/servlet/GenerateRetailerInvoice?hidWhatToDo=getcities&keyword=cuspercent" class="menuitem">Add&nbsp;Customer&nbsp;Invoice&nbsp;Percentage</a></td>
											</tr>
											<tr>
												<td class="menuNormal"><a href="javascript:validatepassword(9);" class="menuitem">Edit&nbsp;Customer&nbsp;Invoice&nbsp;Percentage</a></td>
											</tr>
										</table>
									</div>
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<!--tr><td height="1" bgcolor="#CCCCCC"></td></tr>
				<tr><td height="1" bgcolor="#FFFFFF"></td></tr>
				<tr>
					<td>
						<table width="180" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td width="20" align="left" valign="middle"><img src="/bookbattery/images/arrow-right.png" width="16" height="16" /></td>
								<td height="25" align="left" valign="middle"><a href="/bookbattery/jsp/admin/batterystore/visitors/visitoptions.jsp" class="category">Visitors in BookBattery</a></td>
							</tr>
						</table>
					</td>
				</tr>
				<tr><td height="1" bgcolor="#CCCCCC"></td></tr>
				<tr><td height="1" bgcolor="#FFFFFF"></td></tr>
				<tr>
					<td>
						<table width="180" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td width="20" align="left" valign="middle"><img src="/bookbattery/images/arrow-right.png" width="16" height="16" /></td>
								<td height="25" align="left" valign="middle" onmouseover="expand(this);" onmouseout="collapse(this);"><a href="/bookbattery/jsp/admin/batterystore/laptopBookBattery/addlaptopBookBattery.jsp" class="category">Laptop Battery Details</a>
								<div class="menuNormal" width="120">
										<table class="menu" width="80">
											<tr>
												<td class="menuNormal"><a href="/bookbattery/jsp/admin/batterystore/laptopBookBattery/addlaptopBookBattery.jsp" class="menuitem">Add&nbsp;Battery</a></td>
											</tr>
											<tr>
												<td class="menuNormal"><a href="/bookbattery/servlet/AddLaptopBatteryDetails?hidWhatToDo=getlaptopbatterybrands&keyword=editbattery" class="menuitem">Edit&nbsp;Battery</a></td>
											</tr>
											<tr>
											<td class="menuNormal"><a href="javascript:validatepassword(14);"  class="menuitem">Delete&nbsp;Battery</a></td>
										
											</tr>
										</table>
									</div></td>
							</tr>
						</table>
					</td>
				</tr>
				<tr><td height="1" bgcolor="#CCCCCC"></td></tr>
				<tr><td height="1" bgcolor="#FFFFFF"></td></tr>
				<tr>
					<td>
						<table width="180" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td width="20" align="left" valign="middle" ><img src="/bookbattery/images/arrow-right.png" width="16" height="16" /></td>
								<td height="25" align="left" valign="middle" onmouseover="expand(this);" onmouseout="collapse(this);"><a href="/bookbattery/jsp/admin/batterystore/laptopBookBattery/addlaptop.jsp" class="category">Laptop Details</a>
								<div class="menuNormal" width="120">
										<table class="menu" width="80">
											<tr>
												<td class="menuNormal"><a href="/bookbattery/jsp/admin/batterystore/laptopBookBattery/addlaptop.jsp" class="menuitem">Add&nbsp;Laptop&nbsp;Details</a></td>
											</tr>
											<tr>
												<td class="menuNormal"><a href="javascript:validatepassword(15);"  class="menuitem">Delete&nbsp;Laptop&nbsp;Details</a></td>
												
											</tr>
										</table>
									</div></td>
							</tr>
						</table>
					</td>
				</tr>
				<tr><td height="1" bgcolor="#CCCCCC"></td></tr>
				<tr><td height="1" bgcolor="#FFFFFF"></td></tr>
				<tr>
					<td>
						<table width="180" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td width="20" align="left" valign="middle"><img src="/bookbattery/images/arrow-right.png" width="16" height="16" /></td>
								<td height="25" align="left" valign="middle" onmouseover="expand(this);" onmouseout="collapse(this);"><a href="/bookbattery/jsp/admin/batterystore/laptopBookBattery/addlaptopapplicationchart.jsp" class="category">Application&nbsp;Chart&nbsp;for&nbsp;Laptops</a>
								<div class="menuNormal" width="120">
										<table class="menu" width="80">
											<tr>
												<td class="menuNormal"><a href="/bookbattery/jsp/admin/batterystore/laptopBookBattery/addlaptopapplicationchart.jsp" class="menuitem">Add&nbsp;Application&nbsp;Chat</a></td>
											</tr>
											<tr>
												<td class="menuNormal"><a href="javascript:validatepassword(16);" class="menuitem">Delete&nbsp;Application&nbsp;Chat</a></td>							
											</tr>
										</table>
									</div></td>
							</tr>
						</table>
					</td>
				</tr-->
				<tr><td height="1" bgcolor="#CCCCCC"></td></tr>
				<tr><td height="1" bgcolor="#FFFFFF"></td></tr>
			
				<tr>
					<td>
						<table width="180" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td width="20" align="left" valign="middle"><img src="/bookbattery/images/arrow-right.png" width="16" height="16" /></td>
								<td height="25" align="left" valign="middle"><a href="/bookbattery/jsp/admin/batterystore/brandloyaltyprogram/brandloyaltyselection.jsp" class="category">Loyalty&nbsp;Management</a></td>
							</tr>
						</table>
					</td>
				</tr>
				<tr><td height="1" bgcolor="#CCCCCC"></td></tr>
				<tr><td height="1" bgcolor="#FFFFFF"></td></tr>
				<tr>
					<td>
						<table width="180" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td width="20" align="left" valign="middle"><img src="/bookbattery/images/arrow-right.png" width="16" height="16" /></td>
								<td height="25" align="left" valign="middle"><a href="/bookbattery/jsp/admin/batterystore/signout.jsp" class="category">Sign&nbsp;Out</a></td>
							</tr>
						</table>
					</td>
				</tr>

			</table>
		</td>
	</tr>