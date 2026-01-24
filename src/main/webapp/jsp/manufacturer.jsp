<%-- 
Document   		 : manufacturer_list_display.jsp
Created on 		 : Sep 15, 2016, 10:14:12 AM
Author     		 : BookBattery
Copyright Notice : BookBattery Confidential.
Document         : This jsp is used show List all Manifactures product page of BookBattery Batteries.
--%>
<%@ page language="java" import="java.sql.*"%>
<%@page language="java" import="java.io.File,java.text.*,java.util.Calendar,java.util.ArrayList,java.util.Hashtable,java.util.Vector,com.ngit.javabean.loglevel.*,java.util.Date,java.util.Properties,java.util.Arrays,com.ngit.javabean.loglevel.LogLevel,com.ngit.javabean.consumers.products.GetCookie,com.ngit.javabean.qrymgr.QueryManager,javax.servlet.ServletContext,java.util.Properties,java.io.FileInputStream"%>
<%response.setHeader("Pragma","no-cache"); response.setHeader("Cache-Control","no-store"); response.setHeader("Expires","0"); response.setDateHeader("Expires",-1); %>
<%
ServletContext context = getServletContext();
Properties propsMOPConfig = new Properties(); 
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

QueryManager qm;
qm = QueryManager.getInstance(propsMOPConfig) ;
//################### Getting Location From Cookies 
GetCookie State_City = new GetCookie(qm);
String State_City_Resp=  State_City.getCookieStateCity(request,response,session);
LogLevel.DEBUG(5, new Throwable(), "State_City_Resp :" + State_City_Resp);

String[] State_City_Arr=State_City_Resp.split("~");
String Product_State_Cookie=State_City_Arr[0];
String Product_City_Cookie=State_City_Arr[1];
LogLevel.DEBUG(5, new Throwable(), "State_City_Resp :" + State_City_Resp);
//################### Getting Location From Cookies 
String Product_State_Cookie_URL= Product_State_Cookie.replaceAll(" ", "-");
String Product_City_Cookie_URL= Product_City_Cookie.replaceAll(" ", "-");
%>


<%
Vector Vector_Vehicle_Names=(Vector)request.getAttribute("Vector_Vehicle_Names");
String Products_of_Manufacturer = (String) request.getAttribute("Products_of_Manufacturer");
String Name_of_Manufacturer = (String) request.getAttribute("Name_of_Manufacturer");
String Type_of_Manufacturer = (String) request.getAttribute("Type_of_Manufacturer");
String Brand = (String) request.getAttribute("Brand");
LogLevel.DEBUG(5, new Throwable(), "Brand :" + Brand);
//out.println(Brand);
String Products_of_Manufacturer_List = (String) request.getAttribute("Products_of_Manufacturer_List");
String FuelType = (String) request.getAttribute("fuel_type");
LogLevel.DEBUG(5, new Throwable(), "FuelType in JSP:" + FuelType);
%>
<%

String Products_of_Manufacturer_URL= Products_of_Manufacturer.replaceAll(" ", "-");
String Name_of_Manufacturer_URL= Name_of_Manufacturer.replaceAll(" ", "-");
String Type_of_Manufacturer_URL= Type_of_Manufacturer.replaceAll(" ", "-");

String Type_of_Manufacturer_Lowercase = Type_of_Manufacturer_URL.toLowerCase();

Properties ManufacturerDetails = new Properties(); 
FileInputStream ManufacturerDetails_file = 
new FileInputStream(new File(context.getRealPath("dev/includes/content-pages/"+Type_of_Manufacturer_Lowercase+"-manufacturer-content.txt"))); 
ManufacturerDetails.load(ManufacturerDetails_file); 
ManufacturerDetails_file.close(); 

titles=Name_of_Manufacturer+" | Buy "+Name_of_Manufacturer+" "+Type_of_Manufacturer+" Online at Best Price in India | BookBattery.com";
description="Buy 100% genuine "+Name_of_Manufacturer+" "+Type_of_Manufacturer+" from models like "+Products_of_Manufacturer_List+" Online - Free Shipping, Cash on delivery & Free Home Delivery at BookBattery.com";

%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html class="no-js" xmlns="http://www.w3.org/1999/xhtml">
<head itemscope itemtype="http://schema.org/WebSite">
<title itemprop='name'><%=titles%></title>
<meta charset="utf-8">
<meta http-equiv="x-ua-compatible" content="ie=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="canonical" href="<%=baseURL%><%=baseurldirectory%>manufacturer/<%=Type_of_Manufacturer_URL%>/<%=Name_of_Manufacturer_URL%>/<%=FuelType%>/" itemprop="url">
<meta name="og_title" property="og:title" content="<%=titles%>"/>
<meta name="og_site_name" property="og:site_name" content="BookBattery.com"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="shortcut icon" href="<%=publicUrl%>/bookbattery/images/favicon.png" type="image/x-icon">
<meta name='Description' content="<%=description%>"/>
<meta name='Keywords' content='<%=Type_of_Manufacturer%>,<%=Products_of_Manufacturer_List%>'/>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<META HTTP-EQUIV="Expires" CONTENT="0"/>
<META HTTP-EQUIV="Pragma" CONTENT="no-cache"/>
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache"/>
<META HTTP-EQUIV="Cache-Control" CONTENT="no-store"/>

<!---################################## CSS Include Starts  ################################------>
	<jsp:include page = "/assets/includes/includes_css.jsp" />
<!---################################## CSS Include Ends  ################################------>
</head>
<body>
<!---################################## Header Include Starts  ################################------>
	<jsp:include page = "/assets/includes/includes_header.jsp" />
<!---################################## Header Include Ends  ################################------>
<form name="batterydet" autocomplete="off">
  <!-- breadcrumbs -->
  <div class="breadcrumbs">
    <div class="container">
      <div class="row">
        <ul>
          <li class="home"> <a href="<%=baseURL%><%=baseurldirectory%>" title="Go to Home Page">Home</a><span>&raquo;</span></li>
          <!--<li class=""> <a title="Go to Manufacturer" href="<%=baseURL%><%=baseurldirectory%>manufacturer/">Manufacturer</a><span>&raquo;</span></li>-->
          <li class=""> <a title="Go to <%=Type_of_Manufacturer_URL%>" href="<%=baseURL%><%=baseurldirectory%>manufacturers/<%=Type_of_Manufacturer_URL%>/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/"><%=Type_of_Manufacturer%></a><span>&raquo;</span></li>
          <li class="category13"><strong><%=Name_of_Manufacturer%></strong></li>
        </ul>
      </div>
    </div>
  </div>
	<ol class="hide" itemscope itemtype="http://schema.org/BreadcrumbList">
	  <li itemprop="itemListElement" itemscope itemtype="http://schema.org/ListItem">
		<a itemscope itemtype="http://schema.org/Thing" itemprop="item" href="<%=baseURL%><%=baseurldirectory%>" id="<%=baseURL%><%=baseurldirectory%>">
		  <span itemprop="name">BookBattery</span>
		</a>
		<meta itemprop="position" content="1" />
	  </li>
	  <li itemprop="itemListElement" itemscope itemtype="http://schema.org/ListItem">
		<a itemscope itemtype="http://schema.org/Thing" itemprop="item" href="<%=baseURL%><%=baseurldirectory%>manufacturer/<%=Type_of_Manufacturer_URL%>/" id="<%=baseURL%><%=baseurldirectory%>manufacturer/<%=Type_of_Manufacturer_URL%>/">
		  <span itemprop="name"><%=Type_of_Manufacturer%></span>
		</a>
		<meta itemprop="position" content="2" />
	  </li>
	  <li itemprop="itemListElement" itemscope itemtype="http://schema.org/ListItem">
		<a itemscope itemtype="http://schema.org/Thing" itemprop="item" href="<%=baseURL%><%=baseurldirectory%>manufacturer/<%=Type_of_Manufacturer_URL%>/<%=Name_of_Manufacturer_URL%>/" id="<%=baseURL%><%=baseurldirectory%>manufacturer/<%=Type_of_Manufacturer_URL%>/<%=Name_of_Manufacturer_URL%>/">
		  <span itemprop="name"><%=Name_of_Manufacturer%></span>
		</a>
		<meta itemprop="position" content="3" />
	  </li>
	</ol>
  <!-- End breadcrumbs --> 
  <!-- Two columns content -->
  <div class="main-container col2-left-layout">
    <div class="main container">
      <div class="row">
      <section class="col-md-9 col-xs-12 " style=" z-index: 2;"  id="Page_Result_ID">
			<!--- <div class="col-main"> -->
		<div class="category-title">
			<div  class="col-md-6 col-sm-6 col-xs-12" style=" padding: 0px;">
				<h1 class="font_service_type"> <%=Name_of_Manufacturer%> Models</h1>
				<h6><b>Select Vehicle Model</b></h6>
				
			</div>
			<div  class="col-md-6 col-sm-6 hidden-xs" style=" padding: 0px;">
				<img src="<%=publicUrl%><%=ManufacturerDetails.getProperty(Name_of_Manufacturer_URL+"-logo-path")%>" alt="<%=Name_of_Manufacturer%> Company Logo" style="float: right;display: block; height: 60px;">
			</div>
		</div>
		<div class="block block-list block-viewed-2-custom">
			<div class='block-content'>
				  <ol id='recently-viewed-items-2'>
					<%=Products_of_Manufacturer%>
				  </ol>
			</div>
			<div class='block-content'>					
				<div class="col-md-6 col-sm-6 col-xs-12 hide " >
					<h3> About <%=Name_of_Manufacturer%></h3>
				</div>			
				<div class="col-md-12 col-sm-12 col-xs-12 " style=" padding: 0px; text-align: justify; font-size: 14px;" >
					<p><%=ManufacturerDetails.getProperty(Name_of_Manufacturer_URL)%></p>
				</div>
			</div>
		</div>
      </section>
        
		
	  <aside class="col-left sidebar col-md-3 col-xs-12  wow bounceInUp animated">
          <div class="side-nav-categories">
            <div class="block-title"> Product Finder </div>
            <div class="box-content box-category">
			 <form name="tyreindex">
			 	 <div id="displysesmsg" style='font-size: 17px;'></div>

            <div class="search_bar_tyres">
			  
<!---################################## Dropdown Include Starts  ################################------>
	<jsp:include page = "/dev/includes/jsp/includes_dropdowns.jsp" />
<!---################################## Dropdown Include Ends  ################################------>
		
			</div>
			</form>
            </div>
          </div>
           <div class="side-nav-categories">
            <div class="block-title"> Shop by Other Manufacturer</div>
            <div class="box-content box-category">
                <ul id="magicat">
				<%
				if(Vector_Vehicle_Names!=null && Vector_Vehicle_Names.size() > 0)
				{
				try{
					for ( int i=0; i<Vector_Vehicle_Names.size() ; i++){
						
					Hashtable ht=(Hashtable)Vector_Vehicle_Names.get(i);
					
					String Battery_Type=String.valueOf(ht.get("battery_type"));
					String Battery_Type_URL= Battery_Type.replaceAll(" ", "-"); 
					
					String Vehicle_Name=String.valueOf(ht.get("vehicle_name"));
					String Vehicle_Name_URL= Vehicle_Name.replaceAll(" ", "-"); 
				%>
			  <li class="level0- level0 "> <span class="magicat-cat"><a  href="<%=baseURL%><%=baseurldirectory%>fueltype/<%=Battery_Type_URL%>/<%=Vehicle_Name_URL%>/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/" ><span><%=Vehicle_Name%></span></a></span> </li>
			  
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
         
        </aside>
      
		</div>
    </div>
  </div>
  <!-- End Two columns content --> 
  </form>
  </br>



<!---################################## CSS Include Starts  ################################------>
	<jsp:include page = "/assets/includes/includes_footer.jsp" />
<!---################################## CSS Include Ends  ################################------>

 <!---################################## CSS Include Starts  ################################------>
	<jsp:include page = "/assets/includes/includes_js.jsp" />
<!---################################## CSS Include Ends  ################################------>

<input type="hidden" name="publicUrl" id='publicUrl' value="<%=publicUrl%>">
<input type="hidden" name="brandkeyword" id='brandkeyword' value="Common">
<input type="hidden" name="backkeyword" id='backkeyword' value="index">

<input type="hidden" name="product_type_page" id='product_type_page' value="<%=Type_of_Manufacturer%>">
<input type="hidden" name="product_make_page" id='product_make_page' value="<%=Name_of_Manufacturer%>">
<input type="hidden" name="product_capacity_page" id='product_capacity_page' value="">
<input type="hidden" name="product_model_page" id='product_model_page' value="">
<input type="hidden" name="product_fuel_page" id='product_fuel_page' value="<%=FuelType%>">
<input type="hidden" name="product_brand_page" id='product_brand_page' value="<%=Brand%>">
<input type="hidden" name="product_state_page" id='product_state_page' value="<%=Product_State_Cookie%>">
<input type="hidden" name="product_city_page" id='product_city_page' value="<%=Product_City_Cookie%>">



<script>

	function UpdateProduct_Filter(){ 
  
	//alert(22);
	var State = $("#product_state").val(); 
	var City = $("#product_city").val();
	var product_type= $("#product_type_page").val();
	var product_make= $("#product_make_page").val();
	var product_fuel= $("#product_fuel_page").val();
	var product_model = $("#product_model_page").val();
	var product_brand = $("#product_brand_page").val();
	var product_brand_URL = $("#product_brand").val();
	//alert(product_type);
	//alert(product_make);
	//alert(product_model);
	//alert(product_brand);
	//alert(product_brand_URL);

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
	
	var Filter_Battery_Warranty_Array = [];
	$.each($("input[name='Filter_Battery_Warranty']:checked"), function(){            
	Filter_Battery_Warranty_Array.push($(this).val());
	});
	var Filter_Battery_Warranty=Filter_Battery_Warranty_Array.join(",");
	
	//alert(Filter_Battery_Warranty);
	
	if(Filter_Battery_Warranty=="" || Filter_Battery_Warranty=="undefined")
	{
	Filter_Battery_Warranty_URL="Warranty/";
	}
	else{
	Filter_Battery_Warranty_URL="Warranty="+Filter_Battery_Warranty+"/";
	}

	var publicUrl = $("#publicUrl").val();
	
	product_type= product_type.replace(" ", "-");
	product_type= product_type.replace(/ /g, "-");
	product_make= product_make.replace(" ", "-");
	product_make= product_make.replace(/ /g, "-");
	product_fuel= product_fuel.replace(" ", "-");
	product_fuel= product_fuel.replace(/ /g, "-");
	product_model= product_model.replace(" ", "+");
	product_model= product_model.replace(/ /g, "+");
	product_brand= product_brand.replace(" ", "+");
	product_brand= product_brand.replace(/ /g, "+");
	
	State= State.replace(" ", "-");
	State= State.replace(/ /g, "-");
	
	City= City.replace(" ", "+");
	City= City.replace(/ /g, "+");
	

		Filter_Battery_Warranty_URL= Filter_Battery_Warranty_URL.replace(/ /g, "_");
        //alert(Filter_Battery_Warranty_URL);
		Filter_Battery_Warranty_URL = Filter_Battery_Warranty_URL.replace(/\+/g, 'plus');
		//alert(Filter_Battery_Warranty_URL);
	
        window.location.href = publicUrl+'/bookbattery/manufacturer/'+product_type+'/'+product_make+'/'+product_fuel+'/'+State+'/'+City+'/';
	
	//alert(window.location.href);
  }
</script>

</body>
</html>

