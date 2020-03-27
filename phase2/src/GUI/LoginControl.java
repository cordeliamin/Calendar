package GUI;

import CalendarSystem.Calendar;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;
import CalendarSystem.CalendarManager;

public class LoginControl extends Controller {

    @FXML private Label errorIn;
    @FXML private TextField userInfo;
    @FXML private PasswordField passInfo;

    @Override
    protected void initScreen() { setCalendar(null); }

    @FXML private void login() throws ClassNotFoundException, IOException {
        String user = userInfo.getText();
        String pswd = passInfo.getText();
        HashMap<String, String> users = getUsers();
        if (users.containsKey(user)) {
            if (users.get(user).equals(pswd)) {
                CalendarManager cm = new CalendarManager("./user_data/" + user + "_");
                setCalendar(cm);
                setScreen("SelectCalendarScene.fxml", userInfo);
            } else {
                errorIn.setVisible(true);
            }
        } else {
            errorIn.setVisible(true);
        }
    }

    private HashMap<String, String> getUsers() throws FileNotFoundException {
        Scanner scanner = new Scanner(new FileInputStream("users.csv"));
        String[] login;
        HashMap<String, String> users = new HashMap<>();
        while (scanner.hasNextLine()) {
            login = scanner.nextLine().split(",");
            users.put(login[0], login[1]);
        }
        return users;
    }

}
