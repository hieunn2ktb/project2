package ks.training.service;

import ks.training.dao.BookDAO;
import ks.training.dao.BorrowDao;
import ks.training.dao.impl.BookDaoImpl;
import ks.training.dao.impl.BorrowDaoImpl;
import ks.training.model.Book;
import ks.training.util.DatabaseConnection;

import java.sql.Connection;
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
        Connection conn = null;
        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false);

            bookDAO.save(conn,book);

            conn.commit();
        } catch (SQLException e) {
            if (conn != null) {
                conn.rollback();
            }
            throw e;
        } finally {
            if (conn != null) {
                conn.setAutoCommit(true);
                conn.close();
            }
        }
    }

    public List<Book> findAll() throws SQLException {
        return bookDAO.findAll();
    }

    public void deleteBook(Book book) throws SQLException {
        Connection conn = null;
        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false);

            bookDAO.delete(conn,book);

            conn.commit();
        } catch (SQLException e) {
            if (conn != null) {
                conn.rollback();
            }
            throw e;
        } finally {
            if (conn != null) {
                conn.setAutoCommit(true);
                conn.close();
            }
        }
    }

    public List<Book> searchBookByName(String name) throws SQLException {
        return bookDAO.findByName(name);
    }

    public List<Book> searchBookByNameAndAuthor(String name, String author) throws SQLException {
        return bookDAO.findByNameAndAuthor(name, author);
    }

    public boolean borrowBook(int userId, int bookId) throws SQLException {
        Connection conn = null;
        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false);

            boolean success = borrowDao.borrowBook(userId, bookId);

            conn.commit();
            return success;
        } catch (SQLException e) {
            if (conn != null) {
                conn.rollback();
            }
            throw e;
        } finally {
            if (conn != null) {
                conn.setAutoCommit(true);
                conn.close();
            }
        }
    }
}
