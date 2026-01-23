<%--
    Document   : batteryadminmain
    Created on : Aug 30, 2013, 4:22:12 PM
    Author     : Sai Krishna Daddala.
--%>
<%@ page language="java" import="java.sql.*"%>
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
Vector brandVector=(Vector)session.getAttribute("sesinverterbrandvector");
LogLevel.DEBUG(1,new Throwable(),"brandVector: "+brandVector);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<link rel="shortcut icon" href="../../../../images/favicon.png" type="image/x-icon">
<title>BookBattery.com - India's No.1 Automobile Battery Store</title>
<script language="JavaScript" src="../../../../js/jquery-1.8.2.js" ></script>
<script type="text/javascript">
$(document).ready(function()
{
		$category4 = $('#invbrand');
        $category4.change(
            function() {
			var splitvalm =$category4.val();
			if(splitvalm == "default" || splitvalm == 0)
			 {
				for(i=document.editordeleteinverter.capacity.options.length-1;i>=1;i--)
				{
				document.editordeleteinverter.capacity.remove(i);
				}
				document.editordeleteinverter.invbrand.focus();
				$('#diveditordeleteinvdetails').hide();
			 }
			 else
			 {
				 $('#diveditordeleteinvdetails').hide();
				$.ajax({
				type: "GET",
				url: "../../../../servlet/InverterDetails?hidWhatToDo=getinvcapacity",
				data: {invbrand: $category4.val() },
				success: function(data){
				$("#capacity").html(data)
				document.editordeleteinverter.capacity.focus();
                    }
                });
			 }
            }
        );
	});
	$(document).ready(function()
{
		$category5 = $('#capacity');
        $category5.change(
            function() {
			var splitvalm =$category5.val();
			if(splitvalm == "default" || splitvalm == 0)
			 {
				for(i=document.editordeleteinverter.invertermodel.options.length-1;i>=1;i--)
				{
				document.editordeleteinverter.invertermodel.remove(i);
				}
				document.editordeleteinverter.invbrand.focus();
				$('#diveditordeleteinvdetails').hide();
			 }
			 else
			 {
				 $('#diveditordeleteinvdetails').hide();
				$.ajax({
				type: "GET",
				url: "../../../../servlet/InverterDetails?hidWhatToDo=getinvmodels",
				data: {brand: $category4.val(),invcapacity: $category5.val() },
				success: function(data){
				$("#invertermodel").html(data)
				document.editordeleteinverter.invertermodel.focus();
                    }
                });
			 }
            }
        );
	});
function getinverterdetails()
{
	var invbrand = document.editordeleteinverter.invbrand.value; 
	var capacity = document.editordeleteinverter.capacity.value;
	var invmodel = document.editordeleteinverter.invertermodel.value;

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
				$('#diveditordeleteinvdetails').show();
				document.getElementById("diveditordeleteinvdetails").innerHTML = resp;
				document.getElementById("diveditordeleteinvdetails").innerHTML = xmlhttp.responseText;
			}
		}			
	}
	xmlhttp.open("POST","../../../../servlet/InverterDetails?hidWhatToDo=getinveterdetailstoupdatordelete&invbrand="+invbrand+"&capacity="+capacity+"&invmodel="+invmodel,true);		
	xmlhttp.send();	
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
       startFrom = 0;
    }
    for (var n = 0; n < argvalue.length; n++) {
        if (validChars.indexOf(argvalue.substring(n, n+1)) == -1) return false;
    }
  return true;
}
function funToUpdateinverterdetails(invid)
{
	var invmodel = document.editordeleteinverter.invertermodel.value;
	var invwarrnty = document.editordeleteinverter.invwarrenty.value;
	var tubelights = document.editordeleteinverter.tubelights.value;
	var fans = document.editordeleteinverter.fans.value;
	var telivision = document.editordeleteinverter.tv.value;
	var computers = document.editordeleteinverter.computers.value;
//alert(invmodel);
		var iChars1 = "`~!@#$%^&*()=_[]\\\';,/{}|\":<>?";
		var iChars2 = "`~!@#$%^&*()+=[]';,{}|\":<>?.";
		var regularexp=/^[^\s\-].*[^\s\-]$/;
		var regularexp1=/^(\d.+[a-zA-Z ]|[a-zA-Z ]+\d.)[a-zA-Z\d.]*$/
		var regularexpappliances=/^(([0-9],?)+)$/

		if(invmodel == "") 
		 {
			
         }
		else
		{
			if (document.editordeleteinverter.invmodel.value.length < 3)
			 {
				errMsg ="<font color='#ff3333'>Please enter at least 3 characters in the \'Inverter Model\' field.</font>";
				document.getElementById("displayinvdetailsrrormsg").innerHTML=errMsg;
				document.editordeleteinverter.invmodel.focus();
				return ;
			 }
			if (document.editordeleteinverter.invmodel.value.length > 49)
			 {
				errMsg ="<font color='#ff3333'>Please enter only 49 characters in the in the \'Inverter Model\' field.</font>";
				document.getElementById("displayinvdetailsrrormsg").innerHTML=errMsg;
				document.editordeleteinverter.invmodel.focus();
				return ;
			 }
			 if (document.editordeleteinverter.invmodel.value.indexOf(' ')==0 )
			 {
				 errMsg ="<font color='#ff3333'>Only spaces or space before text is not allowed in the in the \'Inverter Model\' field.</font>";
				document.getElementById("displayinvdetailsrrormsg").innerHTML=errMsg;
				document.editordeleteinverter.invmodel.focus();
				return ;
			 }
			for (var i = 0; i < document.editordeleteinverter.invmodel.value.length; i++)
			{
				 if (iChars2.indexOf(document.editordeleteinverter.invmodel.value.charAt(i))!= -1)
				 {
					errMsg ="<font color='#ff3333'>Special characters are not allowed in \'Inverter Model\' field.</font>";
					document.getElementById("displayinvdetailsrrormsg").innerHTML=errMsg;
					document.editordeleteinverter.invmodel.focus();
					return ;
				 }
			}
			if (! document.editordeleteinverter.invmodel.value.match(regularexp))
			{
				errMsg ="<font color='#ff3333'>\'Inverter Model\' has Unnecessary spaces. Please remove spaces.</font>";
				document.getElementById("displayinvdetailsrrormsg").innerHTML=errMsg;
				document.editordeleteinverter.invmodel.focus();
				return ;
			}
		}

		if(invwarrnty == "")
		  {
			errMsg ="<font color='#ff3333'>Please enter \'Warranty\'.</font>";
			document.getElementById("displayinvdetailsrrormsg").innerHTML=errMsg;
			document.editordeleteinverter.invwarrenty.focus();
			return ;
         }
		 if(invwarrnty == 0)
		{
			errMsg ="<font color='#ff3333'>Please enter valid \'Warranty\'.</font>";
			document.getElementById("displayinvdetailsrrormsg").innerHTML=errMsg;
			document.editordeleteinverter.invwarrenty.focus();
			return ;
         }
		 if (document.editordeleteinverter.invwarrenty.value.indexOf(' ')==0 )
		 {
			 errMsg ="<font color='#ff3333'>Only spaces or space before text is not allowed in the in the \'Warranty\' field.</font>";
			document.getElementById("displayinvdetailsrrormsg").innerHTML=errMsg;
			document.editordeleteinverter.invwarrenty.focus();
			return ;
         }
		 for (var i = 0; i < document.editordeleteinverter.invwarrenty.value.length; i++)
		{
         if (iChars1.indexOf(document.editordeleteinverter.invwarrenty.value.charAt(i))!= -1)
         {
			errMsg ="<font color='#ff3333'>Special characters are not allowed in \'Warranty\' field.</font>";
			document.getElementById("displayinvdetailsrrormsg").innerHTML=errMsg;
			document.editordeleteinverter.invwarrenty.focus();
			return ;
         
		 }
		}
		if(invwarrnty=="NA")
		{
		}
		else
		{
				if (regularexp1.test(invwarrnty)==false)
				{
					errMsg ="<font color='#ff3333'>Please enter only Alphanumerics in the \'Warranty\' field.</font>";
					document.getElementById("displayinvdetailsrrormsg").innerHTML=errMsg;
					document.editordeleteinverter.invwarrenty.focus();
					return ;
				}
		}
		 if(computers == "")
		  {
			errMsg ="<font color='#ff3333'>Please enter \'Computers field\'.</font>";
			document.getElementById("displayinvdetailsrrormsg").innerHTML=errMsg;
			document.editordeleteinverter.computers.focus();
			return ;
         }
		 if (document.editordeleteinverter.computers.value.indexOf(' ')==0 )
		 {
			 errMsg ="<font color='#ff3333'>Only spaces or space before text is not allowed in the \'Computers\' field.</font>";
			document.getElementById("displayinvdetailsrrormsg").innerHTML=errMsg;
			document.editordeleteinverter.computers.focus();
			return ;
         }
		if(isDigits(computers)==true)
		{
			errMsg ="<font color='#ff3333'>Only Numerics are allowed in the \'Computers\' field.</font>";
			document.getElementById("displayinvdetailsrrormsg").innerHTML=errMsg;
			document.editordeleteinverter.computers.focus();
			return ;
         }
		 if (! document.editordeleteinverter.computers.value.match(regularexpappliances))
		{
			errMsg ="<font color='#ff3333'>Please enter computers in the form of 1,1</font>";
			document.getElementById("displayinvdetailsrrormsg").innerHTML=errMsg;
			document.editordeleteinverter.computers.focus();
			return ;
		}
		var resultcomputers = computers.slice(-1);		
		if(resultcomputers==",")
		{
			errMsg ="<font color='#ff3333'>Please enter computers in the form of 1,1</font>";
			document.getElementById("displayinvdetailsrrormsg").innerHTML=errMsg;
			document.editordeleteinverter.computers.focus();
			return ;
		}

		if(tubelights == "")
		  {
			errMsg ="<font color='#ff3333'>Please enter \'Tube Lights field\'.</font>";
			document.getElementById("displayinvdetailsrrormsg").innerHTML=errMsg;
			document.editordeleteinverter.tubelights.focus();
			return ;
         }
		 if (document.editordeleteinverter.tubelights.value.indexOf(' ')==0 )
		 {
			 errMsg ="<font color='#ff3333'>Only spaces or space before text is not allowed in the \'Tube Lights\' field.</font>";
			document.getElementById("displayinvdetailsrrormsg").innerHTML=errMsg;
			document.editordeleteinverter.tubelights.focus();
			return ;
         }
		if(isDigits(tubelights)==true)
		{
			errMsg ="<font color='#ff3333'>Only Numerics are allowed in the \'Tube Lights\' field.</font>";
			document.getElementById("displayinvdetailsrrormsg").innerHTML=errMsg;
			document.editordeleteinverter.tubelights.focus();
			return ;
         }
		 	 if (! document.editordeleteinverter.tubelights.value.match(regularexpappliances))
		{
			errMsg ="<font color='#ff3333'>Please enter tube lights in the form of 1,1</font>";
			document.getElementById("displayinvdetailsrrormsg").innerHTML=errMsg;
			document.editordeleteinverter.tubelights.focus();
			return ;
		}
		var result = tubelights.slice(-1);		
		if(result==",")
		{
			errMsg ="<font color='#ff3333'>Please enter tube lights in the form of 1,1</font>";
			document.getElementById("displayinvdetailsrrormsg").innerHTML=errMsg;
			document.editordeleteinverter.tubelights.focus();
			return ;
		}
		if(fans == "")
		{
			errMsg ="<font color='#ff3333'>Please enter \'Fans field\'.</font>";
			document.getElementById("displayinvdetailsrrormsg").innerHTML=errMsg;
			document.editordeleteinverter.fans.focus();
			return ;
        }
		if(document.editordeleteinverter.fans.value.indexOf(' ')==0 )
		{
			 errMsg ="<font color='#ff3333'>Only spaces or space before text is not allowed in the \'Fans\' field.</font>";
			document.getElementById("displayinvdetailsrrormsg").innerHTML=errMsg;
			document.editordeleteinverter.fans.focus();
			return ;
         }
		if(isDigits(fans)==true)
		{
			errMsg ="<font color='#ff3333'>Only Numerics are allowed in the \'Fans\' field.</font>";
			document.getElementById("displayinvdetailsrrormsg").innerHTML=errMsg;
			document.editordeleteinverter.fans.focus();
			return ;
         }		
		if (! document.editordeleteinverter.fans.value.match(regularexpappliances))
		{
			errMsg ="<font color='#ff3333'>Please enter fans in the form of 1,1</font>";
			document.getElementById("displayinvdetailsrrormsg").innerHTML=errMsg;
			document.editordeleteinverter.fans.focus();
			return ;
		}
		var resultfan = fans.slice(-1);		
		if(resultfan==",")
		{
			errMsg ="<font color='#ff3333'>Please enter fans in the form of 1,1</font>";
			document.getElementById("displayinvdetailsrrormsg").innerHTML=errMsg;
			document.editordeleteinverter.fans.focus();
			return ;
		}
		 if(telivision == "")
		 {
			errMsg ="<font color='#ff3333'>Please enter \'TV field\'.</font>";
			document.getElementById("displayinvdetailsrrormsg").innerHTML=errMsg;
			document.editordeleteinverter.tv.focus();
			return ;
         }
		 if (document.editordeleteinverter.tv.value.indexOf(' ')==0 )
		 {
			 errMsg ="<font color='#ff3333'>Only spaces or space before text is not allowed in the \'TV\' field.</font>";
			document.getElementById("displayinvdetailsrrormsg").innerHTML=errMsg;
			document.editordeleteinverter.tv.focus();
			return ;
         }
		if(isDigits(telivision)==true)
		{
			errMsg ="<font color='#ff3333'>Only Numerics are allowed in the \'TV\' field.</font>";
			document.getElementById("displayinvdetailsrrormsg").innerHTML=errMsg;
			document.editordeleteinverter.tv.focus();
			return ;
       }	
		if (! document.editordeleteinverter.tv.value.match(regularexpappliances))
		{
			errMsg ="<font color='#ff3333'>Please enter telivision in the form of 1,1</font>";
			document.getElementById("displayinvdetailsrrormsg").innerHTML=errMsg;
			document.editordeleteinverter.tv.focus();
			return ;
		}
		var resulttelivision = telivision.slice(-1);		
		if(resulttelivision==",")
		{
			errMsg ="<font color='#ff3333'>Please enter telivision in the form of 1,1</font>";
			document.getElementById("displayinvdetailsrrormsg").innerHTML=errMsg;
			document.editordeleteinverter.tv.focus();
			return ;
		}
		document.editordeleteinverter.method="post";
		document.editordeleteinverter.action="../../../../servlet/InverterDetails?hidWhatToDo=editinverterdetails&invmodel="+invmodel+"&invwarrnty="+invwarrnty+"&tubelights="+tubelights+"&fans="+fans+"&telivision="+telivision+"&computers="+computers+"&invid="+invid;
		//alert(document.editordeleteinverter.action);  
		document.editordeleteinverter.submit();      
}
function funToDeleteInverterDetails(invid)
{
	var invbrand = document.editordeleteinverter.invbrand.value;
	var capacity = document.editordeleteinverter.capacity.value;

	var agree=confirm("Are You sure want to delete inverter details! ");
	if (agree)
	{
		document.editordeleteinverter.method="post";
		document.editordeleteinverter.action="../../../../servlet/InverterDetails?hidWhatToDo=deleteinverterdetails&chkSi="+invid+"&brand="+invbrand+"&capacity="+capacity;
		//alert(document.editbatteryprice.action);  
		document.editordeleteinverter.submit();
	}
}
</script>
<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
	/*background-image: url(../../../../images/index_01_01.gif);
	background-repeat: repeat-x;*/
}
-->
</style>
<link href="../../../../css/bookbattery.css" rel="stylesheet" type="text/css" />
</head>
<body onload="document.editordeleteinverter.batbrand.focus(); getBatteryBrands(); noBack();" >
<!-- Start Alexa Certify Javascript -->
<script type="text/javascript">
_atrk_opts = { atrk_acct:"VrG0j1a4ZP00yt", domain:"BookBattery.com",dynamic: true};
(function() { var as = document.createElement('script'); as.type = 'text/javascript'; as.async = true; as.src = "https://d31qbv1cthcecs.cloudfront.net/atrk.js"; var s = document.getElementsByTagName('script')[0];s.parentNode.insertBefore(as, s); })();
</script>
<noscript><img src="https://d5nxst8fruw4z.cloudfront.net/atrk.gif?account=VrG0j1a4ZP00yt" style="display:none" height="1" width="1" alt="" /></noscript>
<!-- End Alexa Certify Javascript -->
<form name="editordeleteinverter" action="request_for_quote.asp"  method="post">
<!-- Battery Header Starts -->
<tr>
	<jsp:include page = "../header.jsp" />
</tr>
<!-- Battery Header Ends -->
<!--<tr>
	<td>
		<img src="../../../../images/flag1234.JPG" width="880" height="15">
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
					<jsp:include page="../batteryadminleftmenu.jsp"/>
				</tr>
				<!-- Ends battery admin left Menu -->
				</table>
			</td>
			<td width="75%" align="left" valign="top">
				<table width="100%" border="1" cellspacing="0" cellpadding="0">
				<tr>
					<td align="left" valign="top">
					<!-- your page content starts here  -->
					<table width="100%" cellspacing="1" bgcolor="#FFFFFF" cellpadding="0" border="0">
					<tr>
						<td align="left" valign="top">
							<tr>
								<td class="subheading">Edit/Delete&nbsp;Inverter&nbsp;details</td>
								<td  align="right"><a href="../../../../servlet/AdminBatteryLogin?hidWhatToDo=batteryadminhome" class="onclick1">Back&nbsp;&nbsp;</a></td>
							</tr>
							<tr><td height="10"></td></tr>
							<tr><td align="center" class="insidecontentbat"><div id="displayinvdetailsrrormsg"></div></td></tr>
							<td>
								<table width="80%" cellspacing="2" bgcolor="#FFFFFF" cellpadding="0" border="0" align="center">
								<tr>
									<td width="39%" class="insidecontent" align="right">Select&nbsp;Inverter&nbsp;Brand<font color="FF0000">*</font>&nbsp;:</td>
									<td width="59%" class="insidecontent" align="left">
									<select name="invbrand" id="invbrand" class="insidecontent" STYLE="width: 180px" >
										<option value="0" >&lt;-&nbsp;&nbsp; Select Brand &nbsp;&nbsp; --&gt;</option>
										<%
										try
										   {
											if(brandVector!=null && brandVector.size()>0)
											{
											for(int i=0;i<brandVector.size();i++)
											{
												Hashtable ht=(Hashtable)brandVector.get(i);
												String brand=(String)ht.get("inverter_brand");
										%>
										<option value="<%=brand%>"><%=brand%></option>
										<%
										}
										}	
										}
										catch(Exception e )
										{
										e.printStackTrace();
										}
										%>
									</select></td>
								</tr>
								<tr>
									<td width="39%" class="insidecontent" align="right">Select Capacity<font color="FF0000">*</font>&nbsp;:</label></td>
									<td width="59%" class="insidecontent" align="left">
										<select name="capacity" id="capacity"  class="insidecontent" STYLE="width: 180px">
											<option value="0" >&lt;-&nbsp;&nbsp; Select Capacity -&gt;</option>
										</select></td>
								</tr>
								<tr>
									<td width="39%" class="insidecontent" align="right">Select Model<font color="FF0000">*</font>&nbsp;:</label></td>
									<td width="59%" class="insidecontent" align="left">
										<select name="invertermodel" id="invertermodel" onChange="javascript:getinverterdetails();" class="insidecontent" STYLE="width: 180px">
											<option value="0" >&lt;-&nbsp;&nbsp; Select Model -&gt;</option>
										</select></td>
								</tr>
						
								</td>
								</tr>
								</td>
								</tr>
								</table>
								<tr>
									<td>
										<table width="100%" border="0" align="center">
											<tr class="#FFFFFF" bgcolor="FFFFFF">
												<td width="40%"><div id="diveditordeleteinvdetails"></div></td>
											</tr>
										</table>
									</td>
								</tr>
							</td>
						</tr>
					<tr><td height="10"></td></tr>
			
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
<input type="hidden" name="strEmail" value="">
</form>
</center>
<%
String strLogmapMsg=(String)session.getAttribute("seseditdeleteinveterdetailsErrorMsg");
if(strLogmapMsg ==null)
{
	strLogmapMsg="";
}
else
{
%>
<script type="text/javascript">
var loginmpamessg; 
loginmpamessg= "<%=strLogmapMsg%>";
document.getElementById("displayinvdetailsrrormsg").innerHTML=loginmpamessg;
</script>
<%
	session.removeAttribute("seseditdeleteinveterdetailsErrorMsg");
}
%>
</body>
</html>