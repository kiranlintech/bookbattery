<%-- 
Document   		 : 404.jsp
Created on 		 : Sep 15, 2016, 10:14:12 AM
Author     		 : BookBattery
Copyright Notice : BookBattery Confidential.
Document         : This jsp is used as 404 error pages of BookBattery Batteries.
--%>
<%@page language="java" import="java.io.File,java.util.Hashtable,java.util.Vector,com.ngit.javabean.loglevel.*,java.util.Enumeration,java.util.Properties,java.io.FileInputStream,javax.servlet.ServletContext"%>
<%
Properties propsMOPConfig = new Properties();
ServletContext context = getServletContext();
FileInputStream fin1      = new FileInputStream(new File(context.getRealPath("properties/bookbatteryconfig.properties"))); 
propsMOPConfig.load(fin1); 
fin1.close(); 
String domainname = (propsMOPConfig.getProperty("domainname")!=null)?propsMOPConfig.getProperty("domainname"):"";
String publicUrl = (propsMOPConfig.getProperty("publicUrl")!=null)?propsMOPConfig.getProperty("publicUrl"):"";
%>



<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<!--[if IE]>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<![endif]-->
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content=" Oops! No Product Details Found ">
<meta name="author" content="Sai Krishna Daddala">
<meta name="robots" content="noindex">
<meta name="googlebot" content="noindex">
<title>Oops! No Product Details Found .</title>



<!-- Mobile Specific -->
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
<!-- Google Fonts -->
<link href='https://fonts.googleapis.com/css?family=Open+Sans:300italic,400italic,600italic,700italic,800italic,300,700,800,400,600' rel='stylesheet' type='text/css'>
</head>
<!---################################## CSS Include Starts  ################################------>
	<jsp:include page = "/assets/includes/includes_css.jsp" />
<!---################################## CSS Include Ends  ################################------>
</head>
<body>
<!---################################## Header Include Starts  ################################------>
	<jsp:include page = "/assets/includes/includes_header.jsp" />
<!---################################## Header Include Ends  ################################------>
  <!--End main-container -->
  <section class="content-wrapper">
    <div class="container">
		 <div class="row">
			<section class="col-md-9 col-xs-12 " style=" z-index: 2;" >
				<div class="col-main">
				<div class="error_404">
				  <div class="std">
					<div class="page-not-found wow bounceInRight animated">
					  <h2> <span class="icon-shopping-cart"></span></h2>
					   <h3>Product Details are not available for this option.</h3>
					  <div><button onclick="javascript:goBack()" type='button' class="btn-home"><span Class="icon-chevron-left"></span> <span>Go Back</span></button>
					  <button  type='button' onclick="javascript:location.href='<%=publicUrl%>/bookbattery/'" class="btn-home"><span class="icon-home"></span> <span>Back To Home</span></button></div>
					</div>
				  </div>
				</div>
				</div>
			</section>
			<aside class="col-left sidebar col-xs-12  wow bounceInUp animated col-md-3">
				<div class="side-nav-categories">
					<div class="block-title"> Search </div>
					<div class="box-content box-category">
					 <form name="tyreindex">
						 <div id="displysesmsg" style='font-size: 17px;'></div>
						<div class="search_bar_BookBattery" style=" text-align: left;">
						<!---################################## Dropdown Include Starts  ################################------>
							<jsp:include page = "/dev/includes/jsp/includes_dropdowns.jsp" />
						<!---################################## Dropdown Include Ends  ################################------>
						</div>
					 </form>
					</div>
				</div>
			</aside>
		</div>
	</div>
  </section>
  
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

</body>
</html>