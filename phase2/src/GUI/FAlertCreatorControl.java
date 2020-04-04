package GUI;

import CalendarSystem.Calendar;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Paint;

import java.io.IOException;
import java.time.Duration;

public class FAlertCreatorControl extends Controller {
    @FXML Label eventNameLabel;
    @FXML TextField eventName;
    @FXML Label frequencyLabel;
    @FXML Label everyLabel;
    @FXML TextField frequency;
    @FXML ChoiceBox<String> unit;
    @FXML Label messageLabel;
    @FXML TextField message;
    @FXML Button submit;

    @FXML private void submit() {
        if (!hasformaterrors()) {
            Calendar calendar = getCalendar();
            try{
                String eventNameVal = eventName.getText();
                int frequencyVal = Integer.parseInt(frequency.getText());
                String messageVal = message.getText();
                String unitVal = unit.getValue();
                Duration durationVal;
                switch (unitVal){
                    case "day(s)": durationVal = Duration.ofDays(frequencyVal);
                        break;
                    case "hour(s)": durationVal = Duration.ofHours(frequencyVal);
                        break;
                    default:
                        throw new IllegalStateException("Unexpected value: " + unitVal);
                }
                calendar.addFrequentAlert(calendar.getEvent(eventNameVal), messageVal, durationVal);
                getCalendarManager().saveToFile();
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
        boolean result = true;
        if(!calendar.getEventNames().contains(eventName.getText())){
            eventNameLabel.setTextFill(Paint.valueOf("red"));
            result = false;
        } if (!message.getText().equals("")){
            messageLabel.setTextFill(Paint.valueOf("red"));
            result = false;
        }
        return result;

    }

}
