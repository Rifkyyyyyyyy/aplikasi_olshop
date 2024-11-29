package presentation.view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.concurrent.ExecutionException;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.Font;

import domain.model.product.ProductModel;
import presentation.viewModel.product.ProductViewModel;

public class UserView extends JFrame {
    private final ProductViewModel viewModel;
    private String user;

    private final DefaultListModel<String> productListModel;
    private final JList<String> productList;

    public UserView(ProductViewModel viewModel) {
        this.viewModel = viewModel;

        setTitle("User view");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Top Panel
        JPanel topPanel = new JPanel(new BorderLayout());
        JLabel welcomeLabel = new JLabel("Welcome, " + user + "!");
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 18));
        topPanel.add(welcomeLabel, BorderLayout.CENTER);
        add(topPanel, BorderLayout.NORTH);

        // Center Panel - Product List
        productListModel = new DefaultListModel<>();
        productList = new JList<>(productListModel);
        JScrollPane scrollPane = new JScrollPane(productList);
        add(scrollPane, BorderLayout.CENTER);

        // Bottom Panel - Buttons
        JPanel bottomPanel = new JPanel(new GridLayout(1, 4, 10, 0));
        JButton addButton = new JButton("Add");
        JButton viewButton = new JButton("View All");
        JButton updateButton = new JButton("Update");
        JButton deleteButton = new JButton("Delete");

        bottomPanel.add(addButton);
        bottomPanel.add(viewButton);
        bottomPanel.add(updateButton);
        bottomPanel.add(deleteButton);
        add(bottomPanel, BorderLayout.SOUTH);

        // Button Actions
        addButton.addActionListener(_ -> openAddDialog());
        viewButton.addActionListener(_ -> loadAllProducts());
        updateButton.addActionListener(_ -> openUpdateDialog());
        deleteButton.addActionListener(_ -> deleteSelectedProduct());

        setLocationRelativeTo(null);
    }

    private void openAddDialog() {
        JTextField nameField = new JTextField();
        JTextField priceField = new JTextField();
        JTextField descriptionField = new JTextField();

        Object[] message = {
                "Name:", nameField,
                "Price:", priceField,
                "Description:", descriptionField,
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Add Product", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String name = nameField.getText();
            double price = Double.parseDouble(priceField.getText());
            String description = descriptionField.getText();

            // Generate random ID (replace this with a more appropriate ID generation
            // mechanism if needed)
            int productId = (int) (Math.random() * Integer.MAX_VALUE);
            ProductModel product = new ProductModel(productId, name, description, price, productId, productId);
            viewModel.addProduct(product)
                    .thenAccept(_ -> JOptionPane.showMessageDialog(this, "Product added successfully!"))
                    .exceptionally(e -> {
                        JOptionPane.showMessageDialog(this, "Failed to add product: " + e.getMessage());
                        return null;
                    });
        }
    }

    private void loadAllProducts() {
        viewModel.getAllProducts()
                .thenAccept(products -> {
                    productListModel.clear();
                    for (ProductModel product : products) {
                        productListModel.addElement(product.toString());
                    }
                })
                .exceptionally(e -> {
                    JOptionPane.showMessageDialog(this, "Failed to fetch products: " + e.getMessage());
                    return null;
                });
    }

    private void openUpdateDialog() {
        int selectedIndex = productList.getSelectedIndex();
        if (selectedIndex == -1) {
            JOptionPane.showMessageDialog(this, "Please select a product to update.");
            return;
        }

        String selectedProduct = productListModel.get(selectedIndex);
        int productId = extractProductId(selectedProduct);

        try {
            ProductModel product = viewModel.getProductById(productId).get();

            JTextField nameField = new JTextField(product.getName());
            JTextField priceField = new JTextField(String.valueOf(product.getPrice()));
            JTextField descriptionField = new JTextField(product.getDescription());

            Object[] message = {
                    "Name:", nameField,
                    "Price:", priceField,
                    "Description:", descriptionField,
            };

            int option = JOptionPane.showConfirmDialog(this, message, "Update Product", JOptionPane.OK_CANCEL_OPTION);
            if (option == JOptionPane.OK_OPTION) {
                // Update product details with the values from the fields
                product.setName(nameField.getText());
                product.setPrice(Double.parseDouble(priceField.getText()));
                product.setDescription(descriptionField.getText());

                viewModel.updateProduct(product)
                        .thenAccept(result -> JOptionPane.showMessageDialog(this, "Product updated successfully!"))
                        .exceptionally(e -> {
                            JOptionPane.showMessageDialog(this, "Failed to update product: " + e.getMessage());
                            return null;
                        });
            }
        } catch (InterruptedException | ExecutionException e) {
            JOptionPane.showMessageDialog(this, "Failed to load product details: " + e.getMessage());
        }
    }

    private void deleteSelectedProduct() {
        int selectedIndex = productList.getSelectedIndex();
        if (selectedIndex == -1) {
            JOptionPane.showMessageDialog(this, "Please select a product to delete.");
            return;
        }

        String selectedProduct = productListModel.get(selectedIndex);
        int productId = extractProductId(selectedProduct);

        viewModel.deleteProduct(productId)
                .thenAccept(result -> {
                    productListModel.remove(selectedIndex);
                    JOptionPane.showMessageDialog(this, "Product deleted successfully!");
                })
                .exceptionally(e -> {
                    JOptionPane.showMessageDialog(this, "Failed to delete product: " + e.getMessage());
                    return null;
                });
    }

    private int extractProductId(String productString) {
        // Assuming product string starts with ID: "ID: <id>, ..."
        String[] parts = productString.split(",");
        return Integer.parseInt(parts[0].split(":")[1].trim());
    }

    public void setUser(String user) {
        this.user = user;
        JLabel welcomeLabel = (JLabel) ((JPanel) getContentPane().getComponent(0)).getComponent(0);
        welcomeLabel.setText("Welcome, " + user + "!");
    }
}
