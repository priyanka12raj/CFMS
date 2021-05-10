package com.cfms.server.dao.interfaces;

import java.util.List;

import com.cfms.shared.exceptions.CFMSException;
import com.cfms.shared.pojo.Cab;
import com.cfms.shared.pojo.Employee;
import com.cfms.shared.pojo.PickupPoint;
import com.cfms.shared.pojo.TravelHistory;


/**
 * specifies methods to maintain/handle employee travel history.
 * @author Samta, Priyanka, Chaitra
 *
 */
public interface TravelHistoryDAO
{
	/**
	 * gets all the travel history of all the employees from the database.
	 * @return list of travel history
	 * @throws CFMSException
	 */
	public List<TravelHistory> getAllHistory()throws CFMSException;
	/**
	 * gets the travel history of the employee from the database.
	 * @param emp employee whose travel history is to be retrieved
	 * @return list of travel history
	 * @throws CFMSException
	 */
	public List<TravelHistory> getHistory(Employee emp)throws CFMSException;
	/**
	 * updates the travel history of the employee in the database.
	 * @param history travel history of the employee which is to be updated
	 * @throws CFMSException
	 */
	public void updateHistory(TravelHistory history)throws CFMSException;
	/**
	 * inserts the travel history of the employee in the database.
	 * @param history travel history which is to be inserted
	 * @return true if successfully inserted or else false
	 * @throws CFMSException
	 */
	public boolean insert(TravelHistory history)throws CFMSException;
	/**
	 * gets the travel history of the employee from the database based on the employee id, cab number and pick-up point id.
	 * @param emp employee whose travel history is to retrieved
	 * @param cab cab for which travel history of the employee is needed
	 * @param p pick-up point id
	 * @return travel history
	 * @throws CFMSException
	 */
	public TravelHistory getHistory(Employee emp, Cab cab, PickupPoint p) throws CFMSException;
	   
}
