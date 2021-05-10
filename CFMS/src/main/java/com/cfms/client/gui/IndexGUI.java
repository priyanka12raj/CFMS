package com.cfms.client.gui;


import com.cfms.shared.pojo.Employee;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.VerticalPanel;


public class IndexGUI extends Composite {
        DockLayoutPanel mainPanel = new DockLayoutPanel(Unit.PCT);
        Employee emp;

        /**
         * Creates the initial layout
         */
        public IndexGUI() {
                initWidget(mainPanel);
                /**
                 * if employee is admin load admin view otherwise load user view
                 */
                emp = CookieBeans.employee;
                if (emp.getIsAdmin())
                        addAdminGui(emp);
                else
                        addGui(emp);
        }

        /**
         * Adds admin view for employee
         *
         * @param e employee
         */
        private void addAdminGui(Employee e) {

                this.emp = e;
                /**
                 * Initialize widgets
                 */
                HTML header = new HTML("NetCracker Cab Facility Management System");
                Image cfms_image = new Image("cfms.png");
                header.setStyleName("header");
                HTML footer = new HTML("Salarpuria Ascent, Second floor, 77, <br>Jyothi Nivas College Road, Kormangala,<br>Bengaluru, Karnataka 560095");
                footer.setStyleName("footer");
                mainPanel.setStyleName("main-panel");
                /**
                 * Tab panel with user and admin tabs
                 */
                TabPanel tabPanel = new TabPanel();
                tabPanel.setStyleName("tab-panel");
                VerticalPanel contentPanel = new VerticalPanel();
                contentPanel.setStyleName("content-panel");
                SideBar sidebar = new SideBar(contentPanel);
                sidebar.setStyleName("sidebar");
                tabPanel.add(new AdminSideBar(contentPanel), "AdminPanel");
                tabPanel.add(sidebar, "UserPanel");
                tabPanel.selectTab(0);
                ScrollPanel scrollPanel = new ScrollPanel(contentPanel);
                scrollPanel.setSize("100%", "100%");
                /**
                 * add widgets to dock panel
                 */
                mainPanel.addNorth(header, 10);
                mainPanel.addSouth(footer, 10);
                mainPanel.addWest(tabPanel, 20);
                mainPanel.addEast(cfms_image, 20);
                scrollPanel.setStyleName("scrollpanel");

                mainPanel.add(scrollPanel);
        }

        /**
         * adds layout for user view
         *
         * @param e
         */
        private void addGui(Employee e) {
                this.emp = e;
                /**
                 * Initialize widgets
                 */
                HTML header = new HTML("Cab Facility Management System");
                Image cfms_image = new Image("cfms.png");
                header.setStyleName("header");
                HTML footer = new HTML("Salarpuria Ascent, Second floor, 77, <br>Jyothi Nivas College Road, Kormangala,<br>Bengaluru, Karnataka 560095");
                footer.setStyleName("footer");
                mainPanel.setStyleName("main-panel");
                mainPanel.addNorth(header, 10);
                VerticalPanel contentPanel = new VerticalPanel();
                contentPanel.setStyleName("content-panel");
                SideBar sidebar = new SideBar(contentPanel);
                sidebar.setStyleName("sidebar");
                /**
                 * Tab panel with user sidebar
                 */
                TabPanel tabPanel = new TabPanel();
                tabPanel.setStyleName("tab-panel");
                tabPanel.add(sidebar, "User Panel");
                tabPanel.selectTab(0);
                ScrollPanel scrollPanel = new ScrollPanel(contentPanel);
                scrollPanel.setSize("100%", "100%");

                /**
                 * add widgets to dock panel
                 */
                mainPanel.addSouth(footer, 10);
                mainPanel.addWest(tabPanel, 20);
                mainPanel.addEast(cfms_image, 20);
                scrollPanel.setStyleName("scrollpanel");
                mainPanel.add(scrollPanel);
        }
}

