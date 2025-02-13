package ks.training.view;
import ks.training.controller.BookManagementController;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import ks.training.model.Book;
import ks.training.service.BookManagement;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class BookManagementView extends JFrame {

    private static final long serialVersionUID = 1L;
    private JTextField textFieldSearchName;
    private JTextField textFieldSearchAuthor;
    private JTable table;
    private JTextField textFieldAddName;
    private JTextField textFieldAuthor;
    private JTextField textFieldNum;
    private JButton btnPrev, btnNext;
    private BookManagement bookManagement;
    private int currentPage = 1;
    private final int itemsPerPage = 15;
    private DefaultTableModel model;
    private int userId;
    private String currentUser;



    public BookManagementView(int userId,String currentUser) {
        this.bookManagement = new BookManagement();
        this.userId = userId;
        this.currentUser = currentUser;
        init();
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
        mntmNewMenuItem.addActionListener(e -> {
            this.dispose();
            new LoginView();
        });


        JMenuItem mntmMenuItemBrou = new JMenuItem("Admin");
        if (!"Admin".equals(currentUser)){
            mntmMenuItemBrou.setVisible(false);
        }
        mnNewMenu.add(mntmMenuItemBrou);
        mntmMenuItemBrou.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new UserManagementView();
            }
        });
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

        JLabel LabelNameAddBook = new JLabel("Name");
        LabelNameAddBook.setFont(new Font("Tahoma", Font.PLAIN, 15));
        LabelNameAddBook.setBounds(22, 471, 57, 56);
        getContentPane().add(LabelNameAddBook);

        textFieldAddName = new JTextField();
        textFieldAddName.setColumns(10);
        textFieldAddName.setBounds(89, 479, 256, 46);
        getContentPane().add(textFieldAddName);

        JLabel lblAuthorAddBook = new JLabel("Author");
        lblAuthorAddBook.setFont(new Font("Tahoma", Font.PLAIN, 15));
        lblAuthorAddBook.setBounds(22, 529, 57, 56);
        getContentPane().add(lblAuthorAddBook);

        textFieldAuthor = new JTextField();
        textFieldAuthor.setColumns(10);
        textFieldAuthor.setBounds(89, 537, 256, 46);
        getContentPane().add(textFieldAuthor);

        JLabel LabelNameNum = new JLabel("Số lượng");
        LabelNameNum.setFont(new Font("Tahoma", Font.PLAIN, 15));
        LabelNameNum.setBounds(410, 471, 65, 56);
        getContentPane().add(LabelNameNum);

        textFieldNum = new JTextField();
        textFieldNum.setColumns(10);
        textFieldNum.setBounds(485, 479, 256, 46);
        getContentPane().add(textFieldNum);

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
        btnExportFileExcel.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Chọn nơi lưu file Excel");
            fileChooser.setSelectedFile(new File("danh_sach_muon_sach.xlsx"));

            int userSelection = fileChooser.showSaveDialog(null);
            if (userSelection == JFileChooser.APPROVE_OPTION) {
                String filePath = fileChooser.getSelectedFile().getAbsolutePath();
                if (!filePath.endsWith(".xlsx")) {
                    filePath += ".xlsx";
                }
                exportToExcel(table, filePath);
            }
        });

        try {
            updateTable();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi tải danh sách sách!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
        this.setVisible(true);
    }

    public void addBook() throws SQLException {
        try {
            String name = this.textFieldAddName.getText();
            String author = this.textFieldAuthor.getText();
            String result = this.textFieldNum.getText();
            if (name.isEmpty() || author.isEmpty() || result.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please enter all information!");
                return;
            }
            int quantity = Integer.parseInt(result);
            Book book = new Book(name,author,quantity);
            bookManagement.addBook(book);
            updateTable();
            JOptionPane.showMessageDialog(null, "Add book successfully!");
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
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
    public void actionPerformed() {
        new UserReturnBookView(userId).setVisible(true);
    }

    public static void exportToExcel(JTable table, String filePath) {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Data");

        TableModel model = table.getModel();

        Row headerRow = sheet.createRow(0);
        for (int col = 0; col < model.getColumnCount(); col++) {
            Cell cell = headerRow.createCell(col);
            cell.setCellValue(model.getColumnName(col));
        }

        for (int row = 0; row < model.getRowCount(); row++) {
            Row excelRow = sheet.createRow(row + 1);
            for (int col = 0; col < model.getColumnCount(); col++) {
                Cell cell = excelRow.createCell(col);
                cell.setCellValue(model.getValueAt(row, col).toString());
            }
        }

        try (FileOutputStream fileOut = new FileOutputStream(new File(filePath))) {
            workbook.write(fileOut);
            workbook.close();
            JOptionPane.showMessageDialog(null, "Xuất file Excel thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Lỗi khi xuất file Excel: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

}