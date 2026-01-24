/* ********************************************************************\ 
	Author(s)				:	Sai Krishna Daddala
	Date						:	Thursday, March 25, 2014 6:38:12 PM
	Copyright Notice	:	NGIT Pvt.Ltd. Confidential.
	Description			:	To perform support live chat.
\* *******************************************************************/ 


$(window).load(function() 
{
	var batterywalecookieids=$.cookie('batterywalelivechatcookie');
	if(batterywalecookieids!=undefined)
	{
		if(batterywalecookieids.indexOf("null")>=0)
		{
			
		}
		else
		{
			batterywalecookieids=batterywalecookieids.split("|");
			onrefreshdis(batterywalecookieids[0],batterywalecookieids[1],batterywalecookieids[2],batterywalecookieids[3],batterywalecookieids[4],batterywalecookieids[5],batterywalecookieids[6],batterywalecookieids[7],batterywalecookieids[8],batterywalecookieids[9],batterywalecookieids[10],batterywalecookieids[11])
		}
	}
});
var caname="",agentid="",useremail="",username="",loadmsg="",usrid="",loadchathistory="",welcomemsg="",cagentName="";
//function used to maximize chat window
function openchatwindow()
{
	welcomemsg=document.getElementById("welcomemsg").value;
	$("#livechaticon").hide();
	$("#chatagentdiv").show();
	$("#headerdiv").hide();
	$("#batterydetails").hide();
}
function openchatwindowwaiting()
{
	welcomemsg=document.getElementById("welcomemsg").value;
	$("#livechaticon").hide();
	$("#chatagentdiv").show();
	$("#logindetails").show();
	
}
//function used to maximize chat window
function minchatwindow()
{
	$("#livechaticon").show();
	$("#logindetails").hide();
	$("#chatagentdiv").hide();
}
//Key press event for login chat function
function loginchatKeyPress(e)
{
	if (window.event)
	{
		e = window.event;
	}
	if (e.keyCode == 13)
	{
		loginintochat();
	}
}
function isDigits(argvalue) {
    argvalue = argvalue.toString();
	if(argvalue=="")
	{
		return false;
	}
    var validChars = "0123456789";
    var startFrom = 0;
    if (argvalue.substring(0, 2) == "0x") {
       validChars = "0123456789abcdefABCDEF";
       startFrom = 2;
    } else if (argvalue.charAt(0) == "0") {
       validChars = "01234567";
       startFrom = 1;
    }
    for (var n = 0; n < argvalue.length; n++) {
        if (validChars.indexOf(argvalue.substring(n, n+1)) == -1) return false;
    }
  return true;
}


//function used for login into chat
function loginintochat(batterytype,batterybrand,batterymodel,vehiclemake,vehiclemodel,price,withbatprice,striconurl,stractprice,capacity,strstate,city,area,pincity,strpincode)
{
	
	var chatname=document.getElementById("chatname").value;
	var chatemail=document.getElementById("chatemail").value;
	welcomemsg=document.getElementById("welcomemsg").value;
	var iCharsspecial = "`~,!#$%^&*()+=[]\\\';/{}|\":<>?";
	if (chatname=="")
	{
		errMsg ="<font color='#DD0000'>Please fill out name...!</font>";
		document.getElementById("displayerrormsg").innerHTML=errMsg;
		document.mobbatterydetails.chatname.focus();
		$("#namediv").show();
		return ;
	}
	if( chatname.indexOf(' ')==0 )
	{
		errMsg ="<font color='#DD0000'>Only spaces or space before text is not allowed in Name field!</font>";
		document.getElementById("displayerrormsg").innerHTML=errMsg;
		document.mobbatterydetails.chatname.focus();
		$("#namediv").show();
		return ;
	}
	for (var i = 0; i < document.getElementById("chatname").value.length; i++)
	{
         if (iCharsspecial.indexOf(document.getElementById("chatname").value.charAt(i))!= -1)
         {
			errMsg ="<font color='#ff3333'>Special characters are not allowed in \'Name\' field.</font>";
			document.getElementById("displayerrormsg").innerHTML=errMsg;
			document.getElementById("chatname").focus();
			$("#namediv").show();
			return ;
         }
	}
	if(isDigits(chatname)==true)
	{
		errMsg ="<font color='#ff3333'>Only Numerics are not allowed in the \'Name\' field.</font>";
		document.getElementById("displayerrormsg").innerHTML=errMsg;
		document.mobbatterydetails.chatname.focus();
		$("#namediv").show();
		return ;
	 }
	if (chatemail=="")
	{
		errMsg ="<font color='#DD0000'>Please fill out email...!</font>";
		document.getElementById("displayerrormsg").innerHTML=errMsg;
		document.mobbatterydetails.chatemail.focus();
		$("#displayerrormsg").show();
		return ;
	}
	if(chatemail!="")	
    	var emailidat = "@";
	var emailiddot = ".";
	var lat = chatemail.indexOf(emailidat);
	var emailidlength = chatemail.length;
	var ldot = chatemail.indexOf(emailiddot);
	var sst=chatemail.substring(emailidat,emailidlength);
	var sstdot=sst.indexOf(emailiddot);
	if (chatemail.indexOf(emailidat) == -1)
	{
		errMsg ="<font color='#DD0000'>The Email-id should be in the form of username@gmail.com</font>";
		document.getElementById("displayerrormsg").innerHTML=errMsg;
		document.mobbatterydetails.chatemail.focus();
		$("#displayerrormsg").show();
		return;
	}
	if (chatemail.indexOf(emailidat) == -1 || chatemail.indexOf(emailidat) == 0 || chatemail.indexOf(emailidat) == emailidlength)
	{
		errMsg ="<font color='#DD0000'>The Email-id should be in the form of username@gmail.com</font>";
		document.getElementById("displayerrormsg").innerHTML=errMsg;
		document.mobbatterydetails.chatemail.focus();
		$("#displayerrormsg").show();
		return;
	}
	if (chatemail.indexOf(emailidat) < 2)
	{
		errMsg ="<font color='#DD0000'>Please enter valid Email-id</font>";
		document.getElementById("displayerrormsg").innerHTML=errMsg;
		document.mobbatterydetails.chatemail.focus();
		$("#displayerrormsg").show();
		return;
	}
	if (chatemail.indexOf(emailiddot) == -1 || chatemail.indexOf(emailiddot) == 0 || chatemail.indexOf(emailiddot) == emailidlength)
	{
		errMsg ="<font color='#DD0000'>The Email-id should be in the form of username@gmail.com</font>";
		document.getElementById("displayerrormsg").innerHTML=errMsg;
		document.mobbatterydetails.chatemail.focus();
		$("#displayerrormsg").show();
		return;
	}
	if((emailidlength-1)-sstdot<2)
	{
		errMsg ="<font color='#DD0000'>Please enter valid Email-id</font>";
		document.getElementById("displayerrormsg").innerHTML=errMsg;
		document.mobbatterydetails.chatemail.focus();
		$("#displayerrormsg").show();
		return;
	}
	if (chatemail.indexOf(emailidat, (lat + 1)) != -1)
	{
		errMsg ="<font color='#DD0000'>The Email-id should be in the form of username@gmail.com</font>";
		document.getElementById("displayerrormsg").innerHTML=errMsg;
		document.mobbatterydetails.chatemail.focus();
		$("#displayerrormsg").show();
		return;
	}
	if (chatemail.substring(lat - 1, lat) == emailiddot || chatemail.substring(lat + 1, lat + 2) == emailiddot || chatemail.substring(ldot+1,ldot+2)==emailiddot)
	{
		errMsg ="<font color='#DD0000'>The Email-id should be in the form of username@gmail.com</font>";
		document.getElementById("displayerrormsg").innerHTML=errMsg;
		document.mobbatterydetails.chatemail.focus();
		$("#displayerrormsg").show();
		return;
	}
	if (chatemail.indexOf(emailiddot, (lat + 2)) == -1)
	{
		errMsg ="<font color='#DD0000'>The Email-id should be in the form of username@gmail.com</font>";
		document.getElementById("displayerrormsg").innerHTML=errMsg;
		document.mobbatterydetails.chatemail.focus();
		$("#displayerrormsg").show();
		return;
	}
	if (chatemail.indexOf(" ") != -1)
	{
		errMsg ="<font color='#DD0000'>The Email-id should be in the form of username@gmail.com</font>";
		document.getElementById("displayerrormsg").innerHTML=errMsg;
		document.mobbatterydetails.chatemail.focus();
		$("#displayerrormsg").show();
		return;
	}
	if (chatemail.indexOf("_@") != -1)
	{
		errMsg ="<font color='#DD0000'>The Email-id should be in the form of username@gmail.com</font>";
		document.getElementById("displayerrormsg").innerHTML=errMsg;
		document.mobbatterydetails.chatemail.focus();
		$("#displayerrormsg").show();
		return;
	}
	var regularexprsn = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
	if (! document.mobbatterydetails.chatemail.value.match(regularexprsn))
	{
		errMsg ="<font color='#DD0000'>The Email-id should be in the form of username@gmail.com</font>";
		document.getElementById("displayerrormsg").innerHTML=errMsg;
		document.mobbatterydetails.chatemail.focus();
		$("#displayerrormsg").show();
		return;
	}
	var reglrexprsn=/@(([^\.]*\.[^\.]*)?){1,3}$/;
	if (! document.mobbatterydetails.chatemail.value.match(reglrexprsn))
	{
		errMsg ="<font color='#DD0000'>The Email-id should be in the form of username@gmail.com</font>";
		document.getElementById("displayerrormsg").innerHTML=errMsg;
		document.mobbatterydetails.chatemail.focus();
		$("#displayerrormsg").show();
		return;
	}
	var regex=/^[a-zA-Z0-9_\+-]+(\.[a-zA-Z0-9_\+-]+)*@[a-zA-Z0-9-]+(\.[a-zA-Z0-9-]+)*\.([a-zA-Z]{2,3})$/;
	if (! document.mobbatterydetails.chatemail.value.match(regex))
	{
		errMsg ="<font color='#DD0000'>The Email-id should be in the form of username@gmail.com</font>";
		document.getElementById("displayerrormsg").innerHTML=errMsg;
		document.mobbatterydetails.chatemail.focus();
		$("#displayerrormsg").show();
		return;
	}
	if(chatemail!="")
    var hi = "-";
	var us = "_";
	var lhi = chatemail.indexOf(hi);
	var emailidlength = chatemail.length;
	var lus = chatemail.indexOf(us);
	if (chatemail.substring(lhi-1,lhi)==hi || chatemail.substring(lhi+1,lhi+2)==hi)
	{
		errMsg ="<font color='#DD0000'>The Email-id should be in the form of username@gmail.com</font>";
		document.getElementById("displayerrormsg").innerHTML=errMsg;
		document.mobbatterydetails.chatemail.focus();
		$("#displayerrormsg").show();
		return;
	}
	if ((chatemail.substring(lhi-1,lhi)==hi || chatemail.substring(lus+1,lus+2)==us) || (chatemail.substring(lus-1,lus)==us || chatemail.substring(lhi+1,lhi+2)==hi))
	{
		errMsg ="<font color='#DD0000'>The Email-id should be in the form of username@gmail.com</font>";
		document.getElementById("displayerrormsg").innerHTML=errMsg;
		document.mobbatterydetails.chatemail.focus();
		$("#displayerrormsg").show();
		return;
	}
	if (document.mobbatterydetails.chatemail.value.indexOf('com.in') >=0)
	{
		errMsg ="<font color='#DD0000'>The Email-id should be in the form of username@gmail.com</font>";
		document.getElementById("displayerrormsg").innerHTML=errMsg;
		document.mobbatterydetails.chatemail.focus();
		$("#displayerrormsg").show();
		return;
	}
	if (chatemail.substring(lhi - 1, lhi) == us || chatemail.substring(lhi + 1, lhi + 2) == us || chatemail.substring(lus+1,lus+2)==us)
	{
		errMsg ="<font color='#DD0000'>The Email-id should be in the form of username@gmail.com</font>";
		document.getElementById("displayerrormsg").innerHTML=errMsg;
		document.mobbatterydetails.chatemail.focus();
		$("#displayerrormsg").show();
		return;
	}
	if (chatemail.indexOf(hi, (lhi + 1)) != -1)
	{
		errMsg ="<font color='#DD0000'>Please enter valid Email-id</font>";
		document.getElementById("displayerrormsg").innerHTML=errMsg;
		document.mobbatterydetails.chatemail.focus();
		$("#displayerrormsg").show();
		return;
	}
	if (document.mobbatterydetails.chatemail.value.indexOf('.com.com')>=0)
	{
		errMsg ="<font color='#DD0000'>The Email-id should be in the form of username@gmail.com</font>";
		document.getElementById("displayerrormsg").innerHTML=errMsg;
		document.mobbatterydetails.chatemail.focus();
		$("#displayerrormsg").show();
		return;
	}
	if (document.mobbatterydetails.chatemail.value.indexOf('gmail') >=0)
	{
		if (document.mobbatterydetails.chatemail.value.indexOf('com')<0 )
		{
			var emailidvalue=document.mobbatterydetails.chatemail.value;
		    var lastvalue=emailidvalue.slice(-4);
		    var comvalue=lastvalue.toLowerCase();
		    if(comvalue!=".com")
		    {
		        errMsg ="<font color='#DD0000'>The Email-id should be in the form of username@gmail.com</font>";
		        document.getElementById("displayerrormsg").innerHTML=errMsg;
		        document.mobbatterydetails.chatemail.focus();
				$("#displayerrormsg").show();
		        return;
		    } 
		}
	}
	if (document.mobbatterydetails.chatemail.value.indexOf('rediffmail') >=0)
	{
		if (document.mobbatterydetails.chatemail.value.indexOf('com')<0)
		{
			var emailidvalue=document.mobbatterydetails.chatemail.value;
		    var lastvalue=emailidvalue.slice(-4);
		    var comvalue=lastvalue.toLowerCase();
		    if(comvalue!=".com")
		    {
		        errMsg ="<font color='#DD0000'>The Email-id should be in the form of username@gmail.com</font>";
		        document.getElementById("displayerrormsg").innerHTML=errMsg;
		        document.mobbatterydetails.chatemail.focus();
				$("#displayerrormsg").show();
		        return;
		    } 
		}
	}
	if (document.mobbatterydetails.chatemail.value.indexOf('hotmail') >=0)
	{
		if (document.mobbatterydetails.chatemail.value.indexOf('com')<0)
		{
			var emailidvalue=document.mobbatterydetails.chatemail.value;
		    var lastvalue=emailidvalue.slice(-4);
		    var comvalue=lastvalue.toLowerCase();
		    if(comvalue!=".com")
		    {
		        errMsg ="<font color='#DD0000'>The Email-id should be in the form of username@gmail.com</font>";
		        document.getElementById("displayerrormsg").innerHTML=errMsg;
		        document.mobbatterydetails.chatemail.focus();
				$("#displayerrormsg").show();
		        return;
		    } 
		}
	}
	
	if (document.mobbatterydetails.chatemail.value.indexOf('ymail') >=0)
	{
		if (document.mobbatterydetails.chatemail.value.indexOf('com')<0)
		{
			var emailidvalue=document.mobbatterydetails.chatemail.value;
		    var lastvalue=emailidvalue.slice(-4);
		    var comvalue=lastvalue.toLowerCase();
		    if(comvalue!=".com")
		    {
		        errMsg ="<font color='#DD0000'>The Email-id should be in the form of username@gmail.com</font>";
		        document.getElementById("displayerrormsg").innerHTML=errMsg;
				document.mobbatterydetails.chatemail.focus();
				$("#displayerrormsg").show();
		        return;
		    } 
		}
	}
	if (chatemail.indexOf("__") != -1)
	{
		errMsg ="<font color='#DD0000'>Please Enter Valid E-mail Id</font>";
		document.getElementById("displayerrormsg").innerHTML=errMsg;
		document.mobbatterydetails.chatemail.focus();
		$("#displayerrormsg").show();
		return;
	}
	if (chatemail.indexOf("--") != -1)
	{
		errMsg ="<font color='#DD0000'>Please Enter Valid E-mail Id</font>";
		document.getElementById("displayerrormsg").innerHTML=errMsg;
		document.mobbatterydetails.chatemail.focus();
		$("#displayerrormsg").show();
		return;
	}
	if (chatemail.indexOf("-_") != -1)
	{
		errMsg ="<font color='#DD0000'>Please Enter Valid E-mail Id</font>";
		document.getElementById("displayerrormsg").innerHTML=errMsg;
		document.mobbatterydetails.chatemail.focus();
		$("#displayerrormsg").show();
		return;
	}
	if (chatemail.indexOf("_-") != -1)
	{
		errMsg ="<font color='#DD0000'>Please Enter Valid E-mail Id</font>";
		document.getElementById("displayerrormsg").innerHTML=errMsg;
		document.mobbatterydetails.chatemail.focus();
		$("#displayerrormsg").show();
		return;
	}
	for (var i = 0; i < document.mobbatterydetails.chatemail.value.length; i++)
	{
  		if (iCharsspecial.indexOf(document.mobbatterydetails.chatemail.value.charAt(i)) != -1)
		{
			errMsg ="<font color='#DD0000'>Special characters are not allowed in Email-Id field.</font>";
			document.getElementById("displayerrormsg").innerHTML=errMsg;
			document.mobbatterydetails.chatemail.focus();
			$("#displayerrormsg").show();
			return;
  		}
	}
	$("#livechatlogindiv").hide();
	$('#pollopinion').empty();
	var xmlhttp;
	var url="/bookbattery/servlet/ChatLogin?hidWhatToDo=userChatLogin&chatname="+chatname+"&chatemail="+chatemail+"&requestno="+(Math.random()*100);	
	if (window.XMLHttpRequest)
	{	/* code for IE7+, Firefox, Chrome, Opera, Safari*/
		xmlhttp=new XMLHttpRequest();		
	}
	else
	{	/* code for IE6, IE5*/
	    xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
	}
	xmlhttp.onreadystatechange=function()
	{
		greyout(false);
		if(xmlhttp.readyState!=4)
		{
			var msg="<div id='loginchatdiv' style='background:#FFFFFF;'><TABLE valign='top' border='0' width='100%' cellspacing='0' cellpadding='0'><TR height='35'><TD bgcolor='#4682b4'><A HREF='javascript:void(null)' onclick='minafterlogchatwindowwaiting()' title='Minimize window' style='text-decoration:none;color:#FFFFFF;outline:none;'><div><table cellpadding='7' cellspacing='0' border='0' width='100%'><tr><td valign='middle'><font face='verdana' size='2' color='#ffffff'><b>"+welcomemsg+"</b></font></td><td align='right'><SPAN><IMG SRC='/bookbattery/images/downarrow.png' WIDTH='16' HEIGHT='11' BORDER='0' ALT='minimize'></SPAN></td></tr></table></div></A></TD></tr></table></A></font></TD></TR></TABLE><DIV style='height:379px;font-family:verdana;font-size:12px;color:#777777'><BR><BR><BR><span style='margin-left:15em'><IMG SRC='/bookbattery/images/ajax_loader_small_transparent.gif' WIDTH='32' HEIGHT='32' BORDER='0' ALT='load'></span><BR><BR><span style='margin-left:1.5em'>Please wait an operator will be with you in a moment.</span></DIV></div>";
			$('#logindetails').html(msg);
			$('#logindetails').show();
		}
		if (xmlhttp.readyState==4 && xmlhttp.status==200)
		{
			$('#logindetails').hide();
			var logo=document.getElementById("logo").value;
			var SplitResult = xmlhttp.responseText.split("|");
			var len=SplitResult.length;
			caname=SplitResult[0];
			var pictureurl=SplitResult[1];
			agentid=SplitResult[2];
			useremail=SplitResult[3];
			username=SplitResult[4];
			usrid=SplitResult[5];

			var batterywalecookieids=$.cookie('batterywalelivechatcookie');
			$.cookie('batterywalelivechatcookie',  "",{path:'/'});	
			var setcookieid=agentid+"|"+caname+"|"+chatname+"|"+chatemail+"|"+pictureurl+"|"+usrid+"|"+batterymodel+"|"+price+"|"+withbatprice+"|"+striconurl+"|"+stractprice+"|"+capacity;
			$.cookie('batterywalelivechatcookie',  setcookieid,{path:'/'});				

			if (caname== "null null")
			{
				dispbusydiv(batterymodel,price,withbatprice,striconurl,stractprice,capacity);
			}
			else
			{			
				
				$('#batterydetails').hide();
				$('#headerdiv').hide();
				$('#chatagentdiv').hide();

					var newDiv = document.createElement('div');
					newDiv.setAttribute("class", "batterywalechatdiv");
			
					newDiv.setAttribute('style','display:inline-block;background-color: #FFFFFF;height:auto;width:100%;');
					loadmsg ="<div class='qwer'><div id='loginchatdiv' style='background:#FFFFFF;     z-index: 999;'><div id='header'	style='margin-bottom:0.5em '><div > <div class='col-xs-12' style='background: #232323;height: 31px;width: 100%;'> <div class='col-xs-10'> <font face='verdana' size='2' color='#ffffff'><b>"+welcomemsg+"</b></font></div> <div class='col-xs-1' style='padding: 0px;'> <A HREF='javascript:void(null)' onclick='minafterlogchatwindow()' title='Minimize window' style='text-decoration:none;color:#FFFFFF;outline:none;'><IMG SRC='/bookbattery/images/downarrow.png' WIDTH='16' HEIGHT='11' BORDER='0' ALT='minimize'></A></div ><div class='col-xs-1' style='padding: 0px;' id='chatcloseimg' align='right'><A HREF='javascript:void(null)' onclick='endchat()' style='text-decoration:none;color:#FFFFFF;outline:none;' title='stop chat'><IMG SRC='/bookbattery/images/chatclose.png' WIDTH='14' HEIGHT='11' BORDER='0' ALT='close chat'></A></div></div></div><DIV id='logoutoption' style='display:none;height:394px'></DIV><DIV id='opinionoption' style='display:none;height:370px'></DIV><div  id='maincontent' class='col-xs-12' style='padding: 0px;'></ br><div height='10'></div><div><div class='col-xs-12' ><div valign='top' border='0' width='10%' bgcolor='#FCFCFC' cellspacing='0' cellpadding='0'><div><div class='content col-xs-4'><IMG SRC="+pictureurl+" ALT='agent image' style=' width: 100%;'></div><div class='col-xs-8'  align='left' ><div class='caname'>"+caname+"</div><div class='carole'>Support Operator</div><div style='margin-left:0.8em;margin-top:0.25em;'><A HREF='javascript:rateasgood()' style='text-decoration:none;color:#FFFFFF;outline:none;' title='Rate as good'><span><IMG SRC='/bookbattery/images/uphand.png' WIDTH='15' HEIGHT='15' BORDER='0' ALT='good'><span style='font-size:10px;font-family:verdana; color:#ffffff;vertical-align:text-top;'>&nbsp;Good</span></span></A>&nbsp;<A HREF='javascript:rateasbad()' style='text-decoration:none;color:#FFFFFF;outline:none;' title='Rate as bad'><span><IMG SRC='/bookbattery/images/downhand.png' WIDTH='14' HEIGHT='15' BORDER='0' ALT='bad'></span></A></div></div></div></div></div></div><div style='height:1px;background:#DDDDDD;'></div><div width='100%' border='0' ><div class='col-xs-12'  style=' border-bottom: 1px solid #CFCCCC;background: #F8F8F8;    padding: 0px 0px 14px 0px;'><div id='pollopinion' style='height:15px;font-family:verdana;font-size:11px;color:#ffffff;'></div><div  class='col-xs-12' style='border-top: 1px solid #232323;, padding: 0px;' valign='top'><div class='col-xs-5' style='padding: 0px;' style='border: 1px solid #232323;padding-left:2px;padding-right:2px;' border='0'><div align='center' style='font-family:Verdana;font-size:12px;color:#FF8C00;text-decoration:;padding:1px 1px;'><b>Battery Details</b></div><div><div align='center'><img align='center' src=\""+striconurl+"\" ALT='battery' width='50' height='50'/></div></div></div><div class='col-xs-7'><div><div width='100%' border='0'><div><div  style='padding: 0px;'><font color='#232323' face='verdana' size='1'><b>Model:&nbsp;</b>"+batterymodel+"</font></div></div><div><div width='100%'><font color='#232323' face='verdana' size='1'><b>MRP:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</b>Rs. "+stractprice+"</font></div></div><div><div width='100%'><font color='#232323' face='verdana' size='1'><b>Without Old Battery:&nbsp;&nbsp;</b>Rs. "+price+"</font></div></div><div><div width='100%'><font color='#232323' face='verdana' size='1'><b>With Old Battery:&nbsp;&nbsp;</b>Rs. "+withbatprice+"</font></div></div><div><div width='100%'><font color='#232323' face='verdana' size='1'><b>Capacity:&nbsp;</b>"+capacity+"</font></div></div></div></div></div></div><div width='100%' height='20' style='border: 0px solid #232323;padding-left:2px;padding-right:2px;' border='0'><div><div align='center' style='font-family:Verdana;font-size:12px;color:#FF8C00;text-decoration:;padding:1px 1px;'></div></div><div><div class='col-xs-12' style='padding: 0px;' ><div class='col-xs-12' style='padding: 0px;'  id='livechatdiv' style='min-height:200px;max-height:auto;overflow-y:auto;overflow-x:hidden;-webkit-overflow-scrolling: touch;'></div><div id='logoutmsg' height='50px'></div><div style='height:4px;background:#232323;'></div></div><div class='col-xs-12' style='padding: 0px;'  ><div id='messagediv' class='col-xs-10'  style='height:50px;padding: 0px;'><textarea onkeypress='return captureKeys(event,\""+chatname+"\",\""+caname+"\",\""+chatemail+"\","+usrid+","+agentid+",\""+batterymodel+"\","+price+","+withbatprice+",\""+striconurl+"\",\""+stractprice+"\",\""+capacity+"\");' id='lchat' name='lchat' placeholder='Type your message here and press Enter to send' style='border:1px solid #232323;outline: none;overflow-y:auto;width:100%;height:100%;font-family:verdana;font-size:12px;'></textarea></div><div class='col-xs-2'  ><img src='/bookbattery/images/chatsend.png' style='width: 100%;height: auto;padding-top: 5px;'  onclick=\"javascript:captureKeys1('"+chatname+"','"+caname+"','"+chatemail+"',"+usrid+","+agentid+",'"+batterymodel+"',"+price+","+withbatprice+",'"+striconurl+"','"+stractprice+"','"+capacity+"');\"></div></div><div align='center' width='100%'> <input  style='width: 50%;font-family: Helvetica Neue,Helvetica,Arial,sans-serif; background-color:#D6511C; color: white; font-size: 17px;padding: 5px 15px;font-weight: lighter; margin-left: 5px;margin-top: 5px;' type='button' class='btn'  value='Close' align='left' onclick='endchat()'></div></div></div></div></div></div></div></div></div>";
					
					$("#chatagentdiv").html(loadmsg);
					$('#chatagentdiv').show();
					document.getElementById("livechatdiv").innerHTML+="<br><table id='operatorsid' width='100%' border='0' style='width:100%; padding-bottom:2px;'><tr><td align='left'><font color='#COCOCO' face='verdana' size='2'>"+caname+"</font></td></tr></table><table id='operator' width='100%' style='border:2px width:100%; padding-bottom:10px;'><tr><td align='left' class='chatcontent'>Hello "+chatname+". How may I help you? Please be patient if it takes me a while to respond as I am chatting with several customers simultaneously.</td></tr></table>";
			setTimeout(function(){insertUsermessage(batterytype,batterybrand,batterymodel,vehiclemake,vehiclemodel,strstate,city,area,pincity,strpincode,capacity,agentid,usrid,username,useremail,caname)},2000)
				self.setTimeout(function(){livecustomermsg(usrid)},3000);
			}
		}
	}
	xmlhttp.open("GET",url,true);
	xmlhttp.send();
}
function onrefreshdis(chatagentid,chatagentname,chatname,chatemail,pictureurl,userid,batterymodel,price,withbatprice,striconurl,stractprice,capacity)
{
	usrid=userid;
	caname=chatagentname;
	agentid=chatagentid;
	useremail=chatemail;
	username=chatname;
	welcomemsg=document.getElementById("welcomemsg").value;
	var logo=document.getElementById("logo").value;

	var xmlhttp;
	var url="/bookbattery/servlet/LiveChatManagement?hidWhatToDo=fetchconsumerhistory&agentid="+agentid+"&userid="+usrid+"&customername="+chatname+"&consumeremail="+chatemail+"&agentname="+caname+"&version=mobile&requestno="+(Math.random()*100);	
	if (window.XMLHttpRequest)
	{	/* code for IE7+, Firefox, Chrome, Opera, Safari*/
		xmlhttp=new XMLHttpRequest();		
	}
	else
	{	/* code for IE6, IE5*/
	    xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
	}
	xmlhttp.onreadystatechange=function()
	{
		$('#chatagentdiv').hide();
		if(xmlhttp.readyState!=4)
		{
			
		}
		if (xmlhttp.readyState==4 && xmlhttp.status==200)
		{

			var response=xmlhttp.responseText;				
			$('#livechaticon').hide();
			$('#batterydetails').hide();
			$('#headerdiv').hide();		
		loadmsg ="<div class='qwer'><div id='loginchatdiv' style='background:#FFFFFF;     z-index: 999;'><div id='header'	style='margin-bottom:0.5em '><div > <div class='col-xs-12' style='background: #232323;height: 31px;width: 100%;'> <div class='col-xs-10'> <font face='verdana' size='2' color='#ffffff'><b>"+welcomemsg+"</b></font></div> <div class='col-xs-1' style='padding: 0px;'> <A HREF='javascript:void(null)' onclick='minafterlogchatwindow()' title='Minimize window' style='text-decoration:none;color:#FFFFFF;outline:none;'><IMG SRC='/bookbattery/images/downarrow.png' WIDTH='16' HEIGHT='11' BORDER='0' ALT='minimize'></A></div ><div class='col-xs-1' style='padding: 0px;' id='chatcloseimg' align='right'><A HREF='javascript:void(null)' onclick='endchat()' style='text-decoration:none;color:#FFFFFF;outline:none;' title='stop chat'><IMG SRC='/bookbattery/images/chatclose.png' WIDTH='14' HEIGHT='11' BORDER='0' ALT='close chat'></A></div></div></div><DIV id='logoutoption' style='display:none;height:394px'></DIV><DIV id='opinionoption' style='display:none;height:370px'></DIV><div  id='maincontent' class='col-xs-12' style='padding: 0px;'></ br><div height='10'></div><div><div class='col-xs-12' ><div valign='top' border='0' width='10%' bgcolor='#FCFCFC' cellspacing='0' cellpadding='0'><div><div class='content col-xs-4'><IMG SRC="+pictureurl+" ALT='agent image' style=' width: 100%;'></div><div class='col-xs-8'  align='left' ><div class='caname'>"+caname+"</div><div class='carole'>Support Operator</div><div style='margin-left:0.8em;margin-top:0.25em;'><A HREF='javascript:rateasgood()' style='text-decoration:none;color:#FFFFFF;outline:none;' title='Rate as good'><span><IMG SRC='/bookbattery/images/uphand.png' WIDTH='15' HEIGHT='15' BORDER='0' ALT='good'><span style='font-size:10px;font-family:verdana; color:#ffffff;vertical-align:text-top;'>&nbsp;Good</span></span></A>&nbsp;<A HREF='javascript:rateasbad()' style='text-decoration:none;color:#FFFFFF;outline:none;' title='Rate as bad'><span><IMG SRC='/bookbattery/images/downhand.png' WIDTH='14' HEIGHT='15' BORDER='0' ALT='bad'></span></A></div></div></div></div></div></div><div style='height:1px;background:#DDDDDD;'></div><div width='100%' border='0' ><div class='col-xs-12'  style=' border-bottom: 1px solid #CFCCCC;background: #F8F8F8;    padding: 0px 0px 14px 0px;'><div id='pollopinion' style='height:15px;font-family:verdana;font-size:11px;color:#ffffff;'></div><div  class='col-xs-12' style='border-top: 1px solid #232323;, padding: 0px;' valign='top'><div class='col-xs-5' style='padding: 0px;' style='border: 1px solid #232323;padding-left:2px;padding-right:2px;' border='0'><div align='center' style='font-family:Verdana;font-size:12px;color:#FF8C00;text-decoration:;padding:1px 1px;'><b>Battery Details</b></div><div><div align='center'><img align='center' src=\""+striconurl+"\" ALT='battery' width='50' height='50'/></div></div></div><div class='col-xs-7'><div><div width='100%' border='0'><div><div  style='padding: 0px;'><font color='#232323' face='verdana' size='1'><b>Model:&nbsp;</b>"+batterymodel+"</font></div></div><div><div width='100%'><font color='#232323' face='verdana' size='1'><b>MRP:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</b>Rs. "+stractprice+"</font></div></div><div><div width='100%'><font color='#232323' face='verdana' size='1'><b>Without Old Battery:&nbsp;&nbsp;</b>Rs. "+price+"</font></div></div><div><div width='100%'><font color='#232323' face='verdana' size='1'><b>With Old Battery:&nbsp;&nbsp;</b>Rs. "+withbatprice+"</font></div></div><div><div width='100%'><font color='#232323' face='verdana' size='1'><b>Capacity:&nbsp;</b>"+capacity+"</font></div></div></div></div></div></div><div width='100%' height='20' style='border: 0px solid #232323;padding-left:2px;padding-right:2px;' border='0'><div><div align='center' style='font-family:Verdana;font-size:12px;color:#FF8C00;text-decoration:;padding:1px 1px;'></div></div><div><div class='col-xs-12' style='padding: 0px;' ><div class='col-xs-12' style='padding: 0px;'  id='livechatdiv' style='min-height:200px;max-height:auto;overflow-y:auto;overflow-x:hidden;-webkit-overflow-scrolling: touch;'></div><div id='logoutmsg' height='50px'></div><div style='height:4px;background:#232323;'></div></div><div class='col-xs-12' style='padding: 0px;'  ><div id='messagediv' class='col-xs-10'  style='height:50px;padding: 0px;'><textarea onkeypress='return captureKeys(event,\""+chatname+"\",\""+caname+"\",\""+chatemail+"\","+usrid+","+agentid+",\""+batterymodel+"\","+price+","+withbatprice+",\""+striconurl+"\",\""+stractprice+"\",\""+capacity+"\");' id='lchat' name='lchat' placeholder='Type your message here and press Enter to send' style='border:1px solid #232323;outline: none;overflow-y:auto;width:100%;height:100%;font-family:verdana;font-size:12px;'></textarea></div><div class='col-xs-2'  ><img src='/bookbattery/images/chatsend.png' style='width: 100%;height: auto;padding-top: 5px;'  onclick=\"javascript:captureKeys1('"+chatname+"','"+caname+"','"+chatemail+"',"+usrid+","+agentid+",'"+batterymodel+"',"+price+","+withbatprice+",'"+striconurl+"','"+stractprice+"','"+capacity+"');\"></div></div><div align='center' width='100%'> <input  style='width: 50%;font-family: Helvetica Neue,Helvetica,Arial,sans-serif; background-color:#D6511C; color: white; font-size: 17px;padding: 5px 15px;font-weight: lighter; margin-left: 5px;margin-top: 5px;' type='button' class='btn'  value='Close' align='left' onclick='endchat()'></div></div></div></div></div></div></div></div></div>";
		$("#chatagentdiv").html(loadmsg);
		$('#chatagentdiv').show();	
		document.getElementById("livechatdiv").innerHTML+="<br><table id='operatorsid' width='100%' border='0' style='width:100%; padding-bottom:2px;'><tr><td align='left'><font color='#COCOCO' face='verdana' size='2'>"+caname+"</font></td></tr></table><table id='operator' width='100%' style='border:2px width:100%; padding-bottom:10px;'><tr><td align='left' class='chatcontent'>Hello "+chatname+". How may I help you? Please be patient if it takes me a while to respond as I am chatting with several customers simultaneously.</td></tr></table>";
			document.getElementById("livechatdiv").innerHTML+=response;
			self.setTimeout(function(){livecustomermsg(usrid)},3000);
	}
	}
	xmlhttp.open("GET",url,true);
	xmlhttp.send();
}
function insertUsermessage(batterytype,batterybrand,batterymodel,vehiclemake,vehiclemodel,strstate,city,area,pincity,strpincode,capacity,agentid,usrid,username,useremail,caname)
{	
	var message="";
	if(strpincode=="")
	{
		 if(batterytype=="Inverters")
		{
			  message="Customer from "+strstate+">>"+city+">> "+area+" is looking for "+batterytype+">>  "+batterybrand+">> "+batterymodel+" inverter battery with "+capacity+" capacity.";
		}
		else
		{
			message="Customer from "+strstate+">>"+city+">> "+area+" is looking for "+batterytype+">>"+vehiclemake+">> "+vehiclemodel+">>  "+batterybrand+">> "+batterymodel+" battery with "+capacity+" capacity.";
		}
	}
	else
	{
		if(batterytype=="Inverters")
		{
			  message="Customer from "+strstate+">> "+pincity+" with pincode "+strpincode+" is looking for "+batterytype+">>  "+batterybrand+">> "+batterymodel+" inverter battery with "+capacity+" capacity.";
		}
		else
		{
		message="Customer from "+strstate+">> "+pincity+" with pincode "+strpincode+" is looking for "+batterytype+">>"+vehiclemake+">> "+vehiclemodel+">>  "+batterybrand+">> "+batterymodel+" battery with "+capacity+" capacity.";
		}
	}


		var xmlhttp;
		var url="/bookbattery/servlet/LiveChatManagement?hidWhatToDo=insertmsg&agentid="+agentid+"&usrid="+usrid+"&customername="+username+"&email="+useremail+"&chatmsgvalue="+message+"&chatemail="+useremail+"&cname="+caname+"&requestno="+(Math.random()*100);
	
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
			

			}
		}
		xmlhttp.open("GET",url,true);
		xmlhttp.send();	
}
function displogdiv(username,useremail,batterymodel,price,withbatprice,striconurl,stractprice,capacity)
{
	welcomemsg=document.getElementById("welcomemsg").value;
	var batterytype = document.getElementById("batterytype").value;
	var batterybrand = document.getElementById("batterybrand").value;
	var city=document.getElementById("city").value;
	var pincity=document.getElementById("pincity").value;
	var strstate=document.getElementById("strstate").value;
	var area = document.getElementById("strarea").value;
	var strpincode=document.getElementById("strpincode").value;
	var vehiclemake=document.getElementById("vehiclemake").value;
	var vehiclemodel=document.getElementById("vehiclemodel").value;
	$('#livechaticon').hide();
	$('#chatagentdiv').hide();
	$('#batterydetails').show();
		$('#headerdiv').show();	
	errMsg ="<table width='100%' bgcolor='#FFFFFF' valign='top'><tr height='30'><td align='right' valign='top' style='cursor:pointer;' bgcolor='#E6E6E6'><font color='red' size='3'><b><a href=\"javascript:closechatlogindiv(greyout(false));\" style='color:#848484;text-decoration:none;'>X&nbsp;&nbsp;</a></b></td></tr><tr><td><table width='100%'  cellspacing='0' cellpadding='0' bgcolor='#FFFFFF'><tr><td width='100%' colspan='3'><table width='100%' cellspacing='0' cellpadding='0' border='0'><tr><td><font size='2' color='#000000'>Your session has been expired please start your chat again</font></td></tr></table><table width='100%' cellspacing='0' cellpadding='0' border='0'><td width='24%'></td><td width='76%' class='insidecontent' align='left'><font size='2' color='#FF8C00'>Enter Your Name</font><font color='red'>*</font></td></tr></table></td><tr><td height='10'></td></tr></tr><tr><td width='100%' align='center'><input class='insidecontent' type='text' autocomplete='off' name='chatname' id='chatname' value='"+username+"'  style='width:195px;height:20px;border: 2px solid #CCC;font-size: 13px;font-family: Verdana;border-radius: 4px 4px 4px 4px;' maxlength='50' ></td></tr><tr><td height='5'></td></tr><tr><td width='100%' colspan='3'><table width='100%' cellspacing='0' cellpadding='0'><tr><td width='24%'></td><td width='76%' class='insidecontent' align='left'><font size='2' color='#FF8C00'>Enter Your Email</font><font color='red'>*</font></td></tr></table></td><tr><td height='10'></td></tr></tr><tr><td width='100%' align='center'><input class='insidecontent' type='text' autocomplete='off' name='chatemail' id='chatemail' value='"+useremail+"' style='width:195px;height:20px;border: 2px solid #CCC;font-size: 13px;font-family: Verdana;border-radius: 4px 4px 4px 4px;' maxlength='50' onkeydown=\"if ((event.which && event.which == 13) || (event.keyCode && event.keyCode == 13)){javascript:loginintochat('"+batterytype+"','"+batterybrand+"','"+batterymodel+"','"+vehiclemake+"','"+vehiclemodel+"','"+price+"','"+withbatprice+"','"+striconurl+"','"+stractprice+"','"+capacity+"','"+strstate+"','"+city+"','"+area+"','"+pincity+"','"+strpincode+"');return false;} else return true;\"/></td></tr><tr><td height='5'></td></tr><tr><td width='50%' align='center'><input type='button' class='batterywalesubmit' name='Submitrret' value='Start Chat' disable='false' onclick=\"javascript:loginintochat('"+batterytype+"','"+batterybrand+"','"+batterymodel+"','"+vehiclemake+"','"+vehiclemodel+"','"+price+"','"+withbatprice+"','"+striconurl+"','"+stractprice+"','"+capacity+"','"+strstate+"','"+city+"','"+area+"','"+pincity+"','"+strpincode+"');\"></td></tr><tr height='26'><td colspan='3' align='center' class='subheading' id='displayerrormsg'></td></tr><tr><td height='15'></td></tr></table></table>";	
		document.getElementById("livechatlogindiv").innerHTML="";
		document.getElementById("livechatlogindiv").style.display='block';
		document.getElementById("livechatlogindiv").innerHTML=errMsg
		greyout(true);

}

var achatid;
function livecustomermsg(usrid)
{
	var url="/bookbattery/servlet/LiveChatManagement?hidWhatToDo=afetchmsgcustomer&usrid="+usrid+"&achatid="+achatid+"&requestno="+(Math.random()*100);	
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
			var achatRes=xmlhttp.responseText;
			
			
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
						var agentname=result[2];
						var chatArea = document.getElementById("livechatdiv");			
						
						var livechatArea = document.getElementById("livechatdiv");
						var exdata=$(livechatArea).html();
						
						exdata=exdata.split("<table");
						var leng=exdata.length-1;
						if(exdata[leng].indexOf("operator")>=0)
						{}
						else
						{							
							$(chatArea).append("<br><table id='operatorsid' width='100%' style='width:100%; padding-bottom:2px;'><tr><td align='left'><font color='#COCOCO' face='verdana' size='2'>"+agentname+"</font></td></tr></table>");
						}
							var currentTime = new Date();
							var hours = currentTime.getHours();
        						var minutes = currentTime.getMinutes();
        						var seconds = currentTime.getSeconds();

								if(hours.toString().length == 1) 
								{
									 hours = '0'+hours;
								}
								if(minutes.toString().length == 1) 
								{
									 minutes = '0'+minutes;
								}
								if(seconds.toString().length == 1) 
								{
									 seconds = '0'+seconds;
								} 

						var replacePattern1 = /(\b(https?|ftp):\/\/[-A-Z0-9+&@#\/%?=~_|!:,.;]*[-A-Z0-9+&@#\/%=~_|])/gim;
    						result[3] = result[3].replace(replacePattern1, '<a href="$1" target="_blank">$1</a>');
    						var replacePattern2 = /(^|[^\/])(www\.[\S]+(\b|$))/gim;
    						result[3] = result[3].replace(replacePattern2, '$1<a href="http://$2" target="_blank">$2</a>');
    						var replacePattern3 = /(\w+@[a-zA-Z_]+?\.[a-zA-Z]{2,6})/gim;
    						result[3] = result[3].replace(replacePattern3, '<a href="mailto:$1">$1</a>');



						$(chatArea).append( "<table id='operator' border='0' width='100%' style='table-layout:fixed;border:2px; padding-bottom:10px;'><tr><div align='justify'><td align='left' width='70%' style='word-wrap: break-word;' class='chatcontent'>"+result[3]+"</td></div><td width='30%' align='right' class='chatcontent' valign='bottom'>"+hours+":"+minutes+":"+seconds+"</td></tr></table>" );
						chatArea.scrollTop = chatArea.scrollHeight;
					}
				}
				var loadchatbatterywale=document.getElementById("livechatdiv").innerHTML;
				$.cookie('loadchatbatterywale',  "",{path:'/'});
				$.cookie('loadchatbatterywale',  loadchatbatterywale,{path:'/'});
			self.setTimeout(function(){livecustomermsg(usrid)},3000);
		}
	}
	xmlhttp.open("GET",url,true);
	xmlhttp.send();
}
//this function is used to hide error messages
function clearerrmsg()
{
	$("#namediv").hide();
	$("#displayerrormsg").hide();
	$("#busyerrormsg").hide();
}
//this function is used to insert chat message into the database
function captureKeys(e,name,cname,email,usrid,agentid,batterymodel,price,withbatprice,striconurl,stractprice,capacity)
{
	if (window.event)
	{
		e = window.event;
	}
	if (e.keyCode == 13)
	{
		livechat(name,cname,email,usrid,agentid,batterymodel,price,withbatprice,striconurl,stractprice,capacity);
		e.preventDefault();
	}
	
}
function captureKeys1(name,cname,email,usrid,agentid,batterymodel,price,withbatprice,striconurl,stractprice,capacity)
{
	
	livechat(name,cname,email,usrid,agentid,batterymodel,price,withbatprice,striconurl,stractprice,capacity);

}
function closemessagediv()
{	
	$('#livechatlogindiv').hide();
	greyout(false)
}

function livechat(name,cname,email,usrid,agentid,batterymodel,price,withbatprice,striconurl,stractprice,capacity)
{	
	var lchatvalue=$("#lchat").val();	
	if(lchatvalue!="")
	{
		var lchatval=escape(lchatvalue);
		 lchatval=lchatval.replace(/\+/g, '%2B');
		 if(lchatval.length>4000)
		{
			$('#logindetails').hide();
			//errMsg ="<table cellspacing='10' cellpadding='0' bgcolor='#FFFFFF'><table width='100%' bgcolor='#FFFFFF' valign='top'><tr height='30'><td align='right' valign='top' style='cursor:pointer;' bgcolor='#E6E6E6'><font color='red' size='3'><b><a href=\"javascript:closemessagediv();\" style='color:#848484;text-decoration:none;'>X&nbsp;&nbsp;</a></b></td></tr><tr><td><table width='100%'  cellspacing='0' cellpadding='0' bgcolor='#FFFFFF'><tr><td width='100%' colspan='3'><table width='100%' cellspacing='0' cellpadding='0' border='0'><tr><td width='100%' align='center'>Your message length has exceeded the limit. Please reduce.</td></tr><tr><td width='100%' align='center'><input type='button'  name='Submitrret' value='Ok' disable='false' onclick=\"javascript:closemessagediv();\"></td></tr><tr height='26'><td ></td></tr><tr><td height='15'></td></tr></table></table>";	
			
			document.getElementById("livechatlogindiv").innerHTML="";
			document.getElementById("livechatlogindiv").style.display='block';
			document.getElementById("livechatlogindiv").innerHTML=errMsg
			greyout(true)
				return;
		}
		$("#lchat").val('');
		//$("#lchat").val() = $("#lchat").val().replace(/(\r\n|\n|\r)/gm,"");
		var livechatArea = document.getElementById("livechatdiv");
		var exdata=$(livechatArea).html();
		exdata=exdata.split("<table");
		var leng=exdata.length-1;
	if(exdata[leng].indexOf("userdetllls")>=0)
	{}
	else
	{
		livechatArea.innerHTML+="<br><table id='operatorsid' border='0' style='width:100%; padding-bottom:2px;'><tr><td align='left'><font color='#969798' face='verdana' size='2'>"+name+"</font></td></tr></table>";
	}
	var xmlhttp;
	var url="/bookbattery/servlet/LiveChatManagement?hidWhatToDo=insertmsg&agentid="+agentid+"&usrid="+usrid+"&customername="+name+"&email="+email+"&chatmsgvalue="+lchatval+"&chatemail="+name+"&cname="+cname+"&requestno="+(Math.random()*100);	
	if (window.XMLHttpRequest)
	{	/* code for IE7+, Firefox, Chrome, Opera, Safari*/
		xmlhttp=new XMLHttpRequest();		
	}
	else
	{	/* code for IE6, IE5*/
	    xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
	}
	xmlhttp.onreadystatechange=function()
	{
		if (xmlhttp.readyState==4 && xmlhttp.status==200)
		{
			var response =xmlhttp.responseText;			
			if(response.indexOf("inactive")>=0)
			{
				$('#logindetails').hide();	
				displogdiv(name,email,batterymodel,price,withbatprice,striconurl,stractprice,capacity)
			}

				var currentTime = new Date();
				var hours = currentTime.getHours();
        		var minutes = currentTime.getMinutes();
        		var seconds = currentTime.getSeconds();

				if(hours.toString().length == 1) 
				{
					 hours = '0'+hours;
				}
				if(minutes.toString().length == 1) 
				{
					 minutes = '0'+minutes;
				}
				if(seconds.toString().length == 1) 
				{
					 seconds = '0'+seconds;
				} 


			var replacePattern1 = /(\b(https?|ftp):\/\/[-A-Z0-9+&@#\/%?=~_|!:,.;]*[-A-Z0-9+&@#\/%=~_|])/gim;
    			lchatvalue = lchatvalue.replace(replacePattern1, '<a href="$1" target="_blank">$1</a>');
    			var replacePattern2 = /(^|[^\/])(www\.[\S]+(\b|$))/gim;
    			lchatvalue = lchatvalue.replace(replacePattern2, '$1<a href="http://$2" target="_blank">$2</a>');
    			var replacePattern3 = /(\w+@[a-zA-Z_]+?\.[a-zA-Z]{2,6})/gim;
    			lchatvalue = lchatvalue.replace(replacePattern3, '<a href="mailto:$1">$1</a>');
			livechatArea.innerHTML+="<table id='userdetllls' width='100%' border='0' bgcolor='#F0F8FF' style='table-layout:fixed;padding-bottom:2px;'><tr><div align='justify'><td align='left' width='70%' style='word-wrap: break-word;' class='insidechatcontent'>"+lchatvalue+"</td></div><td width='30%' class='chatcontent' align='right' valign='bottom'>"+hours+":"+minutes+":"+seconds+"</td></tr></table>";
			livechatArea.scrollTop = livechatArea.scrollHeight;
			var loadchatbatterywale=document.getElementById("livechatdiv").innerHTML;
			$.cookie('loadchatbatterywale',  "",{path:'/'});
			$.cookie('loadchatbatterywale',  loadchatbatterywale,{path:'/'});
		}
	}
	xmlhttp.open("GET",url,true);
	xmlhttp.send();
	}
}
//this function is used to display message when user clicks on rate as good link
function rateasgood()
{
	var chatmobilenumber = document.getElementById("chatmobilenumber").value;
	var pollmsg="<span style='color: #ff8c00;'>You rated our customer service as good.</span>";
	$('#pollopinion').html(pollmsg);
	$('#pollopinion').show();
	$.get("/bookbattery/servlet/ChatLogin?hidWhatToDo=insertRating&rate=Good&agentid="+agentid+"&useremail="+useremail+"&username="+username+"&userid="+usrid+"&chatmobilenumber="+chatmobilenumber+"&requestno="+(Math.random()*100),function(response, status, xhr)
	{
		if (status == "success" ){}
	});
}
//this function is used to display message when user clicks on rate as bad link
function rateasbad()
{
	var chatmobilenumber = document.getElementById("chatmobilenumber").value;
	var pollmsg="<span style='color: #ff8c00;'>You rated our customer service as bad.</span>";
	$('#pollopinion').empty();
	$('#pollopinion').html(pollmsg);
	$('#pollopinion').show();
	$.get("/bookbattery/servlet/ChatLogin?hidWhatToDo=insertRating&rate=Poor&agentid="+agentid+"&useremail="+useremail+"&username="+username+"&userid="+usrid+"&chatmobilenumber="+chatmobilenumber+"&requestno="+(Math.random()*100),function(response, status, xhr)
	{
		if (status == "success" ){}
	});
}
//this function is used to minimize the after login chat window.
function minafterlogchatwindow()
{
	if(caname=="")
	{
		caname=cagentName;
	}
	$('#logindetails').hide();
	$('#chatagentdiv').hide();
	$('#headerdiv').show();

	$('#batterydetails').show();
	var minimizemsg="<A href='javascript:void(null)' onclick='openchatwindow()' style='text-decoration:none;outline:none;'><TABLE cellspacing='0' cellpadding='5' border='0' width='240'><TR><TD valign='top' width='230'><font face='verdana' size='2' color='#ffffff'><div style='width:200px;overflow:hidden;text-overflow:ellipsis;white-space:nowrap;'><b>Chat&nbsp;with&nbsp;"+caname+"</b><div></font></TD><TD valign='center' width='10'><IMG SRC='/bookbattery/images/uparrow.png' WIDTH='16' HEIGHT='11' BORDER='0' ALT='maximize'></TD></TR></TABLE></A>";
	$('#livechaticon').empty();
	$('#livechaticon').html(minimizemsg);
	$('#livechaticon').show();
}
function minafterlogchatwindowwaiting()
{
	if(caname=="")
	{
		caname=cagentName;
	}
	$('#logindetails').hide();
	$('#chatagentdiv').hide();
	$('#headerdiv').show();

	$('#batterydetails').show();
	var minimizemsg="<A href='javascript:void(null)' onclick='openchatwindowwaiting()' style='text-decoration:none;outline:none;'><TABLE cellspacing='0' cellpadding='5' border='0' width='240'><TR><TD valign='top' width='230'><font face='verdana' size='2' color='#ffffff'><div style='width:200px;overflow:hidden;text-overflow:ellipsis;white-space:nowrap;'><b>BookBattery Live</b><div></font></TD><TD valign='center' width='10'><IMG SRC='/bookbattery/images/uparrow.png' WIDTH='16' HEIGHT='11' BORDER='0' ALT='maximize'></TD></TR></TABLE></A>";
	$('#livechaticon').empty();
	$('#livechaticon').html(minimizemsg);
	$('#livechaticon').show();
}
//this function is used to display message(Leave the chat or Cancel) when user click on close link
function endchat()
{	
	var optinon1="logoutoption";
	var option2="opinionoption";
	$('#chatcloseimg').hide();
	if(($("#"+optinon1).is(':visible')) || ($("#"+option2).is(':visible')))
	{
	}
	else
	{
		
		var endchatmsg="<TABLE border='0'><TR height='70'><TD width='50%' align='center'><font face='verdana' size='2'>Do you really want to leave current chat?</font></TD></TR><TR height='90'><TD width='50%' align='center'><input style='font-family: Helvetica Neue,Helvetica,Arial,sans-serif;background-color:#D6511C; color: white; font-size: 17px;padding: 5px 15px;font-weight: lighter; margin-left: 5px;margin-top: 5px;' type='button' class='btn'  VALUE='Leave the chat' style='font-family:verdana;font-size:12px;cursor:pointer;' ONCLICK='closechat()'>&nbsp;&nbsp;&nbsp;&nbsp;<input style='font-family: Helvetica Neue,Helvetica,Arial,sans-serif;background-color:#D6511C; color: white; font-size: 17px;padding: 5px 15px;font-weight: lighter; margin-left: 5px;margin-top: 5px;' type='button' class='btn' VALUE='Cancel' style='font-family:verdana;font-size:12px;cursor:pointer;' ONCLICK='loadchat()'></TD></TR></TABLE>";
	//	$('#logindetails').show();
		$('#chataagentdiv').show();
		
		$('#maincontent').hide();
		$('#logoutoption').html(endchatmsg);
		$('#logoutoption').show();
	}
}
//this function is used to display chat agent rating opinion options for user after click on Leave the chat
function closechat()
{
	$('#chatcloseimg').hide();
	//$('#logindetails').hide();
	var pollmsg="<TABLE cellspacing='0' cellpadding='0' border='0'><TR height='20'><td align='left' id='opinionerrmsgdiv'></td></TR><TR><TD width='50%' align='left'><font color='#232323' face='verdana' size='2'><b>How do you rate our chat quality?*</b></font></TD></TR><TR height='90'><TD width='50%' align='left'><table border='0' cellspaciing='0' cellpadding='6'><tr><div style='margin-bottom:-5px;margin-top:10px;'><td widht='20%' align='right' ><INPUT TYPE='radio' NAME='opinion' id='opinion1' value='Excellent' style='float:left;margin-right:5px;'></td><td widht='80%' align='left' ><label style='text-decoration:none;color:#232323;font-size:12px;font-family:verdana;' for='opinion1'>Excellent</label></td></div></tr><tr><div style='margin-bottom:-5px;margin-top:10px;'><td widht='20%' align='right'><INPUT TYPE='radio' NAME='opinion' id='opinion2' value='Good' style='float:left;margin-right:5px;'></td><td widht='80%' align='lrft'><label style='text-decoration:none;color:#232323;font-size:12px;font-family:verdana;'for='opinion2'>Good</label></td></div></tr><tr><div style='margin-bottom:-5px;margin-top:10px;'><td widht='20%' align='right'><INPUT TYPE='radio' NAME='opinion' id='opinion3' value='Average' style='float:left;margin-right:5px;'></td><td widht='80%' align='left'><label style='text-decoration:none;color:#232323;font-size:12px;font-family:verdana;' for='opinion3'>Average</label></td></div></tr><tr><div style='margin-bottom:-5px;margin-top:10px;'><td widht='20%' align='right'><INPUT TYPE='radio' NAME='opinion' id='opinion4' value='Poor' style='float:left;margin-right:5px;'></td><td idht='80%' align='left'><label style='text-decoration:none;color:#232323;font-size:12px;font-family:verdana;' for='opinion4'>Poor</label></td></div></tr><tr><div style='margin-bottom:-5px;margin-top:10px;'><td widht='20%' align='right'><INPUT TYPE='radio' NAME='opinion' id='opinion5' value='Not acceptable' style='float:left;margin-right:5px;'></td><td widht='80%' align='left'><label style='text-decoration:none;color:#232323;font-size:12px;font-family:verdana;' for='opinion5'>Not acceptable</label></td></div></tr></table></TD></tr><tr height='80'><td align='left'><span style='margin-left:12px'><input style='font-family: Helvetica Neue,Helvetica,Arial,sans-serif;background-color:#D6511C; color: white; font-size: 17px;padding: 5px 15px;font-weight: lighter; margin-left: 5px;margin-top: 5px;' type='button' class='btn'  VALUE='Submit' name='Chatratingbtn' id='Chatratingbtn' style='cursor:pointer;' ONCLICK='closeentirechat()'></span></td></TR></TABLE>";
	loadchathistory=document.getElementById("livechatdiv").innerHTML;
	$('#chatagentdiv').show();
	$('#logoutoption').empty();
	$('#logoutoption').hide();
	$('#opinionoption').html(pollmsg);
	$('#opinionoption').show();
}
//function used to load chat div when user clicks on cancel in chat close window
function loadchat()
{
	$('#maincontent').show();
	$('#chatcloseimg').show();
	$('#logoutoption').hide();
}
//function used to close the entire window and insert the chat agent rating status
function closeentirechat()
{
	
	var chatmobilenumber = document.getElementById("chatmobilenumber").value;

	$('#pollopinion').empty();
	var rate_value ="";
	if (document.getElementById('opinion1').checked)
	{
	 rate_value = document.getElementById('opinion1').value;
	}
	else if (document.getElementById('opinion2').checked)
	{
	 rate_value = document.getElementById('opinion2').value;
	}
	else if (document.getElementById('opinion3').checked)
	{
	 rate_value = document.getElementById('opinion3').value;
	}
	else if (document.getElementById('opinion4').checked)
	{
	 rate_value = document.getElementById('opinion4').value;
	}
	else if (document.getElementById('opinion5').checked)
	{
	 rate_value = document.getElementById('opinion5').value;
	}
	else
	{
		errMsg ="<font color='#DD0000'>Please fill out all required fields....!</font>";
		document.getElementById("opinionerrmsgdiv").innerHTML=errMsg;
		$("#opinionerrmsgdiv").show();
		return ;
	}
	document.getElementById("Chatratingbtn").disabled = true;
	document.getElementById("Chatratingbtn").value='Please Wait...';
	$("#opinionerrmsgdiv").hide();
	$.get("/bookbattery/servlet/ChatLogin?hidWhatToDo=userChatLogout&chatemail="+useremail+"&rate="+rate_value+"&agentid="+agentid+"&chatname="+username+"&userid="+usrid+"&chatmobilenumber="+chatmobilenumber+"&requestno="+(Math.random()*100),function(response, status, xhr)
	{
		if (status == "success" )
		{
			$.cookie('batterywalelivechatcookie', "null",{path:'/'});
			//$('#logindetails').empty();
		//	$('#logindetails').hide();
			$('#chatagentdiv').hide();
			$('#chatagentdiv').empty();
			$('#headerdiv').show();
			$('#batterydetails').show();
			$('#corner').show();
			//var minimizemsg="<A href='javascript:void(null)' onclick='openchatwindowafterlogout()' style='text-decoration:none;outline:none;'><TABLE cellspacing='0' cellpadding='5' border='0' width='240'><TR><TD valign='top' width='230'><font face='verdana' size='2' color='#ffffff'><div style='width:200px;overflow:hidden;text-overflow:ellipsis;white-space:nowrap;'><b>Chat&nbsp;ended</b><div></font></TD><TD valign='center' width='10'><IMG SRC='/bookbattery/images/uparrow.png' WIDTH='16' HEIGHT='11' BORDER='0' ALT='maximize'></TD></TR></TABLE></A>";
			//$('#livechaticon').empty();
			//$('#livechaticon').html(minimizemsg);
			//$('#livechaticon').show();
		}
	});
}
//function used to display start again chat options div
function openchatwindowafterlogout()
{
	//need to display start chat again link here click in chat message when user clicks on that link the following should display.
	$('#livechaticon').hide();
	var msg="<div style='background:#FFFFFF;'><TABLE valign='top' border='0' cellspacing='0' cellpadding='0' width='100%'><TR height='30'><TD><span style='font-size:12px;font-family:verdana; color:#777777;vertical-align:text-top;margin-bottom:5px;'>&nbsp;Chat ended</span></TD></TR><TR height='30'><TD><A HREF='javascript:displogindiv()' style='color:#FFFFFF;outline:none;'>&nbsp;<span style='font-size:12px;font-family:verdana;text-decoration:underline; color:#000000;'>Start the chat again</span></A></TD></TR></TABLE></div>";
	var headermsg="<div><TABLE valign='top' border='0' width='100%' cellspacing='0' cellpadding='0'><TR height='35'><TD bgcolor='#4682b4'><A HREF='javascript:void(null)' onclick='minafterlogchatwindow()' title='Minimize window' style='text-decoration:none;color:#FFFFFF;outline:none;'><div><table cellpadding='7' cellspacing='0' border='0' width='100%'><tr><td valign='middle'><font face='verdana' size='2' color='#ffffff'><b>"+welcomemsg+"</b></font></td><td align='right'><SPAN><IMG SRC='/bookbattery/images/downarrow.png' WIDTH='16' HEIGHT='11' BORDER='0' ALT='minimize'></SPAN></td></tr></table></div></A></TD></TR></TABLE></div>";
	$('#logindetails').empty();
	$('#chatagentdiv').empty();
	$('#logindetails').html(loadmsg);
	$('#messagediv').hide();
	$('#logindetails').show();
	$('#header').html(headermsg);
	$('#header').show();
	$('#logoutmsg').html(msg);
	$('#logoutmsg').show();
	$('#livechatdiv').html(loadchathistory);
	$('#livechatdiv').show();
}
//function used to display email and name in chatlogin div after logout
function displogindiv()
{
	$('#livechaticon').hide();
	var chatlogmsg="<div id='livechat' style='background:#FFFFFF;'><TABLE valign='top' border='0' cellspacing='0' cellpadding='0' width='100%'><TR height='35'><TD bgcolor='#4682b4'><A HREF='javascript:void(null)' onclick='minchatafterlogoutwindow()' title='Minimize window' style='text-decoration:none;color:#FFFFFF;outline:none;'><div><table cellpadding='7' cellspacing='0' border='0' width='100%'><tr><td valign='middle'><font face='verdana' size='2' color='#ffffff'><b>"+welcomemsg+"</b></font></td><td align='right'><SPAN><IMG SRC='/bookbattery/images/downarrow.png' WIDTH='16' HEIGHT='11' BORDER='0' ALT='minimize'></SPAN></td></tr></table></div></A></TD></TR><TR height='20'><td align='left'id='namediv'></td></TR><TR><TD class='content'>Your Name:* <BR><BR><input type='text' size='15' style='width:200px;height:25px;border: 1px solid #CCC;font-size: 13px;font-family: Verdana;' onmousedown='clearerrmsg();' onkeydown='clearerrmsg();' name='chatname' id='chatname' value='"+username+"' maxlength='50' tabindex='1'></TD></TR><TR height='20'><td align='left'id='emaildiv'></td></TR><TR><TD class='content'> Your e-mail:* <BR><BR><input type='text' size='15' style='width:200px;height:25px;border: 1px solid #CCC;font-size: 13px;font-family: Verdana;' onmousedown='clearerrmsg();' onkeydown='clearerrmsg();' name='chatemail' id='chatemail' value='"+useremail+"' maxlength='50' tabindex='1' onkeypress='loginchatKeyPress(event);'></TD></TR><TR height='20'></TR><TR><TD style='padding-left:10px'><input type='button' value='Start Chat' id='startchat' style='cursor:pointer;' onclick='javascript:loginintochat();'></TD></TR><TR height='100'></TR></TABLE></div>";
	$('#logindetails').empty();
	$('#logindetails').html(chatlogmsg);
	$('#logindetails').show();
}
//function used to display the chatended div after logout
function minchatafterlogoutwindow()
{
	var minimizemsg="<A href='javascript:void(null)' onclick='displogindiv()' style='text-decoration:none;outline:none;'><TABLE cellspacing='0' cellpadding='5' border='0' width='240'><TR><TD valign='top' width='230'><font face='verdana' size='2' color='#ffffff'><div style='width:200px;overflow:hidden;text-overflow:ellipsis;white-space:nowrap;'><b>Chat&nbsp;ended</b><div></font></TD><TD valign='center' width='10'><IMG SRC='/bookbattery/images/uparrow.png' WIDTH='16' HEIGHT='11' BORDER='0' ALT='maximize'></TD></TR></TABLE></A>";
	$('#logindetails').hide();
	$('#livechaticon').empty();
	$('#livechaticon').html(minimizemsg);
	$('#livechaticon').show();
}
//this function is used to display the enquiry message form to user when all chat agents are busy
function sendenqmsg(batterymodel,price,withbatprice,striconurl,stractprice,capacity)
{
	var chatname=document.getElementById("chatname").value;
	var chatemail1=document.getElementById("chatemail1").value;
	var message=document.getElementById("busymessage").value;
	var iCharsspecial = "`~,!#$%^&*()+=[]\\\';/{}|\":<>?";
	if (chatname=="")
	{
		errMsg ="<font color='#DD0000'>Please fill out name...!</font>";
		document.getElementById("namediv").innerHTML=errMsg;
		document.mobbatterydetails.chatname.focus();
		$("#namediv").show();
		return ;
	}
	if( chatname.indexOf(' ')==0 )
	{
		errMsg ="<font color='#DD0000'>Only spaces or space before text is not allowed in Name field!</font>";
		document.getElementById("namediv").innerHTML=errMsg;
		document.mobbatterydetails.chatname.focus();
		$("#namediv").show();
		return ;
	}
	if (chatemail1=="")
	{
		errMsg ="<font color='#DD0000'>Please fill out email...!</font>";
		document.getElementById("emaildiv").innerHTML=errMsg;
		document.mobbatterydetails.chatemail1.focus();
		$("#emaildiv").show();
		return ;
	}
	if(chatemail1!="")	
    var emailidat = "@";
	var emailiddot = ".";
	var lat = chatemail1.indexOf(emailidat);
	var emailidlength = chatemail1.length;
	var ldot = chatemail1.indexOf(emailiddot);
	var sst=chatemail1.substring(emailidat,emailidlength);
	var sstdot=sst.indexOf(emailiddot);
	if (chatemail1.indexOf(emailidat) == -1)
	{
		errMsg ="<font color='#DD0000'>The Email-id should be in the form of username@gmail.com</font>";
		document.getElementById("emaildiv").innerHTML=errMsg;
		document.mobbatterydetails.chatemail1.focus();
		$("#emaildiv").show();
		return;
	}
	if (chatemail1.indexOf(emailidat) == -1 || chatemail1.indexOf(emailidat) == 0 || chatemail1.indexOf(emailidat) == emailidlength)
	{
		errMsg ="<font color='#DD0000'>The Email-id should be in the form of username@gmail.com</font>";
		document.getElementById("emaildiv").innerHTML=errMsg;
		document.mobbatterydetails.chatemail1.focus();
		$("#emaildiv").show();
		return;
	}
	if (chatemail1.indexOf(emailidat) < 2)
	{
		errMsg ="<font color='#DD0000'>Please enter valid Email-id</font>";
		document.getElementById("emaildiv").innerHTML=errMsg;
		document.mobbatterydetails.chatemail1.focus();
		$("#emaildiv").show();
		return;
	}
	if (chatemail1.indexOf(emailiddot) == -1 || chatemail1.indexOf(emailiddot) == 0 || chatemail1.indexOf(emailiddot) == emailidlength)
	{
		errMsg ="<font color='#DD0000'>The Email-id should be in the form of username@gmail.com</font>";
		document.getElementById("emaildiv").innerHTML=errMsg;
		document.mobbatterydetails.chatemail1.focus();
		$("#emaildiv").show();
		return;
	}
	if((emailidlength-1)-sstdot<2)
	{
		errMsg ="<font color='#DD0000'>Please enter valid Email-id</font>";
		document.getElementById("emaildiv").innerHTML=errMsg;
		document.mobbatterydetails.chatemail1.focus();
		$("#emaildiv").show();
		return;
	}
	if (chatemail1.indexOf(emailidat, (lat + 1)) != -1)
	{
		errMsg ="<font color='#DD0000'>The Email-id should be in the form of username@gmail.com</font>";
		document.getElementById("emaildiv").innerHTML=errMsg;
		document.mobbatterydetails.chatemail1.focus();
		$("#emaildiv").show();
		return;
	}
	if (chatemail1.substring(lat - 1, lat) == emailiddot || chatemail1.substring(lat + 1, lat + 2) == emailiddot || chatemail1.substring(ldot+1,ldot+2)==emailiddot)
	{
		errMsg ="<font color='#DD0000'>The Email-id should be in the form of username@gmail.com</font>";
		document.getElementById("emaildiv").innerHTML=errMsg;
		document.mobbatterydetails.chatemail1.focus();
		$("#emaildiv").show();
		return;
	}
	if (chatemail1.indexOf(emailiddot, (lat + 2)) == -1)
	{
		errMsg ="<font color='#DD0000'>The Email-id should be in the form of username@gmail.com</font>";
		document.getElementById("emaildiv").innerHTML=errMsg;
		document.mobbatterydetails.chatemail1.focus();
		$("#emaildiv").show();
		return;
	}
	if (chatemail1.indexOf(" ") != -1)
	{
		errMsg ="<font color='#DD0000'>The Email-id should be in the form of username@gmail.com</font>";
		document.getElementById("emaildiv").innerHTML=errMsg;
		document.mobbatterydetails.chatemail1.focus();
		$("#emaildiv").show();
		return;
	}
	if (chatemail1.indexOf("_@") != -1)
	{
		errMsg ="<font color='#DD0000'>The Email-id should be in the form of username@gmail.com</font>";
		document.getElementById("emaildiv").innerHTML=errMsg;
		document.mobbatterydetails.chatemail1.focus();
		$("#emaildiv").show();
		return;
	}
	var regularexprsn = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
	if (! document.mobbatterydetails.chatemail1.value.match(regularexprsn))
	{
		errMsg ="<font color='#DD0000'>The Email-id should be in the form of username@gmail.com</font>";
		document.getElementById("emaildiv").innerHTML=errMsg;
		document.mobbatterydetails.chatemail1.focus();
		$("#emaildiv").show();
		return;
	}
	var reglrexprsn=/@(([^\.]*\.[^\.]*)?){1,3}$/;
	if (! document.mobbatterydetails.chatemail1.value.match(reglrexprsn))
	{
		errMsg ="<font color='#DD0000'>The Email-id should be in the form of username@gmail.com</font>";
		document.getElementById("emaildiv").innerHTML=errMsg;
		document.mobbatterydetails.chatemail1.focus();
		$("#emaildiv").show();
		return;
	}
	var regex=/^[a-zA-Z0-9_\+-]+(\.[a-zA-Z0-9_\+-]+)*@[a-zA-Z0-9-]+(\.[a-zA-Z0-9-]+)*\.([a-zA-Z]{2,3})$/;
	if (! document.mobbatterydetails.chatemail1.value.match(regex))
	{
		errMsg ="<font color='#DD0000'>The Email-id should be in the form of username@gmail.com</font>";
		document.getElementById("emaildiv").innerHTML=errMsg;
		document.mobbatterydetails.chatemail1.focus();
		$("#emaildiv").show();
		return;
	}
	if(chatemail1!="")
    var hi = "-";
	var us = "_";
	var lhi = chatemail1.indexOf(hi);
	var emailidlength = chatemail1.length;
	var lus = chatemail1.indexOf(us);
	if (chatemail1.substring(lhi-1,lhi)==hi || chatemail1.substring(lhi+1,lhi+2)==hi)
	{
		errMsg ="<font color='#DD0000'>The Email-id should be in the form of username@gmail.com</font>";
		document.getElementById("emaildiv").innerHTML=errMsg;
		document.mobbatterydetails.chatemail1.focus();
		$("#emaildiv").show();
		return;
	}
	if ((chatemail1.substring(lhi-1,lhi)==hi || chatemail1.substring(lus+1,lus+2)==us) || (chatemail1.substring(lus-1,lus)==us || chatemail1.substring(lhi+1,lhi+2)==hi))
	{
		errMsg ="<font color='#DD0000'>The Email-id should be in the form of username@gmail.com</font>";
		document.getElementById("emaildiv").innerHTML=errMsg;
		document.mobbatterydetails.chatemail1.focus();
		$("#emaildiv").show();
		return;
	}
	if (document.mobbatterydetails.chatemail1.value.indexOf('com.in') >=0)
	{
		errMsg ="<font color='#DD0000'>The Email-id should be in the form of username@gmail.com</font>";
		document.getElementById("emaildiv").innerHTML=errMsg;
		document.mobbatterydetails.chatemail1.focus();
		$("#emaildiv").show();
		return;
	}
	if (chatemail1.substring(lhi - 1, lhi) == us || chatemail1.substring(lhi + 1, lhi + 2) == us || chatemail1.substring(lus+1,lus+2)==us)
	{
		errMsg ="<font color='#DD0000'>The Email-id should be in the form of username@gmail.com</font>";
		document.getElementById("emaildiv").innerHTML=errMsg;
		document.mobbatterydetails.chatemail1.focus();
		$("#emaildiv").show();
		return;
	}
	if (chatemail1.indexOf(hi, (lhi + 1)) != -1)
	{
		errMsg ="<font color='#DD0000'>Please enter valid Email-id</font>";
		document.getElementById("emaildiv").innerHTML=errMsg;
		document.mobbatterydetails.chatemail1.focus();
		$("#emaildiv").show();
		return;
	}
	if (document.mobbatterydetails.chatemail1.value.indexOf('.com.com')>=0)
	{
		errMsg ="<font color='#DD0000'>The Email-id should be in the form of username@gmail.com</font>";
		document.getElementById("emaildiv").innerHTML=errMsg;
		document.mobbatterydetails.chatemail1.focus();
		$("#emaildiv").show();
		return;
	}
	if (document.mobbatterydetails.chatemail1.value.indexOf('gmail') >=0)
	{
		if (document.mobbatterydetails.chatemail1.value.indexOf('com')<0 )
		{
			var emailidvalue=document.mobbatterydetails.chatemail1.value;
		    var lastvalue=emailidvalue.slice(-4);
		    var comvalue=lastvalue.toLowerCase();
		    if(comvalue!=".com")
		    {
		        errMsg ="<font color='#DD0000'>The Email-id should be in the form of username@gmail.com</font>";
		        document.getElementById("emaildiv").innerHTML=errMsg;
		        document.mobbatterydetails.chatemail1.focus();
				$("#emaildiv").show();
		        return;
		    } 
		}
	}
	if (document.mobbatterydetails.chatemail1.value.indexOf('rediffmail') >=0)
	{
		if (document.mobbatterydetails.chatemail1.value.indexOf('com')<0)
		{
			var emailidvalue=document.mobbatterydetails.chatemail1.value;
		    var lastvalue=emailidvalue.slice(-4);
		    var comvalue=lastvalue.toLowerCase();
		    if(comvalue!=".com")
		    {
		        errMsg ="<font color='#DD0000'>The Email-id should be in the form of username@gmail.com</font>";
		        document.getElementById("emaildiv").innerHTML=errMsg;
		        document.mobbatterydetails.chatemail1.focus();
				$("#emaildiv").show();
		        return;
		    } 
		}
	}
	if (document.mobbatterydetails.chatemail1.value.indexOf('hotmail') >=0)
	{
		if (document.mobbatterydetails.chatemail1.value.indexOf('com')<0)
		{
			var emailidvalue=document.mobbatterydetails.chatemail1.value;
		    var lastvalue=emailidvalue.slice(-4);
		    var comvalue=lastvalue.toLowerCase();
		    if(comvalue!=".com")
		    {
		        errMsg ="<font color='#DD0000'>The Email-id should be in the form of username@gmail.com</font>";
		        document.getElementById("emaildiv").innerHTML=errMsg;
		        document.mobbatterydetails.chatemail1.focus();
				$("#emaildiv").show();
		        return;
		    } 
		}
	}
	if (document.mobbatterydetails.chatemail1.value.indexOf('live') >=0)
	{
		if (document.mobbatterydetails.chatemail1.value.indexOf('com')<0)
		{
			var emailidvalue=document.mobbatterydetails.chatemail1.value;
		    var lastvalue=emailidvalue.slice(-4);
		    var comvalue=lastvalue.toLowerCase();
		    if(comvalue!=".com")
		    {
		        errMsg ="<font color='#DD0000'>The Email-id should be in the form of username@gmail.com</font>";
		        document.getElementById("emaildiv").innerHTML=errMsg;
		        document.mobbatterydetails.chatemail1.focus();
				$("#emaildiv").show();
		        return;
		    } 
		}
	}
	if (document.mobbatterydetails.chatemail1.value.indexOf('ymail') >=0)
	{
		if (document.mobbatterydetails.chatemail1.value.indexOf('com')<0)
		{
			var emailidvalue=document.mobbatterydetails.chatemail1.value;
		    var lastvalue=emailidvalue.slice(-4);
		    var comvalue=lastvalue.toLowerCase();
		    if(comvalue!=".com")
		    {
		        errMsg ="<font color='#DD0000'>The Email-id should be in the form of username@gmail.com</font>";
		        document.getElementById("emaildiv").innerHTML=errMsg;
				document.mobbatterydetails.chatemail1.focus();
				$("#emaildiv").show();
		        return;
		    } 
		}
	}
	if (chatemail1.indexOf("__") != -1)
	{
		errMsg ="<font color='#DD0000'>Please Enter Valid E-mail Id</font>";
		document.getElementById("emaildiv").innerHTML=errMsg;
		document.mobbatterydetails.chatemail1.focus();
		$("#emaildiv").show();
		return;
	}
	if (chatemail1.indexOf("--") != -1)
	{
		errMsg ="<font color='#DD0000'>Please Enter Valid E-mail Id</font>";
		document.getElementById("emaildiv").innerHTML=errMsg;
		document.mobbatterydetails.chatemail1.focus();
		$("#emaildiv").show();
		return;
	}
	if (chatemail1.indexOf("-_") != -1)
	{
		errMsg ="<font color='#DD0000'>Please Enter Valid E-mail Id</font>";
		document.getElementById("emaildiv").innerHTML=errMsg;
		document.mobbatterydetails.chatemail1.focus();
		$("#emaildiv").show();
		return;
	}
	if (chatemail1.indexOf("_-") != -1)
	{
		errMsg ="<font color='#DD0000'>Please Enter Valid E-mail Id</font>";
		document.getElementById("emaildiv").innerHTML=errMsg;
		document.mobbatterydetails.chatemail1.focus();
		$("#emaildiv").show();
		return;
	}
	if (document.mobbatterydetails.chatemail1.valuelength > 50)
	{
		errMsg ="<font color='#DD0000'>Please Enter Only 50 Characters in the E-mail field.</font>";
		document.getElementById("emaildiv").innerHTML=errMsg;
		document.mobbatterydetails.chatemail1.focus();
		$("#emaildiv").show();
		return;
	}
	for (var i = 0; i < document.mobbatterydetails.chatemail1.value.length; i++)
	{
  		if (iCharsspecial.indexOf(document.mobbatterydetails.chatemail1.value.charAt(i)) != -1)
		{
			errMsg ="<font color='#DD0000'>Special characters are not allowed in Email-Id field.</font>";
			document.getElementById("emaildiv").innerHTML=errMsg;
			document.mobbatterydetails.chatemail1.focus();
			$("#emaildiv").show();
			return;
  		}
	}
	if (message=="")
	{
		errMsg ="<font color='#DD0000'>Please enter text in message field...!</font>";
		document.getElementById("busyerrormsg").innerHTML=errMsg;
		document.mobbatterydetails.busymessage.focus();
		$("#busyerrormsg").show();
		return ;
	}
	if (document.mobbatterydetails.busymessage.value.indexOf(' ')==0 )
	 {
		 errMsg ="<font color='#DD0000'>Only spaces or space before text is not allowed in the \'Message\' field.</font>";
		document.getElementById("busyerrormsg").innerHTML=errMsg;
		document.mobbatterydetails.busymessage.focus();
		$("#busyerrormsg").show();
		return ;
	 }
	if (document.mobbatterydetails.busymessage.value.length < 3)
	 {
		errMsg ="<font color='#ff3333'>Please enter at least 3 characters in the \'Message\' field.</font>";
		document.getElementById("busyerrormsg").innerHTML=errMsg;
		document.mobbatterydetails.busymessage.focus();		
		$("#busyerrormsg").show();
		return ;
	 }
	if(message.length>2500)
	{
			errMsg ="<font color='#ff3333'>Yor message length has exceeded limit. Please Reduce.</font>";
		document.getElementById("busyerrormsg").innerHTML=errMsg;
		document.mobbatterydetails.busymessage.focus();		
		$("#busyerrormsg").show();
		return ;
	}
	var messages=escape(message);
	messages=messages.replace(/\+/g, '%2B');

	$("#livechaticon").hide();
	$("#logindetails").hide();
	$('#pollopinion').empty();
	$('#messagedetails').hide();
	errMsg ="<table width='100%' bgcolor='#FFFFFF' valign='top'><tr height='2'><table width='100%' bgcolor='#FFFFFF' valign='top'><tr><td width='100%' align='center' style='COLOR:#F96F2B; font-size: 24px;font-weight: 600;text-align: center;'> BookBattery</td><td width='100%' align='center' style='COLOR:#F96F2B; font-size: 24px;font-weight: 600;text-align: left;'onclick=\"javascript:closeconfirmationdiv(greyout(false));\"> X</td></tr></table><hr style='background: white; width: 95%;margin-top: 1px;margin-bottom: 1px;'><tr><td><table width='100%'  cellspacing='0' cellpadding='0' bgcolor='#FFFFFF'><tr><td width='100%' colspan='3'><table width='100%' cellspacing='0' cellpadding='0' border='0'><tr><td width='100%' style='color: white;font-size: 16px;padding: 10px;' align='center'>Thank you for contacting us.</br> Our customer service representative will reply to you soon.</td></tr><tr><td width='100%' align='center'><input style='font-family: Helvetica Neue,Helvetica,Arial,sans-serif; background-color:#D6511C; color: white; font-size: 17px;padding: 5px 15px;font-weight: lighter; margin-left: 5px;margin-top: 5px;' type='button' class='btn'  value='Ok' align='left' onclick=\"javascript:closeconfirmationdiv(greyout(false));\"></td></tr><tr height='26'><td ></td></tr><tr><td height='15'></td></tr></table></table>";
		document.getElementById("livechatlogindiv").innerHTML="";
		document.getElementById("livechatlogindiv").style.display='block';
		document.getElementById("livechatlogindiv").innerHTML=errMsg
			greyout(true)

	$.get("/bookbattery/servlet/ChatLogin?hidWhatToDo=sendEnquirymessage&chatname="+chatname+"&chatemail="+chatemail1+"&message="+messages+"&userid="+usrid+"&requestno="+(Math.random()*100),function(response, status, xhr) 
	{
		if (status == "success")
		{

		}
	});
}

function closeconfirmationdiv()
{
	$('#livechatlogindiv').hide();
		greyout(false);
}
//this function is used to display message option div for users when all chat agents are busy.
function dispbusydiv(batterymodel,price,withbatprice,striconurl,stractprice,capacity)
{

	var busymsg="<div id='loginchatdiv' style='background:#232323;'><TABLE valign='top' border='0' cellspacing='0' cellpadding='0' width='100%'><TR height='35'><TD bgcolor='#232323'><div><table cellpadding='7' cellspacing='0' border='0' width='100%'><tr><td valign='middle'><font face='verdana' size='2' color='#F96F2B'><b style='font-size: 17px;color: #F96F2B;font-weight: 800;'>Welcome to BookBattery Live </b></font></td><td align='right'><SPAN><A HREF='javascript:hideChatMessageDiv();'  title='Close window' style='text-decoration:none;color:#FFFFFF;outline:none;'><IMG SRC='/bookbattery/images/chatclose.png' WIDTH='16' HEIGHT='11' BORDER='0' ALT='minimize'></A></SPAN></td></tr></table></div><hr style='background: white; width: 95%;margin-top: 1px;margin-bottom: 1px;'></TD></TR><tr height='5'></tr><tr height='30'><td style='padding: 10px;'><span style='font-size:14px;color:#ffffff; margin-top:2em;margin-bottom:3em'>We're not around right now.</br> Leave us a message with how we can help you, and we will answer you as soon as humanly possible! =).</br>Support Team will be available from 9:00AM to 6:30PM(Mon - Sat).</span><hr style='background: white; width: 95%;margin-top: 1px;margin-bottom: 1px;'></td></tr></table><table style='width: 80%;' align='center'><TR height='10'><td align='center'id='namediv'></td></TR><TR><TD class='content'>Your Name:*<BR> <input type='text' size='15' style='width:100%;height:40px;border: 1px solid #CCC;font-size: 15px;' onmousedown='clearerrmsg();' value='"+username+"' readonly onkeydown='clearerrmsg();' name='chatname' id='chatname' maxlength='50' tabindex='1'></TD></TR><TR height='20'><td align='center'id='emaildiv'></td></TR><TR><TD class='content'> Your e-mail:*<BR> <input type='text' size='15' style='width:100%;height:40px;border: 1px solid #CCC;font-size: 15px;' onmousedown='clearerrmsg();' value='"+useremail+"' readonly onkeydown='clearerrmsg();' name='chatemail1' id='chatemail1' maxlength='50' tabindex='1' onkeypress='loginchatKeyPress(event);'></TD></TR><TR height='10'><td align='center'id='busyerrormsg'></td></TR><TR><TD class='content'> Your Message:* <BR><BR><div id='messagediv' style='width:100%;'><TEXTAREA NAME='busymessage' id='busymessage' onmousedown='clearerrmsg();' onkeydown='clearerrmsg();' style='width:100%;height:60px;border: 1px solid #CCC;font-size: 15px; outline:none;' ></TEXTAREA></div></TD></TR><TR height='5'></TR><TR align='center' ><TD align='center' ><input style='font-family: Helvetica Neue,Helvetica,Arial,sans-serif;background-color:#D6511C; color: white; font-size: 17px;padding: 5px 15px;font-weight: lighter; margin-left: 5px;margin-top: 5px;' type='button' class='btn' value='Send Message' id='startchat' style='cursor:pointer' onclick=\'javascript:sendenqmsg(\""+batterymodel+"\",\""+price+"\",\""+withbatprice+"\",\""+striconurl+"\",\""+stractprice+"\",\""+capacity+"\");\'> <input style='font-family: Helvetica Neue,Helvetica,Arial,sans-serif; background-color:#D6511C; color: white; font-size: 17px;padding: 5px 15px;font-weight: lighter; margin-left: 5px;margin-top: 5px;' type='button' class='btn'  value='Close' align='left' onclick=\"javascript:hideChatMessageDiv(greyout(false));\"></TD></TR><TR height='10'></TR></table></div>";
	$('#messagedetails').html(busymsg);
	$('#messagedetails').show();
	greyout(true)
	
}
function hideChatMessageDiv()
{
	$('#messagedetails').hide();
	greyout(false)
}
//this function is used to display busy message div and to show the livechat iocn div
function dispbusyiconstartdiv()
{
	$('#logindetails').hide();
	var minimizemsg="<A href='javascript:void(null)' onclick='openchatwindow()' style='text-decoration:none;outline:none;'><TABLE cellspacing='0' cellpadding='5' border='0' width='240'><TR><TD valign='top' width='230'><font face='verdana' size='2' color='#ffffff'><div style='width:200px;overflow:hidden;text-overflow:ellipsis;white-space:nowrap;'><b>Live Chat</b><div></font></TD><TD valign='center' width='10'><IMG SRC='/bookbattery/images/uparrow.png' WIDTH='16' HEIGHT='11' BORDER='0' ALT='maximize'></TD></TR></TABLE></A>";
	$('#livechaticon').empty();
	$('#livechaticon').html(minimizemsg);
	$('#livechaticon').show();
}
//this function is used to hide livechat icon div and to display welcome div.
function hidebusydiv(batterymodel,price,withbatprice,striconurl,stractprice,capacity)
{
	$('#livechaticon').hide();
	var msg="<div style='background:#FFFFFF;'><TABLE valign='top' border='0' cellspacing='0' cellpadding='0' width='100%'><TR height='35'><TD bgcolor='#4682b4'><A HREF='javascript:void(null)' onclick='minchatwindow()' title='Minimize window' style='text-decoration:none;color:#FFFFFF;outline:none;'><div><table cellpadding='7' cellspacing='0' border='0' width='100%'><tr><td valign='middle'><font face='verdana' size='2' color='#ffffff'><b>"+welcomemsg+"</b></font></td><td align='right'><SPAN><IMG SRC='/bookbattery/images/downarrow.png' WIDTH='16' HEIGHT='11' BORDER='0' ALT='minimize'></SPAN></td></tr></table></div></A></TD></TR><TR height='20'><td align='left'id='namediv'></td></TR><TR><TD class='content'>Your Name:* <BR><BR><input type='text' size='15' style='width:200px;height:40px;border: 1px solid #CCC;font-size: 13px;font-family: Verdana;' onmousedown='clearerrmsg();' onkeydown='clearerrmsg();' name='chatname' id='chatname' maxlength='50' tabindex='1'></TD></TR><TR height='20'><td align='left'id='emaildiv'></td></TR><TR><TD class='content'> Your e-mail:* <BR><BR><input type='text' size='15' style='width:200px;height:40px;border: 1px solid #CCC;font-size: 13px;font-family: Verdana;' onmousedown='clearerrmsg();' onkeydown='clearerrmsg();' name='chatemail' id='chatemail' maxlength='50' tabindex='1' onkeypress='loginchatKeyPress(event);'></TD></TR><TR height='20'></TR><TR><TD style='padding-left:10px'><input type='button' value='Start Chat' id='startchat' onclick='javascript:loginintochat();'></TD></TR><TR height='100'></TR></TABLE></div>";
	$('#logindetails').empty();
	$('#logindetails').html(msg);
	$('#logindetails').show();
}

