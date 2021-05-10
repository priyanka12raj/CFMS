package com.cfms.client.gui;

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
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.cfms.client.InfoService;

/**
 * Gui to request for a cab from a pick up point
 *
 * @author Priyanka, Chaitra, Samta
 */
public class RequestCabGUI extends Composite {
        /*
         * Widgets for requesting for cab
         */ HorizontalPanel requestPanel = new HorizontalPanel();
        FlexTable requestTable = new FlexTable();
        TextBox loc_txt = new TextBox();
        TextArea details_txt = new TextArea();
        Button submit = new Button("Request Cab/Route");

        public RequestCabGUI(String pickup_point) {
                initWidget(requestPanel);
                requestPanel.add(requestTable);
                details_txt.setStyleName("textarea");
                //setting labels
                requestTable.setText(0, 0, "Request New Cab Route");
                requestTable.setText(1, 0, "Location");
                requestTable.setText(2, 0, "Request Details");
                //setting widgets and values
                loc_txt.setText(pickup_point);
                requestTable.setWidget(1, 1, loc_txt);
                requestTable.setWidget(2, 1, details_txt);
                requestTable.setWidget(3, 1, submit);
                /**
                 * click handler to submit request
                 */
                submit.addClickHandler(new ClickHandler() {
                        @Override public void onClick(ClickEvent event) {
                                String loc = loc_txt.getText().trim();
                                if (loc != "") {
                                        RequestCab req = new RequestCab();
                                        req.setEmployee(CookieBeans.employee);
                                        req.setLocation(loc);
                                        req.setRequest_details(details_txt.getText());
                                        req.setStatus("requested");
                                        requestNewCab(req);
                                }
                        }
                });


        }

        /**
         * updates the request for new cab in gui
         *
         * @param req
         */
        protected void requestNewCab(RequestCab req) {
                InfoService.Util.getService().request_cab(req, new MethodCallback<Boolean>() {

                        @Override public void onSuccess(Method method, Boolean response) {
                                if (response != false) {
                                        requestPanel.clear();
                                        requestPanel.add(new Label("Request Sent"));
                                }

                        }

                        @Override public void onFailure(Method method, Throwable exception) {
                                AlertBox.show("failure");
                        }
                });
        }
}
