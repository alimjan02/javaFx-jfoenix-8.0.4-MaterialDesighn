package sample;

import Util.DataBaseUtil;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Hyperlink;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ForgotPass implements Initializable {

    @FXML
    JFXButton btn_confirm;

    @FXML
    JFXTextField tf_frg_email;

    @FXML
    JFXTextField tf_frg_newPass;

    @FXML
    JFXRadioButton rdb_reader;

    @FXML
    JFXRadioButton rdb_user;

    @FXML
    Hyperlink hbr_goBackLogin;

    @FXML
    Hyperlink hbl_logup;

    private Main myApp;
    Stage myStage;
    private RequiredFieldValidator validator;
    private RequiredFieldValidator validator2;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        validator = new RequiredFieldValidator();
        validator.setMessage("请输入有效的邮箱...");
        tf_frg_email.getValidators().add(validator);
        tf_frg_email.focusedProperty().addListener((o,oldVal,newVal)->{
            if(!newVal) tf_frg_email.validate();
        });

        validator2 = new RequiredFieldValidator();
        validator2.setMessage("请输入有效的密码...");
        tf_frg_newPass.getValidators().add(validator2);
        tf_frg_newPass.focusedProperty().addListener((o,oldVal,newVal)->{
            if(!newVal) tf_frg_newPass.validate();
        });
    }

    public void setMyApp(Main myApp) {
        this.myApp = myApp;
    }

    public void setController(Stage myStage) {
        this.myStage = myStage;
    }

    @FXML
    public void confirm() {
        boolean email_ready = false;
        boolean pass_ready = false;
        String email = tf_frg_email.getText().trim();
        String newPass = tf_frg_newPass.getText().trim();
        if (!email.equals("") || !newPass.equals("")) {
            if (email.endsWith(".com") || email.contains("@")) {
                email_ready = true;
            } else {
                tf_frg_email.setText("");
                tf_frg_email.validate();
            }
            if (newPass.length() >= 8) {
                pass_ready = true;
            } else {
                tf_frg_newPass.setText("");
                tf_frg_newPass.validate();
            }
            if (pass_ready && email_ready) {
                boolean isok;
                if (rdb_reader.isSelected()) {
                    isok = DataBaseUtil.alterReaderPass(email, newPass);
                } else {
                    isok = DataBaseUtil.alterUserPass(email, newPass);
                }
                if (isok) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setAlertType(Alert.AlertType.INFORMATION);
                    alert.setTitle("修改成功！");
                    alert.showAndWait();
                    myApp.showWindow();
                    myStage.close();
                }
            }

        } else {
            tf_frg_email.setText("");
            tf_frg_newPass.setText("");
            tf_frg_email.validate();
            tf_frg_newPass.validate();
            return;
        }
        //myApp.showWindow();
        //myStage.close();
    }

    @FXML
    public void logUp() {
        Stage myLogupStage=new Stage();
        myLogupStage.setResizable(false);
        //设置窗口的图标.
        myLogupStage.getIcons().add(new Image(
                Main.class.getResourceAsStream("logo.png")));
        FXMLLoader loader = new FXMLLoader(getClass().getResource("user_logUp.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        UserLogUp con = loader.getController();
        con.setMyApp(myApp);
        con.setController(myLogupStage);
        myLogupStage.setTitle("注册");
        Scene scene = new Scene(root, 475, 400);
        scene.getStylesheets().add(Main.class.getResource("main.css").toExternalForm());
        myLogupStage.setScene(scene);
        myLogupStage.show();
        myStage.close();
    }

    @FXML
    public void goBackLogin() {
        myApp.showWindow();
        myStage.close();
    }
}
