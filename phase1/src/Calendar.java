import java.io.Serializable;
import java.util.ArrayList;

public class Calendar implements Serializable {
    /*
    Class Calendar is user-specific
     */
    private ArrayList<Event> myEvents = new ArrayList<>();

    /*
    creates event, alerts,
     */
    public void addEvent(Event e){
        myEvents.add(e);
    }

    //Display events
    public String toString(){
        String s = "";
        for(Event e : myEvents){
            s += e.getEventName() + "\n";
        }
       if(s.length() != 0){
           s = s.substring(0, s.length() - 1);
       }
       return s;
    }
}
