package SmartStudents;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
public class Admin extends JFrame implements ActionListener{
    JLabel welcomelabel = new JLabel();

    private JTextField emailTextField;
    private JPasswordField passwordPasswordField;
    private JButton loginButton;
    private JButton backButton;

    Admin(){
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.CENTER,1000,40));

        welcomelabel.setText("Welcome Admin!");
        welcomelabel.setHorizontalAlignment(JLabel.CENTER);
        welcomelabel.setFont(new Font("Arial Black", Font.PLAIN, 30));
        panel.add(welcomelabel);

        panel.setLayout(new FlowLayout(FlowLayout.CENTER,1000,24));

        JLabel userLabel = new JLabel("Username");
        emailTextField = new JTextField(15);
        panel.add(userLabel);
        panel.add(emailTextField);

        JLabel passwordLabel = new JLabel("Password");
        passwordPasswordField = new JPasswordField(15);
        panel.add(passwordLabel);
        panel.add(passwordPasswordField);

        loginButton = new JButton("Log In");
        loginButton.addActionListener(this);
        loginButton.setFocusable(false);
        panel.add(loginButton);


        backButton = new JButton("Back");
        backButton.addActionListener(this);
        backButton.setFocusable(false);
        panel.add(backButton);


        
        ImageIcon image = new ImageIcon(getClass().getResource("ss.png"));
        this.setIconImage(image.getImage());
        this.setTitle("SmartStudent: Navigating Education Information");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setSize(450, 470);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.add(panel);
}
  @Override
  public void actionPerformed(ActionEvent e) {
    if (e.getSource() == loginButton) {
        // Get email and password from text fields
        String email = emailTextField.getText();
        String password = new String(passwordPasswordField.getPassword());

        // Process login here
        boolean loginSuccess = false;

        // Assuming you have a method to validate the email and password, called 'authenticateUser'
        if (authenticateUser(email, password)) {
            loginSuccess = true;
        }

        if (loginSuccess) {
            JOptionPane.showMessageDialog(this, "Login Successful!");
            new AdminView();
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Invalid Email or Password!", "Login Failed", JOptionPane.ERROR_MESSAGE);
        }
        
    } else if (e.getSource() == backButton) {
        WelcomeUser wc1 = new WelcomeUser();
        wc1.user();
        this.dispose();
        // Handle backbutton click;

    } 
}

// This is a placeholder method to authenticate the user
public boolean authenticateUser(String email, String password) {
    if (email.equals("admin") && password.equals("admin")) {
        return true;
    }
    return false;
}
}

