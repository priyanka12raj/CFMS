package com.cfms.shared.pojo;

import java.sql.Time;

/**
 * Relates Cab with Pickup point along with approximate pickup time
 * @author Priyanka, Samta, chaitra
 * @see Cab 
 * @see PickupPoint
 */
public class CabPickupTime 
{
	PickupPoint pickup_point;
	Cab cab;
	String pickup_time;
	
	/**
	 * gets the pickup point object.
	 * Pickup point from where the cab picks up the employee at the time
	 * @return pickup point object
	 * @see PickupPoint
	 */
	public PickupPoint getPickup_point() {
		return pickup_point;
	}
	/**
	 * sets the pick up point. 
	 * @param pickup_point pickup point to be set
	 * @see PickupPoint
	 */
	public void setPickup_point(PickupPoint pickup_point) {
		this.pickup_point = pickup_point;
	}
	/**
	 * gets the cab.
	 * @return cab of this cab pickup time
	 */
	public Cab getCab() {
		return cab;
	}
	/**
	 * sets the cab. Cab which reaches by pickup time at pickup location
	 * @param cab
	 */
	public void setCab(Cab cab) {
		this.cab = cab;
	}
	/**
	 * gets the approximate pickup time
	 * @return pickup time
	 */
	public String getPickup_time() {
		return pickup_time;
	}
	/**
	 * sets the approximate pickup time from String.
	 * @param pickup_time approximate pick up time from String
	 */
	public void setPickup_time(String pickup_time){
		this.pickup_time=pickup_time;
	}
	/**
	 * sets the approximate pickup time from Time
	 * @param pickup_time approximate pickup time from Time
	 * @see Time
	 */
	public void setPickup_time(Time pickup_time) {
		this.pickup_time = pickup_time.toString();
	}

}
