<%-- 
	Author(s)			: Ajay K
	Created on			: Friday, January 17, 2014 1:06:45 PM
	Copyright Notice	: BookBattery Pvt.Ltd. Confidential
	Description			: chat agents login.
--%>
<%@page language="java" import="java.io.File,java.util.Properties,java.io.FileInputStream,javax.servlet.ServletContext"%>
<%
String agentemail=(String)session.getAttribute("seschatagentemail");
String agentid=(String)session.getAttribute("seschatagentid");
String agentname=(String)session.getAttribute("seschatagentname");
if(agentemail==null)
{
	agentemail="";
	session.setAttribute("sesErrorMsg","Session Timed Out. Please login again");
	response.sendRedirect("../../supportlogin/index.html");
	return;
}
String sesErrorMsg=(String)session.getAttribute("seschatagentErrorMsg");
if(sesErrorMsg==null)
{
	sesErrorMsg="";
}
else
{
	session.removeAttribute("seschatagentErrorMsg");
}
Properties propslivechatConfig = new Properties();
ServletContext context = getServletContext();
FileInputStream fin1      = new FileInputStream(new File(context.getRealPath("properties/bookbatteryconfig.properties"))); 
propslivechatConfig.load(fin1); 
fin1.close(); 
String adminlogo = (propslivechatConfig.getProperty("adminlogo")!=null)?propslivechatConfig.getProperty("adminlogo"):"";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<link href="../../css/livechat.css" rel="stylesheet" type="text/css"/>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<link rel="shortcut icon" href="../../images/favicon.png" type="image/x-icon">
<style type="text/css">
#chattab{position: fixed;direction:rtl;width: auto;bottom: 0px;right:10px;z-index:10000;}
ul.chatdivul {list-style-type:none;margin:0px;padding-left:0px;padding-right:0px;padding-top:3px;padding-bottom:5px;}
ul.chatdivul li{display:block;max-width:280px;padding-left:7px;padding-right:7px;}
ul.chatdivul li:hover{background-color:#3B5998;color:#FFFFFF;}
div.achattext {word-wrap:break-word;width:100%;min-height: 30px;max-height: 70px;cursor:text;font: 11px 'Trebuchet MS', Verdana, Arial;outline:none;background:#FFFFFF; display: inline-block;}
</style>
<title>Live Chat Agent Login</title>
<meta name="keywords" content="Live Chat Agent Login"/>
<meta name="description" content="Live Chat Agent Login"/>
<script language="JavaScript" src="../../js/jquery-1.3.2.min.js" ></script>
<script language="JavaScript" src="../../js/pophide.js" ></script>
<script language="JavaScript" src="../../js/livechat-cookie.js"></script>
</head>
<body onload="inactivate();">
<!-- Start Alexa Certify Javascript -->
<script type="text/javascript">
_atrk_opts = { atrk_acct:"VrG0j1a4ZP00yt", domain:"BookBattery.com",dynamic: true};
(function() { var as = document.createElement('script'); as.type = 'text/javascript'; as.async = true; as.src = "https://d31qbv1cthcecs.cloudfront.net/atrk.js"; var s = document.getElementsByTagName('script')[0];s.parentNode.insertBefore(as, s); })();
</script>
<noscript><img src="https://d5nxst8fruw4z.cloudfront.net/atrk.gif?account=VrG0j1a4ZP00yt" style="display:none" height="1" width="1" alt="" /></noscript>
<!-- End Alexa Certify Javascript -->
<center>
<form name="agentsupport"  method="post">
<div id='scrollpopup' class='scrollpopup' style="display:none;"></div>
<table width="960" border="0" align="center" cellpadding="0" cellspacing="0">
	<tr>
		<td align="left" valign="top">
			<table width="960" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td>
						<IMG SRC="<%=adminlogo%>" WIDTH="170" HEIGHT="50" BORDER="0" ALT="logo">
					</td>
					<td align='right' class="subheading">Chat Agent Support</td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td align='right' class="subheading"><INPUT TYPE="button" style='cursor:pointer;' class="button4" VALUE="Logout" ONCLICK="javascript:chatagentlogout();"></td>
	</tr>
	<tr>
		<td width="960"><hr width="100%" align="center" size="1" color="#BEADCB"></td>
   </tr>
	<tr>
		<td align="left" valign="top" bgcolor="#FFFFFF">
			<table width="960" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="180" height="438" align="left" valign="top">
						<table width="180" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td width="680" align="left" valign="top" >
									<table width="513" border="0" cellspacing="0" cellpadding="0">
										<!-- Inner content should be within the below table -->
										<tr>
											<td>
												<table width="955" border="0" valign="top"cellspacing="0" cellpadding="0" bgcolor="#FFFFFF" height="430">
													<tr height="5"><td><font face='verdana' size='2' color='#ff8c00'>Welcome to <%=agentname%> !</font></td></tr>
													<tr height="15"></tr>
													
													<tr>
														<td>

															<table width="160" border="0" align="center" cellspacing="0" cellpadding="2">
																<tr bgcolor="#FFFFFF">
																	<td class="content" colspan="6" width="100%" height="20">
																		<table align="right"  border="0" cellpadding="2" cellspacing="0">
																			<tr height="20"></tr>
																		</table>
																	</td>
																</tr>
															</table>
														</td>
													</tr>
												</table>
											</td>
										</tr>
										<tr height="10">
											<td colspan="4">&nbsp;</td>
										</tr>
									</table>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</td>
		<td width="40" align="right" valign="top"></td>
	</tr>
</table>
<INPUT TYPE="hidden" NAME="agentemail" id='agentemail' value="<%=agentemail%>">
<INPUT TYPE="hidden" NAME="agentid" id='agentid' value="<%=agentid%>">
<INPUT TYPE="hidden" NAME="agentname" id='agentname' value="<%=agentname%>">
</body>
<div id="chattab"><div id="chatdivmax" style="display:inline-block;direction:rtl;"></div><div id="chatdivcont" style="direction:rtl;display:inline-block;margin-left:3px;margin-right:3px;color:#404040 ;position:relative;zindex:9999;"><div id="maxchatlist" style="display:none;direction:ltr;"><ul id="maxchatui" class="chatdivul"></ul></div><div id="minchatlist" style="display:none; border: 1px solid #6f2c9b;background-color: #FFFFFF;direction:ltr;position:relative;width:160px;z-index:100;"><ul id="chatui" class="chatdivul"></ul></div><div  id="chatmincount" style="display:none;direction:ltr;border: 1px solid #CCCCCC;background-color:#E6E9F2;cursor:pointer; width:40px;margin-left:120px; " onclick='togglechatmindiv();'><div id="l_box" ></div><div id="ulChatCountContainer" style='display:none;width:20px;position:absolute;right:10px;height:16px;top:-12px;border:1px solid #FF66FF;background-color:#FF99FF;text-align:center;'><span  id="ulChatCount"><b>0</b></span></div><span id="minchatcount"  style="display:inline-block;padding-left:25px;padding-top:2px;width:10px;height:18px;"></span></div></div></div>
<script type="text/javascript">
function chatagentlogout()
{
	var agentemail=document.getElementById("agentemail").value;
	var agentid=document.getElementById("agentid").value;
	document.agentsupport.method="post";
	document.agentsupport.action="../../servlet/LiveChatManagement?hidWhatToDo=chatagentlogout&email="+agentemail+"&agentid="+agentid;
	document.agentsupport.submit();
}

$(window).load(function() 
{

	var setchatuserids=$.cookie('setchatids');
	if(setchatuserids==undefined || setchatuserids=="undefined")
	{
		displaychatdiv()
	}
	else
	{	
		if(setchatuserids.indexOf("null")>=0)
		{
		
		}
		else
		{
			setchatuserids=setchatuserids.split(",");
			onRefreshDisplayDivs(setchatuserids)	
			self.setTimeout(function(){displaychatdiv()},6000);
		}		
	}
});
$(window).load(function() 
{		
	self.setTimeout(function(){livechattriggermsg()},6000);
	self.setTimeout(function(){inactivate()},180000);
});
var id;
function onRefreshDisplayDivs(setchatids)
{
	var agentid=document.getElementById("agentid").value;
	var ids=setchatids;
	var xmlhttp;
		var url="../../servlet/LiveChatManagement?hidWhatToDo=fetchchathistoryagents&agentid="+agentid+"&userid="+ids+"&requestno="+(Math.random()*100);	
		if (window.XMLHttpRequest)
		{	
			xmlhttp=new XMLHttpRequest();		
		}
		else
		{	
			xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
		}			
		xmlhttp.onreadystatechange=function()
		{
			if (xmlhttp.readyState==4 && xmlhttp.status==200)
			{
					res=xmlhttp.responseText;	
						if($('#fbchatdiv').is(':visible'))
						{
							$('#fbchatdiv').show();								
						}
					document.getElementById("chatdivmax").innerHTML+=res;
					$('#chatdivmax').show();
					$("#chatdivmax").scrollTop = $("#chatdivmax").scrollHeight;								
			}
		}	
			xmlhttp.open("GET",url,true);
			xmlhttp.send();
			
}
var setchatids=0;
function displaychatdiv()
{
	var agentid=document.getElementById("agentid").value;
	var n = $( ".fbchatdiv" ).length;
	var xmlhttp;
	var url="../../servlet/LiveChatManagement?hidWhatToDo=fetchusers&count="+n+"&agentid="+agentid+"&requestno="+(Math.random()*100);	
	if (window.XMLHttpRequest)
	{	xmlhttp=new XMLHttpRequest();		
	}
	else
	{	
	    xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
	}
	xmlhttp.onreadystatechange=function()
	{
		if (xmlhttp.readyState==4 && xmlhttp.status==200)
		{
			res=xmlhttp.responseText;	
			if(res.indexOf("[]")>=0)
			{
			}
			else
			{
				res=res.split("|");
				for(p=0;p<res.length;p++)
				{
					var getdetl=res[p];
					getdetl=getdetl.split(",");
					var id=getdetl[1];
					id=id.replace(/^\s+|\s+$/g,'');
					if($("#"+id+"_ChatHeader" ).is(':visible'))
					{

					}
					else
					{
						var userName=getdetl[0];
						var newDiv = document.createElement('div');
						newDiv.setAttribute("id", ""+id);
						var setchatuserids=$.cookie('setchatids');
						if(setchatuserids==undefined || setchatuserids=="undefined")
						{
							if(setchatids==0)
							{
								$.cookie('setchatids',  "",{path:'/'});
								setchatids=id;
								$.cookie('setchatids',  setchatids,{path:'/'});
							}
							else
							{
								$.cookie('setchatids',  "",{path:'/'});
								setchatids=setchatids+","+id;
								$.cookie('setchatids', setchatids,{path:'/'});
							}	
						}
						else
						{	
							$.cookie('setchatids',  "",{path:'/'});
							setchatids=setchatuserids+","+id;
							$.cookie('setchatids', setchatids,{path:'/'});

						}
						
						if($('#fbchatdiv').is(':visible'))
						{
							$('#fbchatdiv').show();								
						}
						newDiv.setAttribute("class", "fbchatdiv");
						newDiv.setAttribute('style','direction:ltr;display:inline-block;margin-left:2px;margin-right:2px;position:relative;width:240px;');
						newDiv.innerHTML = "<div id='"+id+"_ChatCountContainer' style='display:none;width:20px;position:absolute;right:35px;height:16px;top:-5px;border:1px solid #FF66FF;background-color:#FF99FF;text-align:center;'><span  id='"+id+"_ChatCount' >0</span></div><div id='"+id+"_ChatHeader' align='left' style='width:230px;border: 1px solid #CCCCCC;background-color:#627BAE;padding:2px 5px 2px 5px;cursor:pointer;font: 11px 'Trebuchet MS', Verdana, Arial;'><span style='display:inline-block;width:200px;white-space:nowrap;overflow-x:hidden;'><font color='white' face='verdana' size='2'><b>"+userName+"</b></font></span><span id='"+id+"_MsgSpan' style='display:none;'></span><span onclick='aChatdivClose(\""+id+"\");'style='float:right;' ><font color='white' face='verdana' size='2'><b>x</b></font></span></div><div id='"+id+"_Chatlist' style='width:240px;min-height:230px;max-height:230px;overflow-y:hidden;overflow-x:hidden;padding-right:15px;'><div id='"+id+"_ChatMessages' style='text-align:left;word-wrap:break-word;background-color:#EDEFF4;border-left:1px solid #CCCCCC;border-right: 1px solid #CCCCCC;width:240px;min-height:196px;max-height:196px;overflow-y:auto;overflow-x:hidden;padding-top:15px;font: 11px 'Trebuchet MS', Verdana, Arial;'></div>"+"<div align='left' style='border:1px solid #CCCCCC; background:#red;min-height: 30px;max-height: 50px;overflow-x: hidden;overflow-y: auto;outline:none;width:100%;'><div id='chatmessagetext' style='height:30px;width:95%;'><textarea  rows='1'  id='"+id+"_ChatText'"+" type='text' style=' border:0px;height:30px;width:97%; overflow-y:auto;' onkeypress='return checkForEnterA(event,"+id+",\""+userName+"\")'/></textarea></div></div></div></div>";
						$("#chatdivmax").append(newDiv);
						$(id+"_ChatMessages").scrollTop = $(id+"_ChatMessages").scrollHeight;
					}
				}
			}
			self.setTimeout(function(){displaychatdiv()},3000);

		}
	}
	xmlhttp.open("GET",url,true);
	xmlhttp.send();	
}

function aChatdivClose(id)
{

		var agree=confirm("If you close the chat window, entire chat of the consumer will be lost");
		if (agree)
		{
			var xmlhttp;
			var url="../../servlet/LiveChatManagement?hidWhatToDo=closediv&id="+id+"&requestno="+(Math.random()*100);	
			if (window.XMLHttpRequest)
			{	xmlhttp=new XMLHttpRequest();		
			}
			else
			{	
				xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
			}
			xmlhttp.onreadystatechange=function()
			{
				if (xmlhttp.readyState==4 && xmlhttp.status==200)
				{
					var chatid=id;
					var setchatuserids=$.cookie('setchatids');
					 var splitsetchatuserids=setchatuserids.split(",");
					
					if(splitsetchatuserids.length>1)
					{
						 var index = splitsetchatuserids.indexOf(chatid);
						if(index >= 0) 
						{
							 splitsetchatuserids.splice(index, 1);
						}					
						$.cookie('setchatids', "undefined",{path:'/'});
						$.cookie('setchatids', splitsetchatuserids,{path:'/'});
						
					}
					else
					{
						$.cookie('setchatids', "undefined",{path:'/'});
					}
					$("#"+chatid).remove();
				}
			}
			xmlhttp.open("GET",url,true);
			xmlhttp.send();	
		}
}
function checkForEnterA(e,id,userName)
{
   var e = e || event;
    var key = e.keyCode || e.charCode;
    if(key == 13)
    {
        livechatinsert(id,userName);
	return false;
    }
	else
	{
		return true;
	}

}
function livechatinsert(id,userName)
{
	
	var agentid=document.getElementById("agentid").value;
	var cmsg = document.getElementById(id+'_ChatText').value;
	
	var agentname=document.getElementById("agentname").value;
	if(cmsg.length>4000)
	{
			alert("Yor message length has exceeded limit. Please Reduce.");		
			document.getElementById(id+"_ChatText").focus();		
			return ;
	}
	if(cmsg!="")
	{

		var agentmsg=escape(cmsg);
		agentmsg=agentmsg.replace(/\+/g, '%2B');
	
	var chatArea = document.getElementById(id+"_ChatMessages");
	var xmlhttp;
	var url="../../servlet/LiveChatManagement?hidWhatToDo=agentmsg&cmsg="+agentmsg+"&id="+id+"&usrname="+userName+"&agentid="+agentid+"&agentname="+agentname+"&requestno="+(Math.random()*100);	
	if (window.XMLHttpRequest)
	{	xmlhttp=new XMLHttpRequest();		
	}
	else
	{	
	    xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
	}
	xmlhttp.onreadystatechange=function()
	{
		if (xmlhttp.readyState==4 && xmlhttp.status==200)
		{
			document.getElementById(id+'_ChatText').value="";
		
				var replacePattern1 = /(\b(https?|ftp):\/\/[-A-Z0-9+&@#\/%?=~_|!:,.;]*[-A-Z0-9+&@#\/%=~_|])/gim;
    			cmsg = cmsg.replace(replacePattern1, '<a href="$1" target="_blank">$1</a>');
    			var replacePattern2 = /(^|[^\/])(www\.[\S]+(\b|$))/gim;
    			cmsg = cmsg.replace(replacePattern2, '$1<a href="http://$2" target="_blank">$2</a>');
    			var replacePattern3 = /(\w+@[a-zA-Z_]+?\.[a-zA-Z]{2,6})/gim;
    			cmsg = cmsg.replace(replacePattern3, '<a href="mailto:$1">$1</a>');
				

			$(chatArea).append( "<table cellspacing='0' width='100%'  cellpadding='0' border='0' style='padding-bottom:15px;'><tr><td class='insidechatcontent' style='word-wrap: break-word;' valign='top'><b>"+agentname+":</b>"+cmsg+"</td></tr></table>" );
			chatArea.scrollTop = chatArea.scrollHeight;

		}
	}
	xmlhttp.open("GET",url,true);
	xmlhttp.send();
	}

}
var achatid;
function livechattriggermsg()
{	
	var agentid=document.getElementById("agentid").value;
	var cookieids=$.cookie('setchatids');
	var xmlhttp;
	var url="../../servlet/LiveChatManagement?hidWhatToDo=afetchmsg&cookieids="+cookieids+"&agentid="+agentid+"&achatid="+achatid+"&requestno="+(Math.random()*100);	
	if (window.XMLHttpRequest)
	{xmlhttp=new XMLHttpRequest();		
	}
	else
	{	
	    xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
	}
	xmlhttp.onreadystatechange=function()
	{
		if (xmlhttp.readyState==4 && xmlhttp.status==200)
		{
			var resps=xmlhttp.responseText;
			
				resps=resps.split("||");
				var achatRes=resps[0];
				
				var secondres=resps[1];
				if(secondres!=undefined)
				{
					removediv(secondres)
				}
				if(achatRes.indexOf("firstchatresult")>=0)
				{
					achatRes=achatRes.split(":");
					achatid=achatRes[1];
				}
				else if(achatRes.indexOf("[]")>=0)
				{				
				}
				else
				{
					achatRes=achatRes.split("|");
					for(a=0;a<achatRes.length;a++)
					{
						var result=achatRes[a].split(",");
					
						achatid=result[1];

						var replacePattern1 = /(\b(https?|ftp):\/\/[-A-Z0-9+&@#\/%?=~_|!:,.;]*[-A-Z0-9+&@#\/%=~_|])/gim;
    						result[2] = result[2].replace(replacePattern1, '<a href="$1" target="_blank">$1</a>');
    						var replacePattern2 = /(^|[^\/])(www\.[\S]+(\b|$))/gim;
    						result[2] = result[2].replace(replacePattern2, '$1<a href="http://$2" target="_blank">$2</a>');
    						var replacePattern3 = /(\w+@[a-zA-Z_]+?\.[a-zA-Z]{2,6})/gim;
    						result[2] = result[2].replace(replacePattern3, '<a href="mailto:$1">$1</a>');

						var chatArea = document.getElementById(result[0]+"_ChatMessages");
						$(chatArea).append( "<table cellspacing='0' cellpadding='0' border='0' width='100%' style='padding-bottom:15px;table-layout:fixed;'><tr><td class='insidechatcontent' style='word-wrap: break-word;' valign='top'><b>"+result[3]+":</b>"+result[2]+"</td></tr></table>" );
						chatArea.scrollTop = chatArea.scrollHeight;	
					}
				}			
			self.setTimeout(function(){livechattriggermsg()},3000);
		}
	}
	xmlhttp.open("GET",url,true);
	xmlhttp.send();
}
function removediv(ids)
{
	if(ids.indexOf("|")>=0)
	{
		ids=ids.split("|");
		for(s=0;s<ids.length;s++)
		{
			
			ids[s] = ids[s].replace(/^\s+|\s+$/g,'');
			if(ids[s]=="")
			{	
							
			}
			else
			{
				var chatid=ids[s];
				var setchatuserids=$.cookie('setchatids');
				 var splitsetchatuserids=setchatuserids.split(",");
				if(splitsetchatuserids.length>1)
				{
					 var index = splitsetchatuserids.indexOf(chatid);
					if(index >= 0) 
					{
						 splitsetchatuserids.splice(index, 1);
					}					
					$.cookie('setchatids', "undefined",{path:'/'});
					$.cookie('setchatids', splitsetchatuserids,{path:'/'});
					
				}
				else
				{
					$.cookie('setchatids', "undefined",{path:'/'});
				}
		
				$("#"+chatid).remove();

			}		
			
		}
		
	}
	else
	{
			
	}	
}
function responsechat()
{
	var lchatvalue=$("#lchat").val();
	$("#lchat").val('');
	var exdata=$(livechatArea).html();
	exdata=exdata.split("<table");
	var leng=exdata.length-1;
	var xmlhttp;
	var url="../../servlet/LiveChatManagement?hidWhatToDo=insertsupport&customername="+name+"&email="+email+"&chatmsgvalue="+lchatvalue+"&chatemail="+name+"&cname="+cname+"&requestno="+(Math.random()*100);	
	if (window.XMLHttpRequest)
	{	xmlhttp=new XMLHttpRequest();		
	}
	else
	{	
	    xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
	}
	xmlhttp.onreadystatechange=function()
	{
		if (xmlhttp.readyState==4 && xmlhttp.status==200)
		{
			var livechatArea = document.getElementById("livechatdiv");
livechatArea.innerHTML+="<table id='userdetllls' width='100%' bgcolor='#F0F8FF' style='padding-bottom:2px;'><tr><td align='left'>"+lchatvalue+"</td></tr></table>";
			livechatArea.scrollTop = livechatArea.scrollHeight;		

		}
	}
	xmlhttp.open("GET",url,true);
	xmlhttp.send();
}
function inactivate()
{
	var xmlhttp;
	var url="../../servlet/LiveChatManagement?hidWhatToDo=inactivate&requestno="+(Math.random()*100);	
	if (window.XMLHttpRequest)
	{	xmlhttp=new XMLHttpRequest();		
	}
	else
	{	
	    xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
	}
	xmlhttp.onreadystatechange=function()
	{
		if (xmlhttp.readyState==4 && xmlhttp.status==200)
		{
			self.setTimeout(function(){inactivate()},180000);

		}
	}
	xmlhttp.open("GET",url,true);
	xmlhttp.send();	
}

</script>
</html>
