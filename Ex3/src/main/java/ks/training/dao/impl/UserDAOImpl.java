package ks.training.dao.impl;

import ks.training.commom.SqlConstants;
import ks.training.dao.UserDAO;
import ks.training.model.User;
import ks.training.util.DatabaseConnection;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl implements UserDAO {



    public int findIDUser(String username, String password) throws SQLException {
        int id = -1;
        String storedHash = null;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement("SELECT id, password FROM users WHERE username = ?")) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                id = rs.getInt("id");
                storedHash = rs.getString("password");
            }
        }

        if (storedHash != null && BCrypt.checkpw(password, storedHash)) {
            return id;
        } else {
            return -1;
        }
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
    public void save(Connection conn, User user) throws SQLException {
        try (PreparedStatement pstmt = conn.prepareStatement(SqlConstants.INSERT_USER, PreparedStatement.RETURN_GENERATED_KEYS);
             PreparedStatement roleStmt = conn.prepareStatement(SqlConstants.GET_ROLE_STUDENT);
             PreparedStatement userRoleStmt = conn.prepareStatement(SqlConstants.INSERT_USER_ROLE)) {

            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getPassword());
            pstmt.executeUpdate();

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
    }

    @Override
    public List<User> findAll() throws SQLException {
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

    public boolean isUserBorrowingBook(int userId) {
        String query = "SELECT COUNT(*) FROM borrow WHERE user_id = ? AND return_date IS NULL";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void delete(Connection conn, int id) throws SQLException {
        try (PreparedStatement pstmt = conn.prepareStatement(SqlConstants.DELETE_USER)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
    }

}
