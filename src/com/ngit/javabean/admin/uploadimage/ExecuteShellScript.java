/**
*  @Modified by : Sai Krishna Daddala  on [26th jan 12], added another class called StreamFlusher
				: StreamFlusher, flushes the buffers stderr,stdin allocated to the new process
				: created using Runtime.getRuntime().exec("command") method. In jdk1.4.x, small
				: buffers are allocated to the new process, hence, the new process may hang up.
				: to avoid this problem, we are flushing out the data placed into these buffers
				: by the new process.
*
*/

package com.ngit.javabean.admin.uploadimage;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

public class  ExecuteShellScript{

	public int intExecuteScript(String strCommand){
		Runtime rt = Runtime.getRuntime();
		Process pr=null;
		try{
			pr=rt.exec(strCommand);
            // stream out error messages
            StreamFlusher errorFlusher = new 
                StreamFlusher(pr.getErrorStream(), "ERROR");            
             // stream out the output
            StreamFlusher outputFlusher = new 
                StreamFlusher(pr.getInputStream(), "OUTPUT");
                
            // write the erros and output  to the log/console
            errorFlusher.start();
            outputFlusher.start();
			// wait fore the exit value
			pr.waitFor();
		}
		catch(Exception e){
			e.printStackTrace();
			//System.out.println("Error in executing replaceIP Script" + e);
		}
		//System.out.println("EXECUTE Command=> "+strCommand+"  Returned: "+pr.exitValue());
		return pr.exitValue();
	}
	
	public int intExecuteScript(String strCommand,String logFilePath)
	{
		Runtime rt = Runtime.getRuntime();
		Process pr=null;
		try{
			pr=rt.exec(strCommand);
			// stream out error messages
			StreamFlusher errorFlusher = new 
				StreamFlusher(pr.getErrorStream(), "ERROR",logFilePath);            
			
			// stream out the output
			StreamFlusher outputFlusher = new
				StreamFlusher(pr.getInputStream(), "OUTPUT",logFilePath);
				
			// write the erros and output on  the log/console
			errorFlusher.start();
			outputFlusher.start();
			// wait fore the exit value
			pr.waitFor();
		}
		catch(Exception e){
			e.printStackTrace();
			//System.out.println("Error in executing replaceIP Script" + e);
		}
		System.out.println("EXECUTE Command=> "+strCommand+"  Returned: "+pr.exitValue());
		return pr.exitValue();
	}
}

/**
* this class is a thread that can flush out the contents from the 
* specified stream
*/
class StreamFlusher extends Thread
{
    InputStream is;
    String type;
    String logFilePath;
    
    StreamFlusher(InputStream is, String type)
    {
        this.is = is;
        this.type = type;
		this.logFilePath="";
    }
	StreamFlusher(InputStream is, String type,String logFilePath)
    {
        this.is = is;
        this.type = type;
		this.logFilePath=logFilePath;
    }
    
    public void run()
    {
		FileOutputStream fos=null;
        try
        {
			
            if(!logFilePath.equals(""))
			{
				fos=new FileOutputStream(logFilePath,true);
			}
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String line=null;
            while ( (line = br.readLine()) != null)
			{
				line+="\n";
				if(fos!=null)
				{
					byte[] data=line.getBytes();
					fos.write(data,0,data.length);
				}
				else
				{
					//System.out.print(type + ">" + line);    
				}
			}
         } catch (IOException ioe)
         {
			ioe.printStackTrace();  
         }
		 finally
		 {
			 try
			 {
				fos.close();
			 }catch(Exception e){}
		 }
    }
}
