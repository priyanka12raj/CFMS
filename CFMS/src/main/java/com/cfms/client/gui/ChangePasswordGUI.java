package com.cfms.client.gui;

import com.cfms.shared.pojo.ChangePasswordBean;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.cfms.client.InfoService;

/**
 * A GUI class to change password
 *
 * @author Samta, Priyanka, Chaitra
 */
public class ChangePasswordGUI extends Composite {

        /**
         * a table to hold the current and new passwords with a submit button
         */
        FlexTable passTable;
        /**
         * a label to display the error
         */
        Label errLabel = new Label();
        /**
         * a panel to hold the entire contents
         */
        Panel mainPanel;

        /**
         *
         */
        public ChangePasswordGUI() {
                mainPanel = new HorizontalPanel();
                passTable = new FlexTable();
                initWidget(mainPanel);
                passTable.setText(0, 0, "Current Password");
                passTable.setText(1, 0, "New Password");
                passTable.setText(2, 0, "Confirm Password");
                final PasswordTextBox current_pass = new PasswordTextBox();
                final PasswordTextBox new_pass = new PasswordTextBox();
                final PasswordTextBox confirm_pass = new PasswordTextBox();
                passTable.setWidget(0, 1, current_pass);
                passTable.setWidget(1, 1, new_pass);
                passTable.setWidget(2, 1, confirm_pass);
                Button updateBtn = new Button("Change");
                passTable.setWidget(3, 1, updateBtn);
                passTable.setWidget(4, 0, errLabel);
                mainPanel.add(passTable);
                updateBtn.addClickHandler(new ClickHandler() {

                        @Override public void onClick(ClickEvent event) {
                                String current = current_pass.getText();
                                String newpass = new_pass.getText();
                                String confirm = confirm_pass.getText();
                                ChangePasswordBean cpBean = new ChangePasswordBean();
                                cpBean.setEmp(CookieBeans.employee);
                                if (!(newpass.length() >= 8)) {
                                        errLabel.setText("Password should be atleast 8 characters long");
                                } else if (newpass.equals(confirm)) {
                                        cpBean.setOldPassword(current);
                                        cpBean.setNewPassword(newpass);
                                        changePassword(cpBean);
                                } else {
                                        errLabel.setText("Please enter same passwords");
                                }
                        }
                });

        }

        /**
         * A method to change the password
         *
         * @param cpBean
         */
        void changePassword(ChangePasswordBean cpBean) {
                InfoService.Util.getService().changePassword(cpBean, new MethodCallback<Boolean>() {

                        @Override public void onFailure(Method method, Throwable exception) {
                                //AlertBox.show("failure");
                                errLabel.setText("callback Failure");
                        }

                        @Override public void onSuccess(Method method, Boolean b) {
                                if (b != false) {
                                        mainPanel.clear();
                                        mainPanel.add(new Label("Password changed"));


                                } else
                                        errLabel.setText("Cannot change");
                        }
                });


        }


}

