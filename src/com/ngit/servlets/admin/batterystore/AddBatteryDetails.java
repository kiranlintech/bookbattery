/***********************************************************************		
	NGIT Confidential. 
	@File Name   : AdminBatteryLoginServlet.java
	@Description : This Servlet is used to allow the operator to Login
	@Date        : 30th August 2013
******************************************************************/ 
package com.ngit.servlets.admin.batterystore; 
import com.ngit.javabean.qrymgr.QueryManager;
import com.ngit.javabean.loglevel.LogLevel;
import com.ngit.javabean.admin.mis.AddBatteryTrans;


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
public class AddBatteryDetails extends HttpServlet 
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
		if(strWhatToDo.equalsIgnoreCase("checkmodel"))
		  {
			String strbatname = (req.getParameter("batname")!=null)?(req.getParameter("batname")):"";
			LogLevel.DEBUG(5,new Throwable(),"strbatname :"+strbatname );

			String strbatbrand = (req.getParameter("batbrand")!=null)?(req.getParameter("batbrand")):"";
			LogLevel.DEBUG(5,new Throwable(),"strbatbrand :"+strbatbrand );

			String strbatmodel = (req.getParameter("batmodel")!=null)?(req.getParameter("batmodel")):"";
			LogLevel.DEBUG(5,new Throwable(),"strbatmodel :"+strbatmodel );

			ServletOutputStream out=res.getOutputStream();
			System.out.println("ajaxmodelname called");

			try
			  {
				String strSqlQry = "SELECT bat_model FROM battery_details WHERE  bat_name='"+strbatname+"'and bat_brand='"+strbatbrand+"' and bat_model='"+strbatmodel+"'";
				LogLevel.DEBUG(5,new Throwable(),"strSqlQry :"+strSqlQry);
				
				Hashtable ht = qm.getRow(strSqlQry);
				String bat_model=(String)ht.get("bat_model");
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
		/*This methos is used to insert battery details into battery_details table*/
		else if(strWhatToDo.equalsIgnoreCase("addbatterydetails"))
		  {
			ServletFileUpload servletFileUpload = null;

			String strbatname = (req.getParameter("batname")!=null)?(req.getParameter("batname")):"";
			LogLevel.DEBUG(5,new Throwable(),"strbatname :"+strbatname );

			String strbatbrand = (req.getParameter("batbrand")!=null)?(req.getParameter("batbrand")):"";
			LogLevel.DEBUG(5,new Throwable(),"strbatbrand :"+strbatbrand );

			String strbatmodel = (req.getParameter("batmodel")!=null)?(req.getParameter("batmodel")):"";
			LogLevel.DEBUG(5,new Throwable(),"strbatmodel :"+strbatmodel );

			String strbatwar = (req.getParameter("batwar")!=null)?(req.getParameter("batwar")):"";
			LogLevel.DEBUG(5,new Throwable(),"strbatwar :"+strbatwar );

			String strbatcap = (req.getParameter("batcap")!=null)?(req.getParameter("batcap")):"";
			LogLevel.DEBUG(5,new Throwable(),"strbatcap :"+strbatcap );

			//String strbatacprice = (req.getParameter("batacprice")!=null)?(req.getParameter("batacprice")):"";
			//LogLevel.DEBUG(5,new Throwable(),"strbatacprice :"+strbatacprice );

			//String strbatdiaprice = (req.getParameter("batdiaprice")!=null)?(req.getParameter("batdiaprice")):"";
			//LogLevel.DEBUG(5,new Throwable(),"strbatdiaprice :"+strbatdiaprice );

			String strbatdesc1 = (req.getParameter("batdesc")!=null)?(req.getParameter("batdesc")):"";
			LogLevel.DEBUG(5,new Throwable(),"strbatdesc1 :"+strbatdesc1 );

			String strIconPath = (req.getParameter("fileName")!=null)?(req.getParameter("fileName")):"";
			LogLevel.DEBUG(5,new Throwable(),"strIconPath :"+strIconPath );

			String strVirtualPath = (propsMOPConfig.getProperty("VirtualPath")!=null)?(propsMOPConfig.getProperty("VirtualPath")).trim():"/home/mms/tomcat/webapps";
			LogLevel.DEBUG(5, new Throwable(), "strVirtualPath:" + strVirtualPath);

			String strCMSServerIP = (propsMOPConfig.getProperty("CMS_SERVER_IP")!=null)?(propsMOPConfig.getProperty("CMS_SERVER_IP")).trim():"meraopinion.com";
			LogLevel.DEBUG(5, new Throwable(), "strCMSServerIP:" + strCMSServerIP);

			String strRelativePath = (propsMOPConfig.getProperty("RelativePathbat")!=null)?(propsMOPConfig.getProperty("RelativePathbat")).trim():"/bookbattery/tmpfiles";
			LogLevel.DEBUG(5, new Throwable(), "strRelativePath:" + strRelativePath);

			String strScriptPath = (propsMOPConfig.getProperty("THUMBNAIL_SCRIPT_PATH")!=null)?(propsMOPConfig.getProperty("THUMBNAIL_SCRIPT_PATH")).trim():"/bookbattery/tmpfiles";
			LogLevel.DEBUG(5, new Throwable(), "strScriptPath:" + strScriptPath);

			double attachmentLimit = 4.0;
			String strbatdesc ="";

				if(strbatdesc1.length()>10)
				{
				int len = strbatdesc1.length();
				int totalcharInLine = 10;
				for(int h=0; h<len; h+=totalcharInLine)
				{
				String part = strbatdesc1.substring(h, Math.min(len,h + totalcharInLine));
				strbatdesc = strbatdesc+part+"\r\n";
				}
				}
				else
				{	
				strbatdesc = strbatdesc1;
				}
				LogLevel.DEBUG(5, new Throwable(), "strbatdesc joe:" + strbatdesc);
						
			try
			  { 
				String strSqlQrybat = "SELECT bat_model FROM battery_details WHERE  bat_name='"+strbatname+"'and bat_brand='"+strbatbrand+"' and bat_model='"+strbatmodel+"'";
				LogLevel.DEBUG(5,new Throwable(),"strSqlQrybat :"+strSqlQrybat);
				
				Hashtable ht = qm.getRow(strSqlQrybat);
				String bat_model=(String)ht.get("bat_model");
				LogLevel.DEBUG(5,new Throwable(),"bat_model :"+bat_model);

				if(ht.isEmpty())
				 { 
					// create the local file system folders
					String FilePath = strVirtualPath+"/"+strRelativePath+"/";
					File f1 = new File(FilePath);
					if(!f1.exists())
						f1.mkdirs();
					
					FilePath = FilePath+strbatbrand+"/"+strbatmodel;
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
							res.sendRedirect("../jsp/admin/batterystore/battery/addbattery.jsp");
							return;
						}
						else
						{
													
							// create a thumbnail
							/*AddBatteryTrans pt = new AddBatteryTrans(qm);
							int result=pt.createThumbnail(FilePath,strOrigFileName,strScriptPath);
							LogLevel.DEBUG(5, new Throwable(), "result:" + result);
							if(result<0)
							{
								session.setAttribute("sesErrorMsg","<font color='#CC0000' class='vrb10'>Failed to create thumbnail</font>");
								res.sendRedirect("../jsp/admin/batterystore/battery/addbattery.jsp");
								return;
							}*/
							
							String strFilePath  = FilePath + "/"+strOrigFileName;
							LogLevel.DEBUG(5, new Throwable(), "strFilePath: " + strFilePath);


							// form the picture URL
							String strPicURL="http://"+strCMSServerIP+strRelativePath+"/"+strbatbrand+"/"+strbatmodel+"/"+strOrigFileName;
							LogLevel.DEBUG(5, new Throwable(), "strPicURL : " + strPicURL);

							String strSqlQry = "insert into battery_details(bat_id,bat_name,bat_model,bat_brand,bat_warranty,bat_capacity,bat_act_price,bat_witbat_price,description,icon_name,icon_url,creation_date)values(NULL,'"+strbatname+"','"+strbatmodel+"','"+strbatbrand+"','"+strbatwar+"','"+strbatcap+"','0','0','"+strbatdesc+"','"+strIconFileName+"','"+strPicURL+"',now())";
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
						session.setAttribute("sesbatErrorMsg", "<font color='#FF3333' class='top1'>Failed to add battery details! </font> ");
						res.sendRedirect("../jsp/admin/batterystore/battery/addbattery.jsp");
						return;
					}
				   else
				    {
						LogLevel.DEBUG(1,new Throwable(),"Successfully updated your details! ");
						session.setAttribute("sesbatErrorMsg", "<font color='#000000' class='top1'>Successfully added battery details!</font>");
						//session.setAttribute("sescontVector", reslt);
						res.sendRedirect("../jsp/admin/batterystore/battery/addbattery.jsp");
				    }
						
				 }
				 else
				  {
					LogLevel.DEBUG(1,new Throwable(),"Successfully updated your details! ");
					session.setAttribute("sesbatErrorMsg", "<font color='#000000' class='top1'>Already added battery for Brand "+strbatbrand+" and Model "+strbatmodel+".</font>");
					//session.setAttribute("sescontVector", reslt);
					res.sendRedirect("../jsp/admin/batterystore/battery/addbattery.jsp");

			     }
			   }
			    catch(IOException ioe)
			     {
						LogLevel.ERROR(0,ioe,"problem Caught IOException if(add) !! "+ioe);
						ioe.printStackTrace();
						session.setAttribute("sesbatErrorMsg",	"Generall Error...Please Try Again" );
						res.sendRedirect("../jsp/admin/batterystore/battery/addbattery.jsp");
			     }
			    catch(Exception e)
			     {
						LogLevel.ERROR(0,e,"Problem Caught Exception if(add)!! "+e);
						e.printStackTrace();
						session.setAttribute("sesbatErrorMsg",	"General Error...Please Try Again" );
						res.sendRedirect("../jsp/admin/batterystore/battery/addbattery.jsp");
			     }
		    }
		else if(strWhatToDo.equalsIgnoreCase("getbatterybrands"))
		  {
			String keyword = (req.getParameter("keyword")!=null)?(req.getParameter("keyword")):"";
			LogLevel.DEBUG(5,new Throwable(),"keyword :"+keyword );
			try
			  {
				String strSqlQry ="select distinct(bat_brand) from battery_details";
				LogLevel.DEBUG(5, new Throwable(), "strSqlQry :" + strSqlQry);

				Vector BatteryBrandVector=qm.executeQuery(strSqlQry);
				LogLevel.DEBUG(5,new Throwable(),"BatteryBrandVector:"+BatteryBrandVector );
				if(BatteryBrandVector.isEmpty())
				{
					LogLevel.DEBUG(1,new Throwable(),"Failed to fetch categories ");
					session.setAttribute("priority","1");
					session.setAttribute("sesadminloginErrorMsg", "<font color='#00dd00' class='vrb10'>Failed to Battery price! </font> ");
					res.sendRedirect("../jsp/admin/batterystore/battery/batteryprice.jsp");
					return;
				}
				else
				{
					if(keyword.equals("editbattery"))
					{
					LogLevel.DEBUG(1, new Throwable(),"Succesfully Fetched Categories");
					session.setAttribute("sesbatterybrandsvector", BatteryBrandVector);
					session.setAttribute("sesErrorMsgss","<font color='#CC0000' class='vrb10'>Successfully Fetched batteryprices.</font> ");
					res.sendRedirect("../jsp/admin/batterystore/battery/editbatterydetails.jsp?keyword="+keyword);
					return;
					}
					else if(keyword.equals("deletebattery"))
					{
					LogLevel.DEBUG(1, new Throwable(),"Succesfully Fetched Categories");
					session.setAttribute("sesbatterybrandsvector", BatteryBrandVector);
					session.setAttribute("sesErrorMsgss","<font color='#CC0000' class='vrb10'>Successfully Fetched batteryprices.</font> ");
					res.sendRedirect("../jsp/admin/batterystore/battery/deletebatterydetails.jsp?keyword="+keyword);
					return;
					}
					else
					{
					LogLevel.DEBUG(1, new Throwable(),"Succesfully Fetched Categories");
					session.setAttribute("sesbatterybrandsvector", BatteryBrandVector);
					session.setAttribute("sesErrorMsgss","<font color='#CC0000' class='vrb10'>Successfully Fetched batteryprices.</font> ");
					res.sendRedirect("../jsp/admin/batterystore/battery/batteryprice.jsp");
					return;
					}
				}
				}
			catch(IOException ioe)
				{
					//LogLevel.ERROR(0,ioe,"problem Caught IOException if(add) !! "+ioe);
					ioe.printStackTrace();
					session.setAttribute("sesErrorMsg",	"Generall Error...Please Try Again" );
					res.sendRedirect("../jsp/admin/batterystore/battery/batteryprice.jsp");
				}
			catch(Exception e)
				{
					//LogLevel.ERROR(0,e,"Problem Caught Exception if(add)!! "+e);
					e.printStackTrace();
					session.setAttribute("sesErrorMsg",	"General Error...Please Try Again" );
					res.sendRedirect("../jsp/admin/batterystore/battery/batteryprice.jsp");
				}
		     }
		else if(strWhatToDo.equalsIgnoreCase("getbatterymodels"))
		  {
			String strbatbrand  = (req.getParameter("batbrand")!=null)?req.getParameter("batbrand"):"";
			LogLevel.DEBUG(5,new Throwable(),"strbatbrand :"+strbatbrand );
			
			String result="";
			try
			  {
					ServletOutputStream out = res.getOutputStream();

				String strSqlQry1 = "select distinct(bat_model) from battery_details where bat_brand='"+strbatbrand+"'";
				LogLevel.DEBUG(5,new Throwable(),"strSqlQry1:"+strSqlQry1 );
			
				Vector modelVector=qm.executeQuery(strSqlQry1);
				LogLevel.DEBUG(5,new Throwable(),"modelVector :"+modelVector);
				
				if( modelVector==null || modelVector.size() == 0)
					{
						LogLevel.DEBUG(1, new Throwable(),"Failed to fetch model ");
						session.setAttribute("priority", "1");
						session.setAttribute("sesErrorMsg",	"<font color='#CC0000' class='vrb10'>Failed to fetch model</font> ");
						out.println("Battery models are not available under the selected BatteryBrand");
					}
					else
					{
						for (int i = 0; i < modelVector.size(); i++)
						{
							Hashtable ht = (Hashtable)modelVector.get(i);
							String model = (String)ht.get("bat_model");
							int id=0;
							if (result.equals(""))
							result = id + ":" + model;
							else
							result = result + "|" + id + ":" + model;
						}
						out.println(result);
					 }
					}
					catch(Exception e)
					{
						LogLevel.ERROR(0, e, "Problem Caught Exception if(add)!! " + e);
						e.printStackTrace();
					}
					return;
				   } 
		
		else if(strWhatToDo.equalsIgnoreCase("getmodel"))
		  {
			String brands = (req.getParameter("brands") != null)? (req.getParameter("brands")) : "";
			String result="";
			try
			  {
			String bat_model="";	

	 		ServletOutputStream out=res.getOutputStream();
			
			String strSqlQry ="select distinct(bat_model) from batteryprice where bat_brand='"+brands+"' order by bat_model asc";
			LogLevel.DEBUG(5, new Throwable(), "strSqlQry :" + strSqlQry);

			Vector BatteryVector=qm.executeQuery(strSqlQry);
			LogLevel.DEBUG(5,new Throwable(),"BatteryVector:"+BatteryVector );

			if(BatteryVector.isEmpty())
			{ 
				out.println("<option style='font-family:Verdana;font-size:12px;color:#CCCCCC;' value='defaultss'>&lt;-&nbsp;&nbsp; Select Model &nbsp;&nbsp;-&gt;</option>");
				return;
			}
			else
			{
			for (int i=0; i<BatteryVector.size(); i++)
				{
					if(i==0)
					{
					out.print("<option style='font-family:Verdana;font-size:12px;color:#CCCCCC;' value='default'>&lt;-&nbsp;&nbsp; Select Model &nbsp;&nbsp;-&gt;</option>");
					}
					Hashtable ht1=(Hashtable)BatteryVector.get(i);
					//bat_id =String.valueOf(ht1.get("bat_id"));
					bat_model =String.valueOf(ht1.get("bat_model"));
					out.print("<option style='background:#FFF' value='"+bat_model+"'>"+bat_model+"</option>"); 
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

			String strbatmodel= (req.getParameter("model")!=null)?(req.getParameter("model")):"";
			LogLevel.DEBUG(5,new Throwable(),"strbatmodel :"+strbatmodel );
			
			String[] batemodel = strbatmodel.split(",");
			String moid = batemodel[0];
			String model = batemodel[1];

			ServletOutputStream out=res.getOutputStream();
			try
			  {  
				/* @strSqlQry3 : carries the query to Insert the user Work details into professional_detl table.*/
				String strSqlQry4 = "select bat_id,bat_warranty,bat_capacity,bat_model,bat_brand,icon_url from battery_details where bat_brand='"+brand+"' and bat_model='"+model+"'";
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
						String batid=String.valueOf(ht3.get("bat_id"));
						String batwar = (String)ht3.get("bat_warranty");
						String batcap = (String)ht3.get("bat_capacity");
						String batmodel = (String)ht3.get("bat_model");
						String batbrand = (String)ht3.get("bat_brand");
						String stricon_url=(String)ht3.get("icon_url");
						LogLevel.DEBUG(5,new Throwable(),"stricon_url :"+stricon_url );

						if(stricon_url=="" || stricon_url==null || stricon_url.equals(""))
							{
								stricon_url ="../images/noimage.jpg";
							}
							else
							{
								stricon_url = stricon_url;
							}
						
						strRes=strRes+"<tr><td align='right' class='insidecontent' width='42%'>Battery&nbsp;Warranty<font color='red'>*</font>&nbsp;</td>";
						strRes=strRes+"<td align='left'><input class='insidecontent' type='text' name='batwar' value='"+batwar+"' size='30' maxlength='100'></td></tr>";
						strRes=strRes+"<tr><td align='right' class='insidecontent' width='42%'>Battery&nbsp;Capacity<font color='red'>*</font>&nbsp;</td>";
						strRes=strRes+"<td align='left'><input class='insidecontent' type='text' name='batcap' value='"+batcap+"' size='30' maxlength='10'></td></tr>";
						strRes=strRes+"<tr><td align='right' class='insidecontent' width='42%'>Battery&nbsp;Image<font color='red'>*</font>&nbsp;</td>";
						strRes=strRes+"	<td align='left'>&nbsp;<img src=\""+stricon_url+"\" bordercolor='#000000' border='1' height='150' width='250'/></tr>&nbsp;&nbsp;</tr>";
						strRes=strRes+"<tr><td width='42%'></td><td align='left' class='insidecontent' ><input type='file' name='attachment' class='top1' onkeydown='javascript:return false;' ><font class='insidecontent' width='70%'>*<u>&nbsp;Image&nbsp;formats&nbsp;</u>:&nbsp;jpg&nbsp;&&nbsp;png</td></tr>";
						strRes=strRes+"<tr><td align='right' width='42%'></td><td align='left'><input type='button'  onclick=\"funToUpdatbatterydetails('"+batid+"','"+batmodel+"','"+batbrand+"');\" value='Update' class='smallbutton'></td></tr>";
					
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
					res.sendRedirect("../jsp/admin/batterystore/battery/editbatterydetails.jsp");
			    }
			   catch(Exception e)
			    {
					LogLevel.ERROR(0,e,"Problem Caught Exception if(add)!! "+e);
					e.printStackTrace();
					session.setAttribute("sesErrorMsg",	"General Error...Please Try Again" );
					res.sendRedirect("../jsp/admin/batterystore/battery/editbatterydetails.jsp");
			    }
		  }

		  else if(strWhatToDo.equalsIgnoreCase("gebatterydetailstodelete"))
		  {
			String brand= (req.getParameter("brand")!=null)?(req.getParameter("brand")):"";
			LogLevel.DEBUG(5,new Throwable(),"brand :"+brand );

			String strbatmodel= (req.getParameter("model")!=null)?(req.getParameter("model")):"";
			LogLevel.DEBUG(5,new Throwable(),"strbatmodel :"+strbatmodel );
			
			String[] batemodel = strbatmodel.split(",");
			String moid = batemodel[0];
			String model = batemodel[1];

			ServletOutputStream out=res.getOutputStream();
			String strRes="";
			try
			  {  
			String strSqlQry = "select bat_id,bat_brand,bat_model,bat_warranty,bat_capacity from battery_details where bat_brand='"+brand+"' and bat_model='"+model+"' ";
			LogLevel.DEBUG(5,new Throwable(),"strSqlQry:"+strSqlQry );
			
			Vector batdeleteVector=qm.executeQuery(strSqlQry);
			LogLevel.DEBUG(5,new Throwable(),"batdeleteVector :"+batdeleteVector);

			if(batdeleteVector==null || batdeleteVector.size() == 0)
			{
				out.println("<p align='center' valign='top' class='insidecontent'><table valign='top' width='500' cellspacing='0' border='1' align='center' bgcolor='#FFFFFF' cellpadding='0'><tr><td><table valign='top' width='500' bgcolor='#FFFFFF' border='0'><tr><td class='insidecontent' align='center'>No Battery details Avaliable!</td></tr></table></td></tr></table><a href=\"\" STYLE='TEXT-DECORATION: NONE'></p>");
			}
			else
			{
				strRes=strRes+"<table border='0' align='center' bgcolor='#FFFFFF' width='700'><tr>";
				//strRes=strRes+"<td align='center'  valign='top'><input type='button' onClick=\"javascript:deleteappichatdetails();\" value='DELETE' class='button4'></td></tr></table>";
						
				strRes=strRes+"<table border='0' cellspacing='1' align='center' cellpadding='2' bgcolor='#FFFFFF' width='100%'><tr bgcolor='#2364b1'><td class='prodheading' width='20%'>Battery Brand</td><td  class='prodheading' width='20%'>Battery Model</td><td class='prodheading' width='20%'>Warranty</td><td class='prodheading' width='20%'>Capacity</td><td class='prodheading' width='20%'>DELETE</td></tr>";
			  	for(int j=0; j<batdeleteVector.size();j++)
				{
					Hashtable ht1=(Hashtable)batdeleteVector.get(j);
					String batid=String.valueOf(ht1.get("bat_id"));
					String strbatbrand=(String)ht1.get("bat_brand");
					String strbattmodel=(String)ht1.get("bat_model");
					String strwarrenty=(String)ht1.get("bat_warranty");
					String strcapacity=(String)ht1.get("bat_capacity");
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
					strRes=strRes+"<td  width='20%'  class='table1 align='left'  ><a href=\"javascript:deletebatterydetails('"+batid+"','"+strbatbrand+"','"+strbattmodel+"');\">Delete</td></tr>";
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
					res.sendRedirect("../jsp/admin/batterystore/battery/deletebatterydetails.jsp");
			    }
			   catch(Exception e)
			    {
					LogLevel.ERROR(0,e,"Problem Caught Exception if(add)!! "+e);
					e.printStackTrace();
					session.setAttribute("sesErrorMsg",	"General Error...Please Try Again" );
					res.sendRedirect("../jsp/admin/batterystore/battery/deletebatterydetails.jsp");
			    }
		  }
		   /*This methos is used to insert vehicle details into vehicle_details*/
		else if(strWhatToDo.equalsIgnoreCase("updatebatterydetails"))
		{
			String batwarrnty = (req.getParameter("batwarrnty")!=null)?(req.getParameter("batwarrnty")):"";
			LogLevel.DEBUG(5,new Throwable(),"batwarrnty :"+batwarrnty );

			String batcapcity = (req.getParameter("batcapcity")!=null)?(req.getParameter("batcapcity")):"";
			LogLevel.DEBUG(5,new Throwable(),"batcapcity :"+batcapcity );

			String batid = (req.getParameter("batid")!=null)?(req.getParameter("batid")):"";
			LogLevel.DEBUG(5,new Throwable(),"batid :"+batid );

			String model = (req.getParameter("model")!=null)?(req.getParameter("model")):"";
			LogLevel.DEBUG(5,new Throwable(),"model :"+model );

			String brand = (req.getParameter("brand")!=null)?(req.getParameter("brand")):"";
			LogLevel.DEBUG(5,new Throwable(),"brand :"+brand );

			String strIconPath = (req.getParameter("attachment")!=null)?(req.getParameter("attachment")):"";
			LogLevel.DEBUG(5,new Throwable(),"strIconPath:"+strIconPath);

			String strVirtualPath = (propsMOPConfig.getProperty("VirtualPath")!=null)?(propsMOPConfig.getProperty("VirtualPath")).trim():"/home/mms/tomcat/webapps";
			LogLevel.DEBUG(5, new Throwable(), "strVirtualPath:" + strVirtualPath);

			String strCMSServerIP = (propsMOPConfig.getProperty("CMS_SERVER_IP")!=null)?(propsMOPConfig.getProperty("CMS_SERVER_IP")).trim():"meraopinion.com";
			LogLevel.DEBUG(5, new Throwable(), "strCMSServerIP:" + strCMSServerIP);

			String strRelativePath = (propsMOPConfig.getProperty("RelativePathbat")!=null)?(propsMOPConfig.getProperty("RelativePathbat")).trim():"/bookbattery/tmpfiles";
			LogLevel.DEBUG(5, new Throwable(), "strRelativePath:" + strRelativePath);

			String strScriptPath = (propsMOPConfig.getProperty("THUMBNAIL_SCRIPT_PATH")!=null)?(propsMOPConfig.getProperty("THUMBNAIL_SCRIPT_PATH")).trim():"/bookbattery/tmpfiles";
			LogLevel.DEBUG(5, new Throwable(), "strScriptPath:" + strScriptPath);

			double attachmentLimit = 4.0;
			int reslt=0;

			try
			{
				/*Query to get the Admin User name and Password*/
 				String strSqlQry = "update battery_details set bat_warranty='"+batwarrnty+"',bat_capacity='"+batcapcity+"' where bat_id='"+batid+"'";
				LogLevel.DEBUG(5,new Throwable()," : "+strSqlQry);
				
				reslt = qm.executeUpdate(strSqlQry);

				String strSqlQry1 = "update application_chat_mapping set bat_warranty='"+batwarrnty+"',bat_capacity='"+batcapcity+"' where bat_brand='"+brand+"' and  bat_model='"+model+"'";
				LogLevel.DEBUG(5,new Throwable()," : "+strSqlQry1);
				
				int reslt1 = qm.executeUpdate(strSqlQry1);

				String strSqlQry2 = "update batteryprice set bat_capacity='"+batcapcity+"' where bat_brand='"+brand+"' and  bat_model='"+model+"'";
				LogLevel.DEBUG(5,new Throwable()," : "+strSqlQry2);
				
				int reslt2 = qm.executeUpdate(strSqlQry2);
				
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
							if (( strExtension.equalsIgnoreCase("gif")) || ( strExtension.equalsIgnoreCase("bmp") )||( strExtension.equalsIgnoreCase("jpg") ) ||   ( strExtension.equalsIgnoreCase("jpeg") ) || ( strExtension.equalsIgnoreCase("jpe") ) ||  ( strExtension.equalsIgnoreCase("png") )||( strExtension.equalsIgnoreCase("wbmp") ))
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
											// create a thumbnail
											/*AddBatteryTrans pt = new AddBatteryTrans(qm);
											int result=pt.createThumbnail(FilePath,strOrigFileName,strScriptPath);
											LogLevel.DEBUG(5, new Throwable(), "result:" + result);
											if(result<0)
											{
												session.setAttribute("sesErrorMsg","<font color='#CC0000' class='vrb10'>Failed to create thumbnail</font>");
												res.sendRedirect("../jsp/admin/batterystore/battery/editbatterydetails.jsp");
												return;
											}	*/					
											String strFilePath  = FilePath + "/"+strOrigFileName;
											LogLevel.DEBUG(5, new Throwable(), "strFilePath: " + strFilePath);
											// form the picture URL
											String strPicURL="http://"+strCMSServerIP+strRelativePath+"/"+brand+"/"+model+"/"+strOrigFileName;
											LogLevel.DEBUG(5, new Throwable(), "strPicURL : " + strPicURL);

											String updatepic = "update battery_details set icon_url='"+strPicURL+"' where bat_id='"+batid+"'";
											int updatepicreslt=qm.executeUpdate(updatepic);

											String updatepicapplicationchart = "update application_chat_mapping set icon_url='"+strPicURL+"' where bat_id='"+batid+"'";
											int updatepicapplicationchartreslt=qm.executeUpdate(updatepicapplicationchart);
								}
							}
						}
					}		
					if(reslt <0)
					{
						LogLevel.DEBUG(1,new Throwable()," Failed to update your details! ");
						session.setAttribute("priority","1");
						session.setAttribute("sesleditbatterydetailsErrorMsg", "<font color='#FF3333' class='top1'>Failed to updat battery details! </font> ");
						res.sendRedirect("../jsp/admin/batterystore/battery/editbatterydetails.jsp");
						return;
					}
					else
					{
						LogLevel.DEBUG(1,new Throwable(),"Successfully updated your details! ");
						session.setAttribute("sesleditbatterydetailsErrorMsg", "<font color='#000000' class='top1'>Successfully Updated Battery details.!</font>");
						//session.setAttribute("sescontVector", reslt);
						res.sendRedirect("../jsp/admin/batterystore/battery/editbatterydetails.jsp");
					}
			 }
			catch(IOException ioe)
			 {
					LogLevel.ERROR(0,ioe,"problem Caught IOException if(add) !! "+ioe);
					ioe.printStackTrace();
					session.setAttribute("sesleditbatterydetailsErrorMsg",	"Generall Error...Please Try Again" );
					res.sendRedirect("../jsp/admin/batterystore/battery/editbatterydetails.jsp");
			 }
			catch(Exception e)
			 {
					LogLevel.ERROR(0,e,"Problem Caught Exception if(add)!! "+e);
					e.printStackTrace();
					session.setAttribute("sesleditbatterydetailsErrorMsg",	"General Error...Please Try Again" );
					res.sendRedirect("../jsp/admin/batterystore/battery/editbatterydetails.jsp");
			 }
		}
		/*This methos is used to insert vehicle details into vehicle_details*/
		else if(strWhatToDo.equalsIgnoreCase("deletebatterydetails"))
		{
			String chkSi= (req.getParameter("chkSi")!=null)?(req.getParameter("chkSi")):"";
			LogLevel.DEBUG(5, new Throwable(), "chkSi :" + chkSi);

			String batterybrand= (req.getParameter("batterybrand")!=null)?(req.getParameter("batterybrand")):"";
			LogLevel.DEBUG(5, new Throwable(), "batterybrand :" + batterybrand);

			String batterymodel= (req.getParameter("batterymodel")!=null)?(req.getParameter("batterymodel")):"";
			LogLevel.DEBUG(5, new Throwable(), "batterymodel :" + batterymodel);

			String strRes="";
			try
			{	
				ServletOutputStream out=res.getOutputStream();

				String strSqlQrychk = "SELECT bat_model FROM application_chat_mapping WHERE  bat_brand='"+batterybrand+"' and bat_model='"+batterymodel+"'";				
				LogLevel.DEBUG(5,new Throwable(),"strSqlQrychk :"+strSqlQrychk);
					
					Hashtable htchk = qm.getRow(strSqlQrychk);
					String bat_model=(String)htchk.get("bat_model");
					LogLevel.DEBUG(5,new Throwable(),"bat_model :"+bat_model);

					if(htchk.isEmpty())
					{ 
						String strSqlQry = "delete from battery_details where bat_id='"+chkSi+"'";
						LogLevel.DEBUG(5,new Throwable(),"strSqlQry:"+strSqlQry );
						int i=qm.executeUpdate(strSqlQry);

						String strSqlQrybp = "delete from batteryprice where bat_brand='"+batterybrand+"' and bat_model='"+batterymodel+"'";
						LogLevel.DEBUG(5,new Throwable(),"strSqlQrybp:"+strSqlQrybp );
						int bp=qm.executeUpdate(strSqlQrybp);
						if(i <0)
						{
							LogLevel.DEBUG(1,new Throwable(),"No Name exists u can continue ");
							session.setAttribute("priority","1");
							session.setAttribute("sesErrorMsg", "<font color='#00dd00' class='vrb10'></font> ");
							out.println("Failed to delete Battery details ");
						}
						else
						{
							LogLevel.DEBUG(1,new Throwable(),"");
							session.setAttribute("priority","1");
							session.setAttribute("sesErrorMsg", "<font color='#CC0000' class='vrb10'></font> ");
							//System.out.println(email);
							out.println("Successfully deleted Battery details");
						}
					 }
					else
					{
						LogLevel.DEBUG(1,new Throwable(),"");
						out.println("<font color='#FF0000'>Please delete same details first in Application Chat Mapping and try Here.  Click <a href=\"/bookbattery/jsp/admin/batterystore/applicationchat/editapplicationchatmapping.jsp\">Here</a> to go Application Chat Mapping!</font>");
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
		/*This methos is used to insert vehicle details into vehicle_details*/
		else if(strWhatToDo.equalsIgnoreCase("insertlocandarea"))
		{
			String state = (req.getParameter("state")!=null)?(req.getParameter("state")):"";
			LogLevel.DEBUG(5,new Throwable(),"state :"+state );

			String locaton = (req.getParameter("locaton")!=null)?(req.getParameter("locaton")):"";
			LogLevel.DEBUG(5,new Throwable(),"locaton :"+locaton );

			String area = (req.getParameter("area")!=null)?(req.getParameter("area")):"";
			LogLevel.DEBUG(5,new Throwable(),"area :"+area );
			int reslt=0;
			try
			{

				String StrSqlQryloc = "select location from location_area where location='"+locaton+"' and area='"+area+"'";
				LogLevel.DEBUG(5, new Throwable(), "StrSqlQryloc :" + StrSqlQryloc);

				Hashtable htruser = qm.getRow(StrSqlQryloc); 
				String location=String.valueOf(htruser.get("location"));
				LogLevel.DEBUG(5, new Throwable(), "location :" + location);

				if(location.equals(null) || location.equals("null") || location == null || location == "null" || location =="")
				{
				/*Query to get the Admin User name and Password*/
 				String strSqlQry = "insert into location_area(loc_area_id,location,area,state,creation_date)values(NULL,'"+locaton+"','"+area+"','"+state+"',now())";
				LogLevel.DEBUG(5,new Throwable()," : "+strSqlQry);
				
				reslt = qm.executeUpdate(strSqlQry);
				}
				else
				{
				}

				if(reslt <0)
				{
						LogLevel.DEBUG(1,new Throwable()," Failed to update your details! ");
						session.setAttribute("priority","1");
						session.setAttribute("seslocandareaErrorMsg", "<font color='#FF3333' class='top1'>Failed to add Location and Area! </font> ");
						res.sendRedirect("../jsp/admin/batterystore/locationarea/locandarea.jsp");
						return;
					}
				   else
				    {
						LogLevel.DEBUG(1,new Throwable(),"Successfully updated your details! ");
						session.setAttribute("seslocandareaErrorMsg", "<font color='#000000' class='top1'>Successfully added Location And Area!</font>");
						//session.setAttribute("sescontVector", reslt);
						res.sendRedirect("../jsp/admin/batterystore/locationarea/locandarea.jsp");
				    }
			     }
			    catch(IOException ioe)
			     {
						LogLevel.ERROR(0,ioe,"problem Caught IOException if(add) !! "+ioe);
						ioe.printStackTrace();
						session.setAttribute("seslocandareaErrorMsg",	"Generall Error...Please Try Again" );
						res.sendRedirect("../jsp/admin/batterystore/locationarea/locandarea.jsp");
			     }
			    catch(Exception e)
			     {
						LogLevel.ERROR(0,e,"Problem Caught Exception if(add)!! "+e);
						e.printStackTrace();
						session.setAttribute("seslocandareaErrorMsg",	"General Error...Please Try Again" );
						res.sendRedirect("../jsp/admin/batterystore/locationarea/locandarea.jsp");
			     }
		}
		else if(strWhatToDo.equalsIgnoreCase("getlocation"))
		  {
			String state  = (req.getParameter("state")!=null)?req.getParameter("state"):"";
			LogLevel.DEBUG(5,new Throwable(),"state :"+state );	
			
			String keyword  = (req.getParameter("keyword")!=null)?req.getParameter("keyword"):"";
			LogLevel.DEBUG(5,new Throwable(),"keyword :"+keyword );	
			
			String tablename="";
			String locations="";
			if(keyword.equals("deletepincode"))
			  {
				tablename = "battery_pincode";
				locations ="city";
			  }
			  else
			  {
				tablename = "location_area";
				locations = "location";
			  }
			
			try
			  {
			String location="";	

	 		ServletOutputStream out=res.getOutputStream();
			
			String strSqlQry ="select distinct("+locations+") as location from "+tablename+" where state='"+state+"' order by "+locations+" asc";
			LogLevel.DEBUG(5, new Throwable(), "strSqlQry :" + strSqlQry);

			Vector LocationVector=qm.executeQuery(strSqlQry);
			LogLevel.DEBUG(5,new Throwable(),"LocationVector:"+LocationVector );

			if(LocationVector.isEmpty())
			{ 
				out.println("<option style='font-family:Verdana;font-size:12px;color:#CCCCCC;' value='defaultss'>&lt;-&nbsp;Select Location&nbsp;-&gt;</option>");
				return;
			}
			else
			{
			for (int i=0; i<LocationVector.size(); i++)
				{
					if(i==0)
					{
					out.print("<option style='font-family:Verdana;font-size:12px;color:#CCCCCC;' value='default'>&lt;-&nbsp;Select Location&nbsp;-&gt;</option>");
					}
					Hashtable ht1=(Hashtable)LocationVector.get(i);
					//bat_id =String.valueOf(ht1.get("bat_id"));
					location =String.valueOf(ht1.get("location"));
					out.print("<option style='background:#FFF' value='"+location+"'>"+location+"</option>"); 
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
		else if(strWhatToDo.equalsIgnoreCase("getlocandaraetodelete"))
		  {
			String state = (req.getParameter("state")!=null)?(req.getParameter("state")):"";
			String city = (req.getParameter("city")!=null)?(req.getParameter("city")):"";
			String keyword  = (req.getParameter("keyword")!=null)?req.getParameter("keyword"):"";
			LogLevel.DEBUG(5,new Throwable(),"keyword :"+keyword );	

			ServletOutputStream out=res.getOutputStream();
			String strRes="";
			String tablename="";
			String locations="";
			String field1="";
			String field2="";
			String sesmsg="";
			if(keyword.equals("deletepincode"))
			  {
				tablename = "battery_pincode";
				locations ="city";
				field1 = "pincodeid";
				field2 = "pincode";
				sesmsg = "pincode";
			  }
			  else
			  {
				tablename = "location_area";
				locations = "location";
				field1 = "loc_area_id";
				field2 = "area";
				sesmsg = "Area";
			  }
			try
			  {  
				String strSqlQryloc = "select "+field1+" as id,"+field2+" as locpin from "+tablename+" where state='"+state+"' and "+locations+"='"+city+"' order by "+field2+" asc";
				LogLevel.DEBUG(5,new Throwable(),"strSqlQryloc:"+strSqlQryloc );
				
				Vector LocandareaVector=qm.executeQuery(strSqlQryloc);
				LogLevel.DEBUG(5,new Throwable(),"LocandareaVector :"+LocandareaVector);

				if(LocandareaVector==null || LocandareaVector.size() == 0)
				{
					out.println("<p align='center' valign='top' class='insidecontent'><table valign='top' width='500' cellspacing='0' border='1' align='center' bgcolor='#FFFFFF' cellpadding='0'><tr><td><table valign='top' width='500' bgcolor='#FFFFFF' border='0'><tr><td class='insidecontent' align='center'>No Location and Area details Avaliable!</td></tr></table></td></tr></table><a href=\"\" STYLE='TEXT-DECORATION: NONE'></p>");
				}
				else
				{
					strRes=strRes+"<table border='0' align='center' bgcolor='#FFFFFF' width='700'><tr>";
					//strRes=strRes+"<td align='center'  valign='top'><input type='button' onClick=\"javascript:deleteappichatdetails();\" value='DELETE' class='button4'></td></tr></table>";
							
					strRes=strRes+"<table border='0' cellspacing='1' align='center' cellpadding='2' bgcolor='#FFFFFF' width='100%'><tr bgcolor='#2364b1'><td class='prodheading' width='20%'>SL NO</td><td class='prodheading' width='60%'>"+sesmsg+"</td><td class='prodheading' width='20%'>DELETE</td></tr>";
					for(int j=0; j<LocandareaVector.size();j++)
					{
						Hashtable ht1=(Hashtable)LocandareaVector.get(j);
						String strlocid=String.valueOf(ht1.get("id"));
						String strarea=(String)ht1.get("locpin");
						//LogLevel.DEBUG(5,new Throwable(),"strlocid :"+strlocid );

						int Jcnt=j+1;
						
						strRes=strRes+"<table bgcolor='#FFFFFF' width='100%' cellspacing='1' border='0' align='center'  cellpadding='2'><input type='checkbox' name='chkSi' value='' style='display:none'/>";
						//strRes=strRes+"<tr><td width='6%' class='table1' align='left' >"+Jcnt+"<input type='checkbox' name='chkSi' value='"+strmapid+"'/></td>";
						strRes=strRes+"<td width='20%'   class='table1 align='center'  >"+Jcnt+"</td>";
						strRes=strRes+"<td  width='60%'  class='table1 align='left'>"+strarea+"</td>";
						strRes=strRes+"<td  width='20%'  class='table1 align='center'  ><a href=\"javascript:deletelocandareas('"+strlocid+"');\">Delete</td></tr>";
						strRes=strRes+"</table>";
						strRes=strRes+"</table>";
					} 
					out.println(strRes);
				}				
				}
			   catch(IOException ioe)
			    {
					LogLevel.ERROR(0,ioe,"problem Caught IOException if(add) !! "+ioe);
					ioe.printStackTrace();
					session.setAttribute("sesErrorMsg",	"Generall Error...Please Try Again" );
					res.sendRedirect("../jsp/admin/batterystore/locationarea/deletelocandarea.jsp");
			    }
			   catch(Exception e)
			    {
					LogLevel.ERROR(0,e,"Problem Caught Exception if(add)!! "+e);
					e.printStackTrace();
					session.setAttribute("sesErrorMsg",	"General Error...Please Try Again" );
					res.sendRedirect("../jsp/admin/batterystore/locationarea/deletelocandarea.jsp");
			    }
		  }
		else if(strWhatToDo.equalsIgnoreCase("deletelocandarea"))
		{
		String chkSi= (req.getParameter("chkSi")!=null)?(req.getParameter("chkSi")):"";
		LogLevel.DEBUG(5, new Throwable(), "chkSi :" + chkSi);

		String keyword  = (req.getParameter("keyword")!=null)?req.getParameter("keyword"):"";
		LogLevel.DEBUG(5,new Throwable(),"keyword :"+keyword );	
		
		String tablename="";
		String field1="";
		String sesmsg="";
		if(keyword.equals("deletepincode"))
		  {
			tablename = "battery_pincode";
			field1 = "pincodeid";
			sesmsg = "Pincode";
		  }
		  else
		  {
			tablename = "location_area";
			field1 = "loc_area_id";
			sesmsg = "Area";
		  }
		String strRes="";
		try
		{	
			ServletOutputStream out=res.getOutputStream();

			String strSqlQry = "delete from "+tablename+" where "+field1+"='"+chkSi+"'";
			LogLevel.DEBUG(5,new Throwable(),"strSqlQry:"+strSqlQry );
			
			int i=qm.executeUpdate(strSqlQry);

			if(i <0)
				{
					LogLevel.DEBUG(1,new Throwable(),"Failed to delete "+sesmsg+" details");
					session.setAttribute("priority","1");
					session.setAttribute("sesErrorMsg", "<font color='#00dd00' class='vrb10'></font> ");
					out.println("Failed to delete "+sesmsg+" details! ");
				}
				else
				{
					LogLevel.DEBUG(1,new Throwable(),"");
					session.setAttribute("priority","1");
					session.setAttribute("sesErrorMsg", "<font color='#CC0000' class='vrb10'></font> ");
					//System.out.println(email);
					out.println("Successfully deleted "+sesmsg+" details!");
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