import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

public class Series implements Serializable {
    private String name;
    private Collection<Event> myEvents = new ArrayList<Event>();

    //Construct a series given events
    public Series(String name, Collection<Event> events) {
        this.name = name;
        this.myEvents = events;
    }

    //#TODO
    // Construct a series given duration, frequency, and number of events
//    public Series(String name, ){
//
//    }
}
