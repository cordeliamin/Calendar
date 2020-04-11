package GUI;

import CalendarSystem.Calendar;
import CalendarSystem.Event;
import CalendarSystem.IndividualAlert;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class IAlertCreatorControl extends Controller {
    private boolean edit_Mode = false;
    private IndividualAlert alert = null;
    private boolean editted = false;
    @FXML Label eventNameLabel;
    @FXML TextField eventName;
    @FXML Label dateLabel;
    @FXML DatePicker date;
    @FXML TextField time;
    @FXML Label messageLabel;
    @FXML TextField message;
    @FXML Button submit;

    @FXML void initEditMode(){
        if(edit_Mode){
            eventName.setText(alert.getName());
            eventName.setEditable(false);

            LocalDateTime dateTime = alert.getTime();
            LocalDate dateVal = dateTime.toLocalDate();
            date.setValue(dateVal);
            time.setText(dateTime.toLocalTime().toString());

            message.setText(alert.getMessage());
        }
    }

    @FXML private void submit() {
        if (!hasFormatErrors()) {
            Calendar calendar = getCalendar();
            String eventNameVal = eventName.getText();
            String messageVal = message.getText();
            date.setConverter(getDateConverter());
            String dateVal = (date.getValue()).format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            String timeVal = time.getText();
            System.out.println("here");
            LocalDateTime dateTimeVal = LocalDateTime.parse(dateVal + " " + timeVal, DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
            assert calendar.getEventNames().contains(eventNameVal) && messageVal != null && dateTimeVal != null;
            calendar.addIndividualAlert(calendar.getEvent(eventNameVal), messageVal, dateTimeVal);
            System.out.println("Added!!");
            if (edit_Mode)
                editted = true;
            try{
                getCalendarManager().saveToFile();

                // close the window
                Stage stage = (Stage) submit.getScene().getWindow();
                stage.close();

            } catch (IOException e) {
                System.out.println("Failed to save Individual Alert to File");
            }
        }
    }

    private boolean hasFormatErrors() {
        Calendar calendar = getCalendar();
        boolean result = false;
        if (!calendar.getEventNames().contains(eventName.getText())) {
            eventNameLabel.setTextFill(Paint.valueOf("red"));
            result = true;
        }
        if (message.getText().equals("")) {
            messageLabel.setTextFill(Paint.valueOf("red"));
            result = true;
        }
        if (date.getValue().toString().equals("")) {
            System.out.println("Dateval: " + date.getValue());
            dateLabel.setTextFill(Paint.valueOf("red"));
            result = true;
        }
        if (!isValidTime(time.getText())) {
            dateLabel.setTextFill(Paint.valueOf("red"));
            result = true;
        }
        return result;
    }

    public boolean isValidTime(String time) {
        Pattern timePattern = Pattern.compile("^([01][0-9]|2[0-3]):[0-5][0-9]$");
        Matcher matchTime = timePattern.matcher(time);
        return matchTime.matches();
    }

    public void setEdit_Mode(boolean edit_Mode) {
        this.edit_Mode = edit_Mode;
        initEditMode();
    }

    public void setAlert(IndividualAlert alert) {
        this.alert = alert;
    }

    public boolean isEditted() {
        return editted;
    }
}


