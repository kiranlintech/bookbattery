/********************************************************************************\
    NGIT. Confidential.
@File Name   : MailThread.java
@Description : To send the mail as a separate thread.
@Known Issues: 
@Author      : RamaMohanReddy [Date 13/03/09]
\*********************************************************************************/
package com.ngit.javabean.mail;

import com.ngit.javabean.mail.MailTrans;

import java.io.FileOutputStream;
import java.io.File;
public class  MailThread extends Thread
{
	MailTrans mailTrans;
	String pathLocal="";
  	public MailThread(MailTrans mt,String pl)
	{
		super("mail thread");
		mailTrans=mt;
		pathLocal=pl;
	}
	public MailThread()
	{
		mailTrans=new MailTrans();
	}
	public void run()
	{
		try
		{
			mailTrans.sendMail(pathLocal);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
