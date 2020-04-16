package CalendarSystem;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;

public abstract class Alert implements Serializable {
    private String name;
    private LocalDateTime eventTime;
    private ArrayList<LocalDateTime> times;
    private String message;
    private String data;

    /**
     * create an alert with the given event date and name, and message for alert
     * @param date date of the associated event
     * @param message message of the alert
     * @param name name of the associated event
     */
    public Alert(LocalDateTime date, String message, String name){
        this.name = name;
        this.eventTime = date;
        this.message = message;
        this.times = new ArrayList<>();
    }


    /**
     * add the date to the list of all alert times
     * @param date date to be added
     */
    protected void addtoTimes(LocalDateTime date){
        this.times.add(date);
    }

    /**
     * remove the date from the list of all alert times
     * @param date date to be removed
     */
    protected void removefromTimes(LocalDateTime date) {this.times.remove(date);}

    /**
     * remove all of the alert times
     */
    protected void resetTimes() {this.times = new ArrayList<>();}


    @Override
    public String toString() {
        return "Alert for Event" + name + "occurring at" + eventTime.toString() + ": " + message;
    }

    //abstract methods
    public abstract String getAlertType();

    //getters and setters
    public ArrayList<LocalDateTime> getTimes(){
        return times;
    }

    public LocalDateTime getEventTime(){
        return this.eventTime;
    }

    public void setName(String newName) {
        this.name = newName;
    }

    public void setMessage(String newMessage) {
        this.message = newMessage;
    }

    public String getName() {
        return name;
    }

    public String getMessage() {
        return message;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
