import javax.swing.*;
import javax.swing.JOptionPane;
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

public class Menus extends JFrame{

    private static JFrame f = new JFrame("Account");
    private static JFrame f2 = new JFrame("Log In");
    private static JFrame f3 = new JFrame("New Feature is coming!");
    private static JFrame f4 = new JFrame("Incorrect Credentials!");


    JButton yes = new JButton("Yes");
    JButton no = new JButton("No");
    JButton submit = new JButton("Submit");

    JLabel existingAcc = new JLabel();
    JLabel userLabel = new JLabel("Username:");
    JLabel pswdLabel = new JLabel("Password:");
    JLabel newFeature = new JLabel("You can create a new account in phase 2!");
    JLabel incorrectCre = new JLabel("Please try again!");

    public Menus(String type) throws IOException {
        HashMap<String, String> users = getUsers(); //Creating a map of all users in the csv file
        if(type == "a"){
            accountButton(f);
        }
        else if(type == "l"){
            accountLogIn(users, f2);
        }
    }

    public void accountButton(JFrame f) throws IOException{
        HashMap<String, String> users = getUsers(); //Creating a map of all users in the csv file

        yes.setBounds(70,100,90, 30);
        no.setBounds(170, 100, 90, 30);

        existingAcc.setText("Do you have an existing account?");
        existingAcc.setBounds(10, 10, 300, 100);

        f.setSize(300,300);
        f.add(existingAcc);
        f.add(yes);
        f.add(no);
        f.setLocationRelativeTo(null);
        f.setLayout(null);
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

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

    public void createNewAccount(HashMap<String, String> users, JFrame f){
        f.setVisible(false);
        f = new JFrame("New Feature is Coming!");
        f.setSize(400, 300);
        newFeature.setBounds(50, 100, 300, 30);
        f.add(newFeature);
        f.setLocationRelativeTo(null);
        f.setLayout(null);
        f.setVisible(true);
        // prompt create new user
    }

    public void accountLogIn(HashMap<String, String> users, JFrame f){

        JTextField userText = new JTextField();
        JTextField pswdText = new JTextField();

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
        f.setLocationRelativeTo(null);
        f.setLayout(null);
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        submit.addActionListener(ae -> {
            String user = userText.getText();
            String pswd = pswdText.getText();

            if(login(user, pswd, users)){
                f.setVisible(false);
                f.dispose();
                f4.setVisible(false);
                f4.dispose();
                System.out.println("Correct info!");
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
                f4.setLocationRelativeTo(null);
                f4.setLayout(null);
                f4.setVisible(true);
                f4.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            }
        });
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
