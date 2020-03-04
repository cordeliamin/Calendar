import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.TreeSet;
import java.util.Set;
import java.io.Serializable;

public class AlertSystem implements Serializable{
    private Set<Date> allAlertTimes = new TreeSet<>();
    private Map<Date, List<Alert>> dateAlertsMap = new HashMap<>();
    private Map<Event, List<Alert>> eventAlertsMap = new HashMap<>();

    /**
     * adds the event to the alert system
     * @param event: event of the alert
     * @param type: type of alert: f: frequent, i: individual
     * @param time: time of the alert(date for i, frequency for f)
     */
    public void addAlert(Event event, char type, Date time){
        //initialize just for now
        Alert newAlert = null;

        //initialize in event alert map with event
        switch (type){
            case 'i':
                newAlert = new IndividualAlert(time);
                eventAlertsMap.putIfAbsent(event, new ArrayList<>());
                eventAlertsMap.get(event).add(newAlert);
                break;
            case 'f':
                newAlert = new IndividualAlert(time);
                //     eventAlertsMap.putIfAbsent(event, new ArrayList<Alert>());
                //     eventAlertsMap.get(event).add(new FrequentAlert(event.getTime(), time));
                break;
        }

        // add to
        if(newAlert != null){
            for (Date alertTime: newAlert.appearAt()) {
                dateAlertsMap.putIfAbsent(alertTime, new ArrayList<>());
                dateAlertsMap.get(alertTime).add(newAlert);
                allAlertTimes.add(alertTime);
            }
        }
        else
            System.out.println("Alert type not valid, try again");
    }

    /**
     * display alerts that should appear now
     * @param CurrTime: current time of the calendar system
     */
    public void alert(Date CurrTime){
        if(allAlertTimes.contains(CurrTime))
            System.out.println(dateAlertsMap.get(CurrTime));
    }

    /**
     * get all alerts associated with the event
     * @param e: events
     */
    public List<Alert> getAlerts(Event e) {
        return eventAlertsMap.get(e);
    }
}
