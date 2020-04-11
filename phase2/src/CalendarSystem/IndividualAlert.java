package CalendarSystem;

import java.time.LocalDateTime;

public class IndividualAlert extends Alert{
    private LocalDateTime time;

    public IndividualAlert(LocalDateTime evT, String name, String message, LocalDateTime t){
        super(evT, message, name);
        time = t;
        addtoTimes(time);
        setData("Individual Alert: " + message + " at " + t);
    }

    //Edit IndividualAlert
    public void changeTime(LocalDateTime newTime) {
        this.removefromTimes(this.time);
        this.time = newTime;
        addtoTimes(this.time);
    }

    @Override
    public String getAlertType() {
        return "i";
    }


    @Override
    public String toString(){
        return "Individual Alert: " + getMessage() + " at " + time;
    }

    public LocalDateTime getTime() {
        return time;
    }
}
