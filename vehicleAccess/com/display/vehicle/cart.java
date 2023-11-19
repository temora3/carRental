package vehicleAccess.com.display.vehicle;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

public class cart extends JFrame {
    private JTable table;
    private JLabel custDetails, searchLabel;
    private JTextField searchField;
    private DefaultTableModel modelCust;
    private JButton logOutButton, rentOutButton;

    String url, username, password, admin_id, tempHolder;

    Connection connection = null;
    Statement statement = null;
    ResultSet resultSet = null;
    ResultSet resultSetID = null;

    public cart() {
        url = "jdbc:mysql://localhost:3306/tecomarental";
        username = "root";
        password = "";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, username, password);
            statement = connection.createStatement();
            resultSetID = connection.createStatement().executeQuery("SELECT * FROM tecomarental.cust_temp");

            while (resultSetID.next()) {
                admin_id = resultSetID.getString("nameValue");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        tempHolder = admin_id;
        this.setBounds(230, 100, 800, 480);
        setTitle("Cart");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create table model with 4 columns
        modelCust = new DefaultTableModel();
        modelCust.addColumn("vehicleName");
        modelCust.addColumn("vehiclePlate");
        modelCust.addColumn("vehiclePrice");

        // Create the JTable using the model
        table = new JTable(modelCust);
        table.setModel(modelCust);

        // Add the table to a scroll pane
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(80, 70, 620, 300);

        // Create a panel and add the scroll pane to it
        JPanel panel = new JPanel();
        panel.add(scrollPane);
        panel.setLayout(null);

        this.addWindowListener(closeName);

        custDetails = new JLabel("Logged in as:" + admin_id);
        custDetails.setBounds(20, 5, 200, 30);
        panel.add(custDetails);

        searchLabel = new JLabel("Search");
        searchLabel.setBounds(280, 25, 100, 30);
        panel.add(searchLabel);

        searchField = new JTextField();
        searchField.setBounds(330, 25, 100, 30);
        searchField.addKeyListener(searchListener);
        panel.add(searchField);

        logOutButton = new JButton("Log Out");
        logOutButton.setBounds(200, 400, 180, 30);
        logOutButton.addActionListener(logOutListener);
        panel.add(logOutButton);

        rentOutButton = new JButton("Rent Out");
        rentOutButton.setBounds(430, 400, 180, 30);
        rentOutButton.addActionListener(rentOutListener);
        panel.add(rentOutButton);

        setContentPane(panel);
        showVehicle();
    }

    public ArrayList<vehicle> vehicleList() {
        ArrayList<vehicle> vehicleList = new ArrayList<>();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, username, password);
            statement = connection.createStatement();
            resultSet = connection.createStatement()
                    .executeQuery("SELECT * FROM tecomarental.cart_table where RentedTo ='" + admin_id + "'");

            while (resultSet.next()) {
                Object[] rowData = new Object[modelCust.getColumnCount()];
                for (int i = 0; i < rowData.length; i++) {
                    rowData[i] = resultSet.getObject(i + 1);
                }
                modelCust.addRow(rowData);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return vehicleList;
    }

    public void showVehicle() {
        ArrayList<vehicle> details = vehicleList();
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        Object[] row = new Object[3];
        for (int i = 0; i < details.size(); i++) {
            row[0] = details.get(i).getVehName();
            row[1] = details.get(i).getVehiclePlate();
            row[2] = details.get(i).getVehiclePrice();
            model.addRow(row);
        }
    }

    KeyListener searchListener = new KeyListener() {

        @Override
        public void keyTyped(KeyEvent e) {
        }

        @Override
        public void keyPressed(KeyEvent e) {
        }

        @Override
        public void keyReleased(KeyEvent e) {
            DefaultTableModel modelSearch = (DefaultTableModel) table.getModel();
            TableRowSorter<DefaultTableModel> tableRowSorter = new TableRowSorter<>(modelSearch);
            table.setRowSorter(tableRowSorter);
            RowFilter<DefaultTableModel, Object> caseInsensitiveFilter = new RowFilter<DefaultTableModel, Object>() {
                @Override
                public boolean include(Entry<? extends DefaultTableModel, ? extends Object> entry) {
                    String searchText = searchField.getText().toLowerCase();
                    for (int i = entry.getValueCount() - 1; i >= 0; i--) {
                        if (entry.getStringValue(i).toLowerCase().contains(searchText)) {
                            return true;
                        }
                    }
                    return false;
                }
            };
            tableRowSorter.setRowFilter(caseInsensitiveFilter);
        }
    };

    WindowListener closeName = new WindowListener() {

        @Override
        public void windowOpened(WindowEvent e) {
        }

        @Override
        public void windowClosing(WindowEvent e) {
        }

        @Override
        public void windowClosed(WindowEvent e) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(url, username, password);
                statement = connection.createStatement();

                String deleteQuery = "DELETE FROM tecomarental.cust_temp";
                PreparedStatement statementP = connection.prepareStatement(deleteQuery);

                statementP.executeUpdate(deleteQuery);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, e);
            }
        }

        @Override
        public void windowIconified(WindowEvent e) {
        }

        @Override
        public void windowDeiconified(WindowEvent e) {
        }

        @Override
        public void windowActivated(WindowEvent e) {
        }

        @Override
        public void windowDeactivated(WindowEvent e) {
        }

    };

    ActionListener logOutListener = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {

                Class.forName("com.mysql.jdbc.Driver");
                connection = DriverManager.getConnection(url, username, password);
                statement = connection.createStatement();

                String deleteQuery = "DELETE FROM tecomarental.cust_temp";
                PreparedStatement statementP = connection.prepareStatement(deleteQuery);

                statementP.executeUpdate(deleteQuery);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, e);
            }
            dispose();
            customersignup vers3 = new customersignup();
        }

    };

    ActionListener rentOutListener = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {

                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(url, username, password);
                statement = connection.createStatement();

                String rentQuery = "DELETE FROM tecomarental.cart_table where RentedTo ='" + admin_id + "'";
                PreparedStatement statementP = connection.prepareStatement(rentQuery);

                statementP.executeUpdate(rentQuery);
                JOptionPane.showMessageDialog(null,
                        "Thank you for renting the vehicle. Come to Our nearest shops to pick it");

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, e);
            }
            dispose();
            customersignup vers3 = new customersignup();
        }
    };
}
