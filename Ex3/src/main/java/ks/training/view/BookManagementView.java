package ks.training.view;

import ks.training.controller.BookManagementController;
import ks.training.model.Book;
import ks.training.service.BookManagement;

import javax.swing.*;
import java.awt.Font;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.SQLException;

public class BookManagementView extends JFrame {

	private static final long serialVersionUID = 1L;
	public JTextField textField;
	public JTextField textField_1;
	public JTable table;
	public JTextField textField_2;
	public JTextField textField_3;
	public JTextField textField_4;
	public BookManagement bookManagement;

	public BookManagementView() {
		this.bookManagement = new BookManagement();
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
		
		JLabel LabelName = new JLabel("Name");
		LabelName.setFont(new Font("Tahoma", Font.PLAIN, 15));
		LabelName.setBounds(22, 25, 57, 56);
		getContentPane().add(LabelName);
		
		JLabel lblAuthor = new JLabel("Author");
		lblAuthor.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblAuthor.setBounds(369, 25, 57, 56);
		getContentPane().add(lblAuthor);
		
		textField = new JTextField();
		textField.setBounds(89, 33, 256, 46);
		getContentPane().add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(436, 33, 264, 46);
		getContentPane().add(textField_1);
		
		JButton btnNewSearch = new JButton("Tìm Kiếm");
		btnNewSearch.addActionListener(action);
		btnNewSearch.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnNewSearch.setBounds(709, 30, 102, 46);
		getContentPane().add(btnNewSearch);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(22, 91, 764, 8);
		getContentPane().add(separator);
		
		JLabel lblDanhSchBook = new JLabel("Danh Sách Book");
		lblDanhSchBook.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblDanhSchBook.setBounds(22, 99, 110, 56);
		getContentPane().add(lblDanhSchBook);
		
		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
			},
			new String[] {
				"ID", "Name", "Author", "Quantity"
			}
		));

		
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(22, 147, 774, 267);
		getContentPane().add(scrollPane);
		
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
		
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBounds(89, 479, 256, 46);
		getContentPane().add(textField_2);
		
		JLabel lblAuthor_1 = new JLabel("Author");
		lblAuthor_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblAuthor_1.setBounds(22, 529, 57, 56);
		getContentPane().add(lblAuthor_1);
		
		textField_3 = new JTextField();
		textField_3.setColumns(10);
		textField_3.setBounds(89, 537, 256, 46);
		getContentPane().add(textField_3);
		
		JLabel LabelName_1_1 = new JLabel("Số lượng ");
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
		
		JButton btnExportFileExcel = new JButton("Xuất File Excel");
		btnExportFileExcel.addActionListener(action);
		btnExportFileExcel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnExportFileExcel.setBounds(32, 595, 172, 46);
		getContentPane().add(btnExportFileExcel);

		this.setVisible(true);
	}

	public void addBook(Book book) throws SQLException {
		bookManagement.addBook(book);
	}
}
