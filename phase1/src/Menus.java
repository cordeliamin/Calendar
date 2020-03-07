import javax.swing.*;
import javax.swing.JOptionPane;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
    private static JFrame f9 = new JFrame("Create New Events");
    private static JFrame f10 = new JFrame("Create New Memo");
    private static JFrame f11 = new JFrame("Events not in Calendar");
    private static JFrame f12 = new JFrame("Create New Individual Alert");
    private static JFrame f13 = new JFrame("Create New Frequent Alert");


    JButton yes = new JButton("Yes");
    JButton no = new JButton("No");
    JButton submit = new JButton("Submit");
    JButton viewEvents = new JButton("View all events");
    JButton createEvents = new JButton("Create events");
    JButton viewPastEvents = new JButton("Past events");
    JButton viewCurrentEvents = new JButton("Select");
    JButton viewFutureEvents = new JButton("Select");
    JButton individualEvent = new JButton("Select");
    JButton eventSeries = new JButton("Select");
    JButton individualAlert = new JButton("Individual");
    JButton frequencyAlert = new JButton("Frequency");
    JButton viewAlerts = new JButton("View alerts");
    JButton viewMemos = new JButton("View memos");
    JButton goBack = new JButton("Back");
    JButton createMemo = new JButton("Create memo");
    JButton createIAlert = new JButton("Create individual alert:");
    JButton createFAlert = new JButton("Create frequent alert:");

    JLabel existingAcc = new JLabel("Do you have an existing account?");
    JLabel userLabel = new JLabel("Username:");
    JLabel pswdLabel = new JLabel("Password:");
    JLabel newFeature = new JLabel("You can create a new account in phase 2!");
    JLabel incorrectCre = new JLabel("Please try again!");
    JLabel enterEvents = new JLabel("Event(s):");
    JLabel note = new JLabel("Note:");
    JLabel alert = new JLabel("Alerts:");
    JLabel memo = new JLabel("Memo:");
    JLabel memos = new JLabel("Memos:");
    JLabel pastEvents = new JLabel("Past events:");
    JLabel currentEvents = new JLabel("Current events:");
    JLabel futureEvents = new JLabel("Future events:");
    JLabel newEventName = new JLabel("Event name:");
    JLabel newEventDuration = new JLabel("Event duration in hours:");
    JLabel newEventMemo = new JLabel("Add a memo (optional):");
    JLabel newEventStartTime = new JLabel("Event start time (HH:MM format):");
    JLabel newEventStartDate = new JLabel("Event start time (DD-MM-YYYY format):");
    JLabel date = new JLabel("Date:");
    JLabel message = new JLabel("Message:");

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
            viewEventsDisplay(user, myCalendar, f6);
        });
        viewAlerts.addActionListener(e -> {
            f.dispose();
            viewAlertsDisplay(user, myCalendar, f7);
        });
        viewMemos.addActionListener(e -> {
            f.dispose();
            viewMemosDisplay(user, myCalendar, f8);
        });
        createEvents.addActionListener(e -> {
            f.dispose();
            createEventsDisplay(user, f9);
        });
    }

    public void viewEventsDisplay(String user, Calendar myCalendar, JFrame f){

        JPanel userPanel = new JPanel();
        userPanel.setLayout(new BoxLayout(userPanel, BoxLayout.Y_AXIS));
        JLabel userName = new JLabel();
        JLabel emptySpace = new JLabel(" ");
        userName.setText(user);
        userPanel.add(emptySpace);

        userPanel.add(currentEvents);

        if(myCalendar.getCurrentEvents() != null){
            for(Event item : myCalendar.getCurrentEvents()){
                JLabel label = new JLabel(item.toString());
                userPanel.add(label);
            }
        }
        else {
            JLabel label = new JLabel("No current events");
            userPanel.add(label);
        }

        userPanel.add(pastEvents);

        if(myCalendar.getPastEvents() != null){
            for(Event item : myCalendar.getPastEvents()){
                JLabel label = new JLabel(item.toString());
                userPanel.add(label);
            }
        }
        else {
            JLabel label = new JLabel("No past events");
            userPanel.add(label);
        }

        userPanel.add(futureEvents);

        if(myCalendar.getFutureEvents() != null){
            for(Event item : myCalendar.getFutureEvents()){
                JLabel label = new JLabel(item.toString());
                userPanel.add(label);
            }
        }
        else {
            JLabel label = new JLabel("No future events");
            userPanel.add(label);
        }

        userPanel.add(goBack);

        f.setSize(800, 800);
        f.add(userPanel);
        f.setLocationRelativeTo(null);
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        goBack.addActionListener(e -> {
            f.dispose();
            try {
                f.dispose();
                mainDisplay(user, f5);
            } catch (IOException ex) {
                ex.printStackTrace();
            } catch (ClassNotFoundException ex) {
                ex.printStackTrace();
            }
        });
    }

    public void viewAlertsDisplay(String user, Calendar calendar, JFrame f){

        JPanel display = new JPanel();
        display.setLayout(new BoxLayout(display, BoxLayout.Y_AXIS));

        //display title
        JLabel titleCurrAlert = new JLabel("Current alerts:");
        display.add(titleCurrAlert);

        //display the current alerts
        if (calendar.getAllAlerts().isEmpty())
            display.add(new JLabel("No current alerts!"));

        for (Alert alert: calendar.getCurrAlerts()){
            JLabel a = new JLabel(alert.toString());
            display.add(a);
        }

        //display all alerts
        JLabel titleAllAlert = new JLabel("All alerts:");
        display.add(titleAllAlert);
        if (calendar.getAllAlerts().isEmpty())
            display.add(new JLabel("No current alerts!"));

        for (Alert alert: calendar.getAllAlerts()) {
            JLabel a = new JLabel(alert.toString());
            display.add(a);
        }

        //button for creating alerts
        createIAlert.addActionListener(ae ->{
            //I tried the try-catch block but it shows error that exception is never thrown
            f.dispose();
            createIAlertDisplay(calendar, f12);
        });

        createFAlert.addActionListener(ae ->{
            //I tried the try-catch block but it shows error that exception is never thrown
            f.dispose();
            createFAlertDisplay(calendar, f13);
        });
        display.add(createFAlert);
        display.add(createIAlert);

        display.add(goBack);
        //perform the goback actions here... can we pull it out and make it a helper function?
        goBack.addActionListener(e -> {
            f.dispose();
            try {
                f.dispose();
                mainDisplay(user, f5);
            } catch (IOException ex) {
                ex.printStackTrace();
            } catch (ClassNotFoundException ex) {
                ex.printStackTrace();
            }
        });

        //initialize
        f.setSize(800, 800);
        f.add(display);
        f.setLocationRelativeTo(null);
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    public void createIAlertDisplay(Calendar myCalendar, JFrame f){
        JPanel display = new JPanel();
        f.add(new JLabel("Create Individual Alert"));
        f.add(new JLabel("Enter: (1) Event Name, (2) Alert Message, (3) Alert Date in yyyy-mm-dd: HH:mm"));
        JTextField eventTxt = new JTextField();
        JTextField msgTxt = new JTextField();
        JTextField dateTxt = new JTextField();
        JLabel enterEvent = new JLabel("Event:");
        JLabel enterMessage = new JLabel("Message");
        JLabel enterDate = new JLabel("Date");

        //display field:500*300
        f.setSize(500, 300);
        enterEvent.setBounds(25, 150, 25, 30);
        eventTxt.setBounds(50, 150, 60, 30);
        enterMessage.setBounds(120, 150, 30, 30);
        msgTxt.setBounds(150, 150, 75, 30);
        enterDate.setBounds(225, 150, 25, 30);
        dateTxt.setBounds(250, 150, 25, 30);
        submit.setBounds(200, 200, 90, 30);

        JComponent[] arr = new JComponent[]{enterEvent, eventTxt, enterMessage, msgTxt, enterDate, dateTxt, submit};
        for(JComponent i: arr){
            display.add(i);
        }
        makeVisible(f);


        //functions for submit button
        submit.addActionListener(ae -> {
            String name = eventTxt.getText();
            String msg = msgTxt.getText();
            String d = dateTxt.getText();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            LocalDateTime date = LocalDateTime.parse(d, formatter);

            if (myCalendar.getEventNames().contains(name)) {
                myCalendar.addIndividualAlert(myCalendar.getEvent(name), msg, date);
            }
        });

        f.add(display);
        f.setLocationRelativeTo(null);
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void createFAlertDisplay(Calendar myCalendar, JFrame f){
        JPanel display = new JPanel();
        f.add(new JLabel("Create Individual Alert"));
        f.add(new JLabel("Enter: (1) Event Name, (2) Alert Message, Alert Frequency every (3) number of (4) frequency"));
        f.add(new JLabel("ex. every 1 d(for day) or every 6 h(for hours), only d/h permitted"));
        JTextField eventTxt = new JTextField();
        JTextField msgTxt = new JTextField();
        JTextField nTxt = new JTextField();
        JTextField fTxt = new JTextField();
        JLabel enterEvent = new JLabel("Event:");
        JLabel enterMessage = new JLabel("Message");
        JLabel enterNumberOf = new JLabel("Frequency: every");

        //display field:500*300
        f.setSize(500, 300);
        enterEvent.setBounds(25, 150, 25, 30);
        eventTxt.setBounds(50, 150, 60, 30);
        enterMessage.setBounds(120, 150, 30, 30);
        msgTxt.setBounds(150, 150, 75, 30);
        enterNumberOf.setBounds(225, 150, 50, 30);
        nTxt.setBounds(275, 150, 25, 30);
        fTxt.setBounds(300, 150, 25, 30);
        submit.setBounds(200, 200, 90, 30);

        JComponent[] arr = new JComponent[]{enterEvent, eventTxt, enterMessage, msgTxt, enterNumberOf, nTxt, fTxt, submit};
        for(JComponent i: arr){
            display.add(i);
        }
        makeVisible(f);

        f.add(display);
        f.setLocationRelativeTo(null);
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //functions for submit button
        submit.addActionListener(ae -> {
            String name = eventTxt.getText();
            String msg = msgTxt.getText();
            String num = nTxt.getText();
            char freq = fTxt.getText().charAt(0);

            if (myCalendar.getEventNames().contains(name)) {
                Duration duration = null;
                switch (freq) {
                    case 'd':
                        duration = Duration.ofDays(Long.parseLong(num));
                        break;
                    case 'h':
                        duration = Duration.ofHours(Long.parseLong(num));
                        break;
                }
                if (duration!= null)
                    myCalendar.addFrequentAlert(myCalendar.getEvent(name), msg, duration);
            }
        });
    }

    public void viewMemosDisplay(String user, Calendar myCalendar, JFrame f){
        JPanel userPanel = new JPanel();
        userPanel.setLayout(new BoxLayout(userPanel, BoxLayout.Y_AXIS));
        JLabel userName = new JLabel();
        JLabel emptySpace = new JLabel(" ");
        userName.setText(user);
        userPanel.add(userLabel);
        userPanel.add(userName);
        userPanel.add(emptySpace);

        MemoSystem myMemos = myCalendar.getMyMemos();

        userPanel.add(memos);

        if(myMemos.getMemos() != null){
            for(Memo memo1 : myMemos.getMemos()){
                JLabel label = new JLabel(memo1.toString());
                userPanel.add(label);
            }
        }
        else {
            JLabel label = new JLabel("No memos");
            userPanel.add(label);
        }

        userPanel.add(createMemo);

        f.setSize(800, 800);
        f.add(userPanel);
        f.setLocationRelativeTo(null);
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        createMemo.addActionListener(e -> {
            f.dispose();
            createMemoDisplay(user, myCalendar, f10);
        });

    }

    public void createMemoDisplay(String user, Calendar myCalendar, JFrame f) {
        JTextField eventsText = new JTextField();
        JTextField noteText = new JTextField();

        submit.setBounds(200, 150, 90, 30);
        enterEvents.setBounds(50, 100, 70, 30);
        note.setBounds(300, 100, 70, 30);
        eventsText.setBounds(120, 100, 100, 30);
        noteText.setBounds(370, 100, 100, 30);

        f.setSize(500,300);
        f.add(enterEvents);
        f.add(eventsText);
        f.add(note);
        f.add(noteText);
        f.add(submit);
        makeVisible(f);

        MemoSystem myMemos = myCalendar.getMyMemos();

        submit.addActionListener(ae -> {
            String events = eventsText.getText();
            String note1 = noteText.getText();
            String[] eventList = events.split(",");

            if(eventsInCalendar(eventList, myCalendar)){ // checks if the events entered are in the Calendar

                ArrayList<Event> events1 = eventNameToEventList(eventList, myCalendar);
                myMemos.createMemo(events1, note1);

                f.setVisible(false);
                f.dispose();

            }
            else{
                eventsText.setText("");
                noteText.setText("");
                incorrectCre.setBounds(200, 70, 200, 30);
                f.setVisible(false);
                f11.setSize(500,300);
                f11.add(createEvents);
                f11.add(note);
                f11.add(incorrectCre);
                f11.add(eventsText);
                f11.add(noteText);
                f11.add(submit);
                makeVisible(f11);
            }
        });

    }

    public void createEventsDisplay(String user, JFrame f){
        JPanel gbPanel = new JPanel(new GridBagLayout());
        JTextField eventName = new JTextField();
        JTextField eventDuration = new JTextField();
        JTextField eventStartDate = new JTextField(); //DD-MM-YYYY format
        JTextField eventStartTime = new JTextField(); //HH:MM format
        JTextField eventMemo = new JTextField();

        submit.setBounds(200, 150, 90, 30);
        newEventName.setBounds(50, 100, 70, 30);
        newEventDuration.setBounds(300, 100, 70, 30);
        eventName.setBounds(120, 100, 100, 30);
        eventDuration.setBounds(370, 100, 100, 30);

        f.setSize(500,300);
        f.add(newEventName);
        f.add(newEventDuration);
        f.add(eventName);
        f.add(eventDuration);
        f.add(newEventMemo);
        f.add(eventMemo);
        f.add(submit);
        f.add(newEventStartDate);
        f.add(eventStartDate);
        f.add(newEventStartTime);
        f.add(eventStartTime);
        makeVisible(f);

        submit.addActionListener(ae -> {
            String name = eventName.getText();
            int duration = Integer.parseInt(eventDuration.getText());
            LocalDateTime start = LocalDateTime.of(Integer.parseInt(eventStartDate.getText().substring(6)),
                            Integer.parseInt(eventStartDate.getText().substring(3, 5)),
                            Integer.parseInt(eventStartDate.getText().substring(0,2)),
                                    Integer.parseInt(eventStartTime.getText().substring(0, 2)),
                                            Integer.parseInt(eventStartTime.getText().substring(3)));
            String memo = eventMemo.getText();
        });
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
        Scanner scanner = new Scanner(new FileInputStream("./phase1/users.csv"));
        String[] login;
        HashMap<String, String> users = new HashMap<>();
        while (scanner.hasNextLine()) {
            login = scanner.nextLine().split(",");
            users.put(login[0], login[1]);
        }
        return users;
    }

    // returns a boolean value for whether a given array of events are in the Calendar or not
    public boolean eventsInCalendar(String[] events, Calendar myCalendar) {
        ArrayList<Event> calEvents = myCalendar.getMyEvents();

        for (int i = 0; i < events.length; i++ ) {
            String event = events[i];
            if (!myCalendar.getEventNames().contains(event)) {
                return false;
            }
        }
        return true;
    }

    // converts an array of event names to a list of Events
    public ArrayList<Event> eventNameToEventList(String[] array, Calendar myCalendar) {

        ArrayList<Event> calEvents = myCalendar.getMyEvents();
        ArrayList<Event> events = new ArrayList<>();

        for (int i = 0; i < array.length; i++ ) {
            String name = array[i];
            for (Event e : calEvents){
                if (e.getEventName().equals(name)) {
                    events.add(e);
                }
            }
        }
        return events;
    }



}
