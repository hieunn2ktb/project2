package ks.training.dao;

import ks.training.model.Book;

import java.sql.SQLException;
import java.util.List;

public interface BookDAO {
    void save(Book book) throws SQLException;

    List<Book> findByNameAndAuthor(String name, String author) throws SQLException;

    void delete(Book book) throws SQLException;

    List<Book> findAll() throws SQLException;

    List<Book> findByName(String name) throws SQLException;



}
