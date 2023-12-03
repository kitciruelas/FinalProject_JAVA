package SmartStudents;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class forgettPassword extends JFrame {
    private JTextField emailField;
    private JPasswordField oldPasswordField;
    private JPasswordField newPasswordField;
    private JPasswordField confirmPasswordField;
    private JCheckBox showPasswordCheckbox;
    private JFrame frame;

    public forgettPassword() {
        createUI();
    }

    private void createUI() {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 1000, 20));

        frame = new JFrame("Forget Password");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(450, 490);
        frame.setLocationRelativeTo(null);

        JLabel emailLabel = new JLabel("Email");
        emailField = new JTextField(20);

        JLabel oldPasswordLabel = new JLabel("Old Password");
        oldPasswordField = new JPasswordField(20);

        JLabel newPasswordLabel = new JLabel("New Password");
        newPasswordField = new JPasswordField(20);

        JLabel confirmPasswordLabel = new JLabel("Confirm Password");
        confirmPasswordField = new JPasswordField(20);

        showPasswordCheckbox = new JCheckBox("Show Password");
        showPasswordCheckbox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (showPasswordCheckbox.isSelected()) {
                    newPasswordField.setEchoChar((char) 0);
                    confirmPasswordField.setEchoChar((char) 0);
                } else {
                    newPasswordField.setEchoChar('*');
                    confirmPasswordField.setEchoChar('*');
                }
            }
        });

        JButton submitButton = new JButton("Submit");
        submitButton.setFocusable(false);
        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handleSubmit();
            }
        });

        JButton cancelButton = new JButton("Cancel");
        cancelButton.setFocusable(false);
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new Student();
                frame.dispose();
                        }
        });

        JLabel statusLabel = new JLabel(" ");

        ImageIcon image = new ImageIcon(getClass().getResource("ss.png"));
        frame.setIconImage(image.getImage());

        panel.add(emailLabel);
        panel.add(emailField);
        panel.add(oldPasswordLabel);
        panel.add(oldPasswordField);
        panel.add(newPasswordLabel);
        panel.add(newPasswordField);
        panel.add(confirmPasswordLabel);
        panel.add(confirmPasswordField);
        panel.add(showPasswordCheckbox);
        panel.add(submitButton);
        panel.add(cancelButton);
        panel.add(statusLabel);
        

        frame.getContentPane().add(panel);
        frame.setVisible(true);
    }

    private void handleSubmit() {
        String email = emailField.getText();
        String oldPassword = new String(oldPasswordField.getPassword());
        String newPassword = new String(newPasswordField.getPassword());
        String confirmPassword = new String(confirmPasswordField.getPassword());
    
        // Check if any of the fields are empty
        if (!email.isEmpty() && !oldPassword.isEmpty() && !newPassword.isEmpty() && !confirmPassword.isEmpty()) {
            try {
                // Open connection
                Class.forName("com.mysql.cj.jdbc.Driver");
    
                try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/smartstudents", "root", "")) {
                    // Use PreparedStatement to prevent SQL injection
                    String query = "UPDATE signup_form SET password = ? WHERE email = ?";
                    try (PreparedStatement pstmt = con.prepareStatement(query)) {
                        pstmt.setString(1, newPassword);
                        pstmt.setString(2, email);
    
                        // Execute update
                        int rowsAffected = pstmt.executeUpdate();
    
                        if (rowsAffected > 0) {
                            // Inform the user that the password was updated successfully
                            JOptionPane.showMessageDialog(this, "Password updated successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                            new Student();
                            dispose();
                            
                        } else {
                            JOptionPane.showMessageDialog(this, "Invalid email or old password.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            } catch (ClassNotFoundException | SQLException ex) {
                // Handle exceptions
                JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Please enter all fields.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}    