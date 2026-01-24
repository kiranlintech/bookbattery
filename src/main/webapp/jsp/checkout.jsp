<%-- 
Document   : index.jsp
Created on : Sep 15, 2016, 10:14:12 AM
Author     : Sai Krishna Daddala
--%>
<%@ page language="java" import="java.sql.*"%>
<%@page language="java" import="java.io.File,java.text.*,java.util.Calendar,java.util.ArrayList,java.util.Hashtable,java.util.Vector,com.ngit.javabean.loglevel.*,java.util.Date,java.util.Properties,java.util.*,com.ngit.javabean.loglevel.LogLevel,javax.servlet.ServletContext,java.util.Properties,java.io.FileInputStream"%>
<%
ServletContext context = getServletContext();
Properties propsMOPConfig = new Properties();
FileInputStream fin1      = new FileInputStream(new File(context.getRealPath("properties/bookbatteryconfig.properties"))); 
propsMOPConfig.load(fin1); 
fin1.close(); 
String publicUrl = (propsMOPConfig.getProperty("publicUrl")!=null)?propsMOPConfig.getProperty("publicUrl"):"";
String baseurldirectory = (propsMOPConfig.getProperty("baseurldirectory")!=null)?propsMOPConfig.getProperty("baseurldirectory"):"";
%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<!--[if IE]>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<![endif]-->
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Buy Car Battery, Inverter Batteries & Inverters Online in India at BookBattery.com</title>
<meta name="description" content="Looking for a Car Battery, Inverter Battery in India. Buy Amaron, Exide & Luminious BookBattery with free delivery, free installation & company warranty ">
<meta name="keywords" content="car battery, car battery online, 4  wheeler battery, Exide battery, amaron battery, amaron car battery, exide car battery, inverter battery, amaron inverter battery, exide inverter battery, inverter battery online, BookBattery">

<!---################################## CSS Include Starts  ################################------>
	<jsp:include page = "/assets/includes/includes_css.jsp" />
<!---################################## CSS Include Ends  ################################------>

</head>
<body>
<div class="page">

<!---################################## Header Include Starts  ################################------>
	<jsp:include page = "/assets/includes/includes_header.jsp" />
<!---################################## Header Include Ends  ################################------>
     
  <div class="main-container col2-right-layout">
    <div class="main container">
      <div class="row">
		<section class="main-container col1-layout">
		  <div class="main container">
			<div class="col-main">
			  <div class="cart">
				<div class="page-title">
				  <h2>Shopping Cart</h2>
				</div>
				<div class="table-responsive">
				  <form method="post" action="#updatePost/">
					<input type="hidden" value="Vwww7itR3zQFe86m" name="form_key">
					<fieldset>
					  <table class="data-table cart-table" id="shopping-cart-table">
						<thead>
						  <tr class="first last">
							<th rowspan="1">&nbsp;</th>
							<th rowspan="1"><span class="nobr">Product Name</span></th>
							<th rowspan="1"></th>
							<th colspan="1" class="a-center"><span class="nobr">Unit Price</span></th>
							<th class="a-center" rowspan="1">Qty</th>
							<th colspan="1" class="a-center">Subtotal</th>
							<th class="a-center" rowspan="1">&nbsp;</th>
						  </tr>
						</thead>
						<tfoot>
						  <tr class="first last">
							<td class="a-right last" colspan="50">
							  <button id="empty_cart_button" class="button btn-empty" title="Clear Cart" value="empty_cart" name="update_cart_action" type="submit"><span><span>Clear Cart</span></span></button>
							  <button class="button btn-update" title="Update Cart" value="update_qty" name="update_cart_action" type="submit"><span><span>Update Cart</span></span></button></td>
						  </tr>
						</tfoot>
						<tbody>
						  <tr class="first odd">
							<td class="image"><a class="product-image" title="Sample Product" href="product_detail.html"><img width="75" alt="Sample Product" src="products-images/product1.jpg"></a></td>
							<td><h2 class="product-name"> <a href="product_detail.html">Sample Product</a> </h2></td>
							<td class="a-center">						  
								<input type="radio" class="radio" name="battery_buy_type" value="without-old" style="margin-right: 8px;">Price With Out Old Battery Take Back : &nbsp;<i class="icon-inr" aria-hidden="true"></i>
								</br>
								<input type="radio" class="radio" checked="checked" name="battery_buy_type" value="with-old"  style="margin-right: 8px;">Price With Old Battery Take Back<span style="font-size: 11px;"> (Same Ah)</span> 
									 : &nbsp;<i class="icon-inr" aria-hidden="true"></i><span itemprop="price"> </span>
							</td>
							<td class="a-right"><span class="cart-price"> <span class="price"><i class="icon-inr" aria-hidden="true"></i> 70.00</span> </span></td>
							<td class="a-center movewishlist"><input maxlength="12" class="input-text qty" title="Qty" size="4" value="1" name="cart[15945][qty]"></td>
							<td class="a-right movewishlist"><span class="cart-price"> <span class="price"><i class="icon-inr" aria-hidden="true"></i> 70.00</span> </span></td>
							<td class="a-center last"><a class="button remove-item" title="Remove item" href="#"><span><span>Remove item</span></span></a></td>
						  </tr>
						</tbody>
					  </table>
					</fieldset>
				  </form>
				</div>
				<!-- BEGIN CART COLLATERALS -->
				<div class="cart-collaterals row">
				  <div class="col-sm-4">
					<div class="discount">
						<h3>Discount Codes</h3>
						<form method="post" action="#couponPost/"  >
						  <label for="coupon_code">Enter your coupon code if you have one.</label>
						  <input type="hidden" value="0" id="remove-coupone" name="remove">
						  <input type="text" value="" name="coupon_code" id="coupon_code" class="input-text fullwidth">
						  <button value="Apply Coupon" onClick="discountForm.submit(false)" class="button coupon " title="Apply Coupon" type="button"><span>Apply Coupon</span></button>
						</form>
					</div>
				  </div>
				  <div class="totals col-sm-4 col-sm-offset-4 ">
					<h3>Shopping Cart Total</h3>
					<div class="inner">
					  <table class="table shopping-cart-table-total" id="shopping-cart-totals-table">
						<tfoot>
						  <tr>
							<td colspan="1" class="a-left" style=""><strong>Grand Total</strong></td>
							<td class="a-right" style=""><strong><span class="price">$77.38</span></strong></td>
						  </tr>
						</tfoot>
						<tbody>
						  <tr>
							<td colspan="1" class="a-left" style=""> Subtotal </td>
							<td class="a-right" style=""><span class="price">$77.38</span></td>
						  </tr>
						</tbody>
					  </table>
					  <ul class="checkout">
						<li>
						  <button onClick="" class="button btn-proceed-checkout" title="Proceed to Checkout" type="button"><span>Proceed to Checkout</span></button>
						</li>
					  </ul>
					</div>
					<!--inner--> 
				  </div>
				</div>
			</div>
			</div>
		  </div>
		</section>
      </div>
    </div>
  </div>
<!---################################## CSS Include Starts  ################################------>
	<jsp:include page = "/assets/includes/includes_js.jsp" />
<!---################################## CSS Include Ends  ################################------>
<input type="hidden" name="brandkeyword" id='brandkeyword' value="Common">
<input type="hidden" name="MobileNumberPopUpCheck" id='MobileNumberPopUpCheck' value="HomePage">
<input type="hidden" name="user_mobile_number_cookie_tmp" id='user_mobile_number_cookie_tmp' value="">
<!---################################## JS Include Starts  ################################------>
	<jsp:include page = "/assets/includes/includes_footer.jsp" />
<!---################################## JS Include Ends  ################################------>
<script type="text/javascript" src="<%=publicUrl%>/bookbattery/dev/js/checkout.js"></script>
</body>
</html>
