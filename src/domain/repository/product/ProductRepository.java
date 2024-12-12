package domain.repository.product;

import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import domain.model.product.ProductModel;

public interface ProductRepository {
    public CompletableFuture<Boolean> addProduct(ProductModel product) throws SQLException;

    public CompletableFuture<List<ProductModel>> getAllProducts() throws SQLException;

    public CompletableFuture<ProductModel> getProductById(String id) throws SQLException;

    public CompletableFuture<Boolean> updateProduct(ProductModel product) throws SQLException;

    public CompletableFuture<Void> deleteProduct(String id) throws SQLException;

    public CompletableFuture<List<ProductModel>> getAllProductsBySellerUid(String uid) throws SQLException;

    public CompletableFuture<Void> purchaseProduct(String productId, int quantity)  throws SQLException;
}
