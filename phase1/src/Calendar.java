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
    private ArrayList<Series> mySeries = new ArrayList<>();
    private MemoSystem memoSystem;
    private SeriesSystem mySeries  = new SeriesSystem();
    public LocalDateTime time = LocalDateTime.now();

    public void update(){
        time = time.plus(Period.ofDays(1));
    }

    public String getTime(){
        return time.toString();
    }

    /*
    creates event, alerts,
     */
    public void addEvent(Event e){
        myEvents.add(e);
    }

    //Create a series from a selection of events
    public void createSeries(String name, Collection<Event> ls){
         Series s = new Series(name, ls);
         mySeries.add(s);
    }

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
        if (events.isEmpty()) {
            System.out.println("No events found.");
            return null;
        } else {
            return events;
        }
    }

    /**
     * find events by date
     * @param date
     * @return A list of events
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

    //Create a series from parameters
    public void createSeries(String name, Duration d, Period freq, int num, LocalDateTime first){
        Series s = new Series(name);
        this.myEvents.addAll(s.constructSeries(d, freq, num, first));
        this.mySeries.add(s);
    }

    /**
     *
     * @param seriesName The name of the series to search for
     * @return Returns the events in the series if it exists, null otherwise
     */
    public Collection<Event> findEventsBySeries(String seriesName){
        for(Series s : this.mySeries){
            if(s.getName().equals(seriesName)){
                return s.getEvents();
            }
        }
        return null;
    }

    public void reset(){
        myEvents = new ArrayList<>();
        time = LocalDateTime.now();
    }

    //Create a series from parameters
    public void addSeries(String name, Duration d, Period freq, int num, LocalDateTime first){
        Collection<Event> newEvents = mySeries.buildSeries(name, d, freq, num, first);
        this.myEvents.addAll(newEvents);
    }

    /**
     * Create series from existing events
     */
    public void addSeries(String name, Collection<Event> ls){
        mySeries.createSeries(name, ls);
    }

    public Collection<Event> findEventsBySeries(String name){
        return mySeries.findEventsBySeries(name);
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
