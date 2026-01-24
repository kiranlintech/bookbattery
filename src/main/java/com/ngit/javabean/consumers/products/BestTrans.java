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
/**
 *
 * @author Manjunath G
 */
public class BestTrans {
    QueryManager qmgr;
    public BestTrans(QueryManager qm) {
        qmgr=qm;

    }


   /* This method is used to fetch the categories from category master.
	* @strSqlQry : carries the query to extract the categories
	* @returns : Vector ( Caarries the data in the form of Hashtables)
	*            -null : query failure
	*			 Vector : Query success
	*/
    public Vector getcomplaint(String strSqlQry)
    {
		LogLevel.DEBUG(3,new Throwable(),"Method getcomplaint called " );
		if( strSqlQry==null || strSqlQry.equals(""))
		{
			LogLevel.DEBUG(1,new  Throwable(),"Parameter to getcomplaint is null or empty " );
			return null;
		}
		LogLevel.DEBUG(5,new  Throwable(),"strSqlQry:"+ strSqlQry);

        try{
            Vector i=qmgr.executeQuery(strSqlQry);
			LogLevel.DEBUG(5,new  Throwable(),"result:"+ i);
            return i;
        }
        catch(Exception e){
			LogLevel.DEBUG(5,e,"Exception while extracting the complaint :"+ e);
			e.printStackTrace();
            return null;
        }
      
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
	
	
	
   /* This method is used to fetch all subscategories of a category from the database.
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

   /* This method is used to fetch all products of a category from the database.
	* @strSqlQry : carries the query to execute for select
	* @returns : Vector (This vector contains the details of products)
	*            Null : query failure
 	*			 Vector : Query success
	*/
    public Vector getProdsOfCategory(String strSqlQry)
    {
		LogLevel.DEBUG(3,new Throwable(),"Method getProdsOfCategory called " );
		if( strSqlQry==null || strSqlQry.equals(""))
		{
			LogLevel.DEBUG(1,new  Throwable(),"Parameter to getProdsOfCategory is null or empty " );
			return null;
		}
		Vector prodVector=new Vector();
		LogLevel.DEBUG(5,new  Throwable(),"strSqlQry:"+ strSqlQry);

        try{
            prodVector=qmgr.executeQuery(strSqlQry);
			LogLevel.DEBUG(5,new  Throwable(),"result:"+ prodVector);
            return prodVector;
        }
        catch(Exception e){
			LogLevel.DEBUG(5,e,"Exception while fetching prodcuts:"+ e);
			e.printStackTrace();
            return prodVector;
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



   public Hashtable getsubCategory(String strSqlQry5)

    {
		LogLevel.DEBUG(3,new Throwable(),"Method getspecid called " );
		if( strSqlQry5==null || strSqlQry5.equals(""))
		{
			LogLevel.DEBUG(1,new  Throwable(),"Parameter to getspecid is null or empty " );
			return null;
		}
		Hashtable ht=new Hashtable();
		LogLevel.DEBUG(5,new  Throwable(),"strSqlQry5:"+ strSqlQry5);

        try{
            ht=qmgr.getRow(strSqlQry5);
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
	/* This method is used to fetch the review desc when clicked on more from Review master.
	* @strSqlQry : carries the query to extract the Reviews
	* @returns : Vector ( Caarries the data in the form of Hashtables)
	*            -null : query failure
	*			 Vector : Query success
	*/
	public Vector reviewDesc(String strSqlQry)
    {
		LogLevel.DEBUG(3,new Throwable(),"Method reviewDesc called " );
		if( strSqlQry==null || strSqlQry.equals(""))
		{
			LogLevel.DEBUG(1,new  Throwable(),"Parameter to reviewDesc is null or empty " );
			return null;
		}
		LogLevel.DEBUG(5,new  Throwable(),"strSqlQry:"+ strSqlQry);

        try{
            Vector i=qmgr.executeQuery(strSqlQry);
			LogLevel.DEBUG(5,new  Throwable(),"result:"+ i);
            return i;
        }
        catch(Exception e){
			LogLevel.DEBUG(5,e,"Exception while extracting the reviews:"+ e);
			e.printStackTrace();
            return null;
        }
      
    }

	/* This method is used to fetch the product name from product master.
	          * @strSqlQry : carries the query to extract the products
	          * @returns : Vector ( Caarries the data in the form of Hashtables)
	          *            -null : query failure
 	          *			 Vector : Query success
	          */
             public Vector getBestCount (String strSqlQry2)
                   {
		            LogLevel.DEBUG(3,new Throwable(),"Method getProductName called " );
		            if( strSqlQry2==null || strSqlQry2.equals(""))
		              {
			           LogLevel.DEBUG(1,new  Throwable(),"Parameter to getProductName is null or empty " );
			           return null;
		              }
		            LogLevel.DEBUG(5,new  Throwable(),"strSqlQry2:"+ strSqlQry2);

                    try
					   {
                        Vector i=qmgr.executeQuery(strSqlQry2);
			            LogLevel.DEBUG(5,new  Throwable(),"prodVector1:"+ i);
                        return i;
                       }
                    catch(Exception e)
						 {
			              LogLevel.DEBUG(5,e,"Exception while extracting the comments:"+ e);
			              e.printStackTrace();
                          return null;
                         }
	               }
	/* This method is used to fetch the reviews from Review master.
	* @strSqlQry : carries the query to extract the Reviews
	* @returns : Vector ( Caarries the data in the form of Hashtables)
	*  -null : query failure
	*	Vector : Query success
	*/
 public Vector complaintDesc(String strSqlQry)
    {
		LogLevel.DEBUG(3,new Throwable(),"Method reviewDesc called " );
		if( strSqlQry==null || strSqlQry.equals(""))
		{
			LogLevel.DEBUG(1,new  Throwable(),"Parameter to reviewDesc is null or empty " );
			return null;
		}
		LogLevel.DEBUG(5,new  Throwable(),"strSqlQry:"+ strSqlQry);

        try{
            Vector i=qmgr.executeQuery(strSqlQry);
			LogLevel.DEBUG(5,new  Throwable(),"result:"+ i);
            return i;
        }
        catch(Exception e){
			LogLevel.DEBUG(5,e,"Exception while extracting the reviews:"+ e);
			e.printStackTrace();
            return null;
        }
      
    }

  
}