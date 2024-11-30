package domain.repository.auth;

import java.util.concurrent.CompletableFuture;

import domain.model.users.UsersModel;

public interface UserRepository {
    public CompletableFuture<UsersModel> getUserByUsernameAndPassword(String username, String password);

    public CompletableFuture<Boolean> registerUser(UsersModel user);

}
