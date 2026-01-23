function GetOrderDetails()
{
	var ordrefnumber=$("#ordrefnumber").val();
	
	if(ordrefnumber.indexOf("ORDB")>=0 || ordrefnumber.indexOf("ORDI")>=0 )
	{
		everyThingOk("ordrefnumber");
	}	
	else
	{
		alert("This Order Number is incorrect");
		everyThingNotOk("ordrefnumber","This Order Number is incorrect.<br/> Please Check it.");
		return false ;
	}
	
	$.ajax
	({					 
		type: "GET",
		url: "/bookbattery/servlet/OperatorBatteryDetails?hidWhatToDo=GetOrderDetails",
		data: {ordrefnumber:ordrefnumber},
		success: function(data)
		{
			//alert(data);
			Response = data.split('|~|');
			var Order_Details;
			var Online_Details;
			var Ordered_Type =Response[2];
			//alert(Ordered_Type);
			if (Response[0] == "NA")
			{
				Order_Details="NA";
			}
			else
			{
				Order_Details =jQuery.parseJSON(Response[0]);
			}
			
			if (Response[1] == "NA")
			{
				Online_Details="NA";
			}
			else
			{
				Online_Details =jQuery.parseJSON(Response[1]);
			}
			
			//alert("outside1"+Ordered_Type);
			//alert("Response1"+Response[2]);
			
			var order_details_div="";
			var online_details_div="";
			
			if (Order_Details == "NA")
			{
				order_details_div="<tr><td> No details Found</td></tr>";
			}
			else
			{
				order_details_div="<tr><th> Order Number</th><td>"+ Order_Details.order_number + "</td></tr> <tr><th>Consumer Name</th><td>"+ Order_Details.consumer_name + "</td></tr> <tr><th>Consumer Mobile Number</th><td>"+ Order_Details.consumer_mobnumber + "</td></tr> <tr><th>Retailer Name</th><td>"+ Order_Details.retailer_name + "</td></tr> <tr><th>Retailer Mobile Number</th><td>"+ Order_Details.retailer_mobile_number + "</td></tr><tr><th>State</th><td>"+ Order_Details.state + "</td></tr> <tr><th>City</th><td>"+ Order_Details.city + "</td></tr>  <tr><th>Payment Mode</th><td>"+ Order_Details.payment_mode + "</td></tr> <tr><th>Order Status</th><td>"+ Order_Details.order_reasons + "</td></tr>  <th>Payment Status </th><td>"+ Online_Details.Payment_status + "</td></tr><tr><th>Paid Amount </th><td>"+ Online_Details.amount + "</td></tr><tr><th>Payment Description </th><td>"+ Online_Details.description + "</td></tr><tr><th>Quantity</th><td>"+ Order_Details.quantity + "</td></tr> ";
				
				//alert("outside"+Ordered_Type);
				
				if(ordrefnumber.indexOf("ORDB")>=0)
				{
					//alert("inside"+Ordered_Type);
					//alert("insideResponse1"+Response[2]);
					order_details_div=order_details_div+"<tr><th>Order Type </th><td>"+ Order_Details.order_type + "</td></tr><tr><tr><th>With Out Old Battery Price</th><td>"+ Order_Details.price + "</td></tr><tr><th>With Old Battery Price</th><td>"+ Order_Details.witholdbatprice + "</td></tr>";
				}
				else
				{
					//alert("inside else"+Ordered_Type);
					//alert("inside else Response1"+Response[2]);	
					order_details_div=order_details_div+"<tr><th>Inverter Price</th><td>"+ Order_Details.price + "</td></tr>";
				}					
			}
			$("#Order_details").html(order_details_div);	
			$("#Order_details_div").show();	
				
			if (Online_Details == "NA")
			{
				online_details_div="<tr><td> No details Found</td></tr>";
			}
			else
			{
				online_details_div=" <tr><th> Online Payment Status</th><td>"+ Online_Details.Payment_status+ "</td></tr> <tr><th> Payment URL </th><td>"+ Online_Details.payment_url + "</td></tr> ";
			}
			$("#Online_details").html(online_details_div);
			$("#update_button").html("<input type='button' style='width: 200px;' value='Update Order To Cash' class='button4' onclick='javascript:updateOrderTOCash(\""+Order_Details.order_number+"\");'>");
			$("#Online_details_div").show();	
		}
	});
	
}



function updateOrderTOCash(Order_Number)
{
	
	$.ajax
	({					 
		type: "GET",
		url: "/bookbattery/servlet/OperatorBatteryDetails?hidWhatToDo=UpdatePaymentMode",
		data: {ordrefnumber:Order_Number},
		success: function(data)
		{
			alert(data);
			FunReset();
		}
	});
}



function FunReset()
{
	 window.location.reload();
}


