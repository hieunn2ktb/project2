package ks.training.controller;

import ks.training.model.Book;
import ks.training.view.BookManagementView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeListener;
import java.sql.SQLException;

public class BookManagementController implements Action {
    BookManagementView bookManagementView;
    public BookManagementController(BookManagementView bookManagementView) {
        this.bookManagementView = bookManagementView;
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
        if (action.equals("Thêm Vào Thư Viện")){
            try {
                String name = this.bookManagementView.textField_2.getText();
                String author = this.bookManagementView.textField_3.getText();
                int quantity = Integer.parseInt(this.bookManagementView.textField_4.getText());
                Book book = new Book(name,author,quantity);
                this.bookManagementView.addBook(book);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}
