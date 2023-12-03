package SmartStudents;

import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import javax.swing.*;

public class NextStudentInterface extends JFrame implements ActionListener{

  private JLabel srCodeLabel, deptLabel, courseLabel, yearLabel;
  private JTextField srCodeField, deptField, courseField, yearField;
  private JButton submitButton;


    NextStudentInterface(){

      //CREATE ACC STUDENT
      JLabel newlabel = new JLabel();
        JPanel panel = new JPanel();
        newlabel.setText("New Students? Please fill up the form to continue");
        newlabel.setText("<html><br/>New Students? Please fill up the form to continue</html>");
        newlabel.setFont(new Font("Arial Black", Font.PLAIN, 15));
        newlabel.setHorizontalAlignment(JLabel.CENTER);
        panel.add(newlabel);

        srCodeLabel = new JLabel("SR-Code");
        deptLabel = new JLabel("Department");
        courseLabel = new JLabel("Course");
        yearLabel = new JLabel("Year");

        srCodeField = new JTextField(15);
        deptField = new JTextField(15);
        courseField = new JTextField(15);
        yearField = new JTextField(15);

        submitButton = new JButton("Submit");
        submitButton.setFocusable(false);
       
        // Set up event listeners
        submitButton.addActionListener(this);
      
        // Set up layout
        JPanel panel1 = new JPanel();
        panel1.setLayout(new FlowLayout(FlowLayout.CENTER,400,10));
       // panel1.setLayout(new GridLayout(10, 15));
        panel1.add(srCodeLabel);
        panel1.add(srCodeField);
        panel1.add(deptLabel);
        panel1.add(deptField);
        panel1.add(courseLabel);
        panel1.add(courseField);
        panel1.add(yearLabel);
        panel1.add(yearField);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(submitButton);

        add(BorderLayout.NORTH, newlabel);
        add(panel1, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        ImageIcon image = new ImageIcon(getClass().getResource("ss.png"));
        setIconImage(image.getImage());

      setTitle("Student Interface");
      setSize(700, 450);
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      setResizable(false);
      setLocationRelativeTo(null);
      setVisible(true);
    }
    


    @Override
    
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
      if(e.getSource()==submitButton){
         String srcode = srCodeField.getText();
        String depart = deptField.getText();
        String course = courseField.getText();
        String year = yearField.getText();

        // Database connection and insertion
        Connection conn = null;
        try {
            String url = "jdbc:mysql://localhost:3306/";
            String dbName = "smartstudents";
            String driver = "com.mysql.cj.jdbc.Driver";
            String userName = "root";
            String dbPassword = "";

            Class.forName(driver);
            conn = DriverManager.getConnection(url + dbName, userName, dbPassword);

            String sql = "INSERT INTO next_fillup_form (srcode, depart, course, year) VALUES (?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, srcode);
            pstmt.setString(2, depart);
            pstmt.setString(3, course);
            pstmt.setString(4, year);

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                JOptionPane.showMessageDialog(this, "Student information added successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);

                new Student();
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to add student information. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                    System.out.println("Disconnected from the database");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
      
    } 
    }
  }

