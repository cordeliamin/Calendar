package GUI;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.paint.Paint;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.time.LocalDateTime;
import CalendarSystem.Event;
import javafx.util.StringConverter;
import java.text.DateFormat;

public class EventEditorControl extends Controller{

    private TableView<Event> eventTable;
    private Event event;
    private String ogEventName;
    private LocalDateTime[] ogTimes;
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

    @Override
    protected void initScreen() {
        initDatePicker();
    }

    @FXML private void editEvent() throws IOException {
        hideMessage();
        String nameInput = eventName.getText();
        String startInput = eventStartDate.getEditor().getText();
        String endInput = eventEndDate.getEditor().getText();
        if (hasFormatErrors(nameInput, startInput, endInput)) {
            errorMsg.setVisible(true);
        } else {
            LocalDateTime[] timeInterval = convertToDates(startInput, endInput);
            if (timeInterval[1].isAfter(timeInterval[0])) {
                if (!ogEventName.equals(nameInput) || !Arrays.equals(ogTimes, timeInterval)) {
                    changeEventName(event, ogEventName, nameInput);
                    changeEventTime(event, ogTimes, timeInterval);
                    getCalendarManager().saveToFile();
                    eventTable.refresh();
                    successMsg.setVisible(true);
                }
            } else {
                displayInvalidDateInput();
            }
        }
    }

    private void changeEventName(Event event, String ogName, String newName) {
        if (!ogName.equals(newName)) {
            getCalendar().changeEventName(event, newName);
        }
    }

    private void changeEventTime(Event event, LocalDateTime[] oldDate, LocalDateTime[] newDate) {
        if (!oldDate[0].equals(newDate[0]) || !oldDate[1].equals(newDate[1])) {
            getCalendar().changeEventTime(event, newDate[0], newDate[1]);
        }
    }

    private void initDatePicker() {
        StringConverter<LocalDate> converter = getDateConverter();
        eventStartDate.setConverter(converter);
        eventEndDate.setConverter(converter);
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
    protected void setEventToModify(Event event) {
        this.event = event;
        eventName.setText(event.getEventName());
        eventStartDate.setValue(event.getStartTime().toLocalDate());
        eventStartTime.setText(timeFormatter(event.getStartTime()));
        eventEndDate.setValue(event.getEndTime().toLocalDate());
        eventEndTime.setText(timeFormatter(event.getEndTime()));

        ogEventName = event.getEventName();
        LocalDateTime ogEventStartTime = event.getStartTime();
        LocalDateTime ogEventEndTime = event.getEndTime();
        ogTimes = new LocalDateTime[]{ogEventStartTime, ogEventEndTime};
    }

    @FXML private void hideMessage() {
        errorMsg.setVisible(false);
        successMsg.setVisible(false);
        eventNameLab.getStyleClass().add("label");
        startDateLab.getStyleClass().add("label");
        endDateLab.getStyleClass().add("label");
        startTimeLab.getStyleClass().add("label");
        endTimeLab.getStyleClass().add("label");
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

    // helper methods

    // modelled from code in https://kodejava.org/how-do-i-convert-date-to-string/
    private String dateFormatter(LocalDateTime date) {
        String pattern = "dd/MM/YYYY";
        DateFormat df = new SimpleDateFormat(pattern);
        return df.format(date.toLocalDate());
    }

    private String timeFormatter(LocalDateTime dt) {
        String hour = Integer.toString(dt.getHour());
        String minute = Integer.toString(dt.getMinute());

        String time;

        if ( hour.length() < 2) {
            if (minute.length() < 2) {
                time = "0" + hour + ":" + "0" + minute;
                return time;
            } else {
                time = "0" + hour + ":" + minute;
                return time;
            }
        } else {
            if (minute.length() < 2) {
                time = hour + ":" + "0" + minute;
                return time;
            } else {
                time = hour + ":" + minute;
                return time;
            }
        }
    }

}
