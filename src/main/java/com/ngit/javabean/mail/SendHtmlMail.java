package com.ngit.javabean.mail;
import com.ngit.javabean.loglevel.LogLevel;
import javax.mail.*; 
import javax.mail.internet.*; 
import javax.mail.event.*;
import javax.mail.util.*;
import javax.activation.*;
import java.util.Properties;
import java.util.Date;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.MessagingException;

public class SendHtmlMail implements java.io.Serializable
{
	public static String HtmlEmail(String host, String mailFrom, String toAddress, String subject, String message)
	{
		boolean debug=true;
		try
		{
			Properties properties = System.getProperties();
			if (host == null)
			host = "mail.meraopinion.com";
			//set mail server
			properties.put("mail.smtp.host",host);
			// Get the default Session object.
			Session session = Session.getDefaultInstance( properties, null );
			// Create a default MimeMessage object.
			MimeMessage msg = new MimeMessage(session);
			// Set From address
			msg.setFrom(new InternetAddress(mailFrom));
			// Set To address
			InternetAddress[] toAddresses = { new InternetAddress(toAddress) };
			msg.setRecipients(Message.RecipientType.TO, toAddresses);
			// Set Subject
			msg.setSubject(subject);
			msg.setSentDate(new Date());
			LogLevel.DEBUG(5,new Throwable(),"host:"+host);
			LogLevel.DEBUG(5,new Throwable(),"mailFrom:"+mailFrom);
			LogLevel.DEBUG(5,new Throwable(),"toAddress:"+toAddress);
			//LogLevel.DEBUG(5,new Throwable(),"subject:"+subject,"message:"+message);
			LogLevel.DEBUG(5,new Throwable(),"message:"+message);
			// set the actual message
			msg.setContent(message, "text/html");
			if(debug)
			// sends the e-mail
			Transport.send(msg);
			 return "Successfully send email";
		}
		catch (Exception e)
		{
			LogLevel.DEBUG(1, e, "Problem Caught Exception While Sending HTML Email" + e);
		  e.printStackTrace();
		  return "Fail to send eamil";
		}
	}
}//end of class