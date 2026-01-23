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

Vector alist=(Vector)session.getAttribute("sesMisOrdersVector");
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
							<tr class="header bg-cyan">
								<th>Order No</th>
								<th>Customer Name<br>Number<br/>Email</th>
								<th>Battery Type</th> 
								<th>Vehicle Make/ Model</th> 
								<th>Price</th>
 								<th>Location</th>
 								<th>Order Status</th>
 								<th>Order Reasons</th>
								<th>Payment Details</th>
 								<th>Ordered Date</th>
								<th>Updated Date</th>
								<th>Postponed Date</th>
								<th>Installed Date</th> 
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
																			
									String ordid=String.valueOf(ht.get("ord_id"));
									String ordnum=String.valueOf(ht.get("order_number"));
									String customer_name = String.valueOf(ht.get("consumer_name"));
									String customer_mobnumber = String.valueOf(ht.get("consumer_mobnumber"));
									String customer_emailid = String.valueOf(ht.get("consumer_emailid"));
									String customer_address = String.valueOf(ht.get("consumer_address1")); 
									String state = String.valueOf(ht.get("state"));
									String city = String.valueOf(ht.get("city"));
									String area = String.valueOf(ht.get("area"));
									String pincode = String.valueOf(ht.get("pincode"));
									String product_type = String.valueOf(ht.get("product_type"));
									String service_type = String.valueOf(ht.get("services_type"));
									String service_range = String.valueOf(ht.get("services_place"));
									String veh_name = String.valueOf(ht.get("veh_name"));
									String veh_model = String.valueOf(ht.get("veh_model"));
									String product_capacity = String.valueOf(ht.get("product_capacity"));
									String quantity = String.valueOf(ht.get("quantity")); 
									String price = String.valueOf(ht.get("service_price_mrp"));
									String discountprice = String.valueOf(ht.get("service_price_discount"));
									String payment_mode = String.valueOf(ht.get("payment_mode"));
									String payment_mode_type = String.valueOf(ht.get("payment_mode_type"));
									String service_commission_amount = String.valueOf(ht.get("service_commission_amount"));
									String order_status = String.valueOf(ht.get("order_status"));
									String order_reasons = String.valueOf(ht.get("order_reasons"));
									String order_agent_comments = String.valueOf(ht.get("order_agent_comments"));
									String creation_date = String.valueOf(ht.get("creation_date"));
									String updated_date = String.valueOf(ht.get("updated_date"));
									String postponed_date = String.valueOf(ht.get("postponed_date"));
 
							%>
								<% if(order_reasons.equals("installed")) {%>
									<tr class="bg-green">
									<% } else if(order_reasons.equals("Cancelled")) { %>
									<tr class="bg-red">
									<% } else if(order_status.equals("postponed")) { %>
									<tr class="bg-amber">
									<% } else if(order_reasons.equals("In Process")) { %>
									<tr class="bg-yellow" style="color:#000">
									<% } else { %>
									<tr class="background-color:#fff">
									<% } %>
									<td><button type="button" class="btn btn-primary btn-block waves-effect" data-trigger="focus" data-container="body" data-toggle="popover" data-placement="right" title="Order Details"  data-html="true" data-content="<strong>Payment Mode Ordered:</strong><%=payment_mode%><br /><strong>Payment Mode Installed:</strong><%=payment_mode_type%><br /><strong>Address:</strong><%=customer_address%>"><%=ordnum%></button></td>
									
									<td><%=customer_name%><br/><%=customer_mobnumber%><br/><%=customer_emailid%></td>
									<td>
									<%=product_type%> 
									
									<%if(service_type.equals("Jump Start"))
									{%>
										<span class="label bg-indigo"><%=service_type%></span>			
									<%
									}
									else if(service_type.equals("Health Check"))
									{%>
										<span class="label bg-pink"><%=service_type%></span>			
									<%
									}
									else
									{ %>
										<span class="label bg-green"><%=service_type%></span>						
									<%
									} 
									%>
									</td>
									<td>
									<%if(product_type.equals("Inverter Batteries"))
									{%>
										<%=product_capacity%>				
									<%
									}
									else
									{ %>
										<%=veh_name%><br/><%=veh_model%>					
									<%
									} 
									%>
									
									</td>
									 
									<td><strong>Price:</strong> <%=price%><br/><strong>Place:</strong><%=service_range%><br/><strong>QTY:</strong> <%=quantity%></td>
									<td><%=state%><br/><%=city%><br/><%=area%><br/><%=pincode%></td>
 									 <td>
									<%if(order_status.equals("confirmed"))
									{%>
										<span class="label label-default"><%=order_status%></span>						
									<%
									}
									else if(order_status.equals("installed"))
									{ %>
										<span class="label label-success"><%=order_status%></span>						
									<%
									} else if(order_status.equals("postponed"))
									{ %>
										<span class="label label-warning"><%=order_status%></span>						
									<%
									} else  if(order_status.equals("inprocess")){ %>
										
										<span class="label label-warning"><%=order_status%></span>	 
									<% } else
									
									 { %>
										
										<span class="label label-danger"><%=order_status%></span>	 
									<% }
									%>
									
									</td>
									<td><%=order_reasons %></td>
									<td><%=payment_mode%> </td>
									
									 
                                     <td><%=creation_date%></td>
									<td><%=updated_date%></td>
									<td><%=postponed_date%></td>
									<td><%=updated_date%></td>
								
								</tr>
							<%
								}
						} else 
						{
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
