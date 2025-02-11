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
                String name = this.bookManagementView.textFieldAddName.getText();
                String author = this.bookManagementView.textField_3.getText();
                String result = this.bookManagementView.textField_4.getText();
                if (name.isEmpty() || author.isEmpty() || result.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please enter all information!");
                    return;
                }
                int quantity = Integer.parseInt(result);
                Book book = new Book(name,author,quantity);
                this.bookManagementView.addBook(book);
                JOptionPane.showMessageDialog(null, "Add book successfully!");
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        } else if (action.equals("Xoá Sách")) {
            try {
                this.bookManagementView.deleteBook();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        } else if (action.equals("Tìm Kiếm")) {
            this.bookManagementView.searchBook();
        }
        else if (action.equals("Mượn Sách")) {
            try {
                this.bookManagementView.borrowBook();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }

    }
}
