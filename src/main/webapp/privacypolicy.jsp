<%-- 
Document   		 : privacypolicy.jsp
Created on 		 : Sep 15, 2016, 10:14:12 AM
Author     		 : BookBattery
Copyright Notice : BookBattery Confidential.
Document         : This jsp is used as Privacy Policy Jsp page.
--%>

<%@ page language="java" import="java.sql.*"%>
<%@page language="java" import="java.io.File,java.util.Hashtable,java.util.Vector,com.ngit.javabean.loglevel.*,java.util.Enumeration,java.util.Properties,java.io.FileInputStream,javax.servlet.ServletContext"%>
<%
Properties propsMOPConfig = new Properties();
ServletContext context = getServletContext();
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
	<jsp:include page = "dev/includes/jsp/includes_css.jsp" />
<!---################################## CSS Include Ends  ################################------>

</head>
<body>
<div class="page">

<!---################################## Header Include Starts  ################################------>
	<jsp:include page = "dev/includes/jsp/includes_header.jsp" />
<!---################################## Header Include Ends  ################################------>
  <!-- breadcrumbs -->
  <div class="breadcrumbs">
    <div class="container">
      <div class="row">
        <ul>
          <li class="home"> <a href="<%=publicUrl%><%=baseurldirectory%>" title="Go to Home Page"> Home</a><span>&raquo;</span></li>
          <li class="category13"><strong>Privacy & Policy</strong></li>
        </ul>
      </div>
    </div>
  </div>
  <!-- End breadcrumbs --> 
  <div class="main-container col2-right-layout">
    <div class="main container">
      <div class="row">
        <section class="col-main col-sm-9" style=" font-size: 14px;">  <div class="static-contain">
          <div class="page-title new_page_title">
            <h4>Privacy Policy</h4>
          </div>
	<div>

     <p style="text-align:justify">This Privacy Policy relates solely to the information collection and use practices of our web site located at <a href = "http://www.BookBattery.com">www.BookBattery.com </a> and its related links (this "Website"). We recognize that many visitors and users of this Website are concerned about the information they provide to us, and how we treat that information. This Privacy Policy, which may be updated from time to time, has been developed to address those concerns.</p>

	<p style="text-align:justify">BY ACCESSING OR USING OUR WEBSITE OR BY PROVIDING YOUR INFORMATION, YOU HEREBY ACKNOWLEDGE THAT YOU HAVE READ, UNDERSTOOD, AND AGREED TO BE BOUND BY ALL THE TERMS OF THIS PRIVACY POLICY AND OUR WEBSITE TERMS OF USE. IF YOU DO NOT AGREE TO THESE TERMS, PLEASE EXIT THIS PAGE AND DO NOT ACCESS OR USE THE WEBSITE.</p>

	 <h4 >1. Changes to Privacy Policy</h4>

	<p style="text-align:justify">We review our Privacy Policy from time to time, and we may make periodic changes to the policy in connection with that review. The revisions in Privacy Policy will be effective immediately upon being posted on the Website. Therefore, you may wish periodically review this page to make sure you have the latest version. Your continued use of the Website after the effectiveness of such revisions will constitute your acknowledgment and acceptance of the terms of the revised Privacy Policy.</p>

	<h4 >2. Types of Information Collected and Uses of Collected Information</h4>

	 <p >We collect two types of information about our Website Users: Personally Identifiable Information and Non-Personally Identifiable Information.</p>

	<p style="text-align:justify">Personally Identifiable Information: Personally Identifiable Information is information that identifies a specific End User. When you engage in certain activities on the Website, such as creating an account, ordering a product or service from us, submitting content and/or posting content in discussion forums, filling out a survey, posting a review, requesting information about our services, applying for a job (collectively, “Identification Activities”), we may ask you to provide certain information about yourself. It is optional for you to engage in an Identification Activity. If you elect to engage in an Identification Activity, however, we may ask you to provide us with certain personal information about yourself, such as your first and last name, mailing address (including PIN code), email address, telephone number and date of birth. When you order products, we may also ask you to provide us with your credit card number, expiration date and authentication codes or related information. Depending on the activity, some of the information we ask you to provide is identified as mandatory and some is identified as voluntary. If you do not provide the mandatory information for a particular activity that requires it, you will not be permitted to engage in that activity.</p>

	<p style="text-align:justify">We use Personally Identifiable Information to provide products to you, enhance the operation of the Website, improve our marketing and promotional efforts, analyze Website use, improve our product offerings, and to provide you a better experience. For example, if you send our customer service an email we may use your comments and feedback to tell others about our services, and may post your comment in our marketing materials or on our Website. Also, if you use our Website to send information or a product to another person, we may store your personal information, and the personal information of any recipient. We may use that other person’s contact information to allow him or her to view and accept your gift or to allow the recipient to access the information you sent. We may also use Personally Identifiable Information to troubleshoot, resolve disputes, accomplish administrative tasks, contact you, enforce our agreements with you, including our Website Terms of Use and this Privacy Policy, comply with applicable law, and cooperate with law enforcement activities.</p>

	<p style="text-align:justify">Non-Personally Identifiable Information: Non-Personally Identifiable Information is information that does not identify a specific End User. This type of information may include things like the Uniform Resource Locator (“URL”) of the website you visited before coming to our Website, the URL of the website you visit after leaving our Website, the type of browser you are using and your Internet Protocol (“IP”) address.</p>

	<p style="text-align:justify">We use Non-Personally Identifiable Information to troubleshoot, administer the Website, analyze trends, gather demographic information, comply with applicable law, and cooperate with law enforcement activities.</p>

	<h4 >3. Release of Personally Identifiable Information</h4>

	 <p style="text-align:justify">We will not share your Personally Identifiable Information with other parties. except as provided below: We may share your information with Authorized Third Party Service Providers. We provide some of our services and products through third parties. These “Third Party Service Providers” perform functions on our behalf, like sending out and distributing our administrative and promotional emails. We may share your Personally Identifiable Information with such Service Providers to deliver packages, send email, provide marketing assistance, provide search results and links, process credit card payments, operate the Website, troubleshoot, and provide customer service.</p>

	<p style="text-align:justify">We may also disclose personal information if required to do so by law or in the good faith belief that such disclosure is reasonably necessary to respond to subpoenas, court orders, or other legal process. We may disclose personal information to law enforcement offices, third party rights owners, or others in the good faith belief that such disclosure is reasonably necessary to: enforce our Terms or Privacy Policy; respond to claims that an advertisement, posting or other content violates the rights of a third party; or protect the rights, property or personal safety of our users or the general public.</p>

	<h4 >4. Release of Non-Personally Identifiable Information</h4>

	 <p style="text-align:justify">We may disclose or share Non-Personally Identifiable Information with Partners, Affiliates and Advertisers. We may share aggregated demographic information (which does not include any Personally Identifiable Information) with “Third Party Advertisers” or “Third Party Advertising Companies”.</p>

	<p style="text-align:justify">We also use Third Party Service Providers to track and analyze Non-Personally Identifiable usage and volume statistical information from our Users to administer our Website and constantly improve its quality. We may also publish this information for promotional purposes or as a representative audience for Advertisers. Please note that this is not Personally Identifiable Information, only general summaries of the activities of our Users. Such data is collected on our behalf, and is owned and used by us.</p>

	<h4 >5. Updating Information</h4>

	<p style="text-align:justify">You will have the ability to access and edit the Personally Identifiable Information you provide us. You may change any of your Personally Identifiable Information by accessing through your login and password.</p>

	<p >We would request you to promptly update your Personally Identifiable Information if it changes.</p>

	<h4 >6. Data Tracking</h4>

	 <p style="text-align:justify">Cookies. "Cookies" are small pieces of information that are stored by your browser on your computer's hard drive. The use of cookies is very common on the Internet and our Website´s use of cookies is similar to that of other reputable online companies.Cookies will be used to customize your experience with the Website. We use cookies to save you time while using the Website, helps us identify who you are, and track and target User interests in order to provide a customized experience. Cookies also allow us to collect Non-Personally Identifiable Information from you, like which pages you visited and what links you clicked on. Use of this information helps us to create a more user-friendly experience for all visitors. In addition, we may use Third Party Advertising Companies to display advertisements on our Website. Most browsers automatically accept cookies, but you may be able to modify your browser settings to decline cookies. Please note that if you decline or delete these cookies, some parts of the Website may not work properly.Additionally, you may encounter "cookies" or other similar devices on certain pages of the website that are placed by third parties. We do not control the use of cookies by third parties.Other Tracking Devices. We may use other industry standard technologies like pixel tags and web beacons to track your use of our Website pages and promotions, or we may allow our Third Party Service Providers to use these devices on our behalf. Pixel tags and web beacons are tiny graphic images placed on certain pages on our Website, or in our emails that allow us to determine whether you have performed a specific action. When you access these pages or open or click an email, pixel tags and web beacons generate a Non-Personally Identifiable notice of that action. Pixel tags allow us to measure and improve our understanding of visitor traffic and behaviour on our Website, as well as give us a way to measure our promotions and performance. We may also utilize pixel tags and web beacons provided by our Affiliates and/or Marketing Partners for the same purposes.</p>

	<h4 >7. Security of Information</h4>

	 <p style="text-align:justify">We take appropriate precautions to protect the security of Personally Identifiable Information. You can access your Personally Identifiable Information on our Website through your login and password. We recommend that you do not share your password with anyone. In addition, your Personally Identifiable Information resides on a secure server that only selected personnel and contractors have access to. We encrypt certain sensitive information using Secure Socket Layer (SSL) technology to ensure that your Personally Identifiable Information is safe as it is transmitted to us.</p>

	 <p style="text-align:justify">However, no data transmission over the Internet can be guaranteed to be completely secure. Accordingly, we cannot ensure or warrant the security of any information that you transmit to us, so you do so at your own risk.</p>

	<h4 >8. Privacy Policies of Third Party Websites</h4>

	 <p style="text-align:justify">This Privacy Policy only addresses the use and disclosure of information we collect from you. Other websites that may be accessible through this Website have their own privacy policies and data collection, use and disclosure practices. If you link to any such website, we urge you review the website’s privacy policy. We are not responsible for the policies or practices of third parties. </p>

	 
	 
	 </div>
			
          </div>
        </section>
        <aside class="col-right sidebar col-sm-3 wow bounceInUp animated">
          <div class="block block-company">
            <div class="block-title">Quick Links </div>
            <div class="block-content">
              <ol id="recently-viewed-items">
                <li class="item  even"><a href="choosecarbattery.jsp">Choose your Car Battery</a></li>
                <li class="item even"><a href="faq.jsp">FAQ's</a></li>
                <li class="item last"><a href="contactus.jsp">Contact Us</a></li>
              </ol>
            </div>
          </div>
        </aside>
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
	<jsp:include page = "dev/includes/jsp/includes_footer.jsp" />
<!---################################## JS Include Ends  ################################------>

</body>
</html>
