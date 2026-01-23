<%-- 
Document   		 : battery_list.jsp
Created on 		 : Sep 15, 2016, 10:14:12 AM
Author     		 : BookBattery
Copyright Notice : BookBattery Confidential.
Document         : This jsp is used as Batteries Multi Product list and details page of BookBattery Batteries.
--%>
<%@ page language="java" import="java.sql.*"%>
<%@page language="java" import="java.io.File,java.text.*,java.util.Calendar,java.util.ArrayList,java.util.Hashtable,java.util.Vector,com.ngit.javabean.loglevel.*,java.util.Date,java.util.Properties,java.util.Arrays,com.ngit.javabean.loglevel.LogLevel,javax.servlet.ServletContext,java.util.Properties,java.io.FileInputStream"%>
<%response.setHeader("Pragma","no-cache"); response.setHeader("Cache-Control","no-store"); response.setHeader("Expires","0"); response.setDateHeader("Expires",-1); %>
<%
Properties propsMOPConfig = new Properties();
Vector Product_Vector=(Vector)request.getAttribute("Product_Vector");
Vector Vector_Inverter_Battery_Warranty=(Vector)request.getAttribute("Vector_Inverter_Battery_Warranty");
ServletContext context = getServletContext();
FileInputStream fin1      = new FileInputStream(new File(context.getRealPath("properties/bookbatteryconfig.properties"))); 
propsMOPConfig.load(fin1); 
fin1.close(); 
String publicUrl = (propsMOPConfig.getProperty("publicUrl")!=null)?propsMOPConfig.getProperty("publicUrl"):"";
String baseURL = (propsMOPConfig.getProperty("baseURL")!=null)?propsMOPConfig.getProperty("baseURL"):"";
String baseurldirectory = (propsMOPConfig.getProperty("baseurldirectory")!=null)?propsMOPConfig.getProperty("baseurldirectory"):"";
String serverURL = (propsMOPConfig.getProperty("serverURL")!=null)?propsMOPConfig.getProperty("serverURL"):"";

%>

<%
String Brand=(String)request.getAttribute("Vector_Get_Battery_Brand_List");
String Battery_Type = (String) request.getAttribute("Battery_Type");
String Battery_Type_URL= Battery_Type.replaceAll(" ", "-");
String State = (String) request.getAttribute("State");
String State_URL= State.replaceAll(" ", "-");
String City = (String) request.getAttribute("City");
String City_URL= City.replaceAll(" ", "-");
//out.println("Your IP address is " );

String titles = "BookBattery | "+Brand+" "+Battery_Type_URL.replace("-"," ")+" for all vehicle models";

String description = "Buy "+Battery_Type_URL.replace("-"," ")+" from top Automotive brand "+Brand+" with best price. Free delivery and Installation with in 4 to 24 hours";

String keywords = ""+Battery_Type_URL.replace("-"," ")+","+Brand+" "+Battery_Type_URL.replace("-"," ")+","+Brand+" Battery Online,"+Brand+" Batteries,Buy "+Brand+" Battery,Bangalore,Noida,Gurgaon,Jaipur,Trivandram,surat,Mangalore,Coimbatore,Buy Battery Online,Vijayawada,Guwahati,Chandigarh,Lucknow,Jammu,Bhopal,Mysore,Buy "+Brand+" Battery Online.";

%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head itemscope itemtype="http://schema.org/WebSite">
<title itemprop='name'><%=titles%></title>
<meta charset="utf-8">
<meta http-equiv="x-ua-compatible" content="ie=edge">
<meta name="viewport" content="width=device-width">
<meta name="og_title" property="og:title" content="<%=titles%>"/>
<link rel="canonical"  href="<%=publicUrl%><%=baseurldirectory%>manufacturer/<%=Battery_Type_URL%>/" itemprop="url">
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

  <!-- breadcrumbs -->
  <div class="breadcrumbs">
    <div class="container">
      <div class="row">
        <ul>
          <li class="home"> <a href="<%=publicUrl%><%=baseurldirectory%>" title="Go to Home Page"> Home</a><span>&raquo;</span></li>
          <!--<li class=""> <a href="<%=publicUrl%><%=baseurldirectory%>manufacturer/" title="Go to Manufacturer Page">Manufacturer</a><span>&raquo;</span></li>-->
          <li class=""> <a href="<%=publicUrl%><%=baseurldirectory%>manufacturers/<%=Battery_Type_URL%>/<%=State_URL%>/<%=City_URL%>/" title="Go to <%=Battery_Type%> Page"><%=Battery_Type%></a><span>&raquo;</span></li>
          <li class="category13"><strong>ALL</strong></li>
        </ul>
      </div>
    </div>
  </div>
	<ol class="hide" itemscope itemtype="http://schema.org/BreadcrumbList">
	  <li itemprop="itemListElement" itemscope itemtype="http://schema.org/ListItem">
		<a itemscope itemtype="http://schema.org/Thing" itemprop="item" href="<%=publicUrl%><%=baseurldirectory%>manufacturers/<%=Battery_Type_URL%>/<%=State_URL%>/<%=City_URL%>/" id="<%=publicUrl%><%=baseurldirectory%>manufacturers/<%=Battery_Type_URL%>/<%=State_URL%>/<%=City_URL%>/">
		  <span itemprop="name"><%=Battery_Type%></span>
		</a>
		<meta itemprop="position" content="1" />
	  </li>
	  <li itemprop="itemListElement" itemscope itemtype="http://schema.org/ListItem">
		<a itemscope itemtype="http://schema.org/Thing" itemprop="item"  href="<%=publicUrl%><%=baseurldirectory%><%=Battery_Type_URL%>/ALL/ALL/<%=State_URL%>/<%=City_URL%>/" id="<%=publicUrl%><%=baseurldirectory%><%=Battery_Type_URL%>/ALL/ALL/<%=State_URL%>/<%=City_URL%>/">
		  <span itemprop="name">ALL</span>
		</a>
		<meta itemprop="position" content="3" />
	  </li>
	</ol>
  <!-- End breadcrumbs --> 
  <!-- Two columns content -->
  <div class="main-container col2-left-layout" id="Page_Result_ID">
    <div class="main container">
      <div class="row">
	  <section class="col-md-9 col-xs-12" >
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
            <h1> <%=Battery_Type%> - ALL - ALL</h1>
          </div>
			
		   <div class="category-products">
            <div class="toolbar">
              <div class="sorter">
                <div class="view-mode"> 
					<span title="Grid" class="button button-active button-grid">Grid</span>
				</div>
              </div>
              <div id="sort-by" class="hide">
                <label class="left">Sort By: </label>
                <ul>
                  <li><a href="#">Position<span class="right-arrow"></span></a>
                    <ul>
                      <li><a href="#">Name</a></li>
                      <li><a href="#">Price</a></li>
                      <li><a href="#">Position</a></li>
                    </ul>
                  </li>
                </ul>
                <a class="button-asc left" href="#" title="Set Descending Direction"><span style="color:#999;font-size:11px;" class="glyphicon glyphicon-arrow-up"></span></a> </div>
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
				if(Product_Vector!=null && Product_Vector.size() > 0)
				{
					try{
						for ( int i=0; i<Product_Vector.size() ; i++){
							
							Hashtable ht=(Hashtable)Product_Vector.get(i);
							
							String bat_brand=String.valueOf(ht.get("bat_brand"));
							String bat_capacity=(String)ht.get("bat_capacity");
							String icon_url=(String)ht.get("icon_url");
							String bat_warranty=(String)ht.get("bat_warranty");
							String bat_model=String.valueOf(ht.get("bat_model"));
							
							String WithOutOldBattery_String=String.valueOf(ht.get("bat_witbat_price"));
							String WithOldBattery_Less_String=String.valueOf(ht.get("bat_ret_price"));
							String bat_act_price=String.valueOf(ht.get("bat_act_price"));
							
							int WithOutOldBattery = Integer.parseInt(WithOutOldBattery_String);
							int WithOldBattery_Less = Integer.parseInt(WithOldBattery_Less_String);
							int WithOldBattery = WithOutOldBattery - WithOldBattery_Less;
							
							LogLevel.DEBUG(5,new Throwable(),"WithOldBattery :"+WithOldBattery );
							LogLevel.DEBUG(5,new Throwable(),"WithOutOldBattery :"+WithOutOldBattery );
													
							if(icon_url == null)
							{
								icon_url = "./images/noimage.jpg";
							}
							
							String bat_brand_URL=bat_brand;
							String bat_model_URL=bat_model;
							
							bat_brand_URL= bat_brand_URL.replaceAll(" ", "+");
							bat_model_URL= bat_model_URL.replaceAll(" ", "+");
							
							int WithOldBattery_Percent=(((Integer.parseInt(bat_act_price)-WithOldBattery)*100)/Integer.parseInt(bat_act_price));

			%>
		       <li class="item col-lg-4 col-md-4 col-sm-6 col-xs-12" style="">
                  <div class="item-inner">
                    <div class="item-img">
                      <div class="product-block">
                        <div class="product-image"> <a href="<%=baseURL%>/bookbattery/products/<%=bat_brand%>/<%=bat_model%>/<%=State_URL%>/<%=City_URL%>/" style="height: auto;">
                          <figure class="product-display">
                            <div class="new-label new-top-left"><%=WithOldBattery_Percent%>%&nbsp;Off </div>
                            <img src="<%=icon_url%>" class="lazyOwl product-mainpic" alt=" <%=bat_brand%> <%=bat_model%> <%=State_URL%> <%=City_URL%>" style="display: block;"> <img class="product-secondpic" alt=" <%=bat_brand%> <%=bat_model%> <%=State_URL%> <%=City_URL%>" src="<%=icon_url%>"> </figure>
                          </a> </div>
                        <div class="product-meta">
                          <div class="product-action"> <a class="addcart" href="<%=baseURL%>/bookbattery/products/<%=bat_brand%>/<%=bat_model%>/<%=State_URL%>/<%=City_URL%>/" > <i class="icon-external-link-sign">&nbsp;</i> View More </a>
                        </div>
                      </div>
                    </div>
                    <div class="item-info" style="text-align: left;">
                      <div class="info-inner">
                        <div class="item-title"> <a href="<%=baseURL%>/bookbattery/products/<%=bat_brand%>/<%=bat_model%>/<%=State_URL%>/<%=City_URL%>/" title="Click to View "> <%=bat_brand%> <%=bat_model%> ( <%=bat_capacity%> ) </a> </div>
                        <div class="item-content">
                          <div class="item-price">
                            <div class="price-box">
							   <span class="price-label" style="display: block;">Warranty: <span  > <%=bat_warranty%> </span> </span>
							   <div class="old-price"> <span class="price-label" style="display: block;">Regular Price: <span class="price" ><i class="icon-inr" aria-hidden="true"></i> <%=bat_act_price%> </span> </span></div>
								<table style=" color: #494848;font-size: 15px;">
									  <tr>
										  <th>With Out Old Battery Price</th>
										  <th>: <i class="icon-inr" aria-hidden="true"></i> <%=WithOutOldBattery%> </th>
									  </tr>
									  <tr>
										  <th>With Old Battery Price</th>
										  <th>: <i class="icon-inr" aria-hidden="true"></i> <%=WithOldBattery%> </th>
									  </tr>
								</table> 
							<a class="button btn" style="margin-top: 5px;"  href="<%=baseURL%>/bookbattery/products/<%=bat_brand%>/<%=bat_model%>/<%=State_URL%>/<%=City_URL%>/"><i class="icon-external-link-sign">&nbsp;</i> View More</a>
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
       

	<aside class="col-left sidebar col-md-3 col-xs-12  wow bounceInUp">
          <div class="side-nav-categories">
            <div class="block-title"> Search </div>
            <div class="box-content box-category">
			 <form name="tyreindex">
			 	 <div id="displysesmsg" style='font-size: 17px;'></div>

            <div class="search_bar_BookBattery">
			  
<!---################################## Dropdown Include Starts  ################################------>
	<jsp:include page = "/dev/includes/jsp/includes_dropdowns.jsp" />
<!---################################## Dropdown Include Ends  ################################------>
		
			</div>
			</form>
            </div>			
          </div>
			
	<%		
	if(Vector_Inverter_Battery_Warranty!=null && Vector_Inverter_Battery_Warranty.size() > 0)
		{
		try{
	  %>
			<div class="block block-poll">
				<div class="block-title">Battery Warranty</div>
				<form onSubmit="return validatePollAnswerIsSelected();" method="post" action="#" id="pollForm">
				  <div class="block-content">
					<p class="block-subtitle">Select Your Required Warranty?</p>
					<ul id="poll-answers">
				<%
					for ( int i=0; i<Vector_Inverter_Battery_Warranty.size() ; i++){
						Hashtable ht=(Hashtable)Vector_Inverter_Battery_Warranty.get(i);
						String Battery_Warranty=String.valueOf(ht.get("bat_warranty"));
						String Battery_Warranty_temp= Battery_Warranty.replaceAll("Months", "M");
				%>
					  <li class="odd">
						<input type="checkbox"  value="<%=Battery_Warranty%>"  id="Filter_Battery_Warranty" class="radio poll_vote Filter_Battery_Warranty" name="Filter_Battery_Warranty">
						<span class="label">
						<label for="<%=Battery_Warranty%>"><%=Battery_Warranty_temp%></label>
						</span> </li>
				<%
					}
				%>
					</ul>
					<div class="actions">
					<span style="float: right;"> <a href="javascript:clear_all_filter('Filter_Battery_Warranty')"> Clear All</a></span>
					</br>
					  <button class="button " title="Filter By Battery Warranty" type="button" onClick="javascript:UpdateProduct_Filter();"  ><span>Filter By Battery Warranty</span></button>
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
  <style>
	.price-box 
	{
		margin: 3px 0;
		min-height: 20px;
		height: 160px;
	}
  </style>
<input type="hidden" name="publicUrl" id='publicUrl' value="<%=publicUrl%>">
<input type="hidden" name="baseURL" id='baseURL' value="<%=baseURL%>">
<input type="hidden" name="brandkeyword" id='brandkeyword' value="Common">
<input type="hidden" name="backkeyword" id='backkeyword' value="index">
<input type="hidden" name="product_type_page" id='product_type_page' value="<%=Battery_Type%>">
<input type="hidden" name="product_make_page" id='product_make_page' value="All">
<input type="hidden" name="product_capacity_page" id='product_capacity_page' value="">
<input type="hidden" name="product_model_page" id='product_model_page' value="All">
<input type="hidden" name="product_brand_page" id='product_brand_page' value="<%=Brand%>">
<input type="hidden" name="Vector_Inverter_Battery_Warranty" id='Vector_Inverter_Battery_Warranty' value="<%=Vector_Inverter_Battery_Warranty%>">
<input type="hidden" name="product_state_page" id='product_state_page' value="<%=State%>">
<input type="hidden" name="product_city_page" id='product_city_page' value="<%=City%>">

 
<script> 

	$(document).ready(function()
	{
		//alert(22);
		setCookie("product_type_cookie", product_type);
	})
	
	$(document).ready(function()
	{
		//alert(22);
		
		var Filter_Product_Battery_Warranty = $("#Filter_Product_Battery_Warranty").val();
		
		//alert(Filter_Product_Battery_Warranty);
		
		Filter_Product_Battery_Warranty =Filter_Product_Battery_Warranty.replace(/plus/g, "+");
		
		//alert(Filter_Product_Battery_Warranty);
		
		Filter_Product_Battery_Warranty =Filter_Product_Battery_Warranty.replace(/_/g, " ");
		
		//alert(Filter_Product_Battery_Warranty);
		
		
		//alert(Filter_Product_Battery_Warranty);
		
		if(Filter_Product_Battery_Warranty.indexOf(",")<=0)
		{
			$("input[name='Filter_Battery_Warranty'][value='"+Filter_Product_Battery_Warranty+"']").prop('checked', true);
		}
		else
		{			
			//alert("else");
			var Filter_Product_Battery_Warranty_array = Filter_Product_Battery_Warranty.split(',');			
			//alert(Filter_Product_Battery_Warranty_array);
			
			for(var i = 0; i < Filter_Product_Battery_Warranty_array.length; i++) 
			{
			   $("input[name='Filter_Battery_Warranty'][value='"+Filter_Product_Battery_Warranty_array[i]+"']").prop('checked', true);
			}
		}
	})
 
	function UpdateProduct_Filter(){ 
  
	//alert(223);
	
	var State = $("#product_state").val(); 
	var City = $("#product_city").val();
	var product_type= $("#product_type_page").val();
	var product_make= $("#product_make_page").val();
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
	
	
window.location.href = publicUrl+'/bookbattery/BatteryBrand/'+product_brand+'/'+product_type+'/'+State+'/'+City+'/';
	
	//alert(window.location.href);
  }

	function clear_all_filter(clear_class)
	{
	  $('.'+clear_class+'').prop('checked', false);
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

 </script>
  
 
</body>
</html>