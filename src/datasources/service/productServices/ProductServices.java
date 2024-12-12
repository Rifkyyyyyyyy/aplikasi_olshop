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
    }

    // Add product
    public CompletableFuture<Boolean> addProduct(ProductModel product) {
        return CompletableFuture.supplyAsync(() -> {
            String sql = "INSERT INTO products (id, name, description, price, stock, user_id, seller_name) VALUES (?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setString(1, product.getId());  // id sebagai String
                stmt.setString(2, product.getName());
                stmt.setString(3, product.getDescription());
                stmt.setDouble(4, product.getPrice());
                stmt.setInt(5, product.getStock());
                stmt.setString(6, product.getUserId());  // user_id sebagai String
                stmt.setString(7, product.getSellerName());  // seller_name
                int affectedRows = stmt.executeUpdate();

                return affectedRows > 0;
            } catch (SQLException e) {
                throw new RuntimeException("Error inserting product", e);
            }
        }, executorService);
    }

    // Get a product by ID
    public CompletableFuture<ProductModel> getProductById(String id) {  // id sebagai String
        return CompletableFuture.supplyAsync(() -> {
            String sql = "SELECT * FROM products WHERE id = ?";
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setString(1, id);  // id sebagai String
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        return new ProductModel(
                                rs.getString("id"),
                                rs.getString("name"),
                                rs.getString("description"),
                                rs.getDouble("price"),
                                rs.getInt("stock"),
                                rs.getString("user_id"),
                                rs.getString("seller_name") // Menambahkan seller_name
                        );
                    }
                }
            } catch (SQLException e) {
                throw new RuntimeException("Error fetching product by ID", e);
            }
            return null; // If no product found, return null
        }, executorService);
    }

    // Get all products
    public CompletableFuture<List<ProductModel>> getAllProducts() {
        return CompletableFuture.supplyAsync(() -> {
            List<ProductModel> products = new ArrayList<>();
            String sql = "SELECT * FROM products WHERE stock > 0"; // Menambahkan kondisi stok > 0
            try (Statement stmt = connection.createStatement();
                 ResultSet rs = stmt.executeQuery(sql)) {
                while (rs.next()) {
                    ProductModel product = new ProductModel(
                            rs.getString("id"),
                            rs.getString("name"),
                            rs.getString("description"),
                            rs.getDouble("price"),
                            rs.getInt("stock"),
                            rs.getString("user_id"),
                            rs.getString("seller_name")  // Menambahkan seller_name
                    );
                    products.add(product);
                }
            } catch (SQLException e) {
                throw new RuntimeException("Error fetching all products", e);
            }
            return products;
        }, executorService);
    }

    // Update product
    public CompletableFuture<Boolean> updateProduct(ProductModel product) {
        return CompletableFuture.supplyAsync(() -> {
            String sql = "UPDATE products SET name = ?, description = ?, price = ?, stock = ?, user_id = ?, seller_name = ? WHERE id = ?";
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setString(1, product.getName());
                stmt.setString(2, product.getDescription());
                stmt.setDouble(3, product.getPrice());
                stmt.setInt(4, product.getStock());
                stmt.setString(5, product.getUserId());  // user_id sebagai String
                stmt.setString(6, product.getSellerName());  // Menambahkan seller_name
                stmt.setString(7, product.getId());      // id sebagai String
                 int rows = stmt.executeUpdate();
                 return  rows > 0;
            } catch (SQLException e) {
                throw new RuntimeException("Error updating product", e);
            }
        }, executorService);
    }

    // Delete product by ID
    public CompletableFuture<Void> deleteProduct(String id) {  // id sebagai String
        return CompletableFuture.runAsync(() -> {
            String sql = "DELETE FROM products WHERE id = ?";
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setString(1, id);  // id sebagai String
                stmt.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException("Error deleting product", e);
            }
        }, executorService);
    }

    // Get all products by seller's user_id
    public CompletableFuture<List<ProductModel>> getAllProductsBySellerUid(String userId) {
        return CompletableFuture.supplyAsync(() -> {
            List<ProductModel> products = new ArrayList<>();
            String sql = "SELECT p.id, p.name, p.description, p.price, p.stock, p.user_id, p.seller_name " +
                         "FROM products p " +
                         "JOIN users u ON p.user_id = u.id " +
                         "WHERE u.id = ? AND u.role = 'SELLER' AND p.stock > 0";  // Menambahkan kondisi stok > 0
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setString(1, userId);  // user_id sebagai String
                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        ProductModel product = new ProductModel(
                                rs.getString("id"),
                                rs.getString("name"),
                                rs.getString("description"),
                                rs.getDouble("price"),
                                rs.getInt("stock"),
                                rs.getString("user_id"),
                                rs.getString("seller_name")  // Menambahkan seller_name
                        );
                        products.add(product);
                    }
                }
            } catch (SQLException e) {
                throw new RuntimeException("Error fetching products for user with UID: " + userId, e);
            }
            return products;
        }, executorService);
    }

    // Purchase product (decrease stock)
    public CompletableFuture<Void> purchaseProduct(String productId, int quantity) {
        return CompletableFuture.runAsync(() -> {
            String sql = "UPDATE products SET stock = stock - ? WHERE id = ? AND stock >= ?";
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setInt(1, quantity);      // quantity untuk mengurangi stok
                stmt.setString(2, productId);  // productId sebagai String
                stmt.setInt(3, quantity);      // Pastikan stok mencukupi

                int rowsAffected = stmt.executeUpdate();
                
                if (rowsAffected == 0) {
                    throw new RuntimeException("Not enough stock for product ID: " + productId);
                }
            } catch (SQLException e) {
                throw new RuntimeException("Error purchasing product", e);
            }
        }, executorService);
    }
}
