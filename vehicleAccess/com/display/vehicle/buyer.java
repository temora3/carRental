
package vehicleAccess.com.display.vehicle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class buyer extends JFrame{
    public String fname, email, phone, password, confirmpassword;
    private JLabel lblemail, lblcreate, lblpassword;
    private JTextField txtemail;
    private JButton btncreate, btnlogin, btnback;
    private JPasswordField passwordField;
    private ResultSet resultSet2;

    public buyer() {
        setLayout(new FlowLayout());
        setLayout(null);
        this.setBounds(370, 100, 800, 480);
        btnback = new JButton("Back");
        lblemail = new JLabel("Email: ");
        lblpassword = new JLabel("Password: ");
        lblcreate = new JLabel("Don't have an account ? ");
        passwordField = new JPasswordField(20);
        txtemail = new JTextField(20);
        btnlogin = new JButton("Login");
        btncreate = new JButton("Sign up");
        lblpassword.setBounds(10, 50, 80, 25);
        passwordField.setBounds(100, 50, 180, 25);
        lblemail.setBounds(10, 20, 80, 25);
        txtemail.setBounds(100, 20, 180, 25);
        lblemail.setFont(new Font("Times new roman", Font.PLAIN, 16));
        lblpassword.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        btnlogin.setBounds(150, 80, 80, 25);
        lblcreate.setBounds(10, 150, 200, 25);
        btncreate.setBounds(150, 150, 80, 25);
        btnback.setBounds(300, 80, 80, 25);
        add(lblemail);
        add(txtemail);
        add(lblpassword);
        add(passwordField);
        add(lblcreate);
        add(btncreate);
        add(btnlogin);
        add(btnback);
        this.dispose();
        btncreate.addActionListener(custSignUp);
        btnlogin.addActionListener(
        new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkcredentials();
            }
        });
        btnback.addActionListener(backListener);
    }
    ActionListener backListener  = e -> {
        this.dispose();
        main_page objFrame = new main_page();
        objFrame.setSize(420, 420);
        objFrame.setTitle("Welcome to Tecoma Rental");
        objFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        objFrame.setResizable(false);
        objFrame.setVisible(true);
        objFrame.getContentPane().setBackground(new Color(242, 210, 189));
        objFrame.setLayout(null);
    };

    ActionListener custSignUp  = e -> {
        this.dispose();
        customersignup Frame = new customersignup();
        Frame.setSize(420, 420);
        Frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        Frame.setTitle("Tecoma Rental: Customer login");
        Frame.getContentPane().setBackground(new Color(242, 210, 189));
        Frame.setResizable(false);
        Frame.setVisible(true);
    };

    public void checkcredentials() {
        String email = txtemail.getText();
        String password = String.valueOf(passwordField.getPassword());
        check(email, password);
    }

    public boolean check(String email, String password) {
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/tecomarental", "root", "");
            String sql = "SELECT * FROM customer_cred WHERE customerEmail = ?";
            String nameValue = "SELECT customerName FROM customer_cred WHERE customerEmail = ?";
           
            PreparedStatement stmt = conn.prepareStatement(sql);
            PreparedStatement nameStatement = conn.prepareStatement(nameValue);
           
            stmt.setString(1, email);
            nameStatement.setString(1, email);

            ResultSet resultSet = stmt.executeQuery();
            ResultSet resultSet2 = nameStatement.executeQuery();
            
            if (resultSet.next()) {
                String storedPassword = resultSet.getString("customerPassword");
                if (storedPassword != null && storedPassword.equals(password)) {
                     if (resultSet2.next()) {
                        String nameCust = "INSERT INTO cust_temp (nameValue) " + "VALUES (?)";
                        PreparedStatement nameTemp = conn.prepareStatement(nameCust);
                        String nameCustomer = resultSet2.getString("customerName");
                        nameTemp.setString(1, nameCustomer);
                        int set3 = nameTemp.executeUpdate();
                    } else {
                        InvalidCredentials();
                    }
                    ValidCredentials();   
                } else {
                    InvalidCredentials();
                }
            } else {
                InvalidCredentials();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    public void ValidCredentials() {
        cust_vehicle Frame = new cust_vehicle();
        Frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(false);

    }

    public void InvalidCredentials() {
        JOptionPane.showMessageDialog(null, "Invalid credentials. Access denied!");
    }
}
