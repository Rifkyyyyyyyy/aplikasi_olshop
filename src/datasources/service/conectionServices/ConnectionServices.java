package datasources.service.conectionServices;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import constant.helper.Helper;

public class ConnectionServices {
    public Connection getConnection() {
        try {
            // Load the MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish and return the connection
            return DriverManager.getConnection(Helper.DATABASE_URL, Helper.DATABASE_USERNAME, Helper.DATABASE_PASSWORD);

        } catch (ClassNotFoundException e) {
            // Handle the case where the JDBC driver is not found
            System.err.println("JDBC Driver not found: " + e.getMessage());
            e.printStackTrace();
        } catch (SQLException e) {
            // Handle the case where connection fails
            System.err.println("Failed to connect to the database: " + e.getMessage());
            e.printStackTrace();
        }
        return null;  // Return null if connection fails
    }
}
