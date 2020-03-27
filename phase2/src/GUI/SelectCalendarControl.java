package GUI;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.IOException;

public class SelectCalendarControl extends Controller {
    @FXML private TextField calendarName;
    @FXML private Button enter;


    @FXML private void viewButton() throws IOException, ClassNotFoundException {
        String name = calendarName.getText();
        getCalendarManager().selectCalendar(name);
        setScreen("MainMenuScene.fxml", calendarName);
    }

    @FXML private void createButton() throws IOException {
        String name = calendarName.getText();
        getCalendarManager().createCalendar(name);
        setScreen("MainMenuScene.fxml", calendarName);
    }
}
