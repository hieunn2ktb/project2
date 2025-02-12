package ks.training.controller;

import ks.training.view.UserReturnBookView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class UserReturnBookController implements ActionListener {
    private UserReturnBookView view;

    public UserReturnBookController(UserReturnBookView view) {
        this.view = view;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();
        if (action.equals("Trả sách")){
            try {
                this.view.returnBook();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}
