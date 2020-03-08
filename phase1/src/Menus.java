import javax.swing.*;
import javax.swing.JOptionPane;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
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
    JButton createMemo = new JButton("Create memo");
    JButton createIAlert = new JButton("Create individual alert:");
    JButton createFAlert = new JButton("Create frequent alert:");
    JButton bAllAlert = new JButton("View all alerts");
    JButton findEventByDate = new JButton("Find Event by date");
    JButton findEventByTag = new JButton("Find Event by tag");
    JButton findEventByMemo = new JButton("Find Event by memo");

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
    JLabel date = new JLabel("Date (YYYY-MM-DD format):");
    JLabel message = new JLabel("Message:");
    JLabel tag = new JLabel("Tag:");
    JLabel memoid = new JLabel("Memo id number:");

    public Menus() throws IOException {
        HashMap<String, String> users = getUsers(); //Creating a map of all users in the csv file
        accountButton(f);
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
    public void accountButton(JFrame f) throws IOException {
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
            accountLogIn(users, f2); // prompt user to log in
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
    public void accountLogIn(HashMap<String, String> users, JFrame f) {
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
                    mainDisplay(user, f5);
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
     * @param user: specific user that logged in
     * @param f:    pre-fixed JFrame
     */
    public void mainDisplay(String user, JFrame f) throws IOException, ClassNotFoundException {

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

        if (myCalendar.getCurrentEvents() != null ) {
            for (Event item : myCalendar.getCurrentEvents()) {
                JLabel label = new JLabel(item.toString());
                addGB(userPanel, label, 0, 4);
            }
        } else {
            JLabel label = new JLabel("No current events");
            userPanel.add(label);
            addGB(userPanel, label, 0, 4);
        }

        addGB(userPanel, emptySpace, 0, 2);
        addGB(userPanel, pastEvents, 0, 5);

        if (myCalendar.getPastEvents() != null) {
            for (Event item : myCalendar.getPastEvents()) {
                JLabel label = new JLabel(item.toString());
                userPanel.add(label);
                addGB(userPanel, label, 0, 6);
            }
        } else {
            JLabel label = new JLabel("No past events");
            userPanel.add(label);
            addGB(userPanel, label, 0, 6);
        }

        addGB(userPanel, emptySpace, 0, 2);
        addGB(userPanel, futureEvents, 0, 7);

        if (myCalendar.getFutureEvents() != null) {
            for (Event item : myCalendar.getFutureEvents()) {
                JLabel label = new JLabel(item.toString());
                userPanel.add(label);
                addGB(userPanel, label, 0, 8);
            }
        } else {
            JLabel label = new JLabel("No future events");
            userPanel.add(label);
            addGB(userPanel, label, 0, 8);
        }

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
        });
    }

    public void findEventDateDisplay(String user, Calendar myCalendar, JFrame f) {
        JTextField dateText = new JTextField();

        submit.setBounds(200, 150, 90, 30);
        date.setBounds(50, 100, 70, 30);
        dateText.setBounds(120, 100, 100, 30);

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

            findEventsHelperfunc(events, user);
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

            findEventsHelperfunc(events, user);
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
                findEventsHelperfunc(events, user);

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
    private void findEventsHelperfunc(ArrayList<Event> events, String user) {
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
    }

    public void viewAlertsDisplay(String user, Calendar calendar, JFrame f) {

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
            createIAlertDisplay(calendar, f12);
        });

        createFAlert.addActionListener(ae -> {
            //I tried the try-catch block but it shows error that exception is never thrown
            f.dispose();
            createFAlertDisplay(calendar, f13);
        });
    }

    public void createIAlertDisplay(Calendar myCalendar, JFrame f) {

        JPanel display = new JPanel();
        JLabel create_individual_alert = new JLabel("Create Individual Alert");

        // f.add(new JLabel("Enter: (1) Event Name, (2) Alert Message, (3) Alert Date in yyyy-mm-dd: HH:mm"));
        JTextField eventTxt = new JTextField();
        JTextField msgTxt = new JTextField();
        JTextField dateTxt = new JTextField();
        JLabel enterEvent = new JLabel("Event:");
        JLabel enterMessage = new JLabel("Message");
        JLabel enterDate = new JLabel("Date");

        //display field:500*300
        f.setSize(500, 300);
        create_individual_alert.setBounds(0, 0, 100, 30);
        enterEvent.setBounds(25, 150, 25, 30);
        eventTxt.setBounds(50, 150, 60, 30);
        enterMessage.setBounds(120, 150, 30, 30);
        msgTxt.setBounds(150, 150, 75, 30);
        enterDate.setBounds(225, 150, 25, 30);
        dateTxt.setBounds(250, 150, 25, 30);
        submit.setBounds(200, 200, 90, 30);

        JComponent[] arr = new JComponent[]{create_individual_alert, enterEvent, eventTxt, enterMessage, msgTxt, enterDate, dateTxt, submit};
        for (JComponent i : arr) {
            display.add(i);
        }

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

    public void createFAlertDisplay(Calendar myCalendar, JFrame f) {
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
        for (JComponent i : arr) {
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
                if (duration != null)
                    myCalendar.addFrequentAlert(myCalendar.getEvent(name), msg, duration);
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

    public void createEventsDisplay(String user, JFrame f) {
        JPanel gbPanel = new JPanel(new GridBagLayout());
        JTextField eventName = new JTextField();
        JTextField eventDuration = new JTextField();
        JTextField eventStartDate = new JTextField(); //DD-MM-YYYY format
        JTextField eventStartTime = new JTextField(); //HH:MM format
        JTextField eventMemo = new JTextField();

        submit.setBounds(200, 150, 90, 30);
        newEventName.setBounds(20, 100, 100, 30);
        newEventDuration.setBounds(200, 100, 200, 30);
        eventName.setBounds(120, 100, 100, 30);
        eventDuration.setBounds(370, 100, 100, 30);

        f.setSize(500, 300);
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
                    Integer.parseInt(eventStartDate.getText().substring(0, 2)),
                    Integer.parseInt(eventStartTime.getText().substring(0, 2)),
                    Integer.parseInt(eventStartTime.getText().substring(3)));
            String memo = eventMemo.getText();
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