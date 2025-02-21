package ks.training.commom;

public class SqlConstants {
    public static final String INSERT = "INSERT INTO book (name, author, quantity) VALUES(?,?,?)";
    public static final String COUNT_BOOK = "SELECT COUNT(*) from book where name = ? and author = ?";
    public static final String UPDATE_QUANTITY = "UPDATE book SET quantity = quantity + ? WHERE id = ?";
    public static final String SELECT_ID_UPDATE_QUANTITY = "SELECT id FROM book WHERE name = ? AND author = ? LIMIT 1";
    public static final String DELETE = "DELETE FROM book WHERE id = ?";
    public static final String SHOW_BOOK_BY_NAME_AND_AUTHOR = "SELECT * FROM book WHERE name = ? and author = ?";
    public static final String SHOW_BOOK_BY_NAME = "SELECT * FROM book WHERE name = ?";
    public static final String INSERT_USER = "INSERT INTO users (username, password) VALUES(?,?)";
    public static final String GET_ROLE_STUDENT = "SELECT id FROM roles WHERE role_name = 'Student'";
    public static final String INSERT_USER_ROLE = "INSERT INTO user_roles (user_id, role_id) VALUES (?, ?)";
    public static final String FIND_ALL_USERS = "SELECT * FROM users";
    public static final String DELETE_USER = "DELETE FROM users WHERE id = ?";
    public static final String DELETE_USER_ROLES_SQL = "DELETE FROM user_roles WHERE user_id = ?";
    public static final String CHECK_IF_ADMIN = "SELECT COUNT(*) FROM user_roles ur\n" +
            "\t\tJOIN roles r ON ur.role_id = r.id\n" +
            "            JOIN users u ON ur.user_id = u.id\n" +
            "            WHERE u.username = ? AND r.role_name = 'admin';\n";
    public static final String FIND_ALL_BOOK = "Select * from book";

    public static final String FIND_USER = "SELECT id From users where username = ? and password = ?";
}

