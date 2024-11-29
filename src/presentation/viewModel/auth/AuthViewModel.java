package presentation.viewModel.auth;

import domain.model.users.UsersModel;
import domain.usecase.auth.LoginUsecase;
import domain.usecase.auth.RegisterUsecase;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class AuthViewModel {
    private final LoginUsecase loginUsecase;
    private final RegisterUsecase registerUsecase;

    public AuthViewModel(LoginUsecase loginUsecase, RegisterUsecase registerUsecase) {
        this.loginUsecase = loginUsecase;
        this.registerUsecase = registerUsecase;
    }

    /**
     * Metode untuk melakukan login dan autentikasi pengguna.
     * 
     * @param username username pengguna
     * @param password password pengguna
     * @return CompletableFuture berisi UsersModel yang merepresentasikan pengguna yang berhasil login
     */
    public CompletableFuture<UsersModel> login(String username, String password) {
        Map<String, Object> params = Map.of(
                "userName", username,
                "password", password);

        return loginUsecase.call(params);
    }

    /**
     * Metode untuk melakukan registrasi pengguna baru.
     * 
     * @param newUser objek UsersModel yang berisi detail pengguna baru
     * @return CompletableFuture berisi Boolean yang menunjukkan keberhasilan atau kegagalan proses registrasi
     */
    public CompletableFuture<Boolean> register(UsersModel newUser) {
        return registerUsecase.register(newUser);
    }
}
