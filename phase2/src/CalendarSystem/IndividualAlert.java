package CalendarSystem;

import java.time.LocalDateTime;

public class IndividualAlert extends Alert{
    private LocalDateTime time;

    public IndividualAlert(LocalDateTime evT, String name, String message, LocalDateTime t){
        super(evT, message, name);
        time = t;
        addtoTimes(time);
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
}
