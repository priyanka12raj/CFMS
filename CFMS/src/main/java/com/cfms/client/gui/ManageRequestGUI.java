package com.cfms.client.gui;

import java.util.List;

import com.cfms.client.gui.widgets.AlertBox;
import com.cfms.shared.pojo.RequestCab;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.cfms.client.InfoService;

/**
 * GUI allows to view and manage the requests
 *
 * @author Priyanka, Samta, Chaitra
 */
public class ManageRequestGUI extends Composite {
        VerticalPanel mainPanel;
        /**
         * Provides options to manage requests
         */
        HorizontalPanel navPanel;
        /**
         * displays the requests
         */
        FlexTable reqTable;
        /**
         * list of cab requests
         */
        List<RequestCab> request_list;
        ManageRequestGUI gui;
        RequestCab updatableReq;

        /**
         * adds the widget to manage requests
         */
        public ManageRequestGUI() {
                this.gui = this;
                mainPanel = new VerticalPanel();
                mainPanel.setSpacing(2);
                /**
                 * Initializing the requests table and formatting it
                 */
                reqTable = new FlexTable();
                reqTable.setStyleName("flexth");
                reqTable.getRowFormatter().addStyleName(0, "route-table-header");
                reqTable.setBorderWidth(1);
                reqTable.setCellPadding(0);
                reqTable.setCellSpacing(0);

                /**
                 * Create navigation panel buttons and add it to the panel
                 */
                navPanel = new HorizontalPanel();
                navPanel.setSpacing(10);
                Button seeAllRequestBtn = new Button("All Requests");
                Button seeNewRequestsBtn = new Button("New Requests");
                Button seeAcceptedRequestsBtn = new Button("Approved Requests");
                Button seeRejectedRequestsBtn = new Button("Rejected Requests");
                Button seeClosedRequestsBtn = new Button("Closed Requests");
                navPanel.add(seeAllRequestBtn);
                navPanel.add(seeNewRequestsBtn);
                navPanel.add(seeAcceptedRequestsBtn);
                navPanel.add(seeRejectedRequestsBtn);
                //navPanel.add(seeClosedRequestsBtn);

                /**
                 * add clickhandlers to the buttons
                 */
                seeAllRequestBtn.addClickHandler(new SeeButtonHandler("all"));
                seeNewRequestsBtn.addClickHandler(new SeeButtonHandler("requested"));
                seeAcceptedRequestsBtn.addClickHandler(new SeeButtonHandler("approved"));
                seeRejectedRequestsBtn.addClickHandler(new SeeButtonHandler("rejected"));
                seeClosedRequestsBtn.addClickHandler(new SeeButtonHandler("closed"));
                /**
                 * add panels to main panel
                 */
                mainPanel.add(navPanel);
                mainPanel.add(reqTable);
                initWidget(mainPanel);
                /**
                 * initially load all requests
                 */
                loadRequests("all");
        }

        /**
         * Load all the requests and display them based on status
         *
         * @param status status of request
         */
        private void loadRequests(final String status) {
                InfoService.Util.getService().getAllRequests(new MethodCallback<List<RequestCab>>() {

                        @Override public void onSuccess(Method method, List<RequestCab> response) {
                                request_list = response;
                                updateReqTable(status);
                        }

                        @Override public void onFailure(Method method, Throwable exception) {
                                AlertBox.show("failure");
                        }
                });
        }

        /**
         * shows the requests based on the status
         *
         * @param status status of request to be displayed
         */
        protected void updateReqTable(final String status) {
                int row = 0;
                Button respondBtn;
                String btn_text;
                if (status.equals("requested"))
                        btn_text = "Respond";
                else
                        btn_text = "Change";

                if (request_list != null) {
                        reqTable.removeAllRows();
                        reqTable.getRowFormatter().addStyleName(0, "route-table-header");
                        reqTable.setText(0, 1, "Request Id");
                        reqTable.setText(0, 2, "Employee Name");
                        reqTable.setText(0, 3, "Pickup location");
                        reqTable.setText(0, 4, "Request Details");
                        reqTable.setText(0, 5, "status");
                        reqTable.setText(0, 6, "Response Details");
                        row++;
                        for (int i = 0; i < request_list.size(); i++) {
                                final RequestCab req = request_list.get(i);
                                /**
                                 * if req status matches with the given status display
                                 */
                                if (status.equals("all") || req.getStatus().equals(status)) {
                                        respondBtn = new Button(btn_text);
                                        /**
                                         * display the request details
                                         */
                                        reqTable.setText(row, 1, String.valueOf(req.getRequest_id()));
                                        reqTable.setText(row, 2, req.getEmployee().getEname());
                                        reqTable.setText(row, 3, req.getLocation());
                                        reqTable.setText(row, 4, req.getRequest_details());
                                        reqTable.setText(row, 5, req.getStatus());
                                        final int r = row;
                                        final TextArea response_details = new TextArea();
                                        response_details.setEnabled(false);
                                        response_details.setStyleName("textarea");
                                        response_details.setText(req.getResponse_details());
                                        reqTable.setWidget(row, 6, response_details);
                                        /**
                                         * add respond button if request status is newly requested
                                         */
                                        if (status.equals("requested"))
                                                reqTable.setWidget(row, 7, respondBtn);
                                        respondBtn.addClickHandler(new ClickHandler() {

                                                @Override public void onClick(ClickEvent arg0) {
                                                        response_details.setEnabled(true);
                                                        final StatusList statusList = new StatusList();
                                                        //reqTable.removeCell(r, 5);
                                                        reqTable.setWidget(r, 5, statusList);
                                                        Button btn = new Button("Update");
                                                        //reqTable.removeCell(r, 7);
                                                        reqTable.setWidget(r, 7, btn);
                                                        btn.addClickHandler(new ClickHandler() {

                                                                @Override public void onClick(ClickEvent arg0) {

                                                                        req.setStatus(statusList.getSelectedItemText());
                                                                        req.setResponse_details(response_details.getText());
                                                                        if (req.getStatus().equals("approved")) {
                                                                                updatableReq = req;
                                                                                RequestDialog approveDialog = new RequestDialog(gui);
                                                                                approveDialog.setWidth("1000");

                                                                                approveDialog.show();
                                                                        } else
                                                                                updateRequestThenTable(req, status);

                                                                        request_list = null;

                                                                }
                                                        });

                                                }
                                        });
                                        row++;
                                }
                        }
                } else
                        loadRequests(status);
        }

        /**
         * click Handler to display requests based on status
         *
         * @author Priyanka
         */
        private class SeeButtonHandler implements ClickHandler {
                String status;

                public SeeButtonHandler(String status) {
                        this.status = status;
                }

                @Override public void onClick(ClickEvent arg0) {
                        updateReqTable(status);
                }

        }

        /**
         * ListBox with request status
         *
         * @author Priyanka
         */
        private class StatusList extends ListBox {
                public String[] statusOptions = { "approved", "rejected" };//,"closed"};

                public StatusList() {
                        for (int i = 0; i < statusOptions.length; i++)
                                addItem(statusOptions[i]);
                }
        }

        /**
         * update the request status and response details
         *
         * @param req
         * @param status
         */
        public void updateRequestThenTable(RequestCab req, final String status) {

                InfoService.Util.getService().updateRequest(req, new MethodCallback<Boolean>() {

                        @Override public void onFailure(Method method, Throwable exception) {
                                updateReqTable(status);
                                AlertBox.show("failure");
                        }

                        @Override public void onSuccess(Method method, Boolean response) {
                                updateReqTable(status);
                        }
                });
        }
}
