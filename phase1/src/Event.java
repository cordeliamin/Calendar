import java.util.ArrayList;

public class Event {

    public String eventName;
    public String startTime;
    public String endTime;
    public String startDay;
    public String endDay;

    /** A list of memos associated with this Event */
    public ArrayList<Memo> memos;


    public Event(String name, String st, String et, String sd, String ed){
        eventName = name;
        startTime = st;
        endTime = et;
        startDay = sd;
        endDay = ed;
        memos = new ArrayList<>();
    }










}
