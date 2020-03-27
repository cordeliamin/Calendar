package GUI;

import CalendarSystem.CalendarManager;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;

public class CalendarCreatorPopUp {

    private Stage calMakerWindow;
    private Label invalidNameLabel;
    private Label instructions;
    private Button createCalendarButton;
    private TextField userInput;
    private CalendarManager calendarManager;
    private boolean eventMade;

    public CalendarCreatorPopUp(CalendarManager calendarManager) {
        this.calendarManager = calendarManager;
        this.invalidNameLabel = new Label("Invalid Calendar Name!");
        this.instructions = new Label("New Calendar Name:");
        this.createCalendarButton = new Button("Create Calendar");
        this.calMakerWindow = new Stage();
        this.userInput = new TextField();
        this.eventMade = false;
        invalidNameLabel.setVisible(false);
        createCalendarButton.setOnAction(e -> createCalendar());
        display();
    }

    protected String getNewCalendarName() {
        if (eventMade) {
            return userInput.getText();
        } else {
            return "";
        }
    }

    private void display() {
        //Make New pop up window
        calMakerWindow.setTitle("Create Event");
        calMakerWindow.initModality(Modality.APPLICATION_MODAL);
        calMakerWindow.setResizable(false);

        //Create new scene to display
        VBox newScene = new VBox();
        newScene.setSpacing(8.0);
        newScene.setPadding(new Insets(30, 50, 10, 20));
        newScene.getChildren().addAll(invalidNameLabel, instructions, userInput, createCalendarButton);

        //Set and display scene to new stage
        Scene enterCalendarField = new Scene(newScene, 300, 200);
        enterCalendarField.getStylesheets().add("GUI/Default.css");
        invalidNameLabel.getStyleClass().add("label-error");

        calMakerWindow.setScene(enterCalendarField);
        calMakerWindow.showAndWait();
    }

    private void createCalendar() {
        if (isValidName(userInput.getText())) {
            try {
                calendarManager.createCalendar(userInput.getText());
                eventMade = true;
                calMakerWindow.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean isValidName(String input) {
        if (input.equals("") || input.contains(".") || input.contains(" ")) {
            invalidNameLabel.setText("Invalid Calendar Name!");
            invalidNameLabel.setVisible(true);
            return false;
        } else if (calendarManager.getUserCalendars().contains(input)){
            invalidNameLabel.setText("This Calendar Already Exists!");
            invalidNameLabel.setVisible(true);
            return false;
        } else {
            return true;
        }
    }

}
