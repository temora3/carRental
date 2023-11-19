package vehicleAccess.com.display.vehicle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.sql.*;

public class staff extends JFrame{
    public String ID, password, confirmpassword, fname, phone;

    private JLabel lblID, lblpassword, lblsignup;
    private JTextField txtID;
    private JPasswordField passwordField;
    private JButton btnlogin, btnsignup,btnBack;

    public staff() {
        setLayout(new FlowLayout());
        setLayout(null);
        this.setBounds(370, 100, 800, 480);

        lblID = new JLabel("ID :");
        lblpassword = new JLabel("Password : ");
        lblsignup = new JLabel("Don't have an account?");
        txtID = new JTextField(20);
        passwordField = new JPasswordField(20);
        btnlogin = new JButton("login");
        btnsignup = new JButton("Sign Up");
        btnBack = new JButton("Back");
        lblpassword.setBounds(10, 50, 80, 25);
        passwordField.setBounds(100, 50, 180, 25);
        lblID.setBounds(10, 20, 80, 25);
        txtID.setBounds(100, 20, 180, 25);
        lblsignup.setBounds(10, 130, 180, 25);

        lblID.setFont(new Font("Times new roman", Font.PLAIN, 16));
        lblpassword.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        btnlogin.setBounds(150, 80, 80, 25);
        btnsignup.setBounds(150, 130, 80, 25);
        btnBack.setBounds(280, 80, 80, 25);

        add(lblID);
        add(txtID);
        add(lblpassword);
        add(passwordField);
        add(btnlogin);
        add(lblsignup);
        add(btnsignup);
        add(btnBack);
        this.dispose();

        btnlogin.addActionListener(loginListener);
        btnsignup.addActionListener(staffSignUpListener);
        btnBack.addActionListener(backBtnListener);
    }
    ActionListener staffSignUpListener  = e -> {
        this.dispose();
        staffsignup objFrame = new staffsignup();
        objFrame.setSize(420, 420);
        objFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        objFrame.setTitle("Tecoma Rental: Staff signup");
        objFrame.getContentPane().setBackground(new Color(242, 210, 189));
        objFrame.setResizable(false);
        objFrame.setVisible(true);
    };

    ActionListener backBtnListener  = e -> {
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

     ActionListener loginListener  = e -> {
        checkcredentials();
    };    

    public void checkcredentials() {
        String ID = txtID.getText();
        String password = String.valueOf(passwordField.getPassword());
        check(ID, password);

    }

    public boolean check(String ID, String password) {
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/tecomarental", "root", "");
            String sql = "SELECT * FROM staff_cred WHERE staff_ID = ?";
            String staffValue = "SELECT staffName FROM staff_cred WHERE staff_ID = ?";
            String staffname = "INSERT INTO staff_temp (nameValue) " + "VALUES (?)";

            PreparedStatement stmt = conn.prepareStatement(sql);
            PreparedStatement staffStatement = conn.prepareStatement(staffValue);
            PreparedStatement staffTemp = conn.prepareStatement(staffname);

            stmt.setString(1, ID);
            staffStatement.setString(1, ID);

            ResultSet resultSet = stmt.executeQuery();
            ResultSet resultSet2 = staffStatement.executeQuery();

            
           

            if (resultSet.next()) {
                String storedPassword = resultSet.getString("staffPassword");
                if (storedPassword != null && storedPassword.equals(password)) {
                    if (resultSet2.next()) {
                        String nameStaff = resultSet2.getString("staffName");
                        staffTemp.setString(1, nameStaff);
                        int set3 = staffTemp.executeUpdate();
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
        JOptionPane.showMessageDialog(null, "Valid credentials. Access granted!");
        this.dispose();
        admin_vehicle objFrame = new admin_vehicle();
        objFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        objFrame.setResizable(false);
    }

    public void InvalidCredentials() {
        JOptionPane.showMessageDialog(null, "Invalid credentials. Access denied!");
    }
}
