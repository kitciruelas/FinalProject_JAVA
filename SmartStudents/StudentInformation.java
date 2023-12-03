package SmartStudents;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class StudentInformation extends JFrame {
    private JTextArea infoArea;
    private int studentID;  // Variable to store the ID of the logged-in student

    // Updated constructor to accept studentID as a parameter
    public StudentInformation(int loggedInStudentID) {
        super("Student Information");
        this.studentID = loggedInStudentID; // Set the class variable using the provided parameter

        // Components for displaying info
        infoArea = new JTextArea();
        infoArea.setBorder(new EmptyBorder(20, 10, 0, 0));
        infoArea.setFont(new Font("Arial", Font.PLAIN, 15));
        infoArea.setEditable(false);

        // Layout for displaying info
        JPanel infoPanel = new JPanel(new BorderLayout());
        infoPanel.add(new JScrollPane(infoArea), BorderLayout.CENTER);
        studentInfo("fillup_form", this.studentID);
        myinfo("next_fillup_form",this.studentID);

        // Buttons
       // JButton updateButton = new JButton("Update");
        JButton logoutButton = new JButton("Log out");

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
      //  buttonPanel.add(updateButton);
        buttonPanel.add(logoutButton);

        // Set up the main layout
        add(infoPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Button actions

      /*   updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Placeholder: Add code to update information
                JOptionPane.showMessageDialog(StudentInformation.this, "Updating Information");
            }
        });
        */

        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Placeholder: Add code to go back
                new Student();  // Assuming Student is another class to handle user-related actions
                dispose();
            }
        });

        ImageIcon image = new ImageIcon(getClass().getResource("ss.png"));
        setIconImage(image.getImage());

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setSize(600, 400);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    
    private void studentInfo(String sql, int studentID) {
        try {
            Connection connection = getConnection();
            String query = "SELECT * FROM fillup_form WHERE studentID=?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, studentID);

                // Debugging: Print SQL Query and studentID
                System.out.println("SQL Query: " + query);
                System.out.println("studentID: " + studentID);

                ResultSet resultSet = preparedStatement.executeQuery();
                StringBuilder result = new StringBuilder();

                // Assuming that 'firstName', 'lastName', 'course', and 'email' are the columns of interest
                if (resultSet.next()) { // If there's a record
                    result.append("     Welcome, ").append(resultSet.getString("firstName")).append("\n");

                    result.append("\nName: ").append(resultSet.getString("firstName")).append(" ");
                    result.append("").append(resultSet.getString("middle")).append(" ");
                    result.append("").append(resultSet.getString("lastname")).append("\n");
                    result.append("Age: ").append(resultSet.getString("age")).append("\n");
                    result.append("Birthday: ").append(resultSet.getString("d_o_b")).append("\n");
                    result.append("Address: ").append(resultSet.getString("address")).append("\n");
                    result.append("Phone number: ").append(resultSet.getString("Phone_num")).append("\n");

                } else {
                    result.append("No records found for the provided student ID.");
                }

                // Debugging: Print Fetched Data
                System.out.println("Student Data:");
                System.out.println(result.toString());

                infoArea.setText(result.toString());
            } finally {
                connection.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void myinfo(String tableName, int studentID) {
        try {
            Connection connection = getConnection();
            String query = "SELECT srcode, depart, course, year FROM next_fillup_form WHERE student_ID=?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, studentID);

                ResultSet resultSet = preparedStatement.executeQuery();
                StringBuilder result = new StringBuilder();

                if (resultSet.next()) {
                    result.append("SRCODE: ").append(resultSet.getString("srcode")).append("\n");
                    result.append("Department: ").append(resultSet.getString("depart")).append("\n");
                    result.append("Course: ").append(resultSet.getString("course")).append("\n");
                    result.append("Year: ").append(resultSet.getString("year")).append("\n");
                }
                 infoArea.append("\n" + result.toString());
            } finally {
                connection.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Connection getConnection() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3306/smartstudents";
        String username = "root";
        String password = "";
        return DriverManager.getConnection(url, username, password);
    }

}