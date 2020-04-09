package CalendarSystem;

import java.time.Duration;
import java.time.LocalDateTime;

public class FrequentAlert extends Alert{
    private Duration frequency;

    public FrequentAlert(LocalDateTime evT, String name, String message, Duration d){
        super(evT, message, name);
        frequency = d;
        setTimesHelper();
    }

    // Edit FrequentAlert
    public void changeFrequency(Duration newDur) {
        this.resetTimes();
        this.frequency = newDur;
        setTimesHelper();
    }

    private void setTimesHelper(){
        LocalDateTime t = getEventTime();
        do{
            t = t.minus(this.frequency);
        }

        //use system time now - needs to be changed!!
        while (t.isBefore(LocalDateTime.now()));

        //then t is the first alarm time
        while(t.isBefore(this.getEventTime())){
            addtoTimes(t);
            t = t.plus(this.frequency);
        }
    }

    @Override
    public String getAlertType() {
        return "f";
    }


    @Override
    public String toString(){
        return "Frequent Alert: " + getMessage() + " every " + frequency;
    }

    public String durationToString(Duration duration) {
        if (duration.toDays()!= 0)
            return duration.toDays() + "D";
        return duration.toHours()+"H";
    }
}
