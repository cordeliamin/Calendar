package CalendarSystem;

import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.Collection;

public class SeriesSystem implements Serializable {
    private ArrayList<Series> mySeries = new ArrayList<>();

    //Create a series from a selection of events
    public void createSeries(String name, Collection<Event> ls){
        Series s = new Series(name, ls);
        mySeries.add(s);

        for (Event e: ls){
            e.associateSeries(s);
            // TODO: this method has not been implemented
        }
    }

    //Construct a series given duration, frequency, and number of events
    /**
     * @param d The duration of each event in the series
     * @param freq Duration between each event
     * @param num The number of events in the series
     * @param first The dateTime of the first event in the series
     */
    public Collection<Event> buildSeries(String name, Duration d, Period freq, int num, LocalDateTime first){
        ArrayList<Event> newEvents = new ArrayList<>();
        newEvents.add(new Event(name + ": Event 1", first, first.plus(d)));
        for(int i = 2; i <= num; i++){
            first = first.plus(freq);
            newEvents.add(new Event(name + ": Event " + i , first, first.plus(d)));
        }
        createSeries(name, newEvents);
        return newEvents;
    }

    /**
     * Add event(s) to a series.
     */
    public void addEvent(Series series, Event e){
        series.addEvent(e);
        e.associateSeries(series);
    }


    /**
     *
     * @param seriesName The name of the series to search for
     * @return Returns the events in the series if it exists, an empty list otherwise
     */
    public Collection<Event> findEventsBySeries(String seriesName){
        for(Series s : this.mySeries){
            if(s.getName().equals(seriesName)){
                return s.getEvents();
            }
        }
        return new ArrayList<>();
    }
}
