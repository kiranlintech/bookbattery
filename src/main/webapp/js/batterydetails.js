function OnClickChatAgent(batterybrand,batterymodel,price,withbatprice,ThumbIconPath,stractprice,capacity,fCountOnThisPage)
{	
	var batterytype = document.getElementById("batterytype").value
	//var batterybrand = document.getElementById("batterybrand").value;

	var city=document.getElementById("city").value;
	var pincity=document.getElementById("pincity").value;
	var strstate=document.getElementById("strstate").value;
	var area = document.getElementById("strarea").value;
	var strpincode=document.getElementById("strpincode").value;
	var vehiclemake=document.getElementById("vehiclemake").value;
	var vehiclemodel=document.getElementById("vehiclemodel").value;

	if($('#logindetails').is(':visible') || $('#livechaticon').is(':visible')||$('#logindetailstablet').is(':visible'))
	{		
		var batterywalecookieids=$.cookie('batterywalelivechatcookie');
		if(batterywalecookieids!=undefined)
		{
			$('#livechaticon').hide();
				$('#logindetails').hide();
				$('#logindetailstablet').hide();
			if(batterywalecookieids.indexOf("null")>=0)
			{
				dispbusydiv(batterymodel,price,withbatprice,ThumbIconPath,stractprice,capacity)
			}
			else
			{
				batterywalecookieids=batterywalecookieids.split("|");
				onrefreshdis(batterywalecookieids[0],batterywalecookieids[1],batterywalecookieids[2],batterywalecookieids[3],batterywalecookieids[4],batterywalecookieids[5],batterymodel,price,withbatprice,ThumbIconPath,stractprice,capacity)
			}
		}
		else
		{
				$('#logindetails').hide();
				$('#livechaticon').hide();
				$('#logindetailstablet').hide();
				loginintochat(batterytype,batterybrand,batterymodel,vehiclemake,vehiclemodel,price,withbatprice,ThumbIconPath,stractprice,capacity,strstate,city,area,pincity,strpincode)
		}
	}
	else
	{

				errMsg ="<div class='col-md-4 col-md-offset-4'> <table cellspacing='10' cellpadding='0' ><table valign='top'><tr height='10'><table valign='top'></table><table valign='top'><tr><td width='100%' align='center' style='COLOR:#F96F2B; font-size: 25px;font-weight: 600;text-align: center;'> BookBattery</td><td width='100%' align='center' style='COLOR:#F96F2B; font-size: 24px;font-weight: 600;text-align: left;'href=\"javascript:closechatlogindiv(greyout(false));\" >  <a style='color: #F96F2B;' href=\"javascript:closechatlogindiv(greyout(false));\"> X </a></td></tr></table><hr style='background: white; width: 100%;margin-top: 1px;margin-bottom: 1px;'></tr></table><tr><td><table cellspacing='0' cellpadding='0' align='center' style='width: 100%;'><tr><td width='100%' colspan='3'><table width='100%' cellspacing='0' cellpadding='0' border='0'><td width='18%'></td><td width='76%' class='insidecontent' align='left'><font style='font-size: 15px; color: #ffffff;font-family: sans-serif;' >Enter Your Name </font><font color='red'>*</font></td></tr></table></td><tr><td height='5'></td></tr></tr><tr><td width='100%' align='center'><input class='insidecontent' type='text' autocomplete='off' name='chatname' id='chatname'  style='width:300px;height:40px;border: 2px solid #232323;font-size: 13px;' maxlength='50' ></td></tr><tr><td height='5'></td></tr><tr><td width='100%' colspan='3'><table width='100%' cellspacing='0' cellpadding='0'><tr><td width='18%'></td><td width='76%' class='insidecontent' align='left'><font style='font-size: 15px; color: #ffffff;font-family: sans-serif;'>Enter Your Email </font><font color='red'>*</font></td></tr></table></td><tr><td height='5'></td></tr></tr><tr><td width='100%' align='center'><input class='insidecontent' type='text' autocomplete='off' name='chatemail' id='chatemail'  style='width:300px;height:40px;border: 2px solid #232323;font-size: 13px;' maxlength='50' onkeydown=\"if ((event.which && event.which == 13) || (event.keyCode && event.keyCode == 13)){javascript:loginintochat('"+batterytype+"','"+batterybrand+"','"+batterymodel+"','"+vehiclemake+"','"+vehiclemodel+"','"+price+"','"+withbatprice+"','"+ThumbIconPath+"','"+stractprice+"','"+capacity+"','"+strstate+"','"+city+"','"+area+"','"+pincity+"','"+strpincode+"');return false;} else return true;\"/></td></tr><tr><td height='5'></td></tr>   <tr><td  width='50%' align='center' ><input style='font-family: Helvetica Neue,Helvetica,Arial,sans-serif; background-color:#D6511C; color: white; font-size: 17px;padding: 5px 15px;font-weight: lighter; margin-left: 5px;margin-top: 5px;' type='button' class='btn' name='Submitrret' value='Submit' disable='false' onclick=\"javascript:loginintochat('"+batterytype+"','"+batterybrand+"','"+batterymodel+"','"+vehiclemake+"','"+vehiclemodel+"','"+price+"','"+withbatprice+"','"+ThumbIconPath+"','"+stractprice+"','"+capacity+"','"+strstate+"','"+city+"','"+area+"','"+pincity+"','"+strpincode+"');\"> <input style='font-family: Helvetica Neue,Helvetica,Arial,sans-serif; background-color:#D6511C; color: white; font-size: 17px;padding: 5px 15px;font-weight: lighter; margin-left: 5px;margin-top: 5px;' type='button' class='btn'  value='Close' align='left' onclick=\"javascript:closechatlogindiv(greyout(false));\" ></td></tr>    <tr height='26'><td colspan='3' align='center' class='subheading' id='displayerrormsg'></td></tr><tr><td height='15'></td></tr></table> </div>";	
	
				document.getElementById("livechatlogindiv").innerHTML="";
				document.getElementById("livechatlogindiv").style.display='block';
				document.getElementById("livechatlogindiv").style.top=250+(fCountOnThisPage*180)+"px";
				document.getElementById("livechatlogindiv").innerHTML=errMsg
				greyout(true);
				document.getElementById("chatname").focus();
		}
}
function closechatlogindiv()
{
	$('#livechatlogindiv').hide();
}
function goBack()
{
	window.history.back()
}
/*This function is used to generate verification code for consumers*/
function sortprices()
{
	var sortprice = document.getElementById("sortprice").value;
	var batterytype = document.getElementById("batterytype").value
	var vehiclename = document.getElementById("vehiclemake").value;
	var vehiclemodel = document.getElementById("vehiclemodel").value;
	var batterybrand = document.getElementById("batterybrand").value;
	var batterycapty = document.getElementById("strbatterycapty").value;
	var strstate = document.getElementById("strstate").value;
	var city = document.getElementById("city").value;
	var area = document.getElementById("strarea").value;
	var pincode = document.getElementById("strpincode").value;
	var keyword = "sortprices";

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
		
	document.forms[0].method="post";
	document.forms[0].action="./servlet/BatteryDetails?hidWhatToDo=getbatdetails&batterytype="+batterytype+"&vehiclename="+vehiclename+"&vehiclemodel="+vehiclemodel+"&batterybrand="+batterybrand+"&batterycapty="+batterycapty+"&state="+strstate+"&city="+city+"&area="+area+"&pincode="+pincode+"&sortprice="+sortprice+"&keyword="+keyword;
	//alert(document.batterydet.action);
	document.forms[0].submit();
}
function askcosumerdetails(batterybrand,batterymodel,price,withbatprice,fCountOnThisPage)
{
		errMsg ="<div class='col-md-4 col-md-offset-4'> <table  cellspacing='10' cellpadding='0' bgcolor='#FFFFFF'><table width='350' bgcolor='#FFFFFF' valign='top'><tr height='10'><table width='100%' valign='top'></table><table  width='100%'  valign='top'><tr><td width='100%' align='center' style='COLOR:#F96F2B; font-size: 25px;font-weight: 600;text-align: center;'> BookBattery</td> <td width='100%' align='center' style='COLOR:#F96F2B; font-size: 24px;font-weight: 600;text-align: left;'  href=\"javascript:closemobdiv(greyout(false));\" > <a style='color: #F96F2B;' href=\"javascript:closemobdiv(greyout(false));\"> X </a> </td></tr></table><hr style='background: white; width: 90%;margin-top: 1px;margin-bottom: 1px;'></tr></table><tr><td><table width='100%'  cellspacing='0' cellpadding='0' ><tr><td width='100%' colspan='3'><table width='100%' cellspacing='0' cellpadding='0'><tr><td class='insidecontent' align='center'><font style='font-size: 15px;' color='#FFFFFF'>Please Enter Your Mobile Number!</font></td></tr></table></td><tr><td height='10'></td></tr></tr><tr><td width='100%' align='center'><input class='insidecontent' type='tel' autocomplete='off' name='usermobilenumber' id='usermobilenumber' placeholder='9603467559' style='width:300px;height:40px;border: 2px solid #232323;font-size: 13px;' maxlength='10' onkeydown=\"if ((event.which && event.which == 13) || (event.keyCode && event.keyCode == 13)){javascript:askcosumerdetails1('"+batterybrand+"','"+batterymodel+"','"+price+"','"+withbatprice+"',Submitrret);return false;} else return true;\"/> </td></tr><tr><td height='5'></td></tr><tr><td width='100%' align='center' style='font-size:13px;color:#FFFFFF;	text-decoration:none;padding:1px 1px;'><font color='#ff0000'>*</font>&nbsp;Enter&nbsp;your&nbsp;10&nbsp;digit&nbsp;mobile&nbsp;number</td></tr>    <tr><td  width='50%' align='center' ><input style='font-family: Helvetica Neue,Helvetica,Arial,sans-serif; background-color:#D6511C; color: white; font-size: 17px;padding: 5px 15px;font-weight: lighter; margin-left: 5px;margin-top: 5px;' type='button' class='btn' name='Submitrret' value='Submit' disable='false' onclick=\"javascript:askcosumerdetails1('"+batterybrand+"','"+batterymodel+"','"+price+"','"+withbatprice+"',Submitrret);\"> <input style='font-family: Helvetica Neue,Helvetica,Arial,sans-serif; background-color:#D6511C; color: white; font-size: 17px;padding: 5px 15px;font-weight: lighter; margin-left: 5px;margin-top: 5px;' type='button' class='btn'  value='Close' align='left' onclick=\"javascript:closemobdiv(greyout(false));\" ></td></tr> <td colspan='3' align='center' class='subheading' id='displayrefinederrormsg1'></td><tr><td height='15'></td></tr></table></div>";
	

	document.getElementById("divmobile").innerHTML="";
	document.getElementById("divmobile").style.display='block';
	document.getElementById("divmobile").style.top=180+(fCountOnThisPage*180)+"px";
	document.getElementById("divmobile").innerHTML=errMsg
	greyout(true);
	document.getElementById("usermobilenumber").focus();
}

var verificationcode="";
function askcosumerdetails1(batterybrand,batterymodel,price,withbatprice,varButton)
{
	var strUsermobileno=document.getElementById("usermobilenumber").value;

	if(strUsermobileno == 0 || strUsermobileno == "")
	{
		errMsg ="<font color='#FFFFFF'>Please enter Mobile Number...!</font>";
		document.getElementById("displayrefinederrormsg1").innerHTML=errMsg;
		document.getElementById("usermobilenumber").focus();
		return ;
	}
	else 
	{
		var checkOK = "0123456789";
		var checkStr = strUsermobileno;
		var allValid = true;
		var decPoints = 0;
		var allNum = "";
		for (i = 0;  i < checkStr.length;  i++)
		{
			ch = checkStr.charAt(i);
			for (j = 0;  j < checkOK.length;  j++)
			if (ch == checkOK.charAt(j))
			break;
			if (j == checkOK.length)
			{
				allValid = false;
				break;
			}

		}
		if (!allValid)
		{
		 errMsg ="<font color='#FFFFFF'>Please enter only digits...!</font>";
		document.getElementById("displayrefinederrormsg1").innerHTML=errMsg;
		document.getElementById("usermobilenumber").value="";
		document.getElementById("usermobilenumber").focus();
		return ;
		
		}
	}
	if (document.getElementById("usermobilenumber").value.length<10)
	{
		errMsg ="<font color='#FFFFFF'>Please enter valid Number...!</font>";
		document.getElementById("displayrefinederrormsg1").innerHTML=errMsg;
		document.getElementById("usermobilenumber").focus();
		return;
	}
	if (document.getElementById("usermobilenumber").value.length==10)
	{
		if (strUsermobileno < 7000000000 )
		 {
			errMsg ="<font color='#FFFFFF'>Number Should start with 7 or 8 or 9...!</font>";
			document.getElementById("displayrefinederrormsg1").innerHTML=errMsg;
			document.getElementById("usermobilenumber").focus();
			return ;
		 }
	}  
	if (document.getElementById("usermobilenumber").value.length>10)
	{
		if (strUsermobileno < 917000000000 || strUsermobileno >= 920000000000 )
		{
			errMsg ="<font color='#FFFFFF'>Number Should start with 91-7 or 8 or 9...!</font>";
			document.getElementById("displayrefinederrormsg1").innerHTML=errMsg;
			document.getElementById("usermobilenumber").focus();
			return ;
		}
	}  
	varButton.disabled=true;
	varButton.value='Please Wait...';
	var xmlhttp= "";
	var resp= "";
	var url="./servlet/BatteryDetails?hidWhatToDo=sentverificationcode&strUsermobileno="+strUsermobileno+"&batterybrand="+batterybrand+"&batterymodel="+batterymodel+"&price="+price+"&withbatprice="+withbatprice;
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
		if (xmlhttp.readyState==4)
		{
			if(xmlhttp.status!=200)
			{
				return;
			}
			else
			{
				resp1 = xmlhttp.responseText;
				//$('#divmobile').hide();
				assetpos = resp1.split('^');
				resp=assetpos[0];
				id=assetpos[1];	
					//alert(id);
				id=id.trim();
				verificationcode=id;
				
				document.getElementById("divmobile").innerHTML="";
				document.getElementById("divmobile").innerHTML=resp;
				document.getElementById("verifycode").focus();
			}
		}	
	}
	xmlhttp.open("GET",url,true);
	xmlhttp.send();	
	
}
function getconsumerdetails(batterybrand,batterymodel,price,withbatprice,strUsermobileno,verificationcode,varButton)
{
	var strusername=document.getElementById("username").value;
	var emailid=document.getElementById("emailid").value;
	var addrs1=document.getElementById("addrs1").value;
	var addrs2=document.getElementById("addrs2").value;  
	var userarea=document.getElementById("userarea").value; 
	var usercity=document.getElementById("usercity").value; 
	var userzipcode=document.getElementById("userzipcode").value;
	//var batterybrand=document.getElementById("batterybrand").value; 
	var city=document.getElementById("city").value;
	var pincity=document.getElementById("pincity").value;
	var strarea=document.getElementById("userarea").value; 
	var strstate=document.getElementById("strstate").value;
	var strpincode=document.getElementById("userzipcode").value;
	var batterytype=document.getElementById("batterytype").value 
	var vehiclemake=document.getElementById("vehiclemake").value;
	var vehiclemodel=escape(document.getElementById("vehiclemodel").value);
	var agentname=document.getElementById("agentname").value;

     var reg="/@(([^\.-]*\.[^\.]*)?){1,3}$/";
     var re =
/^(([^()[\]\\.;:\s@\"]+(\.[^()[\]\\.;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
     var regexp=/^[a-zA-Z0-9_\+-]+(\.[a-zA-Z0-9_\+-]+)*@[a-zA-Z0-9-]+(\.[a-zA-Z0-9-]+)*\.([a-zA-Z]{2,3})$/;
     var iChars1 = "`~,!#$%^&*()+=[]\\\';/{}|\":<>?";
     var iChars3 = "`~!@#$%^&*()+=_[]\\\';,/{}|\":<>?";
     var iChars = "!@~`������'\"#";
     var alpchar="/[a-zA-z]/";
     var expr="/^[.-_]/";
     var reg=/@(([^\.]*\.[^\.]*)?){1,3}$/;
     var iChars2 = "`~!@#$%^&*()+=[]\\\';,/{}|\"<>?";
	 var nonums ="1234567890";
	 var dot="."
	var iChars12 = "\\\'";
	var nonums1 =/^([a-z]|[A-Z]| )*$/;

	if(strusername == "")
     {
		errMsg ="<font color='#FFFFFF'>Please enter User Name...!</font>";
		document.getElementById("displayrefinederrormsg").innerHTML=errMsg;
		document.getElementById("username").focus();
		return ;
     }
     if(strusername.length <3)
     {
		errMsg ="<font color='#FFFFFF'>UserName should have minimum 3 characters</font>";
		document.getElementById("displayrefinederrormsg").innerHTML=errMsg;
		document.getElementById("username").focus();
		return ;
     }
     if(strusername.length == 3)
     {
         if (strusername.indexOf(' ') >= 0 )
         {
			errMsg ="<font color='#FFFFFF'>User Name should have minimum 3 characters</font>";
			document.getElementById("displayrefinederrormsg").innerHTML=errMsg;
			document.getElementById("username").focus();
			return ;
         }
     }
     if(strusername.length == 3)
     {
         if (strusername.indexOf(dot) >= 0 )
         {
			errMsg ="<font color='#FFFFFF'>UserName is invalid</font>";
			document.getElementById("displayrefinederrormsg").innerHTML=errMsg;
			document.getElementById("username").focus();
			return ;
         }
     }
     if (document.getElementById("username").value.indexOf(' ')==0 )
     {
			errMsg ="<font color='#FFFFFF'>UserName should not start with space</font>";
			document.getElementById("displayrefinederrormsg").innerHTML=errMsg;
			document.getElementById("username").focus();
			return ;
     }
	 if (strusername.indexOf(' ')==0 )
     {
			errMsg ="<font color='#FFFFFF'>UserName should not end with space</font>";
			document.getElementById("displayrefinederrormsg").innerHTML=errMsg;
			document.getElementById("username").focus();
			return ;
     }
     //if (strusername.indexOf('  ') >= 0 )
     //{
	//		errMsg ="<font color='#FFFFFF'>UserName should not end with space</font>";
	//		document.getElementById("displayrefinederrormsg").innerHTML=errMsg;
	//		document.getElementById("username").focus();
	//		return ;
    // }
	// if (strusername.indexOf(' ') >= 0 )
   //  {
	//		errMsg ="<font color='#FFFFFF'>UserName should not end with space</font>";
	//		document.getElementById("displayrefinederrormsg").innerHTML=errMsg;
	//		document.getElementById("username").focus();
	//		return ;
    // }
     if (strusername.indexOf('..') >= 0 )
     {
			errMsg ="<font color='#FFFFFF'>Invalid User Name</font>";
			document.getElementById("displayrefinederrormsg").innerHTML=errMsg; 
			document.getElementById("username").focus();
			return ;
     }
	// if (strusername.indexOf('.') >= 0 )
    // {
	//		errMsg ="<font color='#FFFFFF'>UserName should not end with space</font>";
	//		document.getElementById("displayrefinederrormsg").innerHTML=errMsg;
	//		document.getElementById("username").focus();
	//		return ;
    //}
     if (document.getElementById("username").value.indexOf(dot)==0 )
     {
			errMsg ="<font color='#FFFFFF'> User Name should not start with dot</font>";
			document.getElementById("displayrefinederrormsg").innerHTML=errMsg;
			document.getElementById("username").focus();
			return ;
     }
     for (var i = 0; i < document.getElementById("username").value.length; i++)
     {
         if (iChars3.indexOf(document.getElementById("username").value.charAt(i))!= -1)
         {
			errMsg ="<font color='#FFFFFF'>Special characters are not allowed in User Name field.</font>";
			document.getElementById("displayrefinederrormsg").innerHTML=errMsg;
			document.getElementById("username").focus();
			return ;
         }
     }
     for (var i = 0; i < document.getElementById("username").value.length; i++)
     {
         if (nonums.indexOf(document.getElementById("username").value.charAt(i))!= -1)
         {
			errMsg ="<font color='#FFFFFF'>Numbers are not allowed in User Name field.</font>";
			document.getElementById("displayrefinederrormsg").innerHTML=errMsg;
			document.getElementById("username").focus();
			return ;
         }
     }
	if (/[a-z][A-Z]{2}/i.test(document.getElementById("username").value) != true) 
	{
	  errMsg ="<font color='#FFFFFF'>Please enter atleast 3 Charaters together in the User Name Field.</font>";
		document.getElementById("displayrefinederrormsg").innerHTML=errMsg;
		document.getElementById("username").focus();
		return ;
	 }

		var at="@"
		var lat=emailid.indexOf(at)
		var lstr=emailid.length
		var ldot=emailid.indexOf(dot)
		var sst=emailid.substring(at,lstr)
		var sstdot=sst.indexOf(dot)
		var hi = "-"
		var us = "_"
		var lhi = emailid.indexOf(hi)
		var lus = emailid.indexOf(us)
	

     if(emailid == "")
     {
         errMsg ="<font color='#FFFFFF'>Please Enter Email id</font>";
		 document.getElementById("displayrefinederrormsg").innerHTML=errMsg;
         document.getElementById("emailid").focus();
         return ;
     }
     if (document.getElementById("emailid").value.indexOf(' ') >= 0 )
     {
         errMsg ="<font color='#FFFFFF'>Spaces are not allowed for Email id</font>";
		document.getElementById("displayrefinederrormsg").innerHTML=errMsg;
         document.getElementById("emailid").focus();
         return ;
     }
     if (emailid.indexOf(at)==-1)
     {
         errMsg ="<font color='#FFFFFF'>Email-id should be in the form of abcxyz@gmail.com</font>";
		document.getElementById("displayrefinederrormsg").innerHTML=errMsg;
         document.getElementById("emailid").focus();
         return ;
     }
     if (emailid.indexOf(at) < 2)
     {
         errMsg ="<font color='#FFFFFF'>Please Enter Valid Email id</font>";
		 document.getElementById("displayrefinederrormsg").innerHTML=errMsg;
         document.getElementById("emailid").focus();
         return ;
     }
     if (emailid.indexOf(at)==-1 || emailid.indexOf(at)==0 || emailid.indexOf(at)==lstr)
     {
         errMsg ="<font color='#FFFFFF'>Email id should be in the form of abcxyz@gmail.com</font>";
		 document.getElementById("displayrefinederrormsg").innerHTML=errMsg;
         document.getElementById("emailid").focus();
         return ;
     }
     if (emailid.indexOf(dot)==-1 || emailid.indexOf(dot)==0 || emailid.indexOf(dot)==lstr)
     {
         errMsg ="<font color='#FFFFFF'>Email id should be in the form of abcxyz@gmail.com</font>";
		 document.getElementById("displayrefinederrormsg").innerHTML=errMsg;
         document.getElementById("emailid").focus();
         return ;
     }
     if (emailid.indexOf(at) >50)
     {
         errMsg ="<font color='#FFFFFF'>Email id should not exceed more than 50 characters</font>";
		 document.getElementById("displayrefinederrormsg").innerHTML=errMsg;
         document.getElementById("emailid").focus();
         return ;
     }
     if (emailid.indexOf(dot) == -1 || emailid.indexOf(dot) == 0 || emailid.indexOf(dot) == lstr)
     {
         errMsg ="<font color='#FFFFFF'>Email id should be in the form of abcxyz@gmail.com</font>";
		 document.getElementById("displayrefinederrormsg").innerHTML=errMsg;
         document.getElementById("emailid").focus();
         return ;
     }
     if (! document.getElementById("emailid").value.match(reg))
     {
         errMsg ="<font color='#FFFFFF'>Email id should be in the form of abcxyz@gmail.com</font>";
         document.getElementById("displayrefinederrormsg").innerHTML=errMsg;
         document.getElementById("emailid").focus();
         return ;
     }
     if (emailid.indexOf(at, (lat + 1)) != -1)
     {
         errMsg ="<font color='#FFFFFF'>Email id should be in the form of abcxyz@gmail.com</font>";
		 document.getElementById("displayrefinederrormsg").innerHTML=errMsg;
         document.getElementById("emailid").focus();
         return ;
     }
     if (emailid.substring(lat - 1, lat) == dot || emailid.substring(lat + 1, lat + 2) == dot || emailid.substring(ldot+1,ldot+2)==dot)
     {
         errMsg ="<font color='#FFFFFF'>Email id should be in the form of abcxyz@gmail.com</font>";
		 document.getElementById("displayrefinederrormsg").innerHTML=errMsg;
         document.getElementById("emailid").focus();
         return ;
     }
     if (emailid.substring(lat - 1, lat) == us || emailid.substring(lat + 1, lat + 2) == us || emailid.substring(lat+1,lat+2)==us)
     {
         errMsg ="<font color='#FFFFFF'>Email id should be in the form of abcxyz@gmail.com</font>";
		 document.getElementById("displayrefinederrormsg").innerHTML=errMsg;
         document.getElementById("emailid").focus();
         return ;
     }
     if (emailid.substring(lat - 1, lat) == hi || emailid.substring(lat + 1, lat + 2) == hi || emailid.substring(lhi+1,lhi+2)==hi)
     {
         errMsg ="<font color='#FFFFFF'>Email id should be in the form of abcxyz@gmail.com</font>";
		 document.getElementById("displayrefinederrormsg").innerHTML=errMsg;
         document.getElementById("emailid").focus();
         return ;
     }
     if (emailid.indexOf(dot, (lat + 2)) == -1)
     {
         errMsg ="<font color='#FFFFFF'>Email id should be in the form of abcxyz@gmail.com</font>";
		 document.getElementById("displayrefinederrormsg").innerHTML=errMsg;
         document.getElementById("emailid").focus();
         return ;
     }
     if (emailid.indexOf(" ") != -1)
     {
         errMsg ="<font color='#FFFFFF'>Email id should be in the form of abcxyz@gmail.com</font>";
		 document.getElementById("displayrefinederrormsg").innerHTML=errMsg;
         document.getElementById("emailid").focus();
         return ;
     }
     if (emailid.substring(lhi-1,lhi)==hi || emailid.substring(lhi+1,lhi+2)==hi)
     {
         errMsg ="<font color='#FFFFFF'>Email id should be in the form of abcxyz@gmail.com</font>";
		 document.getElementById("displayrefinederrormsg").innerHTML=errMsg;
         document.getElementById("emailid").focus();
         return ;
     }
     if ((emailid.substring(lhi-1,lhi)==hi || emailid.substring(lus+1,lus+2)==us) || (emailid.substring(lus-1,lus)==us ||   emailid.substring(lhi+1,lhi+2)==hi))
     {
         errMsg ="<font color='#FFFFFF'>Email id should be in the form of abcxyz@gmail.com</font>";
		 document.getElementById("displayrefinederrormsg").innerHTML=errMsg;
         document.getElementById("emailid").focus();
         return ;
     }
     if (emailid.substring(lhi - 1, lhi) == dot || emailid.substring(lhi + 1, lhi + 2) == dot || emailid.substring(lus+1,lus+2)==dot)
     {
         errMsg ="<font color='#FFFFFF'>Email id should be in the form of abcxyz@gmail.com</font>";
		 document.getElementById("displayrefinederrormsg").innerHTML=errMsg;
         document.getElementById("emailid").focus();
         return ;
     }
     if (emailid.substring(lus - 1, lus) == dot || emailid.substring(lus + 1, lus + 2) == dot || emailid.substring(ldot+1,ldot+2)==dot)
     {
         errMsg ="<font color='#FFFFFF'>Email id should be in the form of abcxyz@gmail.com</font>";
		 document.getElementById("displayrefinederrormsg").innerHTML=errMsg;
         document.getElementById("emailid").focus();
         return ;
     }
     if (document.getElementById("emailid").value.indexOf('com.in') >=0)
     {
         errMsg ="<font color='#FFFFFF'>Email id should be in the form of abcxyz@gmail.com</font>";
		 document.getElementById("displayrefinederrormsg").innerHTML=errMsg;
         document.getElementById("emailid").focus();
         return ;
     }
     if (emailid.substring(lhi - 1, lhi) == us || emailid.substring(lhi + 1, lhi + 2) == us || emailid.substring(lus+1,lus+2)==us)
     {
         errMsg ="<font color='#FFFFFF'>Email id should be in the form of abcxyz@gmail.com</font>";
	     document.getElementById("displayrefinederrormsg").innerHTML=errMsg;
         document.getElementById("emailid").focus();
         return ;
     }
     if (emailid.indexOf(hi, (lhi + 1)) != -1)
     {
         errMsg ="<font color='#FFFFFF'>Email id should be in the form of abcxyz@gmail.com</font>";
		 document.getElementById("displayrefinederrormsg").innerHTML=errMsg;
         document.getElementById("emailid").focus();
         return ;
     }
     if (! document.getElementById("emailid").value.match(re))
     {
         errMsg ="<font color='#FFFFFF'>Email id should be in the form of abcxyz@gmail.com</font>";
		 document.getElementById("displayrefinederrormsg").innerHTML=errMsg;
         document.getElementById("emailid").focus();
         return ;
     }
     for (var i = 0; i < document.getElementById("emailid").value.length; i++)
     {
         if (iChars1.indexOf(document.getElementById("emailid").value.charAt(i))!= -1)
         {
             errMsg ="<font color='#FFFFFF'>Invalid characters are not allowed in Email id field</font>";
		     document.getElementById("displayrefinederrormsg").innerHTML=errMsg;
             document.getElementById("emailid").focus();
             return ;
         }
     }
     if (! document.getElementById("emailid").value.match(regexp))
     {
         errMsg ="<font color='#FFFFFF'>Email id should be in the form of abcxyz@gmail.com</font>";
		 document.getElementById("displayrefinederrormsg").innerHTML=errMsg;
         document.getElementById("emailid").focus();
         return ;
     }
     if (document.getElementById("emailid").value.indexOf('gmail') >=0)
     {
         if (document.getElementById("emailid").value.indexOf('com')<0)
         {
             errMsg ="<font color='#FFFFFF'>Email id should be in the form of abcxyz@gmail.com</font>";
			 document.getElementById("displayrefinederrormsg").innerHTML=errMsg;
             document.getElementById("emailid").focus();
             return ;
         }
     }
     if (document.getElementById("emailid").value.indexOf('rediffmail') >=0)
     {
         if (document.getElementById("emailid").value.indexOf('com')<0)
         {
             errMsg ="<font color='#FFFFFF'>Email id should be in the form of abcxyz@gmail.com</font>";
			 document.getElementById("displayrefinederrormsg").innerHTML=errMsg;
             document.getElementById("emailid").focus();
             return ;
         }
     }
     if (document.getElementById("emailid").value.indexOf('hotmail') >=0)
     {
         if (document.getElementById("emailid").value.indexOf('com')<0)
         {
             errMsg ="<font color='#FFFFFF'>Email id should be in the form of abcxyz@gmail.com</font>";
			 document.getElementById("displayrefinederrormsg").innerHTML=errMsg;
             document.getElementById("emailid").focus();
             return ;
         }
     }     
     if (document.getElementById("emailid").value.indexOf('ymail') >=0)
     {
         if (document.getElementById("emailid").value.indexOf('com')<0)
         {
             errMsg ="<font color='#FFFFFF'>Email id should be in the form of abcxyz@gmail.com</font>";
			 document.getElementById("displayrefinederrormsg").innerHTML=errMsg;
             document.getElementById("emailid").focus();
             return ;
         }
     }
     if (emailid.indexOf("__") != -1)
     {
         errMsg ="<font color='#FFFFFF'>Email id should be in the form of abcxyz@gmail.com</font>";
		 document.getElementById("displayrefinederrormsg").innerHTML=errMsg;
         document.getElementById("emailid").focus();
         return ;
     }
     if (emailid.indexOf(at,(lat+1))!=-1)
     {
         errMsg ="<font color='#FFFFFF'>Email id should be in the form of abcxyz@gmail.com</font>";
		 document.getElementById("displayrefinederrormsg").innerHTML=errMsg;
         document.getElementById("emailid").focus();
         return ;
     }
     if (emailid.substring(lat-1,lat)==dot || emailid.substring(lat+1,lat+2)==dot)
        {
         errMsg ="<font color='#FFFFFF'>Email id should be in the form of abcxyz@gmail.com</font>";
		 document.getElementById("displayrefinederrormsg").innerHTML=errMsg;
         document.getElementById("emailid").focus();
         return ;
     }
     if (emailid.indexOf(dot,(lat+2))==-1)
     {
         errMsg ="<font color='#FFFFFF'>Email id should be in the form of abcxyz@gmail.com</font>";
         openPopUp(errMsg);
         return;
         return false
     }
     if (emailid.indexOf(" ")!=-1)
     {
         errMsg ="<font color='#FFFFFF'>Email id should be in the form of abcxyz@gmail.com</font>";
		 document.getElementById("displayrefinederrormsg").innerHTML=errMsg;
         document.getElementById("emailid").focus();
         return ;
     }
     if (! document.getElementById("emailid").value.match(re))
     {
         errMsg ="<font color='#FFFFFF'>Email id should be in the form of abcxyz@gmail.com</font>";
		 document.getElementById("displayrefinederrormsg").innerHTML=errMsg;
         document.getElementById("emailid").focus();
         return ;
     }
     if (document.getElementById("emailid").value.indexOf("-")==0 )
     {
         errMsg ="<font color='#FFFFFF'>Email id should be in the form of abcxyz@gmail.com</font>";
		 document.getElementById("displayrefinederrormsg").innerHTML=errMsg;
         document.getElementById("emailid").focus();
         return ;
     }
     if (document.getElementById("emailid").value.indexOf("_")==0 )
     {
         errMsg ="<font color='#FFFFFF'>Email id should be in the form of abcxyz@gmail.com</font>";
		 document.getElementById("displayrefinederrormsg").innerHTML=errMsg;
         document.getElementById("emailid").focus();
         return ;
     }
     if (document.getElementById("emailid").value.indexOf("0123456789")==3 )
     {
         errMsg ="<font color='#FFFFFF'>Email id should be in the form of abcxyz@gmail.com</font>";
		document.getElementById("displayrefinederrormsg").innerHTML=errMsg;
         document.getElementById("emailid").focus();
         return ;
     }
	 if(addrs1 == "")
     {
		errMsg ="<font color='#FFFFFF'>Please enter Address 1...!</font>";
		document.getElementById("displayrefinederrormsg").innerHTML=errMsg;
		document.getElementById("addrs1").focus();
		return ;
     }
	 if (document.getElementById("addrs1").value.indexOf(' ') == 0 ) 
	 {
		 errMsg ="<font color='#FFFFFF'>Address 1 should not start with space</font>";
		document.getElementById("displayrefinederrormsg").innerHTML=errMsg;
		document.getElementById("addrs1").focus();
		return ;
	  }
	  var checkOK = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ#&()+=-[]',./\|_\":?0123456789 ";
		var checkStr = addrs1;
		var allValid = true;
		var decPoints = 0;
		var allNum = "";
		for (i = 0;  i < checkStr.length;  i++)
		{
			ch = checkStr.charAt(i);
			for (j = 0;  j < checkOK.length;  j++)
			if (ch == checkOK.charAt(j))
			break;
			if (j == checkOK.length)
			{
				allValid = false;
				break;
			}
		}
		if (!allValid)
		{
		errMsg ="<font color='#FFFFFF'>Address 1 have some special character. Please remove them and try again...!</font>";
		document.getElementById("displayrefinederrormsg").innerHTML=errMsg;
		document.getElementById("addrs1").focus();
		return ;
	  }
	  if(isDigits(addrs1)==true)
		{
			errMsg ="<font color='#FFFFFF'>Only Numerics are not allowed in the \'Address 1\' field.</font>";
			document.getElementById("displayrefinederrormsg").innerHTML=errMsg;
			document.getElementById("addrs1").focus();
			return ;
         }
	 if(addrs2 == "")
     {
		
     }
	 else
	{
	 if (document.getElementById("addrs2").value.indexOf(' ') == 0 ) 
	 {
		 errMsg ="<font color='#FFFFFF'>Address 2 should not start with space</font>";
		document.getElementById("displayrefinederrormsg").innerHTML=errMsg;
		document.getElementById("addrs2").focus();
		return ;
	  }
	  var checkOK = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ#&()+=-[]',./\|_\":?0123456789 ";
		var checkStr = addrs2;
		var allValid = true;
		var decPoints = 0;
		var allNum = "";
		for (i = 0;  i < checkStr.length;  i++)
		{
			ch = checkStr.charAt(i);
			for (j = 0;  j < checkOK.length;  j++)
			if (ch == checkOK.charAt(j))
			break;
			if (j == checkOK.length)
			{
				allValid = false;
				break;
			}
		}
		if (!allValid)
		{
		errMsg ="<font color='#FFFFFF'>Address 2 have some special character. Please remove them and try again...!</font>";
		document.getElementById("displayrefinederrormsg").innerHTML=errMsg;
		document.getElementById("addrs2").focus();
		return ;
	  }
	}
	if(isDigits(addrs2)==true)
	{
		errMsg ="<font color='#FFFFFF'>Only Numerics are not allowed in the \'Address 2\' field.</font>";
		document.getElementById("displayrefinederrormsg").innerHTML=errMsg;
		document.getElementById("addrs2").focus();
		return ;
	 }
	// alert(userzipcode);
	 //alert(userarea);
if(userzipcode == "" && userarea == "default")
     {
		errMsg ="<font color='#9B5BDD'>Please enter Pincode or Select Area to process the order...!</font>";
		document.getElementById("displayrefinederrormsg").innerHTML=errMsg;
		document.batterydet.userarea.focus();
		return ;
     }
	  if(userzipcode != "" && userarea != "default")
     {
		errMsg ="<font color='#ffffff'>Please select either Area or enter Pincode...!</font>";
		document.getElementById("displayrefinederrormsg").innerHTML=errMsg;
		document.batterydet.userarea.focus();
		return ;
     }
	 if(!userzipcode == "")
     {
		var pincoderegex=/^[0-9]{4,6}$/;
		if(!userzipcode.match(pincoderegex))
		{
		errMsg ="<font color='#ffffff'>Please enter valid Pincode..!.</font>";
		document.getElementById("displayrefinederrormsg").innerHTML=errMsg;
		document.batterydet.userzipcode.focus();
		return ;
		}
		city="";
	 }
	 
		addrs1=escape(addrs1);
		addrs2=escape(addrs2);
		varButton.disabled=true; 
		varButton.value='Please Wait...'; 
		
		var xmlhttp= "";
		var resp= "";
		var url="./servlet/BatteryDetails?hidWhatToDo=insertconsumerdetails&username="+strusername+"&emailid="+emailid+"&addrs1="+addrs1+"&addrs2="+addrs2+"&userarea="+userarea+"&usercity="+usercity+"&pincity="+pincity+"&userzipcode="+userzipcode+"&mobilenumber="+strUsermobileno+"&batterymodel="+batterymodel+"&price="+price+"&withbatprice="+withbatprice+"&verifycode="+verificationcode+"&batterybrand="+batterybrand+"&city="+city+"&strarea="+strarea+"&strstate="+strstate+"&strpincode="+strpincode+"&batterytype="+batterytype+"&vehiclemake="+vehiclemake+"&vehiclemodel="+vehiclemodel+"&agentname="+agentname;

		document.getElementById("displayrefinederrormsg").innerHTML=""; 
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
			if (xmlhttp.readyState==4)
			{
				if(xmlhttp.status!=200)
				{
					return;
				}
				else
				{
					$('#divmobile').hide();
					resp = xmlhttp.responseText;	
					document.getElementById("divorderstatus").innerHTML="";
					document.getElementById("divorderstatus").style.display='block';
					document.getElementById("divorderstatus").innerHTML=resp;
					$('html,body').animate({scrollTop:0},'slow');return false;
					resp= "";					
				}
			}	
		}
		xmlhttp.open("GET",url,true);
		xmlhttp.send();		
}
function checkverification(strUsermobileno,batterybrand,batterymodel,price,withbatprice)
{
	var verifycode=document.getElementById("verifycode").value;
	var city=document.getElementById("city").value;
	var area=document.getElementById("strarea").value; 
	var pincode=document.getElementById("strpincode").value;

	if(city == "" || city == 0)
	{
		var styless = "style='display:none;'";
	}
	else
	{
		var styles ="style='display:none;'";
	}
	
	if (verificationcode == verifycode)
	{

		$.ajax({
                    type: "GET",
                    url: "/bookbattery/servlet/BatteryHome?hidWhatToDo=getarea",
                    data: {city: document.getElementById("city").value },
                    success: function(data)
					{
						$('#img7').hide();
						if(data.indexOf("defaultss")>=0)
						{
                        $("#userarea").html(data)
						//document.getElementById("userarea").focus();
						}
						else
						{
						 $("#userarea").html(data)
						//document.getElementById("userarea").focus();
						}
                    }
                });
		errMsg ="<div class='col-md-4 col-md-offset-4'> <table  bgcolor='#FFFFFF'><table width='100%' align='center' bgcolor='#FFFFFF' valign='top'><tr height='10'><table width='100%' bgcolor='#FFFFFF' valign='top'></table><table  width='100%' bgcolor='#FFFFFF' valign='top'><tr><td width='100%' align='center' style='COLOR:#F96F2B; font-size: 25px;font-weight: 1000;text-align: center;'> BookBattery</td> <td width='100%' align='center' style='COLOR:#F96F2B; font-size: 24px;font-weight: 1000;text-align: left;'   href=\"javascript:closemobdiv(greyout(false));\" > <a style='color: #F96F2B;'  href=\"javascript:closemobdiv(greyout(false));\" > X </a> </td></tr></table><hr style='background: white; width: 90%;margin-top: 1px;margin-bottom: 1px;'></tr></table> <tr><td><table width='100%'   bgcolor='#FFFFFF'><tr><td width='100%' colspan='3'><table width='100%' ><tr><td class='insidecontent' align='center'><font style=' font-size: 15px;color: #ffffff;'>Please Enter Your Installation Location Details!</font></td></tr></table></td></tr><tr><td ><table width='100%' style='margin-left: 70px;' ><tr><td width='20%'></td><td width='80%' height='10'></td></tr><tr><td width='100%' align='left' style='font-size:16px;color:#FFFFFF;	text-decoration:none;padding:1px 1px;'><font color='#ff3333'>&nbsp;*&nbsp;</font>Enter&nbsp;your&nbsp;Name </td> </tr><tr> <td width='100%' align='left'>&nbsp;<input class='insidecontent' type='text' autocomplete='off' name='username' id='username' placeholder='Johan' style='width:70%;height:38px;border: 1px solid #CCC;font-size: 13px;font-family: Verdana;border-radius: 0px 0px 0px 0px;' maxlength='100' ></td></tr><tr><td width='20%'></td><td width='80%' height='5'></td></tr><tr><td width='100%' align='left' style='font-size:16px;color:#FFFFFF;	text-decoration:none;padding:1px 1px;'><font color='#ff3333'>&nbsp;*&nbsp;</font>Enter&nbsp;your&nbsp;Email </td> </tr><tr> <td width='100%' align='left'>&nbsp;<input class='insidecontent' type='text' autocomplete='off' name='emailid' id='emailid' placeholder='johan@gmail.com' style='width:70%;height:38px;border: 1px solid #CCC;font-size: 13px;font-family: Verdana;border-radius: 0px 0px 0px 0px;' maxlength='50' ></td></tr><tr><td width='20%'></td><td width='80%' height='5'></td></tr><tr><td width='100%' align='left' style='font-size:16px;color:#FFFFFF;	text-decoration:none;padding:1px 1px;'><font color='#ff3333'>&nbsp;*&nbsp;</font>Enter&nbsp;your&nbsp;Address&nbsp;1</td>  </tr><tr> <td width='100%' align='left'>&nbsp;<input class='insidecontent' type='text' autocomplete='off' name='addrs1' id='addrs1' placeholder='2-124,Road No:4,HSR Layout,Bangalore' style='width:70%;height:38px;border: 1px solid #CCC;font-size: 13px;font-family: Verdana;border-radius: 0px 0px 0px 0px;' maxlength='225' ></td></tr><tr><td width='20%'></td><td width='80%' height='5'></td></tr><tr><td width='100%' align='left' style='font-size:16px;color:#FFFFFF;	text-decoration:none;padding:1px 1px;'>&nbsp;Enter&nbsp;your&nbsp;Address&nbsp;2</td>  </tr><tr> <td width='100%' align='left'>&nbsp;<input class='insidecontent' type='text' autocomplete='off' name='addrs2' id='addrs2' placeholder='Near Forum Mall' style='width:70%;height:38px;border: 1px solid #CCC;font-size: 13px;font-family: Verdana;border-radius: 0px 0px 0px 0px;' maxlength='225' ></td></tr><tr "+styless+"><td width='20%'></td><td width='80%' height='5'></td></tr><tr><td width='20%'></td><td width='80%' height='5'></td></tr><tr "+styless+"><td width='100%' align='left' style='font-size:16px;color:#FFFFFF;	text-decoration:none;padding:1px 1px;'>&nbsp;City</td> </tr><tr "+styless+"> <td width='100%' align='left'>&nbsp;<input class='insidecontent' type='text' autocomplete='off' name='usercity' id='usercity' value='"+city+"' placeholder='Bangalore' style='width:70%;height:38px;border: 1px solid #CCC;font-size: 13px;font-family: Verdana;border-radius: 0px 0px 0px 0px;' maxlength='50' readonly /></td></tr><tr ><td width='100%' align='left' style='font-size:16px;color:#FFFFFF;text-decoration:none;padding:1px 1px;'><font color='#ff3333'>&nbsp;*&nbsp;</font>&nbsp;Area </td> </tr><tr > <td width='80%' align='left'><span style='width:70%;' class='custom-dropdown big'><select style='width:100%; margin-left: -9px;' name='userarea' id='userarea' ><option value='0'>Select Area</option></select></span></td></tr><tr><td width='20%'></td><td width='80%' height='5'></td></tr><tr ><td width='100%' align='left' style='font-size:16px;color:#FFFFFF;	text-decoration:none;padding:1px 1px;'>&nbsp;ZipCode</td> </tr><tr > <td width='100%' align='left'>&nbsp;<input class='insidecontent' type='text' autocomplete='off' name='userzipcode' id='userzipcode' value='' placeholder='5100037' style='width:70%;height:38px;border: 1px solid #CCC;font-size: 13px;font-family: Verdana;border-radius: 0px 0px 0px 0px;' maxlength='6'  onkeydown=\"if ((event.which && event.which == 13) || (event.keyCode && event.keyCode == 13)){javascript:getconsumerdetails('"+batterybrand+"','"+batterymodel+"','"+price+"','"+withbatprice+"','"+strUsermobileno+"','"+verificationcode+"',Submitrret);return false;}  else return true;\"/></td></tr><tr><td  style='padding-left: 15%;' ><input style='font-family: Helvetica Neue,Helvetica,Arial,sans-serif; background-color:#D6511C; color: white; font-size: 17px;padding: 5px 15px;font-weight: lighter; margin-left: 5px;margin-top: 5px;' type='button' class='btn' name='Submitrret' value='Submit' disable='false' onclick=\"javascript:getconsumerdetails('"+batterybrand+"','"+batterymodel+"','"+price+"','"+withbatprice+"','"+strUsermobileno+"','"+verificationcode+"',Submitrret);\">  <input style='font-family: Helvetica Neue,Helvetica,Arial,sans-serif; background-color:#D6511C; color: white; font-size: 17px;padding: 5px 15px;font-weight: lighter; margin-left: 5px;margin-top: 5px;' type='button' class='btn'  value='Close' align='left' onclick=\"javascript:closemobdiv(greyout(false));\" ></td></tr>   </table></td></tr><tr height='26'><td colspan='3' align='center' class='subheading' id='displayrefinederrormsg'></td></tr><tr><td height='15'></td></tr></table> </div>";





	document.getElementById("divmobile").innerHTML="";
	document.getElementById("divmobile").style.display='block';
	document.getElementById("divmobile").style.top="120px";
	document.getElementById("divmobile").innerHTML=errMsg
	greyout(true);
	document.getElementById("username").focus();
	}
	else
	{
		if (verifycode=="")
		{
			errMsg ="<font color='#FFFFFF'>Please enter verification code</font>";
			document.getElementById("codeerrormsg").innerHTML=errMsg;
			document.getElementById("verifycode").focus();
			return ;
		}
		else
		{
			errMsg ="<font color='#FFFFFF'>Verification Code Does Not Match Please Try Again</font>";
			document.getElementById("codeerrormsg").innerHTML=errMsg;
			document.getElementById("verifycode").focus();
			return ;
		}
    }

}
function isDigits(argvalue) 
{
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
    for (var n = 0; n < argvalue.length; n++) 
	{
        if (validChars.indexOf(argvalue.substring(n, n+1)) == -1) return false;
    }
  return true;
}
function closemobdiv()
{
	$('#divmobile').hide();
	greyout(false);
}
function closemobdivindex()
{
	$('#divmobile').hide();
	greyout(false);
	location.href="./index.jsp";
}
function closemobdivindex1()
{
	$('#divorderstatus').hide();
	greyout(false);
	location.href="./index.jsp";
}
/* added function by jhansi to navigate page to bookbattery posts page after ordering the battery */
function navigatetobatterywale(email,mobilenumber,pwd,username,city,state,producttype,vehiclename,vehiclemodel,productbrand,productmodel,withoutoldproductprice,witholdproductprice,quantity,retailername,retailermobileno)
{
	var serverURL = document.getElementById("serverURL").value;
	//alert(serverURL);
	$('#divorderstatus').hide();
	greyout(false);
	location.href="http://"+serverURL+"/bookbattery/servlet/UserLoginServlet?hidWhatToDo=loginwithanotherserver&email="+email+"&mobilenumber="+mobilenumber+"&password="+pwd+"&username="+username+"&city="+city+"&state="+state+"&deviceflag=web&producttype="+producttype+"&vehiclename="+vehiclename+"&vehiclemodel="+vehiclemodel+"&productbrand="+productbrand+"&productmodel="+productmodel+"&withoutoldproductprice="+withoutoldproductprice+"&witholdproductprice="+witholdproductprice+"&quantity="+quantity+"&retailername="+retailername+"&retailermobileno="+retailermobileno;
	//location.href="http://"+serverURL+"/surfmug/jsp/consumers/home/index.jsp";
}