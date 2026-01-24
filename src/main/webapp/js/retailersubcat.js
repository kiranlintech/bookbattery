/*
	Author(s)		: Ajay K
	Date			: March 21, 2013
	Copyright Notice: NGIT Pvt.Ltd. Confidential.
	Description		: To insert Category, Subcategory and Brand Details of a Retailer.
*/

/*This function is used to fetch categories*/
function getCategories()
{
	var key=document.addretailer.key.value;
	if (key=="modifyretailer")
	{
		fetchSubcategorybranddetails();
	}
	var xmlHttp;
	varSubCatFlag = false;
	try
	{ 
		 xmlHttp = new XMLHttpRequest();
	}
	catch (e)
	{ 
		try
		{
			xmlHttp = new ActiveXObject("Msxml2.XMLHTTP");
		}
		catch (e)
		{
			try
			{
				xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
			}
			catch (e)
			{
				 alert("Your	browser	does not support AJAX!");
				 return false;
			}
		}
	}
	xmlHttp.onreadystatechange = function()
	{
		if (xmlHttp.readyState == 4)
		{
			varSubCats = xmlHttp.responseText;
			if (varSubCats.indexOf("Evaluating Session") >= 0)
			{
				alert("Session Timed out, Please login again");
				return;
			}
			if (xmlHttp.statusText != "OK")
			{
				alert("Please Wait fetching Categories");
				return;
			}
			if (varSubCats.indexOf("Session expires please login") >= 0)
			{
				errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Session Expired Please login again to add/update retailer.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='Close_sessionpopup();' class='button4'><br></td></tr>";
				greyout(true);
				document.getElementById("sessionpopup").style.display='block';
				document.getElementById("sessionpopupmessage").innerHTML=errMsg;
				return ;
			}
			if (varSubCats.indexOf("SubCategories are not available")>= 0)
			{
				for (i = document.addretailer.subcategory.options.length- 1; i >= 1; i--)
				{
					document.addretailer.subcategory.remove(i);
				}
				document.addretailer.category.options[0].selected=true;
			}
			else
			{
				var subCats = "";
				if (varSubCats != "")
				{
					for (i= document.addretailer.category.options.length- 1; i >= 1; i--)
					{
						document.addretailer.category.remove(i);
					}
					varSubCatFlag = true;
					varSubCatArray = varSubCats.split("|");
					var len = varSubCatArray.length;
					for (var i = 0; i < len; i++)
					{
						varOptArray = varSubCatArray[i].split(":");
						var Catoptn = document.createElement("OPTION");
						Catoptn.value = +varOptArray[0]+ ","+varOptArray[1];
						Catoptn.text = varOptArray[1];
						document.addretailer.category.options.add(Catoptn); 
					}			
				}
			}
		}
	}
	xmlHttp.open("GET","/bookbattery/servlet/FetchCategories?hidWhatToDo=fetchCategories&requestno="+(Math.random()*100), true);
	xmlHttp.send(null);
}
/*This function is used fetch subcategories when usee selects category*/
function getSubCategories()
{
	var Category = document.addretailer.category.value;
	var Categorysplit = Category.split(",");
	var categoryid=Categorysplit[0];
	var Categoryname=Categorysplit[1];
	var xmlHttp;
	varSubCatFlag = false;
	try
	{    
		 xmlHttp = new XMLHttpRequest();
	}
	catch (e)
	{ 
		try
		{
			xmlHttp = new ActiveXObject("Msxml2.XMLHTTP");
		}
		catch (e)
		{
			try
			{
				xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
			}catch (e)
			{
				alert("Your	browser	does not support AJAX!");
				return false;
			}
		}
	}
	xmlHttp.onreadystatechange = function()
	{
		if (xmlHttp.readyState == 4)
		{
			varSubCats = xmlHttp.responseText;
			if (varSubCats.indexOf("Session expires please login") >= 0)
			{
				errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Session Expired Please login again to add/update retailer.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='Close_sessionpopup();' class='button4'><br></td></tr>";
				greyout(true);
				document.getElementById("sessionpopup").style.display='block';
				document.getElementById("sessionpopupmessage").innerHTML=errMsg;
				return ;
			}
			if (varSubCats.indexOf("SubCategories are not available")>= 0)
			{
				for (k = document.addretailer.brand.options.length- 1; k >= 1; k--)
				{
					document.addretailer.brand.remove(k);
				}
				for (i = document.addretailer.subcategory.options.length- 1; i >= 1; i--)
				{
					document.addretailer.subcategory.remove(i);
				}
				document.addretailer.subcategory.options[0].selected=true;				
			}
			else
			{
				var subCats = "";
				if (varSubCats != "")
				{
					for (i= document.addretailer.subcategory.options.length- 1; i >= 1; i--)
					{
						document.addretailer.subcategory.remove(i);
					}
					for (i= document.addretailer.brand.options.length- 1; i >= 1; i--)
					{
						document.addretailer.brand.remove(i);
					}
					varSubCatFlag = true;
					varSubCatArray = varSubCats.split("|");
					var len = varSubCatArray.length;
					for (var i = 0; i < len; i++)
					{
						varOptArray = varSubCatArray[i].split(":");
						var Subcatoptn = document.createElement("OPTION");
						Subcatoptn.value = +varOptArray[0]+ ","+varOptArray[1];
						Subcatoptn.text = varOptArray[1];
						document.addretailer.subcategory.options.add(Subcatoptn);
					}
				}
			}
		}
    }
	xmlHttp.open("GET","/bookbattery/servlet/FetchCategories?hidWhatToDo=fetchSubcategories&catid="+categoryid+"&requestno="+(Math.random()*100), true);
	xmlHttp.send(null);
}
/*This function is to fetch brands when user selects category and subcategory.*/
function getBrands()
{
	var Category = document.addretailer.category.value;
	var Categorysplit = Category.split(",");
	var categoryid=Categorysplit[0];
	var Categoryname=Categorysplit[1];
	var Subcategory = document.addretailer.subcategory.value;
	var Subcategorysplit = Subcategory.split(",");
	var subcatcname=Subcategorysplit[1];
	var subcategoryid = new Array();
	var count = 0;
  	for(var x=0; x<document.addretailer.subcategory.length; x++)
	{
		if(document.addretailer.subcategory[x].selected==true)
		{
			var subcategoryid1= document.addretailer.subcategory[x].value.split(",");
			subcategoryid[count]=subcategoryid1[0];
			count++;
		}	
	}

	var xmlHttp;
	varSubCatFlag = false;
	try
	{ 
		xmlHttp = new XMLHttpRequest();
	}
	catch (e)
	{ 
		try
		{
			xmlHttp = new ActiveXObject("Msxml2.XMLHTTP");
		}
		catch (e)
		{
			try
			{
				xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
			}
			catch (e)
			{
				alert("Your	browser	does not support AJAX!");
				return false;
			}
		}
	}
	xmlHttp.onreadystatechange = function()
	{
		if (xmlHttp.readyState == 4)
		{
			varSubCats = xmlHttp.responseText;
			if (varSubCats.indexOf("Evaluating Session") >= 0)
			{
				alert("Session Timed out, Please login again");
				return;
			}
			if (xmlHttp.statusText != "OK")
			{
				alert("Please wait while fetching brands");
				return;
			}
			if (varSubCats.indexOf("Session expires please login") >= 0)
			{
				errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Session Expired Please login again to add/update retailer.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='Close_sessionpopup();' class='button4'><br></td></tr>";
				greyout(true);
				document.getElementById("sessionpopup").style.display='block';
				document.getElementById("sessionpopupmessage").innerHTML=errMsg;
				return ;
			}
			if (varSubCats.indexOf("Brands are not available")>= 0)
			{
				for (m = document.addretailer.brand.options.length- 1; m >= 1; m--)
				{
					document.addretailer.brand.remove(m);
				}
				document.addretailer.brand.options[0].selected=true;
			}
			else
			{
				var subCats = "";
				if (varSubCats != "")
				{
					for (i= document.addretailer.brand.options.length- 1; i >= 1; i--)
					{
						document.addretailer.brand.remove(i);
					}
					varSubCatFlag = true;
					varSubCatArray = varSubCats.split("|");
					var len = varSubCatArray.length;
					for (var i = 0; i < len; i++)
					{
						varOptArray = varSubCatArray[i].split(":");
						var Brandoptn = document.createElement("OPTION");
						Brandoptn.value = varOptArray[0];
						Brandoptn.text = varOptArray[1];
						document.addretailer.brand.options.add(Brandoptn);
					}
				}							
			}
		}
	}
	xmlHttp.open("GET", "/bookbattery/servlet/FetchCategories?hidWhatToDo=fetchBrands&subcatid="+subcategoryid+"&catid="+categoryid+"&requestno="+(Math.random()*100), true);
	xmlHttp.send(null);
}
/*This function used when click on ok button in session message popup page redirects to admin login page*/
function Close_sessionpopup()
{
	window.location = "/admin/jsp/admin/batterystore/batteryadminlogin.jsp";
}
/*This function is used to display the category, subcategory and brand details of a retailer after submitting*/
function insertSubcategorybranddetails()
{
	var category = document.addretailer.category.value;
	var CategorySplit = category.split(",");
	var categoryid=CategorySplit[0];
	var categoryname=CategorySplit[1];
	var subcategoryid = new Array();
	var subcategoryname = new Array();
	var count11 = 0;
  	for(var x=0; x<document.addretailer.subcategory.length; x++)
	{
		if(document.addretailer.subcategory[x].selected==true)
		{
			var SubcategorySplit= document.addretailer.subcategory[x].value.split(",");
			subcategoryid[count11]=SubcategorySplit[0];
			subcategoryname[count11]=SubcategorySplit[1];
			count11++;
		}	
	}

	var retailerid = document.addretailer.retailerid.value;
	var retailername = document.addretailer.retailername.value;
	var retailerloginname = document.addretailer.strretailerloginname.value;
	var city = document.addretailer.city.value;
	var strarea = document.addretailer.strarea.value;
	var emailid=document.addretailer.stremail.value;
	var eretailer_flag=document.addretailer.eretailer_flag.value;
	var state = document.addretailer.strstate.value;
	var temp=document.addretailer.temp.value;
	var brandselectedArray = new Array();
	var brandnameArray = new Array();
	if ( document.addretailer.category.selectedIndex <= 0)
	{
		errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='top1'><td align='center'>Please select one Category.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='closepopupcategory();' class='button4'><br></td></tr>";
		document.getElementById("popup").style.display='block';
		greyout(true);
		document.getElementById("popupmessage").innerHTML=errMsg
		return ;
	}
	if ( document.addretailer.subcategory.selectedIndex <= 0)
	{
		errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='top1'><td align='center'>Please select one Sub Category.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='closepopupsubcategory();' class='button4'><br></td></tr>";
		document.getElementById("popup").style.display='block';
		greyout(true);
		document.getElementById("popupmessage").innerHTML=errMsg
		return ;
	}
	count = 0;
	for(x=0; x<document.addretailer.brand.length; x++)
	{
	if(document.addretailer.brand[x].selected==true)
	{
		brandselectedArray[count]= document.addretailer.brand[x].value;
		brandnameArray[count]=document.addretailer.brand[x].text;
		count++;
	}	
	}
	for(k=0; x<document.addretailer.brand.length; k++)
	{
	if(document.addretailer.brand[k].selected==true)
	{
		brandnameArray[count]=document.addretailer.brand[k].text;
	}	
	}

	if(count>1 && count11>1)
	{
		errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='top1'><td align='center'>Please select Only Multiple Subcategories (or) Multiple Brands but not Both.......!</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='closepopupsubcategory();' class='button4'><br></td></tr>";
		document.getElementById("popup").style.display='block';
		greyout(true);
		document.getElementById("popupmessage").innerHTML=errMsg
		return ;
	}
	var xmlhttp;
	retailername=repl(retailername,"&","%26",1);
	retailername=repl(retailername,"/","%2F",1);
	retailername=repl(retailername,":","%3A",1);
	retailername=repl(retailername,"_","%5F",1);
	retailername=repl(retailername,"-","%2D",1);
	var url="/bookbattery/servlet/RetailerRegistration?hidWhatToDo=insertsubcategorybranddetails&categoryname="+categoryname+"&categoryid="+categoryid+"&subcategoryid="+subcategoryid+"&subcategoryname="+subcategoryname+"&retailerid="+retailerid+"&brand="+brandselectedArray+"&brandname="+brandnameArray+"&city="+city+"&state="+state+"&eretailer_flag="+eretailer_flag+"&retailername="+retailername+"&retailerloginname="+retailerloginname+"&area="+strarea+"&temp="+temp+"&emailid="+emailid+"&requestno="+(Math.random()*100);
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
		if(xmlhttp.readyState!=4)
		{
			document.getElementById("SubcatDetails").innerHTML="<span class='style1'><center><br><br><b><img src='/bookbattery/images/pleasewait.gif' align='center'><br>Please wait! Fetching details of retailer...</b></center><br><br></span>";
		}
		if (xmlhttp.readyState==4 && xmlhttp.status==200)
		{
			res=xmlhttp.responseText;
			if(res.indexOf("Session Expired Please login to add/update the retailer again")>=0)
			{
				errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Session Expired Please login to add/update the retailer again</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='Close_sessionpopup();' class='button4'><br></td></tr>";
				greyout(true);
				document.getElementById("sessionpopup").style.display='block';
				document.getElementById("sessionpopupmessage").innerHTML=errMsg;
				return ;
			}
			if(res.indexOf("Failed to add/update a retailer. Please add/update the retailer again")>=0)
			{
				document.getElementById("SubcatDetails").innerHTML="";
				document.getElementById("SubcatDetails").innerHTML=xmlhttp.responseText;
			}
			else
			{
				document.getElementById("SubcatDetails").innerHTML="";
				document.getElementById("SubcatDetails").innerHTML=xmlhttp.responseText;
				addmoredetails()
			}
		}
	}
	xmlhttp.open("GET",url,true);
	xmlhttp.send();			
}
/*This function used when click on ok button in session message popup page redirects to admin login page*/
function Close_sessionpopup()
{
	window.location = "/bookbattery/jsp/admin/batterystore/batteryadminlogin.jsp";
}
function closePopupreport()
{
	$('#popupreply1').hide();
	greyout(false);
}
function closePopupreport1()
{
	$('#popupreply1').show();
	$('#popup').hide();
	greyout(false);
	document.addretailer.message.focus();
}
function repl(o,t,r,c){if(c==1){cs="g"}else{cs="gi"}var mp=new RegExp(t,cs);ns=o.replace(mp,r);return ns}
/*This function is used to fetch the retailer subcategory details*/
function fetchSubcategorybranddetails()
{
	var retailerid = document.addretailer.retailerid.value;
	var state=document.addretailer.strstate.value;
	var eretailerflag=document.addretailer.eretailer_flag.value;						
	var retailername = document.addretailer.retailername.value;
		var stateold = document.addretailer.stateold.value
	retailername=repl(retailername,"&","%26",1);
	retailername=repl(retailername,"/","%2F",1);
	retailername=repl(retailername,":","%3A",1);
	retailername=repl(retailername,"_","%5F",1);
	retailername=repl(retailername,"-","%2D",1);
	var xmlhttp;
	var url="/bookbattery/servlet/RetailerRegistration?hidWhatToDo=fetchsubcatbranddetailsonload&retailerid="+retailerid+"&retailername="+retailername+"&state="+state+"&stateold="+stateold+"&eretailerflag="+eretailerflag+"&requestno="+(Math.random()*100);
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
		if(xmlhttp.readyState!=4)
		{
			document.getElementById("SubcatDetails").innerHTML="<span class='style1'><center><br><br><b><img src='/bookbattery/images/pleasewait.gif' align='center'><br>Please wait! Fetching details of retailer...</b></center><br><br></span>";
		}
		if (xmlhttp.readyState==4 && xmlhttp.status==200)
		{
			res=xmlhttp.responseText;
			if(res.indexOf("Failed to add/update a retailer. Please add/update the retailer again")>=0)
			{
				document.getElementById("SubcatDetails").innerHTML="";
				document.getElementById("SubcatDetails").innerHTML=xmlhttp.responseText;
			}
			else
			{
				document.getElementById("SubcatDetails").innerHTML="";
				document.getElementById("SubcatDetails").innerHTML=xmlhttp.responseText;
			}
		}
	}
	xmlhttp.open("GET",url,true);
	xmlhttp.send();			
}
/*This function is used to delete category,subcategory and brand details for a retailer*/
function deleteSubcategorybranddetails()
{
	var rettotalcount=document.getElementById("rettotalcount").value;
	var retailerid=0;
	for (i = 0; i<document.addretailer.check.length; i++)
	{
		if(document.addretailer.check[i].checked)
		{
			retailerid++;
		}
	}
	var val = [];
        $(':checkbox:checked').each(function(i){
          val[i] = $(this).val();
        });
	if (retailerid ==0)
	{
		errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>One Subcategory is Mandatory.</br>If retailer supports multiple Subcategory select a checkbox to delete</td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='closePopupDelete();' class='button4'><br></td></tr>";
		document.getElementById("popup").style.display='block';
		greyout(true);
		document.getElementById("popupmessage").innerHTML=errMsg
		return;
	}
	if (retailerid == rettotalcount)
	{
		errMsg ="<br><table align='center' border='0' cellpadding='0' chanducellspacing='0'><tr class='pages'><td align='center'>One Subcategory is Mandatory.</br>If retailer supports multiple Subcategory select a checkbox to delete</td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='closePopupDelete();' class='button4'><br></td></tr>";
		document.getElementById("popup").style.display='block';
		greyout(true);
		document.getElementById("popupmessage").innerHTML=errMsg
		return;
	}
	var categoryArray = document.getElementsByName("check");
	for(x=0; x<categoryArray.length; x++)
	{
		if(document.addretailer.check[x].checked==true)
		{
			$('#button1').hide();
			var category = categoryArray[x].value;
			var mySplitResult = category.split(",");
			var retailerid=mySplitResult[0];
			var categoryid=mySplitResult[1];
			var subcategoryid=mySplitResult[2];
			var vendorid=mySplitResult[3];
		}
	}	
	var agree=confirm("Are you sure you wish to Delete?");
	if (agree)
	{
		errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Enter Password&nbsp;</td><td><input type='password' name='message' id='message' size='15' onkeydown=\"if ((event.which && event.which == 13) || (event.keyCode && event.keyCode == 13)){javascript:closepopupadd(greyout(false));   accountdeletecode(); return false;} else return true;\" ></td></tr><tr height='40'><td></td><td align='center'><input type='button' name='emailok' id='emailok' value='Ok' onclick=\" closepopupadd(greyout(false));  accountdeletecode(); \" class='button4'>&nbsp;&nbsp;<input type='button' name='emailok' id='emailok' value='Cancel' onclick='closePopupreport();' class='button4'></td></tr></table>";
		greyout(true);
		document.getElementById("popupreply1").style.display='block';
		document.getElementById("popupmessagereply1").innerHTML=errMsg
		document.addretailer.message.focus();
		return;
	}	
$('#button1').show();
}
function closepopupadd()
{
	$('#popupreply1').hide();
	greyout(false);
}
function closePopupreport()
{
$('#button1').show();
	$('#popupreply1').hide();
	greyout(false);

}
function closePopupDelete1()
{
$('#popup').hide();
$('#popupreply1').show();
document.addretailer.message.focus();
}
function accountdeletecode()
{
	var page = document.getElementById("page").value;
	var categoryArray = document.getElementsByName("check");
	var eretailer_flag=document.addretailer.eretailer_flag.value;
	var state = document.addretailer.strstate.value;
	var val = [];
        $(':checkbox:checked').each(function(i){
          val[i] = $(this).val();
        });
	var passwordretailermsg=document.getElementById('passwordretailerMsg').value;
	var entervalue=document.addretailer.message.value;

	if(entervalue=="" || entervalue==null)
	{
	errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Please enter valid password</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='closePopupDelete1();' class='button4'><br></td></tr>";
			document.getElementById("popup").style.display='block';
			greyout(true);
			document.getElementById("popupmessage").innerHTML=errMsg
			document.getElementById("emailok").focus();
			return;
	}
	entervalue=escape(entervalue);
	$.get("/bookbattery/servlet/AdminHomeServlet?hidWhatToDo=adminPasswords&password="+entervalue+"&featurename=deleteretailer&requestno="+(Math.random()*100),function(response, status, xhr)
	{
	if (status == "success" )
	{
	if (response.indexOf("correct")>=0)
	{
		
		var retailername = document.addretailer.retailername.value;
		retailername=repl(retailername,"&","%26",1);
		retailername=repl(retailername,"/","%2F",1);
		retailername=repl(retailername,":","%3A",1);
		retailername=repl(retailername,"_","%5F",1);
		retailername=repl(retailername,"-","%2D",1);
		var xmlhttp;
		var url="/bookbattery/servlet/RetailerRegistration?hidWhatToDo=deletesubcategorybranddetails&state="+state+"&eretailer_flag="+eretailer_flag+"&page="+page+"&deletearray="+val+"&requestno="+(Math.random()*100);
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
			if(xmlhttp.readyState!=4)
			{
				document.getElementById("SubcatDetails").innerHTML="<span class='style1'><center><br><br><b><img src='/bookbattery/images/pleasewait.gif' align='center'><br>Please wait! Fetching details of retailer...</b></center><br><br></span>";
			}
			if (xmlhttp.readyState==4 && xmlhttp.status==200)
			{
				res=xmlhttp.responseText;
				if (res.indexOf("Session Expired Please login to delete retailer")>=0)
				{
					errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Session Expired Please login to delete retailer </br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='Close_sessionpopup();' class='button4'><br></td></tr>";
					greyout(true);
					document.getElementById("sessionpopup").style.display='block';
					document.getElementById("sessionpopupmessage").innerHTML=errMsg;
					return ;
				}
				document.getElementById("SubcatDetails").innerHTML="";
				document.getElementById("SubcatDetails").innerHTML=res;
			}	
		}
	}
	else
	{
		errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Wrong Password.Please try again</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button'  name='emailok' id='emailok' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='closePopupDelete1();' class='button4'><br></td></tr>";
		document.getElementById("popup").style.display='block';
		greyout(true);
		document.getElementById("popupmessage").innerHTML=errMsg
		document.getElementById("emailok").focus();
		return;
	}
	xmlhttp.open("GET",url,true);
	xmlhttp.send();
	}
	});
}
/*This function is raise a popup to add more catedory details for a retailer*/
function addmoredetails()
{
	errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>You want to add more category details.Click on yes.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='Yes' onclick=\'add_categorydetails();\' class='button4'>&nbsp;&nbsp;<input type='button' name='emailok' id='emailok' value='No' onclick=\'closeretpopup();\' class='button4'></td></tr>";
	categorydetailsreset()
	document.getElementById("popuph").style.display='block';
	greyout(true);
	document.getElementById("popupmessageh").innerHTML=errMsg
	document.getElementById("emailok").focus();
}
/*This function is used to to deselct category, subcategory and brand fields and cursor should focus on category field when click on Yes button in add more details popup */
function add_categorydetails()
{
	for (i = document.addretailer.subcategory.options.length- 1; i >= 1; i--)
	{
		document.addretailer.subcategory.remove(i);
	}
	for (i= document.addretailer.brand.options.length- 1; i >= 1; i--)
	{
		document.addretailer.brand.remove(i);
	}
	document.addretailer.category.options[0].selected=true;
	$('#popuph').hide();
	greyout(false);
	document.addretailer.category.focus();
}
/*This function is used to hide popup*/
function closeretpopup()
{
	$('#popuph').hide();
	greyout(false);
}
/*This function is used to send email to the retailer after successful registration*/
function sendsmstoretailer()
{
	var retailername = document.addretailer.retailername.value;
	var retailerid = document.addretailer.retailerid.value;
	var state=document.addretailer.strstate.value;
	retailername=repl(retailername,"&","%26",1);
	retailername=repl(retailername,"/","%2F",1);
	retailername=repl(retailername,":","%3A",1);
	retailername=repl(retailername,"_","%5F",1);
	retailername=repl(retailername,"-","%2D",1);
	var temp=document.addretailer.temp.value;
	var emailid = document.addretailer.stremail.value;
	var strmbnum = document.addretailer.strmbnum.value;
	var eretailer_flag = document.addretailer.eretailer_flag.value;
	for (i = document.addretailer.subcategory.options.length- 1; i >= 1; i--)
	{
		document.addretailer.subcategory.remove(i);
	}
	for (i= document.addretailer.brand.options.length- 1; i >= 1; i--)
	{
		document.addretailer.brand.remove(i);
	}
	document.addretailer.category.options[0].selected=true;
	$('#popuph').hide();	
	greyout(false);
	var xmlhttp;
	var url="/bookbattery/servlet/RetailerRegistration?hidWhatToDo=sendSMStoRetailer&retailername="+retailername+"&email="+emailid+"&mobilenumber="+strmbnum+"&eretailerflag="+eretailer_flag+"&retailerid="+retailerid+"&state="+state+"&requestno="+(Math.random()*100);
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
		if(xmlhttp.readyState!=4)
		{
		}
		if (xmlhttp.readyState==4 && xmlhttp.status==200)
		{
			
		}
	}
	if(temp=="yes")
	{
		xmlhttp.open("GET",url,true);
		xmlhttp.send();			
	}
}
/*This function is used to reset the category, subcategory and brand fields*/
function categorydetailsreset()
{
	for (i = document.addretailer.subcategory.options.length- 1; i >= 1; i--)
	{
		document.addretailer.subcategory.remove(i);
	}
	for (i= document.addretailer.brand.options.length- 1; i >= 1; i--)
	{
		document.addretailer.brand.remove(i);
	}
	document.addretailer.category.options[0].selected=true;
	$('#popup').hide();	
	greyout(false);
}
/*This fuction is used when admin logout click on browser back button page redirect to admin login page*/
function noBack()
{
	window.history.forward(-3)
	session.invalidate();
	window.location.href="/bookbattery/jsp/admin/batterystore/batteryadminlogin.jsp";
}
window.onpageshow = function(evt) { if (evt.persisted) noBack() }
window.onunload = function() { void (0) }
/*This fuction is used to hide a popup when user won't select subcategory and click on submit button*/
function closepopupsubcategory()
{
	$('#popup').hide();
	greyout(false);
	document.addretailer.subcategory.focus();
}
/*This fuction is used to hide a popup when user won't select category and click on submit button*/
function closepopupcategory()
{
	$('#popup').hide();
	greyout(false);
	document.addretailer.category.focus();
}
/*This function is used to hide the popup when user deletes the subcategory details of a retailer*/
function closePopupDelete()
{
	$('#popup').hide();
	greyout(false);
}
/*This function is used to close the popup*/
function closePopup()
{
	$('#popup').hide();
	greyout(false);
}
/*This function is used to close the popup after submitting the category details*/
function closePopupsh()
{
	$('#popuph').hide();
	greyout(false);
}
//This function is used to dispaly retailer subcat details when admin clicks on first link
function funOnClickFirstSubcatDetls(pagenumber,retailer_id,retailername)
{
	var xmlhttp;
	retailername=repl(retailername,"&","%26",1);
	retailername=repl(retailername,"/","%2F",1);
	retailername=repl(retailername,":","%3A",1);
	retailername=repl(retailername,"_","%5F",1);
	retailername=repl(retailername,"-","%2D",1);
	var state=document.addretailer.strstate.value;
	var eretailerflag=document.addretailer.eretailer_flag.value;
	var url="/bookbattery/servlet/RetailerRegistration?hidWhatToDo=fetchsubcatbranddetailsonload&retailerid="+retailer_id+"&pagenumber="+pagenumber+"&pagetype=first&retailername="+retailername+"&state="+state+"&eretailerflag="+eretailerflag+"&requestno="+(Math.random()*100);
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
		if(xmlhttp.readyState!=4)
		{
			document.getElementById("SubcatDetails").innerHTML="<span class='style1'><center><br><br><b><img src='/bookbattery/images/pleasewait.gif' align='center'><br>Please wait! Fetching details of retailer...</b></center><br><br></span>";
		}
		if (xmlhttp.readyState==4 && xmlhttp.status==200)
		{
			res=xmlhttp.responseText;
			document.getElementById("SubcatDetails").innerHTML="";
			document.getElementById("SubcatDetails").innerHTML=xmlhttp.responseText;
		}
	}
	xmlhttp.open("GET",url,true);
	xmlhttp.send();			
}
//This function is used to dispaly retailer subcat details when admin clicks on previous link
function funOnClickPreviousSubcatDetls(pagenumber,retailer_id,retailername)
{
	var xmlhttp;
	retailername=repl(retailername,"&","%26",1);
	retailername=repl(retailername,"/","%2F",1);
	retailername=repl(retailername,":","%3A",1);
	retailername=repl(retailername,"_","%5F",1);
	retailername=repl(retailername,"-","%2D",1);
	var state=document.addretailer.strstate.value;
	var eretailerflag=document.addretailer.eretailer_flag.value;
	var url="/bookbattery/servlet/RetailerRegistration?hidWhatToDo=fetchsubcatbranddetailsonload&retailerid="+retailer_id+"&pagetype=previous&pagenumber="+pagenumber+"&retailername="+retailername+"&state="+state+"&eretailerflag="+eretailerflag+"&requestno="+(Math.random()*100);
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
		if(xmlhttp.readyState!=4)
		{
			document.getElementById("SubcatDetails").innerHTML="<span class='style1'><center><br><br><b><img src='/bookbattery/images/pleasewait.gif' align='center'><br>Please wait! Fetching details of retailer...</b></center><br><br></span>";
		}
		if (xmlhttp.readyState==4 && xmlhttp.status==200)
		{
			res=xmlhttp.responseText;
			document.getElementById("SubcatDetails").innerHTML="";
			document.getElementById("SubcatDetails").innerHTML=xmlhttp.responseText;
		}
	}
	xmlhttp.open("GET",url,true);
	xmlhttp.send();
}
//This function is used to dispaly retailer subcat details when admin clicks on next link
function funOnClickNextSubcatDetls(pagenumber,retailer_id,retailername)
{
	var xmlhttp;
	retailername=repl(retailername,"&","%26",1);
	retailername=repl(retailername,"/","%2F",1);
	retailername=repl(retailername,":","%3A",1);
	retailername=repl(retailername,"_","%5F",1);
	retailername=repl(retailername,"-","%2D",1);
	var state=document.addretailer.strstate.value;
	var eretailerflag=document.addretailer.eretailer_flag.value;
	var url="/bookbattery/servlet/RetailerRegistration?hidWhatToDo=fetchsubcatbranddetailsonload&retailerid="+retailer_id+"&pagetype=next&pagenumber="+pagenumber+"&retailername="+retailername+"&state="+state+"&eretailerflag="+eretailerflag+"&requestno="+(Math.random()*100);
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
		if(xmlhttp.readyState!=4)
		{
			document.getElementById("SubcatDetails").innerHTML="<span class='style1'><center><br><br><b><img src='/bookbattery/images/pleasewait.gif' align='center'><br>Please wait! Fetching details of retailer...</b></center><br><br></span>";
		}
		if (xmlhttp.readyState==4 && xmlhttp.status==200)
		{
			res=xmlhttp.responseText;
			document.getElementById("SubcatDetails").innerHTML="";
			document.getElementById("SubcatDetails").innerHTML=xmlhttp.responseText;
		}
	}
	xmlhttp.open("GET",url,true);
	xmlhttp.send();
}
//This function is used to dispaly retailer subcat details when admin clicks on last link
function funOnClickLastSubcatDetls(Lastpage,pagenumber,retailer_id,retailername)
{
	var xmlhttp;
	retailername=repl(retailername,"&","%26",1);
	retailername=repl(retailername,"/","%2F",1);
	retailername=repl(retailername,":","%3A",1);
	retailername=repl(retailername,"_","%5F",1);
	retailername=repl(retailername,"-","%2D",1);
	var state=document.addretailer.strstate.value;
	var eretailerflag=document.addretailer.eretailer_flag.value;
	var url="/bookbattery/servlet/RetailerRegistration?hidWhatToDo=fetchsubcatbranddetailsonload&retailerid="+retailer_id+"&pagetype=last&pagenumber="+pagenumber+"&retailername="+retailername+"&state="+state+"&eretailerflag="+eretailerflag+"&lastpage="+Lastpage+"&requestno="+(Math.random()*100);
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
		if(xmlhttp.readyState!=4)
		{
			document.getElementById("SubcatDetails").innerHTML="<span class='style1'><center><br><br><b><img src='/bookbattery/images/pleasewait.gif' align='center'><br>Please wait! Fetching details of retailer...</b></center><br><br></span>";
		}
		if (xmlhttp.readyState==4 && xmlhttp.status==200)
		{
			res=xmlhttp.responseText;
			document.getElementById("SubcatDetails").innerHTML="";
			document.getElementById("SubcatDetails").innerHTML=xmlhttp.responseText;
		}
	}
	xmlhttp.open("GET",url,true);
	xmlhttp.send();
}
//This function is used to dispaly retailer subcat details when admin clicks on pagination link
function getpageSubcatDetls(retailer_id,retailername)
{
	var page = document.getElementById("page").value;
	retailername=repl(retailername,"&","%26",1);
	retailername=repl(retailername,"/","%2F",1);
	retailername=repl(retailername,":","%3A",1);
	retailername=repl(retailername,"_","%5F",1);
	retailername=repl(retailername,"-","%2D",1);
	var state=document.addretailer.strstate.value;
	var eretailerflag=document.addretailer.eretailer_flag.value;
	var url="/bookbattery/servlet/RetailerRegistration?hidWhatToDo=fetchsubcatbranddetailsonload&retailerid="+retailer_id+"&state="+state+"&eretailerflag="+eretailerflag+"&pagenumber="+page+"&retailername="+retailername+"&requestno="+(Math.random()*100);

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
		if(xmlhttp.readyState!=4)
		{
			document.getElementById("SubcatDetails").innerHTML="<span class='style1'><center><br><br><b><img src='/bookbattery/images/pleasewait.gif' align='center'><br>Please wait! Fetching details of retailer...</b></center><br><br></span>";
		}
		if (xmlhttp.readyState==4 && xmlhttp.status==200)
		{
			res=xmlhttp.responseText;
			document.getElementById("SubcatDetails").innerHTML="";
			document.getElementById("SubcatDetails").innerHTML=xmlhttp.responseText;
		}
	}
	xmlhttp.open("GET",url,true);
	xmlhttp.send();
}
