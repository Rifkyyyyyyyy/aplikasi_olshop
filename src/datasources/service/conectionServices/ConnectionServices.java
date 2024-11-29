package datasources.service.conectionServices;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import constant.helper.Helper;

public class ConnectionServices {
    public  Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(Helper.DATABASE_URL, Helper.DATABASE_USERNAME, Helper.DATABASE_PASSWORD);
            System.out.println("koenksi berhasil");
            return conn;
        } catch (ClassNotFoundException var3) {
            throw new SQLException("JDBC Driver not found: " + var3.getMessage(), var3);
        } catch (SQLException var4) {
            throw new SQLException("Database connection failed: " + var4.getMessage(), var4);
        }
    }
}
