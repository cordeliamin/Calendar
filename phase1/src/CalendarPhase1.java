import java.io.IOException;
import javax.swing.JOptionPane;

public class CalendarPhase1 {

    public static void main(String[] args) throws IOException, ClassNotFoundException{

        /*
        Prompt user to log in or create new account
         */

        /*
        boolean accountExist;
        accountExist = JOptionPane.showInputDialog("Do you have an account?");
        */




        /*
        Prompt user to log in
        User input username and password
        Read external User.csv file to compare credentials, loop if credentials do not match

        String userName;
        String password;
        do{
            userName = JOptionPane.showInputDialog("Please enter your user user name to log in");
            password = JOptionPane.showInputDialog("Please enter your password to log in");
        }while ();


         */



        /*
        Constructing the Calendar from the User's .ser file
         */
        Calendar c = constructCalendar("user");

        /*
        Prompt user to create new account
        Writes out to external User.csv file
         */

        /*
        Event displaying (interaction with class Calendar)
         */

        /*
        Save the calendar to the user's .ser file before exiting
         */
        saveCalendar("user");
    }

    public static Calendar constructCalendar(String username) throws IOException, ClassNotFoundException {

        String serializedCalendarManagerInfo = "src/" + username + ".ser";
        CalendarManager sm = new CalendarManager(serializedCalendarManagerInfo);

//      System.out.println("Initial state:\n" + cm);
        // Loads data from a CSV for first launch of the program
//        sm.readFromCSVFile(studentCSVFile);
//        System.out.println("Students from CSV:\n" + sm.toString());

//    // Deserializes contents of the SER file
        sm.readFromFile(serializedCalendarManagerInfo);
        System.out.println("Students from ser:\n" + sm.toString());

        return sm.getCalendar();
    }

    public static void saveCalendar(String username) throws IOException, ClassNotFoundException {
        String serializedCalendarManagerInfo = "src/" + username + ".ser";
        CalendarManager sm = new CalendarManager(serializedCalendarManagerInfo);
        sm.saveToFile(serializedCalendarManagerInfo);
    }
}
