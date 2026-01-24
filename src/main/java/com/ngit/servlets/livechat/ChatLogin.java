/*
File Name           : ChatLogin.java 
Version information : 1.0
Date                : Saturday, January 18, 2014 9:56:30 AM
Copyright notice    : NGIT Private Ltd Confidential
*/
package com.ngit.servlets.livechat;
import com.ngit.javabean.qrymgr.QueryManager;
import com.ngit.javabean.loglevel.LogLevel;
import com.ngit.javabean.mail.*;
import com.ngit.javabean.sendsms.SendSMS;
import java.io.FileInputStream;
import java.util.Properties;
import java.util.Vector;
import java.util.Hashtable;
import java.util.ArrayList;
import java.util.Enumeration;
import java.io.File;
import java.io.IOException;
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
public class ChatLogin extends HttpServlet 
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
    protected synchronized void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
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
 		String strWhatToDo = (request.getParameter("hidWhatToDo")!=null)?request.getParameter("hidWhatToDo"):"";
		ServletOutputStream out=response.getOutputStream();
		String domainname =  propslivechatConfig.getProperty("domainname");
		String SupportEmailId =  propslivechatConfig.getProperty("SupportEmailId");
		String mailTo =  propslivechatConfig.getProperty("livechatadminemail");
		String supportname = propslivechatConfig.getProperty("supportname");

		String strIpAddress =(propslivechatConfig.getProperty("TRANSMITTER_IP_ADDR")!=null)?(propslivechatConfig.getProperty("TRANSMITTER_IP_ADDR")):"";
		String strPort=(propslivechatConfig.getProperty("TRANSMITTER_IP_PORT")!=null)?(propslivechatConfig.getProperty("TRANSMITTER_IP_PORT")):"9910";
		String SMSFromAddress=(propslivechatConfig.getProperty("SMSFromAddress")!=null)?(propslivechatConfig.getProperty("SMSFromAddress")):"";
		LogLevel.DEBUG(5,new Throwable(),"strWhatToDo:"+strWhatToDo);
		//This action is used for to user login into live chat
		if(strWhatToDo.equalsIgnoreCase("userChatLogin"))
		{ 			
			String username = (request.getParameter("chatname")!=null)?(request.getParameter("chatname")):"";
			String useremail = (request.getParameter("chatemail")!=null)?(request.getParameter("chatemail")):"";
			String strRes="";
			try
			{
				//query used to fetch chat agent details from livechat_agentlogin based on status
				String fetchagentdetls="select agentfirst_name,agentlast_name,agent_mobile_number,agent_picture_url,agent_id from batterywale_livechat_agentlogin where agent_status='active' order by agent_logintime desc";
				Hashtable htfetchagentdetls = qm.getRow(fetchagentdetls);
				String agentfirstname=(String)htfetchagentdetls.get("agentfirst_name");
				String agentpic=(String)htfetchagentdetls.get("agent_picture_url");
				String agentlastname=(String)htfetchagentdetls.get("agentlast_name");
				String agent_id=String.valueOf(htfetchagentdetls.get("agent_id"));
				String agent_mobnumber=String.valueOf(htfetchagentdetls.get("agent_mobile_number"));


				String chatagentusers = "select distinct count(agent_id) as count from batterywale_livechat_agentlogin where agent_status='active'";
				Hashtable htagentcount=qm.getRow(chatagentusers);
				String agentcount=String.valueOf(htagentcount.get("count"));
				LogLevel.DEBUG(5,new Throwable(),"agentcount:"+agentcount);

				if(agentcount.equals("null") || agentcount==null)
				{
					agentcount="0";
				}
				int agentscount = Integer.parseInt(agentcount);
				/*String count=(String)session.getAttribute("count");
				LogLevel.DEBUG(5, new Throwable(), "count : " + count);
				if(count.equals("null") || count==null)
				{
					count="0";
				}
					int userscount = Integer.parseInt(count);*/
				
				String agentname=agentfirstname+" "+agentlastname;
				LogLevel.DEBUG(5, new Throwable(), "strRes : " + strRes);
				String inlivechatusers = "select distinct count(chatuser_id) as count from batterywale_livechat_userlogin where user_status='active' and agent_id='"+agent_id+"'";
				Hashtable count=qm.getRow(inlivechatusers);
				String usercount=String.valueOf(count.get("count"));
				LogLevel.DEBUG(5,new Throwable(),"usercount:"+usercount);
				if(usercount.equals("null") || usercount==null)
				{
					usercount="0";
				}
				int userscount = Integer.parseInt(usercount);
				LogLevel.DEBUG(5,new Throwable(),"userscount:"+userscount);
				if (userscount>=3)
				{
					String updateagentstatus="update batterywale_livechat_agentlogin set agent_status='busy' where agent_id='"+agent_id+"'";
					qm.executeUpdate(updateagentstatus);
				}
				if(userscount>=4)
				{
					if (agentscount >1)
					{
					}
					else if (agentscount <= 1)
					{
						agentname="null null";agent_id="null";
					}
					else{agent_id="null";agentname="null null";}
				}
				
				//query used to insert user details into livechat_userlogin table
				if (agent_id == null || agent_id.equals("null"))
				{agent_id="0";}else{agent_id=agent_id;}

				String insertuserdetails= "insert into batterywale_livechat_userlogin(user_name,user_emailid,agent_id,creation_date,last_modified,created_by) values('"+username+"','"+useremail+"','"+agent_id+"',now(),now(),'ngit')";
				int insertresult = qm.executeUpdate(insertuserdetails);
				LogLevel.DEBUG(5,new Throwable(),"insertuserdetails:"+insertuserdetails);

			String message="User with Name- "+username+" Email Id- "+useremail+" has logged in to chat with "+agentname+" .";
			SendSMS sendsms = new SendSMS(qm);
			LogLevel.DEBUG(5,new Throwable(),"strIpAddress: "+strIpAddress);

			LogLevel.DEBUG(5,new Throwable(),"strPort: "+strPort);

			LogLevel.DEBUG(5,new Throwable(),"SMSFromAddress: "+SMSFromAddress);

			LogLevel.DEBUG(5,new Throwable(),"agent_mobnumber: "+agent_mobnumber);

			LogLevel.DEBUG(5,new Throwable(),"message: "+message);

			String strSMSResponse=  sendsms.sendSMS(strIpAddress,strPort,SMSFromAddress,message,agent_mobnumber);
			LogLevel.DEBUG(5,new Throwable(),"strSMSResponse: "+strSMSResponse);

				
				strRes=agentname+"|"+agentpic+"|"+agent_id+"|"+useremail+"|"+username+"";


				String userid="select chatuser_id from  batterywale_livechat_userlogin order by chatuser_id desc limit 1";
				LogLevel.DEBUG(5,new Throwable(),"userid:"+userid);
				ArrayList userID=qm.getField(userid);
				LogLevel.DEBUG(5,new Throwable(),"userID:"+userID);
				String  usrid = userID.toString().replace("[", "").replace("]", "");
				LogLevel.DEBUG(5,new Throwable(),"usrid:"+usrid);
				strRes=strRes+"|"+usrid;
				LogLevel.DEBUG(5,new Throwable(),"strResLast:"+strRes);
				out.println(strRes);
				return;
			}
			catch(Exception e)
			{
				LogLevel.ERROR(0,e,"Exception while livechat user login:"+e);
				e.printStackTrace();
			}
		}

		else if(strWhatToDo.equalsIgnoreCase("cookielogin"))
		{ 			
			String username = (request.getParameter("chatname")!=null)?(request.getParameter("chatname")):"";
			String useremail = (request.getParameter("chatemail")!=null)?(request.getParameter("chatemail")):"";
			String agentid = (request.getParameter("agentid")!=null)?(request.getParameter("agentid")):"";
			String strRes="";
			try
			{				

				//query used to fetch chat agent details from livechat_agentlogin based on status
				String fetchagentdetls="select agentfirst_name,agentlast_name,agent_picture_url,agent_id from batterywale_livechat_agentlogin where agent_status='active' order by agent_logintime desc";
				Hashtable htfetchagentdetls = qm.getRow(fetchagentdetls);
				String agentfirstname=(String)htfetchagentdetls.get("agentfirst_name");
				String agentpic=(String)htfetchagentdetls.get("agent_picture_url");
				String agentlastname=(String)htfetchagentdetls.get("agentlast_name");
				String agent_id=String.valueOf(htfetchagentdetls.get("agent_id"));
				String agentname=agentfirstname+" "+agentlastname;
				strRes=agentname+"|"+agentpic+"|"+agent_id+"|"+useremail+"|"+username+"";

				LogLevel.DEBUG(5, new Throwable(), "strRes : " + strRes);

				//query used to insert user details into livechat_userlogin table
				String insertuserdetails= "insert into batterywale_livechat_userlogin(user_name,user_emailid,agent_id,creation_date,last_modified,created_by) values('"+username+"','"+useremail+"','"+agent_id+"',now(),now(),'ngit')";
				int insertresult = qm.executeUpdate(insertuserdetails);

				String userid="select chatuser_id from  batterywale_livechat_userlogin order by chatuser_id desc limit 1";
				LogLevel.DEBUG(5,new Throwable(),"userid:"+userid);
				ArrayList userID=qm.getField(userid);
				LogLevel.DEBUG(5,new Throwable(),"userID:"+userID);
				String  usrid = userID.toString().replace("[", "").replace("]", "");
				LogLevel.DEBUG(5,new Throwable(),"usrid:"+usrid);
				strRes=strRes+"|"+usrid;
				LogLevel.DEBUG(5,new Throwable(),"strResLast:"+strRes);
				out.println(strRes);
				return;
			}
			catch(Exception e)
			{
				LogLevel.ERROR(0,e,"Exception while livechat user login:"+e);
				e.printStackTrace();
			}
		}

		//this action is used for user logout from live chat
		else if(strWhatToDo.equalsIgnoreCase("userChatLogout"))
		{ 			
			String username = (request.getParameter("chatname")!=null)?(request.getParameter("chatname")):"";
			String useremail = (request.getParameter("chatemail")!=null)?(request.getParameter("chatemail")):"";
			String rate = (request.getParameter("rate")!=null)?(request.getParameter("rate")):"";
			String agentid = (request.getParameter("agentid")!=null)?(request.getParameter("agentid")):"";
			String userid=(request.getParameter("userid")!=null)?(request.getParameter("userid")):"";
			String chatmobilenumber=(request.getParameter("chatmobilenumber")!=null)?(request.getParameter("chatmobilenumber")):"";
			LogLevel.DEBUG(5, new Throwable(), "chatmobilenumber : " + chatmobilenumber);
			String strRes="";
			try
			{
				//query used to update user status in livechat_userlogin table based on user name and user emailid
				String updateuserdetails= "update batterywale_livechat_userlogin set user_status='inactive' where user_name= '"+username+"' and user_emailid ='"+useremail+"'";
				int updateresult = qm.executeUpdate(updateuserdetails);
				LogLevel.DEBUG(5, new Throwable(), "updateuserdetails : " + updateuserdetails);

				Hashtable htfetchagentid =new Hashtable();
				htfetchagentid= qm.getRow("select agent_emailid from batterywale_livechat_agentlogin where agent_id='"+agentid+"'");
				String agent_email=(String)htfetchagentid.get("agent_emailid");
				//query used to insert chat agent rating
				String insertagentrtng= "insert into batterywale_agent_rating (agent_id,agent_emailid,user_email,user_name,user_id,user_rating,user_mobile_number,creation_date) values ('"+agentid+"','"+agent_email+"','"+useremail+"','"+username+"','"+userid+"','"+rate+"','"+chatmobilenumber+"',now())";
				int insertagentrtngresult = qm.executeUpdate(insertagentrtng);
				LogLevel.DEBUG(5, new Throwable(), "insertagentrtng : " + insertagentrtng);
				out.println(strRes);
				return;
			}
			catch(Exception e)
			{
				LogLevel.ERROR(0,e,"Exception while livechat user logout:"+e);
				e.printStackTrace();
			}
		}
		//this action is used to insert chat agent rating
		else if(strWhatToDo.equalsIgnoreCase("insertRating"))
		{ 			
			String rate = (request.getParameter("rate")!=null)?(request.getParameter("rate")):"";
			String agentid = (request.getParameter("agentid")!=null)?(request.getParameter("agentid")):"";
			String useremail =(request.getParameter("useremail")!=null)?(request.getParameter("useremail")):"";
			String username =(request.getParameter("username")!=null)?(request.getParameter("username")):"";
			String userid=(request.getParameter("userid")!=null)?(request.getParameter("userid")):"";
			String chatmobilenumber=(request.getParameter("chatmobilenumber")!=null)?(request.getParameter("chatmobilenumber")):"";
			LogLevel.DEBUG(5, new Throwable(), "chatmobilenumber : " + chatmobilenumber);
			String strRes="";
			try
			{
				Hashtable htfetchagentid =new Hashtable();
				htfetchagentid= qm.getRow("select agent_emailid from batterywale_livechat_agentlogin where agent_id='"+agentid+"'");
				String agent_email=(String)htfetchagentid.get("agent_emailid");
				
				//query used to insert chat agent rating
				String insertagentrtng= "insert into batterywale_agent_rating (agent_id,agent_emailid,user_email,user_name,user_id,user_rating,user_mobile_number,creation_date) values ('"+agentid+"','"+agent_email+"','"+useremail+"','"+username+"','"+userid+"','"+rate+"','"+chatmobilenumber+"',now())";
				int insertagentrtngresult = qm.executeUpdate(insertagentrtng);
				LogLevel.DEBUG(5, new Throwable(), "insertagentrtng : " + insertagentrtng);
				out.println(strRes);
				return;
			}
			catch(Exception e)
			{
				LogLevel.ERROR(0,e,"Exception while insert agent rating:"+e);
				e.printStackTrace();
			}
		}
		//this action is used to send email of enquiry message to livechat admin when all chat agents are busy
		else if(strWhatToDo.equalsIgnoreCase("sendEnquirymessage"))
		{ 			
			String username = (request.getParameter("chatname")!=null)?(request.getParameter("chatname")):"";
			String useremail = (request.getParameter("chatemail")!=null)?(request.getParameter("chatemail")):"";
			String usermessage = (request.getParameter("message")!=null)?(request.getParameter("message")):"";
			String userid = (request.getParameter("userid")!=null)?(request.getParameter("userid")):"0";

			usermessage = usermessage.replace("'","&#39;");usermessage = usermessage.replace(",","&#44;");
				usermessage = usermessage = usermessage.replace("|","&#124;");usermessage.replace("~","&#126;");usermessage = usermessage.replace("!","&#33;");usermessage = usermessage.replace("@","&#64;");usermessage = usermessage.replace("$","&#36;");usermessage = usermessage.replace("%","&#37;");usermessage = usermessage.replace("^","&#94;");usermessage = usermessage.replace("*","&#42;");usermessage = usermessage.replace("(","&#40;");usermessage = usermessage.replace(")","&#41;");usermessage = usermessage.replace("-","&#45;");usermessage = usermessage.replace("=","&#61;");usermessage = usermessage.replace("\\","&#92;");usermessage = usermessage.replace("}","&#125;");usermessage = usermessage.replace(",{","&#123;");usermessage = usermessage.replace("\"","&#34;");usermessage = usermessage.replace(":","&#58;");usermessage = usermessage.replace("?","&#63;");usermessage = usermessage.replace("`","&#96;");usermessage = usermessage.replace("-","&#95;");usermessage = usermessage.replace("+","&#43;");usermessage = usermessage.replace("]","&#93;");usermessage = usermessage.replace("[","&#91;");usermessage = usermessage.replace("'","&#39;");usermessage = usermessage.replace("/","&#47;");
			try
			{
				if (userid == null || userid.equals("null") || userid.equals("") || userid.equals(" "))
				{userid="0";}else{userid=userid;}
				String insertbusyMsg="insert into batterywale_livechat_busymessages (user_name,user_emailid,user_id,user_message,creation_date) values('"+username+"','"+useremail+"','"+userid+"','"+usermessage+"',now())";
				int insertbusyMsgresult = qm.executeUpdate(insertbusyMsg);
				LogLevel.DEBUG(5, new Throwable(), "insertbusyMsg : " + insertbusyMsg);
				String host = domainname;
				String mailFrom = SupportEmailId;
				String subject=""+supportname+" offline message received from "+useremail+"";
				String mailmessage="<table width='100%' bgcolor='#DFBAE3' cellspacing='0' cellpadding='0'><tr><td><table width='670' cellspacing='0' cellpadding='0' border='0' align='center' style='background:#fff;border:0;border-left:1px solid #ccc;border-right:1px solid #ccc;border-bottom:1px solid #ccc;border-top:0px solid #ccc;' ><tbody><tr height='77'><td><table width='670' height='80' cellspacing='0' cellpadding='0' border='0' style='background:#004586;table-layout:fixed;'><tbody><tr><td width='460' height='70' valign='top' style='font-family:'Helvetica Neue', Helvetica, Arial, sans-serif;color:#333;' ><table cellspacing='0' cellpadding='0' border='0'><tbody><tr height='60'><td style='font-family:'Segoe UI,sans-serif';color:white;letter-spacing:-.35pt;font-size:14px;font-weight:bold;color:#FFF;'> <span style='margin-left:2em'>"+supportname+"</span> </td></tr></tbody></table></td></tr></tbody></table></td><td rowspan='3'></td></tr><tr><td width='670' ><table width='670' cellspacing='0' align='center' cellpadding='0' border='0' style='background:#fff;font-family:'Helvetica Neue', Helvetica, Arial, sans-serif;'><tbody><tr><td style='width:85px;' ></td><td><table cellspacing='0' cellpadding='0' align='center' border='0'><tbody><tr><td style='padding:10px;font-family:Helvetica, Arial, sans-serif;color:#333;padding-bottom:0;font-size:15px;line-height:20px;padding-left:15px' align='left'><strong>"+supportname+" Offline Message Received</strong><BR><BR><font face='Helvetica, Arial, sans-serif' size='2'>Dear Admin,</font></td></tr><tr><td height='10'></td></tr><tr><td style='padding:10px;'><table cellspacing='0' cellpadding='0' border='0'><tbody><tr><td style='padding:10px;font-size:10.0pt;font-family:Helvetica, Arial, sans-serif;color:#000;padding-bottom:0;line-height:20px;padding-left:5px'>You have recieved an offline message, sent from "+supportname+" with the following details:</td></tr></tbody></table></td></tr><tr><td ><div style='border: 1px solid #DDDDDD;margin-left:1em;'><table cellspacing='0' cellpadding='0' width='482' style='padding-left:15px;background-color:#F5F5F5;'><tbody><tr><td style='padding-left:0px;'><table cellspacing='0' cellpadding='0' border='0' width='470' style='table-layout:fixed;table-layout:fixed;text-align:center;height:28px;'><tbody><tr><td align='right' width='100' style='width:75.0pt;padding:3.0pt 3.0pt 3.0pt 3.0pt color:#00000;font-size:13px;font-weight:bold;'><span style='font-size:10.0pt;font-family:Helvetica, Arial, sans-serif'>Name:</span></td><td align= 'left' style='padding:3.0pt 3.0pt 3.0pt 10.0pt'><span style='font-size:10.0pt;font-family:Helvetica, Arial, sans-serif'>"+username+"</span></td></tr><tr><td align='right' width='100' style='width:75.0pt;padding:3.0pt 3.0pt 3.0pt 3.0pt color:#00000;font-size:13px;font-weight:bold;'><span style='font-size:10.0pt;font-family:Helvetica, Arial, sans-serif'>Email:</span></td><td align= 'left' style='padding:3.0pt 3.0pt 3.0pt 10.0pt'><span style='font-size:10.0pt;font-family:Helvetica, Arial, sans-serif'>"+useremail+"</span></td></tr><tr><td align='right' width='100' style='width:75.0pt;padding:3.0pt 3.0pt 3.0pt 3.0pt color:#00000;font-size:13px;font-weight:bold;'><span style='font-size:10.0pt;font-family:Helvetica, Arial, sans-serif'>Message:</span></td><td align= 'left' width='50' style='padding:3.0pt 3.0pt 3.0pt 10.0pt;word-wrap: break-word;font-size:10.0pt;font-family:Helvetica, Arial, sans-serif;'>"+usermessage+"</td></tr></tbody></table> </td></tr></tbody></table></div></tr><tr></tr><tr><td height='25'></td></tr></tbody></table> </td><td style='width:85px;'></td></tr></tbody></table> </td></tr><tr><td width='670'><table width='670' cellspacing='0' cellpadding='0' border='0' bgcolor='#F1D3F5' style='background-color:#F1D3F5;background-position:top;background-repeat:repeat-x;border-top-color:#ddd;border-top-style:solid;border-top-width:1px;'><tbody><tr><td height='16'></td></tr><tr><td style='width:85px;'></td><td style='font-family:Arial;font-size:12px;line-height:17px;color:#000;text-shadow:0 1px 0 #fff;font-weight:400;'>Thanks and Regards,<br>"+supportname+" Team.</td><td></td></tr><tr><td height='25'colspan='3'></td></tr></tbody></table> </td></tr></tbody></table></td></tr></table>";
				SendHtmlMail sendemail= new SendHtmlMail();
				sendemail.HtmlEmail(host,mailFrom,mailTo,subject,mailmessage);
				out.println("sucessfully sent emailid");
				return;
			}
			catch(Exception e)
			{
				LogLevel.ERROR(0,e,"Exception while sending email:"+e);
				e.printStackTrace();
			}
		}
		
	}
}