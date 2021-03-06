import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.Collection;

public class Series implements Serializable {
    private String name;
    private Collection<Event> myEvents;

    //Construct a series given events
    public Series(String name, Collection<Event> events) {
        this.name = name;
        this.myEvents = events;
    }

    public String getName() {
        return name;
    }

    public Collection<Event> getEvents(){
        return this.myEvents;
    }
}
