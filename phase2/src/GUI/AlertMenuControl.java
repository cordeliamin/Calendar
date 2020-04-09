package GUI;

import CalendarSystem.Alert;
import CalendarSystem.AlertSystem;
import CalendarSystem.AlertSystemData;
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
import java.util.Map;
import java.util.Observable;
import java.util.Set;

public class AlertMenuControl extends Controller{
    @FXML private TableView<AlertSystemData> upcomingTableView;
    @FXML private TableView<Alert> allTableView;
    @FXML private TableColumn<Alert, String> allNameColumn;
    @FXML private TableColumn<Alert, String> allDataColumn;
    @FXML private TableColumn<AlertSystemData, String> nameColumn;
    @FXML private TableColumn<AlertSystemData, String> messageColumn;
    @FXML private TableColumn<AlertSystemData, LocalDateTime> timeColumn;
    @FXML private TableColumn<AlertSystemData, String> typeColumn;
    @FXML private Button createIAlert;
    @FXML private Button createFAlert;
    @FXML private Button edit;
    @FXML private Button delete;
    @FXML private Button returnToMenu;
    @FXML private Label upcoming;
    @FXML private Label all;

    @Override
    protected void initScreen() {
//        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
//        messageColumn.setCellValueFactory(new PropertyValueFactory<>("message"));
//        timesColumn.setCellValueFactory(new  PropertyValueFactory<>("times"));
//        ObservableList<Alert> alertTableItems = FXCollections.observableArrayList();
//        alertTableItems.addAll(getCalendar().getAllAlerts());
//        alertTableView.setItems(alertTableItems);

        //Populate table
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("time"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("eventName"));
        messageColumn.setCellValueFactory(new PropertyValueFactory<>("message"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        ObservableList<AlertSystemData> upcomingAlertTableItems = FXCollections.observableArrayList();

        //add by running through alert system
        Map<LocalDateTime, List<Alert>> dateAlertsMap = getCalendar().getMyAlerts().getDateAlertsMap();
        Set<LocalDateTime> alertTimes = dateAlertsMap.keySet();
        for(LocalDateTime d: alertTimes){
            List<Alert> alerts = dateAlertsMap.get(d);
            for(Alert a: alerts){
                upcomingAlertTableItems.add(new AlertSystemData(d, a.getName(), a.getMessage(), a.getAlertType()));
            }
        }

        //Populate all alerts table
        allNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        allDataColumn.setCellValueFactory(new PropertyValueFactory<>("data"));
        ObservableList<Alert> allAlertTableItems = FXCollections.observableArrayList();
        allAlertTableItems.addAll(getCalendar().getAllAlerts());

        allTableView.setItems(allAlertTableItems);
        upcomingTableView.setItems(upcomingAlertTableItems);
    }

    @FXML private void createIndividualAlert(){
        try{
            openWindowAndGetLoader("Create Individual Alert",  "IAlertCreator.fxml");
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

    @FXML private void editAlert(){
    }

    @FXML private void deleteAlert(){
    }

    @FXML private void setReturnToMenu() throws IOException{
        setScreen("MainMenuScene.fxml", returnToMenu);
    }
}
