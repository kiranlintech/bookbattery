<%@ page language="java"%>
<%@ page import="java.io.*"%>
<%
	String strUserid=(session.getAttribute("sesServicesOperatorName"))!=null?(String)session.getAttribute("sesServicesOperatorName"):"";

	session.invalidate();
	response.sendRedirect("../../jsp/operator/operatorlogin.jsp");
%>