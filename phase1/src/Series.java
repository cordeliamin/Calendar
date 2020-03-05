import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.Collection;

public class Series implements Serializable {
    private String name;
    private Collection<Event> myEvents = new ArrayList<>();

    //Construct an empty series
    public Series(String name) {
        this.name = name;
    }

    //Construct a series given events
    public Series(String name, Collection<Event> events) {
        this.name = name;
        this.myEvents = events;
    }

    //Construct a series given duration, frequency, and number of events
    /**
     * @param d The duration of each event in the series
     * @param freq Duration between each event
     * @param num The number of events in the series
     * @param first The dateTime of the first event in the series
     */
    public Collection<Event> constructSeries(Duration d, Period freq, int num, LocalDateTime first){
        myEvents.add(new Event(this.name + " 1", first, first.plus(d)));
        for(int i = 2; i <= num; i++){
            first = first.plus(freq);
            myEvents.add(new Event(this.name + " 1", first, first.plus(d)));
        }
        return this.myEvents;
    }
}
