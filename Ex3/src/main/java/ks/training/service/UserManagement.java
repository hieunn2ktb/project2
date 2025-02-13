package ks.training.service;

import ks.training.dao.UserDAO;
import ks.training.dao.impl.UserDAOImpl;
import ks.training.model.User;

import java.sql.SQLException;
import java.util.List;

public class UserManagement {
    public UserDAO userDAO;

    public UserManagement() {
        this.userDAO = new UserDAOImpl();
    }

    public List<User> findAll() throws SQLException{
        return userDAO.findAll();
    }
    public void delete(int id) throws SQLException{
         userDAO.delete(id);
    };
    public boolean isUserBorrowingBook(int userId){
        return userDAO.isUserBorrowingBook(userId);
    }
    public int findIDUser(String username,String password) throws SQLException {
        return userDAO.findIDUser(username,password);
    };
    public boolean isAdmin(String username) throws SQLException {
        return userDAO.isAdmin(username);
    };
    public void save(User user) throws SQLException {
        userDAO.save(user);
    }
}
