package vehicleAccess.com.display.vehicle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class main_page extends JFrame implements ActionListener {
    private JButton btnstaff, btnbuyer;
    private JLabel lblgreeting;

    public JFrame frame;

    public main_page() {
        frame = new JFrame();
        setLayout(new FlowLayout());
        setLayout(null);
        this.setBounds(370, 100, 800, 480);

        btnbuyer = new JButton("Customer Login");
        btnstaff = new JButton("Staff  Login");
        lblgreeting = new JLabel("WELCOME ");
        btnstaff.setFont(new Font("Times New Roman", Font.PLAIN, 12));
        btnbuyer.setFont(new Font("Times new roman", Font.PLAIN, 12));
        lblgreeting.setFont(new Font("Arial Black", Font.BOLD, 20));
        /*
         * lblgreeting.setHorizontalTextPosition(JLabel.CENTER);
         * lblgreeting.setVerticalTextPosition(JLabel.TOP);
         */
        lblgreeting.setBounds(100, 100, 150, 25);

        btnbuyer.setBounds(100, 150, 150, 25);
        btnstaff.setBounds(100, 200, 150, 25);

        add(lblgreeting);

        add(btnbuyer);
        add(btnstaff);
        btnstaff.addActionListener(this);
        btnbuyer.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        buyer objFrame = new buyer();
                        objFrame.setSize(420, 420);
                        objFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
                        objFrame.setTitle("Tecoma Rental: customer login");
                        objFrame.getContentPane().setBackground(new Color(242, 210, 189));
                        objFrame.setResizable(false);
                        objFrame.setVisible(true);
                    }
                });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.dispose();
        staff objFrame = new staff();
        objFrame.setSize(420, 420);
        objFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        objFrame.setTitle("Tecoma Rental: Staff login");
        objFrame.getContentPane().setBackground(new Color(242, 210, 189));
        objFrame.setResizable(false);
        objFrame.setVisible(true);
    }

    public static void main(String args[]) {
        main_page objFrame = new main_page();
        objFrame.setSize(420, 420);
        objFrame.setTitle("Welcome to Tecoma Rental");
        objFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        objFrame.setResizable(false);
        objFrame.setVisible(true);
        objFrame.getContentPane().setBackground(new Color(242, 210, 189));
        objFrame.setLayout(null);
    }
}
