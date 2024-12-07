package domain.usecase.product;

import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import core.usecase.UsecaseApp;
import domain.model.product.ProductModel;
import domain.repository.product.ProductRepository;

public class GetProductBySellerUidUsecase extends UsecaseApp<List<ProductModel>, String> {
    private final ProductRepository productRepository;

    public GetProductBySellerUidUsecase(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public CompletableFuture<List<ProductModel>> call(String params) {
        try {

            return productRepository.getAllProductsBySellerUid(params);
        } catch (SQLException e) {
            throw new RuntimeException(e.toString());
        }
    }
}