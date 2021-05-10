package com.cfms.server.dao.interfaces;

import com.cfms.shared.exceptions.CFMSException;
import com.cfms.shared.pojo.CabUser;
import com.cfms.shared.pojo.Employee;

/**
 * Specifies methods for manipulating cab user table
 * @author Priyanka , Chaitra, Samta
 *
 */
public interface CabUserDAO 
{
	/**
	 * inserts the cabuser into the database
	 * @param cabuser cabuser to be inserted
	 * @return true if success otherwise false
	 * @throws CFMSException
	 */
	public boolean insertCabUser(CabUser cabuser)throws CFMSException;
	/**
	 * updates the cabuser in the database
	 * @param cabuser cabuser to be updated
	 * @return true if success otherwise false
	 * @throws CFMSException
	 */
	public boolean updateCabuser(CabUser cabuser) throws CFMSException;
	/**
	 * deletes the cabuser from the database
	 * @param cabuser cabuser to be deleted
	 * @return true if success otherwise false
	 * @throws CFMSException
	 */
	public boolean deleteCabUser(CabUser cabuser) throws CFMSException;
	/**
	 * prepares the cab user by getting cab, employee, pickup point from database using their id's
	 * @param cab_no cab no of cab to be retrieved
	 * @param employee_id employee id of employee to be retrieved
	 * @param pickup_id pickup point id of pickup point to be retrieved
	 * @return the prepared cabuser
	 * @throws CFMSException
	 */
	public CabUser prepareCabUser(int cab_no, String employee_id, int pickup_id) throws CFMSException;
	/**
	 * gets the cabuser details from database based on specified employee
	 * @param emp employee who uses cab
	 * @return cabuser
	 * @throws CFMSException
	 */
	public CabUser getCabUser(Employee emp) throws CFMSException;
}
