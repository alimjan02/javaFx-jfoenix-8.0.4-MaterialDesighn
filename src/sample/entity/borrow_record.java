package sample.entity;

public class borrow_record {
    private String bookId;
    private String bookName;
    private String borrowDate;
    private String backDate;

    public borrow_record(String bookId, String bookName, String borrowDate, String backDate, int isBack) {
        this.bookId = bookId;
        this.bookName = bookName;
        this.borrowDate = borrowDate;
        this.backDate = backDate;
    }

    public borrow_record() {

    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(String borrowDate) {
        this.borrowDate = borrowDate;
    }

    public String getBackDate() {
        return backDate;
    }

    public void setBackDate(String backDate) {
        this.backDate = backDate;
    }

}
