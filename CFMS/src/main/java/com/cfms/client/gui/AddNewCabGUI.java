package com.cfms.client.gui;

import java.util.ArrayList;
import java.util.List;

import com.cfms.client.InfoService;
import com.cfms.client.gui.widgets.AlertBox;
import com.cfms.client.gui.widgets.TimeBox;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.IntegerBox;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.cfms.shared.pojo.Cab;
import com.cfms.shared.pojo.CabProviderBean;
import com.cfms.shared.pojo.PickupPoint;
import com.cfms.shared.pojo.PickupTime;
import com.cfms.shared.pojo.Route;

/**
 * A GUI class to Add New Cab
 *
 * @author Samta, Priyanka , Chaitra
 */

public class AddNewCabGUI extends Composite {


        /**
         * heading for adding a cab
         */
        Label headingCab = new Label();
        /**
         * heading for adding timings for the pickup-points
         */
        Label headingTime = new Label();
        /**
         * heading for adding a new provider
         */
        Label headingProvider = new Label();
        /**
         * main panel that holds remaining panels
         */
        HorizontalPanel hp;
        /**
         * panel on the left side for adding cab details
         */
        VerticalPanel vp1;
        /**
         * panel on the right side for adding timings for the pick-up points
         */
        VerticalPanel vp2;
        VerticalPanel vp3;
        /**
         * aligns the widgets for adding a new cab
         */
        FlexTable ft;
        /**
         * suggestion box suggests provider names
         */
        SuggestBox provider_names_text;
        /**
         * error label if server connection error
         */
        Label errLabel = new Label();
        /**
         * widget to get the start time from source and office
         *
         * @see TimeBox
         */
        TimeBox start_source, start_office;
        /**
         * boolean flag to decide if the route number entered is new
         */
        boolean isNewRoute;
        /**
         * integer variables to store number of seats, cost per day, route number
         */
        int seats, costperday, route1;
        /**
         * String variables to store provider name, start time from source and office
         */
        String providername, startsource, startoffice;
        //////////////////
        /**
         * List of the TimeBox widgets
         *
         * @see TimeBox
         */
        List<TimeBox> tb;
        /**
         * IntegerBox to get number of seats, cost per day and route number
         */
        IntegerBox no_seats, cost, route_no;
        /**
         * automatically generated cab number
         */
        int generated_cabnum;
        /**
         * list of PickupTime to add the information in the pickuptime table in the database
         *
         * @see PickupTime
         */
        List<PickupTime> pickuptime;
        /**
         * list of cab providers
         */
        List<CabProviderBean> list_provider_names;
        /**
         * to store list of pickup-points if the route number already exists
         */
        List<PickupPoint> listOfPickupTiming;
        /**
         * content panel
         */
        final Panel contentPanel;
        AddNewCabGUI gui;
        private boolean isFromDialog = false;
        private RequestDialog dialog = null;

        /**
         * constructor to initialize main panel widgets and loads the provider names
         *
         * @param contentPanel
         */
        public AddNewCabGUI(final Panel contentPanel) {
                this.contentPanel = contentPanel;
                this.gui = this;

                hp = new HorizontalPanel();
                vp1 = new VerticalPanel();
                vp2 = new VerticalPanel();
                vp3 = new VerticalPanel();
                ft = new FlexTable();
                headingCab.setStyleName("heading");
                headingTime.setStyleName("heading");
                vp1.add(headingCab);
                vp1.setSize("300px", "250px");
                hp.add(vp1);
                ft.setStyleName("flexth");
                ft.setBorderWidth(0);
                ft.setCellPadding(0);
                ft.setCellSpacing(0);

                initWidget(hp);
                LoadProvidersName();

        }

        public AddNewCabGUI(VerticalPanel contentPanel2, boolean isFromDialog2, RequestDialog requestDialog) {

                this(contentPanel2);
                this.isFromDialog = isFromDialog2;
                this.dialog = requestDialog;
        }

        /**
         * function to disable the text boxes of adding a new cab while adding a new provider
         */
        private void set_disable() {
                no_seats.setEnabled(false);
                cost.setEnabled(false);
                provider_names_text.setEnabled(false);
                route_no.setEnabled(false);
                start_source.setEnabled(false);
                start_office.setEnabled(false);
        }

        /**
         * function to enable the text boxes of adding a new cab when adding a new provider is done
         */

        public void set_enable() {
                no_seats.setEnabled(true);
                cost.setEnabled(true);
                provider_names_text.setEnabled(true);
                route_no.setEnabled(true);
                start_source.setEnabled(true);
                start_office.setEnabled(true);
        }

        /**
         * gets the list of the pickup-points for the route that already exists
         *
         * @param route for accessing the route number
         */

        private void getlist(Route route) {

                /**
                 * Requests server to get the pickup-points for the route provided
                 */

                InfoService.Util.getService().getpickuppoints(route, new MethodCallback<List<PickupPoint>>() {

                        @Override public void onFailure(Method method, Throwable exception) {
                                AlertBox.show(" failure " + exception.getMessage());

                        }

                        @Override public void onSuccess(Method method, List<PickupPoint> response) {

                                //if there are no pickup-points add the new route first and set isNewRoute=true

                                if (response == null) {
                                        vp2.clear();
                                        vp2.add(new AddNewRouteGUI(vp2, route_no));
                                        isNewRoute = true;
                                }

                                //if pickup-points exists make the list of pickup-points and set isNewRoute=false
                                else {
                                        isNewRoute = false;
                                        List<PickupPoint> list = response;
                                        listOfPickupTiming = response;
                                        makelist(list);

                                }


                        }
                });


        }

        public void makelist(List<PickupPoint> list) {

                //AlertBox.show("make list is called");
                ////////////////////////////////////////////////////////////
                FlexTable table = new FlexTable();
                tb = new ArrayList<TimeBox>();
                int i;
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


        public void insertTimes(List<PickupPoint> list1) {
                //		AlertBox.show("inside insertTimes() method size of list is"+ list1.size());
                pickuptime = new ArrayList<PickupTime>();
                PickupTime pt;
                List<String> time = new ArrayList<String>();
                for (int i = 0; i < list1.size(); i++) {
                        //time=new ArrayList<String>();
                        pt = new PickupTime();
                        time.add(tb.get(i).getTime());
                        //AlertBox.show(time.get(i));
                        pt.setPickup_id(listOfPickupTiming.get(i).getPickup_id());
                        pt.setCab_number(generated_cabnum);
                        pt.setApprox_time(time.get(i));
                        pickuptime.add(pt);
                        //AlertBox.show(pickuptime.get(i).getPickup_id()+" "+pickuptime.get(i).getCab_number()+" "+pickuptime.get(i).getApprox_time());
                }

                InfoService.Util.getService().addPickupTimings(pickuptime, new MethodCallback<Boolean>() {

                        @Override public void onFailure(Method method, Throwable exception) {
                                AlertBox.show("method call failed");
                                AlertBox.show(exception.getMessage() + exception.getStackTrace());

                        }

                        @Override public void onSuccess(Method method, Boolean response) {
                                //				AlertBox.show("success");

                        }
                });

        }

        public void addCab(Cab cab) {
                InfoService.Util.getService().addCab(cab, new MethodCallback<Cab>() {


                        @Override public void onSuccess(Method method, Cab response) {

                                //				AlertBox.show("new cab is added with cab number ="+response.getCab_no());
                                generated_cabnum = response.getCab_no();
                                //cabinserted=response;
                                if (!isNewRoute) {
                                        insertTimes(listOfPickupTiming);
                                }
                                if (isFromDialog) {
                                        dialog.hide();
                                        AlertBox.show(dialog.gui.updatableReq.getStatus());
                                        dialog.gui.updateRequestThenTable(dialog.gui.updatableReq, dialog.gui.updatableReq.getStatus());

                                } else {
                                        contentPanel.clear();
                                        contentPanel.add(new ViewAllCabs());
                                }
                        }

                        @Override public void onFailure(Method method, Throwable exception) {
                                //				AlertBox.show(" failure " +exception.getMessage()+exception.getStackTrace());

                        }
                });


        }

        public void LoadProvidersName() {
                InfoService.Util.getService().getAllProviders(new MethodCallback<List<CabProviderBean>>() {

                        @Override public void onFailure(Method method, Throwable exception) {
                                errLabel.setText("Could not connect to server");
                        }

                        @Override public void onSuccess(Method method, List<CabProviderBean> response) {

                                MultiWordSuggestOracle oracle = new MultiWordSuggestOracle();
                                if (response != null) {
                                        list_provider_names = response;
                                        for (int i = 0; i < response.size(); ++i) {
                                                oracle.add(response.get(i).getCompany_name());
                                        }

                                }
                                ProviderNamesPanel(oracle);
                        }
                });
        }

        public void ProviderNamesPanel(MultiWordSuggestOracle provider_names) {
                provider_names_text = new SuggestBox(provider_names);
                provider_names_text.setSize("150px", "25px");
                headingCab.setText("Add a Cab");

                ft.setText(0, 0, "No. of seats:");
                ft.setText(1, 0, "Cost per day:");
                ft.setText(2, 0, "Provider:");
                ft.setText(3, 0, "Route Number:");
                ft.setText(4, 0, "Start-time Soure:");
                ft.setText(5, 0, "Start-time office:");
                //ft.setPixelSize(80, 30);
                no_seats = new IntegerBox();
                no_seats.setSize("150px", "25px");
                cost = new IntegerBox();
                cost.setSize("150px", "25px");
                route_no = new IntegerBox();
                route_no.setSize("150px", "25px");
                start_source = new TimeBox();
                start_source.setSize("150px", "25px");
                start_office = new TimeBox();
                start_office.setSize("150px", "25px");


                ft.setWidget(0, 1, no_seats);
                ft.setWidget(1, 1, cost);
                ft.setWidget(2, 1, provider_names_text);
                ft.setWidget(3, 1, route_no);
                ft.setWidget(4, 1, start_source);
                ft.setWidget(5, 1, start_office);
                ft.setStyleName("addcab");
                ft.setBorderWidth(0);
                ft.setCellSpacing(2);
                Button addbtn = new Button("add ");
                ft.setWidget(6, 1, addbtn);
                vp1.add(ft);

                hp.add(vp1);
                hp.add(vp2);
                provider_names_text.addValueChangeHandler(new ValueChangeHandler<String>() {
                        @Override public void onValueChange(ValueChangeEvent<String> arg0) {

                                //							 AlertBox.show("inside value changed");
                                boolean flag = false;
                                providername = provider_names_text.getText();
                                //							 AlertBox.show("provider= "+ providername);
                                //							 AlertBox.show(list_provider_names.size()+"");
                                for (int i = 0; i < list_provider_names.size(); i++) {


                                        //								AlertBox.show(list_provider_names.get(i).getCompany_name()+"");
                                        if (list_provider_names.get(i).getCompany_name().contains(providername)) {
                                                flag = true;
                                                break;
                                        }


                                }

                                if (flag == false) {
                                        vp2.clear();
                                        headingProvider.setText("Add Provider details");
                                        vp2.add(headingProvider);
                                        vp2.add(new AddProviderWithCabGUI(contentPanel, gui));
                                        set_disable();

                                }


                        }
                });
                addbtn.addClickHandler(new ClickHandler() {

                        @Override public void onClick(ClickEvent event) {

                                seats = no_seats.getValue();
                                costperday = cost.getValue();
                                providername = provider_names_text.getText();
                                route1 = route_no.getValue();
                                startsource = start_source.getTime();
                                startoffice = start_office.getTime();
                                if (no_seats.getText().isEmpty() || cost.getText().isEmpty() || provider_names_text.getText().isEmpty() || route_no.getText()
                                        .isEmpty() || !start_source.isInFormat || !start_office.isInFormat) {
                                        AlertBox.show(" All fields are required");
                                } else {
                                        Cab newcab = new Cab();
                                        newcab.setNo_of_seats(seats);
                                        newcab.setCost_per_month(costperday);
                                        newcab.setProvider(providername);
                                        newcab.setRoute_no(route1);
                                        newcab.setStart_time_source(startsource);
                                        newcab.setStart_time_NC(startoffice);
                                        addCab(newcab);
                                }

                        }
                });

                route_no.addChangeHandler(new ChangeHandler() {

                        @Override public void onChange(ChangeEvent event) {
                                vp2.clear();
                                Route route = new Route();  // sending route object with route number
                                route.setRoute_no(route_no.getValue());
                                getlist(route);

                        }
                });

                start_source.addChangeHandler(new ChangeHandler() {

                        @Override public void onChange(ChangeEvent arg0) {
                                startsource = start_source.getTime();
                                tb.get(0).setTime(startsource);
                                tb.get(0).setEnabled(false);


                        }
                });


        }

}
