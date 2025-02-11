package ks.training.view;

import java.awt.EventQueue;
import java.awt.Font;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class AdminView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	DefaultTableModel model;
	public JTable table;
	private JButton btnPrev, btnNext, btnDeleteUser;
	public JTextField textFieldSearchName;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AdminView frame = new AdminView();
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
	public AdminView() {
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
        
        textFieldSearchName = new JTextField();
        textFieldSearchName.setBounds(149, 32, 256, 46);
        getContentPane().add(textFieldSearchName);
        textFieldSearchName.setColumns(10);

        JButton btnNewSearch = new JButton("Tìm Kiếm");
        btnNewSearch.addActionListener(action);
        btnNewSearch.setFont(new Font("Tahoma", Font.PLAIN, 15));
        btnNewSearch.setBounds(493, 30, 102, 46);
        getContentPane().add(btnNewSearch);
        
        JLabel labelListUserBorrow = new JLabel("Danh Sách User Mượn Sách");
        labelListUserBorrow.setFont(new Font("Tahoma", Font.PLAIN, 15));
        labelListUserBorrow.setBounds(27, 67, 213, 56);
        getContentPane().add(labelListUserBorrow);

        model = new DefaultTableModel(new String[]{"ID", "Name", "Author", "Quantity"}, 0);
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
        
        btnDeleteUser = new JButton("Xoá User");
        btnDeleteUser.addActionListener(action);
        btnDeleteUser.setFont(new Font("Tahoma", Font.PLAIN, 15));
        btnDeleteUser.setBounds(301, 437, 172, 46);
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
