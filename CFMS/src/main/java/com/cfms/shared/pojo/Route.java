package com.cfms.shared.pojo;

import java.util.List;

/**
 * Pojo class for Route containing Route no, name and pickup points.
 * @author Priyanka R, Samta , Chaitra
 *
 */
public class Route 
{
	private int route_no; // route no for identifying the route
	private String route_name; // name of the route
	private List<PickupPoint> pickupPoints; // list of pickup points
	
	/**
	 * gets the route no of this route.
	 * @return route no of this route
	 */
	public int getRoute_no() {
		return route_no;
	}
	/**
	 * sets the route no of this route
	 * @param route_no route no for this route
	 */
	public void setRoute_no(int route_no) {
		this.route_no = route_no;
	}
	/**
	 * gets the list of pickup points of this route
	 * @return list of pickup points covered by this route
	 */
	public List<PickupPoint> getPickupPoints() {
		return pickupPoints;
	}
	/**
	 * sets the pickup points for this route.
	 * @param pickupPoints list of pickup points
	 */
	public void setPickupPoints(List<PickupPoint> pickupPoints) {
		this.pickupPoints = pickupPoints;
	}
	/**
	 * gets the route name of this route 
	 * @return 
	 */
	public String getRoute_name() {
		return route_name;
	}
	/**
	 * sets the route name for this route
	 * @param route_name route_name for this route
	 */
	public void setRoute_name(String route_name) {
		this.route_name = route_name;
	}
	
}
