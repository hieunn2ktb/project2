package ks.training.view;

import ks.training.model.User;
import ks.training.service.UserManagement;
import org.mindrot.jbcrypt.BCrypt;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class LoginView extends JFrame {

    private static final long serialVersionUID = 1L;
    private UserManagement userManagement;
    private String currentUser;
    private int userId;
    private JPasswordField passwordText;
    private JTextField userText;

    public LoginView() throws HeadlessException {
        this.userManagement = new UserManagement();
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
         userText = new JTextField(20);
        JLabel passwordLabel = new JLabel("Password:");
         passwordText = new JPasswordField(20);
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
                userId = userManagement.findIDUser(username, password);
                if (userId > -1) {
                    if (userManagement.isAdmin(username)) {
                        this.currentUser = "Admin";
                    } else {
                        this.currentUser = "Student";
                    }
                    loginFrame.dispose();
                    new BookManagementView(userId, currentUser);
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
                    String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt(12));
                    userManagement.save(new User(username, hashedPassword));
                    JOptionPane.showMessageDialog(registerFrame, "Registration Successful", "Success", JOptionPane.INFORMATION_MESSAGE);
                    registerFrame.dispose();
                } else {
                    JOptionPane.showMessageDialog(registerFrame, "Password and Re-Password must be same!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }

        });

        registerFrame.add(panel);
        registerFrame.setVisible(true);
    }
}
