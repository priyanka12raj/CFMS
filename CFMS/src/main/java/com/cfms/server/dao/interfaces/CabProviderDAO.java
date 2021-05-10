package com.cfms.server.dao.interfaces;

import java.util.List;

import com.cfms.shared.exceptions.CFMSException;
import com.cfms.shared.pojo.CabProviderBean;


/**
 * Specifies the methods to manipulate the cab provider table in the database.
 * @author Samta, Priyanka, Chaitra
 *
 */
public interface CabProviderDAO {
	
	/**
	 * gets all the cab providers from the database.
	 * @return List of all cab providers
	 * @throws CFMSException
	 */
	public List<CabProviderBean> getAllCabProviders()throws CFMSException;
	/**
	 * gets the cab provider based on the provider's company name.
	 * @param providerBean CabProviderBean object
	 * @return CabProviderBean object 
	 * @throws CFMSException
	 */
	public CabProviderBean getCabProvider(CabProviderBean providerBean)throws CFMSException;
	
/**
 * inserts the cab provider into the database.
 * @param cabProvider CabProviderBean object
 * @return true if success else false
 * @throws CFMSException
 */
public boolean insertCabProvider(CabProviderBean cabProvider) throws CFMSException;
/**
 * deletes the cab provider from the database based on the provider's company name.
 * @param cabProvider CabProviderBean object
 * @return true if success else false
 * @throws CFMSException
 */
public boolean deleteCabProvider(CabProviderBean cabProvider) throws CFMSException;
/**
 * updates the cab provider in the database.
 * @param cabProvider CabProviderBean object
 * @return true if success
 * @throws CFMSException
 */
public boolean updateCabProvider(CabProviderBean cabProvider) throws CFMSException;
}
