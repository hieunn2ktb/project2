package ks.training;

import ks.training.view.BookManagementView;
import ks.training.view.LibraryManagementUI;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
//        CounterModel ct = new CounterModel();
//        ct.increment();
//        ct.increment();
//        ct.increment();
//        System.out.println(ct.getValue());
//        ct.decrement();
//        System.out.println(ct.getValue());
//        BookDAO bookDAO = new BookDaoImpl();
//        Book book = new Book("Java", "Sachin", 10);
//        try {
//           String result = bookDAO.save(book);
//            System.out.println(result);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        UserDAO userDAO = new UserDAOImpl();
//        try {
//             userDAO.findAll(1).forEach(System.out::println);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            new BookManagementView();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}