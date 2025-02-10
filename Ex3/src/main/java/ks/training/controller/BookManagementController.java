package ks.training.controller;

import ks.training.view.BookManagementView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeListener;

public class BookManagementController implements Action {
    BookManagementView bookManagementView;
    public BookManagementController(BookManagementView bookManagementView) {

    }

    @Override
    public Object getValue(String key) {
        return null;
    }

    @Override
    public void putValue(String key, Object value) {

    }

    @Override
    public void setEnabled(boolean b) {

    }

    @Override
    public boolean isEnabled() {
        return false;
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {

    }

    @Override
    public void removePropertyChangeListener(PropertyChangeListener listener) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();
        JOptionPane.showMessageDialog(bookManagementView,"You click on: " + action);
    }
}
