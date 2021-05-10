package com.cfms.server.dao;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import com.cfms.server.dao.interfaces.CabPickupTimeDAO;
import com.cfms.shared.exceptions.CFMSException;
import com.cfms.shared.pojo.Cab;
import com.cfms.shared.pojo.CabPickupTime;
import com.cfms.shared.pojo.PickupPoint;

/**
 * implements all the methods specified by CabPickupTimeDAO
 * @author Priyanka, Chaitra, Samta
 * @see CabPickupTimeDAO
 */
public class CabPickupTimeDAOImpl implements CabPickupTimeDAO
{
	
	@Override
	/*
	 * (non-Javadoc)
	 * @see com.cfms.server.dao.interfaces.CabPickupTimeDAO#insertCabPickupTime(com.cfms.shared.pojo.CabPickupTime)
	 */
	public boolean insertCabPickupTime(CabPickupTime cpt) throws CFMSException
	{
		boolean insertSuccess=false;
		Connection conn=null;
		PreparedStatement insert_stmt=null;
		try
		{
			conn=DataSource.getInstance().getConnection(); //get the connection from connection pool
			//quer
			insert_stmt = conn.prepareStatement("insert into pickup_time(pickup_point_id,cab_no,approx_time) values (?,?,?)",Statement.RETURN_GENERATED_KEYS);
			insert_stmt.setInt(1, cpt.getPickup_point().getPickup_id());
			insert_stmt.setInt(2, cpt.getCab().getCab_no());
			insert_stmt.setTime(3, Time.valueOf(cpt.getPickup_time()));
			insert_stmt.execute();
			insertSuccess=true;
		}
		catch(SQLException e)
		{
			throw new CFMSException(e);
		} catch (IOException e) {
			throw new CFMSException(e);
		} catch (PropertyVetoException e) {
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
	 * @see com.cfms.server.dao.interfaces.CabPickupTimeDAO#updateCabPickupTime(com.cfms.shared.pojo.CabPickupTime)
	 */
	public boolean updateCabPickupTime(CabPickupTime cpt) throws CFMSException
	{
		boolean updateSuccess=false;
		Connection conn=null;
		PreparedStatement update_stmt=null;
		try
		{
			conn=DataSource.getInstance().getConnection(); //get the connection from connection pool
			update_stmt = conn.prepareStatement("update pickup_time set approx_time=? where cab_no=? and pickup_point_id=?");
			update_stmt.setTime(1,Time.valueOf(cpt.getPickup_time()));
			update_stmt.setInt(2, cpt.getCab().getCab_no());
			update_stmt.setInt(3, cpt.getPickup_point().getPickup_id());
			update_stmt.executeUpdate();
			updateSuccess=true;
		}
		catch(SQLException |IOException |PropertyVetoException e)
		{
			throw new CFMSException(e);
		}
		finally
		{
			try {
				conn.close(); //close the connection
				update_stmt.close();
			} catch (SQLException e) {
				throw new CFMSException(e);
			}
		}
		return updateSuccess;
	}

	@Override
	/*
	 * (non-Javadoc)
	 * @see com.cfms.server.dao.interfaces.CabPickupTimeDAO#deleteCabPickupTime(com.cfms.shared.pojo.CabPickupTime)
	 */
	public boolean deleteCabPickupTime(CabPickupTime cpt) throws CFMSException
	{
		boolean deleteSuccess=false;
		Connection conn=null;
		PreparedStatement delete_stmt=null;
		try
		{
			conn=DataSource.getInstance().getConnection(); //get the connection from connection pool
			delete_stmt = conn.prepareStatement("delete from pickup_time where cab_no=? and pickup_point_id=?");
			delete_stmt.setInt(1, cpt.getCab().getCab_no());
			delete_stmt.setInt(2, cpt.getPickup_point().getPickup_id());
			delete_stmt.execute();
			deleteSuccess=true;
		}
		catch(SQLException |IOException|PropertyVetoException e)
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
	 * @see com.cfms.server.dao.interfaces.CabPickupTimeDAO#getCabPickupTime(com.cfms.shared.pojo.PickupPoint)
	 */
	public List<CabPickupTime> getCabPickupTime(PickupPoint pickup_point) throws CFMSException
	{
		List<CabPickupTime> cabs=new ArrayList<>();
		Connection conn=null;
		PreparedStatement select_stmt=null;
		PickupPointDAOImpl pick_dao=new PickupPointDAOImpl();
		CabPickupTime cpt=null;
		CabDAOImpl cab_dao=new CabDAOImpl();
		ResultSet cpt_rs;
		try
		{
			conn=DataSource.getInstance().getConnection(); //get the connection from connection pool
			select_stmt = conn.prepareStatement("select approx_time,cab_no,pickup_point_id from pickup_time where pickup_point_id=?");
			select_stmt.setInt(1,pickup_point.getPickup_id());
			cpt_rs=select_stmt.executeQuery();
			List<CPT> cpts=new ArrayList<CPT>();
			while(cpt_rs.next())
			{
				CPT cpt_temp=new CPT();
				cpt_temp.setApprox_time(cpt_rs.getTime("approx_time"));
				cpt_temp.setCab_id(cpt_rs.getInt("cab_no"));
				cpt_temp.setPickup_id(cpt_rs.getInt("pickup_point_id"));
				cpts.add(cpt_temp);
			}
			for(CPT cpt_t:cpts)
			{
				cpt=new CabPickupTime();
				cpt.setCab(cab_dao.getCab(cpt_t.getCab_id()));
				cpt.setPickup_point(pick_dao.getPickupPoint(cpt_t.getPickup_id()));
				cpt.setPickup_time(cpt_t.getApprox_time());
				cabs.add(cpt);
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
		return cabs;
	}

	@Override
	/*
	 * (non-Javadoc)
	 * @see com.cfms.server.dao.interfaces.CabPickupTimeDAO#getCabPickupTime(com.cfms.shared.pojo.Cab)
	 */
	public List<CabPickupTime> getCabPickupTime(Cab cab) throws CFMSException {
		List<CabPickupTime> cab_pickup_timings=new ArrayList<>();
		Connection conn=null;
		PreparedStatement select_stmt=null;
		PickupPointDAOImpl pick_dao=new PickupPointDAOImpl();
		CabDAOImpl cab_dao=new CabDAOImpl();
		CabPickupTime cpt=null;
		List<CPT> cpts=new ArrayList<CabPickupTimeDAOImpl.CPT>();
		ResultSet cpt_rs;
		try
		{
			conn=DataSource.getInstance().getConnection(); //get the connection from connection pool
			select_stmt = conn.prepareStatement("select approx_time,cab_no,pickup_point_id from pickup_time where cab_no=?");
			select_stmt.setInt(1,cab.getCab_no());
			cpt_rs=select_stmt.executeQuery();
			/**
			 * retrieve the data from result set
			 */
			while(cpt_rs.next())
			{
				CPT cpt_temp=new CPT();
				cpt_temp.setApprox_time(cpt_rs.getTime("approx_time"));
				cpt_temp.setCab_id(cpt_rs.getInt("cab_no"));
				cpt_temp.setPickup_id(cpt_rs.getInt("pickup_point_id"));
				cpts.add(cpt_temp);
			}
			/**
			 * retrieve objects (cab,pickup point) from their id's
			 */
			for(CPT cpt_t:cpts)
			{
				cpt=new CabPickupTime();
				cpt.setCab(cab_dao.getCab(cpt_t.getCab_id()));
				cpt.setPickup_point(pick_dao.getPickupPoint(cpt_t.getPickup_id()));
				cpt.setPickup_time(cpt_t.getApprox_time());
				cab_pickup_timings.add(cpt);
			}
			
		}
		catch(SQLException e)
		{
			throw new CFMSException(e);
		} catch (IOException e) {
			throw new CFMSException(e);
		} catch (PropertyVetoException e) {
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
		return cab_pickup_timings;

	}
	/**
	 * Used for storing intermediate values
	 * @author Priyanka
	 *
	 */
	private class CPT
	{
		int cab_id;
		int pickup_id;
		Time approx_time;
		/**
		 * gets the cab id
		 * @return cab id
		 */
		public int getCab_id() {
			return cab_id;
		}
		/**
		 * sets the cab id
		 * @param cab_id cab id to be set
		 */
		public void setCab_id(int cab_id) {
			this.cab_id = cab_id;
		}
		/**
		 * gets the pickup point id
		 * @return pickup point id
		 */
		public int getPickup_id() {
			return pickup_id;
		}
		/**
		 * sets the pickup point id
		 * @param pickup_id pickup point id to set
		 */
		public void setPickup_id(int pickup_id) {
			this.pickup_id = pickup_id;
		}
		/**
		 * gets the approx_time
		 * @return approx_time
		 */
		public Time getApprox_time() {
			return approx_time;
		}
		/**
		 * sets the approx time
		 * @param approx_time approximate time to set
		 */
		public void setApprox_time(Time approx_time) {
			this.approx_time = approx_time;
		}
	}
	
}
