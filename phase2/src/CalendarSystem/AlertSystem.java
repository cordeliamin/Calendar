package CalendarSystem;


import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.ArrayList;
import java.io.Serializable;
import java.util.NavigableSet;

public class AlertSystem implements Serializable{
    private TreeSet<LocalDateTime> allAlertTimes = new TreeSet<>();
    private Map<LocalDateTime, List<Alert>> dateAlertsMap = new HashMap<>();
    private Map<Event, List<Alert>> eventAlertsMap = new HashMap<>();

    //Add alerts to the system
    /**
     * adds the new Individual alert to the system:
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
    public void addFrequentAlert(Event event, String message, Duration duration) {
        Alert newAlert = new FrequentAlert(event.getStartTime(), event.getEventName(), message, duration);
        eventAlertsMap.putIfAbsent(event, new ArrayList<>());
        eventAlertsMap.get(event).add(newAlert);
        addtoTimesSet(newAlert);
    }


    //return sets of alerts -> that should appear currently, all alerts, or according to event
    /**
     * display alerts that should appear now
     */
    public Set<Alert> getCurrAlerts(){
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

        return CurrAlerts;
    }

    /**
     * get all alerts associated with the event
     * @param e: events
     */
    public Set<Alert> getAlerts(Event e) {
        Set<Alert> alerts = new HashSet<>(eventAlertsMap.get(e));
        return alerts;
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


    /**
     * Removes specified event from this eventAlertsMap.
     */
    public void removeEvent(Event e) {
        this.eventAlertsMap.remove(e);
    }

    /**
     * Delete an Alert
     */
    public void deleteAlert(Alert alert) {
        // removes alert from dateAlertsMap
        for (LocalDateTime alertTime : alert.getTimes()) {
            dateAlertsMap.get(alertTime).remove(alert);
            if (dateAlertsMap.get(alertTime).isEmpty()) { // no other alerts at alertTime, so removes alertTime
                dateAlertsMap.remove(alertTime);
                allAlertTimes.remove(alertTime);
            }
        }

        // removes alert from eventAlertsMap
        for (Event e: eventAlertsMap.keySet()) {
            eventAlertsMap.get(e).remove(alert);
        }
    }

    public void deleteAllAlertsforEvent(Event e) {
        for (Alert a: this.eventAlertsMap.get(e)) {
            deleteAlert(a);
        }
        this.eventAlertsMap.remove(e);
    }

    // helper methods
    /**
     * helper method for adding the times of the alert to dateAlertsMap and allAlertTimes,
     * @param alert: the alert to add
     */
    public void addtoTimesSet(Alert alert) {
        for (LocalDateTime alertTime: alert.getTimes()) {
            dateAlertsMap.putIfAbsent(alertTime, new ArrayList<>());
            dateAlertsMap.get(alertTime).add(alert);
            allAlertTimes.add(alertTime);
        }
    }
}
