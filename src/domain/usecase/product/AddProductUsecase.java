package domain.usecase.product;

import java.sql.SQLException;
import java.util.concurrent.CompletableFuture;

import core.usecase.UsecaseApp;
import domain.model.product.ProductModel;
import domain.repository.product.ProductRepository;

public class AddProductUsecase extends UsecaseApp<Boolean, ProductModel> {

    private final ProductRepository productRepository;

    public AddProductUsecase(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public CompletableFuture<Boolean> call(ProductModel product) {
        return CompletableFuture.supplyAsync(() -> {
            try {
              return productRepository.addProduct(product).join();
            } catch (SQLException e) {
                throw new RuntimeException(e.toString());
            }
        });
    }
}
