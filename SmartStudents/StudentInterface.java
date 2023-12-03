package SmartStudents;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;

public class StudentInterface extends JFrame implements ActionListener{

 private JLabel lastnameLabel, fnameLabel,miLabel, ageLabel, dobLabel, addressLabel, phoneLabel;
 private JTextField lastnameField,fnameField,miField, ageField, dobField, addressField, phoneField;
 private JButton nextButton, backButton;

    StudentInterface (){

      //CREATE ACC STUDENT
      JLabel newlabel = new JLabel();
        JPanel panel = new JPanel();
        newlabel.setText("<html><br/>New Students? Please fill up the form to continue</html>");
        newlabel.setFont(new Font("Arial Black", Font.PLAIN, 15));
        newlabel.setHorizontalAlignment(JLabel.CENTER);
        panel.add(newlabel);

        lastnameLabel = new JLabel("Lastname");
        fnameLabel = new JLabel("Firstname");
        miLabel = new JLabel("Middlename");
        ageLabel = new JLabel("Age");
        dobLabel = new JLabel("Date of Birth");
        addressLabel = new JLabel("Address");
        phoneLabel = new JLabel("Phone Number");

        lastnameField = new JTextField(15);
        fnameField = new JTextField(15);
        miField = new JTextField(15);
        ageField = new JTextField(15);
        dobField = new JTextField(15);
        addressField = new JTextField(15);
        phoneField = new JTextField(15);

        nextButton = new JButton("Next");
        nextButton.setFocusable(false);
        backButton = new JButton("Back");
        backButton.setFocusable(false);

        // Set up event listeners
        nextButton.addActionListener(this);
        backButton.addActionListener(this);

        // Set up layout
        JPanel panel1 = new JPanel();
        panel1.setLayout(new FlowLayout(FlowLayout.CENTER,400,10));
        panel1.add(fnameLabel);
        panel1.add(fnameField);
        panel1.add(lastnameLabel);
        panel1.add(lastnameField);
        panel1.add(miLabel);
        panel1.add(miField);
        panel1.add(ageLabel);
        panel1.add(ageField);
        panel1.add(dobLabel);
        panel1.add(dobField); 
        panel1.add(addressLabel);
        panel1.add(addressField);
        panel1.add(phoneLabel);
        panel1.add(phoneField);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(backButton);
        buttonPanel.add(nextButton);

        add(BorderLayout.NORTH, newlabel);
        add(panel1, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        
        ImageIcon image = new ImageIcon(getClass().getResource("ss.png"));
        setIconImage(image.getImage());

        setTitle("Student Interface");
        setSize(700, 520);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true); 
    }    
    @Override
public void actionPerformed(ActionEvent e) {
    if (e.getSource() == nextButton) {
        // Check if text fields are empty
        String firstname = fnameField.getText();
        String lastname = lastnameField.getText();
        String middle = miField.getText();
        String age = ageField.getText();
        String d_o_b = dobField.getText();
        String address = addressField.getText();
        String phone_num = phoneField.getText();

        // Check if age text field is empty
        if (!age.isEmpty()) {
            int ageNumber = Integer.parseInt(age);

            try {
                // Open connection
                Class.forName("com.mysql.cj.jdbc.Driver");

                try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/smartstudents", "root", "")) {
                    // Use PreparedStatement to prevent SQL injection
                    String sql = "INSERT INTO fillup_form (firstname, lastname, middle, age, d_o_b, address, phone_num) VALUES (?, ?, ?, ?, ?, ?, ?)";
                    try (PreparedStatement pstmt = con.prepareStatement(sql)) {
                        pstmt.setString(1, firstname);
                        pstmt.setString(2, lastname);
                        pstmt.setString(3, middle);
                        pstmt.setInt(4, ageNumber);
                        pstmt.setString(5, d_o_b);
                        pstmt.setString(6, address);
                        pstmt.setString(7, phone_num);

                        // Execute update
                        pstmt.executeUpdate();

                        // Inform the user that the operation was successful
                        JOptionPane.showMessageDialog(this, "Proceed next student information.", "Success", JOptionPane.INFORMATION_MESSAGE);
                        new NextStudentInterface();
                        dispose();
                    }
                }
            } catch (ClassNotFoundException | SQLException ex) {
                // Handle exceptions
                JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(null, "Please enter all fields");
        }
    } else if (e.getSource() == backButton) {
        // Additional code for handling backButton action...
        new Student();
        dispose();;
    }
}

}