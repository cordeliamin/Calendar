import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Clock;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.HashMap;

public class CalendarPhase1 {

    // clock giving the current time
    public static LocalDateTime time;

    public static void main(String[] args) throws IOException, ClassNotFoundException {

        // display time when program runs
        time = LocalDateTime.now();
        System.out.println(time);

        new Menus("a");



        /*
        Prompt user to log in
        User input username and password
        Read external User.csv file to compare credentials, loop if credentials do not match
         */
        String userName = "user";
        String password;
//        do{
//            Scanner s = new Scanner(System.in);
//            System.out.print("Enter usename: ");
//            userName = s.nextLine();
////            userName = JOptionPane.showInputDialog("Please enter your user user name to log in");
//            System.out.print("Enter password: ");
//            password = s.nextLine();
////            password = JOptionPane.showInputDialog("Please enter your password to log in");
//        }while (!login(userName, password, users));
        System.out.println("Successfully logged in as " + userName);

        /*
        Constructing the Calendar from the User's .ser file
         */
        String serializedCalendarManagerInfo = userName + ".ser";
        CalendarManager sm = new CalendarManager(serializedCalendarManagerInfo);
        sm.readFromFile(serializedCalendarManagerInfo);
        Calendar myCalendar = sm.getCalendar();
        myCalendar.update();
        System.out.println(myCalendar.getTime());
        //Testing series creation
//        myCalendar.addSeries("series 1", Duration.ofHours(1), Period.ofDays(1), 3, LocalDateTime.now());
//        myCalendar.reset();
//        ArrayList<Event> seriesEvents = new ArrayList<>();
//        seriesEvents.add(new Event("series event 1", LocalDateTime.now(), LocalDateTime.now().plus(Duration.ofHours(1))));
//        seriesEvents.add(new Event("series event 2", LocalDateTime.now(), LocalDateTime.now().plus(Duration.ofHours(1))));
//        seriesEvents.add(new Event("series event 3", LocalDateTime.now(), LocalDateTime.now().plus(Duration.ofHours(1))));
//        for(Event e : seriesEvents){
//            myCalendar.addEvent(e);
//        }
//        myCalendar.addSeries("series", seriesEvents);
//        System.out.println(myCalendar.findEventsBySeries("series"));

        /*
        Event displaying (interaction with class Calendar)
         */
        System.out.println(myCalendar);

        /*
        Save the calendar to the user's .ser file before exiting
         */
        sm.saveToFile(serializedCalendarManagerInfo);
    }


}
