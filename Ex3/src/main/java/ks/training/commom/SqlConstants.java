package ks.training.commom;

public class SqlConstants {
    public static final String INSERT = "INSERT INTO book (name, author, quantity) VALUES(?,?,?)";
    public static final String COUNT_BOOK = "SELECT COUNT(id) FROM book WHERE id = ?";
    public static final String DELETE = "DELETE FROM book WHERE id = ? AND status = 0";
    public static final String SHOW_BOOK_BY_NAME = "SELECT * FROM book WHERE name = ?";
    public static final String COUNT_USER = "SELECT COUNT(id) FROM users WHERE username = ? and password = ?;";
    public static final String INSERT_USER = "INSERT INTO users (username, password) VALUES(?,?)";
    public static final String GET_ROLE_STUDENT = "SELECT id FROM roles WHERE role_name = 'Student'";
    public static final String INSERT_USER_ROLE = "INSERT INTO user_roles (user_id, role_id) VALUES (?, ?)";
    public static final String FIND_ALL_USERS = "SELECT * FROM users";
    public static final String DELETE_USER = "DELETE FROM users WHERE id = ?";
    public static final String CHECK_IF_ADMIN = "SELECT COUNT(*) FROM user_roles ur\n" +
            "\t\tJOIN roles r ON ur.role_id = r.id\n" +
            "            JOIN users u ON ur.user_id = u.id\n" +
            "            WHERE u.username = ? AND r.role_name = 'admin';\n";
    public static final String CHECK_IF_USER = "SELECT ";
    public static final String FIND_ALL_BOOK = "SELECT * FROM book";

}

