package ks.training.dao.impl;

import ks.training.commom.SqlConstants;
import ks.training.dao.UserDAO;
import ks.training.model.User;
import ks.training.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl implements UserDAO {

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
    public void save(User user) throws SQLException {
        Connection conn = null;
        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false); // Bắt đầu transaction

            try (PreparedStatement pstmt = conn.prepareStatement(SqlConstants.INSERT_USER, PreparedStatement.RETURN_GENERATED_KEYS);
                 PreparedStatement roleStmt = conn.prepareStatement(SqlConstants.GET_ROLE_STUDENT);
                 PreparedStatement userRoleStmt = conn.prepareStatement(SqlConstants.INSERT_USER_ROLE)) {

                // Thêm user vào database
                pstmt.setString(1, user.getUsername());
                pstmt.setString(2, user.getPassword());
                pstmt.executeUpdate(); // Quan trọng: phải gọi executeUpdate() trước khi lấy ID

                // Lấy ID của user vừa tạo
                int userId = -1;
                try (ResultSet userRs = pstmt.getGeneratedKeys()) {
                    if (userRs.next()) {
                        userId = userRs.getInt(1);
                    }
                }

                // Lấy role ID
                int roleId = -1;
                try (ResultSet roleResult = roleStmt.executeQuery()) {
                    if (roleResult.next()) {
                        roleId = roleResult.getInt("id");
                    }
                }

                // Thêm user_role nếu userId và roleId hợp lệ
                if (userId != -1 && roleId != -1) {
                    userRoleStmt.setInt(1, userId);
                    userRoleStmt.setInt(2, roleId);
                    userRoleStmt.executeUpdate();
                }

                // Commit transaction nếu mọi thứ thành công
                conn.commit();
            } catch (SQLException ex) {
                if (conn != null) {
                    conn.rollback(); // Rollback nếu có lỗi
                }
                throw ex;
            } finally {
                if (conn != null) {
                    conn.setAutoCommit(true); // Trả lại trạng thái ban đầu
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException("Lỗi khi lưu user", ex);
        } finally {
            if (conn != null) {
                conn.close(); // Đóng connection
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
    public void delete(int userId) throws SQLException {
        Connection conn = null;
        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false); // Bắt đầu transaction

            // 1. Xóa user trong bảng user_roles trước
            try (PreparedStatement statement = conn.prepareStatement(SqlConstants.DELETE_USER_ROLES_SQL)) {
                statement.setInt(1, userId);
                statement.executeUpdate();
            }

            // 2. Xóa user trong bảng users
            try (PreparedStatement deleteStmt = conn.prepareStatement(SqlConstants.DELETE_USER)) {
                deleteStmt.setInt(1, userId);
                deleteStmt.executeUpdate();
            }

            // Commit nếu mọi thứ thành công
            conn.commit();
        } catch (SQLException ex) {
            if (conn != null) {
                conn.rollback(); // Rollback nếu có lỗi
            }
            throw ex;
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true); // Trả lại trạng thái ban đầu
                    conn.close(); // Đóng connection
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
