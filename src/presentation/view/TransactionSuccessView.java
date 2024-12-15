package presentation.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import constant.payment.Payment;
import constant.style.ColorsApp;
import domain.model.balance.BalanceModel;
import domain.model.product.ProductModel;
import presentation.viewModel.balance.BalanceViewModel;


public class TransactionSuccessView extends JFrame {

    private final BalanceViewModel viewModel;
    private final List<Map<String, Object>> productList;


 
    public TransactionSuccessView(List<Map<String, Object>> productList, UserView userView, BalanceViewModel viewModel, Payment payment) {
        this.viewModel = viewModel;
        this.productList = productList;

    
        if (productList == null || productList.isEmpty() || userView == null) {
            throw new IllegalArgumentException("ProductList tidak boleh kosong dan UserView tidak boleh null");
        }
    
        setTitle("Struk Pembayaran - Yoto App");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 800);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);
    
        JPanel navbarPanel = new JPanel(new BorderLayout());
        navbarPanel.setBackground(ColorsApp.LIGHT_GRAY);
        navbarPanel.setPreferredSize(new Dimension(getWidth(), 70));
        navbarPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
    
        JLabel titleLabel = new JLabel("Struk Pembayaran", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(ColorsApp.PRIMARY);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0)); 
    
        JButton backButton = new JButton("Kembali");
        backButton.setFont(new Font("Arial", Font.PLAIN, 14));
        backButton.setBackground(ColorsApp.LIGHT_GRAY2);
        backButton.setForeground(ColorsApp.PRIMARY);
        backButton.setFocusPainted(false);
        backButton.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        backButton.addActionListener(e -> {
            dispose();
            userView.reloadProducts();
            userView.setVisible(true);
        });
    
        navbarPanel.add(backButton, BorderLayout.WEST);
        navbarPanel.add(titleLabel, BorderLayout.CENTER);
    
        JPanel bodyPanel = new JPanel();
        bodyPanel.setLayout(new BoxLayout(bodyPanel, BoxLayout.Y_AXIS));
        bodyPanel.setBackground(ColorsApp.LIGHT_GRAY2);
        bodyPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));
    
        JPanel paymentContainer = new JPanel();
        paymentContainer.setLayout(new BoxLayout(paymentContainer, BoxLayout.Y_AXIS));
        paymentContainer.setPreferredSize(new Dimension(getWidth() - 140, 500));
        paymentContainer.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
    
        JPanel paymentBodyPanel = new JPanel();
        paymentBodyPanel.setLayout(new BoxLayout(paymentBodyPanel, BoxLayout.Y_AXIS));
        paymentBodyPanel.setBackground(Color.WHITE);
        paymentBodyPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    
        JLabel headerLabel = new JLabel("Detail Pembayaran");
        headerLabel.setFont(new Font("Arial", Font.BOLD, 16));
        headerLabel.setForeground(Color.BLACK);
        paymentBodyPanel.add(headerLabel);
        paymentBodyPanel.add(Box.createVerticalStrut(10));
    
        JLabel paymentMethodLabel = new JLabel("Metode Pembayaran: " + payment.name());
        paymentMethodLabel.setFont(new Font("Arial", Font.BOLD, 14));
        paymentMethodLabel.setForeground(Color.BLACK);
        paymentBodyPanel.add(paymentMethodLabel);
        paymentBodyPanel.add(Box.createVerticalStrut(10));
    
        JLabel itemListLabel = new JLabel("Item yang dibeli:");
        itemListLabel.setFont(new Font("Arial", Font.BOLD, 14));
        paymentBodyPanel.add(itemListLabel);
        paymentBodyPanel.add(Box.createVerticalStrut(10));
    
        double totalPrice = 0;
        for (Map<String, Object> productData : productList) {
            ProductModel product = (ProductModel) productData.get("product");
            int quantity = (int) productData.get("quantity");
            paymentBodyPanel.add(createLabel(product.getName() + (quantity > 1 ? " x " + quantity : "")));
            totalPrice += product.getPrice() * quantity;
        }
    
        JLabel totalLabel = new JLabel("Total Harga: Rp " + String.format("%,.0f", totalPrice), SwingConstants.RIGHT);
        totalLabel.setFont(new Font("Arial", Font.BOLD, 16));
        totalLabel.setForeground(Color.BLACK);
        totalLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        paymentBodyPanel.add(Box.createVerticalStrut(10));
        paymentBodyPanel.add(totalLabel);
    
        JPanel footerPanel = new JPanel();
        footerPanel.setLayout(new BoxLayout(footerPanel, BoxLayout.Y_AXIS));
        footerPanel.setBackground(new Color(245, 245, 245));
        footerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String transactionDateTime = LocalDateTime.now().format(formatter);
    
        footerPanel.add(createLabel("Waktu Transaksi: " + transactionDateTime));
        footerPanel.add(createLabel("Alamat: Jalan Medan Merdeka Selatan No. 8-9, Jakarta Pusat"));
        footerPanel.add(createLabel("DKI Jakarta - 10110"));
        footerPanel.add(createLabel("Telp: 3456058, 3822800, 3456388"));
        footerPanel.add(createLabel("E-mail: Yoto@id, Yotoshop@gmail.com"));
        footerPanel.add(createLabel("Website: www.yoto.id"));
        footerPanel.add(Box.createVerticalStrut(20));
        footerPanel.add(createLabel("Terima kasih telah menggunakan Yoto App!"));
    
        paymentContainer.add(paymentBodyPanel);
        paymentContainer.add(footerPanel);
    
        add(navbarPanel, BorderLayout.NORTH);
        add(bodyPanel, BorderLayout.CENTER);
        bodyPanel.add(paymentContainer);
    
        SwingUtilities.invokeLater(() -> setVisible(true));
        setBalanceForSeller();
    }
    
    
    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.PLAIN, 14));
        label.setForeground(Color.DARK_GRAY);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        return label;
    }

    private void setBalanceForSeller() {
        for (Map<String, Object> productData : productList) {
            ProductModel product = (ProductModel) productData.get("product");

            if (product != null) {
                BigDecimal amount = BigDecimal.valueOf(product.getPrice());
                String sellerUid = product.getUserId();
                Timestamp lastUpdate = Timestamp.valueOf(LocalDateTime.now());
                Timestamp createAt = Timestamp.valueOf(LocalDateTime.now());

              
                BalanceModel balans = new BalanceModel(sellerUid, amount, lastUpdate, createAt);
                viewModel.updateBalance(balans);
            }
        }
    }
}