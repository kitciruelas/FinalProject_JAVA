package SmartStudents;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class AdminInterface extends JFrame {
    private JTextField studentIDField;
    private JTextArea infoArea;
    private JButton searchButton;
    private JButton delButton;
    private JButton cancelButton;
    private int studentID;

    public AdminInterface() {
        // Components
        studentIDField = new JTextField(10);
        infoArea = new JTextArea(10, 20);
        infoArea.setFont(new Font("Arial", Font.PLAIN, 15));
        infoArea.setBorder(new EmptyBorder(20, 10, 0, 0));
        searchButton = new JButton("Search");
        searchButton.setFocusable(false);

        delButton = new JButton("Delete");
        delButton.setFocusable(false);
        delButton.setForeground(Color.RED);
        delButton.setBorderPainted(false);
        delButton.setFocusPainted(false);
        delButton.setContentAreaFilled(false);
        delButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        cancelButton = new JButton("Cancel");
        cancelButton.setFocusable(false);

        // Button action listeners
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get the student ID from the text field
                try {
                    studentID = Integer.parseInt(studentIDField.getText());
                    Studentinfo("fillup_form", studentID);
                    nextStudentinfo("next_fillup_form", studentID);

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(AdminInterface.this, "Invalid Student ID");
                }
            }
        });
        // DELETE STUDENT
        delButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int option = JOptionPane.showConfirmDialog(
                        AdminInterface.this,
                        "Are you sure you want to delete this student?",
                        "Confirmation",
                        JOptionPane.YES_NO_OPTION);

                if (option == JOptionPane.YES_OPTION) {
                    // User clicked "Yes," proceed with deletion logic
                    deleteStudent();
                    del2ndStudent();
                    delAccStudent();
                }
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Close the window when the Cancel button is clicked
                new AdminView();
                dispose();
            }
        });

        ImageIcon image = new ImageIcon(getClass().getResource("ss.png"));
        setIconImage(image.getImage());

        // JFrame settings
        setTitle("Admin Search and Delete");
        setSize(600, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        // Layout settings
        JPanel northPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        northPanel.add(new JLabel("Student ID: "));
        northPanel.add(studentIDField);
        northPanel.add(searchButton);
        northPanel.add(delButton);
        

        JPanel southPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        southPanel.add(cancelButton);

        add(northPanel, BorderLayout.NORTH);
        add(new JScrollPane(infoArea), BorderLayout.CENTER);
        add(southPanel, BorderLayout.SOUTH);
        setLocationRelativeTo(null);

        setVisible(true);
    }

    private void Studentinfo(String sql, int studentID) {
        try {
            Connection connection = getConnection();
            String query = "SELECT * FROM fillup_form WHERE studentID=?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, studentID);

                ResultSet resultSet = preparedStatement.executeQuery();
                StringBuilder result = new StringBuilder();

                if (resultSet.next()) {
                    result.append("Name: ").append(resultSet.getString("firstName")).append(" ");
                    result.append("").append(resultSet.getString("middle")).append(" ");
                    result.append("").append(resultSet.getString("lastname")).append("\n");
                    result.append("Age: ").append(resultSet.getString("age")).append("\n");
                    result.append("Birthday: ").append(resultSet.getString("d_o_b")).append("\n");
                    result.append("Address: ").append(resultSet.getString("address")).append("\n");
                    result.append("Phone number: ").append(resultSet.getString("Phone_num")).append("\n");
                } 

                infoArea.setText(result.toString());
            } finally {
                connection.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void nextStudentinfo(String tableName, int studentID) {
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
                } else {
                    result.append("No additional details found for the provided student ID.");
                }

                infoArea.append("\n" + result.toString());
            } finally {
                connection.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Connection getConnection() {
        // TODO: Implement a better way to manage your database credentials
        String url = "jdbc:mysql://localhost:3306/smartstudents";
        String username = "root";
        String password = "";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    // AYUSIN KO PA TO, Delete fname,lname,middle,age
    //srcode depart and year
     private void deleteStudent() {
        try {
            Connection connection = getConnection();

            // Delete from the first table (fillup_form)
            String deleteQueryFillupForm = "DELETE FROM fillup_form WHERE studentID=?";
            try (PreparedStatement preparedStatementFillupForm = connection.prepareStatement(deleteQueryFillupForm)) {
                preparedStatementFillupForm.setInt(1, studentID);
                int rowsAffectedFillupForm = preparedStatementFillupForm.executeUpdate();

             
                    if (rowsAffectedFillupForm > 0) {
                        JOptionPane.showMessageDialog(
                                AdminInterface.this,
                                "Student deleted successfully.",
                                "Success",
                                JOptionPane.INFORMATION_MESSAGE);
                        // Clear the infoArea after deletion
                        infoArea.setText("");
                    } else {
                        JOptionPane.showMessageDialog(
                                AdminInterface.this,
                                "Failed to delete student.",
                                "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                
            } finally {
                connection.close();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

}
 private void del2ndStudent() {
        try {
            Connection connection = getConnection();

            // Delete from the first table (fillup_form)
          

                // Delete from the second table (next_fillup_form)
                String deleteQueryNextFillupForm = "DELETE FROM next_fillup_form WHERE student_ID=?";
                try (PreparedStatement preparedStatementNextFillupForm = connection.prepareStatement(deleteQueryNextFillupForm)) {
                    preparedStatementNextFillupForm.setInt(1, studentID);
                    int rowsAffectedNextFillupForm = preparedStatementNextFillupForm.executeUpdate();

                    if ( rowsAffectedNextFillupForm > 0) {
                        // Clear the infoArea after deletion
                        infoArea.setText("");
                    
                    }
                
            } finally {
                connection.close();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

} private void delAccStudent() {
        try {
            Connection connection = getConnection();

            // Delete from the first table (fillup_form)
          

                // Delete from the second table (next_fillup_form)
                String deleteQueryAcc = "DELETE FROM signup_form WHERE student_signupID=?";
                try (PreparedStatement preparedStatementdelAcc = connection.prepareStatement(deleteQueryAcc)) {
                    preparedStatementdelAcc.setInt(1, studentID);
                    int rowsAffectedNextFillupForm = preparedStatementdelAcc.executeUpdate();

                    if ( rowsAffectedNextFillupForm > 0) {
                        // Clear the infoArea after deletion
                        infoArea.setText("");
                    
                    }
                
            } finally {
                connection.close();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

} 

}


