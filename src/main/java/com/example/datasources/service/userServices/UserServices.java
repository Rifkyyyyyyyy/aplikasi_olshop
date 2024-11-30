package datasources.service.userServices;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import constant.role.Role;

import domain.model.users.UsersModel;

public class UserServices {
    private final ExecutorService executorService;
    private final Connection connection;

    public UserServices(Connection connection) {
        this.executorService = Executors.newCachedThreadPool();
        this.connection = connection; // Use the injected connection directly
    }

    // Method to get a user by username and password asynchronously
    public CompletableFuture<UsersModel> getUserByUsernameAndPassword(String username, String password) {
        return CompletableFuture.supplyAsync(() -> {
            String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setString(1, username);
                stmt.setString(2, password);
    
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        int id = rs.getInt("id");
                        String roleString = rs.getString("role");
    
                        // Convert roleString to enum, making it case-insensitive
                        Role role = Role.valueOf(roleString.toUpperCase());
    
                        // Return the user model if found
                        return new UsersModel(id, username, password, role);
                    }
                }
            } catch (SQLException e) {
                // Log and throw the exception with a meaningful message
                System.err.println("Error executing SQL: " + e.getMessage());
                throw new RuntimeException("Login failed: " + e.getMessage(), e);
            }
            return null; // Return null if no user is found
        }, executorService);
    }
    
    // Method to register a user asynchronously

    public CompletableFuture<Boolean> registerUser(UsersModel user) {
        String sql = "INSERT INTO users (username, password, role) VALUES (?, ?, ?)";

        return CompletableFuture.supplyAsync(() -> {
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setString(1, user.getUsername());
                stmt.setString(2, user.getPassword());
                stmt.setString(3, user.getRole().name()); 
                int rowsAffected = stmt.executeUpdate();
                return rowsAffected > 0; // Return true if a row was inserted
            } catch (SQLException e) {
                // Log the exception or handle it appropriately
                System.err.println("Error executing SQL: " + e.getMessage());
                throw new RuntimeException("Register failed: " + e.getMessage(), e);
            }
        }, executorService);
    }
}
