package com.cfms.client.gui;

import java.util.List;

import com.cfms.shared.pojo.Employee;
import com.cfms.shared.pojo.TravelHistory;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;

import com.google.gwt.core.shared.GWT;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.cfms.client.InfoService;
import com.cfms.client.gui.widgets.AlertBox;

/**
 * displays the travel history of the employee
 *
 * @author Samta, Priyanka, Chaitra
 */
public class TravelHistoryTable extends Composite {
        FlexTable th;

        public TravelHistoryTable() {
                th = new FlexTable();
                initWidget(th);
                th.setText(0, 0, "Employee id");
                th.setText(0, 1, "cab no");
                th.setText(0, 2, "pickup point no.");
                th.setText(0, 3, "Period of Travel");
                //th.setText(0, 4, "end date");
                th.getRowFormatter().addStyleName(0, "th_header");
                th.setStyleName("flexth");
                th.setCellPadding(0);
                th.setBorderWidth(1);
                th.setCellSpacing(0);
                th.getRowFormatter().addStyleName(0, "route-table-header");

                getTravelHistory();

        }

        /**
         * get the employees travel history
         */
        private void getTravelHistory() {
                Employee emp = CookieBeans.employee;
                InfoService.Util.getService().get_travelhistory(emp, new MethodCallback<List<TravelHistory>>() {

                        @Override public void onFailure(Method method, Throwable exception) {

                                GWT.log(exception.getMessage());
                                AlertBox.show(" failure " + exception.getMessage() + exception.getStackTrace());
                                System.out.println(" EXCEPTION ::" + exception.getMessage() + exception.getStackTrace());

                        }

                        @Override public void onSuccess(Method method, List<TravelHistory> response) {
                                if (response == null || response.size() == 0)
                                        th.setText(1, 0, "No travel history");
                                else
                                        updateTHtable(response);

                        }


                });
        }

        /**
         * update the travel history table
         *
         * @param travelList
         */
        protected void updateTHtable(List<TravelHistory> travelList) {
                TravelHistory travel;
                int row;
                for (int i = 0; i < travelList.size(); i++) {
                        row = i + 1;
                        travel = travelList.get(i);
                        th.setText(row, 0, travel.getEmp().getEid());
                        th.setText(row, 1, String.valueOf(travel.getCab_no()));
                        th.setText(row, 2, String.valueOf(travel.getP_no()));
                        th.setText(row, 3, travel.getStart_date() + " to " + travel.getEnd_till());
                        //th.setText(row,4 , travel.getEnd_till());
                }
        }
}

