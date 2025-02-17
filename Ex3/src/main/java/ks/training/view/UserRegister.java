package ks.training.view;

import ks.training.model.User;
import ks.training.service.UserManagement;
import org.mindrot.jbcrypt.BCrypt;

import java.awt.Color;
import java.awt.Font;
import java.sql.SQLException;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class UserRegister extends JFrame {
    private static final long serialVersionUID = 1L;


    private JTextField textField;
    private JPasswordField passwordField;
    private JButton btnNewButton;
    private JPanel contentPane;
    private JButton btnRegister;
    private JPasswordField rePasswordField;
    private JLabel lblRepassword;

    private UserManagement userManagement;

    public UserRegister() {
        this.userManagement = new UserManagement();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(450, 190, 1014, 597);
        setResizable(false);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblNewLabel = new JLabel("Register");
        lblNewLabel.setForeground(Color.BLACK);
        lblNewLabel.setFont(new Font("Times New Roman", Font.PLAIN, 46));
        lblNewLabel.setBounds(423, 13, 273, 93);
        contentPane.add(lblNewLabel);

        textField = new JTextField();
        textField.setFont(new Font("Tahoma", Font.PLAIN, 32));
        textField.setBounds(481, 120, 281, 68);
        contentPane.add(textField);
        textField.setColumns(10);

        passwordField = new JPasswordField();
        passwordField.setFont(new Font("Tahoma", Font.PLAIN, 32));
        passwordField.setBounds(481, 214, 281, 68);
        contentPane.add(passwordField);

        JLabel lblUsername = new JLabel("Username");
        lblUsername.setBackground(Color.BLACK);
        lblUsername.setForeground(Color.BLACK);
        lblUsername.setFont(new Font("Tahoma", Font.PLAIN, 31));
        lblUsername.setBounds(250, 116, 193, 52);
        contentPane.add(lblUsername);

        JLabel lblPassword = new JLabel("Password");
        lblPassword.setForeground(Color.BLACK);
        lblPassword.setBackground(Color.CYAN);
        lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 31));
        lblPassword.setBounds(250, 214, 193, 52);
        contentPane.add(lblPassword);

        btnNewButton = new JButton("Login");
        btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 26));
        btnNewButton.setBounds(687, 404, 162, 73);

        contentPane.add(btnNewButton);

        rePasswordField = new JPasswordField();
        rePasswordField.setFont(new Font("Tahoma", Font.PLAIN, 32));
        rePasswordField.setBounds(481, 292, 281, 68);
        contentPane.add(rePasswordField);

        lblRepassword = new JLabel("Re-Password");
        lblRepassword.setForeground(Color.BLACK);
        lblRepassword.setFont(new Font("Tahoma", Font.PLAIN, 31));
        lblRepassword.setBackground(Color.CYAN);
        lblRepassword.setBounds(250, 308, 193, 52);
        contentPane.add(lblRepassword);

        btnRegister = new JButton(" Register");
        btnRegister.setFont(new Font("Tahoma", Font.PLAIN, 26));
        btnRegister.setBounds(481, 404, 162, 73);
        contentPane.add(btnRegister);
        btnRegister.addActionListener(e -> registerUser());
        setVisible(true);
    }

    private void registerUser() {
        String username = textField.getText();
        String password = String.valueOf(passwordField.getPassword());
        String rePassword = String.valueOf(rePasswordField.getPassword());
        if (username.isEmpty() || password.isEmpty() || rePassword.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Username and password cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        try {
            if (password.equals(rePassword)) {
                String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt(12));
                userManagement.save(new User(username, hashedPassword));
                JOptionPane.showMessageDialog(this, "Registration Successful", "Success", JOptionPane.INFORMATION_MESSAGE);
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Password and Re-Password must be same!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

}