<%-- 
Document   		 : includes_css.jsp
Created on 		 : Sep 15, 2016, 10:14:12 AM
Author     		 : BookBattery
Copyright Notice : BookBattery Confidential.
Document         : This jsp is used as Common CSS Jsp used in all over the Front end.
--%>
 
<%@page language="java" import="java.io.File,java.util.Hashtable,java.util.Vector,com.ngit.javabean.loglevel.*,java.util.Enumeration,java.util.Properties,java.io.FileInputStream,javax.servlet.ServletContext"%>
<%
Properties propsMOPConfig = new Properties();
ServletContext context = getServletContext();
FileInputStream fin1      = new FileInputStream(new File(context.getRealPath("properties/bookbatteryconfig.properties"))); 
propsMOPConfig.load(fin1); 
fin1.close(); 
String publicUrl = (propsMOPConfig.getProperty("publicUrl")!=null)?propsMOPConfig.getProperty("publicUrl"):"";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!--

www.BookBattery.com reserves all of our rights, including but not limited to any and all copyrights, 
trademarks, patents, trade secrets, and any other proprietary right that we may have 
in our web site, its content, and the goods and services that may be provided. The 
use of our rights and property requires our prior written consent. We are not 
providing you with any implied or express licenses or rights by making services 
available to you and you will have no rights to make any commercial uses of our 
web site or service without our prior written consent.

Contents of this webpage can't be seen as they are not meant to be viewed or copied.

Any violator will be prosecuted to the full extent of law and may face civil and criminal
charges and huge monetary fines. You are warned! Beware!

-->

<!-- Thanks for showing interest on my CODE, Ping me at contact@BookBattery.com -->

<meta charset="UTF-8">

<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/html5shiv/3.7.3/html5shiv.js"></script>
<script>
  (function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
  (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
  m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
  })(window,document,'script','https://www.google-analytics.com/analytics.js','ga');

  ga('create', 'UA-45977372-1', 'auto');
  ga('send', 'pageview');

</script>
<!-- Mobile Specific -->
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
<meta name="theme-color" content="#323232" />
<!-- Windows Phone -->
<meta name="msapplication-navbutton-color" content="#323232">
<!-- iOS Safari -->
<meta name="apple-mobile-web-app-status-bar-style" content="#323232">
<!-- CSS Style -->
<link rel="stylesheet" href="<%=publicUrl%>/bookbattery/dev/includes/css/bootstrap.min.css?v=2" type="text/css">
<link rel="stylesheet" href="<%=publicUrl%>/bookbattery/dev/includes/css/slider.css?v=1" type="text/css">
<link rel="stylesheet" href="<%=publicUrl%>/bookbattery/dev/includes/css/owl.carousel.css?v=1" type="text/css">
<link rel="stylesheet" href="<%=publicUrl%>/bookbattery/dev/includes/css/owl.theme.css?v=1" type="text/css">
<link rel="stylesheet" href="<%=publicUrl%>/bookbattery/dev/includes/css/font-awesome.css?v=2" type="text/css">
<link rel="stylesheet" href="<%=publicUrl%>/bookbattery/dev/includes/css/style.css?v=30" type="text/css">
<link rel="stylesheet" href="<%=publicUrl%>/bookbattery/dev/includes/css/custom.css?v=31" type="text/css">
<link rel="stylesheet" href="<%=publicUrl%>/bookbattery/dev/includes/css/filter.css?v=2" type="text/css">
<link rel="stylesheet" href="<%=publicUrl%>/bookbattery/dev/includes/js/select2/css/select2.min.css?v=9" type="text/css">
<link rel="stylesheet" href="<%=publicUrl%>/bookbattery/css/googlefonts.css" type="text/css">
 