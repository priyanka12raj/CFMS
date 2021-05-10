package com.cfms.server.dao;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.cfms.server.dao.interfaces.CabUserDAO;
import com.cfms.server.dao.interfaces.EmployeeDAO;
import com.cfms.server.dao.interfaces.PickupPointDAO;
import com.cfms.shared.exceptions.CFMSException;
import com.cfms.shared.pojo.CabUser;
import com.cfms.shared.pojo.Employee;


/**
 * Implements the methods specified by CabUserDAO
 * @author Priyanka, Samta, Chaitra
 * @see CabUserDAO
 */
public class CabUserDAOImpl implements CabUserDAO
{
	Connection conn;
	@Override
	/*
	 * (non-Javadoc)
	 * @see com.cfms.server.dao.interfaces.CabUserDAO#insertCabUser(com.cfms.shared.pojo.CabUser)
	 */
	public boolean insertCabUser(CabUser cabuser) throws CFMSException {
		PreparedStatement insert_cab=null;
		boolean success=false;
		try{
			conn=DataSource.getInstance().getConnection(); //get the connection from connection pool
			insert_cab=conn.prepareStatement("insert into cab_user values(?,?,?)");
			insert_cab.setString(1, cabuser.getEmployee().getEid());
			insert_cab.setInt(2, cabuser.getCab().getCab_no());
			insert_cab.setInt(3,cabuser.getPickuppoint().getPickup_id());
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
				conn.close(); //close the connection
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
	 * @see com.cfms.server.dao.interfaces.CabUserDAO#updateCabuser(com.cfms.shared.pojo.CabUser)
	 */
	public boolean updateCabuser(CabUser cabuser) throws CFMSException
	{
		PreparedStatement update_cab=null;
		boolean success=false;
		try{
			conn=DataSource.getInstance().getConnection(); //get the connection from connection pool
			update_cab=conn.prepareStatement("update cab_user set pickup_point_id=? where employee_id=? and cab_no=?");
			update_cab.setInt(1,cabuser.getPickuppoint().getPickup_id());
			update_cab.setString(2, cabuser.getEmployee().getEid());
			update_cab.setInt(3, cabuser.getCab().getCab_no());
			update_cab.execute();
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
				update_cab.close();
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
	 * @see com.cfms.server.dao.interfaces.CabUserDAO#deleteCabUser(com.cfms.shared.pojo.CabUser)
	 */
	public boolean deleteCabUser(CabUser cabuser) throws CFMSException {
		PreparedStatement delete_user=null;
		boolean success=false;
		try{
//			conn=datasource.getConnection();
			conn=DataSource.getInstance().getConnection(); //get the connection from connection pool
			delete_user=conn.prepareStatement("delete from cab_user where Employee_id=? and cab_no=?");
			delete_user.setString(1, cabuser.getEmployee().getEid());
			delete_user.setInt(2, cabuser.getCab().getCab_no());
			success=delete_user.execute();
		}
		catch (IOException | SQLException | PropertyVetoException e) 
		{
			throw new CFMSException(e);
		}
		finally
		{
			try{
				conn.close(); //close the connection
				delete_user.close();
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
	 * @see com.cfms.server.dao.interfaces.CabUserDAO#prepareCabUser(int, java.lang.String, int)
	 */
	public CabUser prepareCabUser(int cab_no, String employee_id, int pickup_id) throws CFMSException
	{
		CabDAOImpl cabDao=new CabDAOImpl();
		EmployeeDAO empDao=new EmployeeDAOImpl();
		PickupPointDAO pickupDAo=new PickupPointDAOImpl();
		CabUser cabuser=new CabUser();
		
		try 
		{
			cabuser.setCab(cabDao.getCab(cab_no));
			cabuser.setEmployee(empDao.getEmployee(employee_id));
			cabuser.setPickuppoint(pickupDAo.getPickupPoint(pickup_id));
		} 
		catch (CFMSException e)
		{
			throw e;
		}
		return cabuser;
	}
	@Override
	/*
	 * (non-Javadoc)
	 * @see com.cfms.server.dao.interfaces.CabUserDAO#getCabUser(com.cfms.shared.pojo.Employee)
	 */
	public CabUser getCabUser(Employee emp) throws CFMSException
	{
		Connection conn=null;
		PreparedStatement select_stmt=null;
		CabDAOImpl cabDao=new CabDAOImpl();
		EmployeeDAO empDao=new EmployeeDAOImpl();
		PickupPointDAO pickupDAo=new PickupPointDAOImpl();
		CabUser cabuser=null;
		ResultSet cabuser_rs=null;
		try
		{
			conn=DataSource.getInstance().getConnection(); //get the connection from connection pool
			select_stmt = conn.prepareStatement("select employee_id,cab_no,pickup_point_id from cab_user where employee_id=?");
			select_stmt.setString(1, emp.getEid());
			cabuser_rs=select_stmt.executeQuery();
			if(cabuser_rs.next())
			{
				cabuser=new CabUser();
				cabuser.setCab(cabDao.getCab(cabuser_rs.getInt("cab_no")));
				cabuser.setEmployee(empDao.getEmployee(cabuser_rs.getString("employee_id")));
				cabuser.setPickuppoint(pickupDAo.getPickupPoint(cabuser_rs.getInt("pickup_point_id")));
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
		return cabuser;
	}
	
}
