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

    private void setTimesHelper(){
        LocalDateTime t = getEventTime();
        do{
            t = t.minus(this.frequency);
        }
        while (t.isBefore(CalendarPhase1.time));

        //then t is the first alarm time
        while(t.isBefore(this.getEventTime())){
            addtoTimes(t);
            t = t.plus(this.frequency);
        }
    }
}
