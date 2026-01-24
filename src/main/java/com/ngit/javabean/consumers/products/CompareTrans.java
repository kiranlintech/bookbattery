/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ngit.javabean.consumers.products;

import com.ngit.javabean.loglevel.LogLevel;
import com.ngit.javabean.qrymgr.QueryManager;
import java.util.Vector;
import java.util.Hashtable;
import java.util.ArrayList;
import java.net.Socket;
import java.io.*;

/**
 *
 * @author Manjunath G 
 */
public class CompareTrans {
    QueryManager qmgr;
    public CompareTrans(QueryManager qm) {
        qmgr=qm;

    }


   
 
/* This method is used to fetch all categories from the database.
	* @strSqlQry : carries the query to execute for select
	* @returns : Vector (This vector contains the details of categories)
	*            Null : query failure
 	*			 Vector : Query success
	*/
    public Vector getAllCategories(String strSqlQry)
    {
		LogLevel.DEBUG(3,new Throwable(),"Method getAllCategories called " );
		if( strSqlQry==null || strSqlQry.equals(""))
		{
			LogLevel.DEBUG(1,new  Throwable(),"Parameter to getAllCategories is null or empty " );
			return null;
		}
		Vector catVector=new Vector();
		LogLevel.DEBUG(5,new  Throwable(),"strSqlQry:"+ strSqlQry);

        try{
            catVector=qmgr.executeQuery(strSqlQry);
			LogLevel.DEBUG(5,new  Throwable(),"result:"+ catVector);
            return catVector;
        }
        catch(Exception e){
			LogLevel.DEBUG(5,e,"Exception while fetching categories:"+ e);
			e.printStackTrace();
            return catVector;
        }
      
    }
	
	 /* This method is used to insert a complaint in the database.
	* @strSqlQry : carries the query to execute for insertion
	* @returns : int (This value specifies the outcome of the query)
	*            -1 : query failure
	*			 -2 : Invalid Parameter
	*			 >0 : Query success
	*/
    public String addLead(String strSqlQry1)
    {
		 String res = "";
		LogLevel.DEBUG(3,new Throwable(),"Method addLead called " );
		if( strSqlQry1==null || strSqlQry1.equals(""))
		{
			LogLevel.DEBUG(1,new  Throwable(),"Parameter to addLead is null or empty " );
			return res;
		}
		LogLevel.DEBUG(5,new  Throwable(),"strSqlQry1:"+ strSqlQry1);

        try{
            int i=qmgr.executeUpdate(strSqlQry1);
			LogLevel.DEBUG(5,new  Throwable(),"result:"+ i);
            return res;
        }
        catch(Exception e){
			LogLevel.DEBUG(5,e,"Exception while inserting complaint:"+ e);
			e.printStackTrace();
            return res;
        }
	}
	
   /* This method is used to fetch all subcategories of a category from the database.
	* @strSqlQry : carries the query to execute for select
	* @returns : Vector (This vector contains the details of subcategories)
	*            Null : query failure
 	*			 Vector : Query success
	*/
    public Vector getSubCatsOfCategory(String strSqlQry)
    {
		LogLevel.DEBUG(3,new Throwable(),"Method getSubCatsOfCategory called " );
		if( strSqlQry==null || strSqlQry.equals(""))
		{
			LogLevel.DEBUG(1,new  Throwable(),"Parameter to getSubCatsOfCategory is null or empty " );
			return null;
		}
		Vector subCatVector=new Vector();
		LogLevel.DEBUG(5,new  Throwable(),"strSqlQry:"+ strSqlQry);

        try{
            subCatVector=qmgr.executeQuery(strSqlQry);
			LogLevel.DEBUG(5,new  Throwable(),"result:"+ subCatVector);
            return subCatVector;
        }
        catch(Exception e){
			LogLevel.DEBUG(5,e,"Exception while fetching prodcuts:"+ e);
			e.printStackTrace();
            return subCatVector;
        }
      
    }

   /* This method is used to fetch all products of a category from the database.
	* @strSqlQry : carries the query to execute for select
	* @returns : Vector (This vector contains the details of products)
	*            Null : query failure
 	*			 Vector : Query success
	*/
    public Vector getProds1OfCategory(String strSqlQry1)
    {
		LogLevel.DEBUG(3,new Throwable(),"Method getProdsOfCategory called " );
		if( strSqlQry1==null || strSqlQry1.equals(""))
		{
			LogLevel.DEBUG(1,new  Throwable(),"Parameter to getProdsOfCategory is null or empty " );
			return null;
		}
		Vector prod1Vector=new Vector();
		LogLevel.DEBUG(5,new  Throwable(),"strSqlQry1:"+ strSqlQry1);

        try{
            prod1Vector=qmgr.executeQuery(strSqlQry1);
			LogLevel.DEBUG(5,new  Throwable(),"result:"+ prod1Vector);
            return prod1Vector;
        }
        catch(Exception e){
			LogLevel.DEBUG(5,e,"Exception while fetching prodcuts:"+ e);
			e.printStackTrace();
            return prod1Vector;
        }
      
    }
   /* This method is used to fetch all products of a category from the database.
	* @strSqlQry : carries the query to execute for select
	* @returns : Vector (This vector contains the details of products)
	*            Null : query failure
 	*			 Vector : Query success
	*/
    public Vector getProds2OfCategory(String strSqlQry2)
    {
		LogLevel.DEBUG(3,new Throwable(),"Method getProdsOfCategory called " );
		if( strSqlQry2==null || strSqlQry2.equals(""))
		{
			LogLevel.DEBUG(1,new  Throwable(),"Parameter to getProdsOfCategory is null or empty " );
			return null;
		}
		Vector prod2Vector=new Vector();
		LogLevel.DEBUG(5,new  Throwable(),"strSqlQry2:"+ strSqlQry2);

        try{
            prod2Vector=qmgr.executeQuery(strSqlQry2);
			LogLevel.DEBUG(5,new  Throwable(),"result:"+ prod2Vector);
            return prod2Vector;
        }
        catch(Exception e){
			LogLevel.DEBUG(5,e,"Exception while fetching prodcuts:"+ e);
			e.printStackTrace();
            return prod2Vector;
        }
      
    }
  
    /* This method is used to fetch all products of a category from the database.
	* @strSqlQry : carries the query to execute for select
	* @returns : Vector (This vector contains the details of products)
	*            Null : query failure
 	*			 Vector : Query success
	*/
    public Vector getProds3OfCategory(String strSqlQry3)
    {
		LogLevel.DEBUG(3,new Throwable(),"Method getProdsOfCategory called " );
		if( strSqlQry3==null || strSqlQry3.equals(""))
		{
			LogLevel.DEBUG(1,new  Throwable(),"Parameter to getProdsOfCategory is null or empty " );
			return null;
		}
		Vector prod3Vector=new Vector();
		LogLevel.DEBUG(5,new  Throwable(),"strSqlQry3:"+ strSqlQry3);

        try{
            prod3Vector=qmgr.executeQuery(strSqlQry3);
			LogLevel.DEBUG(5,new  Throwable(),"result:"+ prod3Vector);
            return prod3Vector;
        }
        catch(Exception e){
			LogLevel.DEBUG(5,e,"Exception while fetching prodcuts:"+ e);
			e.printStackTrace();
            return prod3Vector;
        }
      
    }
  /* This method is used to fetch teh details of a vendor byu id from the database.
	* @strSqlQry : carries the query to execute for select
	* @returns : Hashtable (This hashtable contains the details of vendor)
	*            Null : query failure
 	*			 Hashtable : Query success
	*/
    public Hashtable getspecid(String strSqlQry)
    {
		LogLevel.DEBUG(3,new Throwable(),"Method getspecid called " );
		if( strSqlQry==null || strSqlQry.equals(""))
		{
			LogLevel.DEBUG(1,new  Throwable(),"Parameter to getspecid is null or empty " );
			return null;
		}
		Hashtable ht=new Hashtable();
		LogLevel.DEBUG(5,new  Throwable(),"strSqlQry:"+ strSqlQry);

        try{
            ht=qmgr.getRow(strSqlQry);
			LogLevel.DEBUG(5,new  Throwable(),"result:"+ ht);
            return ht;
        }
        catch(Exception e){
			LogLevel.DEBUG(5,e,"Exception while fetching vendor details:"+ e);
			e.printStackTrace();
            return ht;
        }
      
    } 
 
		
      
     
  /* This method is used to fetch all products specs of a category from the database.
	* @strSqlQry : carries the query to execute for select
	* @returns : Vector (This vector contains the details of products)
	*            Null : query failure
 	*			 Vector : Query success
	*/
    public Vector getlable(String strSqlQry9)
    {
		LogLevel.DEBUG(3,new Throwable(),"Method getlable called " );
		if( strSqlQry9==null || strSqlQry9.equals(""))
		{
			LogLevel.DEBUG(1,new  Throwable(),"Parameter to get lable is null or empty " );
			return null;
		}
		
	
		
		Vector lable=new Vector();
		LogLevel.DEBUG(5,new  Throwable(),"strSqlQry9:"+ strSqlQry9);

    

		try{
            lable=qmgr.executeQuery(strSqlQry9);
			LogLevel.DEBUG(5,new  Throwable(),"result1:"+ lable);
            return lable;
        }
        catch(Exception e){
			LogLevel.DEBUG(5,e,"Exception while fetching lable:"+ e);
			e.printStackTrace();
            return lable;
        }
		
      
    }  

    /* This method is used to fetch all products specs of a category from the database.
	* @strSqlQry : carries the query to execute for select
	* @returns : Vector (This vector contains the details of products)
	*            Null : query failure
 	*			 Vector : Query success
	*/
    public Vector getproductspecs1(String strSqlQry1)
    {
		LogLevel.DEBUG(3,new Throwable(),"Method getproductspecs1 called " );
		if( strSqlQry1==null || strSqlQry1.equals(""))
		{
			LogLevel.DEBUG(1,new  Throwable(),"Parameter to getproductspecs1 is null or empty " );
			return null;
		}
		
	
		
		Vector prodVector1=new Vector();
		LogLevel.DEBUG(5,new  Throwable(),"strSqlQry1:"+ strSqlQry1);

    

		try{
            prodVector1=qmgr.executeQuery(strSqlQry1);
			LogLevel.DEBUG(5,new  Throwable(),"result1:"+ prodVector1);
            return prodVector1;
        }
        catch(Exception e){
			LogLevel.DEBUG(5,e,"Exception while fetching prodcutspecs1:"+ e);
			e.printStackTrace();
            return prodVector1;
        }
		
      
    }
	/* This method is used to fetch all products specs of a category from the database.
	* @strSqlQry : carries the query to execute for select
	* @returns : Vector (This vector contains the details of products)
	*            Null : query failure
 	*			 Vector : Query success
	*/
	
	 public Vector getproductspecs2(String strSqlQry2)
    {
		LogLevel.DEBUG(3,new Throwable(),"Method getproductspecs2 called " );
		if( strSqlQry2==null || strSqlQry2.equals(""))
		{
			LogLevel.DEBUG(1,new  Throwable(),"Parameter to getproductspecs2 is null or empty " );
			return null;
		}
		
	
		
		Vector prodVector2=new Vector();
		LogLevel.DEBUG(5,new  Throwable(),"strSqlQry2:"+ strSqlQry2);

    

		try{
            prodVector2=qmgr.executeQuery(strSqlQry2);
			LogLevel.DEBUG(5,new  Throwable(),"result2:"+ prodVector2);
            return prodVector2;
        }
        catch(Exception e){
			LogLevel.DEBUG(5,e,"Exception while fetching prodcuts:"+ e);
			e.printStackTrace();
            return prodVector2;
        }
		
      
    }
	/* This method is used to fetch all products specs of a category from the database.
	* @strSqlQry : carries the query to execute for select
	* @returns : Vector (This vector contains the details of products)
	*            Null : query failure
 	*			 Vector : Query success
	*/
	 public Vector getproductspecs3(String strSqlQry3)
    {
		LogLevel.DEBUG(3,new Throwable(),"Method getproductspecs3 called " );
		if( strSqlQry3==null || strSqlQry3.equals(""))
		{
			LogLevel.DEBUG(1,new  Throwable(),"Parameter to getproductspecs3 is null or empty " );
			return null;
		}
		
	
		
		Vector prodVector3=new Vector();
		LogLevel.DEBUG(5,new  Throwable(),"strSqlQry3:"+ strSqlQry3);

    

		try{
            prodVector3=qmgr.executeQuery(strSqlQry3);
			LogLevel.DEBUG(5,new  Throwable(),"result3:"+ prodVector3);
            return prodVector3;
        }
        catch(Exception e){
			LogLevel.DEBUG(5,e,"Exception while fetching prodcuts:"+ e);
			e.printStackTrace();
            return prodVector3;
        }
		
      
    }
	
	 /* This method is used to fetch all products specs of a category from the database.
	* @strSqlQry : carries the query to execute for select
	* @returns : Vector (This vector contains the details of products)
	*            Null : query failure
 	*			 Vector : Query success
	*/
    public Vector getproductCount1(String strCountofprod1)
    {
		LogLevel.DEBUG(3,new Throwable(),"Method getproductCount1 called " );
		if( strCountofprod1==null || strCountofprod1.equals(""))
		{
			LogLevel.DEBUG(1,new  Throwable(),"Parameter to getproductCount1 is null or empty " );
			return null;
		}
		
	
		
		Vector CountOfProd1=new Vector();
		LogLevel.DEBUG(5,new  Throwable(),"strCountofprod1:"+ strCountofprod1);

    

		try{
            CountOfProd1=qmgr.executeQuery(strCountofprod1);
			LogLevel.DEBUG(5,new  Throwable(),"result1:"+ CountOfProd1);
            return CountOfProd1;
        }
        catch(Exception e){
			LogLevel.DEBUG(5,e,"Exception while fetching prodcutspecs1:"+ e);
			e.printStackTrace();
            return CountOfProd1;
        }
		
      
    }
	/* This method is used to fetch all products specs of a category from the database.
	* @strSqlQry : carries the query to execute for select
	* @returns : Vector (This vector contains the details of products)
	*            Null : query failure
 	*			 Vector : Query success
	*/
	
	 public Vector getproductCount2(String strCountofprod2)
    {
		LogLevel.DEBUG(3,new Throwable(),"Method getproductspecs2 called " );
		if( strCountofprod2==null || strCountofprod2.equals(""))
		{
			LogLevel.DEBUG(1,new  Throwable(),"Parameter to getproductspecs2 is null or empty " );
			return null;
		}
		
	
		
		Vector CountOfProd2=new Vector();
		LogLevel.DEBUG(5,new  Throwable(),"strCountofprod2:"+ strCountofprod2);

    

		try{
            CountOfProd2=qmgr.executeQuery(strCountofprod2);
			LogLevel.DEBUG(5,new  Throwable(),"result2:"+ CountOfProd2);
            return CountOfProd2;
        }
        catch(Exception e){
			LogLevel.DEBUG(5,e,"Exception while fetching prodcuts:"+ e);
			e.printStackTrace();
            return CountOfProd2;
        }
		
      
    }
	/* This method is used to fetch all products specs of a category from the database.
	* @strSqlQry : carries the query to execute for select
	* @returns : Vector (This vector contains the details of products)
	*            Null : query failure
 	*			 Vector : Query success
	*/
	 public Vector getproductCount3(String strCountofprod3)
    {
		LogLevel.DEBUG(3,new Throwable(),"Method getproductspecs3 called " );
		if( strCountofprod3==null || strCountofprod3.equals(""))
		{
			LogLevel.DEBUG(1,new  Throwable(),"Parameter to getproductspecs3 is null or empty " );
			return null;
		}
		
	
		
		Vector CountOfProd3=new Vector();
		LogLevel.DEBUG(5,new  Throwable(),"strCountofprod3:"+ strCountofprod3);

    

		try{
            CountOfProd3=qmgr.executeQuery(strCountofprod3);
			LogLevel.DEBUG(5,new  Throwable(),"result3:"+ CountOfProd3);
            return CountOfProd3;
        }
        catch(Exception e){
			LogLevel.DEBUG(5,e,"Exception while fetching prodcuts:"+ e);
			e.printStackTrace();
            return CountOfProd3;
        }
		
      
    }
	 /* This method is used to fetch all products specs of a category from the database.
	* @strSqlQry : carries the query to execute for select
	* @returns : Vector (This vector contains the details of products)
	*            Null : query failure
 	*			 Vector : Query success
	*/
    public Vector getproductRating1(String strRatingofprod1)
    {
		LogLevel.DEBUG(3,new Throwable(),"Method getproductCount1 called " );
		if( strRatingofprod1==null || strRatingofprod1.equals(""))
		{
			LogLevel.DEBUG(1,new  Throwable(),"Parameter to getproductCount1 is null or empty " );
			return null;
		}
		
	
		
		Vector RatingOfProd1=new Vector();
		LogLevel.DEBUG(5,new  Throwable(),"strRatingofprod1:"+ strRatingofprod1);

    

		try{
            RatingOfProd1=qmgr.executeQuery(strRatingofprod1);
			LogLevel.DEBUG(5,new  Throwable(),"result1:"+ RatingOfProd1);
            return RatingOfProd1;
        }
        catch(Exception e){
			LogLevel.DEBUG(5,e,"Exception while fetching prodcutspecs1:"+ e);
			e.printStackTrace();
            return RatingOfProd1;
        }
		
      
    }
	/* This method is used to fetch all products specs of a category from the database.
	* @strSqlQry : carries the query to execute for select
	* @returns : Vector (This vector contains the details of products)
	*            Null : query failure
 	*			 Vector : Query success
	*/
	
	 public Vector getproductRating2(String strRatingofprod2)
    {
		LogLevel.DEBUG(3,new Throwable(),"Method getproductRating called " );
		if( strRatingofprod2==null || strRatingofprod2.equals(""))
		{
			LogLevel.DEBUG(1,new  Throwable(),"Parameter to getproductRating is null or empty " );
			return null;
		}
		
	
		
		Vector RatingOfProd2=new Vector();
		LogLevel.DEBUG(5,new  Throwable(),"strRatingofprod2:"+ strRatingofprod2);

    

		try{
            RatingOfProd2=qmgr.executeQuery(strRatingofprod2);
			LogLevel.DEBUG(5,new  Throwable(),"result2:"+ RatingOfProd2);
            return RatingOfProd2;
        }
        catch(Exception e){
			LogLevel.DEBUG(5,e,"Exception while fetching prodcuts:"+ e);
			e.printStackTrace();
            return RatingOfProd2;
        }
		
      
    }
	/* This method is used to fetch all products specs of a category from the database.
	* @strSqlQry : carries the query to execute for select
	* @returns : Vector (This vector contains the details of products)
	*            Null : query failure
 	*			 Vector : Query success
	*/
	 public Vector getproductRating3(String strRatingofprod3)
    {
		LogLevel.DEBUG(3,new Throwable(),"Method getproductspecs3 called " );
		if( strRatingofprod3==null || strRatingofprod3.equals(""))
		{
			LogLevel.DEBUG(1,new  Throwable(),"Parameter to getproductspecs3 is null or empty " );
			return null;
		}
		
	
		
		Vector RatingOfProd3=new Vector();
		LogLevel.DEBUG(5,new  Throwable(),"strRatingofprod3:"+ strRatingofprod3);

    

		try{
            RatingOfProd3=qmgr.executeQuery(strRatingofprod3);
			LogLevel.DEBUG(5,new  Throwable(),"result3:"+ RatingOfProd3);
            return RatingOfProd3;
        }
        catch(Exception e){
			LogLevel.DEBUG(5,e,"Exception while fetching prodcuts:"+ e);
			e.printStackTrace();
            return RatingOfProd3;
        }
		
      
    }
	/* This method is used to fetch product name from the database.
	* @strSqlQry : carries the query to execute for select
	* @returns : Vector (This vector contains the details of products)
	*            Null : query failure
 	*			 Vector : Query success
	*/

 public Vector getBrandsOfCategory(String strSqlQry1)
    {
		LogLevel.DEBUG(3,new Throwable(),"Method getproduct3 called " );
		if( strSqlQry1==null || strSqlQry1.equals(""))
		{
			LogLevel.DEBUG(1,new  Throwable(),"Parameter to getproduct3 is null or empty " );
			return null;
		}
		
	
		
		Vector brandVector=new Vector();
		LogLevel.DEBUG(5,new  Throwable(),"strSqlQry1:"+ strSqlQry1);

    

		try{
            brandVector=qmgr.executeQuery(strSqlQry1);
			LogLevel.DEBUG(5,new  Throwable(),"result6:"+ brandVector);
            return brandVector;
        }
        catch(Exception e){
			LogLevel.DEBUG(5,e,"Exception while fetching prodcut:"+ e);
			e.printStackTrace();
            return brandVector;
        }
		
      
    }
/* This method is used to fetch product name from the database.
	* @strSqlQry : carries the query to execute for select
	* @returns : Vector (This vector contains the details of products)
	*            Null : query failure
 	*			 Vector : Query success
	*/

 public Vector getProdsOfBrand(String strSqlQry)
    {
		LogLevel.DEBUG(3,new Throwable(),"Method getproduct3 called " );
		if( strSqlQry==null || strSqlQry.equals(""))
		{
			LogLevel.DEBUG(1,new  Throwable(),"Parameter to getproduct3 is null or empty " );
			return null;
		}
		
	
		
		Vector prodVectorofBrand=new Vector();
		LogLevel.DEBUG(5,new  Throwable(),"strSqlQry:"+ strSqlQry);

    

		try{
            prodVectorofBrand=qmgr.executeQuery(strSqlQry);
			LogLevel.DEBUG(5,new  Throwable(),"result6:"+ prodVectorofBrand);
            return prodVectorofBrand;
        }
        catch(Exception e){
			LogLevel.DEBUG(5,e,"Exception while fetching prodcut:"+ e);
			e.printStackTrace();
            return prodVectorofBrand;
        }
		
      
    }	
	
	
	
	/* This method is used to fetch a category name from the database.
	* @strSqlQry : carries the query to execute for select
	* @returns : Vector (This vector contains the details of products)
	*            Null : query failure
 	*			 Vector : Query success
	*/

public Vector getcategory(String strSqlQry7)
    {
		LogLevel.DEBUG(3,new Throwable(),"Method getcategory called " );
		if( strSqlQry7==null || strSqlQry7.equals(""))
		{
			LogLevel.DEBUG(1,new  Throwable(),"Parameter to getcategory is null or empty " );
			return null;
		}
		
	
		
		Vector category=new Vector();
		LogLevel.DEBUG(5,new  Throwable(),"strSqlQry7:"+ strSqlQry7);

    

		try{
            category=qmgr.executeQuery(strSqlQry7);
			LogLevel.DEBUG(5,new  Throwable(),"result7:"+ category);
            return category;
        }
        catch(Exception e){
			LogLevel.DEBUG(5,e,"Exception while fetching category:"+ e);
			e.printStackTrace();
            return category;
        }
		
      
    }
	/* This method is used to fetch a subcategory from the database.
	* @strSqlQry : carries the query to execute for select
	* @returns : Vector (This vector contains the details of products)
	*            Null : query failure
 	*			 Vector : Query success
	*/
public Vector getsubcategory(String strSqlQry8)
    {
		LogLevel.DEBUG(3,new Throwable(),"Method getsubcategory called " );
		if( strSqlQry8==null || strSqlQry8.equals(""))
		{
			LogLevel.DEBUG(1,new  Throwable(),"Parameter to getsubcategory is null or empty " );
			return null;
		}
		
	
		
		Vector subcategory=new Vector();
		LogLevel.DEBUG(5,new  Throwable(),"strSqlQry8:"+ strSqlQry8);

    

		try{
            subcategory=qmgr.executeQuery(strSqlQry8);
			LogLevel.DEBUG(5,new  Throwable(),"result8:"+ subcategory);
            return subcategory;
        }
        catch(Exception e){
			LogLevel.DEBUG(5,e,"Exception while fetching subcategory:"+ e);
			e.printStackTrace();
            return subcategory;
        }
		
      
    }
/* This method is used to fetch teh details of a vendor by id from the database.
	* @strSqlQry : carries the query to execute for select
	* @returns : ArrayList (This hashtable contains the details of vendor)
	*            Null : query failure
 	*			 ArrayList : Query success
	*/
    public ArrayList getvendorids(String strgetvendorids)
    {
		LogLevel.DEBUG(3,new Throwable(),"Method strgetvendorids called " );
		if( strgetvendorids==null || strgetvendorids.equals(""))
		{
			LogLevel.DEBUG(1,new  Throwable(),"Parameter to getcat is null or empty " );
			return null;
		}
		ArrayList ht=new ArrayList();
		LogLevel.DEBUG(5,new  Throwable(),"strgetvendorids:"+ strgetvendorids);

        try{
            ht=qmgr.getField(strgetvendorids);
			LogLevel.DEBUG(5,new  Throwable(),"result:"+ ht);
            return ht;
        }
        catch(Exception e){
			LogLevel.DEBUG(5,e,"Exception while fetching vendor details:"+ e);
			e.printStackTrace();
            return ht;
        }
      
    } 
	/* This method is used to fetch teh details of a vendor by id from the database.
	* @strSqlQry : carries the query to execute for select
	* @returns : ArrayList (This hashtable contains the details of vendor)
	*            Null : query failure
 	*			 ArrayList : Query success
	*/
    public ArrayList getPrices(String strSqlQry)
    {
		LogLevel.DEBUG(3,new Throwable(),"Method strgetvendorids called " );
		if( strSqlQry==null || strSqlQry.equals(""))
		{
			LogLevel.DEBUG(1,new  Throwable(),"Parameter to getcat is null or empty " );
			return null;
		}
		ArrayList ht=new ArrayList();
		LogLevel.DEBUG(5,new  Throwable(),"strSqlQry:"+ strSqlQry);

        try{
            ht=qmgr.getField(strSqlQry);
			LogLevel.DEBUG(5,new  Throwable(),"result:"+ ht);
            return ht;
        }
        catch(Exception e){
			LogLevel.DEBUG(5,e,"Exception while fetching vendor details:"+ e);
			e.printStackTrace();
            return ht;
        }
      
    } 
	/* This method is used to fetch teh details of a vendor by id from the database.
	* @strSqlQry : carries the query to execute for select
	* @returns : ArrayList (This hashtable contains the details of vendor)
	*            Null : query failure
 	*			 ArrayList : Query success
	*/
    public ArrayList getproductids(String strgetproductids)
    {
		LogLevel.DEBUG(3,new Throwable(),"Method strgetproductids called " );
		if( strgetproductids==null || strgetproductids.equals(""))
		{
			LogLevel.DEBUG(1,new  Throwable(),"Parameter to getcat is null or empty " );
			return null;
		}
		ArrayList ht=new ArrayList();
		LogLevel.DEBUG(5,new  Throwable(),"strgetproductids:"+ strgetproductids);

        try{
            ht=qmgr.getField(strgetproductids);
			LogLevel.DEBUG(5,new  Throwable(),"result:"+ ht);
            return ht;
        }
        catch(Exception e){
			LogLevel.DEBUG(5,e,"Exception while fetching vendor details:"+ e);
			e.printStackTrace();
            return ht;
        }
      
    } 
	 /* This method is used to fetch all categories from the database.
	          * @strSqlQry : carries the query to execute for select
	          * @returns : Vector (This vector contains the details of categories)
	          *            Null : query failure
 	          *			 Vector : Query success
	          */
             public Vector getCategories(String strSqlQry)
                   {
		            LogLevel.DEBUG(3,new Throwable(),"Method getAllCategories called " );
		            if( strSqlQry==null || strSqlQry.equals(""))
		              {
		           	   LogLevel.DEBUG(1,new  Throwable(),"Parameter to addComplaint is null or empty " );
			           return null;
		              }
		            Vector catVector=new Vector();
		            LogLevel.DEBUG(5,new  Throwable(),"strSqlQry:"+ strSqlQry);

                    try
					   {
                        catVector=qmgr.executeQuery(strSqlQry);
			            LogLevel.DEBUG(5,new  Throwable(),"result:"+ catVector);
                        return catVector;
                       }
                    catch(Exception e)
						 {
			              LogLevel.DEBUG(5,e,"Exception while fetching categories:"+ e);
			              e.printStackTrace();
                          return catVector;
                         }
                   }
	
	         /* This method is used to fetch all subscategories of a category from the database.
	          * @strSqlQry : carries the query to execute for select
	          * @returns : Vector (This vector contains the details of subcategories)
	          *            Null : query failure
 	          *			 Vector : Query success
	          */
             public Vector getSubCategories(String strSqlQry)
                   {
		            LogLevel.DEBUG(3,new Throwable(),"Method getSubCatsOfCategory called " );
		            if( strSqlQry==null || strSqlQry.equals(""))
		              {
			           LogLevel.DEBUG(1,new  Throwable(),"Parameter to addComplaint is null or empty " );
			           return null;
		              }
		            Vector subCatVector=new Vector();
		            LogLevel.DEBUG(5,new  Throwable(),"strSqlQry:"+ strSqlQry);

                    try
					   {
                        subCatVector=qmgr.executeQuery(strSqlQry);
			            LogLevel.DEBUG(5,new  Throwable(),"result:"+ subCatVector);
                        return subCatVector;
                       }
                    catch(Exception e)
						 {
			              LogLevel.DEBUG(5,e,"Exception while fetching prodcuts:"+ e);
			              e.printStackTrace();
                          return subCatVector;
                         }
                   }

			 /* This method is used to fetch the Brands from vendor master.
	          * @strSqlQry : carries the query to extract the Brands
	          * @returns : Vector ( Carries the data in the form of Drop Tables)
	          *            -null : query failure
	          *			  Vector : Query success
	          */
             public Vector getBrand(String bn)
                   {
		            LogLevel.DEBUG(3,new Throwable(),"Method getcomplaint called " );
		            if( bn==null || bn.equals(""))
		              {
			           LogLevel.DEBUG(1,new  Throwable(),"Parameter to viewcomplaints is null or empty " );
			           return null;
		              }
		            LogLevel.DEBUG(5,new  Throwable(),"bn:"+ bn);

                    try
					   {
                        Vector i=qmgr.executeQuery(bn);
			            LogLevel.DEBUG(5,new  Throwable(),"result:"+ i);
                        return i;
                       }
                    catch(Exception e)
						 {
			              LogLevel.DEBUG(5,e,"Exception while extracting the complaint :"+ e);
			              e.printStackTrace();
                          return null;
                         }
                   }
				   /* This method is used to fetch all products of a category from the database.
	          * @strSqlQry : carries the query to execute for select
	          * @returns : Vector (This vector contains the details of products)
	          *            Null : query failure
 	          *			 Vector : Query success
	          */
             public Vector getProducts(String strSqlQry)
                   {
		            LogLevel.DEBUG(3,new Throwable(),"Method getProdsOfCategory called " );
		            if( strSqlQry==null || strSqlQry.equals(""))
		              {
			           LogLevel.DEBUG(1,new  Throwable(),"Parameter to addComplaint is null or empty " );
			           return null;
		              }
		            Vector prodVector=new Vector();
		            LogLevel.DEBUG(5,new  Throwable(),"strSqlQry:"+ strSqlQry);

                    try
					   {
                        prodVector=qmgr.executeQuery(strSqlQry);
			            LogLevel.DEBUG(5,new  Throwable(),"result:"+ prodVector);
                        return prodVector;
                       }
                    catch(Exception e)
						 {
			              LogLevel.DEBUG(5,e,"Exception while fetching prodcuts:"+ e);
			              e.printStackTrace();
                          return prodVector;
                         }
                   }
				    /* This method is used to fetch teh details of a vendor by id from the database.
	          * @strSqlQry : carries the query to execute for select
	          * @returns : ArrayList (This hashtable contains the details of vendor)
	          *            Null : query failure
 	          *			 ArrayList : Query success
	          */
             public ArrayList getcat(String strgetcat)
                   {
		            LogLevel.DEBUG(3,new Throwable(),"Method getcat called " );
		            if( strgetcat==null || strgetcat.equals(""))
		              {
			           LogLevel.DEBUG(1,new  Throwable(),"Parameter to getcat is null or empty " );
			           return null;
		              }
		            ArrayList ht=new ArrayList();
		            LogLevel.DEBUG(5,new  Throwable(),"strgetcat:"+ strgetcat);

                    try
					   {
                        ht=qmgr.getField(strgetcat);
			            LogLevel.DEBUG(5,new  Throwable(),"result:"+ ht);
                        return ht;
                       }
                    catch(Exception e)
						 {
			              LogLevel.DEBUG(5,e,"Exception while fetching vendor details:"+ e);
			              e.printStackTrace();
                          return ht;
                         }
                   }
			 /* This method is used to fetch all subscategories of a category from the database.
	          * @strSqlQry : carries the query to execute for select
	          * @returns : Vector (This vector contains the details of subcategories)
	          *            Null : query failure
 	          *			 Vector : Query success
	          */
             public Vector getProbtype(String strSqlQry)
                   {
		            LogLevel.DEBUG(3,new Throwable(),"Method getSubCatsOfCategory called " );
		            if( strSqlQry==null || strSqlQry.equals(""))
		              {
			           LogLevel.DEBUG(1,new  Throwable(),"Parameter to addComplaint is null or empty " );
			           return null;
		              }
		            Vector subCatVector=new Vector();
		            LogLevel.DEBUG(5,new  Throwable(),"strSqlQry:"+ strSqlQry);

                    try
					   {
                        subCatVector=qmgr.executeQuery(strSqlQry);
			            LogLevel.DEBUG(5,new  Throwable(),"result:"+ subCatVector);
                        return subCatVector;
                       }
                    catch(Exception e)
						 {
			              LogLevel.DEBUG(5,e,"Exception while fetching prodcuts:"+ e);
			              e.printStackTrace();
                          return subCatVector;
                         }
                   }

			public static String sendSMS(String strIpAddress,String strPort,String strFromAddress,String strMessage,String ToAddress)
        {
                System.out.println("Inside sendRequest");
                try
                {
                        int intPort = Integer.parseInt(strPort);
                        String strMessageType = "SMS";
                        String strRequest = "Command:"+strMessageType+"\nFrom:"+strFromAddress+"\nMessage:"+strMessage+"\nTo:"+ToAddress+"\n";
                        LogLevel.DEBUG(1, new Throwable(), "strRequest is:"+strRequest);

                        byte[] bytestr = null;
                        byte[] response = new byte[1024];
                        bytestr = strRequest.getBytes("ISO-8859-1");

                        Socket soc = new Socket(strIpAddress , intPort);
                        OutputStream os = soc.getOutputStream();
                        InputStream is = soc.getInputStream();

                        int d;
                        int k = 0;
                        os.write(bytestr);
                        os.flush();

                        while ( ( d = is.read() ) != -1 )
                        {
                                response[k++] = ( byte )d;
                        }
                        is.close();
                        os.close();
                        soc.close();
                        String strResponse = new String(response);
                        LogLevel.DEBUG(1, new Throwable(), "Response is :"+strResponse);
                        return strResponse;

                }
                catch(Exception e)
                {
                        System.out.println("Exception in sending request");
                        e.printStackTrace();
                        return "ERROR";
                }
        }
public static String sendSMS2(String strIpAddress,String strPort,String strFromAddress,String strMessage,String ToAddress)
        {
                System.out.println("Inside sendRequest");
                try
                {
                        int intPort = Integer.parseInt(strPort);
                        String strMessageType = "SMS";
                        String strRequest = "Command:"+strMessageType+"\nFrom:"+strFromAddress+"\nMessage:"+strMessage+"\nTo:"+ToAddress+"\n";
                        LogLevel.DEBUG(1, new Throwable(), "strRequest is:"+strRequest);

                        byte[] bytestr = null;
                        byte[] response = new byte[1024];
                        bytestr = strRequest.getBytes("ISO-8859-1");

                        Socket soc = new Socket(strIpAddress , intPort);
                        OutputStream os = soc.getOutputStream();
                        InputStream is = soc.getInputStream();

                        int d;
                        int k = 0;
                        os.write(bytestr);
                        os.flush();

                        while ( ( d = is.read() ) != -1 )
                        {
                                response[k++] = ( byte )d;
                        }
                        is.close();
                        os.close();
                        soc.close();
                        String strResponse = new String(response);
                        LogLevel.DEBUG(1, new Throwable(), "Response is :"+strResponse);
                        return strResponse;

                }
                catch(Exception e)
                {
                        System.out.println("Exception in sending request");
                        e.printStackTrace();
                        return "ERROR";
                }
        }
		public static String sendSMS3(String strIpAddress,String strPort,String strFromAddress,String strMessage,String ToAddress)
        {
                System.out.println("Inside sendRequest");
                try
                {
                        int intPort = Integer.parseInt(strPort);
                        String strMessageType = "SMS";
                        String strRequest = "Command:"+strMessageType+"\nFrom:"+strFromAddress+"\nMessage:"+strMessage+"\nTo:"+ToAddress+"\n";
                        LogLevel.DEBUG(1, new Throwable(), "strRequest is:"+strRequest);

                        byte[] bytestr = null;
                        byte[] response = new byte[1024];
                        bytestr = strRequest.getBytes("ISO-8859-1");

                        Socket soc = new Socket(strIpAddress , intPort);
                        OutputStream os = soc.getOutputStream();
                        InputStream is = soc.getInputStream();

                        int d;
                        int k = 0;
                        os.write(bytestr);
                        os.flush();

                        while ( ( d = is.read() ) != -1 )
                        {
                                response[k++] = ( byte )d;
                        }
                        is.close();
                        os.close();
                        soc.close();
                        String strResponse = new String(response);
                        LogLevel.DEBUG(1, new Throwable(), "Response is :"+strResponse);
                        return strResponse;

                }
                catch(Exception e)
                {
                        System.out.println("Exception in sending request");
                        e.printStackTrace();
                        return "ERROR";
                }
        }

} 
