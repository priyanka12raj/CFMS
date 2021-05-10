package com.cfms.shared.pojo;

/**
 * cab request and response details.
 * @author Priyanka, Samta, Chaitra
 *
 */
public class RequestCab 
{
	/**
	 * request identifier
	 */
	private int request_id; 
	/**
	 * employee who requested
	 */
	private Employee employee;
	/**
	 * pickup point location of request
	 */
	private String location; 
	/**
	 * details of request
	 */
	private String request_details;
	/**
	 * status of the request (requested,approved,rejected)
	 */
	private String status; 
	/**
	 * details of response
	 */
	private String response_details;
	/**
	 * gets the request id
	 * @return
	 */
	public int getRequest_id() {
		return request_id;
	}
	/**
	 * sets the request id
	 * @param request_id id for this request
	 */
	public void setRequest_id(int request_id) {
		this.request_id = request_id;
	}
	/**
	 * gets the employee of this request
	 * @return employee who requested
	 */
	public Employee getEmployee() {
		return employee;
	}
	/**
	 * sets the employee of this request
	 * @param employee requested employee to be set
	 */
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
	/**
	 * gets the pickup point location of the request
	 * @return
	 */
	public String getLocation() {
		return location;
	}
	/**
	 * sets the pickup point location for the request
	 * @param location location to be set
	 */
	public void setLocation(String location) {
		this.location = location;
	}
	/**
	 * gets the request details
	 * @return request details
	 */
	public String getRequest_details() {
		return request_details;
	}
	/**
	 * sets the request details
	 * @param request_details
	 */
	public void setRequest_details(String request_details) {
		this.request_details = request_details;
	}
	/**
	 * gets the status of this request
	 * @return status of this request
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * sets the status of this request
	 * @param status status:requested/approved/rejected
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * gets the response details
	 * @return response details
	 */
	public String getResponse_details() {
		return response_details;
	}
	/**
	 * sets the response details
	 * @param response_details 
	 */
	public void setResponse_details(String response_details) {
		this.response_details = response_details;
	}
}
