package ks.training;

import ks.training.view.BookManagementView;
import ks.training.view.LibraryManagementUI;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            new BookManagementView();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}