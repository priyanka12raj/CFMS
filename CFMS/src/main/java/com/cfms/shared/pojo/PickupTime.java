package com.cfms.shared.pojo;

/**
 * Gives the approximate pickup time of a cab from the pickup point
 * @author Priyanka, samta, chaitra
 *
 */
public class PickupTime {
	
	private int cab_number; //cab number
	private int pickup_id; // pickup point identifier
	private String approx_time; // approximate pickup time
	
	/**
	 * gets the cab number of this PickupTime
	 * @return cab number
	 */
	public int getCab_number() {
		return cab_number;
	}
	/**
	 * sets the cab number
	 * @param cab_number cab number to be set
	 */
	public void setCab_number(int cab_number) {
		this.cab_number = cab_number;
	}
	/**
	 * gets the pickup point location id
	 * @return pickup point id
	 */
	public int getPickup_id() {
		return pickup_id;
	}
	/**
	 * sets the pickup point id
	 * @param pickup_id pickup point id
	 */
	public void setPickup_id(int pickup_id) {
		this.pickup_id = pickup_id;
	}
	/**
	 * gets the approximate time of pickup 
	 * @return pickup time
	 */
	public String getApprox_time() {
		return approx_time;
	}
	/**
	 * sets the approximate time of pickup
	 * @param approx_time approximate pickup time
	 */
	public void setApprox_time(String approx_time) {
		this.approx_time = approx_time;
	}
}
