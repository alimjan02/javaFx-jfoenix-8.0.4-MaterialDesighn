package sample;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class ForgotPass implements Initializable {

    @FXML
    JFXButton btn_confirm;

    private Main myApp;
    Stage myStage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void setMyApp(Main myApp) {
        this.myApp = myApp;
    }

    public void setController(Stage myStage) {
        this.myStage = myStage;
    }

    @FXML
    public void confirm() {
        myApp.showWindow();
        myStage.close();
    }
}
