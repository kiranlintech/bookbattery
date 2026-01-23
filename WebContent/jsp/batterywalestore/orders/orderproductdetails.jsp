<!--
    Document   : orderbatterydetails
    Created on : Oct 19, 2019, 4:22:12 PM
    Author     : C Prasanna Kumari.
-->
<%@ page language="java" import="java.sql.*"%>
<%@page language="java" import="java.io.File,java.text.*,java.util.Calendar,java.util.ArrayList,java.util.Hashtable,java.util.Vector,com.ngit.javabean.loglevel.*,java.util.Date,java.util.Properties,java.util.*,com.ngit.javabean.loglevel.LogLevel,javax.servlet.ServletContext,java.util.Properties,java.io.FileInputStream"%>
<%response.setHeader("Pragma","no-cache"); response.setHeader("Cache-Control","no-store"); response.setHeader("Expires","0"); response.setDateHeader("Expires",-1); %>

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
%>

<%
Properties propsMOPConfig = new Properties();
ServletContext context = getServletContext();
FileInputStream fin1      = new FileInputStream(new File(context.getRealPath("properties/bookbatteryconfig.properties"))); 
propsMOPConfig.load(fin1); 
fin1.close(); 
String publicUrl = (propsMOPConfig.getProperty("publicUrl")!=null)?propsMOPConfig.getProperty("publicUrl"):"";
String baseURL = (propsMOPConfig.getProperty("baseURL")!=null)?propsMOPConfig.getProperty("baseURL"):"";
String baseurldirectory = (propsMOPConfig.getProperty("baseurldirectory")!=null)?propsMOPConfig.getProperty("baseurldirectory"):"";
String serverURL = (propsMOPConfig.getProperty("serverURL")!=null)?propsMOPConfig.getProperty("serverURL"):"";

Properties product_details = new Properties(); 
FileInputStream product_details_file = new FileInputStream("/home/ngit/tomcat/webapps/bookbattery/jsp/BookBatterystore/orders/product_details.html"); 
product_details.load(product_details_file); 
product_details_file.close();

String titles = "";
String description = "";
String keywords = "";

%> 
<%
String Map_Id = (String) request.getAttribute("Map_Id");
String Battery_Type = (String) request.getAttribute("Battery_Type");
String Battery_Brand = (String) request.getAttribute("Battery_Brand");
String Battery_Model = (String) request.getAttribute("Battery_Model");
String Vehicle_Make = (String) request.getAttribute("Vehicle_Make");
String Vehicle_Model = (String) request.getAttribute("Vehicle_Model");
String Battery_Capacity = (String) request.getAttribute("Battery_Capacity");
String Battery_Name = (String) request.getAttribute("Battery_Name");
String Battery_Warranty = (String) request.getAttribute("Battery_Warranty");
String Battery_Image = (String) request.getAttribute("Battery_Image");
String Battery_Description = (String) request.getAttribute("Battery_Description");
String Battery_Act_Price = (String) request.getAttribute("Battery_Act_Price");
String Battery_Witbat_Price = (String) request.getAttribute("Battery_Witbat_Price");
String Battery_Ret_Price = (String) request.getAttribute("Battery_Ret_Price");
String Battery_ERP_Price = (String) request.getAttribute("Battery_ERP_Price");
String Battery_Flag = (String) request.getAttribute("Battery_Flag");
String Stock_Status = (String) request.getAttribute("Stock_Status");
String State = (String) request.getAttribute("State");
String City = (String) request.getAttribute("City");
String Area = (String) request.getAttribute("Area");
String Pincity = (String) request.getAttribute("Pincity");
String delivery_cities  = (String) request.getAttribute("delivery_cities");
 

String State_URL= State.replaceAll(" ", "-");
String City_URL= City.replaceAll(" ", "-");

String Battery_Type_URL= Battery_Type.replaceAll(" ", "-");
String Battery_Brand_URL= Battery_Brand.replaceAll(" ", "-");
String Battery_Model_URL= Battery_Model.replaceAll(" ", "+");

Battery_Warranty= Battery_Warranty.replaceAll("Pro-rata", "<span class='Pro-rata' data-toggle='tooltip' data-placement='top' title='Pro-rata warranty is a kind of partial warranty, If your battery fails in the pro-rata warranty cycle then depending on the value of the battery, you will get discount on the Current MRP on the newly replaced battery.'>Pro-rata</span>");

String Battery_Model_Cat=Battery_Name;
Battery_Model_Cat= Battery_Model_Cat.replaceAll(" ", "-");
Battery_Model_Cat=Battery_Model_Cat.trim();
LogLevel.DEBUG(5,new Throwable(),"Battery_Model_Cat :"+Battery_Model_Cat );
int Battery_Act_Price_int = Integer.parseInt(Battery_Act_Price);
int WithOutOldBattery = Integer.parseInt(Battery_Witbat_Price);
int WithOldBattery_Less = Integer.parseInt(Battery_Ret_Price);
int WithOldBattery = WithOutOldBattery - WithOldBattery_Less;
int Save_Price = Battery_Act_Price_int - WithOldBattery;

int WithOutOldBattery_Percent=(((Battery_Act_Price_int-WithOutOldBattery)*100)/Battery_Act_Price_int);
int WithOldBattery_Percent=(((Battery_Act_Price_int-WithOldBattery)*100)/Battery_Act_Price_int);

LogLevel.DEBUG(5,new Throwable(),"Area :"+Area );
LogLevel.DEBUG(5,new Throwable(),"Pincity :"+Pincity );
LogLevel.DEBUG(5,new Throwable(),"Battery_ERP_Price JSO :"+Battery_ERP_Price );
LogLevel.DEBUG(5,new Throwable(),"Vehicle_Model :"+Vehicle_Model );
LogLevel.DEBUG(5,new Throwable(),"Vehicle_Make :"+Vehicle_Make );


%>

<jsp:include page = "../storeheader.jsp" />
<jsp:include page = "../storeleftmenu.jsp" />
<!-- Bootstrap Spinner Css -->
<link href="/bookbattery/css/BookBatterystore/plugins/jquery-spinner/css/bootstrap-spinner.css" rel="stylesheet">

	<section class="content">
      <form name="orderbatterydetails" method="post">
		<div class="container-fluid">
             

            <!-- CKEditor -->
            <div class="row clearfix">
                <div class="col-md-12">
                    <div class="card">
                        <div class="header">
                            <h2>
                                VIEW PRODUCT DETAILS 
                            </h2>
                            
                        </div>
                        <div class="body">
							<div class="row">
								 <div class="col-sm-4 col-xs-12">
										<img alt="<%=Battery_Brand%> <%=Battery_Model%>" itemprop="image" src="<%=Battery_Image%>" style="width: 100%;"> 
								 </div>
								 <div class="col-sm-5 col-xs-12">
									<h4 itemprop="name"><%=Battery_Brand%>  <%=Battery_Model%></h4>
									 <div class="short-description">
										<h5><strong><u>Quick Overview</u></strong></h5>
										<table style=" color: #494848;">
										 <colgroup> <col class="col-xs-5"> <col class="col-xs-7"> </colgroup>
											  <tr>
												  <th>Product Brand </th>
												  <td itemprop="brand">: <%=Battery_Brand%></td>
											  </tr>
											  <tr>
												  <th>Model Number</th>
												  <td itemprop="mpn">: <%=Battery_Model%></td>
											  </tr>
												 <%
												  if(Battery_Name.equals("0") || Battery_Name.equals("")|| Battery_Name.equals("null"))
													{}
													else
													{							
												%>
											  <tr>
												  <th>Product Name</th>
												  <td>: <%=Battery_Name%></td>
											  </tr>
												 <%
													}
												 %>
											  <tr>
												  <th>Product Capacity</th>
												  <td>: <%=Battery_Capacity%></td>
											  </tr>
												<%
												  if(Battery_Flag.equals("0") || Battery_Flag.equals("")|| Battery_Flag.equals("null"))
													{}
													else
													{							
												%>
											  <tr>
												  <th>Product Type</th>
												  <td>: <%=Battery_Flag%></td>
											  </tr>
												 <%
													}
												 %>
											  <tr>
												  <th>Product Warranty</th>
												  <td>: <%=Battery_Warranty%></td>
											  </tr>
										  </table>
									  </div>
									   <p class="availability">Availability : 
				    
										 <%
										  if(Stock_Status.equals("No") || Stock_Status.equals("no"))
											{
										%>	
										
											<span class="label label-danger">Out of Stock</span>
										<%
											}
											else
											{							
										%>
											
											<span class="label label-success">In Stock</span>
										<%
											}						
										%>
									  </p>
									  
									 <div class="" style="margin:-14px;">
											<table class="table">
												<thead>
													<tr>
														<th>Regular Price</th><th>: <strike><%=Battery_Act_Price%></strike></th> 
													</tr>
												</thead>
												<tbody>
													<div class="demo-radio-button">
														<tr>
															<th><input  name="battery_buy_type" value="New" type="radio" onclick="changeprice(this);" id="radio_3" /><label for="radio_3">With Out Old Battery</th><th>: <%=Battery_Witbat_Price%></th> 
														</tr>
														<tr>
															<th><input name="battery_buy_type" value="Replaced" type="radio" onclick="changeprice(this);" id="radio_4" checked/><label for="radio_4">With Old Battery<span style="font-size: 10px;"> (Same Ah)</span></th><th>: <%=WithOldBattery%></th> 
														</tr>
													</div> 
												</tbody>
											</table>
										</div>	
										<div class="col-sm-6 col-xs-12"> 
											 <button type="button" class="btn btn-warning waves-effect" onclick="javascript:negotitateprice('<%=Battery_Brand%>','<%=Battery_Model%>','<%=WithOutOldBattery%>','<%=WithOldBattery%>','<%=Battery_Ret_Price%>')">
												<i class="material-icons">shuffle</i>
												<span>NEGOTIATE PRICE</span>
											</button>
										</div>
										<div class="col-sm-6 col-xs-12"> 
											<button type="button" class="btn bg-brown waves-effect" onclick="Askcosumerdetails('<%=Battery_Brand%>','<%=Battery_Model%>','<%=WithOutOldBattery%>','<%=WithOldBattery%>')">
												<i class="material-icons">done_all</i>
												<span>ORDER NOW</span>
											</button>
										</div>
										<div id="discountdetails"></div>
								 </div>
								 
							</div>
								
                        </div>
                    </div>
                </div>
            </div>
            <!-- #END# CKEditor -->
            
        </div>
    </form>
    </section>
	  <!-- Small Size -->
            <div class="modal fade" id="smallModal" tabindex="-1" role="dialog">
                <div class="modal-dialog modal-md" role="document">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h4 class="modal-title" id="smallModalLabel">Negotitate Price</h4>
                        </div>
						<div id="discount_div"></div>
                    </div>
                </div>
            </div> 
            <div class="modal fade" id="exampleModal" tabindex="-1" role="dialog">
               
            </div>
		<!-- Default Size -->
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
											<label>Select Battery Order Type:<font color="red"> *</font></label>
											   <select class="form-control show-tick yes" id="order-form-order_type" name="order-form-ordered_by">
													<option value="-1">-- Please select --</option>
													<option value="Replaced">Replaced</option>
													<option value="New">New</option> 
												</select>
												<div id="order-form-order_type-error" style="display:none;"></div>
											</div>
										</div>
                               		 </div>
                                </div>
                               <div class="row clearfix">
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
									<div class="col-md-6">
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
                                  <div class="form-group" id="home_delivery">
		                                    <div class="form-line">
		                                    <label>Select Home Delivery Mode:<font color="red"> *</font></label>
                                   			
		                                       <select class="form-control show-tick yes" id="order-form-delivery_mode" name="order-form-delivery_mode">
													<option value="-1">-- Please select --</option>
													<option value="Yes">Yes</option>
													<option value="No">No</option> 
												</select>
												<div id="order-form-delivery_mode-error" style="display:none;"></div>
		                                    </div>
		                                </div> 
                                <div class="row clearfix">
									<div class="col-md-6">
										<div class="form-group">
										<label class="form-label">Enter Reference Code</label>
											<div class="form-line">
											
												<input type="text" class="form-control yes name" name="order-form-referrence_code"  id="order-form-referrence_code">
												
												<div id="order-form-referrence_code-error" style="display:none;"></div>
											</div>
										</div>
									</div>
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
                            <button type="button" class="btn bg-deep-orange waves-effect" onclick="FuntoOrderBattery()">ORDER BATTERY</button>
                            <button type="button" class="btn bg-indigo waves-effect" data-dismiss="modal">CLOSE</button>
                        </div>
                    </div>
                </div>
            </div>


<input type="hidden" name="batterytype" id="batterytype" value="<%=Battery_Type%>">
<input type="hidden" name="vehiclemake" id="vehiclemake" value="<%=Vehicle_Make%>">
<input type="hidden" name="vehiclemodel" id="vehiclemodel" value="<%=Vehicle_Model%>">
<input type="hidden" name="batterybrand" id="batterybrand" value="<%=Battery_Brand%>">
<input type="hidden" name="batterymodel" id="batterymodel" value="<%=Battery_Model%>">
<input type="hidden" name="batterycapacity" id="batterycapacity" value="<%=Battery_Capacity%>">
<input type="hidden" name="battery_mrpprice" id="battery_mrpprice" value="<%=Battery_Act_Price%>">
<input type="hidden" name="battery_withoutprice" id="battery_withoutprice" value="<%=Battery_Witbat_Price%>">
<input type="hidden" name="battery_withprice" id="battery_withprice" value="<%=WithOldBattery%>">
<input type="hidden" name="battery_erpprice" id="battery_erpprice" value="<%=Battery_ERP_Price%>">
<input type="hidden" name="strarea" id="strarea" value="<%=Area%>">
<input type="hidden" name="strcity" id="strcity" value="<%=City%>">
<input type="hidden" name="strstate" id="strstate" value="<%=State%>">
<input type="hidden" name="strpincode" id="strpincode" value="<%=Pincity%>">
<input type="hidden" name="store_id" id="store_id" value="<%=strStoreId%>">
<input type="hidden" name="store_name" id="store_name" value="<%=strStoreName%>">

<input type="hidden" name="neg_withoutprice" id="neg_withoutprice" value="">
<input type="hidden" name="neg_withprice" id="neg_withprice" value="">

<input type="hidden" name="serverURL" id='serverURL' value="<%=serverURL%>">

	<!-- Jquery Spinner Plugin Js -->
    <script src="/bookbattery/css/BookBatterystore/plugins/jquery-spinner/js/jquery.spinner.js"></script>

<jsp:include page = "../storefooter.jsp" />
	
<script type="text/javascript">

function negotitateprice(strbatbrand,strBatmodel,strdisprice,strwithoutprice,strbattretprice){
	  
	 /*alert(strbatbrand);
	  alert(strBatmodel);
	  alert(strdisprice);
	  alert(strwithoutprice);
	  alert(strbattretprice);*/
	  
	//  document.getElementById("discount_div").innerHTML = "<div class='modal-body'><label for='email_address'>Enter ssssPercentage to discount price.<font color='red'>*</font></label><div class='form-group'><div class='form-line'><input type='text' id='discountprice' class='form-control' placeholder='1% as 0.01'></div><small>&nbsp;Note&nbsp;please&nbsp;enter&nbsp;1%&nbsp;as&nbsp;0.01.</small></div><label for='email_address'>Enter&nbsp;Battery&nbsp;Return&nbsp;price[OBRP].<font color='red'>*</font></label><div class='form-group'><div class='form-line'><input type='text' id='batretprice' class='form-control' value='"+strbattretprice+"'></div></div><button type='button' class='btn bg-pink waves-effect' onclick=\"javascript:getdiscountprices('"+strbatbrand+"','"+strBatmodel+"','"+strdisprice+"','"+strwithoutprice+"','"+strbattretprice+"')\">UPDATE PRICES</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<button type='button' class='btn bg-blue waves-effect' data-dismiss='modal'>CLOSE</button></div>";
	
	document.getElementById("discount_div").innerHTML = "<div class='modal-body'><label for='price'>Existing Price(Without Old Battery).<font color='red'>*</font></label><div class='form-group'><div class='form-line'><input type='text' id='changeprice' class='form-control' value='"+strdisprice+"'></div></div><label for='scrap'>Existing&nbsp;Scrap&nbsp;Price.<font color='red'>*</font></label><div class='form-group'><div class='form-line'><input type='text' id='changebatretprice' class='form-control' value='"+strbattretprice+"'></div></div><button type='button' class='btn bg-green waves-effect' onclick=\"javascript:checkdiscountprices('"+strbatbrand+"','"+strBatmodel+"','"+strdisprice+"','"+strwithoutprice+"','"+strbattretprice+"')\">CHECK PRICES</button></div><div id='discountprices'></div><div id='updatebutton' style='display:none;'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<button type='button' class='btn bg-pink waves-effect' onclick=\"javascript:displaydiscountedprices('"+strbatbrand+"','"+strBatmodel+"')\">UPDATE PRICES</button>&nbsp;&nbsp;&nbsp;&nbsp;<button type='button' class='btn bg-blue waves-effect' data-dismiss='modal'>CLOSE</button><br/></br/></div>";
 $("#smallModal").modal({show: true, backdrop: 'static', keyboard: false});
	
}

function displaydiscountedprices(strbatbrand,strBatmodel){
	
	var changeprice=$("#changeprice").val();
	var newprice = parseInt(changeprice);
	var changebatretprice=$("#changebatretprice").val();
	var scrapprice = parseInt(changebatretprice);
	var newobrpprice = newprice - scrapprice;
	
	/*alert(newprice);
	alert(scrapprice);
	alert(newobrpprice);*/
	$("#smallModal").modal('hide');
	document.getElementById("discountdetails").innerHTML = "<table class='table'><div class='demo-radio-button'><tr><th><input  name='battery_buy_type' value='New' type='radio' id='radio_1'/><label for='radio_1'>With Out Old Battery Take Back </th><th>: "+newprice+"</th></tr><tr><th><input name='battery_buy_type' value='Replaced' type='radio' id='radio_2' checked/><label for='radio_2'>With Old Battery Take Back<span style='font-size: 11px;'> (Same Ah)</span></th><th> :"+newobrpprice+"</th></tr></div></table>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<button type='button' class='btn bg-green waves-effect' onclick=\"javascript:Askcosumerdetails('"+strbatbrand+"','"+strBatmodel+"','"+newprice+"','"+newobrpprice+"');\"><i class='material-icons'>done_all</i><span>ORDER NOW</span></button>"; 
	
}
function checkdiscountprices(strbatbrand,strBatmodel,strdisprice,strwithoutprice,strbattretprice){
	
	 
	 /*alert(strbatbrand);
	  alert(strBatmodel);
	  alert(strdisprice);
	  alert(strwithoutprice);
	  alert(strbattretprice);*/
	  var changeprice=$("#changeprice").val();
	  var changebatretprice=$("#changebatretprice").val();
	  //alert("after change");
	  //alert(changeprice);
	  //alert(changebatretprice);
		  
			$.ajax({
				type: "GET",
				url: "/bookbattery/servlet/BatterystoreBatteryDetails?hidWhatToDo=calculatediscount",
				data: {batbrand: strbatbrand, batmodel: strBatmodel, changeprice: changeprice, changebatretprice: changebatretprice },
				success: function(data){
					$("#smallModal").modal({show: true, backdrop: 'static', keyboard: false});
					// alert(data);
					 $("#updatebutton").show();
					 document.getElementById("discountprices").innerHTML = data;
				}
			});	
}


function getdiscountprices(strbatbrand,strBatmodel,strdisprice,strwithoutprice,strbattretprice){
	
	 var discountprice=$("#discountprice").val();
	 var batretprice=$("#batretprice").val();

	  
		$.ajax({
			type: "GET",
			url: "/bookbattery/servlet/BatterystoreBatteryDetails?hidWhatToDo=calculatepercentage",
			data: {batbrand: strbatbrand, batmodel: strBatmodel, disprice: strdisprice, withoutprice: strwithoutprice, battretprice: batretprice, pricediscount: discountprice },
			success: function(data){
				$("#smallModal").modal('hide');
				// alert(data);
				 document.getElementById("discountdetails").innerHTML = data;
			}
		});	 
}

function Askcosumerdetails(strbatbrand,strBatmodel,discountprice,strwithoutprices){
	
	  $("#orderModal").modal({show: true, backdrop: 'static', keyboard: false});
	  var pincode = $('#strpincode').val();
	  var state = $('#strstate').val();
	  var city = $('#strcity').val();
	  var area = $('#strarea').val();
	  var batterytype = $('#batterytype').val();
	  //alert(batterytype);
	  /*alert(pincode);
	  alert(state);
	  alert(city);
	  alert(area);*/
	  if(batterytype=="Bike Batteries"){
		  
		  $("#home_delivery").show();
		 
		  
	  } else {
		  
		  $("#home_delivery").hide();
	  }
	  document.getElementById("neg_withoutprice").value = discountprice;
	  document.getElementById("neg_withprice").value = strwithoutprices;
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

function changeprice(){
    
    //alert(22);
    var FinalService_Price_Discount_RXT;
    //alert($('#bat_type').val());
    //alert(value);
    var battery_buy_type = $("input[name='battery_buy_type']:checked").val();
	order_product_qty=$("#product_qty").val();
	//alert("order_product_qty"+order_product_qty);
    
    if(order_product_qty=="undefined" || order_product_qty==undefined)
    {
        order_product_qty=1;
    }
    else
    {
        order_product_qty=order_product_qty;
    }
    

      if(battery_buy_type=="New")
        {
           // alert($('#store_price').val());
            FinalService_Price_Discount_RXT=+$('#battery_withoutprice').val();
            $('#final_price').html("<i class='icon-inr' aria-hidden='true'></i>&nbsp;"+FinalService_Price_Discount_RXT);			
            $('#final_price').val($('#battery_withoutprice').val());          
        }	
        else if(battery_buy_type=="Replaced")
        {
            // alert($('#store_price').val());
            FinalService_Price_Discount_RXT=+$('#battery_withprice').val();
            $('#final_price').html("<i class='icon-inr' aria-hidden='true'></i>&nbsp;"+FinalService_Price_Discount_RXT);			
            $('#final_price').val($('#battery_withprice').val());   
        }		
        else
        {
            alert("Opps !. Some thing went wrong. Please reload the Page.");
        } 

}

function changepriceneg(discountprice,strwithoutprices){
    
	// alert(22);
	// alert(discountprice);
	// alert(strwithoutprices);
    var FinalService_Price_Discount_RXT;
    //alert($('#bat_type').val());
    //alert(value);
    var battery_buy_type = $("input[name='battery_buy_type']:checked").val();
	order_product_qty=$("#product_qty").val();
	//alert("order_product_qty"+order_product_qty);
    
    if(order_product_qty=="undefined" || order_product_qty==undefined)
    {
        order_product_qty=1;
    }
    else
    {
        order_product_qty=order_product_qty;
    }
    

      if(battery_buy_type=="New")
        {
           // alert($('#store_price').val());
            //FinalService_Price_Discount_RXT=+$('#neg_withoutprice').val();
            $('#final_price').html("<i class='icon-inr' aria-hidden='true'></i>&nbsp;"+discountprice);			
            $('#final_price').val(discountprice);          
        }	
        else if(battery_buy_type=="Replaced")
        {
            // alert($('#store_price').val());
           // FinalService_Price_Discount_RXT=+$('#neg_withprice').val();
            $('#final_price').html("<i class='icon-inr' aria-hidden='true'></i>&nbsp;"+strwithoutprices);			
            $('#final_price').val(strwithoutprices);   
        }		
        else
        {
            alert("Opps !. Some thing went wrong. Please reload the Page.");
        } 

}

function FuntoOrderBattery(){
		
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
		var batterytype = $('#batterytype').val();
		var vehiclemake = $('#vehiclemake').val();
		var vehiclemodel = $('#vehiclemodel').val();
		var batterybrand = $('#batterybrand').val();
		var batterycapacity = $('#batterycapacity').val();
		var battery_mrpprice = $('#battery_mrpprice').val();
		var battery_withoutprice = $('#battery_withoutprice').val();
		var battery_withprice = $('#battery_withprice').val();
		var battery_erpprice = $('#battery_erpprice').val();
		var batterymodel = $('#batterymodel').val();
		var store_id = $('#store_id').val();
		var store_name = $('#store_name').val();
		var neg_withoutprice = $('#neg_withoutprice').val();
		var neg_withprice = $('#neg_withprice').val();
		 if(batterytype=="Bike Batteries"){
			  
			 var delivery_mode = $('#order-form-delivery_mode').val();
			  //alert(delivery_mode);
			  if (yesValidation("","order-form-delivery_mode","yes") == false)
				{
					return;
					
				}
			  
		  } else {
			  
			  var delivery_mode = "Yes";
		  }
		 
		  var referrence_code = $('#order-form-referrence_code').val();
		 /* alert(referrence_code);
		  if (yesValidation("","order-form-referrence_code","yes") == false)
			{
				return;
				
			}*/
			
		//alert(battery_erpprice);
		/*alert(neg_withoutprice);
		alert(neg_withprice);
	   
	  alert(batterytype);
	  alert(vehiclemake);
	  alert(vehiclemodel);
	  alert(batterybrand);
	  alert(batterycapacity);
	  alert(battery_mrpprice);
	  alert(battery_withoutprice);
	  alert(battery_withprice);
	  alert(batterymodel); */
	 var More_info="";
	 var current_order_details ="";
	  $.ajax({
			type: "GET",
			url: "/bookbattery/servlet/BatterystoreBatteryDetails?hidWhatToDo=insertorderdetails",
			data: {mobile_number: orderform_mobile_number, consumer_name: consumer_name, consumer_emailid: consumer_emailid, consumer_address: consumer_address, ordered_by: ordered_by, order_type: order_type, payment_mode: payment_mode, quantity: quantity, delivery_mode: delivery_mode, referrence_code: referrence_code, zipcode: zipcode, area: area, city: city, state: state, batterytype: batterytype, vehiclemake: vehiclemake, vehiclemodel: vehiclemodel, batterybrand: batterybrand, batterycapacity: batterycapacity, battery_mrpprice: battery_mrpprice, battery_withoutprice: neg_withoutprice, battery_withprice: neg_withprice, battery_erpprice: battery_erpprice, batterymodel: batterymodel, store_id: store_id, store_name: store_name },
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
					OrderMessage="<div style='text-align: center;'><i class='icon-smile' style='font-size: 60px;color: #1d8e11;'></i><p style='font-size: 14px;font-weight: 600;'>Thanks for Ordering with BookBattery</p></div><div><div class='center-col col-md-12 col-sm-12 col-xs-12 nopadding-xs '><h4>Your Order Details</h4><table style=' color: #131212;font-size: 13px;'  class='table table-condensed'> <tbody> <tr> <th>Order Number&nbsp;:</th> <td>"+Response[1]+" </td> </tr><tr> <th>Vehicle Details&nbsp;:</th> <td>"+vehiclemake+", "+vehiclemodel+"</td> </tr> <tr> <th>Battery Details&nbsp;:</th> <td>"+batterybrand+", "+batterymodel+"</td> </tr> <tr> <th>Price Details&nbsp;:</th> <td>Without Old Battery - Rs. "+neg_withoutprice+"</br>With Old Battery - Rs. "+neg_withprice+"</td> </tr></tbody></table></div>"+More_info+"<hr size='100'><div style='text-align: center;'><p>Please Check your Email or Mobile for More Information regarding Your Order.<br /> Our Team will Call you soon for Order Confirmation.</p></div></div>";
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
