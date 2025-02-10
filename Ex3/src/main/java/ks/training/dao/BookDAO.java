package ks.training.dao;

import ks.training.model.Book;

import java.sql.SQLException;
import java.util.List;

public interface BookDAO {
     String save(Book book) throws SQLException;
     Book findByName(String name) throws SQLException;
     String delete(Book book) throws SQLException;
     List<Book> findAll() throws SQLException;
}
