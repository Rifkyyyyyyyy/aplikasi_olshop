package domain.usecase.product;

import java.sql.SQLException;
import java.util.concurrent.CompletableFuture;

import core.usecase.UsecaseApp;
import domain.model.product.ProductModel;
import domain.repository.product.ProductRepository;

public class UpdateProductUsecase extends UsecaseApp<Void, ProductModel> {
    private final ProductRepository productRepository;

   public UpdateProductUsecase(ProductRepository product) {
        this.productRepository = product;
    }

    @Override
    public CompletableFuture<Void> call(ProductModel params) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                productRepository.updateProduct(params);
                return null;
            } catch (SQLException e) {
                throw new RuntimeException(e.toString());
            }
        });
    }
}
