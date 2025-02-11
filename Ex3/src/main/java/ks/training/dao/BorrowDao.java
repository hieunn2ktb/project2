package ks.training.dao;

import java.sql.SQLException;

public interface BorrowDao {
    boolean borrowBook(int userId, int bookId) throws SQLException;
    boolean returnBook(int userId, int bookId) throws SQLException;
}
