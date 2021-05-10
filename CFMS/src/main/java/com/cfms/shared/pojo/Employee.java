package com.cfms.shared.pojo;

/**
 * This is the POJO for the employee details.
 * @author Samta, Priyanka, Chaitra
 *
 */
public class Employee {
	private String eid;
	private String ename;
	private String email;
	private String address;
	private String password;
	private String phone;
	private boolean isAdmin;
	
	/**
	 * It is a getter which gets the employee id.
	 * @return employee id
	 */
	public String getEid() {
		return eid;
	}
	/**
	 * Its a setter which sets the id of this employee.
	 * @param employee id
	 */
	public void setEid(String eid) {
		this.eid = eid;
	}
	/**
	 * Its a getter which gets the name of this employee.
	 * @return employee name
	 */
	public String getEname() {
		return ename;
	}
	/**
	 * Its a setter which sets the name of this employee.
	 * @param employee name
	 */
	public void setEname(String ename) {
		this.ename = ename;
	}
	/**
	 * Its a getter which gets the email id of this employee.
	 * @return email id of this employee
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * Its a setter which sets the email id of this employee.
	 * @param email id of this employee
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * Its a getter which gets the address of this employee.
	 * @return address of this employee
	 */
	public String getAddress() {
		return address;
	}
	
	/**
	 * Its a setter which sets the address of this employee.
	 * @param address of this employee
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	/**
	 * its a getter which gets the password set by this employee.
	 * @return password
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * Its a setter which sets the password provided by the employee.
	 * @param password
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * Its a getter which gets the phone number of this employee.
	 * @return phone number.
	 */
	public String getPhone() {
		return phone;
	}
	/**
	 * Its a setter which sets the phone number of this employee.
	 * @param phone number
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}
	/**
	 * Its a setter which sets the employee as an Admin.
	 * @param boolean variable indicating if this employee is an admin
	 */
	public void setIsAdmin(boolean isadmin)
	{
		this.isAdmin=isadmin;
	}
	/**
	 * Its a getter which gets a boolean variable to indicate if this employee is an admin.
	 * @return boolean variable indicating if this employee is an admin
	 */
	public boolean getIsAdmin() {
		return isAdmin;
	}	
	@Override
	public String toString() {
		return "Employee [eid=" + eid + ", ename=" + ename + ", email=" + email + ", address=" + address + ", password="
				+ password + ", phone=" + phone + ", isadmin="+isAdmin+"]";
	}


}
