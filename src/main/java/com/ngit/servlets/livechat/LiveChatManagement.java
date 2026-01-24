/* ********************************************************************\ 
	NGIT Pvt.Ltd. Confidential. 
	@File Name   : LiveChatManagement.java  
	@Description : To insert live chat agent user details
\* *******************************************************************/ 
package com.ngit.servlets.livechat;
import com.ngit.javabean.qrymgr.QueryManager;
import com.ngit.javabean.admin.uploadimage.ImgTrans;
import com.ngit.javabean.loglevel.LogLevel;
import com.ngit.javabean.mail.*;

import com.oreilly.servlet.MultipartRequest;
import java.io.FileInputStream;
import java.util.Properties;
import java.util.Vector;
import java.util.Hashtable;
import java.util.ArrayList;
import java.util.Enumeration;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.net.URLDecoder;  
import java.net.URLEncoder; 
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.ServletOutputStream;
/**
 * @author Ajay K 
 */
public class LiveChatManagement extends HttpServlet 
{
   	private ServletContext context;
	QueryManager qm;
 
	public void init(ServletConfig config)throws ServletException
	{
		super.init(config);
		context = getServletContext();
		try
		{
			String strlivechatConfig = getInitParameter("paramMOPConfig"); 
			Properties propslivechatConfig = new Properties(); 
			FileInputStream fin1      = new FileInputStream(strlivechatConfig); 
			propslivechatConfig.load(fin1); 
			fin1.close(); 
			context.setAttribute("contextPropbatterywaleConfig",propslivechatConfig); 
			String strLogLevel = (propslivechatConfig.getProperty("LogLevel")!=null)?propslivechatConfig.getProperty("LogLevel"):"1";
			if(strLogLevel.equals(""))
			strLogLevel = "1";
			String strLogFilePath = (propslivechatConfig.getProperty("LogFilePath")!=null)?propslivechatConfig.getProperty("LogFilePath"):"/home/ngit/tomcat/webapps/bookbattery/logs/";
			if(strLogFilePath.equals(""))
			strLogFilePath = "/home/ngit/tomcat/webapps/bookbattery/logs/";
			LogLevel.setLogLevel( strLogLevel, strLogFilePath, "bookbattery.log"); 
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
    /*
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected synchronized void  doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		doGet(request,response);
	}
	protected synchronized void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session=request.getSession(true);
 		session=request.getSession(true);
		String strAdminName="admin";
		Properties propslivechatConfig = (Properties)context.getAttribute("contextPropbatterywaleConfig");
 		if(propslivechatConfig==null)
		{
			LogLevel.DEBUG(1,new Throwable(),"Properties not loaded. So reloading" );
			try
			{
				String strlivechatConfig = "/bookbattery/properties/bookbatteryconfig.properties"; 
				propslivechatConfig = new Properties(); 
				FileInputStream fin1      = new FileInputStream(strlivechatConfig); 
				propslivechatConfig.load(fin1); 
				fin1.close(); 
				context.setAttribute("contextPropbatterywaleConfig",propslivechatConfig); 
			}
 			catch(Exception e)
			{
				LogLevel.ERROR(1, e ,"Permanent Problem So Giving UP: Not able to load properties" );
				e.printStackTrace();
				return;
			}
		}
		qm = QueryManager.getInstance(propslivechatConfig) ;
 		String strWhatToDo =(request.getParameter("hidWhatToDo")!=null)?request.getParameter("hidWhatToDo"):"";
		String strScriptPath = (propslivechatConfig.getProperty("THUMBNAIL_SCRIPT_PATH3")!=null)?(propslivechatConfig.getProperty("THUMBNAIL_SCRIPT_PATH3")).trim():"/home/ngit/tomcat/webapps/bookbattery/properties/thumbnails.pl";
		String strVirtualPath = (propslivechatConfig.getProperty("VirtualPath")!=null)?(propslivechatConfig.getProperty("VirtualPath")).trim():"/home/ngit/tomcat/webapps";
		String strCMSServerIP = (propslivechatConfig.getProperty("domainname")!=null)?(propslivechatConfig.getProperty("domainname")).trim():"";
		String strRelativePath = (propslivechatConfig.getProperty("RelativePath4")!=null)?(propslivechatConfig.getProperty("RelativePath4")).trim():"";
		String SupportEmailId =  propslivechatConfig.getProperty("SupportEmailId");
		String mailTo =  propslivechatConfig.getProperty("livechatadminemail");
		String supportname = propslivechatConfig.getProperty("supportname");

		ServletOutputStream out=response.getOutputStream();
		/*this action is used to livechat agents into database*/
		if(strWhatToDo.equalsIgnoreCase("addlivechatagent"))
		{
			String firstname = (request.getParameter("firstname")!=null)?(request.getParameter("firstname")):"";
			String lastname = (request.getParameter("lastname")!=null)?(request.getParameter("lastname")):"";
			String loginname = (request.getParameter("loginname")!=null)?(request.getParameter("loginname")):"";
			String password = (request.getParameter("password")!=null)?(request.getParameter("password")):"";
			String featureName = (request.getParameter("feature")!=null)?(request.getParameter("feature")):"";
			String emailid = (request.getParameter("emailid")!=null)?(request.getParameter("emailid")):"";
			String mobilenumber = (request.getParameter("mobilenumber")!=null)?(request.getParameter("mobilenumber")):"";
			String chatlimit=(request.getParameter("chatlimit")!=null)?(request.getParameter("chatlimit")):"4";
			String fetchIconPath = (request.getParameter("imagattach")!=null)?(request.getParameter("imagattach")):"";
			double ATTACHMENT_LIMIT = 4.0;
			try
			{
				if((strAdminName==null) || (strAdminName==""))
				{
					session.setAttribute("sesErrorMsg","<font color='#CC0000' class='vrb10'>Session Expired. Please login</font>");
					response.sendRedirect("../jsp/livechatadmin/adminlogin.jsp");
					return;
				}
				else
				{
					//query used to insert chat agent user details
					String insertChatdetls = "insert into batterywale_livechat_agentlogin(agentfirst_name,agentlast_name,agent_loginname,agent_password,agent_emailid,agent_mobile_number,agent_chatlimit,creation_date,created_by) values('"+firstname+"','"+lastname+"','"+loginname+"','"+password+"','"+emailid+"','"+mobilenumber+"','"+chatlimit+"',now(),'ngit')";
					int insertresult = qm.executeUpdate(insertChatdetls);
					if(insertresult<0)
					{
						session.setAttribute("sesErrorMsg","<font color='#CC0000' class='vrb10'>Failed to insert chatagent details</font>");
						response.sendRedirect("../jsp/livechatadmin/agentregistration.jsp");
						return;
					}
					String fetchagentid="select agent_id from batterywale_livechat_agentlogin where agent_emailid='"+emailid+"'";
					Hashtable htfetchagentid=qm.getRow(fetchagentid);
					String agentid=String.valueOf(htfetchagentid.get("agent_id"));
					String FilePath = strVirtualPath+"/"+strRelativePath;
					FilePath = strVirtualPath+"/"+strRelativePath;
					FilePath = FilePath+"/"+featureName+"/"+agentid;
					FilePath=FilePath.replaceAll("//", "/");
					FilePath=FilePath.replaceAll(" ", "");
					File file=new File(FilePath);
					if(!file.exists())
					{
						file.mkdirs();
					}
					/* create a multipart object to upload files from client machine and store in the external directory.*/
					MultipartRequest multi = new MultipartRequest(request, FilePath , 15 * 1024 * 1024,new com.oreilly.servlet.multipart.DefaultFileRenamePolicy());
					String strIconFileName = fetchIconPath.substring (fetchIconPath.lastIndexOf("/")+1,fetchIconPath.length());
					strIconFileName = strIconFileName.trim();
					int index = fetchIconPath.lastIndexOf('.');
					String imageExtension = fetchIconPath.substring( ++index );
					if (( imageExtension.equalsIgnoreCase("gif") ) || ( imageExtension.equalsIgnoreCase("bmp") )||( imageExtension.equalsIgnoreCase("jpg") ) ||   ( imageExtension.equalsIgnoreCase("jpeg") )||( imageExtension.equalsIgnoreCase("jpe") ) ||  ( imageExtension.equalsIgnoreCase("png") )||( imageExtension.equalsIgnoreCase("wbmp") )  )
					{
						Enumeration files = multi.getFileNames();
						while (files.hasMoreElements())
						{
							String name = (String)files.nextElement();
							String imgExtension="";
							String strOrigImageName = multi.getOriginalFileName(name);
							if( imageExtension.equalsIgnoreCase("JPEG") || imageExtension.equalsIgnoreCase("jpeg"))
							{
								imgExtension= strOrigImageName.replace(".JPEG", ".jpeg");
							}
							else if( imageExtension.equalsIgnoreCase("JPE") || imageExtension.equalsIgnoreCase("jpe"))
							{
								imgExtension= strOrigImageName.replace(".JPE", ".jpe");
							}
							else if( imageExtension.equalsIgnoreCase("JPG") || imageExtension.equalsIgnoreCase("jpg"))
							{
								imgExtension= strOrigImageName.replace(".JPG", ".jpg");
							}
							else if( imageExtension.equalsIgnoreCase("PNG") || imageExtension.equalsIgnoreCase("png"))
							{
								imgExtension= strOrigImageName.replace(".PNG", ".png");
							}
							String strOrigFileName= strOrigImageName.replaceAll(" ", "_");
							File oldFileName=new File(FilePath + "/"+strOrigFileName);
							imgExtension=imgExtension.replace(" ","_");
							File newFileName=new File(FilePath + "/"+imgExtension);
							oldFileName.renameTo(newFileName);
							if(strOrigFileName==null || strOrigFileName.equals(""))
							{
								continue;
							}
							File fetchfiles = multi.getFile(name);
							double picSize = (fetchfiles.length())/(1024*1024);
							if(picSize > ATTACHMENT_LIMIT)
							{
								session.setAttribute("sesErrorMsg","<font color='#CC0000' class='vrb10'>Your Attachment size Exceeds the Limit</font>");
								response.sendRedirect("../jsp/livechatadmin/agentregistration.jsp");
								return;
							}
							else
							{
								// create a thumbnail for the uploaded image.
								ImgTrans pt = new ImgTrans(qm);
								int result=pt.createThumbnail(FilePath,strOrigFileName,strScriptPath);
								if(result<0)
								{
									session.setAttribute("sesErrorMsg","<font color='#CC0000' class='vrb10'>Failed to create thumbnail</font>");
									response.sendRedirect("../jsp/livechatadmin/agentregistration.jsp");
									return;
								}
								String strFilePath  = FilePath + "/"+strOrigFileName;
								File oldFile=new File(FilePath + "/"+strOrigFileName);
								String strNewFileName=strOrigFileName;
								File newFile=new File(FilePath + "/"+strNewFileName);
								oldFile.renameTo(newFile);
								File oldTNFile=new File(FilePath + "/"+"thumbnail_"+strOrigFileName);
								String strNewTNFileName="thumbnail_"+strOrigFileName;
								File newTNFile=new File(FilePath + "/"+strNewTNFileName);
								oldTNFile.renameTo(newTNFile);

								String strPicURL="http://"+strCMSServerIP+strRelativePath+featureName+"/"+agentid+"/"+strOrigFileName;
								String updatepic = "update batterywale_livechat_agentlogin set agent_picture_url='"+strPicURL+"' where agent_id='"+agentid+"'";
								int updatepicreslt=qm.executeUpdate(updatepic);
							}
						}
					}
					session.setAttribute("sesErrorMsg", "<font color='#000000' class='top1'>Successfully inserted chatagent Details</font>");
					response.sendRedirect("../jsp/livechatadmin/agentregistration.jsp");
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}	
			return;
		}
		/* This method is used to fetch the chat agents status*/
		else if(strWhatToDo.equalsIgnoreCase("fetchchatagentsloginstatus"))
		{
			
			try
			{ 
				String strConditions="";
							
				//query used to fetch chat agent details
				String fetchagentdetls="select agent_id,agentfirst_name,agentlast_name,agent_emailid,agent_status,agent_logintime from batterywale_livechat_agentlogin where agent_status='active' or agent_status='busy' order by agent_logintime asc";
				Vector result=qm.executeQuery(fetchagentdetls);				
				LogLevel.DEBUG(5, new Throwable(), "result : " + result);				
				if(result.size()==1)
				{
					
				}
				if(result == null || result.size()<=0)
				{				
					session.setAttribute("priority","1");
					session.setAttribute("sesErrorMsg", "<font color='#CC0000' class='vrb10'></font> ");
					session.removeAttribute("ChatAgntDetls");
					response.sendRedirect("../jsp/livechatadmin/fetchchatagentloginstatus.jsp");
					return;
				}
				else
				{
					if(session.getAttribute("ChatAgntDetls")!=null)
					session.removeAttribute("ChatAgntDetls");
					session.setAttribute("ChatAgntDetls", result);
				
					response.sendRedirect("../jsp/livechatadmin/fetchchatagentloginstatus.jsp");
				}
			}
			catch(IOException ioe)
			{
				LogLevel.ERROR(0,ioe,"Problem Caught IOException while fetching agents!! "+ioe);
				ioe.printStackTrace();
			}
			catch(Exception e)
			{
				LogLevel.ERROR(0,e,"Problem Caught Exception while fetching agents!! "+e);
				e.printStackTrace();
			}
		}
		/*this action is used to fetch chat agents*/
		else if(strWhatToDo.equalsIgnoreCase("fetchchatagents"))
		{
			String pagenumber = (request.getParameter("pagenumber")!=null)?(request.getParameter("pagenumber")):"0";
			String pagetype = (request.getParameter("pagetype")!=null)?(request.getParameter("pagetype")):"0";
			String lastpage = (request.getParameter("lastpage")!=null)?(request.getParameter("lastpage")):"0";
			String Dummypagenumber="0";
			try
			{ 
				String strConditions="";
				if(lastpage.equals("0"))
				{
					lastpage="1";
				}
				if(pagetype.equals("first"))
				{
					pagenumber="1"; 
				}
				if(pagetype.equals("previous"))
				{
					int pn=Integer.parseInt(pagenumber)-1;
					pagenumber=Integer.toString(pn);
				}
				if(pagetype.equals("next"))
				{
					int pn=Integer.parseInt(pagenumber)+1;
					pagenumber=Integer.toString(pn);				
				}
				if(pagetype.equals("last"))
				{
					pagenumber=lastpage;
				}
				if(pagenumber.equals("0"))
				{
					pagenumber="1";
				}
				int CHATAGENTS_PER_PAGE=10;
				int Startlimt=CHATAGENTS_PER_PAGE*(Integer.parseInt(pagenumber)-1);
				String Startindex=Integer.toString(Startlimt); 
				
				//query used to fetch chat agent details
				String fetchagentdetls="select agent_id,agentfirst_name,agentlast_name,agent_emailid from batterywale_livechat_agentlogin where agent_status='active' or agent_status='inactive' or agent_status='Busy' order by agentfirst_name asc limit "+Startindex+",10";
				Vector result=qm.executeQuery(fetchagentdetls);
				
				//query used to fetch chat agents count
				String countquery="select count(*)as count from batterywale_livechat_agentlogin where agent_status='active' or agent_status='inactive' or agent_status='Busy'";
				ArrayList tcount=qm.getField(countquery);
				String Count = tcount.toString().replace("[", "").replace("]", "");
				if(result.size()==1)
				{
					Dummypagenumber="1";
				}
				if(result == null || result.size()<=0)
				{				
					session.setAttribute("priority","1");
					session.setAttribute("sesErrorMsg", "<font color='#CC0000' class='vrb10'></font> ");
					session.removeAttribute("ChatAgntDetls");
					session.removeAttribute("sesChatAgntcount");
					response.sendRedirect("../jsp/livechatadmin/fetchchatagents.jsp?pagenumber="+pagenumber+"&Dummypagenumber="+Dummypagenumber);
					return;
				}
				else
				{
					if(session.getAttribute("ChatAgntDetls")!=null)
					session.removeAttribute("ChatAgntDetls");
					session.setAttribute("ChatAgntDetls", result);
					if(session.getAttribute("sesChatAgntcount")!=null)
					session.removeAttribute("sesChatAgntcount");
					session.setAttribute("sesChatAgntcount",Count);
					response.sendRedirect("../jsp/livechatadmin/fetchchatagents.jsp?pagenumber="+pagenumber+"&Dummypagenumber="+Dummypagenumber);
				}
			}
			catch(IOException ioe)
			{
				LogLevel.ERROR(0,ioe,"Problem Caught IOException while fetching agents!! "+ioe);
				ioe.printStackTrace();
			}
			catch(Exception e)
			{
				LogLevel.ERROR(0,e,"Problem Caught Exception while fetching agents!! "+e);
				e.printStackTrace();
			}
		}
		/*this action is used to delete chat agents account*/
		else if(strWhatToDo.equalsIgnoreCase("deleteaccount"))
		{
			String agentid = (request.getParameter("check")!=null)?(request.getParameter("check")):"0";
			String pagenumber = (request.getParameter("pagenumber")!=null)?(request.getParameter("pagenumber")):"0";
			String Dummypagenumber = (request.getParameter("Dummypagenumber")!=null)?(request.getParameter("Dummypagenumber")):"0";
			String featureName = (request.getParameter("feature")!=null)?(request.getParameter("feature")):"0";
			String accids="";
			try
			{
				String deleteagent ="delete from batterywale_livechat_agentlogin where agent_id = "+agentid+" " ; 
				int deleteagentresult=qm.executeUpdate(deleteagent);
				String livechatImagePath = strVirtualPath+"/"+strRelativePath+"/"+featureName+"/"+agentid;
				File directoryPath = new File(livechatImagePath);
				if(directoryPath.isDirectory())
				{  
					//list all the directory contents
					String allImages[] = directoryPath.list();
					for (int i=0; i<allImages.length; i++)
					{  
						//construct the file structure
						File imagePath = new File(directoryPath, allImages[i]);
						//recursive delete images
						imagePath.delete();  
					}
					//directory is empty, then delete it 
					if(directoryPath.list().length==0)
					{
					   directoryPath.delete();
					}
				 }
				 String deleteagentratings ="delete from `batterywale_agent_rating` where agent_id = "+agentid+" " ; 
				int deleteagentratingresult=qm.executeUpdate(deleteagentratings);
				if(Dummypagenumber.equals("1"))
				{
					String fetchcount="select count(*)as count from batterywale_livechat_agentlogin where agent_status='active' or agent_status='inactive' or agent_status='Busy'";
					Hashtable htfetchcount = qm.getRow(fetchcount);
					String count=String.valueOf(htfetchcount.get("count"));
					int TotalCount=Integer.parseInt(count);
					int CHATAGENTS_PER_PAGE=10;
					int tp=((int)(Math.ceil((double)TotalCount/CHATAGENTS_PER_PAGE)));
					pagenumber=Integer.toString(tp);
				}
				if(deleteagentresult <0)
				{
					session.setAttribute("priority","1");
					session.setAttribute("sesErrorMsg", "<font color='#CC0000' class='vrb10'>Failed to delete chat agent!</font> ");
					response.sendRedirect("../servlet/LiveChatManagement?hidWhatToDo=fetchchatagents&pagenumber="+pagenumber);
					return;
				}
				else
				{
					session.setAttribute("sesErrorMsg", "<font color='#2364b1' class='vrb10'>Successfully deleted  chat agent!</font> ");
					response.sendRedirect("../servlet/LiveChatManagement?hidWhatToDo=fetchchatagents&pagenumber="+pagenumber);
					return;
				}
			}
			catch(IOException ioe)
			{
				LogLevel.ERROR(0,ioe,"problem Caught IOException if(deleting chatagent account) !! "+ioe);
				ioe.printStackTrace();
			}
			catch(Exception e)
			{
				LogLevel.ERROR(0,e,"Problem Caught Exception if(deleting chatagent account)!! "+e);
				e.printStackTrace();
			}
		}
		/*this action is used to check chatagent loginname*/
		else if(strWhatToDo.equalsIgnoreCase("checkchatagentloginname"))
		{
			String strRes="";
			String chatagentlogname = (request.getParameter("chatagentloginname")!=null)?(request.getParameter("chatagentloginname")):"0";
		
			try
			{  
				if(chatagentlogname.equals(""))
				{
					chatagentlogname="0";
				}
				else
				{
					chatagentlogname=chatagentlogname;
				}
				String fetchagentlogname = "SELECT agent_loginname FROM batterywale_livechat_agentlogin WHERE agent_loginname='"+chatagentlogname+"'"; 
				Hashtable htfetchagentlogname = qm.getRow(fetchagentlogname);
				if(htfetchagentlogname.isEmpty() )
				{ 
					strRes=strRes+"No chatagent Login Name exists u can continue";
				}
				else
				{
					strRes=strRes+"chatagent Login Name already exists";
				}
				out.println(strRes);
			}
			catch(IOException ioe)
			{
				LogLevel.ERROR(0,ioe,"Problem Caught IOException while checking agent login name !! "+ioe);
				ioe.printStackTrace();
				
				
			}
			catch(Exception e)
			{
				LogLevel.ERROR(0,e,"Problem Caught Exception while checking agent login name !! "+e);
				e.printStackTrace();
				
			}
		}
		//this action is used to modify chat agent
		else if(strWhatToDo.equalsIgnoreCase("modifychatagent"))
		{
			String agentid=(request.getParameter("check")!=null)?(request.getParameter("check")):"";
			String pagenumber = (request.getParameter("pagenumber")!=null)?(request.getParameter("pagenumber")):"0";
			try
			{
				String fetchagentdetls = "select agent_id,agentfirst_name,agentlast_name,agent_loginname,agent_emailid,agent_mobile_number,agent_chatlimit,agent_picture_url from batterywale_livechat_agentlogin where agent_id="+agentid+" "; 
				Vector result=qm.executeQuery(fetchagentdetls);
				if(result == null || result.size()<=0)
				{
					session.setAttribute("priority","1");
					session.setAttribute("sesErrorMsg", "<font color='#CC0000' class='vrb10'>Failed to fetch chatagent details!</font> ");
					response.sendRedirect("../jsp/livechatadmin/modifychatagents.jsp?pagenumber="+pagenumber);
					return;
				}
				else
				{
					session.removeAttribute("ChatAgntDetls");
					session.setAttribute("ChatAgntDetls", result);
					response.sendRedirect("../jsp/livechatadmin/modifychatagents.jsp?pagenumber="+pagenumber);
				}
			}
			catch(IOException ioe)
			{
				LogLevel.ERROR(0,ioe,"Problem Caught IOException if(modifyaccount) !! "+ioe);
				ioe.printStackTrace();
			}
			catch(Exception e)
			{
				LogLevel.ERROR(0,e,"Problem Caught Exception if(modifyaccount)!! "+e);
				e.printStackTrace();
				
			}
		}
		//this action is used to update chat agent details
		else if(strWhatToDo.equalsIgnoreCase("updatechatagent"))
		{
			String firstname = (request.getParameter("firstname")!=null)?(request.getParameter("firstname")):"";
			String lastname = (request.getParameter("lastname")!=null)?(request.getParameter("lastname")):"";
			String loginname = (request.getParameter("loginname")!=null)?(request.getParameter("loginname")):"";
			String featureName = (request.getParameter("feature")!=null)?(request.getParameter("feature")):"";
			String emailid = (request.getParameter("emailid")!=null)?(request.getParameter("emailid")):"";
			String mobilenumber = (request.getParameter("mobilenumber")!=null)?(request.getParameter("mobilenumber")):"";
			String chatlimit = (request.getParameter("chatlimit")!=null)?(request.getParameter("chatlimit")):"4";
			String fetchIconPath = (request.getParameter("imagattach")!=null)?(request.getParameter("imagattach")):"";
			String agentid = (request.getParameter("check")!=null)?(request.getParameter("check")):"";
			String pagenumber = (request.getParameter("pagenumber")!=null)?(request.getParameter("pagenumber")):"";
			double ATTACHMENT_LIMIT = 4.0;
			try
			{
				if((strAdminName==null) || (strAdminName==""))
				{
					session.setAttribute("sesErrorMsg","<font color='#CC0000' class='vrb10'>Session Expired. Please login to upload personal ads</font>");
					response.sendRedirect("../jsp/livechatadmin/adminlogin.jsp");
					return;
				}
				else
				{
					//query used to update chat agent user details
					String updateChatagntdetls = "update batterywale_livechat_agentlogin set agentfirst_name='"+firstname+"',agentlast_name='"+lastname+"',agent_emailid='"+emailid+"',agent_mobile_number='"+mobilenumber+"',agent_chatlimit='"+chatlimit+"' where agent_id="+agentid+"";
					int updateresult = qm.executeUpdate(updateChatagntdetls);
					if(updateresult<0)
					{
						session.setAttribute("sesErrorMsg","<font color='#CC0000' class='vrb10'>Failed to update chatagent details</font>");
						response.sendRedirect("../servlet/LiveChatManagement?hidWhatToDo=fetchchatagents&pagenumber="+pagenumber);
						return;
					}
					if (fetchIconPath.equals("NA"))
					{
					}
					else
					{
						String livechatImagePath = strVirtualPath+"/"+strRelativePath+"/"+featureName+"/"+agentid;
						File directoryPath = new File(livechatImagePath);
						if(directoryPath.isDirectory())
						{  
							//list all the directory contents
							String allImages[] = directoryPath.list();
							for (int i=0; i<allImages.length; i++)
							{  
								//construct the file structure
								File imagePath = new File(directoryPath, allImages[i]);
								//recursive delete images
								imagePath.delete();  
							}
							//directory is empty, then delete it 
							if(directoryPath.list().length==0)
							{
							 directoryPath.delete();
							}
						}
						String FilePath = strVirtualPath+"/"+strRelativePath;
						FilePath = strVirtualPath+"/"+strRelativePath;
						FilePath = FilePath+"/"+featureName+"/"+agentid;
						FilePath=FilePath.replaceAll("//", "/");
						FilePath=FilePath.replaceAll(" ", "");
						File file=new File(FilePath);
						if(!file.exists())
						{
							file.mkdirs();
						}
						/* create a multipart object to upload files from client machine and store in the external directory.*/
						MultipartRequest multi = new MultipartRequest(request, FilePath , 15 * 1024 * 1024,new com.oreilly.servlet.multipart.DefaultFileRenamePolicy());
						String strIconFileName = fetchIconPath.substring (fetchIconPath.lastIndexOf("/")+1,fetchIconPath.length());
						strIconFileName = strIconFileName.trim();
						int index = fetchIconPath.lastIndexOf('.');
						String imageExtension = fetchIconPath.substring( ++index );
						if (( imageExtension.equalsIgnoreCase("gif") ) || ( imageExtension.equalsIgnoreCase("bmp") )||( imageExtension.equalsIgnoreCase("jpg") ) ||   ( imageExtension.equalsIgnoreCase("jpeg") )||( imageExtension.equalsIgnoreCase("jpe") ) ||  ( imageExtension.equalsIgnoreCase("png") )||( imageExtension.equalsIgnoreCase("wbmp") )  )
						{
							Enumeration files = multi.getFileNames();
							while (files.hasMoreElements())
							{
								String name = (String)files.nextElement();
								String imgExtension="";
								String strOrigImageName = multi.getOriginalFileName(name);
								if( imageExtension.equalsIgnoreCase("JPEG") || imageExtension.equalsIgnoreCase("jpeg"))
								{
									imgExtension= strOrigImageName.replace(".JPEG", ".jpeg");
								}
								else if( imageExtension.equalsIgnoreCase("JPE") || imageExtension.equalsIgnoreCase("jpe"))
								{
									imgExtension= strOrigImageName.replace(".JPE", ".jpe");
								}
								else if( imageExtension.equalsIgnoreCase("JPG") || imageExtension.equalsIgnoreCase("jpg"))
								{
									imgExtension= strOrigImageName.replace(".JPG", ".jpg");
								}
								else if( imageExtension.equalsIgnoreCase("PNG") || imageExtension.equalsIgnoreCase("png"))
								{
									imgExtension= strOrigImageName.replace(".PNG", ".png");
								}
								String strOrigFileName= strOrigImageName.replaceAll(" ", "_");
								File oldFileName=new File(FilePath + "/"+strOrigFileName);
								imgExtension=imgExtension.replace(" ","_");
								File newFileName=new File(FilePath + "/"+imgExtension);
								oldFileName.renameTo(newFileName);
								if(strOrigFileName==null || strOrigFileName.equals(""))
								{
									continue;
								}
								File fetchfiles = multi.getFile(name);
								double picSize = (fetchfiles.length())/(1024*1024);
								if(picSize > ATTACHMENT_LIMIT)
								{
									session.setAttribute("sesErrorMsg","<font color='#CC0000' class='vrb10'>Your Attachment size Exceeds the Limit</font>");
									response.sendRedirect("../servlet/LiveChatManagement?hidWhatToDo=fetchchatagents&pagenumber="+pagenumber);
									return;
								}
								else
								{
									// create a thumbnail for the uploaded image.
									ImgTrans pt = new ImgTrans(qm);
									int result=pt.createThumbnail(FilePath,strOrigFileName,strScriptPath);
									if(result<0)
									{
										session.setAttribute("sesErrorMsg","<font color='#CC0000' class='vrb10'>Failed to create thumbnail</font>");
										response.sendRedirect("../servlet/LiveChatManagement?hidWhatToDo=fetchchatagents&pagenumber="+pagenumber);
										return;
									}
									String strFilePath  = FilePath + "/"+strOrigFileName;
									File oldFile=new File(FilePath + "/"+strOrigFileName);
									String strNewFileName=strOrigFileName;
									File newFile=new File(FilePath + "/"+strNewFileName);
									oldFile.renameTo(newFile);
									File oldTNFile=new File(FilePath + "/"+"thumbnail_"+strOrigFileName);
									String strNewTNFileName="thumbnail_"+strOrigFileName;
									File newTNFile=new File(FilePath + "/"+strNewTNFileName);
									oldTNFile.renameTo(newTNFile);
									
									String strPicURL="http://"+strCMSServerIP+strRelativePath+featureName+"/"+agentid+"/"+strOrigFileName;
									String updatepic = "update batterywale_livechat_agentlogin set agent_picture_url='"+strPicURL+"' where agent_id='"+agentid+"'";
									int updatepicreslt=qm.executeUpdate(updatepic);
								}
							}
						}
					}
					session.setAttribute("sesErrorMsg", "<font color='#000000' class='top1'>Successfully updated chatagent Details</font>");
					response.sendRedirect("../servlet/LiveChatManagement?hidWhatToDo=fetchchatagents&pagenumber="+pagenumber);
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}	
			return;
		}
		//this action is used for chat agent login
		else if(strWhatToDo.equalsIgnoreCase("chatagentlogin"))
		{ 			
			String agentloginname = (request.getParameter("username")!=null)?(request.getParameter("username")):"";
			String strPassword = (request.getParameter("passwd")!=null)?(request.getParameter("passwd")):"";
			try
			{ 
 				String fetchagentdelts = "SELECT agent_loginname,agent_password,agent_id,agent_emailid,agentfirst_name,agentlast_name FROM batterywale_livechat_agentlogin WHERE BINARY agent_loginname='"+agentloginname+"' and BINARY agent_password='"+strPassword+"' "; 
				LogLevel.DEBUG(5,new Throwable(),"fetchagentdelts :"+fetchagentdelts );
				Hashtable htfetchagentdelts = qm.getRow(fetchagentdelts);
				if(htfetchagentdelts.isEmpty())
				{
					session.setAttribute("priority","1"); 
					session.setAttribute("seschatagentErrorMsg", "<font color='#CC0000' class='vrb10'>Invalid Loginname or Password</font> ");
					response.sendRedirect("../jsp/supportlogin/chatagentlogin.jsp");
					return;
				}
				else
				{
					String agentid=String.valueOf(htfetchagentdelts.get("agent_id"));
					String agentemail=(String)htfetchagentdelts.get("agent_emailid");
					String agentfirst_name=(String)htfetchagentdelts.get("agentfirst_name");
					String agentlast_name=(String)htfetchagentdelts.get("agentlast_name");
					String caname=agentfirst_name+" "+agentlast_name;
					String updateagentstatus="update batterywale_livechat_agentlogin set agent_status='active',agent_logintime=now() where agent_id="+agentid+" and agent_emailid='"+agentemail+"'";
					int updatestatusreslt=qm.executeUpdate(updateagentstatus);
					session.removeAttribute("seschatagentid");
					session.setAttribute("seschatagentid",agentid);
					session.removeAttribute("seschatagentemail");
					session.setAttribute("seschatagentemail",agentemail);
					session.removeAttribute("seschatagentname");
					session.setAttribute("seschatagentname",caname);
					session.setAttribute("seschatagentErrorMsg", "<font color='#2364b1' class='vrb10'>Welcome to BookBattery Live Support!</font> ");
					response.sendRedirect("../jsp/supportlogin/chatagentsupport.jsp");
					return;
				}
			} 
			catch(IOException ioe)
			{ 
				LogLevel.ERROR(0,ioe,"problem Caught IOException while chat agent login !! "+ioe);
				ioe.printStackTrace(); 
				session.setAttribute("seschatagentErrorMsg",	"Error...Please Try Again" ); 
			} 
			catch(Exception e)
			{ 
				LogLevel.ERROR(0,e,"problem Caught Exception while chat agent login !! "+e);
				e.printStackTrace(); 
				session.setAttribute("seschatagentErrorMsg",	"Error...Please Try Again" );
			}
		}
		//this action is used for chat agent logout
		else if(strWhatToDo.equalsIgnoreCase("chatagentlogout"))
		{ 			
			String agentemail = (request.getParameter("email")!=null)?(request.getParameter("email")):"";
			String agentid=(request.getParameter("agentid")!=null)?(request.getParameter("agentid")):"";
			try
			{ 
				String updateagentstatus="update batterywale_livechat_agentlogin set agent_status='inactive',agent_logouttime=now() where agent_id="+agentid+" and agent_emailid='"+agentemail+"'";
				int updatestatusreslt=qm.executeUpdate(updateagentstatus);
				session.removeAttribute("seschatagentid");
				session.setAttribute("seschatagentemail",agentemail);
				if (updatestatusreslt < 0)
				{
					session.setAttribute("priority","1"); 
					session.setAttribute("seschatagentErrorMsg", "<font color='#CC0000' class='vrb10'>Failed to Logout</font> ");
					response.sendRedirect("../jsp/supportlogin/chatagentlogin.jsp");
					return;
				}
				else
				{
					session.setAttribute("priority","1"); 
					session.setAttribute("seschatagentErrorMsg", "<font color='#CC0000' class='vrb10'>Successfully Logout</font> ");
					response.sendRedirect("../jsp/supportlogin/chatagentlogin.jsp");
					return;
				}
			} 
			catch(IOException ioe)
			{ 
				LogLevel.ERROR(0,ioe,"problem Caught IOException while chat agent login !! "+ioe);
				ioe.printStackTrace(); 
				session.setAttribute("seschatagentErrorMsg",	"Error...Please Try Again" ); 
				
			} 
			catch(Exception e)
			{ 
				LogLevel.ERROR(0,e,"problem Caught Exception while chat agent login !! "+e);
				e.printStackTrace(); 
				session.setAttribute("seschatagentErrorMsg",	"Error...Please Try Again" );
			}
		}
		/*this action is used to fetch chat agents rating details*/
		else if(strWhatToDo.equalsIgnoreCase("fetchchatagentsratings"))
		{
			String pagenumber = (request.getParameter("pagenumber")!=null)?(request.getParameter("pagenumber")):"0";
			String pagetype = (request.getParameter("pagetype")!=null)?(request.getParameter("pagetype")):"0";
			String lastpage = (request.getParameter("lastpage")!=null)?(request.getParameter("lastpage")):"0";
			String Dummypagenumber="0";
			try
			{ 
				String strConditions="";
				if(lastpage.equals("0"))
				{
					lastpage="1";
				}
				if(pagetype.equals("first"))
				{
					pagenumber="1"; 
				}
				if(pagetype.equals("previous"))
				{
					int pn=Integer.parseInt(pagenumber)-1;
					pagenumber=Integer.toString(pn);
				}
				if(pagetype.equals("next"))
				{
					int pn=Integer.parseInt(pagenumber)+1;
					pagenumber=Integer.toString(pn);				
				}
				if(pagetype.equals("last"))
				{
					pagenumber=lastpage;
				}
				if(pagenumber.equals("0"))
				{
					pagenumber="1";
				}
				int CHATAGENTS_PER_PAGE=10;
				int Startlimt=CHATAGENTS_PER_PAGE*(Integer.parseInt(pagenumber)-1);
				String Startindex=Integer.toString(Startlimt); 
				//query used to fetch chat agent rating details
				String fetchagentdetls="select agent_id,agent_emailid,user_email,user_name,user_id,user_rating,user_mobile_number,creation_date from batterywale_agent_rating where user_rating='Good' or user_rating='Excellent' or user_rating='Average' or user_rating='Not acceptable' or user_rating='Poor' order by creation_date desc limit "+Startindex+",10";
				Vector result=qm.executeQuery(fetchagentdetls);
				//query used to fetch chat agents rating count
				String countquery="select count(*)as count from batterywale_agent_rating where user_rating='Good' or user_rating='Excellent' or user_rating='Average' or user_rating='Not acceptable' or user_rating='Poor'";
				ArrayList tcount=qm.getField(countquery);
				String Count = tcount.toString().replace("[", "").replace("]", "");
				if(result.size()==1)
				{
					Dummypagenumber="1";
				}
				if(result == null || result.size()<=0)
				{				
					session.setAttribute("priority","1");
					session.setAttribute("sesErrorMsg", "<font color='#CC0000' class='vrb10'></font> ");
					session.removeAttribute("ChatAgntDetls");
					session.removeAttribute("sesChatAgntcount");
					response.sendRedirect("../jsp/livechatadmin/viewratings.jsp?pagenumber="+pagenumber+"&Dummypagenumber="+Dummypagenumber);
					return;
				}
				else
				{
					if(session.getAttribute("ChatAgntDetls")!=null)
					session.removeAttribute("ChatAgntDetls");
					session.setAttribute("ChatAgntDetls", result);
					if(session.getAttribute("sesChatAgntcount")!=null)
					session.removeAttribute("sesChatAgntcount");
					session.setAttribute("sesChatAgntcount",Count);
					response.sendRedirect("../jsp/livechatadmin/viewratings.jsp?pagenumber="+pagenumber+"&Dummypagenumber="+Dummypagenumber);
				}
			}
			catch(IOException ioe)
			{
				LogLevel.ERROR(0,ioe,"Problem Caught IOException while fetching agents!! "+ioe);
				ioe.printStackTrace();
			}
			catch(Exception e)
			{
				LogLevel.ERROR(0,e,"Problem Caught Exception while fetching agents!! "+e);
				e.printStackTrace();
			}
		}
		/*this action is used to fetch chat history in admin*/
		else if(strWhatToDo.equalsIgnoreCase("fetchchathistory"))
		{
			String pagenumber = (request.getParameter("pagenumber")!=null)?(request.getParameter("pagenumber")):"0";
			String pagetype = (request.getParameter("pagetype")!=null)?(request.getParameter("pagetype")):"0";
			String lastpage = (request.getParameter("lastpage")!=null)?(request.getParameter("lastpage")):"0";
			String Dummypagenumber="0";
			try
			{ 
				if(lastpage.equals("0"))
				{
					lastpage="1";
				}
				if(pagetype.equals("first"))
				{
					pagenumber="1"; 
				}
				if(pagetype.equals("previous"))
				{
					int pn=Integer.parseInt(pagenumber)-1;
					pagenumber=Integer.toString(pn);
				}
				if(pagetype.equals("next"))
				{
					int pn=Integer.parseInt(pagenumber)+1;
					pagenumber=Integer.toString(pn);				
				}
				if(pagetype.equals("last"))
				{
					pagenumber=lastpage;
				}
				if(pagenumber.equals("0"))
				{
					pagenumber="1";
				}
				int CHATHISTORY_PER_PAGE=10;
				int Startlimt=CHATHISTORY_PER_PAGE*(Integer.parseInt(pagenumber)-1);
				String Startindex=Integer.toString(Startlimt); 
				//query used to fetch chat agent rating details
				
				String fetchagentdetls="select agentfirst_name,agentlast_name,user_name,creation_date,agent_id,user_id from batterywale_livechat_messages group by user_id order by creation_date desc limit "+Startindex+",10";

				Vector result=qm.executeQuery(fetchagentdetls);
				//query used to fetch chat agents rating count
				String countquery="select count(*)as count from batterywale_livechat_messages group by user_id";
				ArrayList tcount=qm.getField(countquery);
				int count = tcount.size();
				String Count = new Integer(count).toString();
				if(result.size()==1)
				{
					Dummypagenumber="1";
				}
				if(result == null || result.size()<=0)
				{				
					session.setAttribute("priority","1");
					session.setAttribute("sesErrorMsg", "<font color='#CC0000' class='vrb10'></font> ");
					session.removeAttribute("ChatAgntHistryDetls");
					session.removeAttribute("sesChatAgntHistrycount");
					response.sendRedirect("../jsp/livechatadmin/viewchathistory.jsp?pagenumber="+pagenumber+"&Dummypagenumber="+Dummypagenumber);
					return;
				}
				else
				{
					if(session.getAttribute("ChatAgntHistryDetls")!=null)
					session.removeAttribute("ChatAgntHistryDetls");
					session.setAttribute("ChatAgntHistryDetls", result);
					if(session.getAttribute("sesChatAgntHistrycount")!=null)
					session.removeAttribute("sesChatAgntHistrycount");
					session.setAttribute("sesChatAgntHistrycount",Count);
					response.sendRedirect("../jsp/livechatadmin/viewchathistory.jsp?pagenumber="+pagenumber+"&Dummypagenumber="+Dummypagenumber);
				}
			}
			catch(IOException ioe)
			{
				LogLevel.ERROR(0,ioe,"Problem Caught IOException while fetching agents!! "+ioe);
				ioe.printStackTrace();
			}
			catch(Exception e)
			{
				LogLevel.ERROR(0,e,"Problem Caught Exception while fetching agents!! "+e);
				e.printStackTrace();
			}
		}
		/*this action is used to fetch chat history messages in admin*/
		else if(strWhatToDo.equalsIgnoreCase("fetchchathistorymsgs"))
		{
			String agentid = (request.getParameter("agentid")!=null)?(request.getParameter("agentid")):"0";
			String userid = (request.getParameter("userid")!=null)?(request.getParameter("userid")):"0";
			String strRes="";
			try
			{
				String fetchmsgs="select message,creation_date,from_id,to_id from batterywale_livechat_messages where agent_id="+agentid+" and user_id="+userid+" order by creation_date desc";
				Vector vectfetchmsgs=qm.executeQuery(fetchmsgs);
				if(vectfetchmsgs==null || vectfetchmsgs.size() == 0)
				{

				}
				else
				{
					strRes=strRes+"<table bgcolor='#dddddd'  width='100%'cellspacing='0' cellpadding='0'><tr><td align='right'><INPUT TYPE='button' style='cursor:pointer;' class='button4' VALUE='Close' ONCLICK='javascript:closechatdiv();'>&nbsp;&nbsp;</td></tr></table>";
					strRes=strRes+"<table  width='100%' cellspacing='1' style='table-layout:fixed;' cellpadding='2' bgcolor='#dddddd'><tr  bgcolor='#2364b1'><td align='center' class='prodheading' width='30%'>From</td><td align='center' class='prodheading' width='20%'>To</td><td align='center' class='prodheading' width='33%'>Message</td><td align='center' class='prodheading' width='33%'>Creation Date</td></tr>";
		
					for(int j=0; j<vectfetchmsgs.size();j++)
					{
						Hashtable htfetchMsgs=(Hashtable)vectfetchmsgs.get(j);
						String message=(String)htfetchMsgs.get("message");
						String creationdate=String.valueOf(htfetchMsgs.get("creation_date"));
						String from_id=String.valueOf(htfetchMsgs.get("from_id"));
						String to_id=String.valueOf(htfetchMsgs.get("to_id"));
						String fetchnames= "select from_name,to_name from batterywale_livechat_messages where from_id='"+from_id+"' and to_id='"+to_id+"'";
						Hashtable htfetchnames = qm.getRow(fetchnames);
						String Firstname=(String)htfetchnames.get("from_name");
						String username=(String)htfetchnames.get("to_name");
					
							strRes=strRes+"<tr bgcolor='#aaaaaa'><td  class='table1' width='30%'>"+Firstname+"</td><td  class='table1' width='20%'>"+username+"</td><td  class='table1' style='word-wrap: break-word;' width='33%'>"+message+"</td><td  class='table1' width='33%'>"+creationdate+"</td></tr>";
						
					}
					strRes=strRes+"</table>";
				}
				out.println(strRes);
			}
			catch(Exception e)
			{ 
				LogLevel.ERROR(0,e,"problem Caught Exception while chat agent login !! "+e);
				e.printStackTrace(); 
				session.setAttribute("seschatagentErrorMsg",	"Error...Please Try Again" );
			}
		}
		/*this action is used to fetch busy chat messages in admin*/
		else if(strWhatToDo.equalsIgnoreCase("fetchBusychatmsgs"))
		{
			String pagenumber = (request.getParameter("pagenumber")!=null)?(request.getParameter("pagenumber")):"0";
			String pagetype = (request.getParameter("pagetype")!=null)?(request.getParameter("pagetype")):"0";
			String lastpage = (request.getParameter("lastpage")!=null)?(request.getParameter("lastpage")):"0";
			String Dummypagenumber="0";
			try
			{
				if(lastpage.equals("0"))
				{
					lastpage="1";
				}
				if(pagetype.equals("first"))
				{
					pagenumber="1"; 
				}
				if(pagetype.equals("previous"))
				{
					int pn=Integer.parseInt(pagenumber)-1;
					pagenumber=Integer.toString(pn);
				}
				if(pagetype.equals("next"))
				{
					int pn=Integer.parseInt(pagenumber)+1;
					pagenumber=Integer.toString(pn);				
				}
				if(pagetype.equals("last"))
				{
					pagenumber=lastpage;
				}
				if(pagenumber.equals("0"))
				{
					pagenumber="1";
				}
				int CHATHISTORY_PER_PAGE=10;
				int Startlimt=CHATHISTORY_PER_PAGE*(Integer.parseInt(pagenumber)-1);
				String Startindex=Integer.toString(Startlimt); 
				//query used to fetch chat agent rating details
				
				String fetchbusymsgs="select user_id,user_emailid,user_name,user_message,agent_message,creation_date from batterywale_livechat_busymessages where status='new' order by creation_date desc limit "+Startindex+",10";
				LogLevel.DEBUG(5, new Throwable(), "fetchbusymsgs : " + fetchbusymsgs);
				Vector result=qm.executeQuery(fetchbusymsgs);
				//query used to fetch chat agents rating count
				String countquery="select count(*)as count from batterywale_livechat_busymessages where status='new'";
				LogLevel.DEBUG(5, new Throwable(), "countquery : " + countquery);
				Hashtable htcount = qm.getRow(countquery);
				String strcount=String.valueOf(htcount.get("count"));
				int TotalCount=Integer.parseInt(strcount);
				out.println(TotalCount);
				LogLevel.DEBUG(5, new Throwable(), "TotalCount : " + TotalCount);
				if(result.size()==1)
				{
					Dummypagenumber="1";
				}
				if(result == null || result.size()<=0)
				{				
					session.setAttribute("priority","1");
					session.setAttribute("sesErrorMsg", "<font color='#CC0000' class='vrb10'></font> ");
					session.removeAttribute("ChatAgntBusyMsgDetls");
					session.removeAttribute("sesChatBusyMsgcount");
					response.sendRedirect("../jsp/livechatadmin/viewmessages.jsp?pagenumber="+pagenumber+"&Dummypagenumber="+Dummypagenumber);
					return;
				}
				else
				{
					if(session.getAttribute("ChatAgntBusyMsgDetls")!=null)
					session.removeAttribute("ChatAgntBusyMsgDetls");
					session.setAttribute("ChatAgntBusyMsgDetls", result);
					if(session.getAttribute("sesChatBusyMsgcount")!=null)
					session.removeAttribute("sesChatBusyMsgcount");
					session.setAttribute("sesChatBusyMsgcount",strcount);
					response.sendRedirect("../jsp/livechatadmin/viewmessages.jsp?pagenumber="+pagenumber+"&Dummypagenumber="+Dummypagenumber);
				}
			}
			catch(IOException ioe)
			{
				LogLevel.ERROR(0,ioe,"Problem Caught IOException while fetching agents!! "+ioe);
				ioe.printStackTrace();
			}
			catch(Exception e)
			{
				LogLevel.ERROR(0,e,"Problem Caught Exception while fetching agents!! "+e);
				e.printStackTrace();
			}
		}
		/*this action is used to send message to user*/
		else if(strWhatToDo.equalsIgnoreCase("sendmessageTouser"))
		{
			String useremail = (request.getParameter("useremail")!=null)?(request.getParameter("useremail")):"0";
			String adminmessage = (request.getParameter("adminmessage")!=null)?(request.getParameter("adminmessage")):"0";
			String username = (request.getParameter("username")!=null)?(request.getParameter("username")):"0";
			String user_id=(request.getParameter("user_id")!=null)?(request.getParameter("user_id")):"0";
			adminmessage = adminmessage.replace("'","&#39;");
			try
			{
				String upbusymsgstatus="update batterywale_livechat_busymessages set status='resolved',agent_message='"+adminmessage+"' where user_id='"+user_id+"' and user_emailid='"+useremail+"'";
				int upbusymsgstatusreslt = qm.executeUpdate(upbusymsgstatus);
				LogLevel.DEBUG(5, new Throwable(), "upbusymsgstatus : " + upbusymsgstatus);
				//sending Mail to User 
				MailTrans mtrans=new MailTrans();
				mtrans.setStrSmtpHost(strCMSServerIP);
				mtrans.setStrFrom(SupportEmailId);
				mtrans.setStrTo(useremail);
				mtrans.setStrSubject("Message Recieved from "+supportname+"");
				String strThanks="Thanks & Regards,"+"\n"+""+supportname+" Team.";
				String strMailMessage="Dear "+username+",\n\n "+adminmessage+"\n\n"+strThanks+"\n\nNote: \nConfidentiality Information and Disclaimer:\nThis communication sent from "+supportname+" is confidential and intended solely for the use of addressee. Any retransmission, dissemination or the use of  this information by persons other than addressee is unlawful and prohibited. The views expressed here-in (including any attachments) are those of the individual sender only. "+supportname+" does not accept any liability what-so-ever including on account of any errors,omissions,viruses etc in the contents  of  this message.";
				mtrans.setStrText(strMailMessage);
				Thread mt=new MailThread(mtrans,"");
				LogLevel.DEBUG(1,new Throwable(),"Initiating mail to: "+useremail);
				LogLevel.DEBUG(1,new Throwable(),"mail from: "+strCMSServerIP);
				LogLevel.DEBUG(1,new Throwable(),"Mail text is: "+strMailMessage);
				mt.start();
				out.println("sucessfully sent emailid");
				return;
			}
			catch(Exception e)
			{
				LogLevel.ERROR(0,e,"Exception while sending email:"+e);
				e.printStackTrace();
			}
		}
		/*this action is used to fetch new messages count in admin*/
		else if (strWhatToDo.equalsIgnoreCase("messagescount"))
		{
			try
			{
				//query used to fetch chat agents rating count
				String countquery="select count(*)as count from batterywale_livechat_busymessages where status='new'";
				LogLevel.DEBUG(5, new Throwable(), "countquery : " + countquery);
				Hashtable htcount = qm.getRow(countquery);
				String strcount=String.valueOf(htcount.get("count"));
				int TotalCount=Integer.parseInt(strcount);
				out.println(TotalCount);
				LogLevel.DEBUG(5, new Throwable(), "TotalCount : " + TotalCount);
				return;
			}
			catch(Exception e)
			{
				LogLevel.ERROR(0,e,"Exception while fetching count:"+e);
				e.printStackTrace();
			}
		}
		else if(strWhatToDo.equalsIgnoreCase("insertmsg"))
		{ 			
			try
			{
				String chatmsgvalue = (request.getParameter("chatmsgvalue")!=null)?(request.getParameter("chatmsgvalue")):"";
				LogLevel.DEBUG(5,new Throwable(),"chatmsgvalue :"+chatmsgvalue);
			//	String chatmsgvalue = URLEncoder.encode(chatmsgvalue11, "UTF-8");
				chatmsgvalue = chatmsgvalue.replace("'","&#39;");chatmsgvalue = chatmsgvalue.replace(",","&#44;");
				chatmsgvalue = chatmsgvalue = chatmsgvalue.replace("|","&#124;");chatmsgvalue.replace("~","&#126;");chatmsgvalue = chatmsgvalue.replace("!","&#33;");chatmsgvalue = chatmsgvalue.replace("@","&#64;");chatmsgvalue = chatmsgvalue.replace("$","&#36;");chatmsgvalue = chatmsgvalue.replace("%","&#37;");chatmsgvalue = chatmsgvalue.replace("^","&#94;");chatmsgvalue = chatmsgvalue.replace("*","&#42;");chatmsgvalue = chatmsgvalue.replace("(","&#40;");chatmsgvalue = chatmsgvalue.replace(")","&#41;");chatmsgvalue = chatmsgvalue.replace("-","&#45;");chatmsgvalue = chatmsgvalue.replace("=","&#61;");chatmsgvalue = chatmsgvalue.replace("\\","&#92;");chatmsgvalue = chatmsgvalue.replace("}","&#125;");chatmsgvalue = chatmsgvalue.replace(",{","&#123;");chatmsgvalue = chatmsgvalue.replace("\"","&#34;");chatmsgvalue = chatmsgvalue.replace(":","&#58;");chatmsgvalue = chatmsgvalue.replace("?","&#63;");chatmsgvalue = chatmsgvalue.replace("`","&#96;");chatmsgvalue = chatmsgvalue.replace("-","&#95;");chatmsgvalue = chatmsgvalue.replace("+","&#43;");chatmsgvalue = chatmsgvalue.replace("]","&#93;");chatmsgvalue = chatmsgvalue.replace("[","&#91;");chatmsgvalue = chatmsgvalue.replace("'","&#39;");chatmsgvalue = chatmsgvalue.replace("/","&#47;");
				LogLevel.DEBUG(5, new Throwable(), "chatmsgvalue mallidi : " + chatmsgvalue);
				String agentid=(request.getParameter("chatemail")!=null)?(request.getParameter("chatemail")):"";
				LogLevel.DEBUG(5, new Throwable(), "agentid : " + agentid);
				String agentname=(request.getParameter("cname")!=null)?(request.getParameter("cname")):"";
				LogLevel.DEBUG(5, new Throwable(), "agentname : " + agentname);
				String email=(request.getParameter("email")!=null)?(request.getParameter("email")):"";
				LogLevel.DEBUG(5, new Throwable(), "email : " + email);
				String usrid=(request.getParameter("usrid")!=null)?(request.getParameter("usrid")):"";
				LogLevel.DEBUG(5, new Throwable(), "usrid : " + usrid);
				String agenid=(request.getParameter("agentid")!=null)?(request.getParameter("agentid")):"";
				LogLevel.DEBUG(5, new Throwable(), "agenid : " + agenid);
				String customername=(request.getParameter("customername")!=null)?(request.getParameter("customername")):"";
				LogLevel.DEBUG(5, new Throwable(), "customername : " + customername);
				String userstatus="select user_status from  batterywale_livechat_userlogin where chatuser_id='"+usrid+"'";
				Hashtable htstatus = qm.getRow(userstatus);
				String usrstatus=String.valueOf(htstatus.get("user_status"));
				LogLevel.DEBUG(5, new Throwable(), "usrstatus : " + usrstatus);
				String inlivechatmsg ="";
				if(!usrstatus.equals("inactive"))
				{
					inlivechatmsg = "insert into batterywale_livechat_messages( agent_id,user_id,agentfirst_name,agent_emailid,user_name,message,from_id,to_id,from_name,to_name,creation_date)values('"+agenid+"','"+usrid+"','"+agentname+"','"+email+"','"+customername+"','"+chatmsgvalue+"','"+usrid+"','"+agenid+"','"+customername+"','"+agentname+"',now())";	
				}
				
				LogLevel.DEBUG(5, new Throwable(), "inlivechatmsg : " + inlivechatmsg);
				int result=qm.executeUpdate(inlivechatmsg);
				LogLevel.DEBUG(5, new Throwable(), "result : " + result);

				String updatetime="update batterywale_livechat_userlogin set last_modified=now() where chatuser_id='"+usrid+"'";
				qm.executeUpdate(updatetime);	
				
				out.println(usrstatus);
			} 
			catch(Exception e)
			{ 
				LogLevel.ERROR(0,e,"problem Caught Exception while chat agent login !! "+e);
				e.printStackTrace(); 
				session.setAttribute("seschatagentErrorMsg",	"Error...Please Try Again" );
			}
		}
		else if(strWhatToDo.equalsIgnoreCase("insertsupport"))
		{ 			
			
			try
			{
				String chatmsgvalue = (request.getParameter("chatmsgvalue")!=null)?(request.getParameter("chatmsgvalue")):"";
				String agentid=(request.getParameter("chatemail")!=null)?(request.getParameter("chatemail")):"";
				String agentname=(request.getParameter("cname")!=null)?(request.getParameter("cname")):"";
				String email=(request.getParameter("email")!=null)?(request.getParameter("email")):"";
				String usrid=(request.getParameter("usrid")!=null)?(request.getParameter("usrid")):"";
				String agenid=(request.getParameter("agentid")!=null)?(request.getParameter("agentid")):"";
				String customername=(request.getParameter("customername")!=null)?(request.getParameter("customername")):"";
				String inlivechatmsg = "insert into batterywale_livechat_messages( agent_id,user_id,agentfirst_name,agent_emailid,user_name,message,creation_date)values('"+agenid+"','"+usrid+"','"+agentname+"','"+email+"','"+customername+"','"+chatmsgvalue+"',now())";		
				int result=qm.executeUpdate(inlivechatmsg);	 
				
			} 
			catch(Exception e)
			{ 
				LogLevel.ERROR(0,e,"problem Caught Exception while chat agent login !! "+e);
				e.printStackTrace(); 
				session.setAttribute("seschatagentErrorMsg",	"Error...Please Try Again" );
			}
		}

		else if(strWhatToDo.equalsIgnoreCase("fetchusers"))
		{ 			
			try
			{
				String agenid=(request.getParameter("agentid")!=null)?(request.getParameter("agentid")):"";
				String count=(request.getParameter("count")!=null)?(request.getParameter("count")):"";
				session.removeAttribute(count);
				session.setAttribute("count",count);
				int userscount = Integer.parseInt(count);
				LogLevel.DEBUG(5,new Throwable(),"userscount:"+userscount);
				if(userscount>=4)
				{
					String updateagentstatus="update batterywale_livechat_agentlogin set agent_status='busy' where agent_id='"+agenid+"'";
					qm.executeUpdate(updateagentstatus);	
				}
				else if(userscount<4)
				{
					String updateagents="update batterywale_livechat_agentlogin set agent_status='active' where agent_id='"+agenid+"'";
					qm.executeUpdate(updateagents);	
				}		


				String inlivechatusers = "select chatuser_id,user_name from batterywale_livechat_userlogin where user_status='active' and agent_id='"+agenid+"' order by chatuser_id desc limit 4";
				Vector chatids=qm.executeQuery(inlivechatusers);String  username = chatids.toString().replace("[{", "").replace("}]", "");
				username = username.replace("}, {","|");username = username.replace("user_name=","");username = username.replace("chatuser_id=","");
				out.println(username);
				
			} 
			catch(Exception e)
			{ 
				LogLevel.ERROR(0,e,"problem Caught Exception while chat agent login !! "+e);
				e.printStackTrace(); 
				session.setAttribute("seschatagentErrorMsg",	"Error...Please Try Again" );
			}
		}

		else if(strWhatToDo.equalsIgnoreCase("livechatinsert"))
		{ 
			try
			{
				String agenid=(request.getParameter("agentid")!=null)?(request.getParameter("agentid")):"";
			
				String inlivechatusers = "select chatuser_id,user_name from batterywale_livechat_userlogin where agent_id='"+agenid+"' limit 5";
				Vector chatids=qm.executeQuery(inlivechatusers);String  username = chatids.toString().replace("[{", "").replace("}]", "");
				username = username.replace("}, {","|");username = username.replace("user_name=","");username = username.replace("chatuser_id=","");
				out.println(username);
				
			} 
			catch(Exception e)
			{ 
				LogLevel.ERROR(0,e,"problem Caught Exception while chat agent login !! "+e);
				e.printStackTrace(); 
				session.setAttribute("seschatagentErrorMsg",	"Error...Please Try Again" );
			}
		}
		else if(strWhatToDo.equalsIgnoreCase("agentmsg"))
		{
			try
			{
				String agenid=(request.getParameter("agentid")!=null)?(request.getParameter("agentid")):"";
				String usrname=(request.getParameter("usrname")!=null)?(request.getParameter("usrname")):"";
				String id=(request.getParameter("id")!=null)?(request.getParameter("id")):"";
				String cmsg=(request.getParameter("cmsg")!=null)?(request.getParameter("cmsg")):"";
				String agentname=(request.getParameter("agentname")!=null)?(request.getParameter("agentname")):"";
				
				cmsg = cmsg.replace("'","&#39;");cmsg = cmsg.replace(",","&#44;");
				cmsg = cmsg = cmsg.replace("|","&#124;");cmsg.replace("~","&#126;");cmsg = cmsg.replace("!","&#33;");cmsg = cmsg.replace("@","&#64;");cmsg = cmsg.replace("$","&#36;");cmsg = cmsg.replace("%","&#37;");cmsg = cmsg.replace("^","&#94;");cmsg = cmsg.replace("*","&#42;");cmsg = cmsg.replace("(","&#40;");cmsg = cmsg.replace(")","&#41;");cmsg = cmsg.replace("-","&#45;");cmsg = cmsg.replace("=","&#61;");cmsg = cmsg.replace("\\","&#92;");cmsg = cmsg.replace("}","&#125;");cmsg = cmsg.replace(",{","&#123;");cmsg = cmsg.replace("\"","&#34;");cmsg = cmsg.replace(":","&#58;");cmsg = cmsg.replace("?","&#63;");cmsg = cmsg.replace("`","&#96;");cmsg = cmsg.replace("-","&#95;");cmsg = cmsg.replace("+","&#43;");cmsg = cmsg.replace("]","&#93;");cmsg = cmsg.replace("[","&#91;");cmsg = cmsg.replace("'","&#39;");cmsg = cmsg.replace("/","&#47;");

				String inchatmsg = "insert into batterywale_livechat_messages( agent_id,agentfirst_name,user_id,user_name,message,from_id,to_id,from_name,to_name,creation_date)values('"+agenid+"','"+agentname+"','"+id+"','"+usrname+"','"+cmsg+"','"+agenid+"','"+id+"','"+agentname+"','"+usrname+"',now())";
				int result=qm.executeUpdate(inchatmsg);
				String updatetime="update batterywale_livechat_userlogin set last_modified=now() where chatuser_id='"+id+"'";
				qm.executeUpdate(updatetime);					
			} 
			catch(Exception e)
			{ 
				LogLevel.ERROR(0,e,"problem Caught Exception while chat agent login !! "+e);
				e.printStackTrace(); 
				session.setAttribute("seschatagentErrorMsg",	"Error...Please Try Again" );
			}
		}

		else if(strWhatToDo.equalsIgnoreCase("afetchmsg"))
		{
			try
			{
				String fetchmsg="",activi="";
				String agenid=(request.getParameter("agentid")!=null)?(request.getParameter("agentid")):"";
				String achatid=(request.getParameter("achatid")!=null)?(request.getParameter("achatid")):"";
				String cookieids=(request.getParameter("cookieids")!=null)?(request.getParameter("cookieids")):"";
				if(cookieids.indexOf(",")>=0)
				{
				String[] parts = cookieids.split(",");
				for(int b=0;b<parts.length;b++)
				{
					String activeuser="select user_status from batterywale_livechat_userlogin where chatuser_id='"+parts[b]+"'";
					ArrayList activeusr=qm.getField(activeuser);
					String actuser=activeusr.toString().replace("[", "").replace("]", "");
					if(actuser.equalsIgnoreCase("inactive"))
					{
						activi=activi+parts[b]+"|";
					}

				}
				}
				else
				{
					if(cookieids.equals(""))
					{

					}
					else
					{
						String activeuser="select user_status from batterywale_livechat_userlogin where chatuser_id='"+cookieids+"'";
						ArrayList activeusr=qm.getField(activeuser);
						String actuser=activeusr.toString().replace("[", "").replace("]", "");
						if(actuser.equalsIgnoreCase("inactive"))
						{
							activi=cookieids+"|";
						}
					}

				}

				achatid=achatid.equals("undefined")?"0":achatid;
				if(achatid.equals("0"))
				{
					fetchmsg="select msg_id from batterywale_livechat_messages where to_id='"+agenid+"' order by msg_id desc limit 1";
					ArrayList msgid=qm.getField(fetchmsg);
					String  mgid = msgid.toString().replace("[", "").replace("]", "");
					out.println("firstchatresult:"+mgid);
				}
				else
				{
					fetchmsg="select msg_id,from_id,message,from_name from batterywale_livechat_messages where to_id='"+agenid+"' and msg_id>'"+achatid+"'";
					Vector chatids=qm.executeQuery(fetchmsg);
					String  username = chatids.toString().replace("[{", "").replace("}]", "");
					username = username.replace("}, {","|");username = username.replace("from_id=","");username = username.replace("from_name=","");username = username.replace("message=","");username = username.replace("msg_id=","");username=username+"||"+activi;
					out.println(username);

				}
				
				
			} 
			catch(Exception e)
			{ 
				LogLevel.ERROR(0,e,"problem Caught Exception while chat agent login !! "+e);
				e.printStackTrace(); 
				session.setAttribute("seschatagentErrorMsg",	"Error...Please Try Again" );
			}
		}

		else if(strWhatToDo.equalsIgnoreCase("afetchmsgcustomer"))
		{
			try
			{
				String fetchmsg="";
				String agenid=(request.getParameter("usrid")!=null)?(request.getParameter("usrid")):"";
				String achatid=(request.getParameter("achatid")!=null)?(request.getParameter("achatid")):"";
				achatid=achatid.equals("undefined")?"0":achatid;
				if(achatid.equals("0"))
				{
					fetchmsg="select msg_id,agentfirst_name from batterywale_livechat_messages where to_id='"+agenid+"' order by msg_id desc limit 1";
					ArrayList msgid=qm.getField(fetchmsg);
					String  mgid = msgid.toString().replace("[", "").replace("]", "");
					out.println("firstchatresult:"+mgid);
				}
				else
				{
					fetchmsg="select msg_id,from_id,message,agentfirst_name from batterywale_livechat_messages where to_id='"+agenid+"' and msg_id>'"+achatid+"'";
					Vector chatids=qm.executeQuery(fetchmsg);String  username = chatids.toString().replace("[{", "").replace("}]", "");
					username = username.replace("}, {","|");username = username.replace("from_id=","");username = username.replace("message=","");username = username.replace("msg_id=","");username = username.replace("agentfirst_name=","");
					out.println(username);

				}
				
				
			} 
			catch(Exception e)
			{ 
				LogLevel.ERROR(0,e,"problem Caught Exception while chat agent login !! "+e);
				e.printStackTrace(); 
				session.setAttribute("seschatagentErrorMsg",	"Error...Please Try Again" );
			}
		}
		else if(strWhatToDo.equalsIgnoreCase("inactivate"))
		{
			String updatetime="update batterywale_livechat_userlogin set user_status='inactive' WHERE last_modified < (NOW() - INTERVAL 5 MINUTE)";
			qm.executeUpdate(updatetime);	

		}
		else if(strWhatToDo.equalsIgnoreCase("closediv"))
		{
			String usrid=(request.getParameter("id")!=null)?(request.getParameter("id")):"";
			String updatetime="update batterywale_livechat_userlogin set user_status='inactive' WHERE chatuser_id='"+usrid+"'";
			qm.executeUpdate(updatetime);	

		}
		else if(strWhatToDo.equalsIgnoreCase("fetchchathistoryagents"))
		{
			String agentid = (request.getParameter("agentid")!=null)?(request.getParameter("agentid")):"0";
			String userid = (request.getParameter("userid")!=null)?(request.getParameter("userid")):"0";
			LogLevel.DEBUG(5, new Throwable(),"userid: "+userid );
			LogLevel.DEBUG(5, new Throwable(),"agentid: "+agentid );
			String strRes="";
			String consumername="";
			try
			{
				if(userid.indexOf(",")>=0)
				{
					String[] parts = userid.split(",");
					for(int b=0;b<parts.length;b++)
					{
							String id=parts[b]; 
							LogLevel.DEBUG(5, new Throwable(),"id: "+id );
							String	fetchusername="select from_name from batterywale_livechat_messages where to_id='"+agentid+"' and from_id='"+id+"'";
							Hashtable htusernames = qm.getRow(fetchusername);
							consumername=(String)htusernames.get("from_name");
							LogLevel.DEBUG(5, new Throwable(),"consumername: "+consumername );

							strRes=strRes+"<div id= "+id+" class='fbchatdiv' style='width:240px;bottom:0px;direction:ltr;position:relative;display:inline-block;margin-right:2px;margin-left:2px;'>";
							strRes=strRes+"<div id='"+id+"_ChatCountContainer' style='display:none;width:20px;position:absolute;right:35px;height:16px;top:-5px;border:1px solid #FF66FF;background-color:#FF99FF;text-align:center;'><span  id='"+id+"_ChatCount' >0</span></div><div id='"+id+"_ChatHeader' align='left' style='width:230px;border: 1px solid #CCCCCC;background-color:#627BAE;padding:2px 5px 2px 5px;cursor:pointer;font: 11px 'Trebuchet MS', Verdana, Arial;'><span style='display:inline-block;width:200px;white-space:nowrap;overflow-x:hidden;'><font color='white' face='verdana' size='2'><b>"+consumername+"</b></font></span><span id='"+id+"_MsgSpan' style='display:none;'></span><span onclick='aChatdivClose(\""+id+"\");'style='float:right;' ><font color='white' face='verdana' size='2'><b>x</b></font></span></div><div id='"+id+"_Chatlist' style='width:240px;min-height:230px;max-height:230px;overflow-y:hidden;overflow-x:hidden;padding-right:15px;'>";
							strRes=strRes+"<div id='"+id+"_ChatMessages' style='text-align:left;word-wrap:break-word;background-color:#EDEFF4;border-left:1px solid #CCCCCC;border-right: 1px solid #CCCCCC;width:240px;min-height:196px;max-height:196px;overflow-y:auto;overflow-x:hidden;padding-top:15px;font: 11px 'Trebuchet MS', Verdana, Arial;'>";
						
							String fetchmsgs="select message,creation_date,from_id,to_id from batterywale_livechat_messages where agent_id="+agentid+" and user_id="+id+" order by creation_date asc";
							Vector vectfetchmsgs=qm.executeQuery(fetchmsgs);
							LogLevel.DEBUG(5, new Throwable(),"vectfetchmsgs: "+vectfetchmsgs );
							if(vectfetchmsgs==null || vectfetchmsgs.size() == 0)
							{

							}
							else
							{
								for(int j=0; j<vectfetchmsgs.size();j++)
								{
									Hashtable htfetchMsgs=(Hashtable)vectfetchmsgs.get(j);
									String message=(String)htfetchMsgs.get("message");
									LogLevel.DEBUG(5, new Throwable(),"message: "+message );

									String creationdate=String.valueOf(htfetchMsgs.get("creation_date"));
									String from_id=String.valueOf(htfetchMsgs.get("from_id"));
									String to_id=String.valueOf(htfetchMsgs.get("to_id"));
									String fetchnames= "select from_name,to_name from batterywale_livechat_messages where from_id='"+from_id+"' and to_id='"+to_id+"'";

									Hashtable htfetchnames = qm.getRow(fetchnames);
									String Firstname=(String)htfetchnames.get("from_name");
								
									String username=(String)htfetchnames.get("to_name");
									LogLevel.DEBUG(5, new Throwable(),"username: "+username );								
									
								
									strRes=strRes+"<table cellspacing='0' cellpadding='0' border='0' width='100%' style='padding-bottom:15px;table-layout:fixed;'><tr><td class='insidechatcontent' style='word-wrap: break-word;' valign='top'><b>"+Firstname+":</b>"+message+"</td></tr></table>"+"";
								
									}
								}
								strRes=strRes+"</div>";
									strRes=strRes+"<div align='left' style='border:1px solid #CCCCCC; background:#FFFFFF;min-height: 30px;max-height: 50px;overflow-x: hidden;overflow-y: auto;outline:none;width:100%;'><div id='chatmessagetext' style='height:30px;width:95%;'><textarea  rows='2'  id='"+id+"_ChatText'"+" type='text' style=' border:0px;height:30px;width:97%; overflow-y:auto;'  onkeypress='return checkForEnterA(event,"+id+",\""+consumername+"\")'/></textarea></div></div>";
								strRes=strRes+"</div></div>";
							}
					}
				else
				{					
							String id=userid; 
							LogLevel.DEBUG(5, new Throwable(),"id: "+id );
							String	fetchusername="select from_name from batterywale_livechat_messages where to_id='"+agentid+"' and from_id='"+id+"'";
							Hashtable htusernames = qm.getRow(fetchusername);
							consumername=(String)htusernames.get("from_name");
							LogLevel.DEBUG(5, new Throwable(),"consumername: "+consumername );

							strRes=strRes+"<div id= "+id+" class='fbchatdiv' style='width:240px;bottom:0px;direction:ltr;position:relative;display:inline-block;margin-right:2px;margin-left:2px;'>";
							strRes=strRes+"<div id='"+id+"_ChatCountContainer' style='display:none;width:20px;position:absolute;right:35px;height:16px;top:-5px;border:1px solid #FF66FF;background-color:#FF99FF;text-align:center;'><span  id='"+id+"_ChatCount' >0</span></div><div id='"+id+"_ChatHeader' align='left' style='width:230px;border: 1px solid #CCCCCC;background-color:#627BAE;padding:2px 5px 2px 5px;cursor:pointer;font: 11px 'Trebuchet MS', Verdana, Arial;'><span style='display:inline-block;width:200px;white-space:nowrap;overflow-x:hidden;'><font color='white' face='verdana' size='2'><b>"+consumername+"</b></font></span><span id='"+id+"_MsgSpan' style='display:none;'></span><span onclick='aChatdivClose(\""+id+"\");'style='float:right;' ><font color='white' face='verdana' size='2'><b>x</b></font></span></div><div id='"+id+"_Chatlist' style='width:240px;min-height:230px;max-height:230px;overflow-y:hidden;overflow-x:hidden;padding-right:15px;'>";
							strRes=strRes+"<div id='"+id+"_ChatMessages' style='text-align:left;word-wrap:break-word;background-color:#EDEFF4;border-left:1px solid #CCCCCC;border-right: 1px solid #CCCCCC;width:240px;min-height:196px;max-height:196px;overflow-y:auto;overflow-x:hidden;padding-top:15px;font: 11px 'Trebuchet MS', Verdana, Arial;'>";
						
							String fetchmsgs="select message,creation_date,from_id,to_id from batterywale_livechat_messages where agent_id="+agentid+" and user_id="+id+" order by creation_date asc";
							Vector vectfetchmsgs=qm.executeQuery(fetchmsgs);
							LogLevel.DEBUG(5, new Throwable(),"vectfetchmsgs: "+vectfetchmsgs );
							if(vectfetchmsgs==null || vectfetchmsgs.size() == 0)
							{
							}
							else
							{
								for(int j=0; j<vectfetchmsgs.size();j++)
								{
									Hashtable htfetchMsgs=(Hashtable)vectfetchmsgs.get(j);
									String message=(String)htfetchMsgs.get("message");
									LogLevel.DEBUG(5, new Throwable(),"message: "+message );

									String creationdate=String.valueOf(htfetchMsgs.get("creation_date"));
									String from_id=String.valueOf(htfetchMsgs.get("from_id"));
									String to_id=String.valueOf(htfetchMsgs.get("to_id"));
									String fetchnames= "select from_name,to_name from batterywale_livechat_messages where from_id='"+from_id+"' and to_id='"+to_id+"'";

									Hashtable htfetchnames = qm.getRow(fetchnames);
									String Firstname=(String)htfetchnames.get("from_name");
								
									String username=(String)htfetchnames.get("to_name");
									LogLevel.DEBUG(5, new Throwable(),"username: "+username );								
									
									strRes=strRes+"<table cellspacing='0' cellpadding='0' border='0' width='100%' style='padding-bottom:15px;table-layout:fixed;'><tr><td class='insidechatcontent' style='word-wrap: break-word;' valign='top'><b>"+Firstname+":</b>"+message+"</td></tr></table>"+"";
								
									}
								}
								strRes=strRes+"</div>";
									strRes=strRes+"<div align='left' style='border:1px solid #CCCCCC; background:#FFFFFF;min-height: 30px;max-height: 50px;overflow-x: hidden;overflow-y: auto;outline:none;width:100%;'><div id='chatmessagetext' style='height:30px;width:95%;'><textarea  rows='2'  id='"+id+"_ChatText'"+" type='text' style=' border:0px;height:30px;width:97%; overflow-y:auto;'  onkeypress='return checkForEnterA(event,"+id+",\""+consumername+"\")'/></textarea></div></div>";
								strRes=strRes+"</div></div>";

							
					}
					LogLevel.DEBUG(5, new Throwable(),"strRes: "+strRes );
					out.println(strRes);
			}
			catch(Exception e)
			{ 
				LogLevel.ERROR(0,e,"problem Caught Exception while chat agent login !! "+e);
				e.printStackTrace(); 
				session.setAttribute("seschatagentErrorMsg",	"Error...Please Try Again" );
			}
		}
		else if(strWhatToDo.equalsIgnoreCase("fetchconsumerhistory"))
		{
			String agentid = (request.getParameter("agentid")!=null)?(request.getParameter("agentid")):"0";
			String userid = (request.getParameter("userid")!=null)?(request.getParameter("userid")):"0";
			String agentname = (request.getParameter("agentname")!=null)?(request.getParameter("agentname")):"0";
			String customername = (request.getParameter("customername")!=null)?(request.getParameter("customername")):"0";
			String version = (request.getParameter("version")!=null)?(request.getParameter("version")):"0";
			String strRes="";
			try
			{
				String fetchmsgs="select message,creation_date,from_id,to_id from batterywale_livechat_messages where agent_id="+agentid+" and user_id="+userid+" order by creation_date asc";
				Vector vectfetchmsgs=qm.executeQuery(fetchmsgs);
				if(vectfetchmsgs==null || vectfetchmsgs.size() == 0)
				{
				}
				else
				{
					String dummyfromname="";
					String agentcount="";
					String consumercount="";
					for(int j=0; j<vectfetchmsgs.size();j++)
					{
						Hashtable htfetchMsgs=(Hashtable)vectfetchmsgs.get(j);
						String message=(String)htfetchMsgs.get("message");
						String creationdate=String.valueOf(htfetchMsgs.get("creation_date"));
						String from_id=String.valueOf(htfetchMsgs.get("from_id"));
						String to_id=String.valueOf(htfetchMsgs.get("to_id"));
						String fetchnames= "select from_name,to_name from batterywale_livechat_messages where from_id='"+from_id+"'";
						Hashtable htfetchnames = qm.getRow(fetchnames);
						String fromname=(String)htfetchnames.get("from_name");
						String toname=(String)htfetchnames.get("to_name");
					
					   String time=creationdate.substring(creationdate.indexOf(' ') + 1);
					 
						if (time.endsWith(".0")) 
						{
							time = time.substring(0, time.length() - 2);
						}
					   LogLevel.DEBUG(5, new Throwable(),"time: " + time);
					
					   if(version.equals("mobile"))
						{
						   if(agentname.equals(fromname))
						{					
						
							if(agentcount.equals("0"))
							{
								LogLevel.DEBUG(5, new Throwable(),"agentif: " );
								  if(message.indexOf("Customer from")>=0)
									{
										
									}
									else
									{									
										strRes=strRes+"<table id='userdetllls' width='100%' border='0' bgcolor='#FFFFFF' style='table-layout:fixed;padding-bottom:2px;'><tr><div align='justify'><td align='left' width='70%' style='word-wrap: break-word;' class='insidechatcontent'>"+message+"</td></div><td width='30%' align='right' class='chatcontent' valign='bottom'>"+time+"</td></tr></table>";
									}
							}
							else
							{
								LogLevel.DEBUG(5, new Throwable(),"agentelse: " );
								 if(message.indexOf("Customer from")>=0)
									{
										
									}
									else
									{		
										strRes=strRes+"<table  border='0' style='width:100%; padding-bottom:2px;'><tr><td align='left'><font color='#969798' face='verdana' size='2'>"+fromname+"</font></td></tr></table><table id='userdetllls' width='100%' border='0' bgcolor='#FFFFFF' style='table-layout:fixed;padding-bottom:2px;'><tr><div align='justify'><td align='left' width='70%' style='word-wrap: break-word;' class='insidechatcontent'>"+message+"</td></div><td width='30%' align='right' class='chatcontent' valign='bottom'>"+time+"</td></tr></table>";
									}
							}
							agentcount="0";
							consumercount="0";	
							LogLevel.DEBUG(5, new Throwable(),"agentcount:"+agentcount  );
							LogLevel.DEBUG(5, new Throwable(),"consumercount: "+consumercount );
						}
						else
						{
								
							if(consumercount.equals("1"))
							{
								LogLevel.DEBUG(5, new Throwable(),"consumerif: " );
								 if(message.indexOf("Customer from")>=0)
									{
										
									}
									else
									{		
										strRes=strRes+"<table id='userdetllls' width='100%' border='0' bgcolor='#F0F8FF' style='table-layout:fixed;padding-bottom:2px;'><tr><div align='justify'><td align='left' width='70%' style='word-wrap: break-word;' class='insidechatcontent'>"+message+"</td></div><td width='30%' align='right' class='chatcontent' valign='bottom'>"+time+"</td></tr></table>";
									}
							}
							else
							{
								LogLevel.DEBUG(5, new Throwable(),"consumerelse: " );
								 if(message.indexOf("Customer from")>=0)
									{
										
									}
									else
									{		
										strRes=strRes+"<table border='0' style='width:100%; padding-bottom:2px;'><tr><td align='left'><font color='#COCOCO' face='verdana' size='2'>"+customername+"</font></td></tr></table><table id='userdetllls' width='100%' border='0' bgcolor='#F0F8FF' style='table-layout:fixed;padding-bottom:2px;'><tr><div align='justify'><td align='left' width='70%' style='word-wrap: break-word;' class='insidechatcontent'>"+message+"</td></div><td width='30%' align='right' class='chatcontent' valign='bottom'>"+time+"</td></tr></table>";
									}
							}
							agentcount="1";
							consumercount="1";
								LogLevel.DEBUG(5, new Throwable(),"agentcount:"+agentcount  );
							LogLevel.DEBUG(5, new Throwable(),"consumercount: "+consumercount );
						}

						}
						else
						{
						
						if(agentname.equals(fromname))
						{					
						
							if(agentcount.equals("0"))
							{
								LogLevel.DEBUG(5, new Throwable(),"agentif: " );
								 if(message.indexOf("Customer from")>=0)
									{
										
									}
									else
									{		
										strRes=strRes+"<table id='userdetllls' width='100%' border='0' bgcolor='#FFFFFF' style='table-layout:fixed;padding-bottom:2px;'><tr><div align='justify'><td align='left' width='80%' style='word-wrap: break-word;' class='insidechatcontent'>"+message+"</td></div><td width='20%' align='right' class='chatcontent' valign='bottom'>"+time+"</td></tr></table>";
									}
							}
							else
							{
								LogLevel.DEBUG(5, new Throwable(),"agentelse: " );
								 if(message.indexOf("Customer from")>=0)
									{
										
									}
									else
									{		
										strRes=strRes+"<table  border='0' style='width:100%; padding-bottom:2px;'><tr><td align='left'><font color='#969798' face='verdana' size='2'>"+fromname+"</font></td></tr></table><table id='userdetllls' width='100%' border='0' bgcolor='#FFFFFF' style='table-layout:fixed;padding-bottom:2px;'><tr><div align='justify'><td align='left' width='80%' style='word-wrap: break-word; class='insidechatcontent'>"+message+"</td></div><td width='20%' align='right' class='chatcontent' valign='bottom'>"+time+"</td></tr></table>";
									}
							}
							agentcount="0";
							consumercount="0";	
							LogLevel.DEBUG(5, new Throwable(),"agentcount:"+agentcount  );
							LogLevel.DEBUG(5, new Throwable(),"consumercount: "+consumercount );
						}
						else
						{
								
							if(consumercount.equals("1"))
							{
								LogLevel.DEBUG(5, new Throwable(),"consumerif: " );
								 if(message.indexOf("Customer from")>=0)
									{
										
									}
									else
									{		
										strRes=strRes+"<table id='userdetllls' width='100%' border='0' bgcolor='#F0F8FF' style='table-layout:fixed;padding-bottom:2px;'><tr><div align='justify'><td align='left' width='80%' style='word-wrap: break-word;' class='insidechatcontent'>"+message+"</td></div><td width='20%' align='right' class='chatcontent' valign='bottom'>"+time+"</td></tr></table>";
									}
							}
							else
							{
								LogLevel.DEBUG(5, new Throwable(),"consumerelse: " );
								 if(message.indexOf("Customer from")>=0)
									{
										
									}
									else
									{		
										strRes=strRes+"<table border='0' style='width:100%; padding-bottom:2px;'><tr><td align='left'><font color='#COCOCO' face='verdana' size='2'>"+customername+"</font></td></tr></table><table id='userdetllls' width='100%' border='0' bgcolor='#F0F8FF' style='table-layout:fixed;padding-bottom:2px;'><tr><div align='justify'><td align='left' width='80%' style='word-wrap: break-word;text-align:justify;' class='insidechatcontent'>"+message+"</td></div><td width='20%' align='right' class='chatcontent' valign='bottom'>"+time+"</td></tr></table>";
									}
							}
							agentcount="1";
							consumercount="1";
								LogLevel.DEBUG(5, new Throwable(),"agentcount:"+agentcount  );
							LogLevel.DEBUG(5, new Throwable(),"consumercount: "+consumercount );
						}
						}
					}
					
				}
				out.println(strRes);
			}
			catch(Exception e)
			{ 
				LogLevel.ERROR(0,e,"problem Caught Exception while chat agent login !! "+e);
				e.printStackTrace(); 
				session.setAttribute("seschatagentErrorMsg",	"Error...Please Try Again" );
			}
		}
		
	}
}
