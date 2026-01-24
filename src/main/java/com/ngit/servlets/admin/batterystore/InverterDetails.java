/***********************************************************************		
	NGIT Confidential. 
	@File Name   : AdminBatteryLoginServlet.java
	@Description : This Servlet is used to allow the operator to Login
	@Date        : 30th August 2013
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
 * @author Sai Krishna Daddala.
 */
/* This class is to Admin Login. This class initializes the necessary connection pools and perform the transactions on the pools. */
public class InverterDetails extends HttpServlet 
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
		/*This methos is used to check battery models avliable or not*/
		if(strWhatToDo.equalsIgnoreCase("checkcapacity"))
		  {
			String invname = (req.getParameter("invname")!=null)?(req.getParameter("invname")):"";
			LogLevel.DEBUG(5,new Throwable(),"invname :"+invname );

			String invbrand = (req.getParameter("invbrand")!=null)?(req.getParameter("invbrand")):"";
			LogLevel.DEBUG(5,new Throwable(),"invbrand :"+invbrand );

			String invcapcity = (req.getParameter("invcapcity")!=null)?(req.getParameter("invcapcity")):"";
			LogLevel.DEBUG(5,new Throwable(),"invcapcity :"+invcapcity );

			ServletOutputStream out=res.getOutputStream();
			System.out.println("ajaxmodelname called");

			try
			  {
				String strSqlQry = "SELECT inverter_capacity FROM inverter_details WHERE  inverter_name='"+invname+"'and inverter_brand='"+invbrand+"' and inverter_capacity='"+invcapcity+"'";
				LogLevel.DEBUG(5,new Throwable(),"strSqlQry :"+strSqlQry);
				
				Hashtable ht = qm.getRow(strSqlQry);
				String inverter_capacity=(String)ht.get("inverter_capacity");
				LogLevel.DEBUG(5,new Throwable(),"inverter_capacity :"+inverter_capacity);

				if(ht.isEmpty())
				 { 
					LogLevel.DEBUG(1,new Throwable(),"No Name exists u can continue ");
					out.println("<font color='#00FF00'>Inverter Capacity Avaliable!</font>");
					return;
				 }
				else
				 {
					LogLevel.DEBUG(1,new Throwable(),"");
					out.println("<font color='#FF0000'>Inverter Capacity Already Exists!</font>");
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
		/*This methos is used to insert battery details into battery_details table*/
		else if(strWhatToDo.equalsIgnoreCase("addinverterdetails"))
		  {
			ServletFileUpload servletFileUpload = null;

			String invname = (req.getParameter("invname")!=null)?(req.getParameter("invname")):"";
			LogLevel.DEBUG(5,new Throwable(),"invname :"+invname );

			String invbrand = (req.getParameter("invbrand")!=null)?(req.getParameter("invbrand")):"";
			LogLevel.DEBUG(5,new Throwable(),"invbrand :"+invbrand );

			String invmodel = (req.getParameter("invmodel")!=null)?(req.getParameter("invmodel")):"";
			LogLevel.DEBUG(5,new Throwable(),"invmodel :"+invmodel );

			String invwarrnty = (req.getParameter("invwarrnty")!=null)?(req.getParameter("invwarrnty")):"";
			LogLevel.DEBUG(5,new Throwable(),"invwarrnty :"+invwarrnty );

			String invcapcity = (req.getParameter("invcapcity")!=null)?(req.getParameter("invcapcity")):"";
			LogLevel.DEBUG(5,new Throwable(),"invcapcity :"+invcapcity );

			String tubelights = (req.getParameter("tubelights")!=null)?(req.getParameter("tubelights")):"";
			LogLevel.DEBUG(5,new Throwable(),"tubelights :"+tubelights );
			
			String fans = (req.getParameter("fans")!=null)?(req.getParameter("fans")):"";
			LogLevel.DEBUG(5,new Throwable(),"fans :"+fans );

			String telivision = (req.getParameter("telivision")!=null)?(req.getParameter("telivision")):"";
			LogLevel.DEBUG(5,new Throwable(),"telivision :"+telivision );

			String computers = (req.getParameter("computers")!=null)?(req.getParameter("computers")):"";
			LogLevel.DEBUG(5,new Throwable(),"computers :"+computers );

			String invdesc1 = (req.getParameter("invdesc")!=null)?(req.getParameter("invdesc")):"";
			LogLevel.DEBUG(5,new Throwable(),"invdesc1 :"+invdesc1 );

			String strIconPath = (req.getParameter("fileName")!=null)?(req.getParameter("fileName")):"";
			LogLevel.DEBUG(5,new Throwable(),"strIconPath :"+strIconPath );

			String strVirtualPath = (propsMOPConfig.getProperty("VirtualPath")!=null)?(propsMOPConfig.getProperty("VirtualPath")).trim():"/home/mms/tomcat/webapps";
			LogLevel.DEBUG(5, new Throwable(), "strVirtualPath:" + strVirtualPath);

			String strCMSServerIP = (propsMOPConfig.getProperty("CMS_SERVER_IP")!=null)?(propsMOPConfig.getProperty("CMS_SERVER_IP")).trim():"meraopinion.com";
			LogLevel.DEBUG(5, new Throwable(), "strCMSServerIP:" + strCMSServerIP);

			String strRelativePath = (propsMOPConfig.getProperty("RelativePathbat")!=null)?(propsMOPConfig.getProperty("RelativePathbat")).trim():"../tmpfiles";
			LogLevel.DEBUG(5, new Throwable(), "strRelativePath:" + strRelativePath);

			double attachmentLimit = 4.0;
			String invdesc ="";

				if(invdesc1.length()>10)
				{
				int len = invdesc1.length();
				int totalcharInLine = 10;
				for(int h=0; h<len; h+=totalcharInLine)
				{
				String part = invdesc1.substring(h, Math.min(len,h + totalcharInLine));
				invdesc = invdesc+part+"\r\n";
				}
				}
				else
				{	
				invdesc = invdesc1;
				}
				LogLevel.DEBUG(5, new Throwable(), "invdesc1 joe:" + invdesc1);
						
			try
			  { 
				/*String strSqlQryinv = "SELECT inverter_capacity FROM inverter_details WHERE  inverter_brand='"+invbrand+"'and inverter_capacity='"+invcapcity+"'";
				LogLevel.DEBUG(5,new Throwable(),"strSqlQryinv :"+strSqlQryinv);
				
				Hashtable ht = qm.getRow(strSqlQryinv);
				String inverter_capacity=(String)ht.get("inverter_capacity");
				LogLevel.DEBUG(5,new Throwable(),"inverter_capacity :"+inverter_capacity);

				if(ht.isEmpty())
				{ */
					// create the local file system folders
					String FilePath = strVirtualPath+"/"+strRelativePath+"/";
					File f1 = new File(FilePath);
					if(!f1.exists())
						f1.mkdirs();
					
					FilePath = FilePath+invbrand+"/"+invcapcity;
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

							session.setAttribute("sesErrorMsg","<font color='#CC0000' class='vrb10'>You can upload image of size upto "+attachmentLimit+" MB only</font>");
							res.sendRedirect("../jsp/admin/batterystore/inverter/addinverter.jsp");
							return;
						}
						else
						{
					//	String strFilePath = FilePath +"/"+strFileSysName;
						String strFilePath  = FilePath + "/"+strOrigFileName;
						LogLevel.DEBUG(5, new Throwable(), "strFilePath: " + strFilePath);

						
						// form the picture URL
						String strPicURL="http://"+strCMSServerIP+strRelativePath+"/"+invbrand+"/"+invcapcity+"/"+strOrigFileName;
						LogLevel.DEBUG(5, new Throwable(), "strPicURL : " + strPicURL);

						String strSqlQry = "insert into inverter_details(inverter_id,inverter_name,inverter_brand,inverter_model,inverter_warranty,inverter_capacity,computer,tubelights,fans,television,description,icon_name,icon_url,creation_date,created_by)values(NULL,'"+invname+"','"+invbrand+"','"+invmodel+"','"+invwarrnty+"','"+invcapcity+"','"+computers+"','"+tubelights+"','"+fans+"','"+telivision+"','"+invdesc+"','"+strIconFileName+"','"+strPicURL+"',now(),'ngit')";
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
						session.setAttribute("sesbatErrorMsg", "<font color='#FF3333' class='top1'>Failed to add inverter details! </font> ");
						res.sendRedirect("../jsp/admin/batterystore/inverter/addinverter.jsp");
						return;
					}
				   else
				    {
						LogLevel.DEBUG(1,new Throwable(),"Successfully updated your details! ");
						session.setAttribute("sesbatErrorMsg", "<font color='#000000' class='top1'>Successfully added inverter details!</font>");
						//session.setAttribute("sescontVector", reslt);
						res.sendRedirect("../jsp/admin/batterystore/inverter/addinverter.jsp");
				    }
						
				/* }
				 else
				  {
					LogLevel.DEBUG(1,new Throwable(),"Successfully updated your details! ");
					session.setAttribute("sesbatErrorMsg", "<font color='#000000' class='top1'>Already added inverter for Brand "+invbrand+" and Capacity "+invcapcity+".</font>");
					//session.setAttribute("sescontVector", reslt);
					res.sendRedirect("../jsp/admin/batterystore/inverter/addinverter.jsp");

			     }*/
			   }
			    catch(IOException ioe)
			     {
						LogLevel.ERROR(0,ioe,"problem Caught IOException if(add) !! "+ioe);
						ioe.printStackTrace();
						session.setAttribute("sesbatErrorMsg",	"Generall Error...Please Try Again" );
						res.sendRedirect("../jsp/admin/batterystore/inverter/addinverter.jsp");
			     }
			    catch(Exception e)
			     {
						LogLevel.ERROR(0,e,"Problem Caught Exception if(add)!! "+e);
						e.printStackTrace();
						session.setAttribute("sesbatErrorMsg",	"General Error...Please Try Again" );
						res.sendRedirect("../jsp/admin/batterystore/inverter/addinverter.jsp");
			     }
		    }
		else if(strWhatToDo.equalsIgnoreCase("editordeleteinveterdetails"))
		  {
			//String keyword = (req.getParameter("keyword")!=null)?(req.getParameter("keyword")):"";
			//LogLevel.DEBUG(5,new Throwable(),"keyword :"+keyword );
			try
			  {
				String strSqlQry ="select distinct(inverter_brand) from inverter_details";
				LogLevel.DEBUG(5, new Throwable(), "strSqlQry :" + strSqlQry);

				Vector InverterBrandVector=qm.executeQuery(strSqlQry);
				LogLevel.DEBUG(5,new Throwable(),"InverterBrandVector:"+InverterBrandVector );
				if(InverterBrandVector.isEmpty())
				{
					LogLevel.DEBUG(1,new Throwable(),"Failed to fetch Inverter details! ");
					session.setAttribute("priority","1");
					session.setAttribute("sesadminloginErrorMsg", "<font color='#00dd00' class='vrb10'>Failed to Fetch Inverter Details! </font> ");
					res.sendRedirect("../jsp/admin/batterystore/inverter/editordeleteinverter.jsp");
					return;
				}
				else
				{
					LogLevel.DEBUG(1, new Throwable(),"Succesfully Fetched Inverter details");
					session.setAttribute("sesinverterbrandvector", InverterBrandVector);
					session.setAttribute("sesErrorMsgss","<font color='#CC0000' class='vrb10'>Successfully Fetched Inverter details!.</font> ");
					res.sendRedirect("../jsp/admin/batterystore/inverter/editordeleteinverter.jsp");
					return;
				}
				}
			catch(IOException ioe)
				{
					//LogLevel.ERROR(0,ioe,"problem Caught IOException if(add) !! "+ioe);
					ioe.printStackTrace();
					session.setAttribute("sesErrorMsg",	"Generall Error...Please Try Again" );
					res.sendRedirect("../jsp/admin/batterystore/inverter/editordeleteinverter.jsp");
				}
			catch(Exception e)
				{
					//LogLevel.ERROR(0,e,"Problem Caught Exception if(add)!! "+e);
					e.printStackTrace();
					session.setAttribute("sesErrorMsg",	"General Error...Please Try Again" );
					res.sendRedirect("../jsp/admin/batterystore/inverter/editordeleteinverter.jsp");
				}
		     }
		else if(strWhatToDo.equalsIgnoreCase("getinvcapacity"))
		  {
			String strbatname = (req.getParameter("invbrand") != null)? (req.getParameter("invbrand")) : "";
			try
			  {
			String invcap="";

	 		ServletOutputStream out=res.getOutputStream();
			
			String strSqlQry ="select distinct(inverter_capacity) from inverter_details where inverter_brand='"+strbatname+"' order by inverter_capacity asc";
			LogLevel.DEBUG(5, new Throwable(), "strSqlQry :" + strSqlQry);

			Vector InvCapacityVector=qm.executeQuery(strSqlQry);
			LogLevel.DEBUG(5,new Throwable(),"InvCapacityVector:"+InvCapacityVector );

			if(InvCapacityVector.isEmpty())
			{ 
				out.println("<option style='font-family:Verdana;font-size:12px;color:#CCCCCC;' value='defaultss'>&lt;-&nbsp;Select Capacity&nbsp;-&gt;</option>");
				return;
			}
			else
			{
			for (int i=0; i<InvCapacityVector.size(); i++)
				{
					if(i==0)
					{
					out.print("<option style='font-family:Verdana;font-size:12px;color:#CCCCCC;' value='default'>&lt;-&nbsp;Select Capacity&nbsp;-&gt;</option>");
					}
					Hashtable ht1=(Hashtable)InvCapacityVector.get(i);
					//bat_id =String.valueOf(ht1.get("bat_id"));
					invcap =String.valueOf(ht1.get("inverter_capacity"));
					out.print("<option style='background:#FFF' value='"+invcap+"'>"+invcap+"</option>"); 
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
		else if(strWhatToDo.equalsIgnoreCase("getinveterdetailstoupdatordelete"))
		  {
			String invbrand = (req.getParameter("invbrand")!=null)?(req.getParameter("invbrand")):"";
			LogLevel.DEBUG(5,new Throwable(),"invbrand :"+invbrand );

			String capacity = (req.getParameter("capacity")!=null)?(req.getParameter("capacity")):"";
			LogLevel.DEBUG(5,new Throwable(),"capacity :"+capacity );

			String invertermodel = (req.getParameter("invmodel")!=null)?(req.getParameter("invmodel")):"";
			LogLevel.DEBUG(5,new Throwable(),"invertermodel :"+invertermodel );

			ServletOutputStream out=res.getOutputStream();
			try
			  {  
				/* @strSqlQry3 : carries the query to Insert the user Work details into professional_detl table.*/
				String strSqlQry4 = "select inverter_id,inverter_model,inverter_warranty,computer,tubelights,fans,television from inverter_details where inverter_brand='"+invbrand+"' and inverter_capacity='"+capacity+"' and inverter_model='"+invertermodel+"'";
				LogLevel.DEBUG(5,new Throwable(),"strSqlQry4 :"+strSqlQry4 );

				Vector invcapacity=qm.executeQuery(strSqlQry4);
				LogLevel.DEBUG(5,new Throwable(),"invcapacity:"+invcapacity);
				String strRes="<table  align='center' width='100%' border='0'>";
							
				if(invcapacity==null || invcapacity.size() == 0)
				  {
					out.println("<p align='center' class='insidecontent'>No Inverter Capacity Avaliable!<a href=\"\" STYLE='TEXT-DECORATION: NONE'></p>");
					return;
				  }
				 else
				  {
				   for(int j=0; j<invcapacity.size();j++)
					 {
						Hashtable ht3=(Hashtable)invcapacity.get(j);
						String invid=String.valueOf(ht3.get("inverter_id"));
						String invmodel=String.valueOf(ht3.get("inverter_model"));
						String invwarranty=String.valueOf(ht3.get("inverter_warranty"));
						String computer=String.valueOf(ht3.get("computer"));
						String tubelights=String.valueOf(ht3.get("tubelights"));
						String fans=String.valueOf(ht3.get("fans"));
						String television=String.valueOf(ht3.get("television"));
						
						strRes=strRes+"<tr><td align='right' class='insidecontent' width='42%'>Inverter&nbsp;Model<font color='FF0000'>*</font></td>";
						strRes=strRes+"<td align='left'><input class='insidecontent' type='text' name='invmodel' value='"+invmodel+"' size='30' maxlength='50' readonly></td></tr>";
						strRes=strRes+"<tr><td align='right' class='insidecontent' width='42%'>Inverter&nbsp;Warranty<font color='FF0000'>*</font>&nbsp;</td>";
						strRes=strRes+"<td align='left'><input class='insidecontent' type='text' name='invwarrenty' value='"+invwarranty+"' size='30' maxlength='100'></td></tr>";
						strRes=strRes+"<tr><td align='right' class='insidecontent' width='42%'>Computers<font color='FF0000'>*</font></td>";
						strRes=strRes+"<td align='left'><input class='insidecontent' type='text' name='computers' value='"+computer+"' size='30' maxlength='15'></td></tr>";
						strRes=strRes+"<tr><td align='right' class='insidecontent' width='42%'>Tube&nbsp;Lights<font color='FF0000'>*</font></td>";
						strRes=strRes+"<td align='left'><input class='insidecontent' type='text' name='tubelights' value='"+tubelights+"' size='30' maxlength='15'></td></tr>";
						strRes=strRes+"<tr><td align='right' class='insidecontent' width='42%'>Fans<font color='FF0000'>*</font></td>";
						strRes=strRes+"<td align='left'><input class='insidecontent' type='text' name='fans' value='"+fans+"' size='30' maxlength='15'></td></tr>";
						strRes=strRes+"<tr><td align='right' class='insidecontent' width='42%'>TV<font color='FF0000'>*</font></td>";
						strRes=strRes+"<td align='left'><input class='insidecontent' type='text' name='tv' value='"+television+"' size='30' maxlength='15'></td></tr>";
						strRes=strRes+"<tr><td align='right' width='42%'></td><td align='left'><input type='button'  onclick=\"funToUpdateinverterdetails('"+invid+"');\" value='Update' class='smallbutton'>&nbsp;<input type='button'  onclick=\"funToDeleteInverterDetails('"+invid+"');\" value='Delete' class='smallbutton'></td>";
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
					res.sendRedirect("../jsp/admin/batterystore/inverter/editordeleteinverter.jsp");
			    }
			   catch(Exception e)
			    {
					LogLevel.ERROR(0,e,"Problem Caught Exception if(add)!! "+e);
					e.printStackTrace();
					session.setAttribute("sesErrorMsg",	"General Error...Please Try Again" );
					res.sendRedirect("../jsp/admin/batterystore/inverter/editordeleteinverter.jsp");
			    }
		  }
	 else if(strWhatToDo.equalsIgnoreCase("editinverterdetails"))
		{
			String invmodel = (req.getParameter("invmodel")!=null)?(req.getParameter("invmodel")):"";
			LogLevel.DEBUG(5,new Throwable(),"invmodel :"+invmodel );

			String invwarrnty = (req.getParameter("invwarrnty")!=null)?(req.getParameter("invwarrnty")):"";
			LogLevel.DEBUG(5,new Throwable(),"invwarrnty :"+invwarrnty );

			String tubelights = (req.getParameter("tubelights")!=null)?(req.getParameter("tubelights")):"";
			LogLevel.DEBUG(5,new Throwable(),"tubelights :"+tubelights );

			String fans = (req.getParameter("fans")!=null)?(req.getParameter("fans")):"";
			LogLevel.DEBUG(5,new Throwable(),"fans :"+fans );

			String telivision = (req.getParameter("telivision")!=null)?(req.getParameter("telivision")):"";
			LogLevel.DEBUG(5,new Throwable(),"telivision :"+telivision );

			String computers = (req.getParameter("computers")!=null)?(req.getParameter("computers")):"";
			LogLevel.DEBUG(5,new Throwable(),"computers :"+computers );

			String invid = (req.getParameter("invid")!=null)?(req.getParameter("invid")):"";
			LogLevel.DEBUG(5,new Throwable(),"invid :"+invid );
			int reslt=0;
				try
				{
					/*Query to get the Admin User name for the user and Password*/
					String strSqlQry = "update inverter_details set inverter_model='"+invmodel+"',inverter_warranty='"+invwarrnty+"',tubelights='"+tubelights+"',fans='"+fans+"',television='"+telivision+"',computer='"+computers+"' where inverter_id='"+invid+"'";
					LogLevel.DEBUG(5,new Throwable()," : "+strSqlQry);

					reslt = qm.executeUpdate(strSqlQry);

					if(reslt <0)
					{
						LogLevel.DEBUG(1,new Throwable()," Failed to update your details! ");
						session.setAttribute("priority","1");
						session.setAttribute("seseditdeleteinveterdetailsErrorMsg", "<font color='#FF3333' class='top1'>Failed to update Inverter details! </font> ");
						res.sendRedirect("../jsp/admin/batterystore/inverter/editordeleteinverter.jsp");
						return;
					}
					else
					{
						LogLevel.DEBUG(1,new Throwable(),"Successfully updated your details! ");
						session.setAttribute("seseditdeleteinveterdetailsErrorMsg", "<font color='#000000' class='top1'>Successfully Updated Inverter details.!</font>");
						//session.setAttribute("sescontVector", reslt);
						res.sendRedirect("../jsp/admin/batterystore/inverter/editordeleteinverter.jsp");
					}
				}
			    catch(IOException ioe)
				{
					LogLevel.ERROR(0,ioe,"problem Caught IOException if(add) !! "+ioe);
					ioe.printStackTrace();
					session.setAttribute("seseditdeleteinveterdetailsErrorMsg",	"Generall Error...Please Try Again" );
					res.sendRedirect("../jsp/admin/batterystore/inverter/editordeleteinverter.jsp");
				}
			    catch(Exception e)
				{
					LogLevel.ERROR(0,e,"Problem Caught Exception if(add)!! "+e);
					e.printStackTrace();
					session.setAttribute("seseditdeleteinveterdetailsErrorMsg",	"General Error...Please Try Again" );
					res.sendRedirect("../jsp/admin/batterystore/inverter/editordeleteinverter.jsp");
				}
		}
		else if(strWhatToDo.equalsIgnoreCase("deleteinverterdetails"))
		{
			String chkSi = (req.getParameter("chkSi")!=null)?(req.getParameter("chkSi")):"";
			LogLevel.DEBUG(5,new Throwable(),"chkSi :"+chkSi );
			String brand = (req.getParameter("brand")!=null)?(req.getParameter("brand")):"";
			LogLevel.DEBUG(5,new Throwable(),"brand :"+brand );
			String capacity = (req.getParameter("capacity")!=null)?(req.getParameter("capacity")):"";
			LogLevel.DEBUG(5,new Throwable(),"capacity :"+capacity );
			int reslt=0;
			int priceresult=0;
			try
			{
				String StrSqlQrygetmodel = "select inverter_model from inverter_details where inverter_id='"+chkSi+"'";
				LogLevel.DEBUG(5, new Throwable(), "StrSqlQrygetmodel :" + StrSqlQrygetmodel); 

				Hashtable htgetinvertermodel = qm.getRow(StrSqlQrygetmodel); 
				String strinvertermodel=(String)htgetinvertermodel.get("inverter_model");

				String deleteprice = "delete from inverter_price_details where inverter_brand='"+brand+"' and inverter_capacity='"+capacity+"' and inverter_model='"+strinvertermodel+"'";
				LogLevel.DEBUG(5,new Throwable()," deleteprice: "+deleteprice);
				priceresult = qm.executeUpdate(deleteprice);


				/*Query to get the Admin User name and Password*/
 				String strSqlQry = "delete from inverter_details where inverter_id='"+chkSi+"'";
				LogLevel.DEBUG(5,new Throwable()," : "+strSqlQry);
				reslt = qm.executeUpdate(strSqlQry);


				
		
					if(reslt <0)
					{
						LogLevel.DEBUG(1,new Throwable()," Failed to update your details! ");
						session.setAttribute("priority","1");
						session.setAttribute("seseditdeleteinveterdetailsErrorMsg", "<font color='#FF3333' class='top1'>Failed to delete inverter details! </font> ");
						res.sendRedirect("../jsp/admin/batterystore/inverter/editordeleteinverter.jsp");
						return;
					}
				   else
				    {
						LogLevel.DEBUG(1,new Throwable(),"Successfully updated your details! ");
						session.setAttribute("seseditdeleteinveterdetailsErrorMsg", "<font color='#000000' class='top1'>Successfully delete inverter prices.!</font>");
						//session.setAttribute("sescontVector", reslt);
						res.sendRedirect("../jsp/admin/batterystore/inverter/editordeleteinverter.jsp");
				    }
			     }
			    catch(IOException ioe)
			     {
						LogLevel.ERROR(0,ioe,"problem Caught IOException if(add) !! "+ioe);
						ioe.printStackTrace();
						session.setAttribute("seseditdeleteinveterdetailsErrorMsg",	"Generall Error...Please Try Again" );
						res.sendRedirect("../jsp/admin/batterystore/inverter/editordeleteinverter.jsp");
			     }
			    catch(Exception e)
			     {
						LogLevel.ERROR(0,e,"Problem Caught Exception if(add)!! "+e);
						e.printStackTrace();
						session.setAttribute("seseditdeleteinveterdetailsErrorMsg",	"General Error...Please Try Again" );
						res.sendRedirect("../jsp/admin/batterystore/inverter/editordeleteinverter.jsp");
			     }
		}
		else if(strWhatToDo.equalsIgnoreCase("getinverterbrands"))
		  {
			//String keyword = (req.getParameter("keyword")!=null)?(req.getParameter("keyword")):"";
			//LogLevel.DEBUG(5,new Throwable(),"keyword :"+keyword );
			try
			  {
				String strSqlQry ="select distinct(inverter_brand) from inverter_details";
				LogLevel.DEBUG(5, new Throwable(), "strSqlQry :" + strSqlQry);

				Vector InverterBrandVector=qm.executeQuery(strSqlQry);
				LogLevel.DEBUG(5,new Throwable(),"InverterBrandVector:"+InverterBrandVector );
				if(InverterBrandVector.isEmpty())
				{
					LogLevel.DEBUG(1,new Throwable(),"Failed to fetch inverter brands ");
					session.setAttribute("priority","1");
					session.setAttribute("sesadminloginErrorMsg", "<font color='#00dd00' class='vrb10'>Failed to Inverter Brands! </font> ");
					res.sendRedirect("../jsp/admin/batterystore/inverter/addinverterprice.jsp");
					return;
				}
				else
				{
					LogLevel.DEBUG(1, new Throwable(),"Succesfully Fetched Categories");
					session.setAttribute("sesinverterbrandsvector", InverterBrandVector);
					session.setAttribute("sesErrorMsgss","<font color='#CC0000' class='vrb10'>Successfully Fetched Inverter Brands.</font> ");
					res.sendRedirect("../jsp/admin/batterystore/inverter/addinverterprice.jsp");
					return;
				}
				}
			catch(IOException ioe)
				{
					//LogLevel.ERROR(0,ioe,"problem Caught IOException if(add) !! "+ioe);
					ioe.printStackTrace();
					session.setAttribute("sesErrorMsg",	"Generall Error...Please Try Again" );
					res.sendRedirect("../jsp/admin/batterystore/inverter/addinverterprice.jsp");
				}
			catch(Exception e)
				{
					//LogLevel.ERROR(0,e,"Problem Caught Exception if(add)!! "+e);
					e.printStackTrace();
					session.setAttribute("sesErrorMsg",	"General Error...Please Try Again" );
					res.sendRedirect("../jsp/admin/batterystore/inverter/addinverterprice.jsp");
				}
		     }
		else if(strWhatToDo.equalsIgnoreCase("getinvcapcity"))
		  {
			String strbrand = (req.getParameter("brand") != null)? (req.getParameter("brand")) : "";
			try
			  {
			String invcap="";	

	 		ServletOutputStream out=res.getOutputStream();
			
			String strSqlQry ="select distinct(inverter_capacity) from inverter_details where inverter_brand='"+strbrand+"' order by inverter_capacity asc";
			LogLevel.DEBUG(5, new Throwable(), "strSqlQry :" + strSqlQry);

			Vector InvcapacityVector=qm.executeQuery(strSqlQry);
			LogLevel.DEBUG(5,new Throwable(),"InvcapacityVector:"+InvcapacityVector );

			if(InvcapacityVector.isEmpty())
			{ 
				out.println("<option style='font-family:Verdana;font-size:12px;color:#CCCCCC;' value='defaultss'>&lt;-&nbsp;Select Inverter Capacity&nbsp;-&gt;</option>");
				return;
			}
			else
			{
			for (int i=0; i<InvcapacityVector.size(); i++)
				{
					if(i==0)
					{
					out.print("<option style='font-family:Verdana;font-size:12px;color:#CCCCCC;' value='default'>&lt;-&nbsp;Select Inverter Capacity Name&nbsp;-&gt;</option>");
					}
					Hashtable ht1=(Hashtable)InvcapacityVector.get(i);
					//bat_id =String.valueOf(ht1.get("bat_id"));
					invcap =String.valueOf(ht1.get("inverter_capacity"));
					out.print("<option style='background:#FFF' value='"+invcap+"'>"+invcap+"</option>"); 
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
/**Method to fetch models based on inverter brand and capacity**/
			   else if(strWhatToDo.equalsIgnoreCase("getinvmodels"))
		  {
			String strbrand = (req.getParameter("brand") != null)? (req.getParameter("brand")) : "";
			String strinvcapacity = (req.getParameter("invcapacity") != null)? (req.getParameter("invcapacity")) : "";
			try
			  {
			String inverter_model="";	

	 		ServletOutputStream out=res.getOutputStream();
			
			String strSqlQry ="select distinct(inverter_model) from inverter_details where inverter_brand='"+strbrand+"' and inverter_capacity='"+strinvcapacity+"' order by inverter_model asc";
			LogLevel.DEBUG(5, new Throwable(), "strSqlQry :" + strSqlQry);

			Vector InvcapacityVector=qm.executeQuery(strSqlQry);
			LogLevel.DEBUG(5,new Throwable(),"InvcapacityVector:"+InvcapacityVector );

			if(InvcapacityVector.isEmpty())
			{ 
				out.println("<option style='font-family:Verdana;font-size:12px;color:#CCCCCC;' value='defaultss'>&lt;-&nbsp;Select Inverter Model&nbsp;-&gt;</option>");
				return;
			}
			else
			{
			for (int i=0; i<InvcapacityVector.size(); i++)
				{
					if(i==0)
					{
					out.print("<option style='font-family:Verdana;font-size:12px;color:#CCCCCC;' value='default'>&lt;-&nbsp;Select Inverter Model&nbsp;-&gt;</option>");
					}
					Hashtable ht1=(Hashtable)InvcapacityVector.get(i);
					//bat_id =String.valueOf(ht1.get("bat_id"));
					inverter_model =String.valueOf(ht1.get("inverter_model"));
					out.print("<option style='background:#FFF' value='"+inverter_model+"'>"+inverter_model+"</option>"); 
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
		else if(strWhatToDo.equalsIgnoreCase("insertinverterprices"))
			{
			String invbrand = (req.getParameter("invbrand") != null)? (req.getParameter("invbrand")) : "";
			String invcapacity = (req.getParameter("invcapacity") != null)? (req.getParameter("invcapacity")) : "";
			String invmodel = (req.getParameter("invmodel") != null)? (req.getParameter("invmodel")) : "";

			String state = (req.getParameter("stateselected") != null)? (req.getParameter("stateselected")) : ""; 
			String city = (req.getParameter("cityselected") != null)? (req.getParameter("cityselected")) : ""; 
			
			String batacprice = (req.getParameter("batacprice") != null)? (req.getParameter("batacprice")) : "";
			String batdisprice = (req.getParameter("batdisprice") != null)? (req.getParameter("batdisprice")) : "";
			String erpprice = (req.getParameter("erpprice") != null)? (req.getParameter("erpprice")) : "";
			
			String str_city_state="";
			String strstatefinal="";
			String strcityfinal="";

			int result = 0;
			try
			{
			
			String strstate_city_Array []=city.split(",");
			LogLevel.DEBUG(5,new Throwable(),"strstate_city_Array :"+strstate_city_Array);
			LogLevel.DEBUG(5,new Throwable(),"strstate_city_Array.length :"+strstate_city_Array.length);
			
			for(int i=0;i<strstate_city_Array.length;i++)			
			{
				
			String strcity=strstate_city_Array[i];
			
			LogLevel.DEBUG(5, new Throwable(), "city :" + city);
		
		
			String strstate_citySqlQry ="select distinct(city),state from search_whereever_keywords where city ="+strcity+" order by city asc";
			LogLevel.DEBUG(5, new Throwable(), "strstate_citySqlQry :" + strstate_citySqlQry);
			
			Vector state_district_Vector=qm.executeQuery(strstate_citySqlQry);
			
			LogLevel.DEBUG(5, new Throwable(), "state_district_Vector :" + state_district_Vector);
			
			Hashtable ht=(Hashtable)state_district_Vector.get(0);
			
			strstatefinal=String.valueOf(ht.get("state"));			
			LogLevel.DEBUG(5,new Throwable(),"strstatefinal :"+strstatefinal);
		
			strcityfinal =(String)ht.get("city");			
			LogLevel.DEBUG(5,new Throwable(),"strcityfinal :"+strcityfinal);	
			
			
			String StrSqlQery1 = "select inverter_model from inverter_price_details where inverter_brand='"+invbrand+"' and inverter_capacity='"+invcapacity+"' and inverter_model='"+invmodel+"' and state='"+strstatefinal+"' and city='"+strcityfinal+"'";
			LogLevel.DEBUG(5,new Throwable(),"StrSqlQery1 :"+StrSqlQery1);

			Hashtable htapp1 = qm.getRow(StrSqlQery1);
			String invtercapacity=(String)htapp1.get("inverter_capacity");
			LogLevel.DEBUG(5,new Throwable(),"dhritima :"+htapp1.size());

			if(htapp1.isEmpty())
			{ 
			String strSqlQry = "insert into inverter_price_details(inverter_price_id,inverter_brand,inverter_capacity,state,city,inverter_actual_price,inverter_discount_price,inverter_eretailer_price,inverter_model,creation_date,created_by) values(NULL,'"+invbrand+"','"+invcapacity+"','"+strstatefinal+"','"+strcityfinal+"','"+batacprice+"','"+batdisprice+"','"+erpprice+"','"+invmodel+"',now(),'ngit')";
			//LogLevel.DEBUG(5,new Throwable(),"strSqlQry :"+strSqlQry );

			result=qm.executeUpdate(strSqlQry);
			LogLevel.DEBUG(5,new Throwable(),"result :"+result );

				/*if(result < 0)
				{
					LogLevel.DEBUG(1,new Throwable(),"Failed to insert inverter price details!");
					session.setAttribute("priority","1");
					session.setAttribute("sesinverterErrorMsg", "<font color='#ff3333' class='vrb10'>Failed to insert inverter price details! </font> ");
					res.sendRedirect("../jsp/admin/batterystore/inverter/addinverterprice.jsp");
					return;
				}
				else
				{
					LogLevel.DEBUG(1,new Throwable(),"Successfully inserted battery price details! ");
					session.setAttribute("priority","1");
					session.setAttribute("sesinverterErrorMsg", "<font color='#000000' class='vrb10'>Successfully inserted inverter price details! </font> ");
					res.sendRedirect("../jsp/admin/batterystore/inverter/addinverterprice.jsp");
					return;
					
				}*/
			}
			else
			{
				/*LogLevel.DEBUG(1,new Throwable(),"Successfully inserted battery price details! ");
				session.setAttribute("priority","1");
				session.setAttribute("sesinverterErrorMsg", "<font color='#000000' class='vrb10'>Already have prices for this Capacity! Please update ..... </font> ");
				res.sendRedirect("../jsp/admin/batterystore/inverter/addinverterprice.jsp");
				return;*/
			}
			}
			
			LogLevel.DEBUG(1,new Throwable(),"Successfully inserted battery price details! ");
			session.setAttribute("priority","1");
			session.setAttribute("sesinverterErrorMsg", "<font color='#000000' class='vrb10'>Successfully inserted inverter price details! </font> ");
			res.sendRedirect("../jsp/admin/batterystore/inverter/addinverterprice.jsp");
			return;
	
			}
			catch(IOException ioe)
			{
				//LogLevel.ERROR(0,ioe,"problem Caught IOException if(editcomplaint) !! "+ioe);
				ioe.printStackTrace();
				session.setAttribute("sesErrorMsg",	"Generall Error...Please Try Again" );
				res.sendRedirect("../jsp/admin/batterystore/inverter/addinverterprice.jsp");
			}
			catch(Exception e)
			{
				//LogLevel.ERROR(0,e,"Problem Caught Exception if(editcomplaint)!! "+e);
				e.printStackTrace();
				session.setAttribute("sesErrorMsg",	"General Error...Please Try Again" );
				res.sendRedirect("../jsp/admin/batterystore/inverter/addinverterprice.jsp");
			}
		}
		else if(strWhatToDo.equalsIgnoreCase("getstatetoedit"))
		  {
			//String keyword = (req.getParameter("keyword")!=null)?(req.getParameter("keyword")):"";
			//LogLevel.DEBUG(5,new Throwable(),"keyword :"+keyword );
			try
			  {
				String strSqlQry ="select distinct(state) from inverter_price_details order by state asc";
				LogLevel.DEBUG(5, new Throwable(), "strSqlQry :" + strSqlQry);

				Vector EditInvpriceVector=qm.executeQuery(strSqlQry);
				LogLevel.DEBUG(5,new Throwable(),"EditInvpriceVector:"+EditInvpriceVector );
				if(EditInvpriceVector.isEmpty())
				{
					LogLevel.DEBUG(1,new Throwable(),"Failed to fetch categories ");
					session.setAttribute("priority","1");
					session.setAttribute("sesadminloginErrorMsg", "<font color='#00dd00' class='vrb10'>Failed to Fetch States! </font> ");
					res.sendRedirect("../jsp/admin/batterystore/inverter/editinverterprice.jsp");
					return;
				}
				else
				{
					LogLevel.DEBUG(1, new Throwable(),"Succesfully Fetched Categories");
					session.setAttribute("sesEditInvpriceVector", EditInvpriceVector);
					session.setAttribute("sesErrorMsgss","<font color='#CC0000' class='vrb10'>Succesfully Fetched States.</font> ");
					res.sendRedirect("../jsp/admin/batterystore/inverter/editinverterprice.jsp");
					return;
				}
				}
			catch(IOException ioe)
				{
					//LogLevel.ERROR(0,ioe,"problem Caught IOException if(add) !! "+ioe);
					ioe.printStackTrace();
					session.setAttribute("sesErrorMsg",	"Generall Error...Please Try Again" );
					res.sendRedirect("../jsp/admin/batterystore/inverter/editinverterprice.jsp");
				}
			catch(Exception e)
				{
					//LogLevel.ERROR(0,e,"Problem Caught Exception if(add)!! "+e);
					e.printStackTrace();
					session.setAttribute("sesErrorMsg",	"General Error...Please Try Again" );
					res.sendRedirect("../jsp/admin/batterystore/inverter/editinverterprice.jsp");
				}
		     }
		else if(strWhatToDo.equalsIgnoreCase("getbrands"))
		  {
						
			String result="";
			try
			  {
			String invbrand="";	

	 		ServletOutputStream out=res.getOutputStream();
			
			String strSqlQry ="select distinct(inverter_brand) from inverter_price_details order by inverter_brand asc";
			LogLevel.DEBUG(5, new Throwable(), "strSqlQry :" + strSqlQry);

			Vector InvBrandVector=qm.executeQuery(strSqlQry);
			LogLevel.DEBUG(5,new Throwable(),"InvBrandVector:"+InvBrandVector );

			if(InvBrandVector.isEmpty())
			{ 
				out.println("<option style='font-family:Verdana;font-size:12px;color:#CCCCCC;' value='defaultss'>&lt;-&nbsp;&nbsp; Select Brand &nbsp;&nbsp;-&gt;</option>");
				return;
			}
			else
			{
			for (int i=0; i<InvBrandVector.size(); i++)
				{
					if(i==0)
					{
					out.print("<option style='font-family:Verdana;font-size:12px;color:#CCCCCC;' value='default'>&lt;-&nbsp;&nbsp; Select Brand &nbsp;&nbsp;-&gt;</option>");
					}
					Hashtable ht1=(Hashtable)InvBrandVector.get(i);
					//bat_id =String.valueOf(ht1.get("bat_id"));
					invbrand =String.valueOf(ht1.get("inverter_brand"));
					out.print("<option style='background:#FFF' value='"+invbrand+"'>"+invbrand+"</option>"); 
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
		else if(strWhatToDo.equalsIgnoreCase("getinverpricestoupdate"))
		  {
			String state = (req.getParameter("state")!=null)?(req.getParameter("state")):"";
			LogLevel.DEBUG(5,new Throwable(),"state :"+state );

			String city = (req.getParameter("city")!=null)?(req.getParameter("city")):"";
			LogLevel.DEBUG(5,new Throwable(),"city :"+city );

			String brand= (req.getParameter("brand")!=null)?(req.getParameter("brand")):"";
			LogLevel.DEBUG(5,new Throwable(),"brand :"+brand );

			String capacity= (req.getParameter("capacity")!=null)?(req.getParameter("capacity")):"";
			LogLevel.DEBUG(5,new Throwable(),"capacity :"+capacity );

			String invmodel= (req.getParameter("invmodel")!=null)?(req.getParameter("invmodel")):"";
			LogLevel.DEBUG(5,new Throwable(),"invmodel :"+invmodel );
			ServletOutputStream out=res.getOutputStream();
			try
			  {  
				/* @strSqlQry3 : carries the query to Insert the user Work details into professional_detl table.*/
				String strSqlQry4 = "select inverter_price_id,inverter_actual_price,inverter_discount_price,inverter_eretailer_price from inverter_price_details where state='"+state+"' and city='"+city+"' and inverter_brand='"+brand+"' and inverter_capacity='"+capacity+"' and inverter_model='"+invmodel+"'";
				LogLevel.DEBUG(5,new Throwable(),"strSqlQry4 :"+strSqlQry4 );

				Vector inverterprice=qm.executeQuery(strSqlQry4);
				LogLevel.DEBUG(5,new Throwable(),"inverterprice:"+inverterprice);
				String strRes="<table  align='center' width='100%' border='0'>";
							
				if(inverterprice==null || inverterprice.size() == 0)
				  {
					out.println("<p align='center' class='insidecontent'>No Prices are Avaliable based on selection!<a href=\"\" STYLE='TEXT-DECORATION: NONE'></p>");
					return;
				  }
				 else
				  {
				   for(int j=0; j<inverterprice.size();j++)
					 {
						Hashtable ht3=(Hashtable)inverterprice.get(j);
						String invid=String.valueOf(ht3.get("inverter_price_id"));
						String stractprice=String.valueOf(ht3.get("inverter_actual_price"));
						String strdisprice=String.valueOf(ht3.get("inverter_discount_price"));
						String strreturnprice=String.valueOf(ht3.get("inverter_eretailer_price"));
						
						strRes=strRes+"<tr><td align='right' class='insidecontent' width='42%'>Actual&nbsp;Price</td>";
						strRes=strRes+"<td align='left'><input class='insidecontent' type='text' name='actprice' value='"+stractprice+"' size='30' maxlength='7'></td></tr>";
						strRes=strRes+"<tr><td align='right' class='insidecontent' width='42%'>Discount&nbsp;Price</font>&nbsp;</td>";
						strRes=strRes+"<td align='left'><input class='insidecontent' type='text' name='disprice' value='"+strdisprice+"' size='30' maxlength='7'></td></tr>";
						strRes=strRes+"<tr><td align='right' class='insidecontent' width='42%'>E-Retailer&nbsp;Price[ERP]</td>";
						strRes=strRes+"<td align='left'><input class='insidecontent' type='text' name='erpprice' value='"+strreturnprice+"' size='30' maxlength='7'></td></tr>";
						strRes=strRes+"<tr><td align='right' width='42%'></td><td align='left'><input type='button'  onclick=\"funToUpdatinvprices('"+invid+"');\" value='Update' class='smallbutton'>&nbsp;<input type='button'  onclick=\"funToDeleteinvprices('"+invid+"');\" value='Delete' class='smallbutton'></td>";
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
					res.sendRedirect("../jsp/admin/batterystore/inverter/editinverterprice.jsp");
			    }
			   catch(Exception e)
			    {
					LogLevel.ERROR(0,e,"Problem Caught Exception if(add)!! "+e);
					e.printStackTrace();
					session.setAttribute("sesErrorMsg",	"General Error...Please Try Again" );
					res.sendRedirect("../jsp/admin/batterystore/inverter/editinverterprice.jsp");
			    }
		  }
		  else if(strWhatToDo.equalsIgnoreCase("inverterprices"))
		{
			String batacprice = (req.getParameter("batacprice")!=null)?(req.getParameter("batacprice")):"";
			LogLevel.DEBUG(5,new Throwable(),"batacprice :"+batacprice );

			String batdisprice = (req.getParameter("batdisprice")!=null)?(req.getParameter("batdisprice")):"";
			LogLevel.DEBUG(5,new Throwable(),"batdisprice :"+batdisprice );

			String erpprice = (req.getParameter("erpprice")!=null)?(req.getParameter("erpprice")):"";
			LogLevel.DEBUG(5,new Throwable(),"erpprice :"+erpprice );

			String invid = (req.getParameter("invid")!=null)?(req.getParameter("invid")):"";
			LogLevel.DEBUG(5,new Throwable(),"invid :"+invid );
			int reslt=0;
			try
			{
				/*Query to get the Admin User name and Password*/
 				String strSqlQry = "update inverter_price_details set inverter_actual_price='"+batacprice+"',inverter_discount_price='"+batdisprice+"',inverter_eretailer_price='"+erpprice+"' where inverter_price_id='"+invid+"'";
				LogLevel.DEBUG(5,new Throwable()," : "+strSqlQry);
				
				reslt = qm.executeUpdate(strSqlQry);
		
				if(reslt <0)
				{
						LogLevel.DEBUG(1,new Throwable()," Failed to update your details! ");
						session.setAttribute("priority","1");
						session.setAttribute("sesleditinverterpriceErrorMsg", "<font color='#FF3333' class='top1'>Failed to update inverter price! </font> ");
						res.sendRedirect("../jsp/admin/batterystore/inverter/editinverterprice.jsp");
						return;
					}
				   else
				    {
						LogLevel.DEBUG(1,new Throwable(),"Successfully updated your details! ");
						session.setAttribute("sesleditinverterpriceErrorMsg", "<font color='#000000' class='top1'>Successfully Updated inverter prices.!</font>");
						//session.setAttribute("sescontVector", reslt);
						res.sendRedirect("../jsp/admin/batterystore/inverter/editinverterprice.jsp");
				    }
			     }
			    catch(IOException ioe)
			     {
						LogLevel.ERROR(0,ioe,"problem Caught IOException if(add) !! "+ioe);
						ioe.printStackTrace();
						session.setAttribute("sesleditinverterpriceErrorMsg",	"Generall Error...Please Try Again" );
						res.sendRedirect("../jsp/admin/batterystore/inverter/editinverterprice.jsp");
			     }
			    catch(Exception e)
			     {
						LogLevel.ERROR(0,e,"Problem Caught Exception if(add)!! "+e);
						e.printStackTrace();
						session.setAttribute("sesleditinverterpriceErrorMsg",	"General Error...Please Try Again" );
						res.sendRedirect("../jsp/admin/batterystore/inverter/editinverterprice.jsp");
			     }
		}
		else if(strWhatToDo.equalsIgnoreCase("deleteinverterprices"))
		{
			String chkSi = (req.getParameter("chkSi")!=null)?(req.getParameter("chkSi")):"";
			LogLevel.DEBUG(5,new Throwable(),"chkSi :"+chkSi );
			int reslt=0;
			try
			{
				/*Query to get the Admin User name and Password*/
 				String strSqlQry = "delete from inverter_price_details where inverter_price_id='"+chkSi+"'";
				LogLevel.DEBUG(5,new Throwable()," : "+strSqlQry);
				reslt = qm.executeUpdate(strSqlQry);
		
				if(reslt <0)
				{
						LogLevel.DEBUG(1,new Throwable()," Failed to delete your details! ");
						session.setAttribute("priority","1");
						session.setAttribute("sesleditinverterpriceErrorMsg", "<font color='#FF3333' class='top1'>Failed to delete inverter price! </font> ");
						res.sendRedirect("../jsp/admin/batterystore/inverter/editinverterprice.jsp");
						return;
					}
				   else
				    {
						LogLevel.DEBUG(1,new Throwable(),"Successfully deleted your details! ");
						session.setAttribute("sesleditinverterpriceErrorMsg", "<font color='#000000' class='top1'>Successfully deleted inverter prices.!</font>");
						//session.setAttribute("sescontVector", reslt);
						res.sendRedirect("../jsp/admin/batterystore/inverter/editinverterprice.jsp");
				    }
			     }
			    catch(IOException ioe)
			     {
						LogLevel.ERROR(0,ioe,"problem Caught IOException if(add) !! "+ioe);
						ioe.printStackTrace();
						session.setAttribute("sesleditinverterpriceErrorMsg",	"Generall Error...Please Try Again" );
						res.sendRedirect("../jsp/admin/batterystore/inverter/editinverterprice.jsp");
			     }
			    catch(Exception e)
			     {
						LogLevel.ERROR(0,e,"Problem Caught Exception if(add)!! "+e);
						e.printStackTrace();
						session.setAttribute("sesleditinverterpriceErrorMsg",	"General Error...Please Try Again" );
						res.sendRedirect("../jsp/admin/batterystore/inverter/editinverterprice.jsp");
			     }
		}
		
		else if(strWhatToDo.equalsIgnoreCase("updateinverterstock"))
		{
			String invbrand = (req.getParameter("invbrand")!=null)?(req.getParameter("invbrand")):"";
			LogLevel.DEBUG(5,new Throwable(),"invbrand :"+invbrand ); 

			String invcapacity = (req.getParameter("invcapacity")!=null)?(req.getParameter("invcapacity")):"";
			LogLevel.DEBUG(5,new Throwable(),"invcapacity :"+invcapacity );		
			
			String state = (req.getParameter("state")!=null)?(req.getParameter("state")):"";
			LogLevel.DEBUG(5,new Throwable(),"state :"+state );
		
			String city = (req.getParameter("cityselected")!=null)?(req.getParameter("cityselected")):"";
			LogLevel.DEBUG(5,new Throwable(),"cityselected :"+city );
			
			String stock = (req.getParameter("stock")!=null)?(req.getParameter("stock")):"";
			LogLevel.DEBUG(5,new Throwable(),"stock :"+stock );


			int reslt=0;
			try
			{
				

				//String strSqlQryupdateinverterstock ="update inverter_price_details set model_stock='"+stock+"',updated_date=now() where inverter_brand='"+invbrand+"' and inverter_capacity='"+invcapacity+"' and state='"+state+"' and city='"+city+"'";
				
				String strSqlQryupdateinverterstock ="update inverter_price_details set model_stock='"+stock+"',updated_date=now() where inverter_brand='"+invbrand+"' and inverter_capacity='"+invcapacity+"' and city in("+city+")";
				LogLevel.DEBUG(5, new Throwable(), "strSqlQryupdateinverterstock :" + strSqlQryupdateinverterstock);

				
				reslt = qm.executeUpdate(strSqlQryupdateinverterstock);
		
				if(reslt <0)
				{
						LogLevel.DEBUG(1,new Throwable()," Failed to update your details! ");
						session.setAttribute("priority","1");
						session.setAttribute("sesupdateinverterstockErrorMsg", "<font color='#FF3333' class='top1'>Failed to update Stock Details! </font> ");
						res.sendRedirect("/bookbattery/jsp/admin/batterystore/stock/inverterstock.jsp");
						return;
					}
				   else
				    {
						LogLevel.DEBUG(1,new Throwable(),"Successfully updated your details! ");
						session.setAttribute("sesupdateinverterstockErrorMsg", "<font color='#000000' class='top1'>Successfully Updated Stock Details.!</font>");
						//session.setAttribute("sescontVector", reslt);
						res.sendRedirect("/bookbattery/jsp/admin/batterystore/stock/inverterstock.jsp");
				    }
			     }
			    catch(IOException ioe)
			     {
						LogLevel.ERROR(0,ioe,"problem Caught IOException if(add) !! "+ioe);
						ioe.printStackTrace();
						session.setAttribute("sesupdateinverterstockErrorMsg",	"Generall Error...Please Try Again" );
						res.sendRedirect("/bookbattery/jsp/admin/batterystore/stock/inverterstock.jsp");
			     }
			    catch(Exception e)
			     {
						LogLevel.ERROR(0,e,"Problem Caught Exception if(add)!! "+e);
						e.printStackTrace();
						session.setAttribute("sesupdateinverterstockErrorMsg",	"General Error...Please Try Again" );
						res.sendRedirect("/bookbattery/jsp/admin/batterystore/stock/inverterstock.jsp");
			     }
		}
		

		}
}