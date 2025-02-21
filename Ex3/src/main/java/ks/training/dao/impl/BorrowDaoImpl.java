package ks.training.dao.impl;

import ks.training.commom.SqlConstants;
import ks.training.dao.BorrowDao;
import ks.training.exception.RecordNotFoundException;
import ks.training.model.Book;
import ks.training.model.BorrowDetail;
import ks.training.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BorrowDaoImpl implements BorrowDao {
    private void updateQuantity(String name, int additionalQuantity, String author) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement selectStmt = conn.prepareStatement(SqlConstants.SELECT_ID_UPDATE_QUANTITY);
             PreparedStatement updateStmt = conn.prepareStatement(SqlConstants.UPDATE_QUANTITY)) {

            selectStmt.setString(1, name);
            selectStmt.setString(2, author);

            try (ResultSet rs = selectStmt.executeQuery()) {
                if (rs.next()) {
                    int bookId = rs.getInt("id");

                    updateStmt.setInt(1, additionalQuantity);
                    updateStmt.setInt(2, bookId);

                    updateStmt.executeUpdate();
                } else {
                    throw new RecordNotFoundException("Không tìm thấy sách bản nào.");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean borrowBook(int userId, int bookId) throws SQLException {
        Connection conn = DatabaseConnection.getConnection();
        String checkAvailability = "SELECT quantity FROM book WHERE id = ?";
        String borrowSQL = "INSERT INTO borrow (user_id, book_id, borrow_date) VALUES (?, ?, CURDATE())";
        String updateBookSQL = "UPDATE book SET quantity = quantity - 1 WHERE id = ?";

        try (PreparedStatement checkStmt = conn.prepareStatement(checkAvailability);
             PreparedStatement borrowStmt = conn.prepareStatement(borrowSQL);
             PreparedStatement updateStmt = conn.prepareStatement(updateBookSQL)) {

            checkStmt.setInt(1, bookId);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next() && rs.getInt("quantity") > 0) {
                borrowStmt.setInt(1, userId);
                borrowStmt.setInt(2, bookId);
                borrowStmt.executeUpdate();

                updateStmt.setInt(1, bookId);
                updateStmt.executeUpdate();
                return true;
            } else {
                System.out.println("Sách không còn sẵn có để mượn.");
                return false;
            }
        }
    }
    @Override
    public void returnBook(Connection conn,int userId, int bookId) throws SQLException {
        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false);

            String returnSQL = "UPDATE borrow SET return_date = CURDATE() WHERE user_id = ? AND book_id = ? AND return_date IS NULL ORDER BY borrow_date ASC LIMIT 1";
            String updateBookSQL = "UPDATE book SET quantity = quantity + 1 WHERE id = ?";

            try (PreparedStatement returnStmt = conn.prepareStatement(returnSQL);
                 PreparedStatement updateStmt = conn.prepareStatement(updateBookSQL)) {

                returnStmt.setInt(1, userId);
                returnStmt.setInt(2, bookId);
                int rowsAffected = returnStmt.executeUpdate();

                if (rowsAffected > 0) {
                    updateStmt.setInt(1, bookId);
                    updateStmt.executeUpdate();
                    conn.commit();
                } else {
                    System.out.println("Không tìm thấy bản ghi mượn sách.");
                    conn.rollback();
                }
            }
        } catch (SQLException ex) {
            if (conn != null) {
                conn.rollback();
            }
            throw ex;
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public List<BorrowDetail> getBooksBeingBorrowed() throws SQLException {
        List<BorrowDetail> borrowDetails = new ArrayList<>();
        String query = "SELECT b.id AS borrow_id, u.id AS user_id, u.username AS user_name, " +
                "bk.id AS book_id, bk.name AS book_name, bk.author, " +
                "b.borrow_date, b.return_date " +
                "FROM borrow b " +
                "JOIN users u ON b.user_id = u.id " +
                "JOIN book bk ON b.book_id = bk.id " +
                "WHERE b.return_date IS NULL";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int borrowId = resultSet.getInt("borrow_id");
                int userId = resultSet.getInt("user_id");
                String userName = resultSet.getString("user_name");
                int bookId = resultSet.getInt("book_id");
                String bookName = resultSet.getString("book_name");
                String author = resultSet.getString("author");
                LocalDate borrowDate = resultSet.getDate("borrow_date").toLocalDate();
                LocalDate returnDate = resultSet.getDate("return_date") != null ? resultSet.getDate("return_date").toLocalDate() : null;

                borrowDetails.add(new BorrowDetail(borrowId, userId, userName, bookId, bookName, author, borrowDate, returnDate));
            }
        }
        return borrowDetails;
    }
    public List<Book> getBorrowedBooksByUser(int userId) {
        List<Book> borrowedBooks = new ArrayList<>();
        String query = "SELECT b.id, b.name, b.author, b.status, b.quantity " +
                "FROM book b " +
                "JOIN borrow br ON b.id = br.book_id " +
                "WHERE br.user_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String author = rs.getString("author");
                borrowedBooks.add(new Book(id,name, author));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return borrowedBooks;
    }

    public List<BorrowDetail> searchBorrowHistory(LocalDate startDate, LocalDate endDate) throws SQLException {
        List<BorrowDetail> records = new ArrayList<>();
        String sql = "SELECT b.id, u.id AS user_id, u.username AS user_name, \n" +
                "       bk.id AS book_id, bk.name AS book_name, bk.author AS author, \n" +
                "       b.borrow_date, b.return_date\n" +
                "FROM borrow b\n" +
                "JOIN users u ON b.user_id = u.id\n" +
                "JOIN book bk ON b.book_id = bk.id\n" +
                "WHERE b.borrow_date BETWEEN ? AND ?\n" +
                "ORDER BY b.borrow_date ASC;\n";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDate(1, java.sql.Date.valueOf(startDate));
            stmt.setDate(2, java.sql.Date.valueOf(endDate));

            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                int borrowId = resultSet.getInt("id");
                int userId = resultSet.getInt("user_id");
                String userName = resultSet.getString("user_name");
                int bookId = resultSet.getInt("book_id");
                String bookName = resultSet.getString("book_name");
                String author = resultSet.getString("author");
                LocalDate borrowDate = resultSet.getDate("borrow_date").toLocalDate();
                LocalDate returnDate = resultSet.getDate("return_date") != null ? resultSet.getDate("return_date").toLocalDate() : null;
                BorrowDetail record = new BorrowDetail(borrowId, userId, userName, bookId, bookName, author, borrowDate, returnDate);
                records.add(record);
            }
        }
        return records;
    }

}
