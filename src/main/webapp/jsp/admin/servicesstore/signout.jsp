<%@ page language="java"%>
<%@ page import="java.io.*"%>
<%
	String strUserid=(session.getAttribute("sesBatteryAdminName"))!=null?(String)session.getAttribute("sesBatteryAdminName"):"";

	session.invalidate();
	response.sendRedirect("/bookbattery/jsp/admin/batterystore/batteryadminlogin.jsp");
%>