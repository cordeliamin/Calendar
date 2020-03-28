package GUI;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.IOException;


public class SeriesMenuControl extends Controller {

    @FXML
    private Button createSeries;
    @FXML
    private Button returnToMenu;
    @FXML
    private Label events; // displays all events


    @FXML
    private void goBackToMenu() throws IOException {
        setScreen("MainMenuScene.fxml", returnToMenu);
    }

}
