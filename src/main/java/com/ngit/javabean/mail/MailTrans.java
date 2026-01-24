package com.ngit.javabean.mail; 

import java.util.Properties;
import java.util.StringTokenizer;
import java.util.Vector;
import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import com.ngit.javabean.loglevel.LogLevel;
import javax.mail.*; 
import javax.mail.internet.*; 
import javax.activation.*;
 
public class MailTrans implements java.io.Serializable 
{  
 private String strSmtpHost="";
 private String strFrom="";
 private String strTo="";
 private String strCC="";
 private String strBCC="";
 private String strSubject="";
 private String strFilename="";
 private String strText="";
 private String strFilesToAttach="";

	public static void main(String args[])
	{
		
	 LogLevel.DEBUG(5, new Throwable(), "args.length :" + args.length);
		
		if(args.length < 5)
		{
			System.out.println("Invalid argumets.");
			System.out.println("Usage: java MailTrans <smtphost> <fromaddr> <toaddr> <subject without spaces> <message without spaces>");
			System.exit(0);
		}
		
		 LogLevel.DEBUG(5, new Throwable(), "args[0] :" + args[0]);
		 LogLevel.DEBUG(5, new Throwable(), "args[1] :" + args[1]);
		 LogLevel.DEBUG(5, new Throwable(), "args[2] :" + args[2]);
		 LogLevel.DEBUG(5, new Throwable(), "args[3] :" + args[3]);
		 LogLevel.DEBUG(5, new Throwable(), "args[4] :" + args[4]);
		 LogLevel.DEBUG(5, new Throwable(), "args[5] :" + args[5]);


		try{
			MailTrans mtrans1=new MailTrans();
			mtrans1.setStrSmtpHost(args[0]);
			mtrans1.setStrFrom(args[1]);
			mtrans1.setStrTo(args[2]);
			mtrans1.setStrSubject(args[3]);
 			mtrans1.setStrText(args[4]);
 			mtrans1.setStrFilesToAttach(args[5]);
			
 
			mtrans1.sendMail("");


		}catch(Exception e)
		{
		}
	}


// getter and setter methods
	public void setStrSmtpHost(String strSmtpHost)    
	{    
		this.strSmtpHost=strSmtpHost;    
	}    
	public String getStrSmtpHost()    
	{    
		return strSmtpHost;    
	} 

	public void setStrFrom(String strFrom)    
	{    
		this.strFrom=strFrom;    
	}    
	public String getStrFrom()    
	{    
		return strFrom;    
	} 


    public void setStrTo(String strTo)    
	{    
		this.strTo=strTo;    
	}    
	public String getStrTo()    
	{    
		return strTo;    
	} 


	public void setStrCC(String strCC)    
	{    
		this.strCC=strCC;    
	}    
	public String getStrCC()    
	{    
		return strCC;    
	} 


	public void setStrBCC(String strBCC)    
	{    
		this.strBCC=strBCC;    
	}    
	public String getBCC()    
	{    
		return strBCC;    
	} 


	public void setStrSubject(String strSubject)    
	{    
		this.strSubject=strSubject;    
	}    
	public String getSubject()    
	{    
		return strSubject;    
	} 


	public void setStrFilename(String strFilename)    
	{    
		this.strFilename=strFilename;    
	}    
	public String getStrFilename()    
	{    
		return strFilename;    
	} 


	public void setStrText(String strText)    
	{    
		this.strText=strText;    
	}    
	public String getStrText()    
	{    
		return strText;    
	}

	

	
	public void setStrFilesToAttach(String strFilesToAttach)    
	{    
		this.strFilesToAttach=strFilesToAttach;    
	}    
	public String getStrFilesToAttach()    
	{    
		return strFilesToAttach;    
	}
  
  
  /** 
  * This method sends mail to the reciepients specified
  */
 
/*public int sendMail(String pathLocal)
{
     boolean debug=true;
     try
	 {
		System.out.println("// Create the JavaMail session");
		Properties properties = System.getProperties();
		if (strSmtpHost == null)
		  //strSmtpHost = "mail.meraopinion.com";
		  strSmtpHost = "mail.net4india.com";

		properties.put("mail.smtp.host", strSmtpHost);
	       
		
		Session session = Session.getDefaultInstance( properties, null );



		System.out.println("// Construct the message");
		MimeMessage message = new MimeMessage(session);

		System.out.println("// Set the from address");
		Address fromAddress = new InternetAddress(strFrom);
		message.setFrom(fromAddress);

		System.out.println("// Parse and set the recipient addresses");
		Address[] toAddresses = InternetAddress.parse(strTo);
		message.setRecipients(Message.RecipientType.TO,toAddresses);
		
		Address[] ccAddresses = InternetAddress.parse(strCC);
		message.setRecipients(Message.RecipientType.CC,ccAddresses);

		
		System.out.println("// Set the subject and text");
	    message.setSubject(strSubject);

		System.out.println("// Attach file with message if any");
		if(!strFilesToAttach.equals(""))
		{
			
			StringTokenizer st=new StringTokenizer(strFilesToAttach,":");
			System.out.println("// add the fiels to messade --:"+strFilesToAttach);

			System.out.println("// create and fill the first message part");
			MimeBodyPart mbp1 = new MimeBodyPart();
			mbp1.setText(strText);

			if(debug)System.out.println("// create the Multipart and its parts to it");
			Multipart mp = new MimeMultipart();
			mp.addBodyPart(mbp1);
			
			while(st.hasMoreElements())
			{
				
				String filename=(String)st.nextElement();
		                filename=pathLocal;
				if(debug)System.out.println("// attaching :"+filename);
				File file = new File(filename);
				if (file.exists())
				{
					if(debug)System.out.println("file size :"+file.length());

					if(debug)System.out.println("// create the second message part");
					MimeBodyPart mbp2 = new MimeBodyPart();

					if(debug)System.out.println("// attach the file to the message");
					FileDataSource fds = new FileDataSource(filename);
					mbp2.setDataHandler(new DataHandler(fds));
					mbp2.setFileName(fds.getName());
                     
                    if(debug)System.out.println("// add the mimebodypart to the multipart");
					mp.addBodyPart(mbp2);
					
				}
			}
			if(debug)System.out.println("// add the Multipart to the message");
			message.setContent(mp);
		 }
		 else
		 {
		      message.setText(strText);
         }
		 if(debug)System.out.println("// send the message");
		 Transport.send(message);
		 return 1;
	}
	catch(Exception e)
	{  
	   e.printStackTrace();
	   return -1;

	} 
  }*/

public int sendMail(String pathLocal)
{
     boolean debug=true;
		//String to = "bharath@bookbattery.com";//change accordingly  
		final String from = "BookBattery<helpdesk.bookbattery@gmail.com>";
		final String pass = "Beeky@7394";
	 
	 
		// Defining the gmail host
		//String host = "smtp.net4india.com";
		 String host = "smtp.gmail.com";
		
	 
		// Creating Properties object
		//Properties props = new Properties();
	 
		// Defining properties
		/*props.put("mail.smtp.host", host);
		props.put("mail.transport.protocol", "smtp");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.user", from);
		props.put("mail.password", pass);
		props.put("mail.port", "587");*/
		
		
		
		Properties props = new Properties();
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.port", 587);
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.ssl.protocols", "TLSv1.2");
		props.put("mail.smtp.starttls.enable", "true");
     
   Session session = Session.getDefaultInstance(props,  
    new javax.mail.Authenticator() {  
      protected PasswordAuthentication getPasswordAuthentication() {  
			return new PasswordAuthentication("helpdesk.bookbattery@gmail.com", "oitj seba heyc dtpy");
      }  
    });  

     try
	 {
		System.out.println("// Create the JavaMail session");
		//Properties properties = System.getProperties();
		if (host == null)		  
		  host = "smtp.gmail.com";

		//properties.put("mail.smtp.host", host);
	       
		//Session session = Session.getDefaultInstance( properties, null );
		//Session session = Session.getDefaultInstance(properties);  

		System.out.println("// Construct the message");
		MimeMessage message = new MimeMessage(session);

		System.out.println("// Set the from address");
		//Address fromAddress = new InternetAddress(from);
		Address fromAddress = new InternetAddress(strFrom);
		message.setFrom(fromAddress);

		System.out.println("// Parse and set the recipient addresses");
		//Address[] toAddresses = InternetAddress.parse(to);
		Address[] toAddresses = InternetAddress.parse(strTo);
		message.setRecipients(Message.RecipientType.TO,toAddresses);
		
		//Address[] ccAddresses = InternetAddress.parse(strCC);
		//message.setRecipients(Message.RecipientType.CC,ccAddresses);

		
		System.out.println("// Set the subject and text");
	    message.setSubject(strSubject);
	    //message.setSubject("This is Test Subject");
		
		LogLevel.DEBUG(5, new Throwable(), "Mail From :" +strFrom);
		LogLevel.DEBUG(5, new Throwable(), "Mail To :" +strTo);
		LogLevel.DEBUG(5, new Throwable(), "Mail Attach :" +strFilesToAttach);
		
		System.out.println("// Attach file with message if any");
		 if(!strFilesToAttach.equals(""))
		 {
					// Create the message body part
				 BodyPart messageBodyPart = new MimeBodyPart();

				 // Fill the message
				 messageBodyPart.setText(strText);
				 
				 // Create a multipart message for attachment
				 Multipart multipart = new MimeMultipart();

				 // Set text message part
				 multipart.addBodyPart(messageBodyPart);

				 // Second part is attachment
				 messageBodyPart = new MimeBodyPart();
				 String filename = strFilesToAttach;
				 //String filename = "/home/ngit/tomcat/webapps/bookbattery/userdata/billing/businessinvoices/2019/11/Bangalore/GOMechanic/ASIST-B2B-GOMechanic-1.pdf";
				 LogLevel.DEBUG(5, new Throwable(), "filename"+filename);
				 DataSource source = new FileDataSource(filename);
				 messageBodyPart.setDataHandler(new DataHandler(source));
				 //messageBodyPart.setFileName(filename);
				 messageBodyPart.setFileName(new File(filename).getName());
				 multipart.addBodyPart(messageBodyPart);
				 LogLevel.DEBUG(5, new Throwable(), "filename: "+filename);
				 // Send the complete message parts
				 message.setContent(multipart);
				 // Send message					
		 }
		 else
		 {
 		      message.setText(strText);
		      //message.setText("Hello, this is example of sending email");
         }
		 if(debug)System.out.println("// send the message");
		 Transport.send(message);
		 LogLevel.DEBUG(5, new Throwable(), "EMail Sent Successfully with attachment!!");
		 
		 return 1;
	}
	catch(Exception e)
	{  
	   e.printStackTrace();
	   return -1;

	} 
  }
  
  
public static String parseCClist(String strCCList,String domainlistfile)
{
    BufferedReader br;
	File domainlist=new File(domainlistfile);
	Vector domainnamelist=null;
	String newCCList="";
	if(domainlist.exists())
	{
		try{
			FileReader fr=new FileReader(domainlist);
			br=new BufferedReader(fr);
			String domain="";
			domainnamelist=new Vector();
			domain=br.readLine();
			while(domain!=null)
			{    
				 domainnamelist.add(domain.trim());
				 domain=br.readLine();
			}
		}
		catch(Exception e)
		{
			System.out.println("file not found exception:");
		}
	}
	if(domainnamelist!=null)
	{
		StringTokenizer st=new StringTokenizer(strCCList,",");
		while(st.hasMoreTokens())
		{
			String token=(String)st.nextElement();
			int index=token.indexOf("@");
			String strTemp=token.substring((index+1));
			if(domainnamelist.contains(strTemp))
			{
				if(newCCList.equals(""))
				{
					newCCList=token;
				}
				else
				{
					newCCList=newCCList+","+token;
				}

			}

		}
	}
	else
	{
		newCCList=strCCList;
	}
   return newCCList;
 }

}

