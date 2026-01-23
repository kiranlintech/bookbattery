<%--
    Document   : batteryadminmain
    Created on : Aug 30, 2013, 4:22:12 PM
    Author     : Prasanna Kumari C.
--%>
<%@ page language="java" import="java.sql.*"%>
<%@page language="java" import="java.util.ArrayList,java.util.Hashtable,java.util.Vector,com.ngit.javabean.loglevel.LogLevel"%>
<%
String strUserid=(String)session.getAttribute("sesStoreOperatorName");
String strStoreId=(String)session.getAttribute("sesstrStoreId");
String strStoreName=(String)session.getAttribute("sesstrStoreName");
if(strUserid==null)
{
	strUserid="";
	session.setAttribute("sesErrorMsg","Session Timed Out. Please login again");
	response.sendRedirect("../../../BookBatterystore/index.html");
	return;
}

Vector alist=(Vector)session.getAttribute("sesSalesServiceVector");
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
				<div class="table-responsive" style="height:350px;overflow:scroll; overflow-X:auto;  -webkit-overflow-scrolling: touch;">
					<table class="table table-bordered table-striped table-hover dataTable js-exportable">
						<thead>
							<tr class="header bg-pink">
								<th>S.No</th>
								<th>Date</th>
								<th>Customer Name</th> 
								<th>Price</th>  
								<th>Amount Paid by Customer</th>
 								<th>Qty</th>
								<th>Mode Of Payment</th> 
								<th>Battery Type</th>
								<th>Service Type</th>
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
											 
											String customer_name = String.valueOf(ht.get("consumer_name"));
											String price = String.valueOf(ht.get("service_price_discount"));
											String amount_paid_customer = String.valueOf(ht.get("amount_paid_customer")); 
 											String quantity = String.valueOf(ht.get("quantity")); 
											String payment_mode = String.valueOf(ht.get("payment_mode")); 
											String product_type = String.valueOf(ht.get("product_type")); 
											String services_type = String.valueOf(ht.get("services_type"));  
 											String installed_date = String.valueOf(ht.get("installed_date")); 
 											 
										   	int QTY_int = Integer.parseInt(quantity);
											int Price_Temp_price = Integer.parseInt(price); 
											String Final_Price_price = Integer.toString(Price_Temp_price*QTY_int);
 									%>	
									 	<td><%=i+1%></td>
										<td><%=installed_date%></td>
										<td><%=customer_name%></td>
										<td><%=Final_Price_price%></td>
										<td><%=amount_paid_customer%></td> 
 										<td><%=quantity%></td>
										<td><%=payment_mode%></td>
										<td><%=product_type%></td>
										<td><%=services_type%></td>
 										
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
 <script src="/bookbattery/css/BookBatterystore/js/pages/ui/tooltips-popovers.js"></script>
<!-- Custom Js -->
<script src="/bookbattery/css/BookBatterystore/js/pages/tables/jquery-datatable.js"></script>
