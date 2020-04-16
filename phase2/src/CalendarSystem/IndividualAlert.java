package CalendarSystem;

import java.time.LocalDateTime;

public class IndividualAlert extends Alert{
    private LocalDateTime time;

    /**
     * create individual alert with the given information
     * @param evT event datetime of the associated event
     * @param name event name of the associated event
     * @param message message content of alert
     * @param t datetime of the individual alert to appear at
     */
    public IndividualAlert(LocalDateTime evT, String name, String message, LocalDateTime t){
        super(evT, message, name);
        time = t;
        addtoTimes(time);
        setData("Individual Alert: " + message + " at " + t);
    }

    //methods for editing individual alert
    /**
     * change the datetime of the individual alert
     * @param newTime: new datetime
     */
    public void changeTime(LocalDateTime newTime) {
        this.removefromTimes(this.time);
        this.time = newTime;
        addtoTimes(this.time);
    }

    //methods for displaying alert

    /**
     * return string 'i' representing inidividual alert for display
     * @return type of alert ('i')
     */
    @Override
    public String getAlertType() {
        return "i";
    }


    @Override
    public String toString(){
        return "Individual Alert: " + getMessage() + " at " + time;
    }

    //getters
    public LocalDateTime getTime() {
        return time;
    }
}
