package com.ngit.javabean.consumers.products;
import com.ngit.javabean.loglevel.LogLevel;
import com.ngit.javabean.qrymgr.QueryManager;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.net.URL;

public class GetCookie 
{
    QueryManager qmgr;
    public GetCookie(QueryManager qm) 
	{
        qmgr=qm;
    }
	//######### This method is used to get cookies Of State and City
	public static String getCookieStateCity(HttpServletRequest req,HttpServletResponse res,HttpSession session)
	{
		try
		{
			String Product_State_Cookie="";
			String Product_City_Cookie="";
			//################### Getting Location From Cookies  
			Cookie[] cookies = req.getCookies();
			if (cookies != null) 
			{
			 for (Cookie cookie : cookies) {
				 System.out.println("State Cookie"+cookie.getName());
			   if (cookie.getName().equals("product_state_cookie")) {
					Product_State_Cookie=cookie.getValue();
				} 
			   if (cookie.getName().equals("product_city_cookie")) {
					Product_City_Cookie=cookie.getValue();
				}	 
			  }
			}
			
			if(Product_State_Cookie.isEmpty() || Product_State_Cookie.equals("0") || Product_State_Cookie.equals("null") || Product_State_Cookie.equals("undefined") || Product_State_Cookie.equals(null) || Product_State_Cookie.equals("") || Product_State_Cookie == "")
			{
				Product_State_Cookie="Karnataka";
				Product_City_Cookie="Bangalore";
			}
			else
			{
				if(Product_City_Cookie.isEmpty() || Product_City_Cookie.equals("0")  || Product_City_Cookie.equals("null") || Product_City_Cookie.equals("undefined") || Product_City_Cookie.equals(null)  || Product_City_Cookie.equals("") || Product_City_Cookie == "")
				{
					Product_State_Cookie="Karnataka";
					Product_City_Cookie="Bangalore";
				}
				else
				{
					Product_State_Cookie=Product_State_Cookie;
					Product_City_Cookie=Product_City_Cookie;
				}
			}
			
			LogLevel.DEBUG(5, new Throwable(), "Product_State_Cookie :" + Product_State_Cookie);
			LogLevel.DEBUG(5, new Throwable(), "Product_City_Cookie :" + Product_City_Cookie);
			
			return Product_State_Cookie+"~"+Product_City_Cookie;
		}
		catch(Exception e)
		{
				e.printStackTrace();
				return "ERROR";
		}
	}
	
}//end of Class