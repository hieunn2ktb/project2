package ks.training.controller;


import ks.training.view.CounterView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class CounterListener implements ActionListener {
    private CounterView counterView;

    public CounterListener(CounterView counterView) {
        this.counterView = counterView;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //System.out.println("Co nguoi ban da an nut");
        String src = e.getActionCommand();
        if (src.equals("Up")){
            try {
                this.counterView.setLabelValue();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}
