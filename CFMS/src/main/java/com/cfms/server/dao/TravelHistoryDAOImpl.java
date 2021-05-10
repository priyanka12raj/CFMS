package com.cfms.server.dao;
import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.cfms.server.dao.interfaces.TravelHistoryDAO;
import com.cfms.shared.exceptions.CFMSException;
import com.cfms.shared.pojo.Cab;
import com.cfms.shared.pojo.Employee;
import com.cfms.shared.pojo.PickupPoint;
import com.cfms.shared.pojo.TravelHistory;

/**
 * implements all the methods specified in TravelHistoryDAO
 * @author Samta, Priyanka, Chaitra
 * @see TravelHistoryDAO
 *
 */

public class TravelHistoryDAOImpl implements TravelHistoryDAO{
	

	 List<TravelHistory> history;
	 Connection con = null;
	 Statement stmt= null;
	 PreparedStatement pstmt= null;
	 ResultSet rs= null;
	

	@Override
	/*
	 * (non-Javadoc)
	 * @see com.cfms.server.dao.interfaces.TravelHistoryDAO#getAllHistory()
	 */
	public List<TravelHistory> getAllHistory() throws CFMSException {
	
		try{
				
			    history= new ArrayList<TravelHistory>();
				TravelHistory th;
				EmployeeDAOImpl empdao;
				con= DataSource.getInstance().getConnection();
				stmt= con.createStatement();
				rs=stmt.executeQuery("select * from travel_history");
				while(rs.next())
					
				{
					th= new TravelHistory();
					empdao= new EmployeeDAOImpl();
					th.setEmp(empdao.getEmployee(rs.getString("employee_id")));
					th.setCab_no(rs.getInt("cab_no"));
					th.setP_no(rs.getInt("pickup_point_id"));
			     	th.setStart_date(rs.getDate("start_date").toString());
			     	if(rs.getDate("end_date")!=null)
			     		th.setEnd_till(rs.getDate("end_date").toString());
			     	else
			     		th.setEnd_till("till date");
				//	System.out.println( "start date --->"+rs.getDate("start_date"));
					
					history.add(th);
					
				}	
				
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
			return history;
			
			
	}

	@Override
	/*
	 * (non-Javadoc)
	 * @see com.cfms.server.dao.interfaces.TravelHistoryDAO#getHistory(com.cfms.shared.pojo.Employee)
	 */
	public List<TravelHistory> getHistory(Employee emp) throws CFMSException {
		
			TravelHistory th;
			List<TravelHistory>  his;
			EmployeeDAOImpl empdao;
		
		try
		{
			
			empdao= new EmployeeDAOImpl();
			his = new ArrayList<TravelHistory>();
			con= DataSource.getInstance().getConnection();
			String query="select * from travel_history where employee_id=?";
			pstmt=con.prepareStatement(query);
			pstmt.setString(1, emp.getEid());
			rs=pstmt.executeQuery();
			while(rs.next())
			{
				th=new TravelHistory();
				th.setEmp(empdao.getEmployee(rs.getString("employee_id")));
				th.setCab_no(rs.getInt("cab_no"));
				th.setP_no(rs.getInt("pickup_point_id"));
     			th.setStart_date(rs.getDate("start_date").toString());
     			if(rs.getDate("end_date")!=null)
     				th.setEnd_till(rs.getDate("end_date").toString());
     			else
		     		th.setEnd_till("till date");
				his.add(th);
			}
		}
			
		catch(SQLException e)
		{
			//throw new CFMSException(e);
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
		return his;
		
	}
	@Override
	/*
	 * (non-Javadoc)
	 * @see com.cfms.server.dao.interfaces.TravelHistoryDAO#getHistory(com.cfms.shared.pojo.Employee, com.cfms.shared.pojo.Cab, com.cfms.shared.pojo.PickupPoint)
	 */
	public TravelHistory getHistory(Employee emp, Cab cab, PickupPoint p) throws CFMSException
	{
		
			TravelHistory th=null;
			EmployeeDAOImpl empdao;
		
		try
		{
			
			empdao= new EmployeeDAOImpl();
			con= DataSource.getInstance().getConnection();
			System.out.println(emp.getEid());
			String query="select * from travel_history where employee_id=? and cab_no=? and pickup_point_id=?";
			pstmt=con.prepareStatement(query);
			pstmt.setString(1, emp.getEid());
			pstmt.setInt(2, cab.getCab_no());
			pstmt.setInt(3, p.getPickup_id());
			rs=pstmt.executeQuery();
			while(rs.next())
			{
				th=new TravelHistory();
				th.setEmp(empdao.getEmployee(rs.getString("employee_id")));
				th.setCab_no(rs.getInt("cab_no"));
				th.setP_no(rs.getInt("pickup_point_id"));
     			th.setStart_date(rs.getDate("start_date").toString());
     			if(rs.getDate("end_date")!=null)
     				th.setEnd_till(rs.getDate("end_date").toString());
     			else
		     		th.setEnd_till("-");
				
			}
		}
			
		catch(SQLException e)
		{
			//throw new CFMSException(e);
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
		return th;
		
	}

	@Override
	/*
	 * (non-Javadoc)
	 * @see com.cfms.server.dao.interfaces.TravelHistoryDAO#updateHistory(com.cfms.shared.pojo.TravelHistory)
	 */
	public void updateHistory(TravelHistory history) throws CFMSException {
		
		try{
			
			con= DataSource.getInstance().getConnection();
			String query = " update travel_history set end_date=? where employee_id=? and cab_no=? and pickup_point_id=? and start_date=?";
			pstmt= con.prepareStatement(query);
			pstmt.setString(1, history.getEnd_till());
			pstmt.setString(2, history.getEmp().getEid());
			pstmt.setInt(3, history.getCab_no());
			pstmt.setInt(4, history.getP_no());
			pstmt.setString(5, history.getStart_date());
			pstmt.executeUpdate();
			System.out.println(" row updated");
			}
			
			catch(SQLException e)
			{
				//throw new CFMSException(e);
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
			
		
	}

	@Override
	/*
	 * (non-Javadoc)
	 * @see com.cfms.server.dao.interfaces.TravelHistoryDAO#insert(com.cfms.shared.pojo.TravelHistory)
	 */
	public boolean insert(TravelHistory history) throws CFMSException {
		boolean flag=false;
		
		try{
		con=DataSource.getInstance().getConnection();
		pstmt=con.prepareStatement("insert into travel_history(employee_id,cab_no,pickup_point_id,start_date,end_date) values(?,?,?,?,?)");
		//pstmt.setInt(1,cab.getCab_no());
		pstmt.setString(1,history.getEmp().getEid());
		pstmt.setInt(2,history.getCab_no());
		pstmt.setInt(3,history.getP_no());
		pstmt.setString(4,history.getStart_date());
		pstmt.setString(5,history.getEnd_till());
		flag=pstmt.execute();
		
		//rs=stmt.executeQuery("insert into cab values()
		
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
	
	public static void main(String args[]) throws CFMSException
	{
		TravelHistory th=new TravelHistory();
		Employee emp=new Employee();
		emp.setEid("13GAEC9084");
		th.setEmp(emp);
		th.setCab_no(1);
		th.setP_no(1);
		Date today=new Date();
		DateFormat df=new SimpleDateFormat("YYYY-MM-dd");
		System.out.print(df.format(today));
		th.setStart_date(df.format(today));
		TravelHistoryDAO tdao=new TravelHistoryDAOImpl();
		tdao.insert(th);
	}
}
