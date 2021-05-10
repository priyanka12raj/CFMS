package com.cfms.shared.pojo;

/**
 * Its a POJO to hold the Employee details along with its new and old password.
 * @author Samta, Priyanka, Chaitra
 * @see Employee
 *
 */
public class ChangePasswordBean 
{
	private Employee emp;
	private String oldPassword;
	private String newPassword;
	
	/**
	 * gets the Employee object.
	 * @return Employee object
	 * @see Employee
	 */
	public Employee getEmp() {
		return emp;
	}
	/**
	 * sets the Employee object.
	 * @param Employee object
	 * @see Employee
	 */
	public void setEmp(Employee emp) {
		this.emp = emp;
	}
	/**
	 * gets the old password of this employee.
	 * @return old password
	 */
	public String getOldPassword() {
		return oldPassword;
	}
	/**
	 * sets the old password of this employee.
	 * @param old Password
	 */
	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}
	/**
	 * gets the new password of this employee.
	 * @return new password
	 */
	public String getNewPassword() {
		return newPassword;
	}
	/**
	 * sets the new password of this employee.
	 * @param new Password
	 */
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
}
