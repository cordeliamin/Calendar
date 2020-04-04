package GUI;

import CalendarSystem.Calendar;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.paint.Paint;
import javafx.util.StringConverter;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class IAlertCreatorControl extends Controller {
    @FXML Label eventNameLabel;
    @FXML TextField eventName;
    @FXML Label dateLabel;
    @FXML DatePicker date;
    @FXML TextField time;
    @FXML Label messageLabel;
    @FXML TextField message;
    @FXML Button submit;

    @FXML private void submit() {
        if (!hasFormatErrors()) {
            Calendar calendar = getCalendar();
            String eventNameVal = eventName.getText();
            String messageVal = message.getText();
            date.setConverter(getDateConverter());
            String dateVal = (date.getValue()).format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            String timeVal = time.getText();
            LocalDateTime dateTimeVal = LocalDateTime.parse(dateVal + " " + timeVal, DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
            calendar.addIndividualAlert(calendar.getEvent(eventNameVal), messageVal, dateTimeVal);
            try{
                getCalendarManager().saveToFile();
            } catch (IOException e) {
                System.out.println("Failed to save Individual Alert to File");
            }
        }
    }

    private boolean hasFormatErrors() {
        Calendar calendar = getCalendar();
        boolean result = true;
        if (!calendar.getEventNames().contains(eventName.getText())) {
            eventNameLabel.setTextFill(Paint.valueOf("red"));
            result = false;
        }
        if (message.getText().equals("")) {
            messageLabel.setTextFill(Paint.valueOf("red"));
            result = false;
        }
        if (date.getValue().toString().equals("")) {
            dateLabel.setTextFill(Paint.valueOf("red"));
            result = false;
        }
        if (!isValidTime(time.getText())) {
            dateLabel.setTextFill(Paint.valueOf("red"));
            result = false;
        }
        return result;

    }

    public boolean isValidTime(String time) {
        Pattern timePattern = Pattern.compile("^([01][0-9]|2[0-3]):[0-5][0-9]$");
        Matcher matchTime = timePattern.matcher(time);
        return matchTime.matches();
    }
}


