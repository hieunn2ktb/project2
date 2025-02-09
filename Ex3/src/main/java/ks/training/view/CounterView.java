package ks.training.view;

import ks.training.controller.CounterListener;
import ks.training.dao.UserDAO;
import ks.training.dao.impl.UserDAOImpl;
import ks.training.model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

public class CounterView extends JFrame {
    private UserDAO userDAO;
    private JButton jButton;
    JLabel jLabelValue;

    public CounterView() {
        this.userDAO = new UserDAOImpl();
        this.init();
    }

    private void init() {
        this.setTitle("Counter");
        this.setSize(500, 500);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        ActionListener actionListener = new CounterListener(this);

        this.jButton = new JButton("Up");
        jButton.addActionListener(actionListener);

        this.jLabelValue = new JLabel("Default Text");

        JPanel jPanel = new JPanel();
        jPanel.setLayout(new BorderLayout());
        jPanel.add(this.jButton, BorderLayout.WEST);
        jPanel.add(jLabelValue, BorderLayout.CENTER);

        this.add(jPanel);

        this.setVisible(true);
    }
    public void setLabelValue() throws SQLException {
        jLabelValue.setText(userDAO.findAll(1).toString());
    }


}
