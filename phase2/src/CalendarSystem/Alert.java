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

    public Alert(LocalDateTime date, String name, String message){
        this.name = name;
        this.eventTime = date;
        this.message = message;
        this.times = new ArrayList<LocalDateTime>();
    }

    @Override
    public String toString() {
        return "Alert for Event" + name + "occurring at" + eventTime.toString() + ": " + message;
    }

    public ArrayList<LocalDateTime> getTimes(){
        return times;
    }

    protected void addtoTimes(LocalDateTime date){
        this.times.add(date);
    }

    protected void removefromTimes(LocalDateTime date) {this.times.remove(date);}

    protected void resetTimes() {this.times = new ArrayList<>();}

    public LocalDateTime getEventTime(){
        return this.eventTime;
    }

    //Edit Alert

    public void setName(String newName) {
        this.name = newName;
    }

    public void setMessage(String newMessage) {
        this.message = newMessage;
    }

}
