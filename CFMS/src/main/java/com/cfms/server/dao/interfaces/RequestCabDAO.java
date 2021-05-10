package com.cfms.server.dao.interfaces;

import java.util.List;

import com.cfms.shared.exceptions.CFMSException;
import com.cfms.shared.pojo.RequestCab;

/**
 * Specifies methods to manipulate the requests table
 * @author Priyanka, Chaitra, Samta
 *
 */
public interface RequestCabDAO
{
	/**
	 * inserts the request for cab
	 * @param requestCab details of cab request
	 * @return true on success; false otherwise
	 * @throws CFMSException
	 */
	public boolean insertRequestCab(RequestCab requestCab) throws CFMSException;
	/**
	 * updates the cab request
	 * @param requestCab cab request to be updated
	 * @return true on success; false otherwise
	 * @throws CFMSException
	 */
	public boolean updateRequestCab(RequestCab requestCab) throws CFMSException;
	/**
	 * gets all the requests based on the status
	 * @param status status of request
	 * @return list of request with the specified status
	 * @throws CFMSException
	 */
	public List<RequestCab> getRequestsByStatus(String status) throws CFMSException;
	/**
	 * gets all the request in database
	 * @return list of all requests
	 * @throws CFMSException
	 */
	public List<RequestCab> getAllRequests() throws CFMSException;
}
