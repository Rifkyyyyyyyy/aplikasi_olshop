package datasources.repository.productRepo;

import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import datasources.service.productServices.ProductServices;
import domain.model.product.ProductModel;
import domain.repository.product.ProductRepository;

public class ProductRepositoryImpl implements ProductRepository {
    final ProductServices productService;

    public ProductRepositoryImpl(ProductServices service) {
        this.productService = service;
    }

    @Override
    public CompletableFuture<Void> addProduct(ProductModel product) throws SQLException {
        return productService.addProduct(product);
    }

    @Override
    public CompletableFuture<List<ProductModel>> getAllProducts() throws SQLException {
        return productService.getAllProducts();
    }

    @Override
    public CompletableFuture<ProductModel> getProductById(int id) throws SQLException {
        return productService.getProductById(id);
    }

    @Override
    public CompletableFuture<Void> updateProduct(ProductModel product) throws SQLException {
        return productService.updateProduct(product);
    }

    @Override
    public CompletableFuture<Void> deleteProduct(int id) throws SQLException {
        return productService.deleteProduct(id);
    }

    @Override
    public CompletableFuture<List<ProductModel>> getAllProductsBySellerUid(int uid) throws SQLException {
        return productService.getAllProductsBySellerUid(uid);
    }
}
