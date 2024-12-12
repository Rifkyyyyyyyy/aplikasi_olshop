package presentation.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.text.NumberFormat;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.NumberFormatter;

import domain.model.product.ProductModel;
import presentation.viewModel.balance.BalanceViewModel;
import presentation.viewModel.product.ProductViewModel;


public class SellerView extends JFrame {
    private final ProductViewModel productViewModel;
    private final BalanceViewModel balanceViewModel;

    private JTable productTable;
    private DefaultTableModel tableModel;

    // Input fields

    private JTextField nameField;
    private JTextField descriptionField;
    private JFormattedTextField priceField;
    private JTextField stockField;
    private JLabel nameLabel;
    private JLabel balanceLabel;


    private final String uid;
    private final String user;

    /**
     * Konstruktor untuk SellerView.
     * 
     * @param productViewModel ViewModel untuk produk
     * @param uid ID pengguna penjual
     */
    public SellerView(ProductViewModel productViewModel, String uid , String user , BalanceViewModel viewModel) {
        this.productViewModel = productViewModel;
        this.user = user;
        this.balanceViewModel = viewModel;

        setTitle("Manajemen Produk");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        this.uid = uid;

        initializeComponents();
        loadProducts();
        loadSellerBalance();
    }

    /**
     * Metode untuk menginisialisasi komponen-komponen UI pada SellerView.
     */
    private void initializeComponents() {
        // Main layout
        setLayout(new BorderLayout());

        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15)); 
        nameLabel = new JLabel("Selamat datang" + " " + user); 
      
        balanceLabel = new JLabel("Saldo kamu Rp 0"); 

        headerPanel.add(nameLabel);
        headerPanel.add(Box.createHorizontalStrut(20));
        headerPanel.add(balanceLabel);

        add(headerPanel, BorderLayout.NORTH);

        // Table
        tableModel = new DefaultTableModel(new String[]{"ID", "Nama", "Deskripsi", "Harga", "Stok"}, 0);
        productTable = new JTable(tableModel);
        JScrollPane tableScrollPane = new JScrollPane(productTable);
        add(tableScrollPane, BorderLayout.CENTER);

        // Button panel
        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Tambah");
        JButton updateButton = new JButton("Perbarui");
        JButton deleteButton = new JButton("Hapus");

        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Button actions
        addButton.addActionListener(s -> showAddProductDialog());
        updateButton.addActionListener(s -> showUpdateProductDialog());
        deleteButton.addActionListener(s -> showDeleteProductDialog());
    }

    /**
     * Metode untuk memuat produk berdasarkan seller UID.
     */
    private void loadProducts() {
        productViewModel.getAllProductsBySellerUId(uid).thenAccept(products -> {
            SwingUtilities.invokeLater(() -> {
                tableModel.setRowCount(0);
                for (ProductModel product : products) {
                    tableModel.addRow(new Object[]{
                            product.getId(),
                            product.getName(),
                            product.getDescription(),
                            product.getPrice(),
                            product.getStock()
                    });
                }
            });
        });
    }


    private void loadSellerBalance() {
        balanceViewModel.getBalance(uid).thenAccept((balance) -> {
            SwingUtilities.invokeLater(() -> {
                balanceLabel.setText("Saldo kamu Rp " + balance.getBalance()); // Update balance label
            });
        });
    }


    /**
     * Metode untuk menampilkan dialog Tambah Produk.
     */
    private void showAddProductDialog() {
        JDialog addDialog = new JDialog(this, "Tambah Produk Baru", true);
        addDialog.setLayout(new BorderLayout());
        addDialog.setSize(400, 300);
        addDialog.setLocationRelativeTo(this);

        // Set white background and border
        addDialog.getContentPane().setBackground(Color.WHITE);

        // Create panel with padding
        JPanel panel = new JPanel(new GridLayout(6, 2, 10, 10));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15)); // 15px padding inside the panel

        // Input fields for Add product
        panel.add(new JLabel("Nama:"));
        nameField = new JTextField();
        panel.add(nameField);

        panel.add(new JLabel("Deskripsi:"));
        descriptionField = new JTextField();
        panel.add(descriptionField);

        panel.add(new JLabel("Harga:"));
        priceField = createRupiahFormattedField();
        panel.add(priceField);

        panel.add(new JLabel("Stok:"));
        stockField = new JTextField();
        panel.add(stockField);

        // Add buttons
        JButton saveButton = new JButton("Simpan");
        JButton cancelButton = new JButton("Batal");
        panel.add(saveButton);
        panel.add(cancelButton);

        saveButton.addActionListener(e -> {
            addProduct();
            addDialog.dispose(); // Close dialog after saving product
        });
        cancelButton.addActionListener(e -> addDialog.dispose());

        // Add panel to the dialog
        addDialog.add(panel, BorderLayout.CENTER);

        addDialog.setVisible(true);
    }

    /**
     * Metode untuk menampilkan dialog Perbarui Produk.
     */
    private void showUpdateProductDialog() {
        int selectedRow = productTable.getSelectedRow();
        if (selectedRow >= 0) {
            String productId = (String) tableModel.getValueAt(selectedRow, 0);
            String name = (String) tableModel.getValueAt(selectedRow, 1);
            String description = (String) tableModel.getValueAt(selectedRow, 2);
            double price = (double) tableModel.getValueAt(selectedRow, 3);
            int stock = (int) tableModel.getValueAt(selectedRow, 4);

            JDialog updateDialog = new JDialog(this, "Perbarui Produk", true);
            updateDialog.setLayout(new BorderLayout());
            updateDialog.setSize(400, 300);
            updateDialog.setLocationRelativeTo(this);

            // Set white background and border
            updateDialog.getContentPane().setBackground(Color.WHITE);

            // Create panel with padding
            JPanel panel = new JPanel(new GridLayout(6, 2, 10, 10));
            panel.setBackground(Color.WHITE);
            panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

            // Input fields for Update product
            panel.add(new JLabel("Nama:"));
            nameField = new JTextField(name);
            panel.add(nameField);

            panel.add(new JLabel("Deskripsi:"));
            descriptionField = new JTextField(description);
            panel.add(descriptionField);

            panel.add(new JLabel("Harga:"));
            priceField = createRupiahFormattedField();
            priceField.setValue(price);
            panel.add(priceField);

            panel.add(new JLabel("Stok:"));
            stockField = new JTextField(String.valueOf(stock));
            panel.add(stockField);

            // Add buttons
            JButton saveButton = new JButton("Simpan");
            JButton cancelButton = new JButton("Batal");
            panel.add(saveButton);
            panel.add(cancelButton);

            saveButton.addActionListener(e -> updateProduct(productId));
            cancelButton.addActionListener(e -> updateDialog.dispose());

            updateDialog.add(panel, BorderLayout.CENTER);

            updateDialog.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Silakan pilih produk yang akan diperbarui.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Metode untuk menampilkan dialog Hapus Produk.
     */
    private void showDeleteProductDialog() {
        int selectedRow = productTable.getSelectedRow();
        if (selectedRow >= 0) {
            String productId = (String) tableModel.getValueAt(selectedRow, 0);
            int confirm = JOptionPane.showConfirmDialog(this, "Apakah Anda yakin ingin menghapus produk ini?", "Hapus Produk", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                productViewModel.deleteProduct(productId).thenRun(this::loadProducts);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Silakan pilih produk yang akan dihapus.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Metode untuk membuat JFormattedTextField dengan format Rupiah.
     * 
     * @return JFormattedTextField dengan format Rupiah
     */
    private JFormattedTextField createRupiahFormattedField() {
        NumberFormat format = NumberFormat.getInstance();
        format.setMaximumFractionDigits(0);  // Tidak ada pecahan angka setelah koma
        NumberFormatter formatter = new NumberFormatter(format);
        formatter.setAllowsInvalid(false);  // Menghindari nilai yang tidak valid

        JFormattedTextField formattedField = new JFormattedTextField(formatter);
        formattedField.setValue(0);
        return formattedField;
    }

    /**
     * Metode untuk menambahkan produk baru.
     */
    private void addProduct() {
        try {
            String name = nameField.getText();
            String description = descriptionField.getText();
            double price = ((Number) priceField.getValue()).doubleValue();
            int stock = Integer.parseInt(stockField.getText());
            String productId = "Yoto-" + (int) (Math.random() * Integer.MAX_VALUE);
            ProductModel product = new ProductModel(productId, name, description, price, stock, uid , user);
            productViewModel.addProduct(product).thenRun(this::loadProducts);
            JOptionPane.showMessageDialog(this, "Produk berhasil ditambahkan.", "Sukses", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Input tidak valid: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Metode untuk memperbarui produk yang sudah ada.
     * 
     * @param productId ID produk yang akan diperbarui
     */
    private void updateProduct(String productId) {
        try {
            String name = nameField.getText();
            String description = descriptionField.getText();
            double price = ((Number) priceField.getValue()).doubleValue();
            int stock = Integer.parseInt(stockField.getText());
            ProductModel product = new ProductModel(productId, name, description, price, stock, uid , user);
            productViewModel.updateProduct(product).thenRun(this::loadProducts);
            JOptionPane.showMessageDialog(this, "Produk berhasil diperbarui.", "Sukses", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Input tidak valid: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

 
}