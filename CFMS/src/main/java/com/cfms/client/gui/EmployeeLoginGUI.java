package com.cfms.client.gui;

import com.cfms.client.InfoService;
import com.cfms.client.gui.widgets.AlertBox;
import com.cfms.shared.pojo.CabUser;
import com.cfms.shared.pojo.Employee;
import com.cfms.shared.pojo.LoginBean;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * @author Samta, Priyanka, Chaitra
 * a GUI for Login
 */
public class EmployeeLoginGUI extends Composite {

        /**
         * a dockLayoutpanel to hold the entire page
         */
        DockLayoutPanel mainPanel = new DockLayoutPanel(Unit.PX);
        /**
         * a horizontal panel to hold username and password fields
         */
        HorizontalPanel loginPanel = new HorizontalPanel();
        /**
         * a text box for username input
         */
        final TextBox eid_text = new TextBox();
        /**
         * a password text box for password input
         */
        final PasswordTextBox pwd_text = new PasswordTextBox();
        /**
         * a boolean status variable
         */
        static boolean loginVar = true;

        /**
         *
         */
        public EmployeeLoginGUI() {
                HTML header = new HTML("NetCracker Cab Facility Management System");
                header.setStyleName("header");
                initWidget(mainPanel);
                mainPanel.addNorth(header, 55);
                mainPanel.add(loginPanel);
                loginPanel.setStyleName("login-panel");

                VerticalPanel labelPanel = new VerticalPanel();
                labelPanel.setSpacing(10);

                //labelPanel.add(errLabel);
                labelPanel.add(new Label("Employee Id"));
                labelPanel.add(new Label("Password"));

                VerticalPanel textPanel = new VerticalPanel();
                textPanel.setSpacing(10);

                textPanel.add(eid_text);

                textPanel.add(pwd_text);
                Button loginBtn = new Button("Login");
                loginBtn.setStyleName("purple");
                Button registerBtn = new Button("Register");
                registerBtn.setStyleName("purple");
                loginBtn.addClickHandler(new MyClickHandler());

                registerBtn.addClickHandler(new ClickHandler() {

                        @Override public void onClick(ClickEvent event) {
                                loginPanel.clear();
                                loginPanel.add(new RegisterGUI());

                        }
                });
                textPanel.add(loginBtn);
                textPanel.add(registerBtn);
                loginPanel.add(labelPanel);
                loginPanel.add(textPanel);

        }

        /**
         * @author Samta, Priyanka, Chaitra
         * A class to implement event handling
         */
        class MyClickHandler implements ClickHandler, KeyUpHandler {

                @Override public void onKeyUp(KeyUpEvent event) {
                        if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
                                String eid = eid_text.getText();
                                String pwd = pwd_text.getText();
                                LoginBean bean = new LoginBean();
                                bean.setEmployee_id(eid);
                                bean.setPassword(pwd);
                                sendGWTRestRequest(bean);
                        }

                }

                @Override public void onClick(ClickEvent event) {
                        String eid = eid_text.getText();
                        String pwd = pwd_text.getText();
                        LoginBean bean = new LoginBean();
                        bean.setEmployee_id(eid);
                        bean.setPassword(pwd);
                        sendGWTRestRequest(bean);

                }

        }

        /**
         * @param details a method to login
         */
        private void sendGWTRestRequest(LoginBean details) {
                InfoService.Util.getService().userLogin(details, new MethodCallback<Employee>() {

                        @Override public void onFailure(Method method, Throwable exception) {
                                AlertBox.show("could not connect to server");
                                //errLabel.setText("could not connect to server");
                        }

                        @Override public void onSuccess(Method method, Employee response) {
                                //AlertBox.show("callback success");
                                if (response != null) {

                                        //AlertBox.show("callback success");
                                        SetCabDetailsIfCabUser(response);
                                        CookieBeans.SetEmployeeCookie(response);

                                } else
                                        AlertBox.show("Wrong Employee id or password");
                        }
                });
        }

        /**
         * @param emp a method to set the details of the employee based on his login(new or existing user)
         */
        public void SetCabDetailsIfCabUser(Employee emp) {
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
         * this method directs to home page
         */
        private void gotoHomePage() {
                RootLayoutPanel.get().remove(0);
                RootLayoutPanel.get().add(new IndexGUI());
        }
}

