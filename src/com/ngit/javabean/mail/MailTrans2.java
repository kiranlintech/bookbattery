package com.ngit.javabean.mail; 

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
 
public class MailTrans2 implements java.io.Serializable 
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
		
		if(args.length < 5)
		{
			System.out.println("Invalid argumets.");
			System.out.println("Usage: java MailTrans <smtphost> <fromaddr> <toaddr> <subject without spaces> <message without spaces>");
			System.exit(0);
		}


		try{
			MailTrans2 mtrans1=new MailTrans2();
			mtrans1.setStrSmtpHost(args[0]);
			mtrans1.setStrFrom(args[1]);
			mtrans1.setStrTo(args[2]);
			mtrans1.setStrSubject(args[3]);
 			mtrans1.setStrText(args[4]);

 
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
		  strSmtpHost = "mail.meraopinion.com";

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
    
    // Replace sender@example.com with your "From" address.
    // This address must be verified.
     final String FROM = "helpdesk.bookbattery@gmail.com";
     final String FROMNAME = "BookBattery";
	
    // Replace recipient@example.com with a "To" address. If your account 
    // is still in the sandbox, this address must be verified.
     final String TO = "bharath@bookbattery.com";
    
    // Replace smtp_username with your Amazon SES SMTP user name.
     final String SMTP_USERNAME = "AKIAWWAO7R34N5MCRZQP";
    
    // Replace smtp_password with your Amazon SES SMTP password.
     final String SMTP_PASSWORD = "BG+g35ACp+uzMP7pt+lVOvRW0GzLfsSlTQ0Mx3KUAvKx";
    
    // The name of the Configuration Set to use for this message.
    // If you comment out or remove this variable, you will also need to
    // comment out or remove the header below.
     final String CONFIGSET = "ConfigSet";
    
    // Amazon SES SMTP host name. This example uses the US West (Oregon) region.
    // See https://docs.aws.amazon.com/ses/latest/DeveloperGuide/regions.html#region-endpoints
    // for more information.
     final String HOST = " ";
    
    // The port you will connect to on the Amazon SES SMTP endpoint. 
     final int PORT = 587;
    
     final String SUBJECT = "Amazon SES test (SMTP interface accessed using Java)";
    
     final String BODY = String.join(
    	    System.getProperty("line.separator"),
    	    "<h1>Amazon SES SMTP Email Test</h1>",
    	    "<p>This email was sent with Amazon SES using the ", 
    	    "<a href='https://github.com/javaee/javamail'>Javamail Package</a>",
    	    " for <a href='https://www.java.com'>Java</a>."
    	);
 
    	// Creating Properties object
         // Create a Properties object to contain connection configuration information.
		 Properties props = new Properties();
		 props.put("mail.transport.protocol", "smtp");
		 props.put("mail.smtp.port", PORT); 
		 props.put("mail.smtp.starttls.enable", "true");
		 props.put("mail.smtp.auth", "true");
 
		 // Create a Session object to represent a mail session with the specified properties. 
		 Session session = Session.getDefaultInstance(props);
 
		 // Create a message with the specified information. 
		 MimeMessage msg = new MimeMessage(session);
		 //msg.setFrom(address);
		 /*msg.setFrom(new InternetAddress(FROM,FROMNAME));
		 msg.setRecipient(Message.RecipientType.TO, new InternetAddress(TO));
		 msg.setSubject(SUBJECT);
		 msg.setContent(BODY,"text/html");
		 
		 // Add a configuration set header. Comment or delete the 
		 // next line if you are not using a configuration set
		 msg.setHeader("X-SES-CONFIGURATION-SET", CONFIGSET);*/
			 
		 // Create a transport.
		// Transport transport = session.getTransport();

        // Send the message.
        try
        {
            System.out.println("Sending...");
            
            // Connect to Amazon SES using the SMTP username and password you specified above.
            //transport.connect(HOST, SMTP_USERNAME, SMTP_PASSWORD);
        	
            // Send the email.
            //transport.sendMessage(msg, msg.getAllRecipients());
			System.out.println("Email sent!");
			return 1;
        }
        catch (Exception ex) {
            System.out.println("The email was not sent.");
			System.out.println("Error message: " + ex.getMessage());
			return -1;
        }
        finally
        {
            // Close and terminate the connection.
            //transport.close();
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

