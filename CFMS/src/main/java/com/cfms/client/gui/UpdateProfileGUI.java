package com.cfms.client.gui;

import com.cfms.client.gui.widgets.AlertBox;
import com.cfms.shared.pojo.Employee;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.cfms.client.InfoService;


/**
 * @author Samta, Priyanka, Chaitra
 * a gui to update the user profile
 */
public class UpdateProfileGUI extends Composite {
        /**
         * a flextable to hold all labels and text boxes
         */
        FlexTable profilePanel = new FlexTable();
        /**
         * labels to display errors
         */
        Label errLabel = new Label();
        Label errLabel2 = new Label();

        /**
         * text boxes for employee name, email, mobile and address
         */
        final TextBox ename = new TextBox();
        final TextBox email_text = new TextBox();
        final TextBox mobile = new TextBox();
        final TextArea address = new TextArea();

        /**
         * buttons to change and update the password
         */
        final Button changeBtn = new Button("Change");
        final Button updateBtn = new Button("Update");

        public UpdateProfileGUI() {
                final Employee emp = CookieBeans.employee;
                initWidget(profilePanel);

                profilePanel.setText(0, 0, "Employee Name");
                profilePanel.setText(1, 0, "Email-ID");
                profilePanel.setText(2, 0, "Mobile");
                profilePanel.setText(3, 0, "Address");
                errLabel.setStyleName("red");

                ename.setText(emp.getEname());
                profilePanel.setWidget(0, 1, ename);
                final Label error_ename = new Label();
                error_ename.setStyleName("red");
                ename.addKeyUpHandler(new KeyUpHandler() {

                        @Override public void onKeyUp(KeyUpEvent event) {
                                String input = ename.getValue();
                                if (!input.matches("[ a-z A-Z ]*")) {
                                        error_ename.setText("enter a valid name");
                                } else
                                        error_ename.setText(" ");

                        }
                });
                profilePanel.setWidget(0, 2, error_ename);
                email_text.setText(emp.getEmail());
                profilePanel.setWidget(1, 1, email_text);
                final Label error_email = new Label();
                error_email.setStyleName("red");
                email_text.addKeyUpHandler(new KeyUpHandler() {

                        @Override public void onKeyUp(KeyUpEvent event) {
                                String input = email_text.getValue();
                                if (!input.matches("[a-zA-Z0-9.]*@[a-zA-Z0-9.]*")) {
                                        error_email.setText("enter a valid email");
                                } else
                                        error_email.setText(" ");

                        }
                });
                profilePanel.setWidget(1, 2, error_email);
                mobile.setText(emp.getPhone());
                profilePanel.setWidget(2, 1, mobile);
                final Label error_mobile = new Label();
                error_mobile.setStyleName("red");
                mobile.addKeyUpHandler(new KeyUpHandler() {

                        @Override public void onKeyUp(KeyUpEvent event) {
                                String input = mobile.getValue();
                                if (!input.matches("[0-9]*") && !(input.length() == 10)) {
                                        error_mobile.setText("enter a valid number");
                                } else
                                        error_mobile.setText(" ");

                        }
                });
                profilePanel.setWidget(2, 2, error_mobile);
                address.setText(emp.getAddress());
                address.setStyleName("textarea");
                profilePanel.setWidget(3, 1, address);
                final Label error_addr = new Label();
                error_addr.setStyleName("red");
                address.addKeyUpHandler(new KeyUpHandler() {

                        @Override public void onKeyUp(KeyUpEvent event) {
                                String input = address.getValue();
                                if (!input.matches("[ a-z A-Z 0-9 # , - ]*")) {
                                        error_addr.setText("enter a valid address");
                                } else
                                        error_addr.setText(" ");

                        }
                });
                profilePanel.setWidget(3, 2, error_addr);
                profilePanel.setWidget(4, 1, changeBtn);
                profilePanel.setWidget(5, 1, updateBtn);

                profilePanel.setWidget(7, 1, errLabel2);

                changeBtn.addClickHandler(new ClickHandler() {

                        @Override public void onClick(ClickEvent arg0) {
                                setUpdateView();
                        }
                });

                updateBtn.addClickHandler(new ClickHandler() {

                        @Override public void onClick(ClickEvent event) {
                                String name = ename.getText();
                                String email = email_text.getText();
                                String phone = mobile.getText();
                                String addr = address.getText();
                                Employee empBean = new Employee();
                                empBean.setEid(emp.getEid());
                                empBean.setEname(name);
                                empBean.setEmail(email);
                                empBean.setPhone(phone);
                                empBean.setAddress(addr);
                                if (name.isEmpty() && email.isEmpty() && !(phone.length() == 10) && addr.isEmpty()) {
                                        errLabel.setText("Enter all details");
                                } else if (name.isEmpty() || !name.matches("[ a-z A-Z ]*")) {
                                        errLabel.setText("Please enter a valid name");
                                } else if (email.isEmpty() || !email.matches("[a-zA-Z0-9.]*@[a-zA-Z0-9.]*")) {
                                        errLabel.setText("Please enter a valid email");
                                } else if (!(phone.length() == 10) || !phone.matches("[0-9]*")) {
                                        errLabel.setText("Please enter a valid mobile number");
                                } else if (addr.isEmpty() || !addr.matches("[ a-z A-Z 0-9 # , .]*")) {
                                        errLabel.setText("Please enter valid address");
                                } else {
                                        sendGWTRestRequest(empBean);
                                }
                                profilePanel.setWidget(6, 1, errLabel);
                        }
                });
                setProfileView();

        }

        /**
         * @param details a method to update the profile
         */
        private void sendGWTRestRequest(Employee details) {
                //AlertBox.show("sending request");
                InfoService.Util.getService().updateProfile(details, new MethodCallback<Employee>() {

                        @Override public void onFailure(Method method, Throwable exception) {
                                AlertBox.show("failure");
                                errLabel.setText("callback Failure");
                        }

                        @Override public void onSuccess(Method method, Employee response) {
                                //AlertBox.show("callback success");
                                if (response != null) {
                                        CookieBeans.employee = response;
                                        setProfileView();
                                }
                        }
                });
        }

        /**
         * a method to enable the text boxes and show the change button
         */
        private void setUpdateView() {
                errLabel.setText(" ");
                errLabel2.setText(" ");
                ename.setEnabled(true);
                email_text.setEnabled(true);
                mobile.setEnabled(true);
                address.setEnabled(true);
                updateBtn.setVisible(true);
                changeBtn.setVisible(false);
        }

        /**
         * a method to disable text boxes and show update button
         */
        private void setProfileView() {
                errLabel.setText(" ");
                errLabel2.setText(" ");
                ename.setEnabled(false);
                email_text.setEnabled(false);
                mobile.setEnabled(false);
                address.setEnabled(false);
                updateBtn.setVisible(false);
                changeBtn.setVisible(true);
        }
}

