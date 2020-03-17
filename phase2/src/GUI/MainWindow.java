package GUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainWindow extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent login = FXMLLoader.load(getClass().getResource("LoginScene.fxml"));
        primaryStage.setTitle("Calendar");
        primaryStage.setScene(new Scene(login, 900, 600));
        primaryStage.show();
    }
}

