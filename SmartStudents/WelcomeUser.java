package SmartStudents;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
public class WelcomeUser implements ActionListener{

    JFrame frame = new JFrame();
    JLabel welcomelabel = new JLabel();
    JLabel label = new JLabel();
    JButton adminbutton = new JButton();
    JButton studentbutton = new JButton();
    JLabel wlabel = new JLabel();
    JButton exitbutton = new JButton();

    public void user(){
        JPanel Wpanel = new JPanel();
        JPanel panel = new JPanel();

        panel.setLayout(new FlowLayout(FlowLayout.CENTER,1000,55));

        welcomelabel.setText("Welcome User!");
        welcomelabel.setBounds(0,0,450,0);
        welcomelabel.setFont(new Font("Arial Black", Font.PLAIN, 30));
        Wpanel.add(welcomelabel);

        label.setText("Select how you want to use SmartStudent");
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setFont(new Font("Arial", Font.PLAIN, 18));
        panel.add(label);

        adminbutton.setText("ADMIN");
        adminbutton.setHorizontalAlignment(JLabel.CENTER);
        adminbutton.addActionListener(this);
        adminbutton.setBounds(100, 200, 150, 250);
        adminbutton.setFocusable(false);
        panel.add(adminbutton);

        studentbutton.setText("STUDENT");
        studentbutton.setHorizontalAlignment(JLabel.CENTER);
        studentbutton.addActionListener(this);
        studentbutton.setBounds(100, 200, 150, 250);
        studentbutton.setFocusable(false);
        panel.add(studentbutton);

       // Epanel.setLayout(new FlowLayout(FlowLayout.CENTER,1000,30));

        
        wlabel.setText("<html> <center>Welcome to SmartStudent, your all-in-one solution <br/>for seamlessly managing and navigating education information</html>");
        wlabel.setHorizontalAlignment(JLabel.CENTER);
        wlabel.setFont(new Font("Arial", Font.PLAIN, 12));
        panel.add(wlabel);

        JPanel Epanel = new JPanel();

        exitbutton.setText("EXIT");
        exitbutton.setHorizontalAlignment(JLabel.CENTER);
        exitbutton.setForeground(Color.RED);
        exitbutton.setBorderPainted(false);
        exitbutton.setFocusPainted(false);
        exitbutton.setOpaque(false);
        exitbutton.addActionListener(this);
        exitbutton.setBounds(10, 20, 50, 50);
        exitbutton.setFocusable(false);
        Epanel.add(exitbutton);

//sample
        ImageIcon backgroundImage = new ImageIcon(getClass().getResource("smart.png")); // Replace with the actual path
        Image scaledImage = backgroundImage.getImage().getScaledInstance(450, 350, Image.SCALE_SMOOTH);
        ImageIcon scaledBackgroundImage = new ImageIcon(scaledImage);

        // Create a JLabel with the resized background image
        JLabel backgroundLabel = new JLabel(scaledBackgroundImage);
        backgroundLabel.setSize(450, 350);

        ImageIcon image = new ImageIcon(getClass().getResource("ss.png"));
        frame.setIconImage(image.getImage());
        frame.setTitle("SmartStudent: Navigating Education Information");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setSize(450,470);
        frame.add(panel);
        frame.add(Epanel, BorderLayout.SOUTH);
        frame.add(Wpanel, BorderLayout.NORTH);
      //   frame.add(backgroundLabel);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);

        
    }

    public static void main (String[] args){

        WelcomeUser wcuser = new WelcomeUser();
        wcuser.user();    
        
        }
        
    @Override
    public void actionPerformed(ActionEvent e) {
       if (e.getSource()==adminbutton){
        frame.dispose();
        new Admin();// NEW CLASS WINDOW
       }
       else if (e.getSource()==studentbutton){
        frame.dispose();
        new Student();
       }
       else if (e.getSource()==exitbutton){
        System.exit(0);
       }
    }
}
