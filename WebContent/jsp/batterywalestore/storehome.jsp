<%@ page language="java" import="java.sql.*"%>
<%@page language="java" import="java.io.File,java.text.*,java.util.Calendar,java.util.ArrayList,java.util.Hashtable,java.util.Vector,com.ngit.javabean.loglevel.*,java.util.Date,java.util.Properties,java.util.*,com.ngit.javabean.loglevel.LogLevel,java.util.Properties,java.io.FileInputStream"%>
<%
String strUserid=(String)session.getAttribute("sesStoreOperatorName");
String strStoreId=(String)session.getAttribute("sesstrStoreId");
String strStoreName=(String)session.getAttribute("sesstrStoreName");
if(strUserid==null)
{
	strUserid="";
	session.setAttribute("sesErrorMsg","Session Timed Out. Please login again");
	response.sendRedirect("../../../BookBatterystore/index.html");
	return;
}

Vector alist=(Vector)session.getAttribute("sesCountofBatterynrand");
LogLevel.DEBUG(1,new Throwable(),"alist: "+alist);
Vector alist1=(Vector)session.getAttribute("sesCountofInverterbrand");
LogLevel.DEBUG(1,new Throwable(),"alist1: "+alist1);
Vector alist2=(Vector)session.getAttribute("sesCountofServiceHealth");
LogLevel.DEBUG(1,new Throwable(),"alist2: "+alist2);
//Vector alist3=(Vector)session.getAttribute("sesCountofServiceRecharge");
//LogLevel.DEBUG(1,new Throwable(),"alist3: "+alist3);
//Vector alist4=(Vector)session.getAttribute("sesCountofServiceJumpStart");
//LogLevel.DEBUG(1,new Throwable(),"alist4: "+alist4);
 %>
<jsp:include page = "storeheader.jsp" />
<jsp:include page = "storeleftmenu.jsp" />
  <section class="content">
        <div class="container-fluid">
            <div class="block-header">
                <h2>BATTERY ORDERS</h2>
            </div>
		<%
		
		if(alist!=null && alist.size() > 0)
		{ 
		for(int i=0;i<alist.size();i++)
			{
				Hashtable ht=(Hashtable)alist.get(i);
				String totalorders=String.valueOf(ht.get("count1"));
				String installedorders =String.valueOf(ht.get("count2"));
				String confirmedorders=String.valueOf(ht.get("count3"));
				String cancelledorders=String.valueOf(ht.get("count4"));
			
		%>
			
            <!-- Widgets -->
            <div class="row clearfix">
                <div class="col-lg-3 col-md-3 col-sm-6 col-xs-12 clickMe" id="batterytotal">
                    <div class="info-box bg-red hover-expand-effect">
                        <div class="icon">
                            <i class="material-icons">playlist_add_check</i>
                        </div>
                        <a href="javascript:void(0);" class="">
                        <div class="content">
                            <div class="text">TOTAL</div>
                            <div class="number"  id="total" data-from="0" data-speed="15" data-fresh-interval="20"><%=totalorders%></div>
                        </div></a>
                    </div>
                </div>
                <div class="col-lg-3 col-md-3 col-sm-6 col-xs-12 clickMe" id="batteryinstalledtotal">
                    <div class="info-box bg-pink hover-expand-effect">
                        <div class="icon">
                            <i class="material-icons">help</i>
                        </div>
                        <div class="content">
                            <div class="text">INSTALLED</div>
                            <div class="number" id="installed"  data-from="0" data-speed="1000" data-fresh-interval="20"><%=installedorders%></div>
                        </div>
                    </div>
                </div>
                <div class="col-lg-3 col-md-3 col-sm-6 col-xs-12 clickMe" id="batterypendingtotal">
                    <div class="info-box bg-purple hover-expand-effect">
                        <div class="icon">
                            <i class="material-icons">forum</i>
                        </div>
                        <div class="content">
                            <div class="text">UNCONFIRMED</div>
                            <div class="number"  id="pending" data-from="0" data-speed="1000" data-fresh-interval="20"><%=confirmedorders%></div>
                        </div>
                    </div>
                </div>
                <div class="col-lg-3 col-md-3 col-sm-6 col-xs-12 clickMe" id="batteryinprocesstotal">
                    <div class="info-box bg-deep-purple hover-expand-effect">
                        <div class="icon">
                            <i class="material-icons">person_add</i>
                        </div>
                        <div class="content">
                            <div class="text">IN PROCESS</div>
                            <div class="number" id="inprocess" data-from="0"  data-speed="1000" data-fresh-interval="20"><%=cancelledorders%></div>
                        </div>
                    </div>
                </div>
				<% }  } %>
            </div>
            <!-- #END# Widgets -->
			 
            <!-- Hover Expand Effect -->
            <div class="block-header">
                <h2>INVERTER ORDERS</h2>
            </div>
			<%
		
			if(alist1!=null && alist1.size() > 0)
			{ 
			for(int i=0;i<alist1.size();i++)
				{
					Hashtable ht1=(Hashtable)alist1.get(i);
					String totalorders=String.valueOf(ht1.get("count1"));
					String installedorders =String.valueOf(ht1.get("count2"));
					String confirmedorders=String.valueOf(ht1.get("count3"));
					String cancelledorders=String.valueOf(ht1.get("count4"));
					
			%>
            <div class="row">
                <div class="col-lg-3 col-md-3 col-sm-6 col-xs-12 clickMe" id="invertertotal">
                    <div class="info-box bg-indigo hover-expand-effect">
                        <div class="icon">
                            <i class="material-icons">equalizer</i>
                        </div>
                        <div class="content">
                            <div class="text">TOTAL</div>
                            <div class="number"  id="total" data-from="0" data-speed="15" data-fresh-interval="20"><%=totalorders%></div>
                        </div>
                    </div>
                </div>
                <div class="col-lg-3 col-md-3 col-sm-6 col-xs-12 clickMe" id="inverterinstalledtotal">
                    <div class="info-box bg-blue hover-expand-effect">
                        <div class="icon">
                            <i class="material-icons">flight_takeoff</i>
                        </div>
                        <div class="content">
                            <div class="text">INSTALLED</div>
                            <div class="number"  id="total" data-from="0" data-speed="15" data-fresh-interval="20"><%=installedorders%></div>
                        </div>
                    </div>
                </div>
                <div class="col-lg-3 col-md-3 col-sm-6 col-xs-12 clickMe" id="inverterpendingtotal">
                    <div class="info-box bg-light-blue hover-expand-effect">
                        <div class="icon">
                            <i class="material-icons">battery_charging_full</i>
                        </div>
                        <div class="content">
                           <div class="text">UNCONFIRMED</div>
                            <div class="number"  id="pending" data-from="0" data-speed="15" data-fresh-interval="20"><%=confirmedorders%></div>
                        </div>
                    </div>
                </div>
                <div class="col-lg-3 col-md-3 col-sm-6 col-xs-12 clickMe" id="inverterinprocesstotal">
                    <div class="info-box bg-cyan hover-expand-effect">
                        <div class="icon">
                            <i class="material-icons">brightness_low</i>
                        </div>
                        <div class="content">
							<div class="text">IN PROCESS</div>
                            <div class="number"  id="inprocess" data-from="0" data-speed="15" data-fresh-interval="20"><%=cancelledorders%></div>
                        </div>
                    </div>
                </div>
				<% }  } %>
            </div>
            <!-- #END# Hover Expand Effect -->
            <!-- Chart Samples -->
            <div class="block-header">
                <h2>SERVICE ORDERS</h2>
            </div>
			<%
		
			if(alist2!=null && alist2.size() > 0)
			{ 
			for(int i=0;i<alist2.size();i++)
				{
					Hashtable ht2=(Hashtable)alist2.get(i);
					String totalorders=String.valueOf(ht2.get("count1"));
					String installedorders =String.valueOf(ht2.get("count2"));
					String confirmedorders=String.valueOf(ht2.get("count3"));
					String cancelledorders=String.valueOf(ht2.get("count4"));
					
				
			%>
            <div class="row">
                <div class="col-lg-3 col-md-3 col-sm-6 col-xs-12 clickMe" id="servicetotal">
                    <div class="info-box bg-teal">
                        <div class="icon">
                            <i class="material-icons">flight_takeoff</i>
                        </div>
                        <div class="content">
                             <div class="text">TOTAL</div>
                            <div class="number"  id="total" data-from="0" data-speed="15" data-fresh-interval="20"><%=totalorders%></div>
                        </div>
                    </div>
                </div>
                <div class="col-lg-3 col-md-3 col-sm-6 col-xs-12 clickMe" id="serviceinstalledtotal">
                    <div class="info-box bg-green">
                         <div class="icon">
                            <i class="material-icons">done_all</i>
                        </div>
                        <div class="content">
                            <div class="text">INSTALLED</div>
                            <div class="number"  id="total" data-from="0" data-speed="15" data-fresh-interval="20"><%=installedorders%></div>
                        </div>
                    </div>
                </div>
                <div class="col-lg-3 col-md-3 col-sm-6 col-xs-12 clickMe" id="servicependingtotal">
                    <div class="info-box bg-blue-grey">
                         <div class="icon">
                            <i class="material-icons">done</i>
                        </div>
                        <div class="content">
                            <div class="text">UNCONFIRMED</div>
                            <div class="number"  id="total" data-from="0" data-speed="15" data-fresh-interval="20"><%=confirmedorders%></div>
                        </div>
                    </div>
                </div>
                <div class="col-lg-3 col-md-3 col-sm-6 col-xs-12 clickMe" id="serviceinprocesstotal">
                    <div class="info-box bg-deep-orange">
                        <div class="icon">
                            <i class="material-icons">cached</i>
                        </div>
                        <div class="content">
                            <div class="text">IN PROCESS</div>
                            <div class="number"  id="inprocess" data-from="0" data-speed="15" data-fresh-interval="20"><%=cancelledorders%></div>
                        </div>
                    </div>
                </div>
				<% }  } %>
            </div>
			      
        </div>
    </section>
	
 <input type="hidden" name="store_id" id="store_id" value="<%=strStoreId%>">
 <input type="hidden" name="store_name" id="store_name" value="<%=strStoreName%>">


<script type="text/javascript">

$(document).ready(function(){	
	$.ajax({
			type: "GET",
			url: "/bookbattery/servlet/BatteryStoreLogin?hidWhatToDo=storehome",
			//data: {},
			success: function(data){
				
				//alert(data); 
				//$("#price_details").html(data);
						
					 
			}
		}); 
});
 
$('.clickMe').click(function(){
	
	/*$.ajax({
		type: "GET",
		url: "/bookbattery/servlet/BatterystoreMISReports?hidWhatToDo=getmisreports",
		data: {},
		success: function(data){
			
			alert(data); 
			//$("#price_details").html(data);
					
				 
		}
	});*/
	
   // alert(this.id);
   if(this.id=="batterytotal"){
    	
    	location.replace("/bookbattery/jsp/BookBatterystore/dashboard/selectdetails.jsp");
    	
    } else if(this.id=="batteryinstalledtotal"){
    	
    	location.replace("/bookbattery/jsp/BookBatterystore/dashboard/selectinstalleddetails.jsp");
    	
    } else if(this.id=="batterypendingtotal"){
    	
    	location.replace("/bookbattery/jsp/BookBatterystore/dashboard/selectpendingdetails.jsp");
    	
    } else if(this.id=="batteryinprocesstotal"){
    	
    	location.replace("/bookbattery/jsp/BookBatterystore/dashboard/selectinprocessdetails.jsp");
    	
    } if(this.id=="invertertotal"){
    	
    	location.replace("/bookbattery/jsp/BookBatterystore/dashboard/inverterdetails.jsp");
    	
    } else if(this.id=="inverterinstalledtotal"){
    	
    	location.replace("/bookbattery/jsp/BookBatterystore/dashboard/inverterinstalleddetails.jsp");
    	
    } else if(this.id=="inverterpendingtotal"){
    	
    	location.replace("/bookbattery/jsp/BookBatterystore/dashboard/inverterpendingdetails.jsp");
    	
    } else if(this.id=="inverterinprocesstotal"){
    	
    	location.replace("/bookbattery/jsp/BookBatterystore/dashboard/inverterinprocessdetails.jsp");
    	
    }else   if(this.id=="servicetotal"){
    	
    	location.replace("/bookbattery/jsp/BookBatterystore/dashboard/servicedetails.jsp");
    	
    } else if(this.id=="serviceinstalledtotal"){
    	
    	location.replace("/bookbattery/jsp/BookBatterystore/dashboard/serviceinstalleddetails.jsp");

    } else if(this.id=="servicependingtotal"){
    	
    	location.replace("/bookbattery/jsp/BookBatterystore/dashboard/servicependingdetails.jsp");

    } else if(this.id=="serviceinprocesstotal"){
    	
    	//alert("prasanna");
    	location.replace("/bookbattery/jsp/BookBatterystore/dashboard/serviceinprocessdetails.jsp");
    	
    }


});
</script>
<jsp:include page = "storefooter.jsp" />		