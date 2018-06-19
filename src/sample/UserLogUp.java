package sample;

import Util.DataBaseUtil;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import sample.entity.Reader;
import sample.entity.User;

import java.net.URL;
import java.util.ResourceBundle;

public class UserLogUp implements Initializable {

    private Main myApp;
    private Stage myStage;

    @FXML
    JFXTextField tf_id;
    @FXML
    JFXTextField tf_userName;
    @FXML
    JFXTextField tf_email;
    @FXML
    JFXTextField tf_PassWord;

    @FXML
    JFXRadioButton rb_sex_man;
    @FXML
    JFXRadioButton rb_sex_woman;

    @FXML
    JFXRadioButton rdb_reader;
    @FXML
    JFXRadioButton rdb_user;
    private RequiredFieldValidator validator;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        validator = new RequiredFieldValidator();
        validator.setMessage("请输入...");
        tf_id.getValidators().add(validator);
        tf_id.focusedProperty().addListener((o,oldVal,newVal)->{
            if(!newVal) tf_id.validate();
        });

        tf_userName.getValidators().add(validator);
        tf_userName.focusedProperty().addListener((o,oldVal,newVal)->{
            if(!newVal) tf_userName.validate();
        });

        tf_PassWord.getValidators().add(validator);
        tf_PassWord.focusedProperty().addListener((o,oldVal,newVal)->{
            if(!newVal) tf_PassWord.validate();
        });

        tf_email.getValidators().add(validator);
        tf_email.focusedProperty().addListener((o,oldVal,newVal)->{
            if(!newVal) tf_email.validate();
        });

    }

    public void setMyApp(Main myApp) {
        this.myApp = myApp;
    }

    public void setController(Stage myStage) {
        this.myStage = myStage;
    }

    @FXML
    public void goBackLogin() {
        myApp.showWindow();
        myStage.close();
    }

    @FXML
    public void confirm() {
        String userName = tf_userName.getText().trim();
        String passWord = tf_PassWord.getText().trim();
        String email = tf_email.getText().trim();
        String id = tf_id.getText().trim();
        String sex = "";
        if (rb_sex_man.isSelected()) {
            sex = "男";
        }else{
            sex = "女";
        }
        if (!id.equals("") || !email.equals("") || !passWord.equals("") || !userName.equals("")) {
            boolean isok = false;
            if (rdb_reader.isSelected()) {
                Reader reader = new Reader(id,userName,passWord,"学生",sex,12,30,0);
                isok = DataBaseUtil.addNewReader(reader);
            } else {
                User user = new User(id,userName, passWord, email,1);
                isok = DataBaseUtil.addNewUser(user);
            }
            if (isok) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setAlertType(Alert.AlertType.INFORMATION);
                alert.setTitle("注册成功！");
                alert.showAndWait();
                myApp.showWindow();
                myStage.close();
            }
        } else {

            return;
        }
    }

}
