package sample;

import Util.Constant;
import Util.DataBaseUtil;
import Util.DateUtils;
import com.jfoenix.controls.*;

import com.jfoenix.controls.cells.editors.TextFieldEditorBuilder;
import com.jfoenix.controls.cells.editors.base.GenericEditableTreeTableCell;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import com.jfoenix.validation.RequiredFieldValidator;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import sample.entity.*;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

public class MainUiController implements Initializable {


    private Main myApp;

    private String myName="";

    @FXML
    private AnchorPane treeview_test;

    //所有书目显示列表信息
    @FXML
    private TableView tbv_book;//数目列表
    @FXML
    private TableColumn tb_column_book_id;
    @FXML
    private TableColumn tb_column_book_name;
    @FXML
    private TableColumn tb_column_book_type;
    @FXML
    private TableColumn tb_column_book_author;
    @FXML
    private TableColumn tb_column_book_translator;
    @FXML
    private TableColumn tb_column_book_publisher;
    @FXML
    private TableColumn tb_column_book_publishTime;
    @FXML
    private TableColumn tb_column_book_price;

    //所有读者显示列表信息
    @FXML
    private TableView tbv_reader;//读者列表
    @FXML
    private TableColumn tb_column_reader_id;
    @FXML
    private TableColumn tb_column_reader_name;
    @FXML
    private TableColumn tb_column_reader_type;
    @FXML
    private TableColumn tb_column_reader_sex;
    @FXML
    private TableColumn tb_column_reader_numbers;
    @FXML
    private TableColumn tb_column_reader_days;
    @FXML
    private TableColumn tb_column_reader_forfeit;
    @FXML
    private TableColumn tb_column_reader_borrowed_books;


    //所有借阅记录显示列表
    @FXML
    private TableView tbv_borrow;//借阅列表
    @FXML
    private TableColumn tb_column_borrow_id;
    @FXML
    private TableColumn tb_column_borrow_bookId;
    @FXML
    private TableColumn tb_column_borrow_readerId;
    @FXML
    private TableColumn tb_column_borrow_borrowDate;
    @FXML
    private TableColumn tb_column_borrow_backDate;
    @FXML
    private TableColumn tb_column_borrow_isBack;


    //借书---图书信息
    @FXML
    private JFXTextField tf_js_book_id;//图书编号
    @FXML
    private JFXTextField tf_js_book_name;//图书名称
    @FXML
    private JFXTextField tf_js_book_publisher;//出版社
    @FXML
    private JFXTextField tf_js_book_publish_time;//出版时间

    //借书---读者信息
    @FXML
    private JFXTextField tf_js_reader_id;//读者编号
    @FXML
    private JFXTextField tf_js_reader_name;//读者名称
    @FXML
    private JFXTextField tf_js_reader_type;//读者类别
    @FXML
    private JFXTextField tf_js_reader_sex;//性别

    //借书---button
    @FXML
    private JFXButton btn_jieshu_confirm;//确认按钮
    @FXML
    private JFXButton btn_jieshu_clear;//清楚按钮

    //借书---label---日期
    @FXML
    private Label lb_js_reader_jieshu_date;//借书时间
    @FXML
    private Label lb_js_reader_huanshu_date;//还书时间


    //还书---表格信息
    @FXML
    private TableView tbv_huanshu_record;
    @FXML
    private TableColumn tb_column_huanshu_bookId;
    @FXML
    private TableColumn tb_column_huanshu_bookName;
    @FXML
    private TableColumn tb_column_huanshu_borrowDate;
    @FXML
    private TableColumn tb_column_huanshu_backDate;

    //还书----读者信息
    @FXML
    private JFXTextField tf_hs_reader_id;
    @FXML
    private JFXTextField tf_hs_reader_name;
    @FXML
    private JFXTextField tf_hs_reader_type;
    @FXML
    private JFXTextField tf_hs_reader_sex;

    //还书----图书信息
    @FXML
    private JFXTextField tf_hs_book_id;
    @FXML
    private JFXTextField tf_hs_book_name;


    //图书维护---添加
    @FXML
    private JFXTextField tf_ts_add_book_id;//图书编号
    @FXML
    private JFXTextField tf_ts_add_book_name;//图书名称
    @FXML
    private JFXComboBox cb_ts_add_book_type;//图书类别
    @FXML
    private JFXTextField tf_ts_add_book_author;//作者
    @FXML
    private JFXTextField tf_ts_add_book_translator;//译者
    @FXML
    private JFXTextField tf_ts_add_book_publisher;//出版社
    @FXML
    private JFXDatePicker dp_ts_add_book_publish_time;//出版时间
    @FXML
    private JFXTextField tf_ts_add_book_price;//图书价格
    @FXML
    private JFXTextField tf_ts_add_book_stock;//库存容量

    @FXML
    private JFXButton btn_ts_add_book_add;//确认添加按钮
    @FXML
    private JFXButton btn_ts_add_book_clear;//清楚按钮


    //图书维护----修改
    @FXML
    private JFXTextField tf_ts_alter_book_search_id;//搜索图书编号
    @FXML
    private JFXTextField tf_ts_alter_book_id;//图书编号
    @FXML
    private JFXTextField tf_ts_alter_book_name;//图书名称
    @FXML
    private JFXComboBox cb_ts_alter_book_type;//图书类别
    @FXML
    private JFXTextField tf_ts_alter_book_author;//作者
    @FXML
    private JFXTextField tf_ts_alter_book_translator;//译者
    @FXML
    private JFXTextField tf_ts_alter_book_publisher;//出版社
    @FXML
    private JFXDatePicker tp_ts_alter_book_publish_time;//出版时间
    @FXML
    private JFXTextField tf_ts_alter_book_price;//图书价格
    @FXML
    private JFXTextField tf_ts_alter_book_stock;//库存容量

    @FXML
    private JFXButton btn_ts_alter_book_alterBtn;//确认修改按钮
    @FXML
    private JFXButton btn_ts_alter_book_clearBtn;//清楚按钮
    @FXML
    private JFXButton btn_ts_alter_book_searchBtn;//查询按钮


    //图书维护----删除
    @FXML
    private JFXTextField tf_ts_delete_book_search_id;//搜索图书编号
    @FXML
    private JFXTextField tf_ts_delete_book_id;//图书编号
    @FXML
    private JFXTextField tf_ts_delete_book_name;//图书名称
    @FXML
    private JFXComboBox cb_ts_delete_book_type;//图书类别
    @FXML
    private JFXTextField tf_ts_delete_book_author;//作者
    @FXML
    private JFXTextField tf_ts_delete_book_translator;//译者
    @FXML
    private JFXTextField tf_ts_delete_book_publisher;//出版社
    @FXML
    private JFXTextField tf_ts_delete_book_publish_time;//出版时间
    @FXML
    private JFXTextField tf_ts_delete_book_price;//图书价格
    @FXML
    private JFXTextField tf_ts_delete_book_stock;//库存容量

    @FXML
    private JFXTextField tf_ts_book_typeAdder;
    @FXML
    private JFXComboBox cb_ts_book_type;

    @FXML
    private JFXButton btn_ts_delete_book_deleteBtn;//确认修改按钮
    @FXML
    private JFXButton btn_ts_delete_book_clearBtn;//清楚按钮
    @FXML
    private JFXButton btn_ts_delete_book_searchBtn;//查询按钮



    //读者维护---添加
    @FXML
    private JFXTextField tf_rd_add_reader_id;//读者编号
    @FXML
    private JFXTextField tf_rd_add_reader_name;//读者名称
    @FXML
    private JFXComboBox cb_rd_add_reader_type;//读者类别
    @FXML
    private JFXComboBox cb_rd_add_reader_sex;//性别
    @FXML
    private JFXTextField tf_rd_add_reader_numbers;//可借数量
    @FXML
    private JFXTextField tf_rd_add_reader_days;//可借天数

    @FXML
    private JFXButton btn_rd_add_reader_addBtn;//确认添加按钮
    @FXML
    private JFXButton btn_rd_add_reader_clearBtn;//清楚按钮


    //读者维护----修改
    @FXML
    private JFXTextField tf_rd_alter_reader_search_id;//搜索读者编号
    @FXML
    private JFXTextField tf_rd_alter_reader_id;//读者编号
    @FXML
    private JFXTextField tf_rd_alter_reader_name;//读者名称
    @FXML
    private JFXComboBox cb_rd_alter_reader_type;//读者类别
    @FXML
    private JFXComboBox cb_rd_alter_reader_sex;//性别
    @FXML
    private JFXTextField tf_rd_alter_reader_numbers;//可借数量
    @FXML
    private JFXTextField tf_rd_alter_reader_days;//可借天数
    @FXML
    private JFXToggleButton tgBtn_rd_alter_reader_password_reset;//初始化密码

    @FXML
    private JFXButton btn_rd_alter_reader_alterBtn;//确认修改按钮
    @FXML
    private JFXButton btn_rd_alter_reader_clearBtn;//清楚按钮


    //读者维护----删除
    @FXML
    private JFXTextField tf_rd_delete_reader_search_id;//搜索读者编号
    @FXML
    private JFXTextField tf_rd_delete_reader_id;//读者编号
    @FXML
    private JFXTextField tf_rd_delete_reader_name;//读者名称
    @FXML
    private JFXComboBox cb_rd_delete_reader_type;//读者类别
    @FXML
    private JFXComboBox cb_rd_delete_reader_sex;//性别
    @FXML
    private JFXTextField tf_rd_delete_reader_numbers;//可借数量
    @FXML
    private JFXTextField tf_rd_delete_reader_days;//可借天数

    @FXML
    private JFXButton btn_rd_delete_reader_deleteBtn;//确认删除按钮
    @FXML
    private JFXButton btn_rd_delete_reader_clearBtn;//清楚按钮
    @FXML
    private JFXButton btn_rd_delete_reader_searchBtn;//查询按钮

    @FXML
    private Label lb_welcome;//工作人员欢迎

    @FXML
    private ImageView imageview;


    /**
     * 主类传递进来，方便界面管理
     * @param myApp
     */
    public void setApp(Main myApp) {
        this.myApp = myApp;
    }

    /**
     * 设置欢迎语句
     * @param myName
     */
    public void setMyName(String myName) {
        this.myName = myName;
        User user = DataBaseUtil.getUser(myName);
        if (user != null) {
            lb_welcome.setText(user.getName()+" ,老师您好！");
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

//        Image img=new Image(this.getClass().getResourceAsStream("logo.png"));
//        imageview.setImage(img);
        updateBookType();

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
        //初始化当前时间
        lb_js_reader_jieshu_date.setText(df.format(new Date()));

        RequiredFieldValidator validator_tf_js_book_id = new RequiredFieldValidator();
        validator_tf_js_book_id.setMessage("请输入图书编号...");
        tf_js_book_id.getValidators().add(validator_tf_js_book_id);
        tf_js_book_id.focusedProperty().addListener((o,oldVal,newVal)->{
            if(!newVal) tf_js_book_id.validate();
        });


        RequiredFieldValidator validator_tf_js_reader_id = new RequiredFieldValidator();
        validator_tf_js_reader_id.setMessage("请输入读者编号...");
        tf_js_reader_id.getValidators().add(validator_tf_js_reader_id);
        tf_js_reader_id.focusedProperty().addListener((o,oldVal,newVal)->{
            if(!newVal) tf_js_reader_id.validate();
        });

        RequiredFieldValidator validator_tf_reqireInput = new RequiredFieldValidator();
        validator_tf_reqireInput.setMessage("请输入...");

        tf_hs_reader_id.getValidators().add(validator_tf_reqireInput);
        tf_hs_reader_id.focusedProperty().addListener((o,oldVal,newVal)->{
            if(!newVal) tf_hs_reader_id.validate();
        });

        tf_hs_book_id.getValidators().add(validator_tf_reqireInput);
        tf_hs_book_id.focusedProperty().addListener((o,oldVal,newVal)->{
            if(!newVal) tf_hs_book_id.validate();
        });

        //添加键盘监听
        tf_js_book_id.setOnKeyPressed(this::tf_js_book_id_keyEvent);
        tf_js_reader_id.setOnKeyPressed(this::tf_js_reader_id_keyEvent);
        tf_hs_reader_id.setOnKeyPressed(this::tf_hs_reader_id_keyEvent);
        tf_hs_book_id.setOnKeyPressed(this::tf_hs_book_id_keyEvent);

        //图书维护---初始化
        initBookAddUi();

        //读者维护---初始化
        initReaderAddUi();

//        initBooksUi();

        //所有书目列表初始化
        tb_column_book_id.setCellValueFactory(new PropertyValueFactory("Id"));
        tb_column_book_name.setCellValueFactory(new PropertyValueFactory("name"));
        tb_column_book_type.setCellValueFactory(new PropertyValueFactory("typeStr"));
        tb_column_book_author.setCellValueFactory(new PropertyValueFactory("author"));
        tb_column_book_translator.setCellValueFactory(new PropertyValueFactory("translator"));
        tb_column_book_publisher.setCellValueFactory(new PropertyValueFactory("publisher"));
        tb_column_book_publishTime.setCellValueFactory(new PropertyValueFactory("publishTime"));
        tb_column_book_price.setCellValueFactory(new PropertyValueFactory("price"));

        tb_column_reader_id.setCellValueFactory(new PropertyValueFactory("id"));
        tb_column_reader_name.setCellValueFactory(new PropertyValueFactory("name"));
        tb_column_reader_type.setCellValueFactory(new PropertyValueFactory("type"));
        tb_column_reader_sex.setCellValueFactory(new PropertyValueFactory("sex"));
        tb_column_reader_numbers.setCellValueFactory(new PropertyValueFactory("max_num"));
        tb_column_reader_days.setCellValueFactory(new PropertyValueFactory("days_num"));
        tb_column_reader_forfeit.setCellValueFactory(new PropertyValueFactory("forfeit"));
//        tb_column_reader_borrowed_books.setCellValueFactory(new PropertyValueFactory("borrowed_books"));

        tb_column_borrow_id.setCellValueFactory(new PropertyValueFactory("id"));
        tb_column_borrow_bookId.setCellValueFactory(new PropertyValueFactory("bookId"));
        tb_column_borrow_readerId.setCellValueFactory(new PropertyValueFactory("readerId"));
        tb_column_borrow_borrowDate.setCellValueFactory(new PropertyValueFactory("borrowDate"));
        tb_column_borrow_backDate.setCellValueFactory(new PropertyValueFactory("backDate"));
        tb_column_borrow_isBack.setCellValueFactory(new PropertyValueFactory("isBack"));

        tb_column_huanshu_bookId.setCellValueFactory(new PropertyValueFactory("bookId"));
        tb_column_huanshu_bookName.setCellValueFactory(new PropertyValueFactory("bookName"));
        tb_column_huanshu_borrowDate.setCellValueFactory(new PropertyValueFactory("borrowDate"));
        tb_column_huanshu_backDate.setCellValueFactory(new PropertyValueFactory("backDate"));

    }

    /**
     * 更新图书类别
     */
    private void updateBookType() {
        Constant.BOOK_TYPE = DataBaseUtil.getBookType();

        int size = cb_ts_book_type.getItems().size();
        for (int i = 0; i < size; i++) {
            cb_ts_book_type.getItems().remove(0);
            cb_ts_add_book_type.getItems().remove(0);
            cb_ts_alter_book_type.getItems().remove(0);
        }

        Set set = Constant.BOOK_TYPE.keySet();
        Iterator iter = set.iterator();
        while (iter.hasNext()) {
            String key = (String) iter.next();
            cb_ts_book_type.getItems().addAll(key);
            cb_ts_add_book_type.getItems().addAll(key);
            cb_ts_alter_book_type.getItems().addAll(key);
            cb_ts_delete_book_type.getItems().addAll(key);
        }
        //选择第一个
        cb_ts_book_type.getSelectionModel().selectFirst();
        cb_ts_add_book_type.getSelectionModel().selectFirst();
        cb_ts_alter_book_type.getSelectionModel().selectFirst();
    }

    public int getBookTypeSelectNumber(int id) {
        int number = 0;
        Set set = Constant.BOOK_TYPE.keySet();
        Iterator iter = set.iterator();
        while (iter.hasNext()) {
            String key = (String) iter.next();
            if (id == Constant.BOOK_TYPE.get(key)) {
                return number;
            }
            number++;
        }
        return 0;
    }

    public String getBookTypeAccordingId(int id) {
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

    private int getBookIdAccordingToSelectNumber(int selectedIndex) {
        int number = 0;
        Set set = Constant.BOOK_TYPE.keySet();
        Iterator iter = set.iterator();
        while (iter.hasNext()) {
            String key = (String) iter.next();
            if (selectedIndex == number) {
                return Constant.BOOK_TYPE.get(key);
            }
            number++;
        }
        return 0;
    }


    /**
     * 退出登录
     */
    @FXML
    public void hbrlink_goto_login() {
        myApp.gotoLoginUi();
    }


    /**
     * *********************************************借书模块-------开始************************************************
     */

    /**
     * 监听----借书--button--确认
     */
    @FXML
    public void js_confirm_start() {
        if (!tf_js_book_name.getText().equals("") && !tf_js_reader_name.getText().equals("")) {
            boolean isBorrow = DataBaseUtil.addNewBorrow(tf_js_book_id.getText(), tf_js_reader_id.getText(), lb_js_reader_jieshu_date.getText(), lb_js_reader_huanshu_date.getText(), 0);
            if (isBorrow) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setAlertType(Alert.AlertType.INFORMATION);
                alert.setContentText("借书成功！");
                alert.setTitle("借书成功！");
                alert.show();
                tf_js_book_id.setText("");
                tf_js_reader_id.setText("");
                clear_js_book();
                clear_js_reader();
            }
        } else {
            tf_js_book_id.setText("");
            tf_js_reader_id.setText("");
            clear_js_book();
            clear_js_reader();
            tf_js_book_id.validate();
            tf_js_reader_id.validate();
        }
    }

    /**
     * 监听----借书--书--id--输入回车
     * @param keyEvent
     */
    public void tf_js_book_id_keyEvent(KeyEvent keyEvent) {
        if (keyEvent.getCode().equals(KeyCode.ENTER)) {
            Book book = DataBaseUtil.getBook(tf_js_book_id.getText());
            if (book != null) {
//                tf_js_book_id.setText(book.getId());
                tf_js_book_name.setText(book.getName());
                tf_js_book_publisher.setText(book.getPublisher());
                tf_js_book_publish_time.setText(book.getPublishTime().toString());
            } else {
                clear_js_book();
            }

        }
    }

    /**
     * 监听----借书--读者--id--输入回车
     * @param keyEvent
     */
    public void tf_js_reader_id_keyEvent(KeyEvent keyEvent) {
        if (keyEvent.getCode().equals(KeyCode.ENTER)) {
            Reader reader = DataBaseUtil.getReader(tf_js_reader_id.getText());
            if (reader != null) {
//                tf_js_reader_id.setText(reader.getId());
                tf_js_reader_name.setText(reader.getName());
                tf_js_reader_type.setText(reader.getType());
                tf_js_reader_sex.setText(reader.getSex());
                lb_js_reader_huanshu_date.setText(DateUtils.getAfterDay(lb_js_reader_jieshu_date.getText(), reader.getDays_num()));
            } else {
                clear_js_reader();
            }

        }
    }

    /**
     * 监听----借书--button--清楚
     */
    @FXML
    public void js_clear_start() {
        tf_js_book_id.setText("");
        tf_js_reader_id.setText("");
        clear_js_book();
        clear_js_reader();
    }

    /**
     * 清楚---借书
     */
    public void clear_js_book() {
//        tf_js_book_id.setText(null);
        tf_js_book_name.setText("");
        tf_js_book_publisher.setText("");
        tf_js_book_publish_time.setText("");
    }

    /**
     * 清楚---借书
     */
    public void clear_js_reader() {
//        tf_js_reader_id.setText(null);
        tf_js_reader_name.setText("");
        tf_js_reader_type.setText("");
        tf_js_reader_sex.setText("");
        lb_js_reader_huanshu_date.setText("");
    }

    /**
     * *********************************************还书模块-------开始************************************************
     */

    /**
     * 还书模块---监听---读者id
     * @param keyEvent
     */
    private void tf_hs_reader_id_keyEvent(KeyEvent keyEvent) {
        if (keyEvent.getCode().equals(KeyCode.ENTER)) {
            Reader reader = DataBaseUtil.getReader(tf_hs_reader_id.getText());
            //如果不为空，则进行
            if (reader != null) {
                tf_hs_reader_name.setText(reader.getName());
                tf_hs_reader_type.setText(reader.getType());
                tf_hs_reader_sex.setText(reader.getSex());
                getReaderBorrowRecord(reader.getId());
            } else {
                clear_hs_reader();
            }
        }
    }

    /**
     * 还书模块---用于还书成功后刷新
     */
    private void tf_hs_reader_id_keyEvent() {
        Reader reader = DataBaseUtil.getReader(tf_hs_reader_id.getText());
        //如果不为空，则进行
        if (reader != null) {
            tf_hs_reader_name.setText(reader.getName());
            tf_hs_reader_type.setText(reader.getType());
            tf_hs_reader_sex.setText(reader.getSex());
            getReaderBorrowRecord(reader.getId());
        } else {
            clear_hs_reader();
        }
    }

    /**
     * 清楚---还书
     */
    @FXML
    public void clear_hs_reader() {
        tf_hs_reader_id.setText(null);
        tf_hs_reader_name.setText("");
        tf_hs_reader_type.setText("");
        tf_hs_reader_sex.setText("");
        tf_hs_book_id.setText("");
        tf_hs_book_name.setText("");
        //先清理原来表格记录
        int size = tbv_huanshu_record.getItems().size();
        for (int i = 0; i < size; i++) {
            tbv_huanshu_record.getItems().remove(0);
        }
    }

    public void getReaderBorrowRecord(String id) {
        ObservableList<borrow_record> borrows = DataBaseUtil.getBorrowRecord(id);
        if (borrows != null) {
            tbv_huanshu_record.setItems(borrows);
        }else {

        }
    }

    /**
     * 还书模块---监听---读者id
     * @param keyEvent
     */
    private void tf_hs_book_id_keyEvent(KeyEvent keyEvent) {
        if (keyEvent.getCode().equals(KeyCode.ENTER)) {
            Book book = DataBaseUtil.getBook(tf_hs_book_id.getText());
            //如果不为空，则进行
            if (book != null) {
                tf_hs_book_name.setText(book.getName());
            } else {
                tf_hs_book_name.setText("");
            }
        }
    }

    /**
     * 点击还书按钮----进行还书
     */
    @FXML
    private void huanshu_start() {
        if (!tf_hs_reader_name.getText().trim().equals("")) {
            if (!tf_hs_book_id.getText().trim().equals("")) {
                double isok = DataBaseUtil.backBook(tf_hs_reader_id.getText().trim(),tf_hs_book_id.getText().trim());
                if (isok != -1) {
                    tf_hs_reader_id_keyEvent();
                    tf_hs_book_id.setText("");
                    tf_hs_book_name.setText("");
                    System.out.println("huanshu ok");
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setAlertType(Alert.AlertType.INFORMATION);
                    alert.setContentText("还书成功！  超期罚款为 : " + isok);
                    alert.setTitle("还书成功！");
                    alert.show();
                } else {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setAlertType(Alert.AlertType.ERROR);
                    alert.setContentText("还书失败！");
                    alert.setTitle("还书失败！");
                    alert.show();
                }
            } else {
                tf_hs_book_id.validate();
            }
        } else {
            tf_hs_reader_id.validate();
        }
    }


    /**
     * *********************************************图书维护模块-------开始************************************************
     */

    /**
     * 初始化--图书维护模块
     */
    public void initBookAddUi() {

        RequiredFieldValidator validator_ts_book_add = new RequiredFieldValidator();
        validator_ts_book_add.setMessage("请输入...");
        tf_ts_add_book_id.getValidators().add(validator_ts_book_add);
        tf_ts_add_book_id.focusedProperty().addListener((o,oldVal,newVal)->{
            if(!newVal) tf_ts_add_book_id.validate();
        });

        tf_ts_add_book_name.getValidators().add(validator_ts_book_add);
        tf_ts_add_book_name.focusedProperty().addListener((o,oldVal,newVal)->{
            if(!newVal) tf_ts_add_book_name.validate();
        });

        tf_ts_add_book_author.getValidators().add(validator_ts_book_add);
        tf_ts_add_book_author.focusedProperty().addListener((o,oldVal,newVal)->{
            if(!newVal) tf_ts_add_book_author.validate();
        });

        tf_ts_add_book_translator.getValidators().add(validator_ts_book_add);
        tf_ts_add_book_translator.focusedProperty().addListener((o,oldVal,newVal)->{
            if(!newVal) tf_ts_add_book_translator.validate();
        });

        tf_ts_add_book_publisher.getValidators().add(validator_ts_book_add);
        tf_ts_add_book_publisher.focusedProperty().addListener((o,oldVal,newVal)->{
            if(!newVal) tf_ts_add_book_publisher.validate();
        });

        tf_ts_add_book_price.getValidators().add(validator_ts_book_add);
        tf_ts_add_book_price.focusedProperty().addListener((o,oldVal,newVal)->{
            if(!newVal) tf_ts_add_book_price.validate();
        });

        tf_ts_add_book_stock.getValidators().add(validator_ts_book_add);
        tf_ts_add_book_stock.focusedProperty().addListener((o,oldVal,newVal)->{
            if(!newVal) tf_ts_add_book_stock.validate();
        });

        cb_ts_add_book_type.getSelectionModel().selectFirst();

        //////*****//////
        tf_ts_alter_book_search_id.getValidators().add(validator_ts_book_add);
        tf_ts_alter_book_search_id.focusedProperty().addListener((o,oldVal,newVal)->{
            if(!newVal) tf_ts_alter_book_search_id.validate();
        });

        tf_ts_delete_book_search_id.getValidators().add(validator_ts_book_add);
        tf_ts_delete_book_search_id.focusedProperty().addListener((o,oldVal,newVal)->{
            if(!newVal) tf_ts_delete_book_search_id.validate();
        });

    }

    /**
     * 图书信息维护界面-----添加
     * 添加按钮点击事件
     */
    @FXML
    public void ts_book_add() {
        System.out.println("info====>  "+tf_ts_add_book_id.getText() + tf_ts_add_book_name.getText()+tf_ts_add_book_author.getText()+tf_ts_add_book_translator.getText()+tf_ts_add_book_publisher.getText()+
                tf_ts_add_book_price.getText()+tf_ts_add_book_stock.getText()+cb_ts_add_book_type.getSelectionModel().getSelectedItem().toString()+dp_ts_add_book_publish_time.getEditor().getText());
        if (!tf_ts_add_book_id.getText().equals("") && !tf_ts_add_book_name.getText().equals("") && !tf_ts_add_book_author.getText().equals("") && !tf_ts_add_book_translator.getText().equals("") && !tf_ts_add_book_publisher.getText().equals("") &&
                !tf_ts_add_book_price.getText().equals("") && !tf_ts_add_book_stock.getText().equals("") && !cb_ts_add_book_type.getSelectionModel().getSelectedItem().toString().equals("") && !dp_ts_add_book_publish_time.getEditor().getText().equals("")) {
            Book book = new Book();
            book.setId(tf_ts_add_book_id.getText());
            book.setName(tf_ts_add_book_name.getText());
            book.setType(Constant.BOOK_TYPE.get(cb_ts_add_book_type.getSelectionModel().getSelectedItem().toString()));
            book.setAuthor(tf_ts_add_book_author.getText());
            book.setTranslator(tf_ts_add_book_translator.getText());
            book.setPublisher(tf_ts_add_book_publisher.getText());
            book.setPublishTime(dp_ts_add_book_publish_time.getEditor().getText());
            book.setStock(Integer.parseInt(tf_ts_add_book_stock.getText()));
            book.setPrice(Double.parseDouble(tf_ts_add_book_price.getText()));
            Boolean isok = DataBaseUtil.addNewBook(book);
            if (isok) {
                System.out.println("add ok");
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setAlertType(Alert.AlertType.INFORMATION);
                alert.setContentText("添加成功！");
                alert.setTitle("添加成功！");
                alert.show();
                ts_book_add_clear();
            } else {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setAlertType(Alert.AlertType.ERROR);
                alert.setContentText("添加失败！");
                alert.setTitle("添加失败！");
                alert.show();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.setContentText("信息不完整！");
            alert.setTitle("添加错误！");
            alert.show();
        }
    }

    /**
     * 图书信息维护界面-----添加
     * 清楚按钮点击事件
     */
    @FXML
    private void ts_book_add_clear() {
        tf_ts_add_book_id.setText("");
        tf_ts_add_book_name.setText("");
//        cb_ts_add_book_type.getSelectionModel().clearSelection();
        tf_ts_add_book_author.setText("");
        tf_ts_add_book_translator.setText("");
        tf_ts_add_book_publisher.setText("");
        dp_ts_add_book_publish_time.getEditor().setText("");
        tf_ts_add_book_stock.setText("");
        tf_ts_add_book_price.setText("");
    }

    /**
     * 图书维护---修改---查询图书
     */
    @FXML
    public void ts_book_alter_search() {
        if (!tf_ts_alter_book_search_id.getText().equals("")) {
            Book book = DataBaseUtil.getBook(tf_ts_alter_book_search_id.getText().trim());
            if (book != null) {
                tf_ts_alter_book_id.setText(book.getId());
                tf_ts_alter_book_name.setText(book.getName());
                cb_ts_alter_book_type.getSelectionModel().select(getBookTypeSelectNumber(book.getType()));
                tf_ts_alter_book_author.setText(book.getAuthor());
                tf_ts_alter_book_translator.setText(book.getTranslator());
                tf_ts_alter_book_publisher.setText(book.getPublisher());
                tp_ts_alter_book_publish_time.getEditor().setText(book.getPublishTime());
                tf_ts_alter_book_price.setText("" + book.getPrice());
                tf_ts_alter_book_stock.setText("" + book.getStock());
            } else {
                tf_ts_alter_book_search_id.setText("");
                tf_ts_alter_book_search_id.validate();
            }
        }
    }

    /**
     * 图书维护---修改
     */
    @FXML
    public void ts_book_alter_start() {
        if (!tf_ts_alter_book_id.getText().equals("") && !tf_ts_alter_book_name.getText().equals("") && !tf_ts_alter_book_author.getText().equals("") && !tf_ts_alter_book_translator.getText().equals("") && !tf_ts_alter_book_publisher.getText().equals("") &&
                !tf_ts_alter_book_price.getText().equals("") && !tf_ts_alter_book_stock.getText().equals("") && !cb_ts_alter_book_type.getSelectionModel().getSelectedItem().toString().equals("") && !tp_ts_alter_book_publish_time.getEditor().getText().equals("")) {
            Book book = new Book();
            book.setId(tf_ts_alter_book_id.getText());
            book.setName(tf_ts_alter_book_name.getText());
            book.setType(getBookIdAccordingToSelectNumber(cb_ts_alter_book_type.getSelectionModel().getSelectedIndex()));
            book.setAuthor(tf_ts_alter_book_author.getText());
            book.setTranslator(tf_ts_alter_book_translator.getText());
            book.setPublisher(tf_ts_alter_book_publisher.getText());
            book.setPublishTime(tp_ts_alter_book_publish_time.getEditor().getText());
            book.setStock(Integer.parseInt(tf_ts_alter_book_stock.getText()));
            book.setPrice(Double.parseDouble(tf_ts_alter_book_price.getText()));
            Boolean isok = DataBaseUtil.alterBook(book);
            if (isok) {
                System.out.println("alter ok");
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setAlertType(Alert.AlertType.INFORMATION);
                alert.setContentText("修改成功！");
                alert.setTitle("修改成功！");
                alert.show();
                ts_book_alter_clear();
            } else {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setAlertType(Alert.AlertType.ERROR);
                alert.setContentText("修改失败！");
                alert.setTitle("修改失败！");
                alert.show();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.setContentText("信息不完整！");
            alert.setTitle("修改错误！");
            alert.show();
        }
    }

    /**
     * 图书信息维护界面-----修改
     * 清楚按钮点击事件
     */
    @FXML
    private void ts_book_alter_clear() {
        tf_ts_alter_book_id.setText("");
        tf_ts_alter_book_name.setText("");
//        cb_ts_alter_book_type.getSelectionModel().clearSelection();
        tf_ts_alter_book_author.setText("");
        tf_ts_alter_book_translator.setText("");
        tf_ts_alter_book_publisher.setText("");
        tp_ts_alter_book_publish_time.getEditor().setText("");
        tf_ts_alter_book_stock.setText("");
        tf_ts_alter_book_price.setText("");
    }


    /**
     * 图书维护---删除---查询图书
     */
    @FXML
    public void ts_book_delete_search() {
        if (!tf_ts_delete_book_search_id.getText().equals("")) {
            Book book = DataBaseUtil.getBook(tf_ts_delete_book_search_id.getText().trim());
            if (book != null) {
                tf_ts_delete_book_id.setText(book.getId());
                tf_ts_delete_book_name.setText(book.getName());
                cb_ts_delete_book_type.getSelectionModel().select(getBookTypeSelectNumber(book.getType()));
                tf_ts_delete_book_author.setText(book.getAuthor());
                tf_ts_delete_book_translator.setText(book.getTranslator());
                tf_ts_delete_book_publisher.setText(book.getPublisher());
                tf_ts_delete_book_publish_time.setText(book.getPublishTime());
                tf_ts_delete_book_price.setText("" + book.getPrice());
                tf_ts_delete_book_stock.setText("" + book.getStock());
            } else {
                tf_ts_delete_book_search_id.setText("");
                tf_ts_delete_book_search_id.validate();
            }
        }

    }

    /**
     * 图书维护---删除--按钮
     */
    @FXML
    public void ts_book_delete() {
        if (!tf_ts_delete_book_id.getText().equals("")) {
            Book book = new Book();
            book.setId(tf_ts_delete_book_id.getText());
            book.setName(tf_ts_delete_book_name.getText());
            book.setType(getBookIdAccordingToSelectNumber(cb_ts_delete_book_type.getSelectionModel().getSelectedIndex()));
            book.setAuthor(tf_ts_delete_book_author.getText());
            book.setTranslator(tf_ts_delete_book_translator.getText());
            book.setPublisher(tf_ts_delete_book_publisher.getText());
            book.setPublishTime(tf_ts_delete_book_publish_time.getText());
            book.setStock(Integer.parseInt(tf_ts_delete_book_stock.getText()));
            book.setPrice(Double.parseDouble(tf_ts_delete_book_price.getText()));

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setAlertType(Alert.AlertType.CONFIRMATION);
            alert.setContentText("确认删除？");
            alert.setTitle("确认删除！");
            alert.showAndWait();
            ButtonType type = alert.getResult();
            System.out.println("type="+type.getText());
            if (type == ButtonType.OK) {
                Boolean isok = DataBaseUtil.deleteBook(book);
                if (isok) {
                    System.out.println("delete ok");
                    Alert alert1 = new Alert(Alert.AlertType.CONFIRMATION);
                    alert1.setAlertType(Alert.AlertType.INFORMATION);
                    alert1.setContentText("删除成功！");
                    alert1.setTitle("删除成功！");
                    alert1.show();
                    ts_book_delete_clear();
                } else {
                    Alert alert2 = new Alert(Alert.AlertType.CONFIRMATION);
                    alert2.setAlertType(Alert.AlertType.ERROR);
                    alert2.setContentText("删除失败！");
                    alert2.setTitle("删除失败！");
                    alert2.show();
                }
            }

        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.setContentText("信息不完整！");
            alert.setTitle("删除错误！");
            alert.show();
        }
    }

    /**
     * 图书信息维护界面-----删除
     * 清楚按钮点击事件
     */
    @FXML
    private void ts_book_delete_clear() {
        tf_ts_delete_book_id.setText("");
        tf_ts_delete_book_name.setText("");
        cb_ts_delete_book_type.getEditor().setText("");
        tf_ts_delete_book_author.setText("");
        tf_ts_delete_book_translator.setText("");
        tf_ts_delete_book_publisher.setText("");
        tf_ts_delete_book_publish_time.setText("");
        tf_ts_delete_book_stock.setText("");
        tf_ts_delete_book_price.setText("");
    }

    /**
     * 添加新图书类别
     */
    public void addType() {
        if (!tf_ts_book_typeAdder.getText().trim().equals("")) {
            Set set = Constant.BOOK_TYPE.keySet();
            Iterator iter = set.iterator();
            while (iter.hasNext()) {
                String key = (String) iter.next();
                if (tf_ts_book_typeAdder.getText().trim().equals(key)) {
                    Alert alert1 = new Alert(Alert.AlertType.CONFIRMATION);
                    alert1.setAlertType(Alert.AlertType.INFORMATION);
                    alert1.setContentText("重复类别！");
                    alert1.setTitle("提示！");
                    alert1.show();
                    return;
                }
            }

            boolean isok = DataBaseUtil.addNewBookType(tf_ts_book_typeAdder.getText().trim());
            if (isok) {
                System.out.println("add ok");
                Alert alert1 = new Alert(Alert.AlertType.CONFIRMATION);
                alert1.setAlertType(Alert.AlertType.INFORMATION);
                alert1.setContentText("添加成功！");
                alert1.setTitle("添加成功！");
                alert1.show();
                tf_ts_book_typeAdder.setText("");
                updateBookType();
            } else {
                Alert alert2 = new Alert(Alert.AlertType.CONFIRMATION);
                alert2.setAlertType(Alert.AlertType.ERROR);
                alert2.setContentText("添加失败！");
                alert2.setTitle("添加失败！");
                alert2.show();
            }
        }
    }

    /**
     * *********************************************图书维护模块-------结束************************************************
     */


    /**
     * *********************************************读者维护模块-------开始************************************************
     */

    /**
     * 读者维护模块初始化
     */
    private void initReaderAddUi() {

        for (int i = 0; i < Constant.READER_YTPES.length; i++) {
            cb_rd_add_reader_type.getItems().addAll(Constant.READER_YTPES[i]);
            cb_rd_alter_reader_type.getItems().addAll(Constant.READER_YTPES[i]);
            cb_rd_delete_reader_type.getItems().addAll(Constant.READER_YTPES[i]);
        }
        cb_rd_add_reader_type.getSelectionModel().selectFirst();
        cb_rd_alter_reader_type.getSelectionModel().selectFirst();
        cb_rd_delete_reader_type.getSelectionModel().selectFirst();

        for (int i = 0; i < Constant.SEX.length; i++) {
            cb_rd_add_reader_sex.getItems().addAll(Constant.SEX[i]);
            cb_rd_alter_reader_sex.getItems().addAll(Constant.SEX[i]);
            cb_rd_delete_reader_sex.getItems().addAll(Constant.SEX[i]);
        }
        cb_rd_add_reader_sex.getSelectionModel().selectFirst();
        cb_rd_alter_reader_sex.getSelectionModel().selectFirst();
        cb_rd_delete_reader_sex.getSelectionModel().selectFirst();

        RequiredFieldValidator validator_ts_book_add = new RequiredFieldValidator();
        validator_ts_book_add.setMessage("请输入...");
        tf_rd_alter_reader_search_id.getValidators().add(validator_ts_book_add);
        tf_rd_alter_reader_search_id.focusedProperty().addListener((o,oldVal,newVal)->{
            if(!newVal) tf_rd_alter_reader_search_id.validate();
        });

        tf_rd_delete_reader_search_id.getValidators().add(validator_ts_book_add);
        tf_rd_delete_reader_search_id.focusedProperty().addListener((o,oldVal,newVal)->{
            if(!newVal) tf_rd_delete_reader_search_id.validate();
        });

    }

    /**
     * 添加新的读者
     */
    @FXML
    public void add_new_reader() {
        if (!tf_rd_add_reader_id.getText().equals("") && !tf_rd_add_reader_name.getText().equals("") && !tf_rd_add_reader_numbers.getText().equals("") && !tf_rd_add_reader_days.getText().equals("") &&
                 !cb_rd_add_reader_type.getSelectionModel().getSelectedItem().toString().equals("") && !cb_rd_add_reader_sex.getSelectionModel().getSelectedItem().toString().equals("")) {
            Reader reader = new Reader();
            reader.setId(tf_rd_add_reader_id.getText());
            reader.setName(tf_rd_add_reader_name.getText());
            reader.setPassword("123456");//默认密码
            reader.setType(cb_rd_add_reader_type.getSelectionModel().getSelectedItem().toString());
            reader.setSex(cb_rd_add_reader_sex.getSelectionModel().getSelectedItem().toString());
            reader.setMax_num(Integer.parseInt(tf_rd_add_reader_numbers.getText()));
            reader.setDays_num(Integer.parseInt(tf_rd_add_reader_days.getText()));
            reader.setForfeit(0);

            Boolean isok = DataBaseUtil.addNewReader(reader);
            if (isok) {
                System.out.println("add ok");
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setAlertType(Alert.AlertType.INFORMATION);
                alert.setContentText("添加成功！");
                alert.setTitle("添加成功！");
                alert.show();
                rd_reader_add_clear();
            } else {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setAlertType(Alert.AlertType.ERROR);
                alert.setContentText("添加失败！");
                alert.setTitle("添加失败！");
                alert.show();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.setContentText("信息不完整！");
            alert.setTitle("添加错误！");
            alert.show();
        }
    }

    /**
     * 读者信息维护界面-----添加
     * 清楚按钮点击事件
     */
    @FXML
    private void rd_reader_add_clear() {
        tf_rd_add_reader_id.setText("");
        tf_rd_add_reader_name.setText("");
        tf_rd_add_reader_numbers.setText("");
        tf_rd_add_reader_days.setText("");
    }

    /**
     * 修改读者
     */
    @FXML
    public void alter_rd_reader() {
        if (!tf_rd_alter_reader_id.getText().equals("") && !tf_rd_alter_reader_name.getText().equals("") && !tf_rd_alter_reader_numbers.getText().equals("") && !tf_rd_alter_reader_days.getText().equals("") &&
                !cb_rd_alter_reader_type.getSelectionModel().getSelectedItem().toString().equals("") && !cb_rd_alter_reader_sex.getSelectionModel().getSelectedItem().toString().equals("")) {
            Reader reader = new Reader();
            reader.setId(tf_rd_alter_reader_id.getText());
            reader.setName(tf_rd_alter_reader_name.getText());
            if (tgBtn_rd_alter_reader_password_reset.isPressed()) {
                reader.setPassword("123456");//默认密码
            } else {
                reader.setPassword(rd_reader_alter_password);//原密码
            }
            reader.setType(cb_rd_alter_reader_type.getSelectionModel().getSelectedItem().toString());
            reader.setSex(cb_rd_alter_reader_sex.getSelectionModel().getSelectedItem().toString());
            reader.setMax_num(Integer.parseInt(tf_rd_alter_reader_numbers.getText()));
            reader.setDays_num(Integer.parseInt(tf_rd_alter_reader_days.getText()));
            reader.setForfeit(0);

            Boolean isok = DataBaseUtil.alterReader(reader);
            if (isok) {
                System.out.println("add ok");
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setAlertType(Alert.AlertType.INFORMATION);
                alert.setContentText("修改成功！");
                alert.setTitle("修改成功！");
                alert.show();
                rd_reader_alter_clear();
            } else {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setAlertType(Alert.AlertType.ERROR);
                alert.setContentText("修改失败！");
                alert.setTitle("修改失败！");
                alert.show();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.setContentText("信息不完整！");
            alert.setTitle("修改错误！");
            alert.show();
        }
    }

    private String rd_reader_alter_password = "123456";

    /**
     * 读者维护---修改---查询读者
     */
    @FXML
    public void rd_reader_alter_search() {
        if (!tf_rd_alter_reader_search_id.getText().equals("")) {
            Reader reader = DataBaseUtil.getReader(tf_rd_alter_reader_search_id.getText().trim());
            if (reader != null) {
                tf_rd_alter_reader_id.setText(reader.getId());
                tf_rd_alter_reader_name.setText(reader.getName());
                if (reader.getType().equals("教师")) {
                    cb_rd_alter_reader_type.getSelectionModel().selectFirst();
                } else if (reader.getType().equals("学生")) {
                    cb_rd_alter_reader_type.getSelectionModel().select(1);
                } else {
                    cb_rd_alter_reader_type.getSelectionModel().select(2);
                }
                if (reader.getSex().equals("男")) {
                    cb_rd_alter_reader_sex.getSelectionModel().selectFirst();
                } else {
                    cb_rd_alter_reader_sex.getSelectionModel().select(1);
                }
                rd_reader_alter_password = reader.getPassword();
                tf_rd_alter_reader_numbers.setText(reader.getMax_num()+"");
                tf_rd_alter_reader_days.setText(reader.getDays_num()+"");
            } else {
                tf_rd_alter_reader_search_id.setText("");
                tf_rd_alter_reader_search_id.validate();
            }
        }

    }

    /**
     * 读者信息维护界面-----修改
     * 清楚按钮点击事件
     */
    @FXML
    private void rd_reader_alter_clear() {
        tf_rd_alter_reader_id.setText("");
        tf_rd_alter_reader_name.setText("");
        tf_rd_alter_reader_numbers.setText("");
        tf_rd_alter_reader_days.setText("");
    }


    /**
     * 读者维护---删除---读者查询
     */
    @FXML
    public void rd_reader_delete_search() {
        if (!tf_rd_delete_reader_search_id.getText().equals("")) {
            Reader reader = DataBaseUtil.getReader(tf_rd_delete_reader_search_id.getText().trim());
            if (reader != null) {
                tf_rd_delete_reader_id.setText(reader.getId());
                tf_rd_delete_reader_name.setText(reader.getName());
                if (reader.getType().equals("教师")) {
                    cb_rd_delete_reader_type.getSelectionModel().selectFirst();
                } else if (reader.getType().equals("学生")) {
                    cb_rd_delete_reader_type.getSelectionModel().select(1);
                } else {
                    cb_rd_delete_reader_type.getSelectionModel().select(2);
                }
                if (reader.getSex().equals("男")) {
                    cb_rd_delete_reader_sex.getSelectionModel().selectFirst();
                } else {
                    cb_rd_delete_reader_sex.getSelectionModel().select(1);
                }
                tf_rd_delete_reader_numbers.setText(reader.getMax_num()+"");
                tf_rd_delete_reader_days.setText(reader.getDays_num()+"");
            } else {
                tf_rd_delete_reader_search_id.setText("");
                tf_rd_delete_reader_search_id.validate();
            }
        }

    }

    /**
     * 删除读者
     */
    @FXML
    public void delete_rd_reader() {
        if (!tf_rd_delete_reader_id.getText().equals("")) {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setAlertType(Alert.AlertType.CONFIRMATION);
            alert.setContentText("确认删除？");
            alert.setTitle("确认删除！");
            alert.showAndWait();
            ButtonType type = alert.getResult();
            System.out.println("type="+type.getText());
            if (type == ButtonType.OK) {
                Boolean isok = DataBaseUtil.deleteReader(tf_rd_delete_reader_id.getText());
                if (isok) {
                    System.out.println("add ok");
                    Alert alert1 = new Alert(Alert.AlertType.CONFIRMATION);
                    alert1.setAlertType(Alert.AlertType.INFORMATION);
                    alert1.setContentText("删除成功！");
                    alert1.setTitle("删除成功！");
                    alert1.show();
                    rd_reader_delete_clear();
                } else {
                    Alert alert2 = new Alert(Alert.AlertType.CONFIRMATION);
                    alert2.setAlertType(Alert.AlertType.ERROR);
                    alert2.setContentText("删除失败！");
                    alert2.setTitle("删除失败！");
                    alert2.show();
                }
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.setContentText("信息不完整！");
            alert.setTitle("删除错误！");
            alert.show();
        }
    }

    /**
     * 读者信息维护界面-----删除
     * 清楚按钮点击事件
     */
    @FXML
    private void rd_reader_delete_clear() {
        tf_rd_delete_reader_id.setText("");
        tf_rd_delete_reader_name.setText("");
        tf_rd_delete_reader_numbers.setText("");
        tf_rd_delete_reader_days.setText("");
    }


    /**
     * *********************************************读者维护模块-------结束************************************************
     */


    /**
     * *********************************************所有图书、读者、借阅显示模块-------开始************************************************
     */

    /**
     * 获取全部书目,并显示
     */
    @FXML
    public void getAllBooks() {
        ObservableList<Book> books = DataBaseUtil.getAllBooks();
        if (books != null) {
            tbv_book.setItems(books);
        } else {
//            tbv_book.setAccessibleText("无记录");
        }

    }

    /**
     * 获取全部书目,并显示
     */
    @FXML
    public void getAllReaders() {
        ObservableList<Reader> readers = DataBaseUtil.getAllReaders();
        if (readers != null) {
            tbv_reader.setItems(readers);
        }else {
//            tbv_reader.setAccessibleText("无记录");
        }
    }

    /**
     * 获取全部借阅记录
     */
    public void getAllBorrowedRecordings() {
        ObservableList<Borrow> borrows = DataBaseUtil.getAllBorrowRecord();
        if (borrows != null) {
            tbv_borrow.setItems(borrows);
        }else {
//            tbv_borrow.setAccessibleText("无记录");
        }
    }

    /**
     * *********************************************所有图书、读者、借阅显示模块-------结束************************************************
     */

}
