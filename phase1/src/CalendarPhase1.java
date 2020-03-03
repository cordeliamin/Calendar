import java.io.IOException;
import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class CalendarPhase1 {

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


    public static void main(String[] args) throws IOException, ClassNotFoundException{


        JButton button = new JButton("Yes");

        /*
        Prompt user to log in or create new account
         */

        new accountButton();





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
