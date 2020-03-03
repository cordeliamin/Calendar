import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLOutput;
import java.util.HashMap;
import java.util.Scanner;
import javax.swing.JOptionPane;

public class CalendarPhase1 {

    public static void main(String[] args) throws IOException, ClassNotFoundException {

        /*
        Prompt user to log in or create new account
         */

        /*
        Creating a map of all users in the csv file
         */
        HashMap<String, String> users = getUsers();

        /*
        boolean accountExist;
        accountExist = JOptionPane.showInputDialog("Do you have an account?");
        */




        /*
        Prompt user to log in
        User input username and password
        Read external User.csv file to compare credentials, loop if credentials do not match
         */
        String userName;
        String password;
        do{
            Scanner s = new Scanner(System.in);
            System.out.print("Enter usename: ");
            userName = s.nextLine();
//            userName = JOptionPane.showInputDialog("Please enter your user user name to log in");
            System.out.print("Enter password: ");
            password = s.nextLine();
//            password = JOptionPane.showInputDialog("Please enter your password to log in");
        }while (!login(userName, password, users));
        System.out.println("Successfully logged in as " + userName);
        /*
        Constructing the Calendar from the User's .ser file
         */
        String serializedCalendarManagerInfo = "./phase1/src/" + userName + ".ser";
        CalendarManager sm = new CalendarManager(serializedCalendarManagerInfo);
        sm.readFromFile(serializedCalendarManagerInfo);
        Calendar myCalendar = sm.getCalendar();

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
        sm.saveToFile(serializedCalendarManagerInfo);
    }

    /**
     * Checks if the entered username and password are valid
     */
    public static boolean login(String user, String pswd, HashMap<String, String> users){
        if(!users.containsKey(user)){
            return false;
        }
        return users.get(user).equals(pswd);
    }

    public static HashMap<String, String> getUsers() throws FileNotFoundException {
        Scanner scanner = new Scanner(new FileInputStream("./phase1/src/users.csv"));
        String[] login;
        HashMap<String, String> users = new HashMap<>();
        while (scanner.hasNextLine()) {
            login = scanner.nextLine().split(",");
            users.put(login[0], login[1]);
        }
        return users;
    }
}
