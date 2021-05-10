package com.cfms.client.gui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Dialog box that allows to add route/cab for requests
 *
 * @author Priyanka, Samta, Chaitra
 */
public class RequestDialog extends DialogBox {
        ManageRequestGUI gui;

        /**
         * constructs a dialog box
         *
         * @param gui
         */
        public RequestDialog(ManageRequestGUI gui) {
                this.gui = gui;
                setText("Add Cab/Route");

                // Enable glass background.
                setGlassEnabled(true);

                // DialogBox is a SimplePanel, so you have to set its widget
                // property to whatever you want its contents to be.
                Button ok = new Button("OK");
                ok.addClickHandler(new ClickHandler() {

                        @Override

                        public void onClick(ClickEvent event) {
                                RequestDialog.this.hide();
                        }
                });
                VerticalPanel contentPanel = new VerticalPanel();
                contentPanel.setHeight("350");
                contentPanel.setWidth("400");
                boolean isFromDialog = true;
                contentPanel.add(new AddNewCabGUI(contentPanel, isFromDialog, this));
                setWidget(contentPanel);
                contentPanel.add(ok);
        }

}
