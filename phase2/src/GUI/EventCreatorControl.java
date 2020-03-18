package GUI;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.paint.Paint;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.time.LocalDateTime;
import CalendarSystem.Event;


public class EventCreatorControl extends Controller {

    private TableView<Event> eventTable;
    @FXML private TextField eventName;
    @FXML private DatePicker eventStartDate;
    @FXML private TextField eventStartTime;
    @FXML private DatePicker eventEndDate;
    @FXML private TextField eventEndTime;
    @FXML private Label successMsg;
    @FXML private Label errorMsg;
    @FXML private Label eventNameLab;
    @FXML private Label startDateLab;
    @FXML private Label startTimeLab;
    @FXML private Label endDateLab;
    @FXML private Label endTimeLab;

    @FXML private void createEvent() {
        hideMessage();
        String nameInput = eventName.getText();
        String startInput = eventStartDate.getEditor().getText();
        String endInput = eventEndDate.getEditor().getText();
        if (hasFormatErrors(nameInput, startInput, endInput)) {
            errorMsg.setVisible(true);
        } else {
            LocalDateTime[] timeInterval = convertToDates(startInput, endInput);
            if (timeInterval[1].isAfter(timeInterval[0])) {
                Event newEvent = new Event(nameInput, timeInterval[0], timeInterval[1]);
                userCalendar.addEvent(newEvent);
                eventTable.getItems().add(newEvent);
                successMsg.setVisible(true);
                clearFields();
            } else {
                displayInvalidDateInput();
            }
        }
    }

    private void clearFields() {
        eventName.clear();
        eventStartDate.getEditor().clear();
        eventStartTime.clear();
        eventEndDate.getEditor().clear();
        eventEndTime.clear();
    }

    private void displayInvalidDateInput() {
        errorMsg.setVisible(true);
        startDateLab.setTextFill(Paint.valueOf("red"));
        endDateLab.setTextFill(Paint.valueOf("red"));
        startTimeLab.setTextFill(Paint.valueOf("red"));
        endTimeLab.setTextFill(Paint.valueOf("red"));
    }

    private LocalDateTime[] convertToDates(String startInput, String endInput) {
        String [] startDate = startInput.split("/");
        String [] endDate = endInput.split("/");
        String [] startTime = eventStartTime.getText().split(":");
        String [] endTime = eventEndTime.getText().split(":");
        LocalDateTime start = LocalDateTime.of(Integer.parseInt(startDate[2]),
                Integer.parseInt(startDate[1]), Integer.parseInt(startDate[0]),
                Integer.parseInt(startTime[0]), Integer.parseInt(startTime[1]));
        LocalDateTime end = LocalDateTime.of(Integer.parseInt(endDate[2]),
                Integer.parseInt(endDate[1]), Integer.parseInt(endDate[0]),
                Integer.parseInt(endTime[0]), Integer.parseInt(endTime[1]));
        return new LocalDateTime[] {start, end};
    }

    protected void setTableToModify(TableView<Event> eventTable) {
        this.eventTable = eventTable;
    }

    @FXML private void hideMessage() {
        errorMsg.setVisible(false);
        successMsg.setVisible(false);
        eventNameLab.setTextFill(Paint.valueOf("black"));
        startDateLab.setTextFill(Paint.valueOf("black"));
        endDateLab.setTextFill(Paint.valueOf("black"));
        startTimeLab.setTextFill(Paint.valueOf("black"));
        endTimeLab.setTextFill(Paint.valueOf("black"));
    }

    private boolean hasFormatErrors(String nameInput, String startInput, String endInput) {
        boolean hasError = false;

        Pattern dateRule = Pattern.compile("^[0-3][0-9]/[01][0-9]/([0-9]+)$");
        Matcher matchSDate = dateRule.matcher(startInput);
        Matcher matchEDate = dateRule.matcher(endInput);

        Pattern timeRule = Pattern.compile("^([01][0-9]|2[0-3]):[0-5][0-9]$");
        Matcher matchSTime = timeRule.matcher(eventStartTime.getText());
        Matcher matchETime = timeRule.matcher(eventEndTime.getText());

        if (nameInput.equals("")) {
            eventNameLab.setTextFill(Paint.valueOf("red"));
            hasError = true;
        } if (!matchSDate.matches()) {
            startDateLab.setTextFill(Paint.valueOf("red"));
            hasError = true;
        } if (!matchEDate.matches()) {
            endDateLab.setTextFill(Paint.valueOf("red"));
            hasError = true;
        } if (!matchSTime.matches()) {
            startTimeLab.setTextFill(Paint.valueOf("red"));
            hasError = true;
        } if (!matchETime.matches()) {
            endTimeLab.setTextFill(Paint.valueOf("red"));
            hasError = true;
        }
        return hasError;
    }
}
