<%--
    Document   : batteryadminmain
    Created on : Aug 30, 2013, 4:22:12 PM
    Author     : Sai Krishna Daddala.
--%>
<%@ page language="java" import="java.sql.*"%>
<%@page language="java" import="java.util.ArrayList,java.util.Hashtable,java.util.Vector,com.ngit.javabean.loglevel.LogLevel"%>
<%
String strUserid=(String)session.getAttribute("sesStoreOperatorName");
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
							<tr class="header bg-blue-grey">
								<th>Order No</th>
								<th>Customer Name<br>Number<br/>Email</th>
								<th>Brand/Model</th> 
								<th>Capacity</th>
 								<th>Price</th>
								<th>Order Status</th>
								<th>Order Reasons</th>
								<th>Payment Details</th>
								<th>Update Status</th>
								<th>Comments</th>
								<th>Ordered Date</th>
								<th>Updated Date</th>
								<th>Postponed Date</th>
								<th>Installed Date</th>
								
								<th>Invoice</th>
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
											
											String ordid=String.valueOf(ht.get("order_id"));
											String ordnum=String.valueOf(ht.get("order_number"));
											String customer_name = String.valueOf(ht.get("consumer_name"));
											String customer_mobnumber = String.valueOf(ht.get("consumer_mobnumber"));
											String customer_emailid = String.valueOf(ht.get("consumer_emailid"));
											String customer_address = String.valueOf(ht.get("consumer_address")); 
											String state = String.valueOf(ht.get("state"));
											String city = String.valueOf(ht.get("city"));
											String area = String.valueOf(ht.get("area"));
											String pincode = String.valueOf(ht.get("pincode"));
											String inverter_type = String.valueOf(ht.get("inverter_type"));
											String inverter_brand = String.valueOf(ht.get("inverter_brand"));
											String inverter_model = String.valueOf(ht.get("inverter_model"));
											String inverter_capacity = String.valueOf(ht.get("inverter_capacity"));
											String quantity = String.valueOf(ht.get("quantity")); 
											String MRP_Price = String.valueOf(ht.get("MRP_Price"));
											String price = String.valueOf(ht.get("price"));
											String erp_pre_tax = String.valueOf(ht.get("erp_pre_tax"));
											String payment_mode = String.valueOf(ht.get("payment_mode"));
											String payment_mode_type = String.valueOf(ht.get("payment_mode_type"));
											String inverter_commission_amount = String.valueOf(ht.get("inverter_commission_amount"));
											String total_commission_amount = String.valueOf(ht.get("total_commission_amount"));
											String order_status = String.valueOf(ht.get("order_status"));
											String order_reasons = String.valueOf(ht.get("order_reasons"));
											String order_agent_comments = String.valueOf(ht.get("order_agent_comments"));
											String creation_date = String.valueOf(ht.get("creation_date"));
											String postponed_date = String.valueOf(ht.get("postponed_date"));
											String updated_date = String.valueOf(ht.get("updated_date"));
											
											int QTY_int = Integer.parseInt(quantity);
											int Price_Temp_price = Integer.parseInt(price);
 										
 											String Final_Price = Integer.toString(Price_Temp_price*QTY_int);
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
									<td><%=inverter_model%><br/>(<%=inverter_brand%>)</td>
									<td><%=inverter_capacity%></td>
 									<td>Rs.<%=price%>&nbsp;X&nbsp;<%=quantity%></a><br>=&nbsp;<%=Final_Price%>&nbsp;</td>
								 
									 <td>
									<%if(order_status.equals("confirmed"))
									{%>
										<span class="label label-default"><%=order_status%></span>						
									<%
									}
									else if(order_reasons.equals("installed"))
									{ %>
										<span class="label label-success"><%=order_status%></span>						
									<%
									} else if(order_status.equals("postponed"))
									{ %>
										<span class="label label-warning"><%=order_status%></span>						
									<%
									} else  if(order_reasons.equals("In Process")){ %>
										
										<span class="label label-warning"><%=order_status%></span>	 
									<% } else
									
									 { %>
										
										<span class="label label-danger"><%=order_status%></span>	 
									<% }
									%>
									
									</td>
									<td><%=order_reasons%></td>
									<td><%=payment_mode%> </td>
									<td> <select style='width:110px; height:30px;color:#000;' name="<%=ordid%>" id="<%=ordid%>" onChange="javascript:funtoUpdate('<%=ordid%>','<%=ordnum%>','<%=state%>','<%=city%>','<%=area%>','<%=payment_mode%>')"><option value=''>Update Status</option><option value='Customer Contacted'>Customer Contacted</option><option value='Customer Not Contacted'>Customer Not Contacted</option><option value='Postponed'>Postpone</option><option value='Repeated'>Repeated</option></select></td>
									<td><%=order_agent_comments%> </td>
									<td><%=creation_date%></td>
									<td><%=updated_date%></td>
									<td><%=postponed_date%></td>
									<td><%=updated_date%></td>
									<td><%if(order_status.equals("installed"))
										{%>
										<button class="btn btn-primary waves-effect" type="button" onclick="viewInvoice('<%=ordnum%>')">View Invoice</button>
										 
										<%
										}
										else
										{
										%>
										<button class="btn bg-teal waves-effect" type="button" onclick="generateInvoice('<%=ordnum%>')"><i class="material-icons">create</i> <span class="icon-name">Generate</span></button>
										<button class="btn bg-blue-grey waves-effect" type="button" style="margin-top:5px;" onclick="viewInvoice('<%=ordnum%>')"><i class="material-icons">file_download</i> <span class="icon-name">View</span></button>						
										<button class="btn bg-orange waves-effect" type="button" style="margin-top:5px;" onclick="CheckInvoice('<%=ordnum%>')"> <i class="material-icons">send</i><span class="icon-name">Send</span> </button>
										<%
										}
										%>
									</td>
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
