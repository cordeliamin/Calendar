package GUI;

import CalendarSystem.CalendarManager;
import CalendarSystem.Calendar;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public abstract class Controller {

    protected CalendarManager calendarManager;
    protected Calendar userCalendar;

    protected void setCalendar(CalendarManager calManager) {
        calendarManager = calManager;
        userCalendar = calManager.getCalendar();
    }

    protected void setScreen(String fxmlFileName, Node item) throws IOException {
        Parent newScene = FXMLLoader.load(getClass().getResource(fxmlFileName));
        Stage window = (Stage) item.getScene().getWindow();
        window.setScene(new Scene(newScene, 900, 600));
    }

    /*
     * Changes Scene and returns FXML Loader for passing info to new Scene controller
     */
    protected FXMLLoader setScreenAndGetLoader(String fxmlFileName, Node item) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(fxmlFileName));
        Parent newScene = loader.load();
        Stage window = (Stage) item.getScene().getWindow();
        window.setScene(new Scene(newScene, 900, 600));
        return loader;
    }

}
