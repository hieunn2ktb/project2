package ks.training.dao.impl;

import ks.training.commom.SqlConstants;
import ks.training.dao.UserDAO;
import ks.training.model.Book;
import ks.training.model.User;
import ks.training.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl implements UserDAO {

    public boolean isUserExisted(String username,String password) throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SqlConstants.COUNT_USER)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }
        }
    }

    public int findIDUser(String username,String password) throws SQLException {
        int id = 0;
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SqlConstants.FIND_USER)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
               id = rs.getInt(1);
            }
        }
        return id;
    }

    public boolean isAdmin(String username) throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SqlConstants.CHECK_IF_ADMIN)) {

            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public String save(User user) throws SQLException {
        int result = 0;
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SqlConstants.INSERT_USER, PreparedStatement.RETURN_GENERATED_KEYS);
             PreparedStatement roleStmt = conn.prepareStatement(SqlConstants.GET_ROLE_STUDENT);
             PreparedStatement userRoleStmt = conn.prepareStatement(SqlConstants.INSERT_USER_ROLE)) {

            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getPassword());
            result = pstmt.executeUpdate(); // Gọi trước khi lấy ID

            int userId = -1;
            try (ResultSet userRs = pstmt.getGeneratedKeys()) {
                if (userRs.next()) {
                    userId = userRs.getInt(1);
                }
            }

            int roleId = -1;
            try (ResultSet roleResult = roleStmt.executeQuery()) {
                if (roleResult.next()) {
                    roleId = roleResult.getInt("id");
                }
            }

            if (userId != -1 && roleId != -1) {
                userRoleStmt.setInt(1, userId);
                userRoleStmt.setInt(2, roleId);
                userRoleStmt.executeUpdate();
            }

        }
        return (result > 0 ? "Success" : "Failed");
    }

    @Override
    public List<User> findAll(int userId) throws SQLException {
        List<User> users = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SqlConstants.FIND_ALL_USERS);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                users.add(user);
            }
        }
        return users;
    }

    @Override
    public String delete(int userId, String name) throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement deleteStmt = conn.prepareStatement(SqlConstants.DELETE_USER)) {
            deleteStmt.setInt(1, userId);
            return deleteStmt.executeUpdate() > 0 ? "Success" : "Failed";
        }
    }
}
