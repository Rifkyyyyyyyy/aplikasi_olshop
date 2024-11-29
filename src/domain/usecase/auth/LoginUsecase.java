package domain.usecase.auth;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

import core.usecase.UsecaseApp;
import domain.model.users.UsersModel;
import domain.repository.auth.UserRepository;

public class LoginUsecase extends UsecaseApp<UsersModel, Map<String, Object>> {
  private final UserRepository userRepository;

    public LoginUsecase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public CompletableFuture<UsersModel> call(Map<String, Object> params) {
        final String userName = (String) params.get("userName");
        final String password = (String) params.get("password");

        return userRepository.getUserByUsernameAndPassword(userName, password);
    }

}
