/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ngit.javabean.admin.uploadimage;

import com.ngit.javabean.loglevel.LogLevel;
import com.ngit.javabean.qrymgr.QueryManager;
import java.util.Vector;
import java.util.Hashtable;
import java.net.Socket;
import java.io.*;
import java.util.Calendar;
import java.util.Properties;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author Sai Krishna Daddala
 */
public class ImgTrans {
    QueryManager qmgr;
    public ImgTrans(QueryManager qm) {
        qmgr=qm;

    }
	/* This method is used to fetch the details of a vendor by id from the database.
* @strSqlQry : carries the query to execute for select
* @returns : ArrayList (This hashtable contains the details of vendor)
*            Null : query failure
 *			 ArrayList : Query success
*/
    public ArrayList getParentids(String parentID)
        {
        LogLevel.DEBUG(3, new Throwable(), "Method strgetvendorids called ");

        if (parentID == null || parentID.equals(""))
            {
            LogLevel.DEBUG(1, new Throwable(),
              "Parameter to getcat is null or empty ");
            return null;
            }
        ArrayList ht = new ArrayList();
        LogLevel.DEBUG(5, new Throwable(),
          "parentID:" + parentID);

        try
            {
            ht = qmgr.getField(parentID);
            LogLevel.DEBUG(5, new Throwable(), "result:" + ht);
            return ht;
            }
        catch (Exception e)
            {
            LogLevel.DEBUG(5, e,
              "Exception while fetching vendor details:" + e);
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
    public Vector getAllCategories(String strSqlQry)
        {
        LogLevel.DEBUG(3, new Throwable(),
          "Method getSubCatsOfCategory called ");

        if (strSqlQry == null || strSqlQry.equals(""))
            {
            LogLevel.DEBUG(1, new Throwable(),
              "Parameter to addComplaint is null or empty ");
            return null;
            }
        Vector subCatVector = new Vector();
        LogLevel.DEBUG(5, new Throwable(), "strSqlQry:" + strSqlQry);

        try
            {
            subCatVector = qmgr.executeQuery(strSqlQry);
            LogLevel.DEBUG(5, new Throwable(), "result:" + subCatVector);
            return subCatVector;
            }
        catch (Exception e)
            {
            LogLevel.DEBUG(5, e, "Exception while fetching prodcuts:" + e);
            e.printStackTrace();
            return subCatVector;
            }
        }

	
	/* This method creates the thumbnail for the uploaded images */

	public int createThumbnail(String strFilePath,String strContentName,String strScriptPath)
	{
		LogLevel.DEBUG(5, new Throwable(), "strFilePath:" + strFilePath);
		LogLevel.DEBUG(5, new Throwable(), "strContentName:" + strContentName);
		LogLevel.DEBUG(5, new Throwable(), "strScriptPath:" + strScriptPath);
		if (strFilePath == null || strFilePath.equals("")||strContentName == null || strContentName.equals("")||strScriptPath == null || strScriptPath.equals(""))
        {
            return -1;
        }
		try
		{

			String strActualContentPath  = strFilePath + "/"+strContentName;
			String strWebThumbNailPath   = strFilePath+"/thumbnail_"+strContentName;
		
			strScriptPath = strScriptPath+" "+strActualContentPath+" "+strWebThumbNailPath;
			LogLevel.DEBUG(5, new Throwable(), "strScriptPath:" + strScriptPath);
			ExecuteShellScript e = new ExecuteShellScript();
			LogLevel.DEBUG(5, new Throwable(), "e:" + e);
			int retVal = e.intExecuteScript(strScriptPath);
			LogLevel.DEBUG(5, new Throwable(), "retVal:" + retVal);


			return retVal;
		}
		catch(Exception e)
		{
			return -1;
		}
	}
		
    }

