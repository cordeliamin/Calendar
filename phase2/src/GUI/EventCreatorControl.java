package GUI;

import CalendarSystem.Memo;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.paint.Paint;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.time.LocalDateTime;
import CalendarSystem.Event;
import javafx.util.StringConverter;

public class EventCreatorControl extends Controller {

    private TableView<Event> eventTable;
    @FXML private ComboBox<String> memoOptions;
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
        for (Memo m : getCalendar().getMyMemos().getMemos()) {
            memoOptions.getItems().add(m.getNote());
        }
    }

    @FXML private void createEvent() throws IOException {
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
                getCalendar().addEvent(newEvent);
                eventTable.getItems().add(newEvent);
                setEventMemo(newEvent);
                getCalendarManager().saveToFile();
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
        memoOptions.getEditor().setText("");
    }

    private void initDatePicker() {
        String pattern = "dd/MM/yyyy";
        StringConverter<LocalDate> converter = new StringConverter<LocalDate>() {
            DateTimeFormatter dateFormatter =
                    DateTimeFormatter.ofPattern(pattern);
            @Override
            public String toString(LocalDate date) {
                if (date != null) {
                    return dateFormatter.format(date);
                } else {
                    return "";
                }
            }
            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, dateFormatter);
                } else {
                    return null;
                }
            }
        };
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

    private void setEventMemo(Event newEvent) {
        ArrayList<Event> eventsToAdd = new ArrayList<>();
        eventsToAdd.add(newEvent);
        String selectedMemo = memoOptions.getValue();
        if (selectedMemo == null) {selectedMemo = "";}
        if (!selectedMemo.equals("") &&
                !memoOptions.getItems().contains(selectedMemo)) {
            getCalendar().getMyMemos().createMemo(eventsToAdd, selectedMemo);
            memoOptions.getItems().add(selectedMemo);
        } else if (memoOptions.getItems().contains(selectedMemo)) {
            for (Memo m : getCalendar().getMyMemos().getMemos()) {
                if (m.getNote().equals(selectedMemo)) {
                    newEvent.getMemos().add(m);
                    break;
                }
            }
        }
    }
}
