<%-- 
Document   		 : product_brands_list.jsp
Created on 		 : Aug 6, 2019, 10:14:12 AM
Author     		 : Asistmi Solutions
Copyright Notice : BookBattery Confidential.
Document         : This jsp is used as Batteries Multi Product list and details page of BookBattery Batteries.
--%>
<%@ page language="java" import="java.sql.*"%>
<%@page language="java" import="java.io.File,java.text.*,java.util.Calendar,java.util.ArrayList,java.util.Hashtable,java.util.Vector,com.ngit.javabean.loglevel.*,java.util.Date,java.util.Properties,java.util.Arrays,com.ngit.javabean.loglevel.LogLevel,javax.servlet.ServletContext,java.util.Properties,java.io.FileInputStream"%>
<%response.setHeader("Pragma","no-cache"); response.setHeader("Cache-Control","no-store"); response.setHeader("Expires","0"); response.setDateHeader("Expires",-1); %>
<%
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
Vector Vector_Inverter_Battery_Warranty=(Vector)request.getAttribute("Vector_Inverter_Battery_Warranty");
Vector Vector_Brand_logo_Urls=(Vector)request.getAttribute("Vector_Brand_logo_Urls");
String Product_Type = (String) request.getAttribute("Battery_Type");
String Product_Type_URL= Product_Type.replaceAll(" ", "-");
String State = (String) request.getAttribute("State");
String State_URL= State.replaceAll(" ", "-");
String City = (String) request.getAttribute("City");
String City_URL= City.replaceAll(" ", "-");
//out.println("Your IP address is " );

%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head itemscope itemtype="http://schema.org/WebSite">
<title itemprop='name'><%=titles%></title>
<meta name="og_title" property="og:title" content="<%=titles%>"/>
<link rel="canonical"  href="<%=publicUrl%><%=baseurldirectory%>manufacturer/<%=Product_Type_URL%>/" itemprop="url">
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
          <li class=""> <a href="<%=publicUrl%><%=baseurldirectory%>Brand/<%=Product_Type_URL%>/<%=State_URL%>/<%=City_URL%>/" title="Go to <%=Product_Type%> Page"><%=Product_Type%></a><span>&raquo;</span></li>
          <li class="category13"><strong>ALL</strong></li>
        </ul>
      </div>
    </div>
  </div>
	<ol class="hide" itemscope itemtype="http://schema.org/BreadcrumbList">
	  <li itemprop="itemListElement" itemscope itemtype="http://schema.org/ListItem">
		<a itemscope itemtype="http://schema.org/Thing" itemprop="item" href="<%=publicUrl%><%=baseurldirectory%>manufacturers/<%=Product_Type_URL%>/<%=State_URL%>/<%=City_URL%>/">
		  <span itemprop="name"><%=Product_Type%></span>
		</a>
		<meta itemprop="position" content="1" />
	  </li>
	  <li itemprop="itemListElement" itemscope itemtype="http://schema.org/ListItem">
		<a itemscope itemtype="http://schema.org/Thing" itemprop="item"  href="<%=publicUrl%><%=baseurldirectory%><%=Product_Type_URL%>/ALL/ALL/<%=State_URL%>/<%=City_URL%>/">
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
            <h1> <%=Product_Type%> - Brands</h1>
          </div>
		   <div class="category-products">
            <div class="toolbar">
              <div class="sorter">
                <div class="view-mode"> 
					<span title="Grid" class="button button-active button-grid">Grid</span>
				</div>
              </div>
              <div id="sort-by" class="hide">
                <label class="left">Filter By: </label>
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
            </div>
            <div class="row" style="padding:15px;">
			<h6><b>Select Brand</b></h6>
			<section class="latest-blog" >
			<div class="pro-manifacture-custom ">
			<%
				String redirect_URL="";
				String redirect_URL_ALL="";
				String icon_url="";
				String brand="";
				
				if(Vector_Brand_logo_Urls!=null && Vector_Brand_logo_Urls.size() > 0)
				{
					try{
						for ( int i=0; i<Vector_Brand_logo_Urls.size() ; i++){
							
							Hashtable ht=(Hashtable)Vector_Brand_logo_Urls.get(i);
							
							icon_url=String.valueOf(ht.get("icon_url"));
							brand=(String)ht.get("brand");
							LogLevel.DEBUG(5,new Throwable(),"Product_Type_URL :"+Product_Type_URL);
							if(Product_Type.contains("Inverter"))
							{
								redirect_URL=""+baseURL+"/bookbattery/"+Product_Type_URL+"/"+brand+"/"+State_URL+"/"+City_URL;
								LogLevel.DEBUG(5,new Throwable(),"redirect_URL :"+redirect_URL);
								
								redirect_URL_ALL=""+baseURL+"/bookbattery/"+Product_Type_URL+"/All/"+State_URL+"/"+City_URL;
								LogLevel.DEBUG(5,new Throwable(),"redirect_URL :"+redirect_URL);	
							}
							else
							{
								String Vehicle_Name = (String) request.getAttribute("Vehicle_Name");
								String Vehicle_Model = (String) request.getAttribute("Vehicle_Model");
								
								redirect_URL=""+baseURL+"/bookbattery/"+Product_Type_URL+"/"+Vehicle_Name+"/"+Vehicle_Model.replaceAll("/","|")+"/"+State_URL+"/"+City_URL+"/"+brand+"/Warranty/Price";
								LogLevel.DEBUG(5,new Throwable(),"redirect_URL :"+redirect_URL);
								
								redirect_URL_ALL=""+baseURL+"/bookbattery/"+Product_Type_URL+"/"+Vehicle_Name+"/"+Vehicle_Model.replaceAll("/","|")+"/"+State_URL+"/"+City_URL+"/ALL/Warranty/Price";
								LogLevel.DEBUG(5,new Throwable(),"redirect_URL_ALL :"+redirect_URL_ALL);
							}
							
							if (brand.equals("Luminous")&&(!Product_Type.contains("Inverter")))
							{
								
							}
							
					else{
			%>
				<div class="col-xs-6 col-sm-6 col-md-3">
				<div class="prop" >
				<div class="blog-img-height-custom blog-img" style="height: 50%;"> 
				<a href="<%=redirect_URL%>/">
				<img  src="<%=icon_url%>" >
				</a>
				</div>
				<h2 class="text-align-center-custom" style="white-space: nowrap; overflow: hidden;padding-bottom: 10px;"><a href="<%=redirect_URL%>/" ><%=brand%></a> </h2>
				</div>
				</div>
				<%
					}
					}%>
					<div class="col-xs-6 col-sm-6 col-md-3">
					<div class="prop">
					<div class="blog-img-height-custom blog-img" style="height: 50%;"> 
					<a href="<%=redirect_URL_ALL%>/">
					<img  src="<%=publicUrl%><%=baseurldirectory%>/images/brandlogo/all_brands_logo.jpg" >
					</a>
					</div>
					<h2 class="text-align-center-custom" style="white-space: nowrap; overflow: hidden;padding-bottom: 10px;"><a href="<%=redirect_URL_ALL%>/" >All Brands</a> </h2>
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
<input type="hidden" name="product_type_page" id='product_type_page' value="<%=Product_Type%>">
<input type="hidden" name="product_make_page" id='product_make_page' value="All">
<input type="hidden" name="product_capacity_page" id='product_capacity_page' value="">
<input type="hidden" name="product_model_page" id='product_model_page' value="All">
<input type="hidden" name="product_state_page" id='product_state_page' value="<%=State%>">
<input type="hidden" name="product_city_page" id='product_city_page' value="<%=City%>">

 
<script> 

	$(document).ready(function()
	{
		//alert(22);
		setCookie("product_type_cookie", product_type);
	});
	
$(document).ready(function()
{
	//alert(33);
	//alert($('#product_type').val());
	
	if($('#product_type').val()=='Inverter' || $('#product_type').val()=='Inverter & Battery Combo' || $('#product_type').val()=='Inverter and Battery Combo')
	{		
		
		//alert("inside");
		$.ajax({					 
			type: "GET",
			 url: "/bookbattery/servlet/InvertersDetails?hidWhatToDo=getinvertercapacity",
			data: {inverterbrand:"All"},
			success: function(data)
			{	
				//alert(data);
				$("#product_capacity").html(data).focus();
			}
		});
	}
	else
	{
		//alert(33);		
		$.ajax
		({
			type: "GET",
			url: "/bookbattery/servlet/BatteryHome?hidWhatToDo=getbatterycapacity",
			data: {batterytype: $('#product_type').val()},
			success: function(data)
			{	
				$("#product_capacity").html(data).focus();
			}
		});	
	}	
});

	$(document).ready(function()
	{
	
		var Filter_Product_Battery_Warranty = $("#Filter_Product_Battery_Warranty").val();
		
        //alert(Filter_Product_Battery_Warranty);
        
        if(Filter_Product_Battery_Warranty=="undefined" || Filter_Product_Battery_Warranty==undefined)
        {
           // alert("22");
        }
        else
        {

            //alert("23");
    
		//Filter_Product_Battery_Warranty =Filter_Product_Battery_Warranty.replace(/plus/g, "+");
		//Filter_Product_Battery_Warranty =Filter_Product_Battery_Warranty.replace(/_/g, " ");
        
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
        }
	});
 
	
	function AskforMobileNumber(){

		product_type =$("#product_type").val();
		product_capacity =$("#product_capacity").val();
		product_brand =$("#product_brand").val();
		product_state =$("#product_state").val();
		product_city =$("#product_city").val();
	
		product_type_URL= product_type.replace(/ /g, "-");
		product_capacity_URL= product_capacity.replace(/ /g, "+");
		product_brand_URL= product_brand.replace(/ /g, "-");
		product_state_URL= product_state.replace(/ /g, "-");
		product_city_URL= product_city.replace(/ /g, "-");
		

	var publicUrl =$("#publicUrl").val();
	
	//alert(publicUrl);

	// This is declared to send input_type in valadation
	var input_type;
		input_type="select";
		if (selectValidation("","product_type",input_type) == false)
		{
			return;
		}
		else
		{
			setCookie("product_type_cookie", product_type)
		}
		
		if (selectValidation("","product_capacity",input_type) == false)
		{
			return;
		}
		else
		{
			setCookie("product_capacity_cookie", product_capacity)
		}
		
		if (selectValidation("","product_brand",input_type) == false)
		{
			return;
		}
		else
		{
			setCookie("product_brand_cookie", product_brand)
		}
		//alert(product_type);
		
		if(product_type=='Inverter')
		{
			window.location.href = publicUrl+'/bookbattery/'+product_type_URL+'/'+product_brand_URL+'/'+product_state_URL+'/'+product_city_URL+'/Capacity='+product_capacity_URL+'/';
		}
		else if(product_type=='Inverter Batteries')
		{
			//alert("in"+product_type);
			window.location.href = publicUrl+'/bookbattery/'+product_type_URL+'/'+product_brand_URL+'/'+product_state_URL+'/'+product_city_URL+'/Capacity='+product_capacity_URL+'/';
		}
		else
		{
			//alert("in"+product_type);
			
			window.location.href = publicUrl+'/bookbattery/'+product_type_URL+'/'+product_brand_URL+'/'+product_state_URL+'/'+product_city_URL+'/Capacity='+product_capacity_URL+'/';
		}
	}

	function UpdateProduct_Filter(){ 
  
	//alert(22);
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
    
    Filter_Battery_Warranty_URL= Filter_Battery_Warranty_URL.replace(/ /g, "_");
    //alert(Filter_Battery_Warranty_URL);
    Filter_Battery_Warranty_URL = Filter_Battery_Warranty_URL.replace(/\+/g, 'plus');
    //alert(Filter_Battery_Warranty_URL);
	}

	var publicUrl = $("#publicUrl").val();
	
	product_type= product_type.replace(" ", "-");
	product_type= product_type.replace(/ /g, "-");
	product_make= product_make.replace(" ", "-");
	product_make= product_make.replace(/ /g, "-");
	product_model= product_model.replace(" ", "+");
	product_model= product_model.replace(/ /g, "+");

	
	State= State.replace(" ", "-");
	State= State.replace(/ /g, "-");
	
	City= City.replace(" ", "+");
	City= City.replace(/ /g, "+");
	
	
    window.location.href = publicUrl+'/bookbattery/Brand/'+product_type+'/'+State+'/'+City+'/';
	
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