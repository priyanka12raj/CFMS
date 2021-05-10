package com.cfms.shared.pojo;
/**
 * stores the login details
 * @author Priyanka, Samta, Chaitra
 *
 */
public class LoginBean
{
	/**
	 * employee id
	 */
	private String employee_id;
	/**
	 * password
	 */
	private String password;
	/**
	 * @return the employee_id
	 */
	public String getEmployee_id() {
		return employee_id;
	}
	/**
	 * @param employee_id the employee_id to set
	 */
	public void setEmployee_id(String employee_id) {
		this.employee_id = employee_id;
	}
	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
}
