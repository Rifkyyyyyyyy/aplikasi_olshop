package domain.usecase.balance;

import java.sql.SQLException;
import java.util.concurrent.CompletableFuture;

import core.usecase.UsecaseApp;
import domain.model.balance.BalanceModel;
import domain.repository.balance.BalanceRepository;

public class GetBalanceByIdUsecase extends UsecaseApp<BalanceModel, String> {
    private final BalanceRepository repository;

    public GetBalanceByIdUsecase(BalanceRepository repository) {
        this.repository = repository;
    }

    @Override
    public CompletableFuture<BalanceModel> call(String params) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return  repository.getBalanceById(params).join();
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException("Error retrieving balance for user_id: " + params, e);
            }
        });
    }
}