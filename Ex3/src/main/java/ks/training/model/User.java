package ks.training.model;

import java.util.List;

public class User {
    private int id;
    private String username;
    private String password;
    private List<Role> role;
    private List<Book> books;

    public User(int id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public List<Role> getRole() {
        return role;
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User() {
    }

    public void setRole(List<Role> role) {
        this.role = role;
    }

    public User(int id, String username, String password, List<Role> role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                '}';
    }
}
