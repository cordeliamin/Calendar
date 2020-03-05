import java.io.Serializable;

public class Memo implements Serializable {

    /** the id number for this Memo */
    private int idnum;

    /** the total number of Memos created */
    static private int numOfMemos = 0;

    /** the note this Memo stores */
    private String note;

    public Memo(String note) {
        numOfMemos ++;
        this.idnum = numOfMemos;
        this.note = note;
    }

    public int getIdnumber() {
        return this.idnum;
    }

    public String getNote() {
        return this.note;
    }

    @Override
    public String toString() {
        return "Memo " + this.idnum + ": " + this.note;
    }

    static public int getNumOfMemos() { return numOfMemos; }

}
