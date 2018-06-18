package sample.entity;

public class Book {
    private String Id;
    private String name;
    private int type;
    private String typeStr;
    private String author;
    private String translator;
    private String publisher;
    private String publishTime;
    private int stock;
    private double price;


    public Book(String id, String name, int type, String typeStr, String author, String translator, String publisher, String publishTime, int stock, double price) {
        this.Id = id;
        this.name = name;
        this.type = type;
        this.typeStr = typeStr;
        this.author = author;
        this.translator = translator;
        this.publisher = publisher;
        this.publishTime = publishTime;
        this.stock = stock;
        this.price = price;
    }

    public Book() {

    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTranslator() {
        return translator;
    }

    public void setTranslator(String translator) {
        this.translator = translator;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getTypeStr() {
        return typeStr;
    }

    public void setTypeStr(String typeStr) {
        this.typeStr = typeStr;
    }
}
