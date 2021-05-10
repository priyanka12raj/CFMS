package com.cfms.client.gui;

import java.util.List;

import com.cfms.shared.pojo.CabUser;
import com.cfms.shared.pojo.Employee;
import com.cfms.shared.pojo.PickupPoint;
import com.google.gwt.user.client.Cookies;

/**
 * Holds details about the current user
 *
 * @author Priyanka, Samta, Chaitra
 */
public class CookieBeans {
        /**
         * Employee currently logged in
         */
        public static Employee employee;
        /**
         * cab and pickup point details of current employee
         */
        public static CabUser cabuser;
        /**
         * true if currently logged in employee is a cab user otherwise false
         */
        public static boolean isCabUser;
        public static List<PickupPoint> pickup_point_list;

        /**
         * sets the currently logged in employee as cab user
         *
         * @param cabuser cabuser details
         */
        public static void setCabUser(CabUser cabuser) {
                CookieBeans.isCabUser = true;
                CookieBeans.cabuser = cabuser;
                Cookies.setCookie("cabuser", "true");
        }

        /**
         * sets the currently logged in employee as not a cab user
         */
        public static void setNotCabUser() {
                CookieBeans.isCabUser = false;
                CookieBeans.cabuser = null;
                Cookies.setCookie("cabuser", "false");
        }

        /**
         * Creates a cookie for the currently logged in employee
         *
         * @param emp
         */
        public static void SetEmployeeCookie(Employee emp) {
                CookieBeans.employee = emp;
                Cookies.setCookie("logged_in", "true");
                Cookies.setCookie("eid", emp.getEid());
                Cookies.setCookie("ename", emp.getEname());
                Cookies.setCookie("isadmin", String.valueOf(emp.getIsAdmin()));
        }
}
