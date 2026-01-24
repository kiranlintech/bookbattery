<%-- 
Document   : index.jsp
Created on : Aug 23, 2013, 10:10:12 AM
Author     : Sai Krishna Daddala
--%>
<%@ page language="java" import="java.sql.*"%>
<%@page language="java" import="java.io.File,java.text.*,java.util.Calendar,java.util.ArrayList,java.util.Hashtable,java.util.Vector,com.ngit.javabean.loglevel.*,java.util.Date,java.util.Properties,java.util.*,com.ngit.javabean.loglevel.LogLevel,javax.servlet.ServletContext,java.util.Properties,java.io.FileInputStream"%>
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
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<base href="<%=baseURL%>"></base>
<link href="/bookbattery/css/bookbattery.css" rel="stylesheet" type="text/css" />
<link rel="shortcut icon" href="/bookbattery/images/favicon.png" type="image/x-icon"/>
<script src="/bookbattery/js/jquery-1.3.2.min.js" type="text/javascript"></script>
<script language="JavaScript" src="/bookbattery/js/pophide.js" type="text/javascript"></script>
<script language="JavaScript" src="/bookbattery/js/jquery-1.8.2.js" type="text/javascript"></script>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>BookBattery - Official Online Car/Inverter Amaron Battery Store</title>
<meta name='description' content="Order and Get Genuine Amaron Car Battery or Inverter Battery or Bike Battery.  BookBattery is India's Only Official Amaron Battery Online Store. Best Discount Prices with Free Installation and Original Warranty is Offered" />
<meta name='keywords' content='Battery, Batteries, Car Battery, Inverter Battery, Bike Battery, Amaron Battery, Buy Battery online, Car Battery Online' />
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
body{font:13px/1.231 arial,helvetica,clean,sans-serif;*font-size:small;*font:x-small;}select,input,button,textarea{font:99% arial,helvetica,clean,sans-serif; }table{font-size:inherit;font:100%;}pre,code,kbd,samp,tt{font-family:monospace;*font-size:108%;line-height:100%;}

td.loading_image{background:  url('/bookbattery/images/loader.gif');width:100px; /* use you own image size; */ 
height: 40px; /* use you own image size; */ 
background-repeat: no-repeat; 
background-position: center; 
vertical-align: top;}

.button_example{
border:0px; -webkit-border-radius: 3px; -moz-border-radius: 3px;border-radius: 3px;font-size:14px;width:400px;font-family:arial, helvetica, sans-serif; padding: 10px 10px 10px 10px; text-decoration:none; display:inline-block;text-shadow: -1px -1px 0 rgba(0,0,0,0.3);font-weight:bold; color: #FFFFFF;
 background-color: #FF8c00; background-image: -webkit-gradient(linear, left top, left bottom, from(#FF8c00), to(#FF8c00));
 background-image: -webkit-linear-gradient(top, #FF8c00, #FF8c00);
 background-image: -moz-linear-gradient(top, #FF8c00, #FF8c00);
 background-image: -ms-linear-gradient(top, #FF8c00, #FF8c00);
 background-image: -o-linear-gradient(top, #FF8c00, #FF8c00);
 background-image: linear-gradient(to bottom, #FF8c00, #FF8c00);filter:progid:DXImageTransform.Microsoft.gradient(GradientType=0,startColorstr=#FF8c00, endColorstr=#FF8c00);
}

.button_example1{
border:0px; -webkit-border-radius: 3px; -moz-border-radius: 3px;border-radius: 3px;font-size:14px;width:120px;font-family:arial, helvetica, sans-serif; padding: 10px 10px 10px 10px; text-decoration:none; display:inline-block;text-shadow: -1px -1px 0 rgba(0,0,0,0.3);font-weight:bold; color: #FFFFFF;
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
<body onload= "scrollingtopadsDefault(); globalbanneradbattery('whatevertopad');">
<!-- Start Alexa Certify Javascript -->
<script type="text/javascript">
_atrk_opts = { atrk_acct:"VrG0j1a4ZP00yt", domain:"BookBattery.com",dynamic: true};
(function() { var as = document.createElement('script'); as.type = 'text/javascript'; as.async = true; as.src = "https://d31qbv1cthcecs.cloudfront.net/atrk.js"; var s = document.getElementsByTagName('script')[0];s.parentNode.insertBefore(as, s); })();
</script>
<noscript><img src="https://d5nxst8fruw4z.cloudfront.net/atrk.gif?account=VrG0j1a4ZP00yt" style="display:none" height="1" width="1" alt="" /></noscript>
<!-- End Alexa Certify Javascript -->
<form name="batterylogin" action="">
	<div id='divmobile' class='divmobile' style="display:none;"></div>
	<table border='0' cellpadding="0" width="950" align="center">
		<tr>
			<td>
			<jsp:include page = "../../header.jsp" />
		<table width="950" border="0" class="insidecontent c24" align="center" cellspacing="0" cellpadding="0" >
			<tr>
				<td height="2"><div id='scrollpopup'  style="display:block;"><table width="100%" border="0" align="center" valign="top"><tr><td align='right' class='loading_image'></td></tr><tr><td align='center' style='font-family:Verdana;font-size:12px;color:#000000; text-decoration:none;padding:1px 1px;'>Please wait page is loading....</td></tr></table></div></td></td>
			</tr>
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
											<td width="100%" class="insidecontent" align="left"><a class="insidecontent" href="/bookbattery/index.jsp"><img src="/bookbattery/images/back25.png" alt="order car battery online" border="0" width="25" height="25"/></a></td>
										</tr>
									</table>
									</td>
									</tr>
									<tr>
										<td valign="top" width="70%" align="right">
											<table class='insidecontent' valign="top" align='center' height="100" cellspacing='0'  cellpadding='0' width='50%' border='0' >
											<tr><td align="center" class="insidecontent"><div id="displyloginsesmsg"></div></td></tr>
											<tr>
												<td width='30%' height='20' colspan='5' align='center' valign='middle' class='insidecontent' style="padding-left:5px;"><font size='3' color='2364bl'><h3 style='font-size:16px;font-family:Verdana;color:#ff8c00'>Log In</h3></font>
												<font size='2' color='black' style='font-size:12px;font-family:Verdana;'>Please enter email id and password to log in<b></font></td>
											</tr>
											<tr height='10px'>
												<td align='center' valign='middle' class='insidecontent'><font color='#FFFFFF' size='2px'><input type='text' size='15' style='width:395px;height:25px;border: 2px solid #CCC;font-size: 13px;font-family: Verdana;border-radius: 4px 4px 4px 4px;' placeholder="Email ID" name='usrname'  id='usrname' onmousedown="clearmsg();" onkeydown="clearmsg();" id='usrname'  MAXLENGTH='50' tabindex='1'  autocomplete='on' VCARD_NAME='vCard.Email' onkeydown="clearmsg();" /></font><br>&nbsp;</td></tr>
											<tr>
												<td align='center' valign='middle' class='insidecontent' style="padding-bottom:0px;"><font color='#FFFFFF' size='2px'><input type='password' onkeypress='searchKeyPress(event);' onmousedown="clearmsg();"onkeydown="if ((event.which && event.which == 13) || (event.keyCode && event.keyCode == 13)){javascript:login();return false;} else return true;" id="password" class='paswrd' name='password' MAXLENGTH='50' tabindex='2' size='15' style='width:395px;height:25px;border: 2px solid #CCC;font-size: 13px;font-family: Verdana;border-radius: 4px 4px 4px 4px;' placeholder="Password" /></td></tr>
												<![endif]-->	
											<tr>
												<td align='center' valign="top" style="padding-top:9px;">
												<input type="button" href='#' value="Login" id="signupbutton" class="button_example" onclick="login()" onkeypress='searchKeyPress(event);' tabindex="0"></td>
											</tr>	
											<tr><td height="20"></td></tr>
											<tr><td align='center' style="padding-left:2px;"><input type="checkbox" name="forgotpass" onclick="javascript:forgotpasswordlink();" ><font color='#999999' style='padding-right:0px;font-size:12px;font-family:Verdana;'>forgot&nbsp;your&nbsp;password?</font></td></tr>
											<table id="forgotdiv" width="43%" align="center" border="0" style="display:none;">
												<tr><td align="center" class="insidecontent"><div id="displayemailerrormsg"></div></td></tr>
												<tr><td align="left" style="font-family:Verdana;font-size:12px;font-weight:bold;color:#010101;text-decoration:none;padding:1px 1px;">Enter Your EmailID</td></tr>
												<tr><td align="left"><input type='text' style='width:250px;height:25px;border: 2px solid #CCC;font-size: 13px;font-family: Verdana;border-radius: 4px 4px 4px 4px;' placeholder="Email ID" name='emailid'  id='emailid' MAXLENGTH='50'/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" href='#' value="Submit" id="forgotpasswordbutton" class="button_example1" onclick="forgotpassword()"></td></tr>
											</table>
											<tr><td height="20"></td></tr>
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
					<jsp:include page = "../../footer.jsp" />
				</td>
			</tr>                        
			<!-- footer Ends Here -->
		</table>
<input type="hidden" name="publicUrl" id="publicUrl" value="<%=publicUrl%>"/>
<input type="hidden" name="keyword" id='keyword' value="<%=keyword%>">
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
	 $('#scrollpopup').hide();	 
});


(function() {
    var po = document.createElement('script'); po.type = 'text/javascript'; po.async = true;
      po.src = '/bookbattery/js/fetchdropdownvalues.js?<%=context.getInitParameter("fetchdropdownvalues")%>';
    var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(po, s);
  })();
if (/MSIE (\d+\.\d+);/.test(navigator.userAgent))
{
	var ieversion=new Number(RegExp.$1)
	if (ieversion > 9)
	{
		(function() {
		var po = document.createElement('script'); po.type = 'text/javascript'; po.async = true;
		po.src = '/bookbattery/js/jqFancyTransitions.1.8.min.js';
		var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(po, s);
		})();
	} 
}
browsername=navigator.appName;
if ((browsername.indexOf("Netscape")!=-1) || (browsername.indexOf("Opera")!=-1))
{

		(function() {
		var po = document.createElement('script'); po.type = 'text/javascript'; po.async = true;
		po.src = '/bookbattery/js/jqFancyTransitions.1.8.min.js';
		var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(po, s);
		})();
}
//]]>
function login()
{
	var un=document.batterylogin.usrname.value;
	var passwd=escape(document.batterylogin.password.value);
	var pwd=passwd.replace(/\+/g, '%2B');
	if(un == "")
	{
		errMsg ="<font color='#ff3333'>Please enter user name.</font>";
		document.getElementById("displyloginsesmsg").innerHTML=errMsg;
		document.getElementById("usrname").focus();
		return ;
	}
	if(pwd == "")
	{
		errMsg ="<font color='#ff3333'>Please enter password.</font>";
		document.getElementById("displyloginsesmsg").innerHTML=errMsg;
		document.getElementById("password").focus();
		return ;
	}
	document.batterylogin.method="post";
	document.batterylogin.action="/bookbattery/servlet/UserLoginDetails?hidWhatToDo=batteryuserlogin&useremailid="+un+"&password="+pwd;
	//alert(document.batteryadminlogin.action+"document.batterylogin.action");
	document.batterylogin.submit();
}
function forgotpasswordlink()
{
	var varChk = document.batterylogin.elements["forgotpass"].checked;
	document.getElementById("displayemailerrormsg").innerHTML="";
	document.batterylogin.emailid.value="";
	if(varChk==true)
	{
		$("#forgotdiv").show();
		document.batterylogin.emailid.focus();
	}
	else
	{
		$("#forgotdiv").hide();
	}
}
function forgotpassword()
{
	var emailid = document.batterylogin.emailid.value;

	if (emailid=="")
	{
		errMsg ="<font color='#FF3300' class='vrb10'>Please Enter EmailID.</font>";
		document.getElementById("displayemailerrormsg").innerHTML=errMsg;
		document.batterylogin.emailid.focus();
		return;
	}
	var emailidat = "@";
	var emailiddot = ".";
	var lat = emailid.indexOf(emailidat);
	var emailidlength = emailid.length;
	var ldot = emailid.indexOf(emailiddot);
	if (emailid.indexOf(emailidat) == -1)
	{
		errMsg ="<font color='#FF3300' class='vrb10'>The Email-id should be in the form of username@gmail.com</font>";
		document.getElementById("displayemailerrormsg").innerHTML=errMsg;
		document.batterylogin.emailid.focus();
		return;
	}
	if (emailid.indexOf(emailidat) == -1 || emailid.indexOf(emailidat) == 0 || emailid.indexOf(emailidat) == emailidlength)
	{
		errMsg ="<font color='#FF3300' class='vrb10'>IThe Email-id should be in the form of username@gmail.com</font>";
		document.getElementById("displayemailerrormsg").innerHTML=errMsg;
		document.batterylogin.emailid.focus();
		return;
	}
	if (emailid.indexOf(emailiddot) == -1 || emailid.indexOf(emailiddot) == 0 || emailid.indexOf(emailiddot) == emailidlength)
	{
		errMsg ="<font color='#FF3300' class='vrb10'>The Email-id should be in the form of username@gmail.com</font>";
		document.getElementById("displayemailerrormsg").innerHTML=errMsg;
		document.batterylogin.emailid.focus();
		return;
	}
	if (emailid.indexOf(emailidat, (lat + 1)) != -1)
	{
		errMsg ="<font color='#FF3300' class='vrb10'>The Email-id should be in the form of username@gmail.com</font>";
		document.getElementById("displayemailerrormsg").innerHTML=errMsg;
		document.batterylogin.emailid.focus();
		return;
	}
	if (emailid.substring(lat - 1, lat) == emailiddot || emailid.substring(lat + 1, lat + 2) == emailiddot)
	{
		errMsg ="<font color='#FF3300' class='vrb10'>The Email-id should be in the form of username@gmail.com</font>";
		document.getElementById("displayemailerrormsg").innerHTML=errMsg;
		document.batterylogin.emailid.focus();
		return;
	}
	if (emailid.indexOf(emailiddot, (lat + 2)) == -1)
	{
		errMsg ="<font color='#FF3300' class='vrb10'>The Email-id should be in the form of username@gmail.com</font>";
		document.getElementById("displayemailerrormsg").innerHTML=errMsg;
		document.batterylogin.emailid.focus();
		return;
	}
	if (emailid.indexOf(" ") != -1)
	{	
		 errMsg ="<font color='#FF3300' class='vrb10'>The Email-id should be in the form of username@gmail.com</font>";
		document.getElementById("displayemailerrormsg").innerHTML=errMsg;
		document.batterylogin.emailid.focus();
		return;
	}
	if (document.forms[0].emailid.valuelength > 50)
	{
		errMsg ="<font color='#FF3300' class='vrb10'>Please Enter Only 50 Characters in the E-mail field.</font>";
		document.getElementById("displayemailerrormsg").innerHTML=errMsg;
		document.batterylogin.emailid.focus();
		return;
	}	
	
	var xmlhttp= "";
	var resp= "";
	var url="/bookbattery/servlet/UserLoginDetails?hidWhatToDo=forgotpassword&emailid="+emailid;
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
				if(resp.indexOf("Invalid EmailID")>=0)
				{
					document.getElementById("displayemailerrormsg").innerHTML=xmlhttp.responseText;
					document.getElementById("displayemailerrormsg").innerHTML=resp;
				}
				else
				{
					document.getElementById("displyloginsesmsg").innerHTML=xmlhttp.responseText;
					document.getElementById("displyloginsesmsg").innerHTML=resp;
				}
			}
		}	
	}
	xmlhttp.open("GET",url,true);
	xmlhttp.send();	
	document.getElementById("displayemailerrormsg").innerHTML="";
}
</script>
</body>
</html>
