package com.cfms.client.gui;

import com.cfms.client.gui.widgets.AlertBox;
import com.cfms.shared.pojo.CabBooker;
import com.cfms.shared.pojo.CabUser;
import com.cfms.shared.pojo.Employee;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.cfms.client.InfoService;

/**
 * GUI to allow user to leave cab
 *
 * @author Priyanka, Chaitra, Samta
 */
public class LeaveCabGUI extends Composite {
        Panel mainPanel;
        Panel contentPanel;

        /**
         * adds widget to leave cab
         */
        public LeaveCabGUI() {
                /**
                 * Initialize and widgets to mainPanel
                 */
                contentPanel = (Panel) getParent();
                mainPanel = new VerticalPanel();
                HorizontalPanel BtnPanel = new HorizontalPanel();
                BtnPanel.setSpacing(4);
                initWidget(mainPanel);
                Label confirmL = new Label("Are you sure you wnat to leave cab?");
                Button confirmYesBtn = new Button("Yes");
                Button confirmNoBtn = new Button("No");
                mainPanel.add(confirmL);
                BtnPanel.add(confirmYesBtn);
                BtnPanel.add(confirmNoBtn);
                mainPanel.add(BtnPanel);
                /**
                 * click handler to confirm leave cab
                 */
                confirmYesBtn.addClickHandler(new ClickHandler() {
                        @Override public void onClick(ClickEvent event) {
                                leaveCab(CookieBeans.cabuser);
                        }
                });
                /**
                 * click handler to not leave cab
                 */
                confirmNoBtn.addClickHandler(new ClickHandler() {

                        @Override public void onClick(ClickEvent event) {
                                contentPanel.clear();
                                contentPanel.add(new BookCabGUI(contentPanel));
                        }
                });
        }

        /**
         * detachs the cabuser from the cab
         *
         * @param cabuser
         */
        protected void leaveCab(CabUser cabuser) {
                CabBooker cb = new CabBooker();
                cb.setEmployee_id(cabuser.getEmployee().getEid());
                cb.setCab_no(cabuser.getCab().getCab_no());
                cb.setPickup_id(cabuser.getPickuppoint().getPickup_id());
                /**
                 * Request the server to leave cab
                 */
                InfoService.Util.getService().leaveCab(cb, new MethodCallback<Boolean>() {

                        @Override
                        public void onSuccess(Method method, Boolean response) {
                                if (response == true) {
                                        AlertBox.show("Successful");
                                        //updateCabUserDetails(CookieBeans.employee);
                                        updateLeaveCab();

                                }
                        }

                        @Override public void onFailure(Method method, Throwable exception) {
                                AlertBox.show("failure");
                        }
                });
        }

        /**
         * Update the leave cab details
         */
        private void updateLeaveCab() {
                CookieBeans.cabuser = null;
                CookieBeans.isCabUser = false;
                RootLayoutPanel.get().remove(0);
                RootLayoutPanel.get().add(new IndexGUI());
        }

        /**
         * update the cabuser details
         *
         * @param emp
         */
        public void updateCabUserDetails(Employee emp) {
                InfoService.Util.getService().getCabUser(emp, new MethodCallback<CabUser>() {

                        @Override public void onFailure(Method method, Throwable exception) {
                                CookieBeans.setNotCabUser();
                                RootLayoutPanel.get().remove(0);
                                RootLayoutPanel.get().add(new IndexGUI());
                        }

                        @Override public void onSuccess(Method method, CabUser response) {
                                CookieBeans.setCabUser(response);
                                RootLayoutPanel.get().remove(0);
                                RootLayoutPanel.get().add(new IndexGUI());
                        }
                });
        }
}
