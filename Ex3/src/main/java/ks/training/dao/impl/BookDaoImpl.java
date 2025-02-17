package ks.training.dao.impl;

import ks.training.commom.SqlConstants;
import ks.training.dao.BookDAO;
import ks.training.exception.RecordNotFoundException;
import ks.training.model.Book;
import ks.training.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDaoImpl implements BookDAO {

    @Override
    public void save(Connection conn, Book book) throws SQLException {
        boolean autoCommit = conn.getAutoCommit();
        try {
            conn.setAutoCommit(false);

            if (isBookIdExisted(conn, book.getName(), book.getAuthor())) {
                updateQuantity(conn, book.getName(), book.getQuantity(), book.getAuthor());
            } else {
                try (PreparedStatement pstmt = conn.prepareStatement(SqlConstants.INSERT)) {
                    pstmt.setString(1, book.getName());
                    pstmt.setString(2, book.getAuthor());
                    pstmt.setInt(3, book.getQuantity());
                    pstmt.executeUpdate();
                }
            }

            conn.commit();
        } catch (SQLException e) {
            conn.rollback();
            throw e;
        } finally {
            conn.setAutoCommit(autoCommit);
        }
    }

    private boolean isBookIdExisted(Connection conn, String name, String author) throws SQLException {
        try (PreparedStatement pstmt = conn.prepareStatement(SqlConstants.COUNT_BOOK)) {
            pstmt.setString(1, name);
            pstmt.setString(2, author);
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }
        }
    }

    private void updateQuantity(Connection conn, String name, int additionalQuantity, String author) throws SQLException {
        try (PreparedStatement selectStmt = conn.prepareStatement(SqlConstants.SELECT_ID_UPDATE_QUANTITY);
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
        }
    }

    @Override
    public List<Book> findByNameAndAuthor(String name, String author) throws SQLException {
        List<Book> books = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SqlConstants.SHOW_BOOK_BY_NAME_AND_AUTHOR)) {
            pstmt.setString(1, name);
            pstmt.setString(2, author);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    books.add(mapResultSetToBook(rs));
                }
            }
        }
        return books;
    }

    @Override
    public List<Book> findByName(String name) throws SQLException {
        List<Book> books = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SqlConstants.SHOW_BOOK_BY_NAME)) {
            pstmt.setString(1, name);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    books.add(mapResultSetToBook(rs));
                }
            }
        }
        return books;
    }

    @Override
    public void delete(Connection conn, Book book) throws SQLException {
        boolean autoCommit = conn.getAutoCommit();
        try {
            conn.setAutoCommit(false);
            try (PreparedStatement pstmt = conn.prepareStatement(SqlConstants.DELETE)) {
                pstmt.setInt(1, book.getId());
                if (pstmt.executeUpdate() == 0) {
                    throw new SQLException("Không tìm thấy sách để xóa.");
                }
            }
            conn.commit();
        } catch (SQLException e) {
            conn.rollback();
            throw e;
        } finally {
            conn.setAutoCommit(autoCommit);
        }
    }

    @Override
    public List<Book> findAll() throws SQLException {
        List<Book> books = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SqlConstants.FIND_ALL_BOOK);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                books.add(mapResultSetToBook(rs));
            }
        }
        return books;
    }

    private Book mapResultSetToBook(ResultSet rs) throws SQLException {
        Book book = new Book();
        book.setId(rs.getInt(1));
        book.setName(rs.getString(2));
        book.setAuthor(rs.getString(3));
        book.setStatus(rs.getBoolean(4));
        book.setQuantity(rs.getInt(5));
        return book;
    }
}
