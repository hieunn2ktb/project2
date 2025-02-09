package ks.training.dao.impl;


import ks.training.commom.SqlConstants;
import ks.training.dao.BookDAO;
import ks.training.exception.RecordNotFoundException;
import ks.training.model.Book;
import ks.training.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BookDaoImpl implements BookDAO {

    private boolean isBookIdExisted(int id) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SqlConstants.COUNT_BOOK)) {
            pstmt.setInt(1, id);
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
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SqlConstants.INSERT)) {
            pstmt.setString(1, book.getName());
            pstmt.setString(2, book.getAuthor());
            pstmt.setFloat(3, book.getQuantity());
            result = pstmt.executeUpdate();
        } catch (SQLException e) {

            e.printStackTrace();
        }
        return (result > 0 ? "Success" : "Failed");
    }


    @Override
    public Book findByName(String name) throws SQLException {
        Book book = new Book();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SqlConstants.SHOW_BOOK_BY_NAME)) {
            pstmt.setString(1, name);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                book.setId(rs.getInt(1));
                book.setName(rs.getString(2));
                book.setAuthor(rs.getString(3));
                book.setStatus(rs.getBoolean(4));
                book.setQuantity(rs.getInt(5));
                return book;
            }
        } catch (SQLException e) {

            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String delete(Book book) throws SQLException {
        int result = 0;
        if (isBookIdExisted(book.getId())) {
            throw new RecordNotFoundException("Order with id = " + book.getId() + " does not exist");
        }
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SqlConstants.DELETE)) {
            pstmt.setInt(1, book.getId());
            result = pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return (result > 0 ? "Success" : "Failed");
    }
}
