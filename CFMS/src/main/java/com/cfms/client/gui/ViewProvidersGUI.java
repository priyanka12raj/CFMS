package com.cfms.client.gui;

import java.util.List;

import com.cfms.shared.pojo.CabProviderBean;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.cfms.client.InfoService;

/**
 * @author Samta, Priyanka, Chaitra
 * a gui to view all the existing cab providers
 */
public class ViewProvidersGUI extends Composite {
        /**
         * a table to hold all labels, textboxes
         */
        FlexTable viewProviderTable;
        /**
         * an instance of cabProviderBean
         */
        CabProviderBean cabProvider;
        /**
         * a panel to hold all the contents
         */
        final Panel contentPanel;
        /**
         * textboxes for company name, address, email and mobile
         */
        TextBox compName, address, email, mobile;
        /**
         * a label to display the heading
         */
        Label headingProvider = new Label();
        /**
         * a vetical panel to hold the heading label and flex table
         */
        VerticalPanel providerPanel;

        /**
         * @param contentPanel
         */
        public ViewProvidersGUI(final Panel contentPanel) {
                this.contentPanel = contentPanel;
                viewProviderTable = new FlexTable();
                providerPanel = new VerticalPanel();
                headingProvider.setStyleName("heading");
                headingProvider.setText("Cab Providers");
                providerPanel.add(headingProvider);
                providerPanel.add(viewProviderTable);
                initWidget(providerPanel);
                getAllProviders();
        }

        /**
         * a method to fetch all the providers from the DB
         */
        private void getAllProviders() {
                InfoService.Util.getService().getAllProviders(new MethodCallback<List<CabProviderBean>>() {

                        @Override public void onFailure(Method method, Throwable exception) {
                                viewProviderTable.setText(6, 0, "Error could not connect to load data" + exception);

                        }

                        @Override public void onSuccess(Method method, List<CabProviderBean> response) {

                                updateDeleteTable(response);
                        }

                });
        }

        /**
         * @param cabProviderBean
         */
        private void updateDeleteTable(List<CabProviderBean> cabProviderBean) {


                viewProviderTable.setStyleName("flexth");
                viewProviderTable.setText(0, 0, "Company Name");
                viewProviderTable.setText(0, 1, "Address");
                viewProviderTable.setText(0, 2, "Email-ID");
                viewProviderTable.setText(0, 3, "Mobile");
                viewProviderTable.setStyleName("flexth");
                viewProviderTable.setBorderWidth(1);
                viewProviderTable.setCellPadding(0);
                viewProviderTable.setCellSpacing(0);
                viewProviderTable.getRowFormatter().addStyleName(0, "route-table-header");
                int i;
                for (i = 0; i < cabProviderBean.size(); i++) {
                        final int row;
                        row = i + 1;


                        compName = new TextBox();
                        address = new TextBox();
                        email = new TextBox();
                        mobile = new TextBox();


                        cabProvider = cabProviderBean.get(i);


                        compName.setEnabled(false);
                        address.setEnabled(false);
                        email.setEnabled(false);
                        mobile.setEnabled(false);

                        compName.setText(cabProvider.getCompany_name());
                        viewProviderTable.setWidget(row, 0, compName);
                        address.setText(cabProvider.getAddress());
                        viewProviderTable.setWidget(row, 1, address);
                        email.setText(cabProvider.getEmail());
                        viewProviderTable.setWidget(row, 2, email);
                        mobile.setText(cabProvider.getPhone());
                        viewProviderTable.setWidget(row, 3, mobile);

                }

        }

}


