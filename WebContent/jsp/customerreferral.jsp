<%-- 
Document   		 : online_transaction.jsp
Created on 		 : Feb 15, 2017, 10:14:12 AM
Author     		 : BookBattery
Copyright Notice : Asistmi Solutions Pvt.Ltd. Confidential.
Document         : This jsp is used as show details or orders.
--%>
<%@ page language="java" import="java.sql.*"%>
<%@page language="java" import="java.io.File,java.text.*,java.util.Calendar,java.util.ArrayList,java.util.Hashtable,java.util.Vector,com.ngit.javabean.loglevel.*,java.util.Date,java.util.Properties,java.util.*,com.ngit.javabean.loglevel.LogLevel,javax.servlet.ServletContext,java.util.Properties,java.io.FileInputStream, com.ngit.javabean.qrymgr.QueryManager"%>
<%response.setHeader("Pragma","no-cache"); response.setHeader("Cache-Control","no-store"); response.setHeader("Expires","0"); response.setDateHeader("Expires",-1); 
ServletContext context = getServletContext();
Properties propsMOPConfig = new Properties();
FileInputStream fin1      = new FileInputStream(new File(context.getRealPath("properties/bookbatteryconfig.properties"))); 
propsMOPConfig.load(fin1); 
fin1.close(); 

QueryManager qm;
qm = QueryManager.getInstance(propsMOPConfig) ;
	
String publicUrl = (propsMOPConfig.getProperty("publicUrl")!=null)?propsMOPConfig.getProperty("publicUrl"):"";
String baseURL = (propsMOPConfig.getProperty("baseURL")!=null)?propsMOPConfig.getProperty("baseURL"):"";
String baseurldirectory = (propsMOPConfig.getProperty("baseurldirectory")!=null)?propsMOPConfig.getProperty("baseurldirectory"):"";
String serverURL = (propsMOPConfig.getProperty("serverURL")!=null)?propsMOPConfig.getProperty("serverURL"):"";

%>

<!-- ###############################------------------------------------------------#############################-->
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html lang="en">
<head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta name="description" content=" Oops! No Product Details Found ">
	<meta name="author" content="Sai Krishna Daddala">
	<meta name="robots" content="noindex">
	<meta name="googlebot" content="noindex">
	<title>Payment Tranasaction </title>



<!-- Mobile Specific -->
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
<!-- Google Fonts -->
<link href='https://fonts.googleapis.com/css?family=Open+Sans:300italic,400italic,600italic,700italic,800italic,300,700,800,400,600' rel='stylesheet' type='text/css'>

<!---################################## CSS Include Starts  ################################------>
	<jsp:include page = "/assets/includes/includes_css.jsp" />
<!---################################## CSS Include Ends  ################################------>
<script type="text/javascript">


</script>

</script>
<style type="text/css">
 
#share-buttons img {
width: 35px;
padding: 5px;
border: 0;
box-shadow: 0;
display: inline;
}
 #st-1.st-has-labels .st-btn {
    min-width: 50px!important;
} 
.st-label {
    display: none !important;
}
.Product_Description .block-tags li:before {
    content: "\f054";
    font-family: FontAwesome;
    color: #f57a20;
    font-size: 13px;
    padding: 0px 5px 0 0;
    line-height: 30px;
}
.Product_Description .block-tags li {
    display: table;
    font-size: 14px;
    font-weight: 500;
    color: #4a4a4a;
    display: flex;
    line-height: 30px;
}
.checkout-page {
    padding: 10px;
    background: #fff;
    z-index: 1;
    overflow: hidden;
}
</style>
</head>
<body>
<!---################################## Header Include Starts  ################################------>
	<jsp:include page = "/assets/includes/includes_header.jsp" />
<!---################################## Header Include Ends  ################################------>
  <!--End main-container -->
  <div class="modal fade" id="myModal" role="dialog">
    <div class="modal-dialog modal-sm">
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal">&times;</button>
          <h4 class="modal-title">Modal Header</h4>
        </div>
        <div class="modal-body">
          <p>It is not supported in UCBrowser. Open the copied URL in chrome.</p>
        </div>
        <div class="modal-footer">
          <button type="button" center class="btn btn-default" data-dismiss="modal" href="chrome">OK</button>
        </div>
      </div>
    </div>
  </div>
  <!-- main-container -->
  <div class="main-container col2-right-layout" style="padding: 0px;">
    <div class="main container" style="margin-top: 0px;">
      <div class="row">
        <section class="col-main col-sm-9">
        <div class="checkout-page">
          <div class="page-title new_page_title">
            <h1>Our Referral Offer</h1>
          </div>
		  			<div class="col-sm-12 col-xs-12 col-md-12">
					<h3 style="color: #f15942;margin-top: 5px;margin-bottom: 5px;">Free Battery Health Check Just With a Click</h3>
				</div>
			<div class="form-group"> 

				<div id="after_order_popup_data"> 

				<div class="col-md-12 col-sm-12 col-xs-12">		
					<p style="font-size: 14px;font-weight: 500;padding-top: 20px;font-size: 15px;color: #555858;font-weight: 600;">Get Free Car/Inverter <b style="font-size:18px">"Battery Health Check"</b> of worth Rs. <b style="font-size:18px">299/599</b> by sharing your reference code to your friends or family members.</p>
				</div>					
				<div class="col-md-12 col-sm-12 col-xs-12" style="text-align:center;margin: 8px;">		
					<h4 style="font-size: 21px;font-weight: 600;color: #e02d29;">Refer Using</h4>						
				</div>	
				<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12" style="margin-bottom: 25px;">	
					<div class="col-lg-4 col-md-12 col-sm-12 col-xs-12"><a style="font-size: 14px;font-weight: 600;" href="whatsapp://send?text=Use%20your%20friend%27s%20reference%20code%20<%=request.getParameter("refcode")%>%20when%20you%20order%20a%20Car%2FBike%2FInverter%20Battery%20or%20Home%20UPS%20in%20BookBattery.com%20and%20get%20a%20good%20discount." data-action="share/whatsapp/share"><img src="<%=publicUrl%><%=baseurldirectory%>dev/includes/images/share_whats_app.png" style="width: 50px;margin:10px;" alt="BookBattery - Whatsapp">WhatsApp</a></div>
					<!--<div class="col-lg-4 col-md-12 col-sm-12 col-xs-12"><a style="font-size: 14px;font-weight: 600;" href="#" onclick="openURL()"><img src="<%=publicUrl%><%=baseurldirectory%>assets/includes/images/share_whats_app.png" style="width: 50px;margin:10px;" alt="BookBattery - Whatsapp">WhatsApp</a></div>-->
					<div class="col-lg-4 col-md-12 col-sm-12 col-xs-12"><a style="font-size: 14px;font-weight: 600;" href="sms:?&amp;body=Use%20your%20friend%27s%20reference%20code%20<%=request.getParameter("refcode")%>%20when%20you%20order%20a%20Car%2FBike%2FInverter%20Battery%20or%20Home%20UPS%20in%20BookBattery.com%20and%20get%20a%20good%20discount."><img style="width: 50px;margin:10px;" src="<%=publicUrl%><%=baseurldirectory%>dev/includes/images/share_sms.jpg" alt="BookBattery - Whatsapp">Message</a></div>
					<!--<div class="button share-button whatsapp-share-button">share</div> -->
					
					<div class="col-lg-4 col-md-12 col-sm-12 col-xs-12"><a style="font-size: 14px;font-weight: 600;" href="mailto:?subject=Order%20Referral%20#<%=request.getParameter("refcode")%>&amp;body=Use%20your%20friend%27s%20reference%20code%20<%=request.getParameter("refcode")%>%20when%20you%20order%20a%20battery%20or%20inverter%20in%20BookBattery.com%20before%20it%20gets%20expired."><img src="<%=publicUrl%><%=baseurldirectory%>dev/includes/images/email_share.png" style="width: 50px;margin:10px;" alt="BookBattery - Whatsapp">Email</a></div>
				</div>
				<div style="padding-top: 20px;" class="Product_Description">  
					<div class="col-sm-12 col-xs-12 col-md-12">  
						<div>  
							<div>  
								<h4 style=" color: #0e9842;"><b>What do you get for each referral ?</b></h4>
							</div>
							<div class=" block-tags">
								<div>
									<li>If your referral gets an Inverter Battery or Combo installed using your reference code, Rs.100 will be credited to your referral wallet.</li>
									<li>If your referral gets a Car Battery installed using your reference code, Rs.50 will be credited to your referral wallet.</li>
									<li>If your referral gets a Bike Battery installed using your reference code, Rs.25 will be credited to your referral wallet.</li>
									<li>If your referral gets an Inverter installed using your reference code, Rs.50 will be credited to your referral wallet.</li>
									<li>If your referral gets a three wheeler battery or truck battery, crane battery, etc., installed using your reference code, Rs.50 will be credited to your referral wallet.</li>
								</div>  
							</div>
						</div>
						<div style="padding-top: 30px;">  
							<div>  
								<h4 style=" color: #0e9842;"><b>How to avail free Battery Health Check ?</b></h4>
							</div>
							<div class=" block-tags">
								<div>
									<li>You will be eligible to avail Free Car Battery Health Check when you get 300 Rs in your referral wallet.</li>
									<li>You will be eligible to avail Free Inverter Battery Health Check when you get 600 Rs in your referral wallet.</li>
									<li>If you are eligible to avail Car/Inverter Battery Health Check, You can avail by placing battery health check order for car/inverter battery using your reference code or by calling to our support number 9603467559.</li>
								</div>  
							</div>
						</div>
					</div>
				</div>
				</div>
				<div class="col-sm-12 col-xs-12 col-md-12" style="text-align: center;">					
					<i class="icon-smile" style="font-size: 50px;color: #1d8e11;"></i>					
					<p style="font-size: 14px;font-weight: 600;">Thanks for your Support</p>				
				</div>	
				<div style="text-align: center;" class="col-md-12 col-sm-6 col-xs-12"> 	
				<button type="button" class="button button-continue"  onclick="window.location.href='<%=publicUrl%><%=baseurldirectory%>'">Done</button> 
				</div> 	
			</div>
        </div>
		</section>
        <aside class="col-right sidebar col-sm-3 wow bounceInUp animated">
          <div class="block block-company">
            <div class="block-title">Quick Links </div>
            <div class="block-content">
              <ol id="recently-viewed-items">
                <li class="item odd"><strong>About Us</strong></li>
                <li class="item even"><a href="<%=publicUrl%><%=baseurldirectory%>sitemap.jsp">Sitemap</a></li>
                <li class="item  odd"><a href="<%=publicUrl%><%=baseurldirectory%>payments.jsp">Payments</a></li>
                <li class="item even"><a href="<%=publicUrl%><%=baseurldirectory%>faq.jsp">FAQ's</a></li>
                <li class="item last"><a href="<%=publicUrl%><%=baseurldirectory%>contactus.jsp">Contact Us</a></li>
              </ol>
            </div>
          </div>
        </aside>
      </div>
    </div>
  </div>
  <!--End main-container --> 
  
  <!--End main-container --> 
 <input type="hidden" name="product_page_name" id='product_page_name' value="404">
<!---################################## CSS Include Starts  ################################------>
	<jsp:include page = "/assets/includes/includes_js.jsp" />
<!---################################## CSS Include Ends  ################################------>

<!---################################## CSS Include Starts  ################################------>
	<jsp:include page = "/assets/includes/includes_footer.jsp" />
<!---################################## CSS Include Ends  ################################------>

<script>
	function goBack() {
		window.history.back();
	}	
</script>
<script>
$(document).ready(function() {
	//alert(navigator.userAgent);
//<![CDATA[
var findmove1 = navigator.userAgent;
//alert(findmove1);
var redirect = findmove1.search("UCBrowser");
if(redirect>1) {
	//alert(22);
//document.location = 'googlechrome://navigate?url=www.tovia.com/';
 $('#myModal').modal('show');
}
//]]>
});
</script>
</body>
</html>