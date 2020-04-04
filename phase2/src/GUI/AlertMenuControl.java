package GUI;

import CalendarSystem.Alert;
import CalendarSystem.Event;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Observable;

public class AlertMenuControl extends Controller{
    @FXML private TableView<Alert> alertTableView;
    @FXML private TableColumn<Alert, String> nameColumn;
    @FXML private TableColumn<Alert, String> messageColumn;
    @FXML private TableColumn<Alert, List<LocalDateTime>> timesColumn;
    @FXML private Button createIAlert;
    @FXML private Button createFAlert;

    @Override
    protected void initScreen() {
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        messageColumn.setCellValueFactory(new PropertyValueFactory<>("message"));
        timesColumn.setCellValueFactory(new  PropertyValueFactory<>("times"));
        ObservableList<Alert> alertTableItems = FXCollections.observableArrayList();
        alertTableItems.addAll(getCalendar().getAllAlerts());
        alertTableView.setItems(alertTableItems);
    }

    @FXML private void createIndividualAlert(){
        try{
            FXMLLoader loader = openWindowAndGetLoader("Create Individual Alert",  "IAlertCreator.fxml");
            initScreen();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    @FXML private void createFrequentAlert(){
        try{
            openWindowAndGetLoader("Create Frequent Alert", "FAlertCreator.fxml");
            initScreen();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
}
