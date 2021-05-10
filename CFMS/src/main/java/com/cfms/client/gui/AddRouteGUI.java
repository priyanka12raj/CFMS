package com.cfms.client.gui;


import java.util.ArrayList;
import java.util.List;

import com.cfms.client.gui.widgets.AlertBox;
import com.cfms.shared.pojo.PickupPoint;
import com.cfms.shared.pojo.Route;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.TextBox;
import com.cfms.client.InfoService;

/**
 * A GUI class to Add Route
 *
 * @author Priyanka, Samta, Chaitra
 */
public class AddRouteGUI extends Composite {

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

        /**
         * Constructor initializes mainPanel widgets and loads pickup points
         */
        public AddRouteGUI() {
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
                                //add route only if route name and pickup_list is not empty
                                if (!route_nameTxt.getText().isEmpty() || !pickup_list.isEmpty()) {
                                        Route route = new Route();
                                        route.setRoute_name(route_nameTxt.getText());
                                        route.setPickupPoints(pickup_list);
                                        /*
                                         * Request server to add route
                                         */
                                        InfoService.Util.getService().addRoute(route, new MethodCallback<Route>() {
                                                @Override public void onSuccess(Method method, Route response) {
                                                        route_noTxt.setText(String.valueOf(response.getRoute_no()));//set route no
                                                        view_Added();
                                                }

                                                @Override public void onFailure(Method method, Throwable exception) {
                                                        AlertBox.show("error");
                                                }
                                        });

                                } else {
                                        //show error message
                                        AlertBox.show("Please provide route name and atleast one pickup point");
                                }
                        }
                });
                /*
                 * adding clickhandler to add another route button
                 *
                 */
                addAgainBtn.addClickHandler(new ClickHandler() {

                        @Override public void onClick(ClickEvent arg0) {
                                //clear the list
                                pickup_list.clear();
                                //clear the flextable
                                pickup_points.removeAllRows();
                                enable_add_route();
                                //clear the textboxes
                                route_noTxt.setText("");
                                route_nameTxt.setText("");
                                pickup_suggestBox.setText("");
                        }
                });

                enable_add_route();
                mainPanel.add(routeT);
                mainPanel.add(pickup_points);
        }

        /**
         * Shows the added routes and disables all the textboxes
         */
        private void view_Added() {
                route_noTxt.setVisible(true);
                route_no.setVisible(true);
                route_noTxt.setEnabled(false);
                route_nameTxt.setEnabled(false);
                pickup_suggestBox.setVisible(false);
                addPickup.setVisible(false);
                routeT.setWidget(4, 1, addAgainBtn);
        }

        /**
         * Enables all the textboxes to add Route
         */
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