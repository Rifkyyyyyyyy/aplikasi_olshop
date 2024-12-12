package presentation.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import domain.model.balance.BalanceModel;
import domain.model.product.ProductModel;
import presentation.viewModel.balance.BalanceViewModel;




public class TransactionSuccessView extends JFrame {

    private final BalanceViewModel viewModel;
    private final List<ProductModel> productList;

 

    // Constructor
    public TransactionSuccessView(List<ProductModel> productList, UserView userView, BalanceViewModel viewModel) {
        this.viewModel = viewModel;
        this.productList = productList;

        if (productList == null || productList.isEmpty() || userView == null) {
            throw new IllegalArgumentException("ProductList tidak boleh kosong dan UserView tidak boleh null");
        }

        // Konfigurasi utama frame
        setTitle("Struk Pembayaran - Yoto App");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 600);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);
   
        
        JPanel appBarPanel = new JPanel(new BorderLayout());
        appBarPanel.setBackground(Color.decode("#238b45"));
        appBarPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15)); // Padding 15px di semua sisi

        // Tombol kembali
        JButton backButton = new JButton("Kembali");
        backButton.setFont(new Font("Arial", Font.PLAIN, 14));
        backButton.setBackground(Color.decode("#238b45"));
        backButton.setForeground(Color.WHITE);
        backButton.setFocusPainted(false);
        backButton.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        backButton.addActionListener(e -> {
            dispose();
            userView.reloadProducts();
            userView.setVisible(true);
        });

    
        JLabel titleLabel = new JLabel("Yoto App", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);

      
        appBarPanel.add(backButton, BorderLayout.WEST); 
        appBarPanel.add(titleLabel, BorderLayout.CENTER); 


        JPanel productListPanel = new JPanel();
        productListPanel.setLayout(new BoxLayout(productListPanel, BoxLayout.Y_AXIS));
        productListPanel.setBackground(Color.WHITE);
        productListPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel itemList = new JLabel("Item yang dibeli:");
        productListPanel.add(itemList);
        productListPanel.add(Box.createVerticalStrut(10));

        double totalPrice = 0;
        for (ProductModel product : productList) {
            productListPanel.add(createLabel("- " + product.getName()));
            totalPrice += product.getPrice();
        }

        // Total harga
        JLabel totalLabel = new JLabel("Total Harga: Rp " + String.format("%,.0f", totalPrice), SwingConstants.RIGHT);
        totalLabel.setFont(new Font("Arial", Font.BOLD, 16));
        totalLabel.setForeground(Color.BLACK);
        totalLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        productListPanel.add(Box.createVerticalStrut(10)); 
        productListPanel.add(totalLabel);

 
        JScrollPane scrollPane = new JScrollPane(productListPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());


        JPanel contactPanel = new JPanel();
        contactPanel.setLayout(new BoxLayout(contactPanel, BoxLayout.Y_AXIS));
        contactPanel.setBackground(new Color(245, 245, 245));
        contactPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String transactionDateTime = LocalDateTime.now().format(formatter);

        contactPanel.add(createLabel("Waktu Transaksi: " + transactionDateTime));
        contactPanel.add(createLabel("Alamat: Jalan Medan Merdeka Selatan No. 8-9, Jakarta Pusat"));
        contactPanel.add(createLabel("DKI Jakarta - 10110"));
        contactPanel.add(createLabel("Telp: 3456058, 3822800, 3456388"));
        contactPanel.add(createLabel("E-mail: Yoto@id, Yotoshop@gmail.com"));
        contactPanel.add(createLabel("Website: www.yoto.id"));
        contactPanel.add(Box.createVerticalStrut(20));
        contactPanel.add(createLabel("Terima kasih telah menggunakan Yoto App!"));

        // Tambahkan komponen ke frame
        add(appBarPanel, BorderLayout.NORTH); 
        add(scrollPane, BorderLayout.CENTER); 
        add(contactPanel, BorderLayout.SOUTH); 

        // Tampilkan frame
        SwingUtilities.invokeLater(() -> setVisible(true));
        setBalanceForSeller();
    }



    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.PLAIN, 14));
        label.setForeground(Color.DARK_GRAY);
        return label;
    }

private void setBalanceForSeller() {
    for (ProductModel product : productList) {
            BigDecimal amount = BigDecimal.valueOf(product.getPrice());
        String sellerUid = product.getUserId();
        Timestamp lastUpdate =  Timestamp.valueOf(LocalDateTime.now());
        Timestamp createAt =  Timestamp.valueOf(LocalDateTime.now());
        BalanceModel balans = new BalanceModel(sellerUid, amount, lastUpdate, createAt);

        viewModel.insertBalance(balans);
    }
}
}
