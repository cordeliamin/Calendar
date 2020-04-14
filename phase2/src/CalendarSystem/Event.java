package CalendarSystem;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Event implements Serializable {

    private String eventName;
    private LocalDateTime startTime = LocalDateTime.MAX;
    private LocalDateTime endTime = LocalDateTime.MAX;
    private String tag;

    /** whether this Event is "past", "ongoing" or "future" */
    private String status;

    /**
     * A list of memos associated with this Event
     */
    public static ArrayList<Memo> memos = new ArrayList<>();

    /** A list of series associated with this Event */
    public static ArrayList<Series> series = new ArrayList<>();

    /**
     * Constructs an indefinite event.
     * @param name: name of event
     */
    public Event(String name){
        eventName = name;
        tag = "";
        status = "future";
    }

    public Event(String name, LocalDateTime start, LocalDateTime end){
        eventName = name;
        startTime = start;
        endTime = end;
        tag = "";
        status = "future";
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

    public ArrayList<Series> getSeries() {return series;}

    // Edit Event

    /**
     * change the status of this Event to "past", "ongoing" or "future"
     * @param newStatus: "past" || "ongoing" || "future"
     */
    public void changeStatus(String newStatus) {
        this.status = newStatus;
    }

    public void setEventName(String newName) {this.eventName = newName;}

    public void setStartTime(LocalDateTime newStart) {this.startTime = newStart;}

    public void setEndTime(LocalDateTime newEnd) {this.endTime = newEnd;}

    public void addMemo(Memo memo) {memos.add(memo);}

    public void associateSeries(Series newSeries) {
        series.add(newSeries);
    }

    public void deleteMemo(Memo memo) {memos.remove(memo);}

    public void deleteAllMemos() {memos = new ArrayList<>();}

    public void deleteSeries(Series target) {series.remove(target);}

    public void setTag(String tag) { this.tag = tag; }

    public String toString(){
        return this.eventName + "\t" + this.startTime.toString() + " to " + this.endTime.toString();
    }
    public void associateSeries(Series s){
        // TODO: yet to be implemented
    }
}
