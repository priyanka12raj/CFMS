package com.cfms.shared.pojo;


/**
 * This is a POJO for maintaining the travel history of the employee along with the all employee details.
 * @author Samta, Priyanka, Chaitra
 * @see Employee
 *
 */
public class TravelHistory {
	
	private Employee emp;
	private int cab_no;
	private int p_no;
	private String start_date;
	private String end_till;
	
	/**
	 * Its a getter which gets the cab number in which this employee used to travel.
	 * @return cab number
	 */
	public int getCab_no() {
		return cab_no;
	}
	/**
	 * Its a setter which sets the cab number in which this employee used to travel.
	 * @param cab number
	 */
	public void setCab_no(int cab_no) {
		this.cab_no = cab_no;
	}

	/**
	 * Its a getter which gets the date from when this employee started to use the cab.
	 * @return start date of travel
	 */
	public String getStart_date() {
		return start_date;
	}
	/**
	 * Its a setter which sets the start date of travel for this employee.
	 * @param start date of travel
	 */
	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}
	/**
	 * this is a getter which gets the Pickuppoint of the employee.
	 * @return pickuppoint number
	 */
	public int getP_no() {
		return p_no;
	}
	/**
	 * This is a setter to set pickuppoint number of this employee.
	 * @param pickuppoint number
	 */
	public void setP_no(int p_no) {
		this.p_no = p_no;
	}

	/**
	 * Its a getter to get the end date of travel of this employee. If the user is still using the cab then the method returns null.
	 * @return end date of travel
	 */
	public String getEnd_till() {
		return end_till;
	}
	/**
	 * Its a setter to set the end date of travel for this employee. If the user is still using the cab the it sets the end_till variable to null.
	 * @param end date of travel
	 */
	public void setEnd_till(String end_till) {
		this.end_till = end_till;
	}
	/**
	 * Its a getter to get this Employee as an object.
	 * @return Employee object
	 */
	public Employee getEmp() {
		return emp;
	}
	/**
	 * This setter sets the Employee object.
	 * @param Employee object
	 */
	public void setEmp(Employee emp) {
		this.emp = emp;
	}

}

