package domain.usecase.product;

import java.util.concurrent.CompletableFuture;

import core.usecase.UsecaseApp;
import domain.model.product.ProductModel;
import domain.repository.product.ProductRepository;

public class GetProductById extends UsecaseApp<ProductModel, String> {
    private final ProductRepository productRepository;

    public GetProductById(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public CompletableFuture<ProductModel> call(String params) {
        try {
            return productRepository.getProductById(params);
        } catch (Exception e) {
            throw new RuntimeException(e.toString());
        }
    }
}
