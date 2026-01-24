<%--
    Document   : batteryadminmain
    Created on : Aug 30, 2013, 4:22:12 PM
    Author     : Prasanna Kumari C.
--%>
<%@ page language="java" import="java.sql.*"%>
<%@page language="java" import="java.io.File,java.text.*,java.util.Calendar,java.util.ArrayList,java.util.Hashtable,java.util.Vector,com.ngit.javabean.loglevel.*,java.util.Date,java.util.Properties,java.util.*,com.ngit.javabean.loglevel.LogLevel,javax.servlet.ServletContext,java.util.Properties,java.io.FileInputStream"%>
<%response.setHeader("Pragma","no-cache"); response.setHeader("Cache-Control","no-store"); response.setHeader("Expires","0"); response.setDateHeader("Expires",-1); %>

<%
String strUserid=(String)session.getAttribute("sesStoreOperatorName");
if(strUserid==null)
{
	strUserid="";
	session.setAttribute("sesErrorMsg","Session Timed Out. Please login again");
	response.sendRedirect("../../../BookBatterystore/index.html");
	return;
}

Vector alist=(Vector)session.getAttribute("BatPriceVector");
LogLevel.DEBUG(1,new Throwable(),"alist: "+alist);
//out.println(alist);

%>
<style>

.battery_order_type {
    background: #28a745;
    color: white;
}

.label {
    display: inline;
    padding: .2em .6em .3em;
    font-size: 95%; 
    line-height: 1;
    color: #fff;
    text-align: center;
    white-space: nowrap;
    vertical-align: baseline;
    border-radius: .25em;
}
* {
    -webkit-box-sizing: border-box;
    -moz-box-sizing: border-box;
    box-sizing: border-box;
}

.card .body {
    font-size: 12px;
    color: #555;
    padding: 20px;
}
</style>
<!-- JQuery DataTable Css -->
<link href="/bookbattery/css/BookBatterystore/plugins/jquery-datatable/skin/bootstrap/css/dataTables.bootstrap.css" rel="stylesheet">

    
<!-- Exportable Table -->
<div class="row clearfix">
	<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
		<div class="card"> 
			<div class="body">
				<div class="table-responsive">
					<table class="table table-bordered table-striped table-hover dataTable js-exportable">
						<thead>
							<tr class="header bg-cyan">
								<th>Battery Model</th>
								<th>Brand</th>
								<th>MRP Price</th>
								<th>Without Old Battery Price</th>
								<th>Scrap Battery Price</th>
								<th>With Old Battery Price</th>
								<th>Capacity</th>
								
							</tr>
						</thead> 
						<tbody>
								<%
								if(alist!=null && alist.size() > 0)
								{
								%>	
									<%
										for(int i=0;i<alist.size();i++)
										{
											Hashtable ht=(Hashtable)alist.get(i);
											String bat_model = String.valueOf(ht.get("bat_model"));
											LogLevel.DEBUG(5,new Throwable(),"bat_model"+bat_model);
											String bat_brand=String.valueOf(ht.get("bat_brand"));
											String MRP = String.valueOf(ht.get("bat_act_price"));
											String discountprice = String.valueOf(ht.get("bat_witbat_price"));
											String scrap_price = String.valueOf(ht.get("bat_ret_price"));
											String capacity = String.valueOf(ht.get("bat_capacity")); 
											int Price_Temp_price = Integer.parseInt(discountprice);
											int Price_Temp_scrapprice = Integer.parseInt(scrap_price);
											
											int Price_Temp_obrpprice = (Price_Temp_price - Price_Temp_scrapprice);
											
											String Final_OBRP_price = Integer.toString(Price_Temp_obrpprice);
										%>	
									
									<tr>
										<td><%=bat_model%> </td>
										<td><%=bat_brand%> </td>
										<td><%=MRP%> </td>
										<td><%=discountprice%> </td>
										<td><%=scrap_price%> </td>
										<td><%=Final_OBRP_price%></td>
										<td><%=capacity%> </td>
									</tr>
							<%
								}
							%>
						<%
						} else {
						%>	
							
						<% 
						}
						%>	
			
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>
</div>
<!-- #END# Exportable Table -->
 
  
<!-- Jquery DataTable Plugin Js -->
<script src="/bookbattery/css/BookBatterystore/plugins/jquery-datatable/jquery.dataTables.js"></script>
<script src="/bookbattery/css/BookBatterystore/plugins/jquery-datatable/skin/bootstrap/js/dataTables.bootstrap.js"></script>
<script src="/bookbattery/css/BookBatterystore/plugins/jquery-datatable/extensions/export/dataTables.buttons.min.js"></script>
<script src="/bookbattery/css/BookBatterystore/plugins/jquery-datatable/extensions/export/buttons.flash.min.js"></script>
<script src="/bookbattery/css/BookBatterystore/plugins/jquery-datatable/extensions/export/jszip.min.js"></script>
<script src="/bookbattery/css/BookBatterystore/plugins/jquery-datatable/extensions/export/pdfmake.min.js"></script>
<script src="/bookbattery/css/BookBatterystore/plugins/jquery-datatable/extensions/export/vfs_fonts.js"></script>
<script src="/bookbattery/css/BookBatterystore/plugins/jquery-datatable/extensions/export/buttons.html5.min.js"></script>
<script src="/bookbattery/css/BookBatterystore/plugins/jquery-datatable/extensions/export/buttons.print.min.js"></script>
<!-- Custom Js -->
<script src="/bookbattery/css/BookBatterystore/js/pages/tables/jquery-datatable.js"></script>
