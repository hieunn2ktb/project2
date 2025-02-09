package ks.training.dao;

import ks.training.model.User;

import java.sql.SQLException;
import java.util.List;

public interface UserDAO {
    String save(User user) throws SQLException;

    List<User> findAll(int id) throws SQLException;

    String delete(int id, String name) throws SQLException;
}
