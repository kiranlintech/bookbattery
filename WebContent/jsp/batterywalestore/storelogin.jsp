<%@ page language="java" import="java.sql.*"%>
<%@page language="java" import="java.util.ArrayList,java.util.Hashtable,java.util.Vector,com.ngit.javabean.loglevel.*,java.util.Enumeration,java.util.Properties,java.io.FileInputStream"%>
<%
String strUserid=(String)session.getAttribute("sesStoreOperatorName");
if(strUserid==null)
{
	strUserid="";
}
String sesErrorMsg=(String)session.getAttribute("sesErrorMsg");
if(sesErrorMsg==null)
{
	sesErrorMsg="";
}
else
{
	session.removeAttribute("sesErrorMsg");
}
%>

<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <title>Sign In | Bootstrap Based Admin Template - Material Design</title>
    <!-- Favicon-->
    <link rel="icon" href="/bookbattery/css/BookBatterystore/favicon.ico" type="image/x-icon">

    <!-- Google Fonts -->
    <link href="https://fonts.googleapis.com/css?family=Roboto:400,700&subset=latin,cyrillic-ext" rel="stylesheet" type="text/css">
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet" type="text/css">

    <!-- Bootstrap Core Css -->
    <link href="/bookbattery/css/BookBatterystore/plugins/bootstrap/css/bootstrap.css" rel="stylesheet">

    <!-- Waves Effect Css -->
    <link href="/bookbattery/css/BookBatterystore/plugins/node-waves/waves.css" rel="stylesheet" />

    <!-- Animation Css -->
    <link href="/bookbattery/css/BookBatterystore/plugins/animate-css/animate.css" rel="stylesheet" />

    <!-- Custom Css -->
    <link href="/bookbattery/css/BookBatterystore/css/style.css" rel="stylesheet">
</head>
	<script language="javascript">
function searchKeyPress(e)        
{   
	if (window.event) { e = window.event; }  
	if (e.keyCode == 13)
	{                        
		document.getElementById('btnSearch').click(); 
	}        
}
function check()
{
	var strUserid="<%=strUserid%>";
	if(strUserid=="null" || strUserid=="" || strUserid==null)
	{
		window.close();
		window.opener.location ="/bookbattery/css/BookBatterystore/index.html";
	}
	else
	{
	}
}

	</script>
<body class="login-page">
    <div class="login-box">
       
        <div class="card">
            <div class="body">
                <form name="storelogin">
                     <div class="logo" style="margin-left: 70px;">
						<a href="javascript:void(0);"> 
						
						<img src="../../images/BookBattery_logo.png" alt="BookBattery Store Login" class="img-responsive">
						
						</a>
					   
					</div>
					 <div class="msg"><h2 class="card-inside-title">BookBattery Store Login</h2></div>
					 <div class="col-md-6 col-lg-8 col-xs-12"> <%=sesErrorMsg%> </div>
                    <div class="input-group">
                        <span class="input-group-addon">
                            <i class="material-icons">person</i>
                        </span>
                        <div class="form-line">
                            <input type="text" class="form-control yes name" name="username" id="username" placeholder="Username" required autofocus>
							<div id='username-error'style="display:none;"></div>
                        </div>
                    </div>
                    <div class="input-group">
                        <span class="input-group-addon">
                            <i class="material-icons">lock</i>
                        </span>
                        <div class="form-line">
                            <input type="password" class="form-control yes" name="password" id="password" placeholder="Password" required onkeydown="if ((event.which && event.which == 13) || (event.keyCode && event.keyCode == 13)){javascript:login();return false;} else return true;" onkeypress="searchKeyPress(event);" placeholder="Enter Password">
							<div id='password-error'style="display:none;"></div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-8 p-t-5">
                            
                        </div>
                        <div class="col-xs-4">
                            <button class="btn btn-block bg-pink waves-effect"  id="btnSearch" type="button" onclick="SignIn()">SIGN IN</button>
                        </div>
                    </div>
                    <div class="row m-t-15 m-b--20">
                        <!--div class="col-xs-6">
                            <a href="sign-up.html">Register Now!</a>
                        </div>
                        <div class="col-xs-6 align-right">
                            <a href="forgot-password.html">Forgot Password?</a>
                        </div-->
                    </div>
                </form>
            </div>
        </div>
    </div>

    <!-- Jquery Core Js -->
    <script src="/bookbattery/css/BookBatterystore/plugins/jquery/jquery.min.js"></script>

    <!-- Bootstrap Core Js -->
    <script src="/bookbattery/css/BookBatterystore/plugins/bootstrap/js/bootstrap.js"></script>

    <!-- Waves Effect Plugin Js -->
    <script src="/bookbattery/css/BookBatterystore/plugins/node-waves/waves.js"></script>

    <!-- Validation Plugin Js -->
    <script src="/bookbattery/css/BookBatterystore/plugins/jquery-validation/jquery.validate.js"></script>

    <!-- Custom Js -->
    <script src="/bookbattery/css/BookBatterystore/js/admin.js"></script>
    <script src="/bookbattery/css/BookBatterystore/js/pages/examples/sign-in.js"></script>
	<script src="/bookbattery/css/BookBatterystore/js/validation.js"></script>

</body>

</html>