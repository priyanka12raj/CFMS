package com.cfms.shared.pojo;


/**
 * This is the POJO for cab holding the information about a cab.
 * @author Samta, Priyanka, Chaitra
 *
 *
 */
public class Cab {
	
	private int cab_no;
	private int no_of_seats;
	private int avail_seats;
	private double cost_per_month;
	private String provider;
	private int route_no;
	private String start_time_source;
	private String start_time_NC;
	
	/**
	 * It is a getter which gets the cab number of this cab.
	 * @return cab number of this cab
	 */
	public int getCab_no() {
		return cab_no;
	}
	/**
	 * Its a setter which sets the cab number of this cab.
	 * @param cab number
	 */
	public void setCab_no(int cab_no) {
		this.cab_no = cab_no;
	}
	/**
	 * Its a getter which gets the number of seats in this cab.
	 * @return number of seats in the cab
	 */
	public int getNo_of_seats() {
		return no_of_seats;
	}
	/**
	 * Its a setter which sets the number of seats in this cab.
	 * @param number of seats in the cab
	 */
	public void setNo_of_seats(int no_of_seats) {
		this.no_of_seats = no_of_seats;
	}
	/**
	 * Its a getter which gets the  cost per day charged for this cab.
	 * @return cost per day charged for this cab
	 */
	public double getCost_per_month() {
		return cost_per_month;
	}
	/**
	 * Its a setter which sets the  cost per day charged for this cab.
	 * @param cost per day charged for the cab
	 */
	public void setCost_per_month(double cost_per_month) {
		this.cost_per_month = cost_per_month;
	}
	/**
	 * Its a getter which gets the route number on to which this cab is allocated to.
	 * @return route number on to which this cab is allocated to
	 */
	public int getRoute_no() {
		return route_no;
	}
	/**
	 * Its a setter which sets the route number on to which this cab is allocated to.
	 * @param route number on to which cab is allocated to
	 */
	public void setRoute_no(int route_no) {
		this.route_no = route_no;
	}
	/**
	 * Its a getter which gets the provider name for this cab.
	 * @return name of the provider of the cab
	 */
	public String getProvider() {
		return provider;
	}
	/**
	 * Its a setter which sets the provider name for this cab.
	 * @param name of the provider of the cab
	 */
	public void setProvider(String provider) {
		this.provider = provider;
	}
	/**
	 * Its a getter which gets the start time of this cab from the source.
	 * @return start time of the cab from this source
	 */
	public String getStart_time_source() {
		return start_time_source;
	}
	/**
	 * Its a setter which sets the start time of this cab from the source.
	 * @param start time of the cab from this source
	 */
	public void setStart_time_source(String time) {
		this.start_time_source = time;
	}
	/**
	 * Its a getter which gets the start time of this cab from the office.
	 * @return start time of this cab from the office
	 */
	public String getStart_time_NC() {
		return start_time_NC;
	}
	/**
	 * Its a setter which sets the start time of this cab from the office.
	 * @param start time of the cab from the office
	 */
	public void setStart_time_NC(String start_time_NC) {
		this.start_time_NC = start_time_NC;
	}
	/**
	 * Its a getter which gets the available seats in this cab.
	 * @return available seats in the cab
	 */
	public int getAvail_seats() {
		return avail_seats;
	}
	/**
	 * Its a setter which sets the available seats in this cab.
	 * @param available seats in the cab
	 */
	public void setAvail_seats(int avail_seats) {
		this.avail_seats = avail_seats;
	}

//	@Override
//	public String toString()
//	{
//		return "[cab_no:"+cab_no+", no_of_seats:"+no_of_seats+" , provider: "+provider+" , start_time_source: "+start_time_source.toString()+", start_time_NC: "+start_time_NC+"]";
//	}
	
	

}
