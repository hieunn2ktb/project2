package ks.training.model;

import java.time.LocalDate;

public class BorrowDetail {
    private int borrowId;
    private int userId;
    private String userName;
    private int bookId;
    private String bookName;
    private String author;
    private LocalDate borrowDate;
    private LocalDate returnDate;


    public BorrowDetail(int borrowId, int userId, String userName, int bookId, String bookName, LocalDate borrowDate, LocalDate returnDate) {
        this.borrowId = borrowId;
        this.userId = userId;
        this.userName = userName;
        this.bookId = bookId;
        this.bookName = bookName;
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
    }

    public BorrowDetail(int borrowId, int userId, String userName, int bookId, String bookName, String author, LocalDate borrowDate, LocalDate returnDate) {
        this.borrowId = borrowId;
        this.userId = userId;
        this.userName = userName;
        this.bookId = bookId;
        this.bookName = bookName;
        this.author = author;
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
    }

    public int getBorrowId() {
        return borrowId;
    }

    public int getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public int getBookId() {
        return bookId;
    }

    public String getBookName() {
        return bookName;
    }

    public String getAuthor() {
        return author;
    }

    public LocalDate getBorrowDate() {
        return borrowDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    @Override
    public String toString() {
        return "BorrowDetail{" +
                "borrowId=" + borrowId +
                ", userId=" + userId +
                ", userName='" + userName + '\'' +
                ", bookId=" + bookId +
                ", bookName='" + bookName + '\'' +
                ", author='" + author + '\'' +
                ", borrowDate=" + borrowDate +
                ", returnDate=" + returnDate +
                '}';
    }
}
