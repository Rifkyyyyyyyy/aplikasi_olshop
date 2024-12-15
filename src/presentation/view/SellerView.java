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
import java.text.NumberFormat;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
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
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.text.NumberFormatter;

import components.button.RoundedButton;
import constant.style.ColorsApp;
import domain.model.product.ProductModel;
import presentation.viewModel.auth.AuthViewModel;
import presentation.viewModel.balance.BalanceViewModel;
import presentation.viewModel.product.ProductViewModel;


public class SellerView extends JFrame {
    private final ProductViewModel productViewModel;
    private final BalanceViewModel balanceViewModel;
    private final AuthViewModel authViewModel;

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
    public SellerView(ProductViewModel productViewModel, String uid , String user , BalanceViewModel viewModel , AuthViewModel auth) {
        this.productViewModel = productViewModel;
        this.user = user;
        this.balanceViewModel = viewModel;
        this.authViewModel = auth;

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
        setLayout(new BorderLayout());
    
        // Header Panel
        JPanel headerPanel = createHeaderPanel();
        add(headerPanel, BorderLayout.NORTH);
    
        // Product Table
        JScrollPane tableScrollPane = createProductTable();
        JPanel bodyPanel = new JPanel();
        bodyPanel.setBackground(Color.white);
        bodyPanel.setLayout(new BorderLayout());
        bodyPanel.add(tableScrollPane, BorderLayout.CENTER);
        add(bodyPanel, BorderLayout.CENTER);
    
        // Footer Panel (Label + Button Panel)
        JPanel footerPanel = createFooterPanel();
        add(footerPanel, BorderLayout.SOUTH);
    }
    
    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.X_AXIS));
        headerPanel.setBackground(ColorsApp.LIGHT_GRAY);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 5, 20, 15));
    
        nameLabel = new JLabel("Halo " + user);
        nameLabel.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 0));
        nameLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        nameLabel.setForeground(ColorsApp.PRIMARY);
        nameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
    
        JLabel logoLabel = createLogoLabel();
    
        JPanel balancePanel = createBalancePanel();
    
        headerPanel.add(nameLabel);
        headerPanel.add(Box.createHorizontalGlue());
        headerPanel.add(logoLabel);
        headerPanel.add(Box.createHorizontalGlue());
        headerPanel.add(balancePanel);
    
        return headerPanel;
    }
    
    private JLabel createLogoLabel() {
        JLabel logoLabel = new JLabel();
        ImageIcon logoIcon = new ImageIcon("assets/logo2.png");
        logoLabel.setIcon(logoIcon);
        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        logoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        logoLabel.setPreferredSize(new Dimension(logoIcon.getIconWidth(), 40));
        return logoLabel;
    }
    
    private JPanel createBalancePanel() {
        JPanel balancePanel = new JPanel();
        balancePanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        balancePanel.setBackground(ColorsApp.LIGHT_GRAY2);
        balancePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        balancePanel.setMaximumSize(new Dimension(150, 50));
    
        JLabel balanceLogoLabel = createBalanceLogoLabel();
        balanceLabel = new JLabel("Rp 0.0");
        balanceLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        balanceLabel.setForeground(ColorsApp.PRIMARY);
    
        balancePanel.add(balanceLogoLabel);
        balancePanel.add(Box.createHorizontalStrut(5));
        balancePanel.add(balanceLabel);
    
        return balancePanel;
    }
    
    private JLabel createBalanceLogoLabel() {
        JLabel balanceLogoLabel = new JLabel();
        ImageIcon balanceIcon = new ImageIcon("assets/money.png");
        Image scaledImage = balanceIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        balanceLogoLabel.setIcon(new ImageIcon(scaledImage));
        balanceLogoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        balanceLogoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        return balanceLogoLabel;
    }
    
    private JScrollPane createProductTable() {
        tableModel = new DefaultTableModel(new String[]{"ID", "Nama Produk", "Deskripsi Produk", "Harga Produk", "Stok Produk"}, 0);
        productTable = new JTable(tableModel);
        configureTableColumns();
        configureTableAppearance();
        
        // Remove black border from the table
        productTable.setBorder(BorderFactory.createEmptyBorder());
        
        // Remove border from the scroll pane
        JScrollPane scrollPane = new JScrollPane(productTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
    
        // Configure table header appearance
        JTableHeader header = productTable.getTableHeader();
        header.setBackground(ColorsApp.TERTIARY);
        header.setForeground(ColorsApp.PRIMARY);
        header.setPreferredSize(new Dimension(header.getPreferredSize().width, 50));
        
        return scrollPane;
    }
    
    private void configureTableColumns() {
        productTable.getColumnModel().getColumn(0).setMinWidth(0);
        productTable.getColumnModel().getColumn(0).setMaxWidth(0);
        productTable.getColumnModel().getColumn(0).setWidth(0);
    }
    
    private void configureTableAppearance() {
        productTable.setBackground(Color.white);
        productTable.setForeground(ColorsApp.PRIMARY);
        productTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (cell instanceof JLabel jLabel) {
                    jLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
                }
                return cell;
            }
        });
        productTable.setRowHeight(40);
    }
    
    private JPanel createFooterPanel() {
        JPanel containerPanel = new JPanel();
        containerPanel.setLayout(new BoxLayout(containerPanel, BoxLayout.X_AXIS));
        containerPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
    
        JButton labelPanel = createLabelPanel();
        JPanel buttonPanel = createButtonPanel();

        containerPanel.setBackground(ColorsApp.LIGHT_GRAY);
        containerPanel.add(labelPanel);
        containerPanel.add(Box.createHorizontalGlue());
        containerPanel.add(buttonPanel);
    
        return containerPanel;
    }
    
   private JButton createLabelPanel() {
    JButton buttonPanel = new JButton();

    // Set the icon for the button
    ImageIcon logoutIcon = new ImageIcon("assets/logout.png");
    Image scaledLogoutImage = logoutIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
    buttonPanel.setIcon(new ImageIcon(scaledLogoutImage));

    // Set the button properties
    buttonPanel.setContentAreaFilled(false); // Make the button background transparent
    buttonPanel.setBorderPainted(false); // Remove the border
    buttonPanel.setFocusPainted(false); // Remove the focus indicator
    buttonPanel.setPreferredSize(new Dimension(40, 40));
    buttonPanel.setMaximumSize(new Dimension(40, 40));


    buttonPanel.addActionListener((ActionEvent e) -> {
        logout();
    });

    return buttonPanel;

    }
        
    private void logout() {
        dispose();
        AuthView authView = new AuthView(authViewModel, productViewModel, balanceViewModel);
        authView.display();
    }
    
    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
    

        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
    
        ActionListener addButtonListener = e -> showAddProductDialog();
        ActionListener updateButtonListener = e -> showUpdateProductDialog();
        ActionListener deleteButtonListener = e -> showDeleteProductDialog();
    
        RoundedButton buttonAdd = createRoundedButton("Tambah Produk", addButtonListener, new Color(0, 123, 255));
        RoundedButton buttonUpdate = createRoundedButton("Update Produk", updateButtonListener, new Color(0, 123, 255));
        RoundedButton buttonDelete = createRoundedButton("Hapus Produk", deleteButtonListener, new Color(220, 53, 69));
    
        buttonPanel.add(buttonAdd);
        buttonPanel.add(buttonUpdate);
        buttonPanel.add(buttonDelete);
    
        return buttonPanel;
    }
    
    private RoundedButton createRoundedButton(String text, ActionListener listener, Color buttonColor) {
        return new RoundedButton(text, listener, 150, 50, buttonColor, Color.WHITE);
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
                            product.getId() ,
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
                balanceLabel.setText("Rp " + balance.getBalance()); // Update balance label
            });
        });
    }


    /**
     * Metode untuk menampilkan dialog Tambah Produk.
     */
    private void showAddProductDialog() {
        // Membuat dialog
        JDialog addDialog = new JDialog(this, "Tambah Produk Baru", true);
        addDialog.setLayout(new BorderLayout());
    
        // Ubah ukuran dialog (misalnya, 600x500 untuk lebar dan tinggi lebih besar)
        addDialog.setSize(600, 500);
        addDialog.setLocationRelativeTo(this);
    
        // Set white background dan border
        addDialog.getContentPane().setBackground(Color.WHITE);
        addDialog.getRootPane().setBorder(BorderFactory.createLineBorder(Color.BLACK, 1)); // Border 1px
    
        // Membuat panel dengan padding
        JPanel panel = new JPanel(new GridLayout(6, 2, 10, 10));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15)); // Padding 15px
    
        // Input fields untuk menambahkan produk
        panel.add(new JLabel("Nama produk:"));
        nameField = new JTextField();
        nameField.setBorder(BorderFactory.createCompoundBorder(
            nameField.getBorder(),
            BorderFactory.createEmptyBorder(10, 10, 10, 10) // Padding internal 10px
        ));
        panel.add(nameField);
    
        panel.add(new JLabel("Deskripsi produk:"));
        descriptionField = new JTextField();
        descriptionField.setBorder(BorderFactory.createCompoundBorder(
            descriptionField.getBorder(),
            BorderFactory.createEmptyBorder(10, 10, 10, 10) // Padding internal 10px
        ));
        panel.add(descriptionField);
    
        panel.add(new JLabel("Harga produk:"));
        priceField = createRupiahFormattedField();
        priceField.setBorder(BorderFactory.createCompoundBorder(
            priceField.getBorder(),
            BorderFactory.createEmptyBorder(10, 10, 10, 10) // Padding internal 10px
        ));
        panel.add(priceField);
    
        panel.add(new JLabel("Stok produk:"));
        stockField = new JTextField();
        stockField.setBorder(BorderFactory.createCompoundBorder(
            stockField.getBorder(),
            BorderFactory.createEmptyBorder(10, 10, 10, 10) // Padding internal 10px
        ));
        panel.add(stockField);
    
        // ActionListener untuk tombol
        ActionListener saveListener = e -> {
            addProduct();
            addDialog.dispose(); // Tutup dialog setelah menyimpan
        };
    
        ActionListener cancelListener = e -> addDialog.dispose(); // Tutup dialog tanpa menyimpan
    
        // Tombol untuk Simpan dan Batalkan
        RoundedButton buttonSave = new RoundedButton(
            "Buat", 
            saveListener, 
            150, 
            50, 
            new Color(40, 167, 69), // Hijau success Bootstrap (#28a745)
            Color.white // Teks putih
        );
    
        RoundedButton buttonCancel = new RoundedButton(
            "Batalkan", 
            cancelListener, 
            150, 
            50, 
            new Color(220, 53, 69), // Merah danger Bootstrap (#dc3545)
            Color.white // Teks putih
        );
    
        // Menambahkan tombol ke panel
        panel.add(buttonSave);
        panel.add(buttonCancel);
    
        // Tambahkan panel ke dialog
        addDialog.add(panel, BorderLayout.CENTER);
    
        // Tampilkan dialog
        addDialog.setVisible(true);
    }
    

    /**
     * Metode untuk menampilkan dialog Perbarui Produk.
     */
    
     private void showUpdateProductDialog() {
        int selectedRow = productTable.getSelectedRow();
        if (selectedRow >= 0) {
            // Ambil data produk dari tabel
            String productId = (String) tableModel.getValueAt(selectedRow, 0);
            String name = (String) tableModel.getValueAt(selectedRow, 1);
            String description = (String) tableModel.getValueAt(selectedRow, 2);
            double price = (double) tableModel.getValueAt(selectedRow, 3);
            int stock = (int) tableModel.getValueAt(selectedRow, 4);
    
            // Membuat dialog
            JDialog updateDialog = new JDialog(this, "Perbarui Produk", true);
            updateDialog.setLayout(new BorderLayout());
    
            // Ukuran dan posisi dialog
            updateDialog.setSize(600, 500);
            updateDialog.setLocationRelativeTo(this);
    
            // Set white background dan border
            updateDialog.getContentPane().setBackground(Color.WHITE);
            updateDialog.getRootPane().setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
    
            // Membuat panel dengan padding
            JPanel panel = new JPanel(new GridLayout(6, 2, 10, 10));
            panel.setBackground(Color.WHITE);
            panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
    
            // Input fields untuk memperbarui produk
            panel.add(new JLabel("Nama produk:"));
            nameField = new JTextField(name);
            nameField.setBorder(BorderFactory.createCompoundBorder(
                nameField.getBorder(),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
            ));
            panel.add(nameField);
    
            panel.add(new JLabel("Deskripsi produk:"));
            descriptionField = new JTextField(description);
            descriptionField.setBorder(BorderFactory.createCompoundBorder(
                descriptionField.getBorder(),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
            ));
            panel.add(descriptionField);
    
            panel.add(new JLabel("Harga produk:"));
            priceField = createRupiahFormattedField();
            priceField.setValue(price);
            priceField.setBorder(BorderFactory.createCompoundBorder(
                priceField.getBorder(),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
            ));
            panel.add(priceField);
    
            panel.add(new JLabel("Stok produk:"));
            stockField = new JTextField(String.valueOf(stock));
            stockField.setBorder(BorderFactory.createCompoundBorder(
                stockField.getBorder(),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
            ));
            panel.add(stockField);
    
            // ActionListener untuk tombol
            ActionListener saveListener = e -> {
                updateProduct(productId);
                updateDialog.dispose(); // Tutup dialog setelah menyimpan
            };
    
            ActionListener cancelListener = e -> updateDialog.dispose(); // Tutup dialog tanpa menyimpan
    
            // Tombol untuk Simpan dan Batalkan
            RoundedButton buttonSave = new RoundedButton(
                "Perbarui", 
                saveListener, 
                150, 
                50, 
                new Color(40, 167, 69), // Hijau success Bootstrap (#28a745)
                Color.white // Teks putih
            );
    
            RoundedButton buttonCancel = new RoundedButton(
                "Batalkan", 
                cancelListener, 
                150, 
                50, 
                new Color(220, 53, 69), // Merah danger Bootstrap (#dc3545)
                Color.white // Teks putih
            );
    
            // Menambahkan tombol ke panel
            panel.add(buttonSave);
            panel.add(buttonCancel);
    
            // Tambahkan panel ke dialog
            updateDialog.add(panel, BorderLayout.CENTER);
    
            // Tampilkan dialog
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
    
            // Membuat dialog
            JDialog confirmDialog = new JDialog(this, "Hapus Produk", true);
            confirmDialog.setLayout(new BorderLayout());
            confirmDialog.setSize(400, 200);
            confirmDialog.setLocationRelativeTo(this);
    
            // Panel utama dengan warna putih dan padding
            JPanel container = new JPanel(new BorderLayout());
            container.setBackground(Color.WHITE);
            container.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    
            // Label untuk pesan konfirmasi
            JLabel messageLabel = new JLabel("Apakah Anda yakin ingin menghapus produk ini?");
            messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
            messageLabel.setFont(new Font("Arial", Font.PLAIN, 16));
            container.add(messageLabel, BorderLayout.CENTER);
    
            // Panel untuk tombol
            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
            buttonPanel.setBackground(Color.WHITE);
    
            // Tombol Ya (Menggunakan RoundedButton)
            RoundedButton yesButton = new RoundedButton(
                "Ya", 
                e -> {
                    productViewModel.deleteProduct(productId).thenRun(this::loadProducts);
                    confirmDialog.dispose();
                },
                150, 50, 
                new Color(40, 167, 69), // Hijau success Bootstrap (#28a745)
                Color.white // Teks putih
            );
    
            // Tombol Tidak (Menggunakan RoundedButton)
            RoundedButton noButton = new RoundedButton(
                "Tidak", 
                e -> confirmDialog.dispose(),
                150, 50, 
                new Color(220, 53, 69), // Merah danger Bootstrap (#dc3545)
                Color.white // Teks putih
            );
    
            // Menambahkan tombol ke panel
            buttonPanel.add(yesButton);
            buttonPanel.add(noButton);
    
            // Menambahkan komponen ke dialog
            container.add(buttonPanel, BorderLayout.SOUTH);
            confirmDialog.add(container);
    
            // Tampilkan dialog
            confirmDialog.setVisible(true);
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
            String name = nameField.getText().trim();
            if (name.isEmpty()) {
                throw new IllegalArgumentException("Nama produk tidak boleh kosong.");
            }
    
            String description = descriptionField.getText().trim();
            if (description.isEmpty()) {
                throw new IllegalArgumentException("Deskripsi produk tidak boleh kosong.");
            }
    
            String priceText = priceField.getText().trim();
            if (priceText.isEmpty()) {
                throw new IllegalArgumentException("Harga produk tidak boleh kosong.");
            }
            double price;
            try {
                price = ((Number) priceField.getValue()).doubleValue();
                if (price <= 0) {
                    throw new IllegalArgumentException("Harga produk harus lebih dari 0.");
                }
            } catch (Exception e) {
                throw new IllegalArgumentException("Format harga produk tidak valid.");
            }
    
            String stockText = stockField.getText().trim();
            if (stockText.isEmpty()) {
                throw new IllegalArgumentException("Stok produk tidak boleh kosong.");
            }
            int stock;
            try {
                stock = Integer.parseInt(stockText);
                if (stock < 0) {
                    throw new IllegalArgumentException("Stok produk tidak boleh bernilai negatif.");
                }
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Format stok produk tidak valid.");
            }
    

            String productId = "Yoto-" + (int) (Math.random() * Integer.MAX_VALUE);
            ProductModel product = new ProductModel(productId, name, description, price, stock, uid, user);
            productViewModel.addProduct(product).thenRun(this::loadProducts);
    
            // Tampilkan pesan sukses
            JOptionPane.showMessageDialog(this, "Produk berhasil ditambahkan.", "Sukses", JOptionPane.INFORMATION_MESSAGE);
    
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            // Pesan error umum untuk exception lainnya
            JOptionPane.showMessageDialog(this, "Terjadi kesalahan: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
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