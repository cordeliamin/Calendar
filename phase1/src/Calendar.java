import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.Collection;

public class Calendar implements Serializable {
    /*
    Class Calendar is user-specific
     */
    private ArrayList<Event> myEvents = new ArrayList<>();
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
