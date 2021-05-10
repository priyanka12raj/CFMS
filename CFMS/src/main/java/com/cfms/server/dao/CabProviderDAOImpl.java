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

import com.cfms.server.dao.interfaces.CabProviderDAO;
import com.cfms.shared.exceptions.CFMSException;
import com.cfms.shared.pojo.CabProviderBean;

/**
 * implements all the methods specified by CabProviderDAO
 * @author Samta, Chaitra, Priyanka
 * @see CabProviderDAO
 *
 */
public class CabProviderDAOImpl implements CabProviderDAO{
	Connection conn;
	@Override
	/*
	 * (non-Javadoc)
	 * @see com.cfms.server.dao.interfaces.CabProviderDAO#insertCabProvider(com.cfms.shared.pojo.CabProviderBean)
	 */
	public boolean insertCabProvider(CabProviderBean cabProvider) throws CFMSException {
		boolean insertSuccess=false;
		PreparedStatement insert_cab=null;
		try{
			conn=DataSource.getInstance().getConnection();
			insert_cab=conn.prepareStatement("insert into cab_provider values(?,?,?,?)");  
			insert_cab.setString(1, cabProvider.getCompany_name());
			insert_cab.setString(2,cabProvider.getAddress());
			insert_cab.setString(3, cabProvider.getEmail());
			insert_cab.setString(4, cabProvider.getPhone());
			
			
			insert_cab.execute();
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
				insert_cab.close();
			}
			catch (SQLException e) 
			{
				throw new CFMSException(e);
			}
			
		}
		return insertSuccess;
	}

	@Override
	/*
	 * (non-Javadoc)
	 * @see com.cfms.server.dao.interfaces.CabProviderDAO#deleteCabProvider(com.cfms.shared.pojo.CabProviderBean)
	 */
	public boolean deleteCabProvider(CabProviderBean cabProvider) throws CFMSException {
		
		PreparedStatement delete_cab=null;
		boolean success=false;
		try{
//			conn=datasource.getConnection();
			conn=DataSource.getInstance().getConnection();
			delete_cab=conn.prepareStatement("delete from cab_provider where company_name=?");
			delete_cab.setString(1, cabProvider.getCompany_name());
			success=delete_cab.execute();
		}
		catch (IOException | SQLException | PropertyVetoException e) 
		{
			throw new CFMSException(e);
		}
		finally
		{
			try{
				conn.close();
				delete_cab.close();
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
	 * @see com.cfms.server.dao.interfaces.CabProviderDAO#updateCabProvider(com.cfms.shared.pojo.CabProviderBean)
	 */
	public boolean updateCabProvider(CabProviderBean cabProvider) throws CFMSException {
		PreparedStatement update_cab=null;
		boolean success=false;
		try{
//			conn=datasource.getConnection();
			conn=DataSource.getInstance().getConnection();
			update_cab=conn.prepareStatement("update cab_provider set Address=?,email=?,phone=? where company_name=?");
			
			update_cab.setString(1, cabProvider.getAddress());
			update_cab.setString(2,cabProvider.getEmail() );
			update_cab.setString(3,cabProvider.getPhone() );
			update_cab.setString(4, cabProvider.getCompany_name());
			
			update_cab.executeUpdate();
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
	 * @see com.cfms.server.dao.interfaces.CabProviderDAO#getAllCabProviders()
	 */
	public List<CabProviderBean> getAllCabProviders() throws CFMSException {
		
		Statement retrieve_all=null;
		List<CabProviderBean> l1=new ArrayList<CabProviderBean>();
		try{
//			conn=datasource.getConnection();
			conn=DataSource.getInstance().getConnection();
			retrieve_all=conn.createStatement();
			ResultSet rs=retrieve_all.executeQuery("select * from cab_provider"); 
			while(rs.next())
			{
				CabProviderBean cpBean=new CabProviderBean();
				cpBean.setCompany_name(rs.getString("company_name"));
				cpBean.setAddress(rs.getString("Address"));
				cpBean.setEmail(rs.getString("email"));
				
				cpBean.setPhone(rs.getString("phone"));
				
				l1.add(cpBean);
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
	 * @see com.cfms.server.dao.interfaces.CabProviderDAO#getCabProvider(com.cfms.shared.pojo.CabProviderBean)
	 */
	public CabProviderBean getCabProvider(CabProviderBean providerBean) throws CFMSException {
		PreparedStatement retrieve_cab_provider=null;
		CabProviderBean cabProviderBean=null;
		try
		{
//			conn=datasource.getConnection();
			conn=DataSource.getInstance().getConnection();
			retrieve_cab_provider=conn.prepareStatement("select * from cab_provider where company_name=?");
			retrieve_cab_provider.setString(1, providerBean.getCompany_name());
			ResultSet rs=retrieve_cab_provider.executeQuery();
			if(rs.next())
			{
				cabProviderBean=new CabProviderBean();
				cabProviderBean.setCompany_name(rs.getString("company_name"));
				cabProviderBean.setAddress(rs.getString("Address"));
				cabProviderBean.setEmail(rs.getString("email"));
				
				
				cabProviderBean.setPhone(rs.getString("phone"));
				
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
				retrieve_cab_provider.close();
			}
			catch (SQLException e) 
			{
				throw new CFMSException(e);
			}
			
		}
		return cabProviderBean;
		
	}

}
