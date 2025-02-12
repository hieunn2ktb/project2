package ks.training.controller;

import ks.training.view.AdminView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminManagementController implements ActionListener {
    private AdminView adminView;

    public AdminManagementController(AdminView adminView) {
        this.adminView = adminView;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();
        if (action.equals("Tìm Kiếm")){
            this.adminView.searchUserByBorrowDay();
        }
    }
}
