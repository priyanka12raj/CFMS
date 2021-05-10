package com.cfms.client.gui;

import java.util.Iterator;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * GUI class AdminSidebar presents buttons for admin operations
 *
 * @author Priyanka, Samta, Chaitra
 */
public class AdminSideBar extends Composite {
        /**
         * sidebar panel
         */
        VerticalPanel sidebar = new VerticalPanel();
        /**
         * menu to manage cabs
         */
        MenuBar menu = new MenuBar();
        /**
         * menu to manage cab_providers
         */
        MenuBar providerMenu = new MenuBar();
        Label heading = new Label();
        Panel contentPanel;

        /**
         * Creates a sidebar with admin operation
         *
         * @param contentPanel panel where the contents are to be displayed
         */
        public AdminSideBar(final Panel contentPanel) {
                this.contentPanel = contentPanel;

                //Initializing and positioning the widgets
                sidebar.setSpacing(12);
                heading.setStyleName("heading");


                /**
                 * Creating Buttons and menus for each admin operation and adding it to sidebar
                 */
                //cab menu
                menu.setAutoOpen(true);
                menu.setFocusOnHoverEnabled(true);
                menu.setPixelSize(210, 50);
                manageCabMenu();
                menu.setAnimationEnabled(true);
                sidebar.add(menu);

                //manage request button
                Button ManageRequestsBtn = new Button("Manage Requests");
                ManageRequestsBtn.setStyleName("sidebar-btn");
                sidebar.add(ManageRequestsBtn);

                //add route options
                Button AddRoutesBtn = new Button("Add Route");
                AddRoutesBtn.setStyleName("sidebar-btn");
                sidebar.add(AddRoutesBtn);

                //cab provider management
                providerMenu.setAutoOpen(true);
                providerMenu.setFocusOnHoverEnabled(true);
                providerMenu.setPixelSize(210, 50);
                manageProviders();
                providerMenu.setAnimationEnabled(true);
                sidebar.add(providerMenu);

                //travel history
                Button TravelHistoryBtn = new Button("Employee Travel History");
                TravelHistoryBtn.setStyleName("sidebar-btn");
                sidebar.add(TravelHistoryBtn);

                //logout button
                Button LogOutBtn = new Button("Log Out");
                LogOutBtn.setStyleName("sidebar-btn");
                sidebar.add(LogOutBtn);

                initWidget(sidebar);

                /**
                 * Adding Clickhandlers to buttons
                 */
                AddRoutesBtn.addClickHandler(new ClickHandler() {

                        @Override public void onClick(ClickEvent event) {
                                contentPanel.clear();
                                heading.setText("Add Routes");
                                contentPanel.add(heading);
                                contentPanel.add(new AddRouteGUI());
                        }
                });

                ManageRequestsBtn.addClickHandler(new ClickHandler() {

                        @Override public void onClick(ClickEvent arg0) {
                                contentPanel.clear();
                                heading.setText("Manage Requests");
                                contentPanel.add(heading);
                                contentPanel.add(new ManageRequestGUI());
                        }
                });
                TravelHistoryBtn.addClickHandler(new ClickHandler() {

                        @Override public void onClick(ClickEvent arg0) {
                                contentPanel.clear();
                                heading.setText("Employee Travel History");
                                contentPanel.add(heading);
                                contentPanel.add(new EmpTravelHisGUI());

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

                        }
                });
        }

        /**
         * Creates a menu for managing cab_providers
         */
        private void manageProviders() {
                MenuBar providers = new MenuBar(true);
                providers.setAnimationEnabled(true);
                providers.addItem("View providers", new Command() {

                        @Override public void execute() {
                                contentPanel.clear();

                                contentPanel.add(new ViewProvidersGUI(contentPanel));

                        }
                });
                providers.addItem("Add providers", new Command() {

                        @Override public void execute() {
                                contentPanel.clear();
                                heading.setText("Add Provider");
                                contentPanel.add(heading);
                                contentPanel.add(new AddProviderGUI(contentPanel));

                        }
                });
                MenuItem m1 = new MenuItem("Manage Providers", providers);
                m1.setStyleName("menu-btn");
                providerMenu.addItem(m1);
        }

        /**
         * creates a menu to cabs
         */
        private void manageCabMenu() {
                MenuBar cabmenu = new MenuBar(true);
                cabmenu.setAnimationEnabled(true);

                cabmenu.addItem("Add a cab", new Command() {

                        @Override public void execute() {
                                contentPanel.clear();
                                contentPanel.add(new AddNewCabGUI(contentPanel));
                        }

                });

                cabmenu.addItem(" View all cabs", new Command() {

                        @Override public void execute() {

                                contentPanel.clear();
                                contentPanel.add(new ViewAllCabs());
                        }

                });
                MenuItem m1 = new MenuItem("Manage Cabs", cabmenu);
                m1.setStyleName("menu-btn");
                menu.addItem(m1);
        }

}
