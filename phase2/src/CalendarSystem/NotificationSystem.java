package CalendarSystem;
import java.util.ArrayList;

public class NotificationSystem {
    private ArrayList<Memo> messages = new ArrayList<Memo>();
    private ArrayList<Event> invites = new ArrayList<Event>();

    public ArrayList<Memo> getMessages(){
        return this.messages;
    }

    public ArrayList<Event> getInvites (){
        return this.invites;
    }

    public void addEvent(Event event){
        invites.add(event);
    }
    public void addMessage(Memo memo){
        messages.add(memo);
    }


}
