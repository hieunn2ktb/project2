package ks.training.service;

import ks.training.dao.BorrowDao;
import ks.training.dao.impl.BorrowDaoImpl;
import ks.training.model.Book;
import ks.training.model.BorrowDetail;

import java.sql.SQLException;
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
}
