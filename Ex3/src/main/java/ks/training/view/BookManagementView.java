package ks.training.view;

import ks.training.controller.BookManagementController;
import ks.training.dao.UserDAO;
import ks.training.dao.impl.UserDAOImpl;
import ks.training.exception.RecordNotFoundException;
import ks.training.model.Book;
import ks.training.model.User;
import ks.training.service.BookManagement;

import javax.swing.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;
import java.sql.SQLException;
import java.util.List;

public class BookManagementView extends JFrame {

    private static final long serialVersionUID = 1L;
    public JTextField textFieldSearchName;
    public JTextField textFieldSearchAuthor;
    public JTable table;
    public JTextField textFieldAddName;
    public JTextField textField_3;
    public JTextField textField_4;
    private JButton btnPrev, btnNext;
    public BookManagement bookManagement;
    private int currentPage = 1;
    private int itemsPerPage = 15;
    DefaultTableModel model;
    private UserDAO userDAO;
    private String currentUser;
    private int userId;

    public BookManagementView() {
        this.bookManagement = new BookManagement();
        this.userDAO = new UserDAOImpl();
        //init();
        showLoginScreen();
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
                    userId = userDAO.findIDUser(username,password);
                    if (userDAO.isAdmin(username)) {
                        this.currentUser = "Admin";
                    }else{
                        this.currentUser = "Student";
                    }
                    loginFrame.dispose();
                    init();
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



    private void init() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 835, 720);

        Action action = new BookManagementController(this);

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu mnNewMenu = new JMenu("file");
        menuBar.add(mnNewMenu);

        JMenuItem mntmNewMenuItem = new JMenuItem("Logout");
        mnNewMenu.add(mntmNewMenuItem);

        JMenuItem mntmMenuItemBook = new JMenuItem("Book");
        mnNewMenu.add(mntmMenuItemBook);

        JMenuItem mntmMenuItemBrou = new JMenuItem("Book Management");
        mnNewMenu.add(mntmMenuItemBrou);

        JMenuItem mntmNewMenuItem_1 = new JMenuItem("User");
        mnNewMenu.add(mntmNewMenuItem_1);
        getContentPane().setLayout(null);

        Box verticalBox = Box.createVerticalBox();
        verticalBox.setBounds(0, 0, 1, 1);
        getContentPane().add(verticalBox);

        JLabel labelNameSearchName = new JLabel("Name");
        labelNameSearchName.setFont(new Font("Tahoma", Font.PLAIN, 15));
        labelNameSearchName.setBounds(22, 25, 57, 56);
        getContentPane().add(labelNameSearchName);

        JLabel labelNameSearchAuthor = new JLabel("Author");
        labelNameSearchAuthor.setFont(new Font("Tahoma", Font.PLAIN, 15));
        labelNameSearchAuthor.setBounds(369, 25, 57, 56);
        getContentPane().add(labelNameSearchAuthor);

        textFieldSearchName = new JTextField();
        textFieldSearchName.setBounds(89, 33, 256, 46);
        getContentPane().add(textFieldSearchName);
        textFieldSearchName.setColumns(10);

        textFieldSearchAuthor = new JTextField();
        textFieldSearchAuthor.setColumns(10);
        textFieldSearchAuthor.setBounds(436, 33, 264, 46);
        getContentPane().add(textFieldSearchAuthor);

        JButton btnNewSearch = new JButton("Tìm Kiếm");
        btnNewSearch.addActionListener(action);
        btnNewSearch.setFont(new Font("Tahoma", Font.PLAIN, 15));
        btnNewSearch.setBounds(709, 30, 102, 46);
        getContentPane().add(btnNewSearch);

        JSeparator separator = new JSeparator();
        separator.setBounds(22, 91, 764, 8);
        getContentPane().add(separator);

        JLabel labelListBook = new JLabel("Danh Sách Book");
        labelListBook.setFont(new Font("Tahoma", Font.PLAIN, 15));
        labelListBook.setBounds(22, 99, 110, 56);
        getContentPane().add(labelListBook);

        model = new DefaultTableModel(new String[]{"ID", "Name", "Author", "Quantity"}, 0);
        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(22, 147, 774, 267);
        getContentPane().add(scrollPane);

        btnPrev = new JButton("Trang Trước");
        btnPrev.setBounds(250, 420, 120, 30);
        btnPrev.addActionListener(e -> {
            try {
                previousPage();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });
        getContentPane().add(btnPrev);

        btnNext = new JButton("Trang Sau");
        btnNext.setBounds(400, 420, 120, 30);
        btnNext.addActionListener(e -> {
            try {
                nextPage();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });
        getContentPane().add(btnNext);

        JSeparator separator_1 = new JSeparator();
        separator_1.setBounds(22, 424, 764, 8);
        getContentPane().add(separator_1);

        JLabel lblAddBook = new JLabel("Thêm sách vào thư viện");
        lblAddBook.setFont(new Font("Tahoma", Font.PLAIN, 15));
        lblAddBook.setBounds(22, 424, 172, 56);
        getContentPane().add(lblAddBook);

        JLabel LabelName_1 = new JLabel("Name");
        LabelName_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
        LabelName_1.setBounds(22, 471, 57, 56);
        getContentPane().add(LabelName_1);

        textFieldAddName = new JTextField();
        textFieldAddName.setColumns(10);
        textFieldAddName.setBounds(89, 479, 256, 46);
        getContentPane().add(textFieldAddName);

        JLabel lblAuthor_1 = new JLabel("Author");
        lblAuthor_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
        lblAuthor_1.setBounds(22, 529, 57, 56);
        getContentPane().add(lblAuthor_1);

        textField_3 = new JTextField();
        textField_3.setColumns(10);
        textField_3.setBounds(89, 537, 256, 46);
        getContentPane().add(textField_3);

        JLabel LabelName_1_1 = new JLabel("Số lượng");
        LabelName_1_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
        LabelName_1_1.setBounds(410, 471, 65, 56);
        getContentPane().add(LabelName_1_1);

        textField_4 = new JTextField();
        textField_4.setColumns(10);
        textField_4.setBounds(485, 479, 256, 46);
        getContentPane().add(textField_4);

        JButton btnAddBook = new JButton("Thêm Vào Thư Viện");
        btnAddBook.addActionListener(action);
        btnAddBook.setFont(new Font("Tahoma", Font.PLAIN, 15));
        btnAddBook.setBounds(528, 534, 172, 46);
        getContentPane().add(btnAddBook);

        JButton btnDeleteBook = new JButton("Xoá Sách");
        btnDeleteBook.addActionListener(action);
        btnDeleteBook.setFont(new Font("Tahoma", Font.PLAIN, 15));
        btnDeleteBook.setBounds(233, 595, 172, 46);
        getContentPane().add(btnDeleteBook);


        JButton btnBorrowBooks = new JButton("Mượn Sách");
        btnBorrowBooks.addActionListener(action);
        btnBorrowBooks.setFont(new Font("Tahoma", Font.PLAIN, 15));
        btnBorrowBooks.setBounds(425, 595, 172, 46);
        getContentPane().add(btnBorrowBooks);

        JButton btnReturnBook = new JButton("Trả Sách");
        btnReturnBook.addActionListener(action);
        btnReturnBook.setFont(new Font("Tahoma", Font.PLAIN, 15));
        btnReturnBook.setBounds(624, 595, 172, 46);
        getContentPane().add(btnReturnBook);

        if (!"Admin".equalsIgnoreCase(currentUser)) {
            btnAddBook.setEnabled(false);
            btnDeleteBook.setEnabled(false);
        } else {
            btnBorrowBooks.setEnabled(false);
            btnReturnBook.setEnabled(false);
        }
        JButton btnExportFileExcel = new JButton("Xuất File Excel");
        btnExportFileExcel.addActionListener(action);
        btnExportFileExcel.setFont(new Font("Tahoma", Font.PLAIN, 15));
        btnExportFileExcel.setBounds(32, 595, 172, 46);
        getContentPane().add(btnExportFileExcel);

        this.setVisible(true);
        try {
            updateTable();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi tải danh sách sách!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void addBook(Book book) throws SQLException {
        bookManagement.addBook(book);
        updateTable();
    }

    private void updateTable() throws SQLException {
        List<Book> books = bookManagement.findAll();
        model.setRowCount(0);
        int start = (currentPage - 1) * itemsPerPage;
        int end = Math.min(start + itemsPerPage, books.size());
        for (int i = start; i < end; i++) {
            Book book = books.get(i);
            model.addRow(new Object[]{book.getId(), book.getName(), book.getAuthor(), book.getQuantity()});
        }
        updateButtons();
    }

    private void updateButtons() throws SQLException {
        List<Book> books = bookManagement.findAll();
        btnPrev.setEnabled(currentPage > 1);
        btnNext.setEnabled(currentPage * itemsPerPage < books.size());
    }

    private void previousPage() throws SQLException {

        if (currentPage > 1) {
            currentPage--;
            updateTable();
        }
    }

    private void nextPage() throws SQLException {
        List<Book> books = bookManagement.findAll();
        if (currentPage * itemsPerPage < books.size()) {
            currentPage++;
            updateTable();
        }
    }

    public void deleteBook() throws SQLException {
        DefaultTableModel defaultTableModel = (DefaultTableModel) table.getModel();
        int row = table.getSelectedRow();
        int number = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn xóa dòng đã chọn");
        if (number == JOptionPane.YES_OPTION) {
            this.bookManagement.deleteBook(getBook());
            defaultTableModel.removeRow(row);
        }
    }

    public Book getBook() {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        int row = table.getSelectedRow();
        int id = Integer.valueOf(model.getValueAt(row, 0) + "");
        String name = model.getValueAt(row, 1) + "";
        String author = model.getValueAt(row, 2) + "";
        int quantity = Integer.valueOf(model.getValueAt(row, 3) + "");
        return new Book(id, name, author, quantity);
    }

    public void searchBook() {
        try {
            String name = textFieldSearchName.getText();
            String author = textFieldSearchAuthor.getText();

            if (name.isEmpty() && author.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Bạn chưa nhập thông tin tìm kiếm!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }

            List<Book> searchResults;
            if (author.isEmpty()) {
                searchResults = bookManagement.searchBookByName(name);
            } else {
                searchResults = bookManagement.searchBookByNameAndAuthor(name, author);
            }

            updateTableWithResults(searchResults);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi tìm kiếm sách!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void updateTableWithResults(List<Book> books) {
        model.setRowCount(0);
        for (Book book : books) {
            model.addRow(new Object[]{book.getId(), book.getName(), book.getAuthor(), book.getQuantity()});
        }
    }

    public void borrowBook() throws SQLException {
        int number = JOptionPane.showConfirmDialog(this, "Ấn 'Yes' để mượn sách");
        if (number == JOptionPane.YES_OPTION) {
           boolean result = this.bookManagement.borrowBook(userId,getBook().getId());
            updateTable();
            if(!result){
                JOptionPane.showMessageDialog(null, "Sách không còn sẵn có để mượn.");
            }

        }
    }
}