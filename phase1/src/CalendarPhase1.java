import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Scanner;
import javax.swing.*;
import javax.swing.JOptionPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class CalendarPhase1 {

    // clock giving the current time
    public static Clock clock;

    public static class accountButton{

        accountButton(){
            JFrame f = new JFrame("Account");
            JButton b1 = new JButton("Yes");
            JButton b2 = new JButton("No");

            b1.setBounds(70,100,90, 30);
            b2.setBounds(170, 100, 90, 30);

            JLabel label = new JLabel();
            label.setText("Do you have an existing account?");
            label.setBounds(10, 10, 300, 100);

            f.add(label);
            f.add(b1);
            f.add(b2);
            f.setSize(300,300);
            f.setLayout(null);
            f.setVisible(true);
            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            //action listener
            b1.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent arg0) {
                    System.out.println("yes!");
                    System.exit(0);
                }
            });
            b2.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println(("No!"));
                    System.exit(0);
                }
            });
        }
    }


    public static void main(String[] args) throws IOException, ClassNotFoundException {

        // display time when program runs
        clock = Clock.systemDefaultZone();
        System.out.println(clock.instant().atZone(clock.getZone()));


        /*
        Prompt user to log in or create new account
         */

        /*
        Creating a map of all users in the csv file
         */
//        new accountButton();

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
        Event displaying (interaction with class Calendar)
         */
        System.out.println(myCalendar);

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
