

function load_products_cart()
{
	var cart;
	var jsonStr = JSON.stringify( cart );
	//now the cart is {"item":"Product 1","price":35.50,"qty":2}
	var cartValue = localStorage.getItem( "cart" );
	var cartObj = JSON.parse( cartValue );
	console.log( cartObj );
	console.log( cartObj.length );
	
	if (cartObj.length < 0)
	{
		alert(10);
	}
	else
	{
		for (i = 0; i <  cartObj.length; i++) 
		{
			var Cart_Product_Type=Cart_Product_Type.get(i);
			var Cart_Product_Make=Cart_Product_Make.get(i);
			console.log( Cart_Product_Make );
			alert(Cart_Product_Make);
				// cartObj.Cart_Product_Model[i];
				// cartObj.Cart_Product_Price[i];
				// cartObj.Cart_Product_Obrp[i];
				// cartObj.Cart_Product_Quantity[i];
				// cartObj.Cart_Product_State[i];
				// cartObj.Cart_Product_City[i];
				// cartObj.Cart_Product_Return[i];
				// cartObj.Cart_Product_Image[i];
		}
	}
}