package datasources.repository.balance;

import java.util.concurrent.CompletableFuture;

import datasources.service.balanceServices.BalanceServices;
import domain.model.balance.BalanceModel;
import domain.repository.balance.BalanceRepository;

public class BalanceRepositoryImpl implements  BalanceRepository {
    private final BalanceServices services;

    public  BalanceRepositoryImpl(BalanceServices services) {
        this.services = services;
    }

    @Override
    public CompletableFuture<Boolean> updateBalance(BalanceModel data) {
        return services.updateBalance(data);
    }

    @Override
    public CompletableFuture<BalanceModel> getBalanceById(String id) {
        return  services.getBalanceByUserId(id);
    }
    
}
