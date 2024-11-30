package domain.usecase.auth;

import java.util.concurrent.CompletableFuture;

import core.usecase.UsecaseApp;
import domain.model.users.UsersModel;
import domain.repository.auth.UserRepository;

public class RegisterUsecase extends UsecaseApp<Boolean, UsersModel> {
    private final UserRepository userRepository;

    public RegisterUsecase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public CompletableFuture<Boolean> register(UsersModel data) {

        return userRepository.registerUser(data);

    }
}
