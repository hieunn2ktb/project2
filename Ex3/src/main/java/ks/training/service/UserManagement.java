package ks.training.service;

import ks.training.dao.UserDAO;
import ks.training.dao.impl.UserDAOImpl;
import ks.training.model.User;
import ks.training.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class UserManagement {
    public UserDAO userDAO;

    public UserManagement() {
        this.userDAO = new UserDAOImpl();
    }

    public List<User> findAll() throws SQLException {
        return userDAO.findAll();
    }

    public void delete(int id) throws SQLException {
        Connection conn = null;
        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false); // Bắt đầu transaction

            userDAO.delete(conn, id); // Truyền Connection vào DAO

            conn.commit(); // Commit nếu không có lỗi
        } catch (SQLException e) {
            if (conn != null) {
                conn.rollback(); // Rollback nếu có lỗi
            }
            throw e; // Ném lỗi để xử lý bên trên
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true); // Khôi phục trạng thái AutoCommit
                    conn.close(); // Đóng kết nối
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }



    public boolean isUserBorrowingBook(int userId) {
        return userDAO.isUserBorrowingBook(userId);
    }

    public int findIDUser(String username, String password) throws SQLException {
        return userDAO.findIDUser(username, password);
    }


    public boolean isAdmin(String username) throws SQLException {
        return userDAO.isAdmin(username);
    }


    public void save(User user) throws SQLException {
        Connection conn = null;
        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false); // Bắt đầu transaction

            userDAO.save(conn, user); // Truyền Connection vào DAO

            conn.commit(); // Commit nếu không có lỗi
        } catch (SQLException e) {
            if (conn != null) {
                conn.rollback(); // Rollback nếu có lỗi
            }
            throw e; // Ném lỗi để xử lý bên trên
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true); // Khôi phục trạng thái AutoCommit
                    conn.close(); // Đóng kết nối
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

}
