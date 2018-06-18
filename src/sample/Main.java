package sample;

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.Loader;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.LoadException;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {

    private Stage mainStage;

    @Override
    public void start(Stage primaryStage) throws Exception{
        mainStage = primaryStage;
        mainStage.setResizable(false);
        //设置窗口的图标.
        mainStage.getIcons().add(new Image(
                Main.class.getResourceAsStream("logo.png")));
        FXMLLoader loader = new FXMLLoader(getClass().getResource("sample.fxml"));
        Parent root = loader.load();
        primaryStage.setTitle("图书管理系统");
        Controller controller = loader.getController();
        controller.setApp(this);
        Scene scene = new Scene(root, 700, 460);
        scene.getStylesheets().add(Main.class.getResource("main.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void gotoMainUi(String userId) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("main_ui.fxml"));
            Parent root = loader.load();
            mainStage.setTitle("图书管理系统");
            MainUiController controller = loader.getController();
            controller.setApp(this);
            controller.setMyName(userId);
            Scene scene = new Scene(root, 700, 500);
            scene.getStylesheets().add(Main.class.getResource("main.css").toExternalForm());
            mainStage.setScene(scene);
            mainStage.show();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    public void gotoReaderUi(String id) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("reader_ui.fxml"));
            Parent root = loader.load();
            mainStage.setTitle("图书管理系统");
            ReaderUi controller = loader.getController();
            controller.setApp(this);
            controller.setUserInfo(id);
            Scene scene = new Scene(root, 700, 460);
            scene.getStylesheets().add(Main.class.getResource("main.css").toExternalForm());
            mainStage.setScene(scene);
            mainStage.show();
        } catch (Exception e) {
            System.out.println(e.getMessage()+e.toString());
        }

    }

    public void closeWindow() {
        mainStage.close();
    }

    public void hideWindow(){ mainStage.hide();}

    public void showWindow(){ mainStage.show();}


    public static void main(String[] args) {
        launch(args);
    }

    public void gotoLoginUi() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("sample.fxml"));
            Parent root = loader.load();
            mainStage.setTitle("图书管理系统");
            Controller controller = loader.getController();
            controller.setApp(this);
            Scene scene = new Scene(root, 700, 460);
            scene.getStylesheets().add(Main.class.getResource("main.css").toExternalForm());
            mainStage.setScene(scene);
            mainStage.show();
        } catch (Exception e) {
            System.out.println(e.toString());
        }

    }
}
