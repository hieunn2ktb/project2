package ks.training.service;

import ks.training.dao.BorrowDao;
import ks.training.dao.impl.BorrowDaoImpl;
import ks.training.model.Book;
import ks.training.model.BorrowDetail;
import ks.training.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class AdminManagement {
    public BorrowDao borrowDao;

    public AdminManagement() {
        this.borrowDao = new BorrowDaoImpl();
    }

    public List<BorrowDetail> getBooksBeingBorrowed() {
        try {
            return borrowDao.getBooksBeingBorrowed();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public List<Book> listBook(int id){
        return borrowDao.getBorrowedBooksByUser(id);
    }
    public void returnBook(int userId, int bookId) throws SQLException {
        Connection conn = null;
        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false);

            borrowDao.returnBook(conn, userId, bookId);

            conn.commit();
        } catch (SQLException e) {
            if (conn != null) {
                conn.rollback();
            }
            throw e;
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    public List<BorrowDetail> searchBorrowHistory(LocalDate startDate, LocalDate endDate) throws SQLException{
        return borrowDao.searchBorrowHistory(startDate,endDate);
    }
}
