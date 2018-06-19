package Util;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.entity.*;

import java.math.BigDecimal;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;


public class DataBaseUtil {


    /**
     * 读者登陆检查
     * @param account
     * @param password
     * @return
     */
    public static boolean checkReader(String account, String password) {
        boolean checkbool = false;
        try {
            Connection con = null; //定义一个MYSQL链接对象
            Class.forName("com.mysql.jdbc.Driver").newInstance();//MYSQL驱动
            con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/tushuguanli_database_test", "root", "root"); //链接本地MYSQL
            Statement stmt; //创建声明
            stmt = con.createStatement();

            String password_fromDb;
            String selectSql = "select pass from Reader where Id='"+account+"'";
            ResultSet selectRes = stmt.executeQuery(selectSql);
            if (selectRes.next()) {
                password_fromDb = selectRes.getString("pass");
                if (password_fromDb.equals(password)) {
                    checkbool = true;
                }
            }
            con.close();
        } catch (Exception e) {
            System.out.print("读者登陆检查---checkReader----MYSQL ERROR:" + e.getMessage());
        }
        return checkbool;
    }

    /**
     * 管理员登陆检查
     * @param account
     * @param password
     * @return
     */
    public static boolean checkUser(String account, String password) {
        boolean checkbool = false;
        try {
            Connection con = null; //定义一个MYSQL链接对象
            Class.forName("com.mysql.jdbc.Driver").newInstance();//MYSQL驱动
            con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/tushuguanli_database_test", "root", "root"); //链接本地MYSQL
            Statement stmt; //创建声明
            stmt = con.createStatement();

            String password_fromDb;
            String selectSql = "SELECT pass FROM user where Id='"+account+"'";
            ResultSet selectRes = stmt.executeQuery(selectSql);
            if (selectRes.next()) {
                password_fromDb = selectRes.getString("pass");
                if (password_fromDb.equals(password)) {
                    checkbool = true;
                }
            }
            con.close();
        } catch (Exception e) {
            System.out.print("管理员登陆检查----checkUser----MYSQL ERROR:" + e.getMessage());
        }
        return checkbool;
    }

    /**
     * 获取book
     * @param id
     * @return
     */
    public static Book getBook(String id) {
        Book book = null;
        try {
            Connection con = null; //定义一个MYSQL链接对象
            Class.forName("com.mysql.jdbc.Driver").newInstance();//MYSQL驱动
            con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/tushuguanli_database_test", "root", "root"); //链接本地MYSQL
            Statement stmt; //创建声明
            stmt = con.createStatement();

            String selectSql = "SELECT * FROM book where Id='"+id+"'";
            ResultSet selectRes = stmt.executeQuery(selectSql);
            if (selectRes.next()) {
                book = new Book();
                book.setId(selectRes.getString("Id"));
                book.setName(selectRes.getString("name"));
                book.setType(selectRes.getInt("type"));
                book.setAuthor(selectRes.getString("author"));
                book.setTranslator(selectRes.getString("translator"));
                book.setPublisher(selectRes.getString("publisher"));
                book.setPublishTime(selectRes.getDate("publish_time").toString());
                book.setStock(selectRes.getInt("stock"));
                book.setPrice(selectRes.getDouble("price"));
            }
            con.close();
            return book;
        } catch (Exception e) {
            System.out.print("book获取检查----getBook----MYSQL ERROR:" + e.getMessage());
        }
        return book;
    }

    /**
     * 获取单个读者信息
     * @param id
     * @return
     */
    public static Reader getReader(String id) {
        Reader reader = null;
        try {
            Connection con = null; //定义一个MYSQL链接对象
            Class.forName("com.mysql.jdbc.Driver").newInstance();//MYSQL驱动
            con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/tushuguanli_database_test", "root", "root"); //链接本地MYSQL
            Statement stmt; //创建声明
            stmt = con.createStatement();

            String selectSql = "SELECT * FROM reader where Id='"+id+"'";
            ResultSet selectRes = stmt.executeQuery(selectSql);
            if (selectRes.next()) {
                reader = new Reader();
                reader.setId(selectRes.getString("Id"));
                reader.setName(selectRes.getString("name"));
                reader.setPassword(selectRes.getString("pass"));
                reader.setType(selectRes.getString("type"));
                reader.setSex(selectRes.getString("sex"));
                reader.setMax_num(selectRes.getInt("max_num"));
                reader.setDays_num(selectRes.getInt("days_num"));
                reader.setForfeit(selectRes.getDouble("forfeit"));
            }
            con.close();
            return reader;
        } catch (Exception e) {
            System.out.print("reader获取检查----getReader----MYSQL ERROR:" + e.getMessage());
        }
        return reader;
    }

    /**
     * 添加新的借阅记录
     * @param bookId
     * @param readerId
     * @param jieshu_date
     * @param huanshu_date
     * @param isBack
     * @return
     */
    public static boolean addNewBorrow(String bookId, String readerId, String jieshu_date, String huanshu_date, int isBack) {
        try {
            Connection con = null; //定义一个MYSQL链接对象
            Class.forName("com.mysql.jdbc.Driver").newInstance();//MYSQL驱动
            con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/tushuguanli_database_test", "root", "root"); //链接本地MYSQL
            Statement stmt; //创建声明
            stmt = con.createStatement();
            //检查书库存
            Book book = getBook(bookId);
            boolean isAlreadyBorrowed = isAlreadyBorrowed(readerId,bookId);
            if (book.getStock() <= 0 || isAlreadyBorrowed) {
                return false;
            }
            //继续借书
            String updateSql = "insert into borrow (book_id,reader_id,borrow_date,back_date,is_back) values ('"+bookId+"','"+readerId+"','"+jieshu_date+"','"+huanshu_date+"','"+isBack+"')";

            int selectRes = stmt.executeUpdate(updateSql);
            if (selectRes != 0) {
                return true;
            }
            con.close();
        } catch (Exception e) {
            System.out.print("addBorrow检查----addNewBorrow----MYSQL ERROR:" + e.getMessage());
        }
        return false;

    }

    private static boolean isAlreadyBorrowed(String readerId, String bookId) {
        try {
            Connection con = null; //定义一个MYSQL链接对象
            Class.forName("com.mysql.jdbc.Driver").newInstance();//MYSQL驱动
            con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/tushuguanli_database_test", "root", "root"); //链接本地MYSQL
            Statement stmt; //创建声明
            stmt = con.createStatement();
            //继续借书
            String selectSql = "select * from borrow where reader_id='"+readerId+"' and book_id='"+bookId+"'";

            ResultSet selectRes = null;
            selectRes = stmt.executeQuery(selectSql);
            if (selectRes.next()) {
                return true;
            }
            con.close();
        } catch (Exception e) {
            System.out.print("isAlreadyBorrowed----isAlreadyBorrowed----MYSQL ERROR:" + e.getMessage());
        }
        return false;
    }

    /**
     *
     * 添加新书
     * @param book
     * @return
     */
    public static boolean addNewBook(Book book) {
        try {
            Connection con = null; //定义一个MYSQL链接对象
            Class.forName("com.mysql.jdbc.Driver").newInstance();//MYSQL驱动
            con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/tushuguanli_database_test", "root", "root"); //链接本地MYSQL
            Statement stmt; //创建声明
            stmt = con.createStatement();

            String updateSql = "insert into book (Id,name,type,author,translator,publisher,publish_time,stock,price) values ('" + book.getId() + "','" + book.getName() + "','" + book.getType() + "','" + book.getAuthor() + "','" + book.getTranslator()+ "','" + book.getPublisher()+ "','" + book.getPublishTime()+ "','" + book.getStock()+ "','" + book.getPrice() + "')";
            int selectRes = stmt.executeUpdate(updateSql);
            if (selectRes != 0) {
                return true;
            }
            con.close();
        } catch (Exception e) {
            System.out.print("addBook检查----addNewBook----MYSQL ERROR:" + e.getMessage());
        }
        return false;

    }

    /**
     * 修改单个书
     * @param book
     * @return
     */
    public static Boolean alterBook(Book book) {
        try {
            Connection con = null; //定义一个MYSQL链接对象
            Class.forName("com.mysql.jdbc.Driver").newInstance();//MYSQL驱动
            con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/tushuguanli_database_test", "root", "root"); //链接本地MYSQL
            Statement stmt; //创建声明
            stmt = con.createStatement();

            String updateSql = "update book set name='" + book.getName() + "',type='" + book.getType() + "',author='" + book.getAuthor() + "',translator='" + book.getTranslator() + "',publisher='" + book.getPublisher() + "',publish_time='" + book.getPublishTime() + "',stock=" + book.getStock() + ",price=" + book.getPrice() + " where Id =" + book.getId() + "";
            System.out.println(updateSql);
            int selectRes = stmt.executeUpdate(updateSql);
            if (selectRes != 0) {
                return true;
            }
            con.close();
        } catch (Exception e) {
            System.out.print("alterBook检查----alterBook----MYSQL ERROR:" + e.getMessage());
        }
        return false;
    }

    /**
     * 删除单个书
     * @param book
     * @return
     */
    public static Boolean deleteBook(Book book) {
        try {
            Connection con = null; //定义一个MYSQL链接对象
            Class.forName("com.mysql.jdbc.Driver").newInstance();//MYSQL驱动
            con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/tushuguanli_database_test", "root", "root"); //链接本地MYSQL
            Statement stmt; //创建声明
            stmt = con.createStatement();

            String updateSql = "delete from book where Id='"+book.getId()+"'";
            System.out.println(updateSql);
            int selectRes = stmt.executeUpdate(updateSql);
            if (selectRes != 0) {
                return true;
            }
            con.close();
        } catch (Exception e) {
            System.out.print("deleteBook检查----deleteBook----MYSQL ERROR:" + e.getMessage());
        }
        return false;
    }

    /**
     * 添加新的读者
     * @param reader
     * @return
     */
    public static Boolean addNewReader(Reader reader) {
        try {
            Connection con = null; //定义一个MYSQL链接对象
            Class.forName("com.mysql.jdbc.Driver").newInstance();//MYSQL驱动
            con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/tushuguanli_database_test", "root", "root"); //链接本地MYSQL
            Statement stmt; //创建声明
            stmt = con.createStatement();

            String updateSql = "insert into reader (Id,name,pass,type,sex,max_num,days_num,forfeit) values ('" + reader.getId() + "','" + reader.getName() + "','" + reader.getPassword() + "','"+ reader.getType()+"','" + reader.getSex() + "','" + reader.getMax_num()+ "','" + reader.getDays_num()+ "','" + reader.getForfeit()+ "')";
            System.out.println(updateSql);
            int selectRes = stmt.executeUpdate(updateSql);
            if (selectRes != 0) {
                return true;
            }
            con.close();
        } catch (Exception e) {
            System.out.print("addReader检查----addNewReader----MYSQL ERROR:" + e.getMessage());
        }
        return false;
    }

    /**
     * 修改单个书
     * @param reader
     * @return
     */
    public static Boolean alterReader(Reader reader) {
        try {
            Connection con = null; //定义一个MYSQL链接对象
            Class.forName("com.mysql.jdbc.Driver").newInstance();//MYSQL驱动
            con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/tushuguanli_database_test", "root", "root"); //链接本地MYSQL
            Statement stmt; //创建声明
            stmt = con.createStatement();

            String updateSql = "update reader set name='" + reader.getName() +"',pass='"+reader.getPassword()+ "',type='" + reader.getType() + "',sex='" + reader.getSex() + "',max_num=" + reader.getMax_num() + ",days_num=" + reader.getDays_num() + ",forfeit=" + reader.getForfeit() + "  where Id =" + reader.getId() + "";
            System.out.println(updateSql);
            int selectRes = stmt.executeUpdate(updateSql);
            if (selectRes != 0) {
                return true;
            }
            con.close();
        } catch (Exception e) {
            System.out.print("alterReader检查----alterReader----MYSQL ERROR:" + e.getMessage());
        }
        return false;
    }

    /**
     * 删除读者
     * @param Id
     * @return
     */
    public static Boolean deleteReader(String Id) {
        try {
            Connection con = null; //定义一个MYSQL链接对象
            Class.forName("com.mysql.jdbc.Driver").newInstance();//MYSQL驱动
            con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/tushuguanli_database_test", "root", "root"); //链接本地MYSQL
            Statement stmt; //创建声明
            stmt = con.createStatement();

            String updateSql = "delete from reader where Id='"+Id+"'";
            System.out.println(updateSql);
            int selectRes = stmt.executeUpdate(updateSql);
            if (selectRes != 0) {
                return true;
            }
            con.close();
        } catch (Exception e) {
            System.out.print("deleteReader检查----deleteReader----MYSQL ERROR:" + e.getMessage());
        }
        return false;

    }

    /**
     * 获取用户---管理员
     * @param myName
     * @return
     */
    public static User getUser(String myName) {
        User user = null;
        try {
            Connection con = null; //定义一个MYSQL链接对象
            Class.forName("com.mysql.jdbc.Driver").newInstance();//MYSQL驱动
            con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/tushuguanli_database_test", "root", "root"); //链接本地MYSQL
            Statement stmt; //创建声明
            stmt = con.createStatement();

            String selectSql = "SELECT * FROM user where Id='"+myName+"'";
            ResultSet selectRes = stmt.executeQuery(selectSql);
            if (selectRes.next()) {
                user = new User();
                user.setId(selectRes.getString("Id"));
                user.setName(selectRes.getString("name"));
                user.setEmail(selectRes.getString("email"));
                user.setIsAdmin(selectRes.getInt("is_admin"));
            }
            con.close();
            return user;
        } catch (Exception e) {
            System.out.print("user获取检查----getUser----MYSQL ERROR:" + e.getMessage());
        }
        return user;

    }

    /**
     * 获取所有图书信息
     * @return
     */
    public static ObservableList<Book> getAllBooks() {
        final ObservableList<Book> books = FXCollections.observableArrayList();
        try {
            Connection con = null; //定义一个MYSQL链接对象
            Class.forName("com.mysql.jdbc.Driver").newInstance();//MYSQL驱动
            con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/tushuguanli_database_test", "root", "root"); //链接本地MYSQL
            Statement stmt; //创建声明
            stmt = con.createStatement();

            Book book = null;
            String selectSql = "SELECT * FROM book";
            ResultSet selectRes = stmt.executeQuery(selectSql);
            while (selectRes.next()) {
                book = new Book();
                book.setId(selectRes.getString("Id"));
                book.setName(selectRes.getString("name"));
                book.setTypeStr(getBookTypeAccordingId(selectRes.getInt("type")));
                book.setAuthor(selectRes.getString("author"));
                book.setTranslator(selectRes.getString("translator"));
                book.setPublisher(selectRes.getString("publisher"));
                book.setPublishTime(selectRes.getDate("publish_time").toString());
                book.setStock(selectRes.getInt("stock"));
                book.setPrice(selectRes.getDouble("price"));

                books.add(book);
            }
            con.close();
            return books;
        } catch (Exception e) {
            System.out.print("books获取检查----getAllBooks----MYSQL ERROR:" + e.getMessage());
        }
        return books;
    }

    /**
     * 获取所有读者信息
     * @return
     */
    public static ObservableList<Reader> getAllReaders() {
        final ObservableList<Reader> readers = FXCollections.observableArrayList();
        try {
            Connection con = null; //定义一个MYSQL链接对象
            Class.forName("com.mysql.jdbc.Driver").newInstance();//MYSQL驱动
            con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/tushuguanli_database_test", "root", "root"); //链接本地MYSQL
            Statement stmt; //创建声明
            stmt = con.createStatement();

            Reader reader = null;
            String selectSql = "SELECT * FROM reader";
            ResultSet selectRes = stmt.executeQuery(selectSql);
            while (selectRes.next()) {
                reader = new Reader();
                reader.setId(selectRes.getString("Id"));
                reader.setName(selectRes.getString("name"));
                reader.setPassword(selectRes.getString("pass"));
                reader.setType(selectRes.getString("type"));
                reader.setSex(selectRes.getString("sex"));
                reader.setMax_num(selectRes.getInt("max_num"));
                reader.setDays_num(selectRes.getInt("days_num"));
                reader.setForfeit(selectRes.getDouble("forfeit"));
                readers.add(reader);
            }
            con.close();
            return readers;
        } catch (Exception e) {
            System.out.print("getAllReader获取检查----getAllReader----MYSQL ERROR:" + e.getMessage());
        }
        return readers;
    }

    /**
     * 获取所有借阅记录
     * @return
     */
    public static ObservableList<Borrow> getAllBorrowRecord() {
        final ObservableList<Borrow> borrows = FXCollections.observableArrayList();
        try {
            Connection con = null; //定义一个MYSQL链接对象
            Class.forName("com.mysql.jdbc.Driver").newInstance();//MYSQL驱动
            con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/tushuguanli_database_test", "root", "root"); //链接本地MYSQL
            Statement stmt; //创建声明
            stmt = con.createStatement();

            Borrow borrow = null;
            String selectSql = "SELECT * FROM borrow";
            ResultSet selectRes = stmt.executeQuery(selectSql);
            while (selectRes.next()) {
                borrow = new Borrow();
                borrow.setId(selectRes.getString("Id"));
                borrow.setBookId(selectRes.getString("book_id")+" ("+getBook(selectRes.getString("book_id")).getName()+")");
                borrow.setReaderId(selectRes.getString("reader_id")+" ("+getReader(selectRes.getString("reader_id")).getName()+")");
                borrow.setBorrowDate(selectRes.getString("borrow_date"));
                borrow.setBackDate(selectRes.getString("back_date"));
                borrow.setIsBack(selectRes.getInt("is_back"));
                borrows.add(borrow);
            }
            con.close();
            return borrows;
        } catch (Exception e) {
            System.out.print("getAllBorrowRecord获取检查----getAllBorrowRecord----MYSQL ERROR:" + e.getMessage());
        }
        return borrows;
    }

    /**
     * 获取指定用户的借阅记录
     * @param id
     * @return
     */
    public static ObservableList<borrow_record> getBorrowRecord(String id) {
        final ObservableList<borrow_record> borrows = FXCollections.observableArrayList();
        try {
            Connection con = null; //定义一个MYSQL链接对象
            Class.forName("com.mysql.jdbc.Driver").newInstance();//MYSQL驱动
            con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/tushuguanli_database_test", "root", "root"); //链接本地MYSQL
            Statement stmt; //创建声明
            stmt = con.createStatement();

            borrow_record borrow = null;
            String selectSql = "SELECT * FROM borrow where reader_id='"+id+"' and is_back=0";
            ResultSet selectRes = stmt.executeQuery(selectSql);
            while (selectRes.next()) {
                borrow = new borrow_record();
                borrow.setBookId(selectRes.getString("book_id"));
                borrow.setBookName(getBook(selectRes.getString("book_id")).getName());
                borrow.setBorrowDate(selectRes.getString("borrow_date"));
                borrow.setBackDate(selectRes.getString("back_date"));
                borrows.add(borrow);
            }
            con.close();
            return borrows;
        } catch (Exception e) {
            System.out.print("getBorrowRecord获取检查----getBorrowRecord----MYSQL ERROR:" + e.getMessage());
        }
        return borrows;
    }

    public static double backBook(String redaerId, String bookId) {
        try {
            Connection con = null; //定义一个MYSQL链接对象
            Class.forName("com.mysql.jdbc.Driver").newInstance();//MYSQL驱动
            con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/tushuguanli_database_test", "root", "root"); //链接本地MYSQL
            Statement stmt,stmt1,stmt2; //创建声明
            stmt = con.createStatement();
            stmt1 = con.createStatement();
            stmt2 = con.createStatement();

            //先找到需要还的书籍，计算是否超出应还日期
            String selectSql = "select back_date from borrow where reader_id ='" + redaerId + "' and book_id='"+bookId+"' and is_back=0";
            ResultSet selectResult = stmt1.executeQuery(selectSql);

            String updateSql = "update borrow set is_back=1 where reader_id ='" + redaerId + "' and book_id='"+bookId+"' and is_back=0";
            System.out.println(updateSql);
            int updateRes = stmt.executeUpdate(updateSql);
            if (updateRes != 0) {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
                String todayStr = format.format(new Date());//今天
                Date today = format.parse(todayStr);

                double allForfeit = 0;

                while (selectResult.next()) {
                    String back_date_str = selectResult.getString("back_date");
                    //计算超期天数，按照一天0.2元的方式罚款
                    Date back_date = format.parse(back_date_str);
                    int day = (int) ((today.getTime() - back_date.getTime()) / (1000*3600*24));
                    if (day > 0) {
                        double forfeit = day * 0.2;
                        allForfeit += forfeit;
                        System.out.println("超期书目：" + getBook(bookId).getName() + " 超期天数=" + day + "  罚款金额=" + forfeit);
                    }
                }
                BigDecimal bg = new BigDecimal(allForfeit);
                double myAllForfeit = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                System.out.println("总的罚款金额:" + myAllForfeit);
                String upSql = "update reader set forfeit=forfeit+"+myAllForfeit+" where Id='"+redaerId+"'";
                int rs = stmt2.executeUpdate(upSql);
                if (rs != 0) {
                    return myAllForfeit;
                } else {
                    return -1;
                }
//                return true;
            }
            con.close();
        } catch (Exception e) {
            System.out.print("backBook检查----backBook----MYSQL ERROR:" + e.getMessage());
        }
        return -1;
    }

    public static ObservableList<Book> getAllLikesBooks(String bookName) {
        final ObservableList<Book> books = FXCollections.observableArrayList();
        try {
            Connection con = null; //定义一个MYSQL链接对象
            Class.forName("com.mysql.jdbc.Driver").newInstance();//MYSQL驱动
            con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/tushuguanli_database_test", "root", "root"); //链接本地MYSQL
            Statement stmt; //创建声明
            stmt = con.createStatement();

            Book book = null;
            String selectSql = "SELECT * FROM book where name like '%"+bookName+"%' or author like'%"+bookName+"%' or publisher like '%"+bookName+"%' or type like '%"+bookName+"%'";
            ResultSet selectRes = stmt.executeQuery(selectSql);
            while (selectRes.next()) {
                book = new Book();
                book.setId(selectRes.getString("Id"));
                book.setName(selectRes.getString("name"));
                book.setTypeStr(getBookTypeAccordingId(selectRes.getInt("type")));
                book.setAuthor(selectRes.getString("author"));
                book.setTranslator(selectRes.getString("translator"));
                book.setPublisher(selectRes.getString("publisher"));
                book.setPublishTime(selectRes.getDate("publish_time").toString());
                book.setStock(selectRes.getInt("stock"));
                book.setPrice(selectRes.getDouble("price"));

                books.add(book);
            }
            con.close();
            return books;
        } catch (Exception e) {
            System.out.print("books获取检查----getSearchedBooks----MYSQL ERROR:" + e.getMessage());
        }
        return books;
    }

    public static boolean reBorrow(String id, String bookId, String bDate) {
        try {
            Connection con = null; //定义一个MYSQL链接对象
            Class.forName("com.mysql.jdbc.Driver").newInstance();//MYSQL驱动
            con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/tushuguanli_database_test", "root", "root"); //链接本地MYSQL
            Statement stmt; //创建声明
            stmt = con.createStatement();

            String updateSql = "update borrow set back_date ='"+bDate+"' where book_id='"+bookId+"' and reader_id='"+id+"' and is_back=0";
            int selectRes = stmt.executeUpdate(updateSql);
            if (selectRes != 0) {
                return true;
            }
            con.close();
        } catch (Exception e) {
            System.out.print("reBorrow检查----reBorrow----MYSQL ERROR:" + e.getMessage());
        }
        return false;
    }

    public static boolean jiaokuan(String id, double jiaokuan) {
        try {
            Connection con = null; //定义一个MYSQL链接对象
            Class.forName("com.mysql.jdbc.Driver").newInstance();//MYSQL驱动
            con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/tushuguanli_database_test", "root", "root"); //链接本地MYSQL
            Statement stmt; //创建声明
            stmt = con.createStatement();

            String updateSql = "update reader set forfeit ='" + jiaokuan + "' where Id='" + id + "'";
            int selectRes = stmt.executeUpdate(updateSql);
            if (selectRes != 0) {
                return true;
            }
            con.close();
        } catch (Exception e) {
            System.out.print("jiaokuan检查----jiaokuan----MYSQL ERROR:" + e.getMessage());
        }
        return false;
    }

    public static HashMap<String, Integer> getBookType() {
        HashMap<String, Integer> bookType = new HashMap<>();
        try {
            Connection con = null; //定义一个MYSQL链接对象
            Class.forName("com.mysql.jdbc.Driver").newInstance();//MYSQL驱动
            con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/tushuguanli_database_test", "root", "root"); //链接本地MYSQL
            Statement stmt; //创建声明
            stmt = con.createStatement();

            String selectSql = "SELECT * FROM book_type";
            ResultSet selectRes = stmt.executeQuery(selectSql);
            while (selectRes.next()) {
                bookType.put(selectRes.getString("type"), selectRes.getInt("Id"));
            }
            con.close();
            return bookType;
        } catch (Exception e) {
            System.out.print("bookType获取检查----getBookType----MYSQL ERROR:" + e.getMessage());
        }
        return null;
    }

    public static boolean addNewBookType(String bookType) {
        try {
            Connection con = null; //定义一个MYSQL链接对象
            Class.forName("com.mysql.jdbc.Driver").newInstance();//MYSQL驱动
            con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/tushuguanli_database_test", "root", "root"); //链接本地MYSQL
            Statement stmt; //创建声明
            stmt = con.createStatement();

            String updateSql = "insert into book_type (type) values ('"+bookType+"')";
            System.out.println(updateSql);
            int selectRes = stmt.executeUpdate(updateSql);
            if (selectRes != 0) {
                return true;
            }
            con.close();
        } catch (Exception e) {
            System.out.print("addNewBookType检查----addNewBookType----MYSQL ERROR:" + e.getMessage());
        }
        return false;
    }

    public static String getBookTypeAccordingId(int id) {
        Set set = Constant.BOOK_TYPE.keySet();
        Iterator iter = set.iterator();
        while (iter.hasNext()) {
            String key = (String) iter.next();
            if (id == Constant.BOOK_TYPE.get(key)) {
                return key;
            }
        }
        return "";
    }

    public static boolean alterUserPass(String email, String newPass) {
        try {
            Connection con = null; //定义一个MYSQL链接对象
            Class.forName("com.mysql.jdbc.Driver").newInstance();//MYSQL驱动
            con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/tushuguanli_database_test", "root", "root"); //链接本地MYSQL
            Statement stmt; //创建声明
            stmt = con.createStatement();

            String updateSql = "update user set pass='" + newPass + "' where email= '" + email + "'";
            System.out.println(updateSql);
            int selectRes = stmt.executeUpdate(updateSql);
            if (selectRes != 0) {
                return true;
            }
            con.close();
        } catch (Exception e) {
            System.out.print("alterUserPass检查----alterUserPass----MYSQL ERROR:" + e.getMessage());
        }
        return false;
    }

    public static boolean alterReaderPass(String email, String newPass) {
        try {
            Connection con = null; //定义一个MYSQL链接对象
            Class.forName("com.mysql.jdbc.Driver").newInstance();//MYSQL驱动
            con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/tushuguanli_database_test", "root", "root"); //链接本地MYSQL
            Statement stmt; //创建声明
            stmt = con.createStatement();

            String updateSql = "update reader set pass='" + newPass + "' where email= '" + email + "'";
            System.out.println(updateSql);
            int selectRes = stmt.executeUpdate(updateSql);
            if (selectRes != 0) {
                return true;
            }
            con.close();
        } catch (Exception e) {
            System.out.print("alterReaderPass检查----alterReaderPass----MYSQL ERROR:" + e.getMessage());
        }
        return false;
    }

    public static boolean addNewUser(User user) {
        try {
            Connection con = null; //定义一个MYSQL链接对象
            Class.forName("com.mysql.jdbc.Driver").newInstance();//MYSQL驱动
            con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/tushuguanli_database_test", "root", "root"); //链接本地MYSQL
            Statement stmt; //创建声明
            stmt = con.createStatement();

            String updateSql = "insert into user (Id,name,pass,email,is_admin) values ('" + user.getId() + "','" + user.getName() + "','" + user.getPassWord() + "','"+ user.getEmail()+"','" + user.getIsAdmin()+"')";
            System.out.println(updateSql);
            int selectRes = stmt.executeUpdate(updateSql);
            if (selectRes != 0) {
                return true;
            }
            con.close();
        } catch (Exception e) {
            System.out.print("addNewUser检查----addNewUser----MYSQL ERROR:" + e.getMessage());
        }
        return false;
    }
}
