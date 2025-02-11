package ks.training.view;

import java.awt.EventQueue;
import java.awt.Font;
import java.sql.SQLException;

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

public class UserManagementView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	DefaultTableModel model;
	public JTable table;
	private JButton btnPrev, btnNext, btnDeleteUser;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UserManagementView frame = new UserManagementView();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public UserManagementView() {
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
        
        JLabel labelListUser = new JLabel("Danh Sách User");
        labelListUser.setFont(new Font("Tahoma", Font.PLAIN, 15));
        labelListUser.setBounds(27, 11, 110, 56);
        getContentPane().add(labelListUser);

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
        
        btnDeleteUser = new JButton("Xoá User");
        btnDeleteUser.addActionListener(action);
        btnDeleteUser.setFont(new Font("Tahoma", Font.PLAIN, 15));
        btnDeleteUser.setBounds(302, 408, 172, 46);
        getContentPane().add(btnDeleteUser);
        
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

}
