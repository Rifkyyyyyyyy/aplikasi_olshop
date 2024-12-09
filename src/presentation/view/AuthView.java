package presentation.view;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import constant.role.Role;
import domain.model.users.UsersModel;
import presentation.viewModel.auth.AuthViewModel;
import presentation.viewModel.product.ProductViewModel;

public class AuthView extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton registerButton;
    private final AuthViewModel authViewModel;
    private  final ProductViewModel productViewModel;


    private JPanel cardPanel;
    private CardLayout cardLayout;

    public AuthView(AuthViewModel authViewModel, ProductViewModel productViewModel)  {
        this.authViewModel = authViewModel;

        this.productViewModel = productViewModel;
      

        // Set up frame
        setTitle("Authentication");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLayout(new BorderLayout());

        // Center frame and make it visible
        setLocationRelativeTo(null);
        setVisible(true);

        // Initialize card layout for switching between login and register forms
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        add(cardPanel, BorderLayout.CENTER);

        // Show login form initially
        showLoginForm();
    }

    public void display() {
        showLoginForm();
    }

    private void showLoginForm() {
        // Parent wrapper section with flex layout (Horizontal BoxLayout)
        JPanel wrapperPanel = new JPanel();
        wrapperPanel.setLayout(new BoxLayout(wrapperPanel, BoxLayout.X_AXIS)); // Flex layout for 50% width sections
        wrapperPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0)); // No padding/margin

        // Left section with the new green color (#238b45)
        JPanel leftSection = new JPanel();
        leftSection.setBackground(Color.decode("#238b45")); // Set background to #238b45
        leftSection.setPreferredSize(new Dimension(0, this.getHeight())); // Full height, width will adjust based on
                                                                          // layout
        leftSection.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE)); // Ensure it fills available
                                                                                         // space proportionally
        leftSection.setLayout(new BoxLayout(leftSection, BoxLayout.Y_AXIS)); // Vertical layout for logo and text

        // Add logo and text inside the green section
        JLabel logoLabel = new JLabel();
        ImageIcon logoIcon = new ImageIcon("assets/logo.png"); // Replace with your actual logo path
        logoLabel.setIcon(logoIcon);
        logoLabel.setAlignmentX(CENTER_ALIGNMENT); // Center the logo horizontally
        logoLabel.setMaximumSize(new Dimension(200, 100)); // Adjust size of the logo

        JLabel greenText = new JLabel("Welcome to Yoto App!"); // Text message
        greenText.setFont(new Font("Arial", Font.BOLD, 18)); // Set font style for the text
        greenText.setAlignmentX(CENTER_ALIGNMENT); // Center the text horizontally
        greenText.setForeground(Color.WHITE); // Set the text color to white

        leftSection.add(Box.createVerticalStrut(50)); // Add space above logo (optional)
        leftSection.add(logoLabel);
        leftSection.add(Box.createVerticalStrut(20)); // Add space between logo and text (optional)
        leftSection.add(greenText);

        // Right section with the white background for forms (fills full height)
        JPanel rightSection = new JPanel();
        rightSection.setLayout(new BoxLayout(rightSection, BoxLayout.Y_AXIS)); // Labels above fields
        rightSection.setBackground(Color.WHITE);
        rightSection.setPreferredSize(new Dimension(0, this.getHeight())); // Full height, width will adjust based on
                                                                           // layout
        rightSection.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE)); // Ensure it fills available
                                                                                          // space proportionally
        rightSection.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Padding inside the form

        // Add a header for the login form
        JLabel headerLabel = new JLabel("Login");
        headerLabel.setAlignmentX(CENTER_ALIGNMENT);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 20));
        rightSection.add(headerLabel);

        // Form elements for login
        usernameField = new JTextField(20);
        passwordField = new JPasswordField(20);
        loginButton = new JButton("Login");
        registerButton = new JButton("Register");

        // Set max height and width for buttons and input fields, also set rounded
        // corners
        setComponentSize(usernameField, 50, 300);
        setComponentSize(passwordField, 50, 300);
        setComponentSize(loginButton, 50, 120);
        setComponentSize(registerButton, 50, 120);

        // Apply styles: rounded corners (8px) and bottom margin (20px)
        usernameField.setBorder(BorderFactory.createCompoundBorder(
                usernameField.getBorder(), BorderFactory.createEmptyBorder(0, 0, 20, 0))); // Add margin bottom for
                                                                                           // input fields
        passwordField.setBorder(BorderFactory.createCompoundBorder(
                passwordField.getBorder(), BorderFactory.createEmptyBorder(0, 0, 20, 0)));
        loginButton.setBorder(BorderFactory.createCompoundBorder(
                loginButton.getBorder(), BorderFactory.createEmptyBorder(0, 0, 20, 0)));
        registerButton.setBorder(BorderFactory.createCompoundBorder(
                registerButton.getBorder(), BorderFactory.createEmptyBorder(0, 0, 20, 0)));

        usernameField.setFont(new Font("Arial", Font.PLAIN, 16)); // Font style
        passwordField.setFont(new Font("Arial", Font.PLAIN, 16));
        loginButton.setFont(new Font("Arial", Font.PLAIN, 16));
        registerButton.setFont(new Font("Arial", Font.PLAIN, 16));

        // Add labels and fields to the right section (forms)
        rightSection.add(createFieldPanel("Username:", usernameField));
        rightSection.add(createFieldPanel("Password:", passwordField));
        rightSection.add(Box.createVerticalStrut(20)); // Space between fields and buttons

        // Create a panel to hold the buttons in a horizontal row
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.X_AXIS)); // Horizontal layout
        buttonsPanel.setOpaque(false);
        buttonsPanel.add(loginButton);
        buttonsPanel.add(Box.createHorizontalStrut(20)); // Space between buttons (optional)
        buttonsPanel.add(registerButton);

        rightSection.add(buttonsPanel);

        // Add the left (green) and right (white forms) sections to the wrapper panel
        wrapperPanel.add(leftSection);
        wrapperPanel.add(rightSection);

        // Add wrapper panel to the card layout
        cardPanel.add(wrapperPanel, "login");

        // Add action listeners
        loginButton.addActionListener(s -> handleLogin());
        registerButton.addActionListener(s -> showRegisterForm());

        // Display login form
        cardLayout.show(cardPanel, "login");
    }

    // Helper method to set size for components (like text fields and buttons)

    private void showRegisterForm() {
        JPanel wrapperPanel = new JPanel();
        wrapperPanel.setLayout(new BoxLayout(wrapperPanel, BoxLayout.X_AXIS)); // Flex layout for 50% width sections
        wrapperPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0)); // No padding/margin

        // Left section with a green background (fills full height)
        JPanel leftSection = new JPanel();
        leftSection.setBackground(Color.GREEN);
        leftSection.setPreferredSize(new Dimension(300, this.getHeight())); // Full height

        // Right section with the white background for registration form (fills full
        // height)
        JPanel rightSection = new JPanel();
        rightSection.setLayout(new BoxLayout(rightSection, BoxLayout.Y_AXIS)); // Labels above fields
        rightSection.setBackground(Color.WHITE);
        rightSection.setPreferredSize(new Dimension(300, this.getHeight())); // Full height

        // Add a header for the registration form
        JLabel headerLabel = new JLabel("Register Form");
        headerLabel.setAlignmentX(CENTER_ALIGNMENT);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 20));
        rightSection.add(headerLabel);

        // Form elements for registration
        usernameField = new JTextField(20);
        passwordField = new JPasswordField(20);
        JComboBox<Role> roleComboBox = new JComboBox<>(Role.values());
        JButton registerButton = new JButton("Register");
        JButton backToLoginButton = new JButton("Back to Login");

        // Set max height and width for buttons and input fields
        setComponentSize(usernameField, 50, 300);
        setComponentSize(passwordField, 50, 300);
        setComponentSize(registerButton, 50, 300);
        setComponentSize(backToLoginButton, 50, 300);

        // Add labels and fields to the right section (forms)
        rightSection.add(createFieldPanel("Username:", usernameField));
        rightSection.add(createFieldPanel("Password:", passwordField));
        rightSection.add(createFieldPanel("Role:", roleComboBox));
        rightSection.add(Box.createVerticalStrut(20));
        rightSection.add(registerButton);
        rightSection.add(Box.createVerticalStrut(10));
        rightSection.add(backToLoginButton);

        // Add the left (green) and right (white forms) sections to the wrapper panel
        wrapperPanel.add(leftSection);
        wrapperPanel.add(rightSection);

        // Add wrapper panel to the card layout
        cardPanel.add(wrapperPanel, "register");

        // Add action listeners
        registerButton.addActionListener(s -> handleRegister(roleComboBox));
        backToLoginButton.addActionListener(s -> showLoginForm());

        // Display register form
        cardLayout.show(cardPanel, "register");
    }

    private void handleLogin() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
    
        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Username and password cannot be empty", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
    
        authViewModel.login(username, password)
        .thenAccept(user -> {
            System.out.println("user: " + user);  // log the entire user object
            if (user != null) {
                System.out.println("User ID: " + user.getId());  // log the user ID
                System.out.println("User Role: " + user.getRole());  // log the role
                JOptionPane.showMessageDialog(this, "Login Successful: " + user.getRole());
                if (user.getRole().equals(Role.SELLER)) {
                    System.out.println("Routing to Seller Page with ID: " + user.getId());
                    routeToSellerPage(user.getId() , user.getUsername());
                } else {
                    System.out.println("Routing to Buyer Page with ID: " + user.getId());
                    routeToBuyerPage(user.getUsername());
                }
            } else {
                System.out.println("Invalid credentials or null user returned.");
                JOptionPane.showMessageDialog(this, "Invalid credentials", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }).exceptionally(ex -> {
            System.out.println("Error during login: " + ex.getMessage());
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        });
    
    }
    

    private void handleRegister(JComboBox<Role> roleComboBox) {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Username and password cannot be empty", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

       String userId = "User-" + (int) (Math.random() * Integer.MAX_VALUE);

        Role selectedRole = (Role) roleComboBox.getSelectedItem();
        UsersModel newUser = new UsersModel(userId, username, password, selectedRole);

        authViewModel.register(newUser)
                .thenAccept(isRegistered -> {
                    if (isRegistered) {
                        JOptionPane.showMessageDialog(this, "Registration Successful! Please log in.");
                        showLoginForm();
                        usernameField.setText(""); 
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

    private void routeToSellerPage(String id , String user) {
        JOptionPane.showMessageDialog(this, "Welcome to the Seller Dashboard!");
        this.setVisible(false);
        SellerView sellerView = new SellerView(productViewModel, id , user);


        sellerView.setVisible(true);
    }

    private void routeToBuyerPage(String id) {
        JOptionPane.showMessageDialog(this, "Welcome to the Buyer Dashboard!");
        this.setVisible(false);
       UserView userView = new UserView(productViewModel, id);
       userView.setVisible(true);
    }

    private JPanel createFieldPanel(String labelText, JComponent inputField) {
        JPanel fieldPanel = new JPanel();
        fieldPanel.setOpaque(false);
        fieldPanel.setLayout(new BoxLayout(fieldPanel, BoxLayout.Y_AXIS)); // Stack the label and input vertically
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Arial", Font.PLAIN, 16));
        label.setOpaque(false);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);

        fieldPanel.add(label); // Add the label
        fieldPanel.add(inputField); // Add the input field
        return fieldPanel;
    }

    private void setComponentSize(JComponent component, int height, int width) {
        component.setMaximumSize(new Dimension(width, height));
        component.setPreferredSize(new Dimension(width, height));
    }

}
