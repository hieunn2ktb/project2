package ks.training;

import ks.training.view.AdminView;
import ks.training.view.BookManagementView;
import ks.training.view.UserManagementView;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            new BookManagementView();

            //new UserManagementView();
           // new AdminView();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}