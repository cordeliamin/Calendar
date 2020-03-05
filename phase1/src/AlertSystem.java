import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.ArrayList;
import java.io.Serializable;
import java.util.NavigableSet;

public class AlertSystem implements Serializable{
    private TreeSet<LocalDateTime> allAlertTimes = new TreeSet<>();
    private Map<LocalDateTime, List<Alert>> dateAlertsMap = new HashMap<>();
    private Map<Event, List<Alert>> eventAlertsMap = new HashMap<>();

    /**
     * adds the new Individual alert to the system
     * @param event: event of the alert
     * @param time: time of the alert
     * @param message: message content of the alert
     */
    public void addIndividualAlert(Event event, String message, LocalDateTime time){
        Alert newAlert = new IndividualAlert(event.getStartTime(), event.getEventName(), message, time);
        eventAlertsMap.putIfAbsent(event, new ArrayList<>());
        eventAlertsMap.get(event).add(newAlert);
        addtoTimesSet(newAlert);
    }

    /**
     * adds the new Frequent alert to the system
     * @param event: event of the alert
     * @param duration: the frequency of the event
     * @param message: message content of the alert
     */
    public void addFrequentAlert(Event event, Duration duration, String message) {
        Alert newAlert = new FrequentAlert(event.getStartTime(), event.getEventName(), message, duration);
        eventAlertsMap.putIfAbsent(event, new ArrayList<>());
        eventAlertsMap.get(event).add(newAlert);
        addtoTimesSet(newAlert);
    }

    /**
     * adds the times of the alert to dateAlertsMap and allAlertTimes,
     * @param alert: the alert to add
     */
    public void addtoTimesSet(Alert alert) {
        for (LocalDateTime alertTime: alert.getTimes()) {
            dateAlertsMap.putIfAbsent(alertTime, new ArrayList<>());
            dateAlertsMap.get(alertTime).add(alert);
            allAlertTimes.add(alertTime);
        }
    }

    /**
     * display alerts that should appear now
     */
    public void alert(){
        Set<Alert> CurrAlerts = new HashSet<>(); //the set of Alerts to Show

        // subset of valid times, reverse order
        NavigableSet<LocalDateTime> subset = this.allAlertTimes.headSet(CalendarPhase1.time, true);
        NavigableSet<LocalDateTime> validAlertTimes = subset.descendingSet();

        //check if alert is valid (i.e. event hasn't occurred), then add to set CurrAlerts
        for (LocalDateTime date: validAlertTimes){
            for(Alert alert: dateAlertsMap.get(date)){
                if(alert.getEventTime().isAfter(CalendarPhase1.time))
                    CurrAlerts.add(alert);
            }
        }

        //display alerts in set CurrAlerts
        for (Alert alert: CurrAlerts){
            System.out.println(alert.toString());
        }
    }

    /**
     * get all alerts associated with the event
     * @param e: events
     */
    public List<Alert> getAlerts(Event e) {
        return eventAlertsMap.get(e);
    }

    /**
     * get all alerts in the system, past, current or future
     * @return all alerts
     */
    public Set<Alert> getAllAlerts(){
        Set<Alert> result = new HashSet<>();
        Set<Event> events = eventAlertsMap.keySet();
        for (Event e: events){
            result.addAll(eventAlertsMap.get(e));
        }
        return result;
    }
}
