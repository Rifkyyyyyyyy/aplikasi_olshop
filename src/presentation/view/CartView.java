
package presentation.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
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
        this.viewModel = viewModel;
        this.userView = userView;

        // Mengatur properti frame
        setTitle("Keranjang Belanja");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Panel atas untuk tombol "Kembali" dan judul
        JPanel topPanel = new JPanel(new BorderLayout());

        // Tombol "Kembali"
        JButton backButton = new JButton("Kembali");
        backButton.addActionListener(_ -> goBackToUserView());
        topPanel.add(backButton, BorderLayout.WEST);

        // Judul
        JLabel titleLabel = new JLabel("Keranjang Anda", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        topPanel.add(titleLabel, BorderLayout.CENTER);

        add(topPanel, BorderLayout.NORTH);

        // Panel keranjang belanja
        JPanel cartPanel = new JPanel();
        cartPanel.setLayout(new BoxLayout(cartPanel, BoxLayout.Y_AXIS));
        cartPanel.setBackground(Color.WHITE);
        cartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15)); // Padding 15px

        if (cart.isEmpty()) {
            JLabel emptyLabel = new JLabel("Keranjang Anda kosong.", SwingConstants.CENTER);
            emptyLabel.setFont(new Font("Arial", Font.ITALIC, 16));
            cartPanel.add(emptyLabel);
        } else {
            for (Map.Entry<ProductModel, Integer> entry : cart.entrySet()) {
                JPanel productPanel = createCartItem(entry.getKey(), entry.getValue());
                cartPanel.add(productPanel);
                cartPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Spacing antara item
            }
        }

        JScrollPane scrollPane = new JScrollPane(cartPanel);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        add(scrollPane, BorderLayout.CENTER);

        // Panel bawah dengan total harga dan tombol checkout
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
        JPanel card = new JPanel();
        card.setLayout(new BorderLayout(10, 10));
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        card.setBackground(Color.WHITE);
        card.setPreferredSize(new Dimension(0, 150)); // Set tinggi menjadi 150px, lebar dinamis

        JLabel nameLabel = new JLabel(product.getName());
        nameLabel.setFont(new Font("Arial", Font.BOLD, 14));

        JLabel quantityLabel = new JLabel("Jumlah: " + quantity);
        JLabel priceLabel = new JLabel("Harga: Rp." + (product.getPrice() * quantity));

        JPanel detailsPanel = new JPanel();
        detailsPanel.setLayout(new BoxLayout(detailsPanel, BoxLayout.Y_AXIS));
        detailsPanel.setBackground(Color.WHITE);
        detailsPanel.add(nameLabel);
        detailsPanel.add(Box.createVerticalStrut(5)); // Spacing
        detailsPanel.add(quantityLabel);
        detailsPanel.add(priceLabel);

        card.add(detailsPanel, BorderLayout.CENTER);

        return card;
    }

    /**
     * Metode untuk menghitung total harga dalam keranjang.
     * 
     * @return Total harga semua produk dalam keranjang.
     */
    private double calculateTotal() {
        return cart.entrySet().stream()
                .mapToDouble(entry -> entry.getKey().getPrice() * entry.getValue())
                .sum();
    }

    /**
     * Metode untuk melakukan checkout dan membeli produk dalam keranjang.
     */
    private void checkout() {
        try {
            for (Map.Entry<ProductModel, Integer> entry : cart.entrySet()) {
                ProductModel product = entry.getKey();
                int quantity = entry.getValue();
                
                // Logika pembelian
                String productId = product.getId();
                viewModel.purchase(productId, quantity);
            }

            JOptionPane.showMessageDialog(this, "Checkout selesai!");
            this.setVisible(false); // Menutup tampilan Keranjang
            userView.setVisible(true); // Menampilkan tampilan Pengguna

            // Memuat ulang produk di tampilan Pengguna
            userView.reloadProducts();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Checkout gagal: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
           
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
