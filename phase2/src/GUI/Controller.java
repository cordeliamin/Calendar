package GUI;

import CalendarSystem.CalendarManager;
import CalendarSystem.Calendar;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;

public abstract class Controller {

    private CalendarManager calendarManager;

    protected void setCalendar(CalendarManager calManager) {
        calendarManager = calManager;
    }

    protected Calendar getCalendar() {
        return calendarManager.getCalendar();
    }

    protected CalendarManager getCalendarManager() {
        return calendarManager;
    }

    /*
     * Changes scene to display on the stage <item> is on to a new scene
     * specified by <fxmlFileName>. Then initializes the new scene controller
     * by passing in this controller's calendarManager and any additional
     * initialization the screen needs.
     */
    protected void setScreen(String fxmlFileName, Node item) throws IOException {
        setScreenAndGetLoader(fxmlFileName, item);
    }

    /*
     * Additional steps the controller must perform on passed in data
     * (e.g. Calendar) in order to fully create scene
     */
    protected void initScreen() {}

    /*
     * Sets Main Screen as described in setScreen() and returns FXML Loader for passing
     * additional info to new Scene controller
     */
    protected FXMLLoader setScreenAndGetLoader(String fxmlFileName, Node item) throws IOException {
        return setNewWindowAndGetLoader(fxmlFileName, (Stage) item.getScene().getWindow(), 900, 600);
    }

    protected FXMLLoader setNewWindowAndGetLoader(String fxmlFileName, Stage window,
                                                  int width, int height) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(fxmlFileName));
        Parent newRoot = loader.load();
        Scene newScreen = new Scene(newRoot, width, height);
        window.setScene(newScreen);
        Controller controller = loader.getController();
        controller.setCalendar(calendarManager);
        controller.initScreen();
        return loader;
    }

}
