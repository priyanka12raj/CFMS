package com.cfms.client.gui;


import java.util.ArrayList;
import java.util.List;

import com.cfms.client.InfoService;
import com.cfms.client.gui.widgets.AlertBox;
import com.cfms.client.gui.widgets.TimeBox;
import com.cfms.shared.pojo.PickupPoint;
import com.cfms.shared.pojo.Route;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.IntegerBox;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * A GUI class to Add New Route
 *
 * @author Priyanka, Samta, Chaitra
 */
public class AddNewRouteGUI extends Composite {
        /**
         * container panel for all other panels
         */
        Panel mainPanel;
        /**
         * aligns the widgets for adding route
         */
        FlexTable routeT;
        /**
         * input textboxes for route no & route name
         */
        TextBox route_noTxt, route_nameTxt;
        /**
         * displays the route no after route has been added
         */
        Label route_no;
        /**
         * suggestion box suggests pickup points
         */
        SuggestBox pickup_suggestBox;
        /**
         * flextable contains the added pickup points
         */
        FlexTable pickup_points;
        /**
         * Buttons to add pickpoints and route
         */
        Button addPickup, addRouteBtn, addAgainBtn;
        /**
         * List of pickup points added to the route
         */
        List<PickupPoint> pickup_list = new ArrayList<PickupPoint>();
        PickupPoint p;
        /**
         * count of no. of pickup points added
         */
        int pickup_count = 0;


        List<PickupPoint> listOfPickupTiming;
        VerticalPanel vp2;
        IntegerBox rno;
        List<TimeBox> tb;

        /**
         * constructor to add new route
         *
         * @param vp2     panel to write the output
         * @param rout_no textbox to set the route
         */
        public AddNewRouteGUI(VerticalPanel vp2, IntegerBox rout_no) {
                this.rno = rout_no;
                this.vp2 = vp2;
                mainPanel = new HorizontalPanel();
                initWidget(mainPanel);
                LoadPickupPoints();
        }

        /**
         * Adds all the widgets to the mainPanel
         *
         * @param suggest_pickup_points list of suggestions for creating suggestion box
         */
        public void addRoutePanel(MultiWordSuggestOracle suggest_pickup_points) {
                /*
                 * Initializing the widgets
                 */
                routeT = new FlexTable();
                route_no = new Label("Route No");
                route_noTxt = new TextBox();
                route_nameTxt = new TextBox();
                pickup_points = new FlexTable();
                addPickup = new Button("Add Pickup Point");
                addPickup.setStyleName("blue");
                pickup_suggestBox = new SuggestBox(suggest_pickup_points);

                addRouteBtn = new Button("Submit");
                addRouteBtn.setStyleName("blue");
                addAgainBtn = new Button("Add Another Route");
                addAgainBtn.setStyleName("blue");

                /*
                 * Assigning the widgets/Text to the FlexTable
                 */
                routeT.setWidget(0, 0, route_no);
                routeT.setText(1, 0, "Route Name");
                routeT.setText(2, 0, "Pickup Points");
                routeT.setWidget(0, 1, route_noTxt);
                routeT.setWidget(1, 1, route_nameTxt);
                routeT.setWidget(2, 1, pickup_suggestBox);
                routeT.setWidget(2, 2, addPickup);

                /*
                 * Adding click handlers to button
                 */
                addPickup.addClickHandler(new ClickHandler() {
                        @Override public void onClick(ClickEvent arg0) {
                                /*
                                 * Add pickuppoint selected to the pickup_points flextable
                                 */
                                pickup_points.setText(pickup_count, 0, String.valueOf(pickup_count + 1));
                                pickup_points.setText(pickup_count, 1, pickup_suggestBox.getText());
                                /*
                                 * Create pickup point with the selected pickup point location
                                 */
                                p = new PickupPoint();
                                p.setLocation(pickup_suggestBox.getText());
                                /*
                                 * add the pickup point to list
                                 */
                                pickup_list.add(p);

                                pickup_suggestBox.setText("");
                                pickup_suggestBox.setFocus(true);
                                pickup_count++; //increment the count of pickup points in route
                        }
                });
                /*
                 * adding click handler to addRouteBtn
                 * adds the route and displays the route no.
                 *
                 */

                addRouteBtn.addClickHandler(new ClickHandler() {

                        @Override public void onClick(ClickEvent arg0) {
                                final Route route = new Route();
                                //	AlertBox.show("route btn pressed"+pickup_list.size());
                                route.setRoute_name(route_nameTxt.getText());
                                route.setPickupPoints(pickup_list);
                                InfoService.Util.getService().addRoute(route, new MethodCallback<Route>() {
                                        @Override public void onSuccess(Method method, Route response) {
                                                //AlertBox.show("route Added");
                                                rno.setValue(response.getRoute_no());

                                                getlist(response);
                                                //view_Added();
                                        }


                                        @Override public void onFailure(Method method, Throwable exception) {
                                                AlertBox.show("error");
                                        }
                                });
                                //AlertBox.show("Sending request");
                        }
                });

                enable_add_route();
                mainPanel.add(routeT);
                mainPanel.add(pickup_points);

        }

        private void getlist(final Route route) {

                AlertBox.show(" inside getlist method");
                InfoService.Util.getService().getpickuppoints(route, new MethodCallback<List<PickupPoint>>() {

                        @Override public void onFailure(Method method, Throwable exception) {
                                AlertBox.show(" failure " + exception.getMessage());

                        }

                        @Override public void onSuccess(Method method, List<PickupPoint> response) {

                                //AlertBox.show("size="+response.size());
                                List<PickupPoint> list = response;
                                listOfPickupTiming = response;

                                vp2.clear();

                                makelist(list);

                        }
                });


        }

        public void makelist(List<PickupPoint> list) {

                //AlertBox.show("make list is called");
                ////////////////////////////////////////////////////////////
                FlexTable table = new FlexTable();
                tb = new ArrayList<TimeBox>();
                int i;
                HTML headingTime = new HTML();
                headingTime.setText("Set Time For Pickup Points");
                for (i = 0; i < list.size(); i++) {

                        // AlertBox.show("inside the loop");
                        TimeBox time_tb = new TimeBox();
                        tb.add(time_tb);
                        table.setText(i, 0, list.get(i).getLocation());
                        table.setWidget(i, 1, tb.get(i));

                }

                table.addStyleName("addtiming");
                vp2.setSpacing(10);
                vp2.add(headingTime);
                vp2.add(table);


        }


        private void enable_add_route() {
                route_no.setVisible(false);
                route_noTxt.setVisible(false);
                route_noTxt.setEnabled(true);
                route_nameTxt.setEnabled(true);
                pickup_suggestBox.setVisible(true);
                addPickup.setVisible(true);
                routeT.setWidget(4, 1, addRouteBtn);
        }

        /**
         * Loads all the pickup points
         */
        private void LoadPickupPoints() {
                /**
                 * Request to server and get all pickup points
                 */
                InfoService.Util.getService().getAllPickupPoints(new MethodCallback<List<PickupPoint>>() {

                        @Override public void onFailure(Method method, Throwable exception) {
                                AlertBox.show("Error");
                        }

                        @Override public void onSuccess(Method method, List<PickupPoint> response) {
                                CookieBeans.pickup_point_list = response;
                                /**
                                 * create suggest list from the response
                                 */
                                MultiWordSuggestOracle oracle = new MultiWordSuggestOracle();
                                if (response != null) {
                                        for (int i = 0; i < response.size(); ++i) {
                                                oracle.add(response.get(i).getLocation());
                                        }

                                }
                                addRoutePanel(oracle);
                        }
                });
        }
}