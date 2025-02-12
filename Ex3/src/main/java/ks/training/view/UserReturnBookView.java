package ks.training.view;

import ks.training.model.Book;
import ks.training.model.BorrowDetail;
import ks.training.service.AdminManagement;

import java.awt.EventQueue;
import java.awt.Font;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JDesktopPane;

public class UserReturnBookView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	DefaultTableModel model;
	public JTable table;
	private JButton btnPrev, btnNext, btnReturnBook;
    private AdminManagement adminManagement;
    public int currentPage = 1;
    public int itemsPerPage = 15;


	public UserReturnBookView() {
        this.adminManagement = new AdminManagement();
		init();
	}

    private void init() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 827, 553);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        //Action action = new BookManagementController(this);

        setContentPane(contentPane);
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

        JLabel labelListBookBorrow = new JLabel("Sách đã được mượn");
        labelListBookBorrow.setFont(new Font("Tahoma", Font.PLAIN, 15));
        labelListBookBorrow.setBounds(27, 11, 160, 56);
        getContentPane().add(labelListBookBorrow);

        model = new DefaultTableModel(new String[]{"ID", "Name", "Author", "Quantity"}, 0);
        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(27, 59, 774, 267);
        getContentPane().add(scrollPane);

        btnPrev = new JButton("Trang Trước");
        btnPrev.setBounds(250, 340, 120, 30);
        btnPrev.addActionListener(e -> {
            try {
                previousPage();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });
        getContentPane().add(btnPrev);

        btnNext = new JButton("Trang Sau");
        btnNext.setBounds(400, 340, 120, 30);
        btnNext.addActionListener(e -> {
            try {
                nextPage();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });
        getContentPane().add(btnNext);

        btnReturnBook = new JButton("Trả sách");
        btnReturnBook.addActionListener(action);
        btnReturnBook.setFont(new Font("Tahoma", Font.PLAIN, 15));
        btnReturnBook.setBounds(302, 408, 172, 46);
        getContentPane().add(btnReturnBook);
    }

    private void previousPage() throws SQLException {

        if (currentPage > 1) {
            currentPage--;
            updateTable();
        }
    }

	private void nextPage() throws SQLException {
        List<Book> books = adminManagement.listBook();
        if (currentPage * itemsPerPage < books.size()) {
            currentPage++;
            updateTable();
        }
    }
    private void updateTable() throws SQLException {
        List<BorrowDetail> books = adminManagement.getBooksBeingBorrowed();
        model.setRowCount(0);
        int start = (currentPage - 1) * itemsPerPage;
        int end = Math.min(start + itemsPerPage, books.size());
        for (int i = start; i < end; i++) {
            BorrowDetail borrowDetail = books.get(i);
            model.addRow(new Object[]{borrowDetail.getBorrowId(), borrowDetail.getUserName(), borrowDetail.getBookName(), borrowDetail.getAuthor(), borrowDetail.getBorrowDate(), borrowDetail.getReturnDate()});
        }
        updateButtons();
    }

    private void updateButtons() throws SQLException {
        List<BorrowDetail> books = adminManagement.getBooksBeingBorrowed();
        btnPrev.setEnabled(currentPage > 1);
        btnNext.setEnabled(currentPage * itemsPerPage < books.size());
    }

}
