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

import com.cfms.shared.exceptions.CFMSException;
import com.cfms.shared.pojo.PickupPoint;
import com.cfms.server.dao.interfaces.PickupPointDAO;


/**
 * Implements the methods specified by PickupPointDAO
 * @author Priyanka, Chaitra, Samta
 * @see PickupPointDAO
 *
 */
public class PickupPointDAOImpl implements PickupPointDAO
{
	
	@Override
	/*
	 * (non-Javadoc)
	 * @see com.cfms.server.dao.interfaces.PickupPointDAO#insertPickupPoint(com.cfms.shared.pojo.PickupPoint)
	 */
	public boolean insertPickupPoint(PickupPoint p) throws CFMSException
	{
		boolean insertSuccess=false;
		Connection conn=null;
		PreparedStatement insert_stmt=null;
		try
		{
			conn=DataSource.getInstance().getConnection(); //get the connection from connection pool
			insert_stmt = conn.prepareStatement("insert into pickup_point(location) values (?)",Statement.RETURN_GENERATED_KEYS);
			insert_stmt.setString(1, p.getLocation());
			insert_stmt.execute();
			ResultSet rs=insert_stmt.getGeneratedKeys();
			if(rs.next())
				p.setPickup_id(rs.getInt(1));
			System.out.println(p.getPickup_id());
			insertSuccess=true;
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
	/*
	 * (non-Javadoc)
	 * @see com.cfms.server.dao.interfaces.PickupPointDAO#deletePickupPoint(com.cfms.shared.pojo.PickupPoint)
	 */
	public boolean deletePickupPoint(PickupPoint p) throws CFMSException
	{
		boolean deleteSuccess=false;
		Connection conn=null;
		PreparedStatement delete_stmt=null;
		try
		{
			conn=DataSource.getInstance().getConnection(); //get the connection from connection pool
			delete_stmt = conn.prepareStatement("delete from pickup_point where pickup_point_id=? or location=?");
			delete_stmt.setInt(1, p.getPickup_id());
			delete_stmt.setString(2, p.getLocation());
			delete_stmt.execute();
			deleteSuccess=true;
		}
		catch(SQLException | IOException | PropertyVetoException e)
		{
			throw new CFMSException(e);
		}
		finally
		{
			try {
				conn.close(); //close the connection
				delete_stmt.close();
			} catch (SQLException e) {
				throw new CFMSException(e);
			}
		}
		return deleteSuccess;
	}
	@Override
	/*
	 * (non-Javadoc)
	 * @see com.cfms.server.dao.interfaces.PickupPointDAO#getPickupPoint(int)
	 */
	public PickupPoint getPickupPoint(int pickupno) throws CFMSException
	{
		Connection conn=null;
		PreparedStatement select_stmt=null;
		PickupPoint pickup_point=null;
		ResultSet pickup_point_rs;
		try
		{
			conn=DataSource.getInstance().getConnection(); //get the connection from connection pool
			select_stmt = conn.prepareStatement("select * from pickup_point where pickup_point_id=?");
			select_stmt.setInt(1, pickupno);
			pickup_point_rs=select_stmt.executeQuery();
			if(pickup_point_rs.next())
			{
				pickup_point=new PickupPoint();
				pickup_point.setLocation(pickup_point_rs.getString("location"));
				pickup_point.setPickup_id(pickup_point_rs.getInt("pickup_point_id"));
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
		return pickup_point;
	}
	@Override
	/*
	 * (non-Javadoc)
	 * @see com.cfms.server.dao.interfaces.PickupPointDAO#getPickupPoint(java.lang.String)
	 */
	public PickupPoint getPickupPoint(String location) throws CFMSException {
		Connection conn=null;
		PreparedStatement select_stmt=null;
		PickupPoint pickup_point=null;
		ResultSet pickup_point_rs;
		try
		{
			conn=DataSource.getInstance().getConnection(); //get the connection from connection pool
			select_stmt = conn.prepareStatement("select * from pickup_point where location=?");
			select_stmt.setString(1, location);
			pickup_point_rs=select_stmt.executeQuery();
			if(pickup_point_rs.next())
			{
				pickup_point=new PickupPoint();
				pickup_point.setLocation(pickup_point_rs.getString("location"));
				pickup_point.setPickup_id(pickup_point_rs.getInt("pickup_point_id"));
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
		return pickup_point;
	}
	@Override
	/*
	 * (non-Javadoc)
	 * @see com.cfms.server.dao.interfaces.PickupPointDAO#insertPickupPointRoute(com.cfms.shared.pojo.PickupPoint, int, int)
	 */
	public boolean insertPickupPointRoute(PickupPoint p,int route_no,int index) throws CFMSException
	{
		boolean insertSuccess=false;
		Connection conn=null;
		PreparedStatement insert_stmt=null;
		try
		{
			conn=DataSource.getInstance().getConnection(); //get the connection from connection pool
			insert_stmt = conn.prepareStatement("insert into route_pickup_point(route_no,pickup_point_id,pickup_index) values (?,?,?)");
			insert_stmt.setInt(1,route_no);
			insert_stmt.setInt(2,p.getPickup_id());
			insert_stmt.setInt(3, index);
			insert_stmt.execute();
			insertSuccess=true;
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
	/*
	 * (non-Javadoc)
	 * @see com.cfms.server.dao.interfaces.PickupPointDAO#getPickupPoints(int)
	 */
	public List<PickupPoint> getPickupPoints(int route_no) throws CFMSException
	{
		Connection conn=null;
		PreparedStatement select_stmt=null;
		List<PickupPoint> pickup_points=null;
		PickupPoint pickup_point=null;
		ResultSet pickup_point_rs;
		try
		{
			conn=DataSource.getInstance().getConnection(); //get the connection from connection pool
			select_stmt = conn.prepareStatement("select p.pickup_point_id,p.location from pickup_point p,route_pickup_point rp where p.pickup_point_id=rp.pickup_point_id and rp.route_no=? order by pickup_index");
			select_stmt.setInt(1, route_no);
			pickup_point_rs=select_stmt.executeQuery();
			while(pickup_point_rs.next())
			{
				if(pickup_points==null)
					pickup_points=new ArrayList<>();
				pickup_point=new PickupPoint();
				pickup_point.setLocation(pickup_point_rs.getString("location"));
				pickup_point.setPickup_id(pickup_point_rs.getInt("pickup_point_id"));
				pickup_points.add(pickup_point);
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
		return pickup_points;
	}
	@Override
	/*
	 * (non-Javadoc)
	 * @see com.cfms.server.dao.interfaces.PickupPointDAO#getAllPickupPoints()
	 */
	public List<PickupPoint> getAllPickupPoints() throws CFMSException {
		Connection conn=null;
		PreparedStatement select_stmt=null;
		List<PickupPoint> pickup_list=new ArrayList<PickupPoint>();
		PickupPoint pickup_point=null;
		ResultSet pickup_point_rs;
		try
		{
			conn=DataSource.getInstance().getConnection(); //get the connection from connection pool
			select_stmt = conn.prepareStatement("select * from pickup_point");
			pickup_point_rs=select_stmt.executeQuery();
			while(pickup_point_rs.next())
			{
				pickup_point=new PickupPoint();
				pickup_point.setLocation(pickup_point_rs.getString("location"));
				pickup_point.setPickup_id(pickup_point_rs.getInt("pickup_point_id"));
				pickup_list.add(pickup_point);
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
		return pickup_list;
	}
}
