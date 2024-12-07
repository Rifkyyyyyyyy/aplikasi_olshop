package domain.usecase.product;

import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import core.usecase.UsecaseApp;
import domain.repository.product.ProductRepository;

public class PurchaseProductUsecase extends UsecaseApp<Void, Map<String , Object>> {
    private final ProductRepository productRepository;

    public  PurchaseProductUsecase(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public CompletableFuture<Void> call(Map<String, Object> params) {
        try {
            // Pastikan kunci sesuai dengan yang digunakan di `purchase`
            final String productId = (String) params.get("id");
            final int quantity = (int) params.get("quantity"); // Typo 'quanity' diperbaiki menjadi 'quantity'
    
            return productRepository.purchaseProduct(productId, quantity);
        } catch (SQLException e) {
            throw new RuntimeException(e.toString());
        }
    }
    
}
