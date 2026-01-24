/***********************************************************************		
	NGIT Confidential. 
	@File Name   : AddLaptopBatteryDetails.java
	@Description : This Servlet is used to allow the operator to Login
	@Date        : 6th Feb 2014
******************************************************************/ 
package com.ngit.servlets.admin.batterystore; 
import com.ngit.javabean.qrymgr.QueryManager;
import com.ngit.javabean.loglevel.LogLevel;

import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.io.FilenameUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.List;
import java.util.Iterator;
import java.util.Hashtable;
import java.util.ArrayList;
import java.util.Enumeration;
import java.io.File;
import java.util.Date;
import java.io.FileInputStream;
import java.util.Properties;
import java.util.Vector;
import java.io.IOException;
import java.net.URLDecoder;  
import java.net.URLEncoder; 
import com.oreilly.servlet.MultipartRequest;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
/*
 * @author Sai Krishna Daddala
 */
/* This class is to Admin Login. This class initializes the necessary connection pools and perform the transactions on the pools. */
public class AddLaptopBatteryDetails extends HttpServlet 
{
 	private ServletContext context; 
	QueryManager qm;
	String baseURL;
	static final long serialVersionUID=21;
	/*This init method initializes the necessary connection pools and perform the transactions on the pools from respectvie files. */
	public void init(ServletConfig config)throws ServletException
	{
		super.init(config); 
		context = getServletContext(); 
		try
		{
			String strMOPConfig = getInitParameter("paramMopConfig"); 
			Properties propsMOPConfig = new Properties(); 
			FileInputStream fin1      = new FileInputStream(new File(context.getRealPath(strMOPConfig))); 
			propsMOPConfig.load(fin1); 
			fin1.close(); 
			context.setAttribute("contextPropMOPConfig",propsMOPConfig); 
			//	initialize log
			String strLogLevel = (propsMOPConfig.getProperty("LogLevel")!=null)?propsMOPConfig.getProperty("LogLevel"):"1";
			if(strLogLevel.equals(""))
			strLogLevel = "1";
			String baseurldirectory = (propsMOPConfig.getProperty("baseurldirectory")!=null)?propsMOPConfig.getProperty("baseurldirectory"):"";
			String strLogFilePath = (propsMOPConfig.getProperty("LogFilePath")!=null)?propsMOPConfig.getProperty("LogFilePath"):"/home/ngit/tomcat/webapps"+baseurldirectory+"logs/";
			if(strLogFilePath.equals(""))
			strLogFilePath = "/home/ngit/tomcat/webapps"+baseurldirectory+"logs/";
			LogLevel.setLogLevel( strLogLevel, strLogFilePath, "bookbattery.log"); 
			baseURL = (propsMOPConfig.getProperty("baseURL")!=null)?(propsMOPConfig.getProperty("baseURL")):"";
			LogLevel.DEBUG(5, new Throwable(), "baseURL :" + baseURL);
  		}
		catch(IOException ioe)
		{
			ioe.printStackTrace(); 
		}
		catch(Exception e)
		{
			e.printStackTrace(); 
		}
	}
	/* This doGet service method calls doPost service method to handle all the reqs and responses to perform Admin Login. */
 	public void doGet (HttpServletRequest req , HttpServletResponse res )throws IOException , ServletException
	{
		doPost(req, res);
	}
	/*This doPost service method handles all the requests and responses passing from doGet service method to perform Admin Login.*/
	public void doPost( HttpServletRequest req , HttpServletResponse res )throws IOException , ServletException
	{
		res.setContentType("text/html;charset=UTF-8");
        HttpSession session=req.getSession(true);
 		session=req.getSession(true);
		String struserName=(String)session.getAttribute("sesBatteryAdminName"); 
		LogLevel.DEBUG(5,new Throwable(),"struserName :"+struserName );
		Properties propsMOPConfig = (Properties)context.getAttribute("contextPropMOPConfig"); 		
		 String baseurldirectory = (propsMOPConfig.getProperty("baseurldirectory")!=null)?(propsMOPConfig.getProperty("baseurldirectory")):"";

		if(propsMOPConfig==null)
		{
			LogLevel.DEBUG(1,new Throwable(),"Properties not loaded. So reloading" );
			try
			{
				String strMOPConfig = "/home/ngit/tomcat/webapps"+baseurldirectory+"properties/bookbatteryconfig.properties"; 
				propsMOPConfig = new Properties(); 
				FileInputStream fin1      = new FileInputStream(new File(context.getRealPath(strMOPConfig))); 
				propsMOPConfig.load(fin1); 
				fin1.close(); 
				context.setAttribute("contextPropMOPConfig",propsMOPConfig); 
			}
 			catch(Exception e)
			{
				LogLevel.ERROR(1, e ,"Permanent Problem So Giving UP: Not able to load properties" );
				e.printStackTrace();
				return;
			}
		}
		qm = QueryManager.getInstance(propsMOPConfig) ;
		String strWhatToDo = (req.getParameter("hidWhatToDo")!=null)?req.getParameter("hidWhatToDo"):""; 
		/*This methos is used to check battery models available or not*/
		if(strWhatToDo.equalsIgnoreCase("checkmodel"))
		{
			String strbatname = (req.getParameter("batname")!=null)?(req.getParameter("batname")):"";
			LogLevel.DEBUG(5,new Throwable(),"strbatname :"+strbatname );

			String strbatbrand = (req.getParameter("batterybrand")!=null)?(req.getParameter("batterybrand")):"";
			LogLevel.DEBUG(5,new Throwable(),"strbatbrand :"+strbatbrand );

			String strbatmodel = (req.getParameter("batterymodel")!=null)?(req.getParameter("batterymodel")):"";
			LogLevel.DEBUG(5,new Throwable(),"strbatmodel :"+strbatmodel );

			ServletOutputStream out=res.getOutputStream();
			System.out.println("ajaxmodelname called");

			try
			  {
				String strSqlQry = "SELECT battery_model FROM laptop_battery_details WHERE battery_brand='"+strbatbrand+"' and battery_model='"+strbatmodel+"'";
				LogLevel.DEBUG(5,new Throwable(),"strSqlQry :"+strSqlQry);
				
				Hashtable ht = qm.getRow(strSqlQry);
				String bat_model=(String)ht.get("battery_model");
				LogLevel.DEBUG(5,new Throwable(),"bat_model :"+bat_model);

				if(ht.isEmpty())
				 { 
					LogLevel.DEBUG(1,new Throwable(),"No Name exists u can continue ");
					out.println("<font color='#00FF00'>Battery Model Avaliable!</font>");
					return;
				 }
				else
				 {
					LogLevel.DEBUG(1,new Throwable(),"");
					out.println("<font color='#FF0000'>Battery Model Already Exists!</font>");
					return;
				  }
			  }
			 catch(IOException ioe)
			  {
					LogLevel.ERROR(0,ioe,"problem Caught IOException if(add) !! "+ioe);
					ioe.printStackTrace();
					out.println("problem Caught IOException");
			  }
			 catch(Exception e)
			  {
					LogLevel.ERROR(0,e,"Problem Caught Exception if(add)!! "+e);
					e.printStackTrace();
					out.println("problem Caught Exception");
			  }
					out.flush();
					out.close();
		}
		/*This methos is used to insert laptop battery details into laptop_battery_details table*/
		else if(strWhatToDo.equalsIgnoreCase("addlaptopbatterydetails"))
		{
			ServletFileUpload servletFileUpload = null;

			String batterybrand = (req.getParameter("batterybrand")!=null)?(req.getParameter("batterybrand")):"";
			LogLevel.DEBUG(5,new Throwable(),"batterybrand :"+batterybrand );

			String batterymodel = (req.getParameter("batterymodel")!=null)?(req.getParameter("batterymodel")):"";
			LogLevel.DEBUG(5,new Throwable(),"batterymodel :"+batterymodel );

			String batterywarrnty = (req.getParameter("batterywarrnty")!=null)?(req.getParameter("batterywarrnty")):"";
			LogLevel.DEBUG(5,new Throwable(),"batterywarrnty :"+batterywarrnty );

			String voltage = (req.getParameter("voltage")!=null)?(req.getParameter("voltage")):"";
			LogLevel.DEBUG(5,new Throwable(),"voltage :"+voltage );

			String cellcount = (req.getParameter("cellcount")!=null)?(req.getParameter("cellcount")):"";
			LogLevel.DEBUG(5,new Throwable(),"cellcount :"+cellcount );

			String watthr = (req.getParameter("watthr")!=null)?(req.getParameter("watthr")):"";
			LogLevel.DEBUG(5,new Throwable(),"watthr :"+watthr );

			String batteryactualprice = (req.getParameter("batteryactualprice")!=null)?(req.getParameter("batteryactualprice")):"";
			LogLevel.DEBUG(5,new Throwable(),"batteryactualprice :"+batteryactualprice );

			String batterydesc = (req.getParameter("batterydesc")!=null)?(req.getParameter("batterydesc")):"";
			LogLevel.DEBUG(5,new Throwable(),"batterydesc :"+batterydesc );

			String batpartnum = (req.getParameter("batpartnum")!=null)?(req.getParameter("batpartnum")):"";
			LogLevel.DEBUG(5,new Throwable(),"batpartnum :"+batpartnum );

			String strIconPath = (req.getParameter("batteryattachment")!=null)?(req.getParameter("batteryattachment")):"";
			LogLevel.DEBUG(5,new Throwable(),"strIconPath :"+strIconPath );

			String amazonlink = (req.getParameter("amazonlink")!=null)?(req.getParameter("amazonlink")):"";
			LogLevel.DEBUG(5,new Throwable(),"amazonlink :"+amazonlink );

			String strVirtualPath = (propsMOPConfig.getProperty("VirtualPath")!=null)?(propsMOPConfig.getProperty("VirtualPath")).trim():"/home/mms/tomcat/webapps";
			LogLevel.DEBUG(5, new Throwable(), "strVirtualPath:" + strVirtualPath);

			String strCMSServerIP = (propsMOPConfig.getProperty("CMS_SERVER_IP")!=null)?(propsMOPConfig.getProperty("CMS_SERVER_IP")).trim():"meraopinion.com";
			LogLevel.DEBUG(5, new Throwable(), "strCMSServerIP:" + strCMSServerIP);

			String strRelativePath = (propsMOPConfig.getProperty("RelativePathLaptopBatteries")!=null)?(propsMOPConfig.getProperty("RelativePathLaptopBatteries")).trim():"/bookbattery/tmpfiles";
			LogLevel.DEBUG(5, new Throwable(), "strRelativePath:" + strRelativePath);

			double attachmentLimit = 4.0;
			String strbatdesc ="";

				if(batterydesc.length()>10)
				{
				int len = batterydesc.length();
				int totalcharInLine = 10;
				for(int h=0; h<len; h+=totalcharInLine)
				{
				String part = batterydesc.substring(h, Math.min(len,h + totalcharInLine));
				strbatdesc = strbatdesc+part+"\r\n";
				}
				}
				else
				{	
				strbatdesc = batterydesc;
				}
				LogLevel.DEBUG(5, new Throwable(), "strbatdesc joe:" + strbatdesc);
						
			try
			  { 
				String strSqlQrybat = "SELECT battery_model FROM laptop_battery_details WHERE  battery_brand='"+batterybrand+"' and battery_model='"+batterymodel+"'";
				LogLevel.DEBUG(5,new Throwable(),"strSqlQrybat :"+strSqlQrybat);
				
				Hashtable ht = qm.getRow(strSqlQrybat);
				String battery_model=(String)ht.get("battery_model");
				LogLevel.DEBUG(5,new Throwable(),"battery_model :"+battery_model);

				if(ht.isEmpty())
				 { 
					// create the local file system folders
					String FilePath = strVirtualPath+"/"+strRelativePath+"/";
					File f1 = new File(FilePath);
					if(!f1.exists())
						f1.mkdirs();
					
					FilePath = FilePath+batterybrand+"/"+batterymodel;
					LogLevel.DEBUG(5,new Throwable(),"FilePath :"+FilePath);

					f1 = new File(FilePath);
					if(!f1.exists())
					f1.mkdirs();

				// create a multipart object to upload files from client machine to server
				MultipartRequest multi = new MultipartRequest(req, FilePath , 15 * 1024 * 1024,new com.oreilly.servlet.multipart.DefaultFileRenamePolicy());

				// find the file path, name and extension
				strIconPath = strIconPath.replace('\\','/');
				LogLevel.DEBUG(4, new Throwable(), "battery icon path is : " +strIconPath );

				String strIconFileName = strIconPath.substring (strIconPath.lastIndexOf("/")+1,strIconPath.length());
				strIconFileName = strIconFileName.trim();
				strIconFileName= strIconFileName.replace(" ", "%20");
				LogLevel.DEBUG(5,new Throwable(),"strIconFileName is : "+strIconFileName);

				int index = strIconPath.lastIndexOf('.');
				String strExtension = strIconPath.substring( ++index );;

				// allow only image files
				if (( strExtension.equalsIgnoreCase("gif") ) || ( strExtension.equalsIgnoreCase("bmp") )||( strExtension.equalsIgnoreCase("jpg") ) ||   ( strExtension.equalsIgnoreCase("jpeg") )||( strExtension.equalsIgnoreCase("jpe") ) ||  ( strExtension.equalsIgnoreCase("png") )||( strExtension.equalsIgnoreCase("wbmp") )  )
				{
					// get the list of files attached in the req
					Enumeration files = multi.getFileNames();

					while (files.hasMoreElements())
					{
						String name = (String)files.nextElement();

						String strOrigFileName = multi.getOriginalFileName(name);
						String strenten="";
						if( strExtension.equalsIgnoreCase("JPG") || strExtension.equalsIgnoreCase("jpg"))
						{
							strenten= strOrigFileName.replace(".JPG", ".jpg");
						}
						else if( strExtension.equalsIgnoreCase("GIF") || strExtension.equalsIgnoreCase("gif"))
						{
							strenten= strOrigFileName.replace(".GIF", ".gif");
						}
						else if( strExtension.equalsIgnoreCase("BMP") || strExtension.equalsIgnoreCase("bmp"))
						{
							strenten= strOrigFileName.replace(".BMP", ".bmp");
						}
						else if( strExtension.equalsIgnoreCase("JPEG") || strExtension.equalsIgnoreCase("jpeg"))
						{
							strenten= strOrigFileName.replace(".JPEG", ".jpeg");
						}
						else if( strExtension.equalsIgnoreCase("JPE") || strExtension.equalsIgnoreCase("jpe"))
						{
							strenten= strOrigFileName.replace(".JPE", ".jpe");
						}
						else if( strExtension.equalsIgnoreCase("PNG") || strExtension.equalsIgnoreCase("png"))
						{
							strenten= strOrigFileName.replace(".PNG", ".png");
						}
						else if( strExtension.equalsIgnoreCase("WBMP") || strExtension.equalsIgnoreCase("wbmp"))
						{
							strenten= strOrigFileName.replace(".WBMP", ".wbmp");
						}

						File oldFile=new File(FilePath + "/"+strOrigFileName);
						LogLevel.DEBUG(5, new Throwable(), "oldFile: " + oldFile);
						String strNewFileName=strenten;
						//strOrigFileName= strOrigFileName.replace(".JPG", ".jpg");
						
						LogLevel.DEBUG(5, new Throwable(), "strNewFileName:"+strNewFileName);
						File newFile=new File(FilePath + "/"+strNewFileName);
						LogLevel.DEBUG(5, new Throwable(), "newFile: " + newFile);
						oldFile.renameTo(newFile);

						strOrigFileName= strOrigFileName.replace(" ", "%20");
						strOrigFileName= strOrigFileName.replace(".JPG", ".jpg");
						strOrigFileName= strOrigFileName.replace(".GIF", ".gif");
						strOrigFileName= strOrigFileName.replace(".BMP", ".bmp");
						strOrigFileName= strOrigFileName.replace(".JPEG", ".jpeg");
						strOrigFileName= strOrigFileName.replace(".JPE", ".jpe");
						strOrigFileName= strOrigFileName.replace(".WBMP", ".wbmp");
						strOrigFileName= strOrigFileName.replace(".PNG", ".png");
						LogLevel.DEBUG(5, new Throwable(), "strOrigFileName = " + strOrigFileName);
						
						if(strOrigFileName==null || strOrigFileName.equals(""))
						{
						LogLevel.DEBUG(5, new Throwable(), "File not selected");
						continue;
						}

						File f = multi.getFile(name);
						double picSize = (f.length())/(1024*1024);
						//double picSize = f.length();
						
						/* validating file size */
							if(picSize > attachmentLimit)
						{

							LogLevel.DEBUG(5, new Throwable(), "Attachment size exceeded");

							session.setAttribute("sesErrorMsg","<font color='#CC0000' class='vrb10'>You can upload Photo of size upto "+attachmentLimit+" MB only</font>");
							res.sendRedirect("../jsp/admin/batterystore/laptopbatterywale/addlaptopbatterywale.jsp");
							return;
						}
						else
						{
													

					//	String strFilePath = FilePath +"/"+strFileSysName;
						String strFilePath  = FilePath + "/"+strOrigFileName;
						LogLevel.DEBUG(5, new Throwable(), "strFilePath: " + strFilePath);

						
						// form the picture URL
						String strPicURL="http://"+strCMSServerIP+strRelativePath+"/"+batterybrand+"/"+batterymodel+"/"+strOrigFileName;
						LogLevel.DEBUG(5, new Throwable(), "strPicURL : " + strPicURL);

						String strSqlQry = "insert into laptop_battery_details(battery_id,battery_brand,battery_model,battery_warranty,voltage,battery_cellcount,watt_hr,battery_actual_price,description,icon_name,icon_url,amazonlink,battery_part_no,creation_date)values(NULL,'"+batterybrand+"','"+batterymodel+"','"+batterywarrnty+"','"+voltage+"','"+cellcount+"','"+watthr+"',"+batteryactualprice+",'"+strbatdesc+"','"+strIconFileName+"','"+strPicURL+"','"+amazonlink+"','"+batpartnum+"',now())";
						LogLevel.DEBUG(5,new Throwable()," : "+strSqlQry);
						int reslt = qm.executeUpdate(strSqlQry);
						}
					}
				}
			  	int reslt=0;
				if(reslt >0)
					{
						LogLevel.DEBUG(1,new Throwable()," Failed to update your details! ");
						session.setAttribute("priority","1");
						session.setAttribute("sesbatErrorMsg", "<font color='#FF3333' class='top1'>Failed to add laptop battery details! </font> ");
						res.sendRedirect("../jsp/admin/batterystore/laptopbatterywale/addlaptopbatterywale.jsp");
						return;
					}
				   else
				    {
						LogLevel.DEBUG(1,new Throwable(),"Successfully updated your details! ");
						session.setAttribute("sesbatErrorMsg", "<font color='#000000' class='top1'>Successfully added laptop battery details!</font>");
						//session.setAttribute("sescontVector", reslt);
						res.sendRedirect("../jsp/admin/batterystore/laptopbatterywale/addlaptopbatterywale.jsp");
				    }
						
				 }
				 else
				  {
					LogLevel.DEBUG(1,new Throwable(),"Successfully updated your details! ");
					session.setAttribute("sesbatErrorMsg", "<font color='#000000' class='top1'>Already added battery for Brand "+batterybrand+" and Model "+batterymodel+".</font>");
					//session.setAttribute("sescontVector", reslt);
					res.sendRedirect("../jsp/admin/batterystore/laptopbatterywale/addlaptopbatterywale.jsp");

			     }
			   }
			    catch(IOException ioe)
			     {
						LogLevel.ERROR(0,ioe,"problem Caught IOException if(add) !! "+ioe);
						ioe.printStackTrace();
						session.setAttribute("sesbatErrorMsg",	"Generall Error...Please Try Again" );
						res.sendRedirect("../jsp/admin/batterystore/laptopbatterywale/addlaptopbatterywale.jsp");
			     }
			    catch(Exception e)
			     {
						LogLevel.ERROR(0,e,"Problem Caught Exception if(add)!! "+e);
						e.printStackTrace();
						session.setAttribute("sesbatErrorMsg",	"General Error...Please Try Again" );
						res.sendRedirect("../jsp/admin/batterystore/laptopbatterywale/addlaptopbatterywale.jsp");
			     }
		    }
		else if(strWhatToDo.equalsIgnoreCase("getlaptopbatterybrands"))
		  {
			String keyword = (req.getParameter("keyword")!=null)?(req.getParameter("keyword")):"";
			LogLevel.DEBUG(5,new Throwable(),"keyword :"+keyword );
			try
			  {
				String strSqlQry ="select distinct(battery_brand) from laptop_battery_details order by battery_brand asc";
				LogLevel.DEBUG(5, new Throwable(), "strSqlQry :" + strSqlQry);

				Vector LaptopBatteryBrandVector=qm.executeQuery(strSqlQry);
				LogLevel.DEBUG(5,new Throwable(),"BatteryBrandVector:"+LaptopBatteryBrandVector );
				if(LaptopBatteryBrandVector.isEmpty())
				{
					LogLevel.DEBUG(1,new Throwable(),"Failed to fetch laptop bookbattery ");
					session.setAttribute("priority","1");
					session.setAttribute("sesLaptopbatteryErrorMsg", "<font color='#00dd00' class='vrb10'>Failed to laptop bookbattery! </font> ");
					res.sendRedirect("../jsp/admin/batterystore/laptopbatterywale/editlaptopbattery.jsp");
					return;
				}
				else
				{
					if(keyword.equals("editbattery"))
					{
					LogLevel.DEBUG(1, new Throwable(),"Succesfully Fetched Categories");
					session.setAttribute("sesLaptopbatterybrandsvector", LaptopBatteryBrandVector);
					session.setAttribute("sesLaptopbatteryErrorMsg","<font color='#CC0000' class='vrb10'>Successfully Fetched Laptop Batteries.</font> ");
					res.sendRedirect("../jsp/admin/batterystore/laptopbatterywale/editlaptopbattery.jsp?keyword="+keyword);
					return;
					}
					else if(keyword.equals("deletebattery"))
					{
					LogLevel.DEBUG(1, new Throwable(),"Succesfully Fetched Categories");
					session.setAttribute("sesLaptopbatterybrandsvector", LaptopBatteryBrandVector);
					session.setAttribute("sesLaptopbatteryErrorMsg","<font color='#CC0000' class='vrb10'>Successfully Fetched Laptop Batteries.</font> ");
					res.sendRedirect("../jsp/admin/batterystore/laptopbatterywale/deletelaptopbattery.jsp?keyword="+keyword);
					return;
					}
					else
					{
					LogLevel.DEBUG(1, new Throwable(),"Succesfully Fetched Categories");
					session.setAttribute("sesLaptopbatterybrandsvector", LaptopBatteryBrandVector);
					session.setAttribute("sesLaptopbatteryErrorMsg","<font color='#CC0000' class='vrb10'>Successfully Fetched Laptop Batteries.</font> ");
					res.sendRedirect("../jsp/admin/batterystore/laptopbatterywale/editlaptopbattery.jsp");
					return;
					}
				}
				}
			catch(IOException ioe)
				{
					//LogLevel.ERROR(0,ioe,"problem Caught IOException if(add) !! "+ioe);
					ioe.printStackTrace();
					session.setAttribute("sesLaptopbatteryErrorMsg",	"Generall Error...Please Try Again" );
					res.sendRedirect("../jsp/admin/batterystore/laptopbatterywale/editlaptopbattery.jsp");
				}
			catch(Exception e)
				{
					//LogLevel.ERROR(0,e,"Problem Caught Exception if(add)!! "+e);
					e.printStackTrace();
					session.setAttribute("sesLaptopbatteryErrorMsg",	"General Error...Please Try Again" );
					res.sendRedirect("../jsp/admin/batterystore/laptopbatterywale/editlaptopbattery.jsp");
				}
		     }
		else if(strWhatToDo.equalsIgnoreCase("getbatterymodels"))
		  {
			String brand = (req.getParameter("brand") != null)? (req.getParameter("brand")) : "";
			try
			  {
			String batterymodel="";	

	 		ServletOutputStream out=res.getOutputStream();
			
			String strSqlQry1 = "select distinct(battery_model) from laptop_battery_details where battery_brand='"+brand+"' order by battery_model asc";
			LogLevel.DEBUG(5,new Throwable(),"strSqlQry1:"+strSqlQry1 );

			Vector BatModelVector=qm.executeQuery(strSqlQry1);
			LogLevel.DEBUG(5,new Throwable(),"BatModelVector :"+BatModelVector);

			if(BatModelVector.isEmpty())
			{ 
				out.println("<option style='font-family:Verdana;font-size:12px;color:#CCCCCC;' value='defaultss'>&lt;-&nbsp;Select Model&nbsp;-&gt;</option>");
				return;
			}
			else
			{
			for (int i=0; i<BatModelVector.size(); i++)
				{
					if(i==0)
					{
					out.print("<option style='font-family:Verdana;font-size:12px;color:#CCCCCC;' value='default'>&lt;-&nbsp;Select Model&nbsp;-&gt;</option>");
					}
					Hashtable ht1=(Hashtable)BatModelVector.get(i);
					//bat_id =String.valueOf(ht1.get("bat_id"));
					batterymodel =String.valueOf(ht1.get("battery_model"));
					out.print("<option style='background:#FFF' value='"+batterymodel+"'>"+batterymodel+"</option>"); 
				}
				//out.println(result);
				 }
				}
				catch(Exception e)
				{
					LogLevel.ERROR(0, e, "Problem Caught Exception if(add)!! " + e);
					e.printStackTrace();
				}
				return;
			   } 
		else if(strWhatToDo.equalsIgnoreCase("gebatterydetailstoupdate"))
		  {
			String brand= (req.getParameter("brand")!=null)?(req.getParameter("brand")):"";
			LogLevel.DEBUG(5,new Throwable(),"brand :"+brand );

			String model= (req.getParameter("model")!=null)?(req.getParameter("model")):"";
			LogLevel.DEBUG(5,new Throwable(),"model :"+model ); 
			
			ServletOutputStream out=res.getOutputStream();
			try
			  {  
				/* @strSqlQry3 : carries the query to Insert the user Work details into professional_detl table.*/
				String strSqlQry4 = "select battery_id,battery_warranty,battery_cellcount,voltage,watt_hr,battery_actual_price,battery_brand,battery_model,amazonlink,battery_part_no,icon_url from laptop_battery_details where battery_brand='"+brand+"' and battery_model='"+model+"'";
				LogLevel.DEBUG(5,new Throwable(),"strSqlQry4 :"+strSqlQry4 );

				Vector batterydetails=qm.executeQuery(strSqlQry4);
				LogLevel.DEBUG(5,new Throwable(),"batterydetails:"+batterydetails);
				String strRes="<table  align='center' width='100%' border='0'>";
							
				if(batterydetails==null || batterydetails.size() == 0)
				  {
					out.println("<p align='center' class='insidecontent'>No Battery details Avaliable!<a href=\"\" STYLE='TEXT-DECORATION: NONE'></p>");
					return;
				  }
				 else
				  {
				   for(int j=0; j<batterydetails.size();j++)
					 {
						Hashtable ht3=(Hashtable)batterydetails.get(j);
						String batid=String.valueOf(ht3.get("battery_id"));
						String batwar = (String)ht3.get("battery_warranty");
						String batcellcount = (String)ht3.get("battery_cellcount");
						String batvoltage = (String)ht3.get("voltage");
						String batwatts = (String)ht3.get("watt_hr");
						String batactprice = String.valueOf(ht3.get("battery_actual_price"));
						String batpartnumber = (String)ht3.get("battery_part_no");
						String stricon_url=(String)ht3.get("icon_url");
						LogLevel.DEBUG(5,new Throwable(),"stricon_url :"+stricon_url );
						String batbrand = (String)ht3.get("battery_brand");
						String batmodel = (String)ht3.get("battery_model");
						String amazonlink = (String)ht3.get("amazonlink");

							
							if(stricon_url=="" || stricon_url==null || stricon_url.equals(""))
							{
								stricon_url ="../images/noimage.jpg";
							}
							else
							{
								stricon_url = stricon_url;
							}
						

						strRes=strRes+"<tr><td align='right' class='insidecontent' width='42%'>Battery&nbsp;Warranty</td>";
						strRes=strRes+"<td align='left'><input class='insidecontent' type='text' name='batwar' value='"+batwar+"' size='30' maxlength='50'></td></tr>";
						strRes=strRes+"<tr><td align='right' class='insidecontent' width='42%'>Battery&nbsp;Cellcount<font color='red'>*</font>&nbsp;</td>";
						strRes=strRes+"<td align='left'><input class='insidecontent' type='text' name='batcellcount' value='"+batcellcount+"' size='30' maxlength='10'></td></tr>";
						strRes=strRes+"<tr><td align='right' class='insidecontent' width='42%'>Battery&nbsp;Voltage<font color='red'>*</font>&nbsp;</td>";
						strRes=strRes+"<td align='left'><input class='insidecontent' type='text' name='batvolt' value='"+batvoltage+"' size='30' maxlength='10'></td></tr>";
						strRes=strRes+"<tr><td align='right' class='insidecontent' width='42%'>Battery&nbsp;Watts&nbsp;Hours<font color='red'>*</font>&nbsp;</td>";
						strRes=strRes+"<td align='left'><input class='insidecontent' type='text' name='batwatshrs' value='"+batwatts+"' size='30' maxlength='10'></td></tr>";
						strRes=strRes+"<tr><td align='right' class='insidecontent' width='42%'>Battery&nbsp;Act&nbsp;Price<font color='red'>*</font>&nbsp;</td>";
						strRes=strRes+"<td align='left'><input class='insidecontent' type='text' name='batactprice' value='"+batactprice+"' size='30' maxlength='10'></td></tr>";
						strRes=strRes+"<tr><td align='right' class='insidecontent' width='42%'>Battery&nbsp;Part&nbsp;Number<font color='red'>*</font>&nbsp;</td>";
						strRes=strRes+"<td align='left'><input class='insidecontent' type='text' name='batpartnumber' value='"+batpartnumber+"' size='30' maxlength='100'></td></tr>";
						strRes=strRes+"<tr><td align='right' class='insidecontent' width='42%'>Battery&nbsp;Image<font color='red'>*</font>&nbsp;</td>";
						strRes=strRes+"	<td align='left'>&nbsp;<img src=\""+stricon_url+"\" bordercolor='#000000' border='1' height='150' width='250'/></tr>&nbsp;&nbsp;</tr>";
						strRes=strRes+"<tr><td width='42%'></td><td align='left' class='insidecontent' ><input type='file' name='attachment' class='top1' onkeydown='javascript:return false;' ><font class='insidecontent' width='70%'>*<u>&nbsp;Image&nbsp;formats&nbsp;</u>:&nbsp;jpg&nbsp;&&nbsp;png</td></tr>";
						strRes=strRes+"<tr><td align='right' class='insidecontent' width='42%'>Amazon&nbsp;Link<font color='red'>*</font>&nbsp;</td>";
						strRes=strRes+"<td align='left'><input class='insidecontent' type='textarea' name='amazonlink' value='"+amazonlink+"' ></td></tr>";
						
						strRes=strRes+"<tr><td align='right' width='42%'></td><td align='left'><input type='button'  onclick=\"funToUpdatbatterydetails('"+batid+"','"+batmodel+"','"+batbrand+"');\" value='Update' class='smallbutton'></td>";
						//strRes=strRes+"<td align='left' ><input type='button' onclick=\"Schhide('"+id+"');\" value='Cancel' class='smallbutton'></td></tr>";
					   }
					}
					strRes=strRes+"</table>";							
					out.println(strRes);
					return;				
				}
			   catch(IOException ioe)
			    {
					LogLevel.ERROR(0,ioe,"problem Caught IOException if(add) !! "+ioe);
					ioe.printStackTrace();
					session.setAttribute("sesErrorMsg",	"Generall Error...Please Try Again" );
					res.sendRedirect("../jsp/admin/batterystore/laptopbatterywale/editlaptopbattery.jsp");
			    }
			   catch(Exception e)
			    {
					LogLevel.ERROR(0,e,"Problem Caught Exception if(add)!! "+e);
					e.printStackTrace();
					session.setAttribute("sesErrorMsg",	"General Error...Please Try Again" );
					res.sendRedirect("../jsp/admin/batterystore/laptopbatterywale/editlaptopbattery.jsp");
			    }
		  }
		else if(strWhatToDo.equalsIgnoreCase("updatebatterydetails"))
		{
		String batwarrnty = (req.getParameter("batwarrnty")!=null)?(req.getParameter("batwarrnty")):"";
			String batcellcount = (req.getParameter("batcellcount")!=null)?(req.getParameter("batcellcount")):"";
			String batvolt = (req.getParameter("batvolt")!=null)?(req.getParameter("batvolt")):"";
			String batwatshrs = (req.getParameter("batwatshrs")!=null)?(req.getParameter("batwatshrs")):"";
			String batactprice = (req.getParameter("batactprice")!=null)?(req.getParameter("batactprice")):"";
			String batpartnumber = (req.getParameter("batpartnumber")!=null)?(req.getParameter("batpartnumber")):"";
			String amazonlink = (req.getParameter("amazonlink")!=null)?(req.getParameter("amazonlink")):"";
			String strIconPath = (req.getParameter("attachment")!=null)?(req.getParameter("attachment")):"";
			LogLevel.DEBUG(5,new Throwable(),"strIconPath:"+strIconPath);
			
			String batid = (req.getParameter("batid")!=null)?(req.getParameter("batid")):"";
			String model = (req.getParameter("model")!=null)?(req.getParameter("model")):"";
			String brand = (req.getParameter("brand")!=null)?(req.getParameter("brand")):"";

			String strVirtualPath = (propsMOPConfig.getProperty("VirtualPath")!=null)?(propsMOPConfig.getProperty("VirtualPath")).trim():"/home/mms/tomcat/webapps";
			LogLevel.DEBUG(5, new Throwable(), "strVirtualPath:" + strVirtualPath);

			String strCMSServerIP = (propsMOPConfig.getProperty("CMS_SERVER_IP")!=null)?(propsMOPConfig.getProperty("CMS_SERVER_IP")).trim():"meraopinion.com";
			LogLevel.DEBUG(5, new Throwable(), "strCMSServerIP:" + strCMSServerIP);

			String strRelativePath = (propsMOPConfig.getProperty("RelativePathLaptopBatteries")!=null)?(propsMOPConfig.getProperty("RelativePathLaptopBatteries")).trim():"/bookbattery/tmpfiles";
			LogLevel.DEBUG(5, new Throwable(), "strRelativePath:" + strRelativePath);

			double attachmentLimit = 4.0;

			int reslt=0;
			try
			{
				//String amazonlink = URLEncoder.encode(amazonlink1, "UTF-8");
				//LogLevel.DEBUG(5,new Throwable(),"amazonlink:"+amazonlink);

				/*Query to get the Admin User name and Password*/
 				String strSqlQry = "update laptop_battery_details set battery_warranty='"+batwarrnty+"',battery_cellcount='"+batcellcount+"',voltage='"+batvolt+"',watt_hr='"+batwatshrs+"',battery_actual_price='"+batactprice+"',amazonlink='"+amazonlink+"',battery_part_no='"+batpartnumber+"' where battery_id='"+batid+"'";
				LogLevel.DEBUG(5,new Throwable()," : "+strSqlQry);
				
				reslt = qm.executeUpdate(strSqlQry);

				String strSqlQry1 = "update laptop_application_chart_mapping set battery_warranty='"+batwarrnty+"',battery_cellcount='"+batcellcount+"',voltage='"+batvolt+"',watt_hr='"+batwatshrs+"',battery_actual_price='"+batactprice+"',amazonlink='"+amazonlink+"',battery_part_no='"+batpartnumber+"' where battery_brand='"+brand+"' and  battery_model='"+model+"'";
				LogLevel.DEBUG(5,new Throwable()," : "+strSqlQry1);
				
				int reslt1 = qm.executeUpdate(strSqlQry1);

					if (strIconPath.equals(""))
					{
					}
					else
					{ 
							LogLevel.DEBUG(5,new Throwable()," else: ");
							// create the local file system folders
							String FilePath = strVirtualPath+"/"+strRelativePath+"/";
							File f1 = new File(FilePath);
							if(!f1.exists())
							f1.mkdirs();

							FilePath = FilePath+brand+"/"+model;
							LogLevel.DEBUG(5,new Throwable(),"FilePath :"+FilePath);

							f1 = new File(FilePath);
							deleteFile(f1);
							if(!f1.exists())
							f1.mkdirs();

							// create a multipart object to upload files from client machine to server
							MultipartRequest multi = new MultipartRequest(req, FilePath , 15 * 1024 * 1024,new com.oreilly.servlet.multipart.DefaultFileRenamePolicy());

							// find the file path, name and extension
							strIconPath = strIconPath.replace('\\','/');
							LogLevel.DEBUG(4, new Throwable(), "battery icon path is : " +strIconPath );

							String strIconFileName = strIconPath.substring (strIconPath.lastIndexOf("/")+1,strIconPath.length());
							strIconFileName = strIconFileName.trim();
							strIconFileName= strIconFileName.replace(" ", "%20");
							LogLevel.DEBUG(5,new Throwable(),"strIconFileName is : "+strIconFileName);

							int index = strIconPath.lastIndexOf('.');
							String strExtension = strIconPath.substring( ++index );;

							// allow only image files
							if (( strExtension.equalsIgnoreCase("gif") ) || ( strExtension.equalsIgnoreCase("bmp") )||( strExtension.equalsIgnoreCase("jpg") ) ||   ( strExtension.equalsIgnoreCase("jpeg") )||( strExtension.equalsIgnoreCase("jpe") ) ||  ( strExtension.equalsIgnoreCase("png") )||( strExtension.equalsIgnoreCase("wbmp") )  )
							{
							// get the list of files attached in the req
							Enumeration files = multi.getFileNames();

							while (files.hasMoreElements())
							{
							String name = (String)files.nextElement();

							String strOrigFileName = multi.getOriginalFileName(name);
							String strenten="";
							if( strExtension.equalsIgnoreCase("JPG") || strExtension.equalsIgnoreCase("jpg"))
							{
							strenten= strOrigFileName.replace(".JPG", ".jpg");
							}
							else if( strExtension.equalsIgnoreCase("GIF") || strExtension.equalsIgnoreCase("gif"))
							{
							strenten= strOrigFileName.replace(".GIF", ".gif");
							}
							else if( strExtension.equalsIgnoreCase("BMP") || strExtension.equalsIgnoreCase("bmp"))
							{
							strenten= strOrigFileName.replace(".BMP", ".bmp");
							}
							else if( strExtension.equalsIgnoreCase("JPEG") || strExtension.equalsIgnoreCase("jpeg"))
							{
							strenten= strOrigFileName.replace(".JPEG", ".jpeg");
							}
							else if( strExtension.equalsIgnoreCase("JPE") || strExtension.equalsIgnoreCase("jpe"))
							{
							strenten= strOrigFileName.replace(".JPE", ".jpe");
							}
							else if( strExtension.equalsIgnoreCase("PNG") || strExtension.equalsIgnoreCase("png"))
							{
							strenten= strOrigFileName.replace(".PNG", ".png");
							}
							else if( strExtension.equalsIgnoreCase("WBMP") || strExtension.equalsIgnoreCase("wbmp"))
							{
							strenten= strOrigFileName.replace(".WBMP", ".wbmp");
							}

							File oldFile=new File(FilePath + "/"+strOrigFileName);
							LogLevel.DEBUG(5, new Throwable(), "oldFile: " + oldFile);
							String strNewFileName=strenten;
							//strOrigFileName= strOrigFileName.replace(".JPG", ".jpg");

							LogLevel.DEBUG(5, new Throwable(), "strNewFileName:"+strNewFileName);
							File newFile=new File(FilePath + "/"+strNewFileName);
							LogLevel.DEBUG(5, new Throwable(), "newFile: " + newFile);
							oldFile.renameTo(newFile);

							strOrigFileName= strOrigFileName.replace(" ", "%20");
							strOrigFileName= strOrigFileName.replace(".JPG", ".jpg");
							strOrigFileName= strOrigFileName.replace(".GIF", ".gif");
							strOrigFileName= strOrigFileName.replace(".BMP", ".bmp");
							strOrigFileName= strOrigFileName.replace(".JPEG", ".jpeg");
							strOrigFileName= strOrigFileName.replace(".JPE", ".jpe");
							strOrigFileName= strOrigFileName.replace(".WBMP", ".wbmp");
							strOrigFileName= strOrigFileName.replace(".PNG", ".png");
							LogLevel.DEBUG(5, new Throwable(), "strOrigFileName = " + strOrigFileName);

							if(strOrigFileName==null || strOrigFileName.equals(""))
							{
							LogLevel.DEBUG(5, new Throwable(), "File not selected");
							continue;
							}

							File f = multi.getFile(name);
							double picSize = (f.length())/(1024*1024);
							//double picSize = f.length();

							/* validating file size */
							if(picSize > attachmentLimit)
							{

							LogLevel.DEBUG(5, new Throwable(), "Attachment size exceeded");

							session.setAttribute("sesErrorMsg","<font color='#CC0000' class='vrb10'>You can upload Photo of size upto "+attachmentLimit+" MB only</font>");
							res.sendRedirect("../jsp/admin/batterystore/laptopbatterywale/addlaptopbatterywale.jsp");
							return;
							}
							else
							{


								//	String strFilePath = FilePath +"/"+strFileSysName;
								String strFilePath  = FilePath + "/"+strOrigFileName;
								LogLevel.DEBUG(5, new Throwable(), "strFilePath: " + strFilePath);


								// form the picture URL
								String strPicURL="http://"+strCMSServerIP+strRelativePath+"/"+brand+"/"+model+"/"+strOrigFileName;
								LogLevel.DEBUG(5, new Throwable(), "strPicURL : " + strPicURL);

								String updatepic = "update laptop_battery_details set icon_url='"+strPicURL+"' where battery_id='"+batid+"'";
								int updatepicreslt=qm.executeUpdate(updatepic);

								String updatepicapplicationchart = "update laptop_application_chart_mapping set icon_url='"+strPicURL+"' where battery_id='"+batid+"'";
								int updatepicapplicationchartreslt=qm.executeUpdate(updatepicapplicationchart);


						}
					}
				}
			}
		
				if(reslt <0)
				{
						LogLevel.DEBUG(1,new Throwable()," Failed to update your details! ");
						session.setAttribute("priority","1");
						session.setAttribute("sesleditbatterydetailsErrorMsg", "<font color='#FF3333' class='top1'>Failed to update Laptop battery details! </font> ");
						res.sendRedirect("../jsp/admin/batterystore/laptopbatterywale/editlaptopbattery.jsp");
						return;
					}
				   else
				    {
						LogLevel.DEBUG(1,new Throwable(),"Successfully updated your details! ");
						session.setAttribute("sesleditbatterydetailsErrorMsg", "<font color='#000000' class='top1'>Successfully Updated Laptop Battery details.!</font>");
						//session.setAttribute("sescontVector", reslt);
						res.sendRedirect("../jsp/admin/batterystore/laptopbatterywale/editlaptopbattery.jsp");
				    }
			     }
			    catch(IOException ioe)
			     {
						LogLevel.ERROR(0,ioe,"problem Caught IOException if(add) !! "+ioe);
						ioe.printStackTrace();
						session.setAttribute("sesleditbatterydetailsErrorMsg",	"Generall Error...Please Try Again" );
						res.sendRedirect("../jsp/admin/batterystore/laptopbatterywale/editlaptopbattery.jsp");
			     }
			    catch(Exception e)
			     {
						LogLevel.ERROR(0,e,"Problem Caught Exception if(add)!! "+e);
						e.printStackTrace();
						session.setAttribute("sesleditbatterydetailsErrorMsg",	"General Error...Please Try Again" );
						res.sendRedirect("../jsp/admin/batterystore/laptopbatterywale/editlaptopbattery.jsp");
			     }
		}
		 else if(strWhatToDo.equalsIgnoreCase("gebatterydetailstodelete"))
		  {
			String brand= (req.getParameter("brand")!=null)?(req.getParameter("brand")):"";
			LogLevel.DEBUG(5,new Throwable(),"brand :"+brand );

			String model= (req.getParameter("model")!=null)?(req.getParameter("model")):"";
			LogLevel.DEBUG(5,new Throwable(),"model :"+model );

			ServletOutputStream out=res.getOutputStream();
			String strRes="";
			try
			  {  
			String strSqlQry = "select battery_id,battery_brand,battery_model,battery_warranty,voltage from laptop_battery_details where battery_brand='"+brand+"' and battery_model='"+model+"' ";
			LogLevel.DEBUG(5,new Throwable(),"strSqlQry:"+strSqlQry );
			
			Vector batdeleteVector=qm.executeQuery(strSqlQry);
			LogLevel.DEBUG(5,new Throwable(),"batdeleteVector :"+batdeleteVector);

			if(batdeleteVector==null || batdeleteVector.size() == 0)
			{
				out.println("<p align='center' valign='top' class='insidecontent'><table valign='top' width='500' cellspacing='0' border='1' align='center' bgcolor='#FFFFFF' cellpadding='0'><tr><td><table valign='top' width='500' bgcolor='#FFFFFF' border='0'><tr><td class='insidecontent' align='center'>No Laptop Battery details Avaliable!</td></tr></table></td></tr></table><a href=\"\" STYLE='TEXT-DECORATION: NONE'></p>");
			}
			else
			{
				strRes=strRes+"<table border='0' align='center' bgcolor='#FFFFFF' width='700'><tr>";
				//strRes=strRes+"<td align='center'  valign='top'><input type='button' onClick=\"javascript:deleteappichatdetails();\" value='DELETE' class='button4'></td></tr></table>";
						
				strRes=strRes+"<table border='0' cellspacing='1' align='center' cellpadding='2' bgcolor='#FFFFFF' width='100%'><tr bgcolor='#2364b1'><td class='prodheading' width='20%'>Battery Brand</td><td  class='prodheading' width='20%'>Battery Model</td><td class='prodheading' width='20%'>Warranty</td><td class='prodheading' width='20%'>Voltage</td><td class='prodheading' width='20%'>DELETE</td></tr>";
			  	for(int j=0; j<batdeleteVector.size();j++)
				{
					Hashtable ht1=(Hashtable)batdeleteVector.get(j);
					String batid=String.valueOf(ht1.get("battery_id"));
					String strbatbrand=(String)ht1.get("battery_brand");
					String strbattmodel=(String)ht1.get("battery_model");
					String strwarrenty=(String)ht1.get("battery_warranty");
					String strcapacity=(String)ht1.get("voltage");
					String strwarrenty1="";
					LogLevel.DEBUG(5,new Throwable(),"batid :"+batid );
					if(strwarrenty.length()>20)
					{
						int len = strwarrenty.length();
						int totalcharInLine = 20;
						for(int i=0; i<len; i+=totalcharInLine)
						{
							String part = strwarrenty.substring(i, Math.min(len,i + totalcharInLine));
							strwarrenty1 = strwarrenty1+part+"<br>";
						}
					}
					else
					{	
						strwarrenty1 = strwarrenty;
					}

					int Jcnt=j+1;
					
					strRes=strRes+"<table bgcolor='#FFFFFF' width='100%' cellspacing='1' border='0' align='center'  cellpadding='2'><input type='checkbox' name='chkSi' value='' style='display:none'/>";
					//strRes=strRes+"<tr><td width='6%' class='table1' align='left' >"+Jcnt+"<input type='checkbox' name='chkSi' value='"+strmapid+"'/></td>";
					strRes=strRes+"<td width='20%'   class='table1 align='left'  >"+strbatbrand+"</td>";
					strRes=strRes+"<td  width='20%'  class='table1 align='left'  >"+strbattmodel+"</td>";
					strRes=strRes+"<td  width='20%'  class='table1 align='left'>"+strwarrenty1+"</td>";
					strRes=strRes+"<td  width='20%'  class='table1 align='left'  >"+strcapacity+"</td>";
					strRes=strRes+"<td  width='20%'  class='table1 align='left'  ><a href=\"javascript:deletebatterydetails('"+batid+"');\">Delete</td></tr>";
					strRes=strRes+"</table>";
					strRes=strRes+"</table>";
				} 
				out.println(strRes);
					return;				
			}			
				}
			   catch(IOException ioe)
			    {
					LogLevel.ERROR(0,ioe,"problem Caught IOException if(add) !! "+ioe);
					ioe.printStackTrace();
					session.setAttribute("sesErrorMsg",	"Generall Error...Please Try Again" );
					res.sendRedirect("../jsp/admin/batterystore/laptopbatterywale/deletelaptopbattery.jsp");
			    }
			   catch(Exception e)
			    {
					LogLevel.ERROR(0,e,"Problem Caught Exception if(add)!! "+e);
					e.printStackTrace();
					session.setAttribute("sesErrorMsg",	"General Error...Please Try Again" );
					res.sendRedirect("../jsp/admin/batterystore/laptopbatterywale/deletelaptopbattery.jsp");
			    }
		  }
		  /*This methos is used to insert vehicle details into vehicle_details*/
		else if(strWhatToDo.equalsIgnoreCase("deletebatterydetails"))
		{
				String brand= (req.getParameter("brand")!=null)?(req.getParameter("brand")):"";
				LogLevel.DEBUG(5,new Throwable(),"brand :"+brand );

				String model= (req.getParameter("model")!=null)?(req.getParameter("model")):"";
				LogLevel.DEBUG(5,new Throwable(),"model :"+model );
				String chkSi= (req.getParameter("chkSi")!=null)?(req.getParameter("chkSi")):"";
				LogLevel.DEBUG(5, new Throwable(), "chkSi :" + chkSi);

				String strRes="";
				try
				{	
					ServletOutputStream out=res.getOutputStream();
			
					String strSqlQrychk = "SELECT battery_model FROM laptop_application_chart_mapping WHERE  battery_brand='"+brand+"' and battery_model='"+model+"'";				
					LogLevel.DEBUG(5,new Throwable(),"strSqlQrychk :"+strSqlQrychk);

					Hashtable htchk = qm.getRow(strSqlQrychk);
					String bat_model=(String)htchk.get("bat_model");
					LogLevel.DEBUG(5,new Throwable(),"bat_model :"+bat_model);

					if(htchk.isEmpty())
					{ 
						String strSqlQry = "delete from laptop_battery_details where battery_id='"+chkSi+"'";
						LogLevel.DEBUG(5,new Throwable(),"strSqlQry:"+strSqlQry );

						int i=qm.executeUpdate(strSqlQry);

					if(i <0)
					{
						LogLevel.DEBUG(1,new Throwable(),"No Name exists u can continue ");
						session.setAttribute("priority","1");
						session.setAttribute("sesErrorMsg", "<font color='#00dd00' class='vrb10'></font> ");
						out.println("Failed to delete Laptop Battery details ");
					}
					else
					{
						LogLevel.DEBUG(1,new Throwable(),"");
						session.setAttribute("priority","1");
						session.setAttribute("sesErrorMsg", "<font color='#CC0000' class='vrb10'></font> ");
						//System.out.println(email);
						out.println("Successfully deleted Laptop Battery details");
					}
					}
					else
					{
						LogLevel.DEBUG(1,new Throwable(),"");
						out.println("<font color='#FF0000'>Please delete same details first in Laptop Application Chat Mapping and try Here.  Click <a href=\"/bookbattery/jsp/admin/batterystore/laptopbatterywale/deletelaptopapplicationchart.jsp\">Here</a> to go Laptop Application Chat Mapping!</font>");
						return;
					}
					
				}
				catch (Exception e)
				{
					LogLevel.ERROR(0, e, "Problem Caught Exception if(add)!! " + e);
					e.printStackTrace();
				}
				return;
				}
	}
	public static boolean deleteFile(File fileToDelete)
	{
		if (fileToDelete == null || !fileToDelete.exists()) 
		{
			return true;
		} 
		else 
		{   
			boolean result = deleteChildDirectory(fileToDelete);
			result &= fileToDelete.delete();
			return result;
		}
	}

	public static boolean deleteChildDirectory(File parent) 
	{
		if (parent == null || !parent.exists())
		return false;
		boolean result = true;
		if (parent.isDirectory()) 
		{
			File files[] = parent.listFiles();
			if (files == null) 
			{
				result = false;
			} 
			else 
			{
				for (int i = 0; i < files.length; i++) 
				{
					File file = files[i];
					if (file.getName().equals(".") || file.getName().equals(".."))
					continue;
					if (file.isDirectory())
					result &= deleteFile(file);
					else
					result &= file.delete();
				}

			}
		}
		return result;
	}
	
}