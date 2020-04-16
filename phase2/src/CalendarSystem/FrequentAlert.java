package CalendarSystem;

import java.time.Duration;
import java.time.LocalDateTime;

public class FrequentAlert extends Alert{
    private Duration frequency;

    /**
     * create a frequent alert with the information
     * @param evT datetime of the associated event
     * @param name name of the associated event
     * @param message message content of the alert
     * @param d frequency of the alert (ex. every 1 day)
     */
    public FrequentAlert(LocalDateTime evT, String name, String message, Duration d){
        super(evT, message, name);
        frequency = d;
        setTimesHelper();
        setData("Frequent Alert: " + message + " every " + durationToString(d));
    }

    // methods for editing FrequentAlert

    /**
     * change the frequency of the frequentalert
     * @param newDur new frequency
     */
    public void changeFrequency(Duration newDur) {
        this.resetTimes();
        this.frequency = newDur;
        setTimesHelper();
    }

    /**
     * add every appear datetime to attribute times based on frequency
     */
    private void setTimesHelper(){
        LocalDateTime t = getEventTime();
        do{
            t = t.minus(this.frequency);
        }

        //use system time now - needs to be changed!!
        while (t.isAfter(LocalDateTime.now()));

        //then t is the first alarm time
        while(t.isBefore(this.getEventTime())){
            addtoTimes(t);
            t = t.plus(this.frequency);
        }
    }

    // implement abstract methods

    /**
     * return type of alert
     * @return 'f' -> denoting frequent alert
     */
    @Override
    public String getAlertType() {
        return "f";
    }


    // methods for displaying alert
    /**
     * return the duration in a readable format: ex. 1.5 days -> 1.5D
     * @param duration: duration to be formatted
     * @return formatted string of the frequency
     */
    public String durationToString(Duration duration) {
        if (duration.toDays()!= 0)
            return duration.toDays() + "D";
        return duration.toHours()+"H";
    }

    @Override
    public String toString(){
        return "Frequent Alert: " + getMessage() + " every " + durationToString(frequency);
    }


    //getters
    public Duration getFrequency() {
        return frequency;
    }
}
