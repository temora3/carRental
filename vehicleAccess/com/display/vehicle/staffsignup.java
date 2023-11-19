package vehicleAccess.com.display.vehicle;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
public class staffsignup extends JFrame{
    private JLabel lblID,lblpassword,lblconfpassword,lblphone,lblfname;
    private JTextField txtID,txtphone,txtname;
    private JPasswordField passwordField,confpasswordfield;
    private JButton btnsubmit,btnback;
    public staffsignup() {
        setLayout(null);
        this.setBounds(370, 100, 800, 480);
        
        lblfname=new JLabel("Name: ");
        txtname=new JTextField(100);
        lblphone=new JLabel("Phone number: ");
        txtphone=new JTextField(30);
        lblconfpassword=new JLabel("Confirm password: ");
        lblID=new JLabel("ID : ");
        lblpassword=new JLabel("Password : ");
        txtID=new JTextField(30);
        passwordField=new JPasswordField(50);
        confpasswordfield=new JPasswordField(50);
        btnsubmit=new JButton("Submit");
        btnback=new JButton("Back");

        lblID.setBounds(10,20,100,25);
        txtID.setBounds(100,20,180,25);
        lblfname.setBounds(10,50,100,25);
        txtname.setBounds(100,50,180,25);
        lblphone.setBounds(10,80,100,25);
        txtphone.setBounds(100,80,180,25);
        lblpassword.setBounds(10,110,100,25);
        passwordField.setBounds(100,110,180,25);
        lblconfpassword.setBounds(10,140,100,25);
        confpasswordfield.setBounds(100,140,180,25);
        btnsubmit.setBounds(200,175,85,25);
        btnback.setBounds(200,230,85,25);
         lblID.setFont(new Font("Times new roman", Font.PLAIN, 16));
         lblpassword.setFont(new Font("Times new roman", Font.PLAIN, 16));
         lblconfpassword.setFont(new Font("Times new roman", Font.PLAIN, 16));
         lblfname.setFont(new Font("Times new roman", Font.PLAIN, 16));

         add(lblphone);
         add(txtphone);
         add(lblfname);
         add(txtname);
         add(lblpassword);
         add(passwordField);
         add(lblconfpassword);
         add(confpasswordfield);
         add(btnsubmit);
         add(btnback);
         btnsubmit.addActionListener(staffRegisterListener);
    }

    ActionListener staffRegisterListener  = e -> {
        this.dispose();
        registerstaff();
        staff objFrame =  new staff();
        objFrame.setSize(420, 420);
        objFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        objFrame.setTitle("Tecoma Rental: Staff login");
        objFrame.getContentPane().setBackground(new Color(242, 210, 189));
        objFrame.setResizable(false);
        objFrame.setVisible(true);
    };

    

    private void registerstaff() {
        String fname=txtname.getText();
        String phone=txtphone.getText();
        String password = String.valueOf(passwordField.getPassword());
        String confirmpassword = String.valueOf(confpasswordfield.getPassword());
        if (password.isEmpty() || confirmpassword.isEmpty()) {
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
        insert(fname,phone ,password);


    }
    private staff  insert(String fname,String phone ,String password) {
        staff staff=null;

        try{
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/tecomarental","root","");
            // Connected to database successfully...

            Statement stmt = conn.createStatement();
            String sql = "INSERT INTO staff_cred (staffName, staffPhone, staffPassword) " +
                    "VALUES (?, ?, ?)";
            String staffValue = "SELECT staffName FROM staff_cred WHERE staff_ID = ?";
            String staffname = "INSERT INTO staff_temp (nameValue) " + "VALUES (?)";

            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            PreparedStatement staffStatement = conn.prepareStatement(staffValue);
            PreparedStatement staffTemp = conn.prepareStatement(staffname);
            
            preparedStatement.setString(1, fname);
            preparedStatement.setString(2, phone);
            preparedStatement.setString(3, password);

            //Insert row into the table
            int addedRows = preparedStatement.executeUpdate();
            if (addedRows > 0) {
                staff = new staff();
                staff.fname = fname;
                staff.phone = phone;
                staff.password = password;
            }

            stmt.close();
            conn.close();
        }catch(Exception e){
            e.printStackTrace();
        }

        return staff;
    }
}