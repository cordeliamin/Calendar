package CalendarSystem;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Event implements Serializable {

    private String eventName;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String tag;

    /** whether this Event is "past", "ongoing" or "future" */
    private String status;

    /**
     * A list of memos associated with this Event
     */
    public static ArrayList<Memo> memos;

    public Event(String name, LocalDateTime start, LocalDateTime end){
        eventName = name;
        startTime = start;
        endTime = end;
        tag = null;
        status = "future";
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

    public ArrayList<Memo> getMemos() { return memos; }

    public void viewMemos() {
        for (Memo memo:memos) {
            System.out.println(memo);
        }
    }

    public String getTag() { return this.tag; }

    public String getStatus() { return this.status; }

    /**
     * change the status of this Event to "past", "ongoing" or "future"
     * @param newStatus: "past" || "ongoing" || "future"
     */
    public void changeStatus(String newStatus) {
        this.status = newStatus;
    }

    public void setTag(String tag) { this.tag = tag; }

    public String toString(){
        return this.eventName + "\t" + this.startTime.toString() + " to " + this.endTime.toString();
    }
}
