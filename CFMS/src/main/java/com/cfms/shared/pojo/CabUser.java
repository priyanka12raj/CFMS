package com.cfms.shared.pojo;
/**
 * Relates employee, cab, pickup point as Cab user.
 * @author Priyanka , Samta, Chaitra
 *
 */
public class CabUser 
{
	/**
	 * this cabuser employee
	 */
	private Employee employee;
	/**
	 * the cab this cabuser uses
	 */
	private Cab cab;
	/**
	 * the pickup point from where the cabuser boards the cab
	 */
	private PickupPoint pickuppoint;
	
	/**
	 * gets this cab user employee
	 * @return this cab using employee
	 */
	public Employee getEmployee() {
		return employee;
	}
	/**
	 * sets this cab user employee
	 * @param employee employee as cabuser
	 */
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
	/**
	 * gets the cab used by  this cabuser
	 * @return cab of this cabuser
	 */
	public Cab getCab() {
		return cab;
	}
	/**
	 * sets the cab used by the cabuser
	 * @param cab cab used
	 */
	public void setCab(Cab cab) {
		this.cab = cab;
	}
	/**
	 * gets the pickup point from where the user boards the cab
	 * @return pickup point of this cab
	 */
	public PickupPoint getPickuppoint() {
		return pickuppoint;
	}
	/**
	 * sets the pickup point of this cabuser
	 * @param pickuppoint pickup point from where the user boards the cab
	 */
	public void setPickuppoint(PickupPoint pickuppoint) {
		this.pickuppoint = pickuppoint;
	}
	
}
