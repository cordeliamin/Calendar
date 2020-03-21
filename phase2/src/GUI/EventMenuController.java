package GUI;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import CalendarSystem.Event;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;
import java.time.LocalDateTime;

public class EventMenuController extends Controller {

    @FXML Button returnToMenu;
    @FXML Button eventCreator;
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
}
