package presentation.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.HashMap;
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

import domain.model.product.ProductModel;
import presentation.viewModel.balance.BalanceViewModel;
import presentation.viewModel.product.ProductViewModel;

public class UserView extends JFrame {
    private final ProductViewModel viewModel;
    private final JPanel productPanel;
    private final BalanceViewModel balanceViewModel;
    private final JProgressBar progressBar;
    private final Map<ProductModel, Integer> cart; // Menyimpan produk dengan jumlah
    private final String user;
    /**
     * Konstruktor untuk menginisialisasi tampilan pengguna.
     * 
     * @param viewModel Model tampilan produk
     * @param user Nama pengguna yang akan ditampilkan
     */
    public UserView(ProductViewModel viewModel, String user , BalanceViewModel view) {
        this.viewModel = viewModel;
        this.cart = new HashMap<>(); // Inisialisasi keranjang
        this.user = user;
        this.balanceViewModel = view;

        setTitle("Tampilan Pengguna");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel Header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Menambahkan padding ke panel header

        JLabel welcomeLabel = new JLabel("Halo, " + user + "!", SwingConstants.LEFT);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 18));

        ImageIcon cartIcon = new ImageIcon("assets/cart.png"); // Ganti dengan path ikon keranjang
        JLabel cartLabel = new JLabel(cartIcon);
        cartLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        cartLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                openCartView(); // Membuka tampilan keranjang
            }
        });

        headerPanel.add(welcomeLabel, BorderLayout.WEST);
        headerPanel.add(cartLabel, BorderLayout.EAST);
        add(headerPanel, BorderLayout.NORTH);

        // Panel Daftar Produk
        productPanel = new JPanel();
        productPanel.setLayout(new BoxLayout(productPanel, BoxLayout.Y_AXIS));
        productPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Menambahkan padding ke panel produk

        JScrollPane scrollPane = new JScrollPane(productPanel);
        add(scrollPane, BorderLayout.CENTER);

        // Panel Bawah dengan Progress Bar
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        progressBar = new JProgressBar();
        progressBar.setIndeterminate(true);
        progressBar.setVisible(false); // Menyembunyikan progress bar saat tidak digunakan
        bottomPanel.add(progressBar);
        add(bottomPanel, BorderLayout.SOUTH);

        setLocationRelativeTo(null);
        loadAllProducts(); // Memuat produk saat aplikasi dimulai
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
            productPanel.removeAll(); // Menghapus card sebelumnya
            for (ProductModel product : products) {
                JPanel productCard = createProductCard(product);
                productPanel.add(productCard);
    
                // Menambahkan spacing antar card
                productPanel.add(Box.createVerticalStrut(10)); // Jarak antar card 10px
            }
            productPanel.revalidate(); // Menyegarkan tampilan
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
    
        // Menambahkan label Nama Produk
        JLabel nameLabel = new JLabel(product.getName());
        nameLabel.setFont(new Font("Arial", Font.BOLD, 14));
        card.add(nameLabel);
    
        // Menambahkan label Deskripsi Produk
        JLabel descriptionLabel = new JLabel("<html>" + product.getDescription() + "</html>");
        card.add(descriptionLabel);
    
        // Menambahkan label Harga Produk
        JLabel priceLabel = new JLabel("Harga: Rp." + product.getPrice());
        card.add(priceLabel);
    
        // Menambahkan label Stok Produk
        JLabel stockLabel = new JLabel("Stok: " + product.getStock());
        card.add(stockLabel);

        JLabel sellerLabel = new JLabel("Penjual: " + product.getSellerName());
        card.add(sellerLabel);
    
        // Menambahkan margin top sebelum tombol
        card.add(Box.createVerticalStrut(10)); // Margin top 10px sebelum tombol
    
        // Tombol untuk menambahkan produk ke keranjang
        JButton addToCartButton = new JButton("Tambahkan ke Keranjang");
        addToCartButton.addActionListener(e -> addToCart(product)); // Menambah produk ke keranjang saat diklik
        card.add(addToCartButton);
    
        // Menambahkan margin bottom pada card
        card.setBorder(BorderFactory.createEmptyBorder(10, 10, 20, 10)); // Margin bottom 20px
    
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
            JOptionPane.showMessageDialog(this, "Stok habis!"); // Menampilkan pesan jika stok habis
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
                    JOptionPane.showMessageDialog(this, "Ditambahkan " + quantity + " " + product.getName() + " ke keranjang.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Input tidak valid. Masukkan angka.");
            }
        }
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
