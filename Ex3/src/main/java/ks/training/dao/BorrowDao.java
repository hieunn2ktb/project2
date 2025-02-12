package ks.training.dao;

import ks.training.model.Book;
import ks.training.model.BorrowDetail;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public interface BorrowDao {
    boolean borrowBook(int userId, int bookId) throws SQLException;

    boolean returnBook(int userId, int bookId) throws SQLException;

    List<BorrowDetail> getBooksBeingBorrowed() throws SQLException;
     List<Book> getBorrowedBooksByUser(int userId);
     List<BorrowDetail> searchBorrowHistory(LocalDate startDate, LocalDate endDate) throws SQLException;
}
