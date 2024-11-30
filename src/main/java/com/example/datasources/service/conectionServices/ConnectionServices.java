package datasources.service.conectionServices;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import constant.helper.Helper;

public class ConnectionServices {
    public Connection getConnection() throws SQLException {
        try {
            System.out.println("Classpath: " + System.getProperty("java.class.path"));

            System.out.println("Loading MySQL JDBC Driver...");
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(Helper.DATABASE_URL, Helper.DATABASE_USERNAME,
                    Helper.DATABASE_PASSWORD);
            System.out.println("Connection successful!");
            return conn;
        } catch (ClassNotFoundException var3) {
            System.err.println("JDBC Driver not found: " + var3.getMessage());
            throw new SQLException("JDBC Driver not found: " + var3.getMessage(), var3);
        } catch (SQLException var4) {
            System.err.println("Database connection failed: " + var4.getMessage());
            throw new SQLException("Database connection failed: " + var4.getMessage(), var4);
        }

    }
}
