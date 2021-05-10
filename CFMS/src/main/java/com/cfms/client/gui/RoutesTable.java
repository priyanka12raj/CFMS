package com.cfms.client.gui;


import java.util.List;

import com.cfms.shared.pojo.PickupPoint;
import com.cfms.shared.pojo.Route;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.cfms.client.InfoService;

/**
 * Displays all the routes
 *
 * @author Priyanka, Samta, Chaitra
 */
public class RoutesTable extends Composite {
        /**
         * flextable to display the routes
         */
        FlexTable routeTable = new FlexTable();

        /**
         * initializes the route table
         */
        public RoutesTable() {
                initWidget(routeTable);
                getRoutesTableData();
        }

        /**
         * gets all the routes available
         */
        private void getRoutesTableData() {
                InfoService.Util.getService().getAllRoutes(new MethodCallback<List<Route>>() {

                        @Override public void onSuccess(Method method, List<Route> response) {

                                updateTable(response);
                        }

                        @Override public void onFailure(Method method, Throwable exception) {
                                routeTable.setText(1, 0, "Error could not connect to load data" + exception);
                        }
                });
        }

        /**
         * shows the list of routes in table
         *
         * @param result list of routes
         */
        private void updateTable(List<Route> result) {
                Route route;
                int row;
                /**
                 * set labels and format table
                 */
                routeTable.setBorderWidth(1);
                routeTable.setStyleName("flexth");
                routeTable.setText(0, 0, "Route No");
                routeTable.setText(0, 1, "Route Name");
                routeTable.setText(0, 2, "PickupPoints");
                routeTable.getRowFormatter().addStyleName(0, "route-table-header");
                routeTable.setBorderWidth(1);
                routeTable.setCellPadding(0);
                routeTable.setCellSpacing(0);

                for (int i = 0; i < result.size(); i++) {
                        route = result.get(i);
                        row = i + 1;
                        /**
                         * show the route in table
                         */
                        routeTable.setText(row, 0, String.valueOf(route.getRoute_no()));
                        routeTable.setText(row, 1, route.getRoute_name());
                        FlexTable pickupPointTable = new FlexTable();
                        List<PickupPoint> pickup_points = route.getPickupPoints();
                        for (int j = 0; j < pickup_points.size(); j++) {

                                pickupPointTable.setText(0, j, pickup_points.get(j).getLocation());
                        }
                        routeTable.setWidget(row, 2, pickupPointTable);
                }
        }

}
