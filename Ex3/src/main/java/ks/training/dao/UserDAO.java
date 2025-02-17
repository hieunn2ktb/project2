package ks.training.dao;

import ks.training.model.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface UserDAO {
    void save(Connection conn, User user) throws SQLException;

    List<User> findAll() throws SQLException;

    void delete(Connection conn, int id) throws SQLException;

    boolean isAdmin(String username) throws SQLException;

    int findIDUser(String username, String password) throws SQLException;

    boolean isUserBorrowingBook(int userId);

}
