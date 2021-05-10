package com.cfms.shared.exceptions;
/**
 * Custom exception for CFMS
 * @author Priyanka, Samta, Chaitra
 *
 */
public class CFMSException extends Exception
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Exception caught elsewhere
	 */
	Throwable ex;
	/**
	 * Exception details
	 */
	String message;
	/**
	 * gets the original exception
	 * @return original exception
	 */
	public Throwable getEx() {
		return ex;
	}
	/**
	 * gets the exception message
	 */
	public String getMessage() {
		return message;
	}
	/**
	 * Constructor creates object from throwable object
	 */
	public CFMSException(Throwable ex)
	{
		this.ex=ex;
		this.message=ex.getMessage();
		//print the stack trace of the exception
		ex.printStackTrace();
	}
}
