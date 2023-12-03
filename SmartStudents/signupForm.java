package SmartStudents;//AYUSIN KO PA TO!

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
public class signupForm extends JFrame implements ActionListener{

private JLabel lblName, lblEmail, lblPassword, lblConfirmPassword,txtDisplay;
private JTextField txtName, txtEmail;
private JPasswordField txtPassword, txtConfirmPassword;
private JButton btnSignUp, btnCancel;

signupForm() {
    // Set the title of the frame
    super("Student Sign Up Form");

    // Set the layout of the frame
    //setLayout(new GridLayout(5, 2, 5, 5));
    setLayout(new FlowLayout(FlowLayout.CENTER,300,10));

    // Create the labels
    lblName = new JLabel("Name");
    lblEmail = new JLabel("Email");
    lblPassword = new JLabel("Password");
    lblConfirmPassword = new JLabel("Confirm Password");

    // Create the text fields
    txtName = new JTextField(15);
    txtEmail = new JTextField(15);
    txtPassword = new JPasswordField(15);
    txtConfirmPassword = new JPasswordField(15);

    lblName.setLabelFor(txtName);
    lblEmail.setLabelFor(txtEmail);
    lblPassword.setLabelFor(txtPassword);
    lblConfirmPassword.setLabelFor(txtConfirmPassword);

    txtDisplay = new JLabel(" ");
    add(txtDisplay);

    // Create the buttons
    btnSignUp = new JButton("Sign In");
    btnSignUp.setFocusable(false);
    
    btnCancel = new JButton("Cancel");
    btnCancel.setFocusable(false);

    // Add the labels and text fields to the frame
    add(lblName);
    add(txtName);
    add(lblEmail);
    add(txtEmail);
    add(lblPassword);
    add(txtPassword);
    add(lblConfirmPassword);
    add(txtConfirmPassword);

    // Add the buttons to the frame
    add(btnSignUp);
    add(btnCancel);

    // Add an action listener to the buttons
    btnSignUp.addActionListener(this);
    btnCancel.addActionListener(this);

    ImageIcon image = new ImageIcon(getClass().getResource("ss.png"));
    setIconImage(image.getImage());

    // Set the size of the frame
    setSize(500, 400);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setResizable(false);

    // Set the location of the frame
    setLocationRelativeTo(null);

    // Make the frame visible
    setVisible(true);
}

@Override
public void actionPerformed(ActionEvent e) {
    // Get the source of the event
    Object source = e.getSource();

    // If the source is the sign up button
    if (source == btnSignUp) {
        // Get the text from the text fields
        String name = txtName.getText().trim();
        String email = txtEmail.getText().trim();
        String password = new String(txtPassword.getPassword()).trim();
        String confirmPassword = new String(txtConfirmPassword.getPassword()).trim();

        // Check if passwords match
        if (!password.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(this, "Passwords do not match. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Database connection and insertion
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/smartstudents", "root", "")) {
            String sql = "INSERT INTO signup_form (name, email, password) VALUES (?, ?, ?)";

            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, name);
                pstmt.setString(2, email);
                pstmt.setString(3, password);

                int rowsAffected = pstmt.executeUpdate();

                if (rowsAffected > 0) {
                    // Inform the user that the operation was successful
                    JOptionPane.showMessageDialog(null, "Signup successfully!");
                    new StudentInterface();
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Failed to register user. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (SQLException ex) {
            // Handle SQL exception
            JOptionPane.showMessageDialog(null, "SQL exception: " + ex.getMessage());
        }
    } else if (source==btnCancel) {
        new Student();
        dispose();
        
    } 
}

    }



