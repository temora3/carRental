package vehicleAccess.com.display.vehicle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;

public class customersignup extends JFrame implements ActionListener {
    private JLabel lblpassword, lblfname, lblphonenumber, lblemail, lblconfirmpassword;
    private JPasswordField passwordField, confirmpasswordfield;
    private JTextField txtfname, txtemail, txtphonenumber;

    private JButton btncreate, btnback;

    public customersignup() {
        setLayout(new FlowLayout());
        setLayout(null);
        btncreate = new JButton("Create");
        lblpassword = new JLabel("Password: ");
        lblemail = new JLabel("Email :");
        lblfname = new JLabel("Name :");
        lblphonenumber = new JLabel("Phone: ");
        btnback = new JButton("Back");
        lblconfirmpassword = new JLabel("Confirm Password: ");
        txtemail = new JTextField(100);
        txtfname = new JTextField(20);
        txtphonenumber = new JTextField(20);
        passwordField = new JPasswordField(20);
        confirmpasswordfield = new JPasswordField(20);
        lblemail.setBounds(20, 50, 100, 25);
        txtemail.setBounds(150, 50, 180, 25);
        lblphonenumber.setBounds(20, 80, 100, 25);
        txtphonenumber.setBounds(150, 80, 180, 25);
        lblpassword.setBounds(20, 110, 100, 25);
        passwordField.setBounds(150, 110, 180, 25);
        lblconfirmpassword.setBounds(20, 140, 130, 25);
        confirmpasswordfield.setBounds(150, 140, 180, 25);
        lblfname.setBounds(20, 20, 100, 25);
        txtfname.setBounds(150, 20, 180, 25);
        lblpassword.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        lblphonenumber.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        lblfname.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        lblemail.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        btncreate.setBounds(250, 170, 80, 25);
        btnback.setBounds(250, 200, 80, 25);

        add(lblfname);
        add(txtfname);
        add(lblemail);
        add(txtemail);
        add(lblphonenumber);
        add(txtphonenumber);
        add(lblpassword);
        add(passwordField);
        add(lblconfirmpassword);
        add(confirmpasswordfield);
        add(btncreate);
        add(btnback);

        btncreate.addActionListener(this);
        btnback.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        buyer objFrame = new buyer();
                        objFrame.setSize(420, 420);
                        objFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                        objFrame.setTitle("Tecoma Rental: customer login");
                        objFrame.getContentPane().setBackground(new Color(242, 210, 189));
                        objFrame.setResizable(false);
                        objFrame.setVisible(true);

                        customersignup Frame = new customersignup();
                    }
                });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        registerbuyer();
        buyer objFrame = new buyer();
        objFrame.setSize(420, 420);
        objFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        objFrame.setTitle("Tecoma Rental: customer login");
        objFrame.getContentPane().setBackground(new Color(242, 210, 189));
        objFrame.setResizable(false);
        objFrame.setVisible(true);
    }

    public void registerbuyer() {
        String fname = txtfname.getText();
        String email = txtemail.getText();
        String phone = txtphonenumber.getText();
        String password = String.valueOf(passwordField.getPassword());
        String confirmpassword = String.valueOf(confirmpasswordfield.getPassword());
        if (fname.isEmpty() || email.isEmpty() || phone.isEmpty() || password.isEmpty() || confirmpassword.isEmpty()) {
            JOptionPane.showMessageDialog(null,
                    "Please enter all fields",
                    "try again",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!password.equals(confirmpassword)) {
            JOptionPane.showMessageDialog(null,
                    "confirm password does not match",
                    "try again",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        insert(fname, email, phone, password);

    }

    private buyer insert(String fname, String email, String phone, String password) {
        buyer buyer = null;

        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/tecomarental", "root", "");
            // Connected to database successfully...

            Statement stmt = conn.createStatement();
            String sql = "INSERT INTO customer_cred (customerName, customerEmail, customerPhone, customerPassword) " +
                    "VALUES (?, ?, ?, ?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, fname);
            preparedStatement.setString(2, email);
            preparedStatement.setString(3, phone);
            preparedStatement.setString(4, password);

            // Insert row into the table
            int addedRows = preparedStatement.executeUpdate();
            if (addedRows > 0) {
                buyer = new buyer();
                buyer.fname = fname;
                buyer.email = email;
                buyer.phone = phone;
                buyer.password = password;
            }

            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return buyer;
    }
}
