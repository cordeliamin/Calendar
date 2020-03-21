package GUI;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

public class MemoMenuControl extends Controller {

    @FXML private Button createMemo;
    @FXML private Button returnToMenu;

    @FXML private void goBackToMenu() throws IOException {
        setScreen("MainMenuScene.fxml", returnToMenu);
    }

}
