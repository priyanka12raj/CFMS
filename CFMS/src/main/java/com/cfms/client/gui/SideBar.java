package com.cfms.client.gui;

import java.util.Iterator;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * GUI class for adding a sidebar with user options
 *
 * @author Priyanka, Samta, Chaitra
 */
public class SideBar extends Composite {

        /**
         * sidebar panel
         */
        VerticalPanel sidebar = new VerticalPanel();
        /**
         * content Panel to write the contents
         */
        Panel contentPanel;
        Label heading = new Label();
        /**
         * book cab or cab details
         */
        String book_cab_label;

        /**
         * creates a sidebar with user options
         *
         * @param contentPanel
         */
        public SideBar(final Panel contentPanel) {

                this.contentPanel = contentPanel;
                initWidget(sidebar);
                sidebar.setSpacing(12);

                heading.setStyleName("heading");

                /**
                 * Create and Add buttons for user operations to sidebar
                 */
                Button BookCabBtn = new Button("Book Cab");
                BookCabBtn.setStyleName("sidebar-btn");
                Button MyCabBtn = new Button("Cab Details");
                MyCabBtn.setStyleName("sidebar-btn");
                Button RoutesBtn = new Button("View Routes");
                RoutesBtn.setStyleName("sidebar-btn");
                Button TravelHistoryBtn = new Button("Travel History");
                TravelHistoryBtn.setStyleName("sidebar-btn");
                Button UpdateProfileBtn = new Button("Profile");
                UpdateProfileBtn.setStyleName("sidebar-btn");
                Button ChangePasswordBtn = new Button("Change Password");
                ChangePasswordBtn.setStyleName("sidebar-btn");
                Button LogOutBtn = new Button("Log Out");
                LogOutBtn.setStyleName("sidebar-btn");

                /**
                 * if user is cab user then load cab details otherwise allow to book cab
                 */
                if (!CookieBeans.isCabUser)
                        sidebar.add(BookCabBtn);
                else
                        sidebar.add(MyCabBtn);
                //add other buttons
                sidebar.add(RoutesBtn);
                sidebar.add(TravelHistoryBtn);
                sidebar.add(UpdateProfileBtn);
                sidebar.add(ChangePasswordBtn);
                sidebar.add(LogOutBtn);

                /**
                 * Add click handlers to buttons
                 */
                BookCabBtn.addClickHandler(new BookCabHandler());
                MyCabBtn.addClickHandler(new ClickHandler() {

                        @Override public void onClick(ClickEvent event) {
                                Button changeCabBtn = new Button("Change Cab", new BookCabHandler());
                                Button leaveCabBtn = new Button("Leave Cab", new ClickHandler() {
                                        @Override public void onClick(ClickEvent event) {
                                                contentPanel.clear();
                                                heading.setText("Leave Cab");
                                                contentPanel.add(heading);
                                                contentPanel.add(new LeaveCabGUI());
                                        }
                                });
                                contentPanel.clear();
                                heading.setText("My Cab");
                                contentPanel.add(heading);
                                contentPanel.add(new CabDetailsGUI(CookieBeans.cabuser.getCab()));
                                HorizontalPanel btnPanel = new HorizontalPanel();
                                btnPanel.setSpacing(1);
                                btnPanel.add(changeCabBtn);
                                btnPanel.add(leaveCabBtn);
                                contentPanel.add(btnPanel);
                        }
                });
                RoutesBtn.addClickHandler(new ClickHandler() {

                        @Override public void onClick(ClickEvent event) {
                                contentPanel.clear();
                                heading.setText("View Routes");
                                contentPanel.add(heading);
                                contentPanel.add(new RoutesTable());
                        }
                });
                TravelHistoryBtn.addClickHandler(new ClickHandler() {

                        @Override public void onClick(ClickEvent event) {
                                contentPanel.clear();
                                heading.setText("Travel History");
                                contentPanel.add(heading);
                                contentPanel.add(new TravelHistoryTable());

                        }
                });

                UpdateProfileBtn.addClickHandler(new ClickHandler() {

                        @Override public void onClick(ClickEvent event) {

                                contentPanel.clear();
                                heading.setText("Update Profile");
                                contentPanel.add(heading);
                                contentPanel.add(new UpdateProfileGUI());
                        }
                });

                ChangePasswordBtn.addClickHandler(new ClickHandler() {

                        @Override public void onClick(ClickEvent event) {

                                contentPanel.clear();
                                heading.setText("Change Password");
                                contentPanel.add(heading);
                                contentPanel.add(new ChangePasswordGUI());
                        }
                });
                LogOutBtn.addClickHandler(new ClickHandler() {

                        @Override public void onClick(ClickEvent event) {
                                Iterator<String> cookies = Cookies.getCookieNames().iterator();
                                while (cookies.hasNext()) {
                                        Cookies.removeCookie(cookies.next());
                                }
                                RootLayoutPanel.get().remove(0);
                                RootLayoutPanel.get().clear();
                                RootLayoutPanel.get().add(new EmployeeLoginGUI());
                                CookieBeans.employee = null;
                                CookieBeans.cabuser = null;
                                CookieBeans.isCabUser = false;

                        }
                });


        }

        /**
         * ClickHandler to book cabs
         *
         * @author Priyanka
         */
        private class BookCabHandler implements ClickHandler {

                @Override public void onClick(ClickEvent event) {
                        contentPanel.clear();
                        heading.setText("Book Cab");
                        contentPanel.add(heading);
                        contentPanel.add(new BookCabGUI(contentPanel));
                }
        }

}
