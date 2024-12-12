package domain.repository.balance;

import java.sql.SQLException;
import java.util.concurrent.CompletableFuture;

import domain.model.balance.BalanceModel;

public interface BalanceRepository {
    public  CompletableFuture<Boolean> updateBalance (BalanceModel data) throws  SQLException;
    public  CompletableFuture<BalanceModel> getBalanceById(String id) throws  SQLException;
}
