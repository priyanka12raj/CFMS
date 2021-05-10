package com.cfms.server.dao.interfaces;

import java.util.List;

import com.cfms.shared.exceptions.CFMSException;
import com.cfms.shared.pojo.PickupPoint;
import com.cfms.shared.pojo.Route;


/**
 * Specifies methods for manipulating the Route table
 * @author Priyanka , Chaitra, Samta
 *
 */
public interface RouteDAO 
{
	/**
	 * inserts the route into the database table
	 * @param route route to be inserted
	 * @return true if success else false
	 * @throws CFMSException
	 */
	public boolean insertRoute(Route route)throws CFMSException;
	/**
	 * gets the route based on the route name
	 * @param route_name route name of route to be retrieved
	 * @return route for route name 
	 * @throws CFMSException
	 */
	public Route getRoute(String route_name)throws CFMSException;
	/**
	 * get the route based on the route no
	 * @param route_no route no of route to be retrieved
	 * @return route for route no
	 * @throws CFMSException
	 */
	public Route getRoute(int route_no)throws CFMSException;
	/**
	 * get all the routes
	 * @return list of all routes
	 * @throws CFMSException
	 */
	public List<Route> getAllRoutes()throws CFMSException;
	/**
	 * adding a pickup point to the route
	 * @param pickup_point pickup point to be added
	 * @param routeNo route no of the route
	 * @param index position of pickup point
	 * @return true if success else false
	 * @throws CFMSException
	 */
	public boolean addPickupPointToRoute(PickupPoint pickup_point,int routeNo,int index)throws CFMSException;
	/**
	 * removes a pickup point from the route
	 * @param pickup_point pickup point to be removed
	 * @param routeNo route no of the route
	 * @return true on success false otherwise
	 * @throws CFMSException
	 */
	public boolean removePickupPointToRoute(PickupPoint pickup_point,int routeNo)throws CFMSException;
}
