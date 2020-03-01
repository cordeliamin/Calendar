import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.TreeSet;
import java.util.Set;

public class AlertSystem {
    private Set<Date> allAlertTimes = new TreeSet<>();
    private Map<Date, ArrayList<Alert>> dateAlertsMap = new HashMap<>();
    private Map<Event, ArrayList<Alert>> eventAlertsMap = new HashMap<>();

    /**
     * adds the event to the alert system
     * @param event: event of the alert
     * @param type: type of alert: f: frequent, i: individual
     * @param time: time of the alert(date for i, frequency for f)
     */
    public void addAlert(Event event, char type, Date time){
        //initialize just for now
        Alert newAlert = new IndividualAlert(time);

        //initialize according to types
        if(type == 'i'){
            newAlert = new IndividualAlert(time);
            eventAlertsMap.putIfAbsent(event, new ArrayList<>());
            eventAlertsMap.get(event).add(newAlert);
        }
        // else if(type == 'f'){
        //     eventAlertsMap.putIfAbsent(event, new ArrayList<Alert>());
        //     eventAlertsMap.get(event).add(new FrequentAlert(event.getTime(), time));
        // }

        for (Date alertTime: newAlert.appearAt()) {
            dateAlertsMap.putIfAbsent(alertTime, new ArrayList<>());
            dateAlertsMap.get(alertTime).add(newAlert);
            allAlertTimes.add(alertTime);
        }
    }

    public void alert(Date CurrTime){
        if(allAlertTimes.contains(CurrTime))
            System.out.println(dateAlertsMap.get(CurrTime));
    }
}
