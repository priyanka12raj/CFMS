package com.cfms.client.gui.widgets;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Dialog Box to show alert messages
 *
 * @author Priyanka, Samta, Chaitra
 */
public class AlertBox extends DialogBox {
        Panel dialog;

        public AlertBox(String message) {
                // Enable glass background.
                setGlassEnabled(true);
                dialog = new VerticalPanel();
                setWidget(dialog);

                //add a label with message and a ok button
                Label msg = new Label(message);
                Button ok = new Button("OK");
                ok.addClickHandler(new ClickHandler() {

                        @Override

                        public void onClick(ClickEvent event) {
                                AlertBox.this.hide();
                        }
                });
                dialog.add(msg);
                dialog.add(ok);
        }

        /**
         * Show the message in AlertBox
         *
         * @param message
         */
        public static void show(String message) {
                AlertBox alert = new AlertBox(message);
                alert.setWidth("200");
                alert.center();
                alert.show();
        }
}
