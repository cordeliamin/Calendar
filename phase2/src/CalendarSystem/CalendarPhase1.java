package CalendarSystem;

import java.io.IOException;
import java.time.LocalDateTime;

public class CalendarPhase1 {

    // clock giving the current time
    public static LocalDateTime time;

    public static void main(String[] args) throws IOException, ClassNotFoundException {

        // display time when program runs
        time = LocalDateTime.now();
        System.out.println(time);

        CalendarManager sm = new CalendarManager("user");
        sm.readFromFile();

        new Menus(sm);

        /*
        Prompt user to log in
        User input username and password
        Read external User.csv file to compare credentials, loop if credentials do not match
         */

        /*
        Constructing the Calendar from the User's .ser file
         */
//        String serializedCalendarManagerInfo = userName + ".ser";
//        CalendarManager sm = new CalendarManager(serializedCalendarManagerInfo);
//        sm.readFromFile(serializedCalendarManagerInfo);
//        Calendar myCalendar = sm.getCalendar();
//        myCalendar.update();
//        System.out.println(myCalendar.getTime());


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
        Event e = new Event("name",LocalDateTime.now(), LocalDateTime.now());
        System.out.println("Start time is: " + e.getStartTime());
        //System.out.println(myCalendar);
        //System.out.println("next line");
        //System.out.println(e);

        /*
        Save the calendar to the user's .ser file before exiting
         */
    }


}
