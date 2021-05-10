package com.cfms.server.dao;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;

import com.cfms.server.dao.interfaces.CabDAO;
import com.cfms.shared.exceptions.CFMSException;
import com.cfms.shared.pojo.Cab;

/**
 * implements the methods specified by CabDAO
 *
 * @author Samta, Priyanka, Chaitra
 * @see CabDAO
 */

public class CabDAOImpl implements CabDAO {


        @Override
        /*
         * (non-Javadoc)
         * @see com.cfms.server.dao.interfaces.CabDAO#getAllCabs()
         */ public List<Cab> getAllCabs() throws CFMSException {
                Connection con = null;
                Statement stmt = null;
                ResultSet rs = null;
                List<Cab> cabs = new ArrayList<Cab>();
                try {

                        con = DataSource.getInstance().getConnection();
                        stmt = con.createStatement();
                        rs = stmt.executeQuery("select * from cab");
                        while (rs.next()) {
                                Cab c = new Cab();
                                c.setCab_no(rs.getInt("cab_no"));
                                c.setNo_of_seats(rs.getInt("total_no_seats"));
                                c.setCost_per_month(rs.getDouble("cost_per_month"));
                                c.setProvider(rs.getString("Provider"));
                                c.setRoute_no(rs.getInt("route_no"));
                                c.setStart_time_source(rs.getTime("start_time_source").toString());
                                c.setStart_time_NC(rs.getTime("start_time_NC").toString());

                                cabs.add(c);

                        }

                } catch (SQLException e) {
                        throw new CFMSException(e);

                } catch (IOException e) {
                        throw new CFMSException(e);

                } catch (PropertyVetoException e) {
                        throw new CFMSException(e);

                } finally {
                        try {
                                stmt.close();
                                con.close();
                        } catch (SQLException e) {
                                e.printStackTrace();
                        }

                }
                return cabs;


        }

        @Override
        /*
         * (non-Javadoc)
         * @see com.cfms.server.dao.interfaces.CabDAO#getCab(int)
         */ public Cab getCab(int Cab_no) throws CFMSException {
                Cab c = new Cab();
                Connection con = null;
                PreparedStatement pstmt = null;
                ResultSet rs = null;

                try {
                        con = DataSource.getInstance().getConnection();
                        String query = "select * from cab where cab_no=?";
                        pstmt = con.prepareStatement(query);
                        pstmt.setInt(1, Cab_no);
                        rs = pstmt.executeQuery();
                        while (rs.next()) {
                                int tot = rs.getInt("total_no_seats");
                                int cno = rs.getInt("cab_no");
                                c.setCab_no(cno);
                                c.setNo_of_seats(tot);
                                c.setCost_per_month(rs.getDouble("cost_per_month"));
                                c.setProvider(rs.getString("Provider"));
                                c.setRoute_no(rs.getInt("route_no"));
                                c.setStart_time_source(rs.getTime("start_time_source").toString());
                                c.setStart_time_NC(rs.getTime("start_time_NC").toString());
                                c.setAvail_seats(tot - getOccupiedSeats(cno));
                        }
                } catch (SQLException e) {
                        //throw new CFMSException(e);
                        e.printStackTrace();

                } catch (IOException e) {
                        throw new CFMSException(e);

                } catch (PropertyVetoException e) {
                        throw new CFMSException(e);

                } finally {
                        try {
                                pstmt.close();
                                con.close();
                        } catch (SQLException e) {
                                e.printStackTrace();
                        }

                }
                return c;


        }

        @Override
        /*
         * (non-Javadoc)
         * @see com.cfms.server.dao.interfaces.CabDAO#updateCab(com.cfms.shared.pojo.Cab)
         */ public void updateCab(Cab cab) throws CFMSException {
                Connection con = null;
                PreparedStatement pstmt = null;
                try {
                        con = DataSource.getInstance().getConnection();
                        String query = " update cab set cost_per_month=?,start_time_source=?,start_time_NC=? where cab_no=?";
                        pstmt = con.prepareStatement(query);
                        //pstmt.setInt(1, cab.getNo_of_seats());
                        pstmt.setDouble(1, cab.getCost_per_month());
                        //pstmt.setString(3, cab.getProvider());
                        //pstmt.setInt(4, cab.getRoute_no());
                        pstmt.setString(2, cab.getStart_time_source());
                        pstmt.setString(3, cab.getStart_time_NC());
                        pstmt.setInt(4, cab.getCab_no());

                        pstmt.executeUpdate();
                        System.out.println(" row updated");
                } catch (SQLException e) {
                        //throw new CFMSException(e);
                        e.printStackTrace();

                } catch (IOException e) {
                        throw new CFMSException(e);

                } catch (PropertyVetoException e) {
                        throw new CFMSException(e);

                } finally {
                        try {
                                pstmt.close();
                                con.close();
                        } catch (SQLException e) {
                                e.printStackTrace();
                        }

                }

        }

        @Override
        /*
         * (non-Javadoc)
         * @see com.cfms.server.dao.interfaces.CabDAO#deleteCab(int)
         */ public void deleteCab(int cab_no) throws CFMSException {
                Connection con = null;
                PreparedStatement pstmt = null;
                try {
                        con = DataSource.getInstance().getConnection();
                        String query = " delete from cab where cab_no=?";
                        pstmt = con.prepareStatement(query);
                        pstmt.setInt(1, cab_no);
                        pstmt.executeUpdate();
                        System.out.println("row is deleted");
                } catch (SQLException e) {
                        throw new CFMSException(e);

                } catch (IOException e) {
                        throw new CFMSException(e);

                } catch (PropertyVetoException e) {
                        throw new CFMSException(e);

                } finally {
                        try {
                                pstmt.close();
                                con.close();
                        } catch (SQLException e) {
                                e.printStackTrace();
                        }

                }

        }


        @Override
        /*
         * (non-Javadoc)
         * @see com.cfms.server.dao.interfaces.CabDAO#insert(com.cfms.shared.pojo.Cab)
         */ public boolean insert(Cab cab) throws CFMSException {
                Connection con = null;
                PreparedStatement pstmt = null;


                boolean flag = false;

                try {
                        con = DataSource.getInstance().getConnection();
                        pstmt = con.prepareStatement(
                                "insert into cab(total_no_seats,cost_per_month,Provider,route_no,start_time_source,start_time_NC) values(?,?,?,?,?,?)",
                                Statement.RETURN_GENERATED_KEYS);
                        //pstmt.setInt(1,cab.getCab_no());
                        pstmt.setInt(1, cab.getNo_of_seats());
                        pstmt.setDouble(2, cab.getCost_per_month());
                        pstmt.setString(3, cab.getProvider());
                        pstmt.setInt(4, cab.getRoute_no());
                        pstmt.setString(5, cab.getStart_time_source());
                        pstmt.setString(6, cab.getStart_time_NC());
                        pstmt.execute();
                        ResultSet rs = pstmt.getGeneratedKeys();
                        if (rs.next())
                                cab.setCab_no(rs.getInt(1));
                        System.out.println("cab number **= " + cab.getCab_no());
                        flag = true;

                        //rs=stmt.executeQuery("insert into cab values()

                } catch (SQLException e) {
                        throw new CFMSException(e);

                } catch (IOException e) {
                        throw new CFMSException(e);

                } catch (PropertyVetoException e) {
                        throw new CFMSException(e);

                } finally {
                        try {
                                pstmt.close();
                                con.close();
                        } catch (SQLException e) {
                                e.printStackTrace();
                        }

                }
                return flag;
        }


        @Override
        /*
         * (non-Javadoc)
         * @see com.cfms.server.dao.interfaces.CabDAO#getOccupiedSeats(int)
         */ public int getOccupiedSeats(int cabno) throws CFMSException {
                int occupiedSeats = 0;
                Connection con = null;
                PreparedStatement pstmt = null;
                ResultSet rs = null;
                try {
                        con = DataSource.getInstance().getConnection();
                        pstmt = con.prepareStatement("select count(*) from cab_user where cab_no=?");
                        pstmt.setInt(1, cabno);
                        rs = pstmt.executeQuery();
                        if (rs.next()) {
                                occupiedSeats = rs.getInt(1);
                        }

                } catch (SQLException | IOException | PropertyVetoException e) {
                        throw new CFMSException(e);

                } finally {
                        try {
                                pstmt.close();
                                con.close();
                        } catch (SQLException e) {
                                e.printStackTrace();
                        }

                }
                return occupiedSeats;
        }

        public static void main(String args[]) throws CFMSException {
                CabDAOImpl cdao = new CabDAOImpl();
                System.out.println(cdao.getOccupiedSeats(1));
        }


}


