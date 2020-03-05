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
    private ArrayList<Series> mySeries = new ArrayList<>();
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
        mySeries = new ArrayList<>();
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
