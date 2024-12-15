package presentation.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;  
import javax.swing.JButton;
import javax.swing.JFrame;  
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import components.button.RoundedButton;
import constant.style.ColorsApp;
import domain.model.product.ProductModel;
import presentation.viewModel.auth.AuthViewModel;
import presentation.viewModel.balance.BalanceViewModel;
import presentation.viewModel.product.ProductViewModel;

public class UserView extends JFrame {
    private final ProductViewModel viewModel;
    private final AuthViewModel authViewModel;
    private final JPanel productPanel;
    private final BalanceViewModel balanceViewModel;
    private final JProgressBar progressBar;

    private final Map<ProductModel, Integer> cart; 
    private final String user;

    private final JLabel cartCountLabel = new JLabel("0"); 
    

    public JLabel cartLabel () {
        return  this.cartCountLabel;
    }

    /**
     * Konstruktor untuk menginisialisasi tampilan pengguna.
     * 
     * @param viewModel Model tampilan produk
     * @param user Nama pengguna yang akan ditampilkan
     */

     public UserView(ProductViewModel viewModel, String user, BalanceViewModel view, AuthViewModel auth) {
        this.viewModel = viewModel;
        this.cart = new HashMap<>();
        this.user = user;
        this.balanceViewModel = view;
        this.authViewModel = auth;
    
        setTitle("Homepage");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
    
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(ColorsApp.LIGHT_GRAY);
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.X_AXIS));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
    
        JLabel welcomeLabel = new JLabel("Halo, " + user + "!");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 18));
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
    
        JLabel logoLabel = new JLabel();
        ImageIcon logoIcon = new ImageIcon("assets/logo2.png");
        logoLabel.setIcon(logoIcon);
        logoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
    
        JPanel cartPanel = new JPanel();
        cartPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        cartPanel.setBackground(ColorsApp.LIGHT_GRAY2);
        cartPanel.setLayout(new BoxLayout(cartPanel, BoxLayout.X_AXIS));
    
        ImageIcon cartIcon = new ImageIcon("assets/cart.png");
        JLabel cartLabel = new JLabel();
        Image scaledImage = cartIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        cartLabel.setIcon(new ImageIcon(scaledImage));
        cartLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        cartLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        cartLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                openCartView();
            }
        });
    
        cartCountLabel.setFont(new Font("Arial", Font.BOLD, 14));
        cartCountLabel.setForeground(Color.RED);
        cartPanel.add(cartLabel);
        cartPanel.add(Box.createHorizontalStrut(10));
        cartPanel.add(cartCountLabel);
    
        headerPanel.add(welcomeLabel);
        headerPanel.add(Box.createHorizontalGlue());
        headerPanel.add(logoLabel);
        headerPanel.add(Box.createHorizontalGlue());
        headerPanel.add(cartPanel);
    
        add(headerPanel, BorderLayout.NORTH);
    
        productPanel = new JPanel();
        productPanel.setLayout(new BoxLayout(productPanel, BoxLayout.Y_AXIS));
        productPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    
        JScrollPane scrollPane = new JScrollPane(productPanel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); // Hide horizontal scrollbar
scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);   // Hide vertical scrollbar
        scrollPane.setBorder(null);
        add(scrollPane, BorderLayout.CENTER);
    
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        progressBar = new JProgressBar();
        progressBar.setIndeterminate(true);
        progressBar.setVisible(false);
        bottomPanel.add(progressBar);
    
        add(bottomPanel, BorderLayout.SOUTH);
    
        JPanel footerPanel = new JPanel();
        footerPanel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(169, 169, 169)));
        footerPanel.setBackground(ColorsApp.LIGHT_GRAY);
        footerPanel.setLayout(new BorderLayout());
    
        JButton buttonPanel = new JButton();
        ImageIcon logoutIcon = new ImageIcon("assets/logout.png");
        Image scaledLogoutImage = logoutIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        buttonPanel.setIcon(new ImageIcon(scaledLogoutImage));
    
        buttonPanel.setContentAreaFilled(false);
        buttonPanel.setBorderPainted(false);
        buttonPanel.setFocusPainted(false);
        buttonPanel.setPreferredSize(new Dimension(40, 50));
        buttonPanel.setMaximumSize(new Dimension(40, 50));
    
        buttonPanel.addActionListener((ActionEvent e) -> {
            logout();
        });
    
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        JLabel footerLabel = new JLabel("© " + currentYear + " Yoto app | All Rights Reserved");
        footerLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        footerLabel.setOpaque(false);
        footerLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
    
        JPanel logoutPanel = new JPanel();
        logoutPanel.setOpaque(false);
        logoutPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        logoutPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        logoutPanel.add(buttonPanel);
    
        JPanel textPanel = new JPanel();
        textPanel.setOpaque(false);
        textPanel.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 15));
        textPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        textPanel.add(footerLabel);
    
        footerPanel.add(logoutPanel, BorderLayout.WEST);
        footerPanel.add(textPanel, BorderLayout.EAST);
    
        add(footerPanel, BorderLayout.SOUTH);
    
        setLocationRelativeTo(null);
        loadAllProducts();
    }
    
    
    private void logout() {
        dispose();
        AuthView authView = new AuthView(authViewModel, viewModel, balanceViewModel);
        authView.display();
    }
 
    
    public String userView () {
        return user;
    }

    /**
     * Metode untuk memuat semua produk dan menampilkannya di panel.
     */
    private void loadAllProducts() {
        progressBar.setVisible(true);
        viewModel.getAllProducts().thenAccept(products -> {
            productPanel.removeAll();
            for (ProductModel product : products) {
                JPanel productCard = createProductCard(product);
                productPanel.add(productCard);
                productPanel.add(Box.createVerticalStrut(10));
            }
            productPanel.revalidate();
            productPanel.repaint();
            progressBar.setVisible(false);
        }).exceptionally(e -> {
            JOptionPane.showMessageDialog(this, "Gagal mengambil produk: " + e.getMessage());
            progressBar.setVisible(false);
            return null;
        });
    }
    

    /**
     * Metode untuk memuat ulang produk dan memperbarui stok.
     */
    public void reloadProducts() {
        loadAllProducts(); // Memanggil ulang untuk memperbarui stok
    }

    /**
     * Metode untuk membuat card produk.
     * 
     * @param product Produk yang akan ditampilkan pada card
     * @return Panel yang berisi card produk
     */

     private JPanel createProductCard(ProductModel product) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        card.setBackground(Color.WHITE);
    
        JLabel nameLabel = new JLabel(product.getName());
        nameLabel.setFont(new Font("Arial", Font.BOLD, 16));
        card.add(nameLabel);
        card.add(Box.createVerticalStrut(5));
    
        JLabel descriptionLabel = new JLabel("<html>" + product.getDescription() + "</html>");
        descriptionLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        card.add(descriptionLabel);
        card.add(Box.createVerticalStrut(5));
    
        JLabel priceLabel = new JLabel("Harga: Rp." + product.getPrice());
        priceLabel.setFont(new Font("Arial", Font.BOLD, 14));
        card.add(priceLabel);
        card.add(Box.createVerticalStrut(5));
    
        JLabel stockLabel = new JLabel("Stok: " + product.getStock());
        stockLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        card.add(stockLabel);
        card.add(Box.createVerticalStrut(5));
    
        JLabel sellerLabel = new JLabel("Penjual: " + product.getSellerName());
        sellerLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        card.add(sellerLabel);
        card.add(Box.createVerticalStrut(10));
    
        ActionListener listener = (ActionEvent e) -> {
            addToCart(product);
        };
        RoundedButton aRoundedButton = new RoundedButton("Beli Produk", listener, 150, 40, ColorsApp.PRIMARY, ColorsApp.LIGHT_GRAY);
        card.add(aRoundedButton);
    
        card.setBorder(BorderFactory.createEmptyBorder(10, 10, 20, 10));
    
        return card;
    }
    
    

    /**
     * Metode untuk menambahkan produk ke keranjang.
     * 
     * @param product Produk yang akan ditambahkan ke keranjang
     */
    private void addToCart(ProductModel product) {
        int currentStock = product.getStock();
        if (currentStock <= 0) {
            JOptionPane.showMessageDialog(this, "Yah Stok habis!"); 
            return;
        }

        // Meminta jumlah yang ingin dibeli
        String input = JOptionPane.showInputDialog(this, "Masukkan jumlah (Maks: " + currentStock + "):", 1);
        if (input != null) {
            try {
                int quantity = Integer.parseInt(input);
                if (quantity <= 0 || quantity > currentStock) {
                    JOptionPane.showMessageDialog(this, "Jumlah tidak valid. Masukkan nilai antara 1 dan " + currentStock + ".");
                } else {
                    cart.put(product, cart.getOrDefault(product, 0) + quantity); // Menambahkan jumlah produk ke keranjang
                    updateCounter();
                    JOptionPane.showMessageDialog(this, "Ditambahkan " + quantity + " " + product.getName() + " ke keranjang.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Input tidak valid. Masukkan angka.");
            }
        }
    }
    
    private void updateCounter() {
        int counter = 0;
        List<ProductModel> temp = new ArrayList<>();
        
        for (Map.Entry<ProductModel, Integer> entry : cart.entrySet()) {
            temp.add(entry.getKey());
        }
    
        while (counter < temp.size()) {
            counter += 1;
        }
    
        cartCountLabel.setText(String.valueOf(counter));
    }
    
    /**
     * Metode untuk membuka tampilan keranjang belanja.
     */
    private void openCartView() {
        CartView cartView = new CartView(cart, viewModel, this , balanceViewModel); // Membuka tampilan keranjang
        this.setVisible(false); // Menyembunyikan tampilan pengguna
        cartView.setVisible(true); // Menampilkan tampilan keranjang
    }
}
