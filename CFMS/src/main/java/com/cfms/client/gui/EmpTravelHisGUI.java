package com.cfms.client.gui;

import java.util.List;

import com.cfms.client.gui.widgets.AlertBox;
import com.cfms.shared.pojo.TravelHistory;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.IntegerBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.cfms.client.InfoService;

/**
 * Shows the travel history of all employees with filters for employee and cab
 *
 * @author Samta, Priyanka, Chaitra
 */
public class EmpTravelHisGUI extends Composite {

        VerticalPanel mainpanel;
        HorizontalPanel filterpanel, contentpanel;
        VerticalPanel empidfilter, cabnumfilter;

        HorizontalPanel hp;
        VerticalPanel vp1;
        VerticalPanel vp2;
        FlexTable ft;
        FlexTable ft2;
        FlexTable ft3;
        TextBox eid;
        IntegerBox cab_no;
        String Eid;
        int cabnumber;
        List<TravelHistory> AlltravelList;
        Button search;

        /**
         * loads all the travel history
         */
        public EmpTravelHisGUI() {
                mainpanel = new VerticalPanel();
                contentpanel = new HorizontalPanel();
                filterpanel = new HorizontalPanel();
                empidfilter = new VerticalPanel();
                cabnumfilter = new VerticalPanel();

                eid = new TextBox();
                cab_no = new IntegerBox();
                search = new Button("search");
                ft = new FlexTable();
                ft2 = new FlexTable();
                ft3 = new FlexTable();
                ft3.setText(0, 0, "Cab Number:");
                ft3.setWidget(0, 1, cab_no);

                initWidget(mainpanel);
                contentpanel.add(ft);
                empidfilter.add(ft2);
                cabnumfilter.add(ft3);
                filterpanel.add(empidfilter);
                filterpanel.add(cabnumfilter);
                filterpanel.add(search);
                contentpanel.setSpacing(50);
                mainpanel.add(filterpanel);
                mainpanel.add(contentpanel);
                ft.setText(0, 0, "Employee Id");
                ft.setText(0, 1, " Name");
                ft.setText(0, 2, "Email");
                ft.setText(0, 3, "Address");
                ft.setText(0, 4, "Phone No.");
                ft.setText(0, 5, "cab no");
                ft.setText(0, 6, "pickup point no.");
                ft.setText(0, 7, "Period of Travel");
                //	ft.setText(0, 8, "end date");
                //ft.getRowFormatter().addStyleName(0, "th_header");
                ft.setStyleName("flexth");
                ft.setCellPadding(0);
                ft.setBorderWidth(1);
                ft.setCellSpacing(0);
                ft.getRowFormatter().addStyleName(0, "route-table-header");
                getTH();


        }

        private void getTH() {

                InfoService.Util.getService().get_emptravelhistory(new MethodCallback<List<TravelHistory>>() {

                        @Override public void onFailure(Method method, Throwable exception) {

                                GWT.log(exception.getMessage());
                                AlertBox.show(" failure " + exception);
                        }

                        @Override public void onSuccess(Method method, List<TravelHistory> response) {
                                if (response == null)
                                        ft.setText(1, 0, "No travel history");
                                else
                                        updateTHtable(response);

                        }


                });
        }

        protected void updateTHtable(List<TravelHistory> travelList) {

                TravelHistory travel;
                int row;
                for (int i = 0; i < travelList.size(); i++) {
                        row = i + 1;
                        travel = travelList.get(i);
                        ft.setText(row, 0, travel.getEmp().getEid());
                        ft.setText(row, 1, travel.getEmp().getEname());
                        ft.setText(row, 2, travel.getEmp().getEmail());
                        ft.setText(row, 3, travel.getEmp().getAddress());
                        ft.setText(row, 4, travel.getEmp().getPhone());
                        ft.setText(row, 5, String.valueOf(travel.getCab_no()));
                        ft.setText(row, 6, String.valueOf(travel.getP_no()));
                        ft.setText(row, 7, travel.getStart_date() + " to " + travel.getEnd_till());
                        //	ft.setText(row, 8 , travel.getEnd_till());

                }

                ft.setStyleName("flexth");
                ft.getRowFormatter().setStyleName(0, "table-header");
                getEmpid(travelList);

        }


        public void getEmpid(List<TravelHistory> travelList) {
                AlltravelList = travelList;
                ft2.setText(0, 0, "Employee Id:");
                ft2.setWidget(0, 1, eid);


                search.addClickHandler(new ClickHandler() {

                        @Override public void onClick(ClickEvent event) {


                                if (!eid.getText().isEmpty() && !cab_no.getText().isEmpty()) {
                                        Eid = eid.getText();
                                        cabnumber = cab_no.getValue();

                                        checkexistsemp_cab(Eid, cabnumber, AlltravelList);
                                } else if (!eid.getText().isEmpty()) {
                                        Eid = eid.getText();

                                        checkexists(Eid, AlltravelList);
                                } else if (!cab_no.getText().isEmpty()) {
                                        cabnumber = cab_no.getValue();

                                        checkexistscab(cabnumber, AlltravelList);
                                } else {
                                        AlertBox.show("please give some filter");
                                }

                        }
                });
        }

        protected void checkexistscab(int cabnum, List<TravelHistory> AlltravelList) {

                int i = 0;
                int exists = 0;
                TravelHistory travel;
                for (i = 0; i < AlltravelList.size(); i++) {
                        travel = AlltravelList.get(i);
                        if (cabnumber == travel.getCab_no())
                                exists = 1;
                }
                if (exists == 1)
                        displayheadercab();
                else {
                        AlertBox.show("user not exists ");
                }
        }

        protected void checkexistsemp_cab(String empid, int cabnum, List<TravelHistory> AlltravelList) {

                int i = 0;
                int exists = 0;
                TravelHistory travel;
                for (i = 0; i < AlltravelList.size(); i++) {
                        travel = AlltravelList.get(i);
                        if (cabnum == travel.getCab_no() && empid == travel.getEmp().getEid()) {
                                exists = 1;
                        } else if (cabnum != travel.getCab_no() && empid == travel.getEmp().getEid()) {
                                //AlertBox.show(" Cab is not used or doesnot exists");

                        } else if (cabnum == travel.getCab_no() && empid != travel.getEmp().getEid()) {
                                //AlertBox.show(" user has no  travel history ");

                        } else {
                                //AlertBox.show(" No Travel History");

                        }
                }

                if (exists == 1) {
                        displayheaderemp_cab();
                } else {
                        AlertBox.show(" No Travel History");
                }
        }

        protected void displayheaderemp_cab() {
                ft.removeAllRows();
                ft.setText(0, 0, "Employee Id");
                ft.setText(0, 1, " Name");
                ft.setText(0, 2, "Email");
                ft.setText(0, 3, "Address");
                ft.setText(0, 4, "Phone No.");
                ft.setText(0, 5, "cab no");
                ft.setText(0, 6, "pickup point no.");
                ft.setText(0, 7, "start date");
                ft.setText(0, 8, "end date");
                //ft.getRowFormatter().addStyleName(0, "th_header");
                ft.setStyleName("flexth");
                ft.getRowFormatter().setStyleName(0, "table-header");
                ft.setCellPadding(0);
                ft.setBorderWidth(1);
                ft.setCellSpacing(0);
                displayhistoryemp_cab(Eid, cabnumber, AlltravelList);
        }

        protected void displayhistoryemp_cab(String empid, int cab_num, List<TravelHistory> res) {

                TravelHistory travel;
                int row;


                for (int i = 0; i < res.size(); i++) {
                        travel = res.get(i);
                        if (travel.getCab_no() == cab_num && travel.getEmp().getEid() == empid) {

                                row = i + 1;


                                ft.setText(row, 0, travel.getEmp().getEid());
                                ft.setText(row, 1, travel.getEmp().getEname());
                                ft.setText(row, 2, travel.getEmp().getEmail());
                                ft.setText(row, 3, travel.getEmp().getAddress());
                                ft.setText(row, 4, travel.getEmp().getPhone());
                                ft.setText(row, 5, String.valueOf(travel.getCab_no()));
                                ft.setText(row, 6, String.valueOf(travel.getP_no()));
                                ft.setText(row, 7, travel.getStart_date());
                                ft.setText(row, 8, travel.getEnd_till());
                        }
                }

        }

        protected void checkexists(String eid, List<TravelHistory> AlltravelList) {

                int i = 0;
                int exists = 0;
                TravelHistory travel;
                for (i = 0; i < AlltravelList.size(); i++) {
                        travel = AlltravelList.get(i);
                        if (eid == travel.getEmp().getEid())
                                exists = 1;
                }
                if (exists == 1)
                        displayheader();
                else {
                        AlertBox.show("user not exists ");
                }
        }

        protected void displayheadercab() {
                ft.removeAllRows();
                ft.setText(0, 0, "Employee Id");
                ft.setText(0, 1, " Name");
                ft.setText(0, 2, "Email");
                ft.setText(0, 3, "Address");
                ft.setText(0, 4, "Phone No.");
                ft.setText(0, 5, "cab no");
                ft.setText(0, 6, "pickup point no.");
                ft.setText(0, 7, "start date");
                ft.setText(0, 8, "end date");
                //ft.getRowFormatter().addStyleName(0, "th_header");
                ft.setStyleName("flexth");
                ft.getRowFormatter().setStyleName(0, "table-header");
                ft.setCellPadding(0);
                ft.setBorderWidth(1);
                ft.setCellSpacing(0);
                displayhistorycab(cabnumber, AlltravelList);
        }

        protected void displayhistorycab(int cab_num, List<TravelHistory> res) {

                TravelHistory travel;
                int row;


                for (int i = 0; i < res.size(); i++) {
                        travel = res.get(i);
                        if (travel.getCab_no() == cab_num) {

                                row = i + 1;


                                ft.setText(row, 0, travel.getEmp().getEid());
                                ft.setText(row, 1, travel.getEmp().getEname());
                                ft.setText(row, 2, travel.getEmp().getEmail());
                                ft.setText(row, 3, travel.getEmp().getAddress());
                                ft.setText(row, 4, travel.getEmp().getPhone());
                                ft.setText(row, 5, String.valueOf(travel.getCab_no()));
                                ft.setText(row, 6, String.valueOf(travel.getP_no()));
                                ft.setText(row, 7, travel.getStart_date());
                                ft.setText(row, 8, travel.getEnd_till());
                        }
                }

        }

        protected void displayheader() {
                ft.removeAllRows();
                ft.setText(0, 0, "Employee Id");
                ft.setText(0, 1, " Name");
                ft.setText(0, 2, "Email");
                ft.setText(0, 3, "Address");
                ft.setText(0, 4, "Phone No.");
                ft.setText(0, 5, "cab no");
                ft.setText(0, 6, "pickup point no.");
                ft.setText(0, 7, "start date");
                ft.setText(0, 8, "end date");
                //ft.getRowFormatter().addStyleName(0, "th_header");
                ft.setStyleName("flexth");
                ft.getRowFormatter().setStyleName(0, "table-header");
                ft.setCellPadding(0);
                ft.setBorderWidth(1);
                ft.setCellSpacing(0);
                displayhistory(Eid, AlltravelList);

        }

        protected void displayhistory(String Eid, List<TravelHistory> res) {
                TravelHistory travel;
                int row;


                for (int i = 0; i < res.size(); i++) {
                        travel = res.get(i);
                        if (travel.getEmp().getEid() == Eid) {

                                row = i + 1;


                                ft.setText(row, 0, travel.getEmp().getEid());
                                ft.setText(row, 1, travel.getEmp().getEname());
                                ft.setText(row, 2, travel.getEmp().getEmail());
                                ft.setText(row, 3, travel.getEmp().getAddress());
                                ft.setText(row, 4, travel.getEmp().getPhone());
                                ft.setText(row, 5, String.valueOf(travel.getCab_no()));
                                ft.setText(row, 6, String.valueOf(travel.getP_no()));
                                ft.setText(row, 7, travel.getStart_date());
                                ft.setText(row, 8, travel.getEnd_till());
                        }


                }


        }
}


