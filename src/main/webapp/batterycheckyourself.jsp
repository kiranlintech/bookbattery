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
	<meta name='Title' content='BookBattery.com - Videos to Check Your Car,Bike,Inverter Battery' />
	<title> BookBattery.com - Videos to Check Your Car,Bike,Inverter Battery</title> 
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<link rel="shortcut icon" href="./images/favicon.png" type="image/x-icon">
	<meta name='description' content="Videos to Check Your Car Battery, Bike Battery, Inverter Batteries. Learn How to Check and Change Your Car Battery by Yourself, at BookBattery - India's No.1 Online Car/Inverter Branded Battery Store." />
	<meta name='keywords' content='Videos to Check Battery, Videos to check Car Battery, How to Check Car Battery Videos, Check Car Battery, Car Battery Change Videos.' />
	
	
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
	
	
	<link href="./css/bookbattery.css?<%=context.getInitParameter("cornercss")%>" rel="stylesheet" type="text/css" />
	<link rel="shortcut icon" href="./images/favicon.png" type="image/x-icon"/>
	<script src="./js/jquery-1.3.2.min.js" type="text/javascript"></script>
	<script language="JavaScript" src="./js/popupfrontend.js" type="text/javascript"></script>
	<script language="JavaScript" src="./js/jquery-1.8.2.js" type="text/javascript"></script>
    <!-- Google Fonts -->
    <link href='http://fonts.googleapis.com/css?family=Titillium+Web:400,200,300,700,600' rel='stylesheet' type='text/css'>
    <link href='http://fonts.googleapis.com/css?family=Roboto+Condensed:400,700,300' rel='stylesheet' type='text/css'>
    <link href='http://fonts.googleapis.com/css?family=Raleway:400,100' rel='stylesheet' type='text/css'>
	
	
    <link rel="stylesheet" href="./css/reset.css">
	<link rel="stylesheet" href="./css/set2.css">
	<link rel='stylesheet prefetch' href='http://fonts.googleapis.com/css?family=Playball'>
   
    <!-- Bootstrap -->
    <link rel="stylesheet" href="./css/bootstrap.min.css">
    <!-- Font Awesome -->
    <link rel="stylesheet" href="./css/font-awesome.min.css">
	<!-- Custom CSS -->
    <link rel="stylesheet" href="./css/owl.carousel.css">
    <link rel="stylesheet" href="./css/style.css?v=2">
	<link rel="stylesheet" href="./css/style1.css">
	<link rel="stylesheet" href="./css/responsive.css">
<!--js and css code starts here  for loading popup added by jhansi-->
	<link rel="stylesheet" href="./css/colorbox.css" />
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
	<script src="./js/jquery.colorbox.js"></script>
	<!--js and css code ends here  for loading popup added by jhansi-->

	
<style type='text/css'>
/*<![CDATA[*/


td.loading_image{background:  url('./images/loader.gif');width:100px; /* use you own image size; */ 
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


.divmobilelaptop{left:37.5%;top:15%;font: 16px/22px 'Trebuchet MS', Verdana, Arial; text-align:left; border:6px solid #424242; border-radius: 6px 6px 6px 6px;position:absolute;padding: 1px;display: none;z-index:60;background-color: white;}

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
/*]]>*/
</style>

  </head>
  <body>
 
 
<!-- On load images  -->

<div class="se-pre-con"></div>
 
  <!-- Start Alexa Certify Javascript -->
<script type="text/javascript">
_atrk_opts = { atrk_acct:"VrG0j1a4ZP00yt", domain:"BookBattery.com",dynamic: true};
(function() { var as = document.createElement('script'); as.type = 'text/javascript'; as.async = true; as.src = "https://d31qbv1cthcecs.cloudfront.net/atrk.js"; var s = document.getElementsByTagName('script')[0];s.parentNode.insertBefore(as, s); })();
</script>
<noscript><img src="https://d5nxst8fruw4z.cloudfront.net/atrk.gif?account=VrG0j1a4ZP00yt" style="display:none" height="1" width="1" alt="" /></noscript>
<!-- End Alexa Certify Javascript -->
<form name="batterycheckyourself" action="">

<br>
 <h4 class="section-title" style="width:1347px;padding-top: 10px;padding-bottom: 19px;
    font-weight: 400;">How to Check A Car Battery Condition Yourself</h4> 
   	
<!-- Starts Video url code -->
  
        <div class="container" style="margin-left: 190px;width:1150px;">
            <div class="row">
				<div class="col-md-5">
                    <iframe width="420" height="345" frameborder="0" src="http://www.youtube.com/embed/hGMmtcsuLgc?rel=0&autoplay=0&loop=0&wmode=opaque"></iframe>
                </div>
				<div class="col-md-5">
                   	<iframe width="420" height="345" src="https://www.youtube.com/embed/4IINQ7i4UDk" frameborder="0" allowfullscreen></iframe>
                </div>
               
			</div>
			<br></br>
		</div>
  
<!-- Ends Video url code -->

<!-- Starts promo area -->
    <div style="background: #ffffff;color: #fff; padding: 0px 0 0px; margin-bottom: -20px;">
        <div class="container">
            <div class="row">
				<div class="col-md-3">
                    <div class="single-promo promo8">
                        <i class="fa fa-cog"></i>
                        <h5>Book Your Battery Service</h5>
						<a class="caption button-radius white" href="javascript:checkbatteryservice();"><span class="icon"></span>Book Service</a>
                    </div>
                </div>
                <div class="col-md-3">
                    <div class="single-promo promo3" >
                        <i class="fa fa-play"></i>
                        <h5>How To Check A Car Battery Yourself</h5>
						<a class="caption button-radius white" href="BookBattery/batterycheckyourself.jsp"><span class="icon"></span>Click Here</a>
                    </div>
                </div>
				   <div class="col-md-3">
                    <div class="single-promo promo6">
                        <i class="fa fa-check"></i>
                        <h5>Check Your Warranty Period</h5>
						<a class="caption button-radius white" href="./checkwarranty.jsp"><span class="icon"></span>Check now</a>
                    </div>
                </div>
                <div class="col-md-3">
                    <div class="single-promo promo4">
                        <i class="fa fa-file-text"></i>
                        <h5>Register Your Battery Details </h5>
						<a class="caption button-radius white" href="./registerbattery.jsp"><span class="icon"></span>Register Now</a>
                    </div>
                </div>
			</div>
			<br></br>
		</div>
    </div> 
<!-- End promo area -->

<!-- Starts Shop by Category -->
<div class="container" >
   <h4 class="section-title" style="color: #636363;text-align: left;font-weight: 600;">Shop by Category</h4>
   </div>
	<br>
			<div class="container" >
			
			<ul class="row animated-area" style="margin-left: 0px;">
               <li class="col-md-2 animated" data-animation-delay="0.2s" data-animation="fadeIn" style="width: 230px;padding-left: 0px; padding-bottom: 10px;">
                   <div class="ImageWrapper">
                            <a href="/bookbattery/carbattery.jsp" alt="car battery">
                               <img class="img-responsive"  src="/bookbattery/images/car_battery1.png" alt="Car Batteries"/>
                                <div class="PStyleHe"></div>
                            </a>
                    </div>
                </li>
           
               <li class="col-md-2 animated" data-animation-delay="0.2s" data-animation="fadeIn" style="width: 230px;padding-left: 0px; padding-bottom: 10px;">
                   <div class="ImageWrapper">
                            <a href="/bookbattery/inverterbattery.jsp" alt="Inverter battery online">
                                <img class="img-responsive"  src="/bookbattery/images/inverter_BookBattery1.png"alt="Inverter Batteries"/>
                                <div class="PStyleHe"></div>
                            </a>
                    </div>
                </li>
           
               <li class="col-md-2 animated" data-animation-delay="0.2s" data-animation="fadeIn" style="width: 230px;padding-left: 0px; padding-bottom: 10px;">
                   <div class="ImageWrapper">
                            <a href="/bookbattery/bikebattery.jsp" alt="Bike battery online">
                                <img class="img-responsive" src="/bookbattery/images/bike_battery1.png" alt="Bike Batteries"/>
                                <div class="PStyleHe"></div>
                            </a>
                    </div>
                </li>
            
               <li class="col-md-2 animated" data-animation-delay="0.2s" data-animation="fadeIn" style="width: 230px;padding-left: 0px; padding-bottom: 10px;">
                   <div class="ImageWrapper">
                            <a href="/bookbattery/busbattery.jsp" alt="Bus battery online">
                                <img class="img-responsive" src="/bookbattery/images/bus_battery1.png" alt="Bus Batteries"/>
                                <div class="PStyleHe"></div>
                            </a>
                    </div>
                </li>
           
               <li class="col-md-2 animated" data-animation-delay="0.2s" data-animation="fadeIn" style="width: 230px;padding-left: 0px; padding-bottom: 10px;">
                   <div class="ImageWrapper">
                            <a href="/bookbattery/truckbattery.jsp" alt="truck battery online">
                                <img class="img-responsive" src="/bookbattery/images/truck_battery1.png" alt="Truck Batteries"/>
                                <div class="PStyleHe"></div>
                            </a>
                    </div>
                </li>
           
               <li class="col-md-2 animated" data-animation-delay="0.2s" data-animation="fadeIn" style="width: 230px;padding-left: 0px; padding-bottom: 10px;">
                   <div class="ImageWrapper">
                            <a href="/bookbattery/gensetbattery.jsp" alt="Buy genset battery online">
                                <img class="img-responsive" src="/bookbattery/images/genset_BookBattery1.png" alt="Genset Batteries"/>
                                <div class="PStyleHe"></div>
                            </a>
                    </div>
                </li>
           
               <li class="col-md-2 animated" data-animation-delay="0.2s" data-animation="fadeIn" style="width: 230px;padding-left: 0px; padding-bottom: 10px;">
                   <div class="ImageWrapper">
                            <a href="/bookbattery/tractorbattery.jsp" alt="Tractor battery online">
                                <img class="img-responsive" src="/bookbattery/images/tractor_battery1.png" alt="Tractor Batteries"/>
                                <div class="PStyleHe"></div>
                            </a>
                    </div>
                </li>
           
               <li class="col-md-2 animated" data-animation-delay="0.2s" data-animation="fadeIn" style="width: 230px;padding-left: 0px; padding-bottom: 10px;">
                   <div class="ImageWrapper">
                            <a href="/bookbattery/autorickbattery.jsp" alt="Auto rick battery online">
                                <img class="img-responsive" src="/bookbattery/images/auto_battery1.png" alt="Auto Rick Batteries"/>
                                <div class="PStyleHe"></div>
                            </a>
                    </div>
                </li>
            
               <li class="col-md-2 animated hide" data-animation-delay="0.2s" data-animation="fadeIn" style="width: 230px;padding-left: 0px; padding-bottom: 10px;">
                   <div class="ImageWrapper">
                            <a href="/bookbattery/laptopbattery.jsp" alt="laptop battery online">
                                <img class="img-responsive" src="/bookbattery/images/laptop_battery1.png" alt="Laptop Batteries"/>
                                <div class="PStyleHe"></div>
                            </a>
                    </div>
                </li>
           
               <li class="col-md-2 animated" data-animation-delay="0.2s" data-animation="fadeIn" style="width: 230px;padding-left: 0px; padding-bottom: 10px;">
                   <div class="ImageWrapper">
                            <a href="/bookbattery/inverter.jsp" alt="Buy inverter online">
                                <img class="img-responsive" src="/bookbattery/images/inverter_home1.png" alt="Inverters"/>
                                <div class="PStyleHe"></div>
                            </a>
                    </div>
                </li>
            </ul>
				
       
    </div> 
<!-- Ends Shop by Category -->
<jsp:include page = "footer.jsp" />

  
	
    <!-- Slider -->
    <script type="text/javascript" src="./js/bxslider.min.js"></script>
	<script type="text/javascript" src="./js/script.slider.js"></script>
	<input type="hidden" name="publicUrl" id="publicUrl" value="<%=publicUrl%>"/>
	<input type="hidden" name="keyword" id='keyword' value="<%=keyword%>">
	<input type="hidden" name="bookbatteryservice" id='bookbatteryservice' value="<%=Bookbatteryservice%>">

</form>
<%
String strbatLogMsg=(String)session.getAttribute("sesbatterydetailsErrorMsg");
if(strbatLogMsg ==null)
{
	strbatLogMsg="";
}
else
{
%>
<script type="text/javascript">
//<![CDATA[
var sesmessg; 
sesmessg= "<%=strbatLogMsg%>";
document.getElementById("displysesmsg").innerHTML=sesmessg;
//]]>
</script>
<%
	session.removeAttribute("sesbatterydetailsErrorMsg");
}
%>
<script type="text/javascript">
//<![CDATA[
setTimeout(
function()
{
	$("#BookBatterydiv").show();
	  $("#BookBatterydiv").animate(
            {"left": "73%"},
            {
				duration:6000,
			});
},1500);

$(window).load(function()
{
	 $('#scrollpopup').hide();	 
});
$(window).bind("pageshow", function() 
{
    document.batteryindex.reset();	
});
setInterval(
function()
{
	minimizediv()
},15000);

function minimizediv()
{
	if($("#BookBatterydiv").is(':visible'))
	{
		$('#BookBatterydiv').hide();
		var minimizemsg="<A href='javascript:void(null)' onclick='openBookBatterydiv()' style='text-decoration:none;outline:none;'><TABLE cellspacing='0' cellpadding='5' border='0' width='240'><TR><TD valign='top' width='230'><font face='verdana' size='2' color='#ffffff'><div style='width:200px;overflow:hidden;text-overflow:ellipsis;white-space:nowrap;'><b>BookBattery.com</b><div></font></TD><TD valign='center' width='10'><IMG SRC='./images/uparrow.png' WIDTH='16' HEIGHT='11' BORDER='0' ALT='maximize'></TD></TR></TABLE></A>";
		$('#BookBatterytogglediv').empty();
		$('#BookBatterytogglediv').html(minimizemsg);
		$('#BookBatterytogglediv').show();
	}
	else
	{

	}
}
function openBookBatterydiv()
{
	$("#BookBatterytogglediv").hide();
	$('#BookBatterydiv').show();
}
function hideBookBatterydiv()
{
	$('#BookBatterydiv').hide();
}

(function() {
    var po = document.createElement('script'); po.type = 'text/javascript'; po.async = true;
      po.src = './js/fetchdropdownvalues.js?<%=context.getInitParameter("fetchdropdownvalues")%>';
    var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(po, s);
  })();
if (/MSIE (\d+\.\d+);/.test(navigator.userAgent))
{
	var ieversion=new Number(RegExp.$1)
	if (ieversion > 9)
	{
		(function() {
		var po = document.createElement('script'); po.type = 'text/javascript'; po.async = true;
		po.src = './js/jqFancyTransitions.1.8.min.js';
		var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(po, s);
		})();
	} 
}
browsername=navigator.appName;
if ((browsername.indexOf("Netscape")!=-1) || (browsername.indexOf("Opera")!=-1))
{

		(function() {
		var po = document.createElement('script'); po.type = 'text/javascript'; po.async = true;
		po.src = './js/jqFancyTransitions.1.8.min.js';
		var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(po, s);
		})();
}
$(document).ready(function()
{
	
$('#vehiclemake').show();
$('#vehiclemodel').show();
});

  //]]>
</script>

<!-- Hint Script -->
<script>
$(document).ready(function(){
    $('[data-toggle="tooltip"]').tooltip(); 
});
</script>

<script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
<!-- Pop Up js -->
<script src="./js/popup.js" type="text/javascript"></script>
<script type="text/javascript" src="http://s7.addthis.com/js/300/addthis_widget.js#pubid=ra-55f7e9e53ecd0431" async="async"></script>
</body>
</html>
