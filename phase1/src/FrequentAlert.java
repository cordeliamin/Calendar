import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

public class FrequentAlert extends Alert{
    private Duration frequency;

    public FrequentAlert(LocalDateTime evT, String name, Duration d){
        super(evT, name);
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
