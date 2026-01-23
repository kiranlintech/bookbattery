<!--
    Document   : orderbatterydetails
    Created on : Oct 19, 2019, 4:22:12 PM
    Author     : C Prasanna Kumari.
-->
<%@ page language="java" import="java.sql.*"%>
<%@page language="java" import="java.io.File,java.text.*,java.util.Calendar,java.util.ArrayList,java.util.Hashtable,java.util.Vector,com.ngit.javabean.loglevel.*,java.util.Date,java.util.Properties,java.util.*,com.ngit.javabean.loglevel.LogLevel,java.util.Properties,java.io.FileInputStream"%>
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
String city ="";
Vector alist=(Vector)session.getAttribute("TrolledetailsVector");

String trolleybrand = (request.getParameter("trolleybrand")!=null)?(request.getParameter("trolleybrand")):"0";
LogLevel.DEBUG(5,new Throwable(),"trolleybrand :"+trolleybrand );

String trolleymodel = (request.getParameter("trolleymodel")!=null)?(request.getParameter("trolleymodel")):"0";
LogLevel.DEBUG(5,new Throwable(),"trolleymodel :"+trolleymodel );
 

if(city.equals("")){
	
	city = (request.getParameter("pincity")!=null)?(request.getParameter("pincity")):"0";
	LogLevel.DEBUG(5,new Throwable(),"city :"+city );

} else {
	
	city = (request.getParameter("city")!=null)?(request.getParameter("city")):"0";
	LogLevel.DEBUG(5,new Throwable(),"city :"+city );
}

String strpincity = (request.getParameter("pincity")!=null)?(request.getParameter("pincity")):"0";
LogLevel.DEBUG(5,new Throwable(),"strpincity :"+strpincity );


String strarea = (request.getParameter("area")!=null)?(request.getParameter("area")):"0"; 
LogLevel.DEBUG(5,new Throwable(),"strarea :"+strarea );

String strstate = (request.getParameter("state")!=null)?(request.getParameter("state")):"0"; 
LogLevel.DEBUG(5,new Throwable(),"strstate :"+strstate );

String strpincode = (request.getParameter("pincode")!=null)?(request.getParameter("pincode")):"0"; 
LogLevel.DEBUG(5,new Throwable(),"strpincode :"+strpincode );

Properties propsMOPConfig = new Properties();
ServletContext context = getServletContext();
FileInputStream fin1      = new FileInputStream(new File(context.getRealPath("properties/bookbatteryconfig.properties"))); 
propsMOPConfig.load(fin1); 
fin1.close(); 
String publicUrl = (propsMOPConfig.getProperty("publicUrl")!=null)?propsMOPConfig.getProperty("publicUrl"):"";
String baseURL = (propsMOPConfig.getProperty("baseURL")!=null)?propsMOPConfig.getProperty("baseURL"):"";
String baseurldirectory = (propsMOPConfig.getProperty("baseurldirectory")!=null)?propsMOPConfig.getProperty("baseurldirectory"):"";
String serverURL = (propsMOPConfig.getProperty("serverURL")!=null)?propsMOPConfig.getProperty("serverURL"):"";
  
%>
<jsp:include page = "../storeheader.jsp" />
<jsp:include page = "../storeleftmenu.jsp" />
<!-- Bootstrap Spinner Css -->
<link href="/bookbattery/css/BookBatterystore/plugins/jquery-spinner/css/bootstrap-spinner.css" rel="stylesheet">


  <section class="content">
      <form name="ordertrolleydetails" method="post">
		<div class="container-fluid">
             

            <!-- CKEditor -->
            <div class="row clearfix">
                <div class="col-md-12">
                    <div class="card">
                        <div class="header">
                            <h2>
                                VIEW TROLLEY DETAILS 
                            </h2>
                            
                        </div>
                        <div class="body">
							<div class="row">
								<%
								if(alist!=null && alist.size() > 0)
								{
								%>
								<%
								try
								{
									for ( int i=0; i<alist.size() ; i++)
									{

										Hashtable ht=(Hashtable)alist.get(i);
										String t_id=String.valueOf(ht.get("t_id"));
										String strtrolleybrand=String.valueOf(ht.get("trolley_brand"));
										String strtrolleymodel=(String)ht.get("trolley_model");
										String strtrolleyprice=String.valueOf(ht.get("trolley_price"));
 										String striconurl=(String)ht.get("icon_url");  
										 
										if(striconurl == null)
										{
											striconurl = "../../../images/noimage.jpg";
										}
										else
										{
											striconurl =striconurl;
										}
									 
								%>
									
										<div class="col-md-4" style="margin-bottom: 25px;">
											 <img align="center" style="width:65%;" src="<%=striconurl%>" ALT="battery"/>
											 <br/>
											 <strong><%=strtrolleybrand%>&nbsp;<%=strtrolleymodel%></strong> 
  											<table style=" color: #494848;font-size: 13px;">
												<tbody>
												 <tr>
													  <td>Price&nbsp;:</td>
													  <td>: <i class="icon-inr" aria-hidden="true"></i><%=strtrolleyprice%> </td>
												  </tr>
												 
												</tbody>
											</table>
												<button type="button" class="btn bg-red waves-effect" onclick="javascript:Askcosumerdetails('<%=strtrolleybrand%>','<%=strtrolleymodel%>','<%=strtrolleyprice%>','<%=strstate%>','<%=city%>','<%=strarea%>','<%=strpincode%>')">
													<i class="material-icons">trending_up</i>
													<span>Order Now</span>
												</button>
												
							 
										</div> 
										
								<%
								}
								}
								catch(Exception e)
								{
										e.printStackTrace();
								}
								%>  <!-----End of Try---->
								
								<%
								}
								else
								{
								%>
								 No Battery details found based on selection.! 
								 
								<%
								}
								%>
								</div>
								
							
                        </div>
                    </div>
                </div>
            </div>
            <!-- #END# CKEditor -->
            
        </div>
    </form>
    </section>
   
    <div class="modal fade" id="exampleModal" tabindex="-1" role="dialog">
               
    </div>
	 <div class="modal fade" id="orderModal" tabindex="-1" role="dialog">
                <div class="modal-dialog" role="document">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h4 class="modal-title" id="defaultModalLabel">Enter Customer/Installation Location Details</h4>
                        </div>
                        <div class="modal-body">
                             <form id="form_validation" method="POST">
                              <div class="row clearfix">
									<div class="col-md-6">
										  <div class="form-group form-float">
										   <label class="form-label">Mobile Number<font color="red"> *</font></label>
			                                 <div class="form-line">
			                                    <input type="tel" class="form-control yes" name="order-form-mobile_number" id="order-form-mobile_number" placeholder="9603467559" maxlength="10">
			                                        <div id="order-form-mobile_number-error" style="display:none;"></div>
			                                    </div>
			                                </div>
                                	</div>
                                	<div class="col-md-6">
		                                <div class="form-group form-float">
		                                 <label class="form-label">Name<font color="red"> *</font></label>
		                                    <div class="form-line">
		                                        <input type="text" class="form-control yes name" name="order-form-consumer_name"  id="order-form-consumer_name"  placeholder="Alex">
		                                       
												<div id="order-form-consumer_name-error" style="display:none;"></div>
		                                    </div>
		                                </div>
                                </div>
                                </div>
                                <div class="form-group form-float">
                                   
                                    <label class="form-label">Email<font color="red"> *</font></label>
                                     <div class="form-line">
                                     	    <input type="email" class="form-control yes" name="order-form-emailid"  id="order-form-emailid"  placeholder="alex@gmail.com">
                                        
										<div id="order-form-emailid-error" style="display:none;"></div>
                               		 </div>
                                </div>
                                <div class="form-group form-float"> 
                                     <label class="form-label">Address<font color="red"> *</font></label>
                                      <div class="form-line">
                                        <textarea name="order-form-address" id="order-form-address" cols="30" rows="3" class="form-control no-resize yes" placeholder="2-124,Road No:4,Whitefield,Bangalore"></textarea>
                                       
										<div id="order-form-address-error" style="display:none;"></div>
                                       </div>
                                </div>
                                <div class="row clearfix">
									<div class="col-md-6">
		                                <div class="form-group">
		                                    <div class="form-line">
		                                    <label>Select Ordered By:<font color="red"> *</font></label>
		                                       <select class="form-control show-tick yes" id="order-form-ordered_by" name="order-form-ordered_by">
													<option value="-1">-- Please select --</option>
													<option value="Online Customer">Online Customer</option>
													<option value="Walk-in Customer">Walk-in Customer</option>  
												</select>
												<div id="order-form-ordered_by-error" style="display:none;"></div>
		                                    </div>
		                                </div> 
                               		 </div>
                               		 <div class="col-md-6">
										  <div class="form-group">
											<div class="form-line">
											<label>Select Payment Mode:<font color="red"> *</font></label>
											   <select class="form-control show-tick yes" id="order-form-payment_mode" name="order-form-payment_mode">
													<option value="-1">-- Please select --</option>
													<option value="Cash On Delivery">Cash On Delivery</option>
													<option value="Store UPI PhonePe">Store UPI PhonePe</option> 
													<option value="Online Payment After Fitment">Online Payment After Fitment</option> 
													<option value="Card On Delivery" disabled>Card On Delivery</option> 
												</select>
												<div id="order-form-payment_mode-error" style="display:none;"></div>
											</div>
										</div> 
									</div> 
                                </div>
                               <div class="row clearfix">
									 
									<div class="col-md-12">
										<div class="input-group spinner" data-trigger="spinner">
											<div class="form-line">
											<label class="form-label">Quantity:</label>
												<input type="text" class="form-control text-center yes" id="order-form-quantity" name="order-form-quantity" value="1" data-rule="quantity" readonly>
											</div>
											<span class="input-group-addon">
												<a href="javascript:;" class="spin-up" data-spin="up"><i class="glyphicon glyphicon-chevron-up"></i></a>
												<a href="javascript:;" class="spin-down" data-spin="down"><i class="glyphicon glyphicon-chevron-down"></i></a>
											</span>
										</div>
									</div> 
                                </div> 
                               
                                <div class="row clearfix">
							 
									<div class="col-md-6" id="pincode_div">
									<label class="form-label">Zipcode<font color="red"> *</font></label>
										 <div class="form-group">
											<div class="form-line">
											
												 <input type="text" class="form-control yes" name="order-form-zipcode"  id="order-form-zipcode"> 
												<div id="order-form-zipcode-error" style="display:none;"></div>
											</div>
										</div> 
									</div>
								</div> 
                                <div class="row clearfix" id="cityarea_div" style="display:none;">
									<div class="col-md-6">
										<label class="form-label">Area<font color="red"> *</font></label>
										<div class="form-group">
											<div class="form-line">
												 <input type="text" class="form-control yes" name="order-form-area"  id="order-form-area" disabled> 
												<div id="order-form-area-error" style="display:none;"></div>
											</div>
										</div>
									</div>
									<div class="col-md-6" >
									<label class="form-label">City<font color="red"> *</font></label>
										 <div class="form-group">
											<div class="form-line">
												 <input type="text" class="form-control yes" name="order-form-city"  id="order-form-city" disabled>
												
												<div id="order-form-city-error" style="display:none;"></div>
											</div>
										</div> 
									</div>
								</div> 
								    						
                            </form>
                         </div>
                        <div class="modal-footer">
                            <button type="button" class="btn bg-grey waves-effect" onclick="FuntoOrderTrolley()">ORDER TROLLEY</button>
                            <button type="button" class="btn bg-indigo waves-effect" data-dismiss="modal">CLOSE</button>
                        </div>
                    </div>
                </div>
            </div>

<input type="hidden" name="trolleybrand" id="trolleybrand" value="<%=trolleybrand%>">
<input type="hidden" name="trolleymodel" id="trolleymodel" value="<%=trolleymodel%>">
<input type="hidden" name="strarea" id="strarea" value="<%=strarea%>">
<input type="hidden" name="strcity" id="strcity" value="<%=city%>">
<input type="hidden" name="strstate" id="strstate" value="<%=strstate%>">
<input type="hidden" name="strpincode" id="strpincode" value="<%=strpincode%>">
<input type="hidden" name="store_id" id="store_id" value="<%=strStoreId%>">
<input type="hidden" name="store_name" id="store_name" value="<%=strStoreName%>">
<input type="text" name="price" id="price" value="">

	<!-- Jquery Spinner Plugin Js -->
    <script src="/bookbattery/css/BookBatterystore/plugins/jquery-spinner/js/jquery.spinner.js"></script>


<script type="text/javascript">
function Askcosumerdetails(strtrolleybrand,strtrolleymodel,strtrolleyprice,strstate,city,strarea,strpincode){
 
$("#orderModal").modal({show: true, backdrop: 'static', keyboard: false});
var pincode = $('#strpincode').val();
var state = $('#strstate').val();
var city = $('#strcity').val();
var area = $('#strarea').val();
document.getElementById("price").value = strtrolleyprice;

if(pincode=="")	  {
	  
	  $("#cityarea_div").show();
	  $("#pincode_div").hide();
	  document.getElementById("order-form-city").value = city;
	  document.getElementById("order-form-area").value = area;
	  
} else {
	  
	  $("#cityarea_div").hide();
	  $("#pincode_div").show();
	  document.getElementById("order-form-zipcode").value = pincode;
}

}

function FuntoOrderTrolley(){
	
	
	 var input_type;
	  var orderform_mobile_number = $('#order-form-mobile_number').val();
	  //alert(orderform_mobile_number); 
	  if (mobilenumberValidation("","order-form-mobile_number","mobilenumber") == false)
		{
			return;
			
		} 
	  var consumer_name = $('#order-form-consumer_name').val();
	  //alert(consumer_name); 
	  if (nameValidation("","order-form-consumer_name","name") == false)
		{
			return;
			
		} 
	  
	  var consumer_emailid = $('#order-form-emailid').val();
	  //alert(consumer_emailid);
	  if (emailValidation("","order-form-emailid","email") == false)
		{
			return;
			
		} 
		
	  var consumer_address = $('#order-form-address').val();
	  //alert(consumer_address);
	  if (addressValidation("","order-form-address","address") == false)
		{
			return;
			
		}
	  var ordered_by = $('#order-form-ordered_by').val();
	   //alert(ordered_by);
	   if (selectValidation("","order-form-ordered_by","select") == false)
		{
			return;
			
		}
	  var order_type = $('#order-form-order_type').val();
	   //alert(order_type);
	  if (selectValidation("","order-form-order_type","select") == false)
		{
			return;
			
		}
	  var payment_mode = $('#order-form-payment_mode').val();
	   //alert(payment_mode);
	  if (selectValidation("","order-form-payment_mode","select") == false)
		{
			return;
			
		}
	  var quantity = $('#order-form-quantity').val();
	  //alert(quantity);
	  if (yesValidation("","order-form-quantity","yes") == false)
		{
			return;
			
		}
   
	  var zipcode = $('#strpincode').val();
	  var area = $('#strarea').val();
	  //alert(area);
	  var city = $('#strcity').val();
	  //alert(city);
	  var state = $('#strstate').val();
	  //alert(state);
 	  var trolleymodel = $('#trolleymodel').val();
	  var trolleybrand = $('#trolleybrand').val(); 
	  var price = $('#price').val(); 
	  var store_id = $('#store_id').val();
	  var store_name = $('#store_name').val();
 		  
	//alert(price);	 
	 var More_info="";
	 var current_order_details ="";
	  $.ajax({
			type: "GET",
			url: "/bookbattery/servlet/BatterystoreTrolleyDetails?hidWhatToDo=insertorderdetails",
			data: {mobile_number: orderform_mobile_number, consumer_name: consumer_name, consumer_emailid: consumer_emailid, consumer_address: consumer_address, ordered_by: ordered_by, payment_mode: payment_mode, quantity: quantity, zipcode: zipcode, area: area, city: city, state: state, trolleybrand: trolleybrand, trolleymodel: trolleymodel, price: price, store_id: store_id, store_name: store_name },
			success: function(data){
				$("#orderModal").modal('hide');
				 //alert(data);
				 //current_order_details= current_order_details.replace(" . ", "</br>");
				var OrderLable="";
				var OrderMessage="";
				if(data.indexOf("Sucessfully")>=0)
				{
					$("#exampleModal").html("");
					Response = data.split('|');
					OrderLable="Successfully Ordered - BookBattery";
					OrderMessage="<div style='text-align: center;'><i class='icon-smile' style='font-size: 60px;color: #1d8e11;'></i><p style='font-size: 14px;font-weight: 600;'>Thanks for Ordering with BookBattery</p></div><div><div class='center-col col-md-12 col-sm-12 col-xs-12 nopadding-xs '><h4>Your Order Details</h4><table style=' color: #131212;font-size: 13px;'  class='table table-condensed'> <tbody> <tr> <th>Order Number&nbsp;:</th> <td>"+Response[1]+" </td> </tr><tr> <th>Trolley Details&nbsp;:</th> <td>"+trolleybrand+", "+trolleymodel+"</td> </tr></tbody></table></div>"+More_info+"<hr size='100'><div style='text-align: center;'><p>Please Check your Email or Mobile for More Information regarding Your Order.<br /> Our Team will Call you soon for Order Confirmation.</p></div></div>";
				}
				else if (data.indexOf("No Retailers Found")>=0)
				{
					OrderLable="Failed to Order - BookBattery";
					OrderMessage="<div style='text-align: center;'><i class='icon-frown' style='font-size: 60px;color: #de3315;'></i><h4>We are Sorry, No Retailers Found on Selected City or Area!! </h4><p style='font-size: 17px;'>Please Contact our Support Team at <br/><b>+91 96034 67559</b></p></div></br>";
					
				}else
				{
					OrderLable="Failed to Order - BookBattery";
					OrderMessage="<div style='text-align: center;'><i class='icon-frown' style='font-size: 60px;color: #de3315;'></i><h4>We are Sorry,Some thing went wrong !! </h4><p style='font-size: 17px;'>Please try to reorder or Contact our Support Team at <br/><b>+91 96034 67559</b></p></div></br>";
					
				}
				
				var AfterOrder_PopUp="<a class='btn hide' data-toggle='modal' data-target='.after_order_popup' data-toggle='modal' data-backdrop='static' data-keyboard='false' id='after_order_popup_btn'></a><div class='modal fade after_order_popup' tabindex='-1' role='dialog' aria-labelledby='after_order_popup_label'> <div class='modal-dialog' role='document'> 	<div class='modal-content'> <div class='modal-header'><h4 class='modal-title' id='after_order_popup_label'>"+OrderLable+"</h4><button type='button' onclick='okey_done()' class='close' data-dismiss='modal' aria-label='Close'><span aria-hidden='true'> x</span></button> 	</div> <div class='modal-body'> 	<div class='form-group'> <div id='after_order_popup_data'> "+OrderMessage+"</div> <div style='text-align: center;'> 	<button type='button' class='btn btn-secondary' onclick='okey_done()'>Okay</button> </div> 	</div> </div> 	</div> </div> </div>";
				
				$("#exampleModal").modal({show: true, backdrop: 'static', keyboard: false});
				$("#exampleModal").html(AfterOrder_PopUp);
				$("#after_order_popup_btn").click();
				}
		});
}


function okey_done(){
	
	 location.replace("/bookbattery/jsp/BookBatterystore/storehome.jsp");
}
 </script>
<jsp:include page = "../storefooter.jsp" />
