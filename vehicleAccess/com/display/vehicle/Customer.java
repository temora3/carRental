package vehicleAccess.com.display.vehicle;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Customer {
    private int customerId;
    private String customerName;
    private String customerEmail;
    private String customerPhone;

    public Customer(int customerId, String customerName, String customerEmail, String customerPhone) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.customerPhone = customerPhone;
    }

    // Getters and Setters

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    // Database operations

    public static ArrayList<Customer> fetchAllCustomers() {
        ArrayList<Customer> customers = new ArrayList<>();

        // Database connection details
        String url = "jdbc:mysql://localhost:3306/vechilemanagement";
        String username = "root";
        String password = "";

        // SQL query to retrieve customer data
        String query = "SELECT * FROM customer_cred";

        try (Connection connection = DriverManager.getConnection(url, username, password);
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                int customerId = resultSet.getInt("customerID");
                String customerName = resultSet.getString("customerName");
                String customerEmail = resultSet.getString("customerEmail");
                String customerPhone = resultSet.getString("customerPhone");

                Customer customer = new Customer(customerId, customerName, customerEmail, customerPhone);
                customers.add(customer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return customers;
    }
}
