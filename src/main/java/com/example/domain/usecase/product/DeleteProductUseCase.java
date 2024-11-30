package domain.usecase.product;

import java.sql.SQLException;
import java.util.concurrent.CompletableFuture;

import core.usecase.UsecaseApp;
import domain.repository.product.ProductRepository;

public class DeleteProductUseCase extends UsecaseApp<Void, Integer> {

    private final ProductRepository productRepository;

    public DeleteProductUseCase(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public CompletableFuture<Void> call(Integer productId) {
        return CompletableFuture.supplyAsync(() -> {
            try {

                productRepository.deleteProduct(productId);
                return null;
            } catch (SQLException e) {
                throw new RuntimeException(e.toString());
            }
        });
    }
}
