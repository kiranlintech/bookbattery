/***********************************************************************		
	NGIT Confidential. 
	@File Name   : SendSMS.java 
	@Description : This trans is for Sending SMS.
	@Date        : 20th March 2013
******************************************************************/ 
package com.ngit.javabean.sendsms;
import com.ngit.javabean.loglevel.LogLevel;
import com.ngit.javabean.qrymgr.QueryManager;
import java.net.Socket;
import java.io.*;
/**
 *
 * @author Hari Krishna.L
 */
public class SendSMS 
{
    QueryManager qmgr;
    public SendSMS(QueryManager qm) 
	{
        qmgr=qm;
    }
	/*This method is to Send SMS to the USER*/	
	public static String sendSMS(String strIpAddress,String strPort,String strFromAddress,String strMessage,String ToAddress)
	{
		try
		{
			int intPort = Integer.parseInt(strPort);
			String strMessageType = "SMS";
			String strRequest = "Command:"+strMessageType+"\nFrom:"+strFromAddress+"\nMessage:"+strMessage+"\nTo:"+ToAddress+"\n";
			LogLevel.DEBUG(1, new Throwable(), "strRequest:"+strRequest);
			LogLevel.DEBUG(5, new Throwable(), "strRequest:"+strRequest);

			byte[] bytestr = null;
			byte[] response = new byte[1024];
			bytestr = strRequest.getBytes("ISO-8859-1");

			Socket soc = new Socket(strIpAddress , intPort);
			OutputStream os = soc.getOutputStream();
			InputStream is = soc.getInputStream();

			int d;
			int k = 0;
			os.write(bytestr);
			os.flush();

			while ( ( d = is.read() ) != -1 )
			{
				response[k++] = ( byte )d;
			}
			is.close();
			os.close();
			soc.close();
			String strResponse = new String(response);
			LogLevel.DEBUG(1, new Throwable(), "Response is :"+strResponse);
			return strResponse;
		}
		catch(Exception e)
		{
				e.printStackTrace();
				return "ERROR";
		}
	}
	/*This method is to Send SMS to the USER*/	
	public static String sendSMS2(String strIpAddress,String strPort,String strFromAddress,String strMessage,String ToAddress)
	{
		try
		{
			int intPort = Integer.parseInt(strPort);
			String strMessageType = "SMS";
			String strRequest = "Command:"+strMessageType+"\nFrom:"+strFromAddress+"\nMessage:"+strMessage+"\nTo:"+ToAddress+"\n";
			LogLevel.DEBUG(1, new Throwable(), "strRequest:"+strRequest);

			byte[] bytestr = null;
			byte[] response = new byte[1024];
			bytestr = strRequest.getBytes("ISO-8859-1");

			Socket soc = new Socket(strIpAddress , intPort);
			OutputStream os = soc.getOutputStream();
			InputStream is = soc.getInputStream();

			int d;
			int k = 0;
			os.write(bytestr);
			os.flush();

			while ( ( d = is.read() ) != -1 )
			{
					response[k++] = ( byte )d;
			}
			is.close();
			os.close();
			soc.close();
			String strResponse = new String(response);
			LogLevel.DEBUG(1, new Throwable(), "Response:"+strResponse);
			return strResponse;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return "ERROR";
		}
	}
	/*This method is to Send SMS to the USER*/	
	public static String sendSMS3(String strIpAddress,String strPort,String strFromAddress,String strMessage,String ToAddress)
    {
		try
		{
			int intPort = Integer.parseInt(strPort);
			String strMessageType = "SMS";
			String strRequest = "Command:"+strMessageType+"\nFrom:"+strFromAddress+"\nMessage:"+strMessage+"\nTo:"+ToAddress+"\n";
			LogLevel.DEBUG(1, new Throwable(), "strRequest is:"+strRequest);

			byte[] bytestr = null;
			byte[] response = new byte[1024];
			bytestr = strRequest.getBytes("ISO-8859-1");

			Socket soc = new Socket(strIpAddress , intPort);
			OutputStream os = soc.getOutputStream();
			InputStream is = soc.getInputStream();

			int d;
			int k = 0;
			os.write(bytestr);
			os.flush();

			while ( ( d = is.read() ) != -1 )
			{
					response[k++] = ( byte )d;
			}
			is.close();
			os.close();
			soc.close();
			String strResponse = new String(response);
			LogLevel.DEBUG(1, new Throwable(), "Response is :"+strResponse);
			return strResponse;

		}
		catch(Exception e)
		{
			e.printStackTrace();
			return "ERROR";
		}
    }
}//end of Class