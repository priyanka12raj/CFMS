package com.cfms.client.gui;

import java.util.List;

import com.cfms.client.InfoService;
import com.cfms.client.gui.widgets.AlertBox;
import com.cfms.client.gui.widgets.TimeBox;
import com.cfms.shared.pojo.Cab;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DoubleBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.IntegerBox;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Shows all the available cabs.
 * Also allows the admin to update cab details
 *
 * @author Samta, Priyanka, Chaitra
 */
public class ViewAllCabs extends Composite {

        FlexTable cabstable;
        int Cab_Number;
        Button change, update;
        HorizontalPanel hp;
        VerticalPanel vp1;
        VerticalPanel vp2;
        //int row;
        Cab c = new Cab();
        Double NewCost;
        String NewTimeSource, NewTimeNc;
        Label headingCab = new Label();

        public ViewAllCabs() {
                hp = new HorizontalPanel();
                vp1 = new VerticalPanel();
                vp2 = new VerticalPanel();
                cabstable = new FlexTable();

                cabstable.setText(0, 0, "Cab no.");
                cabstable.setText(0, 1, "No of seats");
                cabstable.setText(0, 2, "cost per day");
                cabstable.setText(0, 3, "provider of cab");
                cabstable.setText(0, 4, "Route no.");
                cabstable.setText(0, 5, "start time from source");
                cabstable.setText(0, 6, "start time from office");
                cabstable.setText(0, 7, "update");
                //cabstable.setText(0, 8, "remove");
                cabstable.getRowFormatter().addStyleName(0, "route-table-header");
                cabstable.setStyleName("flexth");
                cabstable.setBorderWidth(4);

                //cabstable.setCellSpacing(5);
                headingCab.setStyleName("heading");
                headingCab.setText("Cabs");
                vp1.add(headingCab);
                vp1.add(cabstable);
                hp.add(vp1);
                hp.add(vp2);
                initWidget(hp);
                viewallcabs();

        }

        public void viewallcabs() {
                InfoService.Util.getService().viewcabs(new MethodCallback<List<Cab>>() {

                        @Override public void onSuccess(Method method, List<Cab> response) {

                                if (response == null) {
                                        AlertBox.show("no cabs available");
                                } else

                                        showallcabs(response);
                        }

                        @Override public void onFailure(Method method, Throwable exception) {

                                AlertBox.show("failure");
                        }
                });
        }


        public void showallcabs(List<Cab> cabs) {
                for (int i = 0; i < cabs.size(); i++) {
                        final int row;
                        row = i + 1;
                        c = cabs.get(i);

                        final IntegerBox cab_number = new IntegerBox();
                        cab_number.setPixelSize(25, 25);
                        cab_number.setEnabled(false);

                        final IntegerBox seats = new IntegerBox();
                        seats.setPixelSize(25, 25);
                        seats.setEnabled(false);

                        final IntegerBox route = new IntegerBox();
                        route.setPixelSize(25, 25);
                        route.setEnabled(false);

                        final DoubleBox costperday = new DoubleBox();
                        costperday.setPixelSize(55, 25);
                        costperday.setEnabled(false);

                        final TextBox provider = new TextBox();
                        provider.setPixelSize(115, 25);
                        provider.setEnabled(false);

                        final TimeBox start_source = new TimeBox();
                        start_source.setPixelSize(65, 25);
                        start_source.setEnabled(false);

                        final TimeBox start_office = new TimeBox();
                        start_office.setPixelSize(65, 25);
                        start_office.setEnabled(false);

                        final Button change = new Button(" Change");

                        final Button update = new Button("Update ");
                        ////////////////////////////////////////////////
                        cabstable.setWidget(row, 0, cab_number);
                        cab_number.setValue(c.getCab_no());
                        cabstable.setWidget(row, 1, seats);
                        seats.setValue(c.getNo_of_seats());
                        cabstable.setWidget(row, 2, costperday);
                        costperday.setValue(c.getCost_per_month());
                        cabstable.setWidget(row, 3, provider);
                        provider.setText(c.getProvider());
                        cabstable.setWidget(row, 4, route);
                        route.setValue(c.getRoute_no());
                        cabstable.setWidget(row, 5, start_source);
                        start_source.setTime(c.getStart_time_source());
                        cabstable.setWidget(row, 6, start_office);
                        start_office.setTime(c.getStart_time_NC());


                        cabstable.setWidget(row, 7, change);
                        change.setVisible(true);


                        change.addClickHandler(new ClickHandler() {

                                @Override public void onClick(ClickEvent e) {

                                        change.setVisible(false);
                                        cabstable.setWidget(row, 7, update);
                                        update.setVisible(true);

                                        costperday.setEnabled(true);
                                        start_source.setEnabled(true);
                                        start_office.setEnabled(true);


                                }
                        });

                        update.addClickHandler(new ClickHandler() {

                                @Override public void onClick(ClickEvent event) {

                                        Double newcost = costperday.getValue();
                                        String newstart_source = start_source.getTime();
                                        String newstart_office = start_office.getTime();
                                        int cabnumber = cab_number.getValue();
                                        updatecab(cabnumber, newcost, newstart_source, newstart_office);

                                        costperday.setEnabled(false);
                                        start_source.setEnabled(false);
                                        start_office.setEnabled(false);
                                        update.setVisible(false);
                                        cabstable.setWidget(row, 7, change);
                                        change.setVisible(true);


                                }
                        });

                }


        }

        protected void updatecab(int cabnumber, Double newcost, String newstart_source, String newstart_office) {
                //AlertBox.show("update method called");
                Cab newcab = new Cab();
                newcab.setCab_no(cabnumber);
                newcab.setCost_per_month(newcost);
                newcab.setStart_time_source(newstart_source);
                newcab.setStart_time_NC(newstart_office);

                InfoService.Util.getService().update_cab(newcab, new MethodCallback<Cab>() {

                        @Override public void onFailure(Method method, Throwable exception) {
                                AlertBox.show("failure");

                        }

                        @Override public void onSuccess(Method method, Cab response) {

                                //AlertBox.show(" cab with cab number "+ response.getCab_no()+ "is updated \n cost="+ response.getCost_per_month()+"\n start time source ="+response.getStart_time_source()+"\n"+response.getStart_time_NC());
                                NewCost = response.getCost_per_month();
                                NewTimeSource = response.getStart_time_source();
                                NewTimeNc = response.getStart_time_source();
                                //AlertBox.show(" cab with cab number "+ response.getCab_no()+ "is updated \n cost="+ NewCost+"\n start time source ="+NewTimeSource+"\n"+NewTimeNc);


                        }
                });
        }

}
