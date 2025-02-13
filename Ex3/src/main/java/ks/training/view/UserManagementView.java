package ks.training.view;

import ks.training.controller.UserManagementController;
import ks.training.model.User;
import ks.training.service.UserManagement;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class UserManagementView extends JFrame {

    public static final long serialVersionUID = 1L;
    public JPanel contentPane;
	DefaultTableModel model;
	public JTable table;
    public JButton btnPrev, btnNext, btnDeleteUser;
    public int currentPage = 1;
    public int itemsPerPage = 15;
    public UserManagement userManagement;


	public UserManagementView() {
        this.userManagement = new UserManagement();
        this.init();
	}

    private void init() {
        setBounds(100, 100, 827, 553);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        ActionListener action = new UserManagementController(this);

        setContentPane(contentPane);
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);


        getContentPane().setLayout(null);


        JLabel labelListUser = new JLabel("Danh Sách User");
        labelListUser.setFont(new Font("Tahoma", Font.PLAIN, 15));
        labelListUser.setBounds(27, 11, 110, 56);
        getContentPane().add(labelListUser);

        model = new DefaultTableModel(new String[]{"ID", "Name", "Password"}, 0);
        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(27, 59, 774, 267);
        getContentPane().add(scrollPane);

        btnPrev = new JButton("Trang Trước");
        btnPrev.setBounds(250, 340, 120, 30);
        btnPrev.addActionListener(action);
        getContentPane().add(btnPrev);

        btnNext = new JButton("Trang Sau");
        btnNext.setBounds(400, 340, 120, 30);
        btnNext.addActionListener(action);

        getContentPane().add(btnNext);

        btnDeleteUser = new JButton("Xoá User");
        btnDeleteUser.addActionListener(action);
        btnDeleteUser.setFont(new Font("Tahoma", Font.PLAIN, 15));
        btnDeleteUser.setBounds(146, 408, 172, 46);
        getContentPane().add(btnDeleteUser);

        btnDeleteUser = new JButton("Danh Sách User Mượn Sách");
        btnDeleteUser.addActionListener(action);
        btnDeleteUser.setFont(new Font("Tahoma", Font.PLAIN, 15));
        btnDeleteUser.setBounds(509, 408, 232, 46);
        getContentPane().add(btnDeleteUser);

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
        List<User> users = userManagement.findAll();
        if (currentPage * itemsPerPage < users.size()) {
            currentPage++;
            updateTable();
        }
    }
    private void updateTable() throws SQLException {
        List<User> users = userManagement.findAll();
        model.setRowCount(0);
        int start = (currentPage - 1) * itemsPerPage;
        int end = Math.min(start + itemsPerPage, users.size());
        for (int i = start; i < end; i++) {
            User user = users.get(i);
            model.addRow(new Object[]{user.getId(), user.getUsername(), user.getPassword()});
        }
        updateButtons();
    }
    private void updateButtons() throws SQLException {
        List<User> users = userManagement.findAll();
        btnPrev.setEnabled(currentPage > 1);
        btnNext.setEnabled(currentPage * itemsPerPage < users.size());
    }
    public void deleteUser() throws SQLException {
        DefaultTableModel defaultTableModel = (DefaultTableModel) table.getModel();
        int row = table.getSelectedRow();
        User user = getUser();
        int number = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn xóa dòng đã chọn");
        if (number == JOptionPane.YES_OPTION) {
            if (!userManagement.isUserBorrowingBook(user.getId())){
                this.userManagement.delete(user.getId());
                defaultTableModel.removeRow(row);
            }else {
                JOptionPane.showMessageDialog(this, "Không thể xoá User đang mượn sách", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }

        }
    }
    public User getUser() {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        int row = table.getSelectedRow();
        int id = Integer.valueOf(model.getValueAt(row, 0) + "");
        String name = model.getValueAt(row, 1) + "";
        String password = model.getValueAt(row, 2) + "";
        return new User(id, name, password);
    }

    public void clickNext() {
        try {
            nextPage();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void clickPrev() {
        try {
            previousPage();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
}
