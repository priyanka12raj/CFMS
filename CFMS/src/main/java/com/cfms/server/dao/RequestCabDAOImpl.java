package com.cfms.server.dao;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.cfms.server.dao.interfaces.RequestCabDAO;
import com.cfms.shared.exceptions.CFMSException;
import com.cfms.shared.pojo.RequestCab;

/**
 * Implements the methods specified by RequestCabDAO
 * @author Priyanka, Samta, Chaitra
 * @see RequestCabDAO
 */
public class RequestCabDAOImpl implements RequestCabDAO 
{

	Connection conn;
	@Override
	public boolean insertRequestCab(RequestCab requestCab) throws CFMSException
	{
		boolean insertSuccess=false;
		Connection conn=null;
		PreparedStatement insert_stmt=null;
		ResultSet rs_req_id;
		try
		{
			conn=DataSource.getInstance().getConnection(); //get the connection from connection pool
			insert_stmt = conn.prepareStatement("insert into request_new_cab(employee_id,pickup_point_location,request_details,request_status,response_details) values(?,?,?,?,?)",Statement.RETURN_GENERATED_KEYS);
			insert_stmt.setString(1, requestCab.getEmployee().getEid());
			insert_stmt.setString(2, requestCab.getLocation());
			insert_stmt.setString(3, requestCab.getRequest_details());
			insert_stmt.setString(4, requestCab.getStatus());
			insert_stmt.setString(5, requestCab.getResponse_details());
			insert_stmt.execute();
			//for getting the autogenerated key request_id
			rs_req_id=insert_stmt.getGeneratedKeys();
			if(rs_req_id.next())
			{
				requestCab.setRequest_id(rs_req_id.getInt(1));
				insertSuccess=true;
			}
			
		}
		catch(SQLException | IOException | PropertyVetoException e)
		{
			throw new CFMSException(e);
		}
		finally
		{
			try {
				conn.close(); //close the connection
				insert_stmt.close();
			} catch (SQLException e) {
				throw new CFMSException(e);
			}
		}
		return insertSuccess;
	}

	@Override
	public boolean updateRequestCab(RequestCab requestCab) throws CFMSException
	{
		PreparedStatement update_req=null;
		boolean success=false;
		try{
//			conn=datasource.getConnection();
			conn=DataSource.getInstance().getConnection(); //get the connection from connection pool
			update_req=conn.prepareStatement("update request_new_cab set request_status=?,response_details=? where request_id=?");
			update_req.setString(1, requestCab.getStatus());
			update_req.setString(2,requestCab.getResponse_details());
			update_req.setInt(3, requestCab.getRequest_id());
			System.out.println(requestCab.getRequest_id());
			update_req.executeUpdate();
			success=true;
		}
		catch (IOException | SQLException | PropertyVetoException e) 
		{
			throw new CFMSException(e);
		}
		finally
		{
			try{
				conn.close(); //close the connection
				update_req.close();
			}
			catch (SQLException e) 
			{
				throw new CFMSException(e);
			}
			
		}
		return success;
	}
	@Override
	public List<RequestCab> getRequestsByStatus(String status) throws CFMSException
	{
		Connection conn=null;
		PreparedStatement select_stmt=null;
		EmployeeDAOImpl emp_dao=new EmployeeDAOImpl();
		List<RequestCab> req_list=new ArrayList<RequestCab>();
		RequestCab requestCab=null;
		ResultSet req_rs;
		try
		{
			conn=DataSource.getInstance().getConnection(); //get the connection from connection pool
			select_stmt = conn.prepareStatement("select * from request_new_cab where request_status=? order by request_time");
			select_stmt.setString(1, status);
			req_rs=select_stmt.executeQuery();
			while(req_rs.next())
			{
				requestCab=new RequestCab();
				requestCab.setRequest_id(req_rs.getInt(1));
				requestCab.setEmployee(emp_dao.getEmployee(req_rs.getString(2)));
				requestCab.setLocation(req_rs.getString(3));
				requestCab.setRequest_details(req_rs.getString(4));
				requestCab.setStatus(req_rs.getString(5));
				requestCab.setResponse_details(req_rs.getString(6));
				req_list.add(requestCab);
			}
			
		}
		catch(SQLException | IOException | PropertyVetoException e)
		{
			throw new CFMSException(e);
		}
		finally
		{
			try {
				conn.close(); //close the connection
				select_stmt.close();
			} catch (SQLException e) {
				throw new CFMSException(e);
			}
		}
		return req_list;
	}

	@Override
	public List<RequestCab> getAllRequests() throws CFMSException
	{
		Connection conn=null;
		PreparedStatement select_stmt=null;
		EmployeeDAOImpl emp_dao=new EmployeeDAOImpl();
		List<RequestCab> req_list=new ArrayList<RequestCab>();
		RequestCab requestCab=null;
		ResultSet req_rs;
		try
		{
			conn=DataSource.getInstance().getConnection(); //get the connection from connection pool
			select_stmt = conn.prepareStatement("select * from request_new_cab");
			req_rs=select_stmt.executeQuery();
			while(req_rs.next())
			{
				requestCab=new RequestCab();
				requestCab.setRequest_id(req_rs.getInt(1));
				requestCab.setEmployee(emp_dao.getEmployee(req_rs.getString(2)));
				requestCab.setLocation(req_rs.getString(3));
				requestCab.setRequest_details(req_rs.getString(4));
				requestCab.setStatus(req_rs.getString(5));
				requestCab.setResponse_details(req_rs.getString(6));
				req_list.add(requestCab);
			}
			
		}
		catch(SQLException | IOException | PropertyVetoException e)
		{
			throw new CFMSException(e);
		}
		finally
		{
			try {
				conn.close(); //close the connection
				select_stmt.close();
			} catch (SQLException e) {
				throw new CFMSException(e);
			}
		}
		return req_list;
	}

}
