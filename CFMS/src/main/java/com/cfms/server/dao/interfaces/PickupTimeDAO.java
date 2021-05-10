package com.cfms.server.dao.interfaces;

import java.util.List;

import com.cfms.shared.exceptions.CFMSException;
import com.cfms.shared.pojo.PickupTime;

/**
 * specifies method to insert timings for the pickup-points.
 * @author Samta, Priyanka, Chaitra
 *
 */
public interface PickupTimeDAO {
	
	 /**
	  * inserts pickup-point timings in the database for the pick-up points in a route.
	 * @param pt list of pickup timings to be inserted
	 * @return true if success or else false
	 * @throws CFMSException
	 */
	Boolean insert(List<PickupTime> pt)throws CFMSException;

}
