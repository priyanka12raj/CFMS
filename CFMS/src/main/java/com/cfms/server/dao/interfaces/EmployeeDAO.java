package com.cfms.server.dao.interfaces;
import java.util.List;

import com.cfms.shared.exceptions.CFMSException;
import com.cfms.shared.pojo.Cab;
import com.cfms.shared.pojo.Employee;

/**
 * contains the methods for manipulating employee information in the database.
 * @author Samta, Priyanka, Chaitra
 *
 */
public interface EmployeeDAO {
	
	 /**
	  * gets all the employees from the database.
	 * @return list of employee
	 * @throws CFMSException
	 */
	public List<Employee> getAllEmployees()throws CFMSException;
	   /**
	    * gets the employee from the database based on the employee id.
	 * @param eid Employee id
	 * @return Employee object
	 * @see Employee
	 * @throws CFMSException
	 */
	   public Employee getEmployee(String eid)throws CFMSException;
	   /**
	    * inserts the employee into the database.
	 * @param employee Employee to be inserted
	 * @return true if success or else false
	 * @throws CFMSException
	 */
	public boolean insertEmployee(Employee employee)throws CFMSException;
	   /**
	    * updates the employee in the database.
	 * @param employee employee to be updated
	 * @return true if success or else false  
	 * @throws CFMSException
	 */
	public boolean updateEmployee(Employee employee)throws CFMSException;
	   /**
	    * deletes the employee from the database.
	 * @param employee employee to be deleted
	 * @return true if success or else false
	 * @throws CFMSException
	 */
	public boolean deleteEmployee(Employee employee)throws CFMSException;
	   /**
	    * validates the employee in the database based on the employee id and password.
	 * @param eid employee id to be validated
	 * @param pwd password to be validated
	 * @return Employee employee who got validated
	 * @throws CFMSException
	 */
	public Employee validateUser(String eid,String pwd) throws CFMSException;
	   /**
	    * sets new password for the employee
	 * @param employee employee whose password is to be changed
	 * @return true if success or else false
	 * @throws CFMSException
	 */
	public boolean changePassword(Employee employee)throws CFMSException;
	   /**
	    * validates the password of the employee
	 * @param employee employee whose password is to validated
	 * @return true if validated or else false
	 * @throws CFMSException
	 */
	public boolean validatePassword(Employee employee) throws CFMSException;
	   public boolean insertCabUser(Employee employee, Cab cab)throws CFMSException;
	   public List<Employee> getCabUser(Cab cab)throws CFMSException;
	   
	   
}
