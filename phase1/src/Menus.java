import javax.swing.*;
import javax.swing.JOptionPane;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Array;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.time.LocalDateTime;

public class Menus extends JFrame {

    private static JFrame f = new JFrame("Account");
    private static JFrame f2 = new JFrame("Log In");
    private static JFrame f3 = new JFrame("New Feature is coming!");
    private static JFrame f4 = new JFrame("Incorrect Credentials!");
    private static JFrame f5 = new JFrame("Main Menu");
    private static JFrame f6 = new JFrame("View Events");
    private static JFrame f7 = new JFrame("View Alerts");
    private static JFrame f8 = new JFrame("View Memos");
    private static JFrame f9 = new JFrame("Create New Events");
    private static JFrame f10 = new JFrame("Create New Memo for Event(s)");
    private static JFrame f11 = new JFrame("Events not in Calendar");
    private static JFrame f12 = new JFrame("Create New Individual Alert");
    private static JFrame f13 = new JFrame("Create New Frequent Alert");
    private static JFrame f14 = new JFrame("Find Event(s)");
    private static JFrame f15 = new JFrame("Find Event(s) by Date");
    private static JFrame f16 = new JFrame("Find Event(s) by Tag");
    private static JFrame f17 = new JFrame("Find Event(s) by Memo");
    private static JFrame f18 = new JFrame("Invalid Memo Id number");
    private static JFrame f19 = new JFrame("Invalid Event name");
    private static JFrame f20 = new JFrame("Memos for Event");
    private static JFrame f21 = new JFrame("Select Event(s)");
    private static JFrame f22 = new JFrame("Tag Event");
    private static JFrame f23 = new JFrame("Events");


    JButton yes = new JButton("Yes");
    JButton no = new JButton("No");
    JButton submit = new JButton("Submit");
    JButton submit2 = new JButton("Submit");
    JButton submit3 = new JButton("Submit");
    JButton submit4 = new JButton("Submit");
    JButton viewEvents = new JButton("View all events");
    JButton createEvents = new JButton("Create events");
    JButton viewAlerts = new JButton("View alerts");
    JButton viewMemos = new JButton("View memos");
    JButton createMemo = new JButton("Create memo");
    JButton createIAlert = new JButton("Create individual alert:");
    JButton createFAlert = new JButton("Create frequent alert:");
    JButton bAllAlert = new JButton("View all alerts");
    JButton findEventByDate = new JButton("Find Event by date");
    JButton findEventByTag = new JButton("Find Event by tag");
    JButton findEventByMemo = new JButton("Find Event by memo");
    JButton selectEvent = new JButton("Select");
    JButton findEvents = new JButton("Select");
    JButton tagEvents = new JButton("Select");

    JLabel existingAcc = new JLabel("Do you have an existing account?");
    JLabel userLabel = new JLabel("Username:");
    JLabel pswdLabel = new JLabel("Password:");
    JLabel newFeature = new JLabel("You can create a new account in phase 2!");
    JLabel incorrectCre = new JLabel("Please try again!");
    JLabel enterEvents = new JLabel("Event(s):");
    JLabel note = new JLabel("Note:");

    JLabel memos = new JLabel("Memos:");
    JLabel pastEvents = new JLabel("Past events:");
    JLabel currentEvents = new JLabel("Current events:");
    JLabel futureEvents = new JLabel("Future events:");
    JLabel newEventName = new JLabel("Event name:");
    JLabel newEventDuration = new JLabel("Event duration in hours:");
    JLabel newEventMemo = new JLabel("Add a memo (optional):");
    JLabel newEventStartTime = new JLabel("Event start time (HH:MM format):");
    JLabel newEventStartDate = new JLabel("Event start date (DD-MM-YYYY format):");
    JLabel date = new JLabel("Date (YYYY-MM-DD):");
    JLabel tag = new JLabel("Tag:");
    JLabel memoid = new JLabel("Memo id number:");
    JLabel selectEventLabel = new JLabel("Select Event");
    JLabel findEventLabel = new JLabel("Find Event");
    JLabel tagEventLabel = new JLabel("Tag Event");

    public Menus(CalendarManager sm) throws IOException {
        HashMap<String, String> users = getUsers(); //Creating a map of all users in the csv file

        Calendar myCalendar = sm.getCalendar();
        myCalendar.update();

        accountButton(sm, myCalendar, f);
    }

    /**
     * Checks if the entered username and password are valid
     */
    public static boolean login(String user, String pswd, HashMap<String, String> users) {
        if (!users.containsKey(user)) {
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

    /**
     * Displays first screen prompting user if they have an account
     *
     * @param f: pre-fixed JFrame
     */
    public void accountButton(CalendarManager sm, Calendar c, JFrame f) throws IOException {
        HashMap<String, String> users = getUsers(); //Creating a map of all users in the csv file

        yes.setBounds(70, 100, 90, 30);
        no.setBounds(170, 100, 90, 30);

        existingAcc.setBounds(10, 10, 300, 100);

        f.setSize(300, 300);
        f.add(existingAcc);
        f.add(yes);
        f.add(no);
        makeVisible(f);

        //action listener
        yes.addActionListener(arg0 -> {
            f.dispose();
            accountLogIn(sm, c, users, f2); // prompt user to log in
        });
        no.addActionListener(e -> {
            f.dispose();
            createNewAccount(users, f3);
        });
    }

    /**
     * Creates a new account, extension for Phase 2.
     *
     * @param users: a HashMap of all users in the external .csv file
     * @param f:     pre-fixed JFrame
     */
    public void createNewAccount(HashMap<String, String> users, JFrame f) {
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
     *
     * @param users: a HashMap of all users in the external .csv file
     * @param f:     pre-fixed JFrame
     */
    public void accountLogIn(CalendarManager sm, Calendar c, HashMap<String, String> users, JFrame f) {
        JTextField userText = new JTextField();
        JPasswordField pswdText = new JPasswordField();

        submit.setBounds(200, 150, 90, 30);
        userLabel.setBounds(50, 100, 70, 30);
        pswdLabel.setBounds(300, 100, 70, 30);
        userText.setBounds(120, 100, 100, 30);
        pswdText.setBounds(370, 100, 100, 30);

        f.setSize(500, 300);
        f.add(userLabel);
        f.add(pswdLabel);
        f.add(userText);
        f.add(pswdText);
        f.add(submit);
        makeVisible(f);

        submit.addActionListener(ae -> {
            String user = userText.getText();
            String pswd = pswdText.getText();

            if (login(user, pswd, users)) {
                f.setVisible(false);
                f.dispose();
                f4.setVisible(false);
                f4.dispose();
                try {
                    mainDisplay(sm, c, user, f5);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            } else {
                userText.setText("");
                pswdText.setText("");
                incorrectCre.setBounds(200, 70, 200, 30);
                f.setVisible(false);
                f4.setSize(500, 300);
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
     *
     * @param user : specific user that logged in
     * @param f    :    pre-fixed JFrame
     */
    public void mainDisplay(CalendarManager sm, Calendar c, String user, JFrame f) throws IOException, ClassNotFoundException {

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
            viewEventsDisplay(user, c, f6);
        });
        viewAlerts.addActionListener(e -> {
            f.dispose();
            viewAlertsDisplay(user, sm, c, f7);
        });
        viewMemos.addActionListener(e -> {
            f.dispose();
            viewMemosDisplay(user, c, f8);
        });
        createEvents.addActionListener(e -> {
            f.dispose();
            createEventsDisplay(sm, c, f9);
        });
    }

    public void viewEventsDisplay(String user, Calendar myCalendar, JFrame f) {

        JPanel userPanel = new JPanel(new GridBagLayout());
        JLabel userName = new JLabel();
        JLabel emptySpace = new JLabel(" ");
        userName.setBounds(100, 60, 80, 30);
        userName.setText(user);
        userLabel.setBounds(30, 60, 70, 30);

        addGB(userPanel, userLabel, 0, 0);
        addGB(userPanel, userName, 0, 1);
        addGB(userPanel, emptySpace, 0, 2);

        addGB(userPanel, currentEvents, 0, 3);

        int i = 4;

        if (myCalendar.getCurrentEvents() != null && !myCalendar.getCurrentEvents().isEmpty()) {
            for (Event item : myCalendar.getCurrentEvents()) {
                JLabel label = new JLabel(item.toString());
                addGB(userPanel, label, 0, i);
                i++;
            }
        } else {
            JLabel label = new JLabel("No current events");
            addGB(userPanel, label, 0, i);
        }

        addGB(userPanel, emptySpace, 0, i + 1);
        addGB(userPanel, pastEvents, 0, i + 2);

        i = i + 3;

        if (myCalendar.getPastEvents() != null && !myCalendar.getPastEvents().isEmpty()) {
            for (Event item : myCalendar.getPastEvents()) {
                JLabel label = new JLabel(item.toString());
                addGB(userPanel, label, 0, i);
                i++;
            }
        } else {
            JLabel label = new JLabel("No past events");
            addGB(userPanel, label, 0, i);
        }

        addGB(userPanel, emptySpace, 0, 2);
        addGB(userPanel, futureEvents, 0, i + 1);

        i = i + 2;
        if (myCalendar.getFutureEvents() != null && !myCalendar.getFutureEvents().isEmpty()) {
            for (Event item : myCalendar.getFutureEvents()) {
                JLabel label = new JLabel(item.toString());
                addGB(userPanel, label, 0, i);
                i++;
            }
        } else {
            JLabel label = new JLabel("No future events");
            addGB(userPanel, label, 0, i);
        }
        i = i + 1;

        addGB(userPanel, selectEventLabel, 0, i);
        addGB(userPanel, selectEvent, 0, i + 1);
        addGB(userPanel, findEventLabel, 0, i + 2);
        addGB(userPanel, findEvents, 0, i + 3);
        addGB(userPanel, tagEventLabel, 0, i + 4);
        addGB(userPanel, tagEvents, 0, i + 5);

        selectEvent.addActionListener(a -> {
            f.dispose();
            selectEventDisplay(user, myCalendar, f21);
        });
        findEvents.addActionListener(e -> {
            f.dispose();
            findEventsDisplay(user, myCalendar, f14);
        });
        tagEvents.addActionListener(ae -> {
            f.dispose();
            tagEventDisplay(myCalendar, f22);
        });

        JLabel time = new JLabel("Current time: " + myCalendar.getTime());
        userPanel.add(time);
        addGB(userPanel, time, 0, 10);

        f.setSize(800, 800);
        f.add(userPanel);
        f.setLocationRelativeTo(null);
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    public void findEventsDisplay(String user, Calendar myCalendar, JFrame f) {
        JPanel gbPanel = new JPanel(new GridBagLayout());

        addGB(gbPanel, findEventByDate, 0, 3);
        addGB(gbPanel, findEventByTag, 0, 4);
        addGB(gbPanel, findEventByMemo, 0, 5);

        f.setSize(800, 800);
        f.add(gbPanel);
        f.setLocationRelativeTo(null);
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        findEventByDate.addActionListener(e -> {
            f.dispose();
            findEventDateDisplay(user, myCalendar, f15);
        });
        findEventByTag.addActionListener(e -> {
            f.dispose();
            findEventTagDisplay(user, myCalendar, f16);
        });
        findEventByMemo.addActionListener(e -> {
            f.dispose();
            findEventMemoDisplay(user, myCalendar, f17);
            viewMemosDisplay(user, myCalendar, f8);
        });
    }

    public void findEventDateDisplay(String user, Calendar myCalendar, JFrame f) {
        JTextField dateText = new JTextField();

        submit.setBounds(200, 150, 90, 30);
        date.setBounds(50, 100, 130, 30);
        dateText.setBounds(180, 100, 100, 30);

        f.setSize(500, 300);
        f.add(date);
        f.add(dateText);
        f.add(submit);
        makeVisible(f);

        submit.addActionListener(ae -> {
            String date = dateText.getText();
            LocalDate d = LocalDate.parse(date);

            ArrayList<Event> events = myCalendar.findEvent(d);

            f.dispose();

            findEventsHelperfunc(events, user, f23);
        });
    }

    public void findEventTagDisplay(String user, Calendar myCalendar, JFrame f) {
        JTextField tagText = new JTextField();

        submit.setBounds(200, 150, 90, 30);
        tag.setBounds(50, 100, 70, 30);
        tagText.setBounds(120, 100, 100, 30);

        f.setSize(500, 300);
        f.add(tag);
        f.add(tagText);
        f.add(submit);
        makeVisible(f);

        submit.addActionListener(ae -> {
            String tag1 = tagText.getText();

            ArrayList<Event> events = myCalendar.findEvent(tag1);

            f.dispose();

            findEventsHelperfunc(events, user, f23);
        });
    }

    public void findEventMemoDisplay(String user, Calendar myCalendar, JFrame f) {

        JTextField memoText = new JTextField();

        submit.setBounds(200, 150, 90, 30);
        memoid.setBounds(50, 100, 70, 30);
        memoText.setBounds(120, 100, 100, 30);

        f.setSize(500, 300);
        f.add(memoid);
        f.add(memoText);
        f.add(submit);
        makeVisible(f);


        submit.addActionListener(ae -> {
            String memoId = memoText.getText();
            try {
                int memoIdNum = Integer.parseInt(memoId);

                Memo memo1 = myCalendar.getMyMemos().getMemo(memoIdNum);

                ArrayList<Event> events = myCalendar.findEvent(memo1);

                f.dispose();
                findEventsHelperfunc(events, user, f23);

            } catch (NumberFormatException n) {
                memoText.setText("");
                incorrectCre.setBounds(200, 70, 200, 30);
                f.setVisible(false);
                f18.setSize(500, 300);
                f18.add(memoid);
                f18.add(incorrectCre);
                f18.add(memoText);
                f18.add(submit);
                makeVisible(f18);
            }
        });
    }

    // helper function for findEventDateDisplay, findEventTagDisplay and findEventMemoDisplay
    private void findEventsHelperfunc(ArrayList<Event> events, String user, JFrame f) {
        JPanel userPanel = new JPanel(new GridBagLayout());
        JLabel userName = new JLabel();
        JLabel emptySpace = new JLabel(" ");
        userName.setBounds(100, 60, 80, 30);
        userName.setText(user);
        userLabel.setBounds(30, 60, 70, 30);

        addGB(userPanel, userLabel, 0, 0);
        addGB(userPanel, userName, 0, 1);
        addGB(userPanel, emptySpace, 0, 2);

        userPanel.add(emptySpace);

        addGB(userPanel, enterEvents, 0, 3);

        if (events != null) { // displays events to screen
            for (Event e : events) {
                JLabel label = new JLabel(e.toString());
                addGB(userPanel, label, 0, 4);
            }
        } else {
            JLabel label = new JLabel("No events found.");
            userPanel.add(label);
            addGB(userPanel, label, 0, 4);

        }

        f.setSize(800, 800);
        f.add(userPanel);
        makeVisibleGB(f);
    }

    public void selectEventDisplay(String user, Calendar myCalendar, JFrame f) {

        JTextField eventNameText = new JTextField();

        submit3.setBounds(200, 150, 90, 30);
        newEventName.setBounds(50, 100, 70, 30);
        eventNameText.setBounds(120, 100, 100, 30);

        f.setSize(500, 300);
        f.add(newEventName);
        f.add(eventNameText);
        f.add(submit);
        makeVisible(f);

        submit3.addActionListener(ae -> {
            String eventName = eventNameText.getText();

            String[] eventArray = {eventName};

            ArrayList<Event> eventList = eventNameToEventList(eventArray, myCalendar);

            if (eventList != null) {

                Event e = eventList.get(0);

                f.setVisible(false);
                f.dispose();

                JPanel userPanel = new JPanel(new GridBagLayout());
                JLabel userName = new JLabel();
                JLabel emptySpace = new JLabel(" ");
                userName.setBounds(100, 60, 80, 30);
                userName.setText(user);
                userLabel.setBounds(30, 60, 70, 30);

                addGB(userPanel, userLabel, 0, 0);
                addGB(userPanel, userName, 0, 1);
                addGB(userPanel, emptySpace, 0, 2);
                addGB(userPanel, memos, 0, 3);

                int i = 4;

                if (!e.getMemos().isEmpty()) {
                    for (Memo memo1 : e.getMemos()) {
                        JLabel label = new JLabel(memo1.toString());
                        addGB(userPanel, label, 0, i);
                        i++;
                    }
                } else {
                    JLabel label = new JLabel("No memos");
                    userPanel.add(label);
                    addGB(userPanel, label, 0, i);
                }
                f20.setSize(800, 800);
                f20.add(userPanel);
                makeVisibleGB(f20);
            } else {
                eventNameText.setText("");
                incorrectCre.setBounds(200, 70, 200, 30);
                f.setVisible(false);
                f19.setSize(500, 300);
                f19.add(newEventName);
                f19.add(incorrectCre);
                f19.add(eventNameText);
                f19.add(submit);
                makeVisible(f19);
            }
        });
    }

    public void tagEventDisplay(Calendar myCalendar, JFrame f) {
        JTextField eventText = new JTextField();
        JTextField tagText = new JTextField();

        submit.setBounds(200, 150, 90, 30);
        newEventName.setBounds(50, 100, 70, 30);
        tag.setBounds(300, 100, 70, 30);
        eventText.setBounds(120, 100, 100, 30);
        tagText.setBounds(370, 100, 100, 30);

        f.setSize(500, 300);
        f.add(newEventName);
        f.add(tag);
        f.add(eventText);
        f.add(tagText);
        f.add(submit);
        makeVisible(f);

        submit.addActionListener(ae -> {
            String event = eventText.getText();
            String tag1 = tagText.getText();

            String[] eventArray = {event};

            ArrayList<Event> eventList = eventNameToEventList(eventArray, myCalendar);

            if (eventList != null) {

                eventList.get(0).setTag(tag1);
                System.out.println("Tag set to " + tag1);

                f.setVisible(false);
                f.dispose();

            } else {
                eventText.setText("");
                tagText.setText("");
                incorrectCre.setBounds(200, 70, 200, 30);
                f.setVisible(false);
                f19.setSize(500, 300);
                f19.add(newEventName);
                f19.add(tag);
                f19.add(incorrectCre);
                f19.add(eventText);
                f19.add(tagText);
                f19.add(submit);
                makeVisible(f19);
            }
        });
    }

    public void viewAlertsDisplay(String user, CalendarManager sm, Calendar calendar, JFrame f) {

        JPanel display = new JPanel(new GridBagLayout());
        JLabel userName = new JLabel();
        JLabel emptySpace = new JLabel(" ");
        JLabel titleCurrAlert = new JLabel("Current alerts:\n");
        userName.setBounds(100, 60, 80, 30);
        userName.setText(user);
        userLabel.setBounds(30, 60, 70, 30);

        addGB(display, userLabel, 0, 0);
        addGB(display, userName, 0, 1);
        addGB(display, emptySpace, 0, 2);
        addGB(display, titleCurrAlert, 0, 3);

        //display the current alerts
        if (calendar.getAllAlerts().isEmpty() || calendar.getAllAlerts() == null) {
            addGB(display, new JLabel("No current alerts!"), 0, 4);
        }
        else {
            for (Alert alert : calendar.getCurrAlerts()) {
                JLabel a = new JLabel(alert.toString());
                addGB(display, a, 0, 4);
            }
        }

        addGB(display, bAllAlert, 0, 5);
        addGB(display, createFAlert, 0, 6);
        addGB(display, createIAlert, 0, 7);

        f.setSize(800, 800);
        f.add(display);
        f.setLocationRelativeTo(null);
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //display all alerts if pressed
        bAllAlert.addActionListener(e -> {
            int y = 9;

            f.setVisible(false);
            JLabel titleAllAlert = new JLabel("All alerts:");
            addGB(display, titleAllAlert, 0, 8);
            if (calendar.getAllAlerts() == null || calendar.getAllAlerts().isEmpty()) {
                addGB(display, new JLabel("No current alerts!"), 0, 9);
            } else {
                for (Alert alert : calendar.getAllAlerts()) {
                    JLabel a = new JLabel(alert.toString());
                    addGB(display, a, 0, y);
                    y++;
                }
            }
            f.setSize(800, 800);
            f.add(display);
            f.setLocationRelativeTo(null);
            f.setVisible(true);
            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        });
        //button for creating alerts
        createIAlert.addActionListener(e -> {
            //I tried the try-catch block but it shows error that exception is never thrown
            f.dispose();
            createIAlertDisplay(sm, calendar, f12);
        });

        createFAlert.addActionListener(ae -> {
            //I tried the try-catch block but it shows error that exception is never thrown
            f.dispose();
            createFAlertDisplay(sm, calendar, f13);
        });
    }

    public void createIAlertDisplay(CalendarManager sm, Calendar myCalendar, JFrame f) {

        JPanel gbPanel = new JPanel(new GridBagLayout());
        JLabel create_individual_alert = new JLabel("Create Individual Alert");

        // f.add(new JLabel("Enter: (1) Event Name, (2) Alert Message, (3) Alert Date in yyyy-mm-dd: HH:mm"));
        JTextField eventTxt = new JTextField();
        JTextField msgTxt = new JTextField();
        JTextField dateTxt = new JTextField();
        JLabel enterEvent = new JLabel("Event:");
        JLabel enterMessage = new JLabel("Message");
        JLabel enterDate = new JLabel("Date");

//        //display field:500*300
//        f.setSize(500, 300);
//        create_individual_alert.setBounds(0, 0, 100, 30);
//        enterEvent.setBounds(25, 150, 25, 30);
//        eventTxt.setBounds(50, 150, 60, 30);
//        enterMessage.setBounds(120, 150, 30, 30);
//        msgTxt.setBounds(150, 150, 75, 30);
//        enterDate.setBounds(225, 150, 25, 30);
//        dateTxt.setBounds(250, 150, 25, 30);
//        submit.setBounds(200, 200, 90, 30);

        JComponent[] arr = new JComponent[]{eventTxt, msgTxt, dateTxt};
        for (JComponent i: arr){
            i.setPreferredSize(new Dimension(100, 30));
        }

        JComponent[] arr2 = new JComponent[]{create_individual_alert, enterEvent, eventTxt, enterMessage, msgTxt, enterDate, dateTxt, submit};
        addGB(gbPanel, new JLabel("Enter: (1) Event Name, (2) Alert Message, (3) " +
                "Alert Date in yyyy-mm-dd: HH:mm"), 0, 0);
        addGB(gbPanel, enterEvent, 0, 1);
        addGB(gbPanel, eventTxt, 3, 1);
        addGB(gbPanel, enterMessage, 0, 2);
        addGB(gbPanel, msgTxt, 3, 2);
        addGB(gbPanel, enterDate, 0, 3);
        addGB(gbPanel, dateTxt, 3, 3);
        addGB(gbPanel, submit2, 2, 5);

        //functions for submit button
        submit.addActionListener(ae -> {
            String name = eventTxt.getText();
            String msg = msgTxt.getText();
            String d = dateTxt.getText();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            LocalDateTime date = LocalDateTime.parse(d, formatter);

            if (myCalendar.getEventNames().contains(name)) {
                myCalendar.addIndividualAlert(myCalendar.getEvent(name), msg, date);
                try {
                    sm.saveToFile("user.ser");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                f.dispose();
            }
        });

        f.setSize(500, 300);
        f.add(gbPanel);
        makeVisibleGB(f);
    }

    public void createFAlertDisplay(CalendarManager sm, Calendar myCalendar, JFrame f) {
        JPanel display = new JPanel(new GridBagLayout());

        JTextField eventTxt = new JTextField();
        JTextField msgTxt = new JTextField();
        JTextField nTxt = new JTextField();
        JTextField fTxt = new JTextField();
        JLabel enterEvent = new JLabel("Event:");
        JLabel enterMessage = new JLabel("Message");
        JLabel enterNumberOf = new JLabel("Frequency: every");

        //display field:500*300
        //f.setSize(500, 300);
        //enterEvent.setBounds(25, 150, 25, 30);
        //eventTxt.setBounds(50, 150, 60, 30);
        //enterMessage.setBounds(120, 150, 30, 30);
        //msgTxt.setBounds(150, 150, 75, 30);
        //enterNumberOf.setBounds(225, 150, 50, 30);
        //nTxt.setBounds(275, 150, 25, 30);
        //fTxt.setBounds(300, 150, 25, 30);
        //submit.setBounds(200, 200, 90, 30);

        addGB(display, new JLabel("Create Individual Alert"), 0, 1);
        addGB(display, new JLabel("Enter: (1) Event Name, (2) Alert Message, Alert Frequency every (3) number of (4) frequency"), 0, 2);
        addGB(display, new JLabel("ex. every 1 d(for day) or every 6 h(for hours), only d/h permitted"), 0, 3);

        JComponent[] arr = new JComponent[]{eventTxt, msgTxt, nTxt};
        for (JComponent i : arr) {
            i.setPreferredSize(new Dimension(100, 30));
        }
        addGB(display, enterEvent, 0, 4);
        addGB(display, eventTxt, 1, 4);
        addGB(display, enterMessage, 0, 5);
        addGB(display, msgTxt, 1, 5);
        addGB(display, enterNumberOf, 0, 6);
        addGB(display, nTxt, 1, 6);
        addGB(display, submit4, 1, 7);

        f.setSize(800, 800);
        f.add(display);
        makeVisibleGB(f);

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
                if (duration != null)
                    myCalendar.addFrequentAlert(myCalendar.getEvent(name), msg, duration);
                    try {
                        sm.saveToFile("user.ser");
                    } catch (IOException e) {
                      e.printStackTrace();
                 }
                    f.dispose();
            }
        });
    }

    public void viewMemosDisplay(String user, Calendar myCalendar, JFrame f) {

        JPanel display = new JPanel(new GridBagLayout());
        JLabel userName = new JLabel();
        JLabel emptySpace = new JLabel(" ");

        userName.setBounds(100, 60, 80, 30);
        userName.setText(user);
        userLabel.setBounds(30, 60, 70, 30);

        addGB(display, userLabel, 0, 0);
        addGB(display, userName, 0, 1);
        addGB(display, emptySpace, 0, 2);

        int y = 4;

        if (myCalendar.getMyMemos() == null || myCalendar.getMyMemos().getMemos() == null) {
            addGB(display, new JLabel("No memos"), 0, 4);
        } else if (myCalendar.getMyMemos().isEmpty()) {
            addGB(display, new JLabel("No memos"), 0, 4);
        } else {
            MemoSystem myMemos = myCalendar.getMyMemos();
            for (Memo memo1 : myMemos.getMemos()) {
                addGB(display, new JLabel(memo1.toString()), 0, y);
                y++;
            }
        }

        addGB(display, createMemo, 0, y + 1);

        f.setSize(800, 800);
        f.add(display);
        makeVisibleGB(f);

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

        f.setSize(500, 300);
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

            if (eventList.length != 0) {

                if (eventsInCalendar(eventList, myCalendar)) { // checks if the events entered are in the Calendar

                    ArrayList<Event> events1 = eventNameToEventList(eventList, myCalendar);
                    myMemos.createMemo(events1, note1);
                    f.setVisible(false);
                    f.dispose();

                } else {
                    eventsText.setText("");
                    noteText.setText("");
                    incorrectCre.setBounds(200, 70, 200, 30);
                    f.setVisible(false);
                    f11.setSize(500, 300);
                    f11.add(enterEvents);
                    f11.add(note);
                    f11.add(incorrectCre);
                    f11.add(eventsText);
                    f11.add(noteText);
                    f11.add(submit);
                    makeVisible(f11);
                }
            } else {

                String[] newEventsList = {events};

                if (eventsInCalendar(newEventsList, myCalendar)) { // checks if the events entered are in the Calendar

                    ArrayList<Event> events1 = eventNameToEventList(newEventsList, myCalendar);
                    myMemos.createMemo(events1, note1);
                    f.setVisible(false);
                    f.dispose();

                } else {
                    eventsText.setText("");
                    noteText.setText("");
                    incorrectCre.setBounds(200, 70, 200, 30);
                    f.setVisible(false);
                    f11.setSize(500, 300);
                    f11.add(enterEvents);
                    f11.add(note);
                    f11.add(incorrectCre);
                    f11.add(eventsText);
                    f11.add(noteText);
                    f11.add(submit);
                    makeVisible(f11);
                }

            }
        });

    }

    public void createEventsDisplay(CalendarManager sm, Calendar myCalendar, JFrame f) {

        JPanel gbPanel = new JPanel(new GridBagLayout());
        JTextField eventName = new JTextField();
        JTextField eventDuration = new JTextField();
        JTextField eventStartDate = new JTextField(); //DD-MM-YYYY format
        JTextField eventStartTime = new JTextField(); //HH:MM format
        JTextField eventMemo = new JTextField();

        eventName.setPreferredSize(new Dimension(100, 30));
        eventDuration.setPreferredSize(new Dimension(100, 30));
        eventStartDate.setPreferredSize(new Dimension(100, 30));
        eventStartTime.setPreferredSize(new Dimension(100, 30));
        eventMemo.setPreferredSize(new Dimension(100, 30));

        addGB(gbPanel, newEventName, 0, 0);
        addGB(gbPanel, eventName, 3, 0);
        addGB(gbPanel, newEventDuration, 0, 1);
        addGB(gbPanel, eventDuration, 3, 1);
        addGB(gbPanel, newEventMemo, 0, 2);
        addGB(gbPanel, eventMemo, 3, 2);
        addGB(gbPanel, newEventStartDate, 0, 3);
        addGB(gbPanel, eventStartDate, 3, 3);
        addGB(gbPanel, newEventStartTime, 0, 4);
        addGB(gbPanel, eventStartTime, 3, 4);
        addGB(gbPanel, submit2, 2, 5);

        f.setSize(500, 300);
        f.add(gbPanel);
        makeVisibleGB(f);

        submit2.addActionListener(ae -> {
            String name = eventName.getText();
            int duration = Integer.parseInt(eventDuration.getText());
            String m = eventMemo.getText();
            Memo memo = new Memo(m);
            LocalDateTime start = LocalDateTime.of(Integer.parseInt(eventStartDate.getText().substring(6)),
                    Integer.parseInt(eventStartDate.getText().substring(3, 5)),
                    Integer.parseInt(eventStartDate.getText().substring(0, 2)),
                    Integer.parseInt(eventStartTime.getText().substring(0, 2)),
                    Integer.parseInt(eventStartTime.getText().substring(3)));

            int endHour = Integer.parseInt(eventStartTime.getText().substring(0, 2)) + duration;

            LocalDateTime end = LocalDateTime.of(Integer.parseInt(eventStartDate.getText().substring(6)),
                    Integer.parseInt(eventStartDate.getText().substring(3, 5)),
                    Integer.parseInt(eventStartDate.getText().substring(0, 2)),
                    endHour,
                    Integer.parseInt(eventStartTime.getText().substring(3)));

            Event event = new Event(name, start, end);
            event.memos.add(memo);
            myCalendar.addEvent(event);

            //save myCalendar instance
            try {
                sm.saveToFile("user.ser");
            } catch (IOException e) {
                e.printStackTrace();
            }

            f.dispose();
        });
    }

    /**
     * Makes JFrame centered and visible with default layout and exit on close
     *
     * @param f: JFrame
     */
    public void makeVisible(JFrame f) {
        f.setLocationRelativeTo(null);
        f.setLayout(null);
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void makeVisibleGB(JFrame f) {
        f.setLocationRelativeTo(null);
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     * Adds component to the JPanel with proper constraints
     *
     * @param p:    JPanel object
     * @param comp: component to be added
     * @param x:    constraint's x-axis
     * @param y:    constraint's y-axis
     */
    private void addGB(JPanel p, Component comp, int x, int y) {

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(5, 5, 5, 5);
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.gridx = x;
        constraints.gridy = y;
        p.add(comp, constraints);

    }

    // returns a boolean value for whether a given array of events are in the Calendar or not
    public boolean eventsInCalendar(String[] events, Calendar myCalendar) {

        ArrayList<Event> calEvents = myCalendar.getMyEvents();

        for (int i = 0; i < events.length; i++) {
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

        for (int i = 0; i < array.length; i++) {
            String name = array[i];
            for (Event e : calEvents) {
                if (e.getEventName().equals(name)) {
                    events.add(e);
                }
            }
        }
        return events;
    }
}