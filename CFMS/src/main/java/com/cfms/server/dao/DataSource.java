package com.cfms.server.dao;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

//import org.apache.commons.dbcp2.BasicDataSource;

import org.apache.commons.dbcp.BasicDataSource;

/**
 * DBCP used for connection pooling
 * @author Samta, Priyanka, Chaitra
 *
 */
public class DataSource {

    private static DataSource datasource;
    public static int count=0;
    private BasicDataSource ds;
	/**
	 * Constructs Data Source object using BasicDataSource
	 * @throws IOException
	 * @throws SQLException
	 * @throws PropertyVetoException
	 */
    public DataSource() throws IOException, SQLException, PropertyVetoException 
    {
        ds = new BasicDataSource();
        ds.setDriverClassName("com.mysql.jdbc.Driver");//set the driver class name
        ds.setUsername("root"); //sets username
        ds.setPassword(""); //sets password
        ds.setUrl("jdbc:mysql://localhost:3306/cfms"); //database url
       
     // the settings below are optional -- dbcp can work with defaults
        ds.setMinIdle(5);
        ds.setMaxIdle(20);
        ds.setMaxOpenPreparedStatements(180);

    }
    /**
     * gets the instance of datasource if instance exists otherwise creates an instance and returns it.
     * @return DataSource instance
     * @throws IOException
     * @throws SQLException
     * @throws PropertyVetoException
     */
    public static DataSource getInstance() throws IOException, SQLException, PropertyVetoException 
    {
        if (datasource == null) 
        {
            datasource = new DataSource();
            return datasource;
        }
        else 
        {
            return datasource;
        }
    }
    /**
     * gets an Connection using the BasicDatasource
     * @return
     * @throws SQLException
     */
    public Connection getConnection() throws SQLException 
    {
    	
        return this.ds.getConnection();
    }

}


