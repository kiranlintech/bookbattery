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

<!-- On load images starts  -->

<style>
.no-js #loader { display: none;  }
.js #loader { display: block; position: absolute; left: 100px; top: 0; }
.se-pre-con {
	position: fixed;
	left: 0px;
	top: 0px;
	width: 100%;
	height: 100%;
	z-index: 9999;
	background: url(/bookbattery/images/loader/Preloader_3l.gif) center no-repeat #fff;
content: "Joe's Task:"
}
</style>

<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.5.2/jquery.min.js"></script>
<script>
	//paste this code under head tag or in a seperate js file.
	// Wait for window load
	$(window).load(function() {
		// Animate loader off screen
		$(".se-pre-con").fadeOut("slow");;
	});
</script>

<!-- On load images ends  -->

<link href="../../css/bookbattery.css" rel="stylesheet" type="text/css" />
<link rel="shortcut icon" href="../../images/favicon.png" type="image/x-icon"/>
<script src="../../js/jquery-1.3.2.min.js" type="text/javascript"></script>
<script language="JavaScript" src="../../js/pophide.js" type="text/javascript"></script>
<script language="JavaScript" src="../../js/jquery-1.8.2.js" type="text/javascript"></script>
 <!-- Google Fonts -->
    <link href='http://fonts.googleapis.com/css?family=Titillium+Web:400,200,300,700,600' rel='stylesheet' type='text/css'>
    <link href='http://fonts.googleapis.com/css?family=Roboto+Condensed:400,700,300' rel='stylesheet' type='text/css'>
    <link href='http://fonts.googleapis.com/css?family=Raleway:400,100' rel='stylesheet' type='text/css'>
	
	
    <link rel="stylesheet" href="../../css/reset.css">
	<link rel="stylesheet" href="../../css/set2.css">
	<link rel='stylesheet prefetch' href='http://fonts.googleapis.com/css?family=Playball'>
   
    <!-- Bootstrap -->
    <link rel="stylesheet" href="../../css/bootstrap.min.css">
    <!-- Font Awesome -->
    <link rel="stylesheet" href="../../css/font-awesome.min.css">
	<!-- Custom CSS -->
    <link rel="stylesheet" href="../../css/owl.carousel.css">
    <link rel="stylesheet" href="../../css/style.css">
	<link rel="stylesheet" href="../../css/style1.css">
	<link rel="stylesheet" href="../../css/responsive.css">
<!--js and css code starts here  for loading popup added by jhansi-->
	<link rel="stylesheet" href="../../css/colorbox.css" />
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
	<script src="../../js/jquery.colorbox.js"></script>
	<!--js and css code ends here  for loading popup added by jhansi-->
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>BookBattery - India's No.1 Online Car/Inverter Branded Battery Store</title>
<meta name='description' content="Order and Get Genuine Car Battery or Inverter Battery or Bike Battery.  BookBattery is India's No.1 Online Battery Store. Best Discount Prices with Free Installation and Original Warranty is Offered" />
<meta name='keywords' content='Battery, Batteries, Car Battery, Inverter Battery, Bike Battery, Branded Battery, Buy Battery online, Car Battery Online' />
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
table{font-size:inherit;font:100%;}pre,code,kbd,samp,tt{font-family:monospace;*font-size:108%;line-height:100%;}

td.loading_image{background:  url('../../images/loader.gif');width:100px; /* use you own image size; */ 
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
.divmobile{left:22.5%;top:15%;font: 16px/22px 'Trebuchet MS', Verdana, Arial; text-align:left; border:6px solid #424242; border-radius: 6px 6px 6px 6px;position:absolute;padding: 1px;display: none;z-index:60;background-color: white;}

.divmobilelaptop{left:37.5%;top:15%;font: 16px/22px 'Trebuchet MS', Verdana, Arial; text-align:left; border:6px solid #424242; border-radius: 6px 6px 6px 6px;position:absolute;padding: 1px;display: none;z-index:60;background-color: white;}

.knowmore{font-family:Verdana;font-size:11px;color: #FF8C00;text-decoration:none;}
.knowmore:hover{font-family:Verdana;font-size:11px;color:#FF8C00;text-decoration:underline;}

.title{font-family:Verdana;font-size:10px;font-weight:bold;color: #FF8C00;text-decoration:none;}
.title:hover{font-family:Verdana;font-size:10px;font-weight:bold;color:#FF8C00;text-decoration:underline;}
/*]]>*/
</style>
</head>
<body>

<!-- On load images  -->

<div class="se-pre-con"></div>
<!-- Start Alexa Certify Javascript -->
<script type="text/javascript">
_atrk_opts = { atrk_acct:"VrG0j1a4ZP00yt", domain:"BookBattery.com",dynamic: true};
(function() { var as = document.createElement('script'); as.type = 'text/javascript'; as.async = true; as.src = "https://d31qbv1cthcecs.cloudfront.net/atrk.js"; var s = document.getElementsByTagName('script')[0];s.parentNode.insertBefore(as, s); })();
</script>
<noscript><img src="https://d5nxst8fruw4z.cloudfront.net/atrk.gif?account=VrG0j1a4ZP00yt" style="display:none" height="1" width="1" alt="" /></noscript>
<!-- End Alexa Certify Javascript -->
<form name="batterylogin" action="">
	<div id='divmobile' class='divmobile' style="display:none;"></div>

	<table width="950" border="0" class="insidecontent c24" align="center" cellspacing="0" cellpadding="0" style="margin-top: 15px;margin-left: 101px;width: 85%;">
			<tr>
				<td height="2"><div id='scrollpopup'  style="display:block;"><table width="100%" border="0" align="center" valign="top"><tr><td align='right' class='loading_image'></td></tr><tr><td align='center' style='font-size:12px;color:#000000; text-decoration:none;padding:1px 1px;'>Please wait page is loading....</td></tr></table></div></td></td>
			</tr>
			<div class="mainmenu-area" style="margin-top: -15px;;position: fixed;">
        <div class="container">
            <div class="row">
                <div class="col-md-4">
                    <div class="logo">
                        <a href="../../index.jsp"><img src="../../images/bookbatterylogo.png" alt="Amaron online genuine car battery or inverter battery or bike battery or bus battery or tractor battery or truck battery store in india."></a>
                    </div>
                </div>
				 <div class="col-md-4">
                    <div class="logo">
                         <img src="../../images/callsg.png" alt="Call Now to order for car battery or inverter battery or bike battery or bus battery or tractor battery or truck battery" style="padding-top: 15px;">
                    </div>
                </div>
                <div class="col-md-4">
                    <ul class="nav navbar-nav navbar-right">
                        <li><a href="../../index.jsp"><i class="fa fa-home"></i> Home</a></li>
                        <li><a href="../../jsp/login/login.jsp"><i class="fa fa-sign-in"></i> Log In</a></li>
                    </ul>
                </div>  
            </div>
        </div>

		
		<!--Starts Top Row i.e, why shop with BookBattery -->
		<div class="container" style="width:1150px;padding-top: 25px;">
			<div class="row" style='border: 1px;border-style: solid;border-color: #B5AFAF;background-color: #fbfbfb;border-radius: 3px;height: 35px;   padding-top: 5px;'>
                <div class="col-md-11">
				
                    <div class="user-menu" style="width:1150px;">
                        <ul>
						<span class="primary" style="font-size: 16px;color: #424040; " ><i class="fa fa-thumbs-o-up"></i><strong> Why Shop With BookBattery</strong>&nbsp</span>
                        <span class="primary" style="font-size: 14px;color: #424040; border-right-style: dotted;border-right-width: 1px;    border-right-color: #756969;padding-right: 6px;padding-left: 3px;"  ><i class="fa fa-user"></i> Free Professional Installation&nbsp </span>
                        <span class="primary" style="font-size: 14px;color: #424040; border-right-style: dotted;border-right-width: 1px;    border-right-color: #756969;padding-right: 6px;padding-left: 3px;"  >&nbsp;<i class="fa fa-truck"></i> Free Delivery&nbsp </span>
                        <span class="primary" style="font-size: 14px;color: #424040; border-right-style: dotted;border-right-width: 1px;    border-right-color: #756969;padding-right: 6px;padding-left: 3px;" >&nbsp;<i class="fa fa-inr"></i> Cash On Delivery&nbsp </span>
                        <span class="primary" style="font-size: 14px;color: #424040; border-right-style: dotted;border-right-width: 1px;    border-right-color: #756969;padding-right: 6px;padding-left: 3px;" >&nbsp;<i class="fa fa-cog"></i> Genuine Battery&nbsp </span>
                        <span class="primary" style="font-size: 14px;color: #424040; border-right-style: dotted;border-right-width: 1px;    border-right-color: #756969;padding-right: 6px;padding-left: 3px;" >&nbsp;<i class="fa fa-clock-o"></i> 24x7 Service&nbsp </span>
						<span class="primary" style="font-size: 14px;color: #424040; padding-right: 6px;padding-left: 3px;" >&nbsp;<i class="fa fa-heart"></i> Best Prices&nbsp </span>
                        </ul>
                    </div>
                </div>
            </div>
		</div>
<!--Ends Top Row i.e, why shop with BookBattery -->
    <div class="product-big-title-area">
        <div class="container">
            <div class="row">
                <div class="col-md-12">
                    <div class="product-bit-title text-center">
                        <h2><i class="fa fa-sign-in"></i> Log In</h2>
                    </div>
                </div>
            </div>
        </div>
    </div>
			<tr>
				<td>
					<table border="0" align="left"  width="100%" bgcolor="#FFFFFF" cellpadding="0" cellspacing="0" >
						<tr>
							<td>								
								<table border="0" width="100%" style='border: 1px solid #ff8c00;border-radius: 6px 6px 6px 6px;margin-top: 193px;'>
								<tr>
									<td>
									<table class='insidecontent' valign="top" align='center' cellspacing='0'  cellpadding='0' width='100%' border='0' >
										<tr>
											<td width="100%" class="insidecontent" align="left"><a class="insidecontent" href="../../index.jsp"><img src="../../images/back25.png" alt="order car battery online" border="0" width="25" height="25"/></a></td>
										</tr>
									</table>
									</td>
									</tr>
									</br>
									<tr>
										<td valign="top" width="70%" align="center">
											<table class='insidecontent' valign="top" align='center' height="100" cellspacing='0'  cellpadding='0' width='50%' border='0' >
											<tr><td align="center" class="insidecontent"><div id="displyloginsesmsg"></div></td></tr>
											<div class="product-big-title-area">
											<tr>
												<td width='30%' height='20' colspan='5' align='center' valign='middle' class='insidecontent' style="padding-left:5px;">
												<font size='2' color='black' style='font-size:15px;'>Please enter email id and password to log in<b></font></td>
											</tr>
											<tr height='10px'>
												<td align='center' valign='middle' class='insidecontent'><font color='#FFFFFF' size='2px'><input class='insidecontent' type='text' size='15' style='width:395px;height:40px;border: 2px solid #CCC;font-size: 13px;font-family: Verdana;border-radius: 0px;' placeholder="Email ID" name='usrname'  id='usrname'  id='usrname'  MAXLENGTH='50' tabindex='1'  autocomplete='on' VCARD_NAME='vCard.Email' /></font><br>&nbsp;</td></tr>
											<tr>
												<td align='center' valign='middle' class='insidecontent' style="padding-bottom:0px;"><font color='#FFFFFF' size='2px'><input class='insidecontent' type='password' onkeypress='searchKeyPress(event);' onkeydown="if ((event.which && event.which == 13) || (event.keyCode && event.keyCode == 13)){javascript:login();return false;} else return true;" id="password"  name='password' MAXLENGTH='50' tabindex='2' size='15' style='width:395px;height:40px;border: 2px solid #CCC;font-size: 13px ;border-radius: 0px;' placeholder="Password" /></td></tr>
												<![endif]-->	
											<tr>
												<td align='center' valign="top" style="padding-top:9px;">
												<input type="button" href='#' value="Login" id="signupbutton" class="button_example" onclick="login()" onkeypress='searchKeyPress(event);' tabindex="0"></td>
											</tr>	
											<tr><td height="20"></td></tr>
											<tr><td align='center' style="padding-left:2px;"><input type="checkbox" name="forgotpass" onclick="javascript:forgotpasswordlink();" ><font color='#999999' style='padding-right:0px;font-size:12px;font-family:Verdana;'>forgot&nbsp;your&nbsp;password?</font></td></tr>
											<table id="forgotdiv" width="43%" align="center" border="0" style="display:none;">
												<tr><td align="center" class="insidecontent"><div id="displayemailerrormsg"></div></td></tr>
												<tr><td align="center" style="font-family:Verdana;font-size:12px;font-weight:bold;color:#010101;text-decoration:none;padding:1px 1px;">Enter Your EmailID</td></tr>
												<tr><td align="center"><input class='insidecontent' type='text' style='width:250px;height:30px;border: 2px solid #CCC;font-size: 13px;font-family: Verdana;border-radius: 4px 4px 4px 4px;' placeholder="Email ID" name='emailid'  id='emailid' MAXLENGTH='50'/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" href='#' value="Submit" id="forgotpasswordbutton" class="button_example1" onclick="forgotpassword()"></td></tr>
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
					
				</td>
			</tr>                        
			<!-- footer Ends Here -->
		</table>
		<jsp:include page = "../../footer.jsp" />
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
      po.src = '../../js/fetchdropdownvalues.js?<%=context.getInitParameter("fetchdropdownvalues")%>';
    var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(po, s);
  })();
if (/MSIE (\d+\.\d+);/.test(navigator.userAgent))
{
	var ieversion=new Number(RegExp.$1)
	if (ieversion > 9)
	{
		(function() {
		var po = document.createElement('script'); po.type = 'text/javascript'; po.async = true;
		po.src = '../../js/jqFancyTransitions.1.8.min.js';
		var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(po, s);
		})();
	} 
}
browsername=navigator.appName;
if ((browsername.indexOf("Netscape")!=-1) || (browsername.indexOf("Opera")!=-1))
{

		(function() {
		var po = document.createElement('script'); po.type = 'text/javascript'; po.async = true;
		po.src = '../../js/jqFancyTransitions.1.8.min.js';
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
	document.batterylogin.action="../../servlet/UserLoginDetails?hidWhatToDo=batteryuserlogin&useremailid="+un+"&password="+pwd;
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
	var url="../../servlet/UserLoginDetails?hidWhatToDo=forgotpassword&emailid="+emailid;
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
