package com.cfms.server.dao;

import java.beans.PropertyVetoException;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import com.cfms.shared.exceptions.CFMSException;
import com.cfms.shared.pojo.PickupTime;
import com.cfms.server.dao.interfaces.PickupTimeDAO;

/**
 * implements the method specified in PickupTimeDAO
 * @author Samta, Priiyanka, Chaitra
 * @see PickupTimeDAO
 *
 */
public class PickupTimeDAOImpl implements PickupTimeDAO{

	@Override
	/*
	 * (non-Javadoc)
	 * @see com.cfms.server.dao.interfaces.PickupTimeDAO#insert(java.util.List)
	 */
	public Boolean insert(List<PickupTime> pt) throws CFMSException {
		
		    Connection con = null;
			PreparedStatement pstmt= null;
			
		
    boolean flag=false;
		
		try{
			
		PickupTime pickuptime;
		con=DataSource.getInstance().getConnection();
		
		//pstmt.setInt(1,cab.getCab_no());
		
			for(int i=0;i<pt.size();i++)
			
			{	
				pstmt=con.prepareStatement("insert into pickup_time(pickup_point_id,cab_no,approx_time) values(?,?,?)");
				pickuptime= new PickupTime();
				pickuptime=pt.get(i);
				pstmt.setInt(1,pickuptime.getPickup_id());
				pstmt.setInt(2,pickuptime.getCab_number());
				pstmt.setString(3,pickuptime.getApprox_time());
				pstmt.execute();
				
				
			}
			flag=true;
			
		
		
		}
		
		catch(SQLException e)
		{
			throw new CFMSException(e);
			
		}
		
		catch(IOException e)
		{
			throw new CFMSException(e);
			
		}
		catch(PropertyVetoException e)
		{
			throw new CFMSException(e);
			
		}
		return flag;
	}

}
