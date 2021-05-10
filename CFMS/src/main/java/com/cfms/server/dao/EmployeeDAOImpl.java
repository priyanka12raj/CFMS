package com.cfms.server.dao;

import java.util.ArrayList;
import java.util.List;
import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.cfms.shared.exceptions.CFMSException;
import com.cfms.shared.pojo.Cab;
import com.cfms.shared.pojo.Employee;
import com.cfms.server.dao.interfaces.EmployeeDAO;

/**
 * implements all the methods specified by the EmployeeDAO
 * @author Samta, Priyanka, Chaitra
 * @see EmployeeDAO
 *
 */

public class EmployeeDAOImpl implements EmployeeDAO
{	
	Connection conn;
	@Override
	/*
	 * (non-Javadoc)
	 * @see com.cfms.server.dao.interfaces.EmployeeDAO#insertEmployee(com.cfms.shared.pojo.Employee)
	 */
	public boolean insertEmployee(Employee employee) throws CFMSException {
		boolean insertSuccess=false;
		PreparedStatement insert_emp=null;
		try{
			conn=DataSource.getInstance().getConnection();
			insert_emp=conn.prepareStatement("insert into Employee values(?,?,?,?,?,?,?)");  
			insert_emp.setString(1, employee.getEid());
			insert_emp.setString(2,employee.getEname());
			insert_emp.setString(3, employee.getEmail());
			insert_emp.setString(4, employee.getAddress());
			insert_emp.setString(5, employee.getPhone());
			insert_emp.setString(6, employee.getPassword());
			insert_emp.setBoolean(7, employee.getIsAdmin());
			insert_emp.execute();
			insertSuccess=true;
		}
		catch (IOException | SQLException | PropertyVetoException e) 
		{
			throw new CFMSException(e);
		}
		finally
		{
			try{
				conn.close();
				insert_emp.close();
			}
			catch (SQLException e) 
			{
				throw new CFMSException(e);
			}
			
		}
		return insertSuccess;
	}

//	private DataSource datasource;
//	public void setDs(DataSource ds)
//	{
//		this.datasource=ds;
//	}

	
	@Override
	/*
	 * (non-Javadoc)
	 * @see com.cfms.server.dao.interfaces.EmployeeDAO#getAllEmployees()
	 */
	public List<Employee> getAllEmployees() throws CFMSException {
		
		Statement retrieve_all=null;
		List<Employee> l1=new ArrayList<Employee>();
		try{
//			conn=datasource.getConnection();
			conn=DataSource.getInstance().getConnection();
			retrieve_all=conn.createStatement();
			ResultSet rs=retrieve_all.executeQuery("select * from employee"); 
			while(rs.next())
			{
				Employee emp=new Employee();
				emp.setEid(rs.getString("Employee_id"));
				emp.setAddress(rs.getString("address"));
				emp.setEmail(rs.getString("email"));
				emp.setEname(rs.getString("name"));
				emp.setPassword(rs.getString("password"));
				emp.setPhone(rs.getString("phone_no"));
				emp.setIsAdmin(rs.getBoolean("isadmin"));
				l1.add(emp);
			}

		}
		catch (IOException | SQLException | PropertyVetoException e) 
		{
			throw new CFMSException(e);
		}
		finally
		{
			try{
				conn.close();
				retrieve_all.close();
			}
			catch (SQLException e) 
			{
				throw new CFMSException(e);
			}
			
		}
		return l1;
	}

	@Override
	/*
	 * (non-Javadoc)
	 * @see com.cfms.server.dao.interfaces.EmployeeDAO#getEmployee(java.lang.String)
	 */
	public Employee getEmployee(String eid) throws CFMSException {
		PreparedStatement retrieve_emp=null;
		Employee emp=null;
		try
		{
//			conn=datasource.getConnection();
			conn=DataSource.getInstance().getConnection();
			retrieve_emp=conn.prepareStatement("select * from employee where Employee_id=?");
			retrieve_emp.setString(1, eid);
			ResultSet rs=retrieve_emp.executeQuery();
			if(rs.next())
			{
				emp=new Employee();
				emp.setEid(rs.getString("Employee_id"));
				emp.setAddress(rs.getString("address"));
				emp.setEmail(rs.getString("email"));
				emp.setEname(rs.getString("name"));
				//emp.setPassword(rs.getString("password"));
				emp.setPhone(rs.getString("phone_no"));
				emp.setIsAdmin(rs.getBoolean("isadmin"));
			}
			
		}
		catch (IOException | SQLException | PropertyVetoException e) 
		{
			throw new CFMSException(e);
		}
		finally
		{
			try{
				conn.close();
				retrieve_emp.close();
			}
			catch (SQLException e) 
			{
				throw new CFMSException(e);
			}
			
		}
		return emp;
	}

	@Override
	/*
	 * (non-Javadoc)
	 * @see com.cfms.server.dao.interfaces.EmployeeDAO#updateEmployee(com.cfms.shared.pojo.Employee)
	 */
	public boolean updateEmployee(Employee employee) throws CFMSException
	{
		PreparedStatement update_emp=null;
		boolean success=false;
		try{
//			conn=datasource.getConnection();
			conn=DataSource.getInstance().getConnection();
			update_emp=conn.prepareStatement("update Employee set name=?,email=?,address=?,phone_no=? where Employee_id=?");
			
			update_emp.setString(1, employee.getEname());
			update_emp.setString(2,employee.getEmail() );
			update_emp.setString(3,employee.getAddress() );
			update_emp.setString(4, employee.getPhone());
			update_emp.setString(5, employee.getEid());
			update_emp.executeUpdate();
			success=true;
		}
		catch (IOException | SQLException | PropertyVetoException e) 
		{
			throw new CFMSException(e);
		}
		finally
		{
			try{
				conn.close();
				update_emp.close();
			}
			catch (SQLException e) 
			{
				throw new CFMSException(e);
			}
			
		}
		return success;
	}

	@Override
	/*
	 * (non-Javadoc)
	 * @see com.cfms.server.dao.interfaces.EmployeeDAO#deleteEmployee(com.cfms.shared.pojo.Employee)
	 */
	public boolean deleteEmployee(Employee employee) throws CFMSException {
		PreparedStatement delete_emp=null;
		boolean success=false;
		try{
//			conn=datasource.getConnection();
			conn=DataSource.getInstance().getConnection();
			delete_emp=conn.prepareStatement("delete from Employee where Employee_id=?");
			delete_emp.setString(1, employee.getEid());
			success=delete_emp.execute();
		}
		catch (IOException | SQLException | PropertyVetoException e) 
		{
			throw new CFMSException(e);
		}
		finally
		{
			try{
				conn.close();
				delete_emp.close();
			}
			catch (SQLException e) 
			{
				throw new CFMSException(e);
			}
			
		}
		return success;
	}


	@Override
	/*
	 * (non-Javadoc)
	 * @see com.cfms.server.dao.interfaces.EmployeeDAO#insertCabUser(com.cfms.shared.pojo.Employee, com.cfms.shared.pojo.Cab)
	 */
	public boolean insertCabUser(Employee employee, Cab cab) throws CFMSException {
		PreparedStatement insert_cab=null;
		boolean success=false;
		try{
			conn=DataSource.getInstance().getConnection();
			insert_cab=conn.prepareStatement("insert into cab_user values(?,?)");
			insert_cab.setString(1, employee.getEid());
			insert_cab.setInt(2, cab.getCab_no());
			insert_cab.execute();
			success=true;
		}
		catch (IOException | SQLException | PropertyVetoException e) 
		{
			throw new CFMSException(e);
		}
		finally
		{
			try{
				conn.close();
				insert_cab.close();
			}
			catch (SQLException e) 
			{
				throw new CFMSException(e);
			}
			
		}
		return success;
	}

	@Override
	/*
	 * (non-Javadoc)
	 * @see com.cfms.server.dao.interfaces.EmployeeDAO#getCabUser(com.cfms.shared.pojo.Cab)
	 */
	public List<Employee> getCabUser(Cab cab) throws CFMSException {
		Statement cab_user=null;
		List<Employee> l1=new ArrayList<Employee>();
		try{
//			conn=datasource.getConnection();
			conn=DataSource.getInstance().getConnection();
			cab_user=conn.createStatement();
			ResultSet rs=cab_user.executeQuery("select * from employee e,cab-user c where e.Employee_id=c.Employee_id and c.cab_no=cab.getCab_no()"); 
			while(rs.next())
			{
				Employee emp=new Employee();
				emp.setEid(rs.getString("Employee_id"));
				emp.setEname(rs.getString("name"));
				emp.setEmail(rs.getString("address"));
				emp.setEmail(rs.getString("email"));
				emp.setPhone(rs.getString("phone_no"));
				emp.setIsAdmin(rs.getBoolean("isadmin"));
				l1.add(emp);
			}
			}
		catch (IOException | SQLException | PropertyVetoException e) 
		{
			throw new CFMSException(e);
		}
		finally
		{
			try{
				conn.close();
				cab_user.close();
			}
			catch (SQLException e) 
			{
				throw new CFMSException(e);
			}
			
		}
		return l1;
	}
	
	public Employee validateUser(String eid,String pwd) throws CFMSException {
		PreparedStatement validate_emp=null;
		Employee emp=null;
		try
		{
//			conn=datasource.getConnection();
			conn=DataSource.getInstance().getConnection();
			validate_emp=conn.prepareStatement("select * from employee where Employee_id=? and password=?");
			validate_emp.setString(1, eid);
			validate_emp.setString(2, pwd);
			ResultSet rs=validate_emp.executeQuery();
			if(rs.next())
			{
				emp=new Employee();
				emp.setEid(rs.getString("Employee_id"));
				emp.setAddress(rs.getString("address"));
				emp.setEmail(rs.getString("email"));
				emp.setEname(rs.getString("name"));
				//emp.setPassword(rs.getString("password"));
				emp.setPhone(rs.getString("phone_no"));
				emp.setIsAdmin(rs.getBoolean("isadmin"));
			}
			
		}
		catch (IOException | SQLException | PropertyVetoException e) 
		{
			throw new CFMSException(e);
		}
		finally
		{
			try{
				conn.close();
				validate_emp.close();
			}
			catch (SQLException e) 
			{
				throw new CFMSException(e);
			}
			
		}
		return emp;
	}

	@Override
	/*
	 * (non-Javadoc)
	 * @see com.cfms.server.dao.interfaces.EmployeeDAO#changePassword(com.cfms.shared.pojo.Employee)
	 */
	public boolean changePassword(Employee employee) throws CFMSException {
		
		PreparedStatement change_pass=null;
		boolean success=false;
		try{
			conn=DataSource.getInstance().getConnection();
			change_pass=conn.prepareStatement("update Employee set password=? where Employee_id=?");
			change_pass.setString(1, employee.getPassword());
			change_pass.setString(2, employee.getEid());
			change_pass.executeUpdate();
			success=true;
		}
		catch(IOException |SQLException| PropertyVetoException e)
		{
			throw new CFMSException(e);
		}
		finally
		{
			try{
				conn.close();
				
			}
			catch (SQLException e) 
			{
				throw new CFMSException(e);
			}	
		}
		
		return success;
	}

	@Override
	/*
	 * (non-Javadoc)
	 * @see com.cfms.server.dao.interfaces.EmployeeDAO#validatePassword(com.cfms.shared.pojo.Employee)
	 */
	public boolean validatePassword(Employee employee) throws CFMSException {
		PreparedStatement validate_pass=null;
		
		boolean success=false;
		try
		{
//			conn=datasource.getConnection();
			conn=DataSource.getInstance().getConnection();
			validate_pass=conn.prepareStatement("select * from employee where Employee_id=? and password=?");
			validate_pass.setString(1, employee.getEid());
			validate_pass.setString(2, employee.getPassword());
			
			ResultSet rs=validate_pass.executeQuery();
			if(rs.next())
			{
			success=true;					
			}
			
		}
		catch (IOException | SQLException | PropertyVetoException e) 
		{
			throw new CFMSException(e);
		}
		finally
		{
			try{
				conn.close();
				validate_pass.close();
			}
			catch (SQLException e) 
			{
				throw new CFMSException(e);
			}
			
		}
		return success;
	}
	

}
