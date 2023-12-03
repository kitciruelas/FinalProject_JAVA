package SmartStudents;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class AdminView extends JFrame {
    private JTable dataTable;
    private DefaultTableModel tableModel;

    public AdminView() {
        // Set up the table model
        tableModel = new DefaultTableModel();
        tableModel.addColumn("Student ID");
        tableModel.addColumn("First Name");
        tableModel.addColumn("Last Name");

        // Create components
        dataTable = new JTable(tableModel);
        dataTable.setEnabled(false); // Disable editing
        JScrollPane scrollPane = new JScrollPane(dataTable);

        // Set up layout
        setLayout(new BorderLayout());

        // Add a JLabel for information and a "View Information of Student" button at the top
        JPanel northPanel = new JPanel();
        northPanel.setLayout(new BoxLayout(northPanel, BoxLayout.Y_AXIS));

        JLabel infoLabel = new JLabel("Student Information");
        infoLabel.setHorizontalTextPosition(JLabel.CENTER);
        infoLabel.setBorder(new EmptyBorder(20, 200, 0, 0));
        infoLabel.setFont(new Font("Arial", Font.BOLD, 20));
        
        northPanel.add(infoLabel);

        northPanel.add(Box.createVerticalStrut(10)); // Add some vertical space

        JButton viewButton = new JButton("View Information of Student");
        viewButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        viewButton.setBorder(new EmptyBorder(0, 0, 0, 30));
        viewButton.setBorderPainted(false);
        viewButton.setFocusPainted(false);
        viewButton.setOpaque(false);
        viewButton.setFocusable(false);
        viewButton.setContentAreaFilled(false);
        viewButton.setForeground(Color.RED);
        viewButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        viewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle view button click (you can add your logic here)
                new AdminInterface();
                dispose(); // Close the current frame
            }
        });

        add(northPanel, BorderLayout.NORTH);

        // Add an empty border with insets to the top of the scroll pane
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        add(scrollPane, BorderLayout.CENTER);

        // Add a "Cancel" button at the bottom with FlowLayout
        JPanel southPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton logoutButton = new JButton("Log out");
        logoutButton.setFocusable(false);
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Admin();
                dispose(); // Close the current frame
            }
        });
        southPanel.add(viewButton);
        southPanel.add(logoutButton);
        add(southPanel, BorderLayout.SOUTH);

        //display more data immediately when the frame is created
        StudentDisplayData();

                // Set up the frame
        ImageIcon image = new ImageIcon(getClass().getResource("ss.png"));
        setIconImage(image.getImage());
        setTitle("Admin View");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);


        // Make the frame visible
        setVisible(true);
    }

    private void StudentDisplayData() {
        // Clear previous data
        tableModel.setRowCount(0);

        String url = "jdbc:mysql://localhost:3306/smartstudents";
        String user = "root";
        String password = "";

        try {
            // Load the MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish a connection
            Connection connection = DriverManager.getConnection(url, user, password);

            // Create a statement
            Statement statement = connection.createStatement();

            // Execute a query
            String query = "SELECT * FROM fillup_form";
            ResultSet resultSet = statement.executeQuery(query);

            // Process the results
            while (resultSet.next()) {
                int id = resultSet.getInt("studentID");
                String name = resultSet.getString("firstname");
                String lname = resultSet.getString("lastname");

                // Add a new row to the table model
                tableModel.addRow(new Object[]{id, name, lname});
            }

            // Close resources
            resultSet.close();
            statement.close();
            connection.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new AdminView();
            }
        });
    }
}


