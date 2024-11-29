
import java.sql.SQLException;

import javax.swing.SwingUtilities;

import domain.repository.auth.UserRepository;
import domain.repository.product.ProductRepository;
import domain.usecase.auth.LoginUsecase;
import domain.usecase.auth.RegisterUsecase;
import domain.usecase.product.AddProductUsecase;
import domain.usecase.product.DeleteProductUseCase;
import domain.usecase.product.GetProductById;
import domain.usecase.product.ReadProductUseCase;
import domain.usecase.product.UpdateProductUsecase;
import presentation.view.AuthView;
import presentation.view.SellerView;
import presentation.view.UserView;
import presentation.viewModel.auth.AuthViewModel;
import presentation.viewModel.product.ProductViewModel;
import datasources.repository.productRepo.ProductRepositoryImpl;
import datasources.repository.userRepo.UserRepositoryImpl;
import datasources.service.conectionServices.ConnectionServices;
import datasources.service.productServices.ProductServices;
import datasources.service.userServices.UserServices;

class App {

    // viewmodel
    private final AuthViewModel authViewModel;
    private final ProductViewModel productViewModel;

    // usecase
    private final LoginUsecase loginUsecase;
    private final RegisterUsecase registerUsecase;

    private final ReadProductUseCase readProductUseCase;
    private final UpdateProductUsecase updateProductUsecase;
    private final DeleteProductUseCase deleteProductUseCase;
    private final AddProductUsecase addProductUsecase;
    private final GetProductById getProductById;

    // services
    private final ConnectionServices connectionServices;
    private final UserServices userServices;
    private final ProductServices productServices;

    // repo
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public static void main(String[] args) {
        try {
            new App().runApp();
        } catch (SQLException e) {
            System.err.println("An error occurred while initializing the application: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public App() throws SQLException {
        // Initialize services first
        this.connectionServices = new ConnectionServices();
        this.userServices = new UserServices(connectionServices.getConnection());
        this.productServices = new ProductServices(connectionServices.getConnection());

        // Initialize repositories after services are initialized
        this.userRepository = new UserRepositoryImpl(userServices);
        this.productRepository = new ProductRepositoryImpl(productServices);

        // Initialize use cases
        this.loginUsecase = new LoginUsecase(userRepository);
        this.registerUsecase = new RegisterUsecase(userRepository);
        this.readProductUseCase = new ReadProductUseCase(productRepository);
        this.updateProductUsecase = new UpdateProductUsecase(productRepository);
        this.deleteProductUseCase = new DeleteProductUseCase(productRepository);
        this.addProductUsecase = new AddProductUsecase(productRepository);
        this.getProductById = new GetProductById(productRepository);

        // Initialize ViewModels
        this.authViewModel = new AuthViewModel(loginUsecase, registerUsecase);
        this.productViewModel = new ProductViewModel(addProductUsecase, deleteProductUseCase, updateProductUsecase,
                readProductUseCase, getProductById);
    }

    private void runApp() {
        // Start the Swing application
        SwingUtilities.invokeLater(() -> {
            AuthView authView = new AuthView(authViewModel, new UserView(productViewModel),
                    new SellerView(productViewModel));
            authView.setVisible(true);
        });
    }
}
