package com.cfms.server.dao.interfaces;
import java.util.List;

import com.cfms.shared.exceptions.CFMSException;
import com.cfms.shared.pojo.Cab;


/**
 * Specifies methods for manipulating the cab information
 * @author Priyanka, Chaitra, Samta
 *
 */

public interface CabDAO {
	   /**
	    * gets all the Cabs present in the database.
	 * @return List of Cab objects
	 * @throws CFMSException
	 */
	public List<Cab> getAllCabs()throws CFMSException;
	   /**
	    * gets the cab from the database based on the cab number provided.
		 * @param Cab_no cab number
		 * @return Cab with all its information
		 * @throws CFMSException
		 */
	public Cab getCab(int Cab_no)throws CFMSException;
	   /**
	    * updates the cab based on the Cab object provided.
	 * @param cab object
	 * @throws CFMSException
	 */
	public void updateCab(Cab cab)throws CFMSException;
	   /**
		 * deletes a cab from the cab table
		 * @param cab_no cab number to be deleted
		 * @throws CFMSException
		 */
	   public void deleteCab(int cab_no)throws CFMSException;
	   /**
		 * inserts the cab into the database 
		 * @param cab Cab to be inserted
		 * @return true if insertion is success; false otherwise
		 * @throws CFMSException
		 */
	   public boolean insert(Cab cab)throws CFMSException;
	   /**
	    * gets the number of occupied seats in this cab.
	 * @param cabno cab number
	 * @return number of occupied seats 
	 * @throws CFMSException
	 */
	public int getOccupiedSeats(int cabno) throws CFMSException;
	 
	   
	}
