
<%@ page language="java" import="java.sql.*"%>
<%@page language="java" import="java.io.File,java.text.*,java.util.Calendar,java.util.ArrayList,java.util.Hashtable,java.util.Vector,com.ngit.javabean.loglevel.*,java.util.Date,java.util.Properties,java.util.*,com.ngit.javabean.loglevel.LogLevel,java.util.Properties,java.io.FileInputStream"%>
<%
String strUserid=(String)session.getAttribute("sesStoreOperatorName");
LogLevel.DEBUG(5,new Throwable(),"Prasanna strUserid: "+strUserid);
session.setAttribute("sesBatteryOperatorName","storeoperator");
LogLevel.DEBUG(5,new Throwable(),"Prasanna sesBatteryOperatorName: "+strUserid);
String strStoreId=(String)session.getAttribute("sesstrStoreId");
String strStoreName=(String)session.getAttribute("sesstrStoreName");
if(strUserid==null)
{
	strUserid="";
	session.setAttribute("sesErrorMsg","Session Timed Out. Please login again");
	response.sendRedirect("../../../BookBatterystore/index.html");
	return;
}
%>
<jsp:include page = "../storeheader.jsp" />
<jsp:include page = "../storeleftmenu.jsp" />
 <section class="content">
        <div class="container-fluid">
            <!-- Advanced Select -->
            <div class="row clearfix">
                <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
                    <div class="card">
                        <div class="header bg-brown">
                            <h2>
                               INVERTER ORDER DETAILS
                            </h2>
                        </div>
                        <div class="body">
                               <div class="row clearfix">
                                <div class="col-sm-4">
                                    <div class="form-group">
                                        <div class="form-line">
                                            <input type="text" class="datepicker form-control" id="fromdate" placeholder="Please choose a date..." readonly>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-sm-4">
                                    <div class="form-group">
                                        <div class="form-line">
                                            <input type="text" class="datepicker form-control" id="todate" placeholder="Please choose a date..." readonly>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-sm-4">
                                    <div class="form-group">
                                         <button type="button" class="btn bg-brown waves-effect" onclick="GetInverterOrderDetails()">
											<i class="material-icons">search</i>
											<span>GET ORDER DETAILS</span>
										</button>  
                                    </div>
                                </div>
                            </div>
                            <div class="row clearfix" id="misinverter_details"></div>
						</div>
                    </div>
                </div>
            </div>
            <!-- #END# Advanced Select -->
        </div>
    </section>
<input type="hidden" name="ordno" id="ordno" value="">
 <input type="hidden" name="store_id" id="store_id" value="<%=strStoreId%>">
 <input type="hidden" name="store_name" id="store_name" value="<%=strStoreName%>">
<div id="result" style="display:none"></div>
<div id="result1" style="display:none"></div>
  <!-- Small Size -->
<div class="modal fade" id="defaultModal" tabindex="-1" role="dialog">
   
</div> 	
<jsp:include page = "../storefooter.jsp" />		
<script type="text/javascript">

$('#fromdate').bootstrapMaterialDatePicker({ format : 'YYYY-MM-DD', weekStart : 0, time: false });
$('#todate').bootstrapMaterialDatePicker({ format : 'YYYY-MM-DD', weekStart : 0, time: false });
$('#postponeddate').bootstrapMaterialDatePicker({ format : 'YYYY-MM-DD', weekStart : 0, time: false });


function GetInverterOrderDetails(){
	
	var fromdate = $("#fromdate").val();
	var todate = $("#todate").val();
	$.ajax({
			type: "GET",
			url: "/bookbattery/servlet/BatterystoreMISReports?hidWhatToDo=inverterorderdetails",
			data: {todate: todate, fromdate: fromdate },
			success: function(data){
				//alert(data);
				$("#misinverter_details").html(data);
			}
	}); 
}

function funtoUpdate(orderid,ordernumber,state,city,area,payment_mode){
	
	var orderedstatus = document.getElementById(orderid).value;
	//alert(orderedstatus);

	if(orderedstatus=="Customer Contacted")
	{
		CustomerContacted(orderid,ordernumber,orderedstatus,state,city,payment_mode)
	}
	else if(orderedstatus=="Customer Not Contacted")
	{
		CustomernotContacted(orderid,ordernumber,orderedstatus)
	}
	else if(orderedstatus=="Postponed" || orderedstatus=="postponed")
	{
		Postponedvis(orderid,ordernumber,orderedstatus)
	}
	else if(orderedstatus=="Repeated")
	{
		Repeatedvis(orderid,ordernumber,orderedstatus)
	}
	else if(orderedstatus=="confirmed")
	{
		Ordertoretailer(orderid,ordernumber,orderedstatus,state,city,area,payment_mode)
	}
	
	else if(orderedstatus=="")
	{
	}
	else
	{
		var xmlhttp= "";
		var resp= "";
		var url ="/bookbattery/servlet/MISOperatorInverterDetails?hidWhatToDo=updateorderstatus&orderid="+orderid+"&ordernumber="+ordernumber+"&orderstatus="+orderedstatus;
		if (window.XMLHttpRequest)
		{
			// code for IE7+, Firefox, Chrome, Opera, Safari
			 xmlhttp=new XMLHttpRequest();
		}
		else
		{
			// code for IE6, IE5
			xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
		}
		xmlhttp.onreadystatechange=function()
		{
			if (xmlhttp.readyState==4)
			{
				if(xmlhttp.status!=200)
				{
					alert("Error Occured. Please try again.");
					return;
				}
				else 
				{
					resp = xmlhttp.responseText;
					//alert(resp);
					errMsg ="<table border='0' width='300px' height='10px' cellpadding='0' cellspacing='0' bgcolor='#88689f'><tr class='top1'><td>&nbsp;<font color='#FFFFFF'>BookBattery</td><td align='right'><a href='javascript:closemobdiv();'><img src=\"../../../images/Delete1.png \" border='0'></a></td></tr></table><table border='0' width='300px' height='2px' bgcolor='#FFFFFF' valign'top'><tr bgcolor='#FFFFFF'><td align='justify' bgcolor='#FFFFFF' style='font-family:Verdana;font-size:11px;color:#000000;'>"+resp+"</td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='GetInverterOrderDetails();' class='button4'><br></td></tr><tr height='15'></tr></table>";
					document.getElementById("divpostponed").innerHTML=""; 
					document.getElementById("divpostponed").style.display='block';
					document.getElementById("divpostponed").innerHTML=errMsg
					
				}
			}			
		}
		/*var agree=confirm("Are you sure want to update the Order Status! ");
		if (agree)
		{*/
			xmlhttp.open("GET",url, true);		
			xmlhttp.send();	
		/*}*/
		
	}
}

function CustomerContacted(orderid,ordernumber,orderedstatus,state,city,payment_mode){
	
	var PaymentModeClass="";
	if(payment_mode=="Online")
	{
		PaymentModeClass="hide";
	}else{
		// DO Noting
	}
	
	$.ajax
	({
		type: "GET",
		url: "/bookbattery/servlet/BatteryHome?hidWhatToDo=getarea",
		data: {city: city },
		success: function(data)
		{	
			$("#cusarea").html(data)
		}
	});

	$.ajax
	({
		type: "GET",
		url: "/bookbattery/servlet/MISOperatorBatteryDetails?hidWhatToDo=getretailerstoorder",
		data: {state: state,city: city },
		success: function(data)
		{	
			$("#retailertoorder").html(data)
		}
	});

	$.ajax
	({
		type: "GET",
		url: "/bookbattery/servlet/InvertersDetails?hidWhatToDo=getinverterbrand",
		data: {keyword:"Common"},
		success: function(data)
		{	
			$("#inverterbrand").html(data)
		}
	});
	var OrderLable="Update Order Status - Customer Contacted";
	
	var OrderMessage ="<div  class='row clearfix'><div class='form-group'><select name='orderreason'  id='orderreason' class='btn-group bootstrap-select form-control show-tick yes' onChange=\"javascript:setagentcomments('"+payment_mode+"')\"><option value='0' >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Select Reason</option><option value='installed' >Installed</option><option value='In Process' >In Process</option><option value='Cancelled' >Cancelled</option><option class='"+PaymentModeClass+"' value='Modify Battery Details'>Modify Battery Details</option> </select><div id='orderreason-error' style='display:none;'></div></div></div><div  class='row clearfix' height='10' id='modifybatdetls13' readonly><input type='text' value='"+state+"' name='constate' id='constate' style='display:none';></div><div  class='row clearfix' height='10' id='modifybatdetls14' readonly><input type='text' value='"+city+"' name='concity' id='concity' style='display:none';></div><div  class='row clearfix' id='modifybatdetls4' style='display:none;'><font color='#ff3333'>&nbsp;*&nbsp;</font>Select&nbsp;Brand</div><div  class='row clearfix' height='10' id='modifybatdetls5' style='display:none;'><select name='inverterbrand'  id='inverterbrand'  class='btn-group bootstrap-select form-control show-tick yes' onchange='javascript:getinvertercapacities();'><option value='0' >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;-- Select Brand--&gt;</option></select><div id='inverterbrand-error' style='display:none;'></div></div><div  class='row clearfix' id='modifybatdetls6' style='display:none;'><font color='#ff3333'>&nbsp;*&nbsp;</font>Select&nbsp;Capacity</div><div  class='row clearfix' height='10' id='modifybatdetls7' style='display:none;'><select name='invertercap'  id='invertercap'  class='btn-group bootstrap-select form-control show-tick yes' onchange='javascript:getinvertermodels();'><option value='0' >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;-- Select Capacity--&gt;</option></select><div id='invertercap-error' style='display:none;'></div></div><div  class='row clearfix' id='modifybatdetls10' style='display:none;'><font color='#ff3333'>&nbsp;*&nbsp;</font>Select&nbsp;Model</div><div  class='row clearfix' height='10' id='modifybatdetls11' style='display:none;'><select name='invertermodel'  id='invertermodel' class='btn-group bootstrap-select form-control show-tick yes' onchange='javascript:getinverterpricedetls();'><option value='0' >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;-- Select Model--&gt;</option></select><div id='invertermodel-error' style='display:none;'></div></div><div  class='row clearfix' id='modifybatdetls8' style='display:none;'><font color='#ff3333'>&nbsp;*&nbsp;</font>Price</div><div  class='row clearfix form-line' height='10' id='modifybatdetls9' style='display:none;'><input type='text' value='' class='form-control no-resize auto-growth yes' name='inverterprice' id='inverterprice'></div><div id='inverterprice-error' style='display:none;'></div><div  class='row clearfix'  class='Quantity_div  class='row clearfix' style='display:none;'><font color='#ff3333'>&nbsp;*&nbsp;</font>&nbsp;Enter&nbsp;Quantity </div> <div  class='row clearfix'  class='Quantity_div  class='row clearfix'' style='display:none;'> &nbsp;<input class='insidecontent' type='text' autocomplete='off' name='Quantity' id='Quantity' value='1' style='width:195px;height:20px;border: 2px solid #CCC;font-size: 13px;font-family: Verdana;border-radius: 4px 4px 4px 4px;' maxlength='50'></div><div id='confirmord11' class='row clearfix' style='display:none;'><select onchange='javascript:setagentcomments_payment();' name='payment_mode' id='payment_mode'  class='btn-group bootstrap-select form-control show-tick yes'><option value='0'>Select Payment Method</option><option value='Cash'>Cash</option><option value='Cheque'>Cheque</option><option value='Credit Card'>Credit Card</option><option value='Debit Card'>Debit Card</option><option value='UPI'>UPI</option><option value='Online Payment After Fitment'>Online Payment After Fitment</option></select><div id='payment_mode-error' style='display:none;'></div></div><div id='ratingid' style='display:none;'><div  class='row clearfix' ><select name='rating' id='rating'  class='btn-group bootstrap-select form-control show-tick yes'><option value='0'>Select Satisfaction Type</option> <option value='Satisfied'>Satisfied</option> <option value='Not Satisfied'>Not Satisfied</option></select><div id='rating-error' style='display:none;'></div></div><div class='row clearfix' ><select name='payment_collected' id='payment_collected'  class='btn-group bootstrap-select form-control show-tick yes'><option value='0'>Select Payment Collected</option><option value='Asistmi' selected>Collected by Asistmi</option></select><div id='payment_collected-error' style='display:none;'></div></div></div><div  class='row clearfix'><font color='#ff3333'>&nbsp;*&nbsp;</font>Enter&nbsp;Message</div><div class='row clearfix form-group'><div class='form-line'><textarea rows='1' class='form-control no-resize auto-growth yes' id='cusmessage' name='cusmessage' placeholder='Please type what you want'></textarea></div><div id='cusmessage-error' style='display:none;'></div></div>";

	var AfterOrder_PopUp="<div class='modal-dialog' role='document'><div class='modal-content'> <div class='modal-header'><h4 class='modal-title' id='after_order_popup_label'>"+OrderLable+"</h4><button type='button' onclick='okey_done()' class='close' data-dismiss='modal' aria-label='Close'><span aria-hidden='true'> x</span></button> 	</div> <div class='modal-body'> 	<div class='form-group'> <div id='after_order_popup_data'> "+OrderMessage+"</div> <div style='text-align: center;'><button type='button' class='btn bg-purple waves-effect' onclick=\"javascript:UpdateOrderStatus('"+orderid+"','"+ordernumber+"','"+orderedstatus+"')\">Update Status</button>&nbsp;&nbsp;&nbsp;<button type='button' class='btn bg-indigo waves-effect' data-dismiss='modal'>Cancel</button> </div> 	</div> </div> 	</div> </div>";
	$("#defaultModal").modal({show: true, backdrop: 'static', keyboard: false});
	$("#defaultModal").html(AfterOrder_PopUp);
	
}


/**************************Customer Not Contacted ************************/

function CustomernotContacted(orderid,ordernumber,orderedstatus){
	
	var OrderLable="Update Order Status - Customer Not Contacted";
	var OrderMessage ="<div class='row clearfix'><div class='col-sm-12'><div class='form-group'><p><b>Order Resason</b></p><div><select name='orderreason'  id='orderreason'  class='btn-group bootstrap-select form-control show-tick yes' onChange=\'javascript:setagentcomments('"+orderid+"','"+ordernumber+"','"+orderedstatus+"')\'><option value='0' >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Select Reason</option><option value='Phone Busy'>Phone Busy</option><option value='Phone Not Reachable'>Phone Not Reachable</option><option value='Phone Switched Off'>Phone Switched Off</option></select><div id='orderreason-error' style='display:none;'></div></div></div><div class='form-group'><div class='form-line'><textarea rows='1' class='form-control no-resize auto-growth yes' id='cusmessage' name='cusmessage' placeholder='Please type what you want'></textarea></div><div id='cusmessage-error' style='display:none;'></div></div></div></div>";
	 
	 var AfterOrder_PopUp="<div class='modal-dialog' role='document'><div class='modal-content'> <div class='modal-header'><h4 class='modal-title' id='after_order_popup_label'>"+OrderLable+"</h4><button type='button' onclick='okey_done()' class='close' data-dismiss='modal' aria-label='Close'><span aria-hidden='true'> x</span></button> 	</div> <div class='modal-body'> 	<div class='form-group'> <div id='after_order_popup_data'> "+OrderMessage+"</div> <div style='text-align: center;'><button type='button' class='btn bg-purple waves-effect' onclick=\"javascript:UpdateOrderStatus('"+orderid+"','"+ordernumber+"','"+orderedstatus+"')\">Update Status</button>&nbsp;&nbsp;&nbsp;<button type='button' class='btn bg-indigo waves-effect' data-dismiss='modal'>Cancel</button> </div> 	</div> </div> 	</div> </div>";
	$("#defaultModal").modal({show: true, backdrop: 'static', keyboard: false});
	$("#defaultModal").html(AfterOrder_PopUp);	
}

/** Function to show options to select if Repeatedvis **/
function Repeatedvis(orderid,ordernumber,orderedstatus)
{
	
	var OrderLable="Update Order Status - Repeated";
	//var OrderMessage="Repeated";
	
	
	var OrderMessage ="<div class='row clearfix'><div class='col-sm-12'><div class='form-group'><p><b>Order Resason</b></p><div class='form-line'><select name='orderreason' id='orderreason'  class='btn-group bootstrap-select form-control show-tick yes' onchange='javascript:setagentcomments()'><option value='0'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Select Reason</option><option value='Customer Has Placed Order For Twice'>Customer Has Placed Order For Twice</option></select><div id='orderreason-error' style='display:none;'></div></div></div><div class='form-group'><div class='form-line'><textarea rows='1' class='form-control no-resize auto-growth' id='cusmessage' name='cusmessage' placeholder='Please type what you want'></textarea></div><div id='cusmessage-error' style='display:none;'></div></div></div></div>";
	 
	 var AfterOrder_PopUp="<div class='modal-dialog' role='document'><div class='modal-content'> <div class='modal-header'><h4 class='modal-title' id='after_order_popup_label'>"+OrderLable+"</h4><button type='button' onclick='okey_done()' class='close' data-dismiss='modal' aria-label='Close'><span aria-hidden='true'> x</span></button> 	</div> <div class='modal-body'> 	<div class='form-group'> <div id='after_order_popup_data'> "+OrderMessage+"</div> <div style='text-align: center;'><button type='button' class='btn bg-purple waves-effect' onclick=\"javascript:UpdateOrderStatus('"+orderid+"','"+ordernumber+"','"+orderedstatus+"')\">Update Status</button>&nbsp;&nbsp;&nbsp;<button type='button' class='btn bg-indigo waves-effect' data-dismiss='modal'>Cancel</button> </div> 	</div> </div> 	</div> </div>";
	$("#defaultModal").modal({show: true, backdrop: 'static', keyboard: false});
	$("#defaultModal").html(AfterOrder_PopUp);	
} 
 
/********************Customer not contacted *****************/
/**********************Postponed Div ********************/
  
 /** Function to show options to select if customer postponed **/

function Postponedvis(orderid,ordernumber,orderedstatus)
{
	var OrderLable="Update Order Status - PostPoned";
	var OrderMessage ="<div class='row clearfix'><div class='col-sm-12'><div class='form-group'><p><b>Order Resason</b></p><div><select name='orderreason' id='orderreason'  class='btn-group bootstrap-select form-control show-tick yes' onchange='javascript:setagentcomments()'><option value='0'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Select Reason</option><option value='High Priority'>High Priority</option><option value='Customer is not picking the Call'>Customer is not picking the Call</option><option value='Customer Number Busy'>Customer Number is Busy</option><option value='Customer Number is Not Reachable'>Customer Number is Not Reachable</option><option value='Customer Number Switched Off'>Customer Number is Switched Off</option><option value='Customer is Out of Station'>Customer is Out of Station</option><option value='Customer is Not Responding to our Calls'>Customer is Not Responding to our Calls</option><option value='Customer need Installation Today'>Customer need Installation Tomorrow</option><option value='Need to check with franchisee whether the battery is currently available'>Franchisee is not having the Stock</option><option value='Customer Car Old battery is Working Fine need installation today'>Customer Car Old battery is Working Fine Presently</option><option value='Car Old Battery is working Fine He need installation Today'>Car Old Battery is working Fine, He donot need installation Now</option><option value='Yesterdays After Business Hour Order'>As it is not business hours, we will install the battery tomorrow</option><option value='Pitstop is So Far Customer will collect the battery whenever he is free'>Pitstop is So Far, Customer will collect the battery whenever he is free</option><option value='Customer need installation today'>Customer is busy today, he need installation later</option><option value='Customer need latest Manufacturing Battery'>Customer need latest Manufacturing Battery</option><option value='Order Status is not yet confirmed from Franchisee or Customer'>Order Status is not yet confirmed from Franchisee or Customer</option><option value='Customer will collect the battery today'>Customer will collect the battery tomorrow</option><option value='Customer will collect the battery today'>Customer Bike Old battery is Working Fine Presently</option><option value='Pitstop was on Leave Yesterday Need to process the order today'>Franchisee is on Leave Today</option></select><div id='orderreason-error' style='display:none;'></div></div></div><div class='form-group'><div class='form-line'><textarea rows='1' class='form-control no-resize auto-growth yes' id='cusmessage' name='cusmessage' placeholder='Please type what you want'></textarea></div><div id='cusmessage-error' style='display:none;'></div></div></div></div>";
	
	 
	 var AfterOrder_PopUp="<div class='modal-dialog' role='document'><div class='modal-content'> <div class='modal-header'><h4 class='modal-title' id='after_order_popup_label'>"+OrderLable+"</h4><button type='button' onclick='okey_done()' class='close' data-dismiss='modal' aria-label='Close'><span aria-hidden='true'> x</span></button> 	</div> <div class='modal-body'> 	<div class='form-group'> <div id='after_order_popup_data'> "+OrderMessage+"</div> <div style='text-align: center;'><button type='button' class='btn bg-purple waves-effect' onclick=\"javascript:UpdateOrderStatus('"+orderid+"','"+ordernumber+"','"+orderedstatus+"')\">Update Status</button>&nbsp;&nbsp;&nbsp;<button type='button' class='btn bg-indigo waves-effect' data-dismiss='modal'>Cancel</button> </div> 	</div> </div> 	</div> </div>";
	$("#defaultModal").modal({show: true, backdrop: 'static', keyboard: false});
	$("#defaultModal").html(AfterOrder_PopUp);	
}
 
 /*******************Div Ends***************************/
function UpdateOrderStatus(orderid,ordernumber,orderstatus){
	
	//alert(orderid);
	//alert(ordernumber);
	//alert(orderstatus);
	var retailertoorder = "";
	var inverterbrand = "";
	var invertercap = "";
	var inverterprice = "";
	var invertermodel="";
	var Quantity = "";
	var SMSURL = "";
	var SMSURL1 = "";
	var shorturl1 = "";
	var shorturl = "";
	var rating = "";
    var paymentcollected = "";
	var baseurl1 = "";
	var baseurl = "";
	var cusname="";
	var cusemailid="";
	var cusaddr1="";
	var cusaddr2="";
	var cusarea="";	
	var cusstate = ""; 
	var cuscity = ""; 
	var payment_mode = ""; 

	var iChars3 = "`~!@#$%^&*()+=[]\\\';/{}|\":<>?";
	var dot=".";
	
	
	if(orderstatus=="confirmed")
	{
		var orderreason = "Confirmed Order To Retailer";
		var cusmessage = "";
		//alert(orderreason);
		//alert(orderstatus);
	}
	else
	{
	
	var orderreason = $("#orderreason").val();
	if (selectValidation("","orderreason","select") == false)
	{
		return;
		
	} 
	var cusmessage = $("#cusmessage").val();
	if (descriptionValidation("","cusmessage","description") == false)
	{
		return;
		
	} 
	
			 
	}

	if(orderstatus=="postponed" || orderstatus=="Postponed")
	{
		var postponedate = $("#postponedate").val();
		if(postponedate == "")
		 {
			errMsg ="<font color='#9B5BDD'>Please select postpone date</font>";
			document.getElementById("displayrefinederrormsg1").innerHTML=errMsg;
			return ;
		 }
	 /** Validations starts here for postponed date **/
		var mySplitResult = postponedate.split("-");
		var postday=mySplitResult[0];
		var postmonth=mySplitResult[1];
		var postyear=mySplitResult[2];
		date = new Date();
		var month = date.getMonth()+1;
		var day = date.getDate();
		var year = date.getFullYear();

		if(postponedate == "")
		{
			$('#displayrefinederrormsg1').show();
			errMsg ="<font color='#9B5BDD'>Please select postpone date</font>";
			document.getElementById("displayrefinederrormsg1").style.display='block';
			document.getElementById("displayrefinederrormsg1").innerHTML=errMsg;
			return ;
		}
		else if((postday<day)&&(postyear<year))
		{
			$('#displayrefinederrormsg1').show();
			errMsg ="<font color='#9B5BDD'>Selected Date should be greater than Today's Date</font>";
			document.getElementById("displayrefinederrormsg1").style.display='block';
			document.getElementById("displayrefinederrormsg1").innerHTML=errMsg;
			return ;
			
		}
		else if((postday<day)&&(postmonth<=month)&&(postyear<=year))
		{
			$('#displayrefinederrormsg1').show();
			errMsg ="<font color='#9B5BDD'>Selected Date should be greater than Today's Date</font>";
			document.getElementById("displayrefinederrormsg1").style.display='block';
			document.getElementById("displayrefinederrormsg1").innerHTML=errMsg;
			return ;
			
		}
		else if((postday>day)&&(postmonth<month)&&(postyear<=year))
		{
			$('#displayrefinederrormsg1').show();
			errMsg ="<font color='#9B5BDD'>Selected Date should be greater than Today's Date</font>";
			document.getElementById("displayrefinederrormsg1").style.display='block';
			document.getElementById("displayrefinederrormsg1").innerHTML=errMsg;	
			return ;
		}	
		else if((postday>day)&&(postyear<year))
		{
			$('#displayrefinederrormsg1').show();
			errMsg ="<font color='#9B5BDD'>Selected Date should be greater than Today's Date</font>";
			document.getElementById("displayrefinederrormsg1").style.display='block';
			document.getElementById("displayrefinederrormsg1").innerHTML=errMsg;	
			return ;
		}
		else if(postday==day&&(postmonth<=month)&&(postyear<=year))
		{
			$('#displayrefinederrormsg1').show();
			errMsg ="<font color='#9B5BDD'>Selected Date should be greater than Today's Date</font>";
			document.getElementById("displayrefinederrormsg1").style.display='block';
			document.getElementById("displayrefinederrormsg1").innerHTML=errMsg;	
			return ;
		}
	}
	
	
	else if(orderstatus=="confirmed")
	{
		//alert(22);
		retailertoorder = $("#retailer").val();
		cusarea = document.getElementById("cusarea").val();
		
		//alert(retailertoorder);

		if(retailertoorder == "" || retailertoorder == "0" || retailertoorder == "default" || retailertoorder == "defaultss")
		{
			errMsg ="<font color='#9B5BDD'>Please Select Retailer</font>";
			document.getElementById("displayrefinederrormsg1").innerHTML=errMsg;
			$("#retailer").focus();
			return ;
		}
		
		if(cusarea == "" || cusarea == "0"  || cusarea == "default"  || cusarea == "defaultss" )
		{
			errMsg ="<font color='#9B5BDD'>Please Enter Customer Area</font>";
			document.getElementById("displayrefinederrormsg1").innerHTML=errMsg;
			$("#cusarea").focus();
			return ;
		}

	}
	
	
	else if(orderstatus=="Customer Contacted" && orderreason=="Regenerated to Another Retailer")
	{

		retailertoorder = $("#retailertoorder").val();
		if(retailertoorder == "" || retailertoorder == "0" || retailertoorder == "default" || retailertoorder == "defaultss")
		{
			errMsg ="<font color='#9B5BDD'>Please Select Retailer</font>";
			document.getElementById("displayrefinederrormsg1").innerHTML=errMsg;
			$("#retailertoorder").focus();
			return ;
		}

	}
	

	else if(orderstatus=="Customer Contacted" && orderreason=="installed")
	{
		//baseurl="http://192.168.1.95/bookbattery/";
		//baseurl="http://192.168.1.95/bookbattery/";
		baseurl="http://www.BookBattery.com/bookbattery/";
		
		//alert(baseurl);
		//alert("orderid:"+orderid);
		//var SMSURL="http://www.BookBattery.com/bookbattery/jsp/rating.jsp?phone=9108501361&email=rathaiahtulabandhula@gmail.com&ordernumber=ORDB4813098B";
		 SMSURL=baseurl+"jsp/rating.jsp?ordernumber="+ordernumber;
		 SMSURL1=baseurl1+"jsp/rating.jsp?ordernumber="+ordernumber;
		//alert(SMSURL);

		var ordernumbersplit=ordernumber.split("ORDI");
		
		SMSURL=baseurl+"jsp/customerreferral.jsp?refcode=REF"+ordernumbersplit[1];
		
		//alert(SMSURL);

		shorturl = document.getElementById("result").value; 
		shorturl1 = document.getElementById("result1").value; 
		
		//alert("shorturl"+shorturl);
		
		if(shorturl =="" || shorturl == undefined|| shorturl == "undefined")
		{
			makeRequest(SMSURL,orderid,ordernumber,orderstatus);
			return;
		}
		else
		{
		
		}
		
		if(shorturl1 =="" || shorturl1 == undefined|| shorturl1 == "undefined" || (shorturl1==shorturl))
		{
			//alert(22);
			//makeRequest1(SMSURL1,orderid,ordernumber,orderstatus);
			//return;
		}
		else
		{
		
		}

		//alert(23);
		
		payment_mode = $("#payment_mode").val();
		if (selectValidation("","payment_mode","select") == false)
		{
			return;
			
		} 
		rating = $("#rating").val();
		if (selectValidation("","rating","select") == false)
		{
			return;
			
		} 
		paymentcollected = $("#payment_collected").val();
		if (selectValidation("","payment_collected","select") == false)
		{
			return;
			
		} 
		
	}
	else if(orderstatus=="Customer Contacted" && orderreason=="Modify Battery Details")
	{
		inverterbrand = document.getElementById("inverterbrand").value; 
		if (selectValidation("","inverterbrand","select") == false)
		{
			return;
			
		}
		invertercap = document.getElementById("invertercap").value; 
		if (selectValidation("","invertercap","select") == false)
		{
			return;
			
		}
		inverterprice = document.getElementById("inverterprice").value;
		if (yesValidation("","inverterprice","yes") == false)
		{
			return;
			
		}
		invertermodel = document.getElementById("invertermodel").value;
		if (selectValidation("","invertermodel","select") == false)
		{
			return;
			
		}
		Quantity = document.getElementById("Quantity").value;

							
		var number_regex = /^\d+$/;
		if ((!Quantity.match(number_regex) && Quantity.length > 0) || (!Quantity.match(number_regex)) || (Quantity.length == 0) || (Quantity<=0) || (isNaN(parseFloat(Quantity))==true))
		{
			Quantity=1;
			errMsg ="<font color='#9B5BDD'>Please enter valid quantity...</font>";
			document.getElementById("displayrefinederrormsg1").innerHTML=errMsg;
			$("#Quantity").focus();
			return ;
		}

	}
	else if(orderstatus=="Customer Contacted" && orderreason=="Not Confirmed Order - Customer Confirmed to Place Order")
	{
		inverterbrand = $("#inverterbrand").val(); 
		invertercap = $("#invertercap").val(); 
		inverterprice = $("#inverterprice").val();
		invertermodel = $("#invertermodel").val();
		cusname = $("#cusname").val(); 
		cusemailid = $("#cusemailid").val(); 
		cusaddr1 = $("#cusaddr1").val(); 
		cusaddr2 = $("#cusaddr2").val();
		cusstate = $("#constate").val(); 
		cuscity = $("#concity").val(); 
		cusarea = $("#cusarea").val();
		Quantity = $("#Quantity").val();

		if(inverterbrand == "" || inverterbrand == "0" || inverterbrand == "default" || inverterbrand == "defaultss")
		{
			errMsg ="<font color='#9B5BDD'>Please Select Inverter Brand</font>";
			document.getElementById("displayrefinederrormsg1").innerHTML=errMsg;
			$("#inverterbrand").focus();
			return ;
		}

		if(invertercap == "" || invertercap == "0" || invertercap == "default" || invertercap == "defaultss")
		{
			errMsg ="<font color='#9B5BDD'>Please Select Inverter Capacity</font>";
			document.getElementById("displayrefinederrormsg1").innerHTML=errMsg;
			$("#invertercap").focus();
			return ;
		}

		if(invertermodel == "" || invertermodel == "0" || invertermodel == "default" || invertermodel == "defaultss")
		{
			errMsg ="<font color='#9B5BDD'>Please Select Inverter Model</font>";
			document.getElementById("displayrefinederrormsg1").innerHTML=errMsg;
			$("#invertermodel").focus();
			return ;
		}

		if(cusname == "" || cusname == "0" )
		{
			errMsg ="<font color='#9B5BDD'>Please Enter Customer Name</font>";
			document.getElementById("displayrefinederrormsg1").innerHTML=errMsg;
			$("#cusname").focus();
			return ;
		}

		if(cusemailid == "" || cusemailid == "0" )
		{
			errMsg ="<font color='#9B5BDD'>Please Enter Customer Email ID</font>";
			document.getElementById("displayrefinederrormsg1").innerHTML=errMsg;
			$("#cusemailid").focus();
			return ;
		}

		if(cusaddr1 == "" || cusaddr1 == "0" )
		{
			errMsg ="<font color='#9B5BDD'>Please Enter Customer Address1</font>";
			document.getElementById("displayrefinederrormsg1").innerHTML=errMsg;
			$("#cusaddr1").focus();
			return ;
		}

		if(cusaddr2 == "" || cusaddr2 == "0" )
		{
			errMsg ="<font color='#9B5BDD'>Please Enter Customer Address2</font>";
			document.getElementById("displayrefinederrormsg1").innerHTML=errMsg;
			$("#cusaddr2").focus();
			return ;
		}

		if(cusarea == "" || cusarea == "0"  || cusarea == "default"  || cusarea == "defaultss" )
		{
			errMsg ="<font color='#9B5BDD'>Please Select Customer Area</font>";
			document.getElementById("displayrefinederrormsg1").innerHTML=errMsg;
			$("#cusarea").focus();
			return ;
		}
							
		var number_regex = /^\d+$/;
		if ((!Quantity.match(number_regex) && Quantity.length > 0) || (!Quantity.match(number_regex)) || (Quantity.length == 0) || (Quantity<=0) || (isNaN(parseFloat(Quantity))==true))
		{
			Quantity=1;
			errMsg ="<font color='#9B5BDD'>Please enter valid quantity...</font>";
			document.getElementById("displayrefinederrormsg1").innerHTML=errMsg;
			$("#Quantity").focus();
			return ;
		}


	}
	else
	{
		var postponedate ="";
	}
	
	//alert(SMSURL);
	//alert(SMSURL1);
	//alert(shorturl);
	//alert(shorturl1);
	
	$('#divpostponed').hide();
	var xmlhttp= "";
	var resp= "";
	var url ="/bookbattery/servlet/MISOperatorInverterDetails?hidWhatToDo=updateorderstatus&orderid="+orderid+"&ordernumber="+ordernumber+"&orderstatus="+orderstatus+"&orderreason="+orderreason+"&agentcomments="+cusmessage+"&postponedate="+postponedate+"&retailertoorder="+retailertoorder+"&inverterbrand="+inverterbrand+"&invertercap="+invertercap+"&inverterprice="+inverterprice+"&invertermodel="+invertermodel+"&cusname="+cusname+"&cusemailid="+cusemailid+"&cusaddr1="+cusaddr1+"&cusaddr2="+cusaddr2+"&cusstate="+cusstate+"&cuscity="+cuscity+"&cusarea="+cusarea+"&rating="+rating+"&payment_mode="+payment_mode+"&SMSURL="+SMSURL+"&SMSURL1="+SMSURL1+"&shorturl="+shorturl+"&shorturl1="+shorturl1+"&Quantity="+Quantity+"&paymentcollected="+paymentcollected;


	if (window.XMLHttpRequest)
	{
		// code for IE7+, Firefox, Chrome, Opera, Safari
		 xmlhttp=new XMLHttpRequest();
	}
	else
	{
		// code for IE6, IE5
		xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
	}
	xmlhttp.onreadystatechange=function()
	{
		if (xmlhttp.readyState==4)
		{
			if(xmlhttp.status!=200)
			{
				alert("Error Occured. Please try again.");
				return;
			}
			else 
			{
				resp = xmlhttp.responseText;
				//alert(resp);
				$("#defaultModal").html("");
			var AfterOrder_PopUp="<div class='modal-dialog' role='document'><div class='modal-content'> <div class='modal-header bg-amber'><h4 class='modal-title' id='after_order_popup_label'>Confirmation Message - BookBattery</h4><button type='button' data-dismiss='modal' class='close' data-dismiss='modal' aria-label='Close'><span aria-hidden='true'> x</span></button></div> <div class='modal-body'> 	<div class='form-group'> <div id='after_order_popup_data'>"+resp+"<br/></br/></div> <div style='text-align: center;'><button type='button' class='btn bg-amber waves-effect' data-dismiss='modal' onclick='GetInverterOrderDetails()'>Okay</button> </div></div> </div></div> </div>";
		
			$("#defaultModal").modal({show: true, backdrop: 'static', keyboard: false});
			$("#defaultModal").html(AfterOrder_PopUp);
			}
		}			
	}
	/*var agree=confirm("Are You sure want to update the Order Status! ");
	if (agree)
	{*/
	xmlhttp.open("GET",url, true);		
	xmlhttp.send();	

}

function getinvertercapacities()
{
	
			 
			var inverterbrand = document.getElementById("inverterbrand").value; 
			$.ajax
			({
				type: "GET",
				url: "/bookbattery/servlet/InvertersDetails?hidWhatToDo=getinvertercapacity",
				data: {inverterbrand: inverterbrand},
				success: function(data)
				{	
					$("#invertercap").html(data)
				}
			});
	
}

function getinvertermodels()
{
	
			 
			var inverterbrand = document.getElementById("inverterbrand").value; 
			var invertercap = document.getElementById("invertercap").value; 
			$.ajax
			({
				type: "GET",
				url: "/bookbattery/servlet/InvertersDetails?hidWhatToDo=getinvertermodels",
				data: {inverterbrand: inverterbrand,invertercap: invertercap},
				success: function(data)
				{	
					$("#invertermodel").html(data)
				}
			});
	
}

function getinverterpricedetls()
{
	var constate = document.getElementById("constate").value; 
	var concity = document.getElementById("concity").value; 
	var inverterbrand = document.getElementById("inverterbrand").value; 
	var invertercap = document.getElementById("invertercap").value;
	var invertermodel = document.getElementById("invertermodel").value; 
			
			$.ajax
			({
				type: "GET",
				url: "/bookbattery/servlet/MISOperatorInverterDetails?hidWhatToDo=getinverterpricedetls",
				data: {constate: constate,concity: concity,inverterbrand: inverterbrand,invertercap: invertercap,invertermodel: invertermodel},
				success: function(data)
				{	
					//alert(data);
					//$("#batmodel").html(data)
					/*var results=data.split(",");
							var withoutoldbatprice=results[0];
							var witholdbatprice=results[1];*/

							document.getElementById("inverterprice").value=data;
							//document.getElementById("witholdbatprice").value=witholdbatprice;

				}
			});
}

function makeRequest(Url,orderid,ordernumber,orderstatus) {

//alert("inside makeRequest");
	$.ajax
	({					 
		type: "GET",
		dataType: "json",
		url: "https://api-ssl.bitly.com/v3/shorten",
		data:{access_token:"9d31af737fd0644e87be16ff102ac97d09dd554e",longUrl:Url},
		success: function(response)
		{	
			//alert(response.status_txt);
			var responsearr=response.data;
			//alert("responsearr"+responsearr.url);
			str =responsearr.url;
			document.getElementById("result").value = str;
			UpdateOrderStatus(orderid,ordernumber,orderstatus);
		}
	});	

}

function makeRequest1(Url,orderid,ordernumber,orderstatus) {

	var request = gapi.client.urlshortener.url.insert({
	'resource': {
	'longUrl': Url
	}
	});
	var result=request.execute(function(response) {
	if (response.id != null) {
	str =response.id;
	//document.getElementById("result").value = str;
	document.getElementById("result1").value = str;
	
	UpdateOrderStatus(orderid,ordernumber,orderstatus);
	
	}
	else {
	alert("Error: creating short url1 \n" + response.error);
	}
	
	});

}
function setagentcomments(PaymentMode)
{
	//var orderreason = document.misbatstatus.orderreason.value;
	var orderreason = $('#orderreason').val();
	if(orderreason =="Modify Battery Details")
	{
		$('#modifybatdetls').show();
		$('#modifybatdetls1').show();
		$('#modifybatdetls2').show();
		$('#modifybatdetls3').show();
		$('#modifybatdetls4').show();
		$('#modifybatdetls5').show();
		$('#modifybatdetls6').show();
		$('#modifybatdetls7').show();
		$('#modifybatdetls8').show();
		$('#modifybatdetls9').show();
		$('#modifybatdetls10').show();
		$('#modifybatdetls11').show();
		$('#modifybatdetls12').show();
		$('#modifybatdetls13').show();
		$('#modifybatdetls14').show();
		$('#confirmord11').hide();
		$('#ratingid').hide();
		$('.Quantity_tr').show();
		//$('#rettoorder1').show();

	}
	else if(orderreason =="installed")
	{
		$('#rettoorder').hide();
		$('#rettoorder1').hide();
		$('#modifybatdetls').hide();
		$('#modifybatdetls1').hide();
		$('#modifybatdetls2').hide();
		$('#modifybatdetls3').hide();
		$('#modifybatdetls4').hide();
		$('#modifybatdetls5').hide();
		$('#modifybatdetls6').hide();
		$('#modifybatdetls7').hide();
		$('#modifybatdetls8').hide();
		$('#modifybatdetls9').hide();
		$('#modifybatdetls10').hide();
		$('#modifybatdetls11').hide();
		$('#modifybatdetls12').hide();
		$('#modifybatdetls13').hide();
		$('#modifybatdetls14').hide();
		$('#confirmord1').hide();
		$('#confirmord2').hide();
		$('#confirmord3').hide();
		$('#confirmord4').hide();
		$('#confirmord5').hide();
		$('#confirmord6').hide();
		$('#confirmord7').hide();
		$('#confirmord8').hide();
		$('#confirmord9').hide();
		$('#confirmord10').hide();
		$('#ratingid').show();
		
		if(PaymentMode =="Online")
		{
			$('#payment_mode').html("<option value='Online' selected >Online</option>");
		} 
		else if(PaymentMode =="Online Payment After Fitment")
		{
			//alert("in");
			$('#order_type').after("<p style='color: red; font-size: 12px; font-weight: 400;'>Customer has Opted for:"+PaymentMode+"</p>");
			$('#payment_mode').val(PaymentMode);
			$("#payment_mode").empty();
			$('#payment_mode').append("<option value='0' >&lt;-- Select Payment Method--&gt;</option>");
			$('#payment_mode').append("<option value='Cheque'>Cheque</option>");
			$('#payment_mode').append("<option value='Credit Card'>Credit Card</option>");
			$('#payment_mode').append("<option value='Debit Card'>Debit Card</option>");
			$('#payment_mode').append("<option value='Cash On Delivery'>Cash On Delivery</option>");
			$('#payment_mode').append("<option value='Online Payment After Fitment'>Online Payment After Fitment</option>");
	  
		} else if(PaymentMode =="Cash")
		{
			//alert("cash");
			$('#order_type').after("<p style='color: red; font-size: 12px; font-weight: 400;'>Customer has Opted for:"+PaymentMode+"</p>");
			$('#payment_mode').val(PaymentMode);
			$("#payment_mode").empty();
			$('#payment_mode').append("<option value='0' >&lt;-- Select Payment Method--&gt;</option>");
			$('#payment_mode').append("<option value='Cash'>Cash</option>");
			$('#payment_mode').append("<option value='Cheque'>Cheque</option>");
			$('#payment_mode').append("<option value='Credit Card'>Credit Card</option>");
			$('#payment_mode').append("<option value='Debit Card'>Debit Card</option>");
			$('#payment_mode').append("<option value='UPI'>UPI</option>");
			$('#payment_mode').append("<option value='Online Payment After Fitment'>Online Payment After Fitment</option>");
	  
		}
		
		$('#confirmord11').show();
		$('#ratingid').show();
		$('#order_type_div').show();
		
		
		$('.Quantity_tr').hide();
	}
	else
	{
		$('#rettoorder').hide();
		$('#rettoorder1').hide();
		$('#modifybatdetls').hide();
		$('#modifybatdetls1').hide();
		$('#modifybatdetls2').hide();
		$('#modifybatdetls3').hide();
		$('#modifybatdetls4').hide();
		$('#modifybatdetls5').hide();
		$('#modifybatdetls6').hide();
		$('#modifybatdetls7').hide();
		$('#modifybatdetls8').hide();
		$('#modifybatdetls9').hide();
		$('#modifybatdetls10').hide();
		$('#modifybatdetls11').hide();
		$('#modifybatdetls12').hide();
		$('#modifybatdetls13').hide();
		$('#modifybatdetls14').hide();
		$('#confirmord1').hide();
		$('#confirmord2').hide();
		$('#confirmord3').hide();
		$('#confirmord4').hide();
		$('#confirmord5').hide();
		$('#confirmord6').hide();
		$('#confirmord7').hide();
		$('#confirmord8').hide();
		$('#confirmord9').hide();
		$('#confirmord10').hide();
		$('#confirmord11').hide();
		$('#confirmord11').hide();
		$('#ratingid').hide();
		$('#order_type_div').hide();
		$('.Quantity_tr').hide();

	}
		//document.misbatstatus.cusmessage.value=orderreason;
		$('#orderreason').val(orderreason);
}

function generateInvoice(ordnum){
	
	//alert(ordnum);
		$.ajax({
			type: "GET",
			url: "/bookbattery/servlet/UpdatedBatteryOrderDetails?hidWhatToDo=generateorderinvoice",
			data: {ordernumber: ordnum},
			success: function(data){
				 var OrderLable="";
				var OrderMessage="";
				if(data.indexOf("Successfully")>=0)
				{
					$("#defaultModal").html("");
 					OrderLable="Successfully Generated - BookBattery";
					OrderMessage="<div style='text-align: center;'><i class='icon-frown' style='font-size: 60px;color: #de3315;'></i><h4>"+data+"</h4></div></br>";
				}
				else
				{
					OrderLable="Failed to Generate - BookBattery";
					OrderMessage="<div style='text-align: center;'><i class='icon-frown' style='font-size: 60px;color: #de3315;'></i><h4>"+data+"</h4></div></br>";
					
				}
				
				var AfterOrder_PopUp="<a class='btn hide' data-toggle='modal' data-target='.after_order_popup' data-toggle='modal' data-backdrop='static' data-keyboard='false' id='after_order_popup_btn'></a><div class='modal fade after_order_popup' tabindex='-1' role='dialog' aria-labelledby='after_order_popup_label'> <div class='modal-dialog' role='document'> 	<div class='modal-content'> <div class='modal-header bg-amber'><h4 class='modal-title' id='after_order_popup_label'>"+OrderLable+"</h4><button type='button' onclick='okey_done()' class='close' data-dismiss='modal' aria-label='Close'><span aria-hidden='true'> x</span></button> 	</div> <div class='modal-body'><div class='form-group'> <div id='after_order_popup_data'> "+OrderMessage+"</div> <div style='text-align: center;'><button type='button' class='btn bg-amber waves-effect' data-dismiss='modal'>Okay</button> </div></div> </div></div> </div> </div>";
				
				$("#defaultModal").modal({show: true, backdrop: 'static', keyboard: false});
				$("#defaultModal").html(AfterOrder_PopUp);
				$("#after_order_popup_btn").click();
 			}
	}); 
}
function viewInvoice(ordernumber){
	
		$.ajax({
			type: "GET",
			url: "/bookbattery/servlet/UpdatedBatteryOrderDetails?hidWhatToDo=vieworderinvoice",
			data: {ordernumber: ordernumber},
			success: function(data){
				
				if(data.indexOf("No Details found")>=0){
					$("#defaultModal").html("");
					var AfterOrder_PopUp="<div class='modal-dialog' role='document'><div class='modal-content'> <div class='modal-header bg-amber'><h4 class='modal-title' id='after_order_popup_label'>Confirmation Message - BookBattery</h4><button type='button' data-dismiss='modal' class='close' data-dismiss='modal' aria-label='Close'><span aria-hidden='true'> x</span></button></div> <div class='modal-body'> 	<div class='form-group'> <div id='after_order_popup_data'>Invoice Not Generated for the Order. Please Generate Invoice.<br/></br/></div> <div style='text-align: center;'><button type='button' class='btn bg-amber waves-effect' data-dismiss='modal'>Okay</button> </div></div> </div></div> </div>";
				
					$("#defaultModal").modal({show: true, backdrop: 'static', keyboard: false});
					$("#defaultModal").html(AfterOrder_PopUp);
				
				} else {
					
					//alert(data);
					window.open(data,'_blank');
				}
				

			}
		});
	
}
function CheckInvoice(ordernumber)
{
	//alert(ordernumber);	
	$.ajax({
		type: "GET",
		url: "/bookbattery/servlet/UpdatedBatteryOrderDetails?hidWhatToDo=vieworderinvoice",
		data: {ordernumber: ordernumber},
		success: function(data){
			
			if(data.indexOf("No Details found")>=0){
				$("#defaultModal").html("");
				var AfterOrder_PopUp="<div class='modal-dialog' role='document'><div class='modal-content'> <div class='modal-header bg-amber'><h4 class='modal-title' id='after_order_popup_label'>Confirmation Message - BookBattery</h4><button type='button' data-dismiss='modal' class='close' data-dismiss='modal' aria-label='Close'><span aria-hidden='true'> x</span></button></div> <div class='modal-body'> 	<div class='form-group'> <div id='after_order_popup_data'>Invoice Not Generated for the Order. Please Generate Invoice.<br/></br/></div> <div style='text-align: center;'><button type='button' class='btn bg-amber waves-effect' data-dismiss='modal'>Okay</button> </div></div> </div></div> </div>";
			
				$("#defaultModal").modal({show: true, backdrop: 'static', keyboard: false});
				$("#defaultModal").html(AfterOrder_PopUp);
			
			} else {
				
				//alert(data);
				//window.open(data,'_blank');
				sendInvoice(ordernumber)
			}
			

		}
	});
}
function sendInvoice(ordernumber)
{
	
	//alert(ordernumber);
	$.ajax({
		type: "GET",
		url: "/bookbattery/servlet/UpdatedBatteryOrderDetails?hidWhatToDo=sendinvoice",
		data: {ordernumber: ordernumber},
		success: function(data){
			
			if(data.indexOf("No Details found")>=0){
				$("#defaultModal").html("");
				var AfterOrder_PopUp="<div class='modal-dialog' role='document'><div class='modal-content'> <div class='modal-header bg-amber'><h4 class='modal-title' id='after_order_popup_label'>Confirmation Message - BookBattery</h4><button type='button' data-dismiss='modal' class='close' data-dismiss='modal' aria-label='Close'><span aria-hidden='true'> x</span></button></div> <div class='modal-body'> 	<div class='form-group'> <div id='after_order_popup_data'>Invoice Not Generated for the Order. Please Generate Invoice.<br/></br/></div> <div style='text-align: center;'><button type='button' class='btn bg-amber waves-effect' data-dismiss='modal'>Okay</button> </div></div> </div></div> </div>";
			
				$("#defaultModal").modal({show: true, backdrop: 'static', keyboard: false});
				$("#defaultModal").html(AfterOrder_PopUp);
			
			} else {
				
				//alert(data);
				//window.open(data,'_blank');
				$("#defaultModal").html("");
				var AfterOrder_PopUp="<div class='modal-dialog' role='document'><div class='modal-content'> <div class='modal-header bg-amber'><h4 class='modal-title' id='after_order_popup_label'>Confirmation Message - BookBattery</h4><button type='button' data-dismiss='modal' class='close' data-dismiss='modal' aria-label='Close'><span aria-hidden='true'> x</span></button></div> <div class='modal-body'> 	<div class='form-group'> <div id='after_order_popup_data'>Invoice has been successfully sent to Email.<br/></br/></div> <div style='text-align: center;'><button type='button' class='btn bg-amber waves-effect' data-dismiss='modal'>Okay</button> </div></div> </div></div> </div>";
			
				$("#defaultModal").modal({show: true, backdrop: 'static', keyboard: false});
				$("#defaultModal").html(AfterOrder_PopUp);
				
			}
			

		}
	});
	
}
</script>