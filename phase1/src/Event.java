import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Event implements Serializable {

    private String eventName;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    /** A list of memos associated with this Event */
    private ArrayList<Memo> memos;


    public Event(String name, LocalDateTime start, LocalDateTime end){
        eventName = name;
        startTime = start;
        endTime = end;
        memos = new ArrayList<>();
    }

    public String getEventName() {
        return eventName;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public String toString(){
        return this.eventName + "\t" + this.startTime.toString() + " to " + this.endTime.toString();
    }
}
