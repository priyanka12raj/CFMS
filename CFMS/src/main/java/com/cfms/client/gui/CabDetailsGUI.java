package com.cfms.client.gui;

import com.cfms.shared.pojo.Cab;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;

/**
 * Displays the details of the cab
 *
 * @author Priyanka, Samta, Chaitra
 */
public class CabDetailsGUI extends Composite {
        /**
         * contains the cab details
         */
        FlexTable cabDetails = new FlexTable();

        public CabDetailsGUI(Cab cab) {
                initWidget(cabDetails);

                /**
                 * set the cab detail labels
                 */
                cabDetails.setText(0, 0, "Cab No");
                cabDetails.setText(1, 0, "Route No");
                cabDetails.setText(2, 0, "Provider");
                cabDetails.setText(3, 0, "available seats");
                cabDetails.setText(4, 0, "total no of seats");
                cabDetails.setText(5, 0, "cost/day");
                cabDetails.setText(6, 0, "Start time from office");
                /**
                 * set the values
                 */
                cabDetails.setText(0, 1, String.valueOf(cab.getCab_no()));
                cabDetails.setText(1, 1, String.valueOf(cab.getRoute_no()));
                cabDetails.setText(2, 1, cab.getProvider());
                cabDetails.setText(3, 1, String.valueOf(cab.getAvail_seats()));
                cabDetails.setText(4, 1, String.valueOf(cab.getNo_of_seats()));
                cabDetails.setText(5, 1, String.valueOf(cab.getCost_per_month()));
                cabDetails.setText(6, 1, cab.getStart_time_NC());

        }
}
