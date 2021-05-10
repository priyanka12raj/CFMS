package com.cfms.client.gui.widgets;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.IntegerBox;
import com.google.gwt.user.client.ui.Label;

/**
 * custom widget for input of time in hour and minutes
 *
 * @author Priyanka , Samta, Chaitra
 */
public class TimeBox extends Composite {
        HorizontalPanel timePanel;
        /**
         * Integer input box for hour
         */
        IntegerBox hrBox;
        /**
         * Integer input box for minutes
         */
        IntegerBox minBox;
        /**
         * Error Label for hour
         */
        Label errHrLabel;
        /**
         * Error Label for minutes
         */
        Label errMinLabel;
        /**
         * is time in expectede format
         */
        public boolean isInFormat;

        /**
         * initialize the Composite TimeBox widget
         */
        public TimeBox() {
                timePanel = new HorizontalPanel();
                initWidget(timePanel); //set initial widget as timePanel
                /**
                 * Initialize all the widgets
                 */
                hrBox = new IntegerBox();
                minBox = new IntegerBox();
                errHrLabel = new Label();
                errMinLabel = new Label();

                /**
                 * Setting their positions
                 */
                hrBox.setSize("40px", "25px");
                minBox.setSize("40px", "25px");

                /**
                 * adding integer boxes and error labels to timePanel in format hr:min
                 */
                timePanel.add(hrBox);
                timePanel.add(new Label(":"));
                timePanel.add(minBox);
                timePanel.add(errHrLabel);
                timePanel.add(errMinLabel);

                /**
                 * defining key up handler
                 */
                hrBox.addKeyUpHandler(new KeyUpHandler() {

                        @Override public void onKeyUp(KeyUpEvent event) {
                                String input = hrBox.getText();
                                if (!input.matches("[0-9]") || !input.matches("[0-9][0-9]")) {
                                        errHrLabel.setText("only 2 digits are allowed");
                                } else
                                        errHrLabel.setText("");

                        }
                });
                /**
                 * change handler for hrBox
                 */
                hrBox.addChangeHandler(new ChangeHandler() {

                        @Override public void onChange(ChangeEvent event) {
                                int hr = hrBox.getValue();
                                if (hr < 0 || hr > 24) {
                                        errHrLabel.setText(" 24 hr format only ");

                                        isInFormat = false;
                                } else {
                                        errHrLabel.setText("");
                                        isInFormat = true;
                                }
                        }
                });
                /**
                 * key up handler for minBox to validate the hour format
                 */
                minBox.addKeyUpHandler(new KeyUpHandler() {

                        @Override public void onKeyUp(KeyUpEvent event) {
                                String input = minBox.getText();
                                if (!input.matches("[0-9]") || !input.matches("[0-9][0-9]")) {
                                        errMinLabel.setText("only 2 digits are allowed");
                                } else
                                        errMinLabel.setText("");

                        }
                });
                /**
                 * change handler for minBox to validate the minute format
                 */
                minBox.addChangeHandler(new ChangeHandler() {

                        @Override public void onChange(ChangeEvent event) {
                                int min = minBox.getValue();
                                if (min < 0 || min > 59) {
                                        errMinLabel.setText(" minute should be b/w 0 & 59");
                                        isInFormat = false;
                                } else {
                                        errMinLabel.setText("");
                                        isInFormat = true;
                                }
                        }
                });
        }

        /**
         * gets the time from input box
         *
         * @return time in format hr:min:sec
         */
        public String getTime() {
                StringBuffer time = new StringBuffer();
                if (isInFormat) {
                        if (minBox.getValue() == null)
                                time.append("00");
                        else
                                time.append(hrBox.getValue());
                        time.append(":");
                        if (minBox.getValue() == null)
                                time.append("00");
                        else
                                time.append(minBox.getValue());
                        time.append(":");
                        time.append("00");
                } else
                        time.append("00:00:00");
                return time.toString();
        }

        /**
         * sets the time to hrBox and minBox
         *
         * @param time time in format hr:min:sec
         */
        public void setTime(String time) {
                StringBuffer time_s = new StringBuffer(time);
                //extract hr from time
                String hr = time_s.substring(0, time_s.indexOf(":"));
                hrBox.setValue(Integer.parseInt(hr));
                //extract min from time
                String min = time_s.substring(time_s.indexOf(":") + 1, time_s.lastIndexOf(":"));
                minBox.setValue(Integer.parseInt(min));
        }

        public HandlerRegistration addChangeHandler(ChangeHandler handler) {
                return addDomHandler(handler, ChangeEvent.getType());
        }

        /**
         * enables or disables the integer boxes
         *
         * @param enabled true for enabling and false for disabling
         */
        public void setEnabled(boolean enabled) {
                hrBox.setEnabled(enabled);
                minBox.setEnabled(enabled);
        }
}
