<%-- 
Document   : logout.jsp
Created on : April 30 2013, 02:05:00 PM
Author     : Prakash M.
Description: This JSP is used logout validation from the admin portal.
--%>

<%@ page language="java"%>
<%@ page import="java.io.*"%>
<%
	String strUserid=(session.getAttribute("sesBatteryUserLogin"))!=null?(String)session.getAttribute("sesBatteryUserLogin"):"";

	session.invalidate();
	response.sendRedirect("../../index.jsp");
%>