 
/* ********************************************************************\ 
	NGIT Pvt.Ltd. Confidential. 
	@File Name   : QueryManager.java  
	@Description : To perform all database related transactions 
		
\* *******************************************************************/ 

package com.ngit.javabean.qrymgr;

import com.ngit.core.ConnectionPool; 
import com.ngit.javabean.loglevel.LogLevel;
  
import java.sql.*; 

import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException; 
import java.io.PrintWriter; 
import java.io.FileNotFoundException; 
import java.io.OutputStream;
import java.io.InputStream;
import java.util.Hashtable;
import java.util.Vector;
import java.util.ArrayList;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/* ******************************************************************\
* This class is a bean to manage the database transactions. 
* This class initializes the necessary connection pools and perform the transactions on the pools. 
* This class is made singleton, so as to allow all users to use the same QueryManager.
\* ********************************************************************/

public class  QueryManager implements java.io.Serializable
{
	static ConnectionPool conpool;
	static Properties propsFileName;
	static Properties propsMOPConfig;
	static QueryManager qryManager;
	static boolean isInitialized=false;
 
	DataSource dataSource = null;
	private static final QueryManager QM = new QueryManager();
	
	/* ********************************\ 
		private constructor
	\* ********************************/
	private QueryManager()
	{
		isInitialized=false;
		if(dataSource==null) {
			try {
				System.out.println("QM DS created");
				Context initialContext = new InitialContext();
				Context environmentContext = (Context) initialContext.lookup("java:comp/env");
				dataSource = (DataSource) environmentContext.lookup("jdbc/BatterywaleDB");
			} catch (NamingException e) {
				LogLevel.ERROR(1,e,"Failed to create datasource"+e.getMessage());
			}
		}
		LogLevel.DEBUG(5, new Throwable(),"Query Manager Instance Created");
	}
	
	/* *************************************************\
	* A factory method to return the singleton instance
	\* *************************************************/
	public static QueryManager getInstance(Properties props)
	{
		LogLevel.DEBUG(3, new Throwable(),"GetInstance Method called:");
		LogLevel.DEBUG(3, new Throwable(),"LoadProperties Function calling...");
		
		/*if(qryManager == null)
		{
			LogLevel.DEBUG(3, new Throwable(),"No instance found, so calling the constructor");
			qryManager=new QueryManager();
 			propsMOPConfig = props;
			qryManager.initializeConnectionPool();
		}*/
		propsMOPConfig = props;
		LogLevel.DEBUG(3, new Throwable(),"Returning the QM Instance");
		return QM;
	}

	
	/* ************************************************************************\
	* This method initializes the connection pools for query manager.
 	\* ************************************************************************/
	
	public int initializeConnectionPool()
	{
		LogLevel.DEBUG(3, new Throwable()," initializeConnectionPool(Properties propsLbaConfig) method called");

		// initialize conpool to lbadb
		String strDataBaseDriver            = "org.gjt.mm.mysql.Driver"; 
		LogLevel.DEBUG(5, new Throwable(),"strDataBaseDriver:"+strDataBaseDriver);
		String strDataBaseUrl               =(propsMOPConfig.getProperty("DATABASEURL")!=null)?(propsMOPConfig.getProperty("DATABASEURL").trim()):"";
		LogLevel.DEBUG(5, new Throwable(),"strDataBaseUrl:"+strDataBaseUrl);
		String strDataBaseUsername          = (propsMOPConfig.getProperty("db_user")!=null)?(propsMOPConfig.getProperty("db_user").trim()):"ngit"; 
		LogLevel.DEBUG(5, new Throwable(),"strDataBaseUsername:"+strDataBaseUsername);
		String strDataBasePassword          = (propsMOPConfig.getProperty("db_password")!=null)?(String)propsMOPConfig.getProperty("db_password").trim():""; 
		LogLevel.DEBUG(5, new Throwable(),"strDataBasePassword:"+strDataBasePassword);
		int intDataBaseInitialConnections   = (propsMOPConfig.getProperty("db_initial_conn")!=null)?(Integer.parseInt(propsMOPConfig.getProperty("db_initial_conn").trim())):5; 
		LogLevel.DEBUG(5, new Throwable(),"intDataBaseInitialConnections:"+intDataBaseInitialConnections);
		int intDataBaseMaxConnections       = (propsMOPConfig.getProperty("db_max_conn")!=null)?(Integer.parseInt(propsMOPConfig.getProperty("db_max_conn").trim())):20; 
		LogLevel.DEBUG(5, new Throwable(),"intDataBaseMaxConnections:"+intDataBaseMaxConnections);

		boolean bolDataBaseWaitIfBusy       = true; 
		LogLevel.DEBUG(5, new Throwable(),"bolDataBaseWaitIfBusy:"+bolDataBaseWaitIfBusy);

		try
		{
			conpool              = new ConnectionPool(strDataBaseDriver,strDataBaseUrl,strDataBaseUsername,strDataBasePassword,intDataBaseInitialConnections,intDataBaseMaxConnections,bolDataBaseWaitIfBusy);
			if(conpool==null )
			{
				LogLevel.ERROR(0, new Throwable(),"Connection Pool Creation failed");
				System.out.println("ConnectionPool creation failed");
				for(int i=0;i<3;i++)
				{
					System.out.println("Recreation Attempt:"+(i+1));
					LogLevel.DEBUG(3, new Throwable(),"Recreation Attempt:"+(i+1));
					conpool              = new ConnectionPool(strDataBaseDriver,strDataBaseUrl,strDataBaseUsername,strDataBasePassword,intDataBaseInitialConnections,intDataBaseMaxConnections,bolDataBaseWaitIfBusy); 
					if(conpool==null)
					{
						continue;
					}
					else
					{
						System.out.println("ConnectionPool creation successful");
						LogLevel.DEBUG(3, new Throwable(),"ConnectionPool creation successful");
						break;
					}
				}
				if(conpool==null )
				{
					LogLevel.ERROR(0, new Throwable(),"Database connection failed... Please contact Administrator...");
				}
			}
		}
		catch(Exception e)
		{
			LogLevel.DEBUG(3, new Throwable()," Returning from Exception... " );
			e.printStackTrace();
			return -1;
		}
		
		return 1;
	}

	/* ***************************************************************\
	* This method initializes the connection pool to lbadb
	* for query manager.
	* @param conpool: ConnecitonPool object
	\* ***************************************************************/	

	public void setConpool(ConnectionPool conpool)
	{
		this.conpool=conpool;
	}
	
	/* ****************************************************************\
        The following function is used to execute queries like insert,update and delete. This function will use LBADB database
               
        @param strSqlQry        : Query for updating data from DB
        @return  1      	: Suceess
        @return  -1           	: Failure
        \* ****************************************************************/

	synchronized public int executeUpdate(String strSqlQuery)
	{
		LogLevel.DEBUG(3, new Throwable(),"executeUpdate called");

		LogLevel.DEBUG(5, new Throwable(),"strSqlQuery:"+strSqlQuery);

		Connection con=null;
		try
		{
			con=dataSource.getConnection();
			Statement st=con.createStatement();
			return st.executeUpdate(strSqlQuery);
		}
		catch(Exception e)
		{
			LogLevel.ERROR(1, new Throwable(),"exception in execute update:"+e);
			e.printStackTrace();
		}
		finally
		{
			if (con != null) {
                try {
					con.close();
				} catch (SQLException e) {
					LogLevel.ERROR(1,e,"Failed to close connection"+e.getMessage());
				}
            }
            con = null;
		}
		return -1;
	}

	/* ****************************************************************\
        The following function is used to fetch multiple rows
        from DB. This function will use LBADB database
                
        @param strSqlQry        : Query for fetching data from DB
        @return  Vector      : Suceess (Selected rows will return)
        @return  null           : Failure
        \* ****************************************************************/

	synchronized public Vector executeQuery(String strSqlQuery)
	{
		Connection con=null;
		Vector pageRows=new Vector();
		try
		{
			con=dataSource.getConnection();
			Statement st=con.createStatement();
			ResultSetMetaData   metaData=null; 
			ResultSet rs=null;
			rs=st.executeQuery(strSqlQuery);
			metaData = rs.getMetaData();
			int numberOfColumns =  metaData.getColumnCount();
			pageRows=new Vector();
			while (rs.next()) 
			{
				Hashtable newRow = new Hashtable();
				for (int i = 1; i <= numberOfColumns; i++) 
				{
					if(rs.getObject(i)!=null)
						newRow.put(metaData.getColumnName(i),rs.getObject(i));
				}
				pageRows.addElement(newRow);
			
			}
		}
		catch(Exception e)
		{
		}
		finally
		{
			if (con != null) {
                try {
					con.close();
				} catch (SQLException e) {
					LogLevel.ERROR(1,e,"Failed to close connection"+e.getMessage());
				}
            }
            con = null;
		}
		return pageRows;
	}

	/* ****************************************************************\
        The following function is used to fetch field value
        from DB. This function will use LBADB database
                
        @param strSqlQry        : Query for fetching data from DB
        @return  ArayList      : Suceess (Selected field will return)
        @return  null           : Failure
        \* ****************************************************************/	

	synchronized public ArrayList getField(String strSqlQuery )
	{
		Connection con=null;
		ArrayList pageRows=null;
		try
		{
			con=dataSource.getConnection();
			Statement st=con.createStatement();
			ResultSetMetaData   metaData=null;
			ResultSet rs=null;
			rs=st.executeQuery(strSqlQuery);
			metaData = rs.getMetaData();
			int numberOfColumns =  metaData.getColumnCount();
			pageRows=new ArrayList();
			while (rs.next())
			{
				pageRows.add(rs.getObject(1));

			}
		}
		catch(Exception e)
		{
			LogLevel.ERROR(1,e,"Caught exception in getField"+e);
			e.printStackTrace();
		}
		finally
		{
			if (con != null) {
                try {
					con.close();
				} catch (SQLException e) {
					LogLevel.ERROR(1,e,"Failed to close connection"+e.getMessage());
				}
            }
            con = null;
		}
		return pageRows;
	}
	
	/* **************************************************************\
	The following function is used to fetch only one row
	from DB. This function will use LBADB database

	@param strSqlQry        : Query for fetching data from DB
	@return  Hashtable      : Suceess (Selected row will return)
	@return  null           : Failure
	\* **************************************************************/
	public Hashtable getRow(String strSqlQuery)
	{
		LogLevel.DEBUG(4, new Throwable(), "Inside getRow()");
		Connection con     = null;
		Hashtable hashRows = new Hashtable();
		try
		{
			
			con = dataSource.getConnection();
			if(con == null )
			{
				LogLevel.ERROR(0, new Throwable(), "DB Connection is not Available");
				return hashRows;
			}

			Statement st               = con.createStatement();
			ResultSetMetaData metaData = null; 
			ResultSet rs               = null;

			rs       = st.executeQuery(strSqlQuery);
			metaData = rs.getMetaData();
			int numberOfColumns = metaData.getColumnCount();
			LogLevel.DEBUG(5, new Throwable(), "Total Number of columns" + numberOfColumns );
			
			hashRows.clear();
			while (rs.next()) 
			{
				LogLevel.DEBUG(5, new Throwable(), "Fetching next record");
				for (int i = 1; i <= numberOfColumns; i++) 
				{
					if(rs.getObject(i)!=null)
						hashRows.put(metaData.getColumnLabel(i),rs.getObject(i));
				}
			}
			LogLevel.DEBUG(5, new Throwable(), "hashRows:"+hashRows);
		}
		catch(Exception e)
		{
			LogLevel.ERROR(0, new Throwable(), "Exception Occured : " + e);
			e.printStackTrace();
		}
		finally
		{
			if (con != null) {
                try {
					con.close();
				} catch (SQLException e) {
					LogLevel.ERROR(1,e,"Failed to close connection"+e.getMessage());
				}
            }
            con = null;
		}
		return hashRows;
	}
	 /****************************************************
    The following function is used to insert BLOB content
    into DB.
    This function will use DNADB database

    @param strSqlQry        : Insert Blob conetnt query
    @param filePath         : FilePath from where the content has to be read
    @return >0              : Suceess (Successfully inserted BLOB)
    @return  <0             : Failure
    *****************************************************/
	 public int insertBlobContent(String strSqlQry, String filePath)
    {
        LogLevel.DEBUG(4, new Throwable(), "Inside getRow()");
        Connection con     = null;
        Hashtable hashRows = null;
        int retVal = -1;
        try
        {
            con = dataSource.getConnection();
            
            PreparedStatement ps = null;
            File file = new File( filePath );
            FileInputStream fis = new FileInputStream ( file );
            ps = con.prepareStatement(strSqlQry);
            ps.setBinaryStream (1, fis , (int)file.length() );
            retVal = ps.executeUpdate();
            fis.close();
            LogLevel.DEBUG(5, new Throwable(), "Return Value after inserting blob : " + retVal);
        }
        catch(OutOfMemoryError e)
        {
            LogLevel.ERROR(0, e, "OutOfMemoryError : " + e);
            e.printStackTrace();
        }
		catch(IOException ioe)
        {
            LogLevel.ERROR(0, ioe, "IOException Occured : " + ioe);
            ioe.printStackTrace();
        }
        catch(Exception e)
        {
            LogLevel.ERROR(0, e, "Exception Occured : " + e);
            e.printStackTrace();
        }
        finally
        {
        	if (con != null) {
                try {
					con.close();
				} catch (SQLException e) {
					LogLevel.ERROR(1,e,"Failed to close connection"+e.getMessage());
				}
            }
            con = null;
        }
        return  retVal;
    }
	/********************************************************************
    This functon is used to insert Multiple Blob data to database
    @param strSqlQuery           :  Sql Query to insert contents
    @param alFilePath            :  Local file paths where contents will be 									Existing

    @retun                       : 
    **********************************************************************/
	/* Added By Vijaya*/
	public int insertMultipleBlobContents(String strSqlQry, Vector alFilePath)
     {
        LogLevel.DEBUG(4, new Throwable(), "Inside insertMultipleBlobContents()");
		LogLevel.DEBUG(5,new Throwable(),"alFilePath is : "+alFilePath);
        Connection con     = null;
        Hashtable hashRows = null;
        int retVal = -1;
        try
        {
            con = dataSource.getConnection();
            if(con == null)
            {
                LogLevel.ERROR(0, new Throwable(), "DB Connection is not Available");
                return retVal;
            }
			int j=1;
			String strFilePath = "";
            PreparedStatement ps = null;
            File file[] =  new File[alFilePath.size()];
			FileInputStream fis[] = new FileInputStream[alFilePath.size()];
			ps = con.prepareStatement(strSqlQry);
			

			for(int i=0;i<alFilePath.size();i++)
			{
				strFilePath = (String)alFilePath.get(i);
				LogLevel.DEBUG(5,new Throwable(),"strFilePath is : "+strFilePath);
				file[i] = new File( strFilePath );
				
				fis[i] = new FileInputStream ( file[i]);
				ps.setBinaryStream (j, fis[i] , (int)file[i].length() );
				//Thread.sleep(500);
				j++;
				
			}
            retVal = ps.executeUpdate();
            
            LogLevel.DEBUG(5, new Throwable(), "Return Value after inserting multiple blob contents is : " + retVal);
        }
        catch(OutOfMemoryError e)
        {
            LogLevel.ERROR(0, e, "OutOfMemoryError : " + e);
            e.printStackTrace();
        }
		catch(IOException ioe)
        {
            LogLevel.ERROR(0, ioe, "IOException Occured : " + ioe);
            ioe.printStackTrace();
        }
        catch(Exception e)
        {
            LogLevel.ERROR(0, e, "Exception Occured : " + e);
            e.printStackTrace();
        }
        finally
        {
        	if (con != null) {
                try {
					con.close();
				} catch (SQLException e) {
					LogLevel.ERROR(1,e,"Failed to close connection"+e.getMessage());
				}
            }
            con = null;
        }
        return  retVal;
    }
	/********************************************************************
	This functon is used to extract picture(Blob data) and create local file
	@param strSqlQuery           :  Sql Query to fetch data
	@param strAbsFilePath        :	Local directory path where file should be created
	@param strFieldName          :  FiledName to get the filename(picture name) with we are	going to create file
	@retun                       : vector with picture details
	**********************************************************************/
	public Vector extractPicture(String strSqlQuery,String strAbsFilePath,String strFieldName,String strFieldName1)
	{
		LogLevel.DEBUG(3, new Throwable(), "inside extractPicture function");
		LogLevel.DEBUG(5, new Throwable(), "strSqlQuery : " + strSqlQuery + " strAbsFilePath:"+ strAbsFilePath + " strFieldName: " + strFieldName);
		Connection con        = null; 
		Vector PicDetl        = null;
		try
		{
			con             = dataSource.getConnection();
			if(con == null)
			{
				LogLevel.ERROR(0, new Throwable(), "DB Connection is not Available");
				return PicDetl;
			}
			Statement st               = con.createStatement();
			ResultSetMetaData metaData = null;
			ResultSet rs               = null;
			rs                         = st.executeQuery(strSqlQuery);
			metaData                   = rs.getMetaData();
			int numberOfColumns        = metaData.getColumnCount();
			String strMsisdn="";
			LogLevel.DEBUG(3, new Throwable(), "Number of Columns : " + numberOfColumns);
			PicDetl = new Vector();
			while (rs.next())
			{
				//LogLevel.DEBUG(5, new Throwable(), "Fetching next record");
				Hashtable hashPicDetl = new Hashtable();
				for (int i = 1; i <= numberOfColumns; i++)
				{
					if(!(strFieldName1.equals("")))
					{
						if(metaData.getColumnName(i).equals(strFieldName1))	
						{
							strMsisdn = rs.getString(strFieldName1);
						}
					}
					int columnType = metaData.getColumnType(i);
					//LogLevel.DEBUG(5, new Throwable(), "Column Type : " + columnType);
					//LogLevel.DEBUG(5, new Throwable(), "Column Name: " + metaData.getColumnName(i));
					//checking for if column type is BLOB.
					if(columnType == Types.BLOB || columnType == Types.LONGVARBINARY)
					{
						//LogLevel.DEBUG(5,new Throwable(),"Column type is BLOB");
						String strFileName    = rs.getString(strFieldName);
						//LogLevel.DEBUG(5,new Throwable(),"strFileName:"+strFileName);

						if(strFileName==null || strFileName.equals(""))
						{
							LogLevel.DEBUG(5,new Throwable(),"Picture is not available");
						}
						else
						{
							//LogLevel.DEBUG(5,new Throwable(),"strAbsFilePath:"+strAbsFilePath);

							byte[] photo          = (byte[])rs.getObject(i);
							if(photo!=null)
							{
								InputStream inStream  = rs.getBinaryStream(i);
								boolean bolSubDir     = ((new File(strAbsFilePath)).mkdirs());
								if (bolSubDir)
								{
									LogLevel.DEBUG(5,new Throwable(),"Created sub-Directory -> "+strAbsFilePath);
								}
								OutputStream outStream = null;
								if(!(strFieldName1.equals("")))
									outStream = new FileOutputStream(strAbsFilePath+strMsisdn+strFileName);
								else
									 outStream = new FileOutputStream(strAbsFilePath+strFileName);
								
								int bytesRead = 0;
								while((bytesRead = inStream.read(photo)) != -1)
								{
									outStream.write(photo, 0, bytesRead);
								}
								outStream.close();
							}
						}
					}	
					else
					{
						//LogLevel.DEBUG(5, new Throwable(), "Non-BLOB Field");
						hashPicDetl.put(metaData.getColumnName(i),(rs.getObject(i)!=null)?rs.getObject(i):"");
					}
				}
				PicDetl.addElement(hashPicDetl);
			}
		} 
		catch(NullPointerException e)
		{
			LogLevel.ERROR(0, e, "NullPointerException Occured : " + e);
			e.printStackTrace();
		}
		catch(Exception e)
		{
			LogLevel.ERROR(0, e, "Exception Occured : " + e);
			e.printStackTrace();
		}
		finally
		{
			if (con != null) {
                try {
					con.close();
				} catch (SQLException e) {
					LogLevel.ERROR(1,e,"Failed to close connection"+e.getMessage());
				}
            }
            con = null;
		}	
		return PicDetl;
	}

	/********************************************************************
    This functon is used to extract multiple blob contents(Blob data) from a table and create local file
    @param strSqlQuery           :  Sql Query to fetch data
    @param strAbsFilePath        :  Local directory path where file should be created
    @param alFieldName          :  ArrayList of FiledNames to get the filename(picture name) with we r going to create file
	@retun                       : vector with picture details
    **********************************************************************/

    /*Added by Vijaya */
    public Vector extractMultiplePictures(String strSqlQuery,String strAbsFilePath,Hashtable alFieldName,String strFieldName1)

    {
        LogLevel.DEBUG(3, new Throwable(), "inside extractMultiplePictures function");
        LogLevel.DEBUG(5, new Throwable(), "strSqlQuery : " + strSqlQuery + " strAbsFilePath:"+ strAbsFilePath );
        LogLevel.DEBUG(5, new Throwable(), "alFieldName is  : " + alFieldName);
        Connection con        = null;
        Vector PicDetl        = null;
        try
        {
            con             = dataSource.getConnection();
            if(con == null)
            {
                LogLevel.ERROR(0, new Throwable(), "DB Connection is not Available");
                return PicDetl;
            }
            Statement st               = con.createStatement();
			ResultSetMetaData metaData = null;
            ResultSet rs               = null;
            rs                         = st.executeQuery(strSqlQuery);
            metaData                   = rs.getMetaData();
            int numberOfColumns        = metaData.getColumnCount();

            String strMsisdn="";
            LogLevel.DEBUG(3, new Throwable(), "Number of Columns : " + numberOfColumns);
            PicDetl = new Vector();
            while (rs.next())
            {
                //LogLevel.DEBUG(5, new Throwable(), "Fetching next record");
                Hashtable hashPicDetl = new Hashtable();
                for (int i = 1; i <= numberOfColumns; i++)
                {
                    if(!(strFieldName1.equals("")))
                    {
                        if(metaData.getColumnName(i).equals(strFieldName1))
                        {
                            strMsisdn = rs.getString(strFieldName1);
                        }
                    }
                    int columnType = metaData.getColumnType(i);
                    String columnName = metaData.getColumnName(i) ;
                    //LogLevel.DEBUG(5, new Throwable(), "Column Type : " + columnType);
                    //LogLevel.DEBUG(5, new Throwable(), "Column Name: " + metaData.getColumnName(i));
                    //checking for if column type is BLOB.

                    if(columnType == Types.BLOB || columnType == Types.LONGVARBINARY)
					{
                            //LogLevel.DEBUG(5,new Throwable(),"Column type is BLOB");
                            String strFieldName = (String)alFieldName.get(columnName);
                            String strFileName    = rs.getString(strFieldName);
                            //LogLevel.DEBUG(5,new Throwable(),"strFileName:"+strFileName);

                            if(strFileName==null || strFileName.equals(""))
                            {
                                LogLevel.DEBUG(5,new Throwable(),"Picture is not available");
                            }
                            else
                            {
                                //LogLevel.DEBUG(5,new Throwable(),"strAbsFilePath:"+strAbsFilePath);

                                byte[] photo          = (byte[])rs.getObject(i);
                                if(photo!=null)
                                {
                                    InputStream inStream  = rs.getBinaryStream(i);
                                    boolean bolSubDir     = ((new File(strAbsFilePath)).mkdirs());
                                    if (bolSubDir)
                                    {
                                        LogLevel.DEBUG(5,new Throwable(),"Created sub-Directory -> "+strAbsFilePath);
                                    }
                                    OutputStream outStream = null;
                                    if(!(strFieldName1.equals("")))
										outStream = new FileOutputStream(strAbsFilePath+strMsisdn+strFileName);
                                    else
                                         outStream = new FileOutputStream(strAbsFilePath+strFileName);

                                    int bytesRead = 0;
                                    while((bytesRead = inStream.read(photo)) != -1)
                                    {
                                        outStream.write(photo, 0, bytesRead);
                                    }
                                    outStream.close();
                                    }
                            }

                    }
                    else
                    {
                        //LogLevel.DEBUG(5, new Throwable(), "Non-BLOB Field");
                        hashPicDetl.put(metaData.getColumnName(i),(rs.getObject(i)!=null)?rs.getObject(i):"");
                    }
                }
                PicDetl.addElement(hashPicDetl);
            }
        }
        catch(NullPointerException e)
		{
            LogLevel.ERROR(0, e, "NullPointerException Occured : " + e);
            e.printStackTrace();
        }
        catch(Exception e)
        {
            LogLevel.ERROR(0, e, "Exception Occured : " + e);
            e.printStackTrace();
        }
        finally
            {
        	if (con != null) {
                try {
					con.close();
				} catch (SQLException e) {
					LogLevel.ERROR(1,e,"Failed to close connection"+e.getMessage());
				}
            }
            con = null;
        }
        return PicDetl;
    }

	/* ***************************************************************\
        * This method getting the connection from  connection pool to lbadb
        * for query manager.
        @return  connection      : Suceess
        @return  null     : Failure
	\* ***************************************************************/

	public Connection getConnection()
	{
		Connection con;
		try
		{
			con=conpool.getConnection();
		}
		catch(Exception e)
		{
			LogLevel.ERROR(1,e,"Exceptio :"+e);
			e.printStackTrace();
			return null;
		}
		return con;
	}
	
	/* ***************************************************************\
        * This method is used to close the connection
        * for query manager.
        * @param con: Conneciton object
        \* ***************************************************************/

	public void closeConnection(Connection con)
	{
		try
		{
			conpool.free(con);
		}
		catch(Exception e)
		{
			LogLevel.ERROR(1,e,"Exceptio :"+e);
			e.printStackTrace();
		}
	}	

	 /* ***************************************************************\
        * This methods used to close the database opend connections
        * for query manager.
        \* ***************************************************************/

	public void finalize()
	{
		LogLevel.DEBUG(3,new Throwable(),"inside finalize function");
		try{
			conpool.closeAllConnections();
			qryManager=null;
		}
		catch(Exception e)
		{
			LogLevel.ERROR(1,e,"exception while freeing the connections");
			e.printStackTrace();
		}
	}
	
	/***
	 * Validate login using prepared statement
	 * **/
	
	/* public boolean authenticateUser(String sqlQuery,String userName,String password)
		{
			Connection con=null;
			boolean validLogin = false;
			try
			{
				LogLevel.DEBUG(5, new Throwable(),"SQL:"+sqlQuery);

				con=dataSource.getConnection();
				PreparedStatement stmt = con.prepareStatement(sqlQuery);
				stmt.setString(1, userName);
				stmt.setString(2, password);
				
				ResultSet rs = stmt.executeQuery();
				if (rs.next())
				{
					validLogin = true;
				}				
			}
			catch(Exception e)
			{
			}
			finally
			{
				if (con != null) {
	                try {
						con.close();
					} catch (SQLException e) {
						LogLevel.ERROR(1,e,"Failed to close connection"+e.getMessage());
					}
	            }
	            con = null;
			}
			return validLogin;
		}*/
	
	public boolean authenticateUser(String sqlQuery,String userNamepwd)
	{
		Connection con=null;
		boolean validLogin = false;
		try
		{
			LogLevel.DEBUG(5, new Throwable(),"SQL:"+sqlQuery);

			con=dataSource.getConnection();
			PreparedStatement stmt = con.prepareStatement(sqlQuery);
			stmt.setString(1, userNamepwd);
			//stmt.setString(2, password);
			
			ResultSet rs = stmt.executeQuery();
			if (rs.next())
			{
				validLogin = true;
			}				
		}
		catch(Exception e)
		{
		}
		finally
		{
			if (con != null) {
                try {
					con.close();
				} catch (SQLException e) {
					LogLevel.ERROR(1,e,"Failed to close connection"+e.getMessage());
				}
            }
            con = null;
		}
		return validLogin;
	}
}
