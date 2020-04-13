package GUI;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import CalendarSystem.Event;
import javafx.stage.Modality;
import javafx.stage.Popup;
import javafx.stage.Stage;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EventMenuController extends Controller {

    @FXML Button returnToMenu;
    @FXML TextField searchBar;
    @FXML ChoiceBox<String> eventSort;
    @FXML TableView<Event> eventTable;
    @FXML MenuItem linkEventOpt;
    @FXML MenuItem deleteEvent;
    @FXML MenuItem editEvent;
    @FXML TableColumn<Event, String> eventName;
    @FXML TableColumn<Event, LocalDateTime> eventStart;
    @FXML TableColumn<Event, LocalDateTime> eventEnd;

    @FXML private void goBackToMenu() throws IOException {
        setScreen("MainMenuScene.fxml", returnToMenu);
    }

    @Override
    protected void initScreen() {
        //Populate eventTable
        eventName.setCellValueFactory(new PropertyValueFactory<>("eventName"));
        eventStart.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        eventEnd.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        ObservableList<Event> eventTableItems = FXCollections.observableArrayList();
        eventTableItems.addAll(getCalendar().getMyEvents());
        eventTable.setItems(eventTableItems);
        initSelectionSettings();

        //Populate eventSort and set it so it activates on a new selection
        eventSort.getItems().addAll("All", "Past", "Current", "Upcoming");
        eventSort.setValue("All");
        eventSort.getSelectionModel().selectedItemProperty().addListener(
                (v, oldVal, newVal) -> sortEventTable(newVal));
    }

    private void initSelectionSettings() {
        eventTable.setPlaceholder(new Label("No Events Found"));
        eventTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        eventTable.getSelectionModel().getSelectedItems().addListener((ListChangeListener<Event>) change -> {
            if (change.getList().size() > 1) {
                linkEventOpt.setVisible(true);
                deleteEvent.setVisible(true);
                editEvent.setVisible(false);
            } else if (change.getList().size() == 1) {
                deleteEvent.setVisible(true);
                linkEventOpt.setVisible(false);
                editEvent.setVisible(true);
            } else {
                linkEventOpt.setVisible(false);
                deleteEvent.setVisible(false);
                editEvent.setVisible(false);
            }
        });
    }

    @FXML private void sortEventTable(String sortBy) {
        eventTable.getItems().clear();
        switch (sortBy) {
            case "Past":
                eventTable.getItems().addAll(getCalendar().getPastEvents());
                break;
            case "Current":
                eventTable.getItems().addAll(getCalendar().getCurrentEvents());
                break;
            case "Upcoming":
                eventTable.getItems().addAll(getCalendar().getFutureEvents());
                break;
            default:
                eventTable.getItems().addAll(getCalendar().getMyEvents());
        }
    }

    @FXML private void createNewEvent() throws IOException {
        //Make New pop up window
        Stage eventMakerWindow = new Stage();
        eventMakerWindow.setTitle("Create Event");
        eventMakerWindow.initModality(Modality.APPLICATION_MODAL);
        eventMakerWindow.setResizable(false);

        //Create new scene to display
        FXMLLoader loader = setNewWindowAndGetLoader("EventCreatorScene.fxml",
                eventMakerWindow, 600, 350);

        //Pass in additional table data
        EventCreatorControl eventMaker = loader.getController();
        eventMaker.setTableToModify(eventTable);

        eventMakerWindow.showAndWait();
    }

    @FXML private void linkEvents() {
        Label instructions = new Label("Event Series Name:");
        TextField eventsName = new TextField();
        Button createSeries = new Button("Create Series");
        PopUp linkName = new PopUp("Series Name", getTheme(), 9.0, 40, 50, 10, 20);
        linkName.getContent().addAll(instructions, eventsName, createSeries);
        createSeries.setOnAction(e -> {
            try {
                if (!eventsName.getText().equals("")) {
                    ArrayList<Event> toLink = new ArrayList<>(eventTable.getSelectionModel().getSelectedItems());
                    getCalendar().addSeries(eventsName.getText(), toLink);
                }
            } catch (NullPointerException nullp) {}
            linkName.exit();
        });
        linkName.display();
    }

    @FXML private void deleteEvents() throws IOException{
        ArrayList<Event> items = new ArrayList<>(eventTable.getSelectionModel().getSelectedItems());
        for (Event e : items) {
            getCalendar().deleteEvent(e);
            eventTable.getItems().remove(e);
        }
        getCalendarManager().saveToFile();
    }

    // Edit events

    @FXML private void editEvent() throws IOException {

        Event event = eventTable.getSelectionModel().getSelectedItem();

        //Make New pop up window
        Stage eventMakerWindow = new Stage();
        eventMakerWindow.setTitle("Edit Event");
        eventMakerWindow.initModality(Modality.APPLICATION_MODAL);
        eventMakerWindow.setResizable(false);

        //Create new scene to display
        FXMLLoader loader = setNewWindowAndGetLoader("EventEditorScene.fxml",
                eventMakerWindow, 600, 350);

        //Pass in additional table data
        EventEditorControl eventEditor = loader.getController();
        eventEditor.setTableToModify(eventTable);
        eventEditor.setEventToModify(event);

        eventMakerWindow.showAndWait();
    }

    @FXML private void searchForEvent() {
        String userInput = searchBar.getText();
        eventTable.getItems().clear();
        if (!userInput.equals("")) {
            eventTable.getItems().addAll(getCalendar().findEventsBySeries(userInput));
            eventTable.getItems().addAll(getCalendar().findEvent(userInput));
            if (getCalendar().getEvent(userInput) != null) {
                eventTable.getItems().add(getCalendar().getEvent(userInput));
            }
            if (isDateFormat(userInput)) {
                String[] date = userInput.split("/");
                eventTable.getItems().addAll(getCalendar().findEvent(LocalDate.of(
                        Integer.parseInt(date[2]), Integer.parseInt(date[1]), Integer.parseInt(date[0]))));
            }
        }
    }

    private Boolean isDateFormat(String date) {
        Pattern dateRule = Pattern.compile("^[0-3][0-9]/[01][0-9]/([0-9]+)$");
        Matcher matchDate = dateRule.matcher(date);
        return matchDate.matches();
    }
}
