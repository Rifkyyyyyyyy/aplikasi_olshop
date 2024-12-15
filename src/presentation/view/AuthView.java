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

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
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
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        add(cardPanel, BorderLayout.CENTER);

        showLoginForm();
    }

    public void display() {
        this.setVisible(true);
    }

    public void refreshLayout () {
        this.revalidate();
    }



    private void showLoginForm() {
        JPanel wrapperPanel = new JPanel();
        wrapperPanel.setLayout(new GridLayout(1, 2, 5, 5));
        wrapperPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
    
        JPanel leftSection = new JPanel();
        leftSection.setBackground(ColorsApp.PRIMARY);
        leftSection.setPreferredSize(new Dimension(0, this.getHeight()));
        leftSection.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));
        leftSection.setLayout(new GridLayout(0 , 1));
        leftSection.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    
        JLabel logoLabel = new JLabel();
        ImageIcon logoIcon = new ImageIcon("assets/logo.png");
        logoLabel.setIcon(logoIcon);
        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        logoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        logoLabel.setPreferredSize(new Dimension(leftSection.getWidth(), logoIcon.getIconHeight()));
        logoLabel.setMaximumSize(new Dimension(leftSection.getWidth(), logoIcon.getIconHeight()));
        logoLabel.setMinimumSize(new Dimension(leftSection.getWidth(), logoIcon.getIconHeight()));
    
        JPanel footerLabelPanel = new JPanel();
        footerLabelPanel.setOpaque(false);
        footerLabelPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));
        footerLabelPanel.setLayout(new BoxLayout(footerLabelPanel, BoxLayout.Y_AXIS));
    
        JLabel headerText = new JLabel("<html><div style='text-align: center;'>Selamat datang di Yoto App! Nikmati pengalaman berbelanja dan berjualan yang lebih mudah</div></html>");
        headerText.setFont(new Font("Arial", Font.BOLD, 18));
        headerText.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        headerText.setForeground(Color.WHITE);
    
        JLabel descriptionText = new JLabel(
            "<html><div style='text-align: center;'>"
            + "Gabung sebagai seller untuk meraih keuntungan lebih besar, "
            + "atau sebagai buyer untuk menikmati berbagai produk berkualitas dengan harga terbaik."
            + "</div></html>"
        );
        descriptionText.setFont(new Font("Arial", Font.PLAIN, 14));
        descriptionText.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        descriptionText.setForeground(Color.WHITE);
    
        footerLabelPanel.add(headerText);
        footerLabelPanel.add(Box.createRigidArea(new Dimension(0, 40)));
        footerLabelPanel.add(descriptionText);
    
        leftSection.add(logoLabel);
        leftSection.add(footerLabelPanel);
    
        JPanel rightSection = new JPanel();
        rightSection.setLayout(new GridLayout(0 , 1));
        rightSection.setBackground(Color.WHITE);
        rightSection.setPreferredSize(new Dimension(0, this.getHeight()));
        rightSection.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));
        rightSection.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
    
        JLabel headerLabel = new JLabel("Masuk Sekarang");
        headerLabel.setAlignmentX(CENTER_ALIGNMENT);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 32));
        rightSection.add(headerLabel);
    
        usernameField = new JTextField(20);
        passwordField = new JPasswordField(20);
    
        setComponentSize(usernameField, 50, 300);
        setComponentSize(passwordField, 50, 300);
    
        passwordField.setBorder(BorderFactory.createCompoundBorder(
            passwordField.getBorder(), BorderFactory.createEmptyBorder(0, 0, 20, 0)));
    
        usernameField.setFont(new Font("Arial", Font.PLAIN, 16));
        passwordField.setFont(new Font("Arial", Font.PLAIN, 16));
    
        usernameField.setBorder(BorderFactory.createCompoundBorder(
            new RadiusBorder(15),
            new EmptyBorder(5, 5, 5, 5)
        ));
    
        passwordField.setBorder(BorderFactory.createCompoundBorder(
            new RadiusBorder(15),
            new EmptyBorder(5, 5, 5, 5)
        ));
    
        rightSection.add(createFieldPanel("Username:", usernameField));
        rightSection.add(createFieldPanel("Password:", passwordField));
        rightSection.add(Box.createVerticalStrut(20));
    
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.X_AXIS));
        buttonsPanel.setOpaque(false);
    
        rightSection.add(buttonsPanel);
    
        wrapperPanel.add(leftSection);
        wrapperPanel.add(rightSection);
    
        cardPanel.add(wrapperPanel, "login");
    
        ActionListener loginListener = (ActionEvent e) -> {
            handleLogin();
        };
        ActionListener registerListener = (ActionEvent e) -> {
            showRegisterForm();
        };
    
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
        buttonsPanel.add(Box.createHorizontalStrut(20));
        buttonsPanel.add(registerButton);
    
        cardLayout.show(cardPanel, "login");
    }
    

   

    private void showRegisterForm() {
        UIManager.put("ComboBox.border", BorderFactory.createEmptyBorder());
    
        JPanel registerPanel = new JPanel();
        registerPanel.setLayout(new GridLayout(1, 2, 5, 5));
        registerPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
    
        // Left Section
        JPanel leftSection = new JPanel();
        leftSection.setBackground(ColorsApp.PRIMARY);
        leftSection.setPreferredSize(new Dimension(0, this.getHeight()));
        leftSection.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));
        leftSection.setLayout(new GridLayout(0, 1));
        leftSection.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    
        JLabel logoLabel = new JLabel();
        ImageIcon logoIcon = new ImageIcon("assets/logo.png");
        logoLabel.setIcon(logoIcon);
        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        logoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        logoLabel.setPreferredSize(new Dimension(leftSection.getWidth(), logoIcon.getIconHeight()));
        logoLabel.setMaximumSize(new Dimension(leftSection.getWidth(), logoIcon.getIconHeight()));
        logoLabel.setMinimumSize(new Dimension(leftSection.getWidth(), logoIcon.getIconHeight()));
    
        JPanel footerLabelPanel = new JPanel();
        footerLabelPanel.setOpaque(false);
        footerLabelPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));
        footerLabelPanel.setLayout(new BoxLayout(footerLabelPanel, BoxLayout.Y_AXIS));
    
        JLabel headerText = new JLabel("Daftar dan Mulai Perjalananmu di Yoto App!");
        headerText.setFont(new Font("Arial", Font.BOLD, 18));
        headerText.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        headerText.setForeground(Color.WHITE);
    
        JLabel descriptionText = new JLabel(
            "<html><div style='text-align: center;'>"
            + "Jadilah bagian dari Yoto App, tempat terbaik untuk mendapatkan produk berkualitas dengan harga terjangkau, "
            + "atau memulai bisnis dengan potensi keuntungan yang besar,"
            + "Sudah punya akun? Masuk sekarang untuk melanjutkan dan nikmati pengalaman belanja atau jualan yang lebih mudah!"
            + "</div></html>"
        );
        descriptionText.setFont(new Font("Arial", Font.PLAIN, 14));
        descriptionText.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        descriptionText.setForeground(Color.WHITE);
    
        footerLabelPanel.add(headerText);
        footerLabelPanel.add(Box.createRigidArea(new Dimension(0, 40)));
        footerLabelPanel.add(descriptionText);
    
        leftSection.add(logoLabel);
        leftSection.add(footerLabelPanel);
    
        // Right Section
        JPanel rightSection = new JPanel();
        rightSection.setLayout(new GridLayout(0, 1));
        rightSection.setBackground(Color.WHITE);
        rightSection.setPreferredSize(new Dimension(0, this.getHeight()));
        rightSection.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));
        rightSection.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
    
        JLabel headerLabel = new JLabel("Daftar Sekarang");
        headerLabel.setAlignmentX(CENTER_ALIGNMENT);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 32));
        rightSection.add(headerLabel);
    
        JTextField registerUsernameField = new JTextField(20);
        JPasswordField registerPasswordField = new JPasswordField(20);
    
        setComponentSize(registerUsernameField, 50, 300);
        setComponentSize(registerPasswordField, 50, 300);
    
        registerPasswordField.setBorder(BorderFactory.createCompoundBorder(
            registerPasswordField.getBorder(), BorderFactory.createEmptyBorder(0, 0, 20, 0)
        ));
    
        registerUsernameField.setFont(new Font("Arial", Font.PLAIN, 16));
        registerPasswordField.setFont(new Font("Arial", Font.PLAIN, 16));
    
        registerUsernameField.setBorder(BorderFactory.createCompoundBorder(
            new RadiusBorder(15),
            new EmptyBorder(5, 5, 5, 5)
        ));
    
        registerPasswordField.setBorder(BorderFactory.createCompoundBorder(
            new RadiusBorder(15),
            new EmptyBorder(5, 5, 5, 5)
        ));
    
        rightSection.add(createFieldPanel("Username:", registerUsernameField));
        rightSection.add(createFieldPanel("Password:", registerPasswordField));
    
        // Role ComboBox
        Role[] roles = {Role.BUYER, Role.SELLER};
        JComboBox<Role> roleComboBox = new JComboBox<>(roles);
        roleComboBox.setFont(new Font("Arial", Font.PLAIN, 16));
        roleComboBox.setBackground(new Color(0, 0, 0, 0)); // Transparan
        roleComboBox.setOpaque(false);
    
        // Setting Border
        roleComboBox.setBorder(BorderFactory.createCompoundBorder(
            new RadiusBorder(15),
            BorderFactory.createLineBorder(new Color(0, 0, 0, 0), 1) // Border transparan
        ));
    
        // Renderer for Role ComboBox
        roleComboBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(
                JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus
            ) {
                JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
    
                if (value instanceof Role role) {
                    String formattedText = BaseFunc.capitalizeFirstLetter(role.name());
                    label.setText(formattedText);
                }
    
                label.setOpaque(false);
                label.setBorder(new EmptyBorder(0, 0, 0, 0));
    
                return label;
            }
        });
    
        roleComboBox.setFocusable(false);

        
    
        for (int i = 0; i < roleComboBox.getComponentCount(); i++) {
            Component comp = roleComboBox.getComponent(i);
    
            if (comp instanceof JComponent jComp) {
                jComp.setBorder(new EmptyBorder(0, 0, 0, 0));
                jComp.setOpaque(false);
            }
    
            if (comp instanceof JLabel) {
                comp.setVisible(false);
            }
    
            if (comp instanceof AbstractButton button) {
                button.setBorderPainted(false);
                button.setFocusPainted(false);
            }
        }
    
        roleComboBox.setFocusTraversalKeysEnabled(false);
    
        rightSection.add(createFieldPanel("Role:", roleComboBox));
    
        rightSection.add(Box.createVerticalStrut(20));
    
        // Buttons Panel
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.X_AXIS));
        buttonsPanel.setOpaque(false);
    
        rightSection.add(buttonsPanel);
    
        registerPanel.add(leftSection);
        registerPanel.add(rightSection);
    
        cardPanel.add(registerPanel, "register");
    
        // Action Listeners
        ActionListener backToLogin = (ActionEvent e) -> showLoginForm();
        ActionListener registerListener = (ActionEvent e) -> {
            handleRegister(roleComboBox, registerUsernameField.getText(), new String(registerPasswordField.getPassword()));
        };
    
        RoundedButton loginButton = new RoundedButton(
            "Kembali ke menu login", backToLogin, 180, 55, new Color(240, 240, 240), Color.BLACK
        );
    
        RoundedButton registerButton = new RoundedButton(
            "Register", registerListener, 180, 55, ColorsApp.PRIMARY2, Color.white
        );
    
        buttonsPanel.add(registerButton);
        buttonsPanel.add(Box.createHorizontalStrut(20));
        buttonsPanel.add(loginButton);
    
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
              
                JOptionPane.showMessageDialog(this, "Login Successful: " + user.getRole());
                if (user.getRole().equals(Role.SELLER)) {
                  
                    routeToSellerPage(user.getId() , user.getUsername());
                } else {
                   
                    routeToBuyerPage(user.getUsername());
                }
            } else {
              
                JOptionPane.showMessageDialog(this, "Oops, User tidak ditemukan", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }).exceptionally(ex -> {
            System.out.println("Error during login: " + ex.getMessage());
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        });
    
    }
    

    private void handleRegister(JComboBox<Role> roleComboBox , String username , String password) {


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
        
        this.setVisible(false);
        SellerView sellerView = new SellerView(productViewModel, id , user , balanceViewModel , authViewModel );

       
        sellerView.setVisible(true);
        dispose();
    }

    private void routeToBuyerPage(String id) {
      
        this.setVisible(false);
       UserView userView = new UserView(productViewModel, id , balanceViewModel , authViewModel);
    
       userView.setVisible(true);
       dispose();
    }

    private JPanel createFieldPanel(String labelText, JComponent inputField) {
        JPanel fieldPanel = new JPanel();
        fieldPanel.setOpaque(false);
        fieldPanel.setLayout(new GridLayout(0 , 1));
        
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Arial", Font.PLAIN, 14)); 
        label.setForeground(Color.BLACK);  
        label.setOpaque(false);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        label.setBorder(new EmptyBorder(0, 0, 10, 0)); 
    
        fieldPanel.add(label); 
        fieldPanel.add(inputField); 
        
        return fieldPanel;
    }
    

    private void setComponentSize(JComponent component, int height, int width) {
        component.setMaximumSize(new Dimension(width, height));
        component.setPreferredSize(new Dimension(width, height));
    }

}