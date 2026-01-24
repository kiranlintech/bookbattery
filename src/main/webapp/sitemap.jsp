<%-- 
Document   : index.jsp
Created on : Sep 15, 2015, 10:14:12 AM
Author     : Jhansi A
--%>
<%@ page language="java" import="java.sql.*"%>
<%@page language="java" import="java.io.File,java.text.*,java.util.Calendar,java.util.ArrayList,java.util.Hashtable,java.util.Vector,com.ngit.javabean.loglevel.*,java.util.Date,java.util.Properties,java.util.*,com.ngit.javabean.loglevel.LogLevel,javax.servlet.ServletContext,java.util.Properties,java.io.FileInputStream"%>
<%response.setHeader("Pragma","no-cache"); response.setHeader("Cache-Control","no-store"); response.setHeader("Expires","0"); response.setDateHeader("Expires",-1); %>
<%
	String nametype =(session.getAttribute("nametype") != null)?(String)session.getAttribute("nametype"):""; 
	Properties propsMOPConfig = new Properties();
	ServletContext context = getServletContext();
	FileInputStream fin1      = new FileInputStream(new File(context.getRealPath("properties/bookbatteryconfig.properties"))); 
	propsMOPConfig.load(fin1); 
	fin1.close(); 
	String publicUrl = (propsMOPConfig.getProperty("publicUrl")!=null)?propsMOPConfig.getProperty("publicUrl"):"";
	String baseURL = (propsMOPConfig.getProperty("baseURL")!=null)?propsMOPConfig.getProperty("baseURL"):"";
	String Bookbatteryservice = (propsMOPConfig.getProperty("Bookbatteryservice")!=null)?propsMOPConfig.getProperty("Bookbatteryservice"):"";
	String keyword = (request.getParameter("keyword")!=null)?(request.getParameter("keyword")):"0"; 
	LogLevel.DEBUG(5,new Throwable(),"keyword :"+keyword );
	String baseurldirectory = (propsMOPConfig.getProperty("baseurldirectory")!=null)?propsMOPConfig.getProperty("baseurldirectory"):"";
	String serverURL = (propsMOPConfig.getProperty("serverURL")!=null)?propsMOPConfig.getProperty("serverURL"):"";


%>
<%
String ua=request.getHeader("User-Agent").toLowerCase();
if(ua.matches("(?i).*((android|bb\\d+|meego).+mobile|avantgo|bada\\/|blackberry|blazer|compal|elaine|fennec|x86_64|hiptop|iemobile|ip(hone|od)|iris|kindle|lge |maemo|midp|mmp|mobile.+firefox|netfront|opera m(ob|in)i|palm( os)?|phone|p(ixi|re)\\/|plucker|pocket|psp|series(4|6)0|symbian|treo|up\\.(browser|link)|vodafone|wap|windows (ce|phone)|xda|xiino).*")||ua.substring(0,4).matches("(?i)1207|6310|6590|3gso|4thp|50[1-6]i|770s|802s|a wa|abac|ac(er|oo|s\\-)|ai(ko|rn)|al(av|ca|co)|amoi|an(ex|ny|yw)|aptu|ar(ch|go)|as(te|us)|attw|au(di|\\-m|r |s )|avan|be(ck|ll|nq)|bi(lb|rd)|bl(ac|az)|br(e|v)w|bumb|bw\\-(n|u)|c55\\/|capi|ccwa|cdm\\-|cell|chtm|cldc|cmd\\-|co(mp|nd)|craw|da(it|ll|ng)|dbte|dc\\-s|devi|dica|dmob|do(c|p)o|ds(12|\\-d)|el(49|ai)|em(l2|ul)|er(ic|k0)|esl8|ez([4-7]0|os|wa|ze)|fetc|fly(\\-|_)|g1 u|g560|gene|gf\\-5|g\\-mo|go(\\.w|od)|gr(ad|un)|haie|hcit|hd\\-(m|p|t)|hei\\-|hi(pt|ta)|hp( i|ip)|hs\\-c|ht(c(\\-| |_|a|g|p|s|t)|tp)|hu(aw|tc)|i\\-(20|go|ma)|i230|iac( |\\-|\\/)|ibro|idea|ig01|ikom|im1k|inno|ipaq|iris|ja(t|v)a|jbro|jemu|jigs|kddi|keji|kgt( |\\/)|klon|kpt |kwc\\-|kyo(c|k)|le(no|xi)|lg( g|\\/(k|l|u)|50|54|\\-[a-w])|libw|lynx|m1\\-w|m3ga|m50\\/|ma(te|ui|xo)|mc(01|21|ca)|m\\-cr|me(rc|ri)|mi(o8|oa|ts)|mmef|mo(01|02|bi|de|do|t(\\-| |o|v)|zz)|mt(50|p1|v )|mwbp|mywa|n10[0-2]|n20[2-3]|n30(0|2)|n50(0|2|5)|n7(0(0|1)|10)|ne((c|m)\\-|on|tf|wf|wg|wt)|nok(6|i)|nzph|o2im|op(ti|wv)|oran|owg1|p800|pan(a|d|t)|pdxg|pg(13|\\-([1-8]|c))|phil|pire|pl(ay|uc)|pn\\-2|po(ck|rt|se)|prox|psio|pt\\-g|qa\\-a|qc(07|12|21|32|60|\\-[2-7]|i\\-)|qtek|r380|r600|raks|rim9|ro(ve|zo)|s55\\/|sa(ge|ma|mm|ms|ny|va)|sc(01|h\\-|oo|p\\-)|sdk\\/|se(c(\\-|0|1)|47|mc|nd|ri)|sgh\\-|shar|sie(\\-|m)|sk\\-0|sl(45|id)|sm(al|ar|b3|it|t5)|so(ft|ny)|sp(01|h\\-|v\\-|v )|sy(01|mb)|t2(18|50)|t6(00|10|18)|ta(gt|lk)|tcl\\-|tdg\\-|tel(i|m)|tim\\-|t\\-mo|to(pl|sh)|ts(70|m\\-|m3|m5)|tx\\-9|up(\\.b|g1|si)|utst|v400|v750|veri|vi(rg|te)|vk(40|5[0-3]|\\-v)|vm40|voda|vulc|vx(52|53|60|61|70|80|81|83|85|98)|w3c(\\-| )|webc|whit|wi(g |nc|nw)|wmlb|wonu|x700|yas\\-|your|zeto|zte\\-")) {
  response.sendRedirect(""+baseURL+""+baseurldirectory+"mobile/index.jsp");
  return;
}
%>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=1200px, initial-scale=1">
    <title>Car Battery | Buy Car Battery Online in India at BookBattery.com</title>
	<meta name="description" content="Looking for a Car Battery in India.Buy Amaron, Exide  Car Battery  with company warranty and free installation">
	<meta name="keywords" content="car battery, car battery online, 4  wheeler battery, Exide, amaron, amaron car battery, exide car battery, BookBattery">
	
	
	<!-- tags added to display content in social networking pages-->
			<!-- Schema.org markup for Google+ -->
			<meta itemprop="name" content="BookBattery - Online Multi Branded Automotive Battery Store">
			<meta itemprop="description" content="BookBattery.com - Order and Get Genuine Branded Car Battery or Inverter Battery or Bike Battery.  BookBattery is India's Only Authorised Branded  Battery Online Store. Best Discount Prices with Free Installation and Original Warranty is Offered."/>
			<meta itemprop="image" content="https://www.BookBattery.com/bookbattery/images/BookBattery-social.png" />

			<!-- Twitter Card data -->
			<meta name="twitter:site" content="@BookBattery">
			<meta name="twitter:title" content="BookBattery - Online Multi Branded Automotive Battery Store">
			<meta name="twitter:description" content="BookBattery.com - Order and Get Genuine Branded Car Battery or Inverter Battery or Bike Battery.  BookBattery is India's Only Authorised Branded  Battery Online Store. Best Discount Prices with Free Installation and Original Warranty is Offered." />
			<meta name="twitter:image"  content="https://www.BookBattery.com/bookbattery/images/BookBattery-social.png" />

			<!-- Open Graph data -->
			<meta property="og:title" content="BookBattery - Online Multi Branded Automotive Battery Store" />
			<meta property="og:type" content="E-Commerce" />
			<meta property="og:url" content="https://www.BookBattery.com/" />
			<meta property="og:image" content="https://www.BookBattery.com/bookbattery/images/BookBattery-social.png" />
			<meta property="og:description" content="BookBattery.com - Order and Get Genuine Branded Car Battery or Inverter Battery or Bike Battery.  BookBattery is India's Only Authorised Branded  Battery Online Store. Best Discount Prices with Free Installation and Original Warranty is Offered
				BookBattery sells the Car Battery, Inverter Battery, Bike Battery, Bus Battery, Truck Battery, Tractor Battery, Genset Battery and Auto Rick Battery in all areas in Bangalore"/>
			<meta property="og:site_name" content="BookBattery - Online Multi Branded Automotive Battery Store" />	
			
			
			
<!-- On load images starts  -->

<style>
.no-js #loader { display: none;  }
.js #loader { display: block; position: absolute; left: 100px; top: 0; }
.se-pre-con {
	position: fixed;
	left: 0px;
	top: 0px;
	width: 100%;
	height: 100%;
	z-index: 9999;
	background: url(/bookbattery/images/loader/BookBatteryload.gif) center no-repeat #fff;
content: "Joe's Task:"
}
</style>

<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.5.2/jquery.min.js"></script>
<script>
	//paste this code under head tag or in a seperate js file.
	// Wait for window load
	$(window).load(function() {
		// Animate loader off screen
		$(".se-pre-con").fadeOut("slow");;
	});
</script>

<!-- On load images ends  -->

	<meta property="og:image" content="https://www.BookBattery.com/images/bookbatterylogo.png">
	<meta property="og:url" content="http://www.BookBattery.com">
	<meta property="og:type" content="E-Commerce"/>
	<!-- tags ended here to display content in social networking pages-->

	<!-- popup css -->
	<link rel="stylesheet" href="/bookbattery/css/popup.css">

	<link href="/bookbattery/css/bookbattery.css?<%=context.getInitParameter("cornercss")%>" rel="stylesheet" type="text/css" />
	<link rel="shortcut icon" href="/bookbattery/images/favicon.png" type="image/x-icon"/>
	<script src="/bookbattery/js/jquery-1.3.2.min.js" type="text/javascript"></script>
	<script language="JavaScript" src="/bookbattery/js/popupfrontend.js" type="text/javascript"></script>
	<script language="JavaScript" src="/bookbattery/js/jquery-1.8.2.js" type="text/javascript"></script>
    <!-- Google Fonts -->
    <link href='http://fonts.googleapis.com/css?family=Titillium+Web:400,200,300,700,600' rel='stylesheet' type='text/css'>
    <link href='http://fonts.googleapis.com/css?family=Roboto+Condensed:400,700,300' rel='stylesheet' type='text/css'>
    <link href='http://fonts.googleapis.com/css?family=Raleway:400,100' rel='stylesheet' type='text/css'>
	
	
    <link rel="stylesheet" href="/bookbattery/css/reset.css">
	<link rel="stylesheet" href="/bookbattery/css/set2.css">
	<link rel='stylesheet prefetch' href='http://fonts.googleapis.com/css?family=Playball'>
   
    
    <!-- Css for corner pop up  -->
	<link rel="stylesheet" href="/bookbattery/css/coener.css?<%=context.getInitParameter("cornercss")%>">
    <!-- Bootstrap -->
    <link rel="stylesheet" href="/bookbattery/css/bootstrap.min.css">
    <!-- Font Awesome -->
    <link rel="stylesheet" href="/bookbattery/css/font-awesome.min.css">
	<!-- Custom CSS -->
    <link rel="stylesheet" href="/bookbattery/css/owl.carousel.css">
    <link rel="stylesheet" href="/bookbattery/css/style.css?v=2">
	<link rel="stylesheet" href="/bookbattery/css/style1.css">
	<link rel="stylesheet" href="/bookbattery/css/responsive.css">
	<!--js and css code starts here  for loading popup added by jhansi-->
	<link rel="stylesheet" href="/bookbattery/css/colorbox.css" />
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
	<script src="/bookbattery/js/jquery.colorbox.js"></script>
	<!--js and css code ends here  for loading popup added by jhansi-->

<style type='text/css'>



td.loading_image{background:  url('/bookbattery/images/loader.gif');width:100px; /* use you own image size; */ 
height: 40px; /* use you own image size; */ 
background-repeat: no-repeat; 
background-position: center; 
vertical-align: top;}

.button_example{
border:0px; -webkit-border-radius: 3px; -moz-border-radius: 3px;border-radius: 3px;font-size:12px;font-family:arial, helvetica, sans-serif; padding: 10px 10px 10px 10px; text-decoration:none; display:inline-block;text-shadow: -1px -1px 0 rgba(0,0,0,0.3);font-weight:bold; color: #FFFFFF;
 background-color: #FF8c00; background-image: -webkit-gradient(linear, left top, left bottom, from(#FF8c00), to(#FF8c00));
 background-image: -webkit-linear-gradient(top, #FF8c00, #FF8c00);
 background-image: -moz-linear-gradient(top, #FF8c00, #FF8c00);
 background-image: -ms-linear-gradient(top, #FF8c00, #FF8c00);
 background-image: -o-linear-gradient(top, #FF8c00, #FF8c00);
 background-image: linear-gradient(to bottom, #FF8c00, #FF8c00);filter:progid:DXImageTransform.Microsoft.gradient(GradientType=0,startColorstr=#FF8c00, endColorstr=#FF8c00);
}
.divmobile{width:100%;left:0%;top:30%;font: 16px/22px; text-align:left;padding: 10px;display: none; z-index:9999; background-color: #232323; border-width: 0px 0px 0px 0px;}

.divmobilelaptop{width:100%;left:0%;top:30%;font: 16px/22px; text-align:left;padding: 10px;display: none;  position: fixed; z-index:9999; background-color: #232323; border-width: 0px 0px 0px 0px;}


<!---.divmobilelaptop{left:37.5%;top:30%;font: 16px/22px 'Trebuchet MS', Verdana, Arial; text-align:left; border:6px solid #424242; border-radius: 6px 6px 6px 6px; position: fixed; padding: 1px;display: none;z-index:60;background-color: white;} --->

.knowmore{font-family:Verdana;font-size:11px;color: #FF8C00;text-decoration:none;}
.knowmore:hover{font-family:Verdana;font-size:11px;color:#FF8C00;text-decoration:underline;}

#BookBatterydiv
{
    background-color:#FFFFFF;
    position:fixed;
    width:260px;
    height:260px;
   
    bottom: 0px ;
	right: 0px ;
    left:1400px;
	display:none;
	border: 2px solid #FF8c00;
	z-index:2;
}

</style>

  </head>
  <body>
  <!-- On load images  -->

<div class="se-pre-con"></div>
 <!-- pop up -->
<!-- <div id="boxes">
	<div id="dialog" class="window" style="top: 147.5px; left: 309.5px; display: block;background-color:#53AE7E;height: 403px;">
      <button class="close toggleModal" style="color: white;">Close</button>
	<img src="/bookbattery/images/popup.png" alt="Car Batteries">
	<a class="caption button-radius" href="javascript:checkbatteryservice();"><span class="icon"></span>Book Now</a>
	</div>
	<div id="mask" ></div>
	</div>  
	
  <!-- Start Alexa Certify Javascript -->
<script type="text/javascript">
_atrk_opts = { atrk_acct:"VrG0j1a4ZP00yt", domain:"BookBattery.com",dynamic: true};
(function() { var as = document.createElement('script'); as.type = 'text/javascript'; as.async = true; as.src = "https://d31qbv1cthcecs.cloudfront.net/atrk.js"; var s = document.getElementsByTagName('script')[0];s.parentNode.insertBefore(as, s); })();
</script>
<noscript><img src="https://d5nxst8fruw4z.cloudfront.net/atrk.gif?account=VrG0j1a4ZP00yt" style="display:none" height="1" width="1" alt="Automotive Batteries Online Store" /></noscript>
<!-- End Alexa Certify Javascript -->
<form name="batteryindex" action="">
<div id='divmobile' class='divmobile' style="display:none;"></div>
<div id='divmobilelaptop' class='divmobilelaptop' style="display:none;"></div>


   
  </br>

  <!-- end header -->

  <!-- end nav -->
    <!-- breadcrumbs -->
  <!--<div class="breadcrumbs">
    <div class="container">
      <div class="row">
        <ul>
          <li class="home"> <a href="/insurance/index.jsp" title="Go to Home Page">Home</a>  <span>&raquo;</span></li>
          <li class="category13"><strong>Site Map</strong></li>
        </ul>
      </div>
    </div>
  </div>-->
  <!-- End breadcrumbs --> 

   <section class="main-container col1-layout">
    <div class="main container">
      <div class="col-main">
        <div class="cart wow">
          <div class="page-title new_page_title">
            <h2>SITEMAP</h2>
          </div>
          <div class="row content-row">
            <div class="col-xs-6 col-sm-3 col-md-3 col-lg-4">
              <ul class="simple-list arrow-list bold-list">
                <li> <a href="/bookbattery/index.jsp">HOME</a>
                </li> 
				<!--<li> <a href="/insurance/insurance_tips.jsp">Tips</a>
                </li>-->
			     <br>
				<li> <a>CATEGORIES</a>
				<ul>
                    <li><a title="Bike Batteries" href="/bookbattery/bikebattery.jsp">Bike Batteries</a></li>
					<li><a title="Car Batteries"  href="/bookbattery/carbattery.jsp">Car Batteries</a></li>
					<li><a title="Inverter's"  href="/bookbattery/inverter.jsp">Inverter's</a></li>
					<li><a title="Inverter Batteries"  href="/bookbattery/inverterbattery.jsp">Inverter Batteries</a></li>
					<li><a title="Bus Batteries"  href="/bookbattery/busbattery.jsp">Bus Batteries</a></li>
					<li><a title="Auto Rick Batteries"  href="/bookbattery/autorickbattery.jsp">Auto Rick Batteries</a></li>
					<li><a title="Truck Batteries"  href="/bookbattery/truckbattery.jsp">Truck Batteries</a></li>
					<li><a title="Tractor Batteries"  href="/bookbattery/tractorbattery.jsp">Tractor Batteries</a></li>
					<li><a title="Genset Batteries"  href="/bookbattery/gensetbattery.jsp">Genset Batteries</a></li>
                  </ul>
				  </li>
			
				
				
                <br>
                <li> <a>SHOPPING GUIDE</a>
                  <ul>
					<li><a href="/bookbattery/aboutus.jsp" title="How to buy">About us</a></li>
					<li><a href="/bookbattery/faq.jsp" title="FAQs">FAQs</a></li> 
					<li><a  href="/bookbattery/payments.jsp" title="Payment">Payment</a></li>
                  </ul>
                </li>
               <!-- <li><a href="/insurance/tyredetails.jsp">Results</a></li> -->
              </ul>
            </div>
            <div class="col-xs-6 col-sm-3 col-md-3 col-lg-4">
              <ul class="simple-list arrow-list bold-list">
               
			
		<br>
                <li> <a>INFORMATION</a>
                  <ul>
                   <li><a href="/bookbattery/sitemap.jsp" title="How to buy">Site Map</a></li>
                    <li><a href="/bookbattery/contactus.jsp" title="How to buy">Contact Us</a></li>
                  </ul>
                </li>
              </ul>
            </div>
            <div class="col-xs-12 col-sm-4 col-md-4 col-lg-4"> <img class="img-responsive animate scale" src="/bookbattery/images/large-icon-sitemap.jpg" alt=""> </div>
          </div>
        </div>
      </div>
    </div>
  </section>

 

<!---################################## Footer Include Starts  ################################------>
<jsp:include page = "footer.jsp" />
<!---################################## Footer Include Ends  ################################------>

</body>
</html>
