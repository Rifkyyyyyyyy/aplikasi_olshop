package datasources.repository.userRepo;

import java.util.concurrent.CompletableFuture;

import datasources.service.userServices.UserServices;
import domain.model.users.UsersModel;
import domain.repository.auth.UserRepository;

public class UserRepositoryImpl implements UserRepository {

    private final UserServices service;

    public UserRepositoryImpl(UserServices service) {
        this.service = service;
    }

    @Override
    public CompletableFuture<Boolean> registerUser(UsersModel user) {
        return service.registerUser(user);
    }

    @Override
    public CompletableFuture<UsersModel> getUserByUsernameAndPassword(String username, String password) {
        return service.getUserByUsernameAndPassword(username, password);
    }

}
