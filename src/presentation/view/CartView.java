
package presentation.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import domain.model.product.ProductModel;
import presentation.viewModel.product.ProductViewModel;



public class CartView extends JFrame {
    private final Map<ProductModel, Integer> cart;
    private final Map<ProductModel, JCheckBox> selectedItems; // Map untuk melacak produk yang dipilih
    private final ProductViewModel viewModel;
    private final UserView userView;
    private final JLabel totalLabel;

    /**
     * Konstruktor untuk membuat tampilan keranjang belanja.
     * 
     * @param cart      Peta yang berisi produk dan jumlahnya.
     * @param viewModel ViewModel untuk melakukan operasi terkait produk.
     * @param userView  Tampilan pengguna untuk navigasi balik.
     */
      public CartView(Map<ProductModel, Integer> cart, ProductViewModel viewModel, UserView userView) {
        this.cart = cart;
        this.selectedItems = new HashMap<>(); // Inisialisasi map untuk selected items
        this.viewModel = viewModel;
        this.userView = userView;

        setTitle("Keranjang Belanja");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Panel atas
        JPanel topPanel = new JPanel(new BorderLayout());

        JButton backButton = new JButton("Kembali");
        backButton.addActionListener(_ -> goBackToUserView());
        topPanel.add(backButton, BorderLayout.WEST);

        JLabel titleLabel = new JLabel("Keranjang Anda", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        topPanel.add(titleLabel, BorderLayout.CENTER);

        add(topPanel, BorderLayout.NORTH);

        // Panel keranjang
        JPanel cartPanel = new JPanel();
        cartPanel.setLayout(new BoxLayout(cartPanel, BoxLayout.Y_AXIS));
        cartPanel.setBackground(Color.WHITE);
        cartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        if (cart.isEmpty()) {
            JLabel emptyLabel = new JLabel("Keranjang Anda kosong.", SwingConstants.CENTER);
            emptyLabel.setFont(new Font("Arial", Font.ITALIC, 16));
            cartPanel.add(emptyLabel);
        } else {
            for (Map.Entry<ProductModel, Integer> entry : cart.entrySet()) {
                JPanel productPanel = createCartItem(entry.getKey(), entry.getValue());
                cartPanel.add(productPanel);
                cartPanel.add(Box.createRigidArea(new Dimension(0, 10)));
            }
        }

        JScrollPane scrollPane = new JScrollPane(cartPanel);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        add(scrollPane, BorderLayout.CENTER);

        // Panel bawah
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        totalLabel = new JLabel("Total: " + calculateTotal());
        bottomPanel.add(totalLabel);

        JButton checkoutButton = new JButton("Checkout");
        checkoutButton.addActionListener(_ -> checkout());
        bottomPanel.add(checkoutButton);

        add(bottomPanel, BorderLayout.SOUTH);
    }

 

    /**
     * Metode untuk membuat item produk dalam keranjang.
     * 
     * @param product Produk yang ditambahkan ke keranjang.
     * @param quantity Jumlah produk yang ditambahkan.
     * @return Panel yang berisi informasi produk.
     */
   

     private JPanel createCartItem(ProductModel product, int quantity) {
        JPanel card = new JPanel(new BorderLayout(5, 5)); // Kurangi jarak antar elemen di card
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1),
                BorderFactory.createEmptyBorder(5, 5, 5, 5) // Kurangi padding
        ));
        card.setBackground(Color.WHITE);
    
        JLabel nameLabel = new JLabel(product.getName());
        nameLabel.setFont(new Font("Arial", Font.PLAIN, 12)); // Kurangi ukuran font
    
        JLabel quantityLabel = new JLabel("Jumlah: " + quantity);
        JLabel priceLabel = new JLabel("Harga: Rp." + (product.getPrice() * quantity));
    
        JPanel detailsPanel = new JPanel();
        detailsPanel.setLayout(new BoxLayout(detailsPanel, BoxLayout.Y_AXIS));
        detailsPanel.setBackground(Color.WHITE);
        detailsPanel.add(nameLabel);
        detailsPanel.add(Box.createVerticalStrut(2)); // Kurangi spasi antar elemen
        detailsPanel.add(quantityLabel);
        detailsPanel.add(priceLabel);
    
        JCheckBox selectCheckBox = new JCheckBox("Pilih");
        selectCheckBox.setBackground(Color.WHITE);
        selectCheckBox.addActionListener(e -> totalLabel.setText("Total: " + calculateTotal())); // Update total ketika checkbox berubah
        selectedItems.put(product, selectCheckBox);
    
        card.add(detailsPanel, BorderLayout.CENTER);
        card.add(selectCheckBox, BorderLayout.EAST);
    
        // Atur preferensi tinggi card
        card.setPreferredSize(new Dimension(0, 60)); // Sesuaikan tinggi card
    
        return card;
    }
    
    

    /**
     * Metode untuk menghitung total harga dalam keranjang.
     * 
     * @return Total harga semua produk dalam keranjang.
     */
    private double calculateTotal() {
        return cart.entrySet().stream()
                .filter(entry -> selectedItems.get(entry.getKey()).isSelected()) // Hanya produk yang dipilih
                .mapToDouble(entry -> entry.getKey().getPrice() * entry.getValue())
                .sum();
    }
    

    /**
     * Metode untuk melakukan checkout dan membeli produk dalam keranjang.
     */
    private void checkout() {
        try {
            if (cart == null || cart.isEmpty()) {
                JOptionPane.showMessageDialog(
                    this,
                    "Keranjang kosong. Tidak ada produk untuk dibeli.",
                    "Peringatan",
                    JOptionPane.WARNING_MESSAGE
                );
                return;
            }
    
            if (viewModel == null) {
                JOptionPane.showMessageDialog(
                    this,
                    "Checkout gagal: ViewModel belum diinisialisasi.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
                );
                return;
            }
    
            // Daftar produk yang berhasil dibeli
            List<ProductModel> purchasedProducts = new ArrayList<>();
    
            // Proses hanya item yang dipilih
            for (Map.Entry<ProductModel, Integer> entry : cart.entrySet()) {
                ProductModel productModel = entry.getKey();
                int quantity = entry.getValue();
                JCheckBox checkBox = selectedItems.get(productModel);
    
                if (checkBox != null && checkBox.isSelected()) { // Periksa apakah item dipilih
                    // Logika pembelian
                    if (productModel != null) {
                        String productId = productModel.getId();
                        viewModel.purchase(productId, quantity);
                        purchasedProducts.add(productModel); // Tambahkan ke daftar pembelian
                    }
                }
            }
    
            if (purchasedProducts.isEmpty()) {
                JOptionPane.showMessageDialog(
                    this,
                    "Tidak ada produk yang dipilih untuk dibeli.",
                    "Peringatan",
                    JOptionPane.WARNING_MESSAGE
                );
                return;
            }
    
            // Bersihkan hanya produk yang sudah dibeli dari keranjang
            for (ProductModel purchasedProduct : purchasedProducts) {
                cart.remove(purchasedProduct);
            }
    
            // Tampilkan tampilan sukses transaksi
            TransactionSuccessView transactionSuccessView = new TransactionSuccessView(purchasedProducts, userView);
            transactionSuccessView.setVisible(true);
    
            this.dispose(); // Tutup jendela checkout
    
        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                this,
                "Checkout gagal: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }
    
    
    /**
     * Metode untuk kembali ke tampilan Pengguna.
     */
    private void goBackToUserView() {
        this.setVisible(false); // Menyembunyikan tampilan Keranjang
        userView.setVisible(true); // Menampilkan tampilan Pengguna
    }
}
