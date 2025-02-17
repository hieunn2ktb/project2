package ks.training.controller;

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
                this.bookManagementView.addBook();
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
        } else if (action.equals("Trả Sách")) {
            this.bookManagementView.actionPerformed();
        } else if (action.equals("Xuất File Excel")) {
            this.bookManagementView.exportExcel();
        }

    }
}
