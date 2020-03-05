import javax.swing.*;
import javax.swing.JOptionPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class Menus {

    public Menus(String type){

        if(type == "a"){
            accountButton();
        }
    }

    public void accountButton(){
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
        b1.addActionListener(arg0 -> {
            System.out.println("yes!");
            // prompt log in
            accountLogIn();
            System.exit(0);
        });
        b2.addActionListener(e -> {
            System.out.println(("No!"));
            // prompt create new user
            System.exit(0);
        });



    }

    public void accountLogIn(){
        String username;
        String password;
        

    }






}

