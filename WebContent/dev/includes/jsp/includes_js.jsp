<%-- 
Document   		 : includes_js.jsp
Created on 		 : Sep 15, 2016, 10:14:12 AM
Author     		 : BookBattery
Copyright Notice : BookBattery Confidential.
Document         : This jsp is used as Common Js Jsp used in all over the Front end.
--%>
<%@page language="java" import="java.io.File,java.text.*,java.util.Calendar,java.util.ArrayList,java.util.Hashtable,java.util.Vector,com.ngit.javabean.loglevel.*,java.util.Date,java.util.Properties,java.util.*,com.ngit.javabean.loglevel.LogLevel,javax.servlet.ServletContext,java.util.Properties,java.io.FileInputStream,java.io.*,java.util.*"%>
<%
Properties propsMOPConfig = new Properties();
ServletContext context = getServletContext();
FileInputStream fin1      = new FileInputStream(new File(context.getRealPath("properties/bookbatteryconfig.properties"))); 
propsMOPConfig.load(fin1); 
fin1.close(); 
String publicUrl = (propsMOPConfig.getProperty("publicUrl")!=null)?propsMOPConfig.getProperty("publicUrl"):"";
%>

<!-- JavaScript -->
<script type="text/javascript" src="<%=publicUrl%>/bookbattery/dev/includes/js/jquery.min.js"></script>
<script type="text/javascript" src="<%=publicUrl%>/bookbattery/dev/includes/js/bootstrap.min.js"></script>
<script type="text/javascript" src="<%=publicUrl%>/bookbattery/dev/includes/js/common.js?v=19"></script>
<script type="text/javascript" src="<%=publicUrl%>/bookbattery/dev/includes/js/slider.js"></script>
<script type="text/javascript" src="<%=publicUrl%>/bookbattery/dev/includes/js/filter.js?v=1"></script>
<script type="text/javascript" src="<%=publicUrl%>/bookbattery/dev/includes/js/owl.carousel.min.js"></script>
<script type="text/javascript" src="<%=publicUrl%>/bookbattery/dev/includes/js/cloud-zoom.js"></script>
<script type="text/javascript">
    //<![CDATA[
	jQuery(function() {
		jQuery(".slideshow").cycle({
			fx: 'scrollHorz', easing: 'easeInOutCubic', timeout: 10000, speedOut: 800, speedIn: 800, sync: 1, pause: 1, fit: 0, 			pager: '#home-slides-pager',
			prev: '#home-slides-prev',
			next: '#home-slides-next'
		});
	});
    //]]>
</script>

<!-- JavaScript //functionality -->
<script type="text/javascript" src="<%=publicUrl%>/bookbattery/dev/js/dropdown.js?v=24"></script>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/select2/3.4.4/select2.min.js"></script>
<script type="text/javascript" src="<%=publicUrl%>/bookbattery/dev/js/valadation.js?v=21"></script>
<script src="/bookbattery/dev/js/PlacePicker.js"></script>


<!--Code For Google Analytics starts here-->
<script type="text/javascript">

  var _gaq = _gaq || [];
  _gaq.push(['_setAccount', 'UA-36251023-1']);
  _gaq.push(['_setDomainName', 'jqueryscript.net']);
  _gaq.push(['_trackPageview']);

  (function() {
    var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
    ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
    var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
  })();

</script>
<!--Code For Google Analytics ends here-->
<!-- Global site tag (gtag.js) - Google Ads: 970609998 --> <script async src="https://www.googletagmanager.com/gtag/js?id=AW-970609998"></script> <script> window.dataLayer = window.dataLayer || []; function gtag(){dataLayer.push(arguments);} gtag('js', new Date()); gtag('config', 'AW-970609998'); </script>

<!--Start of Tawk.to Script-->
<script type="text/javascript">

/*var Tawk_API=Tawk_API||{}, Tawk_LoadStart=new Date();
(function(){
var s1=document.createElement("script"),s0=document.getElementsByTagName("script")[0];
s1.async=true;
s1.src='https://embed.tawk.to/582f01b0397ff569a3d204ef/default';
s1.charset='UTF-8';
s1.setAttribute('crossorigin','*');
s0.parentNode.insertBefore(s1,s0);
})();

function Open_Chat()
{
	Tawk_API.showWidget();
	Tawk_API.toggle()
}*/

</script>
<!--End of Tawk.to Script-->

<script type="application/ld+json">
{
  "@context": "http://schema.org",
  "@type": "Organization",
  "url": "http://www.BookBattery.com",
  "logo": "http://www.BookBattery.com/images/BookBatterylogo_amaron.png",
  "contactPoint": [{
    "@type": "ContactPoint",
    "telephone": "+91-96663-00003",
    "contactType": "customer service"
  }]
}
{
  "@context": "http://schema.org",
  "@type": "Organization",
  "url": "http://www.BookBattery.com",
  "logo": "http://www.BookBattery.com/images/BookBatterylogo_amaron.png",
  "contactPoint": [{
    "@type": "ContactPoint",
    "telephone": "+91-96663-00003",
    "contactType": "customer care"
  }]
}
</script>


<!-- plugin call -->
<script> 
var $buoop = {vs:{i:10,f:-6,o:-4,s:6,c:-8},unsecure:true,api:4}; 
function $buo_f(){ 
 var e = document.createElement("script"); 
 e.src = "//browser-update.org/update.min.js"; 
 document.body.appendChild(e);
};
try {document.addEventListener("DOMContentLoaded", $buo_f,false)}
catch(e){window.attachEvent("onload", $buo_f)}
</script> 
<script>

window.onscroll = function() {

	//alert(22);
	myFunction()};
	var header = document.getElementById("myHeader");
	var sticky = header.offsetTop;
	
	function myFunction() {
		  if (window.pageYOffset > sticky)
		  {
		    header.classList.add("sticky");
		  } 
		  
		  else 
		  {
		    header.classList.remove("sticky");
		  }
	}

</script>

<input type="hidden" name="brandkeyword" id='brandkeyword' value="Common">