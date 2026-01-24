<%-- 
Document   : displaymap.jsp
Created on	: May 5, 2015, 10:10:12 AM
Author		: Pravallika S
--%>
<%@ page language="java" import="java.sql.*"%>
<%@page language="java" import="com.ngit.javabean.loglevel.*,java.util.Date,java.util.Properties,javax.servlet.ServletContext,java.util.Properties,java.io.FileInputStream"%>

<%response.setHeader("Pragma","no-cache"); response.setHeader("Cache-Control","no-store"); response.setHeader("Expires","0"); response.setDateHeader("Expires",-1); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

<link rel="stylesheet" href="/services/css/bootstrap.min.css" />
 <link rel="stylesheet" href="/services/css/bootstrap-multiselect.css" />

<!-- Including Open Sans Condensed from Google Fonts -->
<link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Open+Sans+Condensed:300,700,300italic" />


<title>Neeru Chettu</title>
<meta name='Title' content='Neeru Chettu' />
<meta name='description' content="Neeru Chettu" />
<meta name='keywords' content='Neeru Chettu' />

<script src="/services/js/jquery.min.js"></script>
<script src="/services/js/bootstrap.min.js"></script>
<script src="/services/js/bootstrap-multiselect.js"></script>



<body >
<form name="displaymap" action="">

<table>
<tr>
				<!-- Code starts here to show multiple select check box-->					
				
				<td id ='checkdams'  width="20%" align="left" valign="middle">
				<select id="lstFruits1" multiple="multiple">
				<option value="Restoration of Check Dams / Check Walls">Restoration of Check Dams / Check Walls</option>
				<option value="Check Dam / Check Wall">Check Dam / Check Wall</option>
				</select>
				</td>
				<!-- Code ends here to show multiple select check box-->
			
				</tr>                        
				<!-- footer Ends Here -->
</table>

</form>

<script type="text/javascript">


    $(function () {
        $('#lstFruits1').multiselect({
            includeSelectAllOption: true
        });
			 $('#lstFruits2').multiselect({
            includeSelectAllOption: true
        });
			 $('#lstFruits3').multiselect({
            includeSelectAllOption: true
        });
			 $('#lstFruits4').multiselect({
            includeSelectAllOption: true
        });
			 $('#lstFruits5').multiselect({
            includeSelectAllOption: true
        });
			 $('#lstFruits6').multiselect({
            includeSelectAllOption: true
        });
    });


   
</script>
</body>
</html>
