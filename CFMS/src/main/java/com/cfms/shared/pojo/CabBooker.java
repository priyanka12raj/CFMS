package com.cfms.shared.pojo;

/**
 * For Booking the Cab
 * @author Priyanka , Samta, Chaitra
 *
 */
public class CabBooker 
{
	/**
	 * employee id of employee who wishes to book the cab
	 */
	private String employee_id;
	/**
	 * cab no of cab which the employee booked
	 */
	private int cab_no;
	/**
	 * pickup location from where the employee booked the cab
	 */
	private int pickup_id;
	/**
	 * gets the employee id of Cab booker
	 * @return employee id
	 */
	public String getEmployee_id() {
		return employee_id;
	}
	/**
	 * sets the employee id who books the cab
	 * @param employee_id employee id of Cab booker
	 */
	public void setEmployee_id(String employee_id) {
		this.employee_id = employee_id;
	}
	/**
	 * gets the cab no of cab being booked
	 * @return cab no
	 */
	public int getCab_no() {
		return cab_no;
	}
	/**
	 * sets the cab no of cab to be booked
	 * @param cab_no cab no of cab to be booked
	 */
	public void setCab_no(int cab_no) {
		this.cab_no = cab_no;
	}
	/**
	 * gets the pickup point location id
	 * @return pickup point id 
	 */
	public int getPickup_id() {
		return pickup_id;
	}
	/**
	 * sets the pickup point id of location from where the user booked the cab
	 * @param pickup_id pickup point location id
	 */
	public void setPickup_id(int pickup_id) {
		this.pickup_id = pickup_id;
	}
}
