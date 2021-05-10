package com.cfms.client;


import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.Resource;
import org.fusesource.restygwt.client.RestService;
import org.fusesource.restygwt.client.RestServiceProxy;

import com.google.gwt.core.client.GWT;
import com.cfms.shared.pojo.Cab;
import com.cfms.shared.pojo.CabBooker;
import com.cfms.shared.pojo.CabPickupTime;
import com.cfms.shared.pojo.CabProviderBean;
import com.cfms.shared.pojo.CabUser;
import com.cfms.shared.pojo.ChangePasswordBean;
import com.cfms.shared.pojo.Employee;
import com.cfms.shared.pojo.LoginBean;
import com.cfms.shared.pojo.PickupPoint;
import com.cfms.shared.pojo.PickupTime;
import com.cfms.shared.pojo.Route;
import com.cfms.shared.pojo.TravelHistory;
import com.cfms.shared.pojo.RequestCab;
/**
 * Specifies all the services provided by the Rest Service 
 * @author Priyanka, Chaitra, Samta
 *
 */
@Path("/service")
public interface InfoService extends RestService 
{

/**
 * Utility class to get InfoService instance
 * @author Priyanka, Chaitra, Samta
 *
 */
public static class Util {

private static InfoService instance;

/**
 * returns the instance of InfoService
 * @return InfoService instance
 */
public static InfoService getService() {

	if (instance == null) {
	
	instance = GWT.create(InfoService.class);
	
	}
	
	Resource resource = new Resource(GWT.getModuleBaseURL() + "service");
	
	        ((RestServiceProxy) instance).setResource(resource);
	
	return instance;
	
	}
	
	}
	
	
	@POST
	@Path("/employee_login")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public void userLogin(LoginBean details,MethodCallback<Employee> callback);
	
	@POST
	@Path("/get_pickuppoint")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public void getPickupPoint(PickupPoint pickup_point,MethodCallback<PickupPoint> callback);
	
	@GET
	@Path("/get_all_routes")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public void getAllRoutes(MethodCallback<List<Route>> callback);
	
	@POST
	@Path("/get_cabs")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public void getCabsforPickupPoint(PickupPoint pickup_point,MethodCallback<List<CabPickupTime>> callback);

	@POST
	@Path("/book_Cab")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public void bookCab(CabBooker cabuser,MethodCallback<CabUser> callback);
	
	@POST
	@Path("/update_profile")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public void updateProfile(Employee details,MethodCallback<Employee> callback);
	
	@POST
	@Path("/travelhistory")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public void get_travelhistory(Employee emp ,MethodCallback<List<TravelHistory>> callback);
	
	@POST
	@Path("/get_employee")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public void getEmployee(Employee emp,MethodCallback<Employee> callback);
	
	
	@POST
	@Path("/add_cab")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public void addCab(Cab cab,MethodCallback<Cab> callback);
	
	@POST
	@Path("/view_cabs")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public void viewcabs(MethodCallback<List<Cab>> callback);
	

	@POST
	@Path("/get_pickuppoints")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public void getpickuppoints(Route route,MethodCallback<List<PickupPoint>> callback);
	
	@POST
	@Path("/employee_register")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public void userRegister(Employee details,MethodCallback<Employee> callback);
	
	@POST
	@Path("/emptravelhistory")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public void get_emptravelhistory(MethodCallback<List<TravelHistory>> callback);
	
	@POST
	@Path("/update_cab")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public void update_cab(Cab cab,MethodCallback<Cab> callback);
	
	@POST
	@Path("/add_route")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public void addRoute(Route route,MethodCallback<Route> callback);
	
	@GET
	@Path("/get_all_pickuppoints")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public void getAllPickupPoints(MethodCallback<List<PickupPoint>> callback);
	
	@POST
	@Path("/add_pickup_timings")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public void addPickupTimings(List<PickupTime> pt,MethodCallback<Boolean> callback);
	
	@POST
	@Path("/get_cabuser")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public void getCabUser(Employee emp,MethodCallback<CabUser> callback);
	
	@POST
	@Path("/leave_Cab")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public void leaveCab(CabBooker cb,MethodCallback<Boolean> callback);
	
	@POST
	@Path("/change_password")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public void changePassword(ChangePasswordBean details,MethodCallback<Boolean> callback);
	
	@POST
	@Path("/add_cabpickuptime")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public void addCabPickupTime(CabPickupTime cab,MethodCallback<Boolean> callback);
	
	@POST
	@Path("/request_cab")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public void request_cab(RequestCab req,MethodCallback<Boolean> callback);
	
	@GET
	@Path("/get_all_requests")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public void getAllRequests(MethodCallback<List<RequestCab>> callback);
	
	@GET
	@Path("/get_all_providers")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public void getAllProviders(MethodCallback<List<CabProviderBean>> callback);
	
	@POST
	@Path("/update_request")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public void updateRequest(RequestCab req,MethodCallback<Boolean> callback);
	
	@POST
	@Path("/add_cab_provider")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public void addCabProvider(CabProviderBean cabProvider,MethodCallback<CabProviderBean> callback);
	
	@POST
	@Path("/update_provider")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public void updateProvider(CabProviderBean details,MethodCallback<CabProviderBean> callback);
	
	@POST
	@Path("/delete_provider")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public void deleteProvider(CabProviderBean details,MethodCallback<CabProviderBean> callback);
	
}