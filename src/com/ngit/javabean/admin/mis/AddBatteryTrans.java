/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ngit.javabean.admin.mis;

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
import java.util.Iterator;

/**
 *
 * @author Sai Krishna Daddala
 */
public class AddBatteryTrans {
    QueryManager qmgr;
    public AddBatteryTrans(QueryManager qm) {
        qmgr=qm;

    }


   /* This method is used to insert a complaint in the database.
	* @strSqlQry : carries the query to execute for insertion
	* @returns : int (This value specifies the outcome of the query)
	*            -1 : query failure
	*			 -2 : Invalid Parameter
	*			 >0 : Query success
	*/
	public int createThumbnail(String strFilePath,String strContentName,String strScriptPath)
	{
		LogLevel.DEBUG(3, new Throwable(), "Method createThumbnail called ");
        LogLevel.DEBUG(5, new Throwable(), "strFilePath:" + strFilePath);
        LogLevel.DEBUG(5, new Throwable(), "strContentName:" + strContentName);
        LogLevel.DEBUG(5, new Throwable(), "strScriptPath:" + strScriptPath);

		if (strFilePath == null || strFilePath.equals("")||strContentName == null || strContentName.equals("")||strScriptPath == null || strScriptPath.equals(""))
        {
            LogLevel.DEBUG(1, new Throwable(),"One of the Mandatory Parameter to is null or empty ");
            return -1;
        }


		try
		{

			String strActualContentPath  = strFilePath + "/"+strContentName;
			String strWebThumbNailPath   = strFilePath+"/100_"+strContentName;
			String strWebThumbNailPath1   = strFilePath+"/90_"+strContentName;
			LogLevel.DEBUG(5, new Throwable(), "strActualContentPath:" + strActualContentPath);
			LogLevel.DEBUG(5, new Throwable(), "strWebThumbNailPath:" + strWebThumbNailPath);

		
			strScriptPath = strScriptPath+" "+strActualContentPath+" "+strWebThumbNailPath+" "+strWebThumbNailPath1;
			//String strScriptPath1 = strScriptPath+" "+strActualContentPath+" "+strWebThumbNailPath1;

			LogLevel.DEBUG(5, new Throwable(), "strScriptPath:" + strScriptPath);

			ExecuteShellScript e = new ExecuteShellScript();
			int retVal = e.intExecuteScript(strScriptPath);
			//int retVal1 = e.intExecuteScript(strScriptPath1);

			LogLevel.DEBUG(5, new Throwable(), "RetVal:" + retVal);

			return retVal;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			LogLevel.ERROR(5, e, "RetVal:" + e);
			return -1;
		}
	}
	public int createThumbnail11(String strFilePath,String strContentName,String strScriptPath)
	{
		LogLevel.DEBUG(3, new Throwable(), "Method createThumbnail called ");
        LogLevel.DEBUG(5, new Throwable(), "strFilePath:" + strFilePath);
        LogLevel.DEBUG(5, new Throwable(), "strContentName:" + strContentName);
        LogLevel.DEBUG(5, new Throwable(), "strScriptPath:" + strScriptPath);

		if (strFilePath == null || strFilePath.equals("")||strContentName == null || strContentName.equals("")||strScriptPath == null || strScriptPath.equals(""))
        {
            LogLevel.DEBUG(1, new Throwable(),"One of the Mandatory Parameter to is null or empty ");
            return -1;
        }


		try
		{

			String strActualContentPath  = strFilePath + "/"+strContentName;
			String strWebThumbNailPath   = strFilePath+"/Thumb_"+strContentName;
			String strWebThumbNailPath1   = strFilePath+"/Thumbdeal_"+strContentName;
		
			LogLevel.DEBUG(5, new Throwable(), "strActualContentPath:" + strActualContentPath);
			LogLevel.DEBUG(5, new Throwable(), "strWebThumbNailPath:" + strWebThumbNailPath);

		
			strScriptPath = strScriptPath+" "+strActualContentPath+" "+strWebThumbNailPath+" "+strWebThumbNailPath1;
			//String strScriptPath1 = strScriptPath+" "+strActualContentPath+" "+strWebThumbNailPath1;

			LogLevel.DEBUG(5, new Throwable(), "strScriptPath:" + strScriptPath);

			ExecuteShellScript e = new ExecuteShellScript();
			int retVal = e.intExecuteScript(strScriptPath);
			//int retVal1 = e.intExecuteScript(strScriptPath1);

			LogLevel.DEBUG(5, new Throwable(), "RetVal:" + retVal);

			return retVal;
		}
		catch(Exception e)
		{
			return -1;
		}
	}
    }