package ks.training.controller;

import ks.training.view.AdminView;
import ks.training.view.UserManagementView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class UserManagementController implements ActionListener {
    private UserManagementView view;

    public UserManagementController(UserManagementView view) {
        this.view = view;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();
        if (action.equals("Trang Sau")){
            view.clickNext();
        } else if (action.equals("Trang Trước")) {
            view.clickPrev();
        } else if (action.equals("Xoá User")){
            try {
                view.deleteUser();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        } else if (action.equals("Danh Sách User Mượn Sách")) {
             new AdminView();
        }
    }
}
