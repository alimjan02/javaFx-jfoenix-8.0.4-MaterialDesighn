package sample;

import Util.DataBaseUtil;
import Util.FileUtil;
import com.jfoenix.controls.*;
import com.jfoenix.validation.RequiredFieldValidator;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;
import java.util.logging.Handler;
import java.util.regex.Pattern;

import static java.lang.Thread.sleep;

public class Controller implements Initializable {

    @FXML
    public ToggleGroup identity;

    private Main myApp;

    @FXML
    private JFXCheckBox rememberInfo;

    @FXML
    private JFXProgressBar prgs_login;

    @FXML
    private JFXButton btn_start;

    @FXML
    private JFXTextField tf_user;

    @FXML
    private JFXPasswordField tf_passWord;

    @FXML
    private JFXRadioButton rb_duzhe;

    @FXML
    private JFXRadioButton rb_gzry;

    JFXDialog dialog = new JFXDialog();

    private Thread thread;

    public void setApp(Main myApp) {
        this.myApp = myApp;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        rememberInfo.setSelected(true);

        RequiredFieldValidator validator = new RequiredFieldValidator();
        validator.setMessage("请输入用户名...");
        tf_user.getValidators().add(validator);
        tf_user.focusedProperty().addListener((o,oldVal,newVal)->{
            if(!newVal) tf_user.validate();
        });

        RequiredFieldValidator validator2 = new RequiredFieldValidator();
        validator2.setMessage("请输入密码...");
        tf_passWord.getValidators().add(validator2);
        tf_passWord.focusedProperty().addListener((o,oldVal,newVal)->{
            if(!newVal) tf_passWord.validate();
        });

        rb_duzhe.setSelected(true);
//        lb_Title.setIcon(new ImageIcon(LogOnFrm.class.getResource("/images/logo.png")));
//        dialog.setContent(new Label("Content"));
//        btn_start.setOnAction((action)->dialog.show());
        prgs_login.setVisible(false);
        String str = FileUtil.getUserAndPass();
        Pattern p = Pattern.compile("[#]+");
        String[] result = p.split(str);
        if (result.length >= 1) {
            tf_user.setText(result[0]);
        }
        if (result.length >= 2) {
            tf_passWord.setText(result[1]);
        }

    }

    /**
     * 登录按钮点击事件
     */
    @FXML
    public void onStart() {
        System.out.println("ok");
        prgs_login.setVisible(true);
        //创建线程登录
        myProgress myProgress = new myProgress(prgs_login);
        thread = new Thread(myProgress);
        thread.setPriority(Thread.MAX_PRIORITY);
        thread.start();

        if (rememberInfo.isSelected()) {
            FileUtil.setUserAndPass(tf_user.getText(), tf_passWord.getText());
        }else{
            FileUtil.setUserAndPass(tf_user.getText(), "");
        }

        //登录界面控件不可见
        setDisable(true);


//        prgs_login.setVisible(false);
    }

    /**
     * 登录期间------组件的控制-----登录界面控件不可见
     */
    public void setDisable(Boolean bool) {
        btn_start.setDisable(bool);
        tf_user.setDisable(bool);
        tf_passWord.setDisable(bool);
        rememberInfo.setDisable(bool);
    }


    /**
     * 检查并登录
     */
    private void doCheckUser() {
        if (identity.getSelectedToggle() == rb_duzhe) {
            if (DataBaseUtil.checkReader(tf_user.getText().trim(),tf_passWord.getText())) {
                myApp.gotoReaderUi(tf_user.getText());
            } else {
                setDisable(false);
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setAlertType(Alert.AlertType.ERROR);
                alert.setTitle("登录失败！");
                alert.show();
            }
        } else if (identity.getSelectedToggle() == rb_gzry) {
            if (DataBaseUtil.checkUser(tf_user.getText().trim(),tf_passWord.getText())) {
                myApp.gotoMainUi(tf_user.getText());
            } else {
                setDisable(false);
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setAlertType(Alert.AlertType.ERROR);
                alert.setTitle("登录失败！");
                alert.show();
            }
        }
    }

    /**
     * 忘记密码
     */
    @FXML
    public void forgotPass() {
        myApp.hideWindow();
        Stage myStage=new Stage();
        myStage.setResizable(false);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("forgotPass.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ForgotPass con = loader.getController();
        con.setMyApp(myApp);
        con.setController(myStage);
        myStage.setTitle("忘记密码");
        Scene scene = new Scene(root, 500, 400);
        scene.getStylesheets().add(Main.class.getResource("main.css").toExternalForm());
        myStage.setScene(scene);
        myStage.show();

    }


    /**
     * 登录界面--点击登录按钮后---启用新的线程检查用户身份是否正确
     */
   class myProgress implements Runnable {

        private JFXProgressBar prgs_login;

        myProgress(JFXProgressBar prgs_login) {
            this.prgs_login = prgs_login;
        }

        @Override
        public void run() {
            try {

                for (int i = 0; i <= 100; i++) {
                    prgs_login.setProgress(i);

                }
                sleep(100);
                //更新JavaFX的主线程的代码放在此处
                Platform.runLater(Controller.this::doCheckUser);

            } catch (Exception ignored) {

            }
        }
    }


}

