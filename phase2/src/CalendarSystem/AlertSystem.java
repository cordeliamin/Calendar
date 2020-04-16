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
     * @param event: the event which the alert is associated with
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
     * adds the new Frequent alert to the system: do nothing if the frequent alert date is not valid (ex. no valid alert
     * time with the given frequency)
     * @param event: the event which the alert is associated with
     * @param duration: frequency of the event
     * @param message: message content of the alert
     */
    public void addFrequentAlert(Event event, String message, Duration duration) {
        if((LocalDateTime.now().isBefore(event.getStartTime())) && event.getStartTime().minus(duration).isAfter(LocalDateTime.now())) {
            Alert newAlert = new FrequentAlert(event.getStartTime(), event.getEventName(), message, duration);
            eventAlertsMap.putIfAbsent(event, new ArrayList<>());
            eventAlertsMap.get(event).add(newAlert);
            addtoTimesSet(newAlert);
        } else{
            System.out.println("Illegal Frequent Alert Time");
        }
    }


    //Return sets of alerts -> that should appear currently, all alerts, or according to event
    /**
     * get all alerts associated with the event
     * @param e: events
     */
    public Set<Alert> getAlerts(Event e) {
        return new HashSet<>(eventAlertsMap.get(e));
    }

    /**
     * return list of upcoming alerts
     * @return upcoming alerts
     */
    public Set<Alert> getCurrAlerts(){
        Set<Alert> CurrAlerts = new HashSet<>(); //the set of Alerts to Show

        // subset of valid times, reverse order
        NavigableSet<LocalDateTime> subset = this.allAlertTimes.tailSet(LocalDateTime.now(), true);
        NavigableSet<LocalDateTime> validAlertTimes = subset.descendingSet();

        //check if alert is valid (i.e. event hasn't occurred), then add to set CurrAlerts
        for (LocalDateTime date: validAlertTimes){
            for(Alert alert: dateAlertsMap.get(date)){
                if(alert.getEventTime().isAfter(LocalDateTime.now()))
                    CurrAlerts.add(alert);
            }
        }

        return CurrAlerts;
    }

    /**
     * return list of all alerts in the system, past, current or future
     * @return list of all alerts
     */
    public Set<Alert> getAllAlerts(){
        Set<Alert> result = new HashSet<>();
        Set<Event> events = eventAlertsMap.keySet();
        for (Event e: events){
            result.addAll(eventAlertsMap.get(e));
        }
        return result;
    }


    //methods to edit or delete alerts
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
        if (!this.eventAlertsMap.isEmpty()) {
            for (Alert a : this.eventAlertsMap.get(e)) {
                deleteAlert(a);
            }
           removeEvent(e);
        }
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


    //getters and setters
    public Map<LocalDateTime, List<Alert>> getDateAlertsMap() {
        return dateAlertsMap;
    }

    public TreeSet<LocalDateTime> getAllAlertTimes() {
        return allAlertTimes;
    }
}
