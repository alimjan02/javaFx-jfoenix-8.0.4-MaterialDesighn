package sample;

import Util.Constant;
import Util.DataBaseUtil;
import Util.DateUtils;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import sample.entity.Book;
import sample.entity.Borrow;
import sample.entity.Reader;
import sample.entity.borrow_record;

import java.math.BigDecimal;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.ResourceBundle;
import java.util.Set;

public class ReaderUi implements Initializable {

    private Reader user;

    @FXML
    private JFXTextField tf_reader_search;//搜索框
    @FXML
    private JFXButton btn_search;//搜索按钮
    @FXML
    private JFXTextField tf_search_bookId;//要借图书id
    @FXML
    private JFXTextField tf_search_bookName;//要借图书名称
    @FXML
    private JFXButton btn_search_confirmBook;//确认借书
    @FXML
    private JFXButton btn_search_confirmBorrow;//确认借书

    @FXML
    private TableView tbv_search_Result;//
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


    //用户信息
    @FXML
    private JFXTextField tf_userInfo_readerId;
    @FXML
    private JFXTextField tf_userInfo_readerName;
    @FXML
    private JFXTextField tf_userInfo_readerType;
    @FXML
    private JFXTextField tf_userInfo_readerSex;
    @FXML
    private JFXTextField tf_userInfo_readerMaxNumbers;
    @FXML
    private JFXTextField tf_userInfo_readerMaxDays;
    @FXML
    private JFXTextField tf_userInfo_readerForfeit;

    @FXML
    private TableView tbv_userInfo_borrowRecord;
    @FXML
    private TableColumn tb_column_userInfo_bookId;
    @FXML
    private TableColumn tb_column_userInfo_bookName;
    @FXML
    private TableColumn tb_column_userInfo_backDate;
    @FXML
    private TableColumn tb_column_userInfo_reBorrow;

    @FXML
    private Label lb_search_resultNumber;
    @FXML
    private Label lb_welcome;
    @FXML
    private Label lb_js_reader_jieshu_date;
    @FXML
    private Label lb_js_reader_huanshu_date;

    @FXML
    private JFXTextField tf_userInfo_jiaoKuan;

    private Main myApp;

    public void setApp(Main myApp) {
        this.myApp = myApp;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        Constant.BOOK_TYPE = DataBaseUtil.getBookType();

        tf_reader_search.setOnKeyPressed(this::tf_reader_search_keyEvent);
        tf_search_bookId.setOnKeyPressed(this::tf_search_book_keyEvent);

        //所有书目列表初始化
        tb_column_book_id.setCellValueFactory(new PropertyValueFactory("Id"));
        tb_column_book_name.setCellValueFactory(new PropertyValueFactory("name"));
        tb_column_book_type.setCellValueFactory(new PropertyValueFactory("typeStr"));
        tb_column_book_author.setCellValueFactory(new PropertyValueFactory("author"));
        tb_column_book_translator.setCellValueFactory(new PropertyValueFactory("translator"));
        tb_column_book_publisher.setCellValueFactory(new PropertyValueFactory("publisher"));
        tb_column_book_publishTime.setCellValueFactory(new PropertyValueFactory("publishTime"));
        tb_column_book_price.setCellValueFactory(new PropertyValueFactory("price"));

        RequiredFieldValidator validator = new RequiredFieldValidator();
        validator.setMessage("请输入...");
        tf_search_bookId.getValidators().add(validator);
        tf_search_bookId.getValidators().add(validator);
        tf_search_bookId.focusedProperty().addListener((o,oldVal,newVal)->{
            if(!newVal) tf_search_bookId.validate();
        });

    }


    //Define the button cell
    private class ButtonCell extends TableCell<borrow_record, Boolean> {
        final Button cellButton = new Button("续借");

        ButtonCell(){
            cellButton.setOnAction(t -> {
                // do something when button clicked
                tbv_userInfo_borrowRecord.getSelectionModel().select(getTableRow().getIndex());
                ObservableList<borrow_record> userbb = tbv_userInfo_borrowRecord.getItems();
                System.out.println("图书ID-->"+userbb.get(getTableRow().getIndex()).getBookId()+userbb.get(getTableRow().getIndex()).getBookName());
                String bookId = userbb.get(getTableRow().getIndex()).getBookId();
                String backDate = userbb.get(getTableRow().getIndex()).getBackDate();
                userBorrowBookXuJie(bookId,backDate);
            });
        }

        //Display button if the row is not empty
        @Override
        protected void updateItem(Boolean t, boolean empty) {
            super.updateItem(t, empty);
            if(!empty){
                setGraphic(cellButton);
            }
        }
    }

    private void userBorrowBookXuJie(String bookId, String backDate) {
        String bDate = DateUtils.getAfterDay(backDate, user.getDays_num());
        if (user.getForfeit() > 0) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setAlertType(Alert.AlertType.INFORMATION);
            alert.setContentText("有超期数目欠款，续借失败！");
            alert.setTitle("续借失败！");
            alert.show();
        } else {
            boolean isok = DataBaseUtil.reBorrow(user.getId(),bookId,bDate);
            if (isok) {
                getBorrowedRecordings(user.getId());
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setAlertType(Alert.AlertType.INFORMATION);
                alert.setContentText("续借成功,应还日期为：" + bDate);
                alert.setTitle("续借成功！");
                alert.show();
            }
        }

    }

    /**
     * 退出登录
     */
    @FXML
    public void hbrlink_goto_login() {
        myApp.gotoLoginUi();
    }

    /**
     * 确认图书
     * @param keyEvent
     */
    private void tf_search_book_keyEvent(KeyEvent keyEvent) {
        if (keyEvent.getCode().equals(KeyCode.ENTER)) {
            Book book = DataBaseUtil.getBook(tf_search_bookId.getText());
            if (book != null) {
                tf_search_bookName.setText(book.getName());
            }
        }
    }

    @FXML
    public void tf_search_book() {
        if (!tf_search_bookId.getText().equals("")) {
            Book book = DataBaseUtil.getBook(tf_search_bookId.getText());
            if (book != null) {
                tf_search_bookName.setText(book.getName());
            }
        } else {
            tf_search_bookId.validate();
        }
    }

    /**
     * 搜索图书---监听回车
     * @param keyEvent
     */
    private void tf_reader_search_keyEvent(KeyEvent keyEvent) {
        if (keyEvent.getCode().equals(KeyCode.ENTER)) {
            ObservableList<Book> books = DataBaseUtil.getAllLikesBooks(tf_reader_search.getText());
            if (books != null) {
                tbv_search_Result.setItems(books);
                lb_search_resultNumber.setText(tbv_search_Result.getItems().size()+" 条记录");
            }
        }
    }

    /**
     * 搜索图书
     */
    @FXML
    private void tf_reader_search() {
        ObservableList<Book> books = DataBaseUtil.getAllLikesBooks(tf_reader_search.getText());
        if (books != null) {
            tbv_search_Result.setItems(books);
            lb_search_resultNumber.setText(tbv_search_Result.getItems().size()+" 条记录");
        }
    }

    /**
     * 设置用户信息
     * @param id
     */
    public void setUserInfo(String id) {
        Reader reader = DataBaseUtil.getReader(id);
        if (reader != null) {
            user = reader;
            tf_userInfo_readerId.setText(reader.getId());
            tf_userInfo_readerName.setText(reader.getName());
            tf_userInfo_readerType.setText(reader.getType());
            tf_userInfo_readerSex.setText(reader.getSex());
            tf_userInfo_readerMaxNumbers.setText(reader.getMax_num()+"");
            tf_userInfo_readerMaxDays.setText(reader.getDays_num()+"");
            tf_userInfo_readerForfeit.setText(reader.getForfeit() + "");

            lb_welcome.setText(reader.getName()+" ，您好！");

            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
            //初始化当前时间
            lb_js_reader_jieshu_date.setText(df.format(new Date()));
            lb_js_reader_huanshu_date.setText(DateUtils.getAfterDay(lb_js_reader_jieshu_date.getText(), user.getDays_num()));

            getBorrowedRecordings(id);
        }
    }

    /**
     * 获取全部借阅记录
     */
    public void getBorrowedRecordings(String id) {
        tb_column_userInfo_bookId.setCellValueFactory(new PropertyValueFactory("bookId"));
        tb_column_userInfo_bookName.setCellValueFactory(new PropertyValueFactory("bookName"));
        tb_column_userInfo_backDate.setCellValueFactory(new PropertyValueFactory("backDate"));
//        tb_column_userInfo_reBorrow.setCellValueFactory(new PropertyValueFactory("isBack"));

        tb_column_userInfo_reBorrow.setCellValueFactory((Callback<TableColumn.CellDataFeatures<borrow_record, Boolean>, ObservableValue<Boolean>>) p -> new SimpleBooleanProperty(p.getValue() != null));
        tb_column_userInfo_reBorrow.setCellFactory((Callback<TableColumn<borrow_record, Boolean>, TableCell<borrow_record, Boolean>>) p -> new ButtonCell());
        //tbv_userInfo_borrowRecord.getColumns().add(tb_column_userInfo_reBorrow);

        ObservableList<borrow_record> borrows = DataBaseUtil.getBorrowRecord(id);
        if (borrows != null) {
            tbv_userInfo_borrowRecord.setItems(borrows);
        }
    }

    public void borrow_book() {
        if (!tf_search_bookId.getText().trim().equals("")) {
            boolean isBorrow = DataBaseUtil.addNewBorrow(tf_search_bookId.getText(), user.getId(), lb_js_reader_jieshu_date.getText(), lb_js_reader_huanshu_date.getText(), 0);
            if (isBorrow) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setAlertType(Alert.AlertType.INFORMATION);
                alert.setContentText("借书成功！");
                alert.setTitle("借书成功！");
                alert.show();
                getBorrowedRecordings(user.getId());
                tf_search_bookId.setText("");
                tf_search_bookName.setText("");
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setAlertType(Alert.AlertType.ERROR);
                alert.setContentText("借书失败！");
                alert.setTitle("借书失败！");
                alert.show();
            }
        } else {
            tf_search_bookId.validate();
        }
    }

    public void jiaokuan() {
        if (user.getForfeit() > 0) {
            if (!tf_userInfo_jiaoKuan.getText().trim().equals("")) {
                double jiaokuanFirst = user.getForfeit() - Double.parseDouble(tf_userInfo_jiaoKuan.getText().trim());
                BigDecimal bg = new BigDecimal(jiaokuanFirst);
                double jiaokuan = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                boolean isok;
                if (jiaokuan < 0) {
                    isok = DataBaseUtil.jiaokuan(user.getId(), 0);
                    if (isok) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setAlertType(Alert.AlertType.ERROR);
                        alert.setContentText("支付成功！找零为 " + Math.abs(jiaokuan) + "¥");
                        alert.setTitle("提示！");
                        alert.show();
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setAlertType(Alert.AlertType.ERROR);
                        alert.setContentText("系统错误，支付失败！");
                        alert.setTitle("提示！");
                        alert.show();
                    }
                }else{
                    isok = DataBaseUtil.jiaokuan(user.getId(), Math.abs(jiaokuan));
                    if (isok) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setAlertType(Alert.AlertType.ERROR);
                        alert.setContentText("支付成功！还需交款: " + Math.abs(jiaokuan) + "¥");
                        alert.setTitle("提示！");
                        alert.show();
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setAlertType(Alert.AlertType.ERROR);
                        alert.setContentText("系统错误，支付失败！");
                        alert.setTitle("提示！");
                        alert.show();
                    }
                }
                setUserInfo(user.getId());
                tf_userInfo_jiaoKuan.setText("");
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setAlertType(Alert.AlertType.ERROR);
                alert.setContentText("请输入交款金额！");
                alert.setTitle("提示！");
                alert.show();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.setContentText("无超期欠款，无需支付！");
            alert.setTitle("提示！");
            alert.show();
            tf_userInfo_jiaoKuan.setText("");
        }
    }


}
