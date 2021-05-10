package com.cfms.server.dao.interfaces;

import java.util.List;

import com.cfms.shared.exceptions.CFMSException;
import com.cfms.shared.pojo.Cab;
import com.cfms.shared.pojo.CabPickupTime;
import com.cfms.shared.pojo.PickupPoint;


/**
 * Specifies methods for manipulating the cab pickup time
 * @author Priyanka, Chaitra, Samta
 *
 */
public interface CabPickupTimeDAO 
{
	/**
	 * inserts the CabPickupTime into the database
	 * @param cpt CabpickupTime
	 * @return true if success else false
	 * @throws CFMSException
	 */
	public boolean insertCabPickupTime(CabPickupTime cpt)throws CFMSException;
	/**
	 * updates the cabpickup time
	 * @param cpt cab pickup time
	 * @return true if success else false
	 * @throws CFMSException
	 */
	public boolean updateCabPickupTime(CabPickupTime cpt)throws CFMSException;
	/**
	 * deletes the cab pickup time
	 * @param cpt cab pickup time to be deleted
	 * @return true if success else false
	 * @throws CFMSException
	 */
	public boolean deleteCabPickupTime(CabPickupTime cpt)throws CFMSException;
	/**
	 * gets the cabPickupTime based on pickup point
	 * @param pickup_point pickup point 
	 * @return list of cabpickupTime from pickup point
	 * @throws CFMSException
	 */
	public List<CabPickupTime> getCabPickupTime(PickupPoint pickup_point)throws CFMSException;
	/**
	 * gets the pickup point and timings of a cab
	 * @param cab cab for which cabpickuptimes is to be retrieved
	 * @return list of pickup point times of cab
	 * @throws CFMSException
	 */
	public List<CabPickupTime> getCabPickupTime(Cab cab)throws CFMSException;
}
