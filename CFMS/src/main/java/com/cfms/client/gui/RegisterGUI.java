package com.cfms.client.gui;

import com.cfms.shared.pojo.Employee;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.cfms.client.InfoService;

/**
 * @author Samta, Priyanka, Chaitra
 * a gui for new user registration
 */
public class RegisterGUI extends Composite {
        /**
         * a table to hold all the widgets
         */
        FlexTable registerTable;
        /**
         * labels to display errors
         */
        Label errLabel = new Label();
        Label errLabel2 = new Label();
        Label errLabel3 = new Label();
        Label errorid = new Label("*");
        Label errorname = new Label("*");
        Label erroremail = new Label("*");
        Label errormobile = new Label("*");
        Label erroraddress = new Label("*");
        Label errorpass = new Label("*");
        Label errorSymbol = new Label("*");

        /**
         *
         */
        public RegisterGUI() {
                registerTable = new FlexTable();
                initWidget(registerTable);
                errLabel.setStyleName("red");
                errorid.setStyleName("red");
                errorname.setStyleName("red");
                erroremail.setStyleName("red");
                erroraddress.setStyleName("red");
                errormobile.setStyleName("red");
                errorpass.setStyleName("red");
                errorSymbol.setStyleName("red");

                //	registerTable.setStyleName("flexth");
                registerTable.setWidget(0, 0, errorid);
                registerTable.setWidget(1, 0, errorname);
                registerTable.setWidget(2, 0, erroremail);
                registerTable.setWidget(3, 0, errormobile);
                registerTable.setWidget(4, 0, erroraddress);
                registerTable.setWidget(5, 0, errorpass);
                registerTable.setWidget(6, 0, errorSymbol);

                registerTable.setText(0, 1, "Employee ID");
                registerTable.setText(1, 1, "Employee Name");
                registerTable.setText(2, 1, "Email-ID");
                registerTable.setText(3, 1, "Address");
                registerTable.setText(4, 1, "Mobile");
                registerTable.setText(5, 1, "Password");
                registerTable.setText(6, 1, "Confirm Password");

                final TextBox eid_text = new TextBox();
                {
                        sinkEvents(Event.ONPASTE);
                }
                final Label error_eid = new Label();
                eid_text.addKeyUpHandler(new KeyUpHandler() {

                        @Override public void onKeyUp(KeyUpEvent event) {
                                String input = eid_text.getValue();
                                if (!input.matches("[a-zA-Z0-9]*")) {
                                        error_eid.setText("Eid should not contain any special characters");
                                } else
                                        error_eid.setText("");

                        }
                });
                registerTable.setWidget(0, 3, error_eid);
                final TextBox ename_text = new TextBox();
                final Label error_ename = new Label();
                ename_text.addKeyUpHandler(new KeyUpHandler() {

                        @Override public void onKeyUp(KeyUpEvent event) {
                                String input = ename_text.getValue();
                                if (!input.matches("[ a-z A-Z ]*")) {
                                        error_ename.setText("enter a valid name");
                                } else
                                        error_ename.setText("");

                        }
                });
                registerTable.setWidget(1, 3, error_ename);
                final TextBox email_text = new TextBox();
                final Label error_email = new Label();
                email_text.addKeyUpHandler(new KeyUpHandler() {

                        @Override public void onKeyUp(KeyUpEvent event) {
                                String input = email_text.getValue();
                                if (!input.matches("[a-zA-Z0-9.]*@[a-zA-Z0-9.]*")) {
                                        error_email.setText("enter a valid email");
                                } else
                                        error_email.setText("");

                        }
                });
                registerTable.setWidget(2, 3, error_email);
                final TextArea address = new TextArea();
                address.setStyleName("textarea");
                final Label error_addr = new Label();
                address.addKeyUpHandler(new KeyUpHandler() {

                        @Override public void onKeyUp(KeyUpEvent event) {
                                String input = address.getValue();
                                if (!input.matches("[ a-z A-Z 0-9 # , .]*")) {
                                        error_addr.setText("enter a valid address");
                                } else
                                        error_addr.setText("");

                        }
                });
                registerTable.setWidget(3, 3, error_addr);
                final TextBox mobile = new TextBox();
                final Label error_mobile = new Label();
                mobile.addKeyUpHandler(new KeyUpHandler() {

                        @Override public void onKeyUp(KeyUpEvent event) {
                                String input = mobile.getValue();
                                if (!input.matches("[0-9]*") && !(mobile.getText().length() == 10)) {
                                        error_mobile.setText("enter a valid number");
                                } else
                                        error_mobile.setText("");

                        }
                });
                registerTable.setWidget(4, 3, error_mobile);
                final PasswordTextBox pwd_text = new PasswordTextBox();

                final PasswordTextBox confirm_pass = new PasswordTextBox();
                registerTable.setWidget(0, 2, eid_text);
                registerTable.setWidget(1, 2, ename_text);
                registerTable.setWidget(2, 2, email_text);
                registerTable.setWidget(3, 2, address);
                registerTable.setWidget(4, 2, mobile);
                registerTable.setWidget(5, 2, pwd_text);
                registerTable.setWidget(6, 2, confirm_pass);

                Button registerBtn = new Button("Register");
                registerTable.setWidget(7, 2, registerBtn);
                registerBtn.setStyleName("purple");
                Button loginBtn = new Button("Already an user?");
                registerTable.setWidget(7, 3, loginBtn);
                loginBtn.setStyleName("newpurple");
                registerTable.setWidget(8, 2, errLabel);
                registerTable.setWidget(9, 2, errLabel2);
                registerTable.setWidget(10, 2, errLabel3);

                registerBtn.addClickHandler(new ClickHandler() {

                        @Override public void onClick(ClickEvent event) {
                                String ename = ename_text.getText();
                                String eid = eid_text.getText();
                                String email = email_text.getText();
                                String phone = mobile.getText();
                                String addr = address.getText();
                                String pwd = pwd_text.getText();
                                String confirm = confirm_pass.getText();
                                Employee empBean = new Employee();
                                empBean.setEname(ename);
                                empBean.setEid(eid);
                                empBean.setEmail(email);
                                empBean.setPhone(phone);
                                empBean.setAddress(addr);
                                if (eid.isEmpty() || !eid.matches("[a-zA-Z0-9]*")) {
                                        errLabel.setText("Please enter a valid ID");
                                } else if (ename.isEmpty() || !ename.matches("[ a-z A-Z]*")) {
                                        errLabel.setText("Please enter a valid name");
                                } else if (email.isEmpty() || !email.matches("[a-zA-Z0-9.]*@[a-zA-Z0-9.]*")) {
                                        errLabel.setText("Please enter a valid email");
                                } else if (addr.isEmpty() || !addr.matches("[ a-z A-Z 0-9 # , .]*")) {
                                        errLabel.setText("Please enter valid address");
                                } else if (!(phone.length() == 10) || !phone.matches("[0-9]*")) {
                                        errLabel.setText("Please enter a valid phone number");
                                } else if (!(pwd.length() >= 8)) {
                                        errLabel.setText("Minimum password length is 8");
                                } else if (!pwd.equals(confirm)) {
                                        errLabel.setText("Both the passwords should match");
                                } else {
                                        empBean.setPassword(pwd);
                                        registerRequest(empBean);
                                }
                        }
                });

                loginBtn.addClickHandler(new ClickHandler() {

                        @Override public void onClick(ClickEvent event) {
                                registerTable.getParent().removeFromParent();
                                RootLayoutPanel.get().add(new EmployeeLoginGUI());

                        }
                });

        }

        /**
         * @param emp a method to register
         */
        private void registerRequest(Employee emp) {
                InfoService.Util.getService().userRegister(emp, new MethodCallback<Employee>() {

                        @Override public void onFailure(Method method, Throwable exception) {
                                //AlertBox.show("failure");
                                errLabel.setText("could not connect to server");
                        }

                        @Override public void onSuccess(Method method, Employee response) {

                                if (response != null) {


                                        SetEmployeeCookie(response);
                                        CookieBeans.employee = response;
                                        registerTable.getParent().removeFromParent();
                                        RootLayoutPanel.get().remove(0);
                                        RootLayoutPanel.get().add(new IndexGUI());
                                } else
                                        errLabel.setText("Cannot Register");
                        }
                });
        }

        /**
         * @param emp a method to set the employee cookie
         */
        protected void SetEmployeeCookie(Employee emp) {
                Cookies.setCookie("eid", emp.getEid());
                Cookies.setCookie("ename", emp.getEname());
                Cookies.setCookie("isadmin", String.valueOf(emp.getIsAdmin()));
        }
}
