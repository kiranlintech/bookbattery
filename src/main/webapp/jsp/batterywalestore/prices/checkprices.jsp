
<%@ page language="java" import="java.sql.*"%>
<%@page language="java" import="java.io.File,java.text.*,java.util.Calendar,java.util.ArrayList,java.util.Hashtable,java.util.Vector,com.ngit.javabean.loglevel.*,java.util.Date,java.util.Properties,java.util.*,com.ngit.javabean.loglevel.LogLevel,java.util.Properties,java.io.FileInputStream"%>
<%
String strUserid=(String)session.getAttribute("sesStoreOperatorName");
if(strUserid==null)
{
	strUserid="";
	session.setAttribute("sesErrorMsg","Session Timed Out. Please login again");
	response.sendRedirect("../../../BookBatterystore/index.html");
	return;
}
%>
<title>BookBattery - Store Prices</title>
<jsp:include page = "../storeheader.jsp" />
<jsp:include page = "../storeleftmenu.jsp" />
<section class="content">
        <div class="container-fluid">
            <!-- Advanced Select -->
            <div class="row clearfix">
                <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
                    <div class="card">
                        <div class="header bg-teal">
                            <h2>
                               BATTERY PRICE DETAILS
                            </h2>
                        </div>
                        <div class="body">
                         
                            <div class="row clearfix" id="price_details"></div>
						</div>
                    </div>
                </div>
            </div>
            <!-- #END# Advanced Select -->
        </div>
    </section>
<!-- Small Size -->
<div class="modal fade" id="defaultModal" tabindex="-1" role="dialog">
   
</div>	
<jsp:include page = "../storefooter.jsp" />		
<script type="text/javascript">

$(document).ready(function(){	
	$.ajax({
			type: "GET",
			url: "/bookbattery/servlet/BatterystoreBatteryDetails?hidWhatToDo=getbatterydetails",
			//data: {},
			success: function(data){
				
				//alert(data);
				 
				$("#price_details").html(data);
						
					 
			}
		}); 
});
 
</script>