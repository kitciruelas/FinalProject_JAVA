package SmartStudents;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;

public class Student extends  JFrame implements ActionListener{

    JLabel welcomelabel = new JLabel();

    private JTextField emailTextField;
    private JPasswordField passwordPasswordField;
    private JButton loginButton;
    private JButton signUpButton;
    private JButton forgotPasswordButton;
    
    Student(){
        JPanel panel = new JPanel();
     //   panel.setLayout(new FlowLayout(FlowLayout.CENTER,1000,40));

        welcomelabel.setText("Welcome Student!");
        welcomelabel.setHorizontalAlignment(JLabel.CENTER);
        welcomelabel.setFont(new Font("Arial Black", Font.PLAIN, 30));
        panel.add(welcomelabel);

        panel.setLayout(new FlowLayout(FlowLayout.CENTER,1000,24));

        JLabel userLabel = new JLabel("Email");
        emailTextField = new JTextField(15);
        panel.add(userLabel);
        panel.add(emailTextField);

        JLabel passwordLabel = new JLabel("Password");
        passwordPasswordField = new JPasswordField(15);
        panel.add(passwordLabel);
        panel.add(passwordPasswordField);
;

        forgotPasswordButton = new JButton("Forgotten Password?");
        forgotPasswordButton.setFont(new Font("Calibri", Font.PLAIN, 12));
        forgotPasswordButton.addActionListener(this);
        forgotPasswordButton.setFocusable(false);
        forgotPasswordButton.setBorder(BorderFactory.createEmptyBorder(0,0 ,0, 0));
        forgotPasswordButton.setContentAreaFilled(false);
        forgotPasswordButton.setForeground(Color.RED);
        forgotPasswordButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        panel.add(forgotPasswordButton);

        loginButton = new JButton("Log In");
        loginButton.addActionListener(this);
        loginButton.setFocusable(false);
        panel.add(loginButton);

        JLabel nolabel = new JLabel("No Account Yet?");
        panel.add(nolabel);
        
         JPanel cancelpanel = new JPanel();

        signUpButton = new JButton("Sign Up");
        signUpButton.setFocusable(false);
        cancelpanel.add(signUpButton);
        signUpButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new signupForm();
                dispose();
                        }
        });

        JButton cancelButton = new JButton("Cancel");
        cancelpanel.add(cancelButton);
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                WelcomeUser CancelUse = new WelcomeUser();
                CancelUse.user();
                dispose();
                        }
        });
        
        ImageIcon image = new ImageIcon(getClass().getResource("ss.png"));
        setIconImage(image.getImage());
        this.setTitle("SmartStudent: Navigating Education Information");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setSize(450, 470);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.add(panel);
        add(cancelpanel,BorderLayout.SOUTH);
       
}  
  @Override
public void actionPerformed(ActionEvent e) {
    if (e.getSource() == loginButton) {
        String email = emailTextField.getText();
        String epassword = new String(passwordPasswordField.getPassword());

        try {
            if (authenticateUser(email, epassword)) {
                System.out.println("User successfully authenticated");
                JOptionPane.showMessageDialog(this, "Login successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                 SwingUtilities.invokeLater(() -> {
            int loggedInStudentID = 11; // Replace this with the actual logged-in user's ID
            new StudentInformation(loggedInStudentID);
                                    dispose();

        });              
              //  JFrame InfoFrame = new JFrame("Info Display");
                //JLabel displayUser = new JLabel();

            } else {
                JOptionPane.showMessageDialog(this, "Incorrect email or password. Please try again.", "Login Failed", JOptionPane.ERROR_MESSAGE);
                System.out.println("Authentication failed");
            }
        } catch (InstantiationException | IllegalAccessException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
    }else if(e.getSource()== forgotPasswordButton){
        new forgettPassword();
        dispose();
    
}
}

private boolean authenticateUser(String email, String epassword) throws InstantiationException, IllegalAccessException {
    boolean authenticationStatus = false;
    Connection conn = null;

    String url = "jdbc:mysql://localhost:3306/";
    String dbName = "smartstudents";
    String driver = "com.mysql.cj.jdbc.Driver";
    String userName = "root";
    String password = "";
    String usn, pass;

    try {
        Class.forName(driver);
        conn = DriverManager.getConnection(url + dbName, userName, password);

        String sql = "SELECT email, password FROM signup_form WHERE email=? AND password=?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, email);
        pstmt.setString(2, epassword);

        ResultSet rs = pstmt.executeQuery();

        if (rs.next()) {
            // User exists in the database
            authenticationStatus = true;
            usn = rs.getString("email");
            pass = rs.getString("password");
            System.out.println("Authenticated User - Username: " + email + ", Password: " + password);
            
        }

    } catch (ClassNotFoundException | SQLException ex) {
        ex.printStackTrace();
    } finally {
        try {
            if (conn != null) {
                conn.close();
                System.out.println("Disconnected from the database");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    return authenticationStatus;
}

}

