package GUI;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.io.FileWriter;

public class UserCreatorControl extends Controller {

    @FXML
    private Label errorIn;
    @FXML
    private Button createNewUser;
    @FXML
    private Button returnToMenu;
    @FXML
    private Label username;
    @FXML
    private Label password;
    @FXML
    private Label passwordAgain;
    @FXML
    private TextField usernameInput;
    @FXML
    private PasswordField passwordInput;
    @FXML
    private PasswordField passwordAgainInput;
    @FXML
    private Label success;
    @FXML
    private Button goToLogin;

    @FXML
    private void goBackToLogin() throws IOException {
        setScreen("LoginScene.fxml", goToLogin);
    }

    @FXML
    private void createNewUser() throws IOException {

        String user = usernameInput.getText();
        String password = passwordInput.getText();
        String password2 = passwordAgainInput.getText();

        if (password.equals(password2)) {

            FileWriter writer = new FileWriter("users.csv");
            writer.append(user);
            writer.append(",");
            writer.append(password);
            writer.append("\n");
            writer.flush();
            writer.close();

            goBackToLogin();

        } else {
            errorIn.setVisible(true);
        }
    }
}