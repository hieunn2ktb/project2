package ks.training.dao.impl;


import ks.training.commom.SqlConstants;
import ks.training.dao.BookDAO;
import ks.training.exception.RecordNotFoundException;
import ks.training.model.Book;
import ks.training.util.DatabaseConnection;

import javax.xml.crypto.Data;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookDaoImpl implements BookDAO {

    private boolean isBookIdExisted(String name, String author) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SqlConstants.COUNT_BOOK)) {
            pstmt.setString(1, name);
            pstmt.setString(2, author);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public String save(Book book) throws SQLException {
        int result = 0;
        if (isBookIdExisted(book.getName(), book.getAuthor())) {
            updateQuantity(book.getName(), book.getQuantity(), book.getAuthor());
        } else {
            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(SqlConstants.INSERT)) {
                pstmt.setString(1, book.getName());
                pstmt.setString(2, book.getAuthor());
                pstmt.setFloat(3, book.getQuantity());
                result = pstmt.executeUpdate();
            } catch (SQLException e) {

                e.printStackTrace();
            }
        }
        return (result > 0 ? "Success" : "Failed");
    }


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
    public List<Book> findByNameAndAuthor(String name, String author) throws SQLException {
        List<Book> books = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SqlConstants.SHOW_BOOK_BY_NAME_AND_AUTHOR)) {
            pstmt.setString(1, name);
            pstmt.setString(2, author);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Book book = new Book();
                book.setId(rs.getInt(1));
                book.setName(rs.getString(2));
                book.setAuthor(rs.getString(3));
                book.setStatus(rs.getBoolean(4));
                book.setQuantity(rs.getInt(5));
                books.add(book);
            }
        } catch (SQLException e) {

            e.printStackTrace();
        }
        return books;
    }


    @Override
    public List<Book> findByName(String name) throws SQLException {
        List<Book> books = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SqlConstants.SHOW_BOOK_BY_NAME)) {
            pstmt.setString(1, name);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Book book = new Book();
                book.setId(rs.getInt(1));
                book.setName(rs.getString(2));
                book.setAuthor(rs.getString(3));
                book.setStatus(rs.getBoolean(4));
                book.setQuantity(rs.getInt(5));
                books.add(book);
            }
        } catch (SQLException e) {

            e.printStackTrace();
        }
        return books;
    }

    @Override
    public String delete(Book book) throws SQLException {
        int result = 0;
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SqlConstants.DELETE)) {
            pstmt.setInt(1, book.getId());
            result = pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return (result > 0 ? "Success" : "Failed");
    }

    @Override
    public List<Book> findAll() throws SQLException {
        List<Book> books = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SqlConstants.FIND_ALL_BOOK)) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Book book = new Book();
                book.setId(rs.getInt(1));
                book.setName(rs.getString(2));
                book.setAuthor(rs.getString(3));
                book.setStatus(rs.getBoolean(4));
                book.setQuantity(rs.getInt(5));
                books.add(book);
            }
        } catch (SQLException e) {

            e.printStackTrace();
        }
        return books;
    }
}
