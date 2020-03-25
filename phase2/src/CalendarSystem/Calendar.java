package CalendarSystem;

import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.Collection;

public class Calendar implements Serializable {
    /*
    Class Calendar is user-specific
     */
    private ArrayList<Event> myEvents = new ArrayList<>();
    private MemoSystem myMemos = new MemoSystem();
    private SeriesSystem mySeries = new SeriesSystem();
    private AlertSystem myAlerts = new AlertSystem();
    public LocalDateTime time = LocalDateTime.now();

    //methods for creating events, alerts
    public void addEvent(Event e) {
        myEvents.add(e);
    }

    public void addIndividualAlert(Event e, String msg, LocalDateTime date) {
        myAlerts.addIndividualAlert(e, msg, date);
    }

    public void addFrequentAlert(Event e, String msg, Duration d) {
        myAlerts.addFrequentAlert(e, msg, d);
    }

    //methods for deleting events, alerts, memos

    public void deleteEvent(Event e) {
        this.myEvents.remove(e);
        this.myAlerts.removeEvent(e);
    }

    public void deleteAlert(Alert a) {
        this.myAlerts.deleteAlert(a);
    }

    public void deleteMemo(Memo m) {
        this.myMemos.deleteMemo(m);
        for (Event e: this.myEvents) {
            e.deleteMemo(m);
        }
    }

    public void deleteAllMemosforEvent(Event e) {
        for (Memo m : e.getMemos()) {
            this.myMemos.deleteMemo(m);
        }
        e.deleteAllMemos();
    }

    public MemoSystem getMyMemos() {
        return myMemos;
    }

    //methods for finding list of events: by tag, memo or date

    /**
     * find events by their tag
     * @param tag: the tag associated with an Event or multiple Events
     * @return A list of events with the input tag
     */
    public ArrayList<Event> findEvent(String tag) {
        ArrayList<Event> events = new ArrayList<>();
        for (Event event: myEvents) {
            if (event.getTag().equals(tag)) {
                events.add(event);
            }
        }
        return events;
    }

    /**
     * find events by date
     * @param date: date of event
     * @return A list of events that are happening during the input date
     */
    public ArrayList<Event> findEvent(LocalDate date) {
        ArrayList<Event> events = new ArrayList<>();
        for (Event event: myEvents) {
            LocalDate start = event.getStartTime().toLocalDate();
            LocalDate end = event.getEndTime().toLocalDate();
            if (date.compareTo(start) >=0 && date.compareTo(end) <= 0) {
                events.add(event);
            }
        }
        return events;
    }

    /**
     * find events by their memo
     * @param memo: a memo associated with an event or multiple events
     * @return A list of events which have the input memo
     */
    public ArrayList<Event> findEvent(Memo memo) {
        ArrayList<Event> events = new ArrayList<>();
        for (Event event: myEvents) {
            for (Memo memo1:event.getMemos()) {
                if (memo1 == memo) {
                    events.add(event);
                }
            }
        }
        return events;
    }

    //methods for getting list of events: past, current or future

    public ArrayList<Event> getMyEvents() {
        return myEvents;
    }

    /**
     * Get the names of all the events in this Calendar
     * @return An Arraylist of the names of events
     */
    public ArrayList<String> getEventNames() {
        ArrayList<String> names = new ArrayList<>();
        for (Event e: myEvents) {
            names.add(e.getEventName());
        }
        return names;
    }

    /**
     * return list of events occurred in the past by time of calendar
     * @return list of past events
     */
    public ArrayList<Event> getPastEvents(){
        ArrayList<Event> events = new ArrayList<>();
        if(myEvents == null || myEvents.isEmpty()){
            return null;
        } else {
            for (Event event : myEvents) {
                if (event.getEndTime().isBefore(time)) {
                    events.add(event);
                }
            }
            return events;
        }
    }

    /**
     * return list of events currently occurring by time of calendar
     * @return list of current events
     */
    public ArrayList<Event> getCurrentEvents(){
        ArrayList<Event> events = new ArrayList<>();
        for (Event event : myEvents) {
            if (event.getEndTime().isAfter(time) && event.getStartTime().isBefore(time)) {
                events.add(event);
            }
        }
        return events;
    }

    /**
     * return list of events occurring in the future by time of calendar
     * @return list of future events
     */
    public ArrayList<Event> getFutureEvents(){
        ArrayList<Event> events = new ArrayList<>();
        if(myEvents == null || myEvents.isEmpty()){
            return null;
        } else {
            for (Event event : myEvents) {
                if (event.getStartTime().isAfter(time)) {
                    events.add(event);
                }
            }
            return events;
        }
    }

    /**
     * precondition: event included
     * @param name: name of event
     * @return event
     */
    public Event getEvent(String name){
        for (Event event: myEvents){
            if (event.getEventName().equals(name))
                return event;
        }
        return null;
    }

    //methods for time
    public void update(){
        time = time.plus(Period.ofDays(1));
        LocalDate date = time.toLocalDate();

        // update the status of events
        for (Event event: myEvents) {
            LocalDate start = event.getStartTime().toLocalDate();
            LocalDate end = event.getEndTime().toLocalDate();
            if (date.compareTo(start) >=0 && date.compareTo(end) <= 0 && !event.getStatus().equals("ongoing")) {
                event.changeStatus("ongoing");
            } else if (date.compareTo(end) > 0 && !event.getStatus().equals("past")) {
                event.changeStatus("past");
            }
        }
    }

    public String getTime(){
        return time.toString();
    }

    public void reset(){
        myEvents = new ArrayList<>();
        time = LocalDateTime.now();
        mySeries = new SeriesSystem();
        myMemos = new MemoSystem();
        myAlerts = new AlertSystem();
    }


    //Create a series from parameters
    public void addSeries(String name, Duration d, Period freq, int num, LocalDateTime first){
        Collection<Event> newEvents = mySeries.buildSeries(name, d, freq, num, first);
        this.myEvents.addAll(newEvents);
    }

    /**
     * Create series from existing events
     */
    public void addSeries(String name, ArrayList<Integer> indexes){
        ArrayList<Event> ls = new ArrayList<>();
        for(Integer i : indexes){
            ls.add(this.myEvents.get(i - 1));
        }
        mySeries.createSeries(name, ls);
    }

    public Collection<Event> findEventsBySeries(String name){
        return mySeries.findEventsBySeries(name);
    }

    //methods for getting alerts: all, based on events or current ones
    public ArrayList<Alert> getAllAlerts(){
        return new ArrayList<>(myAlerts.getAllAlerts());
    }

    public ArrayList<Alert> getAlerts(Event e){
        return new ArrayList<>(myAlerts.getAlerts(e));
    }

    public ArrayList<Alert> getCurrAlerts(){
        return new ArrayList<>(myAlerts.getCurrAlerts());
    }

    //Display events
    public String toString(){
        String s = "";
        for(Event e : myEvents){
            s += e.toString() + "\n";
        }
        if(s.length() != 0){
            s = s.substring(0, s.length() - 1);
        }
        return s;
    }
}
