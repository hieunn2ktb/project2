package ks.training.view;

import ks.training.controller.AdminManagementController;
import ks.training.model.BorrowDetail;
import ks.training.service.AdminManagement;
import org.jdatepicker.impl.JDatePickerImpl;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class AdminView extends JFrame {

    public static final long serialVersionUID = 1L;
    public JPanel contentPane;
    DefaultTableModel model;
    public JTable table;
    public JButton btnPrev, btnNext, btnDeleteUser;
    public JTextField textFieldSearchStartDay,textFieldSearchEndDay;
    public AdminManagement adminManagement;
    public int currentPage = 1;
    public int itemsPerPage = 15;


    public AdminView() {
        this.adminManagement = new AdminManagement();
        init();
    }

    private void init() {
        setBounds(100, 100, 827, 553);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        ActionListener action = new AdminManagementController(this);

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

        JLabel LabelName = new JLabel("Start Day");
        LabelName.setFont(new Font("Tahoma", Font.PLAIN, 15));
        LabelName.setBounds(22, 25, 88, 56);
        getContentPane().add(LabelName);

        JLabel lblAuthor = new JLabel("End Day");
        lblAuthor.setFont(new Font("Tahoma", Font.PLAIN, 15));
        lblAuthor.setBounds(369, 25, 57, 56);
        getContentPane().add(lblAuthor);

        textFieldSearchStartDay = new JTextField();
        textFieldSearchStartDay.setBounds(89, 33, 256, 46);
        getContentPane().add(textFieldSearchStartDay);
        textFieldSearchStartDay.setColumns(10);

        textFieldSearchEndDay = new JTextField();
        textFieldSearchEndDay.setColumns(10);
        textFieldSearchEndDay.setBounds(436, 33, 264, 46);
        getContentPane().add(textFieldSearchEndDay);

        JButton btnNewSearch = new JButton("Tìm Kiếm");
        btnNewSearch.addActionListener(action);
        btnNewSearch.setFont(new Font("Tahoma", Font.PLAIN, 15));
        btnNewSearch.setBounds(709, 30, 102, 46);
        getContentPane().add(btnNewSearch);


        JLabel labelListUserBorrow = new JLabel("Danh Sách User Mượn Sách");
        labelListUserBorrow.setFont(new Font("Tahoma", Font.PLAIN, 15));
        labelListUserBorrow.setBounds(27, 67, 213, 56);
        getContentPane().add(labelListUserBorrow);

        model = new DefaultTableModel(new String[]{"Borrow Id", "User Name", "Book Name", "Author","Borrow Date"}, 0);
        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(27, 115, 774, 267);
        getContentPane().add(scrollPane);

        btnPrev = new JButton("Trang Trước");
        btnPrev.setBounds(250, 396, 120, 30);
        btnPrev.addActionListener(e -> {
            try {
                previousPage();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });
        getContentPane().add(btnPrev);

        btnNext = new JButton("Trang Sau");
        btnNext.setBounds(400, 396, 120, 30);
        btnNext.addActionListener(e -> {
            try {
                nextPage();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });
        getContentPane().add(btnNext);

        try {
            updateTable();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi tải danh sách sách!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
        this.setVisible(true);
    }

    private void previousPage() throws SQLException {
        if (currentPage > 1) {
            currentPage--;
            updateTable();
        }
    }

    private void nextPage() throws SQLException {
        List<BorrowDetail> books = adminManagement.getBooksBeingBorrowed();
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
            model.addRow(new Object[]{borrowDetail.getBorrowId(), borrowDetail.getUserName(), borrowDetail.getBookName(), borrowDetail.getAuthor(), borrowDetail.getBorrowDate()});
        }
        updateButtons();
    }

    private void updateButtons() throws SQLException {
        List<BorrowDetail> books = adminManagement.getBooksBeingBorrowed();
        btnPrev.setEnabled(currentPage > 1);
        btnNext.setEnabled(currentPage * itemsPerPage < books.size());
    }
    private void updateTableWithResults(List<BorrowDetail> books) {
        model.setRowCount(0);
        for (BorrowDetail book : books) {
            model.addRow(new Object[]{book.getBorrowId(), book.getUserName(), book.getBookName(),book.getAuthor(), book.getBorrowDate()});
        }
    }
    public void searchUserByBorrowDay() {
        try {
            LocalDate startDate = LocalDate.parse(textFieldSearchStartDay.getText(), DateTimeFormatter.ISO_DATE);
            LocalDate endDate = LocalDate.parse(textFieldSearchEndDay.getText(), DateTimeFormatter.ISO_DATE);

            if (startDate.isAfter(endDate)) {
                JOptionPane.showMessageDialog(this, "Ngày bắt đầu không thể lớn hơn ngày kết thúc!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            updateTableWithResults(adminManagement.searchBorrowHistory(startDate,endDate));

        } catch (DateTimeParseException ex) {
            JOptionPane.showMessageDialog(this, "Định dạng ngày không hợp lệ!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
