package com.cfms.shared.pojo;

/**
 * 
 * this contains pickup location and the object id.
 * @author Priyanka R
 */
public class PickupPoint 
{
	private int pickup_id; //pickup point identifier 
	private String location; // pickup point location
	
	/**
	 * gets the pickup point identifier.
	 * @return pickup point identifier of this pickup point 
	 */
	public int getPickup_id() {
		return pickup_id;
	}
	/**
	 * sets the pickup point id for this pickup point
	 * @param pickup_id pickup point identifier
	 */
	public void setPickup_id(int pickup_id) {
		this.pickup_id = pickup_id;
	}
	/**
	 * gets the location of this pickup point
	 * @return location of pickup point
	 */
	public String getLocation() {
		return location;
	}
	/**
	 * sets the location of this pickup point 
	 * @param location pick up point location
	 */
	public void setLocation(String location) {
		this.location = location;
	}
	
}
