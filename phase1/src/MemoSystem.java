import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MemoSystem implements Serializable {
    /* A system for handling the creation and storage of Memos */

    /** A list of all the memos created */
    public ArrayList<Memo> memos;

    /**
     * creates a memo
     * @param events: a list of events associated with the memo
     * @param note: the note of the memo
     */
    public void createMemo(List<Event> events, String note){
        // initialize a new Memo with input note
        Memo memo = new Memo(note);

        // adds memo to the list of memos stored in this MemoSystem
        this.memos.add(memo);

        // adds memo to each event's memos from the input list of events
        for (Event event:events){
            event.getMemos().add(memo);
        }
    }

    /** prints a list of all the memos stored in this MemoSystem */
    public void browseMemos(){
        for (Memo memo:memos) {
            System.out.println(memo);
        }
    }

    /**
     * get a memo by its id number
     * @param id: the id number of a memo in this MemoSystem
     * @return the memo with id number id
     */
    public Memo getMemo(int id) {
        for (Memo memo:memos) {
            if(memo.getIdnumber() == id) {
                return memo;
            }
        }
        System.out.println("This is not a valid id number for a memo.");
        return null;
    }



}
