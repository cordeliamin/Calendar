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

    public Alert(LocalDateTime date, String name){
        this.name = name;
        this.eventTime = date;
        this.times = new ArrayList<LocalDateTime>();
    }

    @Override
    public String toString() {
        return "Alert for Event" + name + "occurring at" + eventTime.toString();
    }

    public ArrayList<LocalDateTime> getTimes(){
        return times;
    }

    protected void addtoTimes(LocalDateTime date){
        this.times.add(date);
    }

    public LocalDateTime getEventTime(){
        return this.eventTime;
    }

}
