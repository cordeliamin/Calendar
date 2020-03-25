package GUI;

import CalendarSystem.Event;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;


public class MainMenuControl extends Controller {

    @FXML Button logOutButton;
    @FXML Button eventButton;
    @FXML Button memoButton;
    @FXML Label sysClock;
    @FXML Label monthYearLabel;
    @FXML GridPane monthlyCalendar;

    @FXML private void logOut() throws IOException {
        getCalendarManager().saveToFile();
        setScreen("LoginScene.fxml", logOutButton);
    }

    @FXML private void viewEvents() throws IOException {
        setScreen("EventMenuScene.fxml", eventButton);
    }

    @FXML private void viewMemos() throws IOException {
        setScreen("MemoMenuScene.fxml", memoButton);
    }

    @Override
    protected void initScreen() {
        initClock();
        initMonthlyCalendar();
    }

    private void initClock() {
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            LocalDateTime currentTime = LocalDateTime.now();
            sysClock.setText("Current Time: " + currentTime.getHour() + ":" +
                    currentTime.getMinute() + ":" + currentTime.getSecond());
            monthYearLabel.setText("\t" + currentTime.getMonth().toString() + ", " +
                    currentTime.getYear() + "\t\t");
        }), new KeyFrame(Duration.seconds(1)));
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }

    private void initMonthlyCalendar() {
        int currMonth = LocalDateTime.now().getMonthValue();
        int currYear = LocalDateTime.now().getYear();
        int firstDayOfWeek = LocalDate.of(currYear, currMonth, 1).getDayOfWeek().getValue() % 7;
        LocalDate calCurrDay = LocalDate.of(currYear, currMonth, 1).minusDays(firstDayOfWeek);
        for (int w = 1; w < 7; w++) {
            for (int d = 0; d < 7; d++) {
                VBox day = initMonthlyDayBox(d, w, calCurrDay);
                addEventLabels(day, getCalendar().findEvent(calCurrDay));
                monthlyCalendar.getChildren().add(day);
                calCurrDay = calCurrDay.plusDays(1L);
            }
        }
    }

    private VBox initMonthlyDayBox(int dayOfWeek, int weekNum, LocalDate currDay) {
        VBox day = new VBox();
        day.getStyleClass().add("calendar-grid");
        day.setSpacing(3.0);
        day.setPrefHeight(84.0);
        day.setPrefWidth(100.0);
        GridPane.setConstraints(day, dayOfWeek, weekNum);
        Label dayOfMonth = new Label(Integer.toString(currDay.getDayOfMonth()));
        day.getChildren().add(dayOfMonth);
        return day;
    }

    private void addEventLabels(VBox dayBox, ArrayList<Event> events) {
        for (Event e : events) {
            Label eventLabel = new Label(e.getEventName());
            eventLabel.getStyleClass().add("calendar-event");
            dayBox.getChildren().add(eventLabel);
        }
    }


}
