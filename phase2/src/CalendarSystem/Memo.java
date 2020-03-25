package CalendarSystem;

import java.io.Serializable;

public class Memo implements Serializable {

    /**
     * the id number for this Memo
     */
    private int idNum;

    /**
     * the total number of Memos created
     */
    private static int numOfMemos = 0;

    /**
     * the note this Memo stores
     */
    private String note;

    public Memo(String note) {
        numOfMemos++;
        this.idNum = numOfMemos;
        this.note = note;
    }

    public int getIdNumber() {
        return this.idNum;
    }

    public String getNote() {
        return this.note;
    }

    // Editing the memo

    /**
     * Sets this Memo's note to a new note.
     * @param newNote: the new note to be stored in this Memo
     */
    public void setNote(String newNote) {
        this.note = newNote;
    }

    /**
     * Adds on a message to this Memo's current note.
     */
    public void addToNote(String note1) {
        this.note += note1;
    }

    @Override
    public String toString() {
        return "Memo " + this.idNum + ": " + this.note;
    }

    static public int getNumOfMemos() { return numOfMemos; }

}