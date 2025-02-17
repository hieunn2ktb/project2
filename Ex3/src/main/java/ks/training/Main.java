package ks.training;

import ks.training.view.BookManagementView;
import ks.training.view.LoginView;
import ks.training.view.UserLogin;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            new UserLogin();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}