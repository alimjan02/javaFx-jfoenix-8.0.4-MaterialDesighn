package sample.entity;

public class Borrow {
    private String id;
    private String bookId;
    private String readerId;
    private String borrowDate;
    private String backDate;
    private int isBack;

    public Borrow(String id, String bookId, String readerId, String borrowDate, String backDate, int isBack) {
        this.id = id;
        this.bookId = bookId;
        this.readerId = readerId;
        this.borrowDate = borrowDate;
        this.backDate = backDate;
        this.isBack = isBack;
    }

    public Borrow() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getReaderId() {
        return readerId;
    }

    public void setReaderId(String readerId) {
        this.readerId = readerId;
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

    public int getIsBack() {
        return isBack;
    }

    public void setIsBack(int isBack) {
        this.isBack = isBack;
    }
}
