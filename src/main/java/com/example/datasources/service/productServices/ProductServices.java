package datasources.service.productServices;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import domain.model.product.ProductModel;

public class ProductServices {
    private final Connection connection;
    private final ExecutorService executorService;

    public ProductServices(Connection connection) {
        this.connection = connection;
        this.executorService = Executors.newCachedThreadPool();
    };

    public CompletableFuture<Void> addProduct(ProductModel product) {
        return CompletableFuture.runAsync(() -> {
            String sql = "INSERT INTO products (name, description, price, stock, user_id) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setString(1, product.getName());
                stmt.setString(2, product.getDescription());
                stmt.setDouble(3, product.getPrice());
                stmt.setInt(4, product.getStock());
                stmt.setInt(5, product.getUserId());
                stmt.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException("Error inserting product", e);
            }
        }, executorService);
    }

    // Get a product by ID
    public CompletableFuture<ProductModel> getProductById(int id) {
        return CompletableFuture.supplyAsync(() -> {
            String sql = "SELECT * FROM products WHERE id = ?";
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setInt(1, id);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        return new ProductModel(
                                rs.getInt("id"),
                                rs.getString("name"),
                                rs.getString("description"),
                                rs.getDouble("price"),
                                rs.getInt("stock"),
                                rs.getInt("user_id"));
                    }
                }
            } catch (SQLException e) {
                throw new RuntimeException("Error fetching product by ID", e);
            }
            return null; // If no product found, return null
        }, executorService);
    }

    // Get all products asynchronously
    public CompletableFuture<List<ProductModel>> getAllProducts() {
        return CompletableFuture.supplyAsync(() -> {
            List<ProductModel> products = new ArrayList<>();
            String sql = "SELECT * FROM products";
            try (Statement stmt = connection.createStatement();
                    ResultSet rs = stmt.executeQuery(sql)) {
                while (rs.next()) {
                    ProductModel product = new ProductModel(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("description"),
                            rs.getDouble("price"),
                            rs.getInt("stock"),
                            rs.getInt("user_id"));
                    products.add(product);
                }
            } catch (SQLException e) {
                throw new RuntimeException("Error fetching all products", e);
            }
            return products;
        }, executorService);
    }

    // Update an existing product asynchronously
    public CompletableFuture<Void> updateProduct(ProductModel product) {
        return CompletableFuture.runAsync(() -> {
            String sql = "UPDATE products SET name = ?, description = ?, price = ?, stock = ?, user_id = ? WHERE id = ?";
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setString(1, product.getName());
                stmt.setString(2, product.getDescription());
                stmt.setDouble(3, product.getPrice());
                stmt.setInt(4, product.getStock());
                stmt.setInt(5, product.getUserId());
                stmt.setInt(6, product.getId());
                stmt.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException("Error updating product", e);
            }
        }, executorService);
    }

    // Delete a product by ID asynchronously
    public CompletableFuture<Void> deleteProduct(int id) {
        return CompletableFuture.runAsync(() -> {
            String sql = "DELETE FROM products WHERE id = ?";
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setInt(1, id);
                stmt.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException("Error deleting product", e);
            }
        }, executorService);
    }

    public CompletableFuture<List<ProductModel>> getAllProductsBySellerUid(int uid) {

        return CompletableFuture.supplyAsync(() -> {
            List<ProductModel> products = new ArrayList<>();
            String sql = "SELECT * FROM products WHERE uid = ?";
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setInt(1, uid);
                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        ProductModel product = new ProductModel(
                                rs.getInt("id"),
                                rs.getString("name"),
                                rs.getString("description"),
                                rs.getDouble("price"),
                                rs.getInt("stock"),
                                rs.getInt("uid"));
                        products.add(product);
                    }
                }
            } catch (SQLException e) {
                throw new RuntimeException("Error fetching products for user with UID: " + uid, e);
            }
            return products;
        }, executorService);
    }

    public void shutdown() {
        executorService.shutdown();
    }
}
