package com.cfms.client;


import com.cfms.client.gui.CookieBeans;
import com.cfms.client.gui.IndexGUI;
import com.cfms.shared.pojo.CabUser;
import com.cfms.shared.pojo.Employee;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.cfms.client.gui.EmployeeLoginGUI;


/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class CabManagementSystem implements EntryPoint {

        /**
         * This is the entry point method.
         */
        public void onModuleLoad() {
                /**
                 * checking if user is already logged in
                 */
                if (Cookies.getCookie("logged_in") != null && Cookies.getCookie("logged_in").equals("true")) {
                        //if logged in redirecting user to home page after loading employee details
                        SetIndexForEmp(Cookies.getCookie("eid"));

                } else {
                        //else redirecting to login page
                        RootLayoutPanel.get().add(new EmployeeLoginGUI());
                }

        }

        /**
         * Prepares the index page for Employee.
         *
         * @param eid employee id
         */
        private void SetIndexForEmp(String eid) {
                Employee e = new Employee();
                e.setEid(eid);
                //requesting server for getting Employee details
                InfoService.Util.getService().getEmployee(e, new MethodCallback<Employee>() {

                        @Override public void onSuccess(Method method, Employee response) {
                                CookieBeans.employee = response;
                                SetCabDetailsIfCabUser(response);
                        }

                        @Override public void onFailure(Method method, Throwable exception) {

                        }
                });
        }

        /**
         * Checks if employee is a cab user if yes then loads the cab user details
         *
         * @param emp employee
         */
        public void SetCabDetailsIfCabUser(Employee emp) {
                //request server to get cab user
                InfoService.Util.getService().getCabUser(emp, new MethodCallback<CabUser>() {

                        @Override public void onFailure(Method method, Throwable exception) {
                                CookieBeans.setNotCabUser();
                                gotoHomePage();
                        }

                        @Override public void onSuccess(Method method, CabUser response) {
                                if (response != null)
                                        CookieBeans.setCabUser(response);
                                else
                                        CookieBeans.setNotCabUser();
                                gotoHomePage();
                        }
                });
        }

        /**
         * redirect to home page
         */
        private void gotoHomePage() {
                RootLayoutPanel.get().add(new IndexGUI());
        }

}
