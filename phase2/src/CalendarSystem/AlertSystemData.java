package CalendarSystem;

import java.time.LocalDateTime;

public class AlertSystemData {
    private LocalDateTime time;
    private String eventName;
    private String message;
    private String type;

    public AlertSystemData(LocalDateTime d, String e, String m, String t){
        time = d;
        eventName = e;
        message = m;
        type = t;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

