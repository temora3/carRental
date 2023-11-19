package vehicleAccess.com.display.vehicle;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;


public class admin_vehicle extends JFrame {
    private JLabel vehName_L, vehPlate_L, vehPrice_L;
    private JTable table;
    private JButton addButton, deleteButton, updateButton, logOutButton, clearButton;
    private JTextField vehNameField, vehPlateField, vehPriceField;
    private JLabel userDetails;
    private DefaultTableModel model;

    String url, username, password, admin_id;

    Connection connection = null;
    Statement statement = null;
    Statement statement2 = null;
    ResultSet resultSet = null;
    ResultSet resultSetID = null;

    public admin_vehicle() {
        url = "jdbc:mysql://localhost:3306/tecomarental";
        username = "root";
        password = "";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, username, password);
            statement = connection.createStatement();
            resultSetID = connection.createStatement().executeQuery("SELECT * FROM tecomarental.staff_temp");

            while (resultSetID.next()) {
                admin_id = resultSetID.getString("nameValue");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        this.setBounds(300, 150, 800, 430);
        setTitle("Staff Section");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create table model with 4 columns
        model = new DefaultTableModel();
        model.addColumn("vehicleName");
        model.addColumn("vehiclePlate");
        model.addColumn("vehiclePrice");

        // Create the JTable using the model
        table = new JTable(model);
        table.setModel(model);

        table.addMouseListener(displayValue);

        // Add the table to a scroll pane
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(365, 0, 420, 460);

        // Create a panel and add the scroll pane to it
        JPanel panel = new JPanel();
        panel.add(scrollPane);
        panel.setLayout(null);
        panel.setBackground(new Color(104, 255, 203));

        this.addWindowListener(closeName);

        userDetails = new JLabel("Logged in as: " + admin_id);
        userDetails.setBounds(20, 5, 200, 30);
        panel.add(userDetails);

        vehName_L = new JLabel("Vehicle Name");
        vehName_L.setBounds(20, 60, 100, 30);
        panel.add(vehName_L);

        vehNameField = new JTextField();
        vehNameField.setBounds(120, 65, 160, 23);
        panel.add(vehNameField);

        vehPlate_L = new JLabel("Vehicle Plate");
        vehPlate_L.setBounds(20, 115, 100, 30);
        panel.add(vehPlate_L);

        vehPlateField = new JTextField();
        vehPlateField.setBounds(120, 120, 160, 23);
        panel.add(vehPlateField);

        vehPrice_L = new JLabel("Vehicle Price");
        vehPrice_L.setBounds(20, 170, 100, 30);
        panel.add(vehPrice_L);

        vehPriceField = new JTextField();
        vehPriceField.setBounds(120, 175, 160, 23);
        panel.add(vehPriceField);

        addButton = new JButton("Add");
        addButton.setBounds(30, 260, 80, 20);
        addButton.addActionListener(addActionListener);
        panel.add(addButton);

        updateButton = new JButton("Update");
        updateButton.setBounds(150, 260, 80, 20);
        updateButton.addActionListener(updateActionListener);
        panel.add(updateButton);

        clearButton = new JButton("Clear");
        clearButton.setBounds(90, 320, 80, 20);
        clearButton.addActionListener(clearTextListener);
        panel.add(clearButton);

        deleteButton = new JButton("Delete");
        deleteButton.setBounds(270, 260, 80, 20);
        deleteButton.addActionListener(deleteActionListener);
        panel.add(deleteButton);

        logOutButton = new JButton("Log out");
        logOutButton.setBounds(200, 320, 80, 20);
        logOutButton.addActionListener(logOutListener);
        panel.add(logOutButton);

        // Set the panel as the content pane of the frame
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
                Object[] rowData = new Object[model.getColumnCount()];
                for (int i = 0; i < rowData.length; i++) {
                    rowData[i] = resultSet.getObject(i + 1);
                }
                model.addRow(rowData);
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

    ActionListener addActionListener = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                if (((CharSequence) vehNameField).isEmpty() || ((CharSequence) vehPlateField).isEmpty() || ((CharSequence) vehPriceField).isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please fill in all the fields");
                }
                else{
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    connection = DriverManager.getConnection(url, username, password);
                    statement = connection.createStatement();
                    resultSet = connection.createStatement().executeQuery("SELECT * FROM tecomarental.vehicle_table");
                    String query = "insert into vehicle_table(vehicleName, vehiclePlate, vehiclePrice) values(?,?,?)";
                    PreparedStatement pst = connection.prepareStatement(query);
                    pst.setString(1, vehNameField.getText());
                    pst.setString(2, vehPlateField.getText());
                    pst.setString(3, vehPriceField.getText());
                    pst.executeUpdate();
                    DefaultTableModel model = (DefaultTableModel) table.getModel();
                    model.setRowCount(0);
                    showVehicle();
                    vehNameField.setText("");
                    vehPlateField.setText("");
                    vehPriceField.setText("");
                    JOptionPane.showMessageDialog(null, "Inserted Successfully!");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Please fill in all the fields");
            }
        }
    };

    MouseListener displayValue = new MouseListener() {

        @Override
        public void mouseClicked(MouseEvent evt) {
            // TODO Auto-generated method stub
            if (evt.getClickCount() == 1) { // Check for single-click
                int selectedRow = table.getSelectedRow();
                TableModel model = table.getModel();
                vehNameField.setText(model.getValueAt(selectedRow, 0).toString());
                vehPlateField.setText(model.getValueAt(selectedRow, 1).toString());
                vehPriceField.setText(model.getValueAt(selectedRow, 2).toString());
            }
        }

        @Override
        public void mousePressed(MouseEvent evt) {
        }

        @Override
        public void mouseReleased(MouseEvent evt) {
        }

        @Override
        public void mouseEntered(MouseEvent evt) {
        }

        @Override
        public void mouseExited(MouseEvent evt) {
        }

    };

    ActionListener updateActionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent exec) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(url, username, password);
                int row = table.getSelectedRow();
                String tablevalue = (table.getModel().getValueAt(row, 0).toString());
                String query = "UPDATE vehicle_table SET vehicleName = ?, vehiclePlate = ?, vehiclePrice= ? where vehicleName='"
                        + tablevalue + "'";
                PreparedStatement pst = connection.prepareStatement(query);
                pst.setString(1, vehNameField.getText());
                pst.setString(2, vehPlateField.getText());
                pst.setString(3, vehPriceField.getText());
                pst.executeUpdate();
                DefaultTableModel model = (DefaultTableModel) table.getModel();
                model.setRowCount(0);
                showVehicle();
                vehNameField.setText("");
                vehPlateField.setText("");
                vehPriceField.setText("");
                JOptionPane.showMessageDialog(null, "Updated Successfully!");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Please pick the Vehicle you want to update on the table");
            }

        }
    };

    ActionListener deleteActionListener = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent es) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(url, username, password);
                int row = table.getSelectedRow();
                String tablevalue = (table.getModel().getValueAt(row, 0).toString());
                String query = "DELETE FROM tecomarental.vehicle_table where vehicleName ='" + tablevalue + "'";
                PreparedStatement pst = connection.prepareStatement(query);
                pst.executeUpdate();
                DefaultTableModel model = (DefaultTableModel) table.getModel();
                model.setRowCount(0);
                showVehicle();
                vehNameField.setText("");
                vehPlateField.setText("");
                vehPriceField.setText("");
                JOptionPane.showMessageDialog(null, "Deleted Successfully!");
            } catch (Exception eq) {
                JOptionPane.showMessageDialog(null, "Please pick the Vehicle you want to delete from the table");
            }

        }
    };

    ActionListener logOutListener = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {

                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(url, username, password);
                statement = connection.createStatement();

                String deleteQuery = "DELETE FROM tecomarental.staff_temp";
                PreparedStatement statementP = connection.prepareStatement(deleteQuery);

                statementP.executeUpdate(deleteQuery);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            dispose();
            customersignup vers3 = new customersignup();
        }

    };

    ActionListener clearTextListener = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            vehNameField.setText("");
            vehPlateField.setText("");
            vehPriceField.setText("");
        }

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

                String deleteQuery = "DELETE FROM tecomarental.staff_temp";
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

                String deleteQuery = "DELETE FROM tecomarental.staff_temp";
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