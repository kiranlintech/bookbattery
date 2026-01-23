/* *******************************************************************************\
    NGIT Pvt.Ltd. Confidential.
    @File Name   : LogLevel.java
    @Description : To dump the Debug statements into .log files.
\*********************************************************************************/
package com.ngit.javabean.loglevel;

import java.io.OutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

public class LogLevel
{
        public static String logFile="bookbattery.log";
        public static int logLevel=1;
        public static String logPath="/home/ngit/tomcat/webapps/bookbattery/logs/";

        public static void setLogLevel(String strLogLevel, String strLogPath,String strLogFile)
        {
                try{
                        logLevel=Integer.parseInt(strLogLevel.trim());
                        logPath=strLogPath;
                        logFile=strLogFile;
                }
                catch(Exception e)
                {
                        LogLevel.ERROR(1,e,"Exception while initialising LogLevel");
                }
        }
		public static void setLogFile( String fanme)
        {
                logFile= fanme;
        }
        public static void setLogLevel( int level)
        {
                logLevel= level;
        }
        public static void setLogPath( String path)
        {
                logPath= path;
        }

		public static void DEBUG( int level, Throwable thw, String text, String strFileName )
        {
                try
                {
                        String LogFileName = logPath;

                        File fileDir = new File(LogFileName);
                        if(!fileDir.exists())
                                                 fileDir.mkdirs();

                         LogFileName = logPath + strFileName;

                         if ( level <= logLevel )
                        {
                                String msg = formString( "DEBUG", level, thw, text );
                                OutputStream f_os = new FileOutputStream( LogFileName, true );
                                f_os.write( msg.getBytes() );
                                f_os.close();
                        }
                }
                catch(java.lang.NullPointerException npe)
                {
                        npe.printStackTrace();
                }
                 catch( java.io.FileNotFoundException fnf )
                {
                        fnf.printStackTrace();
                }
               catch(java.io.IOException ioe )
                {
                        ioe.printStackTrace();
                }

        }
	public static void ERROR( int level, Throwable thw, String text ,String strFileName )
	{
                try
                {

                        String LogFileName = logPath;
	                    File fileDir = new File(LogFileName);
                        if(!fileDir.exists())
                                                 fileDir.mkdirs();

                        LogFileName = logPath + strFileName;

                        if ( level <= logLevel )
			            {
                                String msg = formString( "ERROR", level, thw, text );
                                OutputStream f_os = new FileOutputStream( LogFileName, true );
                          f_os.write( msg.getBytes() );
                          f_os.close();
                        }
                }
                catch(java.lang.NullPointerException npe)
                {
                        npe.printStackTrace();
                }
                catch( java.io.FileNotFoundException fnf )
                {
                fnf.printStackTrace();
                }
                catch(java.io.IOException ioe )
                {
                ioe.printStackTrace();
                }

    }

        public static void DEBUG( int level, Throwable thw, String text )
        {
                try
                {
                        File fileDir = new File(logPath);
                        if(!fileDir.exists())
                                fileDir.mkdirs();

                        String LogFileName = logPath + logFile;

                      if ( level <= logLevel )
                        {

                                String msg = formString( "DEBUG", level, thw, text );
                                OutputStream f_os = new FileOutputStream( LogFileName, true );
                                f_os.write( msg.getBytes() );
                                f_os.close();
                        }
                }
                catch(java.lang.NullPointerException npe)
                {
                        npe.printStackTrace();
                }
                 catch( java.io.FileNotFoundException fnf )
                {
                        fnf.printStackTrace();
                }
                catch(java.io.IOException ioe )
                {
                        ioe.printStackTrace();
                }

        }

   public static void ERROR( int level, Throwable thw, String text )
   {
                try
                {
                        File fileDir = new File(logPath);
                        if(!fileDir.exists())
                                fileDir.mkdirs();

                       String LogFileName = logPath + logFile;

                        if ( level <= logLevel )
                {
                                String msg = formString( "ERROR", level, thw, text );
                                OutputStream f_os = new FileOutputStream( LogFileName, true );
                                f_os.write( msg.getBytes() );
                                f_os.close();
                        }
                }
                catch(java.lang.NullPointerException npe)
                {
                        npe.printStackTrace();
                }
               catch( java.io.FileNotFoundException fnf )
                {
                fnf.printStackTrace();
                }
                catch(java.io.IOException ioe )
                {
                ioe.printStackTrace();
                }

    }



        static String formString( String type, int level, Throwable thw, String text )
        {
                String data = null;
                data = "\n\n"+type+"("+ level+"): ";
                data += "Date: ";
                SimpleDateFormat sdfStringFod=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss:SSS");
				Date date = new Date();
				data += sdfStringFod.format(date); 
				data += "  File: ";

                StackTraceElement []st_array =  thw.getStackTrace();
                data += st_array[0].getFileName();
                data += "  Func: ";
                data += st_array[0].getMethodName();
                data += "  Line: ";
                data += st_array[0].getLineNumber();
                data += "   \n";
                data += text;
           return data;
        }
}

