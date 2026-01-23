<%-- 
Document   		 : inverter_list.jsp
Created on 		 : Sep 15, 2016, 10:14:12 AM
Author     		 : BookBattery
Copyright Notice : BookBattery Confidential.
Document         : This jsp is used as Inverter Multi Product list and details page of BookBattery Batteries.
--%>
<%@ page language="java" import="java.sql.*"%>
<%@page language="java" import="java.io.File,java.text.*,java.util.Calendar,java.util.ArrayList,java.util.Hashtable,java.util.Vector,com.ngit.javabean.loglevel.*,java.util.Date,java.util.Properties,java.util.*,com.ngit.javabean.loglevel.LogLevel,javax.servlet.ServletContext,java.util.Properties,java.io.FileInputStream"%>
<%response.setHeader("Pragma","no-cache"); response.setHeader("Cache-Control","no-store"); response.setHeader("Expires","0"); response.setDateHeader("Expires",-1); %>
<%
Vector Vector_Inverter=(Vector)request.getAttribute("Vector_Inverter");
Vector Vector_Inverter_Brands=(Vector)request.getAttribute("Vector_Inverter_Brands");
Vector Vector_Inverter_Name=(Vector)request.getAttribute("Vector_Inverter_Name");
Vector Vector_Inverter_PriceRange=(Vector)request.getAttribute("Vector_Inverter_PriceRange");
Vector Vector_Inverter_Capacity=(Vector)request.getAttribute("Vector_Inverter_Capacity");
Vector Vector_Inverter_Battery_PriceRange=(Vector)request.getAttribute("Vector_Inverter_Battery_PriceRange");

Properties propsMOPConfig = new Properties();
ServletContext context = getServletContext();
FileInputStream fin1      = new FileInputStream(new File(context.getRealPath("properties/bookbatteryconfig.properties"))); 
propsMOPConfig.load(fin1); 
fin1.close(); 
String publicUrl = (propsMOPConfig.getProperty("publicUrl")!=null)?propsMOPConfig.getProperty("publicUrl"):"";
String baseURL = (propsMOPConfig.getProperty("baseURL")!=null)?propsMOPConfig.getProperty("baseURL"):"";
String baseurldirectory = (propsMOPConfig.getProperty("baseurldirectory")!=null)?propsMOPConfig.getProperty("baseurldirectory"):"";
String serverURL = (propsMOPConfig.getProperty("serverURL")!=null)?propsMOPConfig.getProperty("serverURL"):"";

String titles = "";
String description = "";
String keywords = "";

%>

<%
String Product_Type = (String) request.getAttribute("Product_Type");
String Product_Type_URL= Product_Type.replaceAll(" ", "-");
String State = (String) request.getAttribute("Product_State");
String State_URL= State.replaceAll(" ", "-");
String City = (String) request.getAttribute("Product_City");
String City_URL= City.replaceAll(" ", "-");
String Product_Brand = (String) request.getAttribute("Product_Brand");
String Product_Brand_URL= Product_Brand.replaceAll(" ", "-");

String Inverter_Filter_Capacity = (String) request.getAttribute("Inverter_Filter_Capacity");
//String Product_PriceRange_tmp_0 = (String) request.getAttribute("Product_PriceRange_tmp_0");
//String Product_PriceRange_tmp_1 = (String) request.getAttribute("Product_PriceRange_tmp_1");
String Inverter_Filter_Name = (String) request.getAttribute("Inverter_Filter_Name");
String Product_PriceType = (String) request.getAttribute("Product_PriceType");
LogLevel.DEBUG(5, new Throwable(), "Product_PriceType in jsp :" + Product_PriceType);
//out.println("Your IP address is " );

titles=Product_Type+", "+Product_Brand+" | Buy "+Product_Type+" "+Product_Brand+" Online at Best Price in India | BookBattery.com";
description="Buy 100% genuine "+Product_Type+" from Brands Online - Free Shipping, Cash on delivery & Free Home Delivery at BookBattery.com";
keywords=Product_Type+", "+Product_Brand+".";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head itemscope itemtype="http://schema.org/WebSite">
<title itemprop='name'><%=titles%></title>
<meta charset="utf-8">
<meta http-equiv="x-ua-compatible" content="ie=edge">
<meta name="viewport" content="width=device-width">
<meta name="og_title" property="og:title" content="<%=titles%>"/>
<meta name="og_site_name" property="og:site_name" content="BookBattery.com"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="shortcut icon" href="<%=publicUrl%>/bookbattery/images/favicon.png" type="image/x-icon">
<meta name='Description' content="<%=description%>"/>
<meta name='Keywords' content='<%=keywords%>'/>


<!---################################## CSS Include Starts  ################################------>
	<jsp:include page = "/assets/includes/includes_css.jsp" />
<!---################################## CSS Include Ends  ################################------>
</head>
<body>
<!---################################## Header Include Starts  ################################------>
	<jsp:include page = "/assets/includes/includes_header.jsp" />
<!---################################## Header Include Ends  ################################------>

<form name="batterydet" autocomplete="off">
   <div id="filter_container" class="container" style="
    padding: 10px !important;
    z-index: 9999999999 !important;
    min-width: 0px !important;
    min-height: 0px !important;
    max-width: none !important;
    max-height: none !important;
    width: auto !important;
    height: auto !important;
    position: fixed !important;
    margin: 0px !important;
    left: 0px !important;
    top: 0px !important;
    right: 0px !important;
    bottom: 0px !important;
    display: none;
    background: #eee;
	overflow:scroll;">
  <!-- breadcrumbs -->
  <div id="icon-close">
  <div style="text-align: -webkit-center;font-size: x-large;"><b>FILTERS</b><a onClick="closefilter();"><i style="float: right;right: 1%;"  class="glyphicon glyphicon-remove close-icon"></i></a></div>
  </div>
 
	  <%
	  if(Vector_Inverter_Brands!=null && Vector_Inverter_Brands.size() > 0)
		{
		try{
	  %>
			<div class="block block-poll" style="border: 3px solid black;margin-bottom: 15px;">
				<form onSubmit="return validatePollAnswerIsSelected();" method="post" action="#" id="pollForm">
				  <div class="block-content">
					<p class="block-subtitle">Filter by <%=Product_Type%> Brand</p>
					<ul id="poll-answers">
				<%
					for ( int i=0; i<Vector_Inverter_Brands.size() ; i++){
						Hashtable ht=(Hashtable)Vector_Inverter_Brands.get(i); 
						String Brand_Names=String.valueOf(ht.get("inverter_brand"));
				%>
					  <li class="odd">
						<input type="checkbox" style="margin: -1px -18px 0 0;width: 17px;height: 18px;" id="Filter_Brand_Names" class="radio poll_vote Filter_Brand_Names" name="Filter_Brand_Names_pop" value="<%=Brand_Names%>">
						<span class="label">
						<label style="margin-bottom:2px;font-size: 14px;margin-left: 5%;" for="<%=Brand_Names%>"><%=Brand_Names%></label>
						</span> </li>
				<%
					}
				%>
					</ul>
					<span style="float: right;"> <a href="javascript:clear_all_filter('Filter_Brand_Names')"><b>Clear All</b></a></span>
				  </div>
				</form>
			  </div>
		<%
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		%>	  
		

	  <%
	  if(Vector_Inverter_Capacity!=null && Vector_Inverter_Capacity.size() > 0)
		{
		try{
	  %>
			<div class="block block-poll" style="border: 3px solid black;margin-bottom: 15px;">
				  <div class="block-content">
					<p class="block-subtitle">Filter by Capacity</p>
					<ul id="poll-answers">
				<%
					for ( int i=0; i<Vector_Inverter_Capacity.size() ; i++){
						Hashtable ht=(Hashtable)Vector_Inverter_Capacity.get(i);
						String Inverter_Capacity=String.valueOf(ht.get("inverter_capacity"));
				%>
					  <li class="odd">
						<input type="checkbox" style="margin: -1px -18px 0 0;width: 17px;height: 18px;" value="<%=Inverter_Capacity%>"  id="Filter_Inverter_Capacity" class="radio poll_vote Filter_Inverter_Capacity" name="Filter_Inverter_Capacity_pop">
						<span class="label">
						<label style="margin-bottom:2px;font-size: 14px;margin-left: 5%;" for="<%=Inverter_Capacity%>"><%=Inverter_Capacity%></label>
						</span> </li>
				<%
					}
				%>
					</ul>
					<span style="float: right;"> <a href="javascript:clear_all_filter('Filter_Inverter_Capacity')"><b>Clear All</b></a></span>
				  </div>
			  </div>
		<%
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		%>	 		

	  <%
	  if(Vector_Inverter_Name!=null && Vector_Inverter_Name.size() > 0)
		{
		try{
	  %>
			<div class="block block-poll" style="border: 3px solid black;margin-bottom: 15px;">
				  <div class="block-content">
					<p class="block-subtitle">Filter by Inverter Type?</p>
					<ul id="poll-answers">
				<%
					for ( int i=0; i<Vector_Inverter_Name.size() ; i++){
						Hashtable ht=(Hashtable)Vector_Inverter_Name.get(i);
						String Inverter_Name=String.valueOf(ht.get("inverter_name"));
				%>
					  <li class="odd">
						<input type="checkbox" style="margin: -1px -18px 0 0;width: 17px;height: 18px;" value="<%=Inverter_Name%>"  id="Filter_Inverter_Name" class="radio poll_vote Filter_Inverter_Name" name="Filter_Inverter_Name_pop">
						<span class="label">
						<label style="margin-bottom:2px;font-size: 14px;margin-left: 5%;" for="<%=Inverter_Name%>"><%=Inverter_Name%></label>
						</span> </li>
				<%
					}
				%>
					</ul>
					<span style="float: right;"> <a href="javascript:clear_all_filter('Filter_Inverter_Name')"><b>Clear All</b></a></span>
				  </div>
			  </div>
		<%
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		%>	 
					<!--<div class="block block-poll" style="border: 3px solid black;margin-bottom: 15px;">
				  <div class="block-content">
					<p class="block-subtitle">Filter by Price Range</p>
					<div class="col-xs-12">
					<div class="col-xs-6"><input style="color:black" type="text" value="" class="js-input-from form-control Filter_Battery_Price_Range"  name="Filter_Battery_Price_Range_pop"/></div>
					<div class="col-xs-6"><input style="color:black" type="text" value="" class="js-input-to form-control Filter_Battery_Price_Range"  name="Filter_Battery_Price_Range_pop"/></div>
					</div>
					 <hr>
					<div class="range-slider" style="margin-top: 14%;">
					<input type="text" class="js-range-slider" value="" />
					</div>
						<span style="float: right;margin-top: 5%;"> <a href="javascript:clear_all_filter('Filter_Battery_Price_Range')"> <b>Clear All</b></a></span>
				  </div>
			  </div>-->
		
			<div class="actions" style="text-align: -webkit-center;">
			 <button class="button" style="width: 100%;" title="Filter By Battery Warranty" type="button" onClick="javascript:UpdateProduct_Filter_Pop_UP();"><span style="font-size: larger;font-weight: bold;">Apply Filters</span></button>
			</div>
	</div>	
  <!-- breadcrumbs -->
  <div class="breadcrumbs">
    <div class="container">
      <div class="row">
        <ul>
          <li class="home"> <a href="<%=publicUrl%><%=baseurldirectory%>" title="Go to Home Page"> Home</a><span>&raquo;</span></li>
          <!--<li class=""> <a href="<%=publicUrl%><%=baseurldirectory%>manufacturer/" title="Go to Manufacturer Page">Manufacturer</a><span>&raquo;</span></li>-->
          <li class=""> <a href="<%=publicUrl%><%=baseurldirectory%>Brand/<%=Product_Type_URL%>/<%=State_URL%>/<%=City_URL%>/" title="Go to <%=Product_Type%> Page"><%=Product_Type%></a><span>&raquo;</span></li>
          <li class="category13"><strong><%=Product_Brand%></strong></li>
        </ul>
      </div>
    </div>
  </div>
  	<ol class="hide" itemscope itemtype="http://schema.org/BreadcrumbList">
	  <li itemprop="itemListElement" itemscope itemtype="http://schema.org/ListItem">
		<a itemscope itemtype="http://schema.org/Thing" itemprop="item" href="<%=publicUrl%><%=baseurldirectory%>manufacturer/<%=Product_Type_URL%>/">
		  <span itemprop="name"><%=Product_Type%></span>
		</a>
		<meta itemprop="position" content="1" />
	  </li>
	  <li itemprop="itemListElement" itemscope itemtype="http://schema.org/ListItem">
		<a itemscope itemtype="http://schema.org/Thing" itemprop="item" href="<%=publicUrl%><%=baseurldirectory%><%=Product_Type_URL%>/<%=Product_Brand_URL%>/">
		  <span itemprop="name"><%=Product_Brand%></span>
		</a>
		<meta itemprop="position" content="2" />
	  </li>
	</ol>
  <!-- End breadcrumbs --> 
  <!-- Two columns content -->
  <div class="main-container col2-left-layout" id="Page_Result_ID">
    <div class="main container">
      <div class="row">
	  <section class="col-md-9 col-xs-12">
        <div class="col-main custom-class-rec1146">
			<div  style="padding-top: 14px;">
				<!---#### Remove by Sai Krishna Daddala to make onLoad Location on 08/11/2016
				<div class="col-lg-3 col-md-3 col-sm-6 col-xs-12">
					<div class="form-group">
					  <label for="product_state_products_page">State *:</label>
					  <select class="form-control yes product_state" id="product_state_products_page">
						<option value="0">Please wait, States Loading...</option>
					  </select>
					  <div id='product_state_products_page-error'style="display:none;"></div>
					</div>
				</div>
				<div class="col-lg-3 col-md-3 col-sm-6 col-xs-12">
					<div class="form-group">
					  <label for="product_city_products_page">City *:</label>
					  <select class="form-control yes product_city" id="product_city_products_page" onchange="filter_product()">
						<option value="0">Please wait, Cities Loading...</option>
					  </select>
					  <div id='product_city_products_page-error'style="display:none;"></div>
					</div>
				</div>  ------>
				<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12" style="font-size: 16px;color: #e02d29;" >
					<span class="blink_me"> <span class="icon-bell" aria-hidden="true"></span>	&nbsp;&nbsp; Below Prices are for <strong><%=City%></strong>, <strong><%=State%></strong>.</span> <span style=" font-size: 13px;font-weight: 700;"><a href="javascript:AskLocationDetails()" style="text-decoration: none;"> Click here to change Location.</a></span>
				</div>
			</div>
		</div>
        <div class="col-main">
          <div class="category-title">
            <h1> <%=Product_Type%> - <%=Product_Brand%></h1>
          </div>
		   <div class="category-products">
	         <div class="toolbar">
              <div class="sorter hidden-xs hidden-sm">
                <div class="view-mode"> 
					<span title="Grid" class="button button-active button-grid">Grid</span>
				</div>
              </div>
              <div class="hidden-lg hidden-md">
                 <button class="button" style="background-color: #ffffff /*{a-bup-background-color}; */;border-color: #222 /*{a-bup-border}*/;text-shadow: none;border-radius: 0px;" id="filter" title="Filter" type="button"><span>Filter</span> <i class="glyphicon glyphicon-filter"></i></button>
               </div>
			   <div id="sort-by">
                <label class="left"><b>Sort By Price:</b></label>
				<div>
				  <select class="form-control" id="price_range" onChange="javascript:UpdateProduct_Filter()">
					<option value="ASC"<%if(Product_PriceType.equals("ASC")){%> selected <%}%>>Low To High</option>
					<option value="DESC" <%if(Product_PriceType.equals("DESC")){%> selected <%}%>>High to Low</option>
				  </select>
				</div>
				</div>
              <div class="pager hide">
                <div id="limiter">
                  <label>View: </label>
                  <ul>
                    <li><a href="#">15<span class="right-arrow"></span></a>
                      <ul>
                        <li><a href="#">20</a></li>
                        <li><a href="#">30</a></li>
                        <li><a href="#">35</a></li>
                      </ul>
                    </li>
                  </ul>
                </div>
              </div>
            </div>
            <ul class="products-grid">
			<%
				if(Vector_Inverter!=null && Vector_Inverter.size() > 0)
				{
					try{
						for ( int i=0; i<Vector_Inverter.size() ; i++){
							
							Hashtable ht=(Hashtable)Vector_Inverter.get(i);
							
							String inverter_brand=String.valueOf(ht.get("inverter_brand"));
							String inverter_capacity=(String)ht.get("inverter_capacity");
							String icon_url=(String)ht.get("icon_url");
							String inverter_warranty=(String)ht.get("inverter_warranty");
							String inverter_model=String.valueOf(ht.get("inverter_model"));
							String inverter_discount_price=String.valueOf(ht.get("inverter_discount_price"));
							String inverter_actual_price=String.valueOf(ht.get("inverter_actual_price"));
							
							
							String Computer=String.valueOf(ht.get("computer"));
							String Tubelights=String.valueOf(ht.get("tubelights"));
							String Fans=String.valueOf(ht.get("fans"));
							String Television=String.valueOf(ht.get("television"));
													
							if(icon_url == null)
							{
								icon_url = "./images/noimage.jpg";
							}
							
							String inverter_brand_URL=inverter_brand;
							String inverter_model_URL=inverter_model;
							
							inverter_brand_URL= inverter_brand_URL.replaceAll(" ", "+");
							inverter_model_URL= inverter_model_URL.replaceAll(" ", "+");
							
							Computer= Computer.replaceAll(",", "</td><td>");
							Tubelights= Tubelights.replaceAll(",", "</td><td>");
							Fans= Fans.replaceAll(",", "</td><td>");
							Television= Television.replaceAll(",", "</td><td>");
							
							int Inverter_Discount_Percent=(((Integer.parseInt(inverter_actual_price)-Integer.parseInt(inverter_discount_price))*100)/Integer.parseInt(inverter_actual_price));


			%>
		       <li class="item col-lg-4 col-md-4 col-sm-6 col-xs-12" style="">
                  <div class="item-inner">
                    <div class="item-img">
                      <div class="product-block">
                        <div class="product-image"> <a href="<%=baseURL%>/bookbattery/product-inverters/<%=inverter_brand_URL%>/<%=inverter_model_URL%>/<%=State_URL%>/<%=City_URL%>/" style="height: auto;">
                          <figure class="product-display">
						   <div class="new-label new-top-left"><%=Inverter_Discount_Percent%>%&nbsp;Off </div>
                            <img src="<%=icon_url%>" class="lazyOwl product-mainpic" alt=" <%=inverter_brand%> <%=inverter_model%> <%=State_URL%> <%=City_URL%>" style="display: block;"> <img class="product-secondpic" alt=" <%=inverter_brand%> <%=inverter_model%> <%=State_URL%> <%=City_URL%>" src="<%=icon_url%>"> </figure>
                          </a> </div>
                        <div class="product-meta">
                          <div class="product-action"> <a class="addcart" href="<%=baseURL%>/bookbattery/product-inverters/<%=inverter_brand_URL%>/<%=inverter_model_URL%>/<%=State_URL%>/<%=City_URL%>/" > <i class="icon-external-link-sign">&nbsp;</i> View More </a>
                        </div>
                      </div>
                    </div>
                    <div class="item-info" style="text-align: left;">
                      <div class="info-inner">
                        <div class="item-title"> <a href="<%=baseURL%>/bookbattery/product-inverters/<%=inverter_brand_URL%>/<%=inverter_model_URL%>/<%=State_URL%>/<%=City_URL%>/" title="Click to View "> <%=inverter_brand%> <%=inverter_model%> ( <%=inverter_capacity%> ) </a> </div>
                        <div class="item-content">
                          <div class="item-price">
                            <div class="price-box">
                              <div class="old-price"> <span class="price-label" style="display: block;">Regular Price: <span class="price" ><i class="fa fa-inr" aria-hidden="true"></i> <%=inverter_actual_price%> </span> </span></div>
							   <span class="price-label" style="display: block;">Warranty: <span  > <%=inverter_warranty%> </span> </span>
							   <span class="price-label" style="display: block;">Capacity: <span  > <%=inverter_capacity%> </span> </span>
							   <div class="table-responsive"> 
									<table class="table table-bordered table_icons_usage"> 
										 <tbody> 
											 <tr>
												<td><b>Computer</b></td>
												<td><%=Computer%></td>
												<td>250W</td>
											 </tr> 
											 <tr>
												<td><b>Fans</b></td>
												<td><%=Fans%></td>
												<td>80W</td>
											 </tr> 
											 <tr>
												<td><b>Tube lights</b></td>
												<td><%=Tubelights%></td>
												<td>40W</td>
											 </tr> 
											 <tr>
												<td><b>TV (32&#8243;)</b></td>
												<td><%=Television%></td>
												<td>180W</td>
											 </tr> 
										 </tbody> 
									</table>
								</div>
							   <table style=" color: #494848;font-size: 15px;">
									  <tr>
										  <th>Discount Price</th>
										  <th>: <i class="fa fa-inr" aria-hidden="true"></i> <%=inverter_discount_price%> </th>
									  </tr>
								</table> 
							<a class="button btn" style="margin-top: 5px;padding: 5px;" href="<%=baseURL%>/bookbattery/product-inverters/<%=inverter_brand_URL%>/<%=inverter_model_URL%>/<%=State_URL%>/<%=City_URL%>/" ><i class="icon-external-link-sign">&nbsp;</i> View More</a>	
                            </div>
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                  </div>
                </li>
				
				<%
						}
					}
					catch(Exception e)
					{
							e.printStackTrace();
					}
				}
				%>
		
            </ul>
          </div>
		  
		 
			
		   </div>
        </section>
       
	   
	   
	  <aside class="col-left sidebar col-md-3 col-xs-12  wow bounceInUp animated">
	  <%
	  if(Vector_Inverter_Brands!=null && Vector_Inverter_Brands.size() > 0)
		{
		try{
	  %>
			<div class="block block-poll">
				<div class="block-title">Battery Brand</div>
				<form onSubmit="return validatePollAnswerIsSelected();" method="post" action="#" id="pollForm">
				  <div class="block-content">
					<p class="block-subtitle">What is your favorite <%=Product_Type%> Brand?</p>
					<ul id="poll-answers">
				<%
					for ( int i=0; i<Vector_Inverter_Brands.size() ; i++){
						Hashtable ht=(Hashtable)Vector_Inverter_Brands.get(i); 
						String Brand_Names=String.valueOf(ht.get("inverter_brand"));
				%>
					  <li class="odd">
						<input type="checkbox" id="Filter_Brand_Names" class="radio poll_vote Filter_Brand_Names" name="Filter_Brand_Names" value="<%=Brand_Names%>">
						<span class="label">
						<label for="<%=Brand_Names%>"><%=Brand_Names%></label>
						</span> </li>
				<%
					}
				%>
					</ul>
					<div class="actions">
					<span style="float: right;"> <a href="javascript:clear_all_filter('Filter_Brand_Names')"> Clear All</a></span>
					</br>
					  <button class="button " title="Filter By Brand" type="button" onClick="javascript:UpdateProduct_Filter();"  ><span>Filter By Brand</span></button>
					</div>
				  </div>
				</form>
			  </div>
		<%
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		%>	  
		

	  <%
	  if(Vector_Inverter_Capacity!=null && Vector_Inverter_Capacity.size() > 0)
		{
		try{
	  %>
			<div class="block block-poll">
				<div class="block-title">Inverter Capacity</div>
				  <div class="block-content">
					<p class="block-subtitle">Select Your Required Capacity?</p>
					<ul id="poll-answers">
				<%
					for ( int i=0; i<Vector_Inverter_Capacity.size() ; i++){
						Hashtable ht=(Hashtable)Vector_Inverter_Capacity.get(i);
						String Inverter_Capacity=String.valueOf(ht.get("inverter_capacity"));
				%>
					  <li class="odd">
						<input type="checkbox"  value="<%=Inverter_Capacity%>"  id="Filter_Inverter_Capacity" class="radio poll_vote Filter_Inverter_Capacity" name="Filter_Inverter_Capacity">
						<span class="label">
						<label for="<%=Inverter_Capacity%>"><%=Inverter_Capacity%></label>
						</span> </li>
				<%
					}
				%>
					</ul>
					<div class="actions">
					<span style="float: right;"> <a href="javascript:clear_all_filter('Filter_Inverter_Capacity')"> Clear All</a></span>
					</br>
					  <button class="button" title="Filter By Inverter Capacity" type="button" onClick="javascript:UpdateProduct_Filter();" ><span>Filter By Inverter Capacity</span></button>
					</div>
				  </div>
			  </div>
		<%
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		%>	 		

	  <%
	  if(Vector_Inverter_Name!=null && Vector_Inverter_Name.size() > 0)
		{
		try{
	  %>
			<div class="block block-poll">
				<div class="block-title">Inverter Output Type</div>
				  <div class="block-content">
					<p class="block-subtitle">Select Your Inverter Type?</p>
					<ul id="poll-answers">
				<%
					for ( int i=0; i<Vector_Inverter_Name.size() ; i++){
						Hashtable ht=(Hashtable)Vector_Inverter_Name.get(i);
						String Inverter_Name=String.valueOf(ht.get("inverter_name"));
				%>
					  <li class="odd">
						<input type="checkbox"  value="<%=Inverter_Name%>"  id="Filter_Inverter_Name" class="radio poll_vote Filter_Inverter_Name" name="Filter_Inverter_Name">
						<span class="label">
						<label for="<%=Inverter_Name%>"><%=Inverter_Name%></label>
						</span> </li>
				<%
					}
				%>
					</ul>
					<div class="actions">
					<span style="float: right;"> <a href="javascript:clear_all_filter('Filter_Inverter_Name')"> Clear All</a></span>
					</br>
					  <button class="button" title="Filter By Inverter Type" type="button" onClick="javascript:UpdateProduct_Filter();" ><span>Filter By Inverter Type</span></button>
					</div>
				  </div>
			  </div>
		<%
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		%>	 
		

			<!--<div class="block block-poll">
				<div class="block-title">Battery Price Range</div>
				<form onSubmit="return validatePollAnswerIsSelected();" method="post" action="#" id="pollForm">
				  <div class="block-content">
					<p class="block-subtitle">Select Your Price Range?</p>
					<div class="col-xs-12">
					<div class="col-xs-6"><input style="color:black" type="text" value=""  class="js-input-from form-control Filter_Battery_Price_Range"  name="Filter_Battery_Price_Range"/></div>
					<div class="col-xs-6"><input style="color:black" type="text"  value=""  class="js-input-to form-control Filter_Battery_Price_Range"  name="Filter_Battery_Price_Range"/></div>
					</div>
					 <hr>
					<div class="range-slider" style="margin-top: 14%;">
					<input type="text" class="js-range-slider" value="" />
					</div>
					<div class="actions">
					<span style="float: right;margin-top: 5%;"> <a href="javascript:clear_all_filter('Filter_Battery_Price_Range')"> Clear All</a></span>
					</br>
					   <button class="button " title="Filter By Price Range" type="button" onClick="javascript:UpdateProduct_Filter();"  ><span>Filter By Price Range</span></button>
					</div>
				  </div>
				</form>
			  </div>-->
		 
        </aside>
      
     

	   </div>
    </div>
  </div>
  <!-- End Two columns content --> 
  </form>
  </br>
<!---################################## CSS Include Starts  ################################------>
	<jsp:include page = "/assets/includes/includes_js.jsp" />
<!---################################## CSS Include Ends  ################################------>

<!---################################## CSS Include Starts  ################################------>
	<jsp:include page = "/assets/includes/includes_footer.jsp" />
<!---################################## CSS Include Ends  ################################------>

<link rel="stylesheet" href="/bookbattery/dev/includes/js/priceslider/addSlider.min.css" type="text/css">
<script type="text/javascript" src="/bookbattery/dev/includes/js/priceslider/addSlider.min.js"></script>
<script type="text/javascript" src="/bookbattery/dev/includes/js/priceslider/Obj.min.js"></script>
<script>
$(document).ready(function() {
	if (screen.width <= 767)
	{
	 $('html, body').animate({
			scrollTop: $("#Page_Result_ID").offset().top
		},500);
	}
});
</script>
  <script>
    function betterParseFloat(x){
      if(isNaN(parseFloat(x)) && x.length > 0)
        return betterParseFloat(x.substr(1));
      return parseFloat(x);
    }
    function usd(x){
      x = betterParseFloat(x);
      if(isNaN(x))
        return "$0.00";
      var dollars = Math.floor(x);
      var cents = Math.round((x - dollars) * 100) + "";
      if(cents.length==1)cents = "0"+cents;
      return "$"+dollars+"."+cents;
    }
  </script>
  
 
<input type="hidden" name="publicUrl" id='publicUrl' value="<%=publicUrl%>">
<input type="hidden" name="baseURL" id='baseURL' value="<%=baseURL%>">
<input type="hidden" name="brandkeyword" id='brandkeyword' value="Common">
<input type="hidden" name="backkeyword" id='backkeyword' value="index">

<input type="hidden" name="product_type_page" id='product_type_page' value="<%=Product_Type%>">
<input type="hidden" name="product_make_page" id='product_make_page' value="">
<input type="hidden" name="product_capacity_page" id='product_capacity_page' value="">
<input type="hidden" name="product_model_page" id='product_model_page' value="">
<input type="hidden" name="product_brand_page" id='product_brand_page' value="<%=Product_Brand%>">
<input type="hidden" name="Inverter_Filter_Capacity" id='Inverter_Filter_Capacity' value="<%=Inverter_Filter_Capacity%>">
<input type="hidden" name="Inverter_Filter_Name" id='Inverter_Filter_Name' value="<%=Inverter_Filter_Name%>">
<input type="hidden" name="product_state_page" id='product_state_page' value="<%=State%>">
<input type="hidden" name="product_city_page" id='product_city_page' value="<%=City%>">

  <script> 
  
  $('#filter').click(function(){ 
	 // $("#filter_container").animate({ "left": "+=30px" }, "slow" );
	  //$("#filter_container").slideUp(500);
	  $("#filter_container").show(250);
	  //$('body').css('overflow', 'hidden';);
	  $('body').css({"overflow": "hidden", "background": "rgb(238, 238, 238)"});
	});
  
  $(document).ready(function()
	{
		var product_brand_page = $("#product_brand_page").val();
		
		if(product_brand_page=="All")
		{
			$('.Filter_Brand_Names').prop('checked', true);
			$('.Filter_Brand_Names_pop').prop('checked', true);
		}
		else if(product_brand_page.indexOf(",")<=0)
		{
			$("input[name='Filter_Brand_Names_pop'][value='"+product_brand_page+"']").prop('checked', true);
			$("input[name='Filter_Brand_Names'][value='"+product_brand_page+"']").prop('checked', true);
		}
		else{
			var product_brand_page_array = product_brand_page.split(',');
			for(var i = 0; i < product_brand_page_array.length; i++) 
			{
			   $("input[name='Filter_Brand_Names_pop'][value='"+product_brand_page_array[i]+"']").prop('checked', true);
			   $("input[name='Filter_Brand_Names'][value='"+product_brand_page_array[i]+"']").prop('checked', true);
			}
		}
	})
  $(document).ready(function()
	{
		var Inverter_Filter_Capacity = $("#Inverter_Filter_Capacity").val();
		if(Inverter_Filter_Capacity.indexOf(",")<=0)
		{
			$("input[name='Filter_Inverter_Capacity_pop'][value='"+Inverter_Filter_Capacity+"']").prop('checked', true);
			$("input[name='Filter_Inverter_Capacity'][value='"+Inverter_Filter_Capacity+"']").prop('checked', true);
		}
		else{
			var Inverter_Filter_Capacity_array = Inverter_Filter_Capacity.split(',');
			for(var i = 0; i < Inverter_Filter_Capacity_array.length; i++) 
			{
			   $("input[name='Filter_Inverter_Capacity_pop'][value='"+Inverter_Filter_Capacity_array[i]+"']").prop('checked', true);
			   $("input[name='Filter_Inverter_Capacity'][value='"+Inverter_Filter_Capacity_array[i]+"']").prop('checked', true);
			}
		}
	})
  $(document).ready(function()
	{
		var Inverter_Filter_Name = $("#Inverter_Filter_Name").val();
		if(Inverter_Filter_Name.indexOf(",")<=0)
		{
			$("input[name='Filter_Inverter_Name'][value='"+Inverter_Filter_Name+"']").prop('checked', true);
			$("input[name='Filter_Inverter_Name_pop'][value='"+Inverter_Filter_Name+"']").prop('checked', true);
		}
		else{
			var Inverter_Filter_Name_array = Inverter_Filter_Name.split(',');
			for(var i = 0; i < Inverter_Filter_Name_array.length; i++) 
			{
			   $("input[name='Filter_Inverter_Name_pop'][value='"+Inverter_Filter_Name_array[i]+"']").prop('checked', true);
			}
		}
	})
  </script>
  
  <script> 
	  function UpdateProduct_Filter(){ 
		var State = $("#product_state").val(); 
		var City = $("#product_city").val();
		var Price_range = $("#price_range").val();
		
		var ctime=365*24*60*60*1000;
		if (selectValidation("","product_state","select") == false)
		{
			return;
		}
		else
		{
			setCookie("product_state_cookie", State.replace(/ /g, "-"),ctime)
		}	
		if (selectValidation("","product_city","select") == false)
		{
			return;
		}
		else
		{
			setCookie("product_city_cookie", City.replace(/ /g, "-"),ctime)
		}
		
		var Filter_Brand_Names_Array = [];
		$.each($("input[name='Filter_Brand_Names']:checked"), function(){            
			Filter_Brand_Names_Array.push($(this).val());
		});
		var Filter_Brand_Names=Filter_Brand_Names_Array.join(",");
		
		var Filter_Inverter_Capacity_Array = [];
		$.each($("input[name='Filter_Inverter_Capacity']:checked"), function(){            
			Filter_Inverter_Capacity_Array.push($(this).val());
		});
		var Filter_Inverter_Capacity=Filter_Inverter_Capacity_Array.join(",");
		
		var Filter_Inverter_Name_Array = [];
		$.each($("input[name='Filter_Inverter_Name']:checked"), function(){            
			Filter_Inverter_Name_Array.push($(this).val());
		});
		var Filter_Inverter_Name=Filter_Inverter_Name_Array.join(",");
		
		var Filter_Battery_Price_Range_Array = [];
		$.each($("input[name='Filter_Battery_Price_Range']"), function(){
			//alert($(this).val());
			Filter_Battery_Price_Range_Array.push($(this).val());
		});
		Filter_Battery_Price_Range=Filter_Battery_Price_Range_Array.join(",");
		
		var publicUrl = $("#publicUrl").val();
		var Filter_Brand_Names_URL;
		var Filter_Inverter_Capacity_URL;
		var Filter_Inverter_Name_URL;
		
		
		if(Filter_Brand_Names=="" || Filter_Brand_Names=="undefined")
		{
			Filter_Brand_Names_URL="All"
		}
		else{
			Filter_Brand_Names_URL=Filter_Brand_Names;
		}
		
		if(Filter_Inverter_Capacity=="" || Filter_Inverter_Capacity=="undefined")
		{
			Filter_Inverter_Capacity_URL="Capacity"
		}
		else{
			Filter_Inverter_Capacity_URL="Capacity="+Filter_Inverter_Capacity+"";
		}
		
		if(Filter_Inverter_Name=="" || Filter_Inverter_Name=="undefined")
		{
			Filter_Inverter_Name_URL="OutputType"
		}
		else{
			Filter_Inverter_Name_URL="OutputType="+Filter_Inverter_Name+"";
		}
		
		if(Filter_Battery_Price_Range=="" || Filter_Battery_Price_Range=="undefined")
		{
			Filter_Battery_Price_URL="Price/";
		}
		else{
			Filter_Battery_Price_URL="Price="+Filter_Battery_Price_Range+"/";
		}
		
		Filter_Brand_Names_URL= Filter_Brand_Names_URL.replace(" ", "+");
		Filter_Brand_Names_URL= Filter_Brand_Names_URL.replace(/ /g, "+");
		
		State= State.replace(" ", "+");
		State= State.replace(/ /g, "+");
		
		City= City.replace(" ", "+");
		City= City.replace(/ /g, "+");
		
		Price_range= Price_range.replace(" ", "+");
		Price_range= Price_range.replace(/ /g, "+");
		
		Filter_Inverter_Capacity_URL= Filter_Inverter_Capacity_URL.replace(" ", "+");
		Filter_Inverter_Capacity_URL= Filter_Inverter_Capacity_URL.replace(/ /g, "+");
		
		Filter_Inverter_Name_URL= Filter_Inverter_Name_URL.replace(" ", "+");
		Filter_Inverter_Name_URL= Filter_Inverter_Name_URL.replace(/ /g, "+");
		
		window.location.href = publicUrl+'/bookbattery/Inverter/'+Filter_Brand_Names_URL+'/'+State+'/'+City+'/'+Filter_Inverter_Capacity_URL+'/'+Filter_Inverter_Name_URL+'/'+Price_range+'/';
	  }
	  
	  function clear_all_filter(clear_class)
	  {
		  //alert(clear_class);
		  if(clear_class=="Filter_Battery_Price_Range")
		  {
			  $('.js-input-from').val("0");
			  $('.js-input-to').val("100000");
			  $('.from').css("left","0%");
			  $('.to').css("right","0%");
			  $('.to').css("left","92.6829%");
			  $('.irs-to').html("100000");
			  $('.irs-to').css("left","92.6829%");
			  $('.irs-bar').css("width","92.6829%");
		  }
		  else
		  {
			   $('.'+clear_class+'').prop('checked', false);
		  }
	  }	 

	  function UpdateProduct_Filter_Pop_UP(){ 
		var State = $("#product_state").val(); 
		var City = $("#product_city").val();
		var Price_range = $("#price_range").val();
		var ctime=365*24*60*60*1000;
		if (selectValidation("","product_state","select") == false)
		{
			return;
		}
		else
		{
			setCookie("product_state_cookie", State,ctime)
		}	
		if (selectValidation("","product_city","select") == false)
		{
			return;
		}
		else
		{
			setCookie("product_city_cookie", City,ctime)
		}
		
		var Filter_Brand_Names_Array = [];
		$.each($("input[name='Filter_Brand_Names_pop']:checked"), function(){            
			Filter_Brand_Names_Array.push($(this).val());
		});
		var Filter_Brand_Names=Filter_Brand_Names_Array.join(",");
		
		var Filter_Inverter_Capacity_Array = [];
		$.each($("input[name='Filter_Inverter_Capacity_pop']:checked"), function(){            
			Filter_Inverter_Capacity_Array.push($(this).val());
		});
		var Filter_Inverter_Capacity=Filter_Inverter_Capacity_Array.join(",");
		
		var Filter_Inverter_Name_Array = [];
		$.each($("input[name='Filter_Inverter_Name_pop']:checked"), function(){            
			Filter_Inverter_Name_Array.push($(this).val());
		});
		var Filter_Inverter_Name=Filter_Inverter_Name_Array.join(",");
		
		var Filter_Battery_Price_Range_Array = [];
		$.each($("input[name='Filter_Battery_Price_Range_pop']"), function(){
			//alert($(this).val());
			Filter_Battery_Price_Range_Array.push($(this).val());
		});
		Filter_Battery_Price_Range=Filter_Battery_Price_Range_Array.join(",");
		
		var publicUrl = $("#publicUrl").val();
		var Filter_Brand_Names_URL;
		var Filter_Inverter_Capacity_URL;
		var Filter_Inverter_Name_URL;
		
		
		if(Filter_Brand_Names=="" || Filter_Brand_Names=="undefined")
		{
			Filter_Brand_Names_URL="All"
		}
		else{
			Filter_Brand_Names_URL=Filter_Brand_Names;
		}
		
		if(Filter_Inverter_Capacity=="" || Filter_Inverter_Capacity=="undefined")
		{
			Filter_Inverter_Capacity_URL="Capacity"
		}
		else{
			Filter_Inverter_Capacity_URL="Capacity="+Filter_Inverter_Capacity+"";
		}
		
		if(Filter_Inverter_Name=="" || Filter_Inverter_Name=="undefined")
		{
			Filter_Inverter_Name_URL="OutputType"
		}
		else{
			Filter_Inverter_Name_URL="OutputType="+Filter_Inverter_Name+"";
		}
		
		if(Filter_Battery_Price_Range=="" || Filter_Battery_Price_Range=="undefined")
		{
			Filter_Battery_Price_URL="Price/";
		}
		else{
			Filter_Battery_Price_URL="Price="+Filter_Battery_Price_Range+"/";
		}
		
		Filter_Brand_Names_URL= Filter_Brand_Names_URL.replace(" ", "+");
		Filter_Brand_Names_URL= Filter_Brand_Names_URL.replace(/ /g, "+");
		
		State= State.replace(" ", "+");
		State= State.replace(/ /g, "+");
		
		City= City.replace(" ", "+");
		City= City.replace(/ /g, "+");
		
		Price_range= Price_range.replace(" ", "+");
		Price_range= Price_range.replace(/ /g, "+");
		
		Filter_Inverter_Capacity_URL= Filter_Inverter_Capacity_URL.replace(" ", "+");
		Filter_Inverter_Capacity_URL= Filter_Inverter_Capacity_URL.replace(/ /g, "+");
		
		Filter_Inverter_Name_URL= Filter_Inverter_Name_URL.replace(" ", "+");
		Filter_Inverter_Name_URL= Filter_Inverter_Name_URL.replace(/ /g, "+");
		
		window.location.href = publicUrl+'/bookbattery/Inverter/'+Filter_Brand_Names_URL+'/'+State+'/'+City+'/'+Filter_Inverter_Capacity_URL+'/'+Filter_Inverter_Name_URL+'/'+Price_range+'/';
	  }
	  
	  function clear_all_filter(clear_class)
	  {
		  //alert(clear_class);
		  if(clear_class=="Filter_Battery_Price_Range")
		  {
			  $('.js-input-from').val("0");
			  $('.js-input-to').val("100000");
			  $('.from').css("left","0%");
			  $('.to').css("right","0%");
			  $('.to').css("left","92.6829%");
			  $('.irs-to').html("100000");
			  $('.irs-to').css("left","92.6829%");
			  $('.irs-bar').css("width","92.6829%");
		  }
		  else
		  {
			   $('.'+clear_class+'').prop('checked', false);
		  }
	  }
  </script>

  
<script> 
	$(document).ready(function()
	{
		if (screen.width <= 767)
		{
			$("body").append( "<div class='btn Buy_Now_Mobile hidden-sm hidden-md hidden-lg' id='Buy_Now_Mobile'><div style='font-size: 3em;margin-top: -27px;color: #e02d29;cursor: pointer;'><span class='icon-chevron-sign-down arrow_down_button' onClick='javascript:Scroll_to_next()'></span></div></div>" );
		}
		

		$(window).scroll(function() {
			var total_height=$(document).height();
			var current_height=$(document).scrollTop();
			var window_height=screen.height-100;
			if(total_height-(current_height+window_height+window_height)<=window_height)
			{
				$( "#Buy_Now_Mobile" ).fadeOut("slow");
			}
			else
			{
				$( "#Buy_Now_Mobile" ).fadeIn("slow");
			}
		});
	})
	
	function Scroll_to_next()
	{
		var total_height=$(document).height();
		var current_height=$(document).scrollTop();
		var window_height=screen.height-100;
		var goHeight=current_height+window_height;
		$('html, body').animate({scrollTop:goHeight}, 'slow');
	}

	function closefilter()
	{
		//alert(22);
		$("#filter_container").hide(250);
		 $('body').css({ 'background-color' : '', 'overflow' : '' });
	}	
	
 </script>
  

</body>
</html>