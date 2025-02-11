package ks.training.service;

import ks.training.dao.BookDAO;
import ks.training.dao.impl.BookDaoImpl;
import ks.training.model.Book;

import java.sql.SQLException;

public class BookManagement {
    private BookDAO bookDAO;

    public BookManagement(){
        this.bookDAO = new BookDaoImpl();
    }

    public void addBook(Book book) throws SQLException {
        bookDAO.save(book);
    }
}
