package presentation.view;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import components.button.RoundedButton;
import components.radius.RadiusBorder;
import constant.role.Role;
import constant.style.ColorsApp;
import domain.model.users.UsersModel;
import presentation.viewModel.auth.AuthViewModel;
import presentation.viewModel.balance.BalanceViewModel;
import presentation.viewModel.product.ProductViewModel;
import utils.BaseFunc;

public class AuthView extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;


    private final AuthViewModel authViewModel;
    private  final ProductViewModel productViewModel;
    private final BalanceViewModel balanceViewModel;


    private final JPanel cardPanel;
    private final CardLayout cardLayout;

    public AuthView(AuthViewModel authViewModel, ProductViewModel productViewModel , BalanceViewModel viewModel)  {
        this.authViewModel = authViewModel;
      this.balanceViewModel = viewModel;
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
        wrapperPanel.setLayout(new GridLayout(1, 2, 5, 5));  // Horizontal gap of 5, Vertical gap of 5

           wrapperPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0)); // No padding/margin

        // Left section with the new green color (#238b45)
        JPanel leftSection = new JPanel();
        leftSection.setBackground(ColorsApp.PRIMARY); // Set background to #238b45
        leftSection.setPreferredSize(new Dimension(0, this.getHeight())); // Full height, width will adjust based on
                                                                          // layout
        leftSection.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE)); 
                                                                                         
        leftSection.setLayout(new GridLayout(0 , 1));
        leftSection.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Add logo and text inside the green section
    JLabel logoLabel = new JLabel();
ImageIcon logoIcon = new ImageIcon("assets/logo.png"); // Replace with your actual logo path
logoLabel.setIcon(logoIcon);
logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // Center the logo horizontally
logoLabel.setHorizontalAlignment(SwingConstants.CENTER); // Center the icon horizontally within the label

// Set the size of the logo to fit the full width of the container
logoLabel.setPreferredSize(new Dimension(leftSection.getWidth(), logoIcon.getIconHeight())); // containerWidth should be the width of your container
logoLabel.setMaximumSize(new Dimension(leftSection.getWidth(), logoIcon.getIconHeight())); // Set max width to container width
logoLabel.setMinimumSize(new Dimension(leftSection.getWidth(), logoIcon.getIconHeight())); // Set min width to container width


        JPanel footerLabelPanel = new JPanel();
        footerLabelPanel.setOpaque(false);
        footerLabelPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20)); 
        footerLabelPanel.setLayout(new BoxLayout(footerLabelPanel, BoxLayout.Y_AXIS)); // Gunakan BoxLayout untuk pengaturan vertikal
        
        // Header text
        JLabel headerText = new JLabel("Selamat datang di Yoto App!");
        headerText.setFont(new Font("Arial", Font.BOLD, 18)); 
        headerText.setAlignmentX(JLabel.CENTER_ALIGNMENT); 
        headerText.setForeground(Color.WHITE);
        
        // Description text
        JLabel descriptionText = new JLabel(
            "<html><div style='text-align: center;'>"
            + "Gabung sekarang menjadi seller untuk mendapatkan keuntungan lebih besar, "
            + "atau menjadi buyer untuk menikmati berbagai produk berkualitas dengan harga terbaik."
            + "</div></html>"
        );
        descriptionText.setFont(new Font("Arial", Font.PLAIN, 14)); // Set font style for the text
        descriptionText.setAlignmentX(JLabel.CENTER_ALIGNMENT); // Center horizontally
        descriptionText.setForeground(Color.WHITE);
        
        // Tambahkan jarak antar teks menggunakan rigid area
        footerLabelPanel.add(headerText);
        footerLabelPanel.add(Box.createRigidArea(new Dimension(0, 40))); // Jarak 10 piksel
        footerLabelPanel.add(descriptionText);
        
        // Tambahkan panel ke leftSection
        leftSection.add(logoLabel);
        leftSection.add(footerLabelPanel);
        

    
        // Right section with the white background for forms (fills full height)
        JPanel rightSection = new JPanel();
        rightSection.setLayout(new GridLayout(0 , 1));
        rightSection.setBackground(Color.WHITE);
        rightSection.setPreferredSize(new Dimension(0, this.getHeight())); // Full height, width will adjust based on
                                                                           // layout
        rightSection.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE)); // Ensure it fills available
                                                                                          // space proportionally
        rightSection.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40)); // Padding inside the form

        // Add a header for the login form
        JLabel headerLabel = new JLabel("Masuk Sekarang");
        headerLabel.setAlignmentX(CENTER_ALIGNMENT);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 32));
        rightSection.add(headerLabel);

        // Form elements for login
        usernameField = new JTextField(20);
        passwordField = new JPasswordField(20);
        


        // Set max height and width for buttons and input fields, also set rounded
        // corners
        setComponentSize(usernameField, 50, 300);
        setComponentSize(passwordField, 50, 300);


        // Apply styles: rounded corners (8px) and bottom margin (20px)
                                                     
        passwordField.setBorder(BorderFactory.createCompoundBorder(
                passwordField.getBorder(), BorderFactory.createEmptyBorder(0, 0, 20, 0)));
       
   
                

        usernameField.setFont(new Font("Arial", Font.PLAIN, 16)); // Font style
        passwordField.setFont(new Font("Arial", Font.PLAIN, 16));
 


        usernameField.setBorder(BorderFactory.createCompoundBorder(
            new RadiusBorder(15), // Set the custom round border with 15px radius
            new EmptyBorder(5, 5, 5, 5)  // Add internal padding
        ));

        passwordField.setBorder(BorderFactory.createCompoundBorder(
            new RadiusBorder(15), // Set the custom round border with 15px radius
            new EmptyBorder(5, 5, 5, 5)  // Add internal padding
        ));



        // Add labels and fields to the right section (forms)
        rightSection.add(createFieldPanel("Username:", usernameField));
        rightSection.add(createFieldPanel("Password:", passwordField));
        rightSection.add(Box.createVerticalStrut(20)); // Space between fields and buttons

        // Create a panel to hold the buttons in a horizontal row
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.X_AXIS)); // Horizontal layout
        buttonsPanel.setOpaque(false);
      
       

        rightSection.add(buttonsPanel);


        wrapperPanel.add(leftSection);
        wrapperPanel.add(rightSection);

        // Add wrapper panel to the card layout
        cardPanel.add(wrapperPanel, "login");


        ActionListener loginListener = (ActionEvent e) -> {
            showLoginForm();
        };
        ActionListener registerListener = (ActionEvent e) -> {
            showRegisterForm();
        };

        // Add action listeners
     

        RoundedButton loginButton = new RoundedButton("Log in", loginListener, 180, 55 , ColorsApp.PRIMARY2 , Color.white);
        
        RoundedButton registerButton = new RoundedButton(
            "Register",     
            registerListener,  
            180,             
            55,               
            new Color(240, 240, 240),
            Color.BLACK        
        );
        

        buttonsPanel.add(loginButton);
        buttonsPanel.add(Box.createHorizontalStrut(20)); // Space between buttons (optional)
        buttonsPanel.add(registerButton);

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
    
        JButton backToLoginButton = new JButton("Back to Login");

        // Set max height and width for buttons and input fields
        setComponentSize(usernameField, 50, 300);
        setComponentSize(passwordField, 50, 300);
        // setComponentSize(registerButton, 50, 300);
        setComponentSize(backToLoginButton, 50, 300);

        // Add labels and fields to the right section (forms)
        rightSection.add(createFieldPanel("Username:", usernameField));
        rightSection.add(createFieldPanel("Password:", passwordField));
        rightSection.add(createFieldPanel("Role:", roleComboBox));
        rightSection.add(Box.createVerticalStrut(20));
        // rightSection.add(registerButton);
        rightSection.add(Box.createVerticalStrut(10));
        rightSection.add(backToLoginButton);

        // Add the left (green) and right (white forms) sections to the wrapper panel
        wrapperPanel.add(leftSection);
        wrapperPanel.add(rightSection);

        // Add wrapper panel to the card layout
        cardPanel.add(wrapperPanel, "register");

        // Add action listeners
        // registerButton.addActionListener(s -> handleRegister(roleComboBox));
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
    
        authViewModel.login(username, BaseFunc.sha256(password))
        .thenAccept(user -> {
            System.out.println("user: " + user); 
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
        UsersModel newUser = new UsersModel(userId, username, BaseFunc.sha256(password), selectedRole);

        authViewModel.register(newUser)
                .thenAccept(isRegistered -> {
                    if (isRegistered) {
                        JOptionPane.showMessageDialog(this, "Registration Successful! Please log in.");
                        showLoginForm();
                        usernameField.setText(""); 
                        passwordField.setText(""); 
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
        SellerView sellerView = new SellerView(productViewModel, id , user , balanceViewModel);


        sellerView.setVisible(true);
    }

    private void routeToBuyerPage(String id) {
        JOptionPane.showMessageDialog(this, "Welcome to the Buyer Dashboard!");
        this.setVisible(false);
       UserView userView = new UserView(productViewModel, id , balanceViewModel);
       userView.setVisible(true);
    }

    private JPanel createFieldPanel(String labelText, JComponent inputField) {
        JPanel fieldPanel = new JPanel();
        fieldPanel.setOpaque(false);
        fieldPanel.setLayout(new GridLayout(0 , 1));
        
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Arial", Font.PLAIN, 14));  // Set font style, size, and name
        label.setForeground(Color.BLACK);  // Set the font color        
        label.setOpaque(false);
        
        // Align the label to the left
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
    
        // Add bottom margin to the label using EmptyBorder
        label.setBorder(new EmptyBorder(0, 0, 10, 0)); // 10px margin at the bottom
    
        fieldPanel.add(label); // Add the label
        fieldPanel.add(inputField); // Add the input field
        
        return fieldPanel;
    }
    

    private void setComponentSize(JComponent component, int height, int width) {
        component.setMaximumSize(new Dimension(width, height));
        component.setPreferredSize(new Dimension(width, height));
    }

}