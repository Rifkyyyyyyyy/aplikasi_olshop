package presentation.viewModel.product;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import domain.model.product.ProductModel;
import domain.usecase.product.AddProductUsecase;
import domain.usecase.product.DeleteProductUseCase;
import domain.usecase.product.GetProductById;
import domain.usecase.product.GetProductBySellerUidUsecase;
import domain.usecase.product.PurchaseProductUsecase;
import domain.usecase.product.ReadProductUseCase;
import domain.usecase.product.UpdateProductUsecase;

public class ProductViewModel {
    private final AddProductUsecase addProductUsecase;
    private final DeleteProductUseCase deleteProductUseCase;
    private final UpdateProductUsecase updateProductUsecase;
    private final ReadProductUseCase readProductUseCase;
    private final GetProductById getProductByIdUsecase;
    private final GetProductBySellerUidUsecase getProductBySellerUidUsecase;
    private final PurchaseProductUsecase purchaseProductUsecase;

    // Konstruktor untuk inisialisasi dependensi yang diperlukan oleh
    // ProductViewModel
    public ProductViewModel(AddProductUsecase addProductUsecase,
            DeleteProductUseCase deleteProductUseCase,
            UpdateProductUsecase updateProductUsecase,
            ReadProductUseCase readProductUseCase,
            GetProductBySellerUidUsecase getProductBySellerUidUsecase,
            PurchaseProductUsecase purchaseProductUsecase1,
            GetProductById getProductByIdUsecase) {
        this.addProductUsecase = addProductUsecase;
        this.deleteProductUseCase = deleteProductUseCase;
        this.updateProductUsecase = updateProductUsecase;
        this.readProductUseCase = readProductUseCase;
        this.getProductBySellerUidUsecase = getProductBySellerUidUsecase;
        this.purchaseProductUsecase = purchaseProductUsecase1;
        this.getProductByIdUsecase = getProductByIdUsecase;
    }

    /**
     * Metode untuk menambahkan produk.
     * 
     * @param product objek ProductModel yang berisi detail produk yang akan
     *                ditambahkan
     * @return CompletableFuture yang menunjukkan proses penambahan produk
     */
    public CompletableFuture<Boolean> addProduct(ProductModel product) {
        return addProductUsecase.call(product).thenApply((result) -> {
            if(result) {
                System.out.println("Produk berhasil ditambahkan: " + product.getName());
            } else {
                System.out.println("Gagal menambahkan produk: " + product.getName());
            }
            return result;
        });
    }

    /**
     * Metode untuk menghapus produk berdasarkan ID.
     * 
     * @param productId ID produk yang akan dihapus
     * @return CompletableFuture yang menunjukkan proses penghapusan produk
     */
    public CompletableFuture<Void> deleteProduct(String productId) {
        return deleteProductUseCase.call(productId)
                .thenRun(() -> System.out.println("Produk dengan ID " + productId + " berhasil dihapus."));
    }

    /**
     * Metode untuk memperbarui informasi produk.
     * 
     * @param product objek ProductModel yang berisi detail produk yang akan
     *                diperbarui
     * @return CompletableFuture yang menunjukkan proses pembaruan produk
     */
    public CompletableFuture<Boolean> updateProduct(ProductModel product) {
        return updateProductUsecase.call(product).thenApply((result) -> {
             if(result) {
                 System.out.println("Produk berhasil diperbarui: " + product.getName());
             } else {
                 System.out.println("Gagal memperbarui produk: " + product.getName());
             }

            return  result;
        });
    }

    /**
     * Metode untuk mengambil daftar semua produk.
     * 
     * @return CompletableFuture yang berisi daftar produk dalam bentuk
     *         List<ProductModel>
     */
    public CompletableFuture<List<ProductModel>> getAllProducts() {
        return readProductUseCase.call(null)
                .thenApply(products -> {
                    System.out.println("Berhasil mengambil daftar produk: " + products.size() + " item ditemukan.");
                    return products;
                });
    }

    public CompletableFuture<List<ProductModel>> getAllProductsBySellerUId(String uid) {
        return getProductBySellerUidUsecase.call(uid)
                .thenApply(products -> {
                    System.out.println("Berhasil mengambil daftar produk untuk seller: " + uid + products.size()
                            + " item ditemukan.");
                    return products;
                });
    }

    /**
     * Metode untuk mengambil produk berdasarkan ID.
     * 
     * @param productId ID produk yang akan diambil
     * @return CompletableFuture yang berisi produk yang ditemukan atau null jika
     *         tidak ada
     */
    public CompletableFuture<ProductModel> getProductById(String productId) {
        return getProductByIdUsecase.call(productId)
                .thenApply(product -> {
                    if (product != null) {
                        System.out.println("Produk ditemukan: " + product.getName());
                    } else {
                        System.out.println("Produk dengan ID " + productId + " tidak ditemukan.");
                    }
                    return product;
                });
    }

    public CompletableFuture<Void> purchase(String id, int quantity) {
        Map<String, Object> params = Map.of(
                "id", id,
                "quantity", quantity
        );
        return purchaseProductUsecase.call(params).thenRun(() -> {
            System.out.println("Product berhasil dibeli: " + id + ", Quantity: " + quantity);
        });
    }
    
}
