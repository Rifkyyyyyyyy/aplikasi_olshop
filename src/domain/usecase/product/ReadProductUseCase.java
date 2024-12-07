package domain.usecase.product;

import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import core.usecase.UsecaseApp;
import domain.model.product.ProductModel;
import domain.repository.product.ProductRepository;

public class ReadProductUseCase extends UsecaseApp<List<ProductModel>, Void> {

    private final ProductRepository productRepository;

    // Konstruktor untuk menerima ProductRepository
    public ReadProductUseCase(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public CompletableFuture<List<ProductModel>> call(Void params) {

        try {
            // Mengambil semua produk dari repositori
            return productRepository.getAllProducts();
        } catch (SQLException e) {
            throw new RuntimeException(e.toString());
        }

    }
}
