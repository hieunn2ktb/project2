package ks.training.dao;

import ks.training.model.Book;
import ks.training.model.BorrowDetail;

import java.sql.SQLException;
import java.util.List;

public interface BorrowDao {
    boolean borrowBook(int userId, int bookId) throws SQLException;

    boolean returnBook(int userId, int bookId) throws SQLException;

    List<BorrowDetail> getBooksBeingBorrowed() throws SQLException;
     List<Book> getBorrowedBooksByUser(int userId);
}
