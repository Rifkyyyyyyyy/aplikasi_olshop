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
                        String id = rs.getString("id");
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
        String sql = "INSERT INTO users (id, username, password, role) VALUES (?, ?, ?, ?)";
    
        return CompletableFuture.supplyAsync(() -> {
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                // Assuming 'user.getId()' returns the ID value that you want to insert
                stmt.setString(1, user.getId()); // Set the 'id' parameter (assuming it's an integer)
                stmt.setString(2, user.getUsername()); // Set the 'username'
                stmt.setString(3, user.getPassword()); // Set the 'password'
                stmt.setString(4, user.getRole().name()); // Set the 'role' (assuming 'role' is an enum)
    
                int rowsAffected = stmt.executeUpdate(); // Execute the update
                return rowsAffected > 0; // Return true if a row was inserted
            } catch (SQLException e) {
                // Log the exception or handle it appropriately
                System.err.println("Error executing SQL: " + e.getMessage());
                throw new RuntimeException("Register failed: " + e.getMessage(), e);
            }
        }, executorService);
    }
    
}
