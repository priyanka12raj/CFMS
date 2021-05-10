package com.cfms.server.dao.interfaces;

import java.util.List;

import com.cfms.shared.exceptions.CFMSException;
import com.cfms.shared.pojo.PickupPoint;

/**
 * Specifies the required methods for manipulating pickup point 
 * @author Priyanka , Chaitra, Samta
 *
 */
public interface PickupPointDAO 
{
	/**
	 * inserts the pickup point into the database table pickup point
	 * @param pickup_point Pickup point to be inserted
	 * @return true if insertion is success; false otherwise
	 * @throws CFMSException
	 */
	public boolean insertPickupPoint(PickupPoint pickup_point)throws CFMSException;
	/**
	 * deletes the pickup point from the database
	 * @param pickup_point pickup point to be deleted
	 * @return true if success else false
	 * @throws CFMSException
	 */
	public boolean deletePickupPoint(PickupPoint pickup_point)throws CFMSException;
	/**
	 * gets the pickup point from database based on the pickup point id
	 * @param pickupno pickup point id
	 * @return pickup point with id and location
	 * @throws CFMSException
	 */
	public PickupPoint getPickupPoint(int pickupno)throws CFMSException;
	/**
	 * gets the pickup point based on the pickup point location.
	 * @param location pickup point location
	 * @return pickup point with id and location
	 * @throws CFMSException
	 */
	public PickupPoint getPickupPoint(String location)throws CFMSException;
	/**
	 * gets all the pickup points in the database
	 * @return list of all pickup points
	 * @throws CFMSException
	 */
	public List<PickupPoint> getAllPickupPoints() throws CFMSException;
	/**
	 * inserts the pickup point into the specified route 
	 * @param pickup_point pickup point to be inserted
	 * @param route_no route no of pickup point
	 * @param index the position of pickup point in route
	 * @return true if success else false
	 * @throws CFMSException
	 */
	public boolean insertPickupPointRoute(PickupPoint pickup_point,int route_no,int index)throws CFMSException;
	/**
	 * gets the pickup points of a particular route
	 * @param route_no route no of the pickup points
	 * @return pickup points in the route
	 * @throws CFMSException
	 */
	public List<PickupPoint> getPickupPoints(int route_no)throws CFMSException;
	
}
