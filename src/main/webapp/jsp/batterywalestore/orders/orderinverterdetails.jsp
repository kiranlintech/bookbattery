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
Vector alist=(Vector)session.getAttribute("InverterdetailsVector");

String invertertype = (request.getParameter("invertertype")!=null)?(request.getParameter("invertertype")):"0";
LogLevel.DEBUG(5,new Throwable(),"invertertype :"+invertertype ); 

String inverterbrand = (request.getParameter("inverterbrand")!=null)?(request.getParameter("inverterbrand")):"0";
LogLevel.DEBUG(5,new Throwable(),"inverterbrand :"+inverterbrand );

String invertercapacity = (request.getParameter("invertercapacity")!=null)?(request.getParameter("invertercapacity")):"0";
LogLevel.DEBUG(5,new Throwable(),"invertercapacity :"+invertercapacity );

if(city.equals("")){
	
	city = (request.getParameter("pincity")!=null)?(request.getParameter("pincity")):"0";
	LogLevel.DEBUG(5,new Throwable(),"city :"+city );

} else {
	
	city = (request.getParameter("city")!=null)?(request.getParameter("city")):"0";
	LogLevel.DEBUG(5,new Throwable(),"city :"+city );
}

String pincity = (request.getParameter("pincity")!=null)?(request.getParameter("pincity")):"0";
LogLevel.DEBUG(5,new Throwable(),"pincity :"+pincity );

String strarea = (request.getParameter("strarea")!=null)?(request.getParameter("strarea")):"0"; 
LogLevel.DEBUG(5,new Throwable(),"strarea :"+strarea );

String strstate = (request.getParameter("strstate")!=null)?(request.getParameter("strstate")):"0"; 
LogLevel.DEBUG(5,new Throwable(),"strstate :"+strstate );

String strpincode = (request.getParameter("strpincode")!=null)?(request.getParameter("strpincode")):"0"; 
LogLevel.DEBUG(5,new Throwable(),"strpincode :"+strpincode );

String sortpricess = (request.getParameter("sortpricess")!=null)?(request.getParameter("sortpricess")):"0"; 
LogLevel.DEBUG(5,new Throwable(),"sortpricess :"+sortpricess );

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
      <form name="orderinverterdetails" method="post">
		<div class="container-fluid">
             

            <!-- CKEditor -->
            <div class="row clearfix">
                <div class="col-md-12">
                    <div class="card">
                        <div class="header">
                            <h2>
                                VIEW INVERTER DETAILS 
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
									String strinverterbrand=String.valueOf(ht.get("inverter_brand"));
									String strInvertermodel=(String)ht.get("inverter_model");
									String strinvertercapacity=String.valueOf(ht.get("inverter_capacity"));
									String strinverterwarranty=(String)ht.get("inverter_warranty");
									String computer=(String)ht.get("computer");
									String fans=(String)ht.get("fans");
									String tubelights=(String)ht.get("tubelights");
									String television=(String)ht.get("television");
									String striconurl=(String)ht.get("icon_url");
									String strdesc=(String)ht.get("description");
									String stractprice=String.valueOf(ht.get("inverter_actual_price"));
									String strdiscountprice=String.valueOf(ht.get("inverter_discount_price"));
									String tdclass="tablebat1";
									String inverter_warrenty="";
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

										if(strinverterwarranty.indexOf("+")!=-1)
										{
											String[] inverterwarr = strinverterwarranty.split("\\+"); 
											mon = inverterwarr[0];
											dat = inverterwarr[1];
											inverter_warrenty = mon + "<br>" +dat;
										}
										else
										{
											inverter_warrenty = strinverterwarranty;
										}
										String strcomputer1="";
										String strcomputer2="";
										if(computer.indexOf(",")!=-1)
										{
											String[] strcomputer=computer.split(",");
											 strcomputer1=strcomputer[0];
											 strcomputer2=strcomputer[1];
										}
										else
										{
											strcomputer1=computer;
											strcomputer2="-";
										}
										String strfans1="";
										String strfans2="";
										if(fans.indexOf(",")!=-1)
										{
											String[] strfans=fans.split(",");
											 strfans1=strfans[0];
											 strfans2=strfans[1];
										}
										else
										{
											strfans1=fans;
											 strfans2="-";
										}
										String strtubelights1="";
										String strtubelights2="";

										if(tubelights.indexOf(",")!=-1)
										{
											String[] strtubelights=tubelights.split(",");
											 strtubelights1=strtubelights[0];
											 strtubelights2=strtubelights[1];
										}
										else
										{
											 strtubelights1=tubelights;
											 strtubelights2="-";
										}
											String strtelevision1="";
											String strtelevision2="";
										if(tubelights.indexOf(",")!=-1)
										{
											String[] strtelevison=television.split(",");
											strtelevision1=strtelevison[0];
											strtelevision2=strtelevison[1];
										}
										else
										{
											strtelevision1=television;
											strtelevision2="-";
										}

								int discountprice = Integer.parseInt(strdiscountprice);
								int actualprice = Integer.parseInt(stractprice);
								%>
									
										<div class="col-md-4" style="margin-bottom: 25px;">
											 <img align="center" style="width:65%;" src="<%=striconurl%>" ALT="battery"/>
											 <br/>
											 <strong><%=strinverterbrand%>&nbsp;<%=strInvertermodel%></strong> 
											 
 											<table style=" color: #494848;font-size: 13px;">
												<tbody>
												 <tr>
													  <th>Regular Price&nbsp;:</th>
													  <th>: <i class="icon-inr" aria-hidden="true"></i> <strike><%=actualprice%></strike> </th>
												  </tr>
												 <tr>
													  <th>Discount&nbsp;Price</th>
													  <th>: <i class="icon-inr" aria-hidden="true"></i> <%=discountprice%> </th>
												  </tr> 
												</tbody>
											</table>
												<button type="button" class="btn btn-primary waves-effect" onclick="javascript:viewinverterdetails('<%=strinverterbrand%>','<%=strInvertermodel%>','<%=strstate%>','<%=city%>','<%=strarea%>','<%=strpincode%>')">
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

function viewinverterdetails(inverterbrand, invertermodel, state, city, strarea, strpincode){
	 
	 
	document.orderinverterdetails.method="post";
	document.orderinverterdetails.action="../../../servlet/BatterystoreInverterDetails?hidWhatToDo=viewinverterdetails&inverterbrand="+inverterbrand+"&invertermodel="+invertermodel+"&state="+state+"&city="+city+"&area="+strarea+"&pincode="+strpincode;
	//alert(document.orderinverterdetails.action);
	document.orderinverterdetails.submit();
	
	
}

 </script>
<jsp:include page = "../storefooter.jsp" />
