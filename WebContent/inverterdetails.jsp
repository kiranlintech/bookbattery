<%@ page language="java" import="java.sql.*"%>

<%@page language="java" import="java.io.File,com.ngit.javabean.loglevel.*,java.util.Properties,javax.servlet.ServletContext,java.util.Properties,java.io.FileInputStream"%>

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
	String Bookbatteryservice = (propsMOPConfig.getProperty("Bookbatteryservice")!=null)?propsMOPConfig.getProperty("Bookbatteryservice"):"";
	String baseurldirectory = (propsMOPConfig.getProperty("baseurldirectory")!=null)?propsMOPConfig.getProperty("baseurldirectory"):"";

	String serverURL = (propsMOPConfig.getProperty("serverURL")!=null)?propsMOPConfig.getProperty("serverURL"):"";
%>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html>
  <head>
	<meta http-equiv="Refresh" content="0; url=<%=publicUrl%>/bookbattery/manufacturer/Inverter/">
  </head>
  <body>
  </body>
</html>
<!-- On load images ends  -->
