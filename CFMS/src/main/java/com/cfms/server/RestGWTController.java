package com.cfms.server;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cfms.server.dao.EmployeeDAOImpl;
import com.cfms.server.dao.PickupPointDAOImpl;
import com.cfms.server.dao.PickupTimeDAOImpl;
import com.cfms.server.mail.CFMSMailer;
import com.cfms.shared.exceptions.CFMSException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cfms.server.dao.CabDAOImpl;
import com.cfms.server.dao.CabPickupTimeDAOImpl;
import com.cfms.server.dao.CabProviderDAOImpl;
import com.cfms.server.dao.CabUserDAOImpl;
import com.cfms.server.dao.RequestCabDAOImpl;
import com.cfms.server.dao.RouteDAOImpl;
import com.cfms.server.dao.TravelHistoryDAOImpl;
import com.cfms.server.dao.interfaces.CabPickupTimeDAO;
import com.cfms.server.dao.interfaces.CabProviderDAO;
import com.cfms.server.dao.interfaces.CabUserDAO;
import com.cfms.server.dao.interfaces.EmployeeDAO;
import com.cfms.server.dao.interfaces.PickupPointDAO;
import com.cfms.server.dao.interfaces.RequestCabDAO;
import com.cfms.server.dao.interfaces.RouteDAO;
import com.cfms.server.dao.interfaces.TravelHistoryDAO;
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
import com.cfms.shared.pojo.RequestCab;
import com.cfms.shared.pojo.Route;
import com.cfms.shared.pojo.TravelHistory;
/**
 * Defines all the services
 * @author Priyanka, Samta, Chaitra
 *
 */
@RestController
public class RestGWTController 
{

	/**
	 * Retrieves travel history of given employee
	 * @param emp
	 * @param request
	 * @param response
	 * @return list of travel history records
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping ( value="/travelhistory" , method= RequestMethod.POST , headers= "Accept=application/json")
	public @ResponseBody List<TravelHistory> travelHistory(@RequestBody Employee emp ,HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException
	{
		List<TravelHistory> th= null;
		TravelHistory h = null;
		
		TravelHistoryDAOImpl thdao = new TravelHistoryDAOImpl();
		
		try{
			System.out.println("\n"+" DETAILS \n"+ emp.getEid());
			th=thdao.getHistory(emp);
			h=th.get(0);
			System.out.println("\n"+h.getEmp().getEid());
			System.out.println("\n"+h.getCab_no());
			System.out.println("\n"+h.getP_no());
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return th;
		
	}	
	
	/**
	 * Adds the given cab
	 * @param cab
	 * @param request
	 * @param response
	 * @return added cab
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping (value="/add_cab", method = RequestMethod.POST, headers="Accept= application/json")
	public @ResponseBody Cab add_cab(@RequestBody Cab cab, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		Boolean success = false;
		CabDAOImpl cabdao = new CabDAOImpl();
		try{
			cabdao.insert(cab);
			success= true;
		}
		catch(CFMSException e)
		{
			e.printStackTrace();
		}
		System.out.println("cab added:"+success);
		return cab;
	}

	/**
	 * Retrieves all the cabs
	 * @param request
	 * @param response
	 * @return list of cabs
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping( value="/view_cabs", method= RequestMethod.POST, headers="Accept= application/json")
	public @ResponseBody List<Cab> view_cabs(HttpServletRequest request,  HttpServletResponse response) throws ServletException, IOException 
	{
		CabDAOImpl viewcabs = new CabDAOImpl();
		List<Cab> listcabs = null;
		try{
			
			listcabs= viewcabs.getAllCabs();
		}
		catch(CFMSException e)
		{
			e.printStackTrace();
		}
		
		return listcabs;
	}
	

	/**
	 * gets all pickup points of the given route.
	 * @param route
	 * @param response
	 * @return list of pickup points
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping( value="/get_pickuppoints", method= RequestMethod.POST, headers ="Accept= application/json")
	public @ResponseBody List<PickupPoint> get_pickuppoints(@ RequestBody Route route ,HttpServletResponse response) throws ServletException, IOException
	{
		PickupPointDAOImpl pickupdao = null;
		List<PickupPoint> pickuppoints = null;
		//PickupPoint onepickup= null;
		int route_num = route.getRoute_no();
		try
		{
			pickupdao= new PickupPointDAOImpl();
			pickuppoints= pickupdao.getPickupPoints(route_num);
			//onepickup = pickuppoints.get(0);
			if(pickuppoints==null)
			{
			System.out.println("size is null");
			}
			
		}
		catch (CFMSException e)
		{
			e.printStackTrace();
		}
		
		return pickuppoints;
		
	}
	/**
	 * get travel history of all employees
	 * @param request
	 * @param response
	 * @return list od travel history records
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping ( value="/emptravelhistory" , method= RequestMethod.POST , headers= "Accept=application/json")
	public @ResponseBody List<TravelHistory> get_emptrvelhistory(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException
	{
		List<TravelHistory> th= null;
		
		
		TravelHistoryDAOImpl thdao = new TravelHistoryDAOImpl();
		
		try{
			//System.out.println("\n"+" DETAILS \n"+ emp.getEid());
			th=thdao.getAllHistory();
			
          }
		catch (/*CFMSException |*/Exception e)
		{
			e.printStackTrace();
		}
		
		return th;
		
	}	
	/**
	 * updates the details of the cab
	 * @param cab
	 * @param request
	 * @param response
	 * @return updated cab
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping ( value="/update_cab" , method= RequestMethod.POST , headers= "Accept=application/json")
	public @ResponseBody Cab  update_cab(@RequestBody Cab cab,HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException
	{
		Cab c = null;
		CabDAOImpl cabdao = new CabDAOImpl();
		
		try{
			
			System.out.println(" cab number ="+cab.getCab_no());
			cabdao.updateCab(cab);
			c=cabdao.getCab(cab.getCab_no());
          }
		catch (/*CFMSException |*/Exception e)
		{
			e.printStackTrace();
		}
		
		return c;
		
	}	
	/**
	 * get all the pickup points
	 * @param request
	 * @param response
	 * @return list of pickup points
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping(value = "/get_all_pickuppoints", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody List<PickupPoint> getAllPickupPoints(HttpServletRequest request,  HttpServletResponse response) throws ServletException, IOException 
	{
		List<PickupPoint> pickup=null;
		PickupPointDAO pdao=new PickupPointDAOImpl();
		try {
			pickup=pdao.getAllPickupPoints();
			
		} catch (CFMSException e) {
			
		}
		return pickup;
		
	}
	
	
	
	/**
	 * check if cab is being used
	 * @param c
	 * @param request
	 * @param response
	 * @return true if cab is not being used otherwise false
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping ( value="/check_seats" , method= RequestMethod.POST , headers= "Accept=application/json")
	public @ResponseBody Boolean CheckSeats(@RequestBody Cab c,HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException
	{
		
		Boolean isfree= false;
		int seats;
		CabDAOImpl cabdao = new CabDAOImpl();
		//CabPickupTime  cpt =null;
		try{
			
				System.out.println("cab number="+ c.getCab_no());
				seats=cabdao.getOccupiedSeats(c.getCab_no());
				if(seats==0)
					isfree=true;
			
			 }
		catch (/*CFMSException |*/Exception e)
		{
			e.printStackTrace();
		}
		
		return isfree;
		
	}	
	/**
	 * remove the cab 
	 * @param c
	 * @param request
	 * @param response
	 * @return success or failure
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping ( value="/remove_cab" , method= RequestMethod.POST , headers= "Accept=application/json")
	public @ResponseBody Boolean RemoveCab(@RequestBody Cab c,HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException
	{
		
		Boolean flag= false;
		CabDAOImpl cabdao = new CabDAOImpl();
		
		try{
			
				System.out.println("cab number="+ c.getCab_no());
				cabdao.deleteCab(c.getCab_no());
				flag=true;
			
			 }
		catch (/*CFMSException |*/Exception e)
		{
			e.printStackTrace();
		}
		
		return flag;
		
	}	
	/**
	 * validates the employee login details and if valid login returns the employee details 
	 * @param details
	 * @param request
	 * @param response
	 * @return employee
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping(value="/employee_login", method = RequestMethod.POST ,headers ="Accept=application/json")
	public @ResponseBody Employee employeeLogin(@RequestBody LoginBean details, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		Employee emp=null;
		EmployeeDAO emp_dao=new EmployeeDAOImpl();
		try {
			System.out.println("\n"+"Login Details\n"+details.getEmployee_id()+" "+details.getPassword());
			//emp=emp_dao.getEmployee(details.getEmployee_id());
			emp=emp_dao.validateUser(details.getEmployee_id(), details.getPassword());
		}
		catch (CFMSException e)
		{
			e.printStackTrace();
		}
		return emp; 
	}
	
	/**
	 * get cab details if employee is a cab user
	 * @param employee
	 * @param request
	 * @param response
	 * @return cab user details
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping(value="/get_cabuser", method = RequestMethod.POST ,headers ="Accept=application/json")
	public @ResponseBody CabUser getCabUser(@RequestBody Employee employee, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		CabUser cabuser=null;
		CabUserDAO cdao=new CabUserDAOImpl();
		try
		{
			cabuser=cdao.getCabUser(employee);
		}
		catch(CFMSException e)
		{
			e.printStackTrace();
		}
		return cabuser;
		
	}
	/**
	 * Registers the new employee
	 * @param details
	 * @param request
	 * @param response
	 * @return registered employee details
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping(value="/employee_register", method = RequestMethod.POST ,headers ="Accept=application/json")
	public @ResponseBody Employee userRegister(@RequestBody Employee details, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		Employee emp=null;
		EmployeeDAO emp_dao=new EmployeeDAOImpl();
		try
		{
			System.out.println("\n"+"Registered Details\n"+details.getEid()+" "+details.getPassword());
			
			System.out.println("Inserted"+emp_dao.insertEmployee(details));
			emp=emp_dao.getEmployee(details.getEid());
		}
		catch (CFMSException e)
		{
			e.printStackTrace();
		}
		
		return emp;
		
	}
	
	/**
	 * get the employee details
	 * @param details
	 * @param request
	 * @param response
	 * @return employee
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping(value="/get_employee", method = RequestMethod.POST ,headers ="Accept=application/json")
	public @ResponseBody Employee getEmployee(@RequestBody Employee details, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		Employee emp=null;
		EmployeeDAO emp_dao=new EmployeeDAOImpl();
		try {
			//System.out.println("\n"+"Login Details\n"+details.getEmployee_id()+" "+details.getPassword());
			//emp=emp_dao.getEmployee(details.getEmployee_id());
			emp=emp_dao.getEmployee(details.getEid());
		}
		catch (CFMSException e)
		{
			e.printStackTrace();
		}
		return emp; 
	}
	/**
	 * get all the routes
	 * @param request
	 * @param response
	 * @return list of routes
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping(value = "/get_all_routes", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody List<Route> getAllRoutes(HttpServletRequest request,  HttpServletResponse response) throws ServletException, IOException 
	{
		System.out.println("\ngetting routes\n");
		RouteDAO route_dao=new RouteDAOImpl();
		List<Route> allroutes=null;
		try {
			allroutes=route_dao.getAllRoutes();
		} catch (CFMSException e) {
			e.printStackTrace();
		}
		return allroutes;
		
	} 
	/**
	 * get the available cabs for given pickup point
	 * @param pickup_point
	 * @param request
	 * @param response
	 * @return list of cabs
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping(value="/get_cabs", method = RequestMethod.POST ,headers ="Accept=application/json")
	public @ResponseBody List<CabPickupTime> getCabsforPickupPoint(@RequestBody PickupPoint pickup_point, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		List<CabPickupTime> cabs=null;
		CabPickupTime c;
		CabPickupTimeDAO dao=new CabPickupTimeDAOImpl();
		try {
			
			cabs=dao.getCabPickupTime(pickup_point);
			for(int i=0;i<cabs.size();i++)
			{
				c=cabs.get(i);
				System.out.println(c.getCab().getCab_no()+" "+c.getPickup_point().getLocation()+" "+c.getPickup_time());
			}
		}
		catch (CFMSException e)
		{
			e.printStackTrace();
		}
		return  cabs;
	}
	/**
	 * add cab pickup time
	 * @param cpt
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping(value="/add_cabpickuptime", method = RequestMethod.POST ,headers ="Accept=application/json")
	public @ResponseBody Boolean addCabPickupTime(@RequestBody CabPickupTime cpt, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		Boolean success=false;
		
		CabPickupTimeDAO dao=new CabPickupTimeDAOImpl();
		try {
			
			success=dao.insertCabPickupTime(cpt);
			
		}
		catch (CFMSException e)
		{
			e.printStackTrace();
		}
		return  success;
	}
	/**
	 * get Pickup point details
	 * @param p
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping(value = "/get_pickuppoint", method = RequestMethod.POST, headers = "Accept=application/json")
	public @ResponseBody PickupPoint getPickupPoint(@RequestBody PickupPoint p,HttpServletRequest request,  HttpServletResponse response) throws ServletException, IOException 
	{
		PickupPoint pickup=null;
		PickupPointDAO pdao=new PickupPointDAOImpl();
		System.out.println("\n"+p.getLocation());
		try {
			pickup=pdao.getPickupPoint(p.getLocation());
			
		} catch (CFMSException e) {
			
		}
		return pickup;
		
	}
	
	/**
	 * get all the requests for cab/route
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping(value = "/get_all_requests", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody List<RequestCab> getAllRequests(HttpServletRequest request,  HttpServletResponse response) throws ServletException, IOException 
	{
		List<RequestCab> requests=null;
		RequestCabDAO req_dao=new RequestCabDAOImpl();
		try {
			requests=req_dao.getAllRequests();
			
		} catch (CFMSException e) {
			
		}
		return requests;
		
	}
	
	
	/**
	 * updates employee profile
	 * @param details
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping(value="/update_profile", method = RequestMethod.POST ,headers ="Accept=application/json")
	public @ResponseBody Employee updateProfile(@RequestBody Employee details, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		Employee emp=null;
		EmployeeDAO emp_dao=new EmployeeDAOImpl();
		try {
			System.out.println("\n"+"Details\n");
			System.out.println(details.getEmail());
			//emp=emp_dao.validateUser(details.getEmployee_id(), details.getPassword());
		     emp_dao.updateEmployee(details);
		     emp=emp_dao.getEmployee(details.getEid());
		     
		}
		catch (CFMSException e)
		{
			e.printStackTrace();
		}
		return emp; 
	}

	/**
	 * Add new route to database
	 * @param route
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping(value="/add_route", method = RequestMethod.POST ,headers ="Accept=application/json")
	public @ResponseBody Route addRoute(@RequestBody Route route, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		RouteDAO route_dao=new RouteDAOImpl();
		try {
			route_dao.insertRoute(route);
			System.out.println("add route in rest");
		}
		catch (CFMSException e)
		{
			e.printStackTrace();
		}
		return route; 
	}

	/**
	 * Book cab for cab booker
	 * @param details
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping( value="/book_Cab", method= RequestMethod.POST, headers="Accept= application/json")
	public @ResponseBody CabUser bookCab(@RequestBody CabBooker details, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{	
		CabUserDAO c_dao=new CabUserDAOImpl();
		TravelHistoryDAO t_dao=new TravelHistoryDAOImpl();
		CabUser newcabuser=null,cabuser=null;
		Employee emp=new Employee();
		TravelHistory travelhistory=new TravelHistory();
		TravelHistory th=null;
		Date today=new Date();
		DateFormat df=new SimpleDateFormat("YYYY-MM-dd");
		String todayDate=df.format(today);
		try 
		{
			emp.setEid(details.getEmployee_id());
			newcabuser=c_dao.prepareCabUser(details.getCab_no(),details.getEmployee_id(),details.getPickup_id());
			/*
			 * if already cabuser, detach from old cab and add entry to travel history
			 */
			if((cabuser=c_dao.getCabUser(newcabuser.getEmployee()))!=null)
			{
				c_dao.deleteCabUser(cabuser);
				th=t_dao.getHistory(cabuser.getEmployee(), cabuser.getCab(), cabuser.getPickuppoint());
				th.setEnd_till(todayDate);
				t_dao.updateHistory(th);
				
			}
			c_dao.insertCabUser(newcabuser);
			travelhistory.setEmp(emp);
			travelhistory.setCab_no(details.getCab_no());
			travelhistory.setP_no(details.getPickup_id());
			travelhistory.setStart_date(todayDate);
			t_dao.insert(travelhistory);
		}
		catch (CFMSException e1)
		{
			e1.printStackTrace();
		}			
		return cabuser;
	}
	
	/**
	 * leaves the cab. detaches the user from cab
	 * @param details
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping( value="/leave_Cab", method= RequestMethod.POST, headers="Accept= application/json")
	public @ResponseBody Boolean leaveCab(@RequestBody CabBooker details, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{	
		CabUserDAO c_dao=new CabUserDAOImpl();
		TravelHistoryDAO t_dao=new TravelHistoryDAOImpl();
		CabUser cabuser=null;
		TravelHistory th=null;
		Employee emp=new Employee();
		Date today=new Date();
		DateFormat df=new SimpleDateFormat("YYYY-MM-dd");
		String todayDate=df.format(today);
		Boolean success=false;
		try 
		{
				emp.setEid(details.getEmployee_id());
				cabuser=c_dao.getCabUser(emp);
				c_dao.deleteCabUser(cabuser);
				th=t_dao.getHistory(cabuser.getEmployee(), cabuser.getCab(), cabuser.getPickuppoint());
				th.setEnd_till(todayDate);
				t_dao.updateHistory(th);
				success=true;
		}
		catch (CFMSException e1)
		{
			e1.printStackTrace();
		}			
		return success;
	}
	
	/**
	 * requests for new cab or route.
	 * @param reqCab
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping( value="/request_cab", method= RequestMethod.POST, headers="Accept= application/json")
	public @ResponseBody Boolean request_cabs(@RequestBody final RequestCab reqCab,HttpServletRequest request,  HttpServletResponse response) throws ServletException, IOException 
	{
		Boolean success=false;
		RequestCabDAO r_dao=new RequestCabDAOImpl();
		try
		{
			success=r_dao.insertRequestCab(reqCab);
			Thread mailThread=new Thread(new Runnable() {
				
				@Override
				public void run() {
			
				CFMSMailer mailer=new CFMSMailer();
				mailer.setMail_recepients_TO("noreply.cfms@gmail.com");
				mailer.setMail_recepients_CC(reqCab.getEmployee().getEmail());
				mailer.setMail_subject("New Cab/Route Request From "+reqCab.getEmployee().getEname());
				mailer.setMail_content("Hello admin,\n\n"+
						"Mr/Ms/Mrs. "+reqCab.getEmployee().getEname()+"(Employee id "+reqCab.getEmployee().getEid()+")"+
						"has requested for cab from location "+ reqCab.getLocation()+".\n"+
						"additional details provided by the employee is:\n"+reqCab.getRequest_details()+"\n\n");
				mailer.sendMail();
				}
			});
			mailThread.start();
		}
		catch(CFMSException e)
		{
			e.printStackTrace();
		}
		return success;
	}	
	/**
	 * change password for the employee
	 * @param details
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping(value="/change_password", method = RequestMethod.POST ,headers ="Accept=application/json")
	public @ResponseBody Boolean changePassword(@RequestBody ChangePasswordBean details, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		Boolean success=false;
		Employee emp=new Employee();
		emp.setEid(details.getEmp().getEid());
		emp.setPassword(details.getOldPassword());
		EmployeeDAO emp_dao=new EmployeeDAOImpl();
		try {
			System.out.println("\n"+"Details\n");
			boolean x=emp_dao.validatePassword(emp);
			System.out.println(x);
			System.out.println(details.getOldPassword());
			System.out.println(details.getNewPassword());
			if(x)
			{
				emp.setPassword(details.getNewPassword());
			
			  emp_dao.changePassword(emp);
			  success=true;
			} 
			  			//emp=emp_dao.validateUser(details.getEmployee_id(), details.getPassword());
			//emp_dao.updateEmployee(details);
		
		}
		catch (CFMSException e)
		{
			e.printStackTrace();
		}
		return success ; 
	}
	

	/**
	 * updates the cab/route request details
	 * @param requestCab
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping(value="/update_request", method = RequestMethod.POST ,headers ="Accept=application/json")
	public @ResponseBody Boolean updateRequest(@RequestBody final RequestCab requestCab, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		Boolean success=false;
		RequestCabDAO rdao=new RequestCabDAOImpl();
		
		try 
		{
			System.out.println("request cab"+requestCab.getRequest_id()+" "+requestCab.getStatus()+" "+requestCab.getResponse_details());
			success=rdao.updateRequestCab(requestCab);
			Thread mailThread=new Thread(new Runnable() {
				
				@Override
				public void run() {
					CFMSMailer mailer=new CFMSMailer();
					mailer.setMail_recepients_CC("noreply.cfms@gmail.com");
					mailer.setMail_recepients_TO(requestCab.getEmployee().getEmail());
					mailer.setMail_subject("Cab/Route Request Status Update "+requestCab.getEmployee().getEname());
					mailer.setMail_content("Hello Mr/Ms/Mrs. "+requestCab.getEmployee().getEname()+"(Employee id "+requestCab.getEmployee().getEid()+")"+
							"your request for cab from location "+ requestCab.getLocation()+" has been "+requestCab.getStatus());
					mailer.sendMail();
				
					
				}
			});
			mailThread.start();
			
		}
		catch (CFMSException e)
		{
			e.printStackTrace();
		}
		return success; 
	}
	
	/**
	 * gets all cab providers.
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping(value = "/get_all_providers", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody List<CabProviderBean> getAllProviders(HttpServletRequest request,  HttpServletResponse response) throws ServletException, IOException 
	{
		System.out.println("\ngetting providers\n");
		CabProviderDAO cab_provider_dao=new CabProviderDAOImpl();
		List<CabProviderBean> allCabProviders=null;
		try {
			allCabProviders=cab_provider_dao.getAllCabProviders();
		} catch (CFMSException e) {
			e.printStackTrace();
		}
		return allCabProviders;
		
	} 
	
	/**
	 * updates cab provider details
	 * @param details
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping(value="/update_provider", method = RequestMethod.POST ,headers ="Accept=application/json")
	public @ResponseBody CabProviderBean updateProvider(@RequestBody CabProviderBean details, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		CabProviderBean cabProviderBean=null;
		CabProviderDAO cab_provider_dao=new CabProviderDAOImpl();
		try {
			System.out.println("\n"+"Details\n");
			System.out.println(details.getEmail());
			//emp=emp_dao.validateUser(details.getEmployee_id(), details.getPassword());
		     cab_provider_dao.updateCabProvider(details);
		    cabProviderBean=cab_provider_dao.getCabProvider(details);
		     
		}
		catch (CFMSException e)
		{
			e.printStackTrace();
		}
		return cabProviderBean; 
	}
	
	/**
	 * adds new cab provider
	 * @param cabProvider
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping(value="/add_cab_provider", method = RequestMethod.POST ,headers ="Accept=application/json")
	public @ResponseBody CabProviderBean addCabProvider(@RequestBody CabProviderBean cabProvider, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		CabProviderDAOImpl cabProvider_dao=new CabProviderDAOImpl();
		try {
			cabProvider_dao.insertCabProvider(cabProvider);
		}
		catch (CFMSException e)
		{
			e.printStackTrace();
		}
		return cabProvider; 
	}
	
	/**
	 * adds the cab pickup timing
	 * @param pt
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping (value="/add_pickup_timings", method= RequestMethod.POST, headers= "Accept=application/json")
	public @ResponseBody Boolean  addpickuptimings(@RequestBody List<PickupTime> pt,HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		Boolean flag= false;
		PickupTimeDAOImpl ptdao = new PickupTimeDAOImpl();
		for(int i=0;i<pt.size();i++)
		{
			System.out.println(pt.get(i).getPickup_id());
			System.out.println(pt.get(i).getCab_number());
			System.out.println(pt.get(i).getApprox_time());
		}
		try
		{
			
			flag=ptdao.insert(pt);
			System.out.println(flag);
			
		}
		catch(CFMSException e)
		{
			e.printStackTrace();
		}
		return flag;
	}
}
