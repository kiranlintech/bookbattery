<%@ page language="java"%>
<%@ page import="java.io.*"%>
<%
	String strUserid=(session.getAttribute("sesBatteryOperatorName"))!=null?(String)session.getAttribute("sesBatteryOperatorName"):"";

	session.invalidate();
	response.sendRedirect("../../jsp/operator/operatorlogin.jsp");
%>