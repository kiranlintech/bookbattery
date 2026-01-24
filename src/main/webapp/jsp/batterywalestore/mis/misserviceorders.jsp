
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

 <!-- Bootstrap Spinner Css -->
<link href="/bookbattery/css/BookBatterystore/plugins/jquery-spinner/css/bootstrap-spinner.css" rel="stylesheet">

 <section class="content">
        <div class="container-fluid">
            <!-- Advanced Select -->
            <div class="row clearfix">
                <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
                    <div class="card">
                        <div class="header bg-purple">
                            <h2>
                               SERVICE ORDER DETAILS
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
                                         <button type="button" class="btn bg-purple waves-effect" onclick="GetServiceDetails()">
											<i class="material-icons">search</i>
											<span>GET ORDER DETAILS</span>
										</button>  
                                    </div>
                                </div>
                            </div>
                            <div class="row clearfix" id="misservice_details"></div>
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
<div class="modal fade" id="defaultModal" tabindex="-1" role="dialog">
  
</div>
  <!-- Jquery Spinner Plugin Js -->
<script src="/bookbattery/css/BookBatterystore/plugins/jquery-spinner/js/jquery.spinner.js"></script>

<jsp:include page = "../storefooter.jsp" />		
<script type="text/javascript">

$('#fromdate').bootstrapMaterialDatePicker({ format : 'YYYY-MM-DD', weekStart : 0, time: false });
$('#todate').bootstrapMaterialDatePicker({ format : 'YYYY-MM-DD', weekStart : 0, time: false });
$('#datetimepicker1').bootstrapMaterialDatePicker({ format : 'YYYY-MM-DD', weekStart : 0, time: false });


function GetServiceDetails(){
	
	var fromdate = $("#fromdate").val();
	var todate = $("#todate").val();
	$.ajax({
			type: "GET",
			url: "/bookbattery/servlet/BatterystoreMISReports?hidWhatToDo=serviceorderdetails",
			data: {todate: todate, fromdate: fromdate },
			success: function(data){
				//alert(data);
				$("#misservice_details").html(data);
			}
	}); 
}

function funtoUpdate(orderid,ordernumber,state,city,area,cusmobile,PaymentMode){
	
	var orderedstatus = document.getElementById(orderid).value;
	//alert(orderedstatus);
	//alert("inside updatevisitorstatus"+state);
	
	if(orderedstatus=="Customer Contacted")
	{
		CustomerContacted(orderid,ordernumber,orderedstatus,state,city,cusmobile,PaymentMode)
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
		//alert("inside updatevisitorstatus confirmed"+state);
		Ordertoretailer(orderid,ordernumber,orderedstatus,state,city,area,cusmobile,PaymentMode)
	}
	
	else if(orderedstatus=="")
	{
	}
	else
	{
		var xmlhttp= "";
		var resp= "";
		var url ="/bookbattery/servlet/MISOperatorServiceDetails?hidWhatToDo=updateorderstatus&orderid="+orderid+"&ordernumber="+ordernumber+"&orderstatus="+orderedstatus;
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
					errMsg ="<table border='0' width='300px' height='10px' cellpadding='0' cellspacing='0' bgcolor='#88689f'><tr class='top1'><td>&nbsp;<font color='#FFFFFF'>BookBattery</td><td align='right'><a href='javascript:closemobdiv();'><img src=\"../../../images/Delete1.png \" border='0'></a></td></tr></table><table border='0' width='300px' height='2px' bgcolor='#FFFFFF' valign'top'><tr bgcolor='#FFFFFF'><td align='justify' bgcolor='#FFFFFF' style='font-family:Verdana;font-size:11px;color:#000000;'>"+resp+"</td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='getupdatedorders();' class='button4'><br></td></tr><tr height='15'></tr></table>";
					document.getElementById("divpostponed").innerHTML=""; 
					document.getElementById("divpostponed").style.display='block';
					document.getElementById("divpostponed").innerHTML=errMsg
					
				}
			}			
		}
			xmlhttp.open("GET",url, true);		
			xmlhttp.send();	
		
	}


}

/*****************Customer Contacted *************/
 function CustomerContacted(orderid,ordernumber,orderstatus,state,city,cusmobile,PaymentMode){
	
	/*alert(orderid);
	alert(ordernumber);
	alert(orderstatus);*/
	
	var PaymentModeClass="";
	if(PaymentMode=="Online")
	{
		PaymentModeClass="hide";
	}
	else
	{
		// DO Noting
	}
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
			url: "/bookbattery/servlet/BatteryHome?hidWhatToDo=getarea",
			data: {city: city },
			success: function(data)
			{	
				$("#cusarea").html(data)
			}
			});

			var OrderLable ="Update Service Order Status";
				var OrderMessage ="<div class='row clearfix'><div class='col-sm-12'><div class='form-group'><p><b>Order Resason</b></p><div><select name='orderreason'  id='orderreason'  class='btn-group bootstrap-select form-control show-tick yes' onChange=\"javascript:setagentcomments('"+orderid+"','"+ordernumber+"','"+orderstatus+"','"+state+"','"+city+"','"+cusmobile+"','"+PaymentMode+"')\"><option value='0' >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Select Reason</option><option value='installed' >Installed</option><option value='In Process' >In Process</option><option value='Cancelled' >Cancelled</option></select><div id='orderreason-error' style='display:none;'></div></div><div id='confirmord11' style='display:none;'><select onchange='javascript:setagentcomments_payment();' name='payment_mode' id='payment_mode'  class='btn-group bootstrap-select form-control show-tick yes'><option value='0'>Select Payment Method</option><option value='Cash'>Cash</option><option value='Cheque'>Cheque</option><option value='Credit Card'>Credit Card</option><option value='Debit Card'>Debit Card</option><option value='UPI'>UPI</option><option value='Online Payment After Fitment'>Online Payment After Fitment</option></select><div id='payment_mode-error' style='display:none;'></div></div><div id='ratingid' style='display:none;'><div><select name='rating' id='rating'  class='btn-group bootstrap-select form-control show-tick yes'><option value='0'>Select Satisfaction Type</option> <option value='Satisfied'>Satisfied</option> <option value='Not Satisfied'>Not Satisfied</option></select><div id='rating-error' style='display:none;'></div></div><div><select name='payment_collected' id='payment_collected'  class='btn-group bootstrap-select form-control show-tick yes'><option value='0'>Select Payment Collected</option><option value='Asistmi' selected>Collected by Asistmi</option></select><div id='payment_collected-error' style='display:none;'></div></div></div><div height='10' id='modifybatdetls13' readonly><input type='text' value='"+state+"' name='constate' id='constate' style='display:none';></div><div height='10' id='modifybatdetls14' readonly><input type='text' value='"+city+"' name='concity' id='concity' style='display:none';></div><div id='modifybatdetls' style='display:none;'><font color='#ff3333'>&nbsp;*&nbsp;</font>Select&nbsp;Make</div><div id='modifybatdetls1' style='display:none;'><select name='vehmake'  id='vehmake'  class='btn-group bootstrap-select form-control show-tick yes' onchange='javascript:getvehmodels();' ><option value='0' >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Select Make</option></select><div id='vehmake-error' style='display:none;'></div></div><div id='modifybatdetls2' style='display:none;'><font color='#ff3333'>&nbsp;*&nbsp;</font>Select&nbsp;Model</div><div height='10' id='modifybatdetls3' style='display:none;'><select name='vehmodel'  id='vehmodel'  class='btn-group bootstrap-select form-control show-tick yes' onchange='javascript:getbatbrands();' ><option value='0' >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Select Model</option></select><div id='vehmodel-error' style='display:none;'></div></div><div id='modifybatdetls4' style='display:none;'><font color='#ff3333'>&nbsp;*&nbsp;</font>Select&nbsp;Brand</div><div height='10' id='modifybatdetls5' style='display:none;'><select name='batbrand'  id='batbrand'  class='btn-group bootstrap-select form-control show-tick yes' onchange='javascript:getbatmodels();'><option value='0' >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Select Brand</option></select><div id='batbrand-error' style='display:none;'></div></div><div id='modifybatdetls6' style='display:none;'><font color='#ff3333'>&nbsp;*&nbsp;</font>Select&nbsp;Model</div><div height='10' id='modifybatdetls7' style='display:none;'><select name='batmodel'  id='batmodel'  class='btn-group bootstrap-select form-control show-tick yes' onchange='javascript:getbatpricedetls();'><option value='0' >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Select Model</option></select><div id='batmodel-error' style='display:none;'></div></div><div id='modifybatdetls8' style='display:none;'><font color='#ff3333'>&nbsp;*&nbsp;</font>With out Old Battery Price</div><div height='10' id='modifybatdetls9' style='display:none;'><input type='text' value='' name='batprice' class='form-control yes' id='batprice'><div id='batprice-error' style='display:none;'></div></div><div class='form-line' id='modifybatdetls10' style='display:none;'><font color='#ff3333'>&nbsp;*&nbsp;</font>With Old Battery Price</div><div class='form-line' height='10' id='modifybatdetls11' style='display:none;'><input type='text' value='' name='witholdbatprice' id='witholdbatprice' class='form-control yes'><div id='witholdbatprice-error' style='display:none;'></div></div><div  class='Quantity_div' style='display:none;'><font color='#ff3333'>&nbsp;*&nbsp;</font>&nbsp;Enter&nbsp;Quantity</div><div  class='Quantity_div form-line' style='display:none;'>&nbsp;<input type='text' autocomplete='off' name='Quantity' id='Quantity' value='1' class='form-control' maxlength='50'></div></div><div class='form-group'><div class='form-line'><textarea rows='1' class='form-control no-resize auto-growth' id='cusmessage' name='cusmessage' placeholder='Please type what you want'></textarea><div id='cusmessage-error' style='display:none;'></div></div></div></div></div>";
				
				var AfterOrder_PopUp="<div class='modal-dialog' role='document'><div class='modal-content'> <div class='modal-header'><h4 class='modal-title' id='after_order_popup_label'>"+OrderLable+"</h4><button type='button' onclick='okey_done()' class='close' data-dismiss='modal' aria-label='Close'><span aria-hidden='true'> x</span></button> 	</div> <div class='modal-body'> 	<div class='form-group'> <div id='after_order_popup_data'> "+OrderMessage+"</div> <div style='text-align: center;'><button type='button' class='btn bg-purple waves-effect' onclick=\"javascript:UpdateOrderStatus('"+orderid+"','"+ordernumber+"','"+orderstatus+"','"+cusmobile+"')\">Update Status</button>&nbsp;&nbsp;&nbsp;<button type='button' class='btn bg-indigo waves-effect' data-dismiss='modal'>Cancel</button> </div> 	</div> </div> 	</div> </div>";
		$("#defaultModal").modal({show: true, backdrop: 'static', keyboard: false});
		$("#defaultModal").html(AfterOrder_PopUp);	
	
}
 
 function setagentcomments(orderid,ordernumber,orderstatus,state,city,cusmobile,PaymentMode)
 {
 		var orderreason = $("#orderreason").val();
 		document.getElementById("cusmessage").value=orderreason;
 		
 		//alert(PaymentMode);
 		
 	if(orderreason =="installed")
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
 			//alert("inside PaymentMode");
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
 			$('#payment_mode').append("<option value='UPI' >UPI</option>");
 		}
 		
 		$('#confirmord11').show();
 		$('#order_type_div').show();
 		$('#ratingid').show();
 		$('.Quantity_tr').hide();
 	}
 	else if(orderreason =="Not Confirmed Order - Customer Confirmed to Place Order")
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
 		$('#confirmord1').show();
 		$('#confirmord2').show();
 		$('#confirmord3').show();
 		$('#confirmord4').show();
 		$('#confirmord5').show();
 		$('#confirmord6').show();
 		$('#confirmord7').show();
 		$('#confirmord8').show();
 		$('#confirmord9').show();
 		$('#confirmord10').show();

 		$('#confirmord11').hide();
 		$('#ratingid').hide();
 		$('#order_type_div').show();
 		$('.Quantity_tr').show();
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
 		$('#ratingid').hide();
 		$('#order_type_div').hide();
 		$('.Quantity_tr').hide();
 	}
 		
 }
 
 /****************Customer Contacted *************/
 function UpdateOrderStatus(orderid,ordernumber,orderstatus,cusmobile){
	 
		var iChars3 = "`~!@#$%^&*()+=[]\\\';/{}|\":<>?";
		var dot=".";
		var SMSURL = "";
		var shorturl = "";
		var payment_mode = ""; 
		var rating = "";
        var paymentcollected = "";
		var retailertoorder = "";
		var cusarea="";

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
			}
			else if((postday<day)&&(postyear<year))
			{
				$('#displayrefinederrormsg1').show();
				errMsg ="<font color='#9B5BDD'>Selected Date should be greater than Today's Date</font>";
				document.getElementById("displayrefinederrormsg1").style.display='block';
				document.getElementById("displayrefinederrormsg1").innerHTML=errMsg;
				
			}
			else if((postday<day)&&(postmonth<=month)&&(postyear<=year))
			{
				$('#displayrefinederrormsg1').show();
				errMsg ="<font color='#9B5BDD'>Selected Date should be greater than Today's Date</font>";
				document.getElementById("displayrefinederrormsg1").style.display='block';
				document.getElementById("displayrefinederrormsg1").innerHTML=errMsg;
				
			}
			else if((postday>day)&&(postmonth<month)&&(postyear<=year))
			{
				$('#displayrefinederrormsg1').show();
				errMsg ="<font color='#9B5BDD'>Selected Date should be greater than Today's Date</font>";
				document.getElementById("displayrefinederrormsg1").style.display='block';
				document.getElementById("displayrefinederrormsg1").innerHTML=errMsg;		
			}	
			else if((postday>day)&&(postyear<year))
			{
				$('#displayrefinederrormsg1').show();
				errMsg ="<font color='#9B5BDD'>Selected Date should be greater than Today's Date</font>";
				document.getElementById("displayrefinederrormsg1").style.display='block';
				document.getElementById("displayrefinederrormsg1").innerHTML=errMsg;		
			}
			else if(postday==day&&(postmonth<=month)&&(postyear<=year))
			{
				$('#displayrefinederrormsg1').show();
				errMsg ="<font color='#9B5BDD'>Selected Date should be greater than Today's Date</font>";
				document.getElementById("displayrefinederrormsg1").style.display='block';
				document.getElementById("displayrefinederrormsg1").innerHTML=errMsg;		
			}
		}
		
		else if(orderstatus=="confirmed")
		{
			//alert(22);
			retailertoorder = $("#retailer").val();
			cusarea = document.getElementById("cusarea").value;
			
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
		
		else if(orderstatus=="Customer Contacted" && orderreason=="installed")
		{
			
			baseurl="http://www.BookBattery.com/bookbattery/";
			baseurl1="http://www.BookBattery.com/bookbattery/";
			//alert(baseurl);
			//alert("orderid:"+orderid);
			//var SMSURL="http://www.BookBattery.com/bookbattery/jsp/rating.jsp?phone=9108501361&email=rathaiahtulabandhula@gmail.com&ordernumber=ORDB4813098B";
			
			 SMSURL=baseurl+"jsp/rating.jsp?ordernumber="+ordernumber;
			 SMSURL1=baseurl1+"jsp/rating.jsp?ordernumber="+ordernumber;
			
			//alert(SMSURL);

			shorturl = document.getElementById("result").value; 
			shorturl1 = document.getElementById("result1").value; 

			if(shorturl =="" || shorturl == undefined|| shorturl == "undefined")
			{
				//makeRequest(SMSURL,orderid,ordernumber,orderstatus,cusmobile);
				//return;
			}
			else
			{
			
			}
			if(shorturl1 =="" || shorturl1 == undefined|| shorturl1 == "undefined" || (shorturl1==shorturl))
			{
				//alert(22);
				//makeRequest1(SMSURL1,orderid,ordernumber,orderstatus,cusmobile);
				//return;
			}
			else
			{
			
			}
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
			//order_type = $("#order_type").val();
			 
		}	
		
		else if(orderstatus=="Customer Contacted" && orderreason=="Not Confirmed Order - Customer Confirmed to Place Order")
		{
			batterytype = document.getElementById("battype").value;
				
			if(batterytype=="Inverter Batteries" || batterytype=="Flat Plate Battery" || batterytype=="Tubular Battery" || batterytype=="Tall Tubular Battery" )	
			{

			}
			else
			{
				vehmake = document.getElementById("vehmake").value; 
				vehmodel = document.getElementById("vehmodel").value; 

				if(vehmake == "" || vehmake == "0" || vehmake == "default" || vehmake == "defaultss")
					{
						errMsg ="<font color='#9B5BDD'>Please Select Vehicle Make</font>";
						document.getElementById("displayrefinederrormsg1").innerHTML=errMsg;
						$("#vehmake").focus();
						return ;
					}

					if(vehmodel == "" || vehmodel == "0" || vehmodel == "default" || vehmodel == "defaultss")
					{
						errMsg ="<font color='#9B5BDD'>Please Select Vehicle Model</font>";
						document.getElementById("displayrefinederrormsg1").innerHTML=errMsg;
						$("#vehmodel").focus();
						return ;
					}
			}

			batbrand = document.getElementById("batbrand").value; 
			batmodel = document.getElementById("batmodel").value; 
			batprice = document.getElementById("batprice").value; 
			witholdbatprice = document.getElementById("witholdbatprice").value;
			cusname = document.getElementById("cusname").value; 
			cusemailid = document.getElementById("cusemailid").value; 
			cusaddr1 = document.getElementById("cusaddr1").value; 
			cusaddr2 = document.getElementById("cusaddr2").value;
			cusstate = document.getElementById("constate").value; 
			cuscity = document.getElementById("concity").value; 
			cusarea = document.getElementById("cusarea").value;
			order_type = document.getElementById("order_type").value;
			Quantity = document.getElementById("Quantity").value;
			
			if(batbrand == "" || batbrand == "0" || batbrand == "default" || batbrand == "defaultss")
			{
				errMsg ="<font color='#9B5BDD'>Please Select Battery Brand</font>";
				document.getElementById("displayrefinederrormsg1").innerHTML=errMsg;
				$("#batbrand").focus();
				return ;
			}

			if(batmodel == "" || batmodel == "0" || batmodel == "default" || batmodel == "defaultss")
			{
				errMsg ="<font color='#9B5BDD'>Please Select Battery Model</font>";
				document.getElementById("displayrefinederrormsg1").innerHTML=errMsg;
				$("#batmodel").focus();
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
			
			if(order_type == "" || order_type == "0" || order_type == "default" || order_type == "defaultss")
			{
				errMsg ="<font color='#9B5BDD'>Please Select Battery Model</font>";
				document.getElementById("displayrefinederrormsg1").innerHTML=errMsg;
				$("#order_type").focus();
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
		
		$('#divpostponed').hide();
		var xmlhttp= "";
		var resp= "";
		var url ="../../../servlet/MISOperatorServiceDetails?hidWhatToDo=updateorderstatus&orderid="+orderid+"&ordernumber="+ordernumber+"&orderstatus="+orderstatus+"&orderreason="+orderreason+"&agentcomments="+cusmessage+"&postponedate="+postponedate+"&retailertoorder="+retailertoorder+"&rating="+rating+"&payment_mode="+payment_mode+"&SMSURL="+SMSURL+"&shorturl="+shorturl+"&cusarea="+cusarea+"&paymentcollected="+paymentcollected;
				
		//alert(url);
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
					$("#defaultModal").html("");
					var AfterOrder_PopUp="<div class='modal-dialog' role='document'><div class='modal-content'> <div class='modal-header bg-amber'><h4 class='modal-title' id='after_order_popup_label'>Confirmation Message - BookBattery</h4><button type='button' data-dismiss='modal' class='close' data-dismiss='modal' aria-label='Close'><span aria-hidden='true'> x</span></button></div> <div class='modal-body'> 	<div class='form-group'> <div id='after_order_popup_data'>"+resp+"<br/></br/></div> <div style='text-align: center;'><button type='button' class='btn bg-amber waves-effect' data-dismiss='modal' onclick='GetServiceDetails()'>Okay</button> </div></div> </div></div> </div>";
				
					$("#defaultModal").modal({show: true, backdrop: 'static', keyboard: false});
					$("#defaultModal").html(AfterOrder_PopUp);
				}
			}			
		}
		xmlhttp.open("GET",url, true);		
		xmlhttp.send();	
 }
 
 /** Function to show options to select if CustomernotContacted **/
 function CustomernotContacted(orderid,ordernumber,orderstatus)
 {
	 var OrderLable="Customer Not Contacted";
		var OrderMessage ="<div class='row clearfix'><div class='col-sm-12'><div class='form-group'><p><b>Order Resason</b></p><div><select name='orderreason'  id='orderreason'  class='btn-group bootstrap-select form-control show-tick yes' onChange=\'javascript:setagentcomments('"+orderid+"','"+ordernumber+"','"+orderstatus+"')\'><option value='0' >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Select Reason</option><option value='Phone Busy'>Phone Busy</option><option value='Phone Not Reachable'>Phone Not Reachable</option><option value='Phone Switched Off'>Phone Switched Off</option></select><div id='orderreason-error' style='display:none;'></div></div></div><div class='form-group'><div class='form-line'><textarea rows='1' class='form-control no-resize auto-growth yes' id='cusmessage' name='cusmessage' placeholder='Please type what you want'></textarea></div><div id='cusmessage-error' style='display:none;'></div></div></div></div>";
		 
		 var AfterOrder_PopUp="<div class='modal-dialog' role='document'><div class='modal-content'> <div class='modal-header'><h4 class='modal-title' id='after_order_popup_label'>"+OrderLable+"</h4><button type='button' onclick='okey_done()' class='close' data-dismiss='modal' aria-label='Close'><span aria-hidden='true'> x</span></button> 	</div> <div class='modal-body'> 	<div class='form-group'> <div id='after_order_popup_data'> "+OrderMessage+"</div> <div style='text-align: center;'><button type='button' class='btn bg-purple waves-effect' onclick=\"javascript:UpdateOrderStatus('"+orderid+"','"+ordernumber+"','"+orderstatus+"')\">Update Status</button>&nbsp;&nbsp;&nbsp;<button type='button' class='btn bg-indigo waves-effect' data-dismiss='modal'>Cancel</button> </div> 	</div> </div> 	</div> </div>";
		$("#defaultModal").modal({show: true, backdrop: 'static', keyboard: false});
		$("#defaultModal").html(AfterOrder_PopUp);	
}

 /** Function to show options to select if Repeatedvis **/
 function Repeatedvis(orderid,ordernumber,orderstatus)
 {
	
		var OrderLable="Repeated";
		var OrderMessage="Repeated";
		
		
		var OrderMessage ="<div class='row clearfix'><div class='col-sm-12'><div class='form-group'><p><b>Order Resason</b></p><div class='form-line'><select name='orderreason' id='orderreason'  class='btn-group bootstrap-select form-control show-tick yes' onchange='javascript:setagentcomments()'><option value='0'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Select Reason</option><option value='Customer Has Placed Order For Twice'>Customer Has Placed Order For Twice</option></select><div id='orderreason-error' style='display:none;'></div></div></div><div class='form-group'><div class='form-line'><textarea rows='1' class='form-control no-resize auto-growth yes' id='cusmessage' name='cusmessage' placeholder='Please type what you want'></textarea></div><div id='cusmessage-error' style='display:none;'></div></div></div></div>";
		 
		 var AfterOrder_PopUp="<div class='modal-dialog' role='document'><div class='modal-content'> <div class='modal-header'><h4 class='modal-title' id='after_order_popup_label'>"+OrderLable+"</h4><button type='button' onclick='okey_done()' class='close' data-dismiss='modal' aria-label='Close'><span aria-hidden='true'> x</span></button> 	</div> <div class='modal-body'> 	<div class='form-group'> <div id='after_order_popup_data'> "+OrderMessage+"</div> <div style='text-align: center;'><button type='button' class='btn bg-purple waves-effect' onclick=\"javascript:UpdateOrderStatus('"+orderid+"','"+ordernumber+"','"+orderstatus+"')\">Update Status</button>&nbsp;&nbsp;&nbsp;<button type='button' class='btn bg-indigo waves-effect' data-dismiss='modal'>Cancel</button> </div> 	</div> </div> 	</div> </div>";
$("#defaultModal").modal({show: true, backdrop: 'static', keyboard: false});
$("#defaultModal").html(AfterOrder_PopUp);	
 
 }
 
 
 /** Function to show options to select if customer postponed **/

 function Postponedvis(orderid,ordernumber,orderstatus)
 {
	 var OrderLable="PostPoned";
	 var OrderMessage ="<div class='row clearfix'><div class='col-sm-12'><div class='form-group'><p><b>Order Resason</b></p><div><select name='orderreason' id='orderreason'  class='btn-group bootstrap-select form-control show-tick yes' onchange='javascript:setagentcomments()'><option value='0'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Select Reason</option><option value='High Priority'>High Priority</option><option value='Customer is not picking the Call'>Customer is not picking the Call</option><option value='Customer Number Busy'>Customer Number is Busy</option><option value='Customer Number is Not Reachable'>Customer Number is Not Reachable</option><option value='Customer Number Switched Off'>Customer Number is Switched Off</option><option value='Customer is Out of Station'>Customer is Out of Station</option><option value='Customer is Not Responding to our Calls'>Customer is Not Responding to our Calls</option><option value='Customer need Installation Today'>Customer need Installation Tomorrow</option><option value='Need to check with franchisee whether the battery is currently available'>Franchisee is not having the Stock</option><option value='Customer Car Old battery is Working Fine need installation today'>Customer Car Old battery is Working Fine Presently</option><option value='Car Old Battery is working Fine He need installation Today'>Car Old Battery is working Fine, He donot need installation Now</option><option value='Yesterdays After Business Hour Order'>As it is not business hours, we will install the battery tomorrow</option><option value='Pitstop is So Far Customer will collect the battery whenever he is free'>Pitstop is So Far, Customer will collect the battery whenever he is free</option><option value='Customer need installation today'>Customer is busy today, he need installation later</option><option value='Customer need latest Manufacturing Battery'>Customer need latest Manufacturing Battery</option><option value='Order Status is not yet confirmed from Franchisee or Customer'>Order Status is not yet confirmed from Franchisee or Customer</option><option value='Customer will collect the battery today'>Customer will collect the battery tomorrow</option><option value='Customer will collect the battery today'>Customer Bike Old battery is Working Fine Presently</option><option value='Pitstop was on Leave Yesterday Need to process the order today'>Franchisee is on Leave Today</option></select><div id='orderreason-error' style='display:none;'></div></div></div><div class='form-group'> <input id='datetimepicker1' type='text' class='form-control yes' /></div><div class='form-group'><div class='form-line'><textarea rows='1' class='form-control no-resize auto-growth' id='cusmessage' name='cusmessage' placeholder='Please type what you want'></textarea><div id='cusmessage-error' style='display:none;'></div></div></div></div></div>";
		
		 
	 var AfterOrder_PopUp="<div class='modal-dialog' role='document'><div class='modal-content'> <div class='modal-header'><h4 class='modal-title' id='after_order_popup_label'>"+OrderLable+"</h4><button type='button' onclick='okey_done()' class='close' data-dismiss='modal' aria-label='Close'><span aria-hidden='true'> x</span></button> 	</div> <div class='modal-body'> 	<div class='form-group'> <div id='after_order_popup_data'> "+OrderMessage+"</div> <div style='text-align: center;'><button type='button' class='btn bg-purple waves-effect' onclick=\"javascript:UpdateOrderStatus('"+orderid+"','"+ordernumber+"','"+orderstatus+"')\">Update Status</button>&nbsp;&nbsp;&nbsp;<button type='button' class='btn bg-indigo waves-effect' data-dismiss='modal'>Cancel</button> </div> 	</div> </div> 	</div> </div>";
$("#defaultModal").modal({show: true, backdrop: 'static', keyboard: false});
$("#defaultModal").html(AfterOrder_PopUp);	

 }

function generateInvoice(ordnum){
	
	//alert(ordnum);
		$.ajax({
			type: "GET",
			url: "/bookbattery/servlet/UpdatedBatteryOrderDetails?hidWhatToDo=generateorderinvoice",
			data: {ordernumber: ordnum},
			success: function(data){
				//alert(data);
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
				
				var AfterOrder_PopUp="<a class='btn hide' data-toggle='modal' data-target='.after_order_popup' data-toggle='modal' data-backdrop='static' data-keyboard='false' id='after_order_popup_btn'></a><div class='modal fade after_order_popup' tabindex='-1' role='dialog' aria-labelledby='after_order_popup_label'> <div class='modal-dialog' role='document'> 	<div class='modal-content'> <div class='modal-header bg-primary'><h4 class='modal-title' id='after_order_popup_label'>"+OrderLable+"</h4><button type='button' onclick='okey_done()' class='close' data-dismiss='modal' aria-label='Close'><span aria-hidden='true'> x</span></button> 	</div> <div class='modal-body'><div class='form-group'> <div id='after_order_popup_data'> "+OrderMessage+"</div> <div style='text-align: center;'><button type='button' class='btn bg-primary waves-effect' data-dismiss='modal' onclick='GetServiceDetails()'>Okay</button> </div></div> </div></div> </div> </div>";
				
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