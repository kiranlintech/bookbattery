<!--
    Document   : orderbatterydetails
    Created on : Oct 19, 2019, 4:22:12 PM
    Author     : C Prasanna Kumari.
-->
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
String city ="";
Vector alist=(Vector)session.getAttribute("BatdetailsVector");

String batterytype = (request.getParameter("batterytype")!=null)?(request.getParameter("batterytype")):"0";
LogLevel.DEBUG(5,new Throwable(),"batterytype :"+batterytype );

String vehiclemake = (request.getParameter("vehiclemake")!=null)?(request.getParameter("vehiclemake")):"0";
LogLevel.DEBUG(5,new Throwable(),"vehiclemake :"+vehiclemake );

String vehiclemodel = (request.getParameter("vehiclemodel")!=null)?(request.getParameter("vehiclemodel")):"0";
LogLevel.DEBUG(5,new Throwable(),"vehiclemodel :"+vehiclemodel );

String batterybrand = (request.getParameter("batterybrand")!=null)?(request.getParameter("batterybrand")):"0";
LogLevel.DEBUG(5,new Throwable(),"batterybrand :"+batterybrand );

if(city.equals("")){
	
	city = (request.getParameter("pincity")!=null)?(request.getParameter("pincity")):"0";
	LogLevel.DEBUG(5,new Throwable(),"city :"+city );

} else {
	
	city = (request.getParameter("city")!=null)?(request.getParameter("city")):"0";
	LogLevel.DEBUG(5,new Throwable(),"city :"+city );
}

String strpincity = (request.getParameter("pincity")!=null)?(request.getParameter("pincity")):"0";
LogLevel.DEBUG(5,new Throwable(),"strpincity :"+strpincity );


String strarea = (request.getParameter("strarea")!=null)?(request.getParameter("strarea")):"0"; 
LogLevel.DEBUG(5,new Throwable(),"strarea :"+strarea );

String strstate = (request.getParameter("strstate")!=null)?(request.getParameter("strstate")):"0"; 
LogLevel.DEBUG(5,new Throwable(),"strstate :"+strstate );

String strpincode = (request.getParameter("strpincode")!=null)?(request.getParameter("strpincode")):"0"; 
LogLevel.DEBUG(5,new Throwable(),"strpincode :"+strpincode );

Properties propsMOPConfig = new Properties();
ServletContext context = getServletContext();
FileInputStream fin1      = new FileInputStream(new File(context.getRealPath("properties/bookbatteryconfig.properties"))); 
propsMOPConfig.load(fin1); 
fin1.close(); 
String publicUrl = (propsMOPConfig.getProperty("publicUrl")!=null)?propsMOPConfig.getProperty("publicUrl"):"";
String baseURL = (propsMOPConfig.getProperty("baseURL")!=null)?propsMOPConfig.getProperty("baseURL"):"";
String baseurldirectory = (propsMOPConfig.getProperty("baseurldirectory")!=null)?propsMOPConfig.getProperty("baseurldirectory"):"";
String serverURL = (propsMOPConfig.getProperty("serverURL")!=null)?propsMOPConfig.getProperty("serverURL"):"";
  
%>
<jsp:include page = "../storeheader.jsp" />
<jsp:include page = "../storeleftmenu.jsp" />
  <section class="content">
      <form name="orderbatterydetails" method="post">
		<div class="container-fluid">
             

            <!-- CKEditor -->
            <div class="row clearfix">
                <div class="col-md-12">
                    <div class="card">
                        <div class="header">
                            <h2>
                                VIEW BATTERY DETAILS 
                            </h2>
                            
                        </div>
                        <div class="body">
							<div class="row">
								<%
								if(alist!=null && alist.size() > 0)
								{
								%>
								<%
								try
								{
									for ( int i=0; i<alist.size() ; i++)
									{

										Hashtable ht=(Hashtable)alist.get(i);
										String appmapid=String.valueOf(ht.get("map_id"));
										String strbatbrand=String.valueOf(ht.get("bat_brand"));
										String strBatmodel=(String)ht.get("bat_model");
										String strbatcap=String.valueOf(ht.get("bat_capacity"));
										String strbatwarr=(String)ht.get("bat_warranty");
										String striconurl=(String)ht.get("icon_url");
										String strdesc=(String)ht.get("description");
										String stractprice=String.valueOf(ht.get("bat_act_price"));
										String strdiscountprice=String.valueOf(ht.get("bat_witbat_price"));
										String strbattretprice=String.valueOf(ht.get("bat_ret_price"));
										String bat_warrenty="";
										String mon="";
										String dat="";
										if(striconurl == null)
										{
											striconurl = "../../../images/noimage.jpg";
										}
										else
										{
											striconurl =striconurl;
										}
										if(strbatwarr.indexOf("+")!=-1)
										{
											String[] batwarr = strbatwarr.split("\\+"); 
											mon = batwarr[0];
											dat = batwarr[1];
											bat_warrenty = mon + "<br>" +dat;
										}
										else
										{
											bat_warrenty = strbatwarr;
										}

									int strdisprice = Integer.parseInt(strdiscountprice);
									LogLevel.DEBUG(5,new Throwable(),"strdisprice :"+strdisprice );

									int strwitbatprice = Integer.parseInt(strbattretprice);
									LogLevel.DEBUG(5,new Throwable(),"strwitbatprice :"+strwitbatprice );

									int strwithoutprice = strdisprice - strwitbatprice;
									LogLevel.DEBUG(5,new Throwable(),"dddddddhhhhh :"+strwithoutprice );
								%>
									
										<div class="col-md-4" style="margin-bottom: 25px;">
											 <img align="center" style="width:65%;" src="<%=striconurl%>" ALT="battery"/>
											 <br/>
											 <strong><%=strbatbrand%>&nbsp;<%=strBatmodel%>(&nbsp;<%=strbatcap%>&nbsp;)</strong> 
											 <h5>Warranty : <%=bat_warrenty%></h5>
 											<table style=" color: #494848;font-size: 13px;">
												<tbody>
												 <tr>
													  <td>Regular Price&nbsp;:</td>
													  <td>: <i class="icon-inr" aria-hidden="true"></i> <strike><%=stractprice%></strike> </td>
												  </tr>
												 <tr>
													  <td>With Out Old Battery Price</td>
													  <td>: <i class="icon-inr" aria-hidden="true"></i> <%=strdiscountprice%> </td>
												  </tr>
												  <tr>
													  <td>With Old Battery Price</td>
													  <td>: <i class="icon-inr" aria-hidden="true"></i> <%=strwithoutprice%> </td>
												  </tr>
												</tbody>
											</table>
												<button type="button" class="btn btn-primary waves-effect" onclick="javascript:viewproductdetails('<%=strbatbrand%>','<%=strBatmodel%>','<%=batterytype%>','<%=strstate%>','<%=city%>','<%=strarea%>','<%=strpincode%>')">
													<i class="material-icons">trending_up</i>
													<span>View More</span>
												</button>
												
							 
										</div> 
										
								<%
								}
								}
								catch(Exception e)
								{
										e.printStackTrace();
								}
								%>  <!-----End of Try---->
								
								<%
								}
								else
								{
								%>
								 No Battery details found based on selection.! 
								 
								<%
								}
								%>
								</div>
								
							
                        </div>
                    </div>
                </div>
            </div>
            <!-- #END# CKEditor -->
            
        </div>
    </form>
    </section>

<input type="hidden" name="store_id" id="store_id" value="<%=strStoreId%>">
<input type="hidden" name="store_name" id="store_name" value="<%=strStoreName%>">
<script type="text/javascript">

function viewproductdetails(battery_brand, bat_model, batterytype, state, city, strarea, strpincode){
	 
	 
	document.orderbatterydetails.method="post";
	document.orderbatterydetails.action="../../../servlet/BatterystoreBatteryDetails?hidWhatToDo=viewbatdetails&batterytype="+batterytype+"&bat_model="+bat_model+"&batterybrand="+battery_brand+"&state="+state+"&city="+city+"&area="+strarea+"&pincode="+strpincode;
	//alert(document.orderbattery.action);
	document.orderbatterydetails.submit();
	
	
}

 </script>
<jsp:include page = "../storefooter.jsp" />
