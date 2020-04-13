package CalendarSystem;

import java.io.*;
import java.util.ArrayList;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Optional;

public class CalendarManager {
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
        File file = new File(this.filePath);
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

    public void selectCalendar(String name) throws ClassNotFoundException, IOException {
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

    public Calendar getCalendar() {
        return this.calendar;
    }

    public ArrayList<String> getUserCalendars() {
        File userData = new File("user_data");
        String username = userPath.substring(12);
        ArrayList<String> userCalendars = new ArrayList<>();
        if (userData.isDirectory()) {
            File[] sample = userData.listFiles();
            if (sample != null) {
                for (File f : sample) {
                    userCalendars.add(f.getName().replace(username, "").
                            replace(".ser", ""));
                }
            }
        }
        return userCalendars;
    }

    public String shareEvent(String name, Event event) throws IOException, ClassNotFoundException {
        String otherFilePath = this.userPath + name + ".ser";
        // TODO: make sure this is the right kind of filepath
        File otherPersonsFile = new File(otherFilePath);
        if (otherPersonsFile.exists()) {
            Calendar friendsCalendar = readFromOtherFile(otherPersonsFile);
            // add to friendCalendar notificationsystem
            //TODO: make sure this is saving to the ser. file of the other person
            friendsCalendar.addEventNotification(event);
            return "Event Shared! Waiting on" + name + "'s" + "response";


        } else {
            return "Friend's account not found";
        }

    }


    public Calendar readFromOtherFile(File file) throws IOException, ClassNotFoundException {
        InputStream otherFile = new FileInputStream(file);
        InputStream buffer = new BufferedInputStream(otherFile);
        ObjectInput input = new ObjectInputStream(buffer);
        Calendar friendCalendar = (Calendar) input.readObject();
        input.close();
        return friendCalendar;
    }

    public String sendMessage(String name, Memo memo) throws IOException, ClassNotFoundException {
        String otherFilePath = this.userPath + name + ".ser";
        // TODO: make sure this is the right kind of filepath
        File otherPersonsFile = new File(otherFilePath);
        if (otherPersonsFile.exists()) {
            Calendar friendsCalendar = readFromOtherFile(otherPersonsFile);
            // add to friendCalendar notificationsystem
            friendsCalendar.addMessageNotification(memo);
            //TODO: How do I know this will be saved in the serialized file of my friend? do I have to save to file?
            return "Message sent! Waiting on" + name + "'s" + "response";
        }else{
            return "Friend's account not found";
        }


    }
}
