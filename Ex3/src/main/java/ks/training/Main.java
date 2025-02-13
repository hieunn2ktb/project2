package ks.training;

import ks.training.view.BookManagementView;
import ks.training.view.LoginView;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            new LoginView();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}