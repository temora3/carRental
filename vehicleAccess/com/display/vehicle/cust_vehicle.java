package vehicleAccess.com.display.vehicle;

import java.awt.*;
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

public class cust_vehicle extends JFrame {
    private JPanel panel;
    private JTable table;
    private JButton cartButton, cartAddButton;
    private JLabel custDetails, searchLabel;
    private JTextField searchField;
    private DefaultTableModel modelCust;

    String url, username, password, admin_id;

    Connection connection = null;
    Statement statement = null;
    ResultSet resultSet = null;
    ResultSet resultSetID = null;

    public cust_vehicle() {
        url = "jdbc:mysql://localhost:3306/tecomarental";
        username = "root";
        password = "";

        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(url, username, password);
            statement = connection.createStatement();
            resultSetID = connection.createStatement().executeQuery("SELECT * FROM tecomarental.cust_temp");

            while (resultSetID.next()) {
                admin_id = resultSetID.getString("nameValue");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        this.setBounds(230, 100, 800, 480);
        setTitle("Customer Section");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

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

        this.addWindowListener(closeName);

        // Create a panel and add the scroll pane to it
        panel = new JPanel();
        panel.add(scrollPane);
        panel.setLayout(null);

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

        cartButton = new JButton("Open cart");
        cartButton.setBounds(600, 25, 120, 30);
        cartButton.addActionListener(cartOpenListener);
        panel.add(cartButton);

        cartAddButton = new JButton("Add to cart");
        cartAddButton.setBounds(330, 400, 120, 30);
        cartAddButton.addActionListener(cartRow);
        panel.add(cartAddButton);

        setContentPane(panel);
        this.setVisible(true);

        showVehicle();
    }

    public ArrayList<vehicle> vehicleList() {
        ArrayList<vehicle> vehicleList = new ArrayList<>();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, username, password);
            statement = connection.createStatement();
            resultSet = connection.createStatement().executeQuery("SELECT * FROM tecomarental.vehicle_table");

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

    ActionListener cartRow = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                try {

                    Class.forName("com.mysql.cj.jdbc.Driver");
                    connection = DriverManager.getConnection(url, username, password);
                    statement = connection.createStatement();
                    resultSet = connection.createStatement().executeQuery("SELECT * FROM tecomarental.cart_table");

                    String vName = table.getValueAt(selectedRow, 0).toString();
                    String vPlate = table.getValueAt(selectedRow, 1).toString();
                    String vPrice = table.getValueAt(selectedRow, 2).toString();
                    String rentedName = admin_id;

                    String sql = "INSERT INTO tecomarental.cart_table (vName, vPlate, vPrice, RentedTo) VALUES (?, ?, ?, ?)";
                    PreparedStatement statement = connection.prepareStatement(sql);
                    statement.setString(1, vName);
                    statement.setString(2, vPlate);
                    statement.setString(3, vPrice);
                    statement.setString(4, rentedName);

                    // Execute the SQL statement
                    int rowsInserted = statement.executeUpdate();

                    if (rowsInserted > 0) {
                        JOptionPane.showMessageDialog(null, "Vehicle added to the cart!");
                    }

                    // Close the database connection
                    statement.close();
                    connection.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            } else {
                JOptionPane.showMessageDialog(null, "Please select a row to add to the database!");
            }
        }
    };

    ActionListener cartOpenListener = e -> {
        cart vers1 = new cart();
        vers1.setVisible(true);
        this.dispose();
    };

    WindowListener closeName = new WindowListener() {

        @Override
        public void windowOpened(WindowEvent e) {
        }

        @Override
        public void windowClosing(WindowEvent e) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(url, username, password);
                statement = connection.createStatement();

                String deleteQuery = "DELETE FROM tecomarental.cust_temp";
                PreparedStatement statementP = connection.prepareStatement(deleteQuery);

                statementP.executeUpdate(deleteQuery);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
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
                ex.printStackTrace();
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

}

