package ks.training.service;

import ks.training.dao.BookDAO;
import ks.training.dao.BorrowDao;
import ks.training.dao.impl.BookDaoImpl;
import ks.training.dao.impl.BorrowDaoImpl;
import ks.training.model.Book;

import java.sql.SQLException;
import java.util.List;

public class BookManagement {
    private BookDAO bookDAO;
    private BorrowDao borrowDao;

    public BookManagement() {
        this.bookDAO = new BookDaoImpl();
        this.borrowDao = new BorrowDaoImpl();
    }

    public void addBook(Book book) throws SQLException {
        bookDAO.save(book);
    }

    public List<Book> findAll() throws SQLException {
        return bookDAO.findAll();
    }

    public String deleteBook(Book book) throws SQLException {
        return bookDAO.delete(book);
    }
    public List<Book> searchBookByName(String name) throws SQLException {
        return bookDAO.findByName(name);
    }
    public List<Book> searchBookByNameAndAuthor(String name, String author) throws SQLException {
        return bookDAO.findByNameAndAuthor(name,author);
    }
    public boolean borrowBook(int userId, int bookId) throws SQLException {
        return borrowDao.borrowBook(userId,bookId);
    }

}
