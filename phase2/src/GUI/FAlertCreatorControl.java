package GUI;

import CalendarSystem.Calendar;
import CalendarSystem.FrequentAlert;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.Duration;

public class FAlertCreatorControl extends Controller {
    private boolean edit_Mode = false;
    private FrequentAlert alert = null;
    private boolean editted = false;
    @FXML
    private Label eventNameLabel;
    @FXML
    private TextField eventName;
    @FXML
    private Label frequencyLabel;
    @FXML
    private Label everyLabel;
    @FXML
    private TextField frequency;
    @FXML
    private ChoiceBox<String> unit;
    @FXML
    private Label messageLabel;
    @FXML
    private TextField message;
    @FXML
    private Button submit;

    @FXML
    private void initEditMode() {
        System.out.println("initialize called");
        if (edit_Mode) {
            eventName.setText(alert.getName());
            eventName.setEditable(false);

            String frequencyFormat = alert.durationToString(alert.getFrequency());
            frequency.setText(frequencyFormat.substring(0, frequencyFormat.length() - 1));
            if (frequencyFormat.charAt(frequencyFormat.length() - 1) == 'D')
                unit.setValue("day(s)");
            else
                unit.setValue("hour(s)");

            message.setText(alert.getMessage());
        }
    }

    @FXML private void submit() {
        if (!hasformaterrors()) {
            Calendar calendar = getCalendar();
            String eventNameVal = eventName.getText();
            int frequencyVal = Integer.parseInt(frequency.getText());
            String messageVal = message.getText();
            String unitVal = unit.getValue();
            Duration durationVal;
            try{
                switch (unitVal){
                    case "day(s)": durationVal = Duration.ofDays(frequencyVal);
                        break;
                    case "hour(s)": durationVal = Duration.ofHours(frequencyVal);
                        break;
                    default:
                        throw new IllegalStateException("Unexpected value: " + unitVal);
                }
                calendar.addFrequentAlert(calendar.getEvent(eventNameVal), messageVal, durationVal);
                System.out.println("here (f)");
                if (edit_Mode)
                    editted = true;
                getCalendarManager().saveToFile();

                // close the window
                Stage stage = (Stage) submit.getScene().getWindow();
                stage.close();

            } catch(NumberFormatException e){
                frequencyLabel.setTextFill(Paint.valueOf("red"));
            } catch(IllegalStateException e){
                System.out.println("this should not happen");
            } catch (IOException e){
                System.out.println("Failed to save to file");
            }
        }

    }

    private boolean hasformaterrors(){
        Calendar calendar = getCalendar();
        boolean result = false;
        if(!calendar.getEventNames().contains(eventName.getText())){
            eventNameLabel.setTextFill(Paint.valueOf("red"));
            result = true;
        } if (message.getText().equals("")){
            messageLabel.setTextFill(Paint.valueOf("red"));
            result = true;
        }
        return result;

    }

    public void setEdit_Mode(boolean edit_Mode) {
        this.edit_Mode = edit_Mode;
        initEditMode();
    }

    public void setAlert(FrequentAlert alert) {
        this.alert = alert;
    }

    public boolean isEditted() {
        return editted;
    }
}
