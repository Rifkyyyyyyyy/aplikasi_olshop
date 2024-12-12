package domain.usecase.balance;

import java.sql.SQLException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import core.usecase.UsecaseApp;
import domain.model.balance.BalanceModel;
import domain.repository.balance.BalanceRepository;

public class UpdateBalanceUsecase extends  UsecaseApp<Boolean, BalanceModel> {
    private final BalanceRepository repository;

    public UpdateBalanceUsecase(BalanceRepository repository) {
        this.repository = repository;
    }


    @Override
    public CompletableFuture<Boolean> call(BalanceModel params) {
       return CompletableFuture.supplyAsync(() -> {
        try {
            return repository.updateBalance(params).get();
        } catch (InterruptedException | SQLException | ExecutionException e) {
            throw new RuntimeException(e.toString());
        }
       });
    }

}
