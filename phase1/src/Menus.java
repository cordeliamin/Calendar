import javax.swing.*;
import javax.swing.JOptionPane;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.time.LocalDateTime;

public class Menus extends JFrame{

    private static JFrame f = new JFrame("Account");
    private static JFrame f2 = new JFrame("Log In");
    private static JFrame f3 = new JFrame("New Feature is coming!");
    private static JFrame f4 = new JFrame("Incorrect Credentials!");
    private static JFrame f5 = new JFrame("Main Menu");
    private static JFrame f6 = new JFrame("View Events");
    private static JFrame f7 = new JFrame("View Alerts");
    private static JFrame f8 = new JFrame("View Memos");


    JButton yes = new JButton("Yes");
    JButton no = new JButton("No");
    JButton submit = new JButton("Submit");
    JButton viewEvents = new JButton("View all events");
    JButton createEvents = new JButton("Create events");
    JButton viewPastEvents = new JButton("Past events");
    JButton viewCurrentEvents = new JButton("Ongoing events");
    JButton viewFutureEvents = new JButton("Future Events");
    JButton individualEvent = new JButton("Select");
    JButton eventSeries = new JButton("Select");
    JButton individualAlert = new JButton("Individual");
    JButton frequencyAlert = new JButton("Frequency");
    JButton viewAlerts = new JButton("View alerts");
    JButton viewMemos = new JButton("View memos");

    JLabel existingAcc = new JLabel("Do you have an existing account?");
    JLabel userLabel = new JLabel("Username:");
    JLabel pswdLabel = new JLabel("Password:");
    JLabel newFeature = new JLabel("You can create a new account in phase 2!");
    JLabel incorrectCre = new JLabel("Please try again!");
    JLabel alert = new JLabel("Alerts:");
    JLabel memo = new JLabel("Memo: ");

    public Menus() throws IOException {
        HashMap<String, String> users = getUsers(); //Creating a map of all users in the csv file
        accountButton(f);
    }

    /**
     * Displays first screen prompting user if they have an account
     * @param f: pre-fixed JFrame
     */
    public void accountButton(JFrame f) throws IOException{
        HashMap<String, String> users = getUsers(); //Creating a map of all users in the csv file

        yes.setBounds(70,100,90, 30);
        no.setBounds(170, 100, 90, 30);

        existingAcc.setBounds(10, 10, 300, 100);

        f.setSize(300,300);
        f.add(existingAcc);
        f.add(yes);
        f.add(no);
        makeVisible(f);

        //action listener
        yes.addActionListener(arg0 -> {
            f.dispose();
            accountLogIn(users, f2); // prompt user to log in
        });
        no.addActionListener(e -> {
            f.dispose();
            createNewAccount(users, f3);
        });
    }

    /**
     * Creates a new account, extension for Phase 2.
     * @param users: a HashMap of all users in the external .csv file
     * @param f: pre-fixed JFrame
     */
    public void createNewAccount(HashMap<String, String> users, JFrame f){
        f.setVisible(false);
        f = new JFrame("New Feature is Coming!");
        f.setSize(400, 300);
        newFeature.setBounds(50, 100, 300, 30);
        f.add(newFeature);
        makeVisible(f);
        // prompt create new user
    }

    /**
     * Prompts user to log in
     * @param users: a HashMap of all users in the external .csv file
     * @param f: pre-fixed JFrame
     */
    public void accountLogIn(HashMap<String, String> users, JFrame f){
        JTextField userText = new JTextField();
        JPasswordField pswdText = new JPasswordField();

        submit.setBounds(200, 150, 90, 30);
        userLabel.setBounds(50, 100, 70, 30);
        pswdLabel.setBounds(300, 100, 70, 30);
        userText.setBounds(120, 100, 100, 30);
        pswdText.setBounds(370, 100, 100, 30);

        f.setSize(500,300);
        f.add(userLabel);
        f.add(pswdLabel);
        f.add(userText);
        f.add(pswdText);
        f.add(submit);
        makeVisible(f);

        submit.addActionListener(ae -> {
            String user = userText.getText();
            String pswd = pswdText.getText();

            if(login(user, pswd, users)){
                f.setVisible(false);
                f.dispose();
                f4.setVisible(false);
                f4.dispose();
                try {
                    mainDisplay(user, f5);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
            else{
                userText.setText("");
                pswdText.setText("");
                incorrectCre.setBounds(200, 70, 200, 30);
                f.setVisible(false);
                f4.setSize(500,300);
                f4.add(userLabel);
                f4.add(pswdLabel);
                f4.add(incorrectCre);
                f4.add(userText);
                f4.add(pswdText);
                f4.add(submit);
                makeVisible(f4);
            }
        });
    }

    /**
     * Prompts user to select different functions
     * @param user: specific user that logged in
     * @param f: pre-fixed JFrame
     */
    public void mainDisplay(String user, JFrame f) throws IOException, ClassNotFoundException{

        String serializedCalendarManagerInfo = user + ".ser";
        CalendarManager sm = new CalendarManager(serializedCalendarManagerInfo);
        sm.readFromFile(serializedCalendarManagerInfo);
        Calendar myCalendar = sm.getCalendar();
        myCalendar.update();
        //System.out.println(myCalendar.getTime());

        JPanel gbPanel = new JPanel(new GridBagLayout());
        JLabel userName = new JLabel();
        JLabel emptySpace = new JLabel(" ");
        userName.setBounds(100, 60, 80, 30);
        userName.setText(user);
        userLabel.setBounds(30, 60, 70, 30);

        addGB(gbPanel, userLabel, 0, 0);
        addGB(gbPanel, userName, 0, 1);
        addGB(gbPanel, emptySpace, 0, 2);
        addGB(gbPanel, viewEvents, 0, 3);
        addGB(gbPanel, viewAlerts, 0, 4);
        addGB(gbPanel, viewMemos, 0, 5);
        addGB(gbPanel, createEvents, 0, 6);

        f.setSize(800, 800);
        f.add(gbPanel);
        f.setLocationRelativeTo(null);
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        viewEvents.addActionListener(e -> {
            f.dispose();
            viewEventsDisplay(user, f6);
        });
        viewAlerts.addActionListener(e -> {
            f.dispose();
            viewAlertsDisplay(user, f7);
        });
        viewMemos.addActionListener(e -> {
            f.dispose();
            viewMemosDisplay(user, f8);
        });




    }

    public void viewEventsDisplay(String user, JFrame f){

    }

    public void viewAlertsDisplay(String user, JFrame f){

    }

    public void viewMemosDisplay(String user, JFrame f){

    }


    /**
     * Makes JFrame centered and visible with default layout and exit on close
     * @param f: JFrame
     */
    public void makeVisible(JFrame f){
        f.setLocationRelativeTo(null);
        f.setLayout(null);
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     * Adds component to the JPanel with proper constraints
     * @param p: JPanel object
     * @param comp: component to be added
     * @param x: constraint's x-axis
     * @param y: constraint's y-axis
     */
    private void addGB(JPanel p, Component comp, int x, int y){
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(5, 5, 5, 5);
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.gridx = x;
        constraints.gridy = y;
        p.add(comp, constraints);
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
        Scanner scanner = new Scanner(new FileInputStream("users.csv"));
        String[] login;
        HashMap<String, String> users = new HashMap<>();
        while (scanner.hasNextLine()) {
            login = scanner.nextLine().split(",");
            users.put(login[0], login[1]);
        }
        return users;
    }
}
