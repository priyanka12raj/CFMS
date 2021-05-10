package com.cfms.client.gui;

import com.cfms.shared.pojo.CabProviderBean;
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
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.TextBox;
import com.cfms.client.InfoService;
import com.cfms.client.gui.widgets.AlertBox;

/**
 * @author Samta, Priyanka, Chaitra
 * a gui for adding new providers
 */
public class AddProviderGUI extends Composite {
        /**
         * a table to hold all labels, textboxes
         */
        FlexTable providerTable;
        /**
         * text boxes for company name,email,address and mobile
         */
        TextBox compname_text, addr, email_text, mobile_no;
        /**
         * a label to display errors
         */
        Label errLabel = new Label();

        /**
         * a panel to hold the flextable
         */
        final Panel contentPanel;

        /**
         * @param contentPanel
         */
        public AddProviderGUI(final Panel contentPanel) {
                this.contentPanel = contentPanel;
                providerTable = new FlexTable();
                initWidget(providerTable);
                providerTable.setStyleName("flexth");
                providerTable.setText(0, 0, "Company Name");
                providerTable.setText(1, 0, "Address");
                providerTable.setText(2, 0, "Email-ID");
                providerTable.setText(3, 0, "Mobile");

                errLabel.setStyleName("red");
                compname_text = new TextBox();
                final Label error_cname = new Label();
                error_cname.setStyleName("red");
                compname_text.addKeyUpHandler(new KeyUpHandler() {

                        @Override public void onKeyUp(KeyUpEvent event) {
                                String input = compname_text.getValue();
                                if (!input.matches("[ a-z A-Z 0-9 ]*")) {
                                        error_cname.setText("enter a valid name");
                                } else
                                        error_cname.setText(" ");

                        }
                });
                providerTable.setWidget(0, 1, compname_text);
                providerTable.setWidget(0, 2, error_cname);


                addr = new TextBox();
                addr.setStyleName("textarea");
                final Label error_addr = new Label();
                error_addr.setStyleName("red");
                addr.addKeyUpHandler(new KeyUpHandler() {

                        @Override public void onKeyUp(KeyUpEvent event) {
                                String input = addr.getValue();
                                if (!input.matches("[ a-z A-Z 0-9 # , ]*")) {
                                        error_addr.setText("enter a valid address");
                                } else
                                        error_addr.setText(" ");

                        }
                });
                providerTable.setWidget(1, 1, addr);
                providerTable.setWidget(1, 2, error_addr);

                email_text = new TextBox();
                providerTable.setWidget(2, 1, email_text);
                final Label error_email = new Label();
                error_email.setStyleName("red");
                email_text.addKeyUpHandler(new KeyUpHandler() {

                        @Override public void onKeyUp(KeyUpEvent event) {
                                String input = email_text.getValue();
                                if (!input.matches("[a-zA-Z0-9.]*[a-zA-Z0-9.]@[a-zA-Z0-9.]*[a-zA-Z0-9.]")) {
                                        error_email.setText("enter a valid email");
                                } else
                                        error_email.setText(" ");

                        }
                });
                providerTable.setWidget(2, 2, error_email);

                mobile_no = new TextBox();
                providerTable.setWidget(3, 1, mobile_no);
                final Label error_mobile = new Label();
                error_mobile.setStyleName("red");
                mobile_no.addKeyUpHandler(new KeyUpHandler() {

                        @Override public void onKeyUp(KeyUpEvent event) {
                                String input = mobile_no.getValue();
                                if (!input.matches("[0-9]*") && !(input.length() == 10)) {
                                        error_mobile.setText("enter a valid number");
                                } else
                                        error_mobile.setText(" ");

                        }
                });
                providerTable.setWidget(3, 2, error_mobile);
                Button addProviderBtn = new Button("Add Provider");
                providerTable.setWidget(4, 1, addProviderBtn);


                addProviderBtn.addClickHandler(new ClickHandler() {

                        @Override public void onClick(ClickEvent arg0) {
                                String cname = compname_text.getText();
                                String address = addr.getText();
                                String email = email_text.getText();
                                String mobile = mobile_no.getText();
                                CabProviderBean cabProvider = new CabProviderBean();
                                cabProvider.setCompany_name(cname);
                                cabProvider.setAddress(address);
                                cabProvider.setEmail(email);
                                cabProvider.setPhone(mobile);
                                if (cname.isEmpty() && email.isEmpty() && !(mobile.length() == 10) && address.isEmpty()) {
                                        errLabel.setText("Enter all details");
                                } else if (cname.isEmpty() || !cname.matches("[ a-z A-Z 0-9 ]*")) {
                                        errLabel.setText("Please enter a valid name");
                                } else if (address.isEmpty() || !address.matches("[ a-z A-Z 0-9 # , .]*")) {
                                        errLabel.setText("Please enter valid address");
                                } else if (email.isEmpty() || !email.matches("[a-zA-Z0-9.]*[a-zA-Z0-9.]@[a-zA-Z0-9.]*[a-zA-Z0-9.]")) {
                                        errLabel.setText("Please enter a valid email");
                                } else if (!(mobile.length() == 10) || !mobile.matches("[0-9]*")) {
                                        errLabel.setText("Please enter a valid mobile number");
                                } else {
                                        addNewCabProvider(cabProvider);
                                }
                                providerTable.setWidget(5, 1, errLabel);
                        }
                });
        }

        /**
         * @param cabProvider a method to add new cab provider
         */
        private void addNewCabProvider(CabProviderBean cabProvider) {
                InfoService.Util.getService().addCabProvider(cabProvider, new MethodCallback<CabProviderBean>() {

                        @Override public void onSuccess(Method method, CabProviderBean response) {
                                if (response != null) {
                                        contentPanel.clear();
                                        contentPanel.add(new ViewProvidersGUI(contentPanel));
                                } else
                                        errLabel.setText("Cannot add");
                        }

                        @Override public void onFailure(Method method, Throwable exception) {
                                AlertBox.show("Failure");
                        }
                });
        }
}
