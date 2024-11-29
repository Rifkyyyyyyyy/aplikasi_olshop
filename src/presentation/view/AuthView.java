package presentation.view;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import presentation.viewModel.auth.AuthViewModel;

import javax.swing.*;
import java.awt.*;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import constant.role.Role; // Assuming this enum is in the 'constant.role' package
import domain.model.users.UsersModel;

public class AuthView extends JFrame {
    // ViewModel
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton registerButton;
    private final AuthViewModel authViewModel;
    private final UserView homeView;
    private final SellerView sellerView;

    public AuthView(AuthViewModel authViewModel, UserView homeView, SellerView sellerView) {
        this.authViewModel = authViewModel;
        this.homeView = homeView;
        this.sellerView = sellerView;

        // Set up frame
        setTitle("Authentication");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLayout(new BorderLayout());

        // Center frame and make it visible
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // Display method to show the login form initially
    public void display() {
        showLoginForm(); // Show the login form when the app starts
    }

    private void showLoginForm() {
        // Clear current frame content
        getContentPane().removeAll();

        // Create input panel for login
        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Username field
        inputPanel.add(new JLabel("Username:"));
        usernameField = new JTextField();
        inputPanel.add(usernameField);

        // Password field
        inputPanel.add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        inputPanel.add(passwordField);

        // Login button
        loginButton = new JButton("Login");
        inputPanel.add(loginButton);

        // Register button
        registerButton = new JButton("Register");
        inputPanel.add(registerButton);

        // Add input panel to frame
        add(inputPanel, BorderLayout.CENTER);

        // Add action listeners
        loginButton.addActionListener(_ -> handleLogin());
        registerButton.addActionListener(_ -> showRegisterForm());

        // Revalidate the layout to reflect changes
        revalidate();
        repaint();
    }

    private void showRegisterForm() {
        // Clear current frame content
        getContentPane().removeAll();

        // Create input panel for registration
        JPanel inputPanel = new JPanel(new GridLayout(5, 2, 10, 10)); // Increased row count to 5
        inputPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Username field
        inputPanel.add(new JLabel("Username:"));
        usernameField = new JTextField();
        inputPanel.add(usernameField);

        // Password field
        inputPanel.add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        inputPanel.add(passwordField);

        // Role dropdown
        inputPanel.add(new JLabel("Role:"));
        JComboBox<Role> roleComboBox = new JComboBox<>(Role.values()); // Populating JComboBox with Role enum
        inputPanel.add(roleComboBox);

        // Register button
        registerButton = new JButton("Register");
        inputPanel.add(registerButton);

        // Back to Login button
        JButton backToLoginButton = new JButton("Back to Login");
        inputPanel.add(backToLoginButton);

        // Add input panel to frame
        add(inputPanel, BorderLayout.CENTER);

        // Add action listeners
        registerButton.addActionListener(_ -> handleRegister(roleComboBox)); // Pass JComboBox to handleRegister
        backToLoginButton.addActionListener(_ -> showLoginForm());

        // Revalidate the layout to reflect changes
        revalidate();
        repaint();
    }

    private void handleLogin() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        authViewModel.login(username, password)
                .thenAccept(user -> {
                    if (user != null) {
                        JOptionPane.showMessageDialog(this, "Login Successful: " + user.getRole());
                        if (user.getRole().equals(Role.SELLER)) {
                            routeToSellerPage(user);
                        } else {
                            routeToBuyerPage(user);
                        }
                    } else {
                        JOptionPane.showMessageDialog(this, "Invalid credentials", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }).exceptionally(ex -> {
                    JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return null;
                });
    }

    private void handleRegister(JComboBox<Role> roleComboBox) {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        int randomId = (int) (Math.random() * Integer.MAX_VALUE);

        Role selectedRole = (Role) roleComboBox.getSelectedItem();
        UsersModel newUser = new UsersModel(randomId, username, password, selectedRole);

        authViewModel.register(newUser)
                .thenAccept(isRegistered -> {
                    if (isRegistered) {
                        JOptionPane.showMessageDialog(this, "Registration Successful! Please log in.");
                        showLoginForm();
                        usernameField.setText(""); // Clear the username field
                        passwordField.setText(""); // Clear the password field
                    } else {
                        JOptionPane.showMessageDialog(this, "Registration failed. Try again.", "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }).exceptionally(ex -> {
                    JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return null;
                });
    }

    private void routeToSellerPage(UsersModel user) {
        JOptionPane.showMessageDialog(this, "Welcome to the Seller Dashboard!");
        this.setVisible(false);

        int userId = user.getId(); // Assuming `UsersModel` has `getId()` to retrieve the user ID
        sellerView.setUid(userId); // Set the userId in SellerView
        sellerView.setVisible(true);
    }

    private void routeToBuyerPage(UsersModel user) {
        JOptionPane.showMessageDialog(this, "Welcome to the Buyer Dashboard!");
        this.setVisible(false);
        homeView.setUser(user.getUsername()); // Assuming homeView has a method `setUser()`
        homeView.setVisible(true);
    }

    // Getters for the fields and buttons
    public JTextField getUsernameField() {
        return usernameField;
    }

    public JPasswordField getPasswordField() {
        return passwordField;
    }

    public JButton getLoginButton() {
        return loginButton;
    }

    public JButton getRegisterButton() {
        return registerButton;
    }
}
