package GUI;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import CalendarSystem.Event;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EventMenuController extends Controller {

    @FXML Button returnToMenu;
    @FXML Button eventCreator;
    @FXML Button searchEvent;
    @FXML TextField searchBar;
    @FXML ChoiceBox<String> eventSort;
    @FXML TableView<Event> eventTable;
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

        //Populate eventSort and set it so it activates on a new selection
        eventSort.getItems().addAll("All", "Past", "Current", "Upcoming");
        eventSort.setValue("All");
        eventSort.getSelectionModel().selectedItemProperty().addListener(
                (v, oldVal, newVal) -> sortEventTable(newVal));
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

        //Create new scene to display
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("EventCreatorScene.fxml"));
        Parent newScene = loader.load();
        EventCreatorControl eventMaker = loader.getController();
        eventMaker.setCalendar(getCalendarManager());
        eventMaker.setTableToModify(eventTable);

        //Set and display scene to new stage
        eventMakerWindow.setScene(new Scene(newScene, 600, 350));
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
