package com.cfms.client.gui;

import java.util.List;

import com.cfms.client.gui.widgets.AlertBox;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Cookies;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.cfms.client.InfoService;
import com.cfms.shared.pojo.Cab;
import com.cfms.shared.pojo.CabBooker;
import com.cfms.shared.pojo.CabUser;
import com.cfms.shared.pojo.Employee;
import com.cfms.shared.pojo.PickupPoint;
import com.cfms.shared.pojo.CabPickupTime;

/**
 * Gui class allows to search cabs and book them.
 *
 * @author Priyanka, Samta, Chaitra
 */
public class BookCabGUI extends Composite {
        /**
         * Contains all panels
         */
        HorizontalPanel mainPanel = new HorizontalPanel();
        /**
         * shows request cab button
         */
        VerticalPanel displayPanel = new VerticalPanel();
        /**
         * asks for confirmation for cab booking
         */
        VerticalPanel confirmPanel = new VerticalPanel();
        /**
         * shows cab details
         */
        VerticalPanel tablePanel = new VerticalPanel();
        /**
         * request panel
         */
        VerticalPanel reqPanel = new VerticalPanel();
        Label errLabel = new Label();
        Label reqLabel = new Label();
        Label tableHeader;
        FlexTable cabTable;
        /**
         * Pickpup point suggestion box
         */
        SuggestBox pickup_text;
        Label pickup_point_lb;
        Button reqBtn;
        Panel contentPanel;

        /**
         * Creates the composite with widgets to search and book cab
         *
         * @param contentPanel content panel
         */
        public BookCabGUI(Panel contentPanel) {
                this.contentPanel = contentPanel;
                initWidget(mainPanel);
                mainPanel.setSpacing(2);
                LoadPickupPoints();

        }

        public void addBookCabPanel(MultiWordSuggestOracle pickup_points) {

                cabTable = new FlexTable();
                pickup_text = new SuggestBox(pickup_points);
                pickup_point_lb = new Label("Pickup Point");
                Button searchBtn = new Button("Search Cabs");
                HorizontalPanel pickupPanel = new HorizontalPanel();
                pickupPanel.add(pickup_point_lb);
                pickupPanel.add(pickup_text);
                pickupPanel.add(searchBtn);
                pickupPanel.setSpacing(1);

                reqBtn = new Button("Request new cab/route");
                reqBtn.addClickHandler(new ClickHandler() {

                        @Override public void onClick(ClickEvent arg0) {

                                contentPanel.clear();
                                contentPanel.add(new RequestCabGUI(pickup_text.getText()));

                        }
                });
                tableHeader = new Label();
                displayPanel.setSpacing(2);
                displayPanel.add(errLabel);
                displayPanel.add(pickupPanel);

                tablePanel.add(tableHeader);
                tablePanel.add(cabTable);
                tablePanel.setVisible(false);
                tablePanel.setSpacing(1);
                displayPanel.add(tablePanel);

                reqPanel.add(reqLabel);
                reqPanel.add(reqBtn);
                reqPanel.setSpacing(1);
                reqPanel.setVisible(false);

                displayPanel.add(reqPanel);

                mainPanel.add(displayPanel);
                mainPanel.add(confirmPanel);
                searchBtn.addClickHandler(new ClickHandler() {

                        @Override public void onClick(ClickEvent event) {
                                if (pickup_text.getText().trim() != "") {
                                        confirmPanel.clear();
                                        PickupPoint p = new PickupPoint();
                                        p.setLocation(pickup_text.getText().trim());
                                        getCabsForLocation(p);
                                }
                        }
                });

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
                                errLabel.setText("Could not connect to server");
                        }

                        @Override public void onSuccess(Method method, List<PickupPoint> response) {
                                CookieBeans.pickup_point_list = response;
                                MultiWordSuggestOracle oracle = new MultiWordSuggestOracle();
                                if (response != null) {
                                        for (int i = 0; i < response.size(); ++i) {
                                                oracle.add(response.get(i).getLocation());
                                        }

                                }
                                addBookCabPanel(oracle);
                        }
                });
        }

        /**
         * Books cab for emp from pickup_point
         *
         * @param emp          employee who wants to book cab
         * @param cab          cab to be booked
         * @param pickup_point pickup point from where the cab is boarded
         */
        protected void bookCab(Employee emp, Cab cab, PickupPoint pickup_point) {
                /**
                 * prepare cab booker object with the details
                 */
                CabBooker cabBook = new CabBooker();
                cabBook.setCab_no(cab.getCab_no());
                cabBook.setEmployee_id(emp.getEid());
                cabBook.setPickup_id(pickup_point.getPickup_id());
                /**
                 * Request server to book cab
                 */
                InfoService.Util.getService().bookCab(cabBook, new MethodCallback<CabUser>() {

                        @Override public void onFailure(Method method, Throwable exception) {
                                AlertBox.show("failure");
                        }

                        @Override public void onSuccess(Method method, CabUser response) {
                                AlertBox.show("book cab successful");
                                updateCabUserDetails(CookieBeans.employee);
                        }
                });
        }

        /**
         * get cabs from given location
         *
         * @param location
         */
        private void getCabsForLocation(PickupPoint location) {
                InfoService.Util.getService().getPickupPoint(location, new MethodCallback<PickupPoint>() {

                        @Override public void onSuccess(Method method, PickupPoint response) {

                                if (response == null) {
                                        reqLabel.setText("Pickup point not available request new route");
                                        reqPanel.setVisible(true);
                                        tablePanel.setVisible(false);
                                } else
                                        getCabPickup(response);
                        }

                        @Override public void onFailure(Method method, Throwable exception) {

                        }
                });

        }

        /**
         * get available cabs from the given pickup point
         *
         * @param p pickup point
         */
        private void getCabPickup(final PickupPoint p) {
                InfoService.Util.getService().getCabsforPickupPoint(p, new MethodCallback<List<CabPickupTime>>() {

                        @Override public void onFailure(Method method, Throwable exception) {
                                reqLabel.setText("Could not retrieve Cabs for " + pickup_text.getText().trim());
                                reqPanel.setVisible(true);
                                tablePanel.setVisible(false);
                        }

                        @Override public void onSuccess(Method method, List<CabPickupTime> response) {
                                if (response != null && response.size() > 0) {
                                        tablePanel.setVisible(true);
                                        reqPanel.setVisible(false);
                                        cabTable.setBorderWidth(1);
                                        tableHeader.setText("Cabs for " + pickup_text.getText().trim());
                                        cabTable.setText(0, 0, "Cab timings");
                                        cabTable.setText(0, 1, "Cab no");
                                        cabTable.setText(0, 2, "Route No");
                                        cabTable.setText(0, 3, "Cost/day");
                                        cabTable.setText(0, 4, "Available seats");
                                        cabTable.setText(0, 5, "total seats");
                                        cabTable.setText(0, 6, "");
                                        cabTable.getRowFormatter().setStyleName(0, "table-header");
                                        updateCabTable(response);
                                } else {
                                        reqLabel.setText("No Cabs available for " + pickup_text.getText().trim());
                                        reqPanel.setVisible(true);
                                        tablePanel.setVisible(false);
                                }
                        }
                });
        }

        /**
         * displays table with the retrieved cab details
         *
         * @param cptList
         */
        private void updateCabTable(List<CabPickupTime> cptList) {
                int row;
                Button bt;
                for (int i = 0; i < cptList.size(); i++) {
                        final CabPickupTime cpt;
                        row = i + 1;
                        cpt = cptList.get(i);
                        cabTable.setText(row, 0, cpt.getPickup_time());
                        cabTable.setText(row, 1, String.valueOf(cpt.getCab().getCab_no()));
                        cabTable.setText(row, 2, String.valueOf(cpt.getCab().getRoute_no()));
                        cabTable.setText(row, 3, String.valueOf(cpt.getCab().getCost_per_month()));
                        cabTable.setText(row, 4, String.valueOf(cpt.getCab().getAvail_seats()));
                        cabTable.setText(row, 5, String.valueOf(cpt.getCab().getNo_of_seats()));
                        final Cab cab = cpt.getCab();
                        bt = new Button("Avail Cab");
                        /**
                         * if available seats is 0 dont allow user to book the cab
                         */
                        if (cpt.getCab().getAvail_seats() == 0)
                                bt.setEnabled(false);

                        bt.addClickHandler(new ClickHandler() {
                                @Override public void onClick(ClickEvent event) {
                                        confirmPanel.setStyleName("confirm-panel");
                                        confirmPanel.add(new CabDetailsGUI(cab));
                                        confirmPanel.setStyleName("cab-panel");
                                        Button bt = new Button("Book Cab");
                                        bt.addClickHandler(new ClickHandler() {

                                                @Override public void onClick(ClickEvent arg0) {
                                                        Employee employee = new Employee();
                                                        employee.setEid(Cookies.getCookie("eid"));
                                                        bookCab(employee, cab, cpt.getPickup_point());
                                                }
                                        });
                                        confirmPanel.add(bt);
                                }
                        });
                        Button lv_bt = new Button();
                        lv_bt.addClickHandler(new ClickHandler() {

                                @Override public void onClick(ClickEvent event) {
                                        confirmPanel.clear();
                                        confirmPanel.add(new LeaveCabGUI());
                                }
                        });
                        cabTable.setWidget(row, 6, bt);
                }
        }

        /**
         * updates the cab user details
         *
         * @param emp cab user
         */
        public void updateCabUserDetails(Employee emp) {
                InfoService.Util.getService().getCabUser(emp, new MethodCallback<CabUser>() {

                        @Override public void onFailure(Method method, Throwable exception) {
                                CookieBeans.setNotCabUser();
                                RootLayoutPanel.get().remove(0);
                                RootLayoutPanel.get().add(new IndexGUI());
                        }

                        @Override public void onSuccess(Method method, CabUser response) {
                                CookieBeans.setCabUser(response);
                                RootLayoutPanel.get().remove(0);
                                RootLayoutPanel.get().add(new IndexGUI());
                        }
                });
        }
}
