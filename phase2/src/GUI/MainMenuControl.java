package GUI;

import CalendarSystem.CalendarManager;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.util.Duration;
import java.io.IOException;
import java.time.LocalDateTime;


public class MainMenuControl extends Controller {

    @FXML Button logOutButton;
    @FXML Button eventButton;
    @FXML Label sysClock;

    @FXML private void logOut() throws IOException {
        calendarManager.saveToFile();
        setScreen("LoginScene.fxml", logOutButton);
    }

    @FXML private void viewEvents() throws IOException {
        FXMLLoader eventLoader = setScreenAndGetLoader("EventMenuScene.fxml", eventButton);
        EventMenuController eventMenu = eventLoader.getController();
        eventMenu.setCalendar(calendarManager);
    }

    @Override
    protected void setCalendar(CalendarManager calManager) {
        super.setCalendar(calManager);
        //Credit for the code below goes to Shekhar Rai from StackOverflow
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            LocalDateTime currentTime = LocalDateTime.now();
            sysClock.setText("Current Time: " + currentTime.getHour() + ":" +
                    currentTime.getMinute() + ":" + currentTime.getSecond());
        }), new KeyFrame(Duration.seconds(1)));
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }
}
