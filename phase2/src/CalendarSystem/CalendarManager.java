package CalendarSystem;

import java.io.*;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CalendarManager  {
    private Calendar calendar;
    private String filePath;
    private String userPath;
    private static final Logger logger = Logger.getLogger(CalendarManager.class.getName());
    private static final Handler consoleHandler = new ConsoleHandler();

    /**
     * Creates a new empty initialBuild.CalendarManager.
     *
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public CalendarManager(String filePath) throws ClassNotFoundException, IOException {
        this.calendar = new Calendar();
        this.userPath = filePath;
        this.filePath = filePath + "default.ser";

        // Associate the handler with the logger.
        logger.setLevel(Level.ALL);
        consoleHandler.setLevel(Level.ALL);
        logger.addHandler(consoleHandler);

        // Reads serializable objects from file.
        // Populates the record list using stored data, if it exists.
        File file = new File(filePath);
        if (file.exists()) {
            readFromFile();
        } else {
            file.createNewFile();
        }
    }

    public void createCalendar(String name) throws IOException {
        saveToFile();
        this.filePath = this.userPath + name + ".ser";
        this.calendar = new Calendar();
        File file = new File(filePath);
        file.createNewFile();
        saveToFile();
    }

    public void selectCalendar(String name) throws ClassNotFoundException, IOException{
        saveToFile();
        this.filePath = this.userPath + name + ".ser";
        File file = new File(filePath);
        if (file.exists()) {
            readFromFile();
        } else {
            file.createNewFile();
        }
    }

    public void readFromFile() throws ClassNotFoundException {
        try {
            InputStream file = new FileInputStream(filePath);
            InputStream buffer = new BufferedInputStream(file);
            ObjectInput input = new ObjectInputStream(buffer);

            // deserialize
            calendar = (Calendar) input.readObject();
            input.close();
        } catch (IOException ex) {
            logger.log(Level.SEVERE, "Cannot read from input.", ex);
        }
    }

    /**
     * Writes the calendar to file at filePath.
     *
     * @throws IOException
     */
    public void saveToFile() throws IOException {

        OutputStream file = new FileOutputStream(filePath);
        OutputStream buffer = new BufferedOutputStream(file);
        ObjectOutput output = new ObjectOutputStream(buffer);

        // serialize the Map
        output.writeObject(calendar);
        output.close();
    }

    public Calendar getCalendar(){
        return this.calendar;
    }
}
