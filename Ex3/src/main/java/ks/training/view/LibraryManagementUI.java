package ks.training.view;

import ks.training.dao.UserDAO;
import ks.training.dao.impl.UserDAOImpl;
import ks.training.model.User;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class LibraryManagementUI extends JFrame{
    private UserDAO userDAO;
    private String currentUser;

    public LibraryManagementUI() {
        this.userDAO = new UserDAOImpl();
    }

    public void showLoginScreen() {
        JFrame loginFrame = new JFrame("Login");
        loginFrame.setSize(300, 250);
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        loginFrame.add(panel);
        placeLoginComponents(panel, loginFrame);

        loginFrame.setVisible(true);
    }

    private void placeLoginComponents(JPanel panel, JFrame loginFrame) {
        panel.setLayout(new GridLayout(4, 2));

        JLabel userLabel = new JLabel("User:");
        JTextField userText = new JTextField(20);
        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordText = new JPasswordField(20);
        JButton loginButton = new JButton("Login");
        JButton registerButton = new JButton("Register");

        panel.add(userLabel);
        panel.add(userText);
        panel.add(passwordLabel);
        panel.add(passwordText);
        panel.add(loginButton);
        panel.add(registerButton);


        loginButton.addActionListener(e -> {
            String username = userText.getText();
            String password = new String(passwordText.getPassword());
            try {
                if (userDAO.isUserExisted(username, password)) {
                    if (userDAO.isAdmin(username)) {
                        this.currentUser = "Admin";
                    }else{
                        this.currentUser = "Student";
                    }
                    loginFrame.dispose();
                    createAndShowGUI();
                } else {
                    JOptionPane.showMessageDialog(loginFrame, "Invalid credentials", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });

        registerButton.addActionListener(e -> showRegisterScreen());
    }

    private void showRegisterScreen() {
        JFrame registerFrame = new JFrame("Register");
        registerFrame.setSize(300, 200);

        JPanel panel = new JPanel(new GridLayout(4, 2));

        JLabel userLabel = new JLabel("User:");
        JTextField userText = new JTextField(20);
        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordText = new JPasswordField(20);
        JButton registerButton = new JButton("Register");
        JLabel RePasswordLabel = new JLabel("Re-Password:");
        JPasswordField RePasswordText = new JPasswordField(20);

        panel.add(userLabel);
        panel.add(userText);
        panel.add(passwordLabel);
        panel.add(passwordText);
        panel.add(RePasswordLabel);
        panel.add(RePasswordText);
        panel.add(registerButton);

        registerButton.addActionListener(e -> {
            String username = userText.getText();
            String password = new String(passwordText.getPassword());
            String rePassword = new String(RePasswordText.getPassword());
            if (username.isEmpty() || password.isEmpty() || rePassword.isEmpty()) {
                JOptionPane.showMessageDialog(registerFrame, "Username and password cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            try {
                if (password.equals(rePassword)) {
                    userDAO.save(new User(username, password));
                    JOptionPane.showMessageDialog(registerFrame, "Registration Successful", "Success", JOptionPane.INFORMATION_MESSAGE);
                    registerFrame.dispose();
                }else{
                    JOptionPane.showMessageDialog(registerFrame, "Password and Re-Password must be same!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }

        });

        registerFrame.add(panel);
        registerFrame.setVisible(true);
    }

    private void createAndShowGUI() {
        JFrame frame = new JFrame("Library Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        JTabbedPane tabbedPane = new JTabbedPane();


        // Tabs
        tabbedPane.addTab("Books", createBookPanel());
        if ("Admin".equals(currentUser)) {
            tabbedPane.addTab("Users", createUserPanel());
        }
        tabbedPane.addTab("Borrow/Return", createBorrowPanel());
        tabbedPane.addTab("Export Data", createExportPanel());
        tabbedPane.addTab("Logout", logoutButtonPanel(frame));


        frame.add(tabbedPane);
        frame.setVisible(true);
    }

    public JPanel logoutButtonPanel(JFrame mainFrame) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT)); // Căn phải
        JButton logoutButton = new JButton("Logout");
        panel.add(logoutButton);

        logoutButton.addActionListener(e -> {
            mainFrame.dispose(); // Đóng cửa sổ chính
            showLoginScreen();   // Hiển thị lại màn hình đăng nhập
        });

        return panel;
    }


    private JPanel createBookPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        // Bảng hiển thị sách
        JTable bookTable = new JTable();
        JScrollPane tableScrollPane = new JScrollPane(bookTable);

        // Các nút chức năng (Add, Delete, Search)
        JButton addButton = new JButton("Add Book");
        JButton deleteButton = new JButton("Delete Book");
        JTextField searchField = new JTextField(20);
        JButton searchButton = new JButton("Search");

        // Các trường thông tin sách bên dưới
        JPanel formPanel = new JPanel(new GridLayout(3, 2));
        JTextField titleField = new JTextField(20);
        JTextField authorField = new JTextField(20);
        JTextField quantityField = new JTextField(20);



        formPanel.add(new JLabel("Name:"));
        formPanel.add(titleField);
        formPanel.add(new JLabel("Author:"));
        formPanel.add(authorField);
        formPanel.add(new JLabel("Quantity:"));
        formPanel.add(quantityField);

        // Panel phía trên chứa các nút Add, Delete, Search
        JPanel topPanel = new JPanel();
        topPanel.add(new JLabel("Search: "));
        topPanel.add(searchField);
        topPanel.add(searchButton);
        topPanel.add(addButton);
        topPanel.add(deleteButton);

        // Disable Add and Delete nếu không phải Admin
        if (!"Admin".equalsIgnoreCase(currentUser)) {
            addButton.setEnabled(false);
            deleteButton.setEnabled(false);
        }

        // Lắng nghe sự kiện cho các nút
        addButton.addActionListener(e -> {
            String title = titleField.getText();
            String author = authorField.getText();
            String quantity = quantityField.getText();


            // Kiểm tra thông tin nhập vào và gọi DAO để thêm sách vào cơ sở dữ liệu
            if (title.isEmpty() || author.isEmpty() || author.isEmpty() || quantity.isEmpty()) {
                JOptionPane.showMessageDialog(panel, "All fields must be filled out!", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                // Giả sử bạn có phương thức để thêm sách vào cơ sở dữ liệu
                // Gọi phương thức để thêm sách vào cơ sở dữ liệu
                // bookDAO.addBook(new Book(title, author, genre, year));
                JOptionPane.showMessageDialog(panel, "Book added successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                // Làm mới bảng sách sau khi thêm thành công
                // refreshBookTable();
            }
        });

        deleteButton.addActionListener(e -> {
            int selectedRow = bookTable.getSelectedRow();
            if (selectedRow >= 0) {
                // Giả sử bạn có phương thức để xóa sách khỏi cơ sở dữ liệu
                // bookDAO.deleteBook(bookTable.getValueAt(selectedRow, 0)); // Xóa sách dựa vào ID hoặc tên
                JOptionPane.showMessageDialog(panel, "Book deleted successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                // Làm mới bảng sách sau khi xóa thành công
                // refreshBookTable();
            } else {
                JOptionPane.showMessageDialog(panel, "Please select a book to delete", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Thêm các phần tử vào panel chính
        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(tableScrollPane, BorderLayout.CENTER);
        panel.add(formPanel, BorderLayout.SOUTH);

        return panel;
    }


    private JPanel createUserPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JButton addUserButton = new JButton("Add User");
        JButton deleteUserButton = new JButton("Delete User");
        JTable userTable = new JTable();

        JPanel topPanel = new JPanel();
        topPanel.add(addUserButton);
        topPanel.add(deleteUserButton);

        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(new JScrollPane(userTable), BorderLayout.CENTER);

        return panel;
    }

    private JPanel createBorrowPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JButton borrowButton = new JButton("Borrow Book");
        JButton returnButton = new JButton("Return Book");
        JTable borrowTable = new JTable();

        JPanel topPanel = new JPanel();
        topPanel.add(borrowButton);
        topPanel.add(returnButton);

        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(new JScrollPane(borrowTable), BorderLayout.CENTER);

        return panel;
    }

    private JPanel createExportPanel() {
        JPanel panel = new JPanel();
        JButton exportButton = new JButton("Export to Excel");
        panel.add(exportButton);
        return panel;
    }
}
