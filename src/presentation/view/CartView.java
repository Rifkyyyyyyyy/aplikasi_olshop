
package presentation.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

import components.button.RoundedButton;
import constant.payment.Payment;
import constant.style.ColorsApp;
import domain.model.product.ProductModel;
import presentation.viewModel.balance.BalanceViewModel;
import presentation.viewModel.product.ProductViewModel;



public class CartView extends JFrame {
    private final Map<ProductModel, Integer> cart;
    private final Map<ProductModel, JCheckBox> selectedItems; // Map untuk melacak produk yang dipilih
    private final ProductViewModel viewModel;
    private final UserView userView;
    private final JLabel totalLabel;
    private final BalanceViewModel balanceViewModel;

    /**
     * Konstruktor untuk membuat tampilan keranjang belanja.
     * 
     * @param cart      Peta yang berisi produk dan jumlahnya.
     * @param viewModel ViewModel untuk melakukan operasi terkait produk.
     * @param userView  Tampilan pengguna untuk navigasi balik.
     */
      public CartView(Map<ProductModel, Integer> cart, ProductViewModel viewModel, UserView userView , BalanceViewModel view) {
        this.cart = cart;
        this.selectedItems = new HashMap<>(); // Inisialisasi map untuk selected items
        this.viewModel = viewModel;
        this.userView = userView;
        this.balanceViewModel = view;

        setTitle("Keranjang Belanja");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Panel atas
     // Panel atas
JPanel topPanel = new JPanel(new BorderLayout());
topPanel.setBackground(Color.white);
topPanel.setBorder(BorderFactory.createEmptyBorder(20, 15, 20, 15));

// Create a text button (without background and border)
JButton backButton = new JButton("Kembali");
backButton.setBackground(ColorsApp.LIGHT_GRAY2);
backButton.setContentAreaFilled(false); // Make it text-only
backButton.setBorderPainted(false); // Remove the border
backButton.setFocusPainted(false); // Remove focus outline (optional)
backButton.setFont(new Font("Arial", Font.BOLD, 14)); // Adjust font style and size if needed
backButton.setForeground(ColorsApp.PRIMARY);
backButton.addActionListener(_ -> goBackToUserView());
topPanel.add(backButton, BorderLayout.WEST);

JLabel titleLabel = new JLabel("Keranjang Anda", SwingConstants.CENTER);
titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
topPanel.add(titleLabel, BorderLayout.CENTER);

add(topPanel, BorderLayout.NORTH);


        // Panel keranjang
        JPanel cartPanel = new JPanel();
        cartPanel.setLayout(new BoxLayout(cartPanel, BoxLayout.Y_AXIS));
       
        cartPanel.setBackground(ColorsApp.LIGHT_GRAY2);
        
        
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
        scrollPane.setBackground(ColorsApp.LIGHT_GRAY2);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); // Hide horizontal scrollbar
scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);   // Hide vertical scrollbar
        scrollPane.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createEmptyBorder(15, 15, 15, 15), // Padding
            BorderFactory.createLineBorder(new Color(0, 0, 0, 0), 2) 
        ));
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        add(scrollPane, BorderLayout.CENTER);

        // Panel bawah
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
       // Create a matte border with 1px thickness on the top (with a custom color)
Border border = BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(169, 169, 169));

// Create padding with 15px on all sides
Border padding = BorderFactory.createEmptyBorder(15, 15, 15, 15);

// Combine the border and padding using CompoundBorder
bottomPanel.setBorder(BorderFactory.createCompoundBorder(border, padding));

        bottomPanel.setBackground(ColorsApp.LIGHT_GRAY);
        totalLabel = new JLabel("Total: " + calculateTotal());
        bottomPanel.add(totalLabel);
        bottomPanel.add((Box.createHorizontalStrut(5)));

        ActionListener checkoutListener = (ActionEvent e) -> {
           checkout();
        };


        RoundedButton checkoutButton = new RoundedButton("Checkout", checkoutListener, 130, 40, ColorsApp.PRIMARY2, ColorsApp.LIGHT_GRAY);

      
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
        JPanel card = new JPanel(new BorderLayout(10, 10)); 
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        card.setBackground(Color.WHITE);
        
        JLabel nameLabel = new JLabel(product.getName());
        nameLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        
        JLabel quantityLabel = new JLabel("Jumlah: " + quantity);
        JLabel priceLabel = new JLabel("Harga: Rp." + (product.getPrice() * quantity));
        
        JPanel detailsPanel = new JPanel();
        detailsPanel.setLayout(new BoxLayout(detailsPanel, BoxLayout.Y_AXIS));
        detailsPanel.setBackground(Color.WHITE);
        detailsPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        detailsPanel.add(nameLabel);
        detailsPanel.add(Box.createVerticalStrut(5));
        detailsPanel.add(quantityLabel);
        detailsPanel.add(priceLabel);
        
        JCheckBox selectCheckBox = new JCheckBox("Pilih");
        selectCheckBox.setBackground(Color.WHITE);
        selectCheckBox.setFont(new Font("Arial", Font.PLAIN, 12));
        selectCheckBox.setFocusPainted(false);
        selectCheckBox.setOpaque(false);
        
        selectCheckBox.addActionListener(e -> totalLabel.setText("Total: " + calculateTotal()));
        selectedItems.put(product, selectCheckBox);
        
        JPanel contentPanel = new JPanel(new BorderLayout(10, 10));
        contentPanel.setBackground(Color.WHITE);
        contentPanel.add(detailsPanel, BorderLayout.CENTER);
        contentPanel.add(selectCheckBox, BorderLayout.EAST);
        
        card.add(contentPanel, BorderLayout.CENTER);
        
        card.setMinimumSize(new Dimension(0, 150));
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 150));
        
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

   
     // Refactor fungsi utama checkout
private void checkout() {
    try {




        if (!validateCart()) return;
        if (!validateViewModel()) return;

        Payment selectedPayment = showPaymentSelectionDialog();
        if (selectedPayment == null) return;

        String rekeningNumber = inputRekeningNumber();
        if (rekeningNumber == null) return;

        List<Map<String, Object>> selectedProducts = getSelectedProducts();
        if (selectedProducts.isEmpty()) {
            showWarning("Tidak ada produk yang dipilih untuk dibeli.");
            return;
        }

        processTransaction(selectedProducts, selectedPayment);
    } catch (Exception e) {
        showError("Checkout gagal: " + e.getMessage());
    }
}

// Validasi Keranjang
private boolean validateCart() {
    if (cart == null || cart.isEmpty()) {
        showWarning("Keranjang kosong. Tidak ada produk untuk dibeli.");
        return false;
    }
    return true;
}

// Validasi ViewModel
private boolean validateViewModel() {
    if (viewModel == null) {
        showError("Checkout gagal: ViewModel belum diinisialisasi.");
        return false;
    }
    return true;
}

// Dialog Pemilihan Metode Pembayaran dengan Highlight Abu-abu
private Payment showPaymentSelectionDialog() {
    Payment[] paymentMethods = Payment.values();
    JPanel panel = createPaymentOptionsPanel(paymentMethods);

    panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Padding 20px di semua sisi

    int option = JOptionPane.showConfirmDialog(
        this,
        panel,
        "Pilih Metode Pembayaran",
        JOptionPane.OK_CANCEL_OPTION,
        JOptionPane.PLAIN_MESSAGE
    );

    if (option != JOptionPane.OK_OPTION) {
        showWarning("Anda harus memilih metode pembayaran.");
        return null;
    }

    for (Component comp : panel.getComponents()) {
        if (comp instanceof JPanel innerPanel) {
            for (Component innerComp : innerPanel.getComponents()) {
                if (innerComp instanceof JRadioButton radioButton && radioButton.isSelected()) {
                    innerPanel.setBackground(Color.LIGHT_GRAY); // Highlight abu-abu
                    return Payment.valueOf(radioButton.getText());
                }
            }
        }
    }

    showWarning("Anda harus memilih metode pembayaran.");
    return null;
}

// Membuat Panel dengan Gambar Kecil dan RadioButton
private JPanel createPaymentOptionsPanel(Payment[] paymentMethods) {
    JPanel panel = new JPanel(new GridLayout(paymentMethods.length, 1, 10, 10));
    ButtonGroup buttonGroup = new ButtonGroup();

    for (Payment method : paymentMethods) {
        JPanel paymentOption = new JPanel(new BorderLayout());
        JRadioButton radioButton = new JRadioButton(method.name());
        radioButton.setOpaque(false); // Membuat radio button transparan terhadap latar belakang
        buttonGroup.add(radioButton);

        JLabel imageLabel = new JLabel();
        imageLabel.setIcon(resizeIcon(new ImageIcon(getBankLogoPath(method)), 120, 30)); // Ukuran gambar kecil

    

        // Menambahkan Strut untuk memberi ruang di antara gambar dan radio button
        paymentOption.add(imageLabel, BorderLayout.WEST);
        paymentOption.add(Box.createHorizontalStrut(10)); // Menambahkan ruang horizontal di antara gambar dan radio button
        paymentOption.add(radioButton, BorderLayout.CENTER);

        // Set background panel agar terlihat lebih baik
        paymentOption.setBackground(Color.WHITE);

        panel.add(paymentOption);
    }

    // Set background panel utama
    panel.setBackground(Color.WHITE);

    return panel;
}



// Resize Icon Utility
private Icon resizeIcon(ImageIcon icon, int width, int height) {
    Image img = icon.getImage();
    Image resizedImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
    return new ImageIcon(resizedImg);
}

// Mendapatkan Path Logo Bank
private String getBankLogoPath(Payment method) {
    return switch (method) {
        case BCA -> "assets/bca.png";
        case BRI -> "assets/bri.png";
        case MANDIRI -> "assets/mandiri.png";
        default -> "assets/mandiri.png";
    };
}

// Input Nomor Rekening
private String inputRekeningNumber() {
    String rekeningNumber = JOptionPane.showInputDialog(this, "Masukkan nomor rekening Anda:");
    if (rekeningNumber == null || rekeningNumber.trim().isEmpty()) {
        showWarning("Nomor rekening harus diisi.");
        return null;
    } else if (rekeningNumber.length() < 10 || rekeningNumber.length() > 13) {
        showWarning("Nomor rekening harus terdiri dari 10 hingga 13 digit.");
        return null;
    }
    JOptionPane.showMessageDialog(
        this,
        "Nomor rekening Anda: " + rekeningNumber,
        "Konfirmasi Rekening",
        JOptionPane.INFORMATION_MESSAGE
    );
    return rekeningNumber;
}

// Mendapatkan Produk yang Dipilih
private List<Map<String, Object>> getSelectedProducts() {
    List<Map<String, Object>> data = new ArrayList<>();
    for (Map.Entry<ProductModel, Integer> entry : cart.entrySet()) {
        ProductModel productModel = entry.getKey();
        int quantity = entry.getValue();
        JCheckBox checkBox = selectedItems.get(productModel);

        if (checkBox != null && checkBox.isSelected()) {
            Map<String, Object> productData = new HashMap<>();
            productData.put("product", productModel);
            productData.put("quantity", quantity);
            data.add(productData);
        }
    }
    return data;
}

// Proses Transaksi
private void processTransaction(List<Map<String, Object>> selectedProducts, Payment selectedPayment) {
    for (Map<String, Object> productData : selectedProducts) {
        ProductModel purchasedProduct = (ProductModel) productData.get("product");
        int quantity = (int) productData.get("quantity");
        String productId = purchasedProduct.getId();
        viewModel.purchase(productId, quantity);
        cart.remove(purchasedProduct);
    }

    TransactionSuccessView transactionSuccessView = new TransactionSuccessView(
        selectedProducts, userView, balanceViewModel, selectedPayment
    );
    transactionSuccessView.setVisible(true);
    userView.cartLabel().setText("0");
    this.dispose();
}

// Helper untuk Menampilkan Pesan Peringatan
private void showWarning(String message) {
    JOptionPane.showMessageDialog(this, message, "Peringatan", JOptionPane.WARNING_MESSAGE);
}

// Helper untuk Menampilkan Pesan Error
private void showError(String message) {
    JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
}

    
    
    /**
     * Metode untuk kembali ke tampilan Pengguna.
     */
    private void goBackToUserView() {
        this.setVisible(false); // Menyembunyikan tampilan Keranjang
        userView.setVisible(true); // Menampilkan tampilan Pengguna
    }
}
