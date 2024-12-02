package presentation.view;

import javax.swing.JFrame;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import domain.model.product.ProductModel;
import presentation.viewModel.product.ProductViewModel;

import java.awt.*;

public class SellerView extends JFrame {
    private ProductViewModel productViewModel;

    private JTable productTable;
    private DefaultTableModel tableModel;

    // Input fields
    private JTextField idField;
    private JTextField nameField;
    private JTextField descriptionField;
    private JTextField priceField;
    private JTextField stockField;

    private int uid;

    public SellerView(ProductViewModel productViewModel) {
        this.productViewModel = productViewModel;

        setTitle("Product Management");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        initializeComponents();
        loadProducts();
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    private void initializeComponents() {
        // Main layout
        setLayout(new BorderLayout());

        // Table
        tableModel = new DefaultTableModel(new String[] { "ID", "Name", "Description", "Price", "Stock" }, 0);
        productTable = new JTable(tableModel);
        JScrollPane tableScrollPane = new JScrollPane(productTable);
        add(tableScrollPane, BorderLayout.CENTER);

        // Form panel
        JPanel formPanel = new JPanel(new GridLayout(6, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createTitledBorder("Product Details"));

        formPanel.add(new JLabel("ID:"));
        idField = new JTextField();
        idField.setEditable(false);
        formPanel.add(idField);

        formPanel.add(new JLabel("Name:"));
        nameField = new JTextField();
        formPanel.add(nameField);

        formPanel.add(new JLabel("Description:"));
        descriptionField = new JTextField();
        formPanel.add(descriptionField);

        formPanel.add(new JLabel("Price:"));
        priceField = new JTextField();
        formPanel.add(priceField);

        formPanel.add(new JLabel("Stock:"));
        stockField = new JTextField();
        formPanel.add(stockField);

        add(formPanel, BorderLayout.EAST);

        // Button panel
        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Add");
        JButton updateButton = new JButton("Update");
        JButton deleteButton = new JButton("Delete");

        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Button actions
        addButton.addActionListener(s -> addProduct());
        updateButton.addActionListener(s -> updateProduct());
        deleteButton.addActionListener(s -> deleteProduct());
    }

    private void loadProducts() {
        productViewModel.getAllProductsBySellerUId(uid).thenAccept(products -> {
            SwingUtilities.invokeLater(() -> {
                tableModel.setRowCount(0);
                for (ProductModel product : products) {
                    tableModel.addRow(new Object[] {
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

    private void addProduct() {
        try {
            String name = nameField.getText();
            String description = descriptionField.getText();
            double price = Double.parseDouble(priceField.getText());
            int stock = Integer.parseInt(stockField.getText());

            ProductModel product = new ProductModel(0, name, description, price, stock, uid); // Assuming userId = 1
            productViewModel.addProduct(product).thenRun(this::loadProducts);
            clearForm();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Invalid input: " + ex.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateProduct() {
        try {
            int id = Integer.parseInt(idField.getText());
            String name = nameField.getText();
            String description = descriptionField.getText();
            double price = Double.parseDouble(priceField.getText());
            int stock = Integer.parseInt(stockField.getText());

            ProductModel product = new ProductModel(id, name, description, price, stock, uid); // Assuming userId = 1
            productViewModel.updateProduct(product).thenRun(this::loadProducts);
            clearForm();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Invalid input: " + ex.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteProduct() {
        try {
            int selectedRow = productTable.getSelectedRow();
            if (selectedRow >= 0) {
                int productId = (int) tableModel.getValueAt(selectedRow, 0);
                productViewModel.deleteProduct(productId).thenRun(this::loadProducts);
            } else {
                JOptionPane.showMessageDialog(this, "Please select a product to delete.", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error deleting product: " + ex.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearForm() {
        idField.setText("");
        nameField.setText("");
        descriptionField.setText("");
        priceField.setText("");
        stockField.setText("");
    }

}
